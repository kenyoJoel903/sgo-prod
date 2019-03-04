package sgo.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;

import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;

public class Proforma extends EntidadBase implements Serializable {

	private static final long serialVersionUID = -9166998357940265939L;
	//atributos de request sap
	private ProformaHeader header;
	private List<ProformaDetalle> items;
	private Item item;
	private Partners partner;
	
	//atributos bd
	private Integer idProforma;
	private Cliente cliente;
	private CanalSector canalSector;
	private String nroCotizacion;
	private Date fechaCotizacion;
	private DatosInterlocutor interlocutor;
	private String moneda;
	private BigDecimal monto;
	private String proceso;

	public String getMontoVista() {
		return Utilidades.formatearCotizacion(monto, 2, 2);
	}
	
	public List<ProformaDetalle> getItems() {
		return items;
	}
	public void setItems(List<ProformaDetalle> items) {
		this.items = items;
	}
	public ProformaHeader getHeader() {
		return header;
	}
	public void setHeader(ProformaHeader header) {
		this.header = header;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Partners getPartner() {
		return partner;
	}
	public void setPartner(Partners partner) {
		this.partner = partner;
	}
	public Integer getIdProforma() {
		return idProforma;
	}
	public void setIdProforma(Integer idProforma) {
		this.idProforma = idProforma;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente codClientesap) {
		this.cliente = codClientesap;
	}
	public CanalSector getCanalSector() {
		return canalSector;
	}
	public void setCanalSector(CanalSector canalSector) {
		this.canalSector = canalSector;
	}
	public String getNroCotizacion() {
		return nroCotizacion;
	}
	public void setNroCotizacion(String nroCotizacion) {
		this.nroCotizacion = nroCotizacion;
	}
	public Date getFechaCotizacion() {
		return fechaCotizacion;
	}
	public void setFechaCotizacion(Date fechaCotizacion) {
		this.fechaCotizacion = fechaCotizacion;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public DatosInterlocutor getInterlocutor() {
		return interlocutor;
	}
	public void setInterlocutor(DatosInterlocutor interlocutor) {
		this.interlocutor = interlocutor;
	}
	public String getCadena() {
		return StringUtils.defaultString(canalSector.getCanalDistribucionSap(), "")+
				StringUtils.defaultString(canalSector.getSectorSap(),"")+
				StringUtils.defaultString(nroCotizacion,"")+
				StringUtils.defaultString(moneda,"")+
				StringUtils.defaultString(proceso,"");
	}

	public Respuesta validar(MessageSource gestorDiccionario, Locale locale) {
		Respuesta respuesta = new Respuesta();
		try {

			String texto = " de proforma " + this.getNroCotizacion();

			if (!Utilidades.esValidoForingKey(this.cliente.getId())) {
				respuesta.estado = false;
				respuesta.valor = gestorDiccionario.getMessage(
						"sgo.errorValoresNulosEntidad",
						new Object[] { "Cliente" }, locale);
				return respuesta;
			}
			if (!Utilidades.esValido(this.canalSector.getIdCanalSector())) {
				respuesta.estado = false;
				respuesta.valor = gestorDiccionario.getMessage(
						"sgo.errorValoresNulosEntidad",
						new Object[] { "Canal/Sector" + texto }, locale);
				return respuesta;
			}
			if (!Utilidades.esValido(this.interlocutor.getId())) {
				respuesta.estado = false;
				respuesta.valor = gestorDiccionario.getMessage(
						"sgo.errorValoresNulosEntidad",
						new Object[] { "Destinatario" + texto }, locale);
				return respuesta;
			}
			if (this.items.isEmpty()) {
				respuesta.estado = false;
				respuesta.valor = gestorDiccionario.getMessage(
						"sgo.errorValoresNulosEntidad",
						new Object[] { "Detalle Proforma" + texto }, locale);
				return respuesta;
			}

			respuesta.estado = true;
			respuesta.valor = null;
		} catch (Exception excepcionGenerica) {
			excepcionGenerica.printStackTrace();
			respuesta.error = Constante.EXCEPCION_GENERICA;
			respuesta.valor = gestorDiccionario.getMessage(
					"sgo.errorGenericoServidor", null, locale);
			respuesta.estado = false;
		}
		return respuesta;
	}
}
