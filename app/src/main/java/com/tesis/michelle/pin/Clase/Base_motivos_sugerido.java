package com.tesis.michelle.pin.Clase;

public class Base_motivos_sugerido {
    public String id;
    public String canal;
    public String motivo;
    public String foto;
    public Boolean isSelected = false;


    public Base_motivos_sugerido() {}

    public Base_motivos_sugerido(String id, String canal, String motivo, String foto, Boolean isSelected) {
        this.id = id;
        this.canal = canal;
        this.motivo = motivo;
        this.foto = foto;
        this.isSelected = isSelected;


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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
