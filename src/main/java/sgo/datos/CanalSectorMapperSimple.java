package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.CanalSector;
import sgo.utilidades.Utilidades;
public class CanalSectorMapperSimple implements RowMapper<CanalSector>{
	public CanalSector mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		CanalSector canalSector = null;
		try {
			canalSector = new CanalSector();
			canalSector.setCanalDistribucionSap(Utilidades.cleanXSS(rs.getString("canal_distribucion_sap")));
			canalSector.setDescCanalDistribucionSap(Utilidades.cleanXSS(rs.getString("desc_canal_distribucion_sap")));
			canalSector.setSectorSap(rs.getString("sector_sap"));
			canalSector.setDescSectorSap(Utilidades.cleanXSS(rs.getString("desc_sector_sap")));
			canalSector.setDescripcionCanalSector(Utilidades.cleanXSS(rs.getString("descripcion_canal_sector")));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return canalSector;
	}
}