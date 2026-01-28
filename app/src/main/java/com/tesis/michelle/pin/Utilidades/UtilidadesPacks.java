package com.tesis.michelle.pin.Utilidades;

import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.tesis.michelle.pin.Contracts.ContractInsertPacks2;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilidadesPacks {

    public static final int COLUMNA_PHARMA_ID = 2;
    public static final int COLUMNA_CODIGO = 3;
    public static final int COLUMNA_USUARIO = 4;
    public static final int COLUMNA_SUPERVISOR = 5;
    public static final int COLUMNA_FECHA = 6;
    public static final int COLUMNA_HORA = 7;
    public static final int COLUMNA_CATEGORIA = 8;
    public static final int COLUMNA_SUBCATEGORIA = 9;
    public static final int COLUMNA_PRESENTACION = 10;
    public static final int COLUMNA_BRAND = 11;
    public static final int COLUMNA_SKU_CODE = 12;
    public static final int COLUMNA_CATEGORIASEC = 13;
    public static final int COLUMNA_SUBCATEGORIASEC = 14;
    public static final int COLUMNA_PRESENTACIONSEC = 15;
    public static final int COLUMNA_BRANDSEC = 16;
    public static final int COLUMNA_SKUCODESEC = 17;
    public static final int COLUMNA_PVC = 18;
    public static final int COLUMNA_CANTIDAD_ARMADA = 19;
    public static final int COLUMNA_CANTIDAD_ENCONTRADA = 20;
    public static final int COLUMNA_DESCRIPCION = 21;
    public static final int COLUMNA_OBSERVACION = 22;
    public static final int COLUMNA_FOTO = 23;
    public static final int COLUMNA_FOTO_GUIA = 24;
    public static final int COLUMNA_MANUFACTURER = 25;
    public static final int COLUMNA_POS_NAME = 26;

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
        String categoria;
        String subcategoria;
        String presentacion;
        String brand;
        String sku_code;
        String categoriasec;
        String subcategoriasec;
        String presentacionsec;
        String brandsec;
        String skucodesec;
        String pvc;
        String cantidad_armada;
        String cantidad_encontrada;
        String descripcion;
        String observacion;
        String foto;
        String foto_guia;
        String manufacturer;
        String pos_name;

        pharma_id = c.getString(COLUMNA_PHARMA_ID);
        codigo = c.getString(COLUMNA_CODIGO);
        usuario = c.getString(COLUMNA_USUARIO);
        supervisor = c.getString(COLUMNA_SUPERVISOR);
        fecha = c.getString(COLUMNA_FECHA);
        hora = c.getString(COLUMNA_HORA);
        categoria = c.getString(COLUMNA_CATEGORIA);
        subcategoria = c.getString(COLUMNA_SUBCATEGORIA);
        presentacion = c.getString(COLUMNA_PRESENTACION);
        brand = c.getString(COLUMNA_BRAND);
        sku_code = c.getString(COLUMNA_SKU_CODE);
        categoriasec = c.getString(COLUMNA_CATEGORIASEC);
        subcategoriasec = c.getString(COLUMNA_SUBCATEGORIASEC);
        presentacionsec = c.getString(COLUMNA_PRESENTACIONSEC);
        brandsec = c.getString(COLUMNA_BRANDSEC);
        skucodesec = c.getString(COLUMNA_SKUCODESEC);
        pvc = c.getString(COLUMNA_PVC);
        cantidad_armada = c.getString(COLUMNA_CANTIDAD_ARMADA);
        cantidad_encontrada = c.getString(COLUMNA_CANTIDAD_ENCONTRADA);
        descripcion = c.getString(COLUMNA_DESCRIPCION);
        observacion = c.getString(COLUMNA_OBSERVACION);
        foto = c.getString(COLUMNA_FOTO);
        foto_guia = c.getString(COLUMNA_FOTO_GUIA);
        manufacturer = c.getString(COLUMNA_MANUFACTURER);
        pos_name = c.getString(COLUMNA_POS_NAME);


        try {

            jObject.put(ContractInsertPacks2.Columnas.PHARMA_ID, pharma_id);
            jObject.put(ContractInsertPacks2.Columnas.CODIGO, codigo);
            jObject.put(ContractInsertPacks2.Columnas.USUARIO, usuario);
            jObject.put(ContractInsertPacks2.Columnas.SUPERVISOR, supervisor);
            jObject.put(ContractInsertPacks2.Columnas.FECHA, fecha);
            jObject.put(ContractInsertPacks2.Columnas.HORA, hora);
            jObject.put(ContractInsertPacks2.Columnas.CATEGORIA, categoria);
            jObject.put(ContractInsertPacks2.Columnas.SUBCATEGORIA, subcategoria);
            jObject.put(ContractInsertPacks2.Columnas.PRESENTACION, presentacion);
            jObject.put(ContractInsertPacks2.Columnas.BRAND, brand);
            jObject.put(ContractInsertPacks2.Columnas.SKU_CODE, sku_code);
            jObject.put(ContractInsertPacks2.Columnas.CATEGORIASEC, categoriasec);
            jObject.put(ContractInsertPacks2.Columnas.SUBCATEGORIASEC, subcategoriasec);
            jObject.put(ContractInsertPacks2.Columnas.PRESENTACIONSEC, presentacionsec);
            jObject.put(ContractInsertPacks2.Columnas.BRANDSEC, brandsec);
            jObject.put(ContractInsertPacks2.Columnas.SKU_CODESEC, skucodesec);
            jObject.put(ContractInsertPacks2.Columnas.PVC, pvc);
            jObject.put(ContractInsertPacks2.Columnas.CANTIDAD_ARMADA, cantidad_armada);
            jObject.put(ContractInsertPacks2.Columnas.CANTIDAD_ENCONTRADA, cantidad_encontrada);
            jObject.put(ContractInsertPacks2.Columnas.DESCRIPCION, descripcion);
            jObject.put(ContractInsertPacks2.Columnas.OBSERVACION, observacion);
            jObject.put(ContractInsertPacks2.Columnas.FOTO, foto);
            jObject.put(ContractInsertPacks2.Columnas.FOTO_GUIA, foto_guia);
            jObject.put(ContractInsertPacks2.Columnas.MANUFACTURER, manufacturer);
            jObject.put(ContractInsertPacks2.Columnas.POS_NAME, pos_name);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject-", String.valueOf(jObject));

        return jObject;
    }

}
