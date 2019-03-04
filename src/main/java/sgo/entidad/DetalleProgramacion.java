package sgo.entidad;

import java.util.Date;
import java.util.Locale;

import org.springframework.context.MessageSource;

import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;

public class DetalleProgramacion extends EntidadBase {
  private int id_dprogramacion;
  private int id_programacion;
  private int id_cisterna;  
  private int id_producto;  
  private int id_conductor;  
  private int id_planta; 
  private String orden_compra;
  private String codigo_scop;
  private String codigo_sap_pedido;
  private String descripcion_planta_despacho;
  
  private Cisterna cisterna;
  private Producto producto;
  private Conductor conductor;
  private Programacion programacion;
  private Planta planta;
  private float capacidad_cisterna_total;
  private int completar;
  
  // Inicio: Atenci�n Ticket 9000002608 - 22/03/2018 jmatos/IBM
  private String razonSocial;
  private Integer numeroCompartimiento;
  private Integer capacidadVolumetrica;
  // Fin: Atenci�n Ticket 9000002608 - 22/03/2018 jmatos/IBM
  
  //Agregado por req 9000002841====================
  private String tarjetaCub;
  private Date fechaInicioVigTC;
  private Date fechaFinVigTC;
  
  private String strFechaInicioVigTC;
  private String strFechaFinVigTC;
  
	public String getStrFechaInicioVigTC() {
		return strFechaInicioVigTC;
	}
	public void setStrFechaInicioVigTC(String strFechaInicioVigTC) {
		this.strFechaInicioVigTC = strFechaInicioVigTC;
	}
	public String getStrFechaFinVigTC() {
		return strFechaFinVigTC;
	}
	public void setStrFechaFinVigTC(String strFechaFinVigTC) {
		this.strFechaFinVigTC = strFechaFinVigTC;
	}
	public String getTarjetaCub() {
		return tarjetaCub;
	}
	public void setTarjetaCub(String tarjetaCub) {
		this.tarjetaCub = tarjetaCub;
	}
	public Date getFechaInicioVigTC() {
		return fechaInicioVigTC;
	}
	public void setFechaInicioVigTC(Date fechaInicioVigTC) {
		this.fechaInicioVigTC = fechaInicioVigTC;
	}
	public Date getFechaFinVigTC() {
		return fechaFinVigTC;
	}
	public void setFechaFinVigTC(Date fechaFinVigTC) {
		this.fechaFinVigTC = fechaFinVigTC;
	}
  
  //Agregado por req 9000002841====================
  
  
  
//variables para hacer las validaciones
static final int MAXIMA_LONGITUD_CODIGO_PEDIDO_SCOP=20;
/*public Integer getNumeroCompartimiento() {
	return numeroCompartimiento;
}
public void setNumeroCompartimiento(Integer numeroCompartimiento) {
	this.numeroCompartimiento = numeroCompartimiento;
}
public Integer getCapacidadVolumetrica() {
	return capacidadVolumetrica;
}
public void setCapacidadVolumetrica(Integer capacidadVolumetrica) {
	this.capacidadVolumetrica = capacidadVolumetrica;
}*/
static final int MAXIMA_LONGITUD_CODIGO_PEDIDO_SAP=20;

public final static int NO_ES_COMPLETAR=1;
public final static int SI_ES_COMPLETAR=2;
  
public int getId() {
	return id_dprogramacion;
}
public void setId(int id) {
	this.id_dprogramacion = id;
}

public int getIdProgramacion() {
	return id_programacion;
}

public void setIdProgramacion(int idProgramacion) {
	this.id_programacion = idProgramacion;
}

public int getIdCisterna() {
	return id_cisterna;
}

public void setIdCisterna(int idCisterna) {
	this.id_cisterna = idCisterna;
}

public int getIdProducto() {
	return id_producto;
}

public void setIdProducto(int idProducto) {
	this.id_producto = idProducto;
}

public int getIdConductor() {
	return id_conductor;
}

public void setIdConductor(int idConductor) {
	this.id_conductor = idConductor;
}

public String getOrdenCompra() {
	return orden_compra;
}

public void setOrdenCompra(String ordenCompra) {
	this.orden_compra = ordenCompra;
}

public String getCodigoScop() {
	return codigo_scop;
}

public void setCodigoScop(String codigoScop) {
	this.codigo_scop = codigoScop;
}

public String getCodigoSapPedido() {
	return codigo_sap_pedido;
}

public void setCodigoSapPedido(String codigoSapPedido) {
	this.codigo_sap_pedido = codigoSapPedido;
}

public Cisterna getCisterna() {
	return cisterna;
}

public void setCisterna(Cisterna cisterna) {
	this.cisterna = cisterna;
}

public Producto getProducto() {
	return producto;
}

public void setProducto(Producto producto) {
	this.producto = producto;
}

public Conductor getConductor() {
	return conductor;
}

public void setConductor(Conductor conductor) {
	this.conductor = conductor;
}

public Programacion getProgramacion() {
	return programacion;
}

public void setProgramacion(Programacion programacion) {
	this.programacion = programacion;
}
public float getCapacidadCisternaTotal() {
	return capacidad_cisterna_total;
}
public void setCapacidadCisternaTotal(float capacidad_cisterna_total) {
	this.capacidad_cisterna_total = capacidad_cisterna_total;
}

public int getIdPlanta() {
	return id_planta;
}
public void setIdPlanta(int idPlanta) {
	this.id_planta = idPlanta;
}
public Planta getPlanta() {
	return planta;
}
public void setPlanta(Planta planta) {
	this.planta = planta;
}
public String getDescripcionPlantaDespacho() {
	return descripcion_planta_despacho;
}
public void setDescripcionPlantaDespacho(String descripcionPlantaDespacho) {
	this.descripcion_planta_despacho = descripcionPlantaDespacho;
}
// Inicio: Atenci�n Ticket 9000002608 - 22/03/2018 jmatos/IBM
public Integer getNumeroCompartimiento() {
	return numeroCompartimiento;
}
public void setNumeroCompartimiento(Integer numeroCompartimiento) {
	this.numeroCompartimiento = numeroCompartimiento;
}
public Integer getCapacidadVolumetrica() {
	return capacidadVolumetrica;
}
public void setCapacidadVolumetrica(Integer capacidadVolumetrica) {
	this.capacidadVolumetrica = capacidadVolumetrica;
}
// Fin: Atenci�n Ticket 9000002608 - 22/03/2018 jmatos/IBM

public Respuesta validarProgramacion(MessageSource gestorDiccionario, Locale locale){
  Respuesta respuesta = new Respuesta();
	try {
		// Inicio Ticket 9000002608
	  /*if (!Utilidades.esValidoForingKey(this.id_producto)){	
		respuesta.estado = false;
		respuesta.valor = gestorDiccionario.getMessage("sgo.errorValoresNulosEntidad", new Object[] { "Producto" }, locale);
		return respuesta;
	  }*/
		// Fin Ticket 9000002608
	  if (!Utilidades.esValidoForingKey(this.id_cisterna)){	
		respuesta.estado = false;
		respuesta.valor = gestorDiccionario.getMessage("sgo.errorValoresNulosEntidad", new Object[] { "Cisterna" }, locale);
		return respuesta;
	  }
	  if (!Utilidades.esValidoForingKey(this.id_conductor)){	
		respuesta.estado = false;
		respuesta.valor = gestorDiccionario.getMessage("sgo.errorValoresNulosEntidad", new Object[] { "Conductor" }, locale);
		return respuesta;
	  }
	  if (!Utilidades.esValidoForingKey(this.id_planta)){	
			respuesta.estado = false;
			respuesta.valor = gestorDiccionario.getMessage("sgo.errorValoresNulosEntidad", new Object[] { "Planta" }, locale);
			return respuesta;
	  }
	  respuesta.estado = true;
	  respuesta.valor = null;
  } catch (Exception excepcionGenerica) {
   excepcionGenerica.printStackTrace();
   respuesta.error = Constante.EXCEPCION_GENERICA;
   respuesta.valor = gestorDiccionario.getMessage("sgo.errorGenericoServidor", null, locale);
   respuesta.estado = false;
  }
  return respuesta;
}

public Respuesta validarCompletarProgramacion(MessageSource gestorDiccionario, Locale locale){
  Respuesta respuesta = new Respuesta();
	try {
	  /*if (!Utilidades.esValidoForingKey(this.id_producto)){	
		respuesta.estado = false;
		respuesta.valor = gestorDiccionario.getMessage("sgo.errorValoresNulosEntidad", new Object[] { "Producto" }, locale);
		return respuesta;
	  }*/
	  if (!Utilidades.esValidoForingKey(this.id_cisterna)){	
		respuesta.estado = false;
		respuesta.valor = gestorDiccionario.getMessage("sgo.errorValoresNulosEntidad", new Object[] { "Cisterna" }, locale);
		return respuesta;
	  }	  
	  if (!Utilidades.esValidoForingKey(this.id_conductor)){	
			respuesta.estado = false;
			respuesta.valor = gestorDiccionario.getMessage("sgo.errorValoresNulosEntidad", new Object[] { "Conductor" }, locale);
			return respuesta;
	  }
	  if (!Utilidades.esValidoForingKey(this.id_planta)){	
			respuesta.estado = false;
			respuesta.valor = gestorDiccionario.getMessage("sgo.errorValoresNulosEntidad", new Object[] { "Planta" }, locale);
			return respuesta;
	  }
	  /*if (!Utilidades.esValido(this.codigo_scop)){	
		respuesta.estado = false;
		respuesta.valor = gestorDiccionario.getMessage("sgo.errorValoresNulosEntidad", new Object[] { "SCOP" }, locale);
		return respuesta;
	  }
	  
	  if (!Utilidades.esValido(this.codigo_sap_pedido)){	
		respuesta.estado = false;
		respuesta.valor = gestorDiccionario.getMessage("sgo.errorValoresNulosEntidad", new Object[] { "Pedido" }, locale);
		return respuesta;
	  }
	  if (this.codigo_scop.length() > MAXIMA_LONGITUD_CODIGO_PEDIDO_SCOP){
		respuesta.estado = false;
		respuesta.valor = gestorDiccionario.getMessage("sgo.errorValoresEntidad", new Object[] { "SCOP", MAXIMA_LONGITUD_CODIGO_PEDIDO_SCOP }, locale);
		return respuesta;
	  }
	  if (this.codigo_sap_pedido.length() > MAXIMA_LONGITUD_CODIGO_PEDIDO_SAP){
		respuesta.estado = false;
		respuesta.valor = gestorDiccionario.getMessage("sgo.errorValoresEntidad", new Object[] { "Pedido", MAXIMA_LONGITUD_CODIGO_PEDIDO_SAP }, locale);
		return respuesta;
	  }*/

	  respuesta.estado = true;
	  respuesta.valor = null;
  } catch (Exception excepcionGenerica) {
   excepcionGenerica.printStackTrace();
   respuesta.error = Constante.EXCEPCION_GENERICA;
   respuesta.valor = gestorDiccionario.getMessage("sgo.errorGenericoServidor", null, locale);
   respuesta.estado = false;
  }
  return respuesta;
}

//CADENAS SEGUN LOS CAMPOS QUE VIENEN DEL FORMULARIO
public String getCadena() {
	String cadena="";
	if(!Utilidades.esValido(this.getCodigoScop())){ this.setCodigoScop(""); };
	if(!Utilidades.esValido(this.getCodigoSapPedido())){ this.setCodigoSapPedido(""); };
	if(!Utilidades.esValido(this.getOrdenCompra())){ this.setOrdenCompra(""); };
	
	cadena = this.getCodigoScop().toString() + 
			 this.getCodigoSapPedido().toString() +
			 this.getOrdenCompra().toString() ; 

	return cadena;
}
public int getCompletar() {
	return completar;
}
public void setCompletar(int completar) {
	this.completar = completar;
}
public String getRazonSocial() {
	return razonSocial;
}
public void setRazonSocial(String razonSocial) {
	this.razonSocial = razonSocial;
}

}