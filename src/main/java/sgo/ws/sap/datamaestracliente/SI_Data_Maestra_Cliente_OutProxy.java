package sgo.ws.sap.datamaestracliente;

import java.rmi.RemoteException;

import sgo.ws.sap.comun.ZPI_Fault;

public class SI_Data_Maestra_Cliente_OutProxy implements sgo.ws.sap.datamaestracliente.SI_Data_Maestra_Cliente_Out {
  private String _endpoint = null;
  private sgo.ws.sap.datamaestracliente.SI_Data_Maestra_Cliente_Out sI_Data_Maestra_Cliente_Out = null;
  
  public SI_Data_Maestra_Cliente_OutProxy() {
    _initSI_Data_Maestra_Cliente_OutProxy();
  }
  
  public SI_Data_Maestra_Cliente_OutProxy(String endpoint) {
    _endpoint = endpoint;
    _initSI_Data_Maestra_Cliente_OutProxy();
  }
  
  private void _initSI_Data_Maestra_Cliente_OutProxy() {
    try {
      sI_Data_Maestra_Cliente_Out = (new sgo.ws.sap.datamaestracliente.Proforma_BC_Data_Maestra_Cliente_SI_Data_Maestra_Cliente_OutLocator()).getHTTPS_Port();
      if (sI_Data_Maestra_Cliente_Out != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sI_Data_Maestra_Cliente_Out)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sI_Data_Maestra_Cliente_Out)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sI_Data_Maestra_Cliente_Out != null)
      ((javax.xml.rpc.Stub)sI_Data_Maestra_Cliente_Out)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public sgo.ws.sap.datamaestracliente.SI_Data_Maestra_Cliente_Out getSI_Data_Maestra_Cliente_Out() {
    if (sI_Data_Maestra_Cliente_Out == null)
      _initSI_Data_Maestra_Cliente_OutProxy();
    return sI_Data_Maestra_Cliente_Out;
  }

  public sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_Response SI_Data_Maestra_Cliente_Out(sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_Request MT_Data_Maestra_Cliente_Request, String usu, String pass) throws java.rmi.RemoteException, sgo.ws.sap.comun.ZPI_Fault{
	    if (sI_Data_Maestra_Cliente_Out == null)
	      _initSI_Data_Maestra_Cliente_OutProxy();
	    return sI_Data_Maestra_Cliente_Out.SI_Data_Maestra_Cliente_Out(MT_Data_Maestra_Cliente_Request, usu, pass);
	  }
  
  
}