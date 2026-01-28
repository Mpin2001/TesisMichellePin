package com.luckyecuador.app.bassaApp.Clase;

public class Base_Modulos_Roles {

    public String id;//YA
   // public String canal;//YA
    public String rol;//YA
    public String modulo;//YA

    public Base_Modulos_Roles() {}

    public Base_Modulos_Roles(String id, String rol, String modulo) {
        this.id = id;
     //   this.canal = canal;
       this.rol = rol;
       this.modulo = modulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   // public String getCanal() {
    //    return canal;
    //}

   // public void setCanal(String canal) {
    //    this.canal = canal;
   // }

    public String getTipo() {
        return rol;
    }

    public void setTipo(String tipo) {
        this.rol = rol;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
    // public String getDescripcion() {
    //    return descripcion;
   // }

  //  public void setDescripcion(String descripcion) {
  //     this.descripcion = descripcion;
  //  }
}
