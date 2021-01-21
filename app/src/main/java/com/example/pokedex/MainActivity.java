package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity{

    public static Activity act;
    public static TextView txtDisplay;
    public static ImageView imgPok;
    public static TextView txtid;

    protected fetchData processPokemon = null;
    protected fetchData processType =null;


    public static ImageView [] imgType;
    int pokemas,pokemastype=0;
    String nombrepokemontipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pokemas=1;
        act = this;
        imgType = new ImageView[2];

        txtid = findViewById(R.id.idtxt);
        txtDisplay = findViewById(R.id.txtDisplay);
        imgPok = findViewById(R.id.imgPok);
        imgType[0] = findViewById(R.id.imgType0);
        imgType[1] = findViewById(R.id.imgType1);


        processPokemon = new fetchData(Integer.toString(pokemas), "act_1", null);
        processPokemon.execute();

       ImageButton btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTxtSearch();

            }
        });

        ImageButton btnTypes = findViewById(R.id.btnTypes);
        btnTypes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Mostrartipos();
                Log.i("","Has hecho click");
            }
        });

        Button btnRight = findViewById(R.id.btnRight);
        btnRight.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    if (funciones.getboolean()==true){

                        Log.i("tamano", "tamano:" + funciones.getsize());

                        if (pokemastype<funciones.getsize()){
                            pokemastype = pokemastype +1;
                            Log.i("tamano", "pokemastype:" + pokemastype);
                            Log.i("tamano", "nombre:" + funciones.getarray(pokemastype));
                            nombrepokemontipo= funciones.getarray(pokemastype);

                            processPokemon = new fetchData(nombrepokemontipo ,"act_1", null);
                            processPokemon.execute();
                        }else{

                        }
                    }else{
                        String strpokemas;
                        pokemas = pokemas +1;
                        strpokemas = Integer.toString(pokemas);
                        processPokemon = new fetchData(strpokemas, "act_1", null);
                        processPokemon.execute();
                    }
                }

        });

        Button btnLeft = findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if (funciones.getboolean()==true){
                    Log.i("tamano", "pokemastype:" + pokemastype);
                    if(pokemastype>0){
                        Log.i("tamano", "tamano:" + funciones.getsize());
                        pokemastype = pokemastype -1;
                        nombrepokemontipo= funciones.getarray(pokemastype);

                        processPokemon = new fetchData(nombrepokemontipo ,"act_1", null);
                        processPokemon.execute();
                    }else{

                    }


                }else{
                    if (pokemas>1){
                        String strpokemas;

                        pokemas = pokemas -1;
                        strpokemas = Integer.toString(pokemas);
                        processPokemon = new fetchData(strpokemas ,"act_1", null);
                        processPokemon.execute();
                    }else{

                    }
                }

            }
        });
    }


    public void showTxtSearch(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Search a Pokemon");

        final EditText input = new EditText(this);
        input.setHint("Pokemon");
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                funciones.setbooleantipofalse();
                String pokSearch = input.getText().toString();
                processPokemon = new fetchData(pokSearch ,"act_1", null);
                processPokemon.execute();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    public void Mostrartipos(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.Tipo)
                .setItems(R.array.tipos, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        String selected = getResources().getStringArray(R.array.tipos)[which];
                        funciones.setbooleantipotrue();
                        funciones.cleararray();
                        pokemastype = 0;
                        processType = new fetchData(null ,"act_2", selected);
                        processType.execute();

                    }
                });
        builder.show();

    }

}