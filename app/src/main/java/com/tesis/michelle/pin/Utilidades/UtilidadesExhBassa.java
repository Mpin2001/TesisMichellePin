package com.tesis.michelle.pin.Utilidades;


import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.tesis.michelle.pin.Contracts.InsertExhBassa;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilidadesExhBassa {

    public static final int COLUMNA_IDPDV = 2;
    public static final int COLUMNA_ID_R_EXH = 3;
    public static final int COLUMNA_SUPERVISOR = 4;
    public static final int COLUMNA_NOMBRE = 5;
    public static final int COLUMNA_SUPERVISOR_EXH = 6;
    public static final int COLUMNA_NOMBRE_PDV = 7;
    public static final int COLUMNA_CODE = 8;
    public static final int COLUMNA_REPORTE_NUEVA = 9;
    public static final int COLUMNA_REPORTE_MANT = 10;
    public static final int COLUMNA_HERRAMIENTA = 11;
    public static final int COLUMNA_TIPO_EXH = 12;
    public static final int COLUMNA_FABRICANTE = 13;
    public static final int COLUMNA_TIPO_HERRAMIENTA = 14;
    public static final int COLUMNA_TIPO_NOVEDAD= 15;
    public static final int COLUMNA_CONVENIO = 16;
    public static final int COLUMNA_ELIMINAR = 17;
    public static final int COLUMNA_SUBCAT = 18;
    public static final int COLUMNA_CAT = 19;
    public static final int COLUMNA_CAMPANA = 20;
    public static final int COLUMNA_OBSERVACION = 21;
    public static final int COLUMNA_CLASIFICACION = 22;
    public static final int COLUMNA_NUMEXH = 23;
    public static final int COLUMNA_RESPUESTA = 24;
    public static final int COLUMNA_IMAGE = 25;
    public static final int COLUMNA_FECHA = 26;
    public static final int COLUMNA_HORA = 27;
    public static final int COLUMNA_PUNTO_APOYO = 28;

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

        String idpdv;
        String id_r_exh;
        String user;
        String nombre;
        String supervisor_exh;
        String nombre_pdv;
        String code;
        String reporte_nueva;
        String reporte_mant;
        String herramienta;
        String tipo;
        String fabricante;
        String tipo_herra;
        String tipo_novedad;
        String convenio;
        String eliminar;
        String subcat;
        String cat;
        String campana;
        String observacion;
        String clasificacion;
        String numexh;
        String respuesta;
        String image;
        String fecha;
        String hora;
        String punto_apoyo;

        idpdv = c.getString(COLUMNA_IDPDV);
        id_r_exh = c.getString(COLUMNA_ID_R_EXH);
        user = c.getString(COLUMNA_SUPERVISOR);
        nombre = c.getString(COLUMNA_NOMBRE);
        supervisor_exh = c.getString(COLUMNA_SUPERVISOR_EXH);
        nombre_pdv = c.getString(COLUMNA_NOMBRE_PDV);
        user = c.getString(COLUMNA_SUPERVISOR);
        code = c.getString(COLUMNA_CODE);
        reporte_nueva = c.getString(COLUMNA_REPORTE_NUEVA);
        reporte_mant = c.getString(COLUMNA_REPORTE_MANT);
        herramienta = c.getString(COLUMNA_HERRAMIENTA);
        tipo = c.getString(COLUMNA_TIPO_EXH);
        fabricante = c.getString(COLUMNA_FABRICANTE);
        tipo_herra = c.getString(COLUMNA_TIPO_HERRAMIENTA);
        tipo_novedad = c.getString(COLUMNA_TIPO_NOVEDAD);
        convenio = c.getString(COLUMNA_CONVENIO);
        eliminar = c.getString(COLUMNA_ELIMINAR);
        subcat = c.getString(COLUMNA_SUBCAT);
        cat = c.getString(COLUMNA_CAT);
        campana = c.getString(COLUMNA_CAMPANA);
        observacion = c.getString(COLUMNA_OBSERVACION);
        clasificacion = c.getString(COLUMNA_CLASIFICACION);
        numexh = c.getString(COLUMNA_NUMEXH);
        respuesta = c.getString(COLUMNA_RESPUESTA);
        image = c.getString(COLUMNA_IMAGE);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);
        punto_apoyo = c.getString(COLUMNA_PUNTO_APOYO);

        try {
            jObject.put(InsertExhBassa.Columnas.ID_PDV, idpdv);
            jObject.put(InsertExhBassa.Columnas.ID_R_EXH, id_r_exh);
            jObject.put(InsertExhBassa.Columnas.USUARIO, user);
            jObject.put(InsertExhBassa.Columnas.NOMBRE, nombre);
            jObject.put(InsertExhBassa.Columnas.SUPERVISOR, supervisor_exh);
            jObject.put(InsertExhBassa.Columnas.NOMBRE_PDV, nombre_pdv);
            jObject.put(InsertExhBassa.Columnas.CODIGO, code);
            jObject.put(InsertExhBassa.Columnas.REPORTE_NUEVA, reporte_nueva);
            jObject.put(InsertExhBassa.Columnas.REPORTE_MANT, reporte_mant);
            jObject.put(InsertExhBassa.Columnas.HERRAMIENTA, herramienta);
            jObject.put(InsertExhBassa.Columnas.TIPO_EXHIBICION, tipo);
            jObject.put(InsertExhBassa.Columnas.FABRICANTE, fabricante);
            jObject.put(InsertExhBassa.Columnas.TIPO_HERRAMIENTA, tipo_herra);
            jObject.put(InsertExhBassa.Columnas.TIPO_NOVEDAD, tipo_novedad);
            jObject.put(InsertExhBassa.Columnas.CONVENIO, convenio);
            jObject.put(InsertExhBassa.Columnas.ELIMINAR, eliminar);
            jObject.put(InsertExhBassa.Columnas.SUBCAT, subcat);
            jObject.put(InsertExhBassa.Columnas.CAT, cat);
            jObject.put(InsertExhBassa.Columnas.CAMPANA, campana);
            jObject.put(InsertExhBassa.Columnas.OBSERVACION, observacion);
            jObject.put(InsertExhBassa.Columnas.CLASIFICACION, clasificacion);
            jObject.put(InsertExhBassa.Columnas.NUMEROEXH, numexh);
            jObject.put(InsertExhBassa.Columnas.RESPUESTA, respuesta);
            jObject.put(InsertExhBassa.Columnas.KEY_IMAGE, image);
            jObject.put(InsertExhBassa.Columnas.FECHA, fecha);
            jObject.put(InsertExhBassa.Columnas.HORA, hora);
            jObject.put(InsertExhBassa.Columnas.PUNTO_APOYO, punto_apoyo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }

}

