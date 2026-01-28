package com.tesis.michelle.pin.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.tesis.michelle.pin.Conexion.Constantes;

/**
 * Created by Lucky Ecuador on 15/07/2016.
 */
public class ContractInsertVentas2 {
    /**
     * Representación de la tabla a consultar
     */
    public static final String INSERT_PRECIOS = "insert_ventas2";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + INSERT_PRECIOS;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + INSERT_PRECIOS;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + INSERT_PRECIOS);
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
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_PRECIOS, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_PRECIOS + "/#", SINGLE_ROW);
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
        public final static String HORA = "hora";
        public final static String NOMBRE_IMPULSADOR = "nombre_impulsador";
        public final static String FECHA_VENTA = "fecha_venta";
        public final static String CATEGORIA = "categoria";
        public final static String SUBCATEGORIA = "subcategoria";
        public final static String PRESENTACION = "presentacion";
        public final static String BRAND = "brand";
        /*public final static String TAMANO = "tamano";*/
        public final static String SKU_CODE = "sku_code";
        public final static String TIPO_VENTA = "tipo_venta";
        public final static String STOCK_INICIAL = "stock_inicial";
        public final static String CANTIDAD = "cantidad";
        public final static String PREGULAR = "regular_price";
        public final static String PPROMOCION = "promotional_price";
        public final static String POFERTA = "ofert_price";
        public final static String STOCK_FINAL = "stock_final";
        public final static String VALOR_TOTAL = "valor_total";
        public final static String TOTAL_FACTURA = "total_factura";
        public final static String NUMERO_FACTURA = "numero_factura";
        public final static String ENTREGO_PROMOCIONAL = "entrego_promocional";
        public final static String PROMOCIONAL = "promocional";
        public final static String CANT_PROMOCIONAL = "cant_promocional";
        public final static String MANUFACTURER = "manufacturer";
        public final static String POS_NAME = "pos_name";
        public final static String FOTO = "foto";
        public final static String FOTO_ADICIONAL = "foto_adicional";
        public final static String CUENTA = "cuenta";
    }

}
