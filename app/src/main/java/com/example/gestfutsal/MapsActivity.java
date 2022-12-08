package com.example.gestfutsal;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.gestfutsal.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Intent m;
    String morada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        m = getIntent();
        morada = m.getExtras().getString("morada");
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

        // Add a marker in Sydney and move the camera
        LatLng marcador = getLocationFromAddress(this,morada);
        mMap.addMarker(new MarkerOptions().position(marcador).title("Morada"));
        mMap.setMinZoomPreference(13);
        mMap.setMapType(4);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marcador));
    }

    public LatLng getLocationFromAddress(Context context, String strAddress){
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address == null){
                return  null;
            }

            Address location = address.get(0);
            p = new LatLng(location.getLatitude(),location.getLongitude());

        }catch(IOException ex){

        }
        return p;
    }
}