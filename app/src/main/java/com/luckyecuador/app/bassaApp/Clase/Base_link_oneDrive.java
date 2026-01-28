package com.luckyecuador.app.bassaApp.Clase;

public class Base_link_oneDrive {
    public String id;
    public String modulo;
    public String link;

    public Base_link_oneDrive() {}

    public Base_link_oneDrive(String id, String modulo, String link) {
        this.id = id;
        this.modulo = modulo;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
