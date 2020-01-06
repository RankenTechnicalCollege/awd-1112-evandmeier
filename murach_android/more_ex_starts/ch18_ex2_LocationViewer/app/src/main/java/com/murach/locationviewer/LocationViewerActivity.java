package com.murach.locationviewer;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class LocationViewerActivity extends FragmentActivity 
implements ConnectionCallbacks, OnConnectionFailedListener {

    private GoogleApiClient locationClient;
    
    private TextView coordinatesTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_viewer);
        
        coordinatesTextView = (TextView) 
                findViewById(R.id.coordinatesTextView);
        
        // get location client
        locationClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        locationClient.connect();        
    }
    
    @Override
    protected void onStop() {
        locationClient.disconnect();
        super.onStop();
    }
    
    //**************************************************************
    // Implement ConnectionCallbacks interface
    //****************************************************************
    @Override
    public void onConnected(Bundle dataBundle) {
        Location location = LocationServices.FusedLocationApi
                .getLastLocation(locationClient);
        if (location != null){
            coordinatesTextView.setText(
            		"Latitude: " + location.getLatitude() + "\n" + 
            		"Longitude: " + location.getLongitude());
        }
    }
    
    @Override
    public void onConnectionSuspended(int i) {
        coordinatesTextView.setText("Disconnected!");
    }

    //**************************************************************
    // Implement OnConnectionFailedListener
    //****************************************************************
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        coordinatesTextView.setText("Connection Failed!");
    }    
}