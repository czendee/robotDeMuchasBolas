package com.youtochi.youtochiballrobotunit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;

public class DespliegaDetalleRobotActivity extends AppCompatActivity {

    public String cualUsuarioSoy;
    public String cualInfoProductoSoy;
    public String cualProductoIDSoy;
    public String cualPrecioSoy;
    public String cualImagenSoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despliega_detalle_robot);

        Bundle extras = getIntent().getExtras();

        cualUsuarioSoy="ninguno";
        System.out.println("Detalle robot  ProdId:" );

        if (extras != null) {
            System.out.println("Detalle robot  ProdId  step 2:" );
            cualUsuarioSoy = extras.getString("cual_usuario");


            cualInfoProductoSoy =extras.getString("cual_infoproducto");
            cualProductoIDSoy= extras.getString("cual_productoID");
            cualPrecioSoy=  extras.getString("cual_precio");
            cualImagenSoy=  extras.getString("cual_imagen");
            System.out.println("Detalle robot  ProdId  step 3 a:"+cualInfoProductoSoy+" b:"+ cualProductoIDSoy+ " c:"+cualPrecioSoy +" d:"+cualImagenSoy);
            int valorImagenInt=0;
            try{
                valorImagenInt = Integer.parseInt(cualImagenSoy);
            }catch(Exception e){

            }

            System.out.println("Detalle robot  ProdId  step 3:"+ valorImagenInt);
            EditText textitoName =(EditText) findViewById(R.id.productoName);
            textitoName.setText(cualInfoProductoSoy);
            EditText textitoID =(EditText) findViewById(R.id.productoID);
            textitoID.setText(cualProductoIDSoy);
            EditText textitoMes =(EditText) findViewById(R.id.productoPrecio);
            textitoMes.setText(cualPrecioSoy);
            if(valorImagenInt>0) {
                ImageView imagenV = (ImageView) findViewById(R.id.productoImagen);

                imagenV.setImageResource(valorImagenInt);

            }

            System.out.println("Detalle robot  ProdId  step 6:");
        }//if extras

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAddProdShopping);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Registrate para ir a Carrito Compras", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fab02 = (FloatingActionButton) findViewById(R.id.btnRegresarRentar);
        fab02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                System.out.println("back to previous activity - 4");
            }
        });




        FloatingActionButton fabmap = (FloatingActionButton) findViewById(R.id.detallerobotfab01);
        fabmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,R.string.regresar_a_lista_robots, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //ir a screen de mapa
                Intent i = new Intent(DespliegaDetalleRobotActivity.this, MiMapaDetalleUnRobotActivity.class);


                startActivity(i);
            }
        });
    }
}
