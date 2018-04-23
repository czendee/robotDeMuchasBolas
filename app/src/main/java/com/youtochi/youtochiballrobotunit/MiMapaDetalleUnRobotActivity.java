package com.youtochi.youtochiballrobotunit;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MiMapaDetalleUnRobotActivity extends FragmentActivity implements OnMapReadyCallback {

        private GoogleMap mMap;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mi_mapa_detalle_un_robot);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.mapafab01);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view,R.string.controla_robot_remotamente, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            FloatingActionButton fabmap = (FloatingActionButton) findViewById(R.id.mapafab02);
            fabmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view,R.string.regresar_a_lista_robots, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    //ir a screen de mapa
                    Intent i = new Intent(MiMapaDetalleUnRobotActivity.this, DespliegaDetalleRobotActivity.class);

                    startActivity(i);
                }
            });

            FloatingActionButton fab03 = (FloatingActionButton) findViewById(R.id.mapafab04);
            fab03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view,R.string.conecta_robot_remoto, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            FloatingActionButton fab05 = (FloatingActionButton) findViewById(R.id.mapafab05);
            fab05.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view,R.string.controla_robot_via_persicope, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
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
            LatLng sydney = new LatLng(-34, 151);

            LatLng mexicoCity = new LatLng(19.429, -99.146);

//        BitmapDescriptor bmd=BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory. .HUE_AZURE);

            BitmapDescriptor bmd= BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);

            //19.4294359!4d-99.1469533
            mMap.addMarker(
                    new MarkerOptions().position(mexicoCity).title("Ahora:DISPONIBLE - CDMX Centro MEXICO - Tipo: (MuchasBolas)").snippet(" Renta por:(5mins) Renta $:(Gratis) Manada:(No) Transmite Periscope:(No)")
            ).setIcon(bmd);
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(mexicoCity));

            System.out.println("zooooom original" +mMap.getMaxZoomLevel());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mexicoCity,mMap.getMaxZoomLevel()-1));
            System.out.println("zooooom menos" );
            mMap.setBuildingsEnabled(true);



/*

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(19.429, -98.146))
                    .title("San Francisco")
                    .snippet("Population: 776733"))
                    .setIcon(bmd);
*/

        }
}
