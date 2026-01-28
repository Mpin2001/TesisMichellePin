package com.luckyecuador.app.bassaApp.Clase;

public class InsertEvaluacionDemo {

    private String categoria;
    private String pregunta;
    private String respuesta;
    private String foto;
    private String comentario;
    private String tipo_pregunta;
    private String puntaje;

    public InsertEvaluacionDemo() {}

    public InsertEvaluacionDemo(String categoria, String pregunta, String respuesta, String foto, String comentario, String tipo_pregunta, String puntaje) {
        this.categoria = categoria;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.foto = foto;
        this.comentario = comentario;
        this.tipo_pregunta = tipo_pregunta;
        this.puntaje = puntaje;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getTipo_pregunta() {
        return tipo_pregunta;
    }

    public void setTipo_pregunta(String tipo_pregunta) {
        this.tipo_pregunta = tipo_pregunta;
    }

    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }
}
