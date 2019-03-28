package sgo.servicio;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//9000002843 unused import org.jaxen.function.SubstringAfterFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sgo.datos.AsignacionDao;
import sgo.datos.BitacoraDao;
import sgo.datos.CisternaDao;
import sgo.datos.ClienteDao;
import sgo.datos.CompartimentoDao;
import sgo.datos.ContometroDao;
import sgo.datos.ContometroJornadaDao;
import sgo.datos.DespachoCargaDao;
import sgo.datos.DespachoDao;
import sgo.datos.DetalleProgramacionDao;
import sgo.datos.DiaOperativoDao;
import sgo.datos.EnlaceDao;
import sgo.datos.EstacionDao;
import sgo.datos.EventoDao;
import sgo.datos.JornadaDao;
import sgo.datos.OperacionDao;
import sgo.datos.PerfilDetalleHorarioDao;
import sgo.datos.PlanificacionDao;
import sgo.datos.ProductoDao;
import sgo.datos.PropietarioDao;
import sgo.datos.TanqueDao;
import sgo.datos.TanqueJornadaDao;
import sgo.datos.ToleranciaDao;
import sgo.datos.TransportistaDao;
import sgo.datos.VehiculoDao;
import sgo.entidad.Bitacora;
import sgo.entidad.Contometro;
import sgo.entidad.Despacho;
import sgo.entidad.DespachoCarga;
import sgo.entidad.Enlace;
import sgo.entidad.Jornada;
import sgo.entidad.MenuGestor;
import sgo.entidad.Operacion;
import sgo.entidad.ParametrosListar;
import sgo.entidad.PerfilDetalleHorario;
import sgo.entidad.Respuesta;
import sgo.entidad.RespuestaCompuesta;
import sgo.entidad.Tanque;
import sgo.entidad.TanqueJornada;
import sgo.entidad.Tolerancia;
import sgo.entidad.Vehiculo;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.utilidades.CabeceraReporte;
import sgo.utilidades.Constante;
import sgo.utilidades.Formula;
import sgo.utilidades.Reporteador;
import sgo.utilidades.Utilidades;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DespachoControlador {
@Autowired
private MessageSource gestorDiccionario;// Gestor del diccionario de
										// mensajes
										// para la internacionalizacion
@Autowired
private BitacoraDao dBitacora; // Clase para registrar en la bitacora
								// (auditoria por accion)
@Autowired
private EnlaceDao dEnlace;
@Autowired
private MenuGestor menu;
@Autowired
private DiaOperativoDao dDiaOperativo;
@Autowired
private AsignacionDao dAsignacion;
@Autowired
private PlanificacionDao dPlanificacion;
@Autowired
private TransportistaDao dTransportista;
@Autowired
private ClienteDao dCliente;
@Autowired
private CisternaDao dCisterna;
@Autowired
private CompartimentoDao dCompartimento;
@Autowired
private OperacionDao dOperacion;
@Autowired
private ProductoDao dProducto;
@Autowired
private DespachoDao dDespacho;
@Autowired
private DiaOperativoControlador DiaOperativoControlador;
@Autowired
private DetalleProgramacionDao dDetalleProgramacion;
@Autowired
private PropietarioDao dPropietarioDao;
@Autowired
private VehiculoDao dVehiculoDao;
@Autowired
private ContometroDao dContometroDao;
@Autowired
private ContometroJornadaDao dContometroJornadaDao;
@Autowired
private TanqueDao dTanqueDao;
@Autowired
private EstacionDao dEstacion;
@Autowired
private TanqueJornadaDao dTanqueJornadaDao;
@Autowired
private DespachoCargaDao dDespachoCargaDao;
@Autowired
private JornadaDao dJornadaDao;
@Autowired
private ToleranciaDao dToleranciaDao;
@Autowired
private JornadaDao dJornada;
@Autowired
private PerfilDetalleHorarioDao dPerfilDetalleHorario;
@Autowired
ServletContext servletContext;

private DataSourceTransactionManager transaccion;// Gestor de la transaccion
// urls generales
private static final String URL_GESTION_COMPLETA = "/admin/despacho";
private static final String URL_GESTION_RELATIVA = "/despacho";
private static final String URL_GUARDAR_COMPLETA = "/admin/despacho/crear";
private static final String URL_GUARDAR_RELATIVA = "/despacho/crear";
private static final String URL_LISTAR_COMPLETA = "/admin/despacho/listar";
private static final String URL_LISTAR_RELATIVA = "/despacho/listar";
private static final String URL_ACTUALIZAR_COMPLETA = "/admin/despacho/actualizar";
private static final String URL_ACTUALIZAR_RELATIVA = "/despacho/actualizar";
private static final String URL_ACTUALIZAR_ESTADO_COMPLETA = "/admin/despacho/actualizarEstado";
private static final String URL_ACTUALIZAR_ESTADO_RELATIVA = "/despacho/actualizarEstado";
private static final String URL_RECUPERAR_COMPLETA = "/admin/despacho/recuperar";
private static final String URL_RECUPERAR_RELATIVA = "/despacho/recuperar";
private static final String URL_CARGAR_ARCHIVO_COMPLETA="/admin/despacho/cargar-archivo";
private static final String URL_CARGAR_ARCHIVO_RELATIVA="/despacho/cargar-archivo";
private static final String SEPARADOR_CSV=",";
private static final String URL_EXPORTAR_PLANTILLA_COMPLETA = "/admin/despacho/plantilla-despacho";
private static final String URL_EXPORTAR_PLANTILLA_RELATIVA = "/despacho/plantilla-despacho";

private HashMap<String, String> recuperarMapaValores(Locale locale) {
	HashMap<String, String> mapaValores = new HashMap<String, String>();
	try {
		mapaValores.put("ESTADO_ACTIVO", String.valueOf(Constante.ESTADO_ACTIVO));
	    mapaValores.put("ESTADO_INACTIVO", String.valueOf(Constante.ESTADO_INACTIVO));
	    mapaValores.put("FILTRO_TODOS", String.valueOf(Constante.FILTRO_TODOS));
	    mapaValores.put("TEXTO_INACTIVO", gestorDiccionario.getMessage("sgo.estadoInactivo",null,locale));
	    mapaValores.put("TEXTO_ACTIVO", gestorDiccionario.getMessage("sgo.estadoActivo",null,locale));
	    mapaValores.put("TEXTO_TODOS", gestorDiccionario.getMessage("sgo.filtroTodos",null,locale));
	    
	    mapaValores.put("TITULO_AGREGAR_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioAgregar",null,locale));
	    mapaValores.put("TITULO_MODIFICA_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioEditar",null,locale));
	    mapaValores.put("TITULO_DETALLE_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioVer",null,locale));
	    mapaValores.put("TITULO_LISTADO_REGISTROS", gestorDiccionario.getMessage("sgo.tituloFormularioListado",null,locale));
	    //
	    mapaValores.put("ETIQUETA_BOTON_CERRAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCerrar",null,locale));
	    mapaValores.put("ETIQUETA_BOTON_GUARDAR", gestorDiccionario.getMessage("sgo.etiquetaBotonGuardar",null,locale));

	    mapaValores.put("ETIQUETA_BOTON_AGREGAR", gestorDiccionario.getMessage("sgo.etiquetaBotonAgregar",null,locale));
	    mapaValores.put("ETIQUETA_BOTON_MODIFICAR", gestorDiccionario.getMessage("sgo.etiquetaBotonModificar",null,locale));
	    mapaValores.put("ETIQUETA_BOTON_VER", gestorDiccionario.getMessage("sgo.etiquetaBotonVer",null,locale));
	    mapaValores.put("ETIQUETA_BOTON_FILTRAR", gestorDiccionario.getMessage("sgo.etiquetaBotonFiltrar",null,locale));
	    mapaValores.put("ETIQUETA_BOTON_ACTIVAR", gestorDiccionario.getMessage("sgo.etiquetaBotonActivar",null,locale));
	    
	    mapaValores.put("ETIQUETA_BOTON_CANCELAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCancelar",null,locale));
	    mapaValores.put("ETIQUETA_BOTON_CONFIRMAR", gestorDiccionario.getMessage("sgo.etiquetaBotonConfirmar",null,locale));
	    mapaValores.put("MENSAJE_CAMBIAR_ESTADO", gestorDiccionario.getMessage("sgo.mensajeCambiarEstado",null,locale));
	    
	    mapaValores.put("MENSAJE_CARGANDO", gestorDiccionario.getMessage("sgo.mensajeCargando",null,locale));
	    mapaValores.put("MENSAJE_ANULAR_REGISTRO", gestorDiccionario.getMessage("sgo.mensajeAnularRegistro",null,locale));
	    mapaValores.put("TITULO_VENTANA_MODAL", gestorDiccionario.getMessage("sgo.tituloVentanaModal",null,locale));
	} catch (Exception ex) {

	}
	return mapaValores;
}

// @SuppressWarnings("unchecked")
@RequestMapping(URL_GESTION_RELATIVA)
public ModelAndView mostrarFormulario(Locale locale) {
	ModelAndView vista = null;
	AuthenticatedUserDetails principal = null;
	ArrayList<?> listaEnlaces = null;
	ArrayList<?> listaClientes = null;
	ArrayList<?> listaOperaciones = null;
	ArrayList<?> listaEstaciones = null;
	ArrayList<?> listaProductos = null;
	RespuestaCompuesta respuesta = null;
	ParametrosListar parametros = null;
	HashMap<String, String> mapaValores = null;
	try {
		principal = this.getCurrentUser();
		respuesta = menu.Generar(principal.getRol().getId(), URL_GESTION_COMPLETA);
		if (respuesta.estado == false) {
			throw new Exception(gestorDiccionario.getMessage("sgo.menuNoGenerado", null, locale));
		}
		listaEnlaces = (ArrayList<?>) respuesta.contenido.carga;

		parametros = new ParametrosListar();
		parametros.setPaginacion(Constante.SIN_PAGINACION);
		parametros.setFiltroEstado(Constante.FILTRO_TODOS);
		respuesta = dCliente.recuperarRegistros(parametros);
		if (respuesta.estado == false) {
			throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles", null, locale));
		}

		parametros = new ParametrosListar();
		parametros.setPaginacion(Constante.SIN_PAGINACION);
		parametros.setFiltroEstado(Constante.FILTRO_TODOS);
		parametros.setIdCliente(principal.getCliente().getId());
		parametros.setIdOperacion(principal.getOperacion().getId());
		respuesta = dOperacion.recuperarRegistros(parametros);
		if (respuesta.estado == false) {
			throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles", null, locale));
		}
		listaOperaciones = (ArrayList<?>) respuesta.contenido.carga;

		parametros = new ParametrosListar();
		parametros.setPaginacion(Constante.SIN_PAGINACION);
		// Para que retorne sÃƒÂ³lo los productos que se encuentren activos
		parametros.setFiltroEstado(Constante.ESTADO_ACTIVO);
		respuesta = dProducto.recuperarRegistros(parametros);
		if (respuesta.estado == false) {
			throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles", null, locale));
		}
		listaProductos = (ArrayList<?>) respuesta.contenido.carga;
		
		String fecha = dDiaOperativo.recuperarFechaActual().valor;
		Operacion op = (Operacion) listaOperaciones.get(0);
		parametros.setIdOperacion(op.getId());
	    //esto para obtener la Ãºltima jornada cargada 
	    Respuesta oRespuesta = dJornada.recuperarUltimaJornada(parametros);
	    // Verifica el resultado de la accion
	    if (oRespuesta.estado == false) {
	     throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles", null, locale));
	    }
	    if (oRespuesta.valor != null) {
	 	   fecha = oRespuesta.valor;
	    }
	    
	    parametros = new ParametrosListar();
		parametros.setPaginacion(Constante.SIN_PAGINACION);
		parametros.setFiltroEstado(Constante.ESTADO_ACTIVO);  //se puso estado activo por req 9000003068....antes traia todos
		parametros.setIdCliente(principal.getCliente().getId());
		parametros.setFiltroOperacion(op.getId());
		respuesta = dEstacion.recuperarRegistros(parametros);
		if (respuesta.estado == false) {
			throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles", null, locale));
		}
		listaEstaciones = (ArrayList<?>) respuesta.contenido.carga;
	    
		mapaValores = recuperarMapaValores(locale);
		vista = new ModelAndView("plantilla");
		vista.addObject("vistaJSP", "despacho/despacho.jsp");
		vista.addObject("vistaJS", "despacho/despacho.js");
		vista.addObject("identidadUsuario", principal.getIdentidad());
		vista.addObject("menu", listaEnlaces);
		vista.addObject("clientes", listaClientes);
		vista.addObject("operaciones", listaOperaciones);
		vista.addObject("estaciones", listaEstaciones);
		vista.addObject("productos", listaProductos);
		vista.addObject("mapaValores", mapaValores);
		//vista.addObject("fechaActual", dDiaOperativo.recuperarFechaActual().valor);
		vista.addObject("fechaActual", fecha);
	} catch (Exception ex) {

	}
	Utilidades.gestionaTrace("DespachoControlador", "mostrarFormulario");
	return vista;
}

@RequestMapping(value = URL_LISTAR_RELATIVA ,method = RequestMethod.GET)
public @ResponseBody RespuestaCompuesta recuperarRegistros(HttpServletRequest httpRequest, Locale locale){
	RespuestaCompuesta respuesta = null;
	ParametrosListar parametros= null;
	AuthenticatedUserDetails principal = null;
	String mensajeRespuesta="";
	try {
		//Recuperar el usuario actual
		principal = this.getCurrentUser(); 
		//Recuperar el enlace de la accion
		respuesta = dEnlace.recuperarRegistro(URL_LISTAR_COMPLETA);
		if (respuesta.estado==false){
			mensajeRespuesta = gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale);
			throw new Exception(mensajeRespuesta);
		}
		Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
		//Verificar si cuenta con el permiso necesario			
		if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
			mensajeRespuesta = gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale);
			throw new Exception(mensajeRespuesta);
		}
		//Recuperar parametros
		 parametros = new ParametrosListar();
		if (httpRequest.getParameter("registrosxPagina") != null) {
			parametros.setRegistrosxPagina(Integer.parseInt( httpRequest.getParameter("registrosxPagina")));
		}
		
		if (httpRequest.getParameter("inicioPagina") != null) {
			parametros.setInicioPaginacion(Integer.parseInt( httpRequest.getParameter("inicioPagina")));
		}
		
		if (httpRequest.getParameter("campoOrdenamiento") != null) {
			parametros.setCampoOrdenamiento(( httpRequest.getParameter("campoOrdenamiento")));
		}
		
		if (httpRequest.getParameter("sentidoOrdenamiento") != null) {
			parametros.setSentidoOrdenamiento(( httpRequest.getParameter("sentidoOrdenamiento")));
		}
		
		if (httpRequest.getParameter("valorBuscado") != null) {
			parametros.setValorBuscado(( httpRequest.getParameter("valorBuscado")));
		}
		
		if (httpRequest.getParameter("txtFiltro") != null) {
			parametros.setTxtFiltro((httpRequest.getParameter("txtFiltro")));
		}
		
		parametros.setFiltroEstado(Constante.FILTRO_TODOS);
		if (httpRequest.getParameter("filtroEstado") != null) {
			parametros.setFiltroEstado(Integer.parseInt(httpRequest.getParameter("filtroEstado")));
		}
		
		if (httpRequest.getParameter("filtroFechaJornada") != null) {
			parametros.setFiltroFechaJornada((httpRequest.getParameter("filtroFechaJornada")));
		}

		if (httpRequest.getParameter("idJornada") != null) {
			parametros.setIdJornada(Utilidades.parseInt(httpRequest.getParameter("idJornada")));
		}
		
		if (httpRequest.getParameter("idCliente") != null) {
			parametros.setIdCliente(Utilidades.parseInt(httpRequest.getParameter("idCliente")));
		}
		
		if (httpRequest.getParameter("filtroOperacion") != null) {
			parametros.setFiltroOperacion(Utilidades.parseInt(httpRequest.getParameter("filtroOperacion")));
		}

		if (httpRequest.getParameter("filtroEstacion") != null) {
			parametros.setFiltroEstacion(Utilidades.parseInt(httpRequest.getParameter("filtroEstacion")));
		}

		if (httpRequest.getParameter("filtroCodigoArchivoOrigen") != null) {
			parametros.setFiltroCodigoArchivoOrigen(Utilidades.parseInt(httpRequest.getParameter("filtroCodigoArchivoOrigen")));
		}
		
		//Recuperar registros
		respuesta = dDespacho.recuperarRegistros(parametros);
		respuesta.mensaje = gestorDiccionario.getMessage("sgo.listarExitoso", null, locale);
	} catch(Exception e) {
		e.printStackTrace();
		respuesta.estado = false;
		respuesta.contenido = null;
		respuesta.mensaje = e.getMessage();
	}
	return respuesta;
}	

@RequestMapping(value = URL_RECUPERAR_RELATIVA, method = RequestMethod.GET)
public @ResponseBody RespuestaCompuesta recuperaRegistro(int ID,Locale locale) {
	RespuestaCompuesta respuesta = null;
	AuthenticatedUserDetails principal = null;
	
	try {			
		//Recupera el usuario actual
		principal = this.getCurrentUser(); 
		//Recuperar el enlace de la accion
		respuesta = dEnlace.recuperarRegistro(URL_RECUPERAR_COMPLETA);
		if (respuesta.estado==false){
			throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
		}
		Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
		//Verificar si cuenta con el permiso necesario			
		if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
			throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
		}
		//Recuperar el registro
    	respuesta= dDespacho.recuperarRegistro(ID);
    	//Verifica el resultado de la accion
        if (respuesta.estado==false){        	
        	throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
        }
     	respuesta.mensaje=gestorDiccionario.getMessage("sgo.recuperarExitoso", null, locale);
	} catch (Exception e) {
		e.printStackTrace();
		respuesta.estado = false;
		respuesta.contenido = null;
		respuesta.mensaje = e.getMessage();
	}
	
	return respuesta;
}

@RequestMapping(value = URL_GUARDAR_RELATIVA, method = RequestMethod.POST)
public @ResponseBody
RespuestaCompuesta guardarRegistro(@RequestBody Despacho eDespacho, HttpServletRequest peticionHttp, Locale locale) {
	
	RespuestaCompuesta respuesta = null;
	AuthenticatedUserDetails principal = null;
	Bitacora eBitacora = null;
	String ContenidoAuditoria = "";
	TransactionDefinition definicionTransaccion = null;
	TransactionStatus estadoTransaccion = null;
	String direccionIp = "";
	String ClaveGenerada = "";
	
	try {
		
		//Inicia la transaccion
		this.transaccion = new DataSourceTransactionManager(dDespacho.getDataSource());
		definicionTransaccion = new DefaultTransactionDefinition();
		estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
		eBitacora = new Bitacora();
		//Recuperar el usuario actual
		principal = this.getCurrentUser(); 
		//Recuperar el enlace de la accion
		respuesta = dEnlace.recuperarRegistro(URL_GUARDAR_COMPLETA);
		if (!respuesta.estado) {
			throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
		}
		
		Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
		//Verificar si cuenta con el permiso necesario			
		if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
			throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale));
		}
		//Actualiza los datos de auditoria local
		direccionIp = peticionHttp.getHeader("X-FORWARDED-FOR");  
		if (direccionIp == null) {  
			direccionIp = peticionHttp.getRemoteAddr();  
		}
		
		if(!Utilidades.esValido(eDespacho.getFechaHoraInicio())){
			throw new Exception(gestorDiccionario.getMessage("sgo.errorFechaInicio",null,locale));
		}
		
		if(!Utilidades.esValido(eDespacho.getFechaHoraFin())){
			throw new Exception(gestorDiccionario.getMessage("sgo.errorFechaFin",null,locale));
		}
		
		RespuestaCompuesta respuestaPerfilDetalleHorario = dPerfilDetalleHorario.recuperarRegistro(eDespacho.getIdPerfilDetalleHorario());
		if (!respuestaPerfilDetalleHorario.estado) {
			throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
		}
		
		PerfilDetalleHorario ePerfilDetalleHorario = (PerfilDetalleHorario) respuestaPerfilDetalleHorario.getContenido().getCarga().get(0);
		
		Timestamp timeStampDefault = new Timestamp(eDespacho.getFechaHoraFin().getTime());
		
		/**
		 * Perfil Detalle Horario: inicio
		 */
		Calendar calPerfilDetalleHorarioInicio = Calendar.getInstance();
		calPerfilDetalleHorarioInicio.setTime(timeStampDefault);
		String perfilDetalleHorarioInicioArray[] = ePerfilDetalleHorario.getHoraInicioTurno().trim().split(":");
		calPerfilDetalleHorarioInicio.set(Calendar.HOUR_OF_DAY, Integer.parseInt(perfilDetalleHorarioInicioArray[0]));  
		calPerfilDetalleHorarioInicio.set(Calendar.MINUTE, Integer.parseInt(perfilDetalleHorarioInicioArray[1]));  
		calPerfilDetalleHorarioInicio.set(Calendar.SECOND, 0);
		
		/**
		 * Perfil Detalle Horario: fin
		 */
		Calendar calPerfilDetalleHorarioFin = Calendar.getInstance();
		calPerfilDetalleHorarioFin.setTime(timeStampDefault);
		String perfilDetalleHorarioFinArray[] = ePerfilDetalleHorario.getHoraFinTurno().trim().split(":");
		calPerfilDetalleHorarioFin.set(Calendar.HOUR_OF_DAY, Integer.parseInt(perfilDetalleHorarioFinArray[0]));  
		calPerfilDetalleHorarioFin.set(Calendar.MINUTE, Integer.parseInt(perfilDetalleHorarioFinArray[1]));  
		calPerfilDetalleHorarioFin.set(Calendar.SECOND, 0);
		
		/**
		 * Lo ingresado por el usuario
		 */
		Timestamp currentTimestampIni = new Timestamp(eDespacho.getFechaHoraInicio().getTime());
		Timestamp currentTimestampFin = new Timestamp(eDespacho.getFechaHoraFin().getTime());
		
		/**
  		 * Perfil Detalle HorarioInicio: 2 de marzo inicio
  		 */
  		// calPerfilDetalleHorarioInicio
      
  		/**
  		 * MidNight: 2 de marzo
  		 */
		Calendar calfechaMidNight = Calendar.getInstance();
		calfechaMidNight.setTime(currentTimestampIni);
		calfechaMidNight.set(Calendar.HOUR_OF_DAY, 24);  
		calfechaMidNight.set(Calendar.MINUTE, 0);
		calfechaMidNight.set(Calendar.SECOND, 0);
  		java.util.Date UUUUUUUU = calfechaMidNight.getTime();
  		
  		/**
  		 * MidNight: 3 de marzo
  		 */
		Calendar calfechaMidNightNextDay = Calendar.getInstance();
		calfechaMidNightNextDay.setTime(currentTimestampIni);
		calfechaMidNightNextDay.add(Calendar.DATE, 1);
		calfechaMidNightNextDay.set(Calendar.HOUR_OF_DAY, 0);  
		calfechaMidNightNextDay.set(Calendar.MINUTE, 0);  
		calfechaMidNightNextDay.set(Calendar.SECOND, 0);
  		java.util.Date SFFFFF = calfechaMidNightNextDay.getTime();
  		
  		/**
  		 * Perfil Detalle HorarioInicio: 3 de marzo fin
  		 */
	        Calendar calPerfilDetalleHorarioFin2 = Calendar.getInstance();
	        Timestamp ts3 = new Timestamp(calPerfilDetalleHorarioFin.getTime().getTime());
	        calPerfilDetalleHorarioFin2.setTime(ts3);
	        calPerfilDetalleHorarioFin2.add(Calendar.DATE, 1);
	        Timestamp ts4 = new Timestamp(calPerfilDetalleHorarioFin2.getTime().getTime());
	        calPerfilDetalleHorarioFin2.setTime(ts4);
	        java.util.Date UUCCBB = calPerfilDetalleHorarioFin2.getTime();
  		
	        /**
	         * despacho excel: 02 marzo INICIO
	         */
		Calendar calfechaInicio = Calendar.getInstance();
  		calfechaInicio.setTime(currentTimestampIni);
  		java.util.Date XXXXXXXXX = calfechaInicio.getTime();
	        
	        /**
	         * despacho excel: 02 marzo FIN
	         */
		Calendar calfechaFin = Calendar.getInstance();
        calfechaFin.setTime(currentTimestampFin);
        java.util.Date YYYYYYYY = calfechaFin.getTime();
		
        
        boolean x1 = calfechaInicio.after(calPerfilDetalleHorarioInicio);
        boolean x2 = calfechaInicio.before(calfechaMidNight);
        boolean x3 = calfechaInicio.equals(calPerfilDetalleHorarioInicio);
        boolean x4 = calfechaInicio.equals(calfechaMidNight);
		
        /**
         * 2 de marzo
         */
  		if ( (x1 && x2) || x3 || x4 ) {
  			eDespacho.setFechaHoraInicio(currentTimestampIni);
  		} else {
  			
  			/**
  			 * 3 de marzo
  			 */
	  			Calendar calfechaInicioOneMoreDay = Calendar.getInstance();
	  			calfechaInicioOneMoreDay.setTime(currentTimestampIni);
	  			calfechaInicioOneMoreDay.add(Calendar.DATE, 1);
  	        Timestamp tsOneMOreDay = new Timestamp(calfechaInicioOneMoreDay.getTime().getTime());
  	        calfechaInicioOneMoreDay.setTime(tsOneMOreDay);
  	        java.util.Date SSSW3WW = calfechaInicioOneMoreDay.getTime();
  			
            boolean x5 = calfechaInicioOneMoreDay.after(calfechaMidNightNextDay);
            boolean x6 = calfechaInicioOneMoreDay.before(calPerfilDetalleHorarioFin2);
            boolean x7 = calfechaInicioOneMoreDay.equals(calfechaMidNightNextDay);
            boolean x8 = calfechaInicioOneMoreDay.equals(calPerfilDetalleHorarioFin2);
  			
            if ( (x5 && x6) || x7 || x8 ) {
            	eDespacho.setFechaHoraInicio(tsOneMOreDay);
            } else {
            	throw new Exception("la Hora de Inicio esta fuera del rango predefinido de este Turno. Por favor verifique.");
            }
  		}

        boolean y1 = calfechaFin.after(calPerfilDetalleHorarioInicio);
        boolean y2 = calfechaFin.before(calfechaMidNight);
        boolean y3 = calfechaFin.equals(calPerfilDetalleHorarioInicio);
        boolean y4 = calfechaFin.equals(calfechaMidNight);
	        
        /**
         * 2 de marzo
         */
  		if ( (y1 && y2) || y3 || y4 ) {
  			eDespacho.setFechaHoraFin(currentTimestampFin);
  		} else {
  			
  			/**
  			 * 3 de marzo
  			 */
	  			Calendar calfechaFinOneMoreDay = Calendar.getInstance();
				calfechaFinOneMoreDay.setTime(currentTimestampFin);
			calfechaFinOneMoreDay.add(Calendar.DATE, 1);
  	        Timestamp tsOneMOreDay = new Timestamp(calfechaFinOneMoreDay.getTime().getTime());
  	        calfechaFinOneMoreDay.setTime(tsOneMOreDay);
  	        java.util.Date SSSW3WW2 = calfechaFinOneMoreDay.getTime();
  			
            boolean y5 = calfechaFinOneMoreDay.after(calfechaMidNightNextDay);
            boolean y6 = calfechaFinOneMoreDay.before(calPerfilDetalleHorarioFin2);
            boolean y7 = calfechaFinOneMoreDay.equals(calfechaMidNightNextDay);
            boolean y8 = calfechaFinOneMoreDay.equals(calPerfilDetalleHorarioFin2);
  			
            if ( (y5 && y6) || y7 || y8 ) {
            	eDespacho.setFechaHoraFin(tsOneMOreDay);
            } else {
            	throw new Exception("la Hora de fin esta fuera del rango predefinido de este Turno. Por favor verifique.");
            }
  		}
		
		
        /**
         * Set valores para guardar el despacho
         */
		eDespacho.setVolumenCorregidoBigDecimal(
			eDespacho.getVolumenCorregidoBigDecimal() != null ? eDespacho.getVolumenCorregidoBigDecimal() : new BigDecimal(0)
		);
		
		/**
		 * Calcular si el "Flag Calculo Corregido" debe ser 1.
		 */
		double factorCorreccion = 0;
		if (eDespacho.getApiCorregido() > 0 && eDespacho.getTemperatura() > 0) {
			factorCorreccion = Formula.calcularFactorCorreccion(eDespacho.getApiCorregido(), eDespacho.getTemperatura());
		}
		
		if ((eDespacho.getVolumenCorregidoBigDecimal() != null && eDespacho.getVolumenCorregidoBigDecimal().floatValue() > 0)
			&& factorCorreccion > 0) {
			eDespacho.setFlagCalculoCorregido(1);
		}
		//fin
		
		eDespacho.setIdContometro(eDespacho.getIdContometro());
    	eDespacho.setActualizadoEl(Calendar.getInstance().getTime().getTime());
        eDespacho.setActualizadoPor(principal.getID()); 
       	eDespacho.setCreadoEl(Calendar.getInstance().getTime().getTime());
        eDespacho.setCreadoPor(principal.getID());
        eDespacho.setIpActualizacion(direccionIp);
        eDespacho.setIpCreacion(direccionIp);
        eDespacho.setTipoRegistro(Despacho.ORIGEN_MANUAL);
        respuesta = dDespacho.guardarRegistro(eDespacho);
        
        //Verifica si la accion se ejecuto de forma satisfactoria
        if (!respuesta.estado) {     	
          	throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido", null, locale));
        }
        
        ClaveGenerada = respuesta.valor;
        //Guardar en la bitacora
        ObjectMapper mapper = new ObjectMapper(); // no need to do this if you inject via @Autowired
        ContenidoAuditoria =  mapper.writeValueAsString(eDespacho);
        eBitacora.setUsuario(principal.getNombre());
        eBitacora.setAccion(URL_GUARDAR_COMPLETA);
        eBitacora.setTabla(EventoDao.NOMBRE_TABLA);
        eBitacora.setIdentificador(ClaveGenerada);
        eBitacora.setContenido(ContenidoAuditoria);
        eBitacora.setRealizadoEl(eDespacho.getCreadoEl());
        eBitacora.setRealizadoPor(eDespacho.getCreadoPor());
        respuesta= dBitacora.guardarRegistro(eBitacora);
        
        if (!respuesta.estado) {     	
          	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
        }
        
    	respuesta.mensaje = gestorDiccionario.getMessage(
			"sgo.guardarExitoso",
			new Object[] {
				eDespacho.getFechaCreacion().substring(0, 9),
				eDespacho.getFechaCreacion().substring(10),
				principal.getIdentidad()
			},
		locale);
    	this.transaccion.commit(estadoTransaccion);
    	
	} catch (Exception e) {
		this.transaccion.rollback(estadoTransaccion);
		e.printStackTrace();
		respuesta.estado = false;
		respuesta.contenido = null;
		respuesta.mensaje = e.getMessage();
	}
	
	return respuesta;
}

@RequestMapping(value = URL_ACTUALIZAR_RELATIVA, method = RequestMethod.POST)
public @ResponseBody
RespuestaCompuesta actualizarRegistro(@RequestBody Despacho eDespacho, HttpServletRequest peticionHttp, Locale locale) {
	
	RespuestaCompuesta respuesta = null;
	AuthenticatedUserDetails principal = null;
	TransactionDefinition definicionTransaccion = null;
	TransactionStatus estadoTransaccion = null;
	Bitacora eBitacora = null;
	String direccionIp = "";
	
	try {
		
		//Inicia la transaccion
		this.transaccion = new DataSourceTransactionManager(dDespacho.getDataSource());
		definicionTransaccion = new DefaultTransactionDefinition();
		estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
		eBitacora = new Bitacora();
		//Recuperar el usuario actual
		principal = this.getCurrentUser();
		//Recuperar el enlace de la accion
		respuesta = dEnlace.recuperarRegistro(URL_ACTUALIZAR_COMPLETA);
		if (!respuesta.estado) {
			throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
		}
		
		Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
		//Verificar si cuenta con el permiso necesario			
		if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
			throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale));
		}	
		
		//Auditoria local (En el mismo registro)
		direccionIp = peticionHttp.getHeader("X-FORWARDED-FOR");  
		if (direccionIp == null) {  
			direccionIp = peticionHttp.getRemoteAddr();  
		}
		
    	eDespacho.setActualizadoEl(Calendar.getInstance().getTime().getTime());
        eDespacho.setActualizadoPor(principal.getID()); 
        eDespacho.setIpActualizacion(direccionIp);
        
        //RECUPERAR DESPACHO ANTES DE LA MODIFICACION
        respuesta = dDespacho.recuperarRegistro(eDespacho.getId());
	    if (!respuesta.estado) {
		     throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
		}
	    
	    Despacho despachoAnterior = (Despacho)respuesta.contenido.getCarga().get(0);
	    int estadoDespacho = despachoAnterior.getTipoRegistro();
	    if(estadoDespacho == Despacho.ORIGEN_FICHERO) {
	    	eDespacho.setTipoRegistro(Despacho.ORIGEN_MIXTO);
	    }
	    
		eDespacho.setVolumenCorregidoBigDecimal(
				eDespacho.getVolumenCorregidoBigDecimal() != null ? eDespacho.getVolumenCorregidoBigDecimal() : new BigDecimal(0)
		);
	    eDespacho.setFlagCalculoCorregido(despachoAnterior.getFlagCalculoCorregido());
        respuesta= dDespacho.actualizarRegistro(eDespacho);
        if (!respuesta.estado) {          	
        	throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
        }
        
        //Guardar en la bitacora
        ObjectMapper mapper = new ObjectMapper();
        eBitacora.setUsuario(principal.getNombre());
        eBitacora.setAccion(URL_ACTUALIZAR_COMPLETA);
        eBitacora.setTabla(EventoDao.NOMBRE_TABLA);
        eBitacora.setIdentificador(String.valueOf( eDespacho.getId()));
        eBitacora.setContenido( mapper.writeValueAsString(eDespacho));
        eBitacora.setRealizadoEl(eDespacho.getActualizadoEl());
        eBitacora.setRealizadoPor(eDespacho.getActualizadoPor());
        respuesta = dBitacora.guardarRegistro(eBitacora);
        if (!respuesta.estado){     	
          	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
        }
        
    	respuesta.mensaje = gestorDiccionario.getMessage(
			"sgo.actualizarExitoso",
			new Object[] {
    			eDespacho.getFechaActualizacion().substring(0, 9),
    			eDespacho.getFechaActualizacion().substring(10),
    			principal.getIdentidad()
			},
			locale);
    	this.transaccion.commit(estadoTransaccion);
    	
	} catch (Exception e) {
		e.printStackTrace();
		this.transaccion.rollback(estadoTransaccion);
		respuesta.estado = false;
		respuesta.contenido = null;
		respuesta.mensaje = e.getMessage();
	}
	
	return respuesta;
}

@RequestMapping(value = URL_ACTUALIZAR_ESTADO_RELATIVA, method = RequestMethod.POST)
public @ResponseBody
RespuestaCompuesta actualizarEstadoRegistro(@RequestBody Despacho eDespacho, HttpServletRequest peticionHttp, Locale locale) {
   RespuestaCompuesta respuesta = null;
   AuthenticatedUserDetails principal = null;
   TransactionDefinition definicionTransaccion = null;
   TransactionStatus estadoTransaccion = null;
   Bitacora eBitacora = null;
   String direccionIp = "";
   try {
	    // Inicia la transaccion
	    this.transaccion = new DataSourceTransactionManager(dDespacho.getDataSource());
	    definicionTransaccion = new DefaultTransactionDefinition();
	    estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
	    eBitacora = new Bitacora();
	    // Recuperar el usuario actual
	    principal = this.getCurrentUser();
	    // Recuperar el enlace de la accion
	    respuesta = dEnlace.recuperarRegistro(URL_ACTUALIZAR_ESTADO_COMPLETA);
	    if (respuesta.estado == false) {
	       throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
	    }
	    Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
	    // Verificar si cuenta con el permiso necesario
	    if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
	     throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
	    }
	    // Auditoria local (En el mismo registro)
	    direccionIp = peticionHttp.getHeader("X-FORWARDED-FOR");
	    if (direccionIp == null) {
	       direccionIp = peticionHttp.getRemoteAddr();
	    }
	    eDespacho.setActualizadoEl(Calendar.getInstance().getTime().getTime());
	    eDespacho.setActualizadoPor(principal.getID());
	    eDespacho.setIpActualizacion(direccionIp);
	    respuesta = dDespacho.ActualizarEstadoRegistro(eDespacho);
	    if (respuesta.estado == false) {
	     throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
	    }
	    // Guardar en la bitacora
	    ObjectMapper mapper = new ObjectMapper();
	    eBitacora.setUsuario(principal.getNombre());
	    eBitacora.setAccion(URL_ACTUALIZAR_ESTADO_COMPLETA);
	    eBitacora.setTabla(CisternaDao.NOMBRE_TABLA);
	    eBitacora.setIdentificador(String.valueOf(eDespacho.getId()));
	    eBitacora.setContenido(mapper.writeValueAsString(eDespacho));
	    eBitacora.setRealizadoEl(eDespacho.getActualizadoEl());
	    eBitacora.setRealizadoPor(eDespacho.getActualizadoPor());
	    respuesta = dBitacora.guardarRegistro(eBitacora);
	    if (respuesta.estado == false) {
	     throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
	    }
	    respuesta.mensaje = gestorDiccionario.getMessage("sgo.actualizarExitoso", new Object[] { eDespacho.getFechaActualizacion().substring(0, 9), eDespacho.getFechaActualizacion().substring(10),principal.getIdentidad() }, locale);
	    this.transaccion.commit(estadoTransaccion);
    } catch (Exception ex) {
      ex.printStackTrace();
      this.transaccion.rollback(estadoTransaccion);
      respuesta.estado = false;
      respuesta.contenido = null;
      respuesta.mensaje = ex.getMessage();
    }
 return respuesta;
}
	
@RequestMapping(value = URL_CARGAR_ARCHIVO_RELATIVA+"/{idJornada}/{idOperario}/{idTurno}/{nroDecimales}/{comentario}/{idPerfilDetalleHorario}", method = RequestMethod.POST)
public @ResponseBody RespuestaCompuesta cargarArchivo(
  @RequestParam(value="file") MultipartFile file,
  @PathVariable("idJornada") String idJornada,
  @PathVariable("idOperario") String idOperario,
  @PathVariable("idTurno") String idTurno,
  @PathVariable("nroDecimales") String nroDecimales,
  @PathVariable("comentario") String comentario,
  @PathVariable("idPerfilDetalleHorario") int idPerfilDetalleHorario,
  HttpServletRequest peticionHttp, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;
  Despacho despacho= null;
  String direccionIp="";
  TransactionDefinition definicionTransaccion = null;
  TransactionStatus estadoTransaccion = null;
  
  try {
	  
	//Inicia la transaccion
	this.transaccion = new DataSourceTransactionManager(dDespacho.getDataSource());
	definicionTransaccion = new DefaultTransactionDefinition();
	estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
	
	// 9000003068
	int nroDec = Integer.parseInt(nroDecimales);
	
    //Recupera el usuario actual
    principal = this.getCurrentUser(); 
    //Recuperar el enlace de la accion
    respuesta = dEnlace.recuperarRegistro(URL_CARGAR_ARCHIVO_COMPLETA);
    if (!respuesta.estado) {
      throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
    }
    
    Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
    //Verificar si cuenta con el permiso necesario      
    if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
        throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale));
    }
    
    ParametrosListar argumentosListar = null;
    //valido si existe una carga con el mismo archivo
    argumentosListar=new ParametrosListar();
    argumentosListar.setNombreArchivoDespacho(file.getOriginalFilename());
    argumentosListar.setIdJornada(Integer.parseInt(idJornada));
    respuesta=dDespachoCargaDao.recuperarRegistros(argumentosListar);
    if (!respuesta.estado) {  
    	throw new Exception("Error al validar la existencia del archivo de carga");
    }
    
    if(respuesta.contenido.getCarga()!=null && respuesta.contenido.getCarga().size()>0){
    	throw new Exception("Ya existe una carga del archivo "+file.getOriginalFilename());
    }    
    
    InputStream inputStream = file.getInputStream();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    String linea;
    direccionIp = peticionHttp.getHeader("X-FORWARDED-FOR");  
    if (direccionIp == null) {  
      direccionIp = peticionHttp.getRemoteAddr();  
    }
    String[] columnas= null;
    
    //Propietario propietario=null;
    Vehiculo vehiculo=null;
    Tolerancia tolerancia_producto=null;
    Contometro contometro=null;
    Tanque tanque=null;
    String sHoraInicio;
    String sHoraFin;
    String clasificacion=null;
    float volumen_observado=0;
    String sVolumen_observado=null;
    String ClaveGenerada="";
    String numero_vale=null;
    float kilometro_horometro=0;
    float factor_correccion=0;//Valor del factor.
    float lectura_inicial=0;
    float lectura_final=0;
    int numeroLineas = 0;
    float api_60=0;
    float temperatura=0;
    float volumen_corregido=0;
    
    String nombre_corto_vehiculo=null;//Nombre corto del vehÃ¯Â¿Â½culo o maquina al que se le abastece combustible.
    String abreviatura_producto=null;//Abreviatura del producto (material).
    String contrometro_alias=null;
    String descripcion_tanque=null;
    String sLectura_inicial=null;
    String sLectura_final=null;
    Jornada jornada=null;
    double factorCorreccionVolumen=0; 
    
    respuesta = dJornadaDao.recuperarRegistro(Integer.parseInt(idJornada));
    if (!respuesta.estado) {
  	  throw new Exception("Error al obtener jornada");        	  
    }
    
    if(respuesta.contenido.getCarga() != null && respuesta.contenido.getCarga().size() > 0) {
    	jornada = (Jornada)respuesta.contenido.getCarga().get(0);  	 
    }
    
    //guardar cabecera
    DespachoCarga despachoCarga=new DespachoCarga();
    despachoCarga.setNombreArchivo(file.getOriginalFilename());
    despachoCarga.setIdOperario(Integer.parseInt(idOperario));
    despachoCarga.setIdEstacion(jornada.getEstacion().getId());
    despachoCarga.setIdJornada(jornada.getId());
    despachoCarga.setFechaCarga(dDiaOperativo.recuperarFechaActualDateSql("yyyy-MM-dd"));
    byte coment[] = comentario.getBytes("ISO-8859-1");
    despachoCarga.setComentario(new String(coment, "UTF-8"));
    despachoCarga.setActualizadoEl(Calendar.getInstance().getTime().getTime());
    despachoCarga.setActualizadoPor(principal.getID()); 
    despachoCarga.setCreadoEl(Calendar.getInstance().getTime().getTime());
    despachoCarga.setCreadoPor(principal.getID());
    despachoCarga.setIpActualizacion(direccionIp);
    despachoCarga.setIpCreacion(direccionIp);
    
    //buscar jornada 
    respuesta = dDespachoCargaDao.guardarRegistro(despachoCarga);
    if (!respuesta.estado) {       
      throw new Exception("No se pudo registrar Despacho Carga.");
    }
    ClaveGenerada = respuesta.valor;
    
    /**
     * Perfil Detalle Horario
     */
    RespuestaCompuesta respuestaPerfilDetalleHorario = dPerfilDetalleHorario.recuperarRegistro(idPerfilDetalleHorario);
    if (!respuestaPerfilDetalleHorario.estado) {
    	throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
    }

    PerfilDetalleHorario ePerfilDetalleHorario = (PerfilDetalleHorario) respuestaPerfilDetalleHorario.getContenido().getCarga().get(0);
    Timestamp timeStampDefault = new Timestamp(jornada.getFechaOperativa().getTime());
    
    /**
     * Perfil Detalle Horario: inicio
     */
    Calendar calPerfilDetalleHorarioInicio = Calendar.getInstance();
    calPerfilDetalleHorarioInicio.setTime(timeStampDefault);
    String perfilDetalleHorarioInicioArray[] = ePerfilDetalleHorario.getHoraInicioTurno().trim().split(":");
    calPerfilDetalleHorarioInicio.set(Calendar.HOUR_OF_DAY, Integer.parseInt(perfilDetalleHorarioInicioArray[0]));  
    calPerfilDetalleHorarioInicio.set(Calendar.MINUTE, Integer.parseInt(perfilDetalleHorarioInicioArray[1]));  
    calPerfilDetalleHorarioInicio.set(Calendar.SECOND, 0);
    java.util.Date AAAAAAAA = calPerfilDetalleHorarioInicio.getTime();

    /**
     * Perfil Detalle Horario: fin
     */
    Calendar calPerfilDetalleHorarioFin = Calendar.getInstance();
    calPerfilDetalleHorarioFin.setTime(timeStampDefault);
    String perfilDetalleHorarioFinArray[] = ePerfilDetalleHorario.getHoraFinTurno().trim().split(":");
    calPerfilDetalleHorarioFin.set(Calendar.HOUR_OF_DAY, Integer.parseInt(perfilDetalleHorarioFinArray[0]));  
    calPerfilDetalleHorarioFin.set(Calendar.MINUTE, Integer.parseInt(perfilDetalleHorarioFinArray[1]));  
    calPerfilDetalleHorarioFin.set(Calendar.SECOND, 0);
    java.util.Date BBBBBB = calPerfilDetalleHorarioFin.getTime();
    
    boolean sameDate = calPerfilDetalleHorarioInicio.before(calPerfilDetalleHorarioFin);
    Timestamp fechaHoraFinDia1 = new Timestamp(jornada.getFechaOperativa().getTime());
    Timestamp fechaHoraFinDia2 = new Timestamp(jornada.getFechaOperativa().getTime());
    
    if (!sameDate) {
    	Calendar cal = Calendar.getInstance();
        cal.setTime(fechaHoraFinDia2);
        cal.add(Calendar.DATE, 1);
        java.util.Date dateOneMoreDay = cal.getTime();
        fechaHoraFinDia2 = new Timestamp(dateOneMoreDay.getTime());
    }
    
    
    
    /**
     * Loop CSV
     */
    int numero_columna = 0;
    
    while ((linea = bufferedReader.readLine()) != null)
    {
    	
     if (numeroLineas > 0) {
    	 
      columnas = linea.split(SEPARADOR_CSV, -1);
      numero_columna=columnas.length;
      despacho = new Despacho();
      despacho.setIdTurno(Integer.parseInt(idTurno));
      nombre_corto_vehiculo = columnas[0];
      
      if(nombre_corto_vehiculo != null && !nombre_corto_vehiculo.isEmpty()) {
          argumentosListar = new ParametrosListar();
          argumentosListar.setVehiculoNombreCorto(nombre_corto_vehiculo);   
          respuesta = dVehiculoDao.recuperarRegistros(argumentosListar);
          
          if (!respuesta.estado) {
        	  throw new Exception("Error al obtener el identificador del vehiculo :" + nombre_corto_vehiculo);        	  
          } else {
              if(respuesta.contenido.getCarga()!=null && respuesta.contenido.getCarga().size() > 0) {
            	  vehiculo=(Vehiculo)respuesta.contenido.getCarga().get(0);
            	  despacho.setIdVehiculo(vehiculo.getId());
              } else {
            	  throw new Exception("No se encontro el vehiculo con el nombre :"+nombre_corto_vehiculo);            	 
              }
          }
      } else {    	  
    	  throw new Exception("Nombre Corto Vehiculo es requerido. Fila:"+numeroLineas);    	 
      }

      if(columnas[1] != null && !columnas[1].isEmpty()){
    	kilometro_horometro = Float.parseFloat(columnas[1]); 
    	despacho.setKilometroHorometro(kilometro_horometro);
      }      
      
      numero_vale = columnas[2];   
      if (numero_vale != null && !numero_vale.isEmpty()) {
    	despacho.setNumeroVale(numero_vale);    	 
      } else {
    	throw new Exception("Numero Vale es requerido. Fila:"+numeroLineas);    	
      }
     
      sHoraInicio = columnas[3];
      sHoraFin = columnas[4];  
      String fechaOperativa = Utilidades.convierteDateAString(jornada.getFechaOperativa(), "yyyyMMdd");
      
      if (sHoraInicio!=null && !sHoraInicio.isEmpty()) {     	  
          Date fechaInicio = Utilidades.convierteStringADate(fechaOperativa+sHoraInicio, "yyyyMMddHH:mm");      
          java.sql.Timestamp currentTimestampIni = new java.sql.Timestamp(fechaInicio.getTime());
          despacho.setFechaHoraInicio(currentTimestampIni);
      } else {
    	  throw new Exception("Hora de inicio es requerido. Fila:"+numeroLineas);    	
      }
      
      if (sHoraFin!=null && !sHoraFin.isEmpty()) {
    	  Date fechaFin = Utilidades.convierteStringADate(fechaOperativa+sHoraFin, "yyyyMMddHH:mm");      
          java.sql.Timestamp currentTimestampFin = new java.sql.Timestamp(fechaFin.getTime());
          despacho.setFechaHoraFin(currentTimestampFin);   	 
      } else {
    	  throw new Exception("Hora de Fin es requerido. Fila:"+numeroLineas);    	
      }
      
      clasificacion= columnas[5];     
      if(clasificacion!=null && !clasificacion.isEmpty()) {
    	  if(Despacho.CLASIFICACION_RECIRCULADO_TEXT.equals(clasificacion.trim().toUpperCase()) || 
    			  Despacho.CLASIFICACION_TRANSFERIDO_TEXT.equals(clasificacion.trim().toUpperCase())){
    		  if(Despacho.CLASIFICACION_RECIRCULADO_TEXT.equals(clasificacion.trim().toUpperCase())){
    			  despacho.setClasificacion(String.valueOf(Despacho.CLASIFICACION_RECIRCULADO));
    		  }else{
    			  despacho.setClasificacion(String.valueOf(Despacho.CLASIFICACION_TRANSFERIDO));
    		  }    		      	 
    	  }else{
    		  throw new Exception("Ingreso un valor correcto para la Clasificacion :"+Despacho.CLASIFICACION_RECIRCULADO_TEXT +
    				  " o "+ Despacho.CLASIFICACION_TRANSFERIDO_TEXT+". Fila:"+numeroLineas);    	
    	  }
    	  
      }else{
    	throw new Exception("Clasificacion es requerido. Fila:"+numeroLineas);    	
      }
      
      abreviatura_producto=columnas[6];
      if (abreviatura_producto!=null && !abreviatura_producto.isEmpty()) {
          argumentosListar=new ParametrosListar();
          argumentosListar.setAbreviaturaProducto(abreviatura_producto);
          argumentosListar.setFiltroEstacion(jornada.getEstacion().getId());
          respuesta=dToleranciaDao.recuperarRegistros(argumentosListar);
          if (!respuesta.estado) {
        	  throw new Exception("Error al obtener el identificador del producto :"+abreviatura_producto);
          } else {
        	  if(respuesta.contenido.getCarga()!=null && respuesta.contenido.getCarga().size() > 0) {
        		  tolerancia_producto=(Tolerancia)respuesta.contenido.getCarga().get(0); 
        		  despacho.setIdProducto(tolerancia_producto.getProducto().getId());
              } else {            	  
            	  throw new Exception("No se encontro en la estacion  " + jornada.getEstacion().getNombre()+" el Producto con la abreviatura "+abreviatura_producto);            	  
              } 
          }
      } else {
    	  throw new Exception("Abreviatura Producto es requerido Fila:"+numeroLineas);
      }
      
      contrometro_alias=columnas[7];
      if (contrometro_alias != null && !contrometro_alias.isEmpty()) {
          argumentosListar=new ParametrosListar();
          argumentosListar.setTxtFiltro(contrometro_alias);
          argumentosListar.setFiltroEstacion(jornada.getEstacion().getId());
          respuesta = dContometroDao.recuperarRegistros(argumentosListar);
          if (!respuesta.estado) {
        	  throw new Exception("Error al obtener el identificador del contometros :"+contrometro_alias);
          } else {
        	  if (respuesta.contenido.getCarga()!=null && respuesta.contenido.getCarga().size() > 0) {
        		  contometro = (Contometro) respuesta.contenido.getCarga().get(0);
        		  despacho.setIdContometro(contometro.getId());
              } else {
            	  throw new Exception("No se encontro en la estacion  " + jornada.getEstacion().getNombre()+" el Contometro con el alias "+contrometro_alias);            	 
              } 
          }
      } else {
    	  throw new Exception("Alias contometro es requerido.Fila:"+numeroLineas);    	  
      }
      
      //tanque
      Date fechaInicioImportacion = Utilidades.convierteStringADate(fechaOperativa + sHoraInicio, "yyyyMMddHH:mm");
      Date fechaFinImportacion = Utilidades.convierteStringADate(fechaOperativa + sHoraFin, "yyyyMMddHH:mm");  
      
      if(fechaInicioImportacion.after(fechaFinImportacion)){
    	  //throw new Exception("la Hora de Inicio no puede ser posterior a la Hora Fin. Fila:"+numeroLineas); 
      }

      if(sHoraInicio!=null && !sHoraInicio.isEmpty()) {
    	  /*
          Date fechaInicio = Utilidades.convierteStringADate(fechaOperativa + sHoraInicio, "yyyyMMddHH:mm");      
          java.sql.Timestamp currentTimestampIni = new java.sql.Timestamp(fechaInicio.getTime());
		  despacho.setFechaHoraInicio(currentTimestampIni);
		  */
      } else {
    	throw new Exception("Hora de inicio es requerido. Fila:"+numeroLineas);    	
      }
      
      if(sHoraFin!=null && !sHoraFin.isEmpty()) {
    	  /*
    	  Date fechaFin = Utilidades.convierteStringADate(fechaOperativa+sHoraFin, "yyyyMMddHH:mm");      
          java.sql.Timestamp currentTimestampFin = new java.sql.Timestamp(fechaFin.getTime());
          despacho.setFechaHoraFin(currentTimestampFin);
          */
      } else {
    	  throw new Exception("Hora de Fin es requerido. Fila:"+numeroLineas);    	
      }
      
      if( sHoraInicio!=null && !sHoraInicio.isEmpty() && sHoraFin!=null && !sHoraFin.isEmpty() ) {
    	  
          Date fechaInicio = Utilidades.convierteStringADate(fechaOperativa + sHoraInicio, "yyyyMMddHH:mm");      
          java.sql.Timestamp currentTimestampIni = new java.sql.Timestamp(fechaInicio.getTime());
          
    	  Date fechaFin = Utilidades.convierteStringADate(fechaOperativa + sHoraFin, "yyyyMMddHH:mm");      
          java.sql.Timestamp currentTimestampFin = new java.sql.Timestamp(fechaFin.getTime());
    	  

	  		/**
	  		 * Perfil Detalle HorarioInicio: 2 de marzo inicio
	  		 */
	  		// calPerfilDetalleHorarioInicio
          
	  		/**
	  		 * MidNight: 2 de marzo
	  		 */
			Calendar calfechaMidNight = Calendar.getInstance();
			calfechaMidNight.setTime(currentTimestampIni);
			calfechaMidNight.set(Calendar.HOUR_OF_DAY, 24);  
			calfechaMidNight.set(Calendar.MINUTE, 0);  
			calfechaMidNight.set(Calendar.SECOND, 0);
	  		java.util.Date UUUUUUUU = calfechaMidNight.getTime();
	  		
	  		/**
	  		 * MidNight: 3 de marzo
	  		 */
			Calendar calfechaMidNightNextDay = Calendar.getInstance();
			calfechaMidNightNextDay.setTime(currentTimestampIni);
			calfechaMidNightNextDay.add(Calendar.DATE, 1);
			calfechaMidNightNextDay.set(Calendar.HOUR_OF_DAY, 0);  
			calfechaMidNightNextDay.set(Calendar.MINUTE, 0);  
			calfechaMidNightNextDay.set(Calendar.SECOND, 0);
	  		java.util.Date SFFFFF = calfechaMidNightNextDay.getTime();
	  		
	  		/**
	  		 * Perfil Detalle HorarioInicio: 3 de marzo fin
	  		 */
  	        Calendar calPerfilDetalleHorarioFin2 = Calendar.getInstance();
  	        Timestamp ts3 = new Timestamp(calPerfilDetalleHorarioFin.getTime().getTime());
  	        calPerfilDetalleHorarioFin2.setTime(ts3);
  	        calPerfilDetalleHorarioFin2.add(Calendar.DATE, 1);
  	        Timestamp ts4 = new Timestamp(calPerfilDetalleHorarioFin2.getTime().getTime());
  	        calPerfilDetalleHorarioFin2.setTime(ts4);
  	        java.util.Date UUCCBB = calPerfilDetalleHorarioFin2.getTime();
	  		
  	        /**
  	         * despacho excel: 02 marzo INICIO
  	         */
			Calendar calfechaInicio = Calendar.getInstance();
	  		calfechaInicio.setTime(currentTimestampIni);
	  		java.util.Date XXXXXXXXX = calfechaInicio.getTime();
  	        
  	        /**
  	         * despacho excel: 02 marzo FIN
  	         */
			Calendar calfechaFin = Calendar.getInstance();
            calfechaFin.setTime(currentTimestampFin);
            java.util.Date YYYYYYYY = calfechaFin.getTime();
            
            
            boolean x1 = calfechaInicio.after(calPerfilDetalleHorarioInicio);
            boolean x2 = calfechaInicio.before(calfechaMidNight);
            boolean x3 = calfechaInicio.equals(calPerfilDetalleHorarioInicio);
            boolean x4 = calfechaInicio.equals(calfechaMidNight);
  	        
            /**
             * 2 de marzo
             */
	  		if ( (x1 && x2) || x3 || x4 ) {
	  			despacho.setFechaHoraInicio(currentTimestampIni);
	  		} else {
	  			
	  			/**
	  			 * 3 de marzo
	  			 */
  	  			Calendar calfechaInicioOneMoreDay = Calendar.getInstance();
  	  			calfechaInicioOneMoreDay.setTime(currentTimestampIni);
  	  			calfechaInicioOneMoreDay.add(Calendar.DATE, 1);
	  	        Timestamp tsOneMOreDay = new Timestamp(calfechaInicioOneMoreDay.getTime().getTime());
	  	        calfechaInicioOneMoreDay.setTime(tsOneMOreDay);
	  	        java.util.Date SSSW3WW = calfechaInicioOneMoreDay.getTime();
	  			
	            boolean x5 = calfechaInicioOneMoreDay.after(calfechaMidNightNextDay);
	            boolean x6 = calfechaInicioOneMoreDay.before(calPerfilDetalleHorarioFin2);
	            boolean x7 = calfechaInicioOneMoreDay.equals(calfechaMidNightNextDay);
	            boolean x8 = calfechaInicioOneMoreDay.equals(calPerfilDetalleHorarioFin2);
	  			
	            if ( (x5 && x6) || x7 || x8 ) {
	            	despacho.setFechaHoraInicio(tsOneMOreDay);
	            } else {
	            	throw new Exception("la Hora de Inicio esta fuera del rango predefinido de este Turno. Por favor verifique. Fila:" + numeroLineas);
	            }
	  		}

            boolean y1 = calfechaFin.after(calPerfilDetalleHorarioInicio);
            boolean y2 = calfechaFin.before(calfechaMidNight);
            boolean y3 = calfechaFin.equals(calPerfilDetalleHorarioInicio);
            boolean y4 = calfechaFin.equals(calfechaMidNight);
  	        
            /**
             * 2 de marzo
             */
	  		if ( (y1 && y2) || y3 || y4 ) {
	  			despacho.setFechaHoraFin(currentTimestampFin);
	  		} else {
	  			
	  			/**
	  			 * 3 de marzo
	  			 */
  	  			Calendar calfechaFinOneMoreDay = Calendar.getInstance();
  				calfechaFinOneMoreDay.setTime(currentTimestampFin);
				calfechaFinOneMoreDay.add(Calendar.DATE, 1);
	  	        Timestamp tsOneMOreDay = new Timestamp(calfechaFinOneMoreDay.getTime().getTime());
	  	        calfechaFinOneMoreDay.setTime(tsOneMOreDay);
	  	        java.util.Date SSSW3WW2 = calfechaFinOneMoreDay.getTime();
	  			
	            boolean y5 = calfechaFinOneMoreDay.after(calfechaMidNightNextDay);
	            boolean y6 = calfechaFinOneMoreDay.before(calPerfilDetalleHorarioFin2);
	            boolean y7 = calfechaFinOneMoreDay.equals(calfechaMidNightNextDay);
	            boolean y8 = calfechaFinOneMoreDay.equals(calPerfilDetalleHorarioFin2);
	  			
	            if ( (y5 && y6) || y7 || y8 ) {
	            	despacho.setFechaHoraFin(tsOneMOreDay);
	            } else {
	            	throw new Exception("la Hora de fin esta fuera del rango predefinido de este Turno. Por favor verifique. Fila:" + numeroLineas);
	            }
	  		}
      }
      
      
      //buscamos el tanque que se encuentre despachando
      descripcion_tanque = columnas[8];
      ParametrosListar parametros = new ParametrosListar();
      parametros.setIdJornada(despachoCarga.getIdJornada());
      parametros.setFiltroProducto(despacho.getIdProducto());
      // 9000003068 - Añadimos el nombre del tanque como valor de busqueda. Antes solo se consideraba 1 tanque por producto y no era necesario.
      parametros.setFiltroNombreTanque(descripcion_tanque);
      String fecha = (fechaOperativa.substring(0, 4))+"-"+(fechaOperativa.substring(4,6))+"-"+(fechaOperativa.substring(6,8));
      parametros.setFiltroInicioDespacho(fecha+" "+sHoraInicio);
      
      respuesta = dTanqueJornadaDao.recuperarRegistros(parametros);
      if (!respuesta.estado) {
    	  throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido",null,locale));
      } else {
    	  if(respuesta.contenido.carga.size() > 0) {
    		  TanqueJornada eTanque = (TanqueJornada) respuesta.contenido.carga.get(0);
    		  despacho.setIdTanque(eTanque.getIdTanque());
    	  } else {
    		  parametros.setFiltroInicioDespacho("");
    		  parametros.setEstadoDespachando(TanqueJornada.ESTADO_DESPACHANDO);
    		  respuesta = dTanqueJornadaDao.recuperarRegistros(parametros);
    		  if (!respuesta.estado) {
    	    	  throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
    	      } else {
    	    	  
    	    	  if (respuesta.contenido.carga.size() <= 0) {
    	    		  throw new Exception(gestorDiccionario.getMessage("sgo.errorCampoTanque", null, locale));
    	    	  }
    	    	  
	    		  TanqueJornada eTanque = (TanqueJornada) respuesta.contenido.carga.get(0);
	    		  despacho.setIdTanque(eTanque.getIdTanque());
    	      }
    	  }
      }
      
      sVolumen_observado = (columnas[9]==null) ? "" : columnas[9];
      sLectura_inicial = (columnas[10]==null) ? "" : columnas[10];
      sLectura_final = (columnas[11]==null) ? "" : columnas[11];
      
      if(sVolumen_observado.isEmpty() && (!sLectura_inicial.isEmpty() && !sLectura_final.isEmpty())) {
    	  
          String lectInicialD = Utilidades.trailingZeros(sLectura_inicial, nroDec);
          BigDecimal lectInicialBd = Utilidades.strToBigDecimal(lectInicialD);
          despacho.setLecturaInicialBigDecimal(lectInicialBd);
    	  
          String lectFinalD = Utilidades.trailingZeros(sLectura_final, nroDec);
          BigDecimal lectFinalBd = Utilidades.strToBigDecimal(lectFinalD);
    	  despacho.setLecturaFinalBigDecimal(lectFinalBd);
    	  
    	  despacho.setVolumenObservadoBigDecimal(lectFinalBd.subtract(lectInicialBd));

      } else if (!sVolumen_observado.isEmpty() && (sLectura_inicial.isEmpty() || sLectura_final.isEmpty())) {

    	  lectura_inicial = 0;
    	  
          String lectInicialD = Utilidades.trailingZeros(lectura_inicial, nroDec);
          BigDecimal lectInicialBd = Utilidades.strToBigDecimal(lectInicialD);
          despacho.setLecturaInicialBigDecimal(lectInicialBd);
    	  
          String lectFinalD = Utilidades.trailingZeros(sVolumen_observado, nroDec);
          BigDecimal lectFinalBd = Utilidades.strToBigDecimal(lectFinalD);
    	  despacho.setLecturaFinalBigDecimal(lectFinalBd);
    	  
          String volObservadoD = Utilidades.trailingZeros(sVolumen_observado, nroDec);
          BigDecimal volObservadoBd = Utilidades.strToBigDecimal(volObservadoD);
    	  despacho.setVolumenObservadoBigDecimal(volObservadoBd);

      }else if(sVolumen_observado.isEmpty() && (sLectura_inicial.isEmpty() || sLectura_final.isEmpty())){
    	  throw new Exception("El volumen Observado o las lecturas inicial y final son requeridas. Fila:" + numeroLineas);
      }else if(!sVolumen_observado.isEmpty() && (!sLectura_inicial.isEmpty() && !sLectura_final.isEmpty())){

          String lectInicialD = Utilidades.trailingZeros(sLectura_inicial, nroDec);
          BigDecimal lectInicialBd = Utilidades.strToBigDecimal(lectInicialD);
          despacho.setLecturaInicialBigDecimal(lectInicialBd);
    	  
          String lectFinalD = Utilidades.trailingZeros(sLectura_final, nroDec);
          BigDecimal lectFinalBd = Utilidades.strToBigDecimal(lectFinalD);
    	  despacho.setLecturaFinalBigDecimal(lectFinalBd);
    	  
    	  despacho.setVolumenObservadoBigDecimal(lectFinalBd.subtract(lectInicialBd));
      }   
      
      if(columnas[13] == null || columnas[13].isEmpty()){
    	  api_60 = 0;  
      }else{
    	  api_60 = Float.parseFloat(columnas[13]);
      }      
      despacho.setApiCorregido(api_60);
      
      
      if(columnas[14] == null || columnas[14].isEmpty()){
    	  temperatura= 0;
      }else{
    	  temperatura = Float.parseFloat(columnas[14]);    	  
      }
      despacho.setTemperatura(temperatura);
      
      if (despacho.getApiCorregido()>0 && despacho.getTemperatura()>0) {
    	  factorCorreccionVolumen = Formula.calcularFactorCorreccion(despacho.getApiCorregido(), despacho.getTemperatura());
      }
      //Se agrega else por incidencia 7000002349========================
      else{
    	  factorCorreccionVolumen = 0.0;
      }
      //================================================================
      
      if(columnas[12] != null && !columnas[12].isEmpty()){
    	  factor_correccion = Float.parseFloat(columnas[12]);
    	  despacho.setFactorCorreccion(factor_correccion);
      }else {
    	  if(factorCorreccionVolumen > 0) {
    		  despacho.setFactorCorreccion((float) factorCorreccionVolumen);
    	  }
      }
      
      despacho.setVolumenCorregidoBigDecimal(new BigDecimal(0));
      if (numero_columna > 15) {
          if(columnas[15] != null && !columnas[15].isEmpty()) {

              String volCorregido = Utilidades.trailingZeros(columnas[15], nroDec);
              BigDecimal volCorregidoBd = Utilidades.strToBigDecimal(volCorregido);
              despacho.setVolumenCorregidoBigDecimal(volCorregidoBd);
              
              if (volCorregidoBd.floatValue() > 0) {
            	  despacho.setFlagCalculoCorregido(1);
              }
              
          } else {
        	  if (despacho.getFactorCorreccion() > 0) {
            	  
        		  BigDecimal volCorregidoBd = despacho.getVolumenObservadoBigDecimal().multiply(
        				Utilidades.floatToBigDecimal(despacho.getFactorCorreccion())
        		  );
                  despacho.setVolumenCorregidoBigDecimal(volCorregidoBd);
                  if (volCorregidoBd.floatValue() > 0) {
                	  despacho.setFlagCalculoCorregido(1);
                  }
        	  }
          }
      } else {
    	  if (despacho.getFactorCorreccion() > 0) {

    		  BigDecimal volCorregidoBd = despacho.getVolumenObservadoBigDecimal().multiply(
    				Utilidades.floatToBigDecimal(despacho.getFactorCorreccion())
    		  );
              despacho.setVolumenCorregidoBigDecimal(volCorregidoBd);
              if (volCorregidoBd.floatValue() > 0) {
            	  despacho.setFlagCalculoCorregido(1);
              }
    	  }
      }

      despacho.setIdJornada(jornada.getId());
      despacho.setTipoRegistro(Despacho.ORIGEN_FICHERO);
      despacho.setEstado(Despacho.ESTADO_ACTIVO);
      despacho.setCodigoArchivoOrigen(Integer.parseInt(ClaveGenerada));
      
      //auditoria
      despacho.setActualizadoEl(Calendar.getInstance().getTime().getTime());
      despacho.setActualizadoPor(principal.getID()); 
      despacho.setCreadoEl(Calendar.getInstance().getTime().getTime());
      despacho.setCreadoPor(principal.getID());
      despacho.setIpActualizacion(direccionIp);
      despacho.setIpCreacion(direccionIp);
      
      respuesta = dDespacho.guardarRegistro(despacho);
      if (!respuesta.estado){       
    	  throw new Exception("No se pudo registrar el despacho.");
      }       
      
     }
     numeroLineas++;
    }
    
    this.transaccion.commit(estadoTransaccion);
  } catch (Exception e) {
	this.transaccion.rollback(estadoTransaccion);
    e.printStackTrace();
    respuesta.estado = false;
    respuesta.contenido = null;
    respuesta.mensaje = e.getMessage();
  }
  
  return respuesta;
}

	@RequestMapping(value = URL_EXPORTAR_PLANTILLA_RELATIVA, method = RequestMethod.GET)
	public void exportarPlantillaDespacho(HttpServletRequest httpRequest, HttpServletResponse response, Locale locale) {
		RespuestaCompuesta respuesta = null;
	 	ParametrosListar parametros = null;
	 	AuthenticatedUserDetails principal = null;
	 	String mensajeRespuesta = "";
	 	try {
		 
		 	String columnas = gestorDiccionario.getMessage("sgo.plantillaDespacho.columna", null, locale);
		 	String valores = gestorDiccionario.getMessage("sgo.plantillaDespacho.valor", null, locale);
		 	String obligatorios = gestorDiccionario.getMessage("sgo.plantillaDespacho.obligatorio", null, locale);
		 	String tipos = gestorDiccionario.getMessage("sgo.plantillaDespacho.tipo", null, locale);
		 
		 	List<String> datosNotas = new ArrayList<>();
		 	datosNotas.add(columnas);
		 	datosNotas.add(valores);
		 	datosNotas.add(obligatorios);
		 	datosNotas.add(tipos);
		 
		 	// Recuperar el usuario actual
		 	principal = (AuthenticatedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	  		// Recuperar el enlace de la accion
	  		respuesta = dEnlace.recuperarRegistro(URL_EXPORTAR_PLANTILLA_COMPLETA);
	  		if (respuesta.estado == false) {
		  		mensajeRespuesta = gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale);
	   			throw new Exception(mensajeRespuesta);
	  		}
	  		Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
	  		// Verificar si cuenta con el permiso necesario
	  		if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
		  		mensajeRespuesta = gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale);
	   			throw new Exception(mensajeRespuesta);
	  		}
	  		// Recuperar parametros
	  		parametros = new ParametrosListar();
	  
	  		parametros.setFiltroEstado(Constante.ESTADO_ACTIVO);
	  		parametros.setPaginacion(Constante.SIN_PAGINACION);
	  
	  		respuesta = dProducto.recuperarRegistros(parametros);
	  
	  		if (respuesta.estado==false){
		  		mensajeRespuesta = gestorDiccionario.getMessage("sgo.noListadoRegistros", null, locale);
	   			throw new Exception(mensajeRespuesta);
	  		}
	  		ArrayList<?> listaProductos = (ArrayList<?>)  respuesta.contenido.carga;
	
	  		ByteArrayOutputStream baos = null;
	  		Reporteador uReporteador = new Reporteador();
	  		uReporteador.setRutaServlet(servletContext.getRealPath("/"));
	  		ArrayList<CabeceraReporte> listaCamposCabecera = this.generarCabecera();
	
	
	    	baos=uReporteador.generarPlantillaDespacho(listaProductos, listaCamposCabecera, datosNotas);
			try {
				
				byte[] bytes = baos.toByteArray();
				response.setContentType("application/vnd.ms-excel");
				response.addHeader ("Content-Disposition", "attachment; filename=\"plantilla-despacho.xls\"");
				response.setContentLength(bytes.length);
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write( bytes, 0, bytes.length ); 
			    ouputStream.flush();    
		    	ouputStream.close();  
			
			} catch (IOException e) {
				e.printStackTrace();
			}
	
	 	} catch (Exception ex) {
		 	ex.printStackTrace();
	 	}
	}
	
	private ArrayList<CabeceraReporte> generarCabecera(){
		  ArrayList<CabeceraReporte> listaCamposCabecera =null;
		  CabeceraReporte cabeceraReporte = null;
		  try {
			   listaCamposCabecera = new ArrayList<CabeceraReporte>();
			   //1
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Vehículo");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //2
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("K.M/Horómetro");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //3
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Nro. Vale");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //4
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Hora Inicio");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //5
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Hora Fin");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //6
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Clasificación");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //7
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Producto");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //8
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Contómetro");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //9
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Tanque");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //10
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Volumen Obs. (2)");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //11
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Lect. Inicial (1)");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //12
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Lect. Final (1)");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //13
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Factor (3)");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //14
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("API 60°F (4)");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //15
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Temperatura (°F) (4)");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
			   //16
			   cabeceraReporte = new CabeceraReporte();
			   cabeceraReporte.setEtiqueta("Vol. 60°F (5)");
			   cabeceraReporte.setColspan(1);
			   cabeceraReporte.setRowspan(1);
			   listaCamposCabecera.add(cabeceraReporte);
		  }catch(Exception ex){
		   
		  }
		  return listaCamposCabecera;
		}

private AuthenticatedUserDetails getCurrentUser() {
	return (AuthenticatedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
}

}

