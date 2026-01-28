package com.tesis.michelle.pin.Clase;

public class TrackingInsert {

    public int id;
    public String descripcion;
    public String status;
    public String pop;
    public String comentario;

    public TrackingInsert() {}

    public TrackingInsert(String descripcion) {
        this.descripcion = descripcion;
    }

    public TrackingInsert(int id, String descripcion, String status, String pop, String comentario) {
        this.id = id;
        this.descripcion = descripcion;
        this.status = status;
        this.pop = pop;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
