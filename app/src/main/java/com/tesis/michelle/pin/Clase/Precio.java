package com.tesis.michelle.pin.Clase;

public class Precio {

    private String sku_code;
    private String pvp;
    private String pvc;
    private String p_oferta;
    private boolean tieneDescuento;   // <-- ESTA ES LA QUE TE FALTA


    private String descuento;

    public Precio() {}

    public Precio(String sku_code) {
        this.sku_code = sku_code;
    }

    public Precio(String sku_code, String pvp, String pvc, String p_oferta, String descuento, boolean tieneDescuento) {
        this.sku_code = sku_code;
        this.pvp = pvp;
        this.pvc = pvc;
        this.p_oferta = p_oferta;
        this.descuento = descuento;
        this.tieneDescuento = tieneDescuento;
    }

    public boolean isTieneDescuento() {
        return tieneDescuento;
    }

    public void setTieneDescuento(boolean tieneDescuento) {
        this.tieneDescuento = tieneDescuento;
    }

    public String getSku_code() {
        return sku_code;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
    }


    public String getPvp() {
        return pvp;
    }

    public void setPvp(String pvp) {
        this.pvp = pvp;
    }

    public String getPvc() {
        return pvc;
    }

    public void setPvc(String pvc) {
        this.pvc = pvc;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }



    public String getP_oferta() {
        return p_oferta;
    }

    public void setP_oferta(String p_oferta) {
        this.p_oferta = p_oferta;
    }

    @Override
    public String toString() {
        return "Precio{" +
                "sku_code='" + sku_code + '\'' +
                ", pvp='" + pvp + '\'' +
                ", pvc='" + pvc + '\'' +
                ", p_oferta='" + p_oferta + '\'' +
                ", descuento='" + p_oferta + '\'' +
                '}';
    }
}
