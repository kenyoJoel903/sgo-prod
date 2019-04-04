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
import sgo.entidad.DatosInterlocutor;
import sgo.entidad.ParametrosListar;
import sgo.entidad.Planta;
import sgo.entidad.Producto;
import sgo.entidad.Proforma;
import sgo.entidad.ProformaDetalle;
import sgo.entidad.RespuestaCompuesta;
import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;

@Repository
public class ProformaDao {
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	public static final String NOMBRE_TABLA = Constante.ESQUEMA_APLICACION + "proforma";
	private static final String NOMBRE_VISTA = Constante.ESQUEMA_APLICACION + "v_proforma";
	public static final String NOMBRE_TABLA_DETALLE = Constante.ESQUEMA_APLICACION + "detalle_proforma";
	private static final String VISTA_TABLA_DETALLE = Constante.ESQUEMA_APLICACION + "v_detalle_proforma";
	private static final String VISTA_PLANTA_HABILITADA = Constante.ESQUEMA_APLICACION + "v_plantas_habilitadas";
	private static final String VISTA_PRODUCTO_HABILITADO = Constante.ESQUEMA_APLICACION + "v_productos_habilitados";

	private static final String TABLA_CANAL_SECTOR = Constante.ESQUEMA_APLICACION + "canal_sector_sap";
	private static final String TABLA_DESTINATARIO1 = Constante.ESQUEMA_APLICACION + "datos_interlocutor_sap";
	private final static String NOMBRE_CAMPO_CLAVE = "id_proforma";
	private final static String NOMBRE_CAMPO_CLAVE_DETALLE = "id_detalle_proforma";
	private final static String NOMBRE_CAMPO_FILTRO = "fk_cod_cliente";
	private final static String NOMBRE_CAMPO_FILTRO_DETALLE = "fk_id_proforma";
	private final static String FECHA_COTIZACION = "fecha_cotizacion";
	private final static String NOMBRE_CAMPO_ORDENAMIENTO_DEFECTO = "razon_social";	
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public DataSource getDataSource(){
		return this.jdbcTemplate.getDataSource();
	}
	
	public String mapearCampoOrdenamiento(String propiedad){
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
		Contenido<Proforma> contenido = new Contenido<Proforma>();
		List<Proforma> listaRegistros = new ArrayList<Proforma>();
		List<Object> parametros = new ArrayList<Object>();
		try {
			if (argumentosListar.getPaginacion() == Constante.CON_PAGINACION) {
				sqlLimit = Constante.SQL_LIMIT_CONFIGURADO;
				parametros.add(argumentosListar.getInicioPaginacion());
				parametros.add(argumentosListar.getRegistrosxPagina());
			}
			if(argumentosListar.getIdCliente() != 0){
			filtrosWhere.add(" t1." + NOMBRE_CAMPO_FILTRO + " = " + argumentosListar.getIdCliente() );
			}
			sqlOrderBy= " ORDER BY " + this.mapearCampoOrdenamiento(argumentosListar.getCampoOrdenamiento()) + " "  + argumentosListar.getSentidoOrdenamiento();
			
			StringBuilder consultaSQL = new StringBuilder();
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT count(" + NOMBRE_CAMPO_CLAVE+ ") as total FROM " + NOMBRE_TABLA);
			totalRegistros = jdbcTemplate.queryForObject(consultaSQL.toString(), null, Integer.class);
			totalEncontrados=totalRegistros;

			// Esto para el filtro de fechas
			String fechaInicio = argumentosListar.getFiltroFechaInicio();
			String fechaFinal = argumentosListar.getFiltroFechaFinal();

			if (!fechaInicio.isEmpty() && !fechaFinal.isEmpty()) {
				filtrosWhere.add(" t1." + FECHA_COTIZACION + Constante.SQL_ENTRE
						+ ("to_date('" + fechaInicio + "','yyyy-MM-dd')" + Constante.SQL_Y + "to_date('" + fechaFinal + "','yyyy-MM-dd')"));
			} else {
				if (!fechaInicio.isEmpty()) {
					filtrosWhere.add(" t1." + FECHA_COTIZACION + " >= to_date('" + fechaInicio + "','yyyy-MM-dd')");
				}
				if (!fechaFinal.isEmpty()) {
					filtrosWhere.add(" t1." + FECHA_COTIZACION + " <= to_date('" + fechaFinal + "','yyyy-MM-dd')");
				}
			}
			
			if(!filtrosWhere.isEmpty()){
				consultaSQL.setLength(0);
				sqlWhere = "WHERE " + StringUtils.join(filtrosWhere, Constante.SQL_Y);
				consultaSQL.append("SELECT count(t1." + NOMBRE_CAMPO_CLAVE+ ") as total FROM " + NOMBRE_VISTA + " t1 " + sqlWhere);
				totalEncontrados = jdbcTemplate.queryForObject(consultaSQL.toString(), null, Integer.class);
			}

			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_proforma,");
			consultaSQL.append("t1.cod_clientesap,");
			consultaSQL.append("t1.fk_cod_cliente,");
			consultaSQL.append("t1.fk_canal_sector,");
			consultaSQL.append("t1.nro_cotizacion,");
			consultaSQL.append("t1.fecha_cotizacion,");
			consultaSQL.append("t1.destinatario,");
			consultaSQL.append("t1.cod_interlocutor_sap,");
			consultaSQL.append("t1.moneda,");
			consultaSQL.append("t1.monto,");
			consultaSQL.append("t1.razon_social,");
			consultaSQL.append("t1.ruc,");
			consultaSQL.append("t1.nombre_corto,");
			consultaSQL.append("t1.descripcion_canal_sector,");
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
			System.out.println(consultaSQL.toString());
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),parametros.toArray(), new ProformaMapper());
		
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
			List<Proforma> lista=new ArrayList<Proforma>();
			Contenido<Proforma> contenido = new Contenido<Proforma>();
			RespuestaCompuesta respuesta= new RespuestaCompuesta();
			try {
				consultaSQL.append("SELECT ");
				consultaSQL.append("t1.id_proforma,");
				consultaSQL.append("t1.cod_clientesap,");
				consultaSQL.append("t1.fk_cod_cliente,");
				consultaSQL.append("t1.fk_canal_sector,");
				consultaSQL.append("t1.nro_cotizacion,");
				consultaSQL.append("t1.fecha_cotizacion,");
				consultaSQL.append("t1.cod_interlocutor_sap,");
				consultaSQL.append("t1.destinatario,");
				consultaSQL.append("t1.moneda,");
				consultaSQL.append("t1.monto,");
				consultaSQL.append("t1.ruc,");
				consultaSQL.append("t1.razon_social,");
				consultaSQL.append("t1.nombre_corto,");
				consultaSQL.append("t1.descripcion_canal_sector,");
				
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
				lista= jdbcTemplate.query(consultaSQL.toString(),new Object[] {ID},new ProformaMapper());
				contenido.totalRegistros=lista.size();
				contenido.totalEncontrados=lista.size();
				contenido.carga= lista;
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

	public RespuestaCompuesta recuperarDetalleRegistros(ParametrosListar argumentosListar) {
		String sqlLimit = "";
		List<String> filtrosWhere= new ArrayList<String>();
		String sqlWhere="";
		int totalRegistros = 0, 
		totalEncontrados = 0;
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		Contenido<ProformaDetalle> contenido = new Contenido<ProformaDetalle>();
		List<ProformaDetalle> listaRegistros = new ArrayList<ProformaDetalle>();
		List<Object> parametros = new ArrayList<Object>();
		try {
			if (argumentosListar.getPaginacion() == Constante.CON_PAGINACION) {
				sqlLimit = Constante.SQL_LIMIT_CONFIGURADO;
				parametros.add(argumentosListar.getInicioPaginacion());
				parametros.add(argumentosListar.getRegistrosxPagina());
			}
			filtrosWhere.add(" t1." + NOMBRE_CAMPO_FILTRO_DETALLE + " = " + argumentosListar.getFiltroIdProforma() );
			
			StringBuilder consultaSQL = new StringBuilder();
			consultaSQL.setLength(0);
			consultaSQL.append("SELECT count(" + NOMBRE_CAMPO_CLAVE_DETALLE+ ") as total FROM " + VISTA_TABLA_DETALLE);
			totalRegistros = jdbcTemplate.queryForObject(consultaSQL.toString(), null, Integer.class);
			totalEncontrados=totalRegistros;

			if(!filtrosWhere.isEmpty()){
				consultaSQL.setLength(0);
				sqlWhere = "WHERE " + StringUtils.join(filtrosWhere, Constante.SQL_Y);
				consultaSQL.append("SELECT count(t1." + NOMBRE_CAMPO_CLAVE_DETALLE+ ") as total FROM " + VISTA_TABLA_DETALLE + " t1 " + sqlWhere);
				totalEncontrados = jdbcTemplate.queryForObject(consultaSQL.toString(), null, Integer.class);
			}

			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_detalle_proforma,");
			consultaSQL.append("t1.fk_id_proforma,");
			consultaSQL.append("t1.fk_id_planta,");
			consultaSQL.append("t1.fk_id_producto,");
			consultaSQL.append("t1.posicion,");
			consultaSQL.append("t1.volumen,");
			consultaSQL.append("t1.precio,");
			consultaSQL.append("t1.descuento,");
			consultaSQL.append("t1.precio_neto,");
			consultaSQL.append("t1.rodaje,");
			consultaSQL.append("t1.isc,");
			consultaSQL.append("t1.otros,"); // ticket 9000003025
			consultaSQL.append("t1.acumulado,");
			consultaSQL.append("t1.igv,");
			consultaSQL.append("t1.fise,");
			consultaSQL.append("t1.precio_descuento,");
			consultaSQL.append("t1.precio_percepcion,");
			consultaSQL.append("t1.importe_total,");
			consultaSQL.append("t1.nombre_producto,");
			consultaSQL.append("t1.nombre_planta");
			consultaSQL.append(" FROM ");
			consultaSQL.append(VISTA_TABLA_DETALLE);
			consultaSQL.append(" t1 ");	
			consultaSQL.append(sqlWhere);
			consultaSQL.append(sqlLimit);
			System.out.println(consultaSQL.toString());
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),parametros.toArray(), new ProformaDetalleMapper());
		
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
	
	public RespuestaCompuesta guardarRegistro(Proforma proforma){
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL= new StringBuilder();
		KeyHolder claveGenerada = null;
		KeyHolder claveDetGenerada = null;
		int cantidadFilasAfectadas=0;
		try {
			System.out.println("entra a guardarRegistro");
			consultaSQL.append("INSERT INTO ");
			consultaSQL.append(NOMBRE_TABLA);
			consultaSQL.append(" (fk_cod_cliente, fk_canal_sector, canal_distribucion_sap, sector_sap, descripcion_canal_sector, nro_cotizacion, fecha_cotizacion, cod_interlocutor_sap, destinatario, moneda, monto, creado_el, creado_por, actualizado_por, actualizado_el, ip_creacion, ip_actualizacion) ");
//			consultaSQL.append(" VALUES (:fk_cod_cliente, (select id_canal_sector from sgo.canal_sector_sap where fk_cod_cliente=:cod_cli and sector_sap =:fk_canal_sector), :nro_cotizacion, :fecha_cotizacion, :destinatario, :moneda, :monto, :creado_el, :creado_por, :actualizado_por, :actualizado_el, :ip_creacion, :ip_actualizacion)");
			consultaSQL.append(" VALUES (:fk_cod_cliente, :fk_canal_sector, :canal_distribucion_sap, :sector_sap, :descripcion_canal_sector, :nro_cotizacion, :fecha_cotizacion, :cod_interlocutor_sap, :destinatario, :moneda, :monto, :creado_el, :creado_por, :actualizado_por, :actualizado_el, :ip_creacion, :ip_actualizacion)");
			MapSqlParameterSource listaParametros= new MapSqlParameterSource();
			listaParametros.addValue("fk_cod_cliente", proforma.getCliente().getId());
			listaParametros.addValue("fk_canal_sector", proforma.getCanalSector().getIdCanalSector());
			listaParametros.addValue("canal_distribucion_sap", proforma.getCanalSector().getCanalDistribucionSap());
			listaParametros.addValue("sector_sap", proforma.getCanalSector().getSectorSap());
			listaParametros.addValue("descripcion_canal_sector", proforma.getCanalSector().getDescripcionCanalSector());
			listaParametros.addValue("nro_cotizacion", proforma.getNroCotizacion());
			listaParametros.addValue("fecha_cotizacion", proforma.getFechaCotizacion());
			listaParametros.addValue("cod_interlocutor_sap", proforma.getInterlocutor().getCodInterlocutorSap());
			listaParametros.addValue("destinatario", proforma.getInterlocutor().getNomInterlocutorSap());
			listaParametros.addValue("moneda", proforma.getMoneda());
			listaParametros.addValue("monto", proforma.getMonto());
			
			listaParametros.addValue("creado_el", proforma.getCreadoEl());
			listaParametros.addValue("creado_por", proforma.getCreadoPor());			
			listaParametros.addValue("actualizado_el", proforma.getActualizadoEl());
			listaParametros.addValue("actualizado_por", proforma.getActualizadoPor());
			listaParametros.addValue("ip_creacion", proforma.getIpCreacion());
			listaParametros.addValue("ip_actualizacion", proforma.getIpActualizacion());
			SqlParameterSource namedParameters= listaParametros;
			/*Ejecuta la consulta y retorna las filas afectadas*/
			claveGenerada = new GeneratedKeyHolder();
			cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),namedParameters,claveGenerada,new String[] {NOMBRE_CAMPO_CLAVE});		
			if (cantidadFilasAfectadas>1){
				respuesta.error=Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
				respuesta.estado=false;
				return respuesta;
			} else {
				//detalle
				ProformaDetalle item = null;
				for(int i = 0; i<proforma.getItems().size();i++){
					item = proforma.getItems().get(i);
					consultaSQL.setLength(0);
					consultaSQL.append("INSERT INTO ");
					consultaSQL.append(NOMBRE_TABLA_DETALLE);
					consultaSQL.append(" (fk_id_proforma, fk_id_planta, fk_id_producto, posicion, volumen, precio, descuento, precio_neto, rodaje, isc, otros, acumulado, igv, fise, precio_descuento, precio_percepcion, importe_total) ");
					consultaSQL.append(" VALUES (:fk_id_proforma, :fk_id_planta, :fk_id_producto, :posicion,:volumen, :precio, :descuento, :precio_neto, :rodaje, :isc, :otros, :acumulado, :igv, :fise, :precio_descuento, :precio_percepcion, :importe_total)");
					
					listaParametros= new MapSqlParameterSource();
					listaParametros.addValue("fk_id_proforma", claveGenerada.getKey());
					listaParametros.addValue("fk_id_planta", item.getPlanta().getId());
					listaParametros.addValue("fk_id_producto", item.getProducto().getId());
					listaParametros.addValue("posicion", item.getPosicion());
					listaParametros.addValue("volumen", item.getVolumen());
					listaParametros.addValue("precio", item.getPrecio());
					listaParametros.addValue("descuento", item.getDescuento());
					listaParametros.addValue("precio_neto", item.getPrecioNeto());
					listaParametros.addValue("rodaje", item.getRodaje());
					listaParametros.addValue("isc", item.getIsc());
					listaParametros.addValue("otros", item.getOtros());
					listaParametros.addValue("acumulado", item.getAcumulado());
					listaParametros.addValue("igv", item.getIgv());
					listaParametros.addValue("fise", item.getFise());
					listaParametros.addValue("precio_descuento", item.getPrecioDescuento());
					listaParametros.addValue("precio_percepcion", item.getPrecioPercepcion());
					listaParametros.addValue("importe_total", item.getImporteTotal());
					
					/*Ejecuta la consulta y retorna las filas afectadas*/
					claveDetGenerada = new GeneratedKeyHolder();
					cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),listaParametros,claveDetGenerada,new String[] {NOMBRE_CAMPO_CLAVE_DETALLE});		
					if (cantidadFilasAfectadas>1){
						respuesta.error=Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
						respuesta.estado=false;
						return respuesta;
					}
				}
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
	
	public RespuestaCompuesta recuperarRegistrosCanalSector(ParametrosListar argumentosListar) {
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
			
			StringBuilder consultaSQL = new StringBuilder();
			consultaSQL.setLength(0);

			if(argumentosListar.getIdCanalSector() != 0){
				filtrosWhere.add(" t1.id_canal_sector =" +argumentosListar.getIdCanalSector());
			}
			filtrosWhere.add(" t1.fk_cod_cliente =" +argumentosListar.getIdCliente());
			
			if(!filtrosWhere.isEmpty()){
				consultaSQL.setLength(0);
				sqlWhere = "WHERE " + StringUtils.join(filtrosWhere, Constante.SQL_Y);
			}

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
			consultaSQL.append("t1.actualizado_el,");
			consultaSQL.append("t1.ip_creacion,");
			consultaSQL.append("t1.ip_actualizacion");
			consultaSQL.append(" FROM ");
			consultaSQL.append(TABLA_CANAL_SECTOR);
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
	public RespuestaCompuesta recuperarRegistrosDatosInterlocutor(ParametrosListar argumentosListar) {
		String sqlLimit = "";
		String sqlOrderBy="";
		List<String> filtrosWhere= new ArrayList<String>();
		String sqlWhere="";
		int totalRegistros = 0, 
		totalEncontrados = 0;
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		Contenido<DatosInterlocutor> contenido = new Contenido<DatosInterlocutor>();
		List<DatosInterlocutor> listaRegistros = new ArrayList<DatosInterlocutor>();
		List<Object> parametros = new ArrayList<Object>();
		try {
			
			StringBuilder consultaSQL = new StringBuilder();
			consultaSQL.setLength(0);

			if(argumentosListar.getIdInterlocutor() != 0){
				filtrosWhere.add(" t1.id_datos_inter =" +argumentosListar.getIdInterlocutor());
			} else {
				filtrosWhere.add(" t1.fk_cod_cliente =" +argumentosListar.getIdCliente());
				filtrosWhere.add(" t1.canal_distribucion_sap ='" +argumentosListar.getIdCanalSap()+"'");
				filtrosWhere.add(" t1.sector_sap ='" +argumentosListar.getIdSectorSap()+"'");
			}
			if(!filtrosWhere.isEmpty()){
				consultaSQL.setLength(0);
				sqlWhere = "WHERE " + StringUtils.join(filtrosWhere, Constante.SQL_Y);
			}

			consultaSQL.setLength(0);
			consultaSQL.append("SELECT ");
			consultaSQL.append("t1.id_datos_inter,");
			consultaSQL.append("t1.cod_clientesap,");
			consultaSQL.append("t1.fk_cod_cliente,");
			consultaSQL.append("t1.org_venta_sap,");
			consultaSQL.append("t1.canal_distribucion_sap,");
			consultaSQL.append("t1.sector_sap,");
			consultaSQL.append("t1.fun_interlocutor_sap,");
			consultaSQL.append("t1.cod_interlocutor_sap,");
			consultaSQL.append("t1.nom_interlocutor_sap,");
			consultaSQL.append("t1.dir_interlocutor_sap,");
			consultaSQL.append("t1.creado_el,");
			consultaSQL.append("t1.creado_por,");
			consultaSQL.append("t1.actualizado_por,");
			consultaSQL.append("t1.actualizado_el,");
			consultaSQL.append("t1.ip_creacion,");
			consultaSQL.append("t1.ip_actualizacion");
			consultaSQL.append(" FROM ");
			consultaSQL.append(TABLA_DESTINATARIO1);
			consultaSQL.append(" t1 ");	
			consultaSQL.append(sqlWhere);
			consultaSQL.append(sqlOrderBy);
			consultaSQL.append(sqlLimit);
			System.out.println(consultaSQL.toString());
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),parametros.toArray(), new DatosInterlocutorMapper());
		
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
	
	public RespuestaCompuesta recuperarPlantasHabilitadas(ParametrosListar argumentosListar) {
		String sqlLimit = "";
		String sqlOrderBy="";
		List<String> filtrosWhere= new ArrayList<String>();
		String sqlWhere="";
		int totalRegistros = 0, 
		totalEncontrados = 0;
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		Contenido<Planta> contenido = new Contenido<Planta>();
		List<Planta> listaRegistros = new ArrayList<Planta>();
		List<Object> parametros = new ArrayList<Object>();
		try {
			
			StringBuilder consultaSQL = new StringBuilder();
			consultaSQL.setLength(0);

			if(argumentosListar.getIdPlanta() != 0){
				filtrosWhere.add(" t1.id_planta ='" +argumentosListar.getIdPlanta()+"'");
			} else {
				filtrosWhere.add(" (t1.fk_cod_cliente =" +argumentosListar.getIdCliente()+" OR t1.fk_cod_cliente =0)");
				filtrosWhere.add(" t1.cod_canal_sap ='" +argumentosListar.getIdCanalSap()+"'");
				filtrosWhere.add(" (t1.cod_destinat_sap ='" +argumentosListar.getCodInterlocutorSap()+"' OR t1.cod_destinat_sap ='x')");
				filtrosWhere.add(" (t1.clave_ramo_sap ='" +argumentosListar.getClaveRamoSap()+"' OR t1.clave_ramo_sap ='x')");
	//			filtrosWhere.add(" t1.fk_id_planta =" +argumentosListar.getIdPlanta());
			}
			if(!filtrosWhere.isEmpty()){
				consultaSQL.setLength(0);
				sqlWhere = "WHERE " + StringUtils.join(filtrosWhere, Constante.SQL_Y);
			}

			consultaSQL.setLength(0);
			consultaSQL.append("SELECT distinct ");
			consultaSQL.append("t1.id_planta,");
			consultaSQL.append("t1.descripcion,");
//			consultaSQL.append("t1.fk_cod_cliente,");
			consultaSQL.append("t1.planta_cod_sap");
			consultaSQL.append(" FROM ");
//			consultaSQL.append(VISTA_PLANTA_HABILITADA);
			consultaSQL.append(VISTA_PRODUCTO_HABILITADO);
			consultaSQL.append(" t1 ");
			consultaSQL.append(sqlWhere);
			consultaSQL.append(sqlOrderBy);
			consultaSQL.append(sqlLimit);
			System.out.println(consultaSQL.toString());
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),parametros.toArray(), new PlantaHabMapper());
		
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
	
	public RespuestaCompuesta recuperarProductosHabilitados(ParametrosListar argumentosListar) {
		String sqlLimit = "";
		String sqlOrderBy="";
		List<String> filtrosWhere= new ArrayList<String>();
		String sqlWhere="";
		int totalRegistros = 0, 
		totalEncontrados = 0;
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		Contenido<Producto> contenido = new Contenido<Producto>();
		List<Producto> listaRegistros = new ArrayList<Producto>();
		List<Object> parametros = new ArrayList<Object>();
		try {
			
			StringBuilder consultaSQL = new StringBuilder();
			consultaSQL.setLength(0);

			if(argumentosListar.getFiltroProducto() != 0){
				filtrosWhere.add(" t1.id_producto =" +argumentosListar.getFiltroProducto());
			}else{
			filtrosWhere.add(" (t1.fk_cod_cliente =" +argumentosListar.getIdCliente()+" OR t1.fk_cod_cliente =0)");
//			filtrosWhere.add(" t1.centro_sap ='" +argumentosListar.getIdCentroSap()+"'");
			filtrosWhere.add(" t1.cod_canal_sap ='" +argumentosListar.getIdCanalSap()+"'");
			filtrosWhere.add(" (t1.cod_destinat_sap ='" +argumentosListar.getCodInterlocutorSap()+"' OR t1.cod_destinat_sap ='x')");
			filtrosWhere.add(" (t1.clave_ramo_sap ='" +argumentosListar.getClaveRamoSap()+"' OR t1.clave_ramo_sap ='x')");
			filtrosWhere.add(" t1.fk_id_planta =" +argumentosListar.getIdPlanta());
			}
			if(!filtrosWhere.isEmpty()){
				consultaSQL.setLength(0);
				sqlWhere = "WHERE " + StringUtils.join(filtrosWhere, Constante.SQL_Y);
			}

			consultaSQL.setLength(0);
			consultaSQL.append("SELECT distinct ");
			consultaSQL.append("t1.id_producto,");
			consultaSQL.append("t1.nombre,");
			consultaSQL.append("t1.unidad_medida,");
			consultaSQL.append("t1.cod_material_sap");
			consultaSQL.append(" FROM ");
			consultaSQL.append(VISTA_PRODUCTO_HABILITADO);
			consultaSQL.append(" t1 ");
			consultaSQL.append(sqlWhere);
			consultaSQL.append(sqlOrderBy);
			consultaSQL.append(sqlLimit);
			System.out.println(consultaSQL.toString());
			listaRegistros = jdbcTemplate.query(consultaSQL.toString(),parametros.toArray(), new ProductoHabMapper());
		
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
	
	public Cliente getClienteAsignado(int ID){
		List<Cliente> lUsuarios=new ArrayList<Cliente>();
		StringBuilder sqlSB= new StringBuilder();
		try {
			sqlSB.append("SELECT t1.id_usuario, t1.nombre,");
			sqlSB.append("t1.id_cliente,u5.razon_social,u5.razon_social_sap, u5.ruc, u5.rama_sap, u5.codigo_sap");
			sqlSB.append(" FROM seguridad.usuario t1 LEFT JOIN sgo.cliente u5 ON t1.id_cliente = u5.id_cliente");		
			sqlSB.append(" WHERE id_usuario=? and t1.id_cliente != 0");
			lUsuarios= jdbcTemplate.query(sqlSB.toString(),new Object[] {ID},new ClienteAsignadoMapper());

			if(!lUsuarios.isEmpty() && lUsuarios.size() == 1){
				return lUsuarios.get(0);
			}
		} catch (DataAccessException daEx) {
			Utilidades.gestionaWarning(daEx, "ProformaDao", "getClienteAsignado", sqlSB.toString());
		}
		return null;
	}
}

