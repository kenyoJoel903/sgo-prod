package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.CanalSector;
import sgo.entidad.Cliente;
import sgo.entidad.DatosInterlocutor;
import sgo.entidad.Proforma;
import sgo.utilidades.Utilidades;
public class ProformaMapper implements RowMapper<Proforma>{
	public Proforma mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Proforma eCliente = null;
		try {
			eCliente = new Proforma();
			eCliente.setCliente(new Cliente());
			eCliente.setCanalSector(new CanalSector());
			eCliente.setInterlocutor(new DatosInterlocutor());
			
			eCliente.setIdProforma(rs.getInt("id_proforma"));
			eCliente.getCliente().setCodigoSAP(rs.getString("cod_clientesap"));
			eCliente.getCliente().setId(rs.getInt("fk_cod_cliente"));
			eCliente.setNroCotizacion(rs.getString("nro_cotizacion"));
			eCliente.setFechaCotizacion(rs.getDate("fecha_cotizacion"));
			eCliente.getInterlocutor().setCodInterlocutorSap(rs.getString("cod_interlocutor_sap"));
			eCliente.getInterlocutor().setNomInterlocutorSap(rs.getString("destinatario"));
			eCliente.setMoneda(Utilidades.cleanXSS(rs.getString("moneda")));
			eCliente.setMonto(rs.getBigDecimal("monto"));
			eCliente.getCliente().setRazonSocial(Utilidades.cleanXSS(rs.getString("razon_social")));
			eCliente.getCliente().setRuc(Utilidades.cleanXSS(rs.getString("ruc")));
			eCliente.getCliente().setNombreCorto(Utilidades.cleanXSS(rs.getString("nombre_corto")));
			eCliente.getCanalSector().setIdCanalSector(rs.getInt("fk_canal_sector"));
			eCliente.getCanalSector().setDescripcionCanalSector(Utilidades.cleanXSS(rs.getString("descripcion_canal_sector")));
			//Parametros de auditoria
			eCliente.setCreadoPor(rs.getInt("creado_por"));
			eCliente.setCreadoEl(rs.getLong("creado_el"));
			eCliente.setActualizadoPor(rs.getInt("actualizado_por"));
			eCliente.setActualizadoEl(rs.getLong("actualizado_el"));
			eCliente.setIpCreacion(Utilidades.cleanXSS(rs.getString("ip_creacion")));
			eCliente.setIpActualizacion(Utilidades.cleanXSS(rs.getString("ip_actualizacion")));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eCliente;
	}
}