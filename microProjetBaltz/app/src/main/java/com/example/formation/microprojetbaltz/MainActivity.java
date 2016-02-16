package com.example.formation.microprojetbaltz;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

            @Override
            public void onProviderEnabled(String provider) {
                msg = String.format(
                        getResources().getString(R.string.provider_enabled), provider);
                //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                textEtat.setText(msg);
                Log.d("GPS", msg);
            }

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

    // Uniquement pour le bouton imc :
    private View.OnClickListener buttonEnvListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!checkPhoto.isChecked()) {
                Toast.makeText(MainActivity.this, "Merci", Toast.LENGTH_SHORT).show();
            }else{


            }
        }
    };
}
