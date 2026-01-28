package com.luckyecuador.app.bassaApp.Clase;

import android.widget.ImageView;

public class InsertConvenios {

    public ImageView ivFoto;
    public String ivComent;


    public InsertConvenios() {}

    public InsertConvenios(ImageView ivFoto, String ivComent ) {
        this.ivFoto = ivFoto;
        this.ivComent = ivComent;

    }

    public String getIvComent() {
        return ivComent;
    }

    public void setIvComent(String ivComent) {
        this.ivComent = ivComent;
    }

    public ImageView getIvFoto() {
        return ivFoto;
    }

    public void setIvFoto(ImageView ivFoto) {
        this.ivFoto = ivFoto;
    }


}
