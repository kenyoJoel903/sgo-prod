package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.CanalSector;
import sgo.utilidades.Utilidades;
public class CanalSectorMapper implements RowMapper<CanalSector>{
	public CanalSector mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		CanalSector canalSector = null;
		try {
			canalSector = new CanalSector();
			canalSector.setIdCanalSector(rs.getInt("id_canal_sector"));
			canalSector.setCodClientesap(rs.getString("cod_clientesap"));
			canalSector.setFkCodCliente(rs.getInt("fk_cod_cliente"));
			canalSector.setOrgVentaSap(rs.getString("org_venta_sap"));
			canalSector.setDesOrgVentaSap(Utilidades.cleanXSS(rs.getString("des_org_venta_sap")));
			canalSector.setCanalDistribucionSap(Utilidades.cleanXSS(rs.getString("canal_distribucion_sap")));
			canalSector.setDescCanalDistribucionSap(Utilidades.cleanXSS(rs.getString("desc_canal_distribucion_sap")));
			canalSector.setSectorSap(rs.getString("sector_sap"));
			canalSector.setDescSectorSap(Utilidades.cleanXSS(rs.getString("desc_sector_sap")));
			canalSector.setDescripcionCanalSector(Utilidades.cleanXSS(rs.getString("descripcion_canal_sector")));
			//Parametros de auditoria
			canalSector.setCreadoPor(rs.getInt("creado_por"));
			canalSector.setCreadoEl(rs.getLong("creado_el"));
			canalSector.setActualizadoPor(rs.getInt("actualizado_por"));
			canalSector.setActualizadoEl(rs.getLong("actualizado_el"));
//			canalSector.setUsuarioActualizacion(Utilidades.cleanXSS(rs.getString("usuario_actualizacion")));
//			canalSector.setUsuarioCreacion(Utilidades.cleanXSS(rs.getString("usuario_creacion")));
//			canalSector.setIpCreacion(Utilidades.cleanXSS(rs.getString("ip_creacion")));
//			canalSector.setIpActualizacion(Utilidades.cleanXSS(rs.getString("ip_actualizacion")));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return canalSector;
	}
}