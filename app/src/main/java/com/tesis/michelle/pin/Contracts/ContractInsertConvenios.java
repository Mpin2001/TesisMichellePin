package com.tesis.michelle.pin.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.tesis.michelle.pin.Conexion.Constantes;

/**
 * Created by Lucky Ecuador on 15/07/2016.
 */
public class ContractInsertConvenios {
    /**
     * Representación de la tabla a consultar
     */
    public static final String INSERT_CONVENIOS = "insert_convenios";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + INSERT_CONVENIOS;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + INSERT_CONVENIOS;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + INSERT_CONVENIOS);
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
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_CONVENIOS, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_CONVENIOS + "/#", SINGLE_ROW);
    }

    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }
        public final static String PHARMA_ID = "id";
        public final static String FECHA = "fecha";
        public final static String HORA = "hora";
        public final static String CODIGO = "pos_id";
        public final static String POS_NAME = "pos_name";
        public final static String USUARIO = "usuario";
        public final static String LATITUD = "latitud";
        public final static String LONGITUD = "longitud";
        public final static String CATEGORIA = "categoria";
        public final static String FABRICANTE = "fabricante";
        public final static String MARCA = "marca";
        public final static String TIPO_EXHIBICION = "tipo_exhibicion";
        public final static String NUMERO_EXHIBICION = "numero_exhibicion";
        public final static String CONTRATADA = "contratada";
        public final static String OBSERVACION = "observacion";
        public final static String FOTO = "foto";
        public final static String MODULO = "modulo";
        public final static String UNIDAD_DE_NEGOCIO = "unidad_de_negocio";
        public final static String ROL = "rol";
    }

}
