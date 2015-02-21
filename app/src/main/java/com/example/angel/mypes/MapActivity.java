package com.example.angel.mypes;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import com.actionbarsherlock.app.SherlockActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MapActivity extends FragmentActivity {


    GoogleMap map;
    Double latitude;
    Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        String _latitude = getIntent().getStringExtra("latitude");
        String _longitude = getIntent().getStringExtra("longitude");

        latitude = Double.parseDouble(_latitude);
        longitude = Double.parseDouble(_longitude);

        getGoogleMap();
        AddMarker(latitude,longitude);


    }

    public void getGoogleMap(){
        try{
            if(map==null) {
                map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView)).getMap(); //Mostrar mapa nuevo
                map.setMyLocationEnabled(true);
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", marker.getPosition().latitude, marker.getPosition().longitude);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);

                        return true;
                    }
                });


                //actualizarPosicion();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "No se pudo obtener mapa", Toast.LENGTH_SHORT).show();
        }
    }


    public void AddMarker(Double latitude,Double longitude)
    {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("Mi taxi")
                );

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude, longitude), 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))      // Sets the center of the map to location user
                .zoom(17)                   // Sets the zoom           // Sets the orientation of the camera to east
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
