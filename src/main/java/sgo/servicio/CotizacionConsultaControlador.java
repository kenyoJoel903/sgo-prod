package sgo.servicio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
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
import sgo.datos.ClienteDao;
import sgo.datos.DataMaestraClienteDao;
import sgo.datos.EnlaceDao;
import sgo.datos.ProformaDao;
import sgo.datos.ReporteDao;
import sgo.entidad.Bitacora;
import sgo.entidad.CanalSector;
import sgo.entidad.Cliente;
import sgo.entidad.DatosInterlocutor;
import sgo.entidad.Enlace;
import sgo.entidad.MenuGestor;
import sgo.entidad.ParametrosListar;
import sgo.entidad.Planta;
import sgo.entidad.Producto;
import sgo.entidad.Proforma;
import sgo.entidad.Respuesta;
import sgo.entidad.RespuestaCompuesta;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.service.SimularCrearProformaServicioWS;
import sgo.utilidades.Constante;
import sgo.utilidades.Reporteador;
import sgo.utilidades.Utilidades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class CotizacionConsultaControlador {
	
	static {
		System.setProperty(LogFactory.FACTORY_PROPERTY,
				LogFactory.FACTORY_DEFAULT);
	}
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
	private ProformaDao proformaDao;

	private SimularCrearProformaServicioWS dataSimularCrearWS;
	@Autowired
	private DataMaestraClienteDao dataMaestraDao;

	@Autowired
	private ReporteDao dReporte;
	@Autowired
	private ServletContext servletContext;
	private List<Object> data;
	private Class clase;
	 
	protected Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
	//
	private DataSourceTransactionManager transaccion;// Gestor de la transaccion
	// urls generales
	private static final String URL_GESTION_COMPLETA = "/admin/cotizacion";
	private static final String URL_GESTION_RELATIVA = "/cotizacion";
	private static final String URL_LISTAR_COMPLETA = "/admin/cotizacion/listar";
	private static final String URL_LISTAR_RELATIVA = "/cotizacion/listar";
	private static final String URL_LISTAR_DETALLE_COMPLETA = "/admin/cotizacion/listarDetalle";
	private static final String URL_LISTAR_DETALLE_RELATIVA = "/cotizacion/listarDetalle";
	private static final String URL_GENERAR_COMPLETA = "/admin/cotizacion/generar";
	private static final String URL_GENERAR_RELATIVA = "/cotizacion/generar";
	private static final String URL_RECUPERAR_COMPLETA = "/admin/cotizacion/recuperar";
	private static final String URL_RECUPERAR_RELATIVA = "/cotizacion/recuperar";
	private static final String URL_LISTAR_PLANTA_RELATIVA = "/cotizacion/listarPlantaHab"; 
	private static final String URL_LISTAR_PLANTA_COMPLETA = "/admin/cotizacion/listarPlantaHab";
	private static final String URL_LISTAR_PRODUCTO_RELATIVA = "/cotizacion/listarProductoHab";
	private static final String URL_LISTAR_PRODUCTO_COMPLETA = "/admin/cotizacion/listarProductoHab";
	private static final String URL_LISTAR_INTERLOCUTOR_COMPLETA = "/admin/cotizacion/listarInterlocutor";
	private static final String URL_LISTAR_INTERLOCUTOR_RELATIVA = "/cotizacion/listarInterlocutor";
	private static final String URL_LISTAR_CANALSECTOR_RELATIVA = "/cotizacion/listarCanalSector";
	private static final String URL_LISTAR_CANALSECTOR_COMPLETA = "/admin/cotizacion/listarCanalSector";

	private static final String URL_LISTAR_CLIENTE_COMPLETA = "/admin/cotizacion/listarClientes";
	private static final String URL_LISTAR_CLIENTE_RELATIVA = "/cotizacion/listarClientes";
	private static final String URL_REPORTE_COTIZACION_COMPLETA = "/admin/cotizacion/crear-reporte";
	private static final String URL_REPORTE_COTIZACION_RELATIVA = "/cotizacion/crear-reporte";
	
	

 private HashMap<String, String> recuperarMapaValores(Locale locale) {
  HashMap<String, String> mapaValores = new HashMap<String, String>();
  try {
   mapaValores.put("FILTRO_TODOS", String.valueOf(Constante.FILTRO_TODOS));
   mapaValores.put("TEXTO_INACTIVO", gestorDiccionario.getMessage("sgo.estadoInactivo", null, locale));
   mapaValores.put("TEXTO_ACTIVO", gestorDiccionario.getMessage("sgo.estadoActivo", null, locale));
   mapaValores.put("TEXTO_TODOS", gestorDiccionario.getMessage("sgo.filtroTodos", null, locale));
   mapaValores.put("TEXTO_BUSCAR", gestorDiccionario.getMessage("sgo.buscarElemento", null, locale));

   mapaValores.put("TITULO_AGREGAR_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioAgregar", null, locale));
   mapaValores.put("TITULO_MODIFICA_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioEditar", null, locale));
   mapaValores.put("TITULO_DETALLE_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioVer", null, locale));
   mapaValores.put("TITULO_LISTADO_REGISTROS", gestorDiccionario.getMessage("sgo.tituloFormularioListado", null, locale));
   
   //
   mapaValores.put("ETIQUETA_BOTON_CERRAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCerrar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_SIMULAR", gestorDiccionario.getMessage("sgo.cotizacion.etiquetaBotonSimular", null, locale));
   mapaValores.put("ETIQUETA_BOTON_GENERAR", gestorDiccionario.getMessage("sgo.cotizacion.etiquetaBotonGenerar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_AGREGAR", gestorDiccionario.getMessage("sgo.cotizacion.etiquetaBotonAgregar", null, locale));

   mapaValores.put("ETIQUETA_BOTON_AGREGAR", gestorDiccionario.getMessage("sgo.etiquetaBotonAgregar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_VER", gestorDiccionario.getMessage("sgo.etiquetaBotonVer", null, locale));
   mapaValores.put("ETIQUETA_BOTON_FILTRAR", gestorDiccionario.getMessage("sgo.etiquetaBotonFiltrar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_EXPORTAR", gestorDiccionario.getMessage("sgo.etiquetaBotonImprimirPDF", null, locale));
   
   mapaValores.put("ETIQUETA_BOTON_CANCELAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCancelar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_NO", gestorDiccionario.getMessage("sgo.etiquetaBotonNo", null, locale));
   mapaValores.put("ETIQUETA_BOTON_CONFIRMAR", gestorDiccionario.getMessage("sgo.etiquetaBotonSi", null, locale));
   mapaValores.put("MENSAJE_CAMBIO_COMBOS", gestorDiccionario.getMessage("sgo.cotizacion.mensajeconfirmaCambioCombos", null, locale));

   mapaValores.put("MENSAJE_CARGANDO", gestorDiccionario.getMessage("sgo.mensajeCargando", null, locale));
   mapaValores.put("TITULO_VENTANA_MODAL", gestorDiccionario.getMessage("sgo.tituloVentanaModal", null, locale));

  } catch (Exception ex) {
	  ex.printStackTrace();
  }
  return mapaValores;
 }

	@RequestMapping(URL_GESTION_RELATIVA)
	public ModelAndView mostrarFormulario(Locale locale) {
		ModelAndView vista = null;
		AuthenticatedUserDetails principal = null;
		ArrayList<?> listaEnlaces = null;
		RespuestaCompuesta respuesta = null;
		HashMap<String, String> mapaValores = null;
		try {
			principal = this.getCurrentUser();
			respuesta = menu.Generar(principal.getRol().getId(), URL_GESTION_COMPLETA);
			if (respuesta.estado == false) {
				throw new Exception(gestorDiccionario.getMessage("sgo.menuNoGenerado", null, locale));
			}
			listaEnlaces = (ArrayList<?>) respuesta.contenido.carga;
			Cliente clienteUnico = esAdminComercial(principal);

			mapaValores = recuperarMapaValores(locale);
			vista = new ModelAndView("plantilla");
			vista.addObject("vistaJSP", "cotizaciones/listar/cotizacion.jsp");
			vista.addObject("vistaJS", "cotizaciones/listar/cotizacion.js");
			vista.addObject("identidadUsuario", principal.getIdentidad());
			vista.addObject("menu", listaEnlaces);
			vista.addObject("mapaValores", mapaValores);
			if(clienteUnico != null){
				vista.addObject("esAdminComercial", false);
				vista.addObject("idCliente", clienteUnico.getId());
				vista.addObject("rzCliente", clienteUnico.getRazonSocial());
			}else{
				vista.addObject("esAdminComercial", true);
			}
			vista.addObject("fechaActual", Utilidades.convierteDateAString(new Date(), Constante.FORMATO_FECHA_DDMMYYYY));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return vista;
	}

	/** verifica si el usuario se le puede permitir abrir el combo de clientes
	 * @param principal
	 * @param locale
	 * @return
	 */
	private Cliente esAdminComercial(AuthenticatedUserDetails principal) {
		Cliente esAdminComercial = null;
		
		esAdminComercial = proformaDao.getClienteAsignado(principal.getID());
		return esAdminComercial;
	}

	@RequestMapping(value = URL_LISTAR_RELATIVA, method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta recuperarRegistros(HttpServletRequest httpRequest, Locale locale) {
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
				parametros.setSentidoOrdenamiento(httpRequest.getParameter("sentidoOrdenamiento"));
			}

			if (httpRequest.getParameter("filtroFechaInicio") != null) {
				parametros.setFiltroFechaInicio(httpRequest.getParameter("filtroFechaInicio"));
			}
			if (httpRequest.getParameter("filtroFechaFinal") != null) {
				parametros.setFiltroFechaFinal(httpRequest.getParameter("filtroFechaFinal"));
			}
			if (StringUtils.isNotBlank(httpRequest.getParameter("idCliente"))) {
				parametros.setIdCliente(Integer.parseInt(httpRequest.getParameter("idCliente")));
			}

			// Recuperar registros
			respuesta = proformaDao.recuperarRegistros(parametros);
			respuesta.mensaje = gestorDiccionario.getMessage("sgo.listarExitoso", null, locale);
		} catch (Exception ex) {
			ex.printStackTrace();
			respuesta.estado = false;
			respuesta.contenido = null;
			respuesta.mensaje = ex.getMessage();
		}
		return respuesta;
	}
 

	/**
	 * Usado para llenar el combo de clientes
	 * 
	 * @param httpRequest
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = URL_LISTAR_CLIENTE_RELATIVA, method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta recuperarClientes(HttpServletRequest httpRequest, Locale locale) {
		RespuestaCompuesta respuesta = null;
		ParametrosListar parametros = null;
		AuthenticatedUserDetails principal = null;
		try {
			// Recuperar el usuario actual
			principal = this.getCurrentUser();
			// Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_LISTAR_CLIENTE_COMPLETA);
			if (respuesta.estado == false) {
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
			}
			// Verificar si cuenta con el permiso necesario
			verificarPermisoEnlace(respuesta, principal, locale);
			// Recuperar parametros
			parametros = new ParametrosListar();
			parametros.setPaginacion(Constante.SIN_PAGINACION);

			// Recuperar registros. tambien devolver la fecha y el codigo de proforma
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
	
	/**
	 * Usado para llenar el combo de canal sector
	 * 
	 * @param httpRequest
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = URL_LISTAR_CANALSECTOR_RELATIVA, method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta listarCanalSector(int id, HttpServletRequest httpRequest, Locale locale) {
		// Recuperar parametros
		ParametrosListar parametros = null;
		parametros = new ParametrosListar();
		parametros.setIdCliente(id);

		parametros.setPaginacion(Constante.SIN_PAGINACION);
		return obtenerTodos(parametros, locale, URL_LISTAR_CANALSECTOR_COMPLETA);
	}

	/**
	 * Usado para llenar el combo de canal sector
	 * 
	 * @param httpRequest
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = URL_LISTAR_INTERLOCUTOR_RELATIVA, method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta listarInterlocutor(int id,String codCanal, String codSector, HttpServletRequest httpRequest, Locale locale) {
		// Recuperar parametros
		ParametrosListar parametros = null;
		parametros = new ParametrosListar();
		parametros.setIdCliente(id);
		parametros.setIdCanalSap(codCanal);
		parametros.setIdSectorSap(codSector);
		parametros.setPaginacion(Constante.SIN_PAGINACION);
		return obtenerTodos(parametros, locale, URL_LISTAR_INTERLOCUTOR_COMPLETA);
	}

	/**
	 * Usado para llenar el combo de PlantaTerminal de la tabla de detalle
	 * 
	 * @param httpRequest
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = URL_LISTAR_PLANTA_RELATIVA, method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta listarPlantas(int id, String idCanalSap, String codInterlocutorSap, String claveRamoSap, HttpServletRequest httpRequest, Locale locale) {
		// Recuperar parametros
		ParametrosListar parametros = null;
		parametros = new ParametrosListar();
		parametros.setIdCliente(id);
		parametros.setIdCanalSap(idCanalSap);
		parametros.setCodInterlocutorSap(codInterlocutorSap);
		parametros.setClaveRamoSap(claveRamoSap);
		parametros.setPaginacion(Constante.SIN_PAGINACION);

		return obtenerTodos(parametros, locale, URL_LISTAR_PLANTA_COMPLETA);
	}

	/**
	 * Usado para llenar el combo de PlantaTerminal de la tabla de detalle
	 * 
	 * @param httpRequest
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = URL_LISTAR_PRODUCTO_RELATIVA, method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta listarProductos(int id,
			int idPlanta, String codInterlocutorSap, String claveRamoSap,
			String idCanalSap, HttpServletRequest httpRequest, Locale locale) {
		// Recuperar parametros
		ParametrosListar parametros = null;
		parametros = new ParametrosListar();
		parametros.setIdCliente(id);
		parametros.setIdPlanta(idPlanta);
		parametros.setCodInterlocutorSap(codInterlocutorSap);
		parametros.setClaveRamoSap(claveRamoSap);
		parametros.setIdCanalSap(idCanalSap);
		parametros.setPaginacion(Constante.SIN_PAGINACION);

		return obtenerTodos(parametros, locale, URL_LISTAR_PRODUCTO_COMPLETA);
	}
	
	/**
	 * @param id
	 * @param locale
	 * @return
	 */
	private RespuestaCompuesta obtenerTodos(ParametrosListar parametros, Locale locale, String tipoUrl) {
		RespuestaCompuesta respuesta = null;
		
		AuthenticatedUserDetails principal = null;
		try {
			// Recuperar el usuario actual
			principal = this.getCurrentUser();
			// Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(tipoUrl);
			if (respuesta.estado == false) {
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
			}
			// Verificar si cuenta con el permiso necesario
			verificarPermisoEnlace(respuesta, principal, locale);
			
			// Recuperar registros
			if(URL_LISTAR_CANALSECTOR_COMPLETA.equals(tipoUrl)){
				respuesta = proformaDao.recuperarRegistrosCanalSector(parametros);
			} else if(URL_LISTAR_INTERLOCUTOR_COMPLETA.equals(tipoUrl)){
				respuesta = proformaDao.recuperarRegistrosDatosInterlocutor(parametros);
			} else if(URL_LISTAR_PLANTA_COMPLETA.equals(tipoUrl)){
				respuesta = proformaDao.recuperarPlantasHabilitadas(parametros);
			} else if(URL_LISTAR_PRODUCTO_COMPLETA.equals(tipoUrl)){
				respuesta = proformaDao.recuperarProductosHabilitados(parametros);
			}
			
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
	public @ResponseBody RespuestaCompuesta recuperaRegistro(int ID, Locale locale) {
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
			respuesta = proformaDao.recuperarRegistro(ID);
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
	
	@RequestMapping(value = URL_LISTAR_DETALLE_RELATIVA, method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta recuperarRegistrosDetalle(HttpServletRequest httpRequest, Locale locale) {
		RespuestaCompuesta respuesta = null;
		ParametrosListar parametros = null;
		AuthenticatedUserDetails principal = null;
		try {
			// Recuperar el usuario actual
			principal = this.getCurrentUser();
			// Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_LISTAR_DETALLE_COMPLETA);
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

			if (StringUtils.isNotBlank(httpRequest.getParameter("filtroIdProforma"))) {
				parametros.setFiltroIdProforma(Integer.parseInt(httpRequest.getParameter("filtroIdProforma")));
			}

			parametros.setPaginacion(Constante.SIN_PAGINACION);
			// Recuperar registros
			respuesta = proformaDao.recuperarDetalleRegistros(parametros);
			respuesta.mensaje = gestorDiccionario.getMessage("sgo.listarExitoso", null, locale);
		} catch (Exception ex) {
			ex.printStackTrace();
			respuesta.estado = false;
			respuesta.contenido = null;
			respuesta.mensaje = ex.getMessage();
		}
		return respuesta;
	}

	@RequestMapping(value = URL_GENERAR_RELATIVA, method = RequestMethod.POST)
	public @ResponseBody RespuestaCompuesta generarRegistro(@RequestBody Proforma proformaweb,
			HttpServletRequest peticionHttp, Locale locale) {
		RespuestaCompuesta respuesta = null;
		RespuestaCompuesta respuestaBd = null;
		RespuestaCompuesta respuestaBitacora = null;
		AuthenticatedUserDetails principal = null;
		Bitacora eBitacora = null;
		String ContenidoAuditoria = "";
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		String direccionIp = obtenerIP(peticionHttp);
		String ClaveGenerada = "";
		try {
			// valida los datos que vienen del formulario
			 Respuesta validacion = Utilidades.validacionXSS(proformaweb, gestorDiccionario, locale);
			// if (validacion.estado == false) {
			// throw new Exception(validacion.valor);
			// }
			principal = this.getCurrentUser();
			respuesta = dEnlace.recuperarRegistro(URL_GENERAR_COMPLETA);
			if (respuesta.estado == false) {
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
			}
			// Verificar si cuenta con el permiso necesario
			verificarPermisoEnlace(respuesta, principal, locale);
			// enviar a SAP

			this.obtenerDatosCliente(proformaweb);
			this.obtenerDatosCanalSector(proformaweb);
			this.obtenerDatosInterlocutor(proformaweb);
			this.obtenerCentrosProductos(proformaweb);
			
			configurarProforma(proformaweb, principal, direccionIp);
			
			if("10".equals(proformaweb.getProceso())){
				proformaweb.setProceso("");
			} else if("11".equals(proformaweb.getProceso())){
				proformaweb.setProceso(Constante.TIPO_REGISTRO_PROFORMA);
			}
			dataSimularCrearWS = new SimularCrearProformaServicioWS();
			respuesta = dataSimularCrearWS.consultar(null, proformaweb);
			
			// guardamos el registro
			if(respuesta.estado == true && Constante.TIPO_REGISTRO_PROFORMA.equals(proformaweb.getProceso())){
				// Inicia la transaccion
				this.transaccion = new DataSourceTransactionManager(proformaDao.getDataSource());
				definicionTransaccion = new DefaultTransactionDefinition();
				estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
				eBitacora = new Bitacora();
				
				respuestaBd = proformaDao.guardarRegistro(proformaweb);
				if (respuestaBd.estado == false) {
					throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido", null, locale));
				}
				proformaweb.setIdProforma(Integer.parseInt(respuestaBd.valor));
				
				ClaveGenerada = respuestaBd.valor;
				ObjectMapper mapper = new ObjectMapper();
				ContenidoAuditoria = mapper.writeValueAsString(proformaweb);
				eBitacora.setUsuario(principal.getNombre());
				eBitacora.setAccion(URL_GENERAR_COMPLETA);
				eBitacora.setTabla(ProformaDao.NOMBRE_TABLA);
				eBitacora.setIdentificador(ClaveGenerada);
				eBitacora.setContenido(ContenidoAuditoria);
				eBitacora.setRealizadoEl(proformaweb.getCreadoEl());
				eBitacora.setRealizadoPor(proformaweb.getCreadoPor());
				respuestaBitacora = dBitacora.guardarRegistro(eBitacora);
				if (respuestaBitacora.estado == false) {
					throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
				}
				respuestaBitacora.mensaje = gestorDiccionario.getMessage("sgo.guardarExitoso", new Object[] {
						proformaweb.getFechaCreacion().substring(0, 9), proformaweb.getFechaCreacion().substring(10),
						principal.getIdentidad() }, locale);
				this.transaccion.commit(estadoTransaccion);
			}
			
		} catch (Exception ex) {
			respuesta.estado = false;
			respuesta.contenido = null;
			respuesta.mensaje = ex.getMessage();
			ex.printStackTrace();
			if(Constante.TIPO_REGISTRO_PROFORMA.equals(proformaweb.getProceso())){
				this.transaccion.rollback(estadoTransaccion);
			}
		}
		return respuesta;
	}

	private void obtenerDatosCliente(Proforma proformaweb) {
		RespuestaCompuesta respuesta = dCliente.recuperarRegistro(proformaweb.getCliente().getId());
		Cliente datos = (Cliente)respuesta.getContenido().getCarga().get(0);
		proformaweb.setCliente(datos);
	}

	private void obtenerCentrosProductos(Proforma proformaweb) {
		ParametrosListar parametros = null;
		parametros = new ParametrosListar();
		RespuestaCompuesta respuesta;
		for (int i = 0; i < proformaweb.getItems().size(); i++) {
			//producto
			parametros.setFiltroProducto(proformaweb.getItems().get(i).getProducto().getId());
			respuesta = proformaDao.recuperarProductosHabilitados(parametros);
			Producto producto = (Producto)respuesta.getContenido().getCarga().get(0);
			proformaweb.getItems().get(i).setProducto(producto);
			
			//planta
			parametros.setIdPlanta(proformaweb.getItems().get(i).getPlanta().getId());
			respuesta = proformaDao.recuperarPlantasHabilitadas(parametros);
			Planta planta = (Planta)respuesta.getContenido().getCarga().get(0);
			proformaweb.getItems().get(i).setPlanta(planta);
		}
		
	}

	private void obtenerDatosInterlocutor(Proforma proformaweb) {
		ParametrosListar parametros = null;
		parametros = new ParametrosListar();
		parametros.setIdCliente(proformaweb.getCliente().getId());
		parametros.setIdInterlocutor(proformaweb.getInterlocutor().getIdDatosInter());
		parametros.setPaginacion(Constante.SIN_PAGINACION);
		
		RespuestaCompuesta respuesta = proformaDao.recuperarRegistrosDatosInterlocutor(parametros);
		DatosInterlocutor datos = (DatosInterlocutor)respuesta.getContenido().getCarga().get(0);
		proformaweb.setInterlocutor(datos);
	}

	private void obtenerDatosCanalSector(Proforma proformaweb) {
		ParametrosListar parametros = null;
		parametros = new ParametrosListar();
		parametros.setIdCliente(proformaweb.getCliente().getId());
		parametros.setIdCanalSector(proformaweb.getCanalSector().getIdCanalSector());
		parametros.setPaginacion(Constante.SIN_PAGINACION);
		RespuestaCompuesta respuesta = proformaDao.recuperarRegistrosCanalSector(parametros);
		
		CanalSector datos = (CanalSector)respuesta.getContenido().getCarga().get(0);
		proformaweb.setCanalSector(datos);
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
	private Proforma configurarProforma(Proforma proforma,
			AuthenticatedUserDetails principal, String direccionIp) {
		if(proforma == null){
			proforma = new Proforma();
		}
		proforma.setActualizadoEl(Calendar.getInstance().getTime().getTime());
		proforma.setActualizadoPor(principal.getID());
		proforma.setCreadoEl(Calendar.getInstance().getTime().getTime());
		proforma.setCreadoPor(principal.getID());
		proforma.setIpActualizacion(direccionIp);
		proforma.setIpCreacion(direccionIp);
		return proforma;
	}

	/**
	 * @param httpRequest
	 * @param response
	 * @param locale
	 */
	/**
	 * @param httpRequest
	 * @param response
	 * @param locale
	 */
	@RequestMapping(value = URL_REPORTE_COTIZACION_RELATIVA, method = RequestMethod.GET)
	public void mostrarReporte(
			HttpServletRequest httpRequest, HttpServletResponse response,
			Locale locale) {
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		String mensajeRespuesta = "";
		String formatoReporte = "";
		String id = "";
		try {
			// Recuperar el usuario actual
			principal = (AuthenticatedUserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			// Recuperar el enlace de la accion
			respuesta = dEnlace
					.recuperarRegistro(URL_REPORTE_COTIZACION_COMPLETA);
			if (respuesta.estado == false) {
				mensajeRespuesta = gestorDiccionario.getMessage(
						"sgo.accionNoHabilitada", null, locale);
				throw new Exception(mensajeRespuesta);
			}
			Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga()
					.get(0);
			// Verificar si cuenta con el permiso necesario
			if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
				mensajeRespuesta = gestorDiccionario.getMessage(
						"sgo.faltaPermiso", null, locale);
				throw new Exception(mensajeRespuesta);
			}
			// PARAMETROS DEL REPORTE
			if (httpRequest.getParameter("formato") != null) {
				formatoReporte = httpRequest.getParameter("formato");
			}
			if (httpRequest.getParameter("id") != null) {
				id = httpRequest.getParameter("id");
			}

			Map<String, Object> parametrosReporte = new HashMap<String, Object>();

			respuesta = proformaDao.recuperarRegistro(Integer.parseInt(id));
			if (respuesta.estado == false) {
				mensajeRespuesta = gestorDiccionario.getMessage(
						"sgo.noListadoRegistros", null, locale);
				throw new Exception(mensajeRespuesta);
			}
			Proforma proforma = (Proforma)respuesta.getContenido().getCarga().get(0);
//			if (formatoReporte.length() > 0) {
//				parametrosReporte.put("formato", formatoReporte);
//			}
			if (StringUtils.isNotBlank(id)) {

				// ticket 9000003025
				String sql = "SELECT t1.nombre_producto, t1.nombre_planta, t1.volumen, t1.precio, t1.descuento, t1.precio_neto, t1.rodaje, t1.isc, t1.otros, t1.acumulado, t1.igv, t1.fise, t1.precio_descuento, t1.precio_percepcion, t1.importe_total"
						+ " FROM sgo.v_detalle_proforma t1 "
						+ " where t1.fk_id_proforma = "+id;
				respuesta = dReporte.recuperarRegistrosJasper(sql);
				if (respuesta.estado == false) {
					mensajeRespuesta = gestorDiccionario.getMessage(
							"sgo.noListadoRegistros", null, locale);
					throw new Exception(mensajeRespuesta);
				}
	
				String jasperFileName = "";
				String logoFileName = servletContext.getRealPath("/") + "pages"
						+ File.separator + "tema" + File.separator + "app"
						+ File.separator + "imagen" + File.separator + "logo.jpg";
				parametrosReporte.put("rutaImagen", logoFileName);
				parametrosReporte.put("usuario", principal.getIdentidad());
				parametrosReporte.put("nroProforma", proforma.getNroCotizacion());
				parametrosReporte.put("fechaCotizacion", proforma.getFechaCotizacion());
				parametrosReporte.put("razon_social", proforma.getCliente().getRazonSocial());
				parametrosReporte.put("ruc", proforma.getCliente().getRuc());
				parametrosReporte.put("moneda", proforma.getMoneda());
				parametrosReporte.put("destinatario", proforma.getInterlocutor().getCodInterlocutorSap()+" - "+proforma.getInterlocutor().getNomInterlocutorSap());
				parametrosReporte.put("canal_sector", proforma.getCanalSector().getDescripcionCanalSector());
				parametrosReporte.put("TOTAL", proforma.getMonto());
				data = (List<Object>) respuesta.contenido.getCarga();
				ByteArrayOutputStream baos = null;
				Reporteador uReporteador = new Reporteador();
				parametrosReporte.put("REPORT_LOCALE", new Locale("en", "US"));
	
				if (formatoReporte.equals(Reporteador.FORMATO_PDF)) {
					jasperFileName = servletContext.getRealPath("/")+ File.separator + "pages"
							+ File.separator + "reporte_cotizacion" + File.separator
							+ "Reporte_Proforma_PDF.jasper"; //9000002843
					baos = uReporteador.generarPDF(parametrosReporte, clase, data,
							jasperFileName);
					response.setHeader("Content-Disposition",
							"inline; filename=\"reporteCotizacion.pdf\"");
					response.setDateHeader("Expires", -1);
					response.setContentType("application/pdf");
					response.setContentLength(baos.size());
					response.getOutputStream().write(baos.toByteArray());
					response.getOutputStream().flush();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private AuthenticatedUserDetails getCurrentUser() {
		return (AuthenticatedUserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}
 
}
