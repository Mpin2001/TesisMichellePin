package com.luckyecuador.app.bassaApp.Contracts;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import com.luckyecuador.app.bassaApp.Conexion.Constantes;

/**
 * Created by Lucky Ecuador on 28/02/2018.
 */

public class ContractInsertNovedades {

    public static final String INSERT_NOVEDADES = "insert_novedades";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + Constantes.AUTHORITY + INSERT_NOVEDADES;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + Constantes.AUTHORITY + INSERT_NOVEDADES;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + Constantes.AUTHORITY + "/" + INSERT_NOVEDADES);
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
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_NOVEDADES, ALLROWS);
        uriMatcher.addURI(Constantes.AUTHORITY, INSERT_NOVEDADES + "/#", SINGLE_ROW);
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
        public final static String USUARIO = "usuario";
        public final static String SUPERVISOR = "supervisor";
        public final static String POS_NAME = "pos_name";
        public final static String TIPO_NOVEDAD = "tipo_novedad";
        public final static String MARCA = "marca"; //mpin
        public final static String LOTE = "lote"; //mpin
        public final static String SKU = "sku"; //mpin
        public final static String TIPO = "tipo"; //mpin
        public final static String FECHA_VENCIMIENTO = "fecha_vencimiento"; //mpin
        public static final String FECHA_ELABORACION = "fecha_elaboracion"; //NUEVOOOOO
        public final static String NUMERO_FACTURA = "numero_factura"; //mpin
        public final static String COMENTARIO_LOTE = "comentario_lote"; //mpin
        public final static String COMENTARIO_FACTURA = "comentario_factura"; //mpin
        public final static String COMENTARIO_SKU = "comentario_sku"; //mpin
        public final static String OBSERVACION = "observacion";
        public final static String TIPO_IMPLEMENTACION = "tipo_implementacion"; //mpin
        public final static String MECANICA = "mecanica"; //mpin //promo-no-autorizada
        public final static String FECHA_INICIO = "fecha_inicio"; //mpin //promo-no-autorizada
        public final static String  AGOTAR_STOCK= "agotar_stock"; //mpin //promo-no-autorizada
        public final static String  PRECIO_ANTERIOR= "precio_anterior"; //mpin //promo-no-autorizada
       public final static String  PRECIO_PROMOCION = "precio_promocion"; //mpin //promo-no-autorizada
        public final static String FOTO = "foto"; // se guardara la foto_lote
        public final static String SKU_CODE = "sku_code";
        public final static String CANTIDAD = "cantidad";
        public final static String FOTO_PRODUCTO = "foto_producto"; // se guardar la foto sku
        public final static String FOTO_FACTURA = "foto_factura"; // mpin
        public final static String FECHA = "fecha";
        public final static String HORA = "hora";
        public final static String CUENTA = "cuenta";

    }
}
