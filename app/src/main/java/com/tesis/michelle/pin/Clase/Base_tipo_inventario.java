package com.tesis.michelle.pin.Clase;

public class Base_tipo_inventario {

    public String id;//YA
    public String causales;//YA

    public Base_tipo_inventario() {

    }

    public Base_tipo_inventario(String id, String causales) {
        this.id = id;
        this.causales = causales;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCausales() {
        return causales;
    }

    public void setCausales(String causales) {
        this.causales = causales;
    }
}
