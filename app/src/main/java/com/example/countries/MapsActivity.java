package com.example.countries;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        Double lat = intent.getDoubleExtra("Lat",0);
        Double lng = intent.getDoubleExtra("Lng",0);
        Integer population = intent.getIntExtra("Population",0);
        Integer area = intent.getIntExtra("Area",0);
        // Add a marker in Sydney and move the camera
        LatLng country = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(country)
                .title(name)
                .snippet("Area: "+area+" Population: "+population);


        Marker m = mMap.addMarker(markerOptions);
        m.showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(country));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(country, 5), 0, null);
    }
}
