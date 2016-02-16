package com.example.formation.microprojetbaltz;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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

//TODO mieux récupérer la position
//TODO ajouter Type liste ou raidoBouton
//TODO google maps dans nouvelle fenêtre

public class MainActivity extends AppCompatActivity{

    private TextView textPosition, textEtat, textDesc;
    private EditText editDesc;
    private Button buttonEnv, buttonPhoto ;
    private ImageView imageview ;

    private LocationManager lm;

    private double latitude = 49.48, longitude = 3.91, altitude; //Si Gps pas actif centre de Amifontaine de par défaut
    private float accuracy;
    private ArrayList<LocationProvider> providers;

    private String msg, desc, env, photo;

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

        // Récupération des view :
        textPosition = (TextView)findViewById(R.id.textPosition);
        textEtat = (TextView)findViewById(R.id.textEtat);
        textDesc = (TextView)findViewById(R.id.textDesc);
        editDesc = (EditText)findViewById(R.id.editDesc);
        buttonPhoto = (Button)findViewById(R.id.buttonPhoto);
        buttonEnv = (Button)findViewById(R.id.buttonEnv);

        // Remplissage des zones de texte :
        textDesc.setText(desc);
        editDesc.setHint(desc);
        buttonPhoto.setText(photo);
        buttonEnv.setText(env);

        // Attribution des listeners :
        buttonEnv.setOnClickListener(buttonEnvListener);
        buttonPhoto.setOnClickListener(buttonPhotoListener);

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
                textPosition.setText(msg);
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
                textEtat.setText(msg);
                Log.d("GPS", msg);
            }

            // Si le gps s'active
            @Override
            public void onProviderEnabled(String provider) {
                msg = String.format(
                        getResources().getString(R.string.provider_enabled), provider);
                //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                textEtat.setText(msg);
                Log.d("GPS", msg);
            }

            // Si le gps se désactive
            @Override
            public void onProviderDisabled(String provider) {
                msg = String.format(
                        getResources().getString(R.string.provider_disabled), provider);
                //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                textEtat.setText(msg);
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
            // On récupére les autre inforamtion
            String D = editDesc.getText().toString();


            //L'identifiant "-1" dit à SQLite de créer un nouvel identifiant en autoincrémentation avec ORMlite. Inutile avec SQlite.
            enreg_info mEnregistrement1 = new enreg_info(-1, longitude, latitude, D, "NC",mCurrentPhotoPath);

            //Stockage des attributs de l'objet dans la base de données
            mEnregistrementDataAccessObject.create(mEnregistrement1);

            //Controle de l'écriture dans la base de données
            List<enreg_info> liste_enregistrement = mEnregistrementDataAccessObject.readAll();
            for (enreg_info mEnregistrement : liste_enregistrement) {
                Log.w("CONTROLE", mEnregistrement.toString() + "\n");
            }

            Toast.makeText(MainActivity.this, "Merci", Toast.LENGTH_SHORT).show();
        }
    };

    // Gestion du clique le boutton photo :
    private View.OnClickListener buttonPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            takephoto();
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
