package sgo.entidad;

public class ConfiguracionGec {
	
	private int idConfiguracionGec;
	
	private int idOperacion;
	
	private String correlativo;
	
	private String numeroSerie;
	
	private Integer estado;
	
	//Inicio Agregado por req 9000002857
	private int anio;
	private String alias_operacion;

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getAliasOperacion() {
		return alias_operacion;
	}

	public void setAliasOperacion(String alias_operacion) {
		this.alias_operacion = alias_operacion;
	}
	
	//Fin Agregado por req 9000002857

	public int getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(int idOperacion) {
		this.idOperacion = idOperacion;
	}

	public int getIdConfiguracionGec() {
		return idConfiguracionGec;
	}

	public void setIdConfiguracionGec(int idConfiguracionGec) {
		this.idConfiguracionGec = idConfiguracionGec;
	}

	public String getCorrelativo() {
		return correlativo;
	}

	public void setCorrelativo(String correlativo) {
		this.correlativo = correlativo;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

}
