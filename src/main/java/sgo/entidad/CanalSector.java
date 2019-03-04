package sgo.entidad;

public class CanalSector extends DataClienteSAP{

	public CanalSector() {
		super();
	}
	private static final long serialVersionUID = -3396992953060760362L;
	private Integer idCanalSector;
	private String canalDistribucionSap;
	private String descCanalDistribucionSap;
	private String descripcionCanalSector;
	
	/** getter para formato en json
	 * @return
	 */
	public int getId() {
		if (idCanalSector != null) {
			return idCanalSector;
		} else {
			return 0;
		}
	}
	
	public Integer getIdCanalSector() {
		return idCanalSector;
	}
	public void setIdCanalSector(Integer idCanalSector) {
		this.idCanalSector = idCanalSector;
	}
	public String getCanalDistribucionSap() {
		return canalDistribucionSap;
	}
	public void setCanalDistribucionSap(String canalDistribucionSap) {
		this.canalDistribucionSap = canalDistribucionSap;
	}
	public String getDescCanalDistribucionSap() {
		return descCanalDistribucionSap;
	}
	public void setDescCanalDistribucionSap(String descCanalDistribucionSap) {
		this.descCanalDistribucionSap = descCanalDistribucionSap;
	}
	public String getDescripcionCanalSector() {
		return descripcionCanalSector;
	}
	public void setDescripcionCanalSector(String descripcionCanalSector) {
		this.descripcionCanalSector = descripcionCanalSector;
	}
}
