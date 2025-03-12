package com.app.dododexapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.app.dododexapp.databinding.ActivityMapsBinding;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {
    
    private Context context;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String user_token = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = Maps.this;

        // Show the button to go back
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);



        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        
        // Ennable and disabble Zoom Control
        mMap.getUiSettings().setZoomControlsEnabled(true);
        
        //Function to set new markers on the map
        addMarker(mMap);
    }
    
    public void addMarker(GoogleMap map){
        
        mMap = map;
        
        // Defining the new markers
        Marker Arqueopterix = mMap.addMarker(new MarkerOptions().position(new LatLng(47.8710127,12.5101068)).title("Arqueopterix"));
        Arqueopterix.setTag("Arqueopterix");
        
        Marker Ornitomimo = mMap.addMarker(new MarkerOptions().position(new LatLng(38.8786865,-110.8344429)).title("Ornitomimo"));
        Ornitomimo.setTag("Ornitomimo");
        
        Marker Velociraptor = mMap.addMarker(new MarkerOptions().position(new LatLng(34.3666657,106.9917179)).title("Velociraptor"));
        Velociraptor.setTag("Velociraptor");
        
        Marker Diplodocus = mMap.addMarker(new MarkerOptions().position(new LatLng(39.3800017,-116.8305328)).title("Diplodocus"));
        Diplodocus.setTag("Diplodocus");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(Diplodocus.getPosition()));
        
        Marker Tiranosaurio = mMap.addMarker(new MarkerOptions().position(new LatLng(36.4989741,-123.5115565)).title("Tiranosaurio"));
        Tiranosaurio.setTag("Tiranosaurio");

        // When the user clicks on the title of marker, it sends to the dino detail activity
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                
                //Get title of the marker
                String title = marker.getTag().toString();
                
                if (title.equalsIgnoreCase("Arqueopterix")) {
                    Intent intent = new Intent(context, dinasour_information_main.class);
                    intent.putExtra("DinoId", "1");
                    startActivity(intent);
                }
                else if (title.equalsIgnoreCase("Ornitomimo")) {
                    Intent intent = new Intent(context, dinasour_information_main.class);
                    intent.putExtra("DinoId", "2");
                    startActivity(intent);
                }
                else if (title.equalsIgnoreCase("Velociraptor")) {
                    Intent intent = new Intent(context, dinasour_information_main.class);
                    intent.putExtra("DinoId", "3");
                    startActivity(intent);
                }
                else if (title.equalsIgnoreCase("Diplodocus")) {
                    Intent intent = new Intent(context, dinasour_information_main.class);
                    intent.putExtra("DinoId", "4");
                    startActivity(intent);
                }
                else if (title.equalsIgnoreCase("Tiranosaurio")) {
                    Intent intent = new Intent(context, dinasour_information_main.class);
                    intent.putExtra("DinoId", "5");
                    startActivity(intent);
                }
                
            }
        });
        
    }

}