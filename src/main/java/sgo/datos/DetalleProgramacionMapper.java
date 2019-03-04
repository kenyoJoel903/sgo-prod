package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.Cisterna;
import sgo.entidad.Conductor;
import sgo.entidad.DetalleProgramacion;
import sgo.entidad.DiaOperativo;
import sgo.entidad.Operacion;
import sgo.entidad.Planta;
import sgo.entidad.Producto;
import sgo.entidad.Programacion;
import sgo.entidad.Tracto;
import sgo.entidad.Transportista;
import sgo.utilidades.Utilidades;
public class DetalleProgramacionMapper implements RowMapper<DetalleProgramacion>{
	public DetalleProgramacion mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		DetalleProgramacion eDetalleProgramacion = null;
		Cisterna eCisterna = null;
		Producto eProducto = null;
		Conductor eConductor = null;
		Planta ePlanta = null;
		Programacion eProgramacion = null;
		Tracto eTracto = null;
		DiaOperativo eDiaOperativo = null;
		Operacion eOperacion = null;
		Transportista eTransportista=null;
				
		try {
			eDetalleProgramacion = new DetalleProgramacion();
			eDetalleProgramacion.setId(rs.getInt("id_dprogramacion"));
			eDetalleProgramacion.setIdProgramacion(rs.getInt("id_programacion"));
			eDetalleProgramacion.setIdCisterna(rs.getInt("id_cisterna"));
			eDetalleProgramacion.setIdProducto(rs.getInt("id_producto"));
			eDetalleProgramacion.setIdConductor(rs.getInt("id_conductor"));
			eDetalleProgramacion.setIdPlanta(rs.getInt("id_planta"));
			eDetalleProgramacion.setOrdenCompra(Utilidades.cleanXSS(rs.getString("orden_compra")));
			eDetalleProgramacion.setCodigoScop(Utilidades.cleanXSS(rs.getString("codigo_scop")));
			eDetalleProgramacion.setCodigoSapPedido(Utilidades.cleanXSS(rs.getString("codigo_sap_pedido")));
			eDetalleProgramacion.setDescripcionPlantaDespacho(Utilidades.cleanXSS(rs.getString("descripcion_planta_despacho")));			
			
			// Inicio Atencion Ticket 9000002608
			eDetalleProgramacion.setRazonSocial(rs.getString("razon_social"));
			eDetalleProgramacion.setNumeroCompartimiento(rs.getInt("numero_compartimento"));
			eDetalleProgramacion.setCapacidadVolumetrica(rs.getInt("capacidad_volumetrica"));
			// Fin Atencion Ticket 9000002608
			
			//Agregado por req 9000002841====================
			String tarCub = rs.getString("tc_det");
			
			if (tarCub == null){
				eDetalleProgramacion.setTarjetaCub("");
			}else{
				eDetalleProgramacion.setTarjetaCub(tarCub);
			}
			
			Date fechaInicio = rs.getDate("fecha_inicio_vigencia_tarjeta_cubicacion");
			eDetalleProgramacion.setFechaInicioVigTC(fechaInicio);
			
			if(fechaInicio == null){
				eDetalleProgramacion.setStrFechaInicioVigTC("");
			}else{
				eDetalleProgramacion.setStrFechaInicioVigTC(new SimpleDateFormat("dd/MM/yyyy").format(fechaInicio));
			}
			
			Date fechaFin = rs.getDate("fecha_fin_vigencia_tarjeta_cubicacion");
			eDetalleProgramacion.setFechaFinVigTC(fechaFin);
			
			if(fechaFin == null){
				eDetalleProgramacion.setStrFechaFinVigTC("");
			}else{
				eDetalleProgramacion.setStrFechaFinVigTC(new SimpleDateFormat("dd/MM/yyyy").format(fechaFin));
			}
			//Agregado por req 9000002841====================
			
			eCisterna = new Cisterna();
			eCisterna.setId(rs.getInt("id_cisterna"));
			eCisterna.setPlaca(Utilidades.cleanXSS(rs.getString("placa")));
			eCisterna.setCantidadCompartimentos(rs.getInt("cantidad_compartimentos"));
			eCisterna.setTarjetaCubicacion(Utilidades.cleanXSS(rs.getString("tarjeta_cubicacion")));
			eCisterna.setIdTracto(rs.getInt("id_tracto"));
			
			eTracto = new Tracto();
			eTracto.setId(rs.getInt("id_tracto"));
			eTracto.setPlaca(Utilidades.cleanXSS(rs.getString("placa_tracto")));
			eCisterna.setTracto(eTracto);
			
			eDetalleProgramacion.setCisterna(eCisterna);
			
			eProducto = new Producto();
			eProducto.setId(rs.getInt("id_producto"));
			eProducto.setNombre(Utilidades.cleanXSS(rs.getString("nombre_producto")));
			eProducto.setAbreviatura(Utilidades.cleanXSS(rs.getString("abreviatura")));
			
			eDetalleProgramacion.setProducto(eProducto);
			
			eConductor = new Conductor();
			eConductor.setId(rs.getInt("id_conductor"));
			eConductor.setBrevete(Utilidades.cleanXSS(rs.getString("brevete")));
			eConductor.setApellidos(Utilidades.cleanXSS(rs.getString("apellidos")));
			eConductor.setNombres(Utilidades.cleanXSS(rs.getString("nombres")));
			eConductor.setDni(Utilidades.cleanXSS(rs.getString("dni")));
			
			eDetalleProgramacion.setConductor(eConductor);
			
			ePlanta = new Planta();
			ePlanta.setId(rs.getInt("id_planta"));
			ePlanta.setDescripcion(rs.getString("descripcion_planta")); 
			ePlanta.setCorreoPara(rs.getString("correopara")); 
			ePlanta.setCorreoCC(rs.getString("correocc")); 
			
			eDetalleProgramacion.setPlanta(ePlanta);
			
			eProgramacion = new Programacion();
			eProgramacion.setId(rs.getInt("id_programacion"));
			eProgramacion.setIdTransportista(rs.getInt("id_transportista"));
			eProgramacion.setComentario(Utilidades.cleanXSS(rs.getString("comentario")));
			
			//Parametros de auditoria
			eProgramacion.setCreadoPor(rs.getInt("creado_por"));
		      eProgramacion.setCreadoEl(rs.getLong("creado_el"));
		      eProgramacion.setActualizadoPor(rs.getInt("actualizado_por"));
		      eProgramacion.setActualizadoEl(rs.getLong("actualizado_el"));
		      eProgramacion.setUsuarioActualizacion(Utilidades.cleanXSS(rs.getString("usuario_actualizacion")));
		      eProgramacion.setUsuarioCreacion(Utilidades.cleanXSS(rs.getString("usuario_creacion")));
		      eProgramacion.setIpCreacion(Utilidades.cleanXSS(rs.getString("ip_creacion")));
		      eProgramacion.setIpActualizacion(Utilidades.cleanXSS(rs.getString("ip_actualizacion")));
					
			eTransportista=new Transportista();
			eTransportista.setId(rs.getInt("id_transportista"));
			eTransportista.setRazonSocial(Utilidades.cleanXSS(rs.getString("razon_social")));
			eProgramacion.setTransportista(eTransportista);
			
			eDiaOperativo = new DiaOperativo();
			eDiaOperativo.setId(rs.getInt("id_doperativo"));
			eDiaOperativo.setFechaEstimadaCarga(rs.getDate("fecha_estimada_carga"));
			eDiaOperativo.setFechaOperativa(rs.getDate("fecha_operativa"));
			eDiaOperativo.setUltimaJornadaLiquidada(rs.getDate("ultima_jornada_liquidada"));
			eDiaOperativo.setIdOperacion(rs.getInt("id_operacion"));
			
			eOperacion = new Operacion();
			eOperacion.setId(rs.getInt("id_operacion"));
			eOperacion.setNombre(Utilidades.cleanXSS(rs.getString("nombre_operacion")));
			eOperacion.setIdCliente(rs.getInt("id_cliente"));
			eOperacion.setEta(rs.getInt("eta_origen"));
			eOperacion.setIdPlantaDespacho(rs.getInt("planta_despacho_defecto"));
			eDiaOperativo.setOperacion(eOperacion);
			eProgramacion.setDiaOperativo(eDiaOperativo);			
			eDetalleProgramacion.setProgramacion(eProgramacion);

		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eDetalleProgramacion;
	}
}