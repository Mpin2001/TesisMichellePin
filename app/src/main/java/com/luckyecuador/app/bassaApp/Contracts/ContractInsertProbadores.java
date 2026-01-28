package com.luckyecuador.app.bassaApp.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.luckyecuador.app.bassaApp.Conexion.Constantes;

/**
 * Created by Lucky Ecuador on 15/07/2016.
 */
public class ContractInsertProbadores {
    /**
     * Representación de la tabla a consultar
     */
    public static final String INSERT_PROBADORES = "insert_probadores";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + INSERT_PROBADORES;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + INSERT_PROBADORES;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + INSERT_PROBADORES);
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
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_PROBADORES, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_PROBADORES + "/#", SINGLE_ROW);
    }

    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }
        public final static String PHARMA_ID = "id";
        public final static String CODIGO = "pos_id";
        public final static String USUARIO = "user";
        public final static String SUPERVISOR = "supervisor";
        public final static String FECHA = "fecha";
        public final static String HORA = "hora";  //
        public final static String TIPO_REGISTRO = "tipo_registro";  //
        public final static String MARCA = "marca";
        public final static String SKU = "sku_code";
        public final static String CANTIDAD = "cantidad";
        public final static String LOTE = "lote";
        public final static String FECHA_VENCIMIENTO = "fecha_vencimiento";
        public final static String FABRICANTE = "fabricante"; // aqui se guardara el fabricante que lit es BASSA
        public final static String FOTO = "foto"; //
        public final static String COMENTARIO = "comentario"; //


      //  public final static String PLATAFORMA = "plataforma";

    }

}
