package com.example.angel.mypes;

/**
 * Created by angel on 20/02/15.
 */
public class Comentario {

    private String opinion;
    private String fecha;
    private String usuario;

    public Comentario(String _opinion,String _fecha,String _usuario){
        opinion=_opinion;
        fecha=_fecha;
        usuario = _usuario;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getOpinion() {
        return opinion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getUsuario() {
        return usuario;
    }
}
