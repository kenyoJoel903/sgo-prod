package sgo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import sgo.entidad.Cliente;
import sgo.entidad.Contenido;
import sgo.entidad.Proforma;
import sgo.entidad.ProformaDetalle;
import sgo.entidad.RespuestaCompuesta;
import sgo.utilidades.Constante;
import sgo.utilidades.Utilidades;
import sgo.ws.sap.comun.ZPI_Fault;
import sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Request;
import sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestHearder_In;
import sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestItems_InItem;
import sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestPartners_InItem;
import sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Response;
import sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseOrder_Conditions_OutItem;
import sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseReturn_OutItem;
import sgo.ws.sap.simularcrearproforma.SI_Sumular_Crear_Proforma_OutProxy;

public class SimularCrearProformaServicioWS {

	private SI_Sumular_Crear_Proforma_OutProxy proxy;
	
	public RespuestaCompuesta consultar(Cliente eCliente, Proforma proforma) {
		// ticket 9000003025
		ServiceProperties property = new ServiceProperties();
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		
		proxy = new SI_Sumular_Crear_Proforma_OutProxy();
		proxy.setEndpoint(property.getEndPointWS() + "/XISOAPAdapter/MessageServlet?senderParty=Proforma&senderService=BC_Simular_Crear&receiverParty=&receiverService=&interface=SI_Sumular_Crear_Proforma_Out&interfaceNamespace=urn:petroperu.com.pe:pmerp:sd:proforma");
		DT_Simular_Crear_Proforma_Request req = this.configurarRequest(proforma);
		
		try {
			
			DT_Simular_Crear_Proforma_Response res = proxy.SI_Sumular_Crear_Proforma_Out(req, property.getUserWS(), property.getPassWS());
			return configurarRespCompuesta(res,proforma);
			
		} catch (ZPI_Fault e) {

			e.printStackTrace();
			respuesta.mensaje=e.getMessage();
			respuesta.error=Constante.EXCEPCION_INTEGRIDAD_DATOS;
			respuesta.estado=false;
			return respuesta;
		} catch (RemoteException e) {
			
			e.printStackTrace();
			respuesta.mensaje=e.getMessage();
			respuesta.error=Constante.EXCEPCION_ACCESO_DATOS;
			respuesta.estado=false;
			return respuesta;
		}
	}

	private DT_Simular_Crear_Proforma_Request configurarRequest(Proforma proforma) {
		
		DT_Simular_Crear_Proforma_Request request = new DT_Simular_Crear_Proforma_Request();
		
		//cabecera de proforma
		DT_Simular_Crear_Proforma_RequestHearder_In hearderIn = new DT_Simular_Crear_Proforma_RequestHearder_In ();
		hearderIn.setDocVenta("ZCNW");
		hearderIn.setOrgVenta("1000");
		hearderIn.setCanal(proforma.getCanalSector().getCanalDistribucionSap());
		hearderIn.setSector(proforma.getCanalSector().getSectorSap());
		hearderIn.setFecValida_De(proforma.getFechaCotizacion());
		hearderIn.setFecValida_A(proforma.getFechaCotizacion());
		hearderIn.setMoneda(proforma.getMoneda());
		hearderIn.setProceso(proforma.getProceso());
		
		//Detalle de proforma
		DT_Simular_Crear_Proforma_RequestItems_InItem[] items = new DT_Simular_Crear_Proforma_RequestItems_InItem[proforma.getItems().size()];
		DT_Simular_Crear_Proforma_RequestItems_InItem item = null;
		
		for(int i = 0;i<proforma.getItems().size();i++){
			item= new DT_Simular_Crear_Proforma_RequestItems_InItem();
			item.setCantidad(proforma.getItems().get(i).getVolumen());
		    item.setCentro(proforma.getItems().get(i).getPlanta().getCodigoReferencia());
		    item.setMaterial(proforma.getItems().get(i).getProducto().getCodigoReferencia());
		    item.setPosicion(configurarPosReq(proforma.getItems().get(i).getPosicion()));
		    item.setUnidadMedida(proforma.getItems().get(i).getProducto().getUnidadMedida());
		    
		    items[i] = item;
		}
		
		DT_Simular_Crear_Proforma_RequestPartners_InItem[] partner = {
			new DT_Simular_Crear_Proforma_RequestPartners_InItem(),
			new DT_Simular_Crear_Proforma_RequestPartners_InItem()
		};
		
		// Cliente
		partner[0].setFunInterlocutor("AG");
		partner[0].setCodInterlocutor(proforma.getCliente().getCodigoSAP());
		partner[0].setPosicion("000000");

		// interlocutor-destinatario
		partner[1].setFunInterlocutor("WE");// proforma.getInterlocutor().getFunInterlocutorSap());
		partner[1].setCodInterlocutor(proforma.getInterlocutor().getCodInterlocutorSap());
		partner[1].setPosicion("000000");

		request.setHearder_In(hearderIn);
	    request.setPartners_In(partner);
		request.setItems_In(items);
		return request;
	}

	private String configurarPosReq(Integer posicion) {
		int pos = 10*(posicion+1);
		return String.valueOf(pos);
	}

	private RespuestaCompuesta configurarRespCompuesta(DT_Simular_Crear_Proforma_Response response, Proforma proforma) {
		RespuestaCompuesta respuesta = new RespuestaCompuesta();
		
		DT_Simular_Crear_Proforma_ResponseReturn_OutItem error = esConsultaExitosa(response);
		
		if (error == null) {
			List<Proforma> lista = new ArrayList<Proforma>();
			Contenido<Proforma> contenido = new Contenido<Proforma>();
			
			DT_Simular_Crear_Proforma_ResponseOrder_Conditions_OutItem[] rs = response.getOrder_Conditions_Out();
	
			proforma.setNroCotizacion(response.getDocumento_Out());
			BigDecimal total = new BigDecimal(0);
			int pos = 0;
			ProformaDetalle it = null;
			
			for (int i = 0; i < rs.length; i++) {
				pos = Integer.parseInt(rs[i].getPosicion());
				
				it = proforma.getItems().get(i);
				it.setPrecio(rs[i].getCond_ZP00());
				it.setDescuento(rs[i].getCond_ZD01());
				it.setRodaje(rs[i].getCond_ZROD());
				it.setIsc(rs[i].getCond_ZISC());
				it.setIgv(rs[i].getCond_ZIGV());
				it.setFise(rs[i].getCond_ZFIS());
				it.setPrecioPercepcion(rs[i].getCond_ZPER());
				it.setImporteTotal(rs[i].getValor_Total());
				
				total = total.add(calcularCampos(it));
			}
			
			proforma.setMonto(total);
	
			lista.add(proforma);
			contenido.carga = lista;
			respuesta.mensaje = "OK";
			respuesta.estado = true;
			respuesta.contenido = contenido;
		} else {
			respuesta.mensaje = error.getMessage();
			respuesta.estado = false;
			respuesta.contenido = null;
		}
		
		return respuesta;
	}

	/**
	 * @param response
	 * @return
	 */
	private DT_Simular_Crear_Proforma_ResponseReturn_OutItem esConsultaExitosa(DT_Simular_Crear_Proforma_Response response) {
		for (int i = 0; i < response.getReturn_Out().length; i++) {
			if(Constante.TIPO_RESP_ERROR.equals(response.getReturn_Out()[i].getType())){
				return response.getReturn_Out()[i];
			}
		}
		return null;
	}

	private BigDecimal calcularCampos(ProformaDetalle it) {

		//A: calculos
		it.setDescuento(it.getDescuento().abs());
		it.setPrecioNeto(it.getPrecio().subtract(it.getDescuento()));
		it.setAcumulado(
			it.getImporteTotal()
			.subtract(it.getIgv())
			.subtract(it.getFise())
		);
		it.setOtros(
			it.getAcumulado()
			.subtract(it.getPrecioNeto())
			.subtract(it.getRodaje())
			.subtract(it.getIsc())
		);
		it.setImporteTotal(Utilidades.bdFormat(it.getPrecioPercepcion().add(it.getImporteTotal())));
		it.setPrecioDescuento(
			it.getAcumulado()
			.add(it.getIgv())
			.add(it.getFise())
		);
		it.setPrecioPercepcion(it.getPrecioDescuento().add(it.getPrecioPercepcion()));
		
		//B: dividiendo entre el volumen
		BigDecimal volumen = it.getVolumen();
		it.setPrecio(it.getPrecio().divide(volumen,RoundingMode.HALF_UP));
		it.setDescuento(it.getDescuento().divide(volumen,RoundingMode.HALF_UP));
		it.setPrecioNeto(it.getPrecioNeto().divide(volumen,RoundingMode.HALF_UP));
		it.setRodaje(it.getRodaje().divide(volumen,RoundingMode.HALF_UP));
		it.setIsc(it.getIsc().divide(volumen,RoundingMode.HALF_UP));
		it.setAcumulado(it.getAcumulado().divide(volumen, RoundingMode.HALF_UP));
		it.setOtros(it.getOtros().divide(volumen, RoundingMode.HALF_UP));
		it.setIgv(it.getIgv().divide(volumen,RoundingMode.HALF_UP));
		it.setFise(it.getFise().divide(volumen,RoundingMode.HALF_UP));
		it.setPrecioDescuento(it.getPrecioDescuento().divide(volumen,RoundingMode.HALF_UP));
		it.setPrecioPercepcion(it.getPrecioPercepcion().divide(volumen,RoundingMode.HALF_UP));
				
		return it.getImporteTotal();
	}

}
