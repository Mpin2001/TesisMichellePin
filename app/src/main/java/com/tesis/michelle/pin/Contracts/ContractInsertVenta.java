package com.tesis.michelle.pin.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.tesis.michelle.pin.Conexion.Constantes;

/**
 * Created by Lucky Ecuador on 23/03/2018.
 */

public class ContractInsertVenta {


    public static final String INSERT_VENTA = "insert_venta";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + INSERT_VENTA;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + INSERT_VENTA;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + INSERT_VENTA);
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
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_VENTA, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_VENTA + "/#", SINGLE_ROW);
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

        public final static String PHARMA_ID = "id";
        public final static String CODIGO = "pos_id";
        public final static String USUARIO = "user";
        public final static String SUPERVISOR = "supervisor";
        public final static String TIPO_FACTURA = "tipo_factura";  //marca
        public final static String TIPO_VENTA = "tipo_venta"; //mpin
       public final static String TOTAL_FACTURA = "valor_total"; //mpin // aqui ira el total de precios de todos los sku en una factura
        public final static String NUMERO_FACTURA = "numero_factura"; //mpin
        public final static String ENTREGO_PROMOCIONAL = "entrego_promocional"; //mpin
        public final static String PROMOCIONAL = "promocional"; //mpin
        public final static String CANT_PROMOCIONAL = "cant_promocional"; //mpin
        public final static String NUM_FACTURA = "num_factura"; // cantidad ingresada
        public final static String MECANICA = "mecanica"; //mpin
        public final static String MONTO_FACTURA = "monto_factura"; // es el pvp
        public final static String TOTAL_STOCK = "total_stock"; // resultado de cada sku
        public final static String FECHA_VENTA = "fecha_venta";
        public final static String CATEGORIA = "categoria";
        public final static String SUBCATEGORIA = "subcategoria";
        public final static String SKU = "sku";
        public final static String POS_NAME = "pos_name";
        public final static String KEY_IMAGE = "foto";
        public final static String FOTO_ADICIONAL = "foto_adicional"; //mpin
        public final static String FOTO_ACTIVIDAD = "foto_actividad";//mpin
        public final static String COMENTARIO_FACTURA = "comentario_factura";//mpin
        public final static String COMENTARIO_ADICIONAL = "comentario_adicional";//mpin
        public final static String COMENTARIO_ACTIVIDAD = "comentario_actividad";//mpin
        public final static String FECHA = "fecha";
        public final static String HORA = "hora";

    }
}
