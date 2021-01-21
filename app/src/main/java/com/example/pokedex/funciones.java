package com.example.pokedex;

import java.util.ArrayList;

public class funciones {

    private static boolean bolbusquedatipos;
    private static ArrayList<String> busquedatipos = new ArrayList<String>();

    public static void setbooleantipotrue(){
        bolbusquedatipos=true;
    }

    public static void setbooleantipofalse(){
        bolbusquedatipos=false;
    }

    public static boolean getboolean(){
        return  bolbusquedatipos;
    }

    public static  void setarray(String pokenombre){
        busquedatipos.add(pokenombre);
    }

    public static String getarray(int i){
        return busquedatipos.get(i);
    }

    public static int getsize(){
        return busquedatipos.size();
    }
    public  static void cleararray(){
        busquedatipos.clear();
    }
}
