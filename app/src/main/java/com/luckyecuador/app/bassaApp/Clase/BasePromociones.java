package com.luckyecuador.app.bassaApp.Clase;

public class BasePromociones {

    public String id;//YA
    public String canal;//YA
    public String tipo;//YA
    public String cadena;//YA
    public String fabricante;//YA
    public String descripcion;//YA
    public String categoria;//YA
    public String subcategoria;//YA
    public String marca;//YA
    public String sku;//YA
    public String campana;//YA

    public BasePromociones() {}

    public BasePromociones(String id, String canal, String tipo, String cadena, String fabricante, String descripcion, String categoria, String subcategoria, String marca, String sku, String campana) {
        this.id = id;
        this.canal = canal;
        this.tipo = tipo;
        this.cadena = cadena;
        this.fabricante = fabricante;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.marca = marca;
        this.sku = sku;
        this.campana = campana;
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

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCampana() {
        return campana;
    }

    public void setCampana(String campana) {
        this.campana = campana;
    }
}
