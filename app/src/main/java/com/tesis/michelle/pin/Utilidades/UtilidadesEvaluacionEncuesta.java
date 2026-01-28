package com.tesis.michelle.pin.Utilidades;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.tesis.michelle.pin.Contracts.ContractInsertEvaluacionEncuesta;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilidadesEvaluacionEncuesta {

    public static final int COLUMNA_CODIGO = 2;
    public static final int COLUMNA_USUARIO = 3;
    public static final int COLUMNA_CIUDAD = 4;
    public static final int COLUMNA_LOCAL = 5;
    public static final int COLUMNA_GESTOR_ASIGNADO = 6;
    public static final int COLUMNA_NOMBRE_ENCUESTA = 7;
    public static final int COLUMNA_CATEGORIA = 8;
    public static final int COLUMNA_RE = 9;
    public static final int COLUMNA_PREGUNTA = 10;
    public static final int COLUMNA_TIPO_PREGUNTA = 11;
    public static final int COLUMNA_RESPUESTA = 12;
    public static final int COLUMNA_FOTO_PREGUNTA = 13;
    public static final int COLUMNA_COMENTARIO_PREGUNTA = 14;
    public static final int COLUMNA_FOTO_FACHADA = 15;
    public static final int COLUMNA_CALIFICACION = 16;
    public static final int COLUMNA_CALIFICACION_INDIVIDUAL = 17;
    public static final int COLUMNA_CALIFICACION_TOTAL = 18;
    public static final int COLUMNA_SATISFACCION = 19;
    public static final int COLUMNA_COMENTARIO = 20;
    public static final int COLUMNA_APOYO = 21;
    public static final int COLUMNA_SUPERVISOR = 22;
    public static final int COLUMNA_FECHA = 23;
    public static final int COLUMNA_HORA = 24;

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

        String codigo;
        String usuario;
        String ciudad;
        String local;
        String gestor_asignado;
        String nombre_encuesta;
        String categoria;
        String re;
        String pregunta;
        String tipo_pregunta;
        String respuesta;
        String foto_pregunta;
        String comentario_pregunta;
        String foto_fachada;
        String calificacion;
        String calificacion_individual;
        String calificacion_total;
        String satisfaccion;
        String comentario;
        String apoyo;
        String supervisor;
        String fecha;
        String hora;

        codigo = c.getString(COLUMNA_CODIGO);
        usuario = c.getString(COLUMNA_USUARIO);
        ciudad = c.getString(COLUMNA_CIUDAD);
        local = c.getString(COLUMNA_LOCAL);
        gestor_asignado = c.getString(COLUMNA_GESTOR_ASIGNADO);
        nombre_encuesta = c.getString(COLUMNA_NOMBRE_ENCUESTA);
        categoria = c.getString(COLUMNA_CATEGORIA);
        re = c.getString(COLUMNA_RE);
        pregunta = c.getString(COLUMNA_PREGUNTA);
        tipo_pregunta = c.getString(COLUMNA_TIPO_PREGUNTA);
        respuesta = c.getString(COLUMNA_RESPUESTA);
        foto_pregunta = c.getString(COLUMNA_FOTO_PREGUNTA);
        comentario_pregunta = c.getString(COLUMNA_COMENTARIO_PREGUNTA);
        foto_fachada = c.getString(COLUMNA_FOTO_FACHADA);
        calificacion = c.getString(COLUMNA_CALIFICACION);
        calificacion_individual = c.getString(COLUMNA_CALIFICACION_INDIVIDUAL);
        calificacion_total = c.getString(COLUMNA_CALIFICACION_TOTAL);
        satisfaccion = c.getString(COLUMNA_SATISFACCION);
        comentario = c.getString(COLUMNA_COMENTARIO);
        apoyo = c.getString(COLUMNA_APOYO);
        supervisor = c.getString(COLUMNA_SUPERVISOR);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);

        try {

            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.CODIGO, codigo);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.USUARIO, usuario);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.CIUDAD, ciudad);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.LOCAL, local);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.GESTOR_ASIGNADO, gestor_asignado);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.NOMBRE_ENCUESTA, nombre_encuesta);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.CATEGORIA, categoria);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.RE, re);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.PREGUNTA, pregunta);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.TIPO_PREGUNTA, tipo_pregunta);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.RESPUESTA, respuesta);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.FOTO_PREGUNTA, foto_pregunta);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.COMENTARIO_PREGUNTA, comentario_pregunta);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.FOTO_FACHADA, foto_fachada);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.CALIFICACION, calificacion);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.CALIFICACION_INDIVIDUAL, calificacion_individual);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.CALIFICACION_TOTAL, calificacion_total);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.SATISFACCION, satisfaccion);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.COMENTARIO, comentario);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.APOYO, apoyo);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.SUPERVISOR, supervisor);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.FECHA, fecha);
            jObject.put(ContractInsertEvaluacionEncuesta.Columnas.HORA, hora);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }

}
