 	

package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.Planta;
import sgo.utilidades.Utilidades;
public class PlantaHabMapper implements RowMapper<Planta>{
	public Planta mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Planta ePlanta = null; 
		try {
			ePlanta = new Planta();
			ePlanta.setId(rs.getInt("id_planta"));
			ePlanta.setDescripcion(Utilidades.cleanXSS(rs.getString("descripcion")));
			ePlanta.setCodigoReferencia(Utilidades.cleanXSS(rs.getString("planta_cod_sap")));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return ePlanta;
	}
}