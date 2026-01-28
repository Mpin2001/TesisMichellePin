package com.luckyecuador.app.bassaApp.Clase;

public class Base_canales_fotos {

    public String id;//YA
    public String pdv;
    public String canal;//YA
    public String activar;//YA


    public Base_canales_fotos() {}

    public Base_canales_fotos(String id, String pdv, String canal, String activar) {
        this.id = id;
        this.pdv = pdv;
        this.canal = canal;
        this.activar = activar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPdv() {
        return pdv;
    }

    public void setPdv(String pdv) {
        this.pdv = pdv;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getActivar() {
        return activar;
    }

    public void setActivar(String activar) {
        this.activar = activar;
    }


}
