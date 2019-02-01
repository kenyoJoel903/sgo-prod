package sgo.seguridad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
/*import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;*/
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



//INI req : 9000002341
//import com.petroperu.accesodatos.FedoraDirectoryConnection;
//import com.petroperu.accesodatos.FedoraDirectoryConnectionFactory;
//import pe.com.petroperu.directorioAD.ad.ActiveDirectoryConnection;
//import pe.com.petroperu.directorioAD.ad.ActiveDirectoryConnectionFactory;
import pe.com.petroperu.directorioAD.ConexionDirectorioAD;
//FIN req : 9000002341

import pe.com.petroperu.directorioAD.util.UsuarioAD;
import sgo.entidad.Contenido;
import sgo.entidad.RespuestaCompuesta;
import sgo.entidad.Usuario;
import sgo.utilidades.Constante;
/*
import com.petroperu.accesodatos.FedoraDirectoryConnection;
import com.petroperu.accesodatos.FedoraDirectoryConnectionFactory;
*/
@Controller
public class GestorDirectorio {
	@Autowired 
	private MessageSource gestorDiccionario;
	//
	private static final String URL_GESTION_COMPLETA="/admin/gestorDirectorio.js";
	private static final String URL_GESTION_RELATIVA="/gestorDirectorio.js";
	private static final String URL_LISTAR_RELATIVA="/gestorDirectorio/listar";
	private static final String URL_LISTAR_COMPLETA="/admin/gestorDirectorio/listar";
	
	@RequestMapping(URL_GESTION_RELATIVA)
	 public ModelAndView mostrarFormulario( Locale locale){

		ModelAndView vista =null;
		HashMap<String,String> mapaValores =  new HashMap<String,String>();
		try {
			mapaValores.put("CERRAR_VISTA", gestorDiccionario.getMessage("sgo.cerrarVista",null,locale));
			
			vista = new ModelAndView("gestorDirectorio");
			vista.addObject("gestorDirectorio", mapaValores);
		} catch(Exception ex){
			
		}
		return vista;
	 }
	
	
	public Boolean validarUsuario(String nombreUsuario, String clave){
			Boolean resultado=false;
	        DirContext contextoDirectorio = null;  
	        Hashtable<String,String> parametros = new Hashtable<String,String>();
	        String usuarioDirectorio = "";
	        try {
	        	usuarioDirectorio = recuperarUsuarioDirectorio(nombreUsuario);
	        	if (usuarioDirectorio==null){
	        		throw new Exception("Usuario no encontrado");
	        	}
		        parametros.put(Context.INITIAL_CONTEXT_FACTORY, Constante.LDAP_CONTEXTO_FACTORIA);  
		        parametros.put(Context.PROVIDER_URL, Constante.LDAP_URL_PROVEEDOR);  
		        //env.put(Context.SECURITY_PROTOCOL, "ssl");  
		        parametros.put(Context.SECURITY_AUTHENTICATION, Constante.LDAP_METODO_AUTENTICACION);  
				parametros.put(Context.SECURITY_PRINCIPAL,usuarioDirectorio);  
				parametros.put(Context.SECURITY_CREDENTIALS,clave);
	            contextoDirectorio = new InitialDirContext(parametros);  
	            resultado=true;
	            contextoDirectorio.close();
	        }// end try  
	        catch (AuthenticationException autenticacionExcepcion){
	        	autenticacionExcepcion.printStackTrace();
	        }
	        catch (Exception generalExcepcion) {  
	        	generalExcepcion.printStackTrace();  
	        }  
	        return resultado;
	}
	
	public String recuperarUsuarioDirectorio(String nombreUsuario) {
		String respuesta=null;
		try {
			DirContext contextoDirectorio;
			Hashtable<String,String> parametros = new Hashtable<String,String>();
			parametros.put(Context.INITIAL_CONTEXT_FACTORY,Constante.LDAP_CONTEXTO_FACTORIA);
			parametros.put(Context.PROVIDER_URL, Constante.LDAP_URL_PROVEEDOR);			
			contextoDirectorio = new InitialDirContext(parametros);
			SearchControls controlesBusqueda = new SearchControls();
			controlesBusqueda.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String filtro = "(&(uid="+nombreUsuario+")( mail="+nombreUsuario+Constante.LDAP_CORREO+"))";
			NamingEnumeration respuestaDirectorio = contextoDirectorio.search(Constante.LDAP_BASE_DN, filtro, controlesBusqueda);
			if(respuestaDirectorio.hasMoreElements()) {
				SearchResult resultadoBusqueda = (SearchResult)respuestaDirectorio.next();
				respuesta= resultadoBusqueda.getNameInNamespace();				
			}		
			contextoDirectorio.close();
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
		return respuesta;
	}
	
	public boolean validarLogueo(String nombreUsuario, String clave){
		boolean respuesta = false;
		try {
//			System.out.println("validaUsuarioLdap========>");
			//INI req : 9000002341
			//FedoraDirectoryConnectionFactory directoryFactory = new FedoraDirectoryConnectionFactory(); 
			//FedoraDirectoryConnection ldapService = directoryFactory.getConnectionToFedoraDirectory();
			ConexionDirectorioAD conexionDirectorioAD= new ConexionDirectorioAD();
			respuesta=conexionDirectorioAD.validarAcceso(nombreUsuario, clave);
			//			System.out.println("user=" + user + ", cred=" +  cred + ", resul=" + ldapService.bindToServer(user, cred) );
			//respuesta = ldapService.bindToServer(nombreUsuario, clave); 
			//FIN req : 9000002341
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return respuesta;
	}
	
	/**
	 * @param httpRequest
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = URL_LISTAR_RELATIVA ,method = RequestMethod.GET)
	public @ResponseBody RespuestaCompuesta recuperarRegistros(HttpServletRequest httpRequest, Locale locale){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		Usuario eUsuario = new Usuario();
		List<Usuario> listaRegistros = new ArrayList<Usuario>();
		Contenido<Usuario> contenido = new Contenido<Usuario>();
		System.out.println("recuperarRegistros =================> INICIO");
		String rs = "";
		try{
			
			if (httpRequest.getParameter("valorBuscado") != null) {
				rs = httpRequest.getParameter("valorBuscado");
				System.out.println("rs : "+  rs.toString());
			}
			//INI req : 9000002341
			ConexionDirectorioAD conexionDirectorioAD = new ConexionDirectorioAD();
			//List<Object[]> lista = ldapService.getListaCoincidencias_flCompuesto(rs);
			HttpSession session = httpRequest.getSession();
			String userLogueado = (String) session.getAttribute("UsuarioLogueado");
			String claveIngresada=(String) session.getAttribute("ClaveIngresada");
			List<UsuarioAD> lista=conexionDirectorioAD.busquedaCompuestaAD(userLogueado,claveIngresada, rs);
			if (rs.isEmpty()){ 
				lista= null;
					}
			//if(lista==null) lista = new ArrayList<Object[]>();
			if (lista==null) lista = new ArrayList<UsuarioAD>();
			//FIN req : 9000002341
			
			System.out.println ("***** tamaño de lista="+lista.size());
		
			for(int i=0; i<lista.size();i++){
				//INI req : 9000002341
				eUsuario = new Usuario();
				UsuarioAD usuarioAD = (UsuarioAD)lista.get(i);
				String usuario = usuarioAD.getUsuario().toString();
				String identidad = usuarioAD.getNombres().toString().toUpperCase()+" "+usuarioAD.getApellidos().toString().toUpperCase();
				String email = usuarioAD.getEmail().toString();
				eUsuario.setNombre(usuario);
				eUsuario.setEmail(email);
				eUsuario.setTipo(1);
				eUsuario.setIdentidad(identidad);
				//FIN req : 9000002341

				listaRegistros.add(eUsuario);
			}
			contenido.carga = listaRegistros;
			respuesta.estado = true;
			respuesta.contenido = contenido;
			respuesta.contenido.totalRegistros = listaRegistros.size();
			respuesta.contenido.totalEncontrados = listaRegistros.size();

		}catch(Exception e){
			e.printStackTrace();
		}
		return respuesta;
	}
}
