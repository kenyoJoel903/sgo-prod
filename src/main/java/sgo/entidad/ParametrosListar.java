package sgo.entidad;
import sgo.utilidades.Constante;

public class ParametrosListar {
	//filtros para listado
	private int paginacion = Constante.CON_PAGINACION;
	private int inicioPaginacion = 0;
	private int registrosPagina = Constante.CANTIDAD_PAGINACION;
	private int registrosPaginaTransporte = Constante.CANTIDAD_PAGINACION_TRANSPORTE;
	private String campoOrdenamiento = "";
	private String sentidoOrdenamiento = "ASC";
	private String valorBuscado = "";
	private String filtroBrevete="";
	private String filtroCentroCliente="";
	private String filtroDestinarioMercaderia="";
	private String filtroCodigoReferencia="";
	//filtro estado
	private int[] filtroEstados;
	private int filtroEstado = Constante.FILTRO_TODOS;
	private int idTanque=Constante.FILTRO_TODOS;
	private int estadoServicio=Constante.FILTRO_TODOS;
	private int estadoDespachando=Constante.FILTRO_NINGUNO; //esto porque el estado despachando es 0 ó 1
	private int tanqueDeApertura=Constante.FILTRO_NINGUNO;
	private int tanqueDeCierre=Constante.FILTRO_NINGUNO;
	//filtros fechas
	private String filtroFechaInicio = "";
	private String filtroFechaFinal = "";
	private String filtroFechaPlanificada = "";
	private String filtroFechaCarga = "";
	private String filtroFechaDiaOperativo="";
	private String filtroFechaEmisionOE="";
	private String filtroFechaJornada = "";
	private String filtroFechaRecepcion = "";
	private String filtroInicioDespacho = "";
    private String filtroFinDespacho = "";
    
	//filtros identificadores
	private int filtroOperacion=0;
	private int filtroEstacion =0;
	private int filtroCisterna=0;
	private int filtroTracto= 0;
    private int filtroCompartimento= 0;
    private int filtroMilimetros= 0;
    private int filtroCentimetros= 0;
    
	private int filtroDiaOperativo=0;
	private int filtroCargaTanque=0;
	private int filtroProducto =0;
	private int filtroRol =0;
	private int filtroDescargaCisterna = 0;
	private int filtroIdUsuario=0;
	private int filtroIdProgramacion=0;
	private int filtroIdOperario=0;
	private int filtroPerteneceA=0;
	private int filtroIdDocumento=0;
	private int filtroIdEntidad=0;
	private int filtroCodigoArchivoOrigen=0;
	private int idTracto=0;
	private int idCisterna=0;
	private int idOperacion=0;
	private int idTransportista=0;
	private int idCliente=0;
	private int idJornada=0;
	private int idDesconche=0;
	private int idGuiaCombustible=0;
	private int indicadorProducto= 0;
	private int indicadorOperador= 0;
	private int idTurno=0;
	private int idTransporte=0;	
	private int filtroApertura=-1;
	private int filtroCierre=-1;
	private int filtroEta=0;
	//filtros textos
	private String filtroUsuario = "";
	private String filtroTabla = "";
	private String txtFiltro = "";
	private String txtQuery = "";
	private String filtroNumeroGuia="";
	private String filtroEstadoGuia="";	
	private String filtroPlacaCisterna="";
	private String filtroPlacaTracto="";//FIXME 7000001925
	private String filtroParametro = ""; 
	private String filtroEspecialCombinacion = "";
	private String filtroEspecialOrdenamiento="";
	private String filtroCodigoInternoAutorizacion="";
	private String filtroCodigoAutorizacion="";
	private String placaCisterna="";
	private String maximoRegistro="";
	private String propietarioNombreCorto="";
	private String vehiculoNombreCorto="";
	private String abreviaturaProducto="";
	private String nombreArchivoDespacho="";
	private String queryRolGec="";
	//esto para el envio de correo
	private String filtroNombreCliente="";
	private String filtroNombreOperacion="";
	private String filtroNombreTransportista="";
	private String filtroNombreProducto="";
	private String filtroNombreUsuarioEmisor="";
	private String filtroNombreUsuarioAprobador="";
	private String filtroNombreUsuarioObservador="";
	
	private int idPlanta = 0;
	private String idCanalSap = "";
	private String idSectorSap = "";
	private String codInterlocutorSap = "";
	private String claveRamoSap = "";
	private int filtroIdProforma = 0;
	private int idInterlocutor = 0;
	private int idCanalSector = 0;
	
	// 9000002608 - jmatos
	private int filtroCodigoCliente = 0;
	
	public int getFiltroCodigoCliente() {
		return filtroCodigoCliente;
	}

	public void setFiltroCodigoCliente(int filtroCodigoCliente) {
		this.filtroCodigoCliente = filtroCodigoCliente;
	}
	// 9000002608 - jmatos
	
	public String getFiltroNombreUsuarioObservador() {
		return filtroNombreUsuarioObservador;
	}

	public void setFiltroNombreUsuarioObservador(
			String filtroNombreUsuarioObservador) {
		this.filtroNombreUsuarioObservador = filtroNombreUsuarioObservador;
	}

	//filtros numericos
	private float filtroTemperaturaObservada=0;
	private float filtroApiObservado=0;
	
	//filtros para mail
	private String filtroMailPara="";
	private String filtroMailCC="";
	private String filtroMailAsunto="";
	private String filtroMailBody="";
	private String filtroMailComentario="";
	private String correoUsuario="";

	
	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public String getFiltroMailComentario() {
		return filtroMailComentario;
	}

	public void setFiltroMailComentario(String filtroMailComentario) {
		this.filtroMailComentario = filtroMailComentario;
	}

	public int getFiltroIdProgramacion() {
		return filtroIdProgramacion;
	}

	public void setFiltroIdProgramacion(int filtroIdProgramacion) {
		this.filtroIdProgramacion = filtroIdProgramacion;
	}

	public String getFiltroNombreCliente() {
		return filtroNombreCliente;
	}

	public void setFiltroNombreCliente(String filtroNombreCliente) {
		this.filtroNombreCliente = filtroNombreCliente;
	}

	public String getFiltroNombreOperacion() {
		return filtroNombreOperacion;
	}

	public void setFiltroNombreOperacion(String filtroNombreOperacion) {
		this.filtroNombreOperacion = filtroNombreOperacion;
	}

	public String getFiltroMailBody() {
		return filtroMailBody;
	}

	public void setFiltroMailBody(String filtroMailBody) {
		this.filtroMailBody = filtroMailBody;
	}

	public String getFiltroMailPara() {
		return filtroMailPara;
	}

	public void setFiltroMailPara(String filtroMailPara) {
		this.filtroMailPara = filtroMailPara;
	}

	public String getFiltroMailCC() {
		return filtroMailCC;
	}

	public void setFiltroMailCC(String filtroMailCC) {
		this.filtroMailCC = filtroMailCC;
	}

	public String getFiltroMailAsunto() {
		return filtroMailAsunto;
	}

	public void setFiltroMailAsunto(String filtroMailAsunto) {
		this.filtroMailAsunto = filtroMailAsunto;
	}
	
	public int getFiltroRol() {
		return filtroRol;
	}

	public void setFiltroRol(int filtroRol) {
		this.filtroRol = filtroRol;
	}

	public String getFiltroParametro() {
		return filtroParametro;
	}

	public void setFiltroParametro(String filtroParametro) {
		this.filtroParametro = filtroParametro;
	}

	public String getMaximoRegistro() {
		return maximoRegistro;
	}

	public void setMaximoRegistro(String maximoRegistro) {
		this.maximoRegistro = maximoRegistro;
	}

	public int getIdJornada() {
		return idJornada;
	}

	public void setIdJornada(int idJornada) {
		this.idJornada = idJornada;
	}

	/**
	 * @return the idTransportista
	 */
	public int getIdTransportista() {
		return idTransportista;
	}

	/**
	 * @param idTransportista the idTransportista to set
	 */
	public void setIdTransportista(int idTransportista) {
		this.idTransportista = idTransportista;
	}

	/**
	 * @return the idCliente
	 */
	public int getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the idOperacion
	 */
	public int getIdOperacion() {
		return idOperacion;
	}

	/**
	 * @param idOperacion the idOperacion to set
	 */
	public void setIdOperacion(int idOperacion) {
		this.idOperacion = idOperacion;
	}

	/**
	 * @return the idCisterna
	 */
	public int getIdCisterna() {
		return idCisterna;
	}

	/**
	 * @param idCisterna the idCisterna to set
	 */
	public void setIdCisterna(int idCisterna) {
		this.idCisterna = idCisterna;
	}

	/**
	 * @return the idTracto
	 */
	public int getIdTracto() {
		return idTracto;
	}

	/**
	 * @param idTracto the idTracto to set
	 */
	public void setIdTracto(int idTracto) {
		this.idTracto = idTracto;
	}

	/**
	 * @return the placaCisterna
	 */
	public String getPlacaCisterna() {
		return placaCisterna;
	}

	/**
	 * @param placaCisterna the placaCisterna to set
	 */
	public void setPlacaCisterna(String placaCisterna) {
		this.placaCisterna = placaCisterna;
	}

	/**
	 * @return the filtroFechaPlanificada
	 */
	public String getFiltroFechaPlanificada() {
		return filtroFechaPlanificada;
	}

	/**
	 * @param filtroFechaPlanificada the filtroFechaPlanificada to set
	 */
	public void setFiltroFechaPlanificada(String filtroFechaPlanificada) {
		this.filtroFechaPlanificada = filtroFechaPlanificada;
	}

	/**
	 * @return the filtroOperacion
	 */
	public int getFiltroOperacion() {
		return filtroOperacion;
	}

	/**
	 * @param filtroOperacion the filtroOperacion to set
	 */
	public void setFiltroOperacion(int filtroOperacion) {
		this.filtroOperacion = filtroOperacion;
	}

	/**
	 * @return the txtFiltro
	 */
	public String getTxtFiltro() {
		return txtFiltro;
	}

	/**
	 * @param txtFiltro the txtFiltro to set
	 */
	public void setTxtFiltro(String txtFiltro) {
		this.txtFiltro = txtFiltro;
	}

	/**
	 * @return the filtroEstado
	 */
	public int getFiltroEstado() {
		return filtroEstado;
	}

	/**
	 * @param filtroEstado the filtroEstado to set
	 */
	public void setFiltroEstado(int filtroEstado) {
		this.filtroEstado = filtroEstado;
	}

	public String getFiltroFechaInicio() {
		return filtroFechaInicio;
	}

	public void setFiltroFechaInicio(String filtroFechaInicio) {
		this.filtroFechaInicio = filtroFechaInicio;
	}

	public String getFiltroFechaFinal() {
		return filtroFechaFinal;
	}

	public void setFiltroFechaFinal(String filtroFechaFinal) {
		this.filtroFechaFinal = filtroFechaFinal;
	}

	public String getFiltroUsuario() {
		return filtroUsuario;
	}

	public void setFiltroUsuario(String filtroUsuario) {
		this.filtroUsuario = filtroUsuario;
	}

	public String getFiltroTabla() {
		return filtroTabla;
	}

	public void setFiltroTabla(String filtroTabla) {
		this.filtroTabla = filtroTabla;
	}

	/**
	 * @return the paginacion
	 */
	public int getPaginacion() {
		return paginacion;
	}

	/**
	 * @param paginacion the paginacion to set
	 */
	public void setPaginacion(int paginacion) {
		this.paginacion = paginacion;
	}

	/**
	 * @return the cantidadPaginacion
	 */
	public int getRegistrosxPagina() {
		return registrosPagina;
	}

	/**
	 * @param cantidadPaginacion the cantidadPaginacion to set
	 */
	public void setRegistrosxPagina(int numeroRegistros) {
		this.registrosPagina = numeroRegistros;
	}

	/**
	 * @return the inicioPaginacion
	 */
	public int getInicioPaginacion() {
		return inicioPaginacion;
	}

	/**
	 * @param inicioPaginacion
	 *            the inicioPaginacion to set
	 */
	public void setInicioPaginacion(int inicioPaginacion) {
		this.inicioPaginacion = inicioPaginacion;
	}

	/**
	 * @return the sentidoOrdenamiento
	 */
	public String getSentidoOrdenamiento() {
		return sentidoOrdenamiento;
	}

	/**
	 * @param sentidoOrdenamiento
	 *            the sentidoOrdenamiento to set
	 */
	public void setSentidoOrdenamiento(String sentidoOrdenamiento) {
		this.sentidoOrdenamiento = sentidoOrdenamiento;
	}

	/**
	 * @return the campoOrdenamiento
	 */
	public String getCampoOrdenamiento() {
		return campoOrdenamiento;
	}

	/**
	 * @param campoOrdenamiento
	 *            the campoOrdenamiento to set
	 */
	public void setCampoOrdenamiento(String campoOrdenamiento) {
		this.campoOrdenamiento = campoOrdenamiento;
	}

	/**
	 * @return the valorBuscado
	 */
	public String getValorBuscado() {
		return valorBuscado;
	}

	/**
	 * @param valorBuscado the valorBuscado to set
	 */
	public void setValorBuscado(String valorBuscado) {
		this.valorBuscado = valorBuscado;
	}

 /**
  * @return the filtroEstacion
  */
 public int getFiltroEstacion() {
  return filtroEstacion;
 }

 /**
  * @param filtroEstacion the filtroEstacion to set
  */
 public void setFiltroEstacion(int filtroEstacion) {
  this.filtroEstacion = filtroEstacion;
 }

 /**
  * @return the filtroDiaOperativo
  */
 public int getFiltroDiaOperativo() {
  return filtroDiaOperativo;
 }

 /**
  * @param filtroDiaOperativo the filtroDiaOperativo to set
  */
 public void setFiltroDiaOperativo(int filtroDiaOperativo) {
  this.filtroDiaOperativo = filtroDiaOperativo;
 }

 /**
  * @return the filtroCargaTanque
  */
 public int getFiltroCargaTanque() {
  return filtroCargaTanque;
 }

 /**
  * @param filtroCargaTanque the filtroCargaTanque to set
  */
 public void setFiltroCargaTanque(int filtroCargaTanque) {
  this.filtroCargaTanque = filtroCargaTanque;
 }

 /**
  * @return the filtroFechaDiaOperativo
  */
 public String getFiltroFechaDiaOperativo() {
  return filtroFechaDiaOperativo;
 }

 /**
  * @param filtroFechaDiaOperativo the filtroFechaDiaOperativo to set
  */
 public void setFiltroFechaDiaOperativo(String filtroFechaDiaOperativo) {
  this.filtroFechaDiaOperativo = filtroFechaDiaOperativo;
 }

 /**
  * @return the filtroPlacaCisterna
  */
 public String getFiltroPlacaCisterna() {
  return filtroPlacaCisterna;
 }

 /**
  * @param filtroPlacaCisterna the filtroPlacaCisterna to set
  */
 public void setFiltroPlacaCisterna(String filtroPlacaCisterna) {
  this.filtroPlacaCisterna = filtroPlacaCisterna;
 }

	public String getFiltroPlacaTracto() {
		return filtroPlacaTracto;
	}

	public void setFiltroPlacaTracto(String filtroPlacaTracto) {
		this.filtroPlacaTracto = filtroPlacaTracto;
	}

 
 /**
  * @return the filtroFechaEmisionOE
  */
 public String getFiltroFechaEmisionOE() {
  return filtroFechaEmisionOE;
 }

 /**
  * @param filtroFechaEmisionOE the filtroFechaEmisionOE to set
  */
 public void setFiltroFechaEmisionOE(String filtroFechaEmisionOE) {
  this.filtroFechaEmisionOE = filtroFechaEmisionOE;
 }

 /**
  * @return the filtroNumeroGuia
  */
 public String getFiltroNumeroGuia() {
  return filtroNumeroGuia;
 }

 /**
  * @param filtroNumeroGuia the filtroNumeroGuia to set
  */
 public void setFiltroNumeroGuia(String filtroNumeroGuia) {
  this.filtroNumeroGuia = filtroNumeroGuia;
 }

 /**
  * @return the filtroCisterna
  */
 public int getFiltroCisterna() {
  return filtroCisterna;
 }

 /**
  * @param filtroCisterna the filtroCisterna to set
  */
 public void setFiltroCisterna(int filtroCisterna) {
  this.filtroCisterna = filtroCisterna;
 }

 /**
  * @return the filtroTracto
  */
 public int getFiltroTracto() {
  return filtroTracto;
 }

 /**
  * @param filtroTracto the filtroTracto to set
  */
 public void setFiltroTracto(int filtroTracto) {
  this.filtroTracto = filtroTracto;
 }

 /**
  * @return the filtroCompartimento
  */
 public int getFiltroCompartimento() {
  return filtroCompartimento;
 }

 /**
  * @param filtroCompartimento the filtroCompartimento to set
  */
 public void setFiltroCompartimento(int filtroCompartimento) {
  this.filtroCompartimento = filtroCompartimento;
 }

 /**
  * @return the filtroMilimetros
  */
 public int getFiltroMilimetros() {
  return filtroMilimetros;
 }

 /**
  * @param filtroMilimetros the filtroMilimetros to set
  */
 public void setFiltroMilimetros(int filtroMilimetros) {
  this.filtroMilimetros = filtroMilimetros;
 }

 /**
  * @return the filtroTemperaturaObservada
  */
 public float getFiltroTemperaturaObservada() {
  return filtroTemperaturaObservada;
 }

 /**
  * @param filtroTemperaturaObservada the filtroTemperaturaObservada to set
  */
 public void setFiltroTemperaturaObservada(float filtroTemperaturaObservada) {
  this.filtroTemperaturaObservada = filtroTemperaturaObservada;
 }

 /**
  * @return the filtroApiObservado
  */
 public float getFiltroApiObservado() {
  return filtroApiObservado;
 }

 /**
  * @param filtroApiObservado the filtroApiObservado to set
  */
 public void setFiltroApiObservado(float filtroApiObservado) {
  this.filtroApiObservado = filtroApiObservado;
 }

 /**
  * @return the filtroEspecialCombinacion
  */
 public String getFiltroEspecialCombinacion() {
  return filtroEspecialCombinacion;
 }

 /**
  * @param filtroEspecialCombinacion the filtroEspecialCombinacion to set
  */
 public void setFiltroEspecialCombinacion(String filtroEspecialCombinacion) {
  this.filtroEspecialCombinacion = filtroEspecialCombinacion;
 }

 /**
  * @return the filtroEspecialOrdenamiento
  */
 public String getFiltroEspecialOrdenamiento() {
  return filtroEspecialOrdenamiento;
 }

 /**
  * @param filtroEspecialOrdenamiento the filtroEspecialOrdenamiento to set
  */
 public void setFiltroEspecialOrdenamiento(String filtroEspecialOrdenamiento) {
  this.filtroEspecialOrdenamiento = filtroEspecialOrdenamiento;
 }

 /**
  * @return the filtroProducto
  */
 public int getFiltroProducto() {
  return filtroProducto;
 }

 /**
  * @param filtroProducto the filtroProducto to set
  */
 public void setFiltroProducto(int filtroProducto) {
  this.filtroProducto = filtroProducto;
 }

 /**
  * @return the filtroDescargaCisterna
  */
 public int getFiltroDescargaCisterna() {
  return filtroDescargaCisterna;
 }

 /**
  * @param filtroDescargaCisterna the filtroDescargaCisterna to set
  */
 public void setFiltroDescargaCisterna(int filtroDescargaCisterna) {
  this.filtroDescargaCisterna = filtroDescargaCisterna;
 }

 /**
  * @return the filtroCodigoInternoAutorizacion
  */
 public String getFiltroCodigoInternoAutorizacion() {
  return filtroCodigoInternoAutorizacion;
 }

 /**
  * @param filtroCodigoInternoAutorizacion the filtroCodigoInternoAutorizacion to set
  */
 public void setFiltroCodigoInternoAutorizacion(String filtroCodigoInternoAutorizacion) {
  this.filtroCodigoInternoAutorizacion = filtroCodigoInternoAutorizacion;
 }

 /**
  * @return the filtroCodigoAutorizacion
  */
 public String getFiltroCodigoAutorizacion() {
  return filtroCodigoAutorizacion;
 }

 /**
  * @param filtroCodigoAutorizacion the filtroCodigoAutorizacion to set
  */
 public void setFiltroCodigoAutorizacion(String filtroCodigoAutorizacion) {
  this.filtroCodigoAutorizacion = filtroCodigoAutorizacion;
 }

 /**
  * @return the filtroIdUsuario
  */
 public int getFiltroIdUsuario() {
  return filtroIdUsuario;
 }

 /**
  * @param filtroIdUsuario the filtroIdUsuario to set
  */
 public void setFiltroIdUsuario(int filtroIdUsuario) {
  this.filtroIdUsuario = filtroIdUsuario;
 }

public String getFiltroFechaCarga() {
	return filtroFechaCarga;
}

public void setFiltroFechaCarga(String filtroFechaCarga) {
	this.filtroFechaCarga = filtroFechaCarga;
}

public String getFiltroFechaJornada() {
	return filtroFechaJornada;
}

public void setFiltroFechaJornada(String filtroFechaJornada) {
	this.filtroFechaJornada = filtroFechaJornada;
}

public String getTxtQuery() {
	return txtQuery;
}

public void setTxtQuery(String txtQuery) {
	this.txtQuery = txtQuery;
}

/**
 * @return the idDesconche
 */
public int getIdDesconche() {
 return idDesconche;
}

/**
 * @param idDesconche the idDesconche to set
 */
public void setIdDesconche(int idDesconche) {
 this.idDesconche = idDesconche;
}

public int getFiltroPerteneceA() {
	return filtroPerteneceA;
}

public void setFiltroPerteneceA(int filtroPerteneceA) {
	this.filtroPerteneceA = filtroPerteneceA;
}

public int getFiltroIdDocumento() {
	return filtroIdDocumento;
}

public void setFiltroIdDocumento(int filtroIdDocumento) {
	this.filtroIdDocumento = filtroIdDocumento;
}

public int getFiltroIdEntidad() {
	return filtroIdEntidad;
}

public void setFiltroIdEntidad(int filtroIdEntidad) {
	this.filtroIdEntidad = filtroIdEntidad;
}

public int getFiltroCodigoArchivoOrigen() {
	return filtroCodigoArchivoOrigen;
}

public void setFiltroCodigoArchivoOrigen(int filtroCodigoArchivoOrigen) {
	this.filtroCodigoArchivoOrigen = filtroCodigoArchivoOrigen;
}

public int getFiltroIdOperario() {
	return filtroIdOperario;
}

public void setFiltroIdOperario(int filtroIdOperario) {
	this.filtroIdOperario = filtroIdOperario;
}

/**
 * @return the idGuiaCombustible
 */
public int getIdGuiaCombustible() {
 return idGuiaCombustible;
}

/**
 * @param idGuiaCombustible the idGuiaCombustible to set
 */
public void setIdGuiaCombustible(int idGuiaCombustible) {
 this.idGuiaCombustible = idGuiaCombustible;
}

/**
 * @return the idTanque
 */
public int getIdTanque() {
 return idTanque;
}

/**
 * @param idTanque the idTanque to set
 */
public void setIdTanque(int idTanque) {
 this.idTanque = idTanque;
}

public int getIndicadorProducto() {
	return indicadorProducto;
}

public void setIndicadorProducto(int indicadorProducto) {
	this.indicadorProducto = indicadorProducto;
}

/**
 * @return the filtroBrevete
 */
public String getFiltroBrevete() {
 return filtroBrevete;
}

/**
 * @param filtroBrevete the filtroBrevete to set
 */
public void setFiltroBrevete(String filtroBrevete) {
 this.filtroBrevete = filtroBrevete;
}

/**
 * @return the filtroCentroCliente
 */
public String getFiltroCentroCliente() {
 return filtroCentroCliente;
}

/**
 * @param filtroCentroCliente the filtroCentroCliente to set
 */
public void setFiltroCentroCliente(String filtroCentroCliente) {
 this.filtroCentroCliente = filtroCentroCliente;
}

/**
 * @return the filtroDestinarioMercaderia
 */
public String getFiltroDestinarioMercaderia() {
 return filtroDestinarioMercaderia;
}

/**
 * @param filtroDestinarioMercaderia the filtroDestinarioMercaderia to set
 */
public void setFiltroDestinarioMercaderia(String filtroDestinarioMercaderia) {
 this.filtroDestinarioMercaderia = filtroDestinarioMercaderia;
}

/**
 * @return the filtroCodigoReferencia
 */
public String getFiltroCodigoReferencia() {
 return filtroCodigoReferencia;
}

/**
 * @param filtroCodigoReferencia the filtroCodigoReferencia to set
 */
public void setFiltroCodigoReferencia(String filtroCodigoReferencia) {
 this.filtroCodigoReferencia = filtroCodigoReferencia;
}

public int getRegistrosPaginaTransporte() {
	return registrosPaginaTransporte;
}

public void setRegistrosPaginaTransporte(int registrosPaginaTransporte) {
	this.registrosPaginaTransporte = registrosPaginaTransporte;
}

public int getIdTurno() {
	return idTurno;
}

public void setIdTurno(int idTurno) {
	this.idTurno = idTurno;
}

public int[] getFiltroEstados() {
	return filtroEstados;
}

public void setFiltroEstados(int[] filtroEstados) {
	this.filtroEstados = filtroEstados;
}

public int getEstadoServicio() {
	return estadoServicio;
}

public void setEstadoServicio(int estadoServicio) {
	this.estadoServicio = estadoServicio;
}

public String getPropietarioNombreCorto() {
	return propietarioNombreCorto;
}

public void setPropietarioNombreCorto(String propietarioNombreCorto) {
	this.propietarioNombreCorto = propietarioNombreCorto;
}

public String getVehiculoNombreCorto() {
	return vehiculoNombreCorto;
}

public void setVehiculoNombreCorto(String vehiculoNombreCorto) {
	this.vehiculoNombreCorto = vehiculoNombreCorto;
}

public String getAbreviaturaProducto() {
	return abreviaturaProducto;
}

public void setAbreviaturaProducto(String abreviaturaProducto) {
	this.abreviaturaProducto = abreviaturaProducto;
}

public String getNombreArchivoDespacho() {
	return nombreArchivoDespacho;
}

public void setNombreArchivoDespacho(String nombreArchivoDespacho) {
	this.nombreArchivoDespacho = nombreArchivoDespacho;
}

public int getFiltroCentimetros() {
	return filtroCentimetros;
}

public void setFiltroCentimetros(int filtroCentimetros) {
	this.filtroCentimetros = filtroCentimetros;
}

public int getIdTransporte() {
	return idTransporte;
}

public void setIdTransporte(int idTransporte) {
	this.idTransporte = idTransporte;
}

public int getEstadoDespachando() {
	return estadoDespachando;
}

public void setEstadoDespachando(int estadoDespachando) {
	this.estadoDespachando = estadoDespachando;
}

public int getTanqueDeApertura() {
	return tanqueDeApertura;
}

public void setTanqueDeApertura(int tanqueDeApertura) {
	this.tanqueDeApertura = tanqueDeApertura;
}

public int getTanqueDeCierre() {
	return tanqueDeCierre;
}

public void setTanqueDeCierre(int tanqueDeCierre) {
	this.tanqueDeCierre = tanqueDeCierre;
}

public int getFiltroApertura() {
	return filtroApertura;
}

public void setFiltroApertura(int filtroApertura) {
	this.filtroApertura = filtroApertura;
}

public int getFiltroCierre() {
	return filtroCierre;
}

public void setFiltroCierre(int filtroCierre) {
	this.filtroCierre = filtroCierre;
}

public int getFiltroEta() {
	return filtroEta;
}

public void setFiltroEta(int filtroEta) {
	this.filtroEta = filtroEta;
}

public int getIndicadorOperador() {
	return indicadorOperador;
}

public void setIndicadorOperador(int indicadorOperador) {
	this.indicadorOperador = indicadorOperador;
}

public String getQueryRolGec() {
	return queryRolGec;
}

public void setQueryRolGec(String queryRolGec) {
	this.queryRolGec = queryRolGec;
}

public String getFiltroEstadoGuia() {
	return filtroEstadoGuia;
}

public void setFiltroEstadoGuia(String filtroEstadoGuia) {
	this.filtroEstadoGuia = filtroEstadoGuia;
}

public String getFiltroFechaRecepcion() {
	return filtroFechaRecepcion;
}

public void setFiltroFechaRecepcion(String filtroFechaRecepcion) {
	this.filtroFechaRecepcion = filtroFechaRecepcion;
}

public String getFiltroNombreTransportista() {
	return filtroNombreTransportista;
}

public void setFiltroNombreTransportista(String filtroNombreTransportista) {
	this.filtroNombreTransportista = filtroNombreTransportista;
}

public String getFiltroNombreProducto() {
	return filtroNombreProducto;
}

public void setFiltroNombreProducto(String filtroNombreProducto) {
	this.filtroNombreProducto = filtroNombreProducto;
}

public String getFiltroNombreUsuarioEmisor() {
	return filtroNombreUsuarioEmisor;
}

public void setFiltroNombreUsuarioEmisor(String filtroNombreUsuarioEmisor) {
	this.filtroNombreUsuarioEmisor = filtroNombreUsuarioEmisor;
}

public String getFiltroNombreUsuarioAprobador() {
	return filtroNombreUsuarioAprobador;
}

public void setFiltroNombreUsuarioAprobador(String filtroNombreUsuarioAprobador) {
	this.filtroNombreUsuarioAprobador = filtroNombreUsuarioAprobador;
}

public String getFiltroInicioDespacho() {
	return filtroInicioDespacho;
}

public void setFiltroInicioDespacho(String filtroInicioDespacho) {
	this.filtroInicioDespacho = filtroInicioDespacho;
}

public String getFiltroFinDespacho() {
	return filtroFinDespacho;
}

public void setFiltroFinDespacho(String filtroFinDespacho) {
	this.filtroFinDespacho = filtroFinDespacho;
}

public String getIdCanalSap() {
	return idCanalSap;
}

public void setIdCanalSap(String idCanalSap) {
	this.idCanalSap = idCanalSap;
}

public String getIdSectorSap() {
	return idSectorSap;
}

public void setIdSectorSap(String idSectorSap) {
	this.idSectorSap = idSectorSap;
}

public String getCodInterlocutorSap() {
	return codInterlocutorSap;
}

public void setCodInterlocutorSap(String codInterlocutorSap) {
	this.codInterlocutorSap = codInterlocutorSap;
}

public String getClaveRamoSap() {
	return claveRamoSap;
}

public void setClaveRamoSap(String claveRamoSap) {
	this.claveRamoSap = claveRamoSap;
}

public int getIdPlanta() {
	return idPlanta;
}

public void setIdPlanta(int idPlanta) {
	this.idPlanta = idPlanta;
}

public int getFiltroIdProforma() {
	return filtroIdProforma;
}

public void setFiltroIdProforma(int filtroIdProforma) {
	this.filtroIdProforma = filtroIdProforma;
}

public int getIdInterlocutor() {
	return idInterlocutor;
}

public void setIdInterlocutor(int idInterlocutor) {
	this.idInterlocutor = idInterlocutor;
}

public int getIdCanalSector() {
	return idCanalSector;
}

public void setIdCanalSector(int idCanalSector) {
	this.idCanalSector = idCanalSector;
}

}
