package com.luckyecuador.app.bassaApp.Clase;

public class Flooring {

    private String sku_code;
    private String inventario;
    private String fecha_caducidad;

    public Flooring() {}

    public Flooring(String sku_code) {
        this.sku_code = sku_code;
    }

    public Flooring(String sku_code, String inventario, String fecha_caducidad) {
        this.sku_code = sku_code;
        this.inventario = inventario;
        this.fecha_caducidad = fecha_caducidad;
    }

    public String getSku_code() {
        return sku_code;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
    }

    public String getInventario() {
        return inventario;
    }

    public void setInventario(String inventario) {
        this.inventario = inventario;
    }

    public String getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(String fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }
}
