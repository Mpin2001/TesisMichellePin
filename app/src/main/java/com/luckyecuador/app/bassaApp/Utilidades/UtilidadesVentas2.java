package com.luckyecuador.app.bassaApp.Utilidades;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.luckyecuador.app.bassaApp.Contracts.ContractInsertVentas2;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilidadesVentas2 {

    public static final int COLUMNA_PHARMA_ID = 2;
    public static final int COLUMNA_CODIGO = 3;
    public static final int COLUMNA_USUARIO = 4;
    public static final int COLUMNA_SUPERVISOR = 5;
    public static final int COLUMNA_FECHA = 6;
    public static final int COLUMNA_HORA = 7;
    public static final int COLUMNA_NOMBRE_IMPULSADOR = 8;
    public static final int COLUMNA_FECHA_VENTA = 9;
    public static final int COLUMNA_CATEGORIA = 10;
    public static final int COLUMNA_SUBCATEGORIA = 11;
    public static final int COLUMNA_PRESENTACION = 12;
    public static final int COLUMNA_POFERTA = 13;
    public static final int COLUMNA_BRAND = 14;
    /*public static final int COLUMNA_TAMANO = 13;*/

    public static final int COLUMNA_SKU_CODE = 15;
    public static final int COLUMNA_TIPO_VENTA = 16;
    public static final int COLUMNA_STOCK_INICAL = 17;
    public static final int COLUMNA_CANTIDAD = 18;
    public static final int COLUMNA_PREGULAR = 19;
    public static final int COLUMNA_PPROMOCION = 20;

    public static final int COLUMNA_STOCK_FINAL = 21;
    public static final int COLUMNA_VALOR_TOTAL = 22;
    public static final int COLUMNA_TOTAL_FACTURA = 23;
    public static final int COLUMNA_NUMERO_FACTURA = 24;
    public static final int COLUMNA_ENTREGO_PROMOCIONAL = 25;
    public static final int COLUMNA_PROMOCIONAL = 26;
    public static final int COLUMNA_CANT_PROMOCIONAL = 27;
    public static final int COLUMNA_MANUFACTURER = 28;
    public static final int COLUMNA_FOTO = 29;
    public static final int COLUMNA_FOTO_ADICIONAL = 30;
    public static final int COLUMNA_CUENTA = 31;

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
        String fecha;
        String hora;
        String nombre_impulsador;
        String fecha_venta;
        String categoria;
        String subcategoria;
        String presentacion;
        String poferta;
        String brand;
        //String tamano;
        String sku_code;
        String tipo_venta;
        String stock_incial;
        String cantidad;
        String regular_price;
        String promotional_price;
        String stock_final;
        String valor_total;
        String total_factura;
        String numero_factura;
        String entrego_promocional;
        String promocional;
        String cant_promocional;
        String manufacturer;
        String foto;
        String foto_adicional;
        String cuenta;

        pharma_id = c.getString(COLUMNA_PHARMA_ID);
        codigo = c.getString(COLUMNA_CODIGO);
        usuario = c.getString(COLUMNA_USUARIO);
        supervisor = c.getString(COLUMNA_SUPERVISOR);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);
        nombre_impulsador = c.getString(COLUMNA_NOMBRE_IMPULSADOR);
        fecha_venta = c.getString(COLUMNA_FECHA_VENTA);
        categoria = c.getString(COLUMNA_CATEGORIA);
        subcategoria = c.getString(COLUMNA_SUBCATEGORIA);
        presentacion = c.getString(COLUMNA_PRESENTACION);
        poferta = c.getString(COLUMNA_POFERTA);
        brand = c.getString(COLUMNA_BRAND);
        //tamano = c.getString(COLUMNA_TAMANO);
        sku_code = c.getString(COLUMNA_SKU_CODE);
        tipo_venta = c.getString(COLUMNA_TIPO_VENTA);
        stock_incial = c.getString(COLUMNA_STOCK_INICAL);
        cantidad = c.getString(COLUMNA_CANTIDAD);
        regular_price = c.getString(COLUMNA_PREGULAR);
        promotional_price = c.getString(COLUMNA_PPROMOCION);
        stock_final = c.getString(COLUMNA_STOCK_FINAL);
        valor_total = c.getString(COLUMNA_VALOR_TOTAL);
        total_factura = c.getString(COLUMNA_TOTAL_FACTURA);
        numero_factura = c.getString(COLUMNA_NUMERO_FACTURA);
        entrego_promocional = c.getString(COLUMNA_ENTREGO_PROMOCIONAL);
        promocional = c.getString(COLUMNA_PROMOCIONAL);
        cant_promocional = c.getString(COLUMNA_CANT_PROMOCIONAL);
        manufacturer = c.getString(COLUMNA_MANUFACTURER);
        foto = c.getString(COLUMNA_FOTO);
        foto_adicional = c.getString(COLUMNA_FOTO_ADICIONAL);
        cuenta = c.getString(COLUMNA_CUENTA);

        try {

            jObject.put(ContractInsertVentas2.Columnas.PHARMA_ID, pharma_id);
            jObject.put(ContractInsertVentas2.Columnas.CODIGO, codigo);
            jObject.put(ContractInsertVentas2.Columnas.USUARIO, usuario);
            jObject.put(ContractInsertVentas2.Columnas.SUPERVISOR, supervisor);
            jObject.put(ContractInsertVentas2.Columnas.FECHA, fecha);
            jObject.put(ContractInsertVentas2.Columnas.HORA, hora);
            jObject.put(ContractInsertVentas2.Columnas.NOMBRE_IMPULSADOR, nombre_impulsador);
            jObject.put(ContractInsertVentas2.Columnas.FECHA_VENTA, fecha_venta);
            jObject.put(ContractInsertVentas2.Columnas.CATEGORIA, categoria);
            jObject.put(ContractInsertVentas2.Columnas.SUBCATEGORIA, subcategoria);
            jObject.put(ContractInsertVentas2.Columnas.PRESENTACION, presentacion);
            jObject.put(ContractInsertVentas2.Columnas.POFERTA, poferta);
            jObject.put(ContractInsertVentas2.Columnas.BRAND, brand);
            /*jObject.put(ContractInsertVentas2.Columnas.TAMANO, tamano);*/
            jObject.put(ContractInsertVentas2.Columnas.SKU_CODE, sku_code);
            jObject.put(ContractInsertVentas2.Columnas.TIPO_VENTA, tipo_venta);
            jObject.put(ContractInsertVentas2.Columnas.STOCK_INICIAL, stock_incial);
            jObject.put(ContractInsertVentas2.Columnas.CANTIDAD, cantidad);
            jObject.put(ContractInsertVentas2.Columnas.PREGULAR, regular_price);
            jObject.put(ContractInsertVentas2.Columnas.PPROMOCION, promotional_price);
            jObject.put(ContractInsertVentas2.Columnas.STOCK_FINAL, stock_final);
            jObject.put(ContractInsertVentas2.Columnas.VALOR_TOTAL, valor_total);
            jObject.put(ContractInsertVentas2.Columnas.TOTAL_FACTURA, total_factura);
            jObject.put(ContractInsertVentas2.Columnas.NUMERO_FACTURA, numero_factura);
            jObject.put(ContractInsertVentas2.Columnas.ENTREGO_PROMOCIONAL, entrego_promocional);
            jObject.put(ContractInsertVentas2.Columnas.PROMOCIONAL, promocional);
            jObject.put(ContractInsertVentas2.Columnas.CANT_PROMOCIONAL, cant_promocional);
            jObject.put(ContractInsertVentas2.Columnas.MANUFACTURER, manufacturer);
            jObject.put(ContractInsertVentas2.Columnas.FOTO, foto);
            jObject.put(ContractInsertVentas2.Columnas.FOTO_ADICIONAL, foto_adicional);
            jObject.put(ContractInsertVentas2.Columnas.CUENTA, cuenta);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }

}
