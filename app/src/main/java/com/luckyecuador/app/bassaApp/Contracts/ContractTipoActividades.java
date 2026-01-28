package com.luckyecuador.app.bassaApp.Contracts;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.luckyecuador.app.bassaApp.Conexion.Constantes;

public class ContractTipoActividades {

   // public static final String LOG = "logUser";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     *
     *
     */

    public final static String TIPO_ACTIVIDADES = "repo_tipo_actividades";
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + TIPO_ACTIVIDADES;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + TIPO_ACTIVIDADES;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + TIPO_ACTIVIDADES);
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
        uriMatcher.addURI(Constantes.AUTHORITY, TIPO_ACTIVIDADES, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, TIPO_ACTIVIDADES + "/#", SINGLE_ROW);
    }

    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

       // public final static String CANAL = "canal";
        public final static String TIPO = "tipo_actividad";
        // public final static String DESCRIPCION = "descripcion";
       // public final static String CUENTA = "cuenta";
    }
}