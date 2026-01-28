package com.luckyecuador.app.bassaApp.Utilidades;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.luckyecuador.app.bassaApp.Contracts.ContractInsertProbadores;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilidadesProbadores {

    public static final int COLUMNA_PHARMA_ID = 2;
    public static final int COLUMNA_CODIGO = 3;
    public static final int COLUMNA_USUARIO = 4;
    public static final int COLUMNA_SUPERVISOR = 5;
    public static final int COLUMNA_FECHA = 6;
    public static final int COLUMNA_HORA = 7;
    public static final int COLUMNA_TIPO_REGISTRO = 8;
    public static final int COLUMNA_MARCA = 9;
    public static final int COLUMNA_SKU = 10;
    public static final int COLUMNA_CANTIDAD = 11;
    public static final int COLUMNA_LOTE = 12;
    public static final int COLUMNA_FECHA_VENCIMIENTO = 13;
    public static final int COLUMNA_FABRICANTE = 14;
    public static final int COLUMNA_FOTO= 15;
    public static final int COLUMNA_COMENTARIO = 16;



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
        String tipo_registro;
        String marca;
        String sku;
        String cantidad;
        String lote;
        String fecha_vencimiento;
        String fabricante;
        String foto;
        String comentario;
      //  String fabricante;
       

        pharma_id = c.getString(COLUMNA_PHARMA_ID);
        codigo = c.getString(COLUMNA_CODIGO);
        usuario = c.getString(COLUMNA_USUARIO);
        supervisor = c.getString(COLUMNA_SUPERVISOR);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);
        tipo_registro = c.getString(COLUMNA_TIPO_REGISTRO);
        marca = c.getString(COLUMNA_MARCA);
        sku = c.getString(COLUMNA_SKU);
        cantidad = c.getString(COLUMNA_CANTIDAD);
        lote = c.getString(COLUMNA_LOTE);
        fecha_vencimiento = c.getString(COLUMNA_FECHA_VENCIMIENTO);
        fabricante = c.getString(COLUMNA_FABRICANTE);
        foto = c.getString(COLUMNA_FOTO);
        comentario = c.getString(COLUMNA_COMENTARIO);



        try {

            jObject.put(ContractInsertProbadores.Columnas.PHARMA_ID, pharma_id);
            jObject.put(ContractInsertProbadores.Columnas.CODIGO, codigo);
            jObject.put(ContractInsertProbadores.Columnas.USUARIO, usuario);
            jObject.put(ContractInsertProbadores.Columnas.SUPERVISOR, supervisor);
            jObject.put(ContractInsertProbadores.Columnas.FECHA, fecha);
            jObject.put(ContractInsertProbadores.Columnas.HORA, hora);
            jObject.put(ContractInsertProbadores.Columnas.TIPO_REGISTRO, tipo_registro);
            jObject.put(ContractInsertProbadores.Columnas.MARCA, marca);
            jObject.put(ContractInsertProbadores.Columnas.SKU, sku);
            jObject.put(ContractInsertProbadores.Columnas.CANTIDAD, cantidad);
            jObject.put(ContractInsertProbadores.Columnas.LOTE, lote);
            jObject.put(ContractInsertProbadores.Columnas.FECHA_VENCIMIENTO, fecha_vencimiento);
            jObject.put(ContractInsertProbadores.Columnas.FABRICANTE, fabricante);
            jObject.put(ContractInsertProbadores.Columnas.FOTO, foto);
            jObject.put(ContractInsertProbadores.Columnas.COMENTARIO, comentario);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }

}
