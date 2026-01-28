package com.luckyecuador.app.bassaApp.Utilidades;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.luckyecuador.app.bassaApp.Contracts.ContractInsertFotografico;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilidadesFotografico {

    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_PHARMA_ID = 2;
    public static final int COLUMNA_CODIGO = 3;
    public static final int COLUMNA_LOCAL = 4;
    public static final int COLUMNA_USUARIO = 5;
    public static final int COLUMNA_CATEGORIA = 6;
    public static final int COLUMNA_SUBCATEGORIA = 7;
    public static final int COLUMNA_MARCA = 8;
    public static final int COLUMNA_LOGRO = 9;

    public static final int COLUMNA_STATUS = 10;
    public static final int COLUMNA_OBSERVACION = 11;
    public static final int COLUMNA_KEY_IMAGE = 12;
    public static final int COLUMNA_FECHA = 13;
    public static final int COLUMNA_HORA = 14;
    public static final int COLUMNA_TIPO_EXHIBICION = 15;
    public static final int COLUMNA_CONTRATADA = 16;

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
        String local;
        String usuario;
        String categoria;
        String subcategoria;
        String marca;
        String logro;
        String status;
        String observacion;
        String key_image;
        String fecha;
        String hora;
        String tipo_exhibicion;
        String contratada;

        pharma_id = c.getString(COLUMNA_PHARMA_ID);
        codigo = c.getString(COLUMNA_CODIGO);
        local = c.getString(COLUMNA_LOCAL);
        usuario = c.getString(COLUMNA_USUARIO);
        categoria = c.getString(COLUMNA_CATEGORIA);
        subcategoria = c.getString(COLUMNA_SUBCATEGORIA);
        marca = c.getString(COLUMNA_MARCA);
        logro = c.getString(COLUMNA_LOGRO);
        status = c.getString(COLUMNA_STATUS);
        observacion = c.getString(COLUMNA_OBSERVACION);
        key_image = c.getString(COLUMNA_KEY_IMAGE);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);
        tipo_exhibicion = c.getString(COLUMNA_TIPO_EXHIBICION);
        contratada = c.getString(COLUMNA_CONTRATADA);


        try {
            jObject.put(ContractInsertFotografico.Columnas.PHARMA_ID, pharma_id);
            jObject.put(ContractInsertFotografico.Columnas.CODIGO, codigo);
            jObject.put(ContractInsertFotografico.Columnas.POS_NAME, local);
            jObject.put(ContractInsertFotografico.Columnas.USUARIO, usuario);
            jObject.put(ContractInsertFotografico.Columnas.CATEGORIA, categoria);
            jObject.put(ContractInsertFotografico.Columnas.SUBCATEGORIA, subcategoria);
            jObject.put(ContractInsertFotografico.Columnas.MARCA, marca);
            jObject.put(ContractInsertFotografico.Columnas.LOGRO, logro);
            jObject.put(ContractInsertFotografico.Columnas.STATUS, status);
            jObject.put(ContractInsertFotografico.Columnas.OBSERVACION, observacion);
            jObject.put(ContractInsertFotografico.Columnas.KEY_IMAGE, key_image);
            jObject.put(ContractInsertFotografico.Columnas.FECHA, fecha);
            jObject.put(ContractInsertFotografico.Columnas.HORA, hora);
            jObject.put(ContractInsertFotografico.Columnas.TIPO_EXH, tipo_exhibicion);
            jObject.put(ContractInsertFotografico.Columnas.CONTRATADA, contratada);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }

}
