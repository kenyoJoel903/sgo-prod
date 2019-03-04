package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.DatosInterlocutor;
import sgo.utilidades.Utilidades;
public class DatosInterlocutorMapper implements RowMapper<DatosInterlocutor>{
	public DatosInterlocutor mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		DatosInterlocutor bean = null;
		try {
			bean = new DatosInterlocutor();
			bean.setIdDatosInter(rs.getInt("id_datos_inter"));
			bean.setCodClientesap(rs.getString("cod_clientesap"));
			bean.setFkCodCliente(rs.getInt("fk_cod_cliente"));
			bean.setOrgVentaSap(rs.getString("org_venta_sap"));
			bean.setCanalDistribucionSap(Utilidades.cleanXSS(rs.getString("canal_distribucion_sap")));
			bean.setSectorSap(rs.getString("sector_sap"));
			bean.setFunInterlocutorSap(Utilidades.cleanXSS(rs.getString("fun_interlocutor_sap")));
			bean.setCodInterlocutorSap(Utilidades.cleanXSS(rs.getString("cod_interlocutor_sap")));
			bean.setNomInterlocutorSap(Utilidades.cleanXSS(rs.getString("nom_interlocutor_sap")));
			bean.setDireccionSap(Utilidades.cleanXSS(rs.getString("dir_interlocutor_sap")));
			//Parametros de auditoria
			bean.setCreadoPor(rs.getInt("creado_por"));
			bean.setCreadoEl(rs.getLong("creado_el"));
			bean.setActualizadoPor(rs.getInt("actualizado_por"));
			bean.setActualizadoEl(rs.getLong("actualizado_el"));
//			canalSector.setUsuarioActualizacion(Utilidades.cleanXSS(rs.getString("usuario_actualizacion")));
//			canalSector.setUsuarioCreacion(Utilidades.cleanXSS(rs.getString("usuario_creacion")));
//			canalSector.setIpCreacion(Utilidades.cleanXSS(rs.getString("ip_creacion")));
//			canalSector.setIpActualizacion(Utilidades.cleanXSS(rs.getString("ip_actualizacion")));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return bean;
	}
}