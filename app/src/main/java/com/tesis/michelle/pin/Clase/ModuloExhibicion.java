package com.tesis.michelle.pin.Clase;

public class ModuloExhibicion {

    public String eliminar;
    public String subcategoria;
    public String respuesta;

    public ModuloExhibicion() {}

    public ModuloExhibicion(String eliminar, String subcategoria, String respuesta) {
        this.eliminar = eliminar;
        this.subcategoria = subcategoria;
        this.respuesta = respuesta;
    }

    public String getEliminar() {
        return eliminar;
    }

    public void setEliminar(String eliminar) {
        this.eliminar = eliminar;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}