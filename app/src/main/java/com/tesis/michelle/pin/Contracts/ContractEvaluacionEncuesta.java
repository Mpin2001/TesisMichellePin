package com.tesis.michelle.pin.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.tesis.michelle.pin.Conexion.Constantes;


/**
 * Created by Lucky Ecuador on 15/07/2016.
 */
public class ContractEvaluacionEncuesta {
    /**
     * Representación de la tabla a consultar
     */
    public static final String EVALUACION_PROMOTOR = "evaluacion_promotor";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + EVALUACION_PROMOTOR;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + EVALUACION_PROMOTOR;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + EVALUACION_PROMOTOR);
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
        uriMatcher.addURI(Constantes.AUTHORITY, EVALUACION_PROMOTOR, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, EVALUACION_PROMOTOR + "/#", SINGLE_ROW);
    }


    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }
        public final static String NOMBRE_ENCUESTA = "nombre_encuesta";
        public final static String DESCRIPCION = "descripcion";
        public final static String CATEGORIA = "categoria";
        public final static String RE = "re";//subchannel
        public final static String PREGUNTA = "pregunta";
        public final static String TIPO_PREGUNTA = "tipo_pregunta";
        public final static String OPC_A = "opc_a";
        public final static String OPC_B = "opc_b";
        public final static String OPC_C = "opc_c";
        public final static String OPC_D = "opc_d";
        public final static String OPC_E = "opc_e";
        public final static String FOTO = "foto";
        public final static String TIPO_CAMPO = "tipo_campo";
        public final static String PUNTAJE_POR_PREGUNTA = "puntaje_por_pregunta";
        public final static String HABILITADO = "habilitado";
    }

}
