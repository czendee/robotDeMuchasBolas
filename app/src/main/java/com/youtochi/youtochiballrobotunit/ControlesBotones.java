package com.youtochi.youtochiballrobotunit;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by martha on 09/10/2017.
 */
public class ControlesBotones extends Service implements View.OnTouchListener,
                                                         View.OnDragListener {

    Button mButtonIzq;
    Button mButtonArriba;
    Button mButtonAbajo;
    Button mButtonDerecho;
    ImageButton mImgButtonIzq;
    ImageButton mImgButtonArriba;
    ImageButton mImgButtonAbajo;
    ImageButton mImgButtonDerecho;

    //boton para cerrar: oculta los controles
    ImageButton mImgButtonEnds;

    //boton para persocpe: abre app periscope
    ImageButton mImgButtonPeris;

    //boton para personaje: se usara para drag and drop
    ImageButton mImgButtonPerso;
    WindowManager.LayoutParams paramsPerso;

    //boton para personaje 02: se usara para drag and drop
    ImageButton mImgButtonPerso02;
    WindowManager.LayoutParams paramsPerso02;


    WindowManager wm;

    String cualRobotControlamos="00";

    String cualRobotCaracteristicas="NA";

    private DataDescriptorRobot passedDatosDelRobot=null;

    //para manejar la inclinacion y comandos
    private SensorManager mSensorM;
    private Sensor mAcelerador;
    public SensorEventListener listen;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();


        System.out.println("onCreate       Robot - empiezo");

//1st        defineBotonesControlesYListeners();

        System.out.println("onCreate       Robot - termino");


    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Bundle extras = intent.getExtras();
//START:obten los dtos del robot que controlaremos

        if(extras == null) {
            Log.d("Service","null");
        } else {
            Log.d("Service","not null");
            String from = (String) extras.get("CualRobot");
            cualRobotControlamos=from;
            String caractRobot = (String) extras.get("CaracteristicasRobotName");


            passedDatosDelRobot=new DataDescriptorRobot(
                    from,
                    (String) extras.get("CaracteristicasRobotName"),
                    (String) extras.get("CaracteristicasRobotTipo"),
                    (String) extras.get("CaracteristicasRobotManada"),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );  //ponemos aqui estas caraacteristicas, para pdoelos usar en el metodo dodne se definen los botones
            cualRobotCaracteristicas=caractRobot;
            if(from.equalsIgnoreCase("01")) {
                Log.d("Service","robot 01");
                System.out.println("onStart paso conectar 01"+cualRobotControlamos);
                System.out.println("onStart paso conectar 01"+caractRobot);

            }else if(from.equalsIgnoreCase("02")) {
                Log.d("Service","robot 02");

                System.out.println("onStart paso conectar 02"+cualRobotControlamos);
                System.out.println("onStart paso conectar 02"+caractRobot);

            }else if(from.equalsIgnoreCase("03")) {
                Log.d("Service","robot 03");

                System.out.println("onStart paso conectar 03"+cualRobotControlamos);
                System.out.println("onStart paso conectar 03"+caractRobot);

            }
            System.out.println("onStart paso conectar 00-"+cualRobotControlamos);
            System.out.println("onStart paso conectar 00"+caractRobot);
        }


//END:obten los dtos del robot que controlaremos
        System.out.println("             onStart paso antes de definir botones");
//START:define botones, y listeners para controlar al  robot
        defineBotonesControlesYListeners();
//END:define botones, y listeners para controlar al  robot
        System.out.println("             onStart paso despues de definir botones");

//START:define manager for sensor manager
        mSensorM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAcelerador=mSensorM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

//        mSensorM = (SensorManager) getApplicationContext()
//                .getSystemService(SENSOR_SERVICE);
        listen = new SensorListen();
        mSensorM.registerListener(listen, mAcelerador, SensorManager.SENSOR_DELAY_NORMAL);


//END:define manager for sensor manager
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getBaseContext(), "onDestroy", Toast.LENGTH_LONG).show();
        if (mImgButtonIzq != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mImgButtonIzq);
            mImgButtonIzq = null;
        }
        if (mImgButtonArriba != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mImgButtonArriba);
            mImgButtonArriba = null;
        }
        if (mImgButtonAbajo != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mImgButtonAbajo);
            mImgButtonAbajo = null;
        }
        if (mImgButtonDerecho != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mImgButtonDerecho);
            mImgButtonDerecho = null;
        }
        if (mImgButtonEnds != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mImgButtonEnds);
            mImgButtonEnds = null;
        }
        if (mImgButtonPeris != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mImgButtonPeris);
            mImgButtonPeris = null;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d("OverlayButton onTouch", "touched the button");
            stopSelf();
        }
        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {

        if (event.getAction() == DragEvent.ACTION_DRAG_EXITED) {
            Log.d("OverlayButton onDrag", "dragged the button");
            //stopSelf();
        }
        return true;
    }

    public void defineBotonesControlesYListeners(){
        mImgButtonIzq = new ImageButton(this);
        mImgButtonIzq.setBackgroundResource(R.mipmap.ic_botonflecharight);
//        mImgButtonIzq.setOnTouchListener(this);
        mImgButtonIzq.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == (MotionEvent.ACTION_UP)){
                    //Do whatever you want after press
//                    Toast.makeText(getBaseContext(), "Der-"+cualRobotControlamos+cualRobotCaracteristicas, Toast.LENGTH_LONG).show();
                    Toast.makeText(getBaseContext(), "Der", Toast.LENGTH_LONG).show();
                    // stopSelf();


                    System.out.println("goToAdd Comando RIGHT Robot - 2");
//old asp api /accessDB                   RequestTaskAddNuevaRutinaWeb th=new RequestTaskAddNuevaRutinaWeb();
// new nodejs api/mongodb
                    RequestTaskEnviarComandoRobotWeb th=new RequestTaskEnviarComandoRobotWeb();
                    System.out.println("goToAdd Comando RIGHT Robot - 3"+passedDatosDelRobot.getName());

//                    th.nombreRutina="saltitos";
                    th.nombreRutina=passedDatosDelRobot.getName();
                    th.secuencia="24";

//                    th.secuencia=listasecuencia;
                    System.out.println("goToAdd Comando RIGHT Robot - 4");
                    th.comando="RIGHT";
//                    th.comando=listacomandos;
                    System.out.println("goToAdd Comando RIGHT Robot - 5");
                    th.tiempo="2344";
                    th.status="creado";
//                    th.tiempo=listatiempos;

//                    TextView txtText = (TextView) findViewById(R.id.textResult);
                    TextView txtText=null;
                    th.execute(txtText); // here the result in text will be displayed
                    System.out.println("goToAdd RIGHT ComandoRobot - 4");

                }
                else{
                    //Do whatever you want during press
                }
                return true;
            }
        });

        WindowManager.LayoutParams paramsIzq = new WindowManager.LayoutParams(100,
                100,

                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
/*                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,

*/
                PixelFormat.TRANSLUCENT);

        paramsIzq.gravity = Gravity.LEFT | Gravity.CENTER;

//boton de arriba
/*        mButtonArriba = new Button(this);
        mButtonArriba.setText("Arriba");
        mButtonArriba.setTextColor(Color.BLACK);
        mButtonArriba.setOnTouchListener(this);
*/
        mImgButtonArriba = new ImageButton(this);
        mImgButtonArriba.setBackgroundResource(R.mipmap.ic_botonflechaup);
//        mImgButtonArriba.setOnTouchListener(this);
        mImgButtonArriba.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == (MotionEvent.ACTION_UP)){
                    //Do whatever you want after press
//                    Toast.makeText(getBaseContext(), "Arriba-"+cualRobotControlamos+cualRobotCaracteristicas, Toast.LENGTH_LONG).show();
                    Toast.makeText(getBaseContext(), "Arriba", Toast.LENGTH_LONG).show();
//                    stopSelf(); oculta los cuatro botones


                    System.out.println("goToAdd Comando Arriba/Adelante Robot - 2");
                    RequestTaskEnviarComandoRobotWeb th=new RequestTaskEnviarComandoRobotWeb();
                    System.out.println("goToAdd Comando Arriba/Adelante Robot - 3"+passedDatosDelRobot.getName());
//                    th.nombreRutina="saltitos";
                    th.nombreRutina=passedDatosDelRobot.getName();
                    th.secuencia="24";

//                    th.secuencia=listasecuencia;
                    System.out.println("goToAdd Comando Arriba/Adelante Robot - 4");
                    th.comando="UP";
//                    th.comando=listacomandos;
                    System.out.println("goToAdd Comando Arriba/Adelante Robot - 5");
                    th.tiempo="2344";
//                    th.tiempo=listatiempos;
                    th.status="creado";
//                    TextView txtText = (TextView) findViewById(R.id.textResult);
                    TextView txtText=null;
                    th.execute(txtText); // here the result in text will be displayed
                    System.out.println("goToAdd Arriba/Adelante ComandoRobot - 4");

                }
                else{
                    //Do whatever you want during press
                }
                return true;
            }
        });

        WindowManager.LayoutParams paramsTop = new WindowManager.LayoutParams(100,
                100, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

        paramsTop.gravity = Gravity.TOP | Gravity.CENTER;



//boton de abajo
        /*
        mButtonAbajo = new Button(this);
        mButtonAbajo.setText("Abajo");
        mButtonAbajo.setTextColor(Color.BLACK);
        mButtonAbajo.setOnTouchListener(this);
*/
        mImgButtonAbajo = new ImageButton(this);
        mImgButtonAbajo.setBackgroundResource(R.mipmap.ic_botonflechadown);
        //mImgButtonAbajo.setOnTouchListener(this);

        mImgButtonAbajo.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == (MotionEvent.ACTION_UP)){
                    //Do whatever you want after press

                    Toast.makeText(getBaseContext(), "Abajo", Toast.LENGTH_LONG).show();
                    //stopSelf();

// new nodejs api/mongodb
                    RequestTaskEnviarComandoRobotWeb th=new RequestTaskEnviarComandoRobotWeb();
                    System.out.println("goToAdd Comando Abajo/Atras Robot - 3"+passedDatosDelRobot.getName());
//                    th.nombreRutina="saltitos";
                    th.nombreRutina=passedDatosDelRobot.getName();

                    th.secuencia="24";

//                    th.secuencia=listasecuencia;
                    System.out.println("goToAdd Comando Abajo/Atras Robot - 4");
                    th.comando="DOWN";
//                    th.comando=listacomandos;
                    System.out.println("goToAdd Comando Abajo/Atras Robot - 5");
                    th.tiempo="2344";
//                    th.tiempo=listatiempos;
                    th.status="creado";
//                    TextView txtText = (TextView) findViewById(R.id.textResult);
                    TextView txtText=null;
                    th.execute(txtText); // here the result in text will be displayed
                    System.out.println("goToAdd Abajo/Atras ComandoRobot - 4");
                }
                else{
                    //Do whatever you want during press
                }
                return true;
            }
        });

        WindowManager.LayoutParams paramsAbajo = new WindowManager.LayoutParams(100,
                100, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

        paramsAbajo.gravity = Gravity.BOTTOM | Gravity.CENTER;

        //boton de derecho
        /*
        mButtonDerecho = new Button(this);
        mButtonDerecho.setText("->");
        mButtonDerecho.setTextColor(Color.BLACK);
        mButtonDerecho.setOnTouchListener(this);
     */
        mImgButtonDerecho = new ImageButton(this);
        mImgButtonDerecho.setBackgroundResource(R.mipmap.ic_botonflechaleft);
        // mImgButtonDerecho.setOnTouchListener(this);

        mImgButtonDerecho.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == (MotionEvent.ACTION_UP)){
                    //Do whatever you want after press
//                    Toast.makeText(getBaseContext(), "Izq -"+cualRobotControlamos+cualRobotCaracteristicas, Toast.LENGTH_LONG).show();
                    Toast.makeText(getBaseContext(), "Izq ", Toast.LENGTH_LONG).show();

                    // stopSelf();

                    System.out.println("goToAdd Comando LEFT Robot - 2");
                    RequestTaskEnviarComandoRobotWeb th=new RequestTaskEnviarComandoRobotWeb();
                    System.out.println("goToAdd Comando LEFT Robot - 3"+passedDatosDelRobot.getName());
//                    th.nombreRutina="saltitos";
                    th.nombreRutina=passedDatosDelRobot.getName();
                    th.secuencia="24";

//                    th.secuencia=listasecuencia;
                    System.out.println("goToAdd Comando LEFT Robot - 4");
                    th.comando="LEFT";
//                    th.comando=listacomandos;
                    System.out.println("goToAdd Comando LEFT Robot - 5");
                    th.tiempo="2344";
//                    th.tiempo=listatiempos;
                    th.status="creado";
//                    TextView txtText = (TextView) findViewById(R.id.textResult);
                    TextView txtText=null;
                    th.execute(txtText); // here the result in text will be displayed
                    System.out.println("goToAdd LEFT ComandoRobot - 4");

                }
                else{
                    //Do whatever you want during press
                }
                return true;
            }
        });
        WindowManager.LayoutParams paramsDerecho = new WindowManager.LayoutParams(100,
                100, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

        paramsDerecho.gravity = Gravity.RIGHT | Gravity.CENTER;


//bot0on para terminar/cerrar los controles de boton


        mImgButtonEnds = new ImageButton(this);
        mImgButtonEnds.setBackgroundResource(R.mipmap.stop_smallito);


        mImgButtonEnds.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == (MotionEvent.ACTION_UP)){
                    //Do whatever you want after press
                    Toast.makeText(getBaseContext(), "Cerramos/Close -", Toast.LENGTH_LONG).show();

                    stopSelf();


                }
                else{
                    //Do whatever you want during press
                }
                return true;
            }
        });
        WindowManager.LayoutParams paramsCerrar = new WindowManager.LayoutParams(50,
                50, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

        paramsCerrar.gravity = Gravity.LEFT | Gravity.TOP;

        //bot0on para terminar/cerrar los controles de boton


        mImgButtonPeris = new ImageButton(this);
        mImgButtonPeris.setBackgroundResource(R.mipmap.ic_peris);


        mImgButtonPeris.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == (MotionEvent.ACTION_UP)){
                    //Do whatever you want after press
                    Toast.makeText(getBaseContext(), "Periscope -"+cualRobotControlamos+cualRobotCaracteristicas, Toast.LENGTH_LONG).show();

                    // stopSelf();



                }
                else{
                    //Do whatever you want during press
                }
                return true;
            }
        });
        WindowManager.LayoutParams paramsPeris = new WindowManager.LayoutParams(50,
                50, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

        paramsPeris.gravity = Gravity.LEFT | Gravity.BOTTOM;

        //nuevo boton flotante imagen personaje 1
        //start
        mImgButtonPerso = new ImageButton(this);
        mImgButtonPerso.setBackgroundResource(R.mipmap.personaje30vectorized);


        mImgButtonPerso.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("boton abajo");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("boton abajo 01");

                        System.out.println("boton abajo 01x:"+paramsPerso.x);
                        System.out.println("boton abajo 01y:"+paramsPerso.y);

                        //remember the initial position.
                        initialX = paramsPerso.x;
                        initialY = paramsPerso.y;


                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("boton abajo 03");
                        //Calculate the X and Y coordinates of the view.
                        paramsPerso.x = initialX + (int) (event.getRawX() - initialTouchX);
                        paramsPerso.y = initialY + (int) (event.getRawY() - initialTouchY);


                        //Update the layout with new X & Y coordinate
                        wm.updateViewLayout(v, paramsPerso);
                        return true;
                }
                System.out.println("boton abajo 04");
                return false;
            }
        });

//        WindowManager.LayoutParams paramsPerso = new WindowManager.LayoutParams(50,
          paramsPerso = new WindowManager.LayoutParams(100,
                100, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

        paramsPerso.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        paramsPerso.x = 200;
        paramsPerso.y = 200;

        //ends boton flotante personaje 1


        //nuevo boton flotante imagen personaje 2
        //start
        mImgButtonPerso02 = new ImageButton(this);
        mImgButtonPerso02.setBackgroundResource(R.mipmap.personaje16vectorized);


        mImgButtonPerso02.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("boton abajo");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("boton abajo 01");

                        System.out.println("boton abajo 01x:"+paramsPerso02.x);
                        System.out.println("boton abajo 01y:"+paramsPerso02.y);

                        //remember the initial position.
                        initialX = paramsPerso02.x;
                        initialY = paramsPerso02.y;


                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("boton abajo 03");
                        //Calculate the X and Y coordinates of the view.
                        paramsPerso02.x = initialX + (int) (event.getRawX() - initialTouchX);
                        paramsPerso02.y = initialY + (int) (event.getRawY() - initialTouchY);


                        //Update the layout with new X & Y coordinate
                        wm.updateViewLayout(v, paramsPerso02);
                        return true;
                }
                System.out.println("boton abajo 04");
                return false;
            }
        });


        paramsPerso02 = new WindowManager.LayoutParams(100,
                100, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, PixelFormat.TRANSLUCENT);

        paramsPerso02.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        paramsPerso02.x = 50;
        paramsPerso02.y = 50;

        //ends boton flotante personaje 02

//agregar los botonas al viw que flta en la pantalla

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(mImgButtonIzq, paramsIzq);
        wm.addView(mImgButtonArriba, paramsTop);
        wm.addView(mImgButtonAbajo, paramsAbajo);
        wm.addView(mImgButtonDerecho, paramsDerecho);
        wm.addView(mImgButtonEnds, paramsCerrar);
        wm.addView(mImgButtonPeris, paramsPeris);
        wm.addView(mImgButtonPerso, paramsPerso);
        wm.addView(mImgButtonPerso02, paramsPerso02);
    }//termina method defineBotonesControlesYListeners



    //logica para manejo de sensores e inlcinacion

    //START:
    public class SensorListen implements SensorEventListener{

        // se asume que el telefono se utilizara de forma horizontal siempre
        //si z se hace negativo es que el usuario esta mirando hacia abajo, y quiere que el robot retroceda
        //si z se hace positvo es que el usuario esta mirando hacia arriba, y quiere que el robot avance
        //si y se hace negativo es que el usuario esta inclinando su cabeza a la derecha, y quiere que el robot avance a la derecha
        //si y se hace positivo es que el usuario esta inclinando su cabeza a la izquierda, y quiere que el robot avance a la izquierda
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x =event.values[0];
            float y =event.values[1];
            float z =event.values[2];
            System.out.println("Actual valor de x:"+x+  "  valor y:" +  y +" valor z:"+z);
            if(Math.abs(z) > Math.abs(y)){
                //arriba abajo
                if(z<0 && mImgButtonPerso02!=null){ //aajo
                    //inclina su cabeza hacia abajo
                    System.out.println("Abajo-valor de x:"+x+  "  valor y:" +  y +" valor z:"+z);
                    mImgButtonPerso02.setBackgroundResource(R.mipmap.abajo);

                }
                if(z>0 && mImgButtonPerso02!=null){//arriba
                    //inclina su cabeza hacia arriba
                    System.out.println("arriba- valor de x:"+x+  "  valor y:" +  y +" valor z:"+z);
                    mImgButtonPerso02.setBackgroundResource(R.mipmap.arriba);
                }
            }else{
                //inclina su cabeza a la derecha
                if(y<0 && mImgButtonPerso02!=null){
                    System.out.println("der-valor de x:"+x+  "  valor y:" +  y +" valor z:"+z);
                    mImgButtonPerso02.setBackgroundResource(R.mipmap.derecha);
                }
                if(y>0 && mImgButtonPerso02!=null){
                    //inclina su cabeza a la izquierad
                    System.out.println("izq-valor de x:"+x+  "  valor y:" +  y +" valor z:"+z);
                    mImgButtonPerso02.setBackgroundResource(R.mipmap.izquierda);
                }
            }
            if ( z> (-1.5) && z < (1.5) && y >(-1.5) && y< (1.5) && mImgButtonPerso02!=null){
                mImgButtonPerso02.setBackgroundResource(R.mipmap.centro);
            }
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }


        protected void onResume(){
            // super.onResume();
            mSensorM.registerListener(this,mAcelerador,SensorManager.SENSOR_DELAY_NORMAL);
        }


        protected void onPause(){
            // super.onPause();
            mSensorM.unregisterListener(this);
        }
    }
    //END:

}

