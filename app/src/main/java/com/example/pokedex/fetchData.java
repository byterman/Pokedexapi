package com.example.pokedex;


import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.ahmadrosid.svgloader.SvgLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class fetchData extends AsyncTask<Void, Void, Void> {

    protected String data = "";
    protected String results = "";
    protected String resultsid = "";
    protected ArrayList<String> strTypes; // Create an ArrayList object
    protected String pokSearch, typeSearch, tipostr;
    static String intArray[];
    static ArrayList<String> busquedatipos;
    static boolean bolbusquedatipos=false;


    public fetchData(String pokSearch, String typeSearch, String tipostr) {
        this.pokSearch = pokSearch;
        this.typeSearch = typeSearch;
        this.tipostr = tipostr;
        this.strTypes = new ArrayList<String>();
        this.busquedatipos = new ArrayList<String>();
    }


    @Override
    protected Void doInBackground(Void... voids) {

        try {
            if(typeSearch.equals("act_1")){
                //Make API connection
                URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + pokSearch);
                Log.i("logtest", "https://pokeapi.co/api/v2/pokemon/" + pokSearch);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                busqueda(httpURLConnection);

                /*
                this.bolbusquedatipos=false;
                 */

            }else if(typeSearch.equals("act_2")){
                URL url = new URL("https://pokeapi.co/api/v2/type/" + tipostr );
                Log.i("logtest", "https://pokeapi.co/api/v2/type/" + tipostr);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                busqueda(httpURLConnection);

                /*
                this.bolbusquedatipos=true;

                 */
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        JSONObject jObject = null;
        String img = "";
        String typeName = "";
        String typeObj="";

        try {
            jObject = new JSONObject(data);

            if(typeSearch.equals("act_1")) {
                results = "Name: " + jObject.getString("name").toUpperCase() + "\n" +
                        "Height: " + jObject.getString("height") + "\n" +
                        "Weight: " + jObject.getString("weight");
                resultsid = "ID:" + jObject.getString("id");
                // Get img SVG
                JSONObject sprites = new JSONObject(jObject.getString("sprites"));
                JSONObject other = new JSONObject(sprites.getString("other"));
                JSONObject dream_world = new JSONObject(other.getString("dream_world"));
                img  = dream_world.getString("front_default");

                // Get type/types
                JSONArray types = new JSONArray(jObject.getString("types"));
                for(int i=0; i<types.length(); i++){
                    JSONObject type = new JSONObject(types.getString(i));
                    JSONObject type2  = new JSONObject(type.getString("type"));
                    strTypes.add(type2.getString("name"));
                }
            }else if(typeSearch.equals("act_2")){
                JSONArray pokemon = new JSONArray(jObject.getString("pokemon"));
                for(int i=0; i<pokemon.length(); i++){
                    JSONObject objpoke = new JSONObject(pokemon.getString(i));
                    JSONObject objpoke2  = new JSONObject(objpoke.getString("pokemon"));
                    String objpoke3  = objpoke2.getString("name");
                    Log.i("logtest", objpoke2.getString("name"));
                    Log.i("logtest", objpoke3);

                    /*
                    intArray[i]= objpoke3;
                    */
                    busquedatipos.add(objpoke3);
                    funciones.setarray(objpoke3);
                    //strTypes.add(type2.getString("name"));
                }
            }
            // Get JSON name, height, weight

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Set info
        MainActivity.txtDisplay.setText(this.results);
        MainActivity.txtid.setText(this.resultsid);
//        // Set main img
         SvgLoader.pluck()
                   .with(MainActivity.act)
                  .load(img, MainActivity.imgPok);

      setimgtype();
    }



    public void busqueda(HttpURLConnection httpURLConnection) throws IOException {
        // Read API results
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sBuilder = new StringBuilder();

        // Build JSON String
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            sBuilder.append(line + "\n");
        }

        inputStream.close();
        data = sBuilder.toString();

    }



    public void setimgtype (){
        //        // Set img types
        for(int i=0; i<strTypes.size(); i++){
            MainActivity.imgType[i].setImageResource(MainActivity.act.getResources().getIdentifier(strTypes.get(i), "drawable", MainActivity.act.getPackageName()));
            if(strTypes.size()==1){
                MainActivity.imgType[1].setVisibility(View.INVISIBLE);
            }else{
                MainActivity.imgType[1].setVisibility(View.INVISIBLE);
            }
        }
    }

    /*
    public String getPokName(int i){

        return this.busquedatipos.get(i);
    }

    public int getsize(){
        return busquedatipos.size();
    }


    public static boolean getbooleantype(){

        return this.bolbusquedatipos;
    }

    public static String getarraytipe(int num){

        return this.intArray[num];
    }
*/

}
