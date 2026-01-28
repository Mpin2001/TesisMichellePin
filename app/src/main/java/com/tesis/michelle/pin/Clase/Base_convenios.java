package com.tesis.michelle.pin.Clase;

/**
 * Created by Lucky Ecuador on 24/04/2017.
 */

public class Base_convenios {

    public String id;
    public String codigo;
    public String pdv;
    public String categoria;
    public String unidad_negocio;
    public String fabricante;
    public String marca;
    public String tipo_exhibicion;
    public String numero_exhibicion;
    public String formato;
    public String fecha_subida;
    public String enlace;

    private boolean isChecked;


    public String getFecha_subida() {
        return fecha_subida;
    }

    public void setFecha_subida(String fecha_subida) {
        this.fecha_subida = fecha_subida;
    }

    public Base_convenios() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPdv() {
        return pdv;
    }

    public void setPdv(String pdv) {
        this.pdv = pdv;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUnidad_negocio() {
        return unidad_negocio;
    }

    public void setUnidad_negocio(String unidad_negocio) {
        this.unidad_negocio = unidad_negocio;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo_exhibicion() {
        return tipo_exhibicion;
    }

    public void setTipo_exhibicion(String tipo_exhibicion) {
        this.tipo_exhibicion = tipo_exhibicion;
    }

    public String getNumero_exhibicion() {
        return numero_exhibicion;
    }

    public void setNumero_exhibicion(String numero_exhibicion) {
        this.numero_exhibicion = numero_exhibicion;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
