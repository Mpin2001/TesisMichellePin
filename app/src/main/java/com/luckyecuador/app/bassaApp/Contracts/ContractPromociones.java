package com.luckyecuador.app.bassaApp.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.luckyecuador.app.bassaApp.Conexion.Constantes;


/**
 * Created by Lucky Ecuador on 15/07/2016.
 */
public class ContractPromociones {
    /**
     * Representación de la tabla a consultar
     */
    public static final String PROMOCIONES = "repo_promociones";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + PROMOCIONES;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + PROMOCIONES;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + PROMOCIONES);
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
        uriMatcher.addURI(Constantes.AUTHORITY, PROMOCIONES, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, PROMOCIONES + "/#", SINGLE_ROW);
    }


    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public final static String CANAL = "canal";
        public final static String TIPO = "tipo";
        public final static String CADENA = "cadena";
        public final static String FABRICANTE = "fabricante";
        public final static String DESCRIPCION = "descripcion";
        public final static String CATEGORIA = "categoria";
        public final static String SUBCATEGORIA = "subcategoria";
        public final static String MARCA = "marca";
        public final static String SKU = "sku";
        public final static String CAMPANA = "campana";
    }

}
