package sgo.datos;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
//Agregado por req 9000002857
import org.springframework.stereotype.Repository;

import sgo.entidad.Contenido;
import sgo.entidad.NumeracionGec;
import sgo.entidad.ParametrosListar;
import sgo.entidad.RespuestaCompuesta;
import sgo.utilidades.Constante;

@Repository
public class NumeracionGecDao {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	public static final String NOMBRE_TABLA = Constante.ESQUEMA_APLICACION	+ "configuracion_gec";
	public static final String NOMBRE_VISTA = Constante.ESQUEMA_APLICACION	+ "v_configuracion_gec";
	public final static String NOMBRE_CAMPO_CLAVE = "id_configuracion_gec";
	public final static String NOMBRE_CAMPO_ORDENAMIENTO = "nombre_cliente";
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public DataSource getDataSource() {
		return this.jdbcTemplate.getDataSource();
	}
	
	public RespuestaCompuesta recuperarRegistro(int ID) {
		StringBuilder consultaSQL = new StringBuilder();
		List<NumeracionGec> listaRegistros = new ArrayList<NumeracionGec>();
		Contenido<NumeracionGec> contenido = new Contenido<NumeracionGec>();
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		try {
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_configuracion_gec, ");
			consultaSQL.append("t1.id_operacion, ");
			consultaSQL.append("t1.correlativo, ");
			consultaSQL.append("t1.estado, ");
			consultaSQL.append("t1.anio, ");
			consultaSQL.append("t1.alias_operacion, ");
			consultaSQL.append("t1.nombre_operacion, ");
			consultaSQL.append("t1.nombre_cliente, ");
			consultaSQL.append("t1.creado_el, ");
			consultaSQL.append("t1.creado_por, ");
			consultaSQL.append("t1.actualizado_por, ");
			consultaSQL.append("t1.actualizado_el, ");
			consultaSQL.append("t1.ip_creacion, ");			
			consultaSQL.append("t1.ip_actualizacion, ");
			consultaSQL.append(" t1.usuario_creacion, "); 
			consultaSQL.append(" t1.usuario_actualizacion ");
			consultaSQL.append(" FROM ");
			consultaSQL.append(NOMBRE_VISTA);
			consultaSQL.append(" t1 ");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=?");
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),	new Object[] { ID }, new NumeracionGecMapper());
			contenido.totalRegistros = listaRegistros.size();
			contenido.totalEncontrados = listaRegistros.size();
			contenido.carga = listaRegistros;
			respuesta.mensaje = "OK";
			respuesta.estado = true;
			respuesta.contenido = contenido;
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
			respuesta.contenido = null;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta recuperarRegistros(ParametrosListar argumentosListar) {
		
		String sqlLimit = "";
		String sqlOrderBy = "";
		int totalRegistros = 0, totalEncontrados = 0;
		List<Object> parametros = new ArrayList<Object>();
		
		Contenido<NumeracionGec> contenido = new Contenido<NumeracionGec>();
		List<NumeracionGec> listaRegistros = new ArrayList<NumeracionGec>();
		
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		
		try{
			
			if (argumentosListar.getPaginacion() == Constante.CON_PAGINACION) {
				sqlLimit = Constante.SQL_LIMIT_CONFIGURADO;
				parametros.add(argumentosListar.getInicioPaginacion());
				parametros.add(argumentosListar.getRegistrosxPagina());
			}
			
			sqlOrderBy= " ORDER BY " + NOMBRE_CAMPO_ORDENAMIENTO + " ASC";
			
			StringBuilder consultaSQL = new StringBuilder();
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT count(" + NOMBRE_CAMPO_CLAVE	+ ") as total FROM " + NOMBRE_TABLA);
			totalRegistros = jdbcTemplate.queryForObject(consultaSQL.toString(), null, Integer.class);
			totalEncontrados = totalRegistros;
		
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_configuracion_gec, ");
			consultaSQL.append("t1.id_operacion, ");
			consultaSQL.append("t1.correlativo, ");
			consultaSQL.append("t1.estado, ");
			consultaSQL.append("t1.anio, ");
			consultaSQL.append("t1.alias_operacion, ");
			consultaSQL.append("t1.nombre_operacion, ");
			consultaSQL.append("t1.nombre_cliente, ");
			consultaSQL.append("t1.creado_el, ");
			consultaSQL.append("t1.creado_por, ");
			consultaSQL.append("t1.actualizado_por, ");
			consultaSQL.append("t1.actualizado_el, ");
			consultaSQL.append("t1.ip_creacion, ");			
			consultaSQL.append("t1.ip_actualizacion, ");
			consultaSQL.append(" t1.usuario_creacion, "); 
			consultaSQL.append(" t1.usuario_actualizacion ");
			consultaSQL.append("FROM ");
			consultaSQL.append(NOMBRE_VISTA);
			consultaSQL.append(" t1 ");			
			consultaSQL.append(sqlOrderBy);
			consultaSQL.append(sqlLimit);
			
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),	parametros.toArray(), new NumeracionGecMapper());
			
			contenido.carga = listaRegistros;
			respuesta.estado = true;
			respuesta.contenido = contenido;
			respuesta.contenido.totalRegistros = totalRegistros;
			respuesta.contenido.totalEncontrados = totalEncontrados;
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
			respuesta.contenido = null;
		} catch (Exception excepcionGenerica) {
			excepcionGenerica.printStackTrace();
			respuesta.error = Constante.EXCEPCION_GENERICA;
			respuesta.contenido = null;
			respuesta.estado = false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta guardarRegistro(NumeracionGec numeracionGec) {
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL = new StringBuilder();
		KeyHolder claveGenerada = null;
		int cantidadFilasAfectadas = 0;
		
		try {
			consultaSQL.append("INSERT INTO ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" (numero_serie, estado, id_operacion, alias_operacion, anio , correlativo, creado_el ,creado_por ,actualizado_por ,actualizado_el ,ip_creacion ,ip_actualizacion) ");
			consultaSQL.append(" VALUES (:NumeroSerie, :Estado, :IdOperacion, :AliasOperacion, :Anio, :Correlativo, :CreadoEl, :CreadoPor, :ActualizadoPor, :ActualizadoEl, :IpCreacion, :IpActualizacion) ");
			MapSqlParameterSource listaParametros = new MapSqlParameterSource();
			listaParametros.addValue("IdOperacion", numeracionGec.getIdOperacion());
			listaParametros.addValue("AliasOperacion", numeracionGec.getAliasOperacion());
			listaParametros.addValue("Anio",	numeracionGec.getAnio());
			listaParametros.addValue("Correlativo",	numeracionGec.getCorrelativo());
			listaParametros.addValue("NumeroSerie",	"00");			//Este campo ya no se va a usar (solo sirve para historico), se inserta 00 pq es NOT NULL en bd		
			listaParametros.addValue("Estado",	numeracionGec.getEstado());
			listaParametros.addValue("CreadoEl", numeracionGec.getCreadoEl());
			listaParametros.addValue("CreadoPor", numeracionGec.getCreadoPor());
			listaParametros.addValue("ActualizadoPor", numeracionGec.getActualizadoPor());
			listaParametros.addValue("ActualizadoEl", numeracionGec.getActualizadoEl());
			listaParametros.addValue("IpCreacion", numeracionGec.getIpCreacion());
			listaParametros.addValue("IpActualizacion", numeracionGec.getIpActualizacion());

			SqlParameterSource namedParameters = listaParametros;
			/* Ejecuta la consulta y retorna las filas afectadas */
			claveGenerada = new GeneratedKeyHolder();

			cantidadFilasAfectadas = namedJdbcTemplate.update(consultaSQL.toString(), namedParameters, claveGenerada, new String[] { NOMBRE_CAMPO_CLAVE });
			if (cantidadFilasAfectadas > 1) {
				respuesta.error = Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado = false;
				return respuesta;
			}
			respuesta.estado = true;
			respuesta.valor = claveGenerada.getKey().toString();
		} catch (DataIntegrityViolationException excepcionIntegridadDatos) {
			excepcionIntegridadDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado = false;
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta actualizarRegistro(NumeracionGec numeracionGec) {
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL = new StringBuilder();
		int cantidadFilasAfectadas = 0;
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("alias_operacion = :AliasOperacion,");
			consultaSQL.append("correlativo = :Correlativo,");
			consultaSQL.append("actualizado_por=:ActualizadoPor,");
			consultaSQL.append("actualizado_el=:ActualizadoEl,");
			consultaSQL.append("ip_actualizacion=:IpActualizacion");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:Id");
			
			MapSqlParameterSource listaParametros = new MapSqlParameterSource();
			listaParametros.addValue("AliasOperacion", numeracionGec.getAliasOperacion());
			listaParametros.addValue("Correlativo", numeracionGec.getCorrelativo());			
			
			// Valores Auditoria
			listaParametros.addValue("ActualizadoEl", numeracionGec.getActualizadoEl());
			listaParametros.addValue("ActualizadoPor", numeracionGec.getActualizadoPor());
			listaParametros.addValue("IpActualizacion", numeracionGec.getIpActualizacion());
			listaParametros.addValue("Id", numeracionGec.getId());
			
			SqlParameterSource namedParameters = listaParametros;
			/* Ejecuta la consulta y retorna las filas afectadas */
			cantidadFilasAfectadas = namedJdbcTemplate.update(consultaSQL.toString(), namedParameters);
			
			if (cantidadFilasAfectadas > 1) {
				respuesta.error = Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado = false;
				return respuesta;
			}
			respuesta.estado = true;
		} catch (DataIntegrityViolationException excepcionIntegridadDatos) {
			excepcionIntegridadDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado = false;
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta validaRegistroAlias(String aliasOperacion, int idOperacion) {
		StringBuilder consultaSQL = new StringBuilder();
		List<NumeracionGec> listaRegistros = new ArrayList<NumeracionGec>();
		Contenido<NumeracionGec> contenido = new Contenido<NumeracionGec>();
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		List<Object> parametros = new ArrayList<Object>();

		try {

			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_configuracion_gec, ");
			consultaSQL.append("t1.id_operacion, ");
			consultaSQL.append("t1.correlativo, ");
			consultaSQL.append("t1.estado, ");
			consultaSQL.append("t1.anio, ");
			consultaSQL.append("t1.alias_operacion, ");
			consultaSQL.append("t1.nombre_operacion, ");
			consultaSQL.append("t1.nombre_cliente, ");
			consultaSQL.append("t1.creado_el, ");
			consultaSQL.append("t1.creado_por, ");
			consultaSQL.append("t1.actualizado_por, ");
			consultaSQL.append("t1.actualizado_el, ");
			consultaSQL.append("t1.ip_creacion, ");
			consultaSQL.append("t1.ip_actualizacion, ");
			consultaSQL.append(" t1.usuario_creacion, "); 
			consultaSQL.append(" t1.usuario_actualizacion ");
			consultaSQL.append("FROM ");
			consultaSQL.append(NOMBRE_VISTA);
			consultaSQL.append(" t1 ");		
			consultaSQL.append("WHERE t1.estado = 1 AND t1.id_operacion != " + idOperacion + " AND lower(t1.alias_operacion) = lower('" + aliasOperacion + "')");
			
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),	parametros.toArray(), new NumeracionGecMapper());
			contenido.totalRegistros = listaRegistros.size();
			contenido.totalEncontrados = listaRegistros.size();
			contenido.carga = listaRegistros;
			respuesta.mensaje = "OK";
			respuesta.estado = true;
			respuesta.contenido = contenido;
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
			respuesta.contenido = null;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta validaRegistroIdOperAnio(int idOperacion, int anio) {
		StringBuilder consultaSQL = new StringBuilder();
		List<NumeracionGec> listaRegistros = new ArrayList<NumeracionGec>();
		Contenido<NumeracionGec> contenido = new Contenido<NumeracionGec>();
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		List<Object> parametros = new ArrayList<Object>();

		try {

			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_configuracion_gec, ");
			consultaSQL.append("t1.id_operacion, ");
			consultaSQL.append("t1.correlativo, ");
			consultaSQL.append("t1.estado, ");
			consultaSQL.append("t1.anio, ");
			consultaSQL.append("t1.alias_operacion, ");
			consultaSQL.append("t1.nombre_operacion, ");
			consultaSQL.append("t1.nombre_cliente, ");
			consultaSQL.append("t1.creado_el, ");
			consultaSQL.append("t1.creado_por, ");
			consultaSQL.append("t1.actualizado_por, ");
			consultaSQL.append("t1.actualizado_el, ");
			consultaSQL.append("t1.ip_creacion, ");
			consultaSQL.append("t1.ip_actualizacion, ");
			consultaSQL.append(" t1.usuario_creacion, "); 
			consultaSQL.append(" t1.usuario_actualizacion ");
			consultaSQL.append("FROM ");
			consultaSQL.append(NOMBRE_VISTA);
			consultaSQL.append(" t1 ");		
			consultaSQL.append("WHERE t1.estado = 1 AND t1.anio = " + anio + " AND t1.id_operacion = " + idOperacion);
			
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),	parametros.toArray(), new NumeracionGecMapper());
			contenido.totalRegistros = listaRegistros.size();
			contenido.totalEncontrados = listaRegistros.size();
			contenido.carga = listaRegistros;
			respuesta.mensaje = "OK";
			respuesta.estado = true;
			respuesta.contenido = contenido;
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
			respuesta.contenido = null;
		}
		return respuesta;
	}
	
	public RespuestaCompuesta actualizarEstadoRegistro(NumeracionGec numeracionGec) {
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL = new StringBuilder();
		int cantidadFilasAfectadas = 0;
		try {

			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("estado=:Estado,");
			consultaSQL.append("actualizado_por=:ActualizadoPor,");
			consultaSQL.append("actualizado_el=:ActualizadoEl,");
			consultaSQL.append("ip_actualizacion=:IpActualizacion");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:Id");
			MapSqlParameterSource listaParametros = new MapSqlParameterSource();
			listaParametros.addValue("Estado", numeracionGec.getEstado());
			// Valores Auditoria
			listaParametros.addValue("ActualizadoEl", numeracionGec.getActualizadoEl());
			listaParametros.addValue("ActualizadoPor", numeracionGec.getActualizadoPor());
			listaParametros.addValue("IpActualizacion", numeracionGec.getIpActualizacion());
			listaParametros.addValue("Id", numeracionGec.getId());
			SqlParameterSource namedParameters = listaParametros;
			/* Ejecuta la consulta y retorna las filas afectadas */
			cantidadFilasAfectadas = namedJdbcTemplate.update(consultaSQL.toString(), namedParameters);
			if (cantidadFilasAfectadas > 1) {
				respuesta.error = Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado = false;
				return respuesta;
			}
			respuesta.estado = true;
		} catch (DataIntegrityViolationException excepcionIntegridadDatos) {
			excepcionIntegridadDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado = false;
		} catch (DataAccessException excepcionAccesoDatos) {
			excepcionAccesoDatos.printStackTrace();
			respuesta.error = Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado = false;
		}
		return respuesta;
	}
}
