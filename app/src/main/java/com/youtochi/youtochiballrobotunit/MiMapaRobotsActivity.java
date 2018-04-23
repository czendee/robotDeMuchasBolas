package com.youtochi.youtochiballrobotunit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MiMapaRobotsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener,OnMapReadyCallback {

    private GoogleMap mMap;


    final ArrayList<String> arrayTasks = new ArrayList<>();//store the list of robots from the web API

    final ArrayList<Marker> mRobot = new ArrayList<>();//store the list of map markers robots,with info from the web API

    final ArrayList<DataDescriptorRobot> dRobot = new ArrayList<>();//store the list of map markers robots,with info from the web API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_mapa_robots);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.mapafab01);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,R.string.aqui_esta_en_mapa_robots, Snackbar.LENGTH_LONG)
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
                Intent i = new Intent(MiMapaRobotsActivity.this, MainActivity.class);

                startActivity(i);
            }
        });
/*
se movio esta funcionalidad para el menu tools/settings
// Check if Android M or higher
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Show alert dialog to the user saying a separate permission is needed
            // Launch the settings activity if the user prefers
            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(myIntent);
        }
*/

//llmar el que traiga la lista de robots y su info del API
        System.out.println("ListRobots paso 1");
        AsyncListViewLoader task=new AsyncListViewLoader();
        System.out.println("ListRobots paso 2");
        Constants constantes= new Constants();
//        task.parametroURL="http://mexico.brinkster.net/tochi_get_lista_edificios_mobil.asp?operacion=lista";
        task.parametroURL=constantes.URL_LIST_ROBOTS;

        System.out.println("ListRobots paso 3");
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)

            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();


        System.out.println("ListRobots paso 4");


    }


    private Marker mRobot01;
    private Marker mRobot02;
    private Marker mRobot03;

    private  Marker mRobot04;

    final ArrayList<Marker> arrayMarkers = new ArrayList<>();//store the list of robots from the web API
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


        LatLng robotPosicion01 = new LatLng(19.429, -99.148);

        LatLng robotPosicion02 = new LatLng(19.428, -99.148);

//        BitmapDescriptor bmd=BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory. .HUE_AZURE);

        BitmapDescriptor bmd= BitmapDescriptorFactory.fromResource(R.mipmap.ic_mapicon);

        //19.4294359!4d-99.1469533
        mRobot01=  mMap.addMarker(
                 new MarkerOptions().position(mexicoCity).title("Ahora:DISPONIBLE - CDMX Centro MEXICO - Tipo: (Human Walker)").snippet(" Renta por:(5mins) Renta $:(Gratis) Manada:(No) Transmite Periscope:(Si)")
        );
        mRobot01.setIcon(bmd);

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(mexicoCity));

        //19.4294359!4d-99.1469533
        mRobot02= mMap.addMarker(
                new MarkerOptions().position(robotPosicion01).title("Ahora:DISPONIBLE - CDMX Centro MEXICO - Tipo: (MuchasBolas)").snippet(" Renta por:(5mins) Renta $:(Gratis) Manada:(No) Transmite Periscope:(No)")
        );
        mRobot02.setIcon(bmd);


        //19.4294359!4d-99.1469533
        mRobot03= mMap.addMarker(
                new MarkerOptions().position(robotPosicion02).title("Ahora:DISPONIBLE - CDMX Centro MEXICO - Tipo: (MuchasBolas)").snippet(" Renta por:(10mins) Renta $:(Gratis) Manada:(Si) Transmite Periscope:(Si)")
        );
        mRobot03.setIcon(bmd);



        System.out.println("zooooom original" +mMap.getMaxZoomLevel());
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mexicoCity,mMap.getMaxZoomLevel()-14));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mexicoCity,mMap.getMaxZoomLevel()-4));
        System.out.println("zooooom menos" );
        mMap.setBuildingsEnabled(true);





        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(19.429, -98.146))
                .title("San Francisco")
                .snippet("Population: 776733"))
                .setIcon(bmd);

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        int icual=0;

        for( Marker actualMarker: mRobot)
        {
            System.out.println("Regresar - 0 actualMarker"+actualMarker.getSnippet());
            System.out.println("Regresar - 0.1 actualMarker"+actualMarker.getTitle());

            System.out.println("Regresar - 0.2 actualMarker "+dRobot.get(icual).getTipo()+" "+dRobot.get(icual).getName());
            System.out.println("Regresar - 0.3 actualMarker "+dRobot.get(icual).getPosLat()+" "+dRobot.get(icual).getPosLon());


            if (marker.equals(actualMarker))
            {

                //Intent intent=new Intent(MarkerDemoActivity.this,AnotherActivity.class);
                //startActivity();

                System.out.println("Regresar - 3 actualMarker"+actualMarker.getSnippet());

                //CZ 09 Nov 2017:start
                //Open the floating controls in system
                System.out.println("onMarkerClick paso conectar 1 actualMarker");
//            startService(new Intent(MiMapaRobotsActivity.this, ControlesBotones.class));

                Intent serviceIntent = new Intent(MiMapaRobotsActivity.this, ControlesBotones.class);
                serviceIntent.putExtra("CualRobot", "01");
                serviceIntent.putExtra("CaracteristicasRobotName", dRobot.get(icual).getName());
                serviceIntent.putExtra("CaracteristicasRobotTipo", dRobot.get(icual).getTipo());
                serviceIntent.putExtra("CaracteristicasRobotManada", dRobot.get(icual).getManada());

                startService(serviceIntent);

//                Uri webpage = Uri.parse("https://www.pscp.tv/tochizendejas");
                Uri webpage = Uri.parse("https://www.pscp.tv/"+dRobot.get(icual).getTransmiteCanal());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }//end if
            icual++; //increase the countador
        }//end for


        //este es el marker fijo de prueba/para comparar los marker de la web

        if (marker.equals(mRobot01))
        {

            //Intent intent=new Intent(MarkerDemoActivity.this,AnotherActivity.class);
            //startActivity();

            System.out.println("Regresar - 3");

            //CZ 09 Nov 2017:start
            //Open the floating controls in system
            System.out.println("onMarkerClick paso conectar 1");
//            startService(new Intent(MiMapaRobotsActivity.this, ControlesBotones.class));

            Intent serviceIntent = new Intent(MiMapaRobotsActivity.this, ControlesBotones.class);
            serviceIntent.putExtra("CualRobot", "01");
            serviceIntent.putExtra("CaracteristicasRobot", mRobot01.getSnippet());
            startService(serviceIntent);

           Uri webpage = Uri.parse("https://www.pscp.tv/tochizendejas");


            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);
        }






        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


    /*
    Custom method, to be used when the list of markers are read from the web


     */
    public void updateMap() {
//        mMap //ya contiene los amrkers y mapa que se definieron

        mMap.clear(); //limpia markers actuales

        //ahora agreguemos los markers que qeuremos
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        LatLng mexicoCity = new LatLng(19.429, -99.146);


        LatLng robotPosicion01 = new LatLng(19.429, -99.148);

        LatLng robotPosicion02 = new LatLng(19.428, -99.148);



//        BitmapDescriptor bmd=BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory. .HUE_AZURE);

        BitmapDescriptor bmd= BitmapDescriptorFactory.fromResource(R.mipmap.ic_mapicon);

        //19.4294359!4d-99.1469533
        mRobot01=  mMap.addMarker(
                new MarkerOptions().position(mexicoCity).title("Ahora:DISPONIBLE - CDMX Centro MEXICO - Tipo: (Human Walker)").snippet(" Renta por:(5mins) Renta $:(Gratis) Manada:(No) Transmite Periscope:(Si)")
        );
        mRobot01.setIcon(bmd);





        System.out.println("robot 4 de web 1" );
        int primero=0;
        Marker localMarker=null;
        LatLng currentRobotPosicion = null;

        int icual=0;

        for( String actualElemento: arrayTasks)

        {
            System.out.println("robot 4 de web 2 "+actualElemento );
                double ivalorLat;
                double ivalorLon;
                try{
                    String valorLat= dRobot.get(icual).getPosLat();
                    String valorLon= dRobot.get(icual).getPosLon();
                     ivalorLat = new Double(valorLat);
                     ivalorLon = new Double(valorLon);

                    System.out.println("robot 4 de web 2.0  "+ ivalorLat +"  "+ ivalorLon );
                    currentRobotPosicion = new LatLng(ivalorLat, ivalorLon);
                    mRobot04= mMap.addMarker(
//    1st                    new MarkerOptions().position(currentRobotPosicion).title(actualElemento).snippet("SII Renta por:(10mins) Renta $:(Gratis) Manada:(Si) Transmite Periscope:(Si)")
//    2nd                        new MarkerOptions().position(currentRobotPosicion).title(actualElemento).snippet("SII Renta por:("+dRobot.get(icual).getRentaTiempo()+") Renta $:("+dRobot.get(icual).getRentaCosto()+") Manada:("+dRobot.get(icual).getManada()+") Transmite:("+dRobot.get(icual).getTransmite()+")")
                            new MarkerOptions().position(currentRobotPosicion).title(dRobot.get(icual).getName()).snippet("[:)   Renta por:("+dRobot.get(icual).getRentaTiempo()+") Renta $:("+dRobot.get(icual).getRentaCosto()+") Manada:("+dRobot.get(icual).getManada()+") Transmite:("+dRobot.get(icual).getTransmite()+")")
                    );
                    System.out.println("robot 4 de web 2.1" );
                    mRobot04.setIcon(bmd);
                    localMarker=mRobot04;
                }catch (Exception e){
                    ivalorLat = new Double(0.0);
                    ivalorLon = new Double(0.0);

                }

            //arrayMarkers

            mRobot.add(localMarker); //agregar aqui los markers en esta lista
            localMarker=null;
            icual++;
            System.out.println("robot 4 de web 3" );
        }//end for
        System.out.println("robot 4 de web 4" );

        System.out.println("zooooom original" +mMap.getMaxZoomLevel());
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mexicoCity,mMap.getMaxZoomLevel()-14));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mexicoCity,mMap.getMaxZoomLevel()-4));
        System.out.println("zooooom menos" );
        mMap.setBuildingsEnabled(true);





        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(19.429, -98.146))
                .title("San Francisco")
                .snippet("Population: 776733"))
                .setIcon(bmd);

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

//-------------------------------------------------Tarea asyncrona que lee datos de la web --------------------

    private class AsyncListViewLoader extends AsyncTask<String, Void, List<DataDescriptorRobot>> {
        private final ProgressDialog dialog = new ProgressDialog(MiMapaRobotsActivity.this); //asi porque estamos dentro de un fragment

        public String parametroURL="aqui";

        @Override
        protected void onPostExecute(List<DataDescriptorRobot> result) {
            super.onPostExecute(result);
            dialog.dismiss();
//            adpt.setItemList(result);
//            adpt.notifyDataSetChanged();
            System.out.println("AsyncListViewLoader onPostExecute paso 1");

            //cylce through List DataDescriptorRobot result, y ponerlo en lista de ArrayTask
            if(result!=null) {
                for (DataDescriptorRobot f : result) {
//                    arrayTasks.add(f.getName().toUpperCase() + " - " + f.getTipo().toUpperCase() + " - " + f.getManada().toUpperCase() + "-"+ f.getPosLon().toUpperCase() + "-"+ f.getPosLat().toUpperCase() + "-");// origianl
                    arrayTasks.add(f.getName() + " - " + f.getTipo() + " - " + f.getManada() + "-" + f.getPosLon() + "-" + f.getPosLat() + "-");// api robots

                    dRobot.add(f); //aqui almacenamos cada dataDescriptor de robot, asi esta lista tendra los datos del robot web
                }
               // adapter.notifyDataSetChanged();//no hay un objeto en la screen que estara siendo modificado
                //quiza se repinten los markers
                System.out.println("AsyncListViewLoader onPostExecute paso 2");
                updateMap();
                System.out.println("AsyncListViewLoader onPostExecute paso 3");
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Downloading contacts...");
            dialog.show();
        }
        @Override
        protected List<DataDescriptorRobot> doInBackground(String... params) {

            System.out.println("PrimerFragment doInBackground paso 1");

            List<DataDescriptorRobot> result = new ArrayList<DataDescriptorRobot>();
            System.out.println("PrimerFragment doInBackground paso 2");
            try {

                BufferedReader inStream = null;
                System.out.println("ValidaRobot - 2"+parametroURL);
                String JSONResp =null;

                URL url = new URL(parametroURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                try {

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    //readStream(in);

                    inStream = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                    StringBuffer buffer = new StringBuffer("");

                    System.out.println("Obten Lista - 5");
                    String line = "";
                    System.out.println("Obten Lista - 7");
                    String NL = System.getProperty("line.separator");
                    System.out.println("Obten Lista - 8");
                    while ((line = inStream.readLine()) != null) {
                        buffer.append(line + NL);
                    }
                    System.out.println("Obten Lista - 9");
                    inStream.close();
                    System.out.println("Obten Lista - 10");
                    JSONResp = buffer.toString();
                    System.out.println("GetSomething - 11");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("Obten Lista - 12 error");
                    e.printStackTrace();
                } finally {
                    System.out.println("Obten Lista - 13");
                    if (inStream != null) {
                        try {
                            inStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                System.out.println("PrimerFragmentdoInBackground paso 5");
//antes asp tenia { edificio [ {     }, {    } ] }
//antes asp                JSONObject jsonResponse = new JSONObject(new String(JSONResp));
//antes asp                JSONArray arr = jsonResponse.getJSONArray("edificio");


//ahora solo es [ {     }, {    } ]
                JSONArray arr = new JSONArray(new String(JSONResp));
                System.out.println("PrimerFragment doInBackground paso 6");

                for (int i=0; i < arr.length(); i++) {
                    result.add(convertDataDescriptorRobot(arr.getJSONObject(i)));
                }
                System.out.println("PrimerFragment doInBackground paso 7");
                return result;
            } catch(Throwable t) {
                t.printStackTrace();
            }
            System.out.println("PrimerFragment doInBackground paso 8");
            return null;
        }
        private DataDescriptorRobot convertDataDescriptorRobot(JSONObject obj) throws JSONException {

            System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 10");

//            String id = obj.getString("id");
            String name = obj.getString("name");
            String tipo = obj.getString("tipo");
            String manada = obj.getString("manada");
            String posLon = obj.getString("lon");
            String posLat = obj.getString("lat");
            String rentaTiempo = obj.getString("rentatiempo");
            String rentaCosto = obj.getString("rentacosto");

            String transmite = obj.getString("transmite");
            String transmiteCanal=obj.getString("transmitecanal");
            System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 11:"+name);
            System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 12:"+posLon);
            System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 13:"+posLat);
            System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 14:"+transmite);
            System.out.println("ListBuildingsActivity convertDataDescriptorRobot paso 15:"+transmiteCanal);

            return new DataDescriptorRobot("01",name, tipo,manada,posLon,posLat,rentaTiempo,rentaCosto,transmite, transmiteCanal);
        }

    }//end private class



}//end class MiMapaRobotsActivity

