package com.tesis.michelle.pin.Utilidades;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.tesis.michelle.pin.Contracts.ContractInsertCodificados;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilidadesCodificados {

    public static final int COLUMNA_PHARMA_ID = 2;
    public static final int COLUMNA_CODIGO = 3;
    public static final int COLUMNA_POS_NAME = 4;
    public static final int COLUMNA_USUARIO = 5;
    public static final int COLUMNA_SUPERVISOR = 6;
    public static final int COLUMNA_FECHA = 7;
    public static final int COLUMNA_HORA = 8;
    public static final int COLUMNA_SECTOR = 9;
    public static final int COLUMNA_CATEGORIA = 10;
    public static final int COLUMNA_SUBCATEGORIA = 11;
    public static final int COLUMNA_PRESENTACION = 12;
    public static final int COLUMNA_FABRICANTE = 13;
    public static final int COLUMNA_BRAND = 14;
    public static final int COLUMNA_CONTENIDO = 15;
    public static final int COLUMNA_VARIANTE = 16;

    public static final int COLUMNA_SKU_CODE = 17;
    public static final int COLUMNA_PRESENCIA = 18;



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
        String pos_name;
        String usuario;
        String supervisor;
        String fecha;
        String hora;
        String sector;
        String categoria;
        String subcategoria;
        String presentacion;
        String fabricante;
        String brand;
        String contenido;
        String variante;
        String sku_code;
        String presencia;



        pharma_id = c.getString(COLUMNA_PHARMA_ID);
        codigo = c.getString(COLUMNA_CODIGO);
        pos_name = c.getString(COLUMNA_POS_NAME);
        usuario = c.getString(COLUMNA_USUARIO);
        supervisor = c.getString(COLUMNA_SUPERVISOR);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);
        sector = c.getString(COLUMNA_SECTOR);
        categoria = c.getString(COLUMNA_CATEGORIA);
        subcategoria = c.getString(COLUMNA_SUBCATEGORIA);
        presentacion = c.getString(COLUMNA_PRESENTACION);
        fabricante = c.getString(COLUMNA_FABRICANTE);
        brand = c.getString(COLUMNA_BRAND);
        contenido = c.getString(COLUMNA_CONTENIDO);
        variante = c.getString(COLUMNA_VARIANTE);
        sku_code = c.getString(COLUMNA_SKU_CODE);
        presencia = c.getString(COLUMNA_PRESENCIA);


        try {
            jObject.put(ContractInsertCodificados.Columnas.PHARMA_ID, pharma_id);
            jObject.put(ContractInsertCodificados.Columnas.CODIGO, codigo);
            jObject.put(ContractInsertCodificados.Columnas.POS_NAME, pos_name);
            jObject.put(ContractInsertCodificados.Columnas.USUARIO, usuario);
            jObject.put(ContractInsertCodificados.Columnas.SUPERVISOR, supervisor);
            jObject.put(ContractInsertCodificados.Columnas.FECHA, fecha);
            jObject.put(ContractInsertCodificados.Columnas.HORA, hora);
            jObject.put(ContractInsertCodificados.Columnas.SECTOR, sector);
            jObject.put(ContractInsertCodificados.Columnas.CATEGORIA, categoria);
            jObject.put(ContractInsertCodificados.Columnas.SUBCATEGORIA, subcategoria);
            jObject.put(ContractInsertCodificados.Columnas.PRESENTACION, presentacion);
            jObject.put(ContractInsertCodificados.Columnas.FABRICANTE, fabricante);
            jObject.put(ContractInsertCodificados.Columnas.BRAND, brand);
            jObject.put(ContractInsertCodificados.Columnas.CONTENIDO, contenido);
            jObject.put(ContractInsertCodificados.Columnas.VARIANTE, variante);
            jObject.put(ContractInsertCodificados.Columnas.SKU_CODE, sku_code);
            jObject.put(ContractInsertCodificados.Columnas.PRESENCIA, presencia);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }

}
