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

import sgo.entidad.Parametro;
import sgo.entidad.Contenido;
import sgo.entidad.ParametrosListar;
import sgo.entidad.RespuestaCompuesta;
import sgo.utilidades.Constante;

@Repository
public class ParametroDao {
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	public static final String NOMBRE_TABLA = Constante.ESQUEMA_APLICACION	+  "parametro";
	public static final String NOMBRE_VISTA =  Constante.ESQUEMA_APLICACION	+ "v_parametro";
	public final static String NOMBRE_CAMPO_CLAVE = "id_parametro";
	public final static String NOMBRE_CAMPO_FILTRO = "valor";
	public final static String NOMBRE_CAMPO_ORDENAMIENTO_DEFECTO = "valor";
	public final static String O = " OR ";
	public final static String Y = " AND ";
	public final static String ENTRE = " BETWEEN ";
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public DataSource getDataSource() {
		return this.jdbcTemplate.getDataSource();
	}

	public String mapearCampoOrdenamiento(String propiedad) {
		String campoOrdenamiento = NOMBRE_CAMPO_ORDENAMIENTO_DEFECTO;
		try {
			if (propiedad.equals("valor")) {
				campoOrdenamiento = "valor";
			}
			if (propiedad.equals("alias")) {
				campoOrdenamiento = "alias";
			}
			// Campos de auditoria
		} catch (Exception excepcion) {

		}
		return campoOrdenamiento;
	}
	
	public RespuestaCompuesta recuperarRegistros(ParametrosListar argumentosListar) {
		String sqlLimit = "";
		String sqlOrderBy = "";
		List<String> filtrosWhere = new ArrayList<String>();
		String sqlWhere = "";
		int totalRegistros = 0, totalEncontrados = 0;
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		Contenido<Parametro> contenido = new Contenido<Parametro>();
		List<Parametro> listaRegistros = new ArrayList<Parametro>();
		List<Object> parametros = new ArrayList<Object>();
		try {
			if (argumentosListar.getPaginacion() == Constante.CON_PAGINACION) {
				sqlLimit = Constante.SQL_LIMIT_CONFIGURADO;
				parametros.add(argumentosListar.getInicioPaginacion());
				parametros.add(argumentosListar.getRegistrosxPagina());
			}
			
			sqlOrderBy= " ORDER BY " + this.mapearCampoOrdenamiento(argumentosListar.getCampoOrdenamiento()) + " " + argumentosListar.getSentidoOrdenamiento();

			StringBuilder consultaSQL = new StringBuilder();
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT count(" + NOMBRE_CAMPO_CLAVE	+ ") as total FROM " + NOMBRE_TABLA);
			totalRegistros = jdbcTemplate.queryForObject(consultaSQL.toString(), null, Integer.class);
			totalEncontrados = totalRegistros;
			
			if (!argumentosListar.getValorBuscado().isEmpty()) {
				filtrosWhere.add("lower(t1." + NOMBRE_CAMPO_FILTRO + ") like lower('%" + argumentosListar.getValorBuscado() + "%') ");
			}
			if (!argumentosListar.getTxtFiltro().isEmpty()){
				filtrosWhere.add("lower(t1."+NOMBRE_CAMPO_FILTRO+") like lower('%"+ argumentosListar.getTxtFiltro() +"%') ");
			}
			if (!argumentosListar.getFiltroParametro().isEmpty()){
				filtrosWhere.add("lower(t1.alias) like lower('%"+ argumentosListar.getFiltroParametro() +"%') ");
			}
			if (!filtrosWhere.isEmpty()) {
				consultaSQL.setLength(0);
				sqlWhere = "WHERE " + StringUtils.join(filtrosWhere, Y);
				consultaSQL.append("SELECT count(t1." + NOMBRE_CAMPO_CLAVE + ") as total FROM " + NOMBRE_VISTA + " t1 " + sqlWhere);
				totalEncontrados = jdbcTemplate.queryForObject(consultaSQL.toString(), null, Integer.class);
			}
		
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_parametro,");
			consultaSQL.append("t1.valor,");
			consultaSQL.append("t1.alias,");
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
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),parametros.toArray(), new ParametroMapper());
			totalEncontrados =totalRegistros;
			contenido.carga = listaRegistros;
			respuesta.estado = true;
			respuesta.mensaje = "OK";
			respuesta.contenido = contenido;
			respuesta.contenido.totalRegistros = totalRegistros;
			respuesta.contenido.totalEncontrados = totalEncontrados;
		} catch (DataAccessException daEx) {
			daEx.printStackTrace();
			respuesta.error=  Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
		} catch (Exception ex) {
			ex.printStackTrace();
			respuesta.estado = false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta recuperarRegistro(int ID){
			StringBuilder consultaSQL= new StringBuilder();		
			List<Parametro> listaRegistros=new ArrayList<Parametro>();
			Contenido<Parametro> contenido = new Contenido<Parametro>();
			RespuestaCompuesta respuesta= new RespuestaCompuesta();
			try {
				consultaSQL.append("SELECT ");
				consultaSQL.append("t1.id_parametro,");
				consultaSQL.append("t1.valor,");
				consultaSQL.append("t1.alias,");
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
				listaRegistros= jdbcTemplate.query(consultaSQL.toString(),new Object[] {ID},new ParametroMapper());
				contenido.totalRegistros=listaRegistros.size();
				contenido.totalEncontrados=listaRegistros.size();
				contenido.carga= listaRegistros;
				respuesta.mensaje="OK";
				respuesta.estado=true;
				respuesta.contenido = contenido;			
			} catch (DataAccessException daEx) {
				respuesta.mensaje=daEx.getMessage();
				respuesta.estado=false;
				respuesta.contenido=null;
			}
			return respuesta;
		}
	
	public RespuestaCompuesta guardarRegistro(Parametro parametro){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder sbSQL= new StringBuilder();
		KeyHolder claveGenerada = null;
		int rowsAffected=0;
		try {
			sbSQL.append("INSERT INTO ");
			sbSQL.append(NOMBRE_TABLA);
			sbSQL.append(" (valor,alias,creado_el,creado_por,actualizado_por,actualizado_el,ip_creacion,ip_actualizacion) ");
			sbSQL.append(" VALUES (:Valor,:Alias,:CreadoEl,:CreadoPor,:ActualizadoPor,:ActualizadoEl,:IpCreacion,:IpActualizacion) ");
			MapSqlParameterSource mapParameters= new MapSqlParameterSource();
			mapParameters.addValue("Valor", parametro.getValor());
			mapParameters.addValue("Alias", parametro.getAlias());
			mapParameters.addValue("CreadoEl", parametro.getCreadoEl());
			mapParameters.addValue("CreadoPor", parametro.getCreadoPor());
			mapParameters.addValue("ActualizadoPor", parametro.getActualizadoPor());
			mapParameters.addValue("ActualizadoEl", parametro.getActualizadoEl());
			mapParameters.addValue("IpCreacion", parametro.getIpCreacion());
			mapParameters.addValue("IpActualizacion", parametro.getIpActualizacion());
			
			SqlParameterSource namedParameters= mapParameters;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			claveGenerada = new GeneratedKeyHolder();
			rowsAffected= namedJdbcTemplate.update(sbSQL.toString(),namedParameters,claveGenerada,new String[] {NOMBRE_CAMPO_CLAVE});
			if (rowsAffected>1){
				respuesta.mensaje="Mas filas afectadas";
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.mensaje="OK";
			respuesta.estado=true;
			respuesta.valor= claveGenerada.getKey().toString();

		} catch (DataIntegrityViolationException daEx){
			respuesta.mensaje=daEx.getMessage();
			respuesta.estado=false;
		} catch (DataAccessException daEx){
			respuesta.mensaje=daEx.getMessage();
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta actualizarRegistro(Parametro parametro){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		int rowsAffected=0;
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("valor=:Valor,");
			consultaSQL.append("alias=:Alias,");
			consultaSQL.append("actualizado_por=:ActualizadoPor,");
			consultaSQL.append("actualizado_el=:ActualizadoEl,");
			consultaSQL.append("ip_actualizacion=:IpActualizacion");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:id");
			MapSqlParameterSource mapParameters= new MapSqlParameterSource();
			mapParameters.addValue("Valor", parametro.getValor());
			mapParameters.addValue("Alias", parametro.getAlias());
			mapParameters.addValue("ActualizadoPor", parametro.getActualizadoPor());
			mapParameters.addValue("ActualizadoEl", parametro.getActualizadoEl());
			mapParameters.addValue("IpActualizacion", parametro.getIpActualizacion());
			
			mapParameters.addValue("id", parametro.getId());
			SqlParameterSource namedParameters= mapParameters;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			rowsAffected= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters);		
			if (rowsAffected>1){
				respuesta.mensaje="Mas filas afectadas";
				respuesta.estado=false;
				return respuesta;
			}
			respuesta.estado=true;
		} catch (DataIntegrityViolationException daEx){
			respuesta.mensaje=daEx.getMessage();
			respuesta.estado=false;
		} catch (DataAccessException daEx){
			respuesta.mensaje=daEx.getMessage();
			respuesta.estado=false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta eliminarRegistro(int idRegistro){		
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		int rowsAffected=0;	
		String sql="";
		Object[] params = {idRegistro};
		try {
			sql="DELETE FROM " + NOMBRE_TABLA + " WHERE " + NOMBRE_CAMPO_CLAVE + "=?";
        	rowsAffected = jdbcTemplate.update(sql, params);
			if (rowsAffected==1){
				respuesta.estado=true;
			} else {
				respuesta.estado=false;
			}
		} catch (DataIntegrityViolationException daEx){	
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		} catch (DataAccessException daEx){
			respuesta.error= Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
		}
		return respuesta;
	}
}
