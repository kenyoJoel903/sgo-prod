 	

package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.Producto;
import sgo.utilidades.Utilidades;
public class ProductoHabMapper implements RowMapper<Producto>{
	public Producto mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Producto eProducto = null;
		try {
			eProducto = new Producto();
			eProducto.setId(rs.getInt("id_producto"));
			eProducto.setNombre(Utilidades.cleanXSS(rs.getString("nombre")));
			eProducto.setCodigoReferencia(Utilidades.cleanXSS(rs.getString("cod_material_sap")));
			eProducto.setUnidadMedida(Utilidades.cleanXSS(rs.getString("unidad_medida")));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eProducto;
	}
}