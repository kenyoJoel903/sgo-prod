package sgo.entidad;

import java.io.Serializable;
import java.math.BigDecimal;

import sgo.utilidades.Utilidades;

public class ProformaDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7930838102467364712L;
	private Integer idDetalleProforma;
	private Integer idProforma;
	private Planta planta;
	private Producto producto;
	private Integer posicion;
	private BigDecimal volumen;
//	private BigDecimal volumenDecimal;
	private BigDecimal precio;
	private BigDecimal descuento;
	private BigDecimal precioNeto;
	private BigDecimal rodaje;
	private BigDecimal isc;
	private BigDecimal otros; // ticket 9000003025
	private BigDecimal acumulado;
	private BigDecimal igv;
	private BigDecimal fise;
	private BigDecimal precioDescuento;
	private BigDecimal precioPercepcion;
	private BigDecimal importeTotal;
	
	public String getVolumenVista() {
		return Utilidades.formatearCotizacion(volumen, 3, 3);
	}
	public String getPrecioVista() {
		return Utilidades.formatearCotizacion(precio, 4, 4);
	}
	public String getDescuentoVista() {
		return Utilidades.formatearCotizacion(descuento, 4, 4);
	}
	public String getPrecioDescuentoVista() {
		return Utilidades.formatearCotizacion(precioDescuento, 4, 4);
	}
	public String getRodajeVista() {
		return Utilidades.formatearCotizacion(rodaje, 4, 4);
	}
	public String getIscVista() {
		return Utilidades.formatearCotizacion(isc, 4, 4);
	}
	public String getAcumuladoVista() {
		return Utilidades.formatearCotizacion(acumulado, 4, 4);
	}
	public String getIgvVista() {
		return Utilidades.formatearCotizacion(igv, 4, 4);
	}
	public String getFiseVista() {
		return Utilidades.formatearCotizacion(fise, 4, 4);
	}
	public String getPrecioNetoVista() {
		return Utilidades.formatearCotizacion(precioNeto, 4, 4);
	}
	public String getPrecioPercepcionVista() {
		return Utilidades.formatearCotizacion(precioPercepcion, 4, 4);
	}
	public String getImporteTotalVista() {
		return Utilidades.formatearCotizacion(importeTotal, 2, 2);
	}
	public String getOtrosVista() {
		return Utilidades.formatearCotizacion(otros, 4, 4);
	}
	public Integer getIdDetalleProforma() {
		return idDetalleProforma;
	}
	public void setIdDetalleProforma(Integer idDetalleProforma) {
		this.idDetalleProforma = idDetalleProforma;
	}
	public Integer getIdProforma() {
		return idProforma;
	}
	public void setIdProforma(Integer idProforma) {
		this.idProforma = idProforma;
	}
	public BigDecimal getVolumen() {
		return volumen;
	}
	public void setVolumen(BigDecimal volumen) {
		this.volumen = volumen;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public BigDecimal getDescuento() {
		return descuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	public BigDecimal getPrecioNeto() {
		return precioNeto;
	}
	public void setPrecioNeto(BigDecimal precioNeto) {
		this.precioNeto = precioNeto;
	}
	public BigDecimal getRodaje() {
		return rodaje;
	}
	public void setRodaje(BigDecimal rodaje) {
		this.rodaje = rodaje;
	}
	public BigDecimal getIsc() {
		return isc;
	}
	public void setIsc(BigDecimal isc) {
		this.isc = isc;
	}
	public BigDecimal getAcumulado() {
		return acumulado;
	}
	public void setAcumulado(BigDecimal acumulado) {
		this.acumulado = acumulado;
	}
	public BigDecimal getIgv() {
		return igv;
	}
	public void setIgv(BigDecimal igv) {
		this.igv = igv;
	}
	public BigDecimal getFise() {
		return fise;
	}
	public void setFise(BigDecimal fise) {
		this.fise = fise;
	}
	public BigDecimal getPrecioDescuento() {
		return precioDescuento;
	}
	public void setPrecioDescuento(BigDecimal precioDescuento) {
		this.precioDescuento = precioDescuento;
	}
	public BigDecimal getImporteTotal() {
		return importeTotal;
	}
	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}
	public BigDecimal getPrecioPercepcion() {
		return precioPercepcion;
	}
	public void setPrecioPercepcion(BigDecimal precioPercepcion) {
		this.precioPercepcion = precioPercepcion;
	}
	public Planta getPlanta() {
		return planta;
	}
	public void setPlanta(Planta planta) {
		this.planta = planta;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Integer getPosicion() {
		return posicion;
	}
	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}
	public BigDecimal getOtros() {
		return otros;
	}
	public void setOtros(BigDecimal otros) {
		this.otros = otros;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
