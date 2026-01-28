package com.tesis.michelle.pin.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.tesis.michelle.pin.Conexion.Constantes;

public class InsertExhBassa {
    /**
     * Representación de la tabla a consultar
     */
    public static final String INSERT_EXHIBICION_COLGATE = "insert_exhibicion_bassa";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + INSERT_EXHIBICION_COLGATE;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + INSERT_EXHIBICION_COLGATE;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + INSERT_EXHIBICION_COLGATE);
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
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_EXHIBICION_COLGATE, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_EXHIBICION_COLGATE + "/#", SINGLE_ROW);
    }


    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public final static String ID_PDV = "id_pdv"; //ID_SERVIDOR = repositorio_puntoventa
        public final static String ID_R_EXH = "id_r_exh";
        public final static String USUARIO = "supervisor";
        public final static String NOMBRE = "nombre";
        public final static String SUPERVISOR = "supervisor_exh";
        public final static String NOMBRE_PDV = "nombre_pdv";
        public final static String CODIGO = "codigo";
        public final static String REPORTE_NUEVA = "reporte_nueva";
        public final static String REPORTE_MANT = "reporte_mant";
        public final static String HERRAMIENTA = "herramienta";
        public final static String TIPO_EXHIBICION = "tipo_exhibicion";
        public final static String FABRICANTE = "fabricante";
        public final static String TIPO_HERRAMIENTA = "tipo_herramienta";
        public final static String TIPO_NOVEDAD = "tipo_novedad";
        public final static String CONVENIO = "convenio";
        public final static String ELIMINAR = "eliminar";
        public final static String SUBCAT = "subcategoria";
        public final static String CAT = "categoria";
        public final static String CAMPANA = "campana";
        public final static String OBSERVACION = "observacion";
        public final static String CLASIFICACION = "clasificacion";
        public final static String NUMEROEXH = "numero_exhibicion";
        public final static String RESPUESTA = "respuesta";

        public final static String FECHA = "fecha";
        public final static String HORA = "hora";
        public final static String KEY_IMAGE = "photo";
        public final static String PUNTO_APOYO = "punto_apoyo";
    }
}
