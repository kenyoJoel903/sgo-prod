package sgo.datos;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;




import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sgo.entidad.Cisterna;
import sgo.entidad.Contenido;
import sgo.entidad.ParametrosListar;
import sgo.entidad.Respuesta;
import sgo.entidad.RespuestaCompuesta;
import sgo.entidad.Usuario;
import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;


@Repository
public class UsuarioDao {
	protected final Log logger = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	//private final String primaryKey="rol";
	private final String primaryKey="id_usuario";
	private final static String ESQUEMA="seguridad.";
	private final static String TABLA_ROL= ESQUEMA +"rol";
	private final static String TABLA_PERMISOS= ESQUEMA +"permisos_rol";
	private final static String TABLA_PERMISO= ESQUEMA +"permiso";
	private final static String VISTA_PERMISO= ESQUEMA +"v_permiso";
	
	public static final String NOMBRE_TABLA = Constante.ESQUEMA_SEGURIDAD + "usuario";
	public static final String NOMBRE_VISTA = Constante.ESQUEMA_SEGURIDAD + "v_usuario";
	public final static String NOMBRE_CAMPO_CLAVE = "id_usuario";
	public final static String NOMBRE_CAMPO_ORDENAMIENTO_DEFECTO = "nombre";
	
	public final static String NOMBRE_CAMPO_FILTRO = "nombre";
	public final static String NOMBRE_CAMPO_FILTRO_ESTADO = "estado";
	public final static String NOMBRE_CAMPO_FILTRO_FECHA = "actualizado_por";
	
	public final static String O = "OR";
	public final static String Y = "AND";
	public final static String ENTRE = "BETWEEN";
	 /** Nombre de la clase. */
	 private static final String sNombreClase = "UsuarioDao";
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public DataSource getDataSource(){
		return this.jdbcTemplate.getDataSource();
	}
	
	public String mapearCampoOrdenamiento(String propiedad){
		String campoOrdenamiento = NOMBRE_CAMPO_ORDENAMIENTO_DEFECTO;
		try {
			if (propiedad.equals("nombre")){
				campoOrdenamiento="nombre";
			}
			if (propiedad.equals("identidad")){
				campoOrdenamiento="identidad";
			}
			if (propiedad.equals("rol.nombre")){
				campoOrdenamiento="nombre_rol";
			}
			if (propiedad.equals("operacion.nombre")){
				campoOrdenamiento="nombre_operacion";
			}
			if (propiedad.equals("estado")){
				campoOrdenamiento="estado";
			}
		}catch(Exception excepcion){
			
		}
		return campoOrdenamiento;
	}
	
	public RespuestaCompuesta recuperarRegistros(ParametrosListar argumentosListar) {
		StringBuilder consultaSQL = new StringBuilder();
		String sqlLimit = "";
		String sqlOrderBy="";
		List<String> filtrosWhere= new ArrayList<String>();
		String sqlWhere=""; 
		int totalRegistros = 0, 
		totalEncontrados = 0;
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		Contenido<Usuario> contenido = new Contenido<Usuario>();
		List<Usuario> listaRegistros = new ArrayList<Usuario>();
		List<Object> parametros = new ArrayList<Object>();
		try {
			if (argumentosListar.getPaginacion() == Constante.CON_PAGINACION) {
				sqlLimit = Constante.SQL_LIMIT_CONFIGURADO;
				parametros.add(argumentosListar.getInicioPaginacion());
				parametros.add(argumentosListar.getRegistrosxPagina());
			}
			
			sqlOrderBy= " ORDER BY " + this.mapearCampoOrdenamiento(argumentosListar.getCampoOrdenamiento()) + " " + argumentosListar.getSentidoOrdenamiento();
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT count(" + NOMBRE_CAMPO_CLAVE+ ") as total FROM " + NOMBRE_TABLA);
			totalRegistros = jdbcTemplate.queryForObject(consultaSQL.toString(), null, Integer.class);
			totalEncontrados=totalRegistros;

			if (!argumentosListar.getValorBuscado().isEmpty()){
				filtrosWhere.add("lower(t1."+NOMBRE_CAMPO_FILTRO+") like lower('%"+ argumentosListar.getValorBuscado() +"%') ");
			}
			if (!argumentosListar.getTxtFiltro().isEmpty()){
				filtrosWhere.add("lower(t1."+NOMBRE_CAMPO_FILTRO+") like lower('%"+ argumentosListar.getTxtFiltro() +"%') ");
			}
			if(argumentosListar.getFiltroEstado() != Constante.FILTRO_TODOS){
				filtrosWhere.add(" t1."+NOMBRE_CAMPO_FILTRO_ESTADO + "=" + argumentosListar.getFiltroEstado() + " ");
			}
			if(!argumentosListar.getFiltroUsuario().isEmpty()){
				filtrosWhere.add(" t1.nombre ='" + argumentosListar.getFiltroUsuario() + "' ");
			}
			if(argumentosListar.getFiltroOperacion() > 0){
				filtrosWhere.add(" t1.id_operacion =" + argumentosListar.getFiltroOperacion() + " ");
			}
			if(argumentosListar.getFiltroRol() > 0){
				filtrosWhere.add(" t1.rol =" + argumentosListar.getFiltroRol() + " ");
			}
			if(!argumentosListar.getTxtQuery().isEmpty()){
				filtrosWhere.add(" t1.id_transportista in(" + argumentosListar.getTxtQuery() + ") ");
			}
			if(argumentosListar.getIdTransportista() != Constante.FILTRO_TODOS){
				filtrosWhere.add(" t1.id_transportista = " + argumentosListar.getIdTransportista() + " ");
			}
			if(!argumentosListar.getCorreoUsuario().isEmpty()){
				filtrosWhere.add(" t1.email = '" + argumentosListar.getCorreoUsuario() + "' ");
			}
			if(argumentosListar.getFiltroIdUsuario() != Constante.FILTRO_TODOS){
				filtrosWhere.add(" t1.id_usuario <> " + argumentosListar.getFiltroIdUsuario() + " ");
			}
			//inicio

			if(argumentosListar.getIdCliente() != Constante.FILTRO_TODOS){
				filtrosWhere.add(" t1.id_cliente = " + argumentosListar.getIdCliente() + " ");
			}
			//fin

			if(!filtrosWhere.isEmpty()){
				consultaSQL.setLength(0);
				sqlWhere = "WHERE " + StringUtils.join(filtrosWhere, Y);
				
				consultaSQL.append("SELECT count(t1." + NOMBRE_CAMPO_CLAVE+ ") as total FROM " + NOMBRE_VISTA + " t1 " + sqlWhere);
				totalEncontrados = jdbcTemplate.queryForObject(consultaSQL.toString(), null, Integer.class);
			}
			
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_usuario,");
			consultaSQL.append("t1.nombre,");
			consultaSQL.append("t1.clave,");
			consultaSQL.append("t1.identidad,");
			consultaSQL.append("t1.zona_horaria,");
			consultaSQL.append("t1.estado,");
			consultaSQL.append("t1.email,");
			//consultaSQL.append("t1.cambio_clave,");
			consultaSQL.append("t1.rol as id_rol,");
			consultaSQL.append("t1.tipo,");
			consultaSQL.append("t1.id_operacion as id_operacion,");
			consultaSQL.append("t1.id_cliente as id_cliente,");
			consultaSQL.append("t1.id_transportista, ");
			consultaSQL.append("t1.razon_social, ");
			consultaSQL.append("t1.nombre_corto, ");
			consultaSQL.append("t1.ruc, ");
			consultaSQL.append("t1.actualizacion_clave, ");
			consultaSQL.append("t1.intentos, ");
			consultaSQL.append("t1.clave_temporal, ");
			//Descripción del rol y operación
			consultaSQL.append("t1.nombre_rol, ");
			consultaSQL.append("t1.nombre_operacion, ");
			consultaSQL.append("t1.nombre_cliente, ");
			consultaSQL.append("t1.referencia_planta_recepcion, ");
			consultaSQL.append("t1.referencia_destinatario_mercaderia, ");
			consultaSQL.append("t1.volumen_promedio_cisterna, ");
			//Campos de auditoria
			consultaSQL.append("t1.creado_el,");
			consultaSQL.append("t1.creado_por,");
			consultaSQL.append("t1.actualizado_por,");
			consultaSQL.append("t1.actualizado_el,");
			consultaSQL.append("t1.usuario_creacion,");
			consultaSQL.append("t1.usuario_actualizacion,");
			consultaSQL.append("t1.ip_creacion,");
			consultaSQL.append("t1.ip_actualizacion");
			consultaSQL.append(" FROM ");
			consultaSQL.append(NOMBRE_VISTA);
			consultaSQL.append(" t1 ");
			consultaSQL.append(sqlWhere);
			consultaSQL.append(sqlOrderBy);
			consultaSQL.append(sqlLimit);
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),parametros.toArray(), new UsuarioMapper());
			//totalEncontrados = totalRegistros;

			contenido.carga = listaRegistros;
			respuesta.estado = true;
			respuesta.contenido = contenido;
			respuesta.contenido.totalRegistros = totalRegistros;
			respuesta.contenido.totalEncontrados = totalEncontrados;
			Utilidades.gestionaTrace(sNombreClase, "recuperarRegistros");
		} catch (DataAccessException excepcionAccesoDatos) {
			Utilidades.gestionaWarning(excepcionAccesoDatos, sNombreClase, "recuperarRegistros", consultaSQL.toString());
			//excepcionAccesoDatos.printStackTrace();
			respuesta.error=  Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
			respuesta.contenido=null;
		} catch (Exception excepcionGenerica) {
			//excepcionGenerica.printStackTrace();
			Utilidades.gestionaWarning(excepcionGenerica, sNombreClase, "recuperarRegistros", consultaSQL.toString());
			respuesta.error= Constante.EXCEPCION_GENERICA;
			respuesta.contenido=null;
			respuesta.estado = false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta recuperarRegistro(int ID){
		StringBuilder consultaSQL= new StringBuilder();		
		List<Usuario> listaRegistros=new ArrayList<Usuario>();
		Contenido<Usuario> contenido = new Contenido<Usuario>();
		RespuestaCompuesta respuesta= new RespuestaCompuesta();
		try {
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_usuario,");
			consultaSQL.append("t1.nombre,");
			consultaSQL.append("t1.clave,");
			consultaSQL.append("t1.identidad,");
			consultaSQL.append("t1.zona_horaria,");
			consultaSQL.append("t1.estado,");
			consultaSQL.append("t1.email,");
			//consultaSQL.append("t1.cambio_clave,");
			consultaSQL.append("t1.rol as id_rol,");
			consultaSQL.append("t1.tipo,");
			consultaSQL.append("t1.id_operacion,");
			consultaSQL.append("t1.id_cliente,");
			consultaSQL.append("t1.id_transportista, ");
			consultaSQL.append("t1.razon_social, ");
			consultaSQL.append("t1.nombre_corto, ");
			consultaSQL.append("t1.ruc, ");
			consultaSQL.append("t1.actualizacion_clave, ");
			consultaSQL.append("t1.intentos, ");
			consultaSQL.append("t1.clave_temporal, ");
			//Descripción del rol y operación
			consultaSQL.append("t1.nombre_rol, ");
			consultaSQL.append("t1.nombre_operacion, ");
			consultaSQL.append("t1.nombre_cliente, ");
			consultaSQL.append("t1.referencia_planta_recepcion, ");
			consultaSQL.append("t1.referencia_destinatario_mercaderia, ");
			consultaSQL.append("t1.volumen_promedio_cisterna, ");
			//Campos de auditoria
			consultaSQL.append("t1.creado_el,");
			consultaSQL.append("t1.creado_por,");
			consultaSQL.append("t1.actualizado_por,");
			consultaSQL.append("t1.actualizado_el,");	
			consultaSQL.append("t1.usuario_creacion,");
			consultaSQL.append("t1.usuario_actualizacion,");
			consultaSQL.append("t1.ip_creacion,");
			consultaSQL.append("t1.ip_actualizacion");
			consultaSQL.append(" FROM ");				
			consultaSQL.append(NOMBRE_VISTA);
			consultaSQL.append(" t1 ");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=?");
			listaRegistros= jdbcTemplate.query(consultaSQL.toString(),new Object[] {ID},new UsuarioMapper());
			contenido.totalRegistros=listaRegistros.size();
			contenido.totalEncontrados=listaRegistros.size();
			contenido.carga= listaRegistros;
			respuesta.mensaje="OK";
			respuesta.estado=true;
			respuesta.contenido = contenido;	
			Utilidades.gestionaTrace(sNombreClase, "recuperarRegistro");
		} catch (DataAccessException excepcionAccesoDatos) {
			Utilidades.gestionaWarning(excepcionAccesoDatos, sNombreClase, "recuperarRegistro", consultaSQL.toString());
			//excepcionAccesoDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
			respuesta.contenido=null;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta guardarRegistro(Usuario usuario){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		KeyHolder claveGenerada = null;
		int cantidadFilasAfectadas=0;
		try {
			consultaSQL.append("INSERT INTO ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" (nombre,clave,identidad,zona_horaria,estado,email,rol,id_cliente,creado_el,creado_por,actualizado_por,actualizado_el,ip_creacion,ip_actualizacion,id_operacion,tipo,id_transportista,actualizacion_clave,intentos,clave_temporal) ");
			consultaSQL.append(" VALUES (:Nombre,:Clave,:Identidad,:ZonaHoraria,:Estado,:Email,:Rol,:Cliente,:CreadoEl,:CreadoPor,:ActualizadoPor,:ActualizadoEl,:IpCreacion,:IpActualizacion,:Operacion,:Tipo,:Transportista,:ActualizacionClave,:Intentos,:ClaveTemporal) ");
			
			//consultaSQL.append(" (nombre,clave,identidad,zona_horaria,estado,email,rol,id_cliente,creado_el,creado_por,actualizado_por,actualizado_el,ip_creacion,ip_actualizacion,id_operacion,tipo,id_transportista,clave_temporal) ");
			//consultaSQL.append(" VALUES (:Nombre,:Clave,:Identidad,:ZonaHoraria,:Estado,:Email,:Rol,:Cliente,:CreadoEl,:CreadoPor,:ActualizadoPor,:ActualizadoEl,:IpCreacion,:IpActualizacion,:Operacion,:Tipo,:Transportista,:ClaveTemporal) ");
			
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();   
			listaParametros.addValue("Nombre", usuario.getNombre());
			listaParametros.addValue("Clave", usuario.getClave());
			listaParametros.addValue("Identidad", usuario.getIdentidad());
			listaParametros.addValue("ZonaHoraria", usuario.getZonaHoraria());
			listaParametros.addValue("Estado", usuario.getEstado());
			listaParametros.addValue("Email", usuario.getEmail());
			//listaParametros.addValue("CambioClave", usuario.getCambioClave());
			listaParametros.addValue("Rol", usuario.getId_rol());
			listaParametros.addValue("Cliente", usuario.getIdCliente());
			listaParametros.addValue("Operacion", usuario.getId_operacion());
			listaParametros.addValue("Transportista", usuario.getIdTransportista());
			listaParametros.addValue("ClaveTemporal", usuario.getClaveTemporal());
			
//			listaParametros.addValue("IdRol", usuario.getIdRol());
			listaParametros.addValue("CreadoEl", usuario.getCreadoEl());
			listaParametros.addValue("CreadoPor", usuario.getCreadoPor());
			listaParametros.addValue("ActualizadoPor", usuario.getActualizadoPor());
			listaParametros.addValue("ActualizadoEl", usuario.getActualizadoEl());
			listaParametros.addValue("IpCreacion", usuario.getIpCreacion());
			listaParametros.addValue("IpActualizacion", usuario.getIpActualizacion());
			listaParametros.addValue("Tipo", usuario.getTipo());
			listaParametros.addValue("ActualizacionClave", usuario.getActualizacionClave());
			listaParametros.addValue("Intentos", usuario.getIntentos());
			
			SqlParameterSource namedParameters= listaParametros;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			claveGenerada = new GeneratedKeyHolder();
			cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters,claveGenerada,new String[] {NOMBRE_CAMPO_CLAVE});		
			if (cantidadFilasAfectadas>1){
				respuesta.error=Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
			respuesta.valor= claveGenerada.getKey().toString();
			Utilidades.gestionaTrace(sNombreClase, "guardarRegistro");
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){
			Utilidades.gestionaWarning(excepcionIntegridadDatos, sNombreClase, "guardarRegistro", consultaSQL.toString());
			//excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			Utilidades.gestionaWarning(excepcionAccesoDatos, sNombreClase, "guardarRegistro", consultaSQL.toString());
			//excepcionAccesoDatos.printStackTrace();
			respuesta.error=Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta actualizarRegistro(Usuario usuario){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		int cantidadFilasAfectadas=0;
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			//consultaSQL.append("nombre=:Nombre,");
			//consultaSQL.append("clave=:Clave,");
			consultaSQL.append("identidad=:Identidad,");
			consultaSQL.append("zona_horaria=:ZonaHoraria,");
			//consultaSQL.append("estado=:Estado,");
			consultaSQL.append("email=:Email,");
			//consultaSQL.append("cambio_clave=:CambioClave,");
			consultaSQL.append("rol=:Rol,");
			//consultaSQL.append("tipo=:Tipo,");
			consultaSQL.append("id_transportista=:Transportista,");
			consultaSQL.append("id_cliente=:Cliente,");
			consultaSQL.append("id_operacion=:Operacion,");
			consultaSQL.append("actualizado_por=:ActualizadoPor,");
			consultaSQL.append("actualizado_el=:ActualizadoEl,");
			consultaSQL.append("ip_actualizacion=:IpActualizacion");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:id");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			//listaParametros.addValue("Nombre", usuario.getNombre());
			//listaParametros.addValue("Clave", usuario.getClave());
			listaParametros.addValue("Identidad", usuario.getIdentidad());
			listaParametros.addValue("ZonaHoraria", usuario.getZonaHoraria());
			//listaParametros.addValue("Estado", usuario.getEstado());
			listaParametros.addValue("Email", usuario.getEmail());
			//listaParametros.addValue("CambioClave", usuario.getCambioClave());
			listaParametros.addValue("Rol", usuario.getId_rol());
			//listaParametros.addValue("Tipo", usuario.getTipo());
			listaParametros.addValue("Transportista", usuario.getIdTransportista());
			listaParametros.addValue("Cliente", usuario.getIdCliente());
			listaParametros.addValue("Operacion", usuario.getId_operacion());
			//Valores Auditoria
			listaParametros.addValue("ActualizadoEl", usuario.getActualizadoEl());
			listaParametros.addValue("ActualizadoPor", usuario.getActualizadoPor());
			listaParametros.addValue("IpActualizacion", usuario.getIpActualizacion());
			listaParametros.addValue("id", usuario.getId());
			SqlParameterSource namedParameters= listaParametros;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters);		
			if (cantidadFilasAfectadas>1){
				respuesta.error= Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
			Utilidades.gestionaTrace(sNombreClase, "actualizarRegistro");
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){
			Utilidades.gestionaWarning(excepcionIntegridadDatos, sNombreClase, "actualizarRegistro", consultaSQL.toString());
			excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			Utilidades.gestionaWarning(excepcionAccesoDatos, sNombreClase, "actualizarRegistro", consultaSQL.toString());
			excepcionAccesoDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta actualizarIntentos(Usuario usuario){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		int cantidadFilasAfectadas=0;
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("intentos=:Intentos");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:id");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("Intentos", usuario.getIntentos());
			listaParametros.addValue("id", usuario.getId());
			SqlParameterSource namedParameters= listaParametros;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters);		
			if (cantidadFilasAfectadas>1){
				respuesta.error= Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
			Utilidades.gestionaTrace(sNombreClase, "actualizarIntentos");
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){
			Utilidades.gestionaWarning(excepcionIntegridadDatos, sNombreClase, "actualizarIntentos", consultaSQL.toString());
			//excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			Utilidades.gestionaWarning(excepcionAccesoDatos, sNombreClase, "actualizarIntentos", consultaSQL.toString());
			//excepcionAccesoDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta eliminarRegistro(int idRegistro){		
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		int cantidadFilasAfectadas=0;	
		String consultaSQL="";
		Object[] parametros = {idRegistro};
		try {
			consultaSQL="DELETE FROM " + NOMBRE_TABLA + " WHERE " + NOMBRE_CAMPO_CLAVE + "=?";
        	cantidadFilasAfectadas = jdbcTemplate.update(consultaSQL, parametros);
			if (cantidadFilasAfectadas > 1){
				respuesta.error= Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
			Utilidades.gestionaTrace(sNombreClase, "eliminarRegistro");
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){	
			Utilidades.gestionaWarning(excepcionIntegridadDatos, sNombreClase, "eliminarRegistro", consultaSQL.toString());
			excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			Utilidades.gestionaWarning(excepcionAccesoDatos, sNombreClase, "eliminarRegistro", consultaSQL.toString());
			excepcionAccesoDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta ActualizarEstadoRegistro(Usuario usuario){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		int cantidadFilasAfectadas=0;
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("estado=:Estado,");
			/*if(usuario.getEstado() == Constante.ESTADO_ACTIVO){
				consultaSQL.append("clave=:Clave,");
				consultaSQL.append("actualizacion_clave=:ActualizacionClave,");
				consultaSQL.append("intentos=:Intentos,");
				
			}*/
			consultaSQL.append("actualizado_por=:ActualizadoPor,");
			consultaSQL.append("actualizado_el=:ActualizadoEl,");
			consultaSQL.append("ip_actualizacion=:IpActualizacion");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:Id");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("Estado", usuario.getEstado());
			/*if(usuario.getEstado() == Constante.ESTADO_ACTIVO){
				listaParametros.addValue("Clave", usuario.getClave());
				listaParametros.addValue("ActualizacionClave", usuario.getActualizacionClave());
				listaParametros.addValue("Intentos", usuario.getIntentos());
			}*/
			//Valores Auditoria
			listaParametros.addValue("ActualizadoEl", usuario.getActualizadoEl());
			listaParametros.addValue("ActualizadoPor", usuario.getActualizadoPor());
			listaParametros.addValue("IpActualizacion", usuario.getIpActualizacion());
			listaParametros.addValue("Id", usuario.getId());
			SqlParameterSource namedParameters= listaParametros;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters);		
			if (cantidadFilasAfectadas>1){
				respuesta.error= Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
			Utilidades.gestionaTrace(sNombreClase, "ActualizarEstadoRegistro");
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){
			Utilidades.gestionaWarning(excepcionIntegridadDatos, sNombreClase, "ActualizarEstadoRegistro", consultaSQL.toString());
			//excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			Utilidades.gestionaWarning(excepcionAccesoDatos, sNombreClase, "ActualizarEstadoRegistro", consultaSQL.toString());
			//excepcionAccesoDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}

	public RespuestaCompuesta ActualizarPassword(Usuario usuario){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		int cantidadFilasAfectadas=0;
		
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("clave=:Clave,");
			consultaSQL.append("clave_temporal=:ClaveTemporal,");
			consultaSQL.append("zona_horaria=:ZonaHoraria,");
			consultaSQL.append("actualizacion_clave=:ActualizacionClave,");
			consultaSQL.append("intentos=:Intentos,");
			consultaSQL.append("actualizado_por=:ActualizadoPor,");
			consultaSQL.append("actualizado_el=:ActualizadoEl,");
			consultaSQL.append("ip_actualizacion=:IpActualizacion");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:Id");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("Clave", usuario.getClave());
			listaParametros.addValue("ZonaHoraria", usuario.getZonaHoraria());
			listaParametros.addValue("ActualizacionClave", usuario.getActualizacionClave());
			listaParametros.addValue("Intentos", usuario.getIntentos());
			listaParametros.addValue("ClaveTemporal", usuario.getClaveTemporal());
			//Valores Auditoria
			listaParametros.addValue("ActualizadoEl", usuario.getActualizadoEl());
			listaParametros.addValue("ActualizadoPor", usuario.getActualizadoPor());
			listaParametros.addValue("IpActualizacion", usuario.getIpActualizacion());
			listaParametros.addValue("Id", usuario.getId());
			SqlParameterSource namedParameters= listaParametros;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters);		
			if (cantidadFilasAfectadas>1){
				respuesta.error= Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
			Utilidades.gestionaTrace(sNombreClase, "ActualizarPassword");
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){
			Utilidades.gestionaWarning(excepcionIntegridadDatos, sNombreClase, "ActualizarPassword", consultaSQL.toString());
			//excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			Utilidades.gestionaWarning(excepcionAccesoDatos, sNombreClase, "ActualizarPassword", consultaSQL.toString());
			//excepcionAccesoDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta resetPassword(Usuario usuario){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		int cantidadFilasAfectadas=0;
		
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("clave=:Clave,");
			consultaSQL.append("clave_temporal=1,");
			consultaSQL.append("actualizacion_clave=:ActualizacionClave,");
			consultaSQL.append("intentos=:Intentos,");
			consultaSQL.append("actualizado_por=:ActualizadoPor,");
			consultaSQL.append("actualizado_el=:ActualizadoEl,");
			consultaSQL.append("ip_actualizacion=:IpActualizacion");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:Id");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("Clave", usuario.getClave());
			listaParametros.addValue("ActualizacionClave", usuario.getActualizacionClave());
			listaParametros.addValue("Intentos", usuario.getIntentos());
			//Valores Auditoria
			listaParametros.addValue("ActualizadoEl", usuario.getActualizadoEl());
			listaParametros.addValue("ActualizadoPor", usuario.getActualizadoPor());
			listaParametros.addValue("IpActualizacion", usuario.getIpActualizacion());
			listaParametros.addValue("Id", usuario.getId());
			SqlParameterSource namedParameters= listaParametros;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters);		
			if (cantidadFilasAfectadas>1){
				respuesta.error= Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
			Utilidades.gestionaTrace(sNombreClase, "resetPassword");
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){
			Utilidades.gestionaWarning(excepcionIntegridadDatos, sNombreClase, "resetPassword", consultaSQL.toString());
			//excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			Utilidades.gestionaWarning(excepcionAccesoDatos, sNombreClase, "resetPassword", consultaSQL.toString());
			//excepcionAccesoDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	
	/* INICIO SECCION CRUD */
	public Respuesta createRecord(Usuario usuario){		
		SqlParameterSource namedParameters;
		KeyHolder keyHolder;
		StringBuilder sbSQL= new StringBuilder();
		Respuesta Respuesta = new Respuesta();
		int rowsAffected=0;
		try {			
			sbSQL.append("INSERT INTO ");
			sbSQL.append(NOMBRE_TABLA);
			sbSQL.append(" (nombre,clave,zona_horaria,habilitado,rol,nombre_real,apellido_real,direccion,email,cmp,imagen) ");
			sbSQL.append(" VALUES (:nombre,sha1(:clave),:zonaHoraria,:habilitado,:rol,:nombreReal,:apellidoReal,:direccion,:email,:cmp,:imagen)");
			/*Se crea el map que contendra los parametros*/
			/*se utiliza parametros nombrados porque es mas facil quitar/agregar sin tener en cuenta el orden*/
			keyHolder = new GeneratedKeyHolder();
			MapSqlParameterSource mapParameters= new MapSqlParameterSource();
			mapParameters.addValue("nombre", usuario.getNombre());
			mapParameters.addValue("clave", usuario.getClave());
			mapParameters.addValue("zonaHoraria", usuario.getZonaHoraria());
			mapParameters.addValue("habilitado", usuario.getEstado());
			mapParameters.addValue("rol", usuario.getRol().getId());
			//mapParameters.addValue("nombreReal", usuario.getNombreReal());
			//mapParameters.addValue("apellidoReal", usuario.getApellidoReal());
			//mapParameters.addValue("direccion", usuario.getDireccion());
			mapParameters.addValue("email", usuario.getEmail());
			//mapParameters.addValue("cambioClave", usuario.getEstado());
			//mapParameters.addValue("imagen", usuario.getImagen());
			namedParameters= mapParameters;
			rowsAffected= namedJdbcTemplate.update(sbSQL.toString(),namedParameters,keyHolder);	
			Respuesta.valor= String.valueOf(keyHolder.getKey().intValue());
			if (rowsAffected==1){				
				Respuesta.mensaje="OK";
				Respuesta.estado=true;
				Respuesta.valor = null;				
			} else {
				Respuesta.mensaje="Mas filas afectadas";
				Respuesta.estado=false;
				Respuesta.valor = null;
			}			
			Utilidades.gestionaTrace(sNombreClase, "createRecord");
		} catch (DataIntegrityViolationException daEx){
			Utilidades.gestionaWarning(daEx, sNombreClase, "createRecord", sbSQL.toString());
			//logger.info(daEx.getMessage());
			Respuesta.mensaje="";//"No se puede crear el usuario: ya existe un usuario con el mismo nombre.";
			Respuesta.estado=false;
			Respuesta.valor = null;
		} catch (DataAccessException daEx){
			Utilidades.gestionaWarning(daEx, sNombreClase, "createRecord", sbSQL.toString());
			//logger.info(daEx.getMessage());
			Respuesta.mensaje=daEx.getMessage();
			Respuesta.estado=false;
			Respuesta.valor = null;
		}
		return Respuesta;
	}
	
	public RespuestaCompuesta getRecords(String search, int sortCol, String sortDir, int rowStart, int length) {
		String sqlSearch="",sqlSort="",sqlRecordsFound="",sqlTotalRecords = "";
		int TotalRecords=0,recordsFound=0;
		List<Object> list = new ArrayList<Object>();
		List<Usuario> lUsuarios= new ArrayList<Usuario>();
		RespuestaCompuesta Respuesta = new RespuestaCompuesta();
		Contenido<Usuario> mContenido = new Contenido<Usuario>();

		if (!search.equals("")) {			
			sqlSearch = "WHERE u.nombre LIKE ? OR u.identidad LIKE ?";
			search = "%" + search + "%";
			list.add(search);
			list.add(search);
		}
		
		switch(sortCol)	{
			case 1:	sqlSort = "ORDER BY id_usuario " + sortDir;
					break;		
			case 2:	sqlSort = "ORDER BY nombre " + sortDir;
					break;								
			case 3:	sqlSort = "ORDER BY identidad " + sortDir;
					break;	
			case 5:	sqlSort = "ORDER BY habilitado " + sortDir;
					break;						
		}
		
		list.add(rowStart);
		list.add(length);
		
		try {
			sqlTotalRecords = "SELECT COUNT(*) FROM "  + NOMBRE_TABLA;
			TotalRecords = jdbcTemplate.queryForInt(sqlTotalRecords);
			recordsFound= TotalRecords;
			if (!sqlSearch.equals("")){
				sqlRecordsFound = "SELECT COUNT(*) FROM "+ NOMBRE_TABLA +" u " + sqlSearch + " " + sqlSort + " LIMIT ?,?";
				recordsFound=jdbcTemplate.queryForInt(sqlRecordsFound,list.toArray());
			}
			
			StringBuilder sqlSB= new StringBuilder();
			sqlSB.append("SELECT u.id_usuario,u.nombre as nombre, u.clave as clave,");
			sqlSB.append("u.nombre_real,u.apellido_real,u.direccion, u.zona_horaria as zona_horaria,u.cmp,u.imagen,");
			sqlSB.append("u.habilitado as habilitado,u.rol as rol_id,u.email as email,r.nombre as rol_nombre");
			sqlSB.append(" FROM usuario u INNER JOIN rol r ON u.rol = r.rol_id ");		
			sqlSB.append(sqlSearch + " " + sqlSort + " LIMIT ?,?");
			lUsuarios= jdbcTemplate.query(sqlSB.toString(),list.toArray(),new UsuarioMapper());
			mContenido.carga= lUsuarios;
			
			Respuesta.estado=true;
			Respuesta.mensaje="OK";
			Respuesta.contenido= mContenido;
			Respuesta.contenido.totalEncontrados=recordsFound;
			Respuesta.contenido.totalRegistros= TotalRecords;
			Utilidades.gestionaTrace(sNombreClase, "getRecords");
		} catch (DataAccessException daEx){
			Utilidades.gestionaWarning(daEx, sNombreClase, "getRecords", sqlTotalRecords.toString());
			//logger.info(daEx.getMessage());
			Respuesta.mensaje=daEx.getMessage();
			Respuesta.estado=false;
			Respuesta.contenido = null;	
		} catch (Exception ex){
			Utilidades.gestionaWarning(ex, sNombreClase, "getRecords", sqlTotalRecords.toString());
			//logger.info(ex.getMessage());
			Respuesta.mensaje=ex.getMessage();
			Respuesta.estado=false;
			Respuesta.contenido = null;
		}
		return Respuesta;
	}
	
	/**
	 * @param ID
	 * @return Una lista conteniendo el usuario para su edicion
	 */
	public RespuestaCompuesta getRecord(int ID){		
		List<Usuario> lUsuarios=new ArrayList<Usuario>();
		Contenido<Usuario> mContenido = new Contenido<Usuario>();
		RespuestaCompuesta Respuesta= new RespuestaCompuesta();
		StringBuilder sqlSB= new StringBuilder();
		try {
			sqlSB.append("SELECT u.id_usuario,u.nombre, u.clave,");
			sqlSB.append("u.identidad, u.zona_horaria,u.habilitado,u.rol as id_rol,u.email,r.nombre as rol_nombre,");
			sqlSB.append(" u.creado_por,u.creado_el,u.actualizado_por,u.actualizado_el,u.ip_actualizacion,u.ip_creacion,");
			sqlSB.append(" FROM usuario u INNER JOIN sgo.rol r ON u.rol = r.id_rol ");		
			sqlSB.append("WHERE id_usuario=?");
			lUsuarios= jdbcTemplate.query(sqlSB.toString(),new Object[] {ID},new UsuarioMapper());
			mContenido.carga=lUsuarios;
			mContenido.totalRegistros=1;
			mContenido.totalEncontrados=1;
			Respuesta.mensaje="OK";
			Respuesta.estado=true;
			Respuesta.contenido = mContenido;
			
		} catch (DataAccessException daEx) {
			Utilidades.gestionaWarning(daEx, sNombreClase, "getRecord", sqlSB.toString());
			//logger.info(daEx.getMessage());
			Respuesta.mensaje="Error";
			Respuesta.estado=false;
			Respuesta.contenido.carga = null;	
		}
		return Respuesta;
	}
	
    public RespuestaCompuesta editRecord(Usuario usuario){
    	RespuestaCompuesta Respuesta = new RespuestaCompuesta();
		SqlParameterSource namedParameters;
		int rowsAffected=0;
		StringBuilder sbSQL= new StringBuilder();
        try {
			sbSQL.append("UPDATE ");
			sbSQL.append(NOMBRE_TABLA);
			sbSQL.append(" SET ");
			sbSQL.append(" nombre=:nombre,");
			if (! usuario.getClave().equals("")) {
				sbSQL.append(" clave=sha1(:clave),");
			}
			sbSQL.append(" zona_horaria=:zonaHoraria,");
			sbSQL.append(" habilitado=:habilitado,");
			sbSQL.append(" rol=:rol,");
			sbSQL.append(" cmp=:cmp,");
			sbSQL.append(" nombre_real=:nombreReal,");
			sbSQL.append(" direccion=:direccion,");
			sbSQL.append(" apellido_real=:apellidoReal,");
			sbSQL.append(" email=:email");
			sbSQL.append(" WHERE ");
			sbSQL.append(this.primaryKey);
			sbSQL.append(" =:pk ");			
			/*Se crea el map que contendra los parametros*/
			/*se utiliza parametros nombrados porque es mas facil quitar/agregar sin tener en cuenta el orden*/
			MapSqlParameterSource mapParameters= new MapSqlParameterSource();
			mapParameters.addValue("nombre", usuario.getNombre());
			if (! usuario.getClave().equals("")) {
				mapParameters.addValue("clave", usuario.getClave());
			}
			mapParameters.addValue("zonaHoraria", usuario.getZonaHoraria());
			mapParameters.addValue("habilitado", usuario.getEstado());
			mapParameters.addValue("rol", usuario.getRol().getId());
			//mapParameters.addValue("nombreReal", usuario.getNombreReal());
			//mapParameters.addValue("apellidoReal", usuario.getApellidoReal());
			//mapParameters.addValue("direccion", usuario.getDireccion());
			mapParameters.addValue("email", usuario.getEmail());
			//mapParameters.addValue("cmp", usuario.getCmp());
			mapParameters.addValue("pk", usuario.getId());	
			namedParameters= mapParameters;
			rowsAffected= namedJdbcTemplate.update(sbSQL.toString(),namedParameters);
			if (rowsAffected==1){
				Respuesta.mensaje="OK";
				Respuesta.estado=true;
				Respuesta.contenido = null;
			} else {
				Respuesta.mensaje="Mas filas afectadas";
				Respuesta.estado=false;
				Respuesta.contenido = null;
			}
        } catch (DataIntegrityViolationException daEx){
        	Utilidades.gestionaWarning(daEx, sNombreClase, "editRecord", sbSQL.toString());
			Respuesta.mensaje="No se puede editar el usuario: ya existe un usuario con el mismo nombre.";
			Respuesta.estado=false;
			Respuesta.contenido = null;
		} catch (DataAccessException daEx){
			Utilidades.gestionaWarning(daEx, sNombreClase, "editRecord", sbSQL.toString());
                //logger.info(daEx.getMessage());
			Respuesta.mensaje=daEx.getMessage();
            Respuesta.estado=false;
            Respuesta.contenido = null;
        }
        return Respuesta;
    }
    
	public Respuesta deleteRecord(int ID){
		String sql="DELETE FROM " + NOMBRE_TABLA +" WHERE "+this.primaryKey+"=?";
		Respuesta Respuesta = new Respuesta();
		int rowsAffected=0;	
		Object[] params = {ID};
		try {
			rowsAffected = jdbcTemplate.update(sql, params);
			if (rowsAffected==1){
				Respuesta.mensaje="OK";
				Respuesta.estado=true;
				Respuesta.valor = null;
			} else {
				Respuesta.mensaje="Mas filas afectadas";
				Respuesta.estado=false;
				Respuesta.valor = null;
			}
		} catch (DataAccessException daEx){
			Utilidades.gestionaWarning(daEx, sNombreClase, "deleteRecord", sql.toString());
			//logger.info(daEx.getMessage());
			Respuesta.mensaje=daEx.getMessage();
			Respuesta.estado=false;
			Respuesta.valor = null;
		}
		return Respuesta;
	}
	
	/* FIN DE SECCION CRUD */

    /*FUNCIONES ADICIONALES*/
    
	/**
	 * Permite recuperar un usuario por su nombre, esta funcion se utiliza por el gestor de autenticación
	 * @param username
	 * @return
	 */
	public Usuario getRecord (String username){
		logger.info("getRecord");
        StringBuilder sqlSB= new StringBuilder();
        Usuario mUsuario=null;
		sqlSB.append("SELECT u.id_usuario,u.nombre, u.clave,");
		sqlSB.append("u.identidad, u.zona_horaria,u.estado,u.rol as id_rol,u.email,u.tipo,r.nombre as nombre_rol, u.id_operacion as id_operacion,u.id_cliente, ");
		sqlSB.append(" u.creado_por,u.creado_el,u.actualizado_por,u.actualizado_el,u.ip_actualizacion,u.ip_creacion, u.usuario_creacion, u.usuario_actualizacion, ");
		sqlSB.append(" k.referencia_planta_recepcion, k.referencia_destinatario_mercaderia, k.volumen_promedio_cisterna, k.nombre as nombre_operacion, c.nombre_corto as nombre_cliente, ");
		//sqlSB.append(" u.clave_temporal, ");
		sqlSB.append(" u6.id_transportista, u6.razon_social, u6.nombre_corto, u6.ruc, u.actualizacion_clave, u.intentos, u.clave_temporal ");
		
		//sqlSB.append(" u6.id_transportista, u6.razon_social, u6.nombre_corto, u6.ruc ");
		
        sqlSB.append(" FROM ");
        sqlSB.append(NOMBRE_VISTA);
        //sqlSB.append(NOMBRE_TABLA);
        sqlSB.append(" u INNER JOIN ");
        sqlSB.append(TABLA_ROL);
        sqlSB.append(" r ON u.rol = r.id_rol ");
        sqlSB.append(" LEFT JOIN sgo.operacion k ON u.id_operacion = k.id_operacion ");
        sqlSB.append(" LEFT JOIN sgo.cliente c ON u.id_cliente = c.id_cliente ");
        sqlSB.append(" LEFT JOIN sgo.transportista u6 ON u.id_transportista = u6.id_transportista ");
        sqlSB.append(" WHERE u.nombre=?");
        Object[] params = {username};
        try{
            mUsuario=(Usuario) jdbcTemplate.queryForObject(sqlSB.toString(), new UsuarioMapper(),params);
            sqlSB.setLength(0);
            sqlSB.append("SELECT pr.id_prol, pr.id_permiso, p.estado, p.nombre AS nombre, ");
            sqlSB.append(" p.creado_por,p.creado_el,p.actualizado_por,p.actualizado_el, p.usuario_actualizacion, p.usuario_creacion, p.ip_actualizacion, p.ip_creacion ");
            sqlSB.append(" FROM ");
            sqlSB.append(TABLA_PERMISOS);
            sqlSB.append(" pr ");
            sqlSB.append(" INNER JOIN ");
            sqlSB.append(VISTA_PERMISO);
            sqlSB.append(" p ON pr.id_permiso = p.id_permiso ");
            sqlSB.append(" WHERE pr.id_rol = ?");
            
            Object[] params2 = {mUsuario.getId_rol()};
//            Object[] params2 = {mUsuario.getIdRol()};
            mUsuario.getRol().setPermisos(jdbcTemplate.query(sqlSB.toString(), new PermisoMapper(),params2));
            logger.info(mUsuario.getRol().getPermisos().size());
        } catch(DataAccessException daEx){
                logger.info(daEx.getMessage());
               // daEx.printStackTrace();
        }
        return mUsuario;
	}

	
	/*public Respuesta editUsuarioClave(Usuario usuario){
		String sql = "UPDATE usuario SET clave=sha1(?),cambio_clave=? WHERE id_usuario=?"; 
		Respuesta Respuesta = new Respuesta();
		int rowsAffected=0;
		try {
			rowsAffected= jdbcTemplate.update(sql,new Object[] {usuario.getClave(),0,usuario.getId()});	
			if (rowsAffected==1){
				Respuesta.mensaje="OK";
				Respuesta.estado=true;
				Respuesta.valor = null;
			} else {
				Respuesta.mensaje="Mas filas afectadas";
				Respuesta.estado=false;
				Respuesta.valor = null;
			}
		} catch (DataAccessException daEx){
			logger.info(daEx.getMessage());
			Respuesta.mensaje=daEx.getMessage();
			Respuesta.estado=false;
			Respuesta.valor = null;
		}
		return Respuesta;
	}

	public Respuesta resetUsuarioClave(Usuario usuario){
		String sql = "UPDATE usuario SET clave=?,cambio_clave=? WHERE id_usuario=?"; 
		Respuesta Respuesta = new Respuesta();
		int rowsAffected=0;
		try {
			rowsAffected= jdbcTemplate.update(sql,new Object[] {getRandomPassword(),1,usuario.getId()});	
			if (rowsAffected==1){
				Respuesta.mensaje="OK";
				Respuesta.estado=true;
				Respuesta.valor = null;
			} else {
				Respuesta.mensaje="Mas filas afectadas";
				Respuesta.estado=false;
				Respuesta.valor = null;
			}
		} catch (DataAccessException daEx){
			logger.info(daEx.getMessage());
			Respuesta.mensaje=daEx.getMessage();
			Respuesta.estado=false;
			Respuesta.valor = null;
		}
		return Respuesta;
	}*/
	
	public Respuesta editUsuarioHabilitado(Usuario usuario){
		String sql = "UPDATE usuario SET habilitado=? WHERE id_usuario=?"; 
		Respuesta Respuesta = new Respuesta();
		int rowsAffected=0;
		try {
			rowsAffected= jdbcTemplate.update(sql,new Object[] {usuario.getEstado(),usuario.getId()});	
			if (rowsAffected==1){
				Respuesta.mensaje="OK";
				Respuesta.estado=true;
				Respuesta.valor = null;
			} else {
				Respuesta.mensaje="Mas filas afectadas";
				Respuesta.estado=false;
				Respuesta.valor = null;
			}
		} catch (DataAccessException daEx){
			Utilidades.gestionaWarning(daEx, sNombreClase, "editUsuarioHabilitado", sql.toString());
			//logger.info(daEx.getMessage());
			Respuesta.mensaje=daEx.getMessage();
			Respuesta.estado=false;
			Respuesta.valor = null;
		}
		return Respuesta;
	}
	
	public Respuesta actualizarClave(int usuarioID, int claveID ){
		String sql = "UPDATE usuario SET par_clave=? WHERE id_usuario=?"; 
		Respuesta Respuesta = new Respuesta();
		int rowsAffected=0;
		try {
			rowsAffected= jdbcTemplate.update(sql,new Object[] {claveID,usuarioID});	
			if (rowsAffected==1){
				Respuesta.mensaje="OK";
				Respuesta.estado=true;
				Respuesta.valor = null;
			} else {
				Respuesta.mensaje="Mas filas afectadas";
				Respuesta.estado=false;
				Respuesta.valor = null;
			}
		} catch (DataAccessException daEx){
			Utilidades.gestionaWarning(daEx, sNombreClase, "actualizarClave", sql.toString());
			//logger.info(daEx.getMessage());
			Respuesta.mensaje=daEx.getMessage();
			Respuesta.estado=false;
			Respuesta.valor = null;
		}
		return Respuesta;
	}
	
	private String getRandomPassword() {   
		return "sdada";
	   // return RandomStringUtils.randomAlphanumeric(constants.PASSWORD_LENGTH);
	}
}
