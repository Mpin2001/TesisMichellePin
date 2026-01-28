package com.luckyecuador.app.bassaApp.Clase;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lucky Ecuador on 24/04/2017.
 * Modificado por Joffre Herrera 10/02/2019
 */

public class BaseEvaluacionPromotor {

    @SerializedName("id")
    public String id;

    @SerializedName("nombre_encuesta")
    public String nombre_encuesta;

    @SerializedName("descripcion")
    public String descripcion;

    @SerializedName("categoria")
    public String categoria;

    @SerializedName("re")
    public String re;

    @SerializedName("pregunta")
    public String pregunta;

    @SerializedName("tipo_pregunta")
    public String tipo_pregunta;

    @SerializedName("opc_a")
    public String opc_a;

    @SerializedName("opc_b")
    public String opc_b;

    @SerializedName("opc_c")
    public String opc_c;

    @SerializedName("opc_d")
    public String opc_d;

    @SerializedName("opc_e")
    public String opc_e;

    @SerializedName("foto")
    public String foto;

    @SerializedName("tipo_campo")
    public String tipo_campo;

    @SerializedName("puntaje_por_pregunta")
    public String puntaje_por_pregunta;

    @SerializedName("habilitado")
    public String habilitado;

    public String getTipo_pregunta() {
        return tipo_pregunta;
    }

    public void setTipo_pregunta(String tipo_pregunta) {
        this.tipo_pregunta = tipo_pregunta;
    }

    public BaseEvaluacionPromotor() {}

    public BaseEvaluacionPromotor(String id, String nombre_encuesta, String descripcion, String categoria, String re, String pregunta, String tipo_pregunta, String opc_a, String opc_b, String opc_c, String opc_d, String opc_e, String foto, String tipo_campo, String puntaje_por_pregunta, String habilitado) {
        this.id = id;
        this.nombre_encuesta = nombre_encuesta;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.re = re;
        this.pregunta = pregunta;
        this.tipo_pregunta = tipo_pregunta;
        this.opc_a = opc_a;
        this.opc_b = opc_b;
        this.opc_c = opc_c;
        this.opc_d = opc_d;
        this.opc_e = opc_e;
        this.foto = foto;
        this.tipo_campo = tipo_campo;
        this.puntaje_por_pregunta = puntaje_por_pregunta;
        this.habilitado = habilitado;
    }

    public String getOpc_a() {
        return opc_a;
    }

    public void setOpc_a(String opc_a) {
        this.opc_a = opc_a;
    }

    public String getOpc_b() {
        return opc_b;
    }

    public void setOpc_b(String opc_b) {
        this.opc_b = opc_b;
    }

    public String getOpc_c() {
        return opc_c;
    }

    public String getOpc_d() {
        return opc_d;
    }

    public void setOpc_d(String opc_d) {
        this.opc_d = opc_d;
    }

    public String getOpc_e() {
        return opc_e;
    }

    public void setOpc_e(String opc_e) {
        this.opc_e = opc_e;
    }

    public void setOpc_c(String opc_c) {
        this.opc_c = opc_c;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTipo_campo() {
        return tipo_campo;
    }

    public void setTipo_campo(String tipo_campo) {
        this.tipo_campo = tipo_campo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPuntaje_por_pregunta() {
        return puntaje_por_pregunta;
    }

    public void setPuntaje_por_pregunta(String puntaje_por_pregunta) {
        this.puntaje_por_pregunta = puntaje_por_pregunta;
    }

    public String getNombre_encuesta() {
        return nombre_encuesta;
    }

    public void setNombre_encuesta(String nombre_encuesta) {
        this.nombre_encuesta = nombre_encuesta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRe() {
        return re;
    }

    public void setRe(String re) {
        this.re = re;
    }

    public String getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

    @Override
    public String toString() {
        return "BaseEvaluacionPromotor{" +
                "id='" + id + '\'' +
                ", nombre_encuesta='" + nombre_encuesta + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", re='" + re + '\'' +
                ", pregunta='" + pregunta + '\'' +
                ", tipo_pregunta='" + tipo_pregunta + '\'' +
                ", opc_a='" + opc_a + '\'' +
                ", opc_b='" + opc_b + '\'' +
                ", opc_c='" + opc_c + '\'' +
                ", foto='" + foto + '\'' +
                ", tipo_campo='" + tipo_campo + '\'' +
                ", puntaje_por_pregunta='" + puntaje_por_pregunta + '\'' +
                ", habilitado='" + habilitado + '\'' +
                '}';
    }
}