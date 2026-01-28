package com.luckyecuador.app.bassaApp.Utilidades;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.luckyecuador.app.bassaApp.Contracts.ContractInsertVenta;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lucky Ecuador on 23/03/2018.
 */

public class UtilidadesVenta {

    public static final int COLUMNA_PHARMA_ID = 2;
    public static final int COLUMNA_CODIGO = 3;
    public static final int COLUMNA_USUARIO = 4;
    public static final int COLUMNA_SUPERVISOR = 5;
    public static final int COLUMNA_CATEGORIA = 6;
    public static final int COLUMNA_SUBCATEGORIA = 7;
    public static final int COLUMNA_SKU = 8;
    public static final int COLUMNA_POS_NAME = 9;
    public static final int COLUMNA_TIPO_FACTURA = 10;
    public static final int COLUMNA_TIPO_VENTA = 11;
    public static final int COLUMNA_TOTAL_FACTURA = 12;
    public static final int COLUMNA_NUMERO_FACTURA = 13;
    public static final int COLUMNA_ENTREGO_PROMOCIONAL = 14;
    public static final int COLUMNA_PROMOCIONAL = 15;
    public static final int COLUMNA_CANT_PROMOCIONAL = 16;
    public static final int COLUMNA_NUM_FACTURA = 17;
    public static final int COLUMNA_MECANICA= 18;
    public static final int COLUMNA_MONTO_FACTURA = 19;
    public static final int COLUMNA_TOTAL_STOCK = 20;
    public static final int COLUMNA_FECHA_VENTA = 21;

    public static final int KEY_IMAGE = 22;
    public static final int COLUMNA_FOTO_ADICIONAL = 23;
    public static final int COLUMNA_FOTO_ACTIVIDAD = 24;
    public static final int COLUMNA_COMENTARIO_FACTURA= 25;
    public static final int COLUMNA_COMENTARIO_ADICIONAL = 26;
    public static final int COLUMNA_COMENTARIO_ACTIVIDAD = 27;
    public static final int COLUMNA_FECHA = 28;
    public static final int COLUMNA_HORA = 29;

    /**
     * Determina si la aplicación corre en versiones superiores o iguales
     * a Android LOLLIPOP
     *
     * @return booleano de confirmación
     */
    public static boolean materialDesign() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Copia los datos de un gasto almacenados en un cursor hacia un
     * JSONObject
     *
     * @param c cursor
     * @return objeto jason
     */
    public static JSONObject deCursorAJSONObject(Cursor c) {
        JSONObject jObject = new JSONObject();

         String pharma_id;
         String codigo;
         String usuario;
         String supervisor;
         String categoria;
         String subcategoria;
         String sku;
         String pos_name;
         String tipo_factura;
         String tipo_venta;
         String total_factura;
         String numero_factura;
         String entrego_promocional;
         String promocional;
         String cant_promocional;
         String num_factura;
         String mecanica;
         String monto_factura;
         String total_stock;
         String fecha_venta;
         String image;
         String foto_adicional;
         String foto_actividad;
         String comentario_factura;
         String comentario_adicional;
         String comentario_actividad;
         String fecha;
         String hora;


        pharma_id = c.getString(COLUMNA_PHARMA_ID);
        codigo = c.getString(COLUMNA_CODIGO);
        usuario = c.getString(COLUMNA_USUARIO);
        supervisor = c.getString(COLUMNA_SUPERVISOR);
        categoria = c.getString(COLUMNA_CATEGORIA);
        subcategoria = c.getString(COLUMNA_SUBCATEGORIA);
        sku = c.getString(COLUMNA_SKU);
        pos_name = c.getString(COLUMNA_POS_NAME);
        tipo_factura = c.getString(COLUMNA_TIPO_FACTURA);
        tipo_venta= c.getString(COLUMNA_TIPO_VENTA);
        total_factura= c.getString(COLUMNA_TOTAL_FACTURA);
        numero_factura= c.getString(COLUMNA_NUMERO_FACTURA);
        entrego_promocional= c.getString(COLUMNA_ENTREGO_PROMOCIONAL);
        promocional= c.getString(COLUMNA_PROMOCIONAL);
        cant_promocional= c.getString(COLUMNA_CANT_PROMOCIONAL);
        num_factura = c.getString(COLUMNA_NUM_FACTURA);
        mecanica = c.getString(COLUMNA_MECANICA);
        monto_factura = c.getString(COLUMNA_MONTO_FACTURA);
        total_stock = c.getString(COLUMNA_TOTAL_STOCK);
        fecha_venta = c.getString(COLUMNA_FECHA_VENTA);
        image = c.getString(KEY_IMAGE);
        foto_adicional = c.getString(COLUMNA_FOTO_ADICIONAL);
        foto_actividad = c.getString(COLUMNA_FOTO_ACTIVIDAD);
        comentario_factura = c.getString(COLUMNA_COMENTARIO_FACTURA);
        comentario_adicional = c.getString(COLUMNA_COMENTARIO_ADICIONAL);
        comentario_actividad = c.getString(COLUMNA_COMENTARIO_ACTIVIDAD);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);

        try {
            jObject.put(ContractInsertVenta.Columnas.PHARMA_ID, pharma_id);
            jObject.put(ContractInsertVenta.Columnas.CODIGO, codigo);
            jObject.put(ContractInsertVenta.Columnas.USUARIO, usuario);
            jObject.put(ContractInsertVenta.Columnas.SUPERVISOR, supervisor);
            jObject.put(ContractInsertVenta.Columnas.CATEGORIA, categoria);
            jObject.put(ContractInsertVenta.Columnas.SUBCATEGORIA, subcategoria);
            jObject.put(ContractInsertVenta.Columnas.SKU, sku);
            jObject.put(ContractInsertVenta.Columnas.POS_NAME, pos_name);
            jObject.put(ContractInsertVenta.Columnas.TIPO_FACTURA, tipo_factura);
            jObject.put(ContractInsertVenta.Columnas.TIPO_VENTA, tipo_venta);
            jObject.put(ContractInsertVenta.Columnas.TOTAL_FACTURA, total_factura);
            jObject.put(ContractInsertVenta.Columnas.NUMERO_FACTURA, numero_factura);
            jObject.put(ContractInsertVenta.Columnas.ENTREGO_PROMOCIONAL, entrego_promocional);
            jObject.put(ContractInsertVenta.Columnas.PROMOCIONAL, promocional);
            jObject.put(ContractInsertVenta.Columnas.CANT_PROMOCIONAL, cant_promocional);
            jObject.put(ContractInsertVenta.Columnas.NUM_FACTURA, num_factura);
            jObject.put(ContractInsertVenta.Columnas.MECANICA, mecanica);
            jObject.put(ContractInsertVenta.Columnas.MONTO_FACTURA, monto_factura);
            jObject.put(ContractInsertVenta.Columnas.TOTAL_STOCK, total_stock);
            jObject.put(ContractInsertVenta.Columnas.FECHA_VENTA, fecha_venta);
            jObject.put(ContractInsertVenta.Columnas.KEY_IMAGE, image);
            jObject.put(ContractInsertVenta.Columnas.FOTO_ADICIONAL, foto_adicional);
            jObject.put(ContractInsertVenta.Columnas.FOTO_ACTIVIDAD, foto_actividad);
            jObject.put(ContractInsertVenta.Columnas.COMENTARIO_FACTURA,comentario_factura );
            jObject.put(ContractInsertVenta.Columnas.COMENTARIO_ADICIONAL, comentario_adicional);
            jObject.put(ContractInsertVenta.Columnas.COMENTARIO_ACTIVIDAD, comentario_actividad);
            jObject.put(ContractInsertVenta.Columnas.FECHA, fecha);
            jObject.put(ContractInsertVenta.Columnas.HORA, hora);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }
}
