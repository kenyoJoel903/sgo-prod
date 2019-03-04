package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sgo.entidad.Cliente;
import sgo.utilidades.Utilidades;
public class ClienteAsignadoMapper implements RowMapper<Cliente>{
	public Cliente mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		Cliente eCliente = null;
		try {
			eCliente = new Cliente();
			eCliente.setId(rs.getInt("id_cliente"));
			eCliente.setCodigoSAP(rs.getString("codigo_sap"));//phf:
			eCliente.setRazonSocialSAP(rs.getString("razon_social_sap"));//phf:
			eCliente.setRamaSAP(rs.getString("rama_sap"));//phf:
			eCliente.setRazonSocial(Utilidades.cleanXSS(rs.getString("razon_social")));
			eCliente.setRuc(Utilidades.cleanXSS(rs.getString("ruc")));
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return eCliente;
	}
}