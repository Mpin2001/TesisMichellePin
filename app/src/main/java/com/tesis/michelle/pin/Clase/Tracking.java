package com.tesis.michelle.pin.Clase;

public class Tracking {
    public String id;
    public String customer;
    public String cuentas;
    public String mecanica;
    public String categoria;
    public String subcategoria;
    public String marca;
    public String descripcion;
    public String precio_promocion;
    public String vigencia;
    public String material_pop;

    public Tracking() {}

    public Tracking(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tracking(String id, String customer, String cuentas, String mecanica, String categoria, String subcategoria, String marca, String descripcion,
                    String precio_promocion, String vigencia, String material_pop) {
        this.id = id;
        this.customer = customer;
        this.cuentas = cuentas;
        this.mecanica = mecanica;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio_promocion = precio_promocion;
        this.vigencia = vigencia;
        this.material_pop = material_pop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCuentas() {
        return cuentas;
    }

    public void setCuentas(String cuentas) {
        this.cuentas = cuentas;
    }

    public String getMecanica() {
        return mecanica;
    }

    public void setMecanica(String mecanica) {
        this.mecanica = mecanica;
    }

    public String getCategoria() {
        return categoria;
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

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio_promocion() {
        return precio_promocion;
    }

    public void setPrecio_promocion(String precio_promocion) {
        this.precio_promocion = precio_promocion;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getMaterial_pop() {
        return material_pop;
    }

    public void setMaterial_pop(String material_pop) {
        this.material_pop = material_pop;
    }
}
