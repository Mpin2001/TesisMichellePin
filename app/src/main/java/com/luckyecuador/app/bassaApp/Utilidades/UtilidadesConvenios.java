package com.luckyecuador.app.bassaApp.Utilidades;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.luckyecuador.app.bassaApp.Contracts.ContractInsertConvenios;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilidadesConvenios {

    public static final int COLUMNA_PHARMA_ID = 2;
    public static final int COLUMNA_FECHA = 3;
    public static final int COLUMNA_HORA = 4;
    public static final int COLUMNA_CODIGO = 5;
    public static final int COLUMNA_POS_NAME = 6;
    public static final int COLUMNA_USUARIO = 7;
    public static final int COLUMNA_LATITUD = 8;
    public static final int COLUMNA_LONGITUD = 9;
    public static final int COLUMNA_CATEGORIA = 10;
    public static final int COLUMNA_FABRICANTE = 11;
    public static final int COLUMNA_MARCA = 12;
    public static final int COLUMNA_TIPO_EXHIBICION = 13;
    public static final int COLUMNA_NUMERO_EXHIBICION = 14;
    public static final int COLUMNA_CONTRATADA = 15;
    public static final int COLUMNA_OBSERVACION = 16;
    public static final int COLUMNA_FOTO = 17;
    public static final int COLUMNA_MODULO = 18;
    public static final int COLUMNA_UNIDAD_DE_NEGOCIO = 19;
    public static final int COLUMNA_ROL = 20;

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
        String pos_name;
        String usuario;
        String latitud;
        String longitud;
        String categoria;
        String fabricante;
        String marca;
        String tipo_exhibicion;
        String numero_exhibicion;
        String contratada;
        String observacion;
        String foto;
        String modulo;
        String unidad_de_negocio;
        String rol;

        pharma_id = c.getString(COLUMNA_PHARMA_ID);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);
        codigo = c.getString(COLUMNA_CODIGO);
        pos_name = c.getString(COLUMNA_POS_NAME);
        usuario = c.getString(COLUMNA_USUARIO);
        latitud = c.getString(COLUMNA_LATITUD);
        longitud = c.getString(COLUMNA_LONGITUD);
        categoria = c.getString(COLUMNA_CATEGORIA);
        fabricante = c.getString(COLUMNA_FABRICANTE);
        marca = c.getString(COLUMNA_MARCA);
        tipo_exhibicion = c.getString(COLUMNA_TIPO_EXHIBICION);
        numero_exhibicion = c.getString(COLUMNA_NUMERO_EXHIBICION);
        contratada = c.getString(COLUMNA_CONTRATADA);
        observacion = c.getString(COLUMNA_OBSERVACION);
        foto = c.getString(COLUMNA_FOTO);
        modulo = c.getString(COLUMNA_MODULO);
        unidad_de_negocio = c.getString(COLUMNA_UNIDAD_DE_NEGOCIO);
        rol = c.getString(COLUMNA_ROL);

        try {

            jObject.put(ContractInsertConvenios.Columnas.PHARMA_ID, pharma_id);
            jObject.put(ContractInsertConvenios.Columnas.FECHA, fecha);
            jObject.put(ContractInsertConvenios.Columnas.HORA, hora);
            jObject.put(ContractInsertConvenios.Columnas.CODIGO, codigo);
            jObject.put(ContractInsertConvenios.Columnas.POS_NAME, pos_name);
            jObject.put(ContractInsertConvenios.Columnas.USUARIO, usuario);
            jObject.put(ContractInsertConvenios.Columnas.LATITUD, latitud);
            jObject.put(ContractInsertConvenios.Columnas.LONGITUD, longitud);
            jObject.put(ContractInsertConvenios.Columnas.CATEGORIA, categoria);
            jObject.put(ContractInsertConvenios.Columnas.FABRICANTE, fabricante);
            jObject.put(ContractInsertConvenios.Columnas.MARCA, marca);
            jObject.put(ContractInsertConvenios.Columnas.TIPO_EXHIBICION, tipo_exhibicion);
            jObject.put(ContractInsertConvenios.Columnas.NUMERO_EXHIBICION, numero_exhibicion);
            jObject.put(ContractInsertConvenios.Columnas.CONTRATADA, contratada);
            jObject.put(ContractInsertConvenios.Columnas.OBSERVACION, observacion);
            jObject.put(ContractInsertConvenios.Columnas.FOTO, foto);
            jObject.put(ContractInsertConvenios.Columnas.MODULO, modulo);
            jObject.put(ContractInsertConvenios.Columnas.UNIDAD_DE_NEGOCIO, unidad_de_negocio);
            jObject.put(ContractInsertConvenios.Columnas.ROL, rol);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }

}
