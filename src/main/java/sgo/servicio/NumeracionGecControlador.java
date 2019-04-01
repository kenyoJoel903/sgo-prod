package sgo.servicio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
import sgo.datos.EnlaceDao;
import sgo.datos.NumeracionGecDao;
import sgo.entidad.Bitacora;
import sgo.entidad.Contenido;
import sgo.entidad.Enlace;
import sgo.entidad.MenuGestor;
import sgo.entidad.NumeracionGec;
import sgo.entidad.ParametrosListar;
import sgo.entidad.RespuestaCompuesta;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.utilidades.Constante;

//Agregado por req 9000002857

@Controller
public class NumeracionGecControlador {
	
	 @Autowired
	 private MessageSource gestorDiccionario;
	
	 @Autowired
	 private MenuGestor menu;
	 
	 @Autowired
	 private BitacoraDao dBitacora; 
	 
	 @Autowired
	 private EnlaceDao dEnlace;
	 
	 @Autowired
	 private NumeracionGecDao dNumeracionGec;
	 
	 private static final String URL_GESTION_COMPLETA = "/admin/numeracionGec";
	 private static final String URL_GESTION_RELATIVA = "/numeracionGec";
	 private static final String URL_LISTAR_COMPLETA = "/admin/numeracionGec/listar";
	 private static final String URL_LISTAR_RELATIVA = "/numeracionGec/listar";
	 private static final String URL_RECUPERAR_COMPLETA = "/admin/numeracionGec/recuperar";
	 private static final String URL_RECUPERAR_RELATIVA = "/numeracionGec/recuperar";
	 private static final String URL_ACTUALIZAR_ESTADO_COMPLETA = "/admin/numeracionGec/actualizarEstado";
	 private static final String URL_ACTUALIZAR_ESTADO_RELATIVA = "/numeracionGec/actualizarEstado";
	 private static final String URL_GUARDAR_COMPLETA = "/admin/numeracionGec/crear";
	 private static final String URL_GUARDAR_RELATIVA = "/numeracionGec/crear";
	 private static final String URL_ACTUALIZAR_COMPLETA = "/admin/numeracionGec/actualizar";
	 private static final String URL_ACTUALIZAR_RELATIVA = "/numeracionGec/actualizar";
	 
	 private DataSourceTransactionManager transaccion;// Gestor de la transaccion
	 
	 private HashMap<String, String> recuperarMapaValores(Locale locale) {
		  HashMap<String, String> mapaValores = new HashMap<String, String>();
		  try{
			  mapaValores.put("TITULO_LISTADO_REGISTROS", gestorDiccionario.getMessage("sgo.tituloFormularioListado", null, locale)); 
			  mapaValores.put("MENSAJE_CARGANDO", gestorDiccionario.getMessage("sgo.mensajeCargando", null, locale));
			  mapaValores.put("ETIQUETA_BOTON_AGREGAR", gestorDiccionario.getMessage("sgo.etiquetaBotonAgregar", null, locale));
			  mapaValores.put("ETIQUETA_BOTON_MODIFICAR", gestorDiccionario.getMessage("sgo.etiquetaBotonModificar", null, locale));
			  mapaValores.put("ETIQUETA_BOTON_ACTIVAR", gestorDiccionario.getMessage("sgo.etiquetaBotonActivar", null, locale));
			  mapaValores.put("ETIQUETA_BOTON_VER", gestorDiccionario.getMessage("sgo.etiquetaBotonVer", null, locale));
			  mapaValores.put("TITULO_VENTANA_MODAL", gestorDiccionario.getMessage("sgo.tituloVentanaModal", null, locale));
			  mapaValores.put("MENSAJE_CAMBIAR_ESTADO", gestorDiccionario.getMessage("sgo.mensajeCambiarEstado", null, locale));
			  mapaValores.put("ETIQUETA_BOTON_CERRAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCerrar", null, locale));
			  mapaValores.put("ETIQUETA_BOTON_CONFIRMAR", gestorDiccionario.getMessage("sgo.etiquetaBotonConfirmar", null, locale));
			  mapaValores.put("ETIQUETA_BOTON_GUARDAR", gestorDiccionario.getMessage("sgo.etiquetaBotonGuardar", null, locale));
			  mapaValores.put("ETIQUETA_BOTON_CANCELAR", gestorDiccionario.getMessage("sgo.etiquetaBotonCancelar", null, locale));
			  mapaValores.put("MENSAJE_DESEA_CONTINUAR", gestorDiccionario.getMessage("sgo.deseaContinuar",null,locale));			  
			  
		  }catch(Exception ex){
			  
		  }
		  return mapaValores;
	 }
	 
	 @RequestMapping(value = URL_RECUPERAR_RELATIVA, method = RequestMethod.GET)
	 public @ResponseBody RespuestaCompuesta recuperaRegistro(int ID, Locale locale) {
		  RespuestaCompuesta respuesta = null;
		  AuthenticatedUserDetails principal = null;
		  NumeracionGec eNumeracionGec = null;		  
		 
		  try{
			  
			  principal = this.getCurrentUser();
			  
			  respuesta = dEnlace.recuperarRegistro(URL_RECUPERAR_COMPLETA);
			  if (respuesta.estado == false) {
				  throw new Exception(gestorDiccionario.getMessage("sgo.accionNoHabilitada", null, locale));
			  }
			  
			  Enlace eEnlace = (Enlace) respuesta.getContenido().getCarga().get(0);
			  if (!principal.getRol().searchPermiso(eEnlace.getPermiso())) {
				  throw new Exception(gestorDiccionario.getMessage("sgo.faltaPermiso", null, locale));
			  }
			  
			  respuesta = dNumeracionGec.recuperarRegistro(ID);
			  if (respuesta.estado == false) {
				  throw new Exception(gestorDiccionario.getMessage("sgo.recuperarFallido", null, locale));
			  }
			  
			  eNumeracionGec = (NumeracionGec) respuesta.contenido.carga.get(0);
			  
			  Contenido<NumeracionGec> contenido = new Contenido<NumeracionGec>();
			  contenido.carga = new ArrayList<NumeracionGec>();
			  contenido.carga.add(eNumeracionGec);
			  respuesta.contenido = contenido;  
			   
			   respuesta.mensaje = gestorDiccionario.getMessage("sgo.recuperarExitoso", null, locale);
		  } catch (Exception ex) {
			 ex.printStackTrace();
			 respuesta.estado = false;
			 respuesta.contenido = null;
			 respuesta.mensaje = ex.getMessage();
		  }
		  return respuesta;
	 }
	 
	 @RequestMapping(value = URL_LISTAR_RELATIVA, method = RequestMethod.GET)
	 public @ResponseBody RespuestaCompuesta recuperarRegistros(HttpServletRequest httpRequest, Locale locale) {
		 
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
		   
		   parametros.setPaginacion(Constante.CON_PAGINACION);
		   parametros.setFiltroEstado(Constante.FILTRO_TODOS);
			   
			// Recuperar registros
			respuesta = dNumeracionGec.recuperarRegistros(parametros);
			respuesta.mensaje = gestorDiccionario.getMessage("sgo.listarExitoso", null, locale);
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
	 RespuestaCompuesta guardarRegistro(@RequestBody NumeracionGec eNumeracionGec, HttpServletRequest peticionHttp, Locale locale) {
		 
		 RespuestaCompuesta respuesta = null;
		 AuthenticatedUserDetails principal = null;
		 Bitacora eBitacora = null;
		 
		 String ContenidoAuditoria = "";
		 String ClaveGenerada = "";
		 String direccionIp = "";
		 TransactionDefinition definicionTransaccion = null;
		 TransactionStatus estadoTransaccion = null;
		 
		 try{
			 
			   // Inicia la transaccion
			   this.transaccion = new DataSourceTransactionManager(dNumeracionGec.getDataSource());
			   definicionTransaccion = new DefaultTransactionDefinition();
			   estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
			   
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
			   
			   eNumeracionGec.setEstado(Constante.ESTADO_ACTIVO);
			   eNumeracionGec.setActualizadoEl(Calendar.getInstance().getTime().getTime());
			   eNumeracionGec.setActualizadoPor(principal.getID());
			   eNumeracionGec.setCreadoEl(Calendar.getInstance().getTime().getTime());
			   eNumeracionGec.setCreadoPor(principal.getID());
			   eNumeracionGec.setIpActualizacion(direccionIp);
			   eNumeracionGec.setIpCreacion(direccionIp);
			   
			   //Valido primero si no hay otro registro con el mismo alias
			   respuesta = dNumeracionGec.validaRegistroAlias(eNumeracionGec.getAliasOperacion());
			   
			   // Verifica si la accion se ejecuto de forma satisfactoria
			   if (respuesta.estado == false) {
			    throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido", null, locale));
			   }
			   
			   //Si existe el registro valido que no se encuentre activo y que no se encuentre duplicado
			   if(respuesta.getContenido().getCarga().size() > 0){
				   throw new Exception("Ya existe una numeración gec con el alias ingresado");
			   }
			   
			   //Valido segundo si no hay otro registro con misma operacion y año en BD
			   respuesta = dNumeracionGec.validaRegistroIdOperAnio(eNumeracionGec.getIdOperacion(), eNumeracionGec.getAnio());
			   
			   // Verifica si la accion se ejecuto de forma satisfactoria
			   if (respuesta.estado == false) {
			    throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido", null, locale));
			   }
			   
			   //Si existe el registro valido que no se encuentre activo y que no se encuentre duplicado
			   if(respuesta.getContenido().getCarga().size() > 0){
				   throw new Exception("Ya existe una numeración gec con la misma operación y año ingresado");
			   }
			   
			   respuesta = dNumeracionGec.guardarRegistro(eNumeracionGec);
			   // Verifica si la accion se ejecuto de forma satisfactoria
			   if (respuesta.estado == false) {
			    throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido", null, locale));
			   }
			   
			   ClaveGenerada = respuesta.valor;
			   // Guardar en la bitacora
			   ObjectMapper mapper = new ObjectMapper(); 
			   ContenidoAuditoria = mapper.writeValueAsString(eNumeracionGec);
			   
			   eBitacora = new Bitacora();
			   eBitacora.setUsuario(principal.getNombre());
			   eBitacora.setAccion(URL_GUARDAR_COMPLETA);
			   eBitacora.setTabla(NumeracionGecDao.NOMBRE_TABLA);
			   eBitacora.setIdentificador(ClaveGenerada);
			   eBitacora.setContenido(ContenidoAuditoria);
			   eBitacora.setRealizadoEl(eNumeracionGec.getCreadoEl());
			   eBitacora.setRealizadoPor(eNumeracionGec.getCreadoPor());
			   
			   respuesta = dBitacora.guardarRegistro(eBitacora);
			   if (respuesta.estado == false) {
				   throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
			   }
				   
				String dia = eNumeracionGec.getFechaActualizacion().substring(0, 11);
				String hora = eNumeracionGec.getFechaActualizacion().substring(10);		
				respuesta.mensaje = gestorDiccionario.getMessage("sgo.guardarExitoso", new Object[] { dia, hora, principal.getIdentidad() }, locale);
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
		   mapaValores = recuperarMapaValores(locale);
		   vista = new ModelAndView("plantilla");
		   vista.addObject("vistaJSP", "mantenimiento/numeracionGec.jsp");
		   vista.addObject("vistaJS", "mantenimiento/numeracionGec.js");
		   vista.addObject("identidadUsuario", principal.getIdentidad());
		   vista.addObject("menu", listaEnlaces);
		   vista.addObject("mapaValores", mapaValores);
		  } catch (Exception ex) {

		  }
		  return vista;
	 }
	 
	 private AuthenticatedUserDetails getCurrentUser() {
		 return (AuthenticatedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 }
	 
	 @RequestMapping(value = URL_ACTUALIZAR_ESTADO_RELATIVA, method = RequestMethod.POST)
	 public @ResponseBody RespuestaCompuesta actualizarEstadoRegistro(@RequestBody NumeracionGec eNumeracionGec, HttpServletRequest peticionHttp, Locale locale) {
		  RespuestaCompuesta respuesta = null;
		  AuthenticatedUserDetails principal = null;
		  TransactionDefinition definicionTransaccion = null;
		  TransactionStatus estadoTransaccion = null;
		  Bitacora eBitacora = null;
		  String direccionIp = "";
		  
		  try{
			  
			   // Inicia la transaccion
			   this.transaccion = new DataSourceTransactionManager(dNumeracionGec.getDataSource());
			   definicionTransaccion = new DefaultTransactionDefinition();
			   estadoTransaccion = this.transaccion.getTransaction(definicionTransaccion);
			   
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
			   
			   eNumeracionGec.setActualizadoEl(Calendar.getInstance().getTime().getTime());
			   eNumeracionGec.setActualizadoPor(principal.getID());
			   eNumeracionGec.setIpActualizacion(direccionIp);
			   respuesta = dNumeracionGec.actualizarEstadoRegistro(eNumeracionGec);
			   
			   eBitacora = new Bitacora();
			   
			   // Guardar en la bitacora
			   ObjectMapper mapper = new ObjectMapper();
			   eBitacora.setUsuario(principal.getNombre());
			   eBitacora.setAccion(URL_ACTUALIZAR_ESTADO_COMPLETA);
			   eBitacora.setTabla(NumeracionGecDao.NOMBRE_TABLA);
			   eBitacora.setIdentificador(String.valueOf(eNumeracionGec.getId()));
			   eBitacora.setContenido(mapper.writeValueAsString(eNumeracionGec));
			   eBitacora.setRealizadoEl(eNumeracionGec.getActualizadoEl());
			   eBitacora.setRealizadoPor(eNumeracionGec.getActualizadoPor());
			   
			   respuesta = dBitacora.guardarRegistro(eBitacora);
			   if (respuesta.estado == false) {
			    throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
			   }
			   
				String dia = eNumeracionGec.getFechaActualizacion().substring(0, 11);
				String hora = eNumeracionGec.getFechaActualizacion().substring(10);			   
			   respuesta.mensaje = gestorDiccionario.getMessage("sgo.actualizarExitoso", new Object[] { dia, hora,principal.getIdentidad() }, locale);
			   
			   this.transaccion.commit(estadoTransaccion);
			   
			   if (respuesta.estado == false) {
			    throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
			   }
		  } catch (Exception ex) {
			   ex.printStackTrace();
			   this.transaccion.rollback(estadoTransaccion);
			   respuesta.estado = false;
			   respuesta.contenido = null;
			   respuesta.mensaje = ex.getMessage();
		  }
		  return respuesta;
	 }
	 
	 @RequestMapping(value = URL_ACTUALIZAR_RELATIVA, method = RequestMethod.POST)
	 public @ResponseBody RespuestaCompuesta actualizarRegistro(@RequestBody NumeracionGec eNumeracionGec, HttpServletRequest peticionHttp, Locale locale) {
		 
		  RespuestaCompuesta respuesta = null;
		  AuthenticatedUserDetails principal = null;
		  TransactionDefinition definicionTransaccion = null;
		  TransactionStatus estadoTransaccion = null;
		  Bitacora eBitacora = null;
		  String direccionIp = "";
		 
		 try{
			 
			   // Inicia la transaccion
			   this.transaccion = new DataSourceTransactionManager(dNumeracionGec.getDataSource());
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
			   
			   //Valido primero si no hay otro registro igual en BD
			   respuesta = dNumeracionGec.validaRegistroAlias(eNumeracionGec.getAliasOperacion());
			   
			   // Verifica si la accion se ejecuto de forma satisfactoria
			   if (respuesta.estado == false) {
			    throw new Exception(gestorDiccionario.getMessage("sgo.guardarFallido", null, locale));
			   }
			   
			   //Si existe el registro valido que no se encuentre activo y que no se encuentre duplicado
			   if(respuesta.getContenido().getCarga().size() > 0){
				   
				   List<NumeracionGec> lstTemp = (List<NumeracionGec>) respuesta.getContenido().getCarga();
				   
				   for(NumeracionGec eNum : lstTemp){
					   if(eNum.getId() != eNumeracionGec.getId()){
						   throw new Exception("Ya existe una numeración gec con el alias ingresado");
					   }
				   }
			   }
			   
			   eNumeracionGec.setActualizadoEl(Calendar.getInstance().getTime().getTime());
			   eNumeracionGec.setActualizadoPor(principal.getID());
			   eNumeracionGec.setIpActualizacion(direccionIp);
			  
			   //si las validaciones son correctas permito hacer la actualizacion
			   respuesta = dNumeracionGec.actualizarRegistro(eNumeracionGec);
			   if (respuesta.estado == false) {
			    throw new Exception(gestorDiccionario.getMessage("sgo.actualizarFallido", null, locale));
			   }
			   // Guardar en la bitacora
			   ObjectMapper mapper = new ObjectMapper();
			   eBitacora.setUsuario(principal.getNombre());
			   eBitacora.setAccion(URL_ACTUALIZAR_COMPLETA);
			   eBitacora.setTabla(NumeracionGecDao.NOMBRE_TABLA);
			   eBitacora.setIdentificador(String.valueOf(eNumeracionGec.getId()));
			   eBitacora.setContenido(mapper.writeValueAsString(eNumeracionGec));
			   eBitacora.setRealizadoEl(eNumeracionGec.getActualizadoEl());
			   eBitacora.setRealizadoPor(eNumeracionGec.getActualizadoPor());
			   
			   respuesta = dBitacora.guardarRegistro(eBitacora);
			   if (respuesta.estado == false) {
			    throw new Exception(gestorDiccionario.getMessage("sgo.guardarBitacoraFallido", null, locale));
			   }
				
				String dia = eNumeracionGec.getFechaActualizacion().substring(0, 11);
				String hora = eNumeracionGec.getFechaActualizacion().substring(10);
			   respuesta.mensaje = gestorDiccionario.getMessage("sgo.actualizarExitoso", new Object[] { dia, hora,principal.getIdentidad() }, locale);

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


}
