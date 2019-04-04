package sgo.datos;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import sgo.entidad.Planta;
import sgo.entidad.Producto;
import sgo.entidad.ProformaDetalle;
public class ProformaDetalleMapper implements RowMapper<ProformaDetalle>{
	public ProformaDetalle mapRow(ResultSet rs, int arg1) throws SQLException 
	{
		ProformaDetalle detalle = null;
		try {
			detalle = new ProformaDetalle();
			
			detalle.setIdProforma(rs.getInt("id_detalle_proforma"));
			detalle.setIdProforma(rs.getInt("fk_id_proforma"));
			detalle.setPlanta(new Planta());
			detalle.getPlanta().setId(rs.getInt("fk_id_planta"));
			detalle.getPlanta().setDescripcion(rs.getString("nombre_planta"));
			detalle.setProducto(new Producto());
			detalle.getProducto().setId(rs.getInt("fk_id_producto"));
			detalle.getProducto().setNombre(rs.getString("nombre_producto"));
			detalle.setPosicion(rs.getInt("posicion"));
			detalle.setVolumen(rs.getBigDecimal("volumen"));
			detalle.setPrecio(rs.getBigDecimal("precio"));
			detalle.setDescuento(rs.getBigDecimal("descuento"));
			detalle.setPrecioNeto(rs.getBigDecimal("precio_neto"));
			detalle.setRodaje(rs.getBigDecimal("rodaje"));
			detalle.setIsc(rs.getBigDecimal("isc"));
			detalle.setOtros(rs.getBigDecimal("otros")); // ticket 9000003025
			detalle.setAcumulado(rs.getBigDecimal("acumulado"));
			detalle.setIgv(rs.getBigDecimal("igv"));
			detalle.setFise(rs.getBigDecimal("fise"));
			detalle.setPrecioDescuento(rs.getBigDecimal("precio_descuento"));
			detalle.setPrecioPercepcion(rs.getBigDecimal("precio_percepcion"));
			detalle.setImporteTotal(rs.getBigDecimal("importe_total"));
			
//			if(StringUtils.isNotBlank(detalle.getVolumen())){
//				detalle.setVolumenDecimal(new BigDecimal(detalle.getVolumen().replaceAll(",", "")));
//			}
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return detalle;
	}
}