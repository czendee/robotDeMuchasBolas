package com.youtochi.youtochiballrobotunit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String[] web ={"CDMX- Centro,Mexico",
            "Cuernavaca, Mexico",
            "CDMX -Santa Fe, Mexico",
            "Santiago - Mauipu, Chile",
            "Sao Paulo-Lagoa, rasil"
    };

    Integer[] imageId={
            R.mipmap.ic_robot,
            R.mipmap.ic_robot,
            R.mipmap.ic_robot,
            R.mipmap.ic_robot,
            R.mipmap.ic_robot
    };

    String[] robotPrecios ={"35.00",
            "32.00",
            "28.00",
            "22.00",
            "25.00"
    };

    String[] robotProdId ={"A001",
            "A020",
            "A011",
            "B002",
            "B305"
    };

    String cualUsuarioSoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,R.string.mostrar_mapa_robots, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //arriba esta el codigo estandard

        //boton flotante mapa

        FloatingActionButton fabmap = (FloatingActionButton) findViewById(R.id.fabmap);
        fabmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,R.string.registrate_controlar_robots, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //ir a screen de mapa
                Intent i = new Intent(MainActivity.this, MiMapaRobotsActivity.class);

                startActivity(i);
            }
        });

        //abajo de esta linea esta el codigo de beer app
        cualUsuarioSoy ="1806";

        ListView lstTaskListLocal=(ListView)  findViewById(R.id.lista);



        // se declara el adapter, intermediario, se le pasa context, y el layout. se utilizara el layout default de andriod para lista
        //y la lista de array con los objetos
        ListaRobots adapter= new ListaRobots(
                MainActivity.this,
                web,
                imageId
        );
        lstTaskListLocal.setAdapter(adapter);
        lstTaskListLocal.setLongClickable(true);
        System.out.println("antes del OnClick en una robot posicion" );
//        lstTaskListLocal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
        lstTaskListLocal.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
//            public boolean onItemLongClick(AdapterView<?> arg0,
            public void onItemClick(AdapterView<?> arg0,
                                           View arg1,
                                           int position,
                                           long arg3){

                System.out.println("OnClick en una robot posicion es:" + position);

                String valorNombre =  web[position];
                int valorImagen =  imageId[position];
                String valorPrecio =  robotPrecios[position];
                String valorProdId =  robotProdId[position];


                System.out.println("OnClick en una robot nombre:" +valorNombre );
                System.out.println("OnClick en una robot valorImagen:" + valorImagen);
                System.out.println("OnClick en una robot  beerPrecios:" + valorPrecio);
                System.out.println("OnClick en una robot  ProdId:" + valorProdId);

                if(valorNombre!=null && valorPrecio!=null) {


                    Intent i = null;


                    i = new Intent(MainActivity.this, DespliegaDetalleRobotActivity.class);

                    i.putExtra("cual_usuario", cualUsuarioSoy + "");
                    i.putExtra("cual_infoproducto", valorNombre + "");
                    i.putExtra("cual_productoID", valorProdId + "");
                    i.putExtra("cual_precio", valorPrecio + "");
                    i.putExtra("cual_imagen", valorImagen + "");


                    startActivity(i);
                }else{
                    System.out.println("No se puede desplegar el detalle" );
                }
//                return true;   it was used for long click
            }

        });//end onclick
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(MainActivity.this, MiMapaRobotsActivity.class);

            startActivity(i);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            // Check if Android M or higher
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Show alert dialog to the user saying a separate permission is needed
                // Launch the settings activity if the user prefers
                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(myIntent);
            }


        } else if (id == R.id.nav_share) {

/*            Intent i = new Intent(Intent.ACTION_MAIN);
            PackageManager managerclock = getPackageManager();
            i = managerclock.getLaunchIntentForPackage("pscp://broadcast/1RDtochizendejas");
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);
*/
            Uri webpage = Uri.parse("https://www.pscp.tv/tochizendejas");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /** Called when the user clicks the gotoDetalle  row */
    public void irDetalleRobot(View view) {

        System.out.println("OnClick boton en una irDetalleRobot:"  );
        Button btnTitle =(Button) findViewById(R.id.btnRegresarCobrar);
        String positionStr =(String) btnTitle.getHint();

        int valorPosition=-1;
        try{
            valorPosition = Integer.parseInt(positionStr);
        }catch(Exception e){

        }
        if (valorPosition>-1){
            System.out.println("OnClick boton en una cerveza posicion es:" + valorPosition);

            String valorNombre =  web[valorPosition];
            int valorImagen =  imageId[valorPosition];
            String valorPrecio =  robotPrecios[valorPosition];
            String valorProdId =  robotProdId[valorPosition];


            System.out.println("OnClick boton en una  robot nombre:" +valorNombre );
            System.out.println("OnClick boton en una  robot valorImagen:" + valorImagen);
            System.out.println("OnClick boton en una  robot  beerPrecios:" + valorPrecio);
            System.out.println("OnClick boton en una  robot  ProdId:" + valorProdId);

            if(valorNombre!=null && valorPrecio!=null) {


                Intent i = null;


                i = new Intent(MainActivity.this, DespliegaDetalleRobotActivity.class);

                i.putExtra("cual_usuario", cualUsuarioSoy + "");
                i.putExtra("cual_infoproducto", valorNombre + "");
                i.putExtra("cual_productoID", valorProdId + "");
                i.putExtra("cual_precio", valorPrecio + "");
                i.putExtra("cual_imagen", valorImagen + "");


                startActivity(i);
            }else{
                System.out.println("OnClick boton No se puede desplegar el detalle" );
            }
        }else{
            System.out.println("OnClick boton No hay itmes" );
        }


        //Intent i = new Intent(EdificioListaActivity.this, EdificionewActivity.class);

        //startActivity(i);
    }
}
