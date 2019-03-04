package sgo.entidad;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import sgo.utilidades.Utilidades;

public class DiaOperativo extends EntidadBase {
	private int id_doperativo;
	private Date fecha_operativa;
	private Date ultima_jornada_liquidada;//abarrios
	private int id_operacion;
	private int estado;
	private Operacion operacion;
	private ArrayList<Planificacion> planificaciones;
	private String detalleProductoSolicitado;
	private int totalCisternas;
	private int totalCisternasPlanificados=0;
	private int totalCisternasAsignados=0;
	private int totalCisternasDescargados=0;
	private int totalVolumenSolicitado;
	private int totalVolumenPropuesto;
	private int totalCisternasProgramados=0;//abarrios
	private String justificacionCierre="";//knavarro
	private ProgramacionPlanificada programacionPlanificada; // jmatos Ticket 9000002608
	private List<ProductoProgramacion> productos;// jmatos Ticket 9000002608
	
	//9000002608========================================================
	private int cantidadCisternasPlan;
	
	public int getCantidadCisternasPlan() {
		return cantidadCisternasPlan;
	}

	public void setCantidadCisternasPlan(int cantidadCisternasPlan) {
		this.cantidadCisternasPlan = cantidadCisternasPlan;
	}
	//==================================================================

	public static final int ESTADO_PLANIFICADO=1;
	public static final int ESTADO_ASIGNADO=2;
	public static final int ESTADO_DESCARGANDO=3;
	public static final int ESTADO_CERRADO=4;
	public static final int ESTADO_LIQUIDADO=5;
	public static final int ESTADO_ANULADO=6;
	public static final int ESTADO_PROGRAMACION=7;
	
	public static final String ASUNTO_MAIL_PLANIFICACION = "Programaci�n de Planificaci�n.";
	public static final String BODY_MAIL_PLANIFICACION = "Este contenido lo debe definir el usuario.";
	
	private Date fecha_estimada_carga;//abarrios
		
public int getTotalCisternasPlanificados() {
  return totalCisternasPlanificados;
 }

 public void setTotalCisternasPlanificados(int totalCisternasPlanificados) {
  this.totalCisternasPlanificados = totalCisternasPlanificados;
 }

 public int getTotalCisternasAsignados() {
  return totalCisternasAsignados;
 }

 public void setTotalCisternasAsignados(int totalCisternasAsignados) {
  this.totalCisternasAsignados = totalCisternasAsignados;
 }

 public int getTotalCisternasDescargados() {
  return totalCisternasDescargados;
 }

 public void setTotalCisternasDescargados(int totalCisternasDescargados) {
  this.totalCisternasDescargados = totalCisternasDescargados;
 }

	public String getDetalleProductoSolicitado() {
		return detalleProductoSolicitado;
	}

	public void setDetalleProductoSolicitado(String detalleProductoSolicitado) {
		this.detalleProductoSolicitado = detalleProductoSolicitado;
	}
	
	/**
	 * @return the totalVolumenSolicitado
	 */
	public int getTotalVolumenSolicitado() {
		return totalVolumenSolicitado;
	}

	/**
	 * @param totalVolumenSolicitado the totalVolumenSolicitado to set
	 */
	public void setTotalVolumenSolicitado(int totalVolumenSolicitado) {
		this.totalVolumenSolicitado = totalVolumenSolicitado;
	}

	/**
	 * @return the totalVolumenPropuesto
	 */
	public int getTotalVolumenPropuesto() {
		return totalVolumenPropuesto;
	}

	/**
	 * @param totalVolumenPropuesto the totalVolumenPropuesto to set
	 */
	public void setTotalVolumenPropuesto(int totalVolumenPropuesto) {
		this.totalVolumenPropuesto = totalVolumenPropuesto;
	}

	public int getTotalCisternas() {
		return totalCisternas;
	}

	public void setTotalCisternas(int totalCisternas) {
		this.totalCisternas = totalCisternas;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}

	public void setPlanificaciones(ArrayList<Planificacion> planificacion) {
		this.planificaciones = planificacion;
	}

	public List<Planificacion> getPlanificaciones() {
		return planificaciones;
	}
	
	public void agregarPlanificacion(Planificacion ePlanificacion){
	 if (this.planificaciones==null){
	  this.planificaciones =  new ArrayList<Planificacion>();
	 }
	 this.planificaciones.add(ePlanificacion);
	}

	public int getId() {
		return id_doperativo;
	}

	public void setId(int Id) {
		this.id_doperativo = Id;
	}

	public Date getFechaOperativa() {
		return fecha_operativa;
	}

	public void setFechaOperativa(Date FechaOperativa) {
		this.fecha_operativa = FechaOperativa;
	}

	public int getIdOperacion() {
		return id_operacion;
	}

	public void setIdOperacion(int id_operacion) {
		this.id_operacion = id_operacion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int Estado) {
		this.estado = Estado;
	}
	
	public boolean validar(){
		boolean resultado = true;

		if(!Utilidades.esValido(this.id_operacion)){
			return false;
		}
		
		if(!Utilidades.esValido(this.fecha_operativa)){
			return false;
		}
		
		if(!Utilidades.esValido(this.estado)){
			return false;
		}

		return resultado;
	}

	public Date getFechaEstimadaCarga() {
		return fecha_estimada_carga;
	}

	public void setFechaEstimadaCarga(Date fecha_estimada_carga) {
		this.fecha_estimada_carga = fecha_estimada_carga;
	}

	public int getTotalCisternasProgramados() {
		return totalCisternasProgramados;
	}

	public void setTotalCisternasProgramados(int totalCisternasProgramados) {
		this.totalCisternasProgramados = totalCisternasProgramados;
	}

	public Date getUltimaJornadaLiquidada() {
		return ultima_jornada_liquidada;
	}

	public void setUltimaJornadaLiquidada(Date ultimaJornadaLiquidada) {
		this.ultima_jornada_liquidada = ultimaJornadaLiquidada;
	}

	public String getJustificacionCierre() {
		return justificacionCierre;
	}

	public void setJustificacionCierre(String justificacionCierre) {
		this.justificacionCierre = justificacionCierre;
	}
	 // jmatos Ticket 9000002608
	public ProgramacionPlanificada getProgramacionPlanificada() {
		return programacionPlanificada;
	}

	public void setProgramacionPlanificada(ProgramacionPlanificada programacionPlanificada) {
		this.programacionPlanificada = programacionPlanificada;
	}
	 

	public List<ProductoProgramacion> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoProgramacion> productos) {
		this.productos = productos;
	}
	// jmatos Ticket 9000002608

}