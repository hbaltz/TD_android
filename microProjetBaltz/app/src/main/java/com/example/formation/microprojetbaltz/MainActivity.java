package com.example.formation.microprojetbaltz;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dataccess.DataSource;
import dataccess.EnregistrementDataAccessObject;
import dataobjects.enreg_info;

public class MainActivity extends AppCompatActivity{

    private TextView textPosition, textEtat, textDesc, textType;
    private EditText editDesc;
    private Button buttonEnv, buttonPhoto, buttonVis ;
    private ImageView imageview ;
    private RadioButton radioButtonP,radioButtonD,radioButtonA;
    private RadioGroup group;

    private LocationManager lm;

    private double latitude = 49.48, longitude = 3.91, altitude; //Si Gps pas actif centre de Amifontaine de par défaut
    private float accuracy;
    private ArrayList<LocationProvider> providers;

    private String msg, desc, env, photo, msgMail, to, cc, sjt, panne, det, aut, type, tValue, vis;

    private int CAMERA_PIC_REQUEST = 2;

    private String mCurrentPhotoPath = "/data/data/com.example.formation.microprojetbaltz/picFolder/";

    // Variables pour la gestion de la base de données SQLite
    DataSource mdatasource = null;
    EnregistrementDataAccessObject mEnregistrementDataAccessObject=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Accès au service de localisation :
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Récupération des fournisseurs de position utilisables :
        providers = new ArrayList<LocationProvider>();
        ArrayList<String> names = (ArrayList<String>) lm.getProviders(true);

        // Recuperation des ressources :
        desc = getResources().getString(R.string.desc);
        photo = getResources().getString(R.string.photo);
        env = getResources().getString(R.string.env);
        panne = getResources().getString(R.string.panne);
        det = getResources().getString(R.string.det);
        aut = getResources().getString(R.string.aut);
        type = getResources().getString(R.string.type);
        vis = getResources().getString(R.string.vis);

        // Récupération des view :
        textPosition = (TextView)findViewById(R.id.textPosition);
        textEtat = (TextView)findViewById(R.id.textEtat);
        textDesc = (TextView)findViewById(R.id.textDesc);
        textType = (TextView)findViewById(R.id.textType);
        editDesc = (EditText)findViewById(R.id.editDesc);
        buttonPhoto = (Button)findViewById(R.id.buttonPhoto);
        buttonEnv = (Button)findViewById(R.id.buttonEnv);
        buttonVis = (Button)findViewById(R.id.buttonVis);
        radioButtonP = (RadioButton)findViewById(R.id.radioButtonP);
        radioButtonD = (RadioButton)findViewById(R.id.radioButtonD);
        radioButtonA = (RadioButton)findViewById(R.id.radioButtonA);
        group = (RadioGroup)findViewById(R.id.group);

        // Remplissage des zones de texte :
        textDesc.setText(desc);
        textType.setText(type);
        editDesc.setHint(desc);
        buttonPhoto.setText(photo);
        buttonEnv.setText(env);
        buttonVis.setText(vis);
        radioButtonP.setText(panne);
        radioButtonD.setText(det);
        radioButtonA.setText(aut);

        // Attribution des listeners :
        buttonEnv.setOnClickListener(buttonEnvListener);
        buttonPhoto.setOnClickListener(buttonPhotoListener);
        buttonVis.setOnClickListener(buttonVisListener);

        // Instanciation de la base de données SQLite
        mdatasource = new DataSource(this);
        mEnregistrementDataAccessObject = mdatasource.newEnregistrementhDataAccessObject();
        mdatasource.open();

        /**
         * Gestion de la postion GPS
         */

        for (String name : names) {
            providers.add(lm.getProvider(name));
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {

            // Si la position change
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                altitude = location.getAltitude();
                accuracy = location.getAccuracy();

                msg = String.format(
                        getResources().getString(R.string.new_location), latitude,
                        longitude, altitude, accuracy);
                //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                textPosition.setText(msg); // Utile pour le débogage
                Log.d("GPS", msg);
            }

            // Si le gps change d'état
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                String newStatus = "";
                switch (status) {
                    case LocationProvider.OUT_OF_SERVICE:
                        newStatus = "OUT_OF_SERVICE";
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        newStatus = "TEMPORARILY_UNAVAILABLE";
                        break;
                    case LocationProvider.AVAILABLE:
                        newStatus = "AVAILABLE";
                        break;
                }
                msg = String.format(getResources().getString(R.string.provider_disabled), provider, newStatus);
                //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                textEtat.setText(msg); // Utile pour le débogage
                Log.d("GPS", msg);
            }

            // Si le gps s'active
            @Override
            public void onProviderEnabled(String provider) {
                msg = String.format(
                        getResources().getString(R.string.provider_enabled), provider);
                //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                textEtat.setText(msg); // Utile pour le débogage
                Log.d("GPS", msg);
            }

            // Si le gps se désactive
            @Override
            public void onProviderDisabled(String provider) {
                msg = String.format(
                        getResources().getString(R.string.provider_disabled), provider);
                //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                textEtat.setText(msg); // Utile pour le débogage
                Log.d("GPS", msg);
            }
        });
    }

    /**
     * Gestion des évenements :
     */

    // Gestion du clique le bouton env :
    private View.OnClickListener buttonEnvListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // On récupére les autre informations
            String D = editDesc.getText().toString();

            // On récupère le type
            if(group.getCheckedRadioButtonId() == R.id.radioButtonP) {
                tValue = panne;
            }else if(group.getCheckedRadioButtonId() == R.id.radioButtonD) {
                tValue = det;
            }else{
                tValue = aut;
            }

            /*
            * Gestion envoie bdd :
             */

            // Création de la requete
            enreg_info mEnregistrement1;
            if(mCurrentPhotoPath != "/data/data/com.example.formation.microprojetbaltz/picFolder/") {
                mEnregistrement1 = new enreg_info(-1, longitude, latitude, D, tValue, mCurrentPhotoPath);
            }else {
                mEnregistrement1 = new enreg_info(-1, longitude, latitude, D, tValue);
            }

            //Stockage des attributs de l'objet dans la base de données
            mEnregistrementDataAccessObject.create(mEnregistrement1);

            //Controle de l'écriture dans la base de données
            List<enreg_info> liste_enregistrement = mEnregistrementDataAccessObject.readAll();
            for (enreg_info mEnregistrement : liste_enregistrement) {
                Log.w("CONTROLE", mEnregistrement.toString() + "\n");
            }

            Toast.makeText(MainActivity.this, "Merci", Toast.LENGTH_SHORT).show();


            /*
            * Gestion envoie de mail :
             */

            Log.i("Send email", "");

            // Recuperation des ressources :
            to = getResources().getString(R.string.to); //TODO changer les destinataires
            cc = getResources().getString(R.string.cc);
            sjt = String.format(getResources().getString(R.string.sjt),tValue);

            // Récupération du message
            msgMail = String.format(getResources().getString(R.string.msgMail),tValue,D,
                    longitude, latitude);

            String[] TO = {to};
            String[] CC = {cc};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");

            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, sjt);
            emailIntent.putExtra(Intent.EXTRA_TEXT, msgMail);

            // Pour ajouter une pièce jointe définie par une URL
            if(mCurrentPhotoPath != "/data/data/com.example.formation.microprojetbaltz/picFolder/") {
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:" + mCurrentPhotoPath));
            }

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                finish();
                Log.i("Finished sending email...", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this,
                        "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // Gestion du clique le bouton photo :
    private View.OnClickListener buttonPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            takephoto();
        }
    };

    // Gestion du clique le bouton vis :
    private View.OnClickListener buttonVisListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<Integer> all = mEnregistrementDataAccessObject.getLatLng();

            /*
            for(Integer j:all){
                Log.d("LatLng",j.toString());
            }
            */

            // Test ouverture de la Gmap
            Intent i = new Intent(getApplicationContext(), MapsActivity.class);
            i.putExtra("LAT_LNG", all);
            startActivity(i);
        }
    };

    /**
     * Utilitaires :
     */

    // Gestion de la prise de photo
    public void takephoto() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                //result.setText("startActivityForResult");
            }
        }
    }

    // Pour afficher la photo qui a été prise
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            //result.setText("1");

            Bitmap thumbnail;
            if (data != null) {
                if (data.hasExtra("data")) {
                    thumbnail = data.getParcelableExtra("data");
                    ImageView image = (ImageView) findViewById(R.id.photoResultView);
                    image.setImageBitmap(thumbnail);
                    //result.setText("2");
                }
            } else {
                // On sait ici que le fichier pointé par mFichier est accessible, on peut donc faire ce qu'on veut avec, par exemple en faire un Bitmap
                Bitmap image = BitmapFactory.decodeFile(mCurrentPhotoPath);
                thumbnail = ThumbnailUtils.extractThumbnail(image, 100, 100);

                imageview = (ImageView) findViewById(R.id.photoResultView);
                imageview.setImageBitmap(thumbnail);
            }
        }
    }

    // Pour créer un File image au bon format :
    private File createImageFile() throws IOException {
        //Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        //result.setText(mCurrentPhotoPath);
        return image;
    }
}
