package sgo.ws.sap.simularcrearproforma;

public class SI_Sumular_Crear_Proforma_OutProxy implements sgo.ws.sap.simularcrearproforma.SI_Sumular_Crear_Proforma_Out {
  private String _endpoint = null;
  private sgo.ws.sap.simularcrearproforma.SI_Sumular_Crear_Proforma_Out sI_Sumular_Crear_Proforma_Out = null;
  
  public SI_Sumular_Crear_Proforma_OutProxy() {
    _initSI_Sumular_Crear_Proforma_OutProxy();
  }
  
  public SI_Sumular_Crear_Proforma_OutProxy(String endpoint) {
    _endpoint = endpoint;
    _initSI_Sumular_Crear_Proforma_OutProxy();
  }
  
  private void _initSI_Sumular_Crear_Proforma_OutProxy() {
    try {
      sI_Sumular_Crear_Proforma_Out = (new sgo.ws.sap.simularcrearproforma.Proforma_BC_Simular_Crear_SI_Sumular_Crear_Proforma_OutLocator()).getHTTPS_Port();
      if (sI_Sumular_Crear_Proforma_Out != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sI_Sumular_Crear_Proforma_Out)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sI_Sumular_Crear_Proforma_Out)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sI_Sumular_Crear_Proforma_Out != null)
      ((javax.xml.rpc.Stub)sI_Sumular_Crear_Proforma_Out)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public sgo.ws.sap.simularcrearproforma.SI_Sumular_Crear_Proforma_Out getSI_Sumular_Crear_Proforma_Out() {
    if (sI_Sumular_Crear_Proforma_Out == null)
      _initSI_Sumular_Crear_Proforma_OutProxy();
    return sI_Sumular_Crear_Proforma_Out;
  }
  
  public sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Response SI_Sumular_Crear_Proforma_Out(sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Request MT_Simular_Crear_Proforma_Request, String usu, String pass) throws java.rmi.RemoteException, sgo.ws.sap.comun.ZPI_Fault{
    if (sI_Sumular_Crear_Proforma_Out == null)
      _initSI_Sumular_Crear_Proforma_OutProxy();
    return sI_Sumular_Crear_Proforma_Out.SI_Sumular_Crear_Proforma_Out(MT_Simular_Crear_Proforma_Request, usu, pass);
  }
  
  
}