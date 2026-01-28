package com.luckyecuador.app.bassaApp.Clase;

public class Base_campos_x_modulos {

    public String id;//YA
    public String modulo;//YA
    public String campo;//YA
    public String obligatorio; //YA
    public String cuenta;//YA

    public Base_campos_x_modulos() {}

    public Base_campos_x_modulos(String id, String modulo, String campo, String obligatorio, String cuenta) {
        this.id = id;
        this.modulo = modulo;
        this.campo = campo;
        this.obligatorio = obligatorio;
        this.cuenta = cuenta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(String obligatorio) {
        this.obligatorio = obligatorio;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
}
