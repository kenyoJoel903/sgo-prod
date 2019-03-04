package sgo.datos;

import java.sql.Types;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sgo.entidad.CanalSector;
import sgo.entidad.Cliente;
import sgo.entidad.Contenido;
import sgo.entidad.ParametrosListar;
import sgo.entidad.RespuestaCompuesta;
import sgo.utilidades.Constante;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_Response;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_ResponseClieDestMate_OutItem;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_ResponseClieDestRamoCentMate_OutItem;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_ResponseClieMate_OutItem;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_ResponseClieRamoMate_OutItem;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_ResponseDatosInterlocutor_OutItem;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem;

@Repository
public class DataMaestraClienteDao {
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	public static final String NOMBRE_TABLA = Constante.ESQUEMA_APLICACION + "cliente";
	public final static String NOMBRE_CAMPO_CLAVE = "id_cliente";
	public final static String NOMBRE_CAMPO_FILTRO = "razon_social";
	public final static String NOMBRE_CAMPO_ORDENAMIENTO_DEFECTO = "razon_social";	
	public final static String NOMBRE_CAMPO_FILTRO_ESTADO = "estado";
	
	private static final String TABLA_CANAL_SECTOR_HOM = Constante.ESQUEMA_APLICACION + "canal_sector_homologacion";
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public DataSource getDataSource(){
		return this.jdbcTemplate.getDataSource();
	}
	
	/**
	 * @param cliente
	 * @param response
	 * @return
	 */
	public RespuestaCompuesta guardarRegistro(Cliente cliente,
			DT_Data_Maestra_Cliente_Proforma_Response response) {
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		StringBuilder consultaSQL = new StringBuilder();
		KeyHolder claveGenerada = null;
		int cantidadFilasAfectadas = 0;
		
		if (response == null) {
			respuesta.estado = true;
			return respuesta;
		}
		try {
			DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem item0 = null;
			MapSqlParameterSource listaParametros;
			int i = 0;
			//canal_sector_sap
			eliminarSincronizado(cliente, "canal_sector_sap");
			eliminarSincronizado(cliente, "datos_interlocutor_sap");
			eliminarSincronizado(cliente, "clie_dest_ramo_cent_mate_sap");
			eliminarSincronizado(cliente, "clie_dest_mate_sap");
			eliminarSincronizado(cliente, "clie_ramo_mate_sap");
			eliminarSincronizado(cliente, "clie_mate_sap");
			eliminarSincronizadoMateriales();
			//insercion
			consultaSQL = new StringBuilder();
			for(i = 0; i < response.getDatosAreaVenta_Out().length;i++){
				item0 = response.getDatosAreaVenta_Out()[i];
				System.out.println("entra a guardarRegistro canal_sector_sap");
				consultaSQL.setLength(0);
				consultaSQL.append("INSERT INTO ");
				consultaSQL.append(" sgo.canal_sector_sap ");
				consultaSQL.append(" (cod_clientesap, fk_cod_cliente, org_venta_sap, des_org_venta_sap, canal_distribucion_sap, desc_canal_distribucion_sap, descripcion_canal_sector, sector_sap, desc_sector_sap, creado_el, creado_por, actualizado_por, actualizado_el, ip_creacion, ip_actualizacion) ");
				consultaSQL.append(" VALUES (:cod_clientesap, :fk_cod_cliente, :org_venta_sap, :des_org_venta_sap, :canal_distribucion_sap, :desc_canal_distribucion_sap, :descripcion_canal_sector, :sector_sap, :desc_sector_sap, :creado_el, :creado_por, :actualizado_por, :actualizado_el, :ip_creacion, :ip_actualizacion)");
				
				listaParametros = new MapSqlParameterSource();
				listaParametros.addValue("cod_clientesap", item0.getCodCliente());
				listaParametros.addValue("fk_cod_cliente", cliente.getId());
				listaParametros.addValue("org_venta_sap", item0.getOrgVenta());
				listaParametros.addValue("des_org_venta_sap", item0.getDesOrgVenta());
				listaParametros.addValue("canal_distribucion_sap", item0.getCanalDistrib());
				listaParametros.addValue("desc_canal_distribucion_sap", item0.getDesCanalDistrib());
				listaParametros.addValue("descripcion_canal_sector", obtenerDescripcion(cliente,i));
				listaParametros.addValue("sector_sap", item0.getSector());
				listaParametros.addValue("desc_sector_sap", item0.getDesSector());
				listaParametros.addValue("creado_el", cliente.getCreadoEl());
				listaParametros.addValue("creado_por", cliente.getCreadoPor());
				listaParametros.addValue("actualizado_por", cliente.getActualizadoPor());
				listaParametros.addValue("actualizado_el", cliente.getActualizadoEl());
				listaParametros.addValue("ip_creacion", cliente.getIpCreacion());
				listaParametros.addValue("ip_actualizacion", cliente.getIpActualizacion());
	//			SqlParameterSource namedParameters= listaParametros;
				/*Ejecuta la consulta y retorna las filas afectadas*/
				claveGenerada = new GeneratedKeyHolder();
				cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),listaParametros,claveGenerada,new String[] {"id_canal_sector"});
				if (cantidadFilasAfectadas>1){
					respuesta.error=Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
					respuesta.estado=false;
					return respuesta;
				}
			}
			
			//datos_interlocutor_sap
			consultaSQL = new StringBuilder();
			DT_Data_Maestra_Cliente_Proforma_ResponseDatosInterlocutor_OutItem inter = null;
			for(i = 0; i < response.getDatosInterlocutor_Out().length;i++){
				inter = response.getDatosInterlocutor_Out()[i];
				consultaSQL.setLength(0);
				System.out.println("entra a guardarRegistro");
				consultaSQL.append("INSERT INTO ");
				consultaSQL.append(" sgo.datos_interlocutor_sap ");
				consultaSQL.append(" (cod_clientesap, fk_cod_cliente, org_venta_sap, canal_distribucion_sap, sector_sap, fun_interlocutor_sap, cod_interlocutor_sap, dir_interlocutor_sap, nom_interlocutor_sap, creado_el, creado_por, actualizado_por, actualizado_el, ip_creacion, ip_actualizacion) ");
				consultaSQL.append(" VALUES (:cod_clientesap, :fk_cod_cliente, :org_venta_sap, :canal_distribucion_sap, :sector_sap, :fun_interlocutor_sap, :cod_interlocutor_sap, :dir_interlocutor_sap, :nom_interlocutor_sap, :creado_el, :creado_por, :actualizado_por, :actualizado_el, :ip_creacion, :ip_actualizacion)");
				
				listaParametros = new MapSqlParameterSource();
				listaParametros.addValue("cod_clientesap", inter.getCodCliente());
				listaParametros.addValue("fk_cod_cliente", cliente.getId());
				listaParametros.addValue("org_venta_sap", inter.getOrgVenta());
				listaParametros.addValue("canal_distribucion_sap", inter.getCanalDistrib());
				listaParametros.addValue("sector_sap", inter.getSector());
				listaParametros.addValue("fun_interlocutor_sap", inter.getFunInterlocutor());
				listaParametros.addValue("cod_interlocutor_sap", inter.getCodInterlocutor());
				listaParametros.addValue("nom_interlocutor_sap", inter.getNomInterlocutor());
				listaParametros.addValue("dir_interlocutor_sap", inter.getDirInterlocutor());
				listaParametros.addValue("creado_el", cliente.getCreadoEl());
				listaParametros.addValue("creado_por", cliente.getCreadoPor());
				listaParametros.addValue("actualizado_por", cliente.getActualizadoPor());
				listaParametros.addValue("actualizado_el", cliente.getActualizadoEl());
				listaParametros.addValue("ip_creacion", cliente.getIpCreacion());
				listaParametros.addValue("ip_actualizacion", cliente.getIpActualizacion());
	//			SqlParameterSource namedParameters= listaParametros;
				/*Ejecuta la consulta y retorna las filas afectadas*/
				claveGenerada = new GeneratedKeyHolder();
				cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),listaParametros,claveGenerada,new String[] {"id_datos_inter"});		
				if (cantidadFilasAfectadas>1){
					respuesta.error=Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
					respuesta.estado=false;
					return respuesta;
				}
			}
			//clie_dest_ramo_cent_mate_sap
			consultaSQL = new StringBuilder();
			DT_Data_Maestra_Cliente_Proforma_ResponseClieDestRamoCentMate_OutItem item;
			for(i = 0; i < response.getClieDestRamoCentMate_Out().length;i++){
				item = response.getClieDestRamoCentMate_Out()[i];
				consultaSQL.setLength(0);
				System.out.println("entra a guardarRegistro clie_dest_ramo_cent_mate_sap");
				consultaSQL.append("INSERT INTO ");
				consultaSQL.append(" sgo.clie_dest_ramo_cent_mate_sap ");
				consultaSQL.append(" (cod_clientesap, fk_cod_cliente, cod_destinat_sap, clave_ramo_sap, centro_sap, cod_material_sap, des_material_sap, fec_validez_fin_sap, fec_validez_ini_sap, creado_el, creado_por, actualizado_por, actualizado_el, ip_creacion, ip_actualizacion) ");
				consultaSQL.append(" VALUES (:cod_clientesap, :fk_cod_cliente, :cod_destinat_sap, :clave_ramo_sap, :centro_sap, :cod_material_sap, :des_material_sap, :fec_validez_fin_sap, :fec_validez_ini_sap, :creado_el, :creado_por, :actualizado_por, :actualizado_el, :ip_creacion, :ip_actualizacion)");
				//falta ponerlos al addvalue
				listaParametros = new MapSqlParameterSource();
				listaParametros.addValue("cod_clientesap", item.getCodCliente());
		        listaParametros.addValue("fk_cod_cliente", cliente.getId());
		        listaParametros.addValue("cod_destinat_sap", item.getCodDestinat());
		        listaParametros.addValue("clave_ramo_sap", item.getClaveRamo());
		        listaParametros.addValue("centro_sap", item.getCentro());
		        listaParametros.addValue("cod_material_sap", item.getCodMaterial());
		        listaParametros.addValue("des_material_sap", item.getDesMaterial());
		        listaParametros.addValue("fec_validez_fin_sap", item.getFecValidez_Fin());
		        listaParametros.addValue("fec_validez_ini_sap", item.getFecValidez_Ini());
		        listaParametros.addValue("creado_el", cliente.getCreadoEl());
		        listaParametros.addValue("creado_por", cliente.getCreadoPor());
		        listaParametros.addValue("actualizado_por", cliente.getActualizadoPor());
		        listaParametros.addValue("actualizado_el", cliente.getActualizadoEl());
		        listaParametros.addValue("ip_creacion", cliente.getIpCreacion());
		        listaParametros.addValue("ip_actualizacion", cliente.getIpActualizacion());
				/*Ejecuta */
				claveGenerada = new GeneratedKeyHolder();
				cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),listaParametros,claveGenerada,new String[] {"id_cdrcm"});		
				if (cantidadFilasAfectadas>1){
					respuesta.error=Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
					respuesta.estado=false;
					return respuesta;
				}
			}
			
			//clie_dest_mate_sap
			consultaSQL = new StringBuilder();
			DT_Data_Maestra_Cliente_Proforma_ResponseClieDestMate_OutItem item2;
			for(i = 0; i < response.getClieDestMate_Out().length;i++){
				item2 = response.getClieDestMate_Out()[i];
				consultaSQL.setLength(0);
				System.out.println("entra a guardarRegistro clie_dest_mate_sap");
				consultaSQL.append("INSERT INTO ");
				consultaSQL.append(" sgo.clie_dest_mate_sap ");
				consultaSQL.append(" (cod_clientesap, fk_cod_cliente, cod_destinat_sap, cod_material_sap, des_material_sap, fec_validez_fin_sap, fec_validez_ini_sap, creado_el, creado_por, actualizado_por, actualizado_el, ip_creacion, ip_actualizacion) ");
				consultaSQL.append(" VALUES (:cod_clientesap, :fk_cod_cliente, :cod_destinat_sap, :cod_material_sap, :des_material_sap, :fec_validez_fin_sap, :fec_validez_ini_sap, :creado_el, :creado_por, :actualizado_por, :actualizado_el, :ip_creacion, :ip_actualizacion)");
				//falta ponerlos al addvalue
				listaParametros = new MapSqlParameterSource();
				listaParametros.addValue("cod_clientesap", item2.getCodCliente());
		        listaParametros.addValue("fk_cod_cliente", cliente.getId());
		        listaParametros.addValue("cod_destinat_sap", item2.getCodDestinat());
		        listaParametros.addValue("cod_material_sap", item2.getCodMaterial());
		        listaParametros.addValue("des_material_sap", item2.getDesMaterial());
		        listaParametros.addValue("fec_validez_fin_sap", item2.getFecValidez_Fin(),Types.TIMESTAMP);
		        listaParametros.addValue("fec_validez_ini_sap", item2.getFecValidez_Ini(),Types.TIMESTAMP);
		        listaParametros.addValue("creado_el", cliente.getCreadoEl());
		        listaParametros.addValue("creado_por", cliente.getCreadoPor());
		        listaParametros.addValue("actualizado_por", cliente.getActualizadoPor());
		        listaParametros.addValue("actualizado_el", cliente.getActualizadoEl());
		        listaParametros.addValue("ip_creacion", cliente.getIpCreacion());
		        listaParametros.addValue("ip_actualizacion", cliente.getIpActualizacion());
				/*Ejecuta la consulta y retorna las filas afectadas*/
				claveGenerada = new GeneratedKeyHolder();
				cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),listaParametros,claveGenerada,new String[] {"id_cliedestmate"});		
				if (cantidadFilasAfectadas>1){
					respuesta.error=Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
					respuesta.estado=false;
					return respuesta;
				}
			}
			
			//clie_ramo_mate_sap
			consultaSQL = new StringBuilder();
			DT_Data_Maestra_Cliente_Proforma_ResponseClieRamoMate_OutItem item3;
			for(i = 0; i < response.getClieRamoMate_Out().length;i++){
				item3 = response.getClieRamoMate_Out()[i];
				consultaSQL.setLength(0);
				System.out.println("entra a guardarRegistro clie_ramo_mate_sap");
				consultaSQL.append("INSERT INTO ");
				consultaSQL.append(" sgo.clie_ramo_mate_sap ");
				consultaSQL.append(" (cod_clientesap, fk_cod_cliente, clave_ramo_sap, cod_material_sap, des_material_sap, fec_validez_fin_sap, fec_validez_ini_sap, creado_el, creado_por, actualizado_por, actualizado_el, ip_creacion, ip_actualizacion) ");
				consultaSQL.append(" VALUES (:cod_clientesap, :fk_cod_cliente, :clave_ramo_sap, :cod_material_sap, :des_material_sap, :fec_validez_fin_sap, :fec_validez_ini_sap, :creado_el, :creado_por, :actualizado_por, :actualizado_el, :ip_creacion, :ip_actualizacion)");
				//falta ponerlos al addvalue
				listaParametros = new MapSqlParameterSource();
				listaParametros.addValue("cod_clientesap", item3.getCodCliente());
		        listaParametros.addValue("fk_cod_cliente", cliente.getId());
		        listaParametros.addValue("clave_ramo_sap", item3.getClaveRamo());
		        listaParametros.addValue("cod_material_sap", item3.getCodMaterial());
		        listaParametros.addValue("des_material_sap", item3.getDesMaterial());
		        listaParametros.addValue("fec_validez_fin_sap", item3.getFecValidez_Fin(),Types.TIMESTAMP);
		        listaParametros.addValue("fec_validez_ini_sap", item3.getFecValidez_Ini(),Types.TIMESTAMP);
		        listaParametros.addValue("creado_el", cliente.getCreadoEl());
		        listaParametros.addValue("creado_por", cliente.getCreadoPor());
		        listaParametros.addValue("actualizado_por", cliente.getActualizadoPor());
		        listaParametros.addValue("actualizado_el", cliente.getActualizadoEl());
		        listaParametros.addValue("ip_creacion", cliente.getIpCreacion());
		        listaParametros.addValue("ip_actualizacion", cliente.getIpActualizacion());
				/*Ejecuta la consulta y retorna las filas afectadas*/
				claveGenerada = new GeneratedKeyHolder();
				cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),listaParametros,claveGenerada,new String[] {"id_clieramomate"});		
				if (cantidadFilasAfectadas>1){
					respuesta.error=Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
					respuesta.estado=false;
					return respuesta;
				}
			}
			
			//clie_mate_sap
			consultaSQL = new StringBuilder();
			DT_Data_Maestra_Cliente_Proforma_ResponseClieMate_OutItem item4;
			for(i = 0; i < response.getClieMate_Out().length;i++){
				item4 = response.getClieMate_Out()[i];
				consultaSQL.setLength(0);
				System.out.println("entra a guardarRegistro clie_mate_sap");
				consultaSQL.append("INSERT INTO ");
				consultaSQL.append(" sgo.clie_mate_sap ");
				consultaSQL.append(" (cod_clientesap, fk_cod_cliente, cod_material_sap, des_material_sap, fec_validez_fin_sap, fec_validez_ini_sap, creado_el, creado_por, actualizado_por, actualizado_el, ip_creacion, ip_actualizacion) ");
				consultaSQL.append(" VALUES (:cod_clientesap, :fk_cod_cliente, :cod_material_sap, :des_material_sap, :fec_validez_fin_sap, :fec_validez_ini_sap, :creado_el, :creado_por, :actualizado_por, :actualizado_el, :ip_creacion, :ip_actualizacion)");
				//falta ponerlos al addvalue
				listaParametros = new MapSqlParameterSource();
				listaParametros.addValue("cod_clientesap", item4.getCodCliente());
		        listaParametros.addValue("fk_cod_cliente", cliente.getId());
		        listaParametros.addValue("cod_material_sap", item4.getCodMaterial());
		        listaParametros.addValue("des_material_sap", item4.getDesMaterial());
		        listaParametros.addValue("fec_validez_fin_sap", item4.getFecValidez_Fin(),Types.TIMESTAMP);
		        listaParametros.addValue("fec_validez_ini_sap", item4.getFecValidez_Ini(),Types.TIMESTAMP);
		        listaParametros.addValue("creado_el", cliente.getCreadoEl());
		        listaParametros.addValue("creado_por", cliente.getCreadoPor());
		        listaParametros.addValue("actualizado_por", cliente.getActualizadoPor());
		        listaParametros.addValue("actualizado_el", cliente.getActualizadoEl());
		        listaParametros.addValue("ip_creacion", cliente.getIpCreacion());
		        listaParametros.addValue("ip_actualizacion", cliente.getIpActualizacion());
				/*Ejecuta la consulta y retorna las filas afectadas*/
				claveGenerada = new GeneratedKeyHolder();
				cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),listaParametros,claveGenerada,new String[] {"id_cliemate"});		
				if (cantidadFilasAfectadas>1){
					respuesta.error=Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
					respuesta.estado=false;
					return respuesta;
				}
			}
			
			//mate_sap
			consultaSQL = new StringBuilder();
			DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem item5;
			if(response.getMaterial_Out() != null)
			for(i = 0; i < response.getMaterial_Out().length;i++){
				item5 = response.getMaterial_Out()[i];
				consultaSQL.setLength(0);
				System.out.println("entra a guardarRegistro material_sap");
				consultaSQL.append("INSERT INTO ");
				consultaSQL.append(" sgo.material_sap ");
				consultaSQL.append(" (cod_material_sap, des_material_sap, fec_validez_fin_sap, fec_validez_ini_sap, creado_el, creado_por, actualizado_por, actualizado_el, ip_creacion, ip_actualizacion) ");
				consultaSQL.append(" VALUES (:cod_material_sap, :des_material_sap, :fec_validez_fin_sap, :fec_validez_ini_sap, :creado_el, :creado_por, :actualizado_por, :actualizado_el, :ip_creacion, :ip_actualizacion)");
				//falta ponerlos al addvalue
				listaParametros = new MapSqlParameterSource();
//				listaParametros.addValue("cod_clientesap", item5.getCodCliente());
//		        listaParametros.addValue("fk_cod_cliente", cliente.getId());
		        listaParametros.addValue("cod_material_sap", item5.getCodMaterial());
		        listaParametros.addValue("des_material_sap", item5.getDesMaterial());
		        listaParametros.addValue("fec_validez_fin_sap", item5.getFecValidez_Fin(),Types.TIMESTAMP);
		        listaParametros.addValue("fec_validez_ini_sap", item5.getFecValidez_Ini(),Types.TIMESTAMP);
		        listaParametros.addValue("creado_el", cliente.getCreadoEl());
		        listaParametros.addValue("creado_por", cliente.getCreadoPor());
		        listaParametros.addValue("actualizado_por", cliente.getActualizadoPor());
		        listaParametros.addValue("actualizado_el", cliente.getActualizadoEl());
		        listaParametros.addValue("ip_creacion", cliente.getIpCreacion());
		        listaParametros.addValue("ip_actualizacion", cliente.getIpActualizacion());
				/*Ejecuta la consulta y retorna las filas afectadas*/
				claveGenerada = new GeneratedKeyHolder();
				cantidadFilasAfectadas= namedJdbcTemplate.update(consultaSQL.toString(),listaParametros,claveGenerada,new String[] {"id_material"});		
				if (cantidadFilasAfectadas>1){
					respuesta.error=Constante.EXCEPCION_CANTIDAD_REGISTROS_INCORRECTA;
					respuesta.estado=false;
					return respuesta;
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

	private void eliminarSincronizado(Cliente cliente, String tabla) {
		MapSqlParameterSource listaParametros;
		StringBuilder sql = new StringBuilder();
		//eliminacion
		sql.append("delete from ");
		sql.append(Constante.ESQUEMA_APLICACION);
		sql.append(tabla);
		sql.append(" where fk_cod_cliente = :id;");
		listaParametros = new MapSqlParameterSource();
		listaParametros.addValue("id", cliente.getId());
		namedJdbcTemplate.update(sql.toString(),listaParametros);
	}
	private void eliminarSincronizadoMateriales() {
		MapSqlParameterSource listaParametros;
		StringBuilder sql = new StringBuilder();
		//eliminacion
		sql.append("delete from ");
		sql.append(Constante.ESQUEMA_APLICACION);
		sql.append("material_sap");
		listaParametros = new MapSqlParameterSource();
		namedJdbcTemplate.update(sql.toString(),listaParametros);
	}
	
	private Object obtenerDescripcion(Cliente cliente, int i) {
		String desc = cliente.getListaDescripciones().get(i).getDescripcionCanalSector();
		if(desc != null && !desc.equals("null")){
			return desc;
		}
		return "";
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
	
	public RespuestaCompuesta configurarCanalSecRespComp(
			DT_Data_Maestra_Cliente_Proforma_Response response, Cliente eCliente) {
		List<CanalSector> lista = new ArrayList<CanalSector>();
		Contenido<CanalSector> contenido = new Contenido<CanalSector>();
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		CanalSector canalSector = null;
		DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem item0 = null;
		
		List<CanalSector> maestrosCS = this.obtenerMaestroCanalSector(response.getDatosAreaVenta_Out());
		// canal_sector_sap
		for (int i = 0; i < response.getDatosAreaVenta_Out().length; i++) {
			item0 = response.getDatosAreaVenta_Out()[i];
			canalSector = new CanalSector();
			canalSector.setIdCanalSector(i);//id temporal para ubicarlo cuando se le de guardar
			canalSector.setCodClientesap(item0.getCodCliente());
			canalSector.setFkCodCliente(eCliente.getId());
			canalSector.setOrgVentaSap(item0.getOrgVenta());
			canalSector.setDesOrgVentaSap(item0.getDesOrgVenta());
			canalSector.setCanalDistribucionSap(item0.getCanalDistrib());
			canalSector.setDescCanalDistribucionSap(item0.getDesCanalDistrib());
			
			canalSector.setSectorSap(item0.getSector());
			canalSector.setDescSectorSap(item0.getDesSector());
			
			canalSector.setDescripcionCanalSector(this.obtenerDescripcion(canalSector, maestrosCS));
			// Parametros de auditoria
			canalSector.setCreadoPor(eCliente.getCreadoPor());
			canalSector.setCreadoEl(eCliente.getCreadoEl());
			canalSector.setActualizadoPor(eCliente.getActualizadoPor());
			canalSector.setActualizadoEl(eCliente.getActualizadoEl());

			lista.add(canalSector);
		}
		contenido.carga = lista;
		respuesta.mensaje = "OK";
		respuesta.estado = true;
		respuesta.contenido = contenido;
		return respuesta;
	}

	/**Obtiene la desciprcion: se obtiene de la tabla maestra canal_sector_homologacion, en caso no se ecnuetre configurada, la descripcion sera la concatenacion de los datos sincronizados del cliente
	 * @param canalSector
	 * @param maestrosCS
	 * @return
	 */
	private String obtenerDescripcion(CanalSector canalSector,
			List<CanalSector> maestrosCS) {
		String desc = "";
		for (int j = 0; j < maestrosCS.size(); j++) {
			
			if (maestrosCS.get(j).getCanalDistribucionSap()
					.equals(canalSector.getCanalDistribucionSap())
					&& maestrosCS.get(j).getSectorSap()
							.equals(canalSector.getSectorSap())) {
				desc = maestrosCS.get(j).getDescripcionCanalSector();
				break;
			}
		}
		if (StringUtils.isBlank(desc)) {
			desc = canalSector.getDescCanalDistribucionSap() + " - "
					+ canalSector.getDescSectorSap();
		}
		return desc;
	}
	
	private List<CanalSector> obtenerMaestroCanalSector(
			DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem[] datosAreaVenta_Out) {
	List<String> filtrosWhere= new ArrayList<String>();
	String sqlWhere="";
	List<CanalSector> listaRegistros = new ArrayList<CanalSector>();
	List<Object> parametros = new ArrayList<Object>();
	try {
		
		StringBuilder consultaSQL = new StringBuilder();
//		if(argumentosListar.getFiltroEstado() != Constante.FILTRO_TODOS){
//			filtrosWhere.add(" t1.canal_distribucion_sap in(" + argumentosListar.getFiltroEstado());
//		}
		
//		if(!filtrosWhere.isEmpty()){
//			sqlWhere = "WHERE " + StringUtils.join(filtrosWhere, Constante.SQL_Y);
//		}

		consultaSQL.setLength(0);
		consultaSQL.append("SELECT ");
		consultaSQL.append("t1.canal_distribucion_sap,t1.desc_canal_distribucion_sap,");
		consultaSQL.append("t1.sector_sap,t1.desc_sector_sap,t1.descripcion_canal_sector ");
		consultaSQL.append(" FROM ");
		consultaSQL.append(TABLA_CANAL_SECTOR_HOM);
		consultaSQL.append(" t1 ");	
		consultaSQL.append(sqlWhere);
		consultaSQL.append(" order by t1.canal_distribucion_sap ");
		System.out.println(consultaSQL.toString());
		listaRegistros = jdbcTemplate.query(consultaSQL.toString(),parametros.toArray(), new CanalSectorMapperSimple());
	
	} catch (DataAccessException excepcionAccesoDatos) {
		excepcionAccesoDatos.printStackTrace();
	} catch (Exception excepcionGenerica) {
		excepcionGenerica.printStackTrace();
	}
	return listaRegistros;
}

}


