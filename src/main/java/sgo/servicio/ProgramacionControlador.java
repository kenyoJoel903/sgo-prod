package sgo.servicio;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sgo.datos.BitacoraDao;
import sgo.datos.ClienteDao;
import sgo.datos.DetalleProgramacionDao;
import sgo.datos.DiaOperativoDao;
import sgo.datos.EnlaceDao;
import sgo.datos.OperacionDao;
import sgo.datos.ParametroDao;
import sgo.datos.PlanificacionDao;
import sgo.datos.ProductoDao;
import sgo.datos.ProgramacionDao;
import sgo.datos.UsuarioDao;
import sgo.entidad.Bitacora;
import sgo.entidad.Contenido;
import sgo.entidad.DetalleProgramacion;
import sgo.entidad.DetalleProgramacionCorreos;
import sgo.entidad.DiaOperativo;
import sgo.entidad.Enlace;
import sgo.entidad.MenuGestor;
import sgo.entidad.Operacion;
import sgo.entidad.Parametro;
import sgo.entidad.ParametrosListar;
import sgo.entidad.Planificacion;
import sgo.entidad.ProductoProgramacion;
import sgo.entidad.Programacion;
import sgo.entidad.ProgramacionPlanificada;
import sgo.entidad.Respuesta;
import sgo.entidad.RespuestaCompuesta;
import sgo.entidad.Vigencia;
import sgo.negocio.VigenciaBusiness;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.utilidades.Campo;
import sgo.utilidades.Constante;
import sgo.utilidades.MailNotifica;
import sgo.utilidades.Reporteador;
import sgo.utilidades.Utilidades;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ProgramacionControlador {
 @Autowired
 private MessageSource gestorDiccionario;// Gestor del diccionario de mensajes
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
 private ClienteDao dCliente;
 @Autowired
 private OperacionDao dOperacion;
 @Autowired
 private ProductoDao dProducto;
 @Autowired
 private ProgramacionDao dProgramacion;
 
 @Autowired
 private DetalleProgramacionDao dDetalleProgramacion;
 @Autowired
 private MailNotifica dMailNotifica;
 @Autowired
 private UsuarioDao dUsuario;
 @Autowired
 private ParametroDao dParametro;
 @Autowired
 ServletContext servletContext;
 @Autowired
 private VigenciaBusiness vigenciaBusiness;
 @Autowired
 private PlanificacionDao dPlanificacion;
 //
 /** Nombre de la clase. */
 private static final String sNombreClase = "ProgramacionControlador";
 // Gestor de la transaccion
 private DataSourceTransactionManager transaccion;
 // urls generales
 private static final String URL_GESTION_COMPLETA = "/admin/programacion";
 private static final String URL_GESTION_RELATIVA = "/programacion";
 
 private static final String URL_GUARDAR_COMPLETA = "/admin/programacion/crear";
 private static final String URL_GUARDAR_RELATIVA = "/programacion/crear";
 
private static final String URL_ELIMINAR_COMPLETA="/admin/programacion/eliminar";
private static final String URL_ELIMINAR_RELATIVA="/programacion/eliminar";
 
 private static final String URL_LISTAR_COMPLETA = "/admin/programacion/listar";
 private static final String URL_LISTAR_RELATIVA = "/programacion/listar";
 
 private static final String URL_ACTUALIZAR_COMPLETA = "/admin/programacion/actualizar";
 private static final String URL_ACTUALIZAR_RELATIVA = "/programacion/actualizar";

 private static final String URL_LISTAR_DET_COMPLETA = "/admin/programacion/listardet";
 private static final String URL_LISTAR_DET_RELATIVA = "/programacion/listardet";
 
 private static final String URL_RECUPERAR_CORREO_COMPLETA = "/admin/programacion/recuperar-correo";
 private static final String URL_RECUPERAR_CORREO_RELATIVA = "/admin/programacion/recuperar-correo";
 
 private static final String URL_RECUPERAR_COMPLETA = "/admin/programacion/recuperar";
 private static final String URL_RECUPERAR_RELATIVA = "/programacion/recuperar";
 
 private static final String URL_NOTIFICAR_COMPLETA = "/admin/programacion/notificar";
 private static final String URL_NOTIFICAR_RELATIVA = "/programacion/notificar";
 
 
 private static final String URL_REPORTE_PROGRAMACION_COMPLETA = "/admin/programacion/reporte";
 private static final String URL_REPORTE_PROGRAMACION_RELATIVA = "/programacion/reporte";
 
 
 private static final String URL_VERIFICA_NOTIFICAR_COMPLETA = "/admin/programacion/verifica-notificacion";
 private static final String URL_VERIFICA_NOTIFICAR_RELATIVA = "/programacion/verifica-notificacion";
 
 private static final String URL_RECUPERAR_PLANIFICACION = "/programacion/recuperar-planificacion";
 
 private static final String URL_COMPLETAR_COMPLETA = "/admin/programacion/completarProgramacion";
 private static final String URL_COMPLETAR_RELATIVA = "/programacion/completarProgramacion";
 
 private static final String URL_COMENTAR_COMPLETA = "/admin/programacion/comentar";
 private static final String URL_COMENTAR_RELATIVA = "/programacion/comentar";

 private HashMap<String, String> recuperarMapaValores(Locale locale) {
  HashMap<String, String> mapaValores = new HashMap<String, String>();
  try {
   mapaValores.put("ESTADO_ACTIVO", String.valueOf(Constante.ESTADO_ACTIVO));
   mapaValores.put("ESTADO_INACTIVO", String.valueOf(Constante.ESTADO_INACTIVO));
   mapaValores.put("FILTRO_TODOS", String.valueOf(Constante.FILTRO_TODOS));
   mapaValores.put("TEXTO_INACTIVO", gestorDiccionario.getMessage("sgo.estadoInactivo", null, locale));
   mapaValores.put("TEXTO_ACTIVO", gestorDiccionario.getMessage("sgo.estadoActivo", null, locale));
   mapaValores.put("TEXTO_TODOS", gestorDiccionario.getMessage("sgo.filtroTodos", null, locale));

   mapaValores.put("TITULO_AGREGAR_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioAgregar", null, locale));
   mapaValores.put("TITULO_MODIFICA_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioEditar", null, locale));
   mapaValores.put("TITULO_COMPLETA_REGISTRO", gestorDiccionario.getMessage("sgo.tituloCompletarProgramacion", null, locale));
   mapaValores.put("TITULO_VER_REGISTRO", gestorDiccionario.getMessage("sgo.tituloVerProgramacion", null, locale));
   
   mapaValores.put("TITULO_DETALLE_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioVer", null, locale));
   mapaValores.put("TITULO_LISTADO_DIA_PLANIFICADO", gestorDiccionario.getMessage("sgo.tituloFormularioListadoDiaPlanificado", null, locale));
   //
   mapaValores.put("ETIQUETA_BOTON_CERRAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCerrar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_GUARDAR", gestorDiccionario.getMessage("sgo.etiquetaBotonGuardar", null, locale));

   mapaValores.put("ETIQUETA_BOTON_AGREGAR", gestorDiccionario.getMessage("sgo.etiquetaBotonAgregar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_MODIFICAR", gestorDiccionario.getMessage("sgo.etiquetaBotonModificar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_VER", gestorDiccionario.getMessage("sgo.etiquetaBotonVer", null, locale));
   mapaValores.put("ETIQUETA_BOTON_FILTRAR", gestorDiccionario.getMessage("sgo.etiquetaBotonFiltrar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_ACTIVAR", gestorDiccionario.getMessage("sgo.etiquetaBotonActivar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_COMENTAR", gestorDiccionario.getMessage("sgo.etiquetaBotonComentar", null, locale));

   mapaValores.put("ETIQUETA_BOTON_CANCELAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCancelar", null, locale));
   mapaValores.put("ETIQUETA_BOTON_CONFIRMAR", gestorDiccionario.getMessage("sgo.etiquetaBotonConfirmar", null, locale));
   mapaValores.put("MENSAJE_CAMBIAR_ESTADO", gestorDiccionario.getMessage("sgo.mensajeCambiarEstado", null, locale));
   mapaValores.put("MENSAJE_ELIMINAR_REGISTRO", gestorDiccionario.getMessage("sgo.mensajeEliminarRegistro",null,locale));

   mapaValores.put("MENSAJE_CARGANDO", gestorDiccionario.getMessage("sgo.mensajeCargando", null, locale));
   mapaValores.put("TITULO_VENTANA_MODAL", gestorDiccionario.getMessage("sgo.tituloVentanaModal", null, locale));
   mapaValores.put("MENSAJE_ENVIAR_CORREO", gestorDiccionario.getMessage("sgo.mensajeEnviarCorreo", null, locale));
   
   mapaValores.put("MENSAJE_NOTIFICAR_PROGRAMACION", gestorDiccionario.getMessage("sgo.mensajeNotificarProgramacion",null,locale));
   mapaValores.put("MENSAJE_COMENTAR_PROGRAMACION", gestorDiccionario.getMessage("sgo.mensajeComentarProgramacion",null,locale));
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
   parametros.setFiltroEstado(Constante.ESTADO_ACTIVO);
   parametros.setIdCliente(principal.getCliente().getId());
   parametros.setIdOperacion(principal.getOperacion().getId());
   respuesta = dOperacion.recuperarRegistros(parametros);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles", null, locale));
   }
   listaOperaciones = (ArrayList<?>) respuesta.contenido.carga;

   Operacion opeTemp = (Operacion) respuesta.contenido.carga.get(0);
   int idOperacion = opeTemp.getId();
   String fecha = dDiaOperativo.recuperarFechaActual().valor;
   //esto para obtener el ultimo dia operativo 
   Respuesta oRespuesta = dDiaOperativo.recuperarUltimoDiaOperativo(idOperacion, "");
   // Verifica el resultado de la accion
   if (oRespuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles", null, locale));
   }
   if (oRespuesta.valor != null) {
	   fecha = oRespuesta.valor;
   }
   
   parametros = new ParametrosListar();
   parametros.setPaginacion(Constante.SIN_PAGINACION);
   //Para que retorne sÃ³lo los productos que se encuentren activos
   parametros.setFiltroEstado(Constante.ESTADO_ACTIVO);
   respuesta = dProducto.recuperarRegistros(parametros);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.noPermisosDisponibles", null, locale));
   }
   listaProductos = (ArrayList<?>) respuesta.contenido.carga;
   mapaValores = recuperarMapaValores(locale);
   vista = new ModelAndView("plantilla");
   vista.addObject("vistaJSP", "programacion/programacion.jsp");
   vista.addObject("vistaJS", "programacion/programacion.js");
   vista.addObject("identidadUsuario", principal.getIdentidad());
   vista.addObject("rolUsuario", String.valueOf(principal.getRol().getId()));
   vista.addObject("menu", listaEnlaces);
   vista.addObject("clientes", listaClientes);
   vista.addObject("operaciones", listaOperaciones);
   vista.addObject("productos", listaProductos);
   vista.addObject("mapaValores", mapaValores);
   //vista.addObject("fechaActual", dDiaOperativo.recuperarFechaActual().valor);
   //SAR N� SGCO-OI-007-2016
   vista.addObject("fechaActual", fecha);
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

   if (httpRequest.getParameter("txtFiltro") != null) {
    parametros.setTxtFiltro((httpRequest.getParameter("txtFiltro")));
   }

   parametros.setFiltroEstado(Constante.FILTRO_TODOS);
   if (httpRequest.getParameter("filtroEstado") != null && !httpRequest.getParameter("filtroEstado").isEmpty()) {
    parametros.setFiltroEstado(Integer.parseInt(httpRequest.getParameter("filtroEstado")));
   }
   
   if (httpRequest.getParameter("filtroOperacion") != null && !httpRequest.getParameter("filtroOperacion").isEmpty()) {
	    parametros.setFiltroOperacion(Integer.parseInt(httpRequest.getParameter("filtroOperacion")));
   }
   
   
   if (httpRequest.getParameter("filtroFechaFinal") != null) {
	    parametros.setFiltroFechaFinal((httpRequest.getParameter("filtroFechaFinal")));
   }

   if (httpRequest.getParameter("filtroFechaInicio") != null) {
    parametros.setFiltroFechaInicio((httpRequest.getParameter("filtroFechaInicio")));
   }
   
   
   
   // Recuperar registros
   respuesta = dProgramacion.recuperarRegistros(parametros);
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.listarExitoso", null, locale);
  } catch (Exception ex) {
   //ex.printStackTrace();
   Utilidades.gestionaError(ex, sNombreClase, "recuperarRegistros");
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
  }
  return respuesta;
 }
 
 @RequestMapping(value = URL_ACTUALIZAR_RELATIVA, method = RequestMethod.POST)
 public @ResponseBody
 RespuestaCompuesta actualizarRegistro(@RequestBody Programacion eProgramacion, HttpServletRequest peticionHttp, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;
  TransactionDefinition definicionTransaccion = null;
  TransactionStatus estadoTransaccion = null;
  Bitacora eBitacora = null;
  String direccionIp = "";
  try {
   // Inicia la transaccion
   this.transaccion = new DataSourceTransactionManager(dProgramacion.getDataSource());
   definicionTransaccion = new DefaultTransactionDefinition();
   estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
   eBitacora = new Bitacora();
   // Recuperar el usuario actual
   principal = this.getCurrentUser();
   // Recuperar el enlace de la accion
   respuesta = dEnlace.recuperarRegistro(URL_ACTUALIZAR_COMPLETA);
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
   
   eProgramacion.setEstado(Constante.ESTADO_ACTIVO);   
   eProgramacion.setActualizadoEl(Calendar.getInstance().getTime().getTime());
   eProgramacion.setActualizadoPor(principal.getID());
   eProgramacion.setIpActualizacion(direccionIp);
   
   respuesta = dDiaOperativo.recuperarRegistro(eProgramacion.getIdDiaOperativo());
   // Verifica si la accion se ejecuto de forma satisfactoria
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale)); 
   }   
   eProgramacion.setDiaOperativo((DiaOperativo)respuesta.getContenido().getCarga().get(0));
   
   //recuperamos la cisterna original con sus respectivos compartimentos
   respuesta = dProgramacion.actualizarRegistro(eProgramacion);
   if (respuesta.estado == false) {
     throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
   }
// Guardar en la bitacora
   ObjectMapper mapper = new ObjectMapper();
   eBitacora.setUsuario(principal.getNombre());
   eBitacora.setAccion(URL_ACTUALIZAR_COMPLETA);
   eBitacora.setTabla(ProgramacionDao.NOMBRE_TABLA);
   eBitacora.setIdentificador(String.valueOf(eProgramacion.getId()));
   eBitacora.setContenido(mapper.writeValueAsString(eProgramacion));
   eBitacora.setRealizadoEl(eProgramacion.getActualizadoEl());
   eBitacora.setRealizadoPor(eProgramacion.getActualizadoPor());
   respuesta = dBitacora.guardarRegistro(eBitacora);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
   }
   //recuperamos la cisterna original con sus respectivos compartimentos
//   respuesta = dDetalleProgramacion.recuperarDetalleProgramacion(eProgramacion.getId());
//   if (respuesta.estado == false) {
//     throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
//   }
   DetalleProgramacion detalle=null;
   DetalleProgramacion detalleAux=null;
   ParametrosListar parametrosVigencia=null;
   String mensajeVigencia=null;
   
 //Inicio Agregado por req 9000002464====================
   Date fechaPlanificada = eProgramacion.getDiaOperativo().getFechaOperativa();
 //Fin Agregado por req 9000002464====================
   
   if(eProgramacion.getProgramaciones()!=null && eProgramacion.getProgramaciones().size()>0){	   
	    for(int i=0;i<eProgramacion.getProgramaciones().size();i++){    	
	    	   detalle=(DetalleProgramacion)eProgramacion.getProgramaciones().get(i);
		 	   detalle.setIdProgramacion(eProgramacion.getId());
		 	   detalle.setIpActualizacion(direccionIp);
		 	   detalle.setIdPlanta(eProgramacion.getDiaOperativo().getOperacion().getIdPlantaDespacho()); 
		 	  /*FIXME 7000001924 
		 	  for(int j=0;j<eProgramacion.getProgramaciones().size();j++){
		 		 detalleAux=(DetalleProgramacion)eProgramacion.getProgramaciones().get(j);
		 		  if(i!=j){
		 			 if(detalle.getIdCisterna()==detalleAux.getIdCisterna()){
		 				throw new Exception("Las Cisternas no deben estar duplicadas."); 
		 			 }
		 			 if(detalle.getIdConductor()==detalleAux.getIdConductor()){
		 				throw new Exception("Los Conductores no deben estar duplicados."); 
		 			 }
		 		  }
		 	  }*/
		 	  
		 	  //verifica si existe algun documento caducado CISTERNA
		 	 parametrosVigencia=new ParametrosListar();
		 	 parametrosVigencia.setFiltroPerteneceA(detalle.getIdCisterna());
		 	 parametrosVigencia.setFiltroIdEntidad(Vigencia.DOCUMENTO_CISTERNA);		 	  
		 	 mensajeVigencia=vigenciaBusiness.verificaVigenciaDocumento(parametrosVigencia, locale);
		 	 if(mensajeVigencia!=null && mensajeVigencia.length()>0){
		 		throw new Exception(mensajeVigencia); 
		 	 }
		 	 
		 	  //verifica si existe algun documento caducado CONDUCTOR
		 	 parametrosVigencia=new ParametrosListar();
		 	 parametrosVigencia.setFiltroPerteneceA(detalle.getIdConductor());
		 	 parametrosVigencia.setFiltroIdEntidad(Vigencia.DOCUMENTO_CONDUCTOR);		 	  
		 	 mensajeVigencia=vigenciaBusiness.verificaVigenciaDocumento(parametrosVigencia, locale);
		 	 if(mensajeVigencia!=null && mensajeVigencia.length()>0){
		 		throw new Exception(mensajeVigencia); 
		 	 }
		 	 
		 	 if(!Utilidades.esValido(detalle.getCompletar())){
		 		 throw new Exception(gestorDiccionario.getMessage("sgo.guardarProgramacionFallido", null, locale));
		 	 }
		 	 else{
		 		 if(detalle.getCompletar() == 1){
		 			//valida los datos que vienen del formulario
		 		    Respuesta validacion = Utilidades.validacionProgramacionXSS(detalle, gestorDiccionario, locale);
		 		    if (validacion.estado == false) {
		 		      throw new Exception(validacion.valor);
		 		    }
		 		 } else {
		 			Respuesta validacion = Utilidades.validacionCompletarProgramacionXSS(detalle, gestorDiccionario, locale);
		 		    if (validacion.estado == false) {
		 		      throw new Exception(validacion.valor);
		 		    }
		 		 }
		 	 }
		  	   if(detalle.getId()>0){//actualizar	
		 		   	respuesta = dDetalleProgramacion.actualizarRegistro(detalle);
				     if (respuesta.estado == false) {
				      throw new Exception(gestorDiccionario.getMessage("sgo.guardarProgramacionFallido", null, locale));
				     }	 
		 	   }else{//insertar
		 		   	 detalle.setIdPlanta(eProgramacion.getDiaOperativo().getOperacion().getIdPlantaDespacho());
		 		   	 
				 	   String fechaInicioVigTC = detalle.getStrFechaInicioVigTC();
				 	   String fechaFinVigTC = detalle.getStrFechaFinVigTC();;
				 	   
				 	   if(fechaInicioVigTC != null && !fechaInicioVigTC.equals("")){
				 		  detalle.setFechaInicioVigTC(new SimpleDateFormat("dd/MM/yyyy").parse(fechaInicioVigTC));
				 	   }
				 	   
				 	  if(fechaFinVigTC != null && !fechaFinVigTC.equals("")){
				 		 Date fechaFinVigTTemp = new SimpleDateFormat("dd/MM/yyyy").parse(fechaFinVigTC);
				 		  detalle.setFechaFinVigTC(fechaFinVigTTemp);
				 		  
						 //Inicio Agregado por req 9000002464====================
				 		  if(fechaFinVigTTemp.compareTo(fechaPlanificada) < 0){
				 			 throw new Exception("La cisterna con tarjeta Cub. " + detalle.getTarjetaCub() + " tiene fecha Vig. TC menor a la fecha planificada"); 
				 		  }
				 		//Fin Agregado por req 9000002464====================
				 	   }
				 	  
		 		   	 respuesta = dDetalleProgramacion.guardarRegistro(detalle);
				     if (respuesta.estado == false) {
				      throw new Exception(gestorDiccionario.getMessage("sgo.guardarProgramacionFallido", null, locale));
				     }	
		 	   }
	   
		   }
   }
   
   
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.actualizarExitoso", new Object[] { eProgramacion.getFechaActualizacion().substring(0, 9), eProgramacion.getFechaActualizacion().substring(10),principal.getIdentidad() }, locale);

   this.transaccion.commit(estadoTransaccion);
  } catch (Exception ex) {
    //ex.printStackTrace();
	  Utilidades.gestionaError(ex, sNombreClase, "actualizarRegistro");
    this.transaccion.rollback(estadoTransaccion);
    respuesta.estado = false;
    respuesta.contenido = null;
    respuesta.mensaje = ex.getMessage();
  }
  return respuesta;
 }
 
 
 @RequestMapping(value = URL_GUARDAR_RELATIVA, method = RequestMethod.POST)
 public @ResponseBody
 RespuestaCompuesta guardarRegistro(@RequestBody Programacion eProgramacion, HttpServletRequest peticionHttp, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;
  Bitacora eBitacora = null;
  String ContenidoAuditoria = "";
  TransactionDefinition definicionTransaccion = null;
  TransactionStatus estadoTransaccion = null;
  String direccionIp = "";
  String ClaveGenerada = "";
  ParametrosListar parametros = null;
  //ParametrosListar parametros = null;
  try {
   // Inicia la transaccion
   this.transaccion = new DataSourceTransactionManager(dProgramacion.getDataSource());
   definicionTransaccion = new DefaultTransactionDefinition();
   estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
   eBitacora = new Bitacora();
   // Recuperar el usuario actual
   principal = this.getCurrentUser();
   // Recuperar el enlace de la accion
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
   //eCisterna.setCantidadCompartimentos(eCisterna.getCompartimentos().size());
   eProgramacion.setEstado(Constante.ESTADO_ACTIVO);
   eProgramacion.setActualizadoEl(Calendar.getInstance().getTime().getTime());
   eProgramacion.setActualizadoPor(principal.getID());
   eProgramacion.setCreadoEl(Calendar.getInstance().getTime().getTime());
   eProgramacion.setCreadoPor(principal.getID());
   eProgramacion.setIpActualizacion(direccionIp);
   eProgramacion.setIpCreacion(direccionIp);
   
   //parametros = new ParametrosListar();
   //parametros.setPlacaCisterna(eCisterna.getPlaca());
   
   respuesta = dDiaOperativo.recuperarRegistro(eProgramacion.getIdDiaOperativo());
   // Verifica si la accion se ejecuto de forma satisfactoria
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
   }   
   eProgramacion.setDiaOperativo((DiaOperativo)respuesta.getContenido().getCarga().get(0));
   //Valido primero si no hay otro registro igual en BD para un transportista
   parametros=new ParametrosListar();
   parametros.setIdTransportista(eProgramacion.getIdTransportista());
   parametros.setFiltroDiaOperativo(eProgramacion.getIdDiaOperativo());
   
   respuesta = dProgramacion.recuperaProgramacionDetalle(parametros);
   // Verifica si la accion se ejecuto de forma satisfactoria
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
   }
   
   if(respuesta.contenido.getCarga()!=null && respuesta.contenido.getCarga().size()>0){
	   respuesta.estado = false;
	   throw new Exception(gestorDiccionario.getMessage("sgo.programacion.ValidacionTransportista", null, locale));
   }

   respuesta = dProgramacion.guardarRegistro(eProgramacion);
   // Verifica si la accion se ejecuto de forma satisfactoria
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido", null, locale));
   }
   ClaveGenerada = respuesta.valor;
   
   DetalleProgramacion detalle=null;
   DetalleProgramacion detalleAux=null;
   ParametrosListar parametrosVigencia=null;
   String mensajeVigencia=null;
   
 //Inicio Agregado por req 9000002464====================
   Date fechaPlanificada = eProgramacion.getDiaOperativo().getFechaOperativa();
 //Fin Agregado por req 9000002464====================
   
   if(eProgramacion.getProgramaciones()!=null && eProgramacion.getProgramaciones().size()>0){	   
	    for(int i=0;i<eProgramacion.getProgramaciones().size();i++){    	
	    	   detalle=(DetalleProgramacion)eProgramacion.getProgramaciones().get(i);
	    	   detalle.setIdProgramacion(Integer.parseInt(ClaveGenerada));		 	   
		 	   detalle.setIdPlanta(eProgramacion.getDiaOperativo().getOperacion().getIdPlantaDespacho()); 
		 	   detalle.setCapacidadCisternaTotal(eProgramacion.getProgramaciones().get(i).getCapacidadCisternaTotal());
		 	   detalle.setCapacidadVolumetrica(eProgramacion.getProgramaciones().get(i).getCapacidadVolumetrica());
		 	   
			   //Agregado por req 9000002841====================
		 	   
		 	   String tarjetaCub = eProgramacion.getProgramaciones().get(i).getTarjetaCub();
		 	   detalle.setTarjetaCub(tarjetaCub);
		 	   
		 	   String fechaInicioVigTC = eProgramacion.getProgramaciones().get(i).getStrFechaInicioVigTC();
		 	   String fechaFinVigTC = eProgramacion.getProgramaciones().get(i).getStrFechaFinVigTC();;
		 	   
		 	   if(fechaInicioVigTC != null && !fechaInicioVigTC.equals("")){
		 		  detalle.setFechaInicioVigTC(new SimpleDateFormat("dd/MM/yyyy").parse(fechaInicioVigTC));
		 	   }
		 	   
		 	  if(fechaFinVigTC != null && !fechaFinVigTC.equals("")){
		 		  Date fechaFinVigTTemp = new SimpleDateFormat("dd/MM/yyyy").parse(fechaFinVigTC);
		 		  detalle.setFechaFinVigTC(fechaFinVigTTemp);
		 		  
		 		  System.out.println("fechaPlanificada: " + fechaPlanificada);
		 		 System.out.println("fechaFinVigTTemp: " + fechaFinVigTTemp);
		 		//Inicio Agregado por req 9000002464====================
		 		  if(fechaFinVigTTemp.compareTo(fechaPlanificada) < 0){
		 			 throw new Exception("La cisterna con tarjeta Cub. " + tarjetaCub + " tiene fecha Vig. TC menor a la fecha planificada"); 
		 		  }
		 		//Fin Agregado por req 9000002464====================
		 	   }
		 	   
		 	  //Agregado por req 9000002841====================
		 	  
		 	  /*//FIXME 7000001924 
		 	  for(int j=0;j<eProgramacion.getProgramaciones().size();j++){
		 		 detalleAux=(DetalleProgramacion)eProgramacion.getProgramaciones().get(j);
		 		  if(i!=j){
		 			 if(detalle.getIdCisterna()==detalleAux.getIdCisterna()){
		 				throw new Exception("Las Cisternas no deben estar duplicadas."); 
		 			 }
		 			 if(detalle.getIdConductor()==detalleAux.getIdConductor()){
		 				throw new Exception("Los Conductores no deben estar duplicados."); 
		 			 }
		 		  }
		 	  }*/
		 	  
		 	  //verifica si existe algun documento caducado CISTERNA
		 	 parametrosVigencia=new ParametrosListar();
		 	 parametrosVigencia.setFiltroPerteneceA(detalle.getIdCisterna());
		 	 parametrosVigencia.setFiltroIdEntidad(Vigencia.DOCUMENTO_CISTERNA);		 	  
		 	 mensajeVigencia=vigenciaBusiness.verificaVigenciaDocumento(parametrosVigencia, locale);
		 	 if(mensajeVigencia!=null && mensajeVigencia.length()>0){
		 		throw new Exception(mensajeVigencia); 
		 	 }
		 	 
		 	  //verifica si existe algun documento caducado CONDUCTOR
		 	 parametrosVigencia=new ParametrosListar();
		 	 parametrosVigencia.setFiltroPerteneceA(detalle.getIdConductor());
		 	 parametrosVigencia.setFiltroIdEntidad(Vigencia.DOCUMENTO_CONDUCTOR);		 	  
		 	 mensajeVigencia=vigenciaBusiness.verificaVigenciaDocumento(parametrosVigencia, locale);
		 	 if(mensajeVigencia!=null && mensajeVigencia.length()>0){
		 		throw new Exception(mensajeVigencia); 
		 	 }
		 	//valida los datos que vienen del formulario
		     Respuesta validacion = Utilidades.validacionProgramacionXSS(detalle, gestorDiccionario, locale);
		     if (validacion.estado == false) {
		       throw new Exception(validacion.valor);
		     }
			   respuesta = dDetalleProgramacion.guardarRegistro(detalle);
			    if (respuesta.estado == false) {
			     throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
			    }
	   
		   }
   }
   
   // Guardar en la bitacora
   ObjectMapper mapper = new ObjectMapper(); 
   ContenidoAuditoria = mapper.writeValueAsString(eProgramacion);
   eBitacora.setUsuario(principal.getNombre());
   eBitacora.setAccion(URL_GUARDAR_COMPLETA);
   eBitacora.setTabla(ProgramacionDao.NOMBRE_TABLA);
   eBitacora.setIdentificador(ClaveGenerada);
   eBitacora.setContenido(ContenidoAuditoria);
   eBitacora.setRealizadoEl(eProgramacion.getCreadoEl());
   eBitacora.setRealizadoPor(eProgramacion.getCreadoPor());
   respuesta = dBitacora.guardarRegistro(eBitacora);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
   }
   
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.guardarExitoso", new Object[] { eProgramacion.getFechaCreacion().substring(0, 9), eProgramacion.getFechaCreacion().substring(10), principal.getIdentidad() }, locale);
   this.transaccion.commit(estadoTransaccion);
  } catch (Exception ex) {
   this.transaccion.rollback(estadoTransaccion);
   //ex.printStackTrace();
   Utilidades.gestionaError(ex, sNombreClase, "guardarRegistro");
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
  }
  return respuesta;
 }
 
 @RequestMapping(value = URL_LISTAR_DET_RELATIVA, method = RequestMethod.GET)
 public @ResponseBody
 RespuestaCompuesta recuperaRegistroDetalle(HttpServletRequest httpRequest, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;
  try {
   // Recupera el usuario actual
   principal = this.getCurrentUser();
   // Recuperar el enlace de la accion
   respuesta = dEnlace.recuperarRegistro(URL_LISTAR_DET_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
   // Verificar si cuenta con el permiso necesario
   if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
    throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
   }
  int id=0;
   if (httpRequest.getParameter("idDiaOperativo") != null) {
	   id = Integer.parseInt(httpRequest.getParameter("idDiaOperativo").toString());
  }
//   ParametrosListar parametros = new ParametrosListar();
//   parametros.setPaginacion(Constante.SIN_PAGINACION);
//   parametros.setCampoOrdenamiento("id");
//   parametros.setSentidoOrdenamiento("asc");
//   parametros.setFiltroDiaOperativo(eDiaOperativo.getId());
   
   respuesta = dProgramacion.recuperaProgramacionDetalle(id);
   if (respuesta.estado==false){
    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
   }  
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.recuperarExitoso", null, locale);
  } catch (Exception ex) {
   //ex.printStackTrace();
	  Utilidades.gestionaError(ex, sNombreClase, "recuperaRegistroDetalle");
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
   principal = this.getCurrentUser();
   respuesta = dEnlace.recuperarRegistro(URL_RECUPERAR_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
   if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
    throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
   }
   respuesta = dDetalleProgramacion.recuperarDetalleProgramacion(ID);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
   }   
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.recuperarExitoso", null, locale);
  } catch (Exception ex) {
   //ex.printStackTrace();
	  Utilidades.gestionaError(ex, sNombreClase, "recuperaRegistro");
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
  }
  return respuesta;
 }

 
 @RequestMapping(value = URL_NOTIFICAR_RELATIVA, method = RequestMethod.GET)
 public @ResponseBody Respuesta enviarMail(HttpServletRequest httpRequest, Locale locale) {
  Respuesta respuesta = new Respuesta();
  RespuestaCompuesta oRespuesta = null;
  AuthenticatedUserDetails principal = null;
  ParametrosListar parametros = null;
  String direccionIp = "";
  try {
   // Recupera el usuario actual
   principal = this.getCurrentUser();
   // Recuperar el enlace de la accion
   oRespuesta = dEnlace.recuperarRegistro(URL_NOTIFICAR_COMPLETA);
   if (oRespuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   Enlace eEnlace = (Enlace) oRespuesta.getContenido().getCarga().get(0);
   // Verificar si cuenta con el permiso necesario
   if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
    throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
   }

   // Recuperar parametros
   parametros = new ParametrosListar();
   if (httpRequest.getParameter("filtroMailPara") != null) {
    parametros.setFiltroMailPara((httpRequest.getParameter("filtroMailPara")));
   }

   if (httpRequest.getParameter("filtroMailCC") != null) {
    parametros.setFiltroMailCC((httpRequest.getParameter("filtroMailCC")));
   }
   
   if (httpRequest.getParameter("filtroMailComentario") != null) {
	    parametros.setFiltroMailComentario((httpRequest.getParameter("filtroMailComentario")));
   }
   
   if (httpRequest.getParameter("filtroFechaDiaOperativo") != null) {
    parametros.setFiltroFechaDiaOperativo((httpRequest.getParameter("filtroFechaDiaOperativo")));
   } 
   
   if (httpRequest.getParameter("filtroFechaCarga") != null) {
    parametros.setFiltroFechaCarga((httpRequest.getParameter("filtroFechaCarga")));
   } 
   

   
   if (httpRequest.getParameter("filtroNombreOperacion") != null) {
    parametros.setFiltroNombreOperacion((httpRequest.getParameter("filtroNombreOperacion")));
   } 
   
   if (httpRequest.getParameter("filtroDiaOperativo") != null) {
    parametros.setFiltroDiaOperativo(Integer.parseInt(httpRequest.getParameter("filtroDiaOperativo")));
   } 
   
   //el filtro Operacion es para la busqueda de los usuarios
//   if (httpRequest.getParameter("filtroOperacion") != null) {
//    parametros.setFiltroOperacion(Integer.parseInt(httpRequest.getParameter("filtroOperacion")));
//   }
   
   RespuestaCompuesta respuestaC = dDiaOperativo.recuperarRegistro(parametros.getFiltroDiaOperativo());
   DiaOperativo eDiaOperativoRep = (DiaOperativo) respuestaC.contenido.getCarga().get(0);  
   String razonSocial= eDiaOperativoRep.getOperacion().getCliente().getRazonSocial(); 
    parametros.setFiltroNombreCliente(razonSocial);//razon social
    ArrayList<String> file=new ArrayList<String>();   
   SimpleDateFormat sdfArchivo = new SimpleDateFormat("ddMMyyyy");
    String fileName=Constante.PREFIJO_ARCHIVO_PROGRAMACION+"_"+eDiaOperativoRep.getOperacion().getCliente().getNombreCorto().trim().toLowerCase()
		   +eDiaOperativoRep.getOperacion().getId()
		   +sdfArchivo.format(eDiaOperativoRep.getFechaEstimadaCarga())+".xls";
   
   
   ParametrosListar paramListar= new ParametrosListar();   
   paramListar.setFiltroParametro(Parametro.ALIAS_DIRECTORIO_ARCHIVOS);   
   oRespuesta=dParametro.recuperarRegistros(paramListar);
   if (oRespuesta.estado == false) {
	    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
   }
   Parametro eParametro = (Parametro)  oRespuesta.contenido.getCarga().get(0);
   if(eParametro==null || eParametro.getValor()==null || eParametro.getValor().length()==0){
	   throw new Exception("Falta configurar el directorio");
	   
   }else{
	   File validaDirectorio=new File(eParametro.getValor().toLowerCase());
	   if(!validaDirectorio.isDirectory()){
		   String mensaje=gestorDiccionario.getMessage("sgo.directorioArchivo.noExiste", null, locale)+ ": "+ eParametro.getValor().toLowerCase();
		   throw new Exception(mensaje);
	   }
   }   
   String directorio=eParametro.getValor().toLowerCase();   
   File fileXLS=new File(directorio+File.separator+fileName);
   String path_archivo=null;
   if(!fileXLS.exists()){	  
	    path_archivo=generarArchivo(parametros.getFiltroDiaOperativo(),locale,directorio);		   
	   //throw new Exception(gestorDiccionario.getMessage("sgo.programacion.NoEncontroArchivo", null, locale));
   }else{
	   path_archivo=fileXLS.getAbsolutePath();	   
   }
   file.add(path_archivo);

   //enviamos los parametros del correo, el listado de usuarios con rol transportista y el listado de usuarioscon rol modulo transporte
   //respuesta.estado = dMailNotifica.enviarMailModuloProgramacion(parametros,file);
   respuesta.estado = dMailNotifica.enviarMailModuloProgramacionComentario(parametros,file);
   
   if(respuesta.estado){
	   respuesta.mensaje = gestorDiccionario.getMessage("sgo.NotificacionExitosa", null, locale);
	   //Actualizamos el estado del diaOperativo
	   DiaOperativo eDiaOperativo=new DiaOperativo();
	   eDiaOperativo.setId(parametros.getFiltroDiaOperativo());
	   eDiaOperativo.setActualizadoEl(Calendar.getInstance().getTime().getTime());
	   // Actualiza los datos de auditoria local
	   direccionIp = httpRequest.getHeader("X-FORWARDED-FOR");
	   if (direccionIp == null) {
	    direccionIp = httpRequest.getRemoteAddr();
	   }
	   eDiaOperativo.setActualizadoPor(principal.getID());
	   eDiaOperativo.setIpActualizacion(direccionIp);
	   eDiaOperativo.setEstado(DiaOperativo.ESTADO_PROGRAMACION);
	   dDiaOperativo.ActualizarEstadoRegistro(eDiaOperativo);
   }
   else{
	   respuesta.mensaje = gestorDiccionario.getMessage("sgo.NotificacionNOExitosa", null, locale);
	   throw new Exception(gestorDiccionario.getMessage("sgo.NotificacionNOExitosa", null, locale));
   }
  } catch (Exception ex) {
   //ex.printStackTrace();
	  Utilidades.gestionaError(ex, sNombreClase, "enviarMailModuloProgramacion");
   respuesta.estado = false;
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.NotificacionNOExitosa", null, locale);
  }
  return respuesta;
 }
 
 @RequestMapping(value = URL_REPORTE_PROGRAMACION_RELATIVA, method = RequestMethod.GET)
 public void mostrarReporteProgramacion(HttpServletRequest httpRequest, HttpServletResponse response, Locale locale) {
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
   
//   if (httpRequest.getParameter("formato") != null) {
//    formatoReporte=((httpRequest.getParameter("formato")));
//   }
   if (httpRequest.getParameter("idDiaOperativo") != null) {
	    parametros.setFiltroDiaOperativo(Integer.parseInt((httpRequest.getParameter("idDiaOperativo"))));
   } else {
    throw new Exception("No se ingreso el dia operativo a visualizar");
   }   
  
   ParametrosListar paramListar= new ParametrosListar();   
   paramListar.setFiltroParametro(Parametro.ALIAS_DIRECTORIO_ARCHIVOS);   
   respuesta=dParametro.recuperarRegistros(paramListar);
   if (respuesta.estado == false) {
	    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
   }
   Parametro eParametro = (Parametro)  respuesta.contenido.getCarga().get(0);
   if(eParametro==null || eParametro.getValor()==null || eParametro.getValor().length()==0){
	   throw new Exception(gestorDiccionario.getMessage("sgo.directorioArchivo.noExiste", null, locale));
   }   
   String directorio_archivo=eParametro.getValor().toLowerCase();
   
   
   SimpleDateFormat sdfArchivo = new SimpleDateFormat("ddMMyyyy");
   respuesta = dDiaOperativo.recuperarRegistro(parametros.getFiltroDiaOperativo());
   if (respuesta.estado==true && respuesta.contenido.getCarga().size() > 0){
	   DiaOperativo eDiaOperativo = (DiaOperativo) respuesta.contenido.getCarga().get(0);  	   
	   String fileName = "";
	   fileName=Constante.PREFIJO_ARCHIVO_PROGRAMACION+"_"+eDiaOperativo.getOperacion().getCliente().getNombreCorto().trim().toLowerCase()
			   +eDiaOperativo.getOperacion().getId()
			   +sdfArchivo.format(eDiaOperativo.getFechaEstimadaCarga())+".xls";
	   InputStream is=null;
		   String path_archivo=generarArchivo(parametros.getFiltroDiaOperativo(),locale,directorio_archivo);
		   if (path_archivo != null && !path_archivo.isEmpty()) {
				response.setContentType("application/vnd.ms-excel");   
				response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
				is = new FileInputStream(directorio_archivo+File.separator+fileName);            
	           IOUtils.copy(is, response.getOutputStream());             
	           response.flushBuffer();
	           is.close();
		   }   
   }	
  } catch (Exception ex) {
  //ex.printStackTrace();
	  Utilidades.gestionaError(ex, sNombreClase, "mostrarReporteProgramacion");
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
  }
 }
 
 public String generarArchivo(int idDiaOperativo,Locale locale,String directorio_archivo){
	 String path_archivo=null;
	 ParametrosListar parametros = new ParametrosListar();	   
	  parametros.setFiltroDiaOperativo(idDiaOperativo);
	  RespuestaCompuesta respuesta=null;
	  String mensajeRespuesta = "";
	   try{	   
		   respuesta = dProgramacion.recuperarRegistrosReporte(parametros);
		   if (respuesta.estado==false){
		    mensajeRespuesta = gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale);
		    throw new Exception(mensajeRespuesta);
		   }		   
		    
			   DetalleProgramacion cierre =null;
			   HashMap<String,Object> hmValor = null;
			   ArrayList<HashMap<?,?>> hmRegistros = null;
			   hmRegistros= new  ArrayList<HashMap<?,?>>();
			   ArrayList<?> elementos =(ArrayList<?>) respuesta.contenido.getCarga();
			   
			   String fecha_arribo = "";
			   String fecha_cargo="";
			   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			   SimpleDateFormat sdfArchivo = new SimpleDateFormat("ddMMyyyy");
			   
			   for (int indice=0; indice <elementos.size();indice++){
			    cierre= (DetalleProgramacion) elementos.get(indice);
			    hmValor= new HashMap<String,Object>();
			    
			    //hmValor.put("id_planta", cierre.getPlanta().getId());
			    hmValor.put("planta_despacho", cierre.getPlanta().getDescripcion());
			    hmValor.put("orden_compra", cierre.getOrdenCompra());
			    hmValor.put("producto", cierre.getProducto().getNombre());
			    //fecha operativa
			    Date fechaOperativa=cierre.getProgramacion().getDiaOperativo().getFechaOperativa();
			    Date fechaEstimadaCarga = cierre.getProgramacion().getDiaOperativo().getFechaEstimadaCarga();
			    
			    if(fechaOperativa.equals(fechaEstimadaCarga)){
			    	fecha_arribo = sdf.format(fechaOperativa);
			    } else if(fechaOperativa.after(fechaEstimadaCarga)) {
			    	 //fecha un dia menos
				    Calendar cal = Calendar.getInstance();
					cal.setTime(fechaOperativa);	
					cal.add(Calendar.DAY_OF_YEAR, -1);
					Date fecha = cal.getTime();
					fecha_arribo = sdf.format(fecha);
			    } else {
			    	fecha_arribo = "";
			    }
			    
			    //fecha_arribo = sdf.format(fechaOperativa);
			    hmValor.put("fecha_arribo", fecha_arribo);
			    
			    //fecha un dia mas
			    /*Calendar cal = Calendar.getInstance();
				cal.setTime(fechaOperativa);	
				cal.add(Calendar.DAY_OF_YEAR, 1);*/
				
				//Date fecha_descarga = cal.getTime();
			    //hmValor.put("fecha_descarga", sdf.format(fecha_descarga) );
				hmValor.put("fecha_descarga", sdf.format(fechaOperativa) );	
			    //hmValor.put("vol_total", cierre.getProgramacion().getTotalVolumenCisterna());			    
			    //fecha_cargo=sdf.format(cierre.getProgramacion().getDiaOperativo().getFechaEstimadaCarga());			    
			    //hmValor.put("fecha_cargo", fecha_cargo );    
			    hmValor.put("fecha_cargo", sdf.format(fechaEstimadaCarga) );	
			    hmValor.put("transportista", cierre.getProgramacion().getTransportista().getRazonSocial());			    
			    hmValor.put("scop", cierre.getCodigoScop());			    
			    hmValor.put("placa_tracto", cierre.getCisterna().getTracto().getPlaca());			    
			    hmValor.put("placa_cisterna", cierre.getCisterna().getPlaca());	
			    
			  //Agregado por req 9000002608 para Programacion=============================
			    hmValor.put("compartimento", cierre.getNumeroCompartimiento());	
			    //========================================================================
			    hmValor.put("cubicacion",  cierre.getCisterna().getTarjetaCubicacion());	
			  //Agregado por req 9000002608 para Programacion=============================
			    NumberFormat nf = NumberFormat.getNumberInstance(Locale.UK);
				DecimalFormat df = (DecimalFormat)nf;
			    hmValor.put("volumen", df.format(cierre.getCapacidadVolumetrica()));
			  //==========================================================================
				  //Comentado por req 9000002608 para Programacion=============================
//			    hmValor.put("volumen", cierre.getCapacidadCisternaTotal());
			  //==========================================================================
			    hmValor.put("codigo_sap_unidad", cierre.getCisterna().getTracto().getCodigoReferencia());
			    hmValor.put("conductor", cierre.getConductor().getNombreCompleto());
			    hmValor.put("brevete", cierre.getConductor().getBrevete());
			    hmValor.put("pedido_sap", cierre.getCodigoSapPedido());
			    hmRegistros.add(hmValor);
			   }
			   respuesta = dDiaOperativo.recuperarRegistro(parametros.getFiltroDiaOperativo());
			   if (respuesta.estado==true && respuesta.contenido.getCarga().size() > 0){
				   
				   DiaOperativo eDiaOperativo = (DiaOperativo) respuesta.contenido.getCarga().get(0);
				   String tituloReporte= eDiaOperativo.getOperacion().getCliente().getRazonSocial()+" - OPERACI�N:" + eDiaOperativo.getOperacion().getNombre();
				 
				   Reporteador uReporteador = new Reporteador();
				   uReporteador.setRutaServlet(servletContext.getRealPath("/"));
				   ArrayList<Campo> listaCampos = this.generarCamposProgramacion();				   
				   String rutaPlantilla="";				   
				   String fileName = "";				      
					   rutaPlantilla= servletContext.getRealPath("/")+File.separator+"pages"+File.separator+"reportes"+File.separator+"Programacion.xls";					   
					   fileName=Constante.PREFIJO_ARCHIVO_PROGRAMACION+"_"+eDiaOperativo.getOperacion().getCliente().getNombreCorto().trim().toLowerCase()
							   +eDiaOperativo.getOperacion().getId()
							   +sdfArchivo.format(eDiaOperativo.getFechaEstimadaCarga())+".xls";		  
					   path_archivo=uReporteador.generarReportePlantillaListadoExcel(rutaPlantilla ,hmRegistros,listaCampos,tituloReporte,directorio_archivo,fileName,4);						
			   }
			   
		   
	   }catch(Exception ex){
		   
	   }
	 return path_archivo;
 }
 
 
 
 @RequestMapping(value = URL_VERIFICA_NOTIFICAR_RELATIVA, method = RequestMethod.GET)
 public @ResponseBody
 RespuestaCompuesta recuperaRegistroNotificacion(int ID, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;

  try {
   principal = this.getCurrentUser();
   respuesta = dEnlace.recuperarRegistro(URL_VERIFICA_NOTIFICAR_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
   if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
    throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
   }
   respuesta = dDetalleProgramacion.recuperarDetalleProgramacionPorDiaOperativo(ID);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
   }else{
	   respuesta.mensaje = gestorDiccionario.getMessage("sgo.programacion.ValidacionNotificacion", null, locale);
	   ArrayList<?> elementos =(ArrayList<?>) respuesta.contenido.getCarga();
	   DetalleProgramacion detalleProgra=null;
	   for (int indice=0; indice <elementos.size();indice++){
		   detalleProgra= (DetalleProgramacion) elementos.get(indice);
		   if(/*detalleProgra.getIdProgramacion()>0 	// Ticket 9000002708 				  
				   && */ detalleProgra.getCodigoSapPedido()!=null  && detalleProgra.getCodigoSapPedido().length()>0){		   
		   }else{
			   respuesta.estado = false;
			   respuesta.mensaje = gestorDiccionario.getMessage("sgo.programacion.FaltaCompletarDatos", null, locale);
			   break;
		   }   
	   }
   }
   
    
  } catch (Exception ex) {
   //ex.printStackTrace();
	  Utilidades.gestionaError(ex, sNombreClase, "recuperaRegistroNotificacion");
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
  }
  return respuesta;
 }
 
 @RequestMapping(value = URL_ELIMINAR_RELATIVA, method = RequestMethod.POST) 
	//public @ResponseBody RespuestaCompuesta eliminarRegistro(@PathVariable("ID") Integer [] ID,
 	public @ResponseBody RespuestaCompuesta eliminarRegistro(@RequestBody Integer [] ID,
		HttpServletRequest peticionHttp, Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		Bitacora eBitacora=null;
		//String direccionIp="";
		try {
			//Inicia la transaccion
			this.transaccion = new DataSourceTransactionManager(dDetalleProgramacion.getDataSource());
			definicionTransaccion = new DefaultTransactionDefinition();
			estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
			eBitacora = new Bitacora();
			//Recuperar el usuario actual
			principal = this.getCurrentUser();
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_ELIMINAR_COMPLETA);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}
			Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
			//Verificar si cuenta con el permiso necesario			
			if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
				throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale));
		}			
            //7000001924 respuesta= dDetalleProgramacion.eliminarRegistro(ID);
			//respuesta = dDetalleProgramacion.eliminarRegistroMismoCisterna(ID); //7000001924
			respuesta = dDetalleProgramacion.eliminarRegistroGrupoCisterna(ID); //7000001924
      if (respuesta.estado==false){          	
      	throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido",null,locale));
      }
      //Guardar en la bitacora
      //ObjectMapper mapper = new ObjectMapper();
      eBitacora.setUsuario(principal.getNombre());
      eBitacora.setAccion(URL_ELIMINAR_COMPLETA);
      eBitacora.setTabla(DetalleProgramacionDao.NOMBRE_TABLA);
      eBitacora.setIdentificador(String.valueOf(ID[0]));
      //eBitacora.setContenido(mapper.writeValueAsString(eDetalleProgramacion));
      eBitacora.setRealizadoEl(Calendar.getInstance().getTime().getTime());
      eBitacora.setRealizadoPor(principal.getID());
      respuesta= dBitacora.guardarRegistro(eBitacora);
      if (respuesta.estado==false){     	
        	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
      }  
  	respuesta.mensaje=gestorDiccionario.getMessage("sgo.actualizarExitoso",new Object[] {eBitacora.getFechaRealizacion().substring(0, 9),eBitacora.getFechaRealizacion().substring(10),principal.getIdentidad() },locale);;
  	this.transaccion.commit(estadoTransaccion);
		} catch (Exception ex){
			//ex.printStackTrace();
			Utilidades.gestionaError(ex, sNombreClase, "eliminarRegistro");
			this.transaccion.rollback(estadoTransaccion);
			respuesta.estado=false;
			respuesta.contenido = null;
			respuesta.mensaje=ex.getMessage();
		}
		return respuesta;
	}
 
	@RequestMapping(value = URL_ELIMINAR_RELATIVA+"/{ID}" ,method = RequestMethod.DELETE)
	public @ResponseBody RespuestaCompuesta eliminarRegistro(@PathVariable("ID") int ID,
		HttpServletRequest peticionHttp, Locale locale){
		RespuestaCompuesta respuesta = null;
		AuthenticatedUserDetails principal = null;
		TransactionDefinition definicionTransaccion = null;
		TransactionStatus estadoTransaccion = null;
		Bitacora eBitacora=null;
		//String direccionIp="";
		try {
			//Inicia la transaccion
			this.transaccion = new DataSourceTransactionManager(dDetalleProgramacion.getDataSource());
			definicionTransaccion = new DefaultTransactionDefinition();
			estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
			eBitacora = new Bitacora();
			//Recuperar el usuario actual
			principal = this.getCurrentUser();
			//Recuperar el enlace de la accion
			respuesta = dEnlace.recuperarRegistro(URL_ELIMINAR_COMPLETA);
			if (respuesta.estado==false){
				throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada",null,locale));
			}
			Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
			//Verificar si cuenta con el permiso necesario			
			if (!principal.getRol().searchPermiso(eEnlace.getPermiso())){
				throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso",null,locale));
 		}			

         //7000001924 respuesta= dDetalleProgramacion.eliminarRegistro(ID);
		 respuesta = dDetalleProgramacion.eliminarRegistroMismoCisterna(ID); //7000001924
         if (respuesta.estado==false){          	
         	throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido",null,locale));
         }
         //Guardar en la bitacora
         //ObjectMapper mapper = new ObjectMapper();
         eBitacora.setUsuario(principal.getNombre());
         eBitacora.setAccion(URL_ELIMINAR_COMPLETA);
         eBitacora.setTabla(DetalleProgramacionDao.NOMBRE_TABLA);
         eBitacora.setIdentificador(String.valueOf(ID));
         //eBitacora.setContenido(mapper.writeValueAsString(eDetalleProgramacion));
         eBitacora.setRealizadoEl(Calendar.getInstance().getTime().getTime());
         eBitacora.setRealizadoPor(principal.getID());
         respuesta= dBitacora.guardarRegistro(eBitacora);
         if (respuesta.estado==false){     	
           	throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido",null,locale));
         }  
     	respuesta.mensaje=gestorDiccionario.getMessage("sgo.actualizarExitoso",new Object[] {eBitacora.getFechaRealizacion().substring(0, 9),eBitacora.getFechaRealizacion().substring(10),principal.getIdentidad() },locale);;
     	this.transaccion.commit(estadoTransaccion);
		} catch (Exception ex){
			//ex.printStackTrace();
			Utilidades.gestionaError(ex, sNombreClase, "eliminarRegistro");
			this.transaccion.rollback(estadoTransaccion);
			respuesta.estado=false;
			respuesta.contenido = null;
			respuesta.mensaje=ex.getMessage();
		}
		return respuesta;
	}
 
 private  AuthenticatedUserDetails getCurrentUser(){
	return  (AuthenticatedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 }

 private ArrayList<Campo> generarCamposProgramacion() {
	  ArrayList<Campo> listaCampos = null;
	  try {
	   listaCampos = new ArrayList<Campo>();
	   Campo eCampo = null;
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("Planta Despacho");
	   eCampo.setNombre("planta_despacho");
	   eCampo.setTipo(Campo.TIPO_TEXTO);	   
	   listaCampos.add(eCampo);
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("Orden Compra");
	   eCampo.setNombre("orden_compra");
	   eCampo.setTipo(Campo.TIPO_TEXTO);	   
	   listaCampos.add(eCampo);
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("Producto");
	   eCampo.setNombre("producto");
	   eCampo.setTipo(Campo.TIPO_TEXTO);	   
	   listaCampos.add(eCampo);  
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("fecha_arribo");
	   eCampo.setNombre("fecha_arribo");
	   eCampo.setTipo(Campo.TIPO_TEXTO);	   
	   listaCampos.add(eCampo);
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("fecha_descarga");
	   eCampo.setNombre("fecha_descarga");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);
	   /*
	   eCampo = new Campo();
	   eCampo.setEtiqueta("vol_total");
	   eCampo.setNombre("vol_total");
	   eCampo.setTipo(Campo.TIPO_DECIMAL);
	   listaCampos.add(eCampo);
		*/
	   eCampo = new Campo();
	   eCampo.setEtiqueta("fecha_cargo");
	   eCampo.setNombre("fecha_cargo");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("transportista");
	   eCampo.setNombre("transportista");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("scop");
	   eCampo.setNombre("scop");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("placa_tracto");
	   eCampo.setNombre("placa_tracto");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);

	   eCampo = new Campo();
	   eCampo.setEtiqueta("placa_cisterna");
	   eCampo.setNombre("placa_cisterna");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);
	   
	 //Agregado por req 9000002608 para Programacion=============================
	   eCampo = new Campo();
	   eCampo.setEtiqueta("compartimento");
	   eCampo.setNombre("compartimento");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);
	   //========================================================================
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("cubicacion");
	   eCampo.setNombre("cubicacion");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("volumen");
	   eCampo.setNombre("volumen");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("codigo_sap_unidad");
	   eCampo.setNombre("codigo_sap_unidad");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);

	   eCampo = new Campo();
	   eCampo.setEtiqueta("conductor");
	   eCampo.setNombre("conductor");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("brevete");
	   eCampo.setNombre("brevete");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);  
	   
	   eCampo = new Campo();
	   eCampo.setEtiqueta("pedido_sap");
	   eCampo.setNombre("pedido_sap");
	   eCampo.setTipo(Campo.TIPO_TEXTO);
	   listaCampos.add(eCampo);  
	   
	   
	  } catch (Exception ex) {

	  }
	  return listaCampos;
	 }
 
 @RequestMapping(value = URL_RECUPERAR_PLANIFICACION, method = RequestMethod.GET)
 public @ResponseBody
 RespuestaCompuesta recuperaPlanificacion(int ID, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;
  DiaOperativo eDiaOperativo= null;
  Contenido<DiaOperativo> contenido=null;
  try {
   // Recupera el usuario actual
   principal = this.getCurrentUser();
   // Recuperar el enlace de la accion
   respuesta = dEnlace.recuperarRegistro(URL_GUARDAR_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
   // Verificar si cuenta con el permiso necesario
   if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
    throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
   }
   // Recuperar el registro
   respuesta = dDiaOperativo.recuperarRegistro(ID);
   if (respuesta.estado==false){
    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
   }
   eDiaOperativo = (DiaOperativo) respuesta.contenido.carga.get(0);

   ParametrosListar parametros = new ParametrosListar();
   parametros.setPaginacion(Constante.SIN_PAGINACION);
   parametros.setCampoOrdenamiento("id");
   parametros.setSentidoOrdenamiento("asc");
   parametros.setFiltroDiaOperativo(eDiaOperativo.getId());

   respuesta = dPlanificacion.recuperarRegistros(parametros);
   if (respuesta.estado==false){
    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
   }

   ArrayList<?> listaPlanificacion = (ArrayList<?>) respuesta.contenido.carga;
   //LA LISTA DE PLANIFICACIONES PUEDE ESTAR VACIA
   /*if (listaPlanificacion.size()==0) {
    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
   }*/
   // Inicio Ticket 9000002608
   parametros.setIdOperacion(eDiaOperativo.getIdOperacion());
   List<ProductoProgramacion> listaProductoProgramacion = dPlanificacion.recuperarRegistrosPorProgramacion(parametros);
   eDiaOperativo.setProductos(listaProductoProgramacion);
   
   
   ProgramacionPlanificada programacionPlanificada = dProgramacion.recuperarCabeceraProgramacion(parametros);
   eDiaOperativo.setProgramacionPlanificada(programacionPlanificada);
   // Fin Ticket 9000002608
   for(Object tPlanificacion : listaPlanificacion){
    eDiaOperativo.agregarPlanificacion((Planificacion)tPlanificacion);
   }

   contenido = new Contenido<DiaOperativo>();
   contenido.carga= new ArrayList<DiaOperativo>();
   contenido.carga.add(eDiaOperativo);   
   respuesta.contenido=contenido;
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.recuperarExitoso", null, locale);
  } catch (Exception ex) {
	Utilidades.gestionaError(ex, "ProgramacionControlador", "recuperaRegistro");
	//ex.printStackTrace();
	respuesta.estado=false;
	respuesta.contenido = null;
	respuesta.mensaje=ex.getMessage();
  }
  return respuesta;
 }
 @RequestMapping(value = URL_COMPLETAR_RELATIVA, method = RequestMethod.POST)
 public @ResponseBody
 RespuestaCompuesta completarRegistro(@RequestBody Programacion eProgramacion, HttpServletRequest peticionHttp, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;
  TransactionDefinition definicionTransaccion = null;
  TransactionStatus estadoTransaccion = null;
  Bitacora eBitacora = null;
  String direccionIp = "";
  try {
   // Inicia la transaccion
   this.transaccion = new DataSourceTransactionManager(dProgramacion.getDataSource());
   definicionTransaccion = new DefaultTransactionDefinition();
   estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
   eBitacora = new Bitacora();
   // Recuperar el usuario actual
   principal = this.getCurrentUser();
   // Recuperar el enlace de la accion
   respuesta = dEnlace.recuperarRegistro(URL_COMPLETAR_COMPLETA);
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
   
   eProgramacion.setEstado(Constante.ESTADO_ACTIVO);   
   eProgramacion.setActualizadoEl(Calendar.getInstance().getTime().getTime());
   eProgramacion.setActualizadoPor(principal.getID());
   eProgramacion.setIpActualizacion(direccionIp);
   
   
   
   //recuperamos la cisterna original con sus respectivos compartimentos
   respuesta = dProgramacion.actualizarRegistro(eProgramacion);
   
   if (respuesta.estado == false) {
     throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
   }
// Guardar en la bitacora
   ObjectMapper mapper = new ObjectMapper();
   eBitacora.setUsuario(principal.getNombre());
   eBitacora.setAccion(URL_ACTUALIZAR_COMPLETA);
   eBitacora.setTabla(ProgramacionDao.NOMBRE_TABLA);
   eBitacora.setIdentificador(String.valueOf(eProgramacion.getId()));
   eBitacora.setContenido(mapper.writeValueAsString(eProgramacion));
   eBitacora.setRealizadoEl(eProgramacion.getActualizadoEl());
   eBitacora.setRealizadoPor(eProgramacion.getActualizadoPor());
   respuesta = dBitacora.guardarRegistro(eBitacora);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
   }
   //recuperamos la cisterna original con sus respectivos compartimentos
//   respuesta = dDetalleProgramacion.recuperarDetalleProgramacion(eProgramacion.getId());
//   if (respuesta.estado == false) {
//     throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
//   }
   DetalleProgramacion detalle=null;
//   DetalleProgramacion detalleAux=null;
//   ParametrosListar parametrosVigencia=null;
//   String mensajeVigencia=null;
   if(eProgramacion.getProgramaciones()!=null && eProgramacion.getProgramaciones().size()>0){	   
	    for(int i=0;i<eProgramacion.getProgramaciones().size();i++){
	   
	    	   detalle=(DetalleProgramacion)eProgramacion.getProgramaciones().get(i);
		 	   detalle.setIdProgramacion(eProgramacion.getId());
		 	   detalle.setIpActualizacion(direccionIp);
		 	   
//		 	  for(int j=0;j<eProgramacion.getProgramaciones().size();j++){
//		 		 detalleAux=(DetalleProgramacion)eProgramacion.getProgramaciones().get(j);
//		 		  if(i!=j){
//		 			 if(detalle.getIdCisterna()==detalleAux.getIdCisterna()){
//		 				throw new Exception("Las Cisternas no deben estar duplicadas."); 
//		 			 }
//		 			 if(detalle.getIdConductor()==detalleAux.getIdConductor()){
//		 				throw new Exception("Los Conductores no deben estar duplicados."); 
//		 			 }
//		 		  }
//		 	  }
		 	  
		 	  //verifica si existe algun documento caducado CISTERNA
//		 	 parametrosVigencia=new ParametrosListar();
//		 	 parametrosVigencia.setFiltroPerteneceA(detalle.getIdCisterna());
//		 	 parametrosVigencia.setFiltroIdEntidad(Vigencia.DOCUMENTO_CISTERNA);		 	  
//		 	 mensajeVigencia=vigenciaBusiness.verificaVigenciaDocumento(parametrosVigencia, locale);
//		 	 if(mensajeVigencia!=null && mensajeVigencia.length()>0){
//		 		throw new Exception(mensajeVigencia); 
//		 	 }
		 	 
		 	  //verifica si existe algun documento caducado CONDUCTOR
//		 	 parametrosVigencia=new ParametrosListar();
//		 	 parametrosVigencia.setFiltroPerteneceA(detalle.getIdConductor());
//		 	 parametrosVigencia.setFiltroIdEntidad(Vigencia.DOCUMENTO_CONDUCTOR);		 	  
//		 	 mensajeVigencia=vigenciaBusiness.verificaVigenciaDocumento(parametrosVigencia, locale);
//		 	 if(mensajeVigencia!=null && mensajeVigencia.length()>0){
//		 		throw new Exception(mensajeVigencia); 
//		 	 }
		 	 
		 	 if(!Utilidades.esValido(detalle.getCompletar())){
		 		 throw new Exception(gestorDiccionario.getMessage("sgo.guardarProgramacionFallido", null, locale));
		 	 }
		 	 else{
		 		 if(detalle.getCompletar() == 1){
		 			//valida los datos que vienen del formulario
		 		    Respuesta validacion = Utilidades.validacionProgramacionXSS(detalle, gestorDiccionario, locale);
		 		    if (validacion.estado == false) {
		 		      throw new Exception(validacion.valor);
		 		    }
		 		 } else {
		 			Respuesta validacion = Utilidades.validacionCompletarProgramacionXSS(detalle, gestorDiccionario, locale);
		 		    if (validacion.estado == false) {
		 		      throw new Exception(validacion.valor);
		 		    }
		 		 }
		 	 }
		  	   if(detalle.getId()>0){//actualizar
		 		   	respuesta = dDetalleProgramacion.actualizarRegistroCompletar(detalle);
				     if (respuesta.estado == false) {
				      throw new Exception(gestorDiccionario.getMessage("sgo.guardarProgramacionFallido", null, locale));
				     }	 
		 	   }else{//insertar
		 		   	 respuesta = dDetalleProgramacion.guardarRegistro(detalle);
				     if (respuesta.estado == false) {
				      throw new Exception(gestorDiccionario.getMessage("sgo.guardarProgramacionFallido", null, locale));
				     }	
		 	   }
	   
		   }
   }
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.actualizarExitoso", new Object[] { eProgramacion.getFechaActualizacion().substring(0, 9), eProgramacion.getFechaActualizacion().substring(10),principal.getIdentidad() }, locale);
   this.transaccion.commit(estadoTransaccion);
  } catch (Exception ex) {
    //ex.printStackTrace();
	  Utilidades.gestionaError(ex, sNombreClase, "completarRegistro");
    this.transaccion.rollback(estadoTransaccion);
    respuesta.estado = false;
    respuesta.contenido = null;
    respuesta.mensaje = ex.getMessage();
  }
  return respuesta;
 }
 
 @RequestMapping(value = URL_COMENTAR_RELATIVA, method = RequestMethod.POST)
 public @ResponseBody Respuesta comentarProgramacion(@RequestBody Programacion eProgramacion, HttpServletRequest peticionHttp, Locale locale) {
	 RespuestaCompuesta respuesta = null;
	 AuthenticatedUserDetails principal = null;
	 TransactionDefinition definicionTransaccion = null;
	 TransactionStatus estadoTransaccion = null;
	 Bitacora eBitacora = null;
	 String direccionIp = "";
	 try {
	 	// Inicia la transaccion
	 	this.transaccion = new DataSourceTransactionManager(dProgramacion.getDataSource());
	 	definicionTransaccion = new DefaultTransactionDefinition();
	 	estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
	 	eBitacora = new Bitacora();
	 	// Recuperar el usuario actual
	 	principal = this.getCurrentUser();
	 	// Recuperar el enlace de la accion
	 	respuesta = dEnlace.recuperarRegistro(URL_COMPLETAR_COMPLETA); //para el permiso
	 	if (respuesta.estado == false) {
	 		throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
	 	}
	 	Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga()
	 			.get(0);
	 	// Verificar si cuenta con el permiso necesario
	 	if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
	 		throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
	 	}
	 	// Auditoria local (En el mismo registro)
	 	direccionIp = peticionHttp.getHeader("X-FORWARDED-FOR");
	 	if (direccionIp == null) {
	 		direccionIp = peticionHttp.getRemoteAddr();
	 	}
	 	//validar que el estado del D�a Operativo sea 1: Planificado.
	 	respuesta = dDiaOperativo.recuperarRegistro(eProgramacion.getIdDiaOperativo());
	 	if (respuesta.estado == false) {
	 	    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
	 	}
	 	
	 	DiaOperativo dOperativo=(DiaOperativo) respuesta.getContenido().getCarga().get(0);
	 	
	 	if(dOperativo.getEstado()!=1){
	 	    throw new Exception(gestorDiccionario.getMessage("sgo.noComentarRegistro", null, locale));
	 	}
	 	
	 	eProgramacion.setComentario(eProgramacion.getComentario().trim()); 
	 	eProgramacion.setActualizadoEl(Calendar.getInstance().getTime().getTime());
	 	eProgramacion.setActualizadoPor(principal.getID());
	 	eProgramacion.setIpActualizacion(direccionIp);
	 	
	 	// recuperamos la cisterna original con sus respectivos
	 	// compartimentos
	 	respuesta = dProgramacion.actualizarComentario(eProgramacion);
	 	if (respuesta.estado == false) {
	 		throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
	 	}
	 	// Guardar en la bitacora
	 	ObjectMapper mapper = new ObjectMapper();
	 	eBitacora.setUsuario(principal.getNombre());
	 	eBitacora.setAccion(URL_COMENTAR_COMPLETA);
	 	eBitacora.setTabla(ProgramacionDao.NOMBRE_TABLA);
	 	eBitacora.setIdentificador(String.valueOf(eProgramacion.getId()));
	 	eBitacora.setContenido(mapper.writeValueAsString(eProgramacion));
	 	eBitacora.setRealizadoEl(eProgramacion.getActualizadoEl());
	 	eBitacora.setRealizadoPor(eProgramacion.getActualizadoPor());
	 	respuesta = dBitacora.guardarRegistro(eBitacora);
	 	if (respuesta.estado == false) {
	 		throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
	 	}

	 	respuesta.mensaje = gestorDiccionario.getMessage("sgo.actualizarExitoso",new Object[] {eProgramacion.getFechaActualizacion().substring(0, 9), eProgramacion.getFechaActualizacion().substring(10), principal.getIdentidad() }, locale);
	 	this.transaccion.commit(estadoTransaccion);
	 } catch (Exception ex) {
	 	// ex.printStackTrace();
	 	Utilidades.gestionaError(ex, sNombreClase, "actualizarRegistro");
	 	this.transaccion.rollback(estadoTransaccion);
	 	respuesta.estado = false;
	 	respuesta.contenido = null;
	 	respuesta.mensaje = ex.getMessage();
	 }
	 return respuesta;  		
 }
 
 @RequestMapping(value = URL_RECUPERAR_CORREO_RELATIVA, method = RequestMethod.GET)
 public @ResponseBody
 RespuestaCompuesta recuperarCorreo(int idDiaOperativo, Locale locale) {
  RespuestaCompuesta respuesta = null;
  AuthenticatedUserDetails principal = null;

  try {
   principal = this.getCurrentUser();
   respuesta = dEnlace.recuperarRegistro(URL_RECUPERAR_CORREO_COMPLETA);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
   }
   Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
   if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
    throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
   }
   respuesta = dDetalleProgramacion.recuperarCorreoProgramacion(idDiaOperativo);
   if (respuesta.estado == false) {
    throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
   }
   
   respuesta.mensaje = gestorDiccionario.getMessage("sgo.recuperarExitoso", null, locale);
  } catch (Exception ex) {
   //ex.printStackTrace();
	  Utilidades.gestionaError(ex, sNombreClase, "recuperaRegistro");
   respuesta.estado = false;
   respuesta.contenido = null;
   respuesta.mensaje = ex.getMessage();
  }
  return respuesta;
 }
}
