package com.tesis.michelle.pin.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.tesis.michelle.pin.Conexion.Constantes;


public class ContractInsertTracking {

    /**
     * Representación de la tabla a consultar
     */
    public static final String INSERT_TRACKING = "insert_tracking";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + INSERT_TRACKING;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + INSERT_TRACKING;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + INSERT_TRACKING);
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
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_TRACKING, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_TRACKING + "/#", SINGLE_ROW);
    }

    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public final static String PHARMA_ID = "id"; //ID_SERVIDOR = repositorio_puntoventa
        public final static String FECHA = "fecha";
        public final static String HORA = "hora";
        public final static String CODIGO = "pos_id";
        public final static String LOCAL = "local";
        public final static String USUARIO = "user";
        public final static String LATITUD = "latitud"; //del pdv
        public final static String LONGITUD = "longitud"; //del pdv
        public final static String MECANICA = "mecanica";
        public final static String CATEGORIA = "categoria";
        public final static String DESCRIPCION = "descripcion";
        public final static String VIGENCIA = "vigencia";
        public final static String STATUS_ACTIVIDAD = "status_actividad";
        public final static String COMENTARIO = "comentario";
        public final static String FOTO = "foto";

        public final static String PRECIO_PROMOCION = "ppromo";
        public final static String MATERIAL_POP = "material_pop";
        public final static String IMPLEMENTACION_POP = "implementacion_pop";
        public final static String CUENTA = "cuenta";
        public final static String MODULO = "modulo";
        public final static String UNIDAD_DE_NEGOCIO = "unidad_de_negocio";

    }

}
