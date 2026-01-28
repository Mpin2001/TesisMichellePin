package com.luckyecuador.app.bassaApp.Conexion;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

public class ContractTipoInventario {
    /**
     * Representación de la tabla a consultar
     */

    public static final String TIPOINVENTARIO = "repo_tipo_inventario";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + TIPOINVENTARIO;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + TIPOINVENTARIO;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + TIPOINVENTARIO);
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
        uriMatcher.addURI(Constantes.AUTHORITY, TIPOINVENTARIO, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, TIPOINVENTARIO + "/#", SINGLE_ROW);
    }


    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public final static String CAUSALES = "causales";

    }
}
