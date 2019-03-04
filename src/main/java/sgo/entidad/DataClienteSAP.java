package sgo.entidad;

public class DataClienteSAP extends EntidadBase{

	private static final long serialVersionUID = -3396992953060760362L;
	
	private String codClientesap;
	private Integer fkCodCliente;
	private String orgVentaSap;
	private String desOrgVentaSap;
	private String sectorSap;
	private String descSectorSap;
	
	public String getCodClientesap() {
		return codClientesap;
	}
	public void setCodClientesap(String codClientesap) {
		this.codClientesap = codClientesap;
	}
	public Integer getFkCodCliente() {
		return fkCodCliente;
	}
	public void setFkCodCliente(Integer fkCodCliente) {
		this.fkCodCliente = fkCodCliente;
	}
	public String getOrgVentaSap() {
		return orgVentaSap;
	}
	public void setOrgVentaSap(String orgVentaSap) {
		this.orgVentaSap = orgVentaSap;
	}
	public String getDesOrgVentaSap() {
		return desOrgVentaSap;
	}
	public void setDesOrgVentaSap(String desOrgVentaSap) {
		this.desOrgVentaSap = desOrgVentaSap;
	}
	public String getSectorSap() {
		return sectorSap;
	}
	public void setSectorSap(String sectorSap) {
		this.sectorSap = sectorSap;
	}
	public String getDescSectorSap() {
		return descSectorSap;
	}
	public void setDescSectorSap(String descSectorSap) {
		this.descSectorSap = descSectorSap;
	}
}
