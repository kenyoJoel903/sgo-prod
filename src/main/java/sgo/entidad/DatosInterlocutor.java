package sgo.entidad;

public class DatosInterlocutor extends DataClienteSAP{

	public DatosInterlocutor() {
		super();
	}
	private static final long serialVersionUID = -3396992953060760362L;
	private Integer idDatosInter;
	private String canalDistribucionSap;
	private String descCanalDistribucionSap;
	private String funInterlocutorSap;
	private String codInterlocutorSap;
	private String nomInterlocutorSap;
	private String direccionSap;
	
	/** getter para formato en json
	 * @return
	 */
	public Integer getId() {
		return idDatosInter;
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
	public String getFunInterlocutorSap() {
		return funInterlocutorSap;
	}
	public void setFunInterlocutorSap(String funInterlocutorSap) {
		this.funInterlocutorSap = funInterlocutorSap;
	}
	public String getCodInterlocutorSap() {
		return codInterlocutorSap;
	}
	public void setCodInterlocutorSap(String codInterlocutorSap) {
		this.codInterlocutorSap = codInterlocutorSap;
	}
	public String getNomInterlocutorSap() {
		return nomInterlocutorSap;
	}
	public void setNomInterlocutorSap(String nomInterlocutorSap) {
		this.nomInterlocutorSap = nomInterlocutorSap;
	}
	public Integer getIdDatosInter() {
		return idDatosInter;
	}
	public void setIdDatosInter(Integer idDatosInter) {
		this.idDatosInter = idDatosInter;
	}

	public String getDireccionSap() {
		return direccionSap;
	}

	public void setDireccionSap(String direccionSap) {
		this.direccionSap = direccionSap;
	}
}
