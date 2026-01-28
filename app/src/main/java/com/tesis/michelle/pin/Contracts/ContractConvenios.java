package com.tesis.michelle.pin.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.tesis.michelle.pin.Conexion.Constantes;


/**
 * Created by Lucky Ecuador on 15/07/2016.
 */
public class ContractConvenios {
    /**
     * Representación de la tabla a consultar
     */
    public static final String CONVENIOS = "convenios";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + CONVENIOS;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + CONVENIOS;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + CONVENIOS);
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
        uriMatcher.addURI(Constantes.AUTHORITY, CONVENIOS, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, CONVENIOS + "/#", SINGLE_ROW);
    }


    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public final static String CODIGO = "codigo";
        public final static String PDV = "pdv";
        public final static String CATEGORIA = "categoria";
        public final static String UNIDAD_NEGOCIO = "unidad_negocio";
        public final static String FABRICANTE = "fabricante";
        public final static String MARCA = "marca";
        public final static String TIPO_EXHIBICION = "tipo_exhibicion";
        public final static String NUMERO_EXHIBICION = "numero_exhibicion";
        public final static String FORMATO = "formato";
        public final static String FECHA_SUBIDA = "fecha_subida";
        public final static String ENLACE = "enlace";
    }

}
