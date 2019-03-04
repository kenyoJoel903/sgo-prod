/**
 * SI_Sumular_Crear_Proforma_OutBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sgo.ws.sap.simularcrearproforma;

public class SI_Sumular_Crear_Proforma_OutBindingStub extends org.apache.axis.client.Stub implements sgo.ws.sap.simularcrearproforma.SI_Sumular_Crear_Proforma_Out {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[1];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SI_Sumular_Crear_Proforma_Out");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", "MT_Simular_Crear_Proforma_Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", "DT_Simular_Crear_Proforma_Request"), sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Request.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", "DT_Simular_Crear_Proforma_Response"));
        oper.setReturnClass(sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Response.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", "MT_Simular_Crear_Proforma_Response"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:pi:common", "ZPI_Fault"),
                      "sgo.ws.sap.comun.ZPI_Fault",
                      new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:pi:common", ">ZPI_Fault"), 
                      true
                     ));
        _operations[0] = oper;

    }

    public SI_Sumular_Crear_Proforma_OutBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SI_Sumular_Crear_Proforma_OutBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SI_Sumular_Crear_Proforma_OutBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:pi:common", ">ZPI_Fault");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.comun.ZPI_Fault.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:pi:common", "ExchangeFaultData");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.comun.ExchangeFaultData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:pi:common", "ExchangeLogData");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.comun.ExchangeLogData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:pi:common", "ZPI_Fault_DT");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.comun.ZPI_Fault_DT.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Request>Items_In>Item");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestItems_InItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Request>Partners_In>Item");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestPartners_InItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Response>Order_Conditions_Out>Item");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseOrder_Conditions_OutItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Response>Return_Out>Item");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseReturn_OutItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">DT_Simular_Crear_Proforma_Request>Hearder_In");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestHearder_In.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">DT_Simular_Crear_Proforma_Request>Items_In");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestItems_InItem[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Request>Items_In>Item");
            qName2 = new javax.xml.namespace.QName("", "Item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">DT_Simular_Crear_Proforma_Request>Partners_In");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestPartners_InItem[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Request>Partners_In>Item");
            qName2 = new javax.xml.namespace.QName("", "Item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">DT_Simular_Crear_Proforma_Response>Order_Conditions_Out");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseOrder_Conditions_OutItem[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Response>Order_Conditions_Out>Item");
            qName2 = new javax.xml.namespace.QName("", "Item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">DT_Simular_Crear_Proforma_Response>Return_Out");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseReturn_OutItem[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Response>Return_Out>Item");
            qName2 = new javax.xml.namespace.QName("", "Item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", "DT_Simular_Crear_Proforma_Request");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Request.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", "DT_Simular_Crear_Proforma_Response");
            cachedSerQNames.add(qName);
            cls = sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Response.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Response SI_Sumular_Crear_Proforma_Out(sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Request MT_Simular_Crear_Proforma_Request, String usu, String pass) throws java.rmi.RemoteException, sgo.ws.sap.comun.ZPI_Fault {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://sap.com/xi/WebService/soap1.1");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "SI_Sumular_Crear_Proforma_Out"));
        /******************************/
        /**/_call.setUsername(usu);/***/
        /**/_call.setPassword(pass);/**/
        /******************************/
        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {MT_Simular_Crear_Proforma_Request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Response) _resp;
            } catch (java.lang.Exception _exception) {
                return (sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Response) org.apache.axis.utils.JavaUtils.convert(_resp, sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_Response.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof sgo.ws.sap.comun.ZPI_Fault) {
              throw (sgo.ws.sap.comun.ZPI_Fault) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
