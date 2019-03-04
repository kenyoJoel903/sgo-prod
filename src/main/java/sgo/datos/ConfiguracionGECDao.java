package sgo.datos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Repository;

import sgo.entidad.ConfiguracionGec;
import sgo.entidad.Contenido;
import sgo.entidad.Planta;
import sgo.entidad.RespuestaCompuesta;
import sgo.utilidades.Constante;

@Repository
public class ConfiguracionGECDao {
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	public static final String NOMBRE_TABLA = Constante.ESQUEMA_APLICACION + "configuracion_gec";
	public final static String NOMBRE_CAMPO_CLAVE = "id_configuracion_gec";
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public DataSource getDataSource(){
		return this.jdbcTemplate.getDataSource();
	}
	
	public RespuestaCompuesta recuperarConfigPorIdOperacion(int idOperacion){
		
		ConfiguracionGec eConfigGec = null;
		
		List<ConfiguracionGec> listaRegistros=new ArrayList<ConfiguracionGec>();
		Contenido<ConfiguracionGec> contenido = new Contenido<ConfiguracionGec>();
		RespuestaCompuesta respuesta= new RespuestaCompuesta();
		
		StringBuilder consultaSQL= new StringBuilder();	
		
		try{
			
			consultaSQL.append("SELECT id_configuracion_gec, id_operacion, correlativo, numero_serie "  );
			consultaSQL.append("FROM " + NOMBRE_TABLA );
			consultaSQL.append(" WHERE estado = 1 AND id_operacion = " + idOperacion);
			
			List<Map<String,Object>> mapRegistros= jdbcTemplate.queryForList(consultaSQL.toString(),new Object[]{});
			
			if(mapRegistros != null && !mapRegistros.isEmpty()){
				Map<String, Object> map = mapRegistros.get(0);
				
				Integer id = (Integer) map.get("id_configuracion_gec");
				
				if(id != null){
					eConfigGec = new ConfiguracionGec();
					eConfigGec.setIdConfiguracionGec((Integer) map.get("id_configuracion_gec"));
					eConfigGec.setIdOperacion((Integer) map.get("id_operacion"));
					eConfigGec.setCorrelativo((String) map.get("correlativo"));
					eConfigGec.setNumeroSerie((String) map.get("numero_serie"));
				}
			}

			listaRegistros.add(eConfigGec);
			contenido.totalRegistros=listaRegistros.size();
			contenido.totalEncontrados=listaRegistros.size();
			contenido.carga= listaRegistros;
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
	
	public RespuestaCompuesta guardarRegistro(ConfiguracionGec confGec){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		KeyHolder claveGenerada = null;
		int cantidadFilasAfectadas=0;
		try {
			consultaSQL.append("INSERT INTO ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" (id_operacion, correlativo, numero_serie, estado) ");

			consultaSQL.append(" VALUES (:idOperacion,:correlativo,:nroSerie,:estado)");
		
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();   
			listaParametros.addValue("idOperacion", confGec.getIdOperacion());
			listaParametros.addValue("correlativo", confGec.getCorrelativo());
			listaParametros.addValue("nroSerie", confGec.getNumeroSerie());
			listaParametros.addValue("estado", confGec.getEstado());			
			
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
	
	public RespuestaCompuesta actualizarRegistro(ConfiguracionGec confGec){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		int cantidadFilasAfectadas=0;
		try {
			consultaSQL.append("UPDATE ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" SET ");
			consultaSQL.append("correlativo	 = :correlativo,");
			consultaSQL.append("numero_serie = :nroSerie");
			consultaSQL.append(" WHERE ");
			consultaSQL.append(NOMBRE_CAMPO_CLAVE);
			consultaSQL.append("=:Id");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("correlativo", confGec.getCorrelativo());
			listaParametros.addValue("nroSerie", confGec.getNumeroSerie());

			listaParametros.addValue("Id", confGec.getIdConfiguracionGec());
			
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
	
	public Integer recuperarMaxNroSerie(){
		
		StringBuilder consultaSQL= new StringBuilder();	
		
		consultaSQL.append("SELECT cast(max(numero_serie) AS integer) + 1 AS nroSerie FROM sgo.configuracion_gec ");		
		
		Integer nroSerie = 1;
		
		try{
			
			List<Map<String,Object>> mapRegistros= jdbcTemplate.queryForList(consultaSQL.toString(),new Object[]{});
			
			if(mapRegistros != null && mapRegistros.size() > 0){
				Map<String,Object> map = mapRegistros.get(0);
				
				nroSerie = (Integer) map.get("nroSerie");
				
			}else{
				nroSerie = 1;
			}
			
			if(nroSerie == null){
				nroSerie = 1;
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return nroSerie;
	}

}
