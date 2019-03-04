package sgo.entidad;

import java.io.Serializable;

public class Partners implements Serializable{

	private static final long serialVersionUID = -3189991216897271357L;
	private java.lang.String funInterlocutor;
	private java.lang.String codInterlocutor;
    private java.lang.String posicion;
    
    public java.lang.String getFunInterlocutor() {
		return funInterlocutor;
	}

	public void setFunInterlocutor(java.lang.String funInterlocutor) {
		this.funInterlocutor = funInterlocutor;
	}

	public java.lang.String getCodInterlocutor() {
		return codInterlocutor;
	}

	public void setCodInterlocutor(java.lang.String codInterlocutor) {
		this.codInterlocutor = codInterlocutor;
	}

	public java.lang.String getPosicion() {
		return posicion;
	}

	public void setPosicion(java.lang.String posicion) {
		this.posicion = posicion;
	}

}
