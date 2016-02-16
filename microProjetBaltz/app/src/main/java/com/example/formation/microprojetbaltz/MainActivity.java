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

public class MainActivity extends AppCompatActivity{

    private TextView textPosition = null;
    private TextView textEtat = null;
    private TextView textDesc = null;
    private EditText editDesc = null;
    private CheckBox checkPhoto = null;
    private Button buttonEnv = null;

    private LocationManager lm;

    private double latitude;
    private double longitude;
    private double altitude;
    private float accuracy;
    private ArrayList<LocationProvider> providers;

    private String msg, desc, env, photo;

    private static int count = 0;
    private int TAKE_PHOTO_CODE = 0;
    private int CAMERA_PIC_REQUEST = 2;

    private String mCurrentPhotoPath = "/data/data/com.example.formation.microprojetbaltz/picFolder/";

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
        checkPhoto = (CheckBox)findViewById(R.id.checkPhoto);
        buttonEnv = (Button)findViewById(R.id.buttonEnv);

        // Remplissage des zones de texte :
        textDesc.setText(desc);
        editDesc.setHint(desc);
        checkPhoto.setText(photo);
        buttonEnv.setText(env);

        // Attribution des listeners :
        buttonEnv.setOnClickListener(buttonEnvListener);

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

            // Si le gps est activé
            @Override
            public void onProviderEnabled(String provider) {
                msg = String.format(
                        getResources().getString(R.string.provider_enabled), provider);
                //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                textEtat.setText(msg);
                Log.d("GPS", msg);
            }

            // Si le gps est désactivé
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

    // Uniquement pour le bouton env :
    private View.OnClickListener buttonEnvListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!checkPhoto.isChecked()) {
                Toast.makeText(MainActivity.this, "Merci", Toast.LENGTH_SHORT).show();
            }else{
                takephoto();
            }
        }
    };

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

                ImageView imageview = (ImageView) findViewById(R.id.photoResultView);
                imageview.setImageBitmap(thumbnail);

            }
        }
    }

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
