package com.luckyecuador.app.bassaApp.Clase;

public class ShareElements {

    private String marca;
    private String caras;
    private String razon;

    private String porcentaje;

    public ShareElements() {}

    public ShareElements(String marca, String caras, String razon, String porcentaje) {
        this.marca = marca;
        this.caras = caras;
        this.razon = razon;
        this.porcentaje = porcentaje;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCaras() {
        return caras;
    }

    public void setCaras(String caras) {
        this.caras = caras;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }
}
