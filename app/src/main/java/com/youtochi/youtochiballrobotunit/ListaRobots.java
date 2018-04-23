package com.youtochi.youtochiballrobotunit;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 813743 on 26/03/2017.
 */
public class ListaRobots extends ArrayAdapter{

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;

    public ListaRobots(Activity context,String[] web, Integer[] imageId){
        super(context,R.layout.lista_single,web);
        this.context= context;
        this.web = web;
        this.imageId=imageId;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflador= context.getLayoutInflater();
        View rowView=inflador.inflate(R.layout.lista_single,null,true);

        TextView txtTitle =(TextView) rowView.findViewById(R.id.txt);
        ImageView imagenV= (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);

        imagenV.setImageResource(imageId[position]);;
        Button btnTitle =(Button) rowView.findViewById(R.id.btnRegresarCobrar);
        btnTitle.setHint(position+"");//this value is used later when user clicks on the button, hint position value

        return rowView;

    }
}
