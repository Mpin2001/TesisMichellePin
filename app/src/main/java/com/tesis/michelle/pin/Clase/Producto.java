package com.tesis.michelle.pin.Clase;

public class Producto {
    private String id;
    private String sku;
    private String pvc;
    private String pvp;
    private String descuento;

    public Producto(String id, String sku, String pvc, String descuento) {
        this.id = id;
        this.sku = sku;
        this.pvc = pvc;
        this.pvp = pvp;
        this.descuento = descuento;
    }

    public Producto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPvc() {
        return pvc;
    }

    public void setPvc(String pvc) {
        this.pvc = pvc;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id='" + id + '\'' +
                ", sku='" + sku + '\'' +
                ", pvc='" + pvc + '\'' +
                ", descuento='" + descuento + '\'' +
                '}';
    }

    public String getPvp() {
        return pvp;
    }

    public void setPvp(String pvp) {
        this.pvp = pvp;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }
}
