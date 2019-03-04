package sgo.datos;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
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

import sgo.entidad.CanalSector;
import sgo.entidad.Cliente;
import sgo.entidad.Contenido;
import sgo.entidad.ParametrosListar;
import sgo.entidad.RespuestaCompuesta;
import sgo.utilidades.Constante;

@Repository
public class CanalSectorDao {
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	private static final String NOMBRE_TABLA = Constante.ESQUEMA_APLICACION + "canal_sector_sap";
	private static final String NOMBRE_VISTA = Constante.ESQUEMA_APLICACION + "v_cliente";
	private final static String NOMBRE_CAMPO_CLAVE = "id_canal_sector";
	private final static String NOMBRE_CAMPO_FILTRO = "fk_cod_cliente";
	private final static String NOMBRE_CAMPO_ORDENAMIENTO_DEFECTO = "";	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	private String mapearCampoOrdenamiento(String propiedad){
		String campoOrdenamiento=NOMBRE_CAMPO_ORDENAMIENTO_DEFECTO;
		try {
			if (propiedad.equals("id")){
				campoOrdenamiento="id_cliente";
			}
			if (propiedad.equals("nombreCorto")){
				campoOrdenamiento="nombre_corto";
			}
			if (propiedad.equals("razonSocial")){
				campoOrdenamiento="razon_social";
			}
			if (propiedad.equals("ruc")){
				campoOrdenamiento="ruc";
			}
			if (propiedad.equals("estado")){
				campoOrdenamiento="estado";
			}
		} catch(Exception excepcion){
			
		}
		return campoOrdenamiento;
	}

	public RespuestaCompuesta recuperarRegistros(ParametrosListar argumentosListar) {
		String sqlLimit = "";
		String sqlOrderBy="";
		List<String> filtrosWhere= new ArrayList<String>();
		String sqlWhere="";
		int totalRegistros = 0, 
		totalEncontrados = 0;
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		Contenido<CanalSector> contenido = new Contenido<CanalSector>();
		List<CanalSector> listaRegistros = new ArrayList<CanalSector>();
		List<Object> parametros = new ArrayList<Object>();
		try {
			if (argumentosListar.getPaginacion() == Constante.CON_PAGINACION) {
				sqlLimit = Constante.SQL_LIMIT_CONFIGURADO;
				parametros.add(argumentosListar.getInicioPaginacion());
				parametros.add(argumentosListar.getRegistrosxPagina());
			}
			
//			sqlOrderBy= " ORDER BY " + this.mapearCampoOrdenamiento(argumentosListar.getCampoOrdenamiento()) + " "  + argumentosListar.getSentidoOrdenamiento();
			
			StringBuilder consultaSQL = new StringBuilder();
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT count(" + NOMBRE_CAMPO_CLAVE+ ") as total FROM " + NOMBRE_TABLA);
			totalRegistros = jdbcTemplate.queryForObject(consultaSQL.toString(), null, Integer.class);
			totalEncontrados=totalRegistros;

				filtrosWhere.add(" t1."+NOMBRE_CAMPO_FILTRO + "=" + argumentosListar.getFiltroIdUsuario());
			
				consultaSQL.setLength(0);
				sqlWhere = "WHERE " + " t1."+NOMBRE_CAMPO_FILTRO + "=" + argumentosListar.getFiltroIdUsuario();
//			
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_canal_sector,");
			consultaSQL.append("t1.cod_clientesap,");
			consultaSQL.append("t1.fk_cod_cliente,");
			consultaSQL.append("t1.org_venta_sap,");
			consultaSQL.append("t1.des_org_venta_sap,");
			consultaSQL.append("t1.canal_distribucion_sap,");
			consultaSQL.append("t1.desc_canal_distribucion_sap,");
			consultaSQL.append("t1.sector_sap,");
			consultaSQL.append("t1.desc_sector_sap,");
			consultaSQL.append("t1.descripcion_canal_sector,");
			consultaSQL.append("t1.creado_el,");
			consultaSQL.append("t1.creado_por,");
			consultaSQL.append("t1.actualizado_por,");
			consultaSQL.append("t1.actualizado_el");
			consultaSQL.append(" FROM ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" t1 ");	
			consultaSQL.append(sqlWhere);
			consultaSQL.append(sqlOrderBy);
			consultaSQL.append(sqlLimit);
			System.out.println(consultaSQL.toString());
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),parametros.toArray(), new CanalSectorMapper());
		
			contenido.carga = listaRegistros;
			respuesta.estado = true;
			respuesta.contenido = contenido;
			respuesta.contenido.totalRegistros = totalRegistros;
			respuesta.contenido.totalEncontrados = totalEncontrados;
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error=  Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
			respuesta.contenido=null;
		} catch (Exception excepcionGenerica) {
			excepcionGenerica.printStackTrace();
			respuesta.error= Constante.EXCEPCION_GENERICA;
			respuesta.contenido=null;
			respuesta.estado = false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta recuperarRegistro(int ID){
			StringBuilder consultaSQL= new StringBuilder();
			List<Cliente> listaClientes=new ArrayList<Cliente>();
			Contenido<Cliente> contenido = new Contenido<Cliente>();
			RespuestaCompuesta respuesta= new RespuestaCompuesta();
			try {
				consultaSQL.append("SELECT ");
				consultaSQL.append("t1.id_cliente,");
				consultaSQL.append("t1.nombre_corto,");
				consultaSQL.append("t1.razon_social,");
				consultaSQL.append("t1.numero_contrato,");
				consultaSQL.append("t1.descripcion_contrato,");
				consultaSQL.append("t1.ruc,");
				consultaSQL.append("t1.estado,");
				consultaSQL.append("t1.creado_el,");
				consultaSQL.append("t1.creado_por,");
				consultaSQL.append("t1.actualizado_por,");
				consultaSQL.append("t1.actualizado_el,");	
				consultaSQL.append("t1.usuario_creacion,");
				consultaSQL.append("t1.usuario_actualizacion,");
				consultaSQL.append("t1.ip_creacion,");
				consultaSQL.append("t1.ip_actualizacion,");
				consultaSQL.append("t1.codigo_sap,");
				consultaSQL.append("t1.razon_social_sap,");
				consultaSQL.append("t1.rama_sap");
				consultaSQL.append(" FROM ");				
				consultaSQL.append(NOMBRE_VISTA);
				consultaSQL.append(" t1 ");
				consultaSQL.append(" WHERE ");
				consultaSQL.append(NOMBRE_CAMPO_CLAVE);
				consultaSQL.append("=?");
				listaClientes= jdbcTemplate.query(consultaSQL.toString(),new Object[] {ID},new ClienteMapper());
				contenido.totalRegistros=listaClientes.size();
				contenido.totalEncontrados=listaClientes.size();
				contenido.carga= listaClientes;
				respuesta.mensaje="OK";
				respuesta.estado=true;
				respuesta.contenido = contenido;			
			} catch (DataAccessException excepcionAccesoDatos) {
				excepcionAccesoDatos.printStackTrace();
				respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
				respuesta.estado=false;
				respuesta.contenido=null;
			}
			return respuesta;
		}
	
	public RespuestaCompuesta guardarRegistro(Cliente cliente){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		KeyHolder claveGenerada = null;
		int cantidadFilasAfectadas=0;
		try {
			System.out.println("entra a guardarRegistro");
			consultaSQL.append("INSERT INTO ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" (razon_social,nombre_corto,numero_contrato,descripcion_contrato,ruc,estado,creado_el,creado_por,actualizado_por,actualizado_el,ip_creacion,ip_actualizacion,codigo_sap,razon_social_sap,rama_sap) ");
			consultaSQL.append(" VALUES (:razonSocial,:nombreCorto,:numeroContrato,:descripcionContrato,:ruc,:estado,:creadoEl,:creadoPor,:actualizadoPor,:actualizadoEl,:IpCreacion,:IpActualizacion,:codigoSAP,:razonSocialSAP,:ramaSAP)");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("razonSocial", cliente.getRazonSocial());
			listaParametros.addValue("nombreCorto", cliente.getNombreCorto());
			listaParametros.addValue("numeroContrato", cliente.getNumeroContrato());
			listaParametros.addValue("descripcionContrato", cliente.getDescripcionContrato());
			listaParametros.addValue("ruc", cliente.getRuc());			
			listaParametros.addValue("estado", cliente.getEstado());
			listaParametros.addValue("creadoEl", cliente.getCreadoEl());
			listaParametros.addValue("creadoPor", cliente.getCreadoPor());			
			listaParametros.addValue("actualizadoEl", cliente.getActualizadoEl());
			listaParametros.addValue("actualizadoPor", cliente.getActualizadoPor());
			listaParametros.addValue("IpCreacion", cliente.getIpCreacion());
			listaParametros.addValue("IpActualizacion", cliente.getIpActualizacion());
			listaParametros.addValue("codigoSAP", cliente.getCodigoSAP());/*phf*/
			listaParametros.addValue("razonSocialSAP", cliente.getRazonSocialSAP());
			listaParametros.addValue("ramaSAP", cliente.getRamaSAP());
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

		} catch (DataIntegrityViolationException excepcionIntegridadDatos){
			excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			excepcionAccesoDatos.printStackTrace();
			respuesta.error=Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta actualizarRegistro(Cliente cliente){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		int cantidadFilasAfectadas=0;
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("razon_social=:razonSocial,");
			consultaSQL.append("nombre_corto=:nombreCorto,");
			consultaSQL.append("numero_contrato=:numeroContrato,");
			consultaSQL.append("descripcion_contrato=:descripcionContrato,");
			consultaSQL.append("ruc=:ruc,");
			//consultaSQL.append("estado=:estado,");
			consultaSQL.append("actualizado_por=:actualizadoPor,");
			consultaSQL.append("actualizado_el=:actualizadoEl,");
			consultaSQL.append("ip_actualizacion=:IpActualizacion,");
			consultaSQL.append("codigo_sap=:codigoSAP,");/*phf*/
			consultaSQL.append("razon_social_sap=:razonSocialSAP,");
			consultaSQL.append("rama_sap=:ramaSAP");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:id");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("razonSocial", cliente.getRazonSocial());
			listaParametros.addValue("nombreCorto", cliente.getNombreCorto());
			listaParametros.addValue("numeroContrato", cliente.getNumeroContrato());
			listaParametros.addValue("descripcionContrato", cliente.getDescripcionContrato());
			listaParametros.addValue("ruc", cliente.getRuc());			
			//listaParametros.addValue("estado", cliente.getEstado());
			//listaParametros.addValue("creadoEl", cliente.getCreadoEl());
			//listaParametros.addValue("creadoPor", cliente.getCreadoPor());			
			listaParametros.addValue("actualizadoEl", cliente.getActualizadoEl());
			listaParametros.addValue("actualizadoPor", cliente.getActualizadoPor());
			listaParametros.addValue("IpActualizacion", cliente.getIpActualizacion());
			listaParametros.addValue("id", cliente.getId());
			listaParametros.addValue("codigoSAP", cliente.getCodigoSAP());
			listaParametros.addValue("razonSocialSAP", cliente.getRazonSocialSAP());
			listaParametros.addValue("ramaSAP", cliente.getRamaSAP());
			SqlParameterSource namedParameters= listaParametros;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters);		
			if (cantidadFilasAfectadas>1){
				respuesta.error= Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){
			excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			excepcionAccesoDatos.printStackTrace();
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
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){	
			excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			excepcionAccesoDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta actualizarDescripcionCanalSector(CanalSector canalSector){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		int cantidadFilasAfectadas=0;
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("descripcion_canal_sector=:descCanalSector,");
			consultaSQL.append("actualizado_por=:actualizadoPor,");
			consultaSQL.append("actualizado_el=:actualizadoEl,");
			consultaSQL.append("ip_actualizacion=:IpActualizacion");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:id");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("descCanalSector", canalSector.getDescripcionCanalSector());
			listaParametros.addValue("actualizadoEl",   canalSector.getActualizadoEl());
			listaParametros.addValue("actualizadoPor",  canalSector.getActualizadoPor());
			listaParametros.addValue("IpActualizacion", canalSector.getIpActualizacion());
			listaParametros.addValue("id", canalSector.getIdCanalSector());
			SqlParameterSource namedParameters= listaParametros;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters);		
			if (cantidadFilasAfectadas>1){
				respuesta.error= Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
		} catch (DataIntegrityViolationException excepcionIntegridadDatos){
			excepcionIntegridadDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException excepcionAccesoDatos){
			excepcionAccesoDatos.printStackTrace();
			respuesta.error= Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta actualizarDescripcionCanalSector(Cliente cliente) {
		if (cliente.getListaDescripciones() != null) {
			for (CanalSector cs : cliente.getListaDescripciones()) {
				if (cs != null) {
					cs.setActualizadoEl(cliente.getActualizadoEl());
					cs.setActualizadoPor(cliente.getActualizadoPor());
					cs.setIpActualizacion(cliente.getIpActualizacion());
					actualizarDescripcionCanalSector(cs);
				}
			}
		}
		return null;
	}
}

