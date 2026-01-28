package com.luckyecuador.app.bassaApp.Clase;

public class Base_productos_novedades {

    public String id;//YA
    public String codigo_pdv;
    public String sku;
    public String cuenta;//YA


    public Base_productos_novedades() {}

    public Base_productos_novedades(String id, String codigo_pdv, String sku, String cuenta) {
        this.id = id;
        this.codigo_pdv = codigo_pdv;
        this.sku = sku;
        this.cuenta = cuenta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo_pdv() {
        return codigo_pdv;
    }

    public void setCodigo_pdv(String codigo_pdv) {
        this.codigo_pdv = codigo_pdv;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
}
