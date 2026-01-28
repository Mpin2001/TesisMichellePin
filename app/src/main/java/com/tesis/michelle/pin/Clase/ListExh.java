package com.tesis.michelle.pin.Clase;

public class ListExh {

    public String subcategoria;
    public String numExh;

    public ListExh() {}

    public ListExh(String subcategoria, String numExh) {
        this.subcategoria = subcategoria;
        this.numExh = numExh;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getNumExh() {
        return numExh;
    }

    public void setNumExh(String numExh) {
        this.numExh = numExh;
    }

}