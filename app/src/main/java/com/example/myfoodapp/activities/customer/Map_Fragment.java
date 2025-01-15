package com.example.myfoodapp.activities.customer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfoodapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_Fragment extends Fragment {

    private GoogleMap mMaps;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_map_, container, false);
        SupportMapFragment supportMapFragment=(SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_layout);

        // Asynchronously load the map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMaps=googleMap;
                //request runtime permission
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                    mMaps.setMyLocationEnabled(true);
                }
                // Move camera to a specific location with zoom level 12
                mMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7.398768359398988, 81.83274995305986), 12));
                // Add a marker at a specific location with a title YUM FOOD
                mMaps.addMarker(new MarkerOptions().position(new LatLng(7.398768359398988, 81.83274995305986)).title("YUM FOOD"));
                // Enable zoom controls on the map
                mMaps.getUiSettings().setZoomControlsEnabled(true);
            }
        });
        return view;
    }
}