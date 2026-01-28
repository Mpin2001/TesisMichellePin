package com.luckyecuador.app.bassaApp.Utilidades;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.luckyecuador.app.bassaApp.Contracts.ContractInsertMuestras;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilidadesMuestras {

    public static final int COLUMNA_PHARMA_ID = 2;
    public static final int COLUMNA_CODIGO = 3;
    public static final int COLUMNA_USUARIO = 4;
    public static final int COLUMNA_SUPERVISOR = 5;
    public static final int COLUMNA_FECHA = 6;
    public static final int COLUMNA_HORA = 7;
    public static final int COLUMNA_MARCA = 8;
    public static final int COLUMNA_SKU = 9;
    public static final int COLUMNA_TIPO_ACTIVIDAD = 10;
    public static final int COLUMNA_CANTIDAD = 11;
    public static final int COLUMNA_POS_NAME = 12;



    /**
     * Determina si la aplicación corre en versiones superiores o iguales
     * a Android LOLLIPOP
     *
     * @return booleano de confirmación
     */
    public static boolean materialDesign() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Copia los datos de un gasto almacenados en un cursor hacia un
     * JSONObject
     *
     * @param c cursor
     * @return objeto jason
     */
    public static JSONObject deCursorAJSONObject(Cursor c) {
        JSONObject jObject = new JSONObject();

        String pharma_id;
        String codigo;
        String usuario;
        String supervisor;
        String fecha;
        String hora;
        String marca;
        String sku;
        String tipo_actividad;
        String cantidad;
        String pos_name;
      //  String fabricante;
       

        pharma_id = c.getString(COLUMNA_PHARMA_ID);
        codigo = c.getString(COLUMNA_CODIGO);
        usuario = c.getString(COLUMNA_USUARIO);
        supervisor = c.getString(COLUMNA_SUPERVISOR);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);
        marca = c.getString(COLUMNA_MARCA);
        sku = c.getString(COLUMNA_SKU);
        tipo_actividad = c.getString(COLUMNA_TIPO_ACTIVIDAD);
        cantidad = c.getString(COLUMNA_CANTIDAD);
        pos_name = c.getString(COLUMNA_POS_NAME);



        try {

            jObject.put(ContractInsertMuestras.Columnas.PHARMA_ID, pharma_id);
            jObject.put(ContractInsertMuestras.Columnas.CODIGO, codigo);
            jObject.put(ContractInsertMuestras.Columnas.USUARIO, usuario);
            jObject.put(ContractInsertMuestras.Columnas.SUPERVISOR, supervisor);
            jObject.put(ContractInsertMuestras.Columnas.FECHA, fecha);
            jObject.put(ContractInsertMuestras.Columnas.HORA, hora);
            jObject.put(ContractInsertMuestras.Columnas.MARCA, marca);
            jObject.put(ContractInsertMuestras.Columnas.SKU, sku);
            jObject.put(ContractInsertMuestras.Columnas.TIPO_ACTIVIDAD, tipo_actividad);
            jObject.put(ContractInsertMuestras.Columnas.CANTIDAD, cantidad);
            jObject.put(ContractInsertMuestras.Columnas.POS_NAME, pos_name);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }

}
