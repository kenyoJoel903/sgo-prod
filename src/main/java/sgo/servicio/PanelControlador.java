package sgo.servicio;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sgo.datos.BitacoraDao;
import sgo.datos.ClienteDao;
import sgo.datos.DiaOperativoDao;
import sgo.datos.EnlaceDao;
import sgo.datos.ParametroDao;
import sgo.datos.UsuarioDao;
import sgo.entidad.MenuGestor;
import sgo.entidad.Parametro;
import sgo.entidad.ParametrosListar;
import sgo.entidad.RespuestaCompuesta;
import sgo.entidad.Usuario;
import sgo.seguridad.AuthenticatedUserDetails;
import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;

@Controller
public class PanelControlador {
 @Autowired
 private MessageSource gestorDiccionario;
 @Autowired
 private BitacoraDao dBitacora;
 @Autowired
 private EnlaceDao dEnlace;
 @Autowired
 private UsuarioDao dUsuario;
 @Autowired
 private MenuGestor menu;
 @Autowired
 private ClienteDao dCliente;
 @Autowired
private ParametroDao dParametro;
 @Autowired
 private DiaOperativoDao dDiaOperativo;
 //
 // urls generales
 private static final String URL_BASE = "/admin";
 private static final String URL_GESTION = "/panel";

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
   mapaValores.put("TITULO_DETALLE_REGISTRO", gestorDiccionario.getMessage("sgo.tituloFormularioVer", null, locale));
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

   mapaValores.put("MENSAJE_CARGANDO", gestorDiccionario.getMessage("sgo.mensajeCargando", null, locale));
   mapaValores.put("TITULO_VENTANA_MODAL", gestorDiccionario.getMessage("sgo.tituloVentanaModal", null, locale));
   
   mapaValores.put("MENSAJE_BIENVENIDA", gestorDiccionario.getMessage("sgo.mensajeBienvenida", null, locale));
  } catch (Exception ex) {

  }
  return mapaValores;
 }

 @RequestMapping(URL_GESTION)
 public ModelAndView mostrarFormulario(Locale locale) {
  ModelAndView vista = null;
  AuthenticatedUserDetails principal = null;
  ArrayList<?> listaEnlaces = null;
  RespuestaCompuesta respuesta = null;
  HashMap<String, String> mapaValores = null;
  ParametrosListar parametros = new ParametrosListar();
  try {
   principal = this.getCurrentUser();

   respuesta = dUsuario.recuperarRegistro(principal.getID());
   
  
   
   
   if (respuesta.estado == false) {	
     throw new Exception(gestorDiccionario.getMessage("sgo.menuNoGenerado", null, locale));
   }
   Usuario user = (Usuario) respuesta.getContenido().getCarga().get(0);
   
   Date fActual = Utilidades.convierteStringADate(dDiaOperativo.recuperarFechaActual().valor, "yyyy-MM-dd");
  	int dias = 0; //7000001938
  	if(fActual!=null && user.getActualizacionClave()!=null){
  			dias= (int) ((fActual.getTime() - user.getActualizacionClave().getTime()) / (1000 * 60 * 60 * 24));
  	}
  	parametros = new ParametrosListar();
  	parametros.setFiltroParametro(Parametro.ALIAS_MAX_CAMBIO_CLAVE);
  	respuesta = dParametro.recuperarRegistros(parametros); 
  	Parametro eParametro = (Parametro) respuesta.contenido.carga.get(0);

     
   
	
   if(user.getClaveTemporal() == 1 && user.getTipo() == 2){
	   vista= new ModelAndView("reseteoPassword");
	   vista.addObject("mensajeError", "Debe modificar su contrase�a.");
	   vista.addObject("username", user.getNombre());
	   vista.addObject("password", user.getClave());
   } else if(dias >= Integer.parseInt(eParametro.getValor()) && user.getTipo() == 2) { // 7000001981 jmatos Modificación....
	   vista= new ModelAndView("reseteoPassword");
	   vista.addObject("mensajeError", "Su contraseña ha expirado. Debe modificarla.");
	   vista.addObject("username", user.getNombre());
	   vista.addObject("password", user.getClave());
	   
   } else {
	   respuesta = menu.Generar(principal.getRol().getId(), URL_BASE+URL_GESTION);
	   if (respuesta.estado == false) {
	    throw new Exception(gestorDiccionario.getMessage("sgo.menuNoGenerado", null, locale));
	   }
	   listaEnlaces = (ArrayList<?>) respuesta.contenido.carga;
	   mapaValores = recuperarMapaValores(locale);
	   vista = new ModelAndView("plantilla");
	   vista.addObject("vistaJSP", "mantenimiento/panel.jsp");
	   vista.addObject("vistaJS", "mantenimiento/panel.js");
	   vista.addObject("identidadUsuario", principal.getIdentidad());
	   vista.addObject("menu", listaEnlaces);
	   vista.addObject("mapaValores", mapaValores);
   }
  } catch (Exception ex) {

  }
  return vista;
 }
 private AuthenticatedUserDetails getCurrentUser() {
  return (AuthenticatedUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 }
}
