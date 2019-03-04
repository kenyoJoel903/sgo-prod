package sgo.servicio;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
//import org.apache.cxf.common.logging.Log4jLogger;
//import org.jvnet.fastinfoset.stax.LowLevelFastInfosetStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
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

import sgo.datos.BitacoraDao;
import sgo.datos.CanalSectorDao;
import sgo.datos.ClienteDao;
import sgo.datos.DataMaestraClienteDao;
import sgo.datos.EnlaceDao;
import sgo.entidad.Bitacora;
import sgo.entidad.CanalSector;
import sgo.entidad.Cliente;
import sgo.entidad.Enlace;
import sgo.entidad.MenuGestor;
import sgo.entidad.ParametrosListar;
import sgo.entidad.Respuesta;
import sgo.entidad.RespuestaCompuesta;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.service.DataMaestraServicioWS;
import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@Controller
public class ClienteControlador {
	
	static {
		System.setProperty(LogFactory.FACTORY_PROPERTY,
				LogFactory.FACTORY_DEFAULT);
	}
 private static final String RESP_CLIENTE_SES = "RESP_CLIENTE_SES";
@Autowired
 private MessageSource gestorDiccionario;
 @Autowired
 private BitacoraDao dBitacora;
 @Autowired
 private EnlaceDao dEnlace;
 @Autowired
 private MenuGestor menu;
 @Autowired
 private ClienteDao dCliente;
 @Autowired
 private CanalSectorDao canalSectorDao;
 
 
 private DataMaestraServicioWS dataMaestraWS;
 @Autowired
 private DataMaestraClienteDao dataMaestraDao;
 
 protected Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
 //
 private DataSourceTransactionManager transaccion;// Gestor de la transaccion
 // urls generales
 private static final String URL_GESTION_COMPLETA = "/admin/cliente";
 private static final String URL_GESTION_RELATIVA = "/cliente";
 private static final String URL_GUARDAR_COMPLETA = "/admin/cliente/crear";
 private static final String URL_GUARDAR_RELATIVA = "/cliente/crear";
 private static final String URL_LISTAR_COMPLETA = "/admin/cliente/listar";
 private static final String URL_LISTAR_CS_COMPLETA = "/admin/cliente/listarCS";
 private static final String URL_LISTAR_RELATIVA = "/cliente/listar";
 private static final String URL_LISTAR_CS_RELATIVA = "/cliente/listarCS";
 private static final String URL_ACTUALIZAR_COMPLETA = "/admin/cliente/actualizar";
 private static final String URL_ACTUALIZAR_RELATIVA = "/cliente/actualizar";
 private static final String URL_RECUPERAR_COMPLETA = "/admin/cliente/recuperar";
 private static final String URL_RECUPERAR_RELATIVA = "/cliente/recuperar";
 private static final String URL_ACTUALIZAR_ESTADO_COMPLETA = "/admin/cliente/actualizarEstado";
 private static final String URL_ACTUALIZAR_ESTADO_RELATIVA = "/cliente/actualizarEstado";
 private static final String URL_SINCRONIZAR_COMPLETA = "/admin/cliente/sincronizar";
 private static final String URL_SINCRONIZAR_RELATIVA = "/cliente/sincronizar";
 private static final String URL_SINCRO_CANALSECTOR_RELATIVA = "/cliente/sincroCanalSector";
 private static final String URL_SINCRO_CANALSECTOR_COMPLETA = "/admin/cliente/sincroCanalSector";
 
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
   mapaValores.put("TITULO_DETALLE_REGISTRO", gestorDiccionario.getMessage("sgo.cotizacion.tituloFormularioVer", null, locale));
   mapaValores.put("TITULO_LISTADO_REGISTROS", gestorDiccionario.getMessage("sgo.tituloFormularioListado", null, locale));
   
   //
   mapaValores.put("ETIQUETA_BOTON_CERRAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCerrar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_GUARDAR", gestorDiccionario.getMessage("sgo.etiquetaBotonGuardar", null, locale));

   mapaValores.put("ETIQUETA_BOTON_AGREGAR", gestorDiccionario.getMessage("sgo.etiquetaBotonAgregar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_MODIFICAR", gestorDiccionario.getMessage("sgo.etiquetaBotonModificar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_VER", gestorDiccionario.getMessage("sgo.etiquetaBotonVer", null, locale));
   mapaValores.put("ETIQUETA_BOTON_FILTRAR", gestorDiccionario.getMessage("sgo.etiquetaBotonFiltrar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_ACTIVAR", gestorDiccionario.getMessage("sgo.etiquetaBotonActivar", null, locale));

   mapaValores.put("ETIQUETA_BOTON_CANCELAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCancelar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_CONFIRMAR", gestorDiccionario.getMessage("sgo.etiquetaBotonConfirmar", null, locale));
   mapaValores.put("MENSAJE_CAMBIAR_ESTADO", gestorDiccionario.getMessage("sgo.mensajeCambiarEstado", null, locale));
   mapaValores.put("MENSAJE_SINCRONIZAR_DATOS", gestorDiccionario.getMessage("sgo.mensajeSincronizar", null, locale));
   mapaValores.put("MENSAJE_ACTUALIZAR_SINCRONIZADO", gestorDiccionario.getMessage("sgo.mensajeActualizarSincronizado", null, locale));

   mapaValores.put("MENSAJE_CARGANDO", gestorDiccionario.getMessage("sgo.mensajeCargando", null, locale));
   mapaValores.put("TITULO_VENTANA_MODAL", gestorDiccionario.getMessage("sgo.tituloVentanaModal", null, locale));

  } catch (Exception ex) {

  }
  return mapaValores;
 }

 @RequestMapping(URL_GESTION_RELATIVA)
 public ModelAndView mostrarFormulario(HttpServletRequest peticionHttp, Locale locale) {
  ModelAndView vista = null;
  AuthenticatedUserDetails principal = null;
  ArrayList<?> listaEnlaces = null;
  RespuestaCompuesta respuesta = null;
  HashMap<String, String> mapaValores = null;
		try {
			peticionHttp.getSession(false).setAttribute(RESP_CLIENTE_SES, null);
   principal = this.getCurrentUser();
   respuesta = menu.Generar(principal.getRol().getId(), URL_GESTION_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.menuNoGenerado", null, locale));
   }
   listaEnlaces = (ArrayList<?>) respuesta.contenido.carga;
   mapaValores = recuperarMapaValores(locale);
   vista = new ModelAndView("plantilla");
   vista.addObject("vistaJSP", "mantenimiento/cliente.jsp");
   vista.addObject("vistaJS", "mantenimiento/cliente.js");
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
  try {
   // Recuperar el usuario actual
   principal = this.getCurrentUser();
   // Recuperar el enlace de la accion
   respuesta = dEnlace.recuperarRegistro(URL_LISTAR_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   // Verificar si cuenta con el permiso necesario
   verificarPermisoEnlace(respuesta, principal, locale);
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

   if (httpRequest.getParameter("txtFiltro") != null) {
    parametros.setTxtFiltro((httpRequest.getParameter("txtFiltro")));
   }

   parametros.setFiltroEstado(Constante.FILTRO_TODOS);
   if (httpRequest.getParameter("filtroEstado") != null) {
    parametros.setFiltroEstado(Integer.parseInt(httpRequest.getParameter("filtroEstado")));
   }

   // Recuperar registros
   respuesta = dCliente.recuperarRegistros(parametros);
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.listarExitoso", null, locale);
  } catch (Exception ex) {
   ex.printStackTrace();
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
  }
  return respuesta;
 }
 
	@RequestMapping(value = URL_LISTAR_CS_RELATIVA, method = RequestMethod.GET)
	public @ResponseBody
 RespuestaCompuesta recuperarRegistrosCanalSector(HttpServletRequest httpRequest, Locale locale) {
  RespuestaCompuesta respuesta = null;
  ParametrosListar parametros = null;
  AuthenticatedUserDetails principal = null;
  try {
   // Recuperar el usuario actual
   principal = this.getCurrentUser();
   // Recuperar el enlace de la accion
   respuesta = dEnlace.recuperarRegistro(URL_LISTAR_CS_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
   // Verificar si cuenta con el permiso necesario
   verificarPermisoEnlace(respuesta, principal, locale);
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

   if (httpRequest.getParameter("txtFiltro") != null) {
    parametros.setTxtFiltro((httpRequest.getParameter("txtFiltro")));
   }

			parametros.setFiltroIdUsuario(Constante.FILTRO_TODOS);
			if (httpRequest.getParameter("filtroIdUsuario") != null) {
				parametros.setFiltroIdUsuario(Integer.parseInt(httpRequest
						.getParameter("filtroIdUsuario")));
			}

			// Recuperar registros
			respuesta = canalSectorDao.recuperarRegistros(parametros);
			respuesta.mensaje = gestorDiccionario.getMessage("sgo.listarExitoso", null, locale);
  } catch (Exception ex) {
   ex.printStackTrace();
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
  }
  return respuesta;
 }
	
	@RequestMapping(value = URL_SINCRO_CANALSECTOR_RELATIVA, method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta recuperarRegistrosSyncroCanalSector(
			HttpServletRequest httpRequest, Locale locale) {
		RespuestaCompuesta respuesta = null;
		ParametrosListar parametros = null;
		AuthenticatedUserDetails principal = null;
		try {
			// Recuperar el usuario actual
			principal = this.getCurrentUser();
			// Recuperar el enlace de la accion
			respuesta = dEnlace
					.recuperarRegistro(URL_SINCRO_CANALSECTOR_COMPLETA);
			if (respuesta.estado == false) {
				throw new Exception(gestorDiccionario.getMessage(
						"sgo.accionNoHabilitada", null, locale));
			}
			// Verificar si cuenta con el permiso necesario
			verificarPermisoEnlace(respuesta, principal, locale);
			// Recuperar parametros
			parametros = new ParametrosListar();

			parametros.setFiltroIdUsuario(Constante.FILTRO_TODOS);
			if (httpRequest.getParameter("filtroIdUsuario") != null) {
				parametros.setFiltroIdUsuario(Integer.parseInt(httpRequest
						.getParameter("filtroIdUsuario")));
			}
			String direccionIp = obtenerIP(httpRequest);
			// Recuperar registros
			DT_Data_Maestra_Cliente_Proforma_Response response = (DT_Data_Maestra_Cliente_Proforma_Response) httpRequest
					.getSession(false).getAttribute(RESP_CLIENTE_SES);
			respuesta = dataMaestraDao.configurarCanalSecRespComp(response,
					configurarCliente(null, null, principal, direccionIp));

			respuesta.mensaje = gestorDiccionario.getMessage(
					"sgo.listarExitoso", null, locale);
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
   // Verificar si cuenta con el permiso necesario
   verificarPermisoEnlace(respuesta, principal, locale);
   // Recuperar el registro
   respuesta = dCliente.recuperarRegistro(ID);
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
 RespuestaCompuesta guardarRegistro(@RequestBody Cliente eCliente, HttpServletRequest peticionHttp, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;
  Bitacora eBitacora = null;
  String ContenidoAuditoria = "";
  TransactionDefinition definicionTransaccion = null;
  TransactionStatus estadoTransaccion = null;
  String direccionIp = "";
  String ClaveGenerada = "";
  try {
	//valida los datos que vienen del formulario
    Respuesta validacion = Utilidades.validacionXSS(eCliente, gestorDiccionario, locale);
    if (validacion.estado == false) {
      throw new Exception(validacion.valor);
    }
   // Inicia la transaccion
   this.transaccion = new DataSourceTransactionManager(dCliente.getDataSource());
   definicionTransaccion = new DefaultTransactionDefinition();
   estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
   eBitacora = new Bitacora();
   principal = this.getCurrentUser();
   respuesta = dEnlace.recuperarRegistro(URL_GUARDAR_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   // Verificar si cuenta con el permiso necesario
   verificarPermisoEnlace(respuesta, principal, locale);
			// configura lista de CanalSector
			eCliente = configurarDescripciones(eCliente);
			// Actualiza los datos de auditoria local
			configurarCliente(eCliente, null, principal, direccionIp);

			// guardamos el registro
			respuesta = dCliente.guardarRegistro(eCliente);
			// actualizamos las tablas sap
			eCliente.setId(Integer.parseInt(respuesta.valor));
			DT_Data_Maestra_Cliente_Proforma_Response response = (DT_Data_Maestra_Cliente_Proforma_Response) peticionHttp
					.getSession(false).getAttribute(RESP_CLIENTE_SES);
			dataMaestraDao.guardarRegistro(eCliente, response);
			peticionHttp.getSession(false).setAttribute(RESP_CLIENTE_SES, null);
   
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido", null, locale));
   }
   ClaveGenerada = respuesta.valor;
   ObjectMapper mapper = new ObjectMapper(); 
   ContenidoAuditoria = mapper.writeValueAsString(eCliente);
   eBitacora.setUsuario(principal.getNombre());
   eBitacora.setAccion(URL_GUARDAR_COMPLETA);
   eBitacora.setTabla(ClienteDao.NOMBRE_TABLA);
   eBitacora.setIdentificador(ClaveGenerada);
   eBitacora.setContenido(ContenidoAuditoria);
   eBitacora.setRealizadoEl(eCliente.getCreadoEl());
   eBitacora.setRealizadoPor(eCliente.getCreadoPor());
   respuesta = dBitacora.guardarRegistro(eBitacora);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
   }
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.guardarExitoso", new Object[] { eCliente.getFechaCreacion().substring(0, 9), eCliente.getFechaCreacion().substring(10), principal.getIdentidad() }, locale);
   this.transaccion.commit(estadoTransaccion);
  } catch (Exception ex) {
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
   ex.printStackTrace();
   this.transaccion.rollback(estadoTransaccion);
  }
  return respuesta;
 }

 @RequestMapping(value = URL_ACTUALIZAR_RELATIVA, method = RequestMethod.POST)
 public @ResponseBody
 RespuestaCompuesta actualizarRegistro(@RequestBody Cliente eCliente, HttpServletRequest peticionHttp, Locale locale) {
  RespuestaCompuesta respuesta = new RespuestaCompuesta();
  AuthenticatedUserDetails principal = null;
  TransactionDefinition definicionTransaccion = null;
  TransactionStatus estadoTransaccion = null;
  Bitacora eBitacora = null;
  String direccionIp = "";
  try {
	//valida los datos que vienen del formulario
    Respuesta validacion = Utilidades.validacionXSS(eCliente, gestorDiccionario, locale);
    if (validacion.estado == false) {
      throw new Exception(validacion.valor);
    }
   // Inicia la transaccion
   this.transaccion = new DataSourceTransactionManager(dCliente.getDataSource());
   definicionTransaccion = new DefaultTransactionDefinition();
   estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
   eBitacora = new Bitacora();
   principal = this.getCurrentUser();
   respuesta = dEnlace.recuperarRegistro(URL_ACTUALIZAR_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   verificarPermisoEnlace(respuesta, principal, locale);
   direccionIp = peticionHttp.getHeader("X-FORWARDED-FOR");
   if (direccionIp == null) {
    direccionIp = peticionHttp.getRemoteAddr();
   }
   eCliente.setActualizadoEl(Calendar.getInstance().getTime().getTime());
   eCliente.setActualizadoPor(principal.getID());
   eCliente.setIpActualizacion(direccionIp);
/*   //esto para validar la entidad
   Respuesta validaEntidad = eCliente.validar(gestorDiccionario, locale);
   if (validaEntidad.estado == false) {
     throw new Exception(validaEntidad.valor);
   }*/
   
			eCliente = configurarDescripciones(eCliente);
			// actualizamos el registro
			respuesta = dCliente.actualizarRegistro(eCliente);
			// actualizamos las tablas sap si se consultaron
			DT_Data_Maestra_Cliente_Proforma_Response response = (DT_Data_Maestra_Cliente_Proforma_Response) peticionHttp
					.getSession(false).getAttribute(RESP_CLIENTE_SES);
			if (response != null) {
				dataMaestraDao.guardarRegistro(eCliente, response);
				peticionHttp.getSession(false).setAttribute(RESP_CLIENTE_SES, null);
			} else {
				// actualizamos las descripciones de sectorCanal
				canalSectorDao.actualizarDescripcionCanalSector(eCliente);
			}
  
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
   }
   ObjectMapper mapper = new ObjectMapper();
   eBitacora.setUsuario(principal.getNombre());
   eBitacora.setAccion(URL_ACTUALIZAR_COMPLETA);
   eBitacora.setTabla(ClienteDao.NOMBRE_TABLA);
   eBitacora.setIdentificador(String.valueOf(eCliente.getId()));
   eBitacora.setContenido(mapper.writeValueAsString(eCliente));
   eBitacora.setRealizadoEl(eCliente.getActualizadoEl());
   eBitacora.setRealizadoPor(eCliente.getActualizadoPor());
   respuesta = dBitacora.guardarRegistro(eBitacora);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
   }
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.actualizarExitoso",
     new Object[] { eCliente.getFechaActualizacion().substring(0, 9), eCliente.getFechaActualizacion().substring(10), principal.getIdentidad() }, locale);
   ;
   this.transaccion.commit(estadoTransaccion);
  } catch (Exception ex) {
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
   ex.printStackTrace();
   this.transaccion.rollback(estadoTransaccion);
  }
  return respuesta;
 }

@RequestMapping(value = URL_ACTUALIZAR_ESTADO_RELATIVA, method = RequestMethod.POST)
 public @ResponseBody
 RespuestaCompuesta actualizarEstadoRegistro(@RequestBody Cliente eEntidad, HttpServletRequest peticionHttp, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;
  TransactionDefinition definicionTransaccion = null;
  TransactionStatus estadoTransaccion = null;
  Bitacora eBitacora = null;
  String direccionIp = "";
  try {
   // Inicia la transaccion
   this.transaccion = new DataSourceTransactionManager(dCliente.getDataSource());
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
   // Verificar si cuenta con el permiso necesario
   verificarPermisoEnlace(respuesta, principal, locale);
   // Auditoria local (En el mismo registro)
   direccionIp = peticionHttp.getHeader("X-FORWARDED-FOR");
   if (direccionIp == null) {
    direccionIp = peticionHttp.getRemoteAddr();
   }
   eEntidad.setActualizadoEl(Calendar.getInstance().getTime().getTime());
   eEntidad.setActualizadoPor(principal.getID());
   eEntidad.setIpActualizacion(direccionIp);
   respuesta = dCliente.ActualizarEstadoRegistro(eEntidad);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
   }
   // Guardar en la bitacora
   ObjectMapper mapper = new ObjectMapper();
   eBitacora.setUsuario(principal.getNombre());
   eBitacora.setAccion(URL_ACTUALIZAR_ESTADO_COMPLETA);
   eBitacora.setTabla(ClienteDao.NOMBRE_TABLA);
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
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
   ex.printStackTrace();
   this.transaccion.rollback(estadoTransaccion);
  }
  return respuesta;
 }

	@RequestMapping(value = URL_SINCRONIZAR_RELATIVA, method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta sincronizarCliente(
			String codigoSap, HttpServletRequest peticionHttp, Locale locale) {
		System.out.println("codigoSap:" + codigoSap);
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		try {
			// Recupera el usuario actual
			principal = this.getCurrentUser();
			// Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_SINCRONIZAR_COMPLETA);
			if (respuesta.estado == false) {
				throw new Exception(gestorDiccionario.getMessage(
						"sgo.accionNoHabilitada", null, locale));
			}
			verificarPermisoEnlace(respuesta, principal, locale);
			// Recuperar el registro
			// Actualiza los datos de auditoria local
			String direccionIp = obtenerIP(peticionHttp);
			Cliente eCliente = configurarCliente(null, codigoSap, principal,
					direccionIp);

			// respuesta = dCliente.sincronizar(eCliente);
			dataMaestraWS = new DataMaestraServicioWS();
			DT_Data_Maestra_Cliente_Proforma_Response response = new DT_Data_Maestra_Cliente_Proforma_Response();
			respuesta = dataMaestraWS.consultar(eCliente, response);
			
//			Proforma_BC_Data_Maestra_Cliente_SI_Data_Maestra_Cliente_OutLocator servicio = new Proforma_BC_Data_Maestra_Cliente_SI_Data_Maestra_Cliente_OutLocator();
//			SI_Data_Maestra_Cliente_Out puerto = servicio.getHTTP_Port();
//			
//			DT_Data_Maestra_Cliente_RequestEntradaCodCliente_In[][] req = new DT_Data_Maestra_Cliente_RequestEntradaCodCliente_In[1][1];
//			DT_Data_Maestra_Cliente_RequestEntradaCodCliente_In req1 = new DT_Data_Maestra_Cliente_RequestEntradaCodCliente_In();
//			req1.setSign("I");
//			req1.setOption("EQ");
//			req1.setHigh(eCliente.getCodigoSAP());
//			req1.setLow(eCliente.getCodigoSAP());
//			
//			req[0][0] = req1;
//			DT_Data_Maestra_Cliente_Proforma_Response[] res = puerto.SI_Data_Maestra_Cliente_Out(req);

//		      Petroperu_BC_SWCC_ConsInformacionEntregas_OutLocator servicio = new Petroperu_BC_SWCC_ConsInformacionEntregas_OutLocator();     
//		       puerto = servicio.getHTTP_Port();
//		       resultado = puerto.consInformacionEntregas_Out(parametrosSAP);
		       
		       
			// Verifica el resultado de la accion
			if (respuesta.estado == false) {
				throw new Exception(gestorDiccionario.getMessage(
						"sgo.recuperarFallido", null, locale));
			}
			// subiendo a sesion
			peticionHttp.getSession(false).setAttribute(RESP_CLIENTE_SES,
					response);
			respuesta.mensaje = gestorDiccionario.getMessage(
					"sgo.recuperarExitoso", null, locale);
		} catch (Exception ex) {
			ex.printStackTrace();
			respuesta.estado = false;
			respuesta.contenido = null;
			respuesta.mensaje = ex.getMessage();
		}
		return respuesta;
	}

	private String obtenerIP(HttpServletRequest peticionHttp) {
		String direccionIp = peticionHttp.getHeader("X-FORWARDED-FOR");
		if (direccionIp == null) {
			direccionIp = peticionHttp.getRemoteAddr();
		}
		return direccionIp;
	}

	/** Verificar si cuenta con el permiso necesario
	 * @param respuesta
	 * @param principal
	 * @param locale
	 * @throws Exception
	 * @throws NoSuchMessageException
	 */
	private void verificarPermisoEnlace(RespuestaCompuesta respuesta,
			AuthenticatedUserDetails principal, Locale locale)
			throws Exception, NoSuchMessageException {
		Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga()
				.get(0);
		if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
			throw new Exception(gestorDiccionario.getMessage(
					"sgo.faltaPermiso", null, locale));
		}
	}

	private Cliente configurarCliente(Cliente eCliente, String codigoSap,
			AuthenticatedUserDetails principal, String direccionIp) {
		if(eCliente == null){
			eCliente = new Cliente();
		}
		if (codigoSap != null) {
			eCliente.setCodigoSAP(codigoSap);
		}
		eCliente.setEstado(Cliente.ESTADO_ACTIVO);
		eCliente.setActualizadoEl(Calendar.getInstance().getTime().getTime());
		eCliente.setActualizadoPor(principal.getID());
		eCliente.setCreadoEl(Calendar.getInstance().getTime().getTime());
		eCliente.setCreadoPor(principal.getID());
		eCliente.setIpActualizacion(direccionIp);
		eCliente.setIpCreacion(direccionIp);
		return eCliente;
	}

	/** Se configura la lista de descripciones
	 * @param eCliente
	 * @return
	 */
	private Cliente configurarDescripciones(Cliente eCliente) {
		if (StringUtils.isNotBlank(eCliente.getDescripcionCS())) {
			Type listType = new TypeToken<ArrayList<CanalSector>>() {
			}.getType();
			try {
				List<CanalSector> lista = gson.fromJson(
						eCliente.getDescripcionCS(), listType);
				eCliente.setListaDescripciones(lista);
			} catch (JsonSyntaxException e) {

			}
		}
		return eCliente;
	}
	
	@RequestMapping(value = "/cliente/limpiar", method = RequestMethod.GET)
	public @ResponseBody void limpiar(HttpServletRequest peticionHttp,
			Locale locale) {
		System.out.println("limpiar");
		// subiendo a sesion
		peticionHttp.getSession(false).setAttribute(RESP_CLIENTE_SES, null);
	}

 private AuthenticatedUserDetails getCurrentUser() {
  return (AuthenticatedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 }
 
}
