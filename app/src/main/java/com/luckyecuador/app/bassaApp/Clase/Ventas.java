package com.luckyecuador.app.bassaApp.Clase;

public class Ventas {

    private String sku;
    private String pvp;
    private String pvc;
    private String tipo_venta;



    private String tipo_factura;
    private String cantidad;
    private String precio_unitario;
    private String valor_total;

    public Ventas() {}

    public Ventas(String sku) {
        this.sku = sku;
    }

    public Ventas(String sku, String pvp, String pvc,String tipo_venta, String tipo_factura,String cantidad, String precio_unitario, String valor_total) {
        this.sku = sku;
        this.pvp = pvp;
        this.pvc = pvc;
        this.tipo_venta = tipo_venta;
        this.tipo_factura = tipo_factura;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.valor_total = valor_total;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPvp() {
        return pvp;
    }

    public void setPvp(String pvp) {
        this.pvp = pvp;
    }

    public String getPvc() {
        return pvc;
    }

    public void setPvc(String pvc) {
        this.pvc = pvc;
    }

    public String getTipo_venta() {
        return tipo_venta;
    }

    public void setTipo_venta(String tipo_venta) {
        this.tipo_venta = tipo_venta;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(String precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public String getValor_total() {
        return valor_total;
    }

    public void setValor_total(String valor_total) {
        this.valor_total = valor_total;
    }

    public String getTipo_factura() {
        return tipo_factura;
    }

    public void setTipo_factura(String tipo_factura) {
        this.tipo_factura = tipo_factura;
    }
}



