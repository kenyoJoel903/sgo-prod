package consulta_venc_docu.sgo.pmerp.pe.com.petroperu;

public class SI_Consulta_Venc_DocuProxy implements consulta_venc_docu.sgo.pmerp.pe.com.petroperu.SI_Consulta_Venc_Docu {
  private String _endpoint = null;
  private consulta_venc_docu.sgo.pmerp.pe.com.petroperu.SI_Consulta_Venc_Docu sI_Consulta_Venc_Docu = null;
  
  public SI_Consulta_Venc_DocuProxy() {
    _initSI_Consulta_Venc_DocuProxy();
  }
  
  public SI_Consulta_Venc_DocuProxy(String endpoint) {
    _endpoint = endpoint;
    _initSI_Consulta_Venc_DocuProxy();
  }
  
  private void _initSI_Consulta_Venc_DocuProxy() {
    try {
      sI_Consulta_Venc_Docu = (new consulta_venc_docu.sgo.pmerp.pe.com.petroperu.BC_WS_SI_Consulta_Venc_DocuLocator()).getHTTPS_Port();
      if (sI_Consulta_Venc_Docu != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sI_Consulta_Venc_Docu)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sI_Consulta_Venc_Docu)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sI_Consulta_Venc_Docu != null)
      ((javax.xml.rpc.Stub)sI_Consulta_Venc_Docu)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public consulta_venc_docu.sgo.pmerp.pe.com.petroperu.SI_Consulta_Venc_Docu getSI_Consulta_Venc_Docu() {
    if (sI_Consulta_Venc_Docu == null)
      _initSI_Consulta_Venc_DocuProxy();
    return sI_Consulta_Venc_Docu;
  }
  
  public consulta_venc_docu.sgo.pmerp.pe.com.petroperu.DT_Consulta_Venc_Docu_Res SI_Consulta_Venc_Docu(consulta_venc_docu.sgo.pmerp.pe.com.petroperu.DT_Consulta_Venc_Docu_Req MT_Consulta_Venc_Doc_Request) throws java.rmi.RemoteException, sgo.ws.sap.vigencia.comun.ZPI_Fault{
    if (sI_Consulta_Venc_Docu == null)
      _initSI_Consulta_Venc_DocuProxy();
    return sI_Consulta_Venc_Docu.SI_Consulta_Venc_Docu(MT_Consulta_Venc_Doc_Request);
  }
  
  
}