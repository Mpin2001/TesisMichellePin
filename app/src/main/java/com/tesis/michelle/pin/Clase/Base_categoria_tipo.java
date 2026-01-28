package com.tesis.michelle.pin.Clase;

public class Base_categoria_tipo {
    public String id;
    public String canal;
    public String tipo;


    public Base_categoria_tipo(String id, String canal, String tipo) {
        this.id = id;
        this.canal = canal;
        this.tipo = tipo;
    }

    public Base_categoria_tipo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
