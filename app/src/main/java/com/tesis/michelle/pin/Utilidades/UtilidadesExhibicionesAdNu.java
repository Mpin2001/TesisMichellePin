package com.tesis.michelle.pin.Utilidades;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.tesis.michelle.pin.Contracts.InsertExhibicionesAdNu;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilidadesExhibicionesAdNu {
    public static final int COLUMNA_IDPDV = 2;
    public static final int COLUMNA_USER = 3;
    public static final int COLUMNA_NOMBRE = 4;
    public static final int COLUMNA_SUPERVISOR = 5;
    public static final int COLUMNA_NOMBRE_PDV = 6;
    public static final int COLUMNA_CODE = 7;
    public static final int COLUMNA_TIPO_HERRAMIENTA = 8;
    public static final int COLUMNA_TIPO = 9;
    public static final int COLUMNA_FAB = 10;
    public static final int COLUMNA_CAT = 11;
    public static final int COLUMNA_TIPO_NOVEDAD = 12;
    public static final int COLUMNA_SUBCAT = 13;
    public static final int COLUMNA_ESCORRRECCION = 14;
    public static final int COLUMNA_ZONA = 15;
    public static final int COLUMNA_PERS = 16;
    public static final int COLUMNA_ESTRELLA = 17;
    public static final int COLUMNA_NUMEXH = 18;
    public static final int COLUMNA_OBSERVACION = 19;
    public static final int COLUMNA_OBSERVACION_CORRECCION = 20;
    public static final int COLUMNA_IMAGE = 21;
    public static final int COLUMNA_FECHA = 22;
    public static final int COLUMNA_HORA = 23;
    public static final int COLUMNA_IMEI = 24;
    public static final int COLUMNA_CLASIFICACION_PRIMARIA = 25;
    public static final int COLUMNA_CLASIFICACION_SECUNDARIA = 26;
    public static final int COLUMNA_PUNTO_APOYO = 27;

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
        String user;
        String nombre;
        String supervisor;
        String nombre_pdv;
        String code;
        String tipo_herramienta;
        String tipo;
        String fabricante;
        String cat;
        String tipo_novedad;
        String subcat;
        String escorreccion;
        String zona;
        String pers;
        String estrella;
        String numexh;
        String observacion;
        String obs_correccion;
        String image;
        String fecha;
        String hora;
        String imei;
        String clasificacion_primaria;
        String clasificacion_secundaria;
        String punto_apoyo;

        idpdv = c.getString(COLUMNA_IDPDV);
        user = c.getString(COLUMNA_USER);
        nombre = c.getString(COLUMNA_NOMBRE);
        supervisor = c.getString(COLUMNA_SUPERVISOR);
        nombre_pdv = c.getString(COLUMNA_NOMBRE_PDV);
        code = c.getString(COLUMNA_CODE);
        tipo_herramienta = c.getString(COLUMNA_TIPO_HERRAMIENTA);
        tipo = c.getString(COLUMNA_TIPO);
        fabricante = c.getString(COLUMNA_FAB);
        cat = c.getString(COLUMNA_CAT);
        tipo_novedad = c.getString(COLUMNA_TIPO_NOVEDAD);
        subcat = c.getString(COLUMNA_SUBCAT);
        escorreccion = c.getString(COLUMNA_ESCORRRECCION);
        zona = c.getString(COLUMNA_ZONA);
        pers = c.getString(COLUMNA_PERS);
        estrella = c.getString(COLUMNA_ESTRELLA);
        numexh = c.getString(COLUMNA_NUMEXH);
        observacion = c.getString(COLUMNA_OBSERVACION);
        obs_correccion = c.getString(COLUMNA_OBSERVACION_CORRECCION);
        image = c.getString(COLUMNA_IMAGE);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);
        imei = c.getString(COLUMNA_IMEI);
        clasificacion_primaria = c.getString(COLUMNA_CLASIFICACION_PRIMARIA);
        clasificacion_secundaria = c.getString(COLUMNA_CLASIFICACION_SECUNDARIA);
        punto_apoyo = c.getString(COLUMNA_PUNTO_APOYO);

        try {
            jObject.put(InsertExhibicionesAdNu.Columnas.ID_PDV, idpdv);
            jObject.put(InsertExhibicionesAdNu.Columnas.USUARIO, user);
            jObject.put(InsertExhibicionesAdNu.Columnas.NOMBRE, nombre);
            jObject.put(InsertExhibicionesAdNu.Columnas.SUPERVISOR, supervisor);
            jObject.put(InsertExhibicionesAdNu.Columnas.NOMBRE_PDV, nombre_pdv);
            jObject.put(InsertExhibicionesAdNu.Columnas.CODIGO, code);
            jObject.put(InsertExhibicionesAdNu.Columnas.TIPO_HERRAMIENTA, tipo_herramienta);
            jObject.put(InsertExhibicionesAdNu.Columnas.TIPO, tipo);
            jObject.put(InsertExhibicionesAdNu.Columnas.FABRICANTE, fabricante);
            jObject.put(InsertExhibicionesAdNu.Columnas.CATEGORIA, cat);
            jObject.put(InsertExhibicionesAdNu.Columnas.TIPO_NOVEDAD, tipo_novedad);
            jObject.put(InsertExhibicionesAdNu.Columnas.SUBCAT, subcat);
            jObject.put(InsertExhibicionesAdNu.Columnas.ES_CORRECCION, escorreccion);
            jObject.put(InsertExhibicionesAdNu.Columnas.ZONAEXH, zona);
            jObject.put(InsertExhibicionesAdNu.Columnas.PERSONALIZACION, pers);
            jObject.put(InsertExhibicionesAdNu.Columnas.ESTRELLA, estrella);
            jObject.put(InsertExhibicionesAdNu.Columnas.NUMEROEXH, numexh);
            jObject.put(InsertExhibicionesAdNu.Columnas.OBSERVACION, observacion);
            jObject.put(InsertExhibicionesAdNu.Columnas.OBSERVACION_CORRECCION, obs_correccion);
            jObject.put(InsertExhibicionesAdNu.Columnas.KEY_IMAGE, image);
            jObject.put(InsertExhibicionesAdNu.Columnas.FECHA, fecha);
            jObject.put(InsertExhibicionesAdNu.Columnas.HORA, hora);
            jObject.put(InsertExhibicionesAdNu.Columnas.IMEI, imei);
            jObject.put(InsertExhibicionesAdNu.Columnas.CLASIFICACION_PRIMARIA, clasificacion_primaria);
            jObject.put(InsertExhibicionesAdNu.Columnas.CLASIFICACION_SECUNDARIA, clasificacion_secundaria);
            jObject.put(InsertExhibicionesAdNu.Columnas.PUNTO_APOYO, punto_apoyo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }

}
