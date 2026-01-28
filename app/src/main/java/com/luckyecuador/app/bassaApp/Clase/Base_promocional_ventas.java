package com.luckyecuador.app.bassaApp.Clase;

public class Base_promocional_ventas {

    public String id;//YA
    public String cadena;//YA
    public String promocional;//YA
    public String cuenta;//YA

    public Base_promocional_ventas() {}

    public Base_promocional_ventas(String id, String cadena, String promocional, String cuenta) {
        this.id = id;
        this.cadena = cadena;
        this.promocional = promocional;
        this.cuenta = cuenta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getPromocional() {
        return promocional;
    }

    public void setPromocional(String promocional) {
        this.promocional = promocional;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
}
