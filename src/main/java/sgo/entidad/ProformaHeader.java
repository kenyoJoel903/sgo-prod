package sgo.entidad;

import java.io.Serializable;

public class ProformaHeader implements Serializable {

	private static final long serialVersionUID = -4843359593342953946L;

	private java.lang.String docVenta;
	private java.lang.String orgVenta;
	private java.lang.String canal;
	private java.lang.String sector;
	private java.lang.String fecValidaDe;
	private java.lang.String fecValidaA;
	private java.lang.String moneda;
	private java.lang.String proceso;

	public java.lang.String getDocVenta() {
		return docVenta;
	}

	public void setDocVenta(java.lang.String docVenta) {
		this.docVenta = docVenta;
	}

	public java.lang.String getOrgVenta() {
		return orgVenta;
	}

	public void setOrgVenta(java.lang.String orgVenta) {
		this.orgVenta = orgVenta;
	}

	public java.lang.String getCanal() {
		return canal;
	}

	public void setCanal(java.lang.String canal) {
		this.canal = canal;
	}

	public java.lang.String getSector() {
		return sector;
	}

	public void setSector(java.lang.String sector) {
		this.sector = sector;
	}

	public java.lang.String getFecValidaDe() {
		return fecValidaDe;
	}

	public void setFecValidaDe(java.lang.String fecValidaDe) {
		this.fecValidaDe = fecValidaDe;
	}

	public java.lang.String getFecValidaA() {
		return fecValidaA;
	}

	public void setFecValidaA(java.lang.String fecValidaA) {
		this.fecValidaA = fecValidaA;
	}

	public java.lang.String getMoneda() {
		return moneda;
	}

	public void setMoneda(java.lang.String moneda) {
		this.moneda = moneda;
	}

	public java.lang.String getProceso() {
		return proceso;
	}

	public void setProceso(java.lang.String proceso) {
		this.proceso = proceso;
	}
}
