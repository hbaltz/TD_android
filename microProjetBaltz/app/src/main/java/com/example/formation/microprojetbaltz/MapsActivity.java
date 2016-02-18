package com.example.formation.microprojetbaltz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); // Bug

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent i = getIntent();
        ArrayList<Integer> latlng = i.getIntegerArrayListExtra("LAT_LNG");

        for(int k = 0; k <= latlng.size()-2; k=k+2)
        {
            Integer lat = (Integer)latlng.get(k);
            //Log.d("Lat",lat.toString());

            Integer lng = (Integer)latlng.get(k+1);
            //Log.d("Lng",lng.toString());

            LatLng mark = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(mark).title("Report"));
        }

        LatLng Amifontaine = new LatLng(49.48,3.91);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Amifontaine));
    }
}