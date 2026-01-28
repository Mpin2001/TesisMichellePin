package com.luckyecuador.app.bassaApp.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.luckyecuador.app.bassaApp.Conexion.Constantes;

/**
 * Created by Lucky Ecuador on 19/01/2022.
 */

public class ContractExhibicionSupervisor {

    /**
     * Representación de la tabla a consultar
     */
    public static final String EXHIBICIONSUPERVISOR = "exhibicionsupervisor";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + EXHIBICIONSUPERVISOR;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + EXHIBICIONSUPERVISOR;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + EXHIBICIONSUPERVISOR);
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
        uriMatcher.addURI(Constantes.AUTHORITY, EXHIBICIONSUPERVISOR, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, EXHIBICIONSUPERVISOR + "/#", SINGLE_ROW);
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

        public final static String CP_CODE = "cp_code";
        public final static String CANAL = "canal";
        public final static String SUPERVISOR = "supervisor";
        public final static String EXHIBITION_TOOL = "exhibition_tool";
        public final static String MANUFACTURER = "manufacturer";
        public final static String CATEGORY = "category";
        public final static String SUBCATEGORY = "subcategory";
        public final static String ZONE_EXHIBITION = "zone_exhibition";
        public final static String PERSONALIZATION = "personalization";
        public final static String PRICE_TAG = "price_tag";
        public final static String NUM_EXHIBITIONS = "num_exhibitions";
        public final static String OBSERVATION = "observation";
        public final static String PHOTO = "photo";
        public final static String DATE = "date";
        public final static String VISUAL_ACCESS = "visual_access";
        public final static String PHOTO2 = "photo2";
        public final static String TIPO_HERRAMIENTA = "tipo_herramienta";
        public final static String CAMPANA = "campana";
        public final static String CONVENIO = "convenio";
        public final static String CLASIFICACION = "clasificacion";

        public static final String ESTADO = "estado";
        public static final String ID_REMOTA = "idRemota";
        public final static String PENDIENTE_INSERCION = "pendiente_insercion";

    }

}
