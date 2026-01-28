package com.tesis.michelle.pin.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.tesis.michelle.pin.Conexion.Constantes;

/**
 * Created by Lucky Ecuador on 15/07/2016.
 */
public class ContractExhibicion {
    /**
     * Representación de la tabla a consultar
     */
    public static final String TIPOEXH = "tipoexhibicion";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + TIPOEXH;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + TIPOEXH;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + TIPOEXH);
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
        uriMatcher.addURI(Constantes.AUTHORITY, TIPOEXH, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, TIPOEXH + "/#", SINGLE_ROW);
    }

    // Valores para la columna ESTADO
    public static final int ESTADO_OK = 0;
    public static final int ESTADO_SYNC = 1;


    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public final static String RE = "re";
        public final static String TIPO_HERRAMIENTA = "tipo_herramienta";

        public final static String FABRICANTE = "fabricante";

        //en el contract primero se declara
        public final static String CATEGORIA = "categoria";
        public final static String MARCA = "marca";
        public final static String TIPO = "tipo";

        public final static String SUBCAT = "subcategoria";
        public final static String ES_CORRECCION = "es_correccion";

        public static final String ESTADO = "estado";
        public static final String ID_REMOTA = "idRemota";
        public final static String PENDIENTE_INSERCION = "pendiente_insercion";

    }

}
