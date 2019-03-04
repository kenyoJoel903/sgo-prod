package sgo.entidad;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item implements Serializable {

	private static final long serialVersionUID = 1745197466580068395L;
	private Integer idDetalleProforma;
	private Integer fkIdProforma;
	private Planta planta;
	private Producto producto;
	private Integer posicion;
	private String volumen;
	private BigDecimal precio;
	private BigDecimal descuento;
	private BigDecimal precioNeto;
	private String rodaje;
	private BigDecimal isc;
	private BigDecimal acumulado;
	private BigDecimal igv;
	private BigDecimal fise;
	private BigDecimal precioDescuento;
	private BigDecimal precioPercepcion;
	private BigDecimal importeTotal;
	public Integer getIdDetalleProforma() {
		return idDetalleProforma;
	}
	public void setIdDetalleProforma(Integer idDetalleProforma) {
		this.idDetalleProforma = idDetalleProforma;
	}
	public Integer getFkIdProforma() {
		return fkIdProforma;
	}
	public void setFkIdProforma(Integer fkIdProforma) {
		this.fkIdProforma = fkIdProforma;
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
	public String getVolumen() {
		return volumen;
	}
	public void setVolumen(String volumen) {
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
	public String getRodaje() {
		return rodaje;
	}
	public void setRodaje(String rodaje) {
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
	public BigDecimal getPrecioNeto() {
		return precioNeto;
	}
	public void setPrecioNeto(BigDecimal precioNeto) {
		this.precioNeto = precioNeto;
	}
	public BigDecimal getPrecioDescuento() {
		return precioDescuento;
	}
	public void setPrecioDescuento(BigDecimal precioDescuento) {
		this.precioDescuento = precioDescuento;
	}
	public BigDecimal getPrecioPercepcion() {
		return precioPercepcion;
	}
	public void setPrecioPercepcion(BigDecimal precioPercepcion) {
		this.precioPercepcion = precioPercepcion;
	}
	public BigDecimal getImporteTotal() {
		return importeTotal;
	}
	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}

}
