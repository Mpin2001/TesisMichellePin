package com.luckyecuador.app.bassaApp.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.luckyecuador.app.bassaApp.Conexion.Constantes;


public class ContractInsertEvaluacionEncuesta {

    public static final String INSERT_EVALUACION = "insert_evaluacion_encuesta";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + INSERT_EVALUACION;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + INSERT_EVALUACION;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + INSERT_EVALUACION);
    /**
     * Comparador de URIs de contenido
     */
    public static final UriMatcher uriMatcher;
    /**
     * Código para URIs de multiples registros
     */
    public static final int ALLROWS = 1;
    /**
     * Código para URIS de un solo registro
     */
    public static final int SINGLE_ROW = 2;


    // Asignación de URIs
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_EVALUACION, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_EVALUACION + "/#", SINGLE_ROW);
    }


    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public final static String CODIGO = "codigo";
        public final static String USUARIO = "usuario";
        public final static String CIUDAD = "ciudad";
        public final static String LOCAL= "local";
        public final static String GESTOR_ASIGNADO = "gestor_asignado";
        public final static String NOMBRE_ENCUESTA = "nombre_encuesta";
        public final static String CATEGORIA = "categoria";
        public final static String RE = "re";
        public final static String PREGUNTA = "pregunta";
        public final static String TIPO_PREGUNTA = "tipo_pregunta";
        public final static String RESPUESTA = "respuesta";
        public final static String FOTO_PREGUNTA = "foto_pregunta";
        public final static String COMENTARIO_PREGUNTA = "comentario_pregunta";
        public final static String FOTO_FACHADA = "foto_fachada";
        public final static String CALIFICACION = "calificacion";
        public final static String CALIFICACION_INDIVIDUAL = "calificacion_individual";
        public final static String CALIFICACION_TOTAL = "calificacion_total";
        public final static String SATISFACCION = "satisfaccion";
        public final static String COMENTARIO = "comentario";
        public final static String APOYO = "apoyo";
        public final static String SUPERVISOR = "supervisor";
        public final static String FECHA = "fecha";
        public final static String HORA = "hora";

    }

}
