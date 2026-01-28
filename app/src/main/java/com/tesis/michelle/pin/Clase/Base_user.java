package com.tesis.michelle.pin.Clase;

public class Base_user {
    public String id;
    public String user;
    public String mercaderista;
    public String device_id;
    public String color;
    public String status;


    public Base_user() {}

    public Base_user(String id, String user, String mercaderista, String device_id, String color, String status) {
        this.id = id;
        this.user = user;
        this.mercaderista = mercaderista;
        this.device_id = device_id;
        this.color = color;
        this.status = status;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMercaderista() {
        return mercaderista;
    }

    public void setMercaderista(String mercaderista) {
        this.mercaderista = mercaderista;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
