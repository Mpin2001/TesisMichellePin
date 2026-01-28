package com.tesis.michelle.pin.Utilidades;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.tesis.michelle.pin.Contracts.ContractInsertNovedades;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lucky Ecuador on 28/02/2018.
 */

public class UtilidadesNovedades {

    public static final int COLUMNA_PHARMA_ID = 2;
    public static final int COLUMNA_CODIGO = 3;
    public static final int COLUMNA_USUARIO = 4;
    public static final int COLUMNA_SUPERVISOR = 5;
    public static final int COLUMNA_POS_NAME = 6;
    public static final int COLUMNA_TIPO_NOVEDAD = 7;
    public static final int COLUMNA_MARCA = 8;
    public static final int COLUMNA_LOTE = 9;
    public static final int COLUMNA_SKU = 10;
    public static final int COLUMNATIPO = 11;
    public static final int COLUMNA_FECHA_VENCIMIENTO = 12;
    public static final int COLUMNA_FECHA_ELABORACION = 13;
    public static final int COLUMNA_NUMERO_FACTURA = 14;
    public static final int COLUMNA_COMENTARIO_LOTE_ = 15;
    public static final int COLUMNA_COMENTARIO_FACTURA = 16;
    public static final int COLUMNA_COMENTARIO_SKU = 17;
    public static final int COLUMNA_OBSERVACION = 18;
    public static final int COLUMNA_TIPO_IMPLEMENTACION = 19;
    public static final int COLUMNA_MECANICA = 20;
    public static final int COLUMNA_FECHA_INICIO = 21;
    public static final int COLUMNA_AGOTAR_STOCK = 22;
    public static final int COLUMNA_PRECIO_ANTERIOR = 23 ;
    public static final int COLUMNA_PRECIO_PROMOCION = 24;
    public static final int COLUMNA_FOTO = 25;
    public static final int COLUMNA_SKU_CODE = 26;
    public static final int COLUMNA_CANTIDAD = 27;
    public static final int COLUMNA_FOTO_PRODUCTO = 28;
    public static final int COLUMNA_FOTO_FACTURA = 29;
    public static final int COLUMNA_FECHA = 30;
    public static final int COLUMNA_HORA = 31;
    public static final int COLUMNA_CUENTA = 32;


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
        String pos_name;
        String tipo_novedad;
        String marca;
        String lote;
        String sku;
        String tipo;
        String fecha_vencimiento;
        String fecha_elaboracion;
        String numero_factura;
        String comentario_lote;
        String comentario_factura;
        String comentario_sku;
        String observacion;
        String tipo_implementacion;
        String mecanica;
        String fecha_inicio;
        String agotar_stock;
        String precio_anterior;
        String precio_promocion;
        String foto;
        String sku_code;
        String cantidad;
        String foto_producto;
        String foto_factura;
        String fecha;
        String hora;
        String cuenta;

        pharma_id = c.getString(COLUMNA_PHARMA_ID);
        codigo = c.getString(COLUMNA_CODIGO);
        usuario = c.getString(COLUMNA_USUARIO);
        supervisor = c.getString(COLUMNA_SUPERVISOR);
        pos_name = c.getString(COLUMNA_POS_NAME);
        tipo_novedad = c.getString(COLUMNA_TIPO_NOVEDAD);
        marca = c.getString(COLUMNA_MARCA);
        lote = c.getString(COLUMNA_LOTE);
        sku = c.getString(COLUMNA_SKU);
        tipo = c.getString(COLUMNATIPO);
        fecha_vencimiento = c.getString(COLUMNA_FECHA_VENCIMIENTO);
        fecha_elaboracion = c.getString(COLUMNA_FECHA_ELABORACION);
        numero_factura = c.getString(COLUMNA_NUMERO_FACTURA);
        comentario_lote = c.getString(COLUMNA_COMENTARIO_LOTE_);
        comentario_factura = c.getString(COLUMNA_COMENTARIO_FACTURA);
        comentario_sku = c.getString(COLUMNA_COMENTARIO_SKU);
        observacion = c.getString(COLUMNA_OBSERVACION);
        tipo_implementacion = c.getString(COLUMNA_TIPO_IMPLEMENTACION);
        mecanica = c.getString(COLUMNA_MECANICA);
        fecha_inicio = c.getString(COLUMNA_FECHA_INICIO);
        agotar_stock = c.getString(COLUMNA_AGOTAR_STOCK);
        precio_anterior = c.getString(COLUMNA_PRECIO_ANTERIOR);
        precio_promocion = c.getString(COLUMNA_PRECIO_PROMOCION);
        foto = c.getString(COLUMNA_FOTO);
        sku_code = c.getString(COLUMNA_SKU_CODE);
        cantidad = c.getString(COLUMNA_CANTIDAD);
        foto_producto = c.getString(COLUMNA_FOTO_PRODUCTO);
        foto_factura = c.getString(COLUMNA_FOTO_FACTURA);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);
        cuenta = c.getString(COLUMNA_CUENTA);

        try {

            jObject.put(ContractInsertNovedades.Columnas.PHARMA_ID,pharma_id);
            jObject.put(ContractInsertNovedades.Columnas.CODIGO,codigo);
            jObject.put(ContractInsertNovedades.Columnas.USUARIO,usuario);
            jObject.put(ContractInsertNovedades.Columnas.SUPERVISOR,supervisor);
            jObject.put(ContractInsertNovedades.Columnas.POS_NAME,pos_name);
            jObject.put(ContractInsertNovedades.Columnas.TIPO_NOVEDAD,tipo_novedad);
            jObject.put(ContractInsertNovedades.Columnas.MARCA,marca);
            jObject.put(ContractInsertNovedades.Columnas.LOTE,lote);
            jObject.put(ContractInsertNovedades.Columnas.SKU,sku);
            jObject.put(ContractInsertNovedades.Columnas.TIPO,tipo);
            jObject.put(ContractInsertNovedades.Columnas.FECHA_VENCIMIENTO,fecha_vencimiento);
            jObject.put(ContractInsertNovedades.Columnas.FECHA_ELABORACION,fecha_elaboracion);
            jObject.put(ContractInsertNovedades.Columnas.NUMERO_FACTURA,numero_factura);
            jObject.put(ContractInsertNovedades.Columnas.COMENTARIO_LOTE,comentario_lote);
            jObject.put(ContractInsertNovedades.Columnas.COMENTARIO_FACTURA,comentario_factura);
            jObject.put(ContractInsertNovedades.Columnas.COMENTARIO_SKU,comentario_sku);
            jObject.put(ContractInsertNovedades.Columnas.OBSERVACION,observacion);
            jObject.put(ContractInsertNovedades.Columnas.TIPO_IMPLEMENTACION,tipo_implementacion);
            jObject.put(ContractInsertNovedades.Columnas.MECANICA,mecanica);
            jObject.put(ContractInsertNovedades.Columnas.FECHA_INICIO,fecha_inicio);
            jObject.put(ContractInsertNovedades.Columnas.AGOTAR_STOCK,agotar_stock);
            jObject.put(ContractInsertNovedades.Columnas.PRECIO_ANTERIOR,precio_anterior);
            jObject.put(ContractInsertNovedades.Columnas.PRECIO_PROMOCION,precio_promocion);
            jObject.put(ContractInsertNovedades.Columnas.FOTO,foto);
            jObject.put(ContractInsertNovedades.Columnas.SKU_CODE,sku_code);
            jObject.put(ContractInsertNovedades.Columnas.CANTIDAD,cantidad);
            jObject.put(ContractInsertNovedades.Columnas.FOTO_PRODUCTO,foto_producto);
            jObject.put(ContractInsertNovedades.Columnas.FOTO_FACTURA,foto_factura);
            jObject.put(ContractInsertNovedades.Columnas.FECHA,fecha);
            jObject.put(ContractInsertNovedades.Columnas.HORA,hora);
            jObject.put(ContractInsertNovedades.Columnas.CUENTA,cuenta);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }
}
