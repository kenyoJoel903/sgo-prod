package sgo.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import sgo.entidad.Cliente;
import sgo.entidad.Contenido;
import sgo.entidad.RespuestaCompuesta;
import sgo.utilidades.Constante;
import sgo.ws.sap.comun.ZPI_Fault;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_Request;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_Response;
import sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem;
import sgo.ws.sap.datamaestracliente.SI_Data_Maestra_Cliente_OutProxy;


public class DataMaestraServicioWS {

	private SI_Data_Maestra_Cliente_OutProxy proxy;
	
	public RespuestaCompuesta consultar(Cliente eCliente, DT_Data_Maestra_Cliente_Proforma_Response response) {
		// ticket 9000003025
		ServiceProperties property = new ServiceProperties();
		
		proxy = new SI_Data_Maestra_Cliente_OutProxy();
		proxy.setEndpoint(property.getEndPointWS() + "/XISOAPAdapter/MessageServlet?senderParty=Proforma&senderService=BC_Data_Maestra_Cliente&receiverParty=&receiverService=&interface=SI_Data_Maestra_Cliente_Out&interfaceNamespace=urn:petroperu.com.pe:pmerp:sd:proforma");
		
		DT_Data_Maestra_Cliente_Proforma_Request req = new DT_Data_Maestra_Cliente_Proforma_Request();
		DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem [] subreq = new DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem[1];
		subreq[0] = new DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem();
		subreq[0].setSign("I");
		subreq[0].setOption("EQ");
		subreq[0].setHigh(eCliente.getCodigoSAP());
		subreq[0].setLow(eCliente.getCodigoSAP());
		
		req.setCodCliente_In(subreq);
		try {
			
			DT_Data_Maestra_Cliente_Proforma_Response res = proxy.SI_Data_Maestra_Cliente_Out(req, property.getUserWS(), property.getPassWS());
			
			response.setClieDestMate_Out(res.getClieDestMate_Out());
			response.setClieDestRamoCentMate_Out(res.getClieDestRamoCentMate_Out());
			response.setClieMate_Out(res.getClieMate_Out());
			response.setClieRamoMate_Out(res.getClieRamoMate_Out());
			response.setDatosAreaVenta_Out(res.getDatosAreaVenta_Out());
			response.setDatosClientes_Out(res.getDatosClientes_Out());
			response.setDatosInterlocutor_Out(res.getDatosInterlocutor_Out());
			response.setMaterial_Out(res.getMaterial_Out());
			response.setReturn_Out(res.getReturn_Out());
			
			return configurarRespCompuesta(response,eCliente);
		} catch (ZPI_Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private RespuestaCompuesta configurarRespCompuesta(
			DT_Data_Maestra_Cliente_Proforma_Response response, Cliente eCliente) {
		RespuestaCompuesta respuesta = new RespuestaCompuesta();

		if(response.getReturn_Out().length == 0 
				|| !Constante.TIPO_RESP_ERROR.equals(response.getReturn_Out()[0].getType())){
			List<Cliente> listaClientes = new ArrayList<Cliente>();
			Contenido<Cliente> contenido = new Contenido<Cliente>();
			
			DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem[] rs = response
					.getDatosClientes_Out();
			// eCliente.setId();
			eCliente.setCodigoSAP(rs[0].getCodCliente());
			eCliente.setRazonSocialSAP(rs[0].getNombre());
			eCliente.setRamaSAP(rs[0].getRamo());
			eCliente.setNombreCorto("");
			eCliente.setRazonSocial(rs[0].getNombre());
			eCliente.setEstado(Constante.ESTADO_ACTIVO);
			eCliente.setNumeroContrato("");
			eCliente.setDescripcionContrato("");
			eCliente.setRuc(rs[0].getNroRuc());
	
			listaClientes.add(eCliente);
			contenido.carga = listaClientes;
			respuesta.mensaje = "OK";
			respuesta.estado = true;
			respuesta.contenido = contenido;
		} else {
			respuesta.mensaje = response.getReturn_Out()[0].getMessage();
			respuesta.estado = false;
			respuesta.contenido = null;
		}
		return respuesta;
	}

}
