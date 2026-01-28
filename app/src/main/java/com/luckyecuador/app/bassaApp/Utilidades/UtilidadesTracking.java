package com.luckyecuador.app.bassaApp.Utilidades;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;


import com.luckyecuador.app.bassaApp.Contracts.ContractInsertTracking;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilidadesTracking {

    public static final int COLUMNA_PHARMA_ID = 2;
    public static final int COLUMNA_FECHA = 3;
    public static final int COLUMNA_HORA = 4;
    public static final int COLUMNA_CODIGO = 5;
    public static final int COLUMNA_LOCAL = 6;
    public static final int COLUMNA_USUARIO = 7;
    public static final int COLUMNA_LATITUD = 8;
    public static final int COLUMNA_LONGITUD = 9;
    public static final int COLUMNA_MECANICA = 10;
    public static final int COLUMNA_CATEGORIA = 11;
    public static final int COLUMNA_DESCRIPCION = 12;
    public static final int COLUMNA_VIGENCIA = 13;
    public static final int COLUMNA_STATUS_ACTIVIDAD = 14;
    public static final int COLUMNA_COMENTARIO = 15;
    public static final int COLUMNA_FOTO = 16;
    public static final int COLUMNA_PRECIO_PROMOCION = 17;
    public static final int COLUMNA_MATERIAL_POP = 18;
    public static final int COLUMNA_IMPLEMENTACION_POP = 19;
    public static final int COLUMNA_CUENTA = 20;
    public static final int COLUMNA_MODULO = 21;
    public static final int COLUMNA_UNIDAD_DE_NEGOCIO = 22;

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
        String fecha;
        String hora;
        String codigo;
        String local;
        String usuario;
        String latitud;
        String longitud;
        String mecanica;
        String categoria;
        String descripcion;
        String vigencia;
        String status_actividad;
        String comentario;
        String foto;
        String precio_promocion;
        String material_pop;
        String implementacion_pop;
        String cuenta;
        String modulo;
        String unidad_de_negocio;

        pharma_id = c.getString(COLUMNA_PHARMA_ID);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);
        codigo = c.getString(COLUMNA_CODIGO);
        local = c.getString(COLUMNA_LOCAL);
        usuario = c.getString(COLUMNA_USUARIO);
        latitud = c.getString(COLUMNA_LATITUD);
        longitud = c.getString(COLUMNA_LONGITUD);
        mecanica = c.getString(COLUMNA_MECANICA);
        categoria = c.getString(COLUMNA_CATEGORIA);
        descripcion = c.getString(COLUMNA_DESCRIPCION);
        vigencia = c.getString(COLUMNA_VIGENCIA);
        status_actividad = c.getString(COLUMNA_STATUS_ACTIVIDAD);
        comentario = c.getString(COLUMNA_COMENTARIO);
        foto = c.getString(COLUMNA_FOTO);
        precio_promocion = c.getString(COLUMNA_PRECIO_PROMOCION);
        material_pop = c.getString(COLUMNA_MATERIAL_POP);
        implementacion_pop = c.getString(COLUMNA_IMPLEMENTACION_POP);
        cuenta = c.getString(COLUMNA_CUENTA);
        modulo = c.getString(COLUMNA_MODULO);
        unidad_de_negocio = c.getString(COLUMNA_UNIDAD_DE_NEGOCIO);

        try {
            jObject.put(ContractInsertTracking.Columnas.PHARMA_ID, pharma_id);
            jObject.put(ContractInsertTracking.Columnas.FECHA, fecha);
            jObject.put(ContractInsertTracking.Columnas.HORA, hora);
            jObject.put(ContractInsertTracking.Columnas.CODIGO, codigo);
            jObject.put(ContractInsertTracking.Columnas.LOCAL, local);
            jObject.put(ContractInsertTracking.Columnas.USUARIO, usuario);
            jObject.put(ContractInsertTracking.Columnas.LATITUD, latitud);
            jObject.put(ContractInsertTracking.Columnas.LONGITUD, longitud);
            jObject.put(ContractInsertTracking.Columnas.MECANICA, mecanica);
            jObject.put(ContractInsertTracking.Columnas.CATEGORIA, categoria);
            jObject.put(ContractInsertTracking.Columnas.DESCRIPCION, descripcion);
            jObject.put(ContractInsertTracking.Columnas.VIGENCIA, vigencia);
            jObject.put(ContractInsertTracking.Columnas.STATUS_ACTIVIDAD, status_actividad);
            jObject.put(ContractInsertTracking.Columnas.COMENTARIO, comentario);
            jObject.put(ContractInsertTracking.Columnas.FOTO, foto);
            jObject.put(ContractInsertTracking.Columnas.PRECIO_PROMOCION, precio_promocion);
            jObject.put(ContractInsertTracking.Columnas.MATERIAL_POP, material_pop);
            jObject.put(ContractInsertTracking.Columnas.IMPLEMENTACION_POP, implementacion_pop);
            jObject.put(ContractInsertTracking.Columnas.CUENTA, cuenta);
            jObject.put(ContractInsertTracking.Columnas.MODULO, modulo);
            jObject.put(ContractInsertTracking.Columnas.UNIDAD_DE_NEGOCIO, unidad_de_negocio);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }

}
