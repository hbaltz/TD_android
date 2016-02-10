package com.example.formation.localisation;

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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private LocationManager lm;

    private double latitude;
    private double longitude;
    private double altitude;
    private float accuracy;
    private ArrayList<LocationProvider> providers;

    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Accès au service de localisation :
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // Récupération des fournisseurs de position utilisables :
        providers = new ArrayList<LocationProvider>();
        ArrayList<String> names = (ArrayList<String>) lm.getProviders(true);

        for (String name : names) {
            providers.add(lm.getProvider(name));
        }

        /**
         * Définition des critères pour la sélection du fournisseur (non utilisé pour le moment)
         */

        Criteria critere = new Criteria();

        // Pour indiquer la précision voulue
        // On peut mettre ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision
        critere.setAccuracy(Criteria.ACCURACY_FINE);

        // Est-ce que le fournisseur doit être capable de donner une altitude ?
        critere.setAltitudeRequired(true);

        // Est-ce que le fournisseur doit être capable de donner une direction ?
        critere.setBearingRequired(true);

        // Est-ce que le fournisseur peut être payant ?
        critere.setCostAllowed(false);

        // Pour indiquer la consommation d'énergie demandée
        // Criteria.POWER_HIGH pour une haute consommation, Criteria.POWER_MEDIUM pour une consommation moyenne et Criteria.POWER_LOW pour une basse consommation
        critere.setPowerRequirement(Criteria.POWER_HIGH);

        // Est-ce que le fournisseur doit être capable de donner une vitesse ?
        critere.setSpeedRequired(true);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            return;
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 150, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                altitude = location.getAltitude();
                accuracy = location.getAccuracy();

                msg = String.format(
                        getResources().getString(R.string.new_location), latitude,
                        longitude, altitude, accuracy);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                Log.d("GPS",msg);
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
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                Log.d("GPS", msg);
            }

            @Override
            public void onProviderEnabled(String provider) {
                msg = String.format(
                        getResources().getString(R.string.provider_enabled), provider);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                Log.d("GPS", msg);
            }

            @Override
            public void onProviderDisabled(String provider) {
                msg = String.format(
                        getResources().getString(R.string.provider_disabled), provider);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                Log.d("GPS", msg);
            }
        });
    }
}
