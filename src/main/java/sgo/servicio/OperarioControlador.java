package sgo.servicio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import sgo.datos.BitacoraDao;
import sgo.datos.OperarioDao;
import sgo.datos.DiaOperativoDao;
import sgo.datos.EnlaceDao;
import sgo.datos.JornadaDao;
import sgo.datos.OperacionDao;
import sgo.entidad.Bitacora;
import sgo.entidad.Cliente;
import sgo.entidad.Enlace;
import sgo.entidad.Jornada;
import sgo.entidad.MenuGestor;
import sgo.entidad.Operario;
import sgo.entidad.ParametrosListar;
import sgo.entidad.Respuesta;
import sgo.entidad.RespuestaCompuesta;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;

@Controller
public class OperarioControlador {
 @Autowired
 private MessageSource gestorDiccionario;
 @Autowired
 private BitacoraDao dBitacora;
 @Autowired
 private EnlaceDao dEnlace;
 @Autowired
 private OperacionDao dOperacion;
 @Autowired
 private MenuGestor menu;
 @Autowired
 private OperarioDao dOperario;
 @Autowired
 private DiaOperativoDao dDiaOperativo;
 @Autowired
 private JornadaDao dJornada;
 //
 private DataSourceTransactionManager transaccion;// Gestor de la transaccion
 // urls generales
 private static final String URL_GESTION_COMPLETA = "/admin/operario";
 private static final String URL_GESTION_RELATIVA = "/operario";
 private static final String URL_GUARDAR_COMPLETA = "/admin/operario/crear";
 private static final String URL_GUARDAR_RELATIVA = "/operario/crear";
 private static final String URL_LISTAR_COMPLETA = "/admin/operario/listar";
 private static final String URL_LISTAR_RELATIVA = "/operario/listar";
 private static final String URL_ACTUALIZAR_COMPLETA = "/admin/operario/actualizar";
 private static final String URL_ACTUALIZAR_RELATIVA = "/operario/actualizar";
 private static final String URL_RECUPERAR_COMPLETA = "/admin/operario/recuperar";
 private static final String URL_RECUPERAR_RELATIVA = "/operario/recuperar";
 private static final String URL_ACTUALIZAR_ESTADO_COMPLETA = "/admin/operario/actualizarEstado";
 private static final String URL_ACTUALIZAR_ESTADO_RELATIVA = "/operario/actualizarEstado";
 

 private HashMap<String, String> recuperarMapaValores(Locale locale) {
  HashMap<String, String> mapaValores = new HashMap<String, String>();
  try {
   mapaValores.put("ESTADO_ACTIVO", String.valueOf(Constante.ESTADO_ACTIVO));
   mapaValores.put("ESTADO_INACTIVO", String.valueOf(Constante.ESTADO_INACTIVO));
   mapaValores.put("FILTRO_TODOS", String.valueOf(Constante.FILTRO_TODOS));
   mapaValores.put("TEXTO_INACTIVO", gestorDiccionario.getMessage("sgo.estadoInactivo", null, locale));
   mapaValores.put("TEXTO_ACTIVO", gestorDiccionario.getMessage("sgo.estadoActivo", null, locale));
   mapaValores.put("TEXTO_TODOS", gestorDiccionario.getMessage("sgo.filtroTodos", null, locale));
   mapaValores.put("TEXTO_BUSCAR", gestorDiccionario.getMessage("sgo.buscarElemento", null, locale));

   mapaValores.put("TITULO_AGREGAR_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioAgregar", null, locale));
   mapaValores.put("TITULO_MODIFICA_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioEditar", null, locale));
   mapaValores.put("TITULO_DETALLE_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioVer", null, locale));
   mapaValores.put("TITULO_LISTADO_REGISTROS", gestorDiccionario.getMessage("sgo.tituloFormularioListado", null, locale));
  
   mapaValores.put("ETIQUETA_BOTON_CERRAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCerrar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_GUARDAR", gestorDiccionario.getMessage("sgo.etiquetaBotonGuardar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_CANCELAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCancelar", null, locale));

   mapaValores.put("ETIQUETA_BOTON_AGREGAR", gestorDiccionario.getMessage("sgo.etiquetaBotonAgregar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_MODIFICAR", gestorDiccionario.getMessage("sgo.etiquetaBotonModificar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_VER", gestorDiccionario.getMessage("sgo.etiquetaBotonVer", null, locale));
   mapaValores.put("ETIQUETA_BOTON_FILTRAR", gestorDiccionario.getMessage("sgo.etiquetaBotonFiltrar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_ACTIVAR", gestorDiccionario.getMessage("sgo.etiquetaBotonActivar", null, locale));

   mapaValores.put("ETIQUETA_BOTON_CANCELAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCancelar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_CONFIRMAR", gestorDiccionario.getMessage("sgo.etiquetaBotonConfirmar", null, locale));
   mapaValores.put("MENSAJE_CAMBIAR_ESTADO", gestorDiccionario.getMessage("sgo.mensajeCambiarEstado", null, locale));

   mapaValores.put("MENSAJE_CARGANDO", gestorDiccionario.getMessage("sgo.mensajeCargando", null, locale));
   mapaValores.put("TITULO_VENTANA_MODAL", gestorDiccionario.getMessage("sgo.tituloVentanaModal", null, locale));
   mapaValores.put("TITULO_SELECCIONAR_ELEMENTO", gestorDiccionario.getMessage("sgo.seleccionarElemento", null, locale));
   

  } catch (Exception ex) {

  }
  return mapaValores;
 }

 @RequestMapping(URL_GESTION_RELATIVA)
 public ModelAndView mostrarFormulario(Locale locale) {
  ModelAndView vista = null;
  AuthenticatedUserDetails principal = null;
  ArrayList<?> listaEnlaces = null;
  ArrayList<?> listaOperaciones = null;
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
   parametros.setIdCliente(principal.getCliente().getId());
   parametros.setIdOperacion(principal.getOperacion().getId());
   respuesta = dOperacion.recuperarRegistros(parametros);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles", null, locale));
   }
   listaOperaciones = (ArrayList<?>) respuesta.contenido.carga;
   mapaValores = recuperarMapaValores(locale);
   vista = new ModelAndView("plantilla");
   vista.addObject("vistaJSP", "mantenimiento/operario.jsp");
   vista.addObject("vistaJS", "mantenimiento/operario.js");
   vista.addObject("identidadUsuario", principal.getIdentidad());
   vista.addObject("menu", listaEnlaces);
   vista.addObject("mapaValores", mapaValores);
  } catch (Exception ex) {

  }
  return vista;
 }

 @RequestMapping(value = URL_LISTAR_RELATIVA, method = RequestMethod.GET)
 public @ResponseBody
 RespuestaCompuesta recuperarRegistros(HttpServletRequest httpRequest, Locale locale) {
  RespuestaCompuesta respuesta = null;
  ParametrosListar parametros = null;
  AuthenticatedUserDetails principal = null;
  String mensajeRespuesta = "";
  try {
   // Recuperar el usuario actual
   principal = this.getCurrentUser();
   // Recuperar el enlace de la accion
   respuesta = dEnlace.recuperarRegistro(URL_LISTAR_COMPLETA);
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
   if (httpRequest.getParameter("paginacion") != null) {
    parametros.setPaginacion(Integer.parseInt(httpRequest.getParameter("paginacion")));
   }

   if (httpRequest.getParameter("registrosxPagina") != null) {
    parametros.setRegistrosxPagina(Integer.parseInt(httpRequest.getParameter("registrosxPagina")));
   }

   if (httpRequest.getParameter("inicioPagina") != null) {
    parametros.setInicioPaginacion(Integer.parseInt(httpRequest.getParameter("inicioPagina")));
   }

   if (httpRequest.getParameter("campoOrdenamiento") != null) {
    parametros.setCampoOrdenamiento((httpRequest.getParameter("campoOrdenamiento")));
   }

   if (httpRequest.getParameter("sentidoOrdenamiento") != null) {
    parametros.setSentidoOrdenamiento((httpRequest.getParameter("sentidoOrdenamiento")));
   }

   if (httpRequest.getParameter("valorBuscado") != null) {
    parametros.setValorBuscado((httpRequest.getParameter("valorBuscado")));
   }
   
   parametros.setIndicadorOperador(Constante.FILTRO_TODOS);
	if (httpRequest.getParameter("indicadorOperario") != null) {
		parametros.setIndicadorOperador(Integer.parseInt(httpRequest.getParameter("indicadorOperario")));
	}

   if (httpRequest.getParameter("txtFiltro") != null) {
	// Agregado por incidencia 7000002193
	String s_aux = httpRequest.getParameter("txtFiltro");
	System.out.println("s_aux1: " + s_aux);
	s_aux = java.net.URLDecoder.decode(s_aux, "UTF-8");
	System.out.println("s_aux2: " + s_aux);
	// ===============================================
	s_aux = s_aux.replace("'", "\\'");
    //parametros.setTxtFiltro((httpRequest.getParameter("txtFiltro")));
	parametros.setTxtFiltro(s_aux);
   }
   
   parametros.setFiltroEstado(Constante.FILTRO_TODOS);
   if (httpRequest.getParameter("filtroEstado") != null) {
    parametros.setFiltroEstado(Integer.parseInt(httpRequest.getParameter("filtroEstado")));
   }

   // Recuperar registros
   respuesta = dOperario.recuperarRegistros(parametros);
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.listarExitoso", null, locale);
  } catch (Exception ex) {
   ex.printStackTrace();
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
  }
  return respuesta;
 }

 @RequestMapping(value = URL_RECUPERAR_RELATIVA, method = RequestMethod.GET)
 public @ResponseBody
 RespuestaCompuesta recuperaRegistro(int ID, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;
  try {
   // Recupera el usuario actual
   principal = this.getCurrentUser();
   // Recuperar el enlace de la accion
   respuesta = dEnlace.recuperarRegistro(URL_RECUPERAR_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
   // Verificar si cuenta con el permiso necesario
   if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
    throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
   }
   // Recuperar el registro
   respuesta = dOperario.recuperarRegistro(ID);
   // Verifica el resultado de la accion
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
   }
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.recuperarExitoso", null, locale);
  } catch (Exception ex) {
   ex.printStackTrace();
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
  }
  return respuesta;
 }

 @RequestMapping(value = URL_GUARDAR_RELATIVA, method = RequestMethod.POST)
 public @ResponseBody
 RespuestaCompuesta guardarRegistro(@RequestBody Operario eOperario, HttpServletRequest peticionHttp, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;
  Bitacora eBitacora = null;
  String ContenidoAuditoria = "";
  TransactionDefinition definicionTransaccion = null;
  TransactionStatus estadoTransaccion = null;
  String direccionIp = "";
  String ClaveGenerada = "";
  ParametrosListar parametros = null;
  Jornada eJornada = null;
  try {
	//valida los datos que vienen del formulario
    Respuesta validacion = Utilidades.validacionXSS(eOperario, gestorDiccionario, locale);
    if (validacion.estado == false) {
      throw new Exception(validacion.valor);
    }
   // Inicia la transaccion
   this.transaccion = new DataSourceTransactionManager(dOperario.getDataSource());
   definicionTransaccion = new DefaultTransactionDefinition();
   estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
   eBitacora = new Bitacora();
   principal = this.getCurrentUser();
   respuesta = dEnlace.recuperarRegistro(URL_GUARDAR_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
   // Verificar si cuenta con el permiso necesario
   if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
    throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
   }
   // Actualiza los datos de auditoria local
   direccionIp = peticionHttp.getHeader("X-FORWARDED-FOR");
   if (direccionIp == null) {
    direccionIp = peticionHttp.getRemoteAddr();
   }
   eOperario.setEstado(Cliente.ESTADO_ACTIVO);
   eOperario.setActualizadoEl(Calendar.getInstance().getTime().getTime());
   eOperario.setActualizadoPor(principal.getID());
   eOperario.setCreadoEl(Calendar.getInstance().getTime().getTime());
   eOperario.setCreadoPor(principal.getID());
   eOperario.setIpActualizacion(direccionIp);
   eOperario.setIpCreacion(direccionIp);  
   eOperario.setEstado(Constante.ESTADO_ACTIVO);
   respuesta = dOperario.guardarRegistro(eOperario);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido", null, locale));
   }
   ClaveGenerada = respuesta.valor;
   ObjectMapper mapper = new ObjectMapper(); 
   ContenidoAuditoria = mapper.writeValueAsString(eOperario);
   eBitacora.setUsuario(principal.getNombre());
   eBitacora.setAccion(URL_GUARDAR_COMPLETA);
   eBitacora.setTabla(OperarioDao.NOMBRE_TABLA);
   eBitacora.setIdentificador(ClaveGenerada);
   eBitacora.setContenido(ContenidoAuditoria);
   eBitacora.setRealizadoEl(eOperario.getCreadoEl());
   eBitacora.setRealizadoPor(eOperario.getCreadoPor());
   if (eOperario.validar() == false) {
	    throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido", null, locale));
	   }
   respuesta = dBitacora.guardarRegistro(eBitacora);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
   }
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.guardarExitoso",
     new Object[] { eOperario.getFechaCreacion().substring(0, 9), eOperario.getFechaCreacion().substring(10), principal.getIdentidad() }, locale);
   this.transaccion.commit(estadoTransaccion);
  } catch (Exception ex) {
   this.transaccion.rollback(estadoTransaccion);
   ex.printStackTrace();
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
  }
  return respuesta;
 }

 @RequestMapping(value = URL_ACTUALIZAR_RELATIVA, method = RequestMethod.POST)
 public @ResponseBody
 RespuestaCompuesta actualizarRegistro(@RequestBody Operario eOperario, HttpServletRequest peticionHttp, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;
  TransactionDefinition definicionTransaccion = null;
  TransactionStatus estadoTransaccion = null;
  Bitacora eBitacora = null;
  String direccionIp = "";
  ParametrosListar parametros = null;
  Jornada eJornada = null;
  try {
	//valida los datos que vienen del formulario
    Respuesta validacion = Utilidades.validacionXSS(eOperario, gestorDiccionario, locale);
    if (validacion.estado == false) {
      throw new Exception(validacion.valor);
    }
   // Inicia la transaccion
   this.transaccion = new DataSourceTransactionManager(dOperario.getDataSource());
   definicionTransaccion = new DefaultTransactionDefinition();
   estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
   eBitacora = new Bitacora();
   principal = this.getCurrentUser();
   respuesta = dEnlace.recuperarRegistro(URL_ACTUALIZAR_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
   if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
    throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
   }
   direccionIp = peticionHttp.getHeader("X-FORWARDED-FOR");
   if (direccionIp == null) {
    direccionIp = peticionHttp.getRemoteAddr();
   }
   
   eOperario.setActualizadoEl(Calendar.getInstance().getTime().getTime());
   eOperario.setActualizadoPor(principal.getID());
   eOperario.setIpActualizacion(direccionIp);
   respuesta = dOperario.actualizarRegistro(eOperario);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
   }
   ObjectMapper mapper = new ObjectMapper();
   eBitacora.setUsuario(principal.getNombre());
   eBitacora.setAccion(URL_ACTUALIZAR_COMPLETA);
   eBitacora.setTabla(OperarioDao.NOMBRE_TABLA);
   eBitacora.setIdentificador(String.valueOf(eOperario.getId()));
   eBitacora.setContenido(mapper.writeValueAsString(eOperario));
   eBitacora.setRealizadoEl(eOperario.getActualizadoEl());
   eBitacora.setRealizadoPor(eOperario.getActualizadoPor());
   respuesta = dBitacora.guardarRegistro(eBitacora);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
   }
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.actualizarExitoso",
     new Object[] { eOperario.getFechaActualizacion().substring(0, 9), eOperario.getFechaActualizacion().substring(10), principal.getIdentidad() }, locale);
   ;
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
 
 @RequestMapping(value = URL_ACTUALIZAR_ESTADO_RELATIVA, method = RequestMethod.POST)
 public @ResponseBody
 RespuestaCompuesta actualizarEstadoRegistro(@RequestBody Operario eEntidad, HttpServletRequest peticionHttp, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;
  TransactionDefinition definicionTransaccion = null;
  TransactionStatus estadoTransaccion = null;
  Bitacora eBitacora = null;
  String direccionIp = "";
  try {
   // Inicia la transaccion
   this.transaccion = new DataSourceTransactionManager(dOperario.getDataSource());
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
   eEntidad.setActualizadoEl(Calendar.getInstance().getTime().getTime());
   eEntidad.setActualizadoPor(principal.getID());
   eEntidad.setIpActualizacion(direccionIp);
   respuesta = dOperario.ActualizarEstadoRegistro(eEntidad);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
   }
   // Guardar en la bitacora
   ObjectMapper mapper = new ObjectMapper();
   eBitacora.setUsuario(principal.getNombre());
   eBitacora.setAccion(URL_ACTUALIZAR_ESTADO_COMPLETA);
   eBitacora.setTabla(OperarioDao.NOMBRE_TABLA);
   eBitacora.setIdentificador(String.valueOf(eEntidad.getId()));
   eBitacora.setContenido(mapper.writeValueAsString(eEntidad));
   eBitacora.setRealizadoEl(eEntidad.getActualizadoEl());
   eBitacora.setRealizadoPor(eEntidad.getActualizadoPor());
   respuesta = dBitacora.guardarRegistro(eBitacora);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
   }
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.actualizarExitoso",
     new Object[] { eEntidad.getFechaActualizacion().substring(0, 9), eEntidad.getFechaActualizacion().substring(10), principal.getIdentidad() }, locale);
   ;
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

 private AuthenticatedUserDetails getCurrentUser() {
  return (AuthenticatedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 }
}
