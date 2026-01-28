package com.tesis.michelle.pin.Clase;

public class Base_tipo_novedades {

    public String id;//YA
    public String tipo_novedad;
    public String cuenta;//YA


    public Base_tipo_novedades() {}

    public Base_tipo_novedades(String id, String tipo_novedad, String cuenta) {
        this.id = id;
        this.tipo_novedad = tipo_novedad;
        this.cuenta = cuenta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo_novedades() {
        return tipo_novedad;
    }

    public void setTipo_novedades(String tipo_novedades) {
        this.tipo_novedad = tipo_novedades;
    }

    public String getTipo_novedad() {
        return tipo_novedad;
    }

    public void setTipo_novedad(String tipo_novedad) {
        this.tipo_novedad = tipo_novedad;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
}
