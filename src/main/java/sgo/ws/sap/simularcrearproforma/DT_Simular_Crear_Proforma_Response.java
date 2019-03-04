/**
 * DT_Simular_Crear_Proforma_Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sgo.ws.sap.simularcrearproforma;

public class DT_Simular_Crear_Proforma_Response  implements java.io.Serializable {
    private java.lang.String documento_Out;

    private sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseOrder_Conditions_OutItem[] order_Conditions_Out;

    private sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseReturn_OutItem[] return_Out;

    public DT_Simular_Crear_Proforma_Response() {
    }

    public DT_Simular_Crear_Proforma_Response(
           java.lang.String documento_Out,
           sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseOrder_Conditions_OutItem[] order_Conditions_Out,
           sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseReturn_OutItem[] return_Out) {
           this.documento_Out = documento_Out;
           this.order_Conditions_Out = order_Conditions_Out;
           this.return_Out = return_Out;
    }


    /**
     * Gets the documento_Out value for this DT_Simular_Crear_Proforma_Response.
     * 
     * @return documento_Out
     */
    public java.lang.String getDocumento_Out() {
        return documento_Out;
    }


    /**
     * Sets the documento_Out value for this DT_Simular_Crear_Proforma_Response.
     * 
     * @param documento_Out
     */
    public void setDocumento_Out(java.lang.String documento_Out) {
        this.documento_Out = documento_Out;
    }


    /**
     * Gets the order_Conditions_Out value for this DT_Simular_Crear_Proforma_Response.
     * 
     * @return order_Conditions_Out
     */
    public sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseOrder_Conditions_OutItem[] getOrder_Conditions_Out() {
        return order_Conditions_Out;
    }


    /**
     * Sets the order_Conditions_Out value for this DT_Simular_Crear_Proforma_Response.
     * 
     * @param order_Conditions_Out
     */
    public void setOrder_Conditions_Out(sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseOrder_Conditions_OutItem[] order_Conditions_Out) {
        this.order_Conditions_Out = order_Conditions_Out;
    }


    /**
     * Gets the return_Out value for this DT_Simular_Crear_Proforma_Response.
     * 
     * @return return_Out
     */
    public sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseReturn_OutItem[] getReturn_Out() {
        return return_Out;
    }


    /**
     * Sets the return_Out value for this DT_Simular_Crear_Proforma_Response.
     * 
     * @param return_Out
     */
    public void setReturn_Out(sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_ResponseReturn_OutItem[] return_Out) {
        this.return_Out = return_Out;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DT_Simular_Crear_Proforma_Response)) return false;
        DT_Simular_Crear_Proforma_Response other = (DT_Simular_Crear_Proforma_Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.documento_Out==null && other.getDocumento_Out()==null) || 
             (this.documento_Out!=null &&
              this.documento_Out.equals(other.getDocumento_Out()))) &&
            ((this.order_Conditions_Out==null && other.getOrder_Conditions_Out()==null) || 
             (this.order_Conditions_Out!=null &&
              java.util.Arrays.equals(this.order_Conditions_Out, other.getOrder_Conditions_Out()))) &&
            ((this.return_Out==null && other.getReturn_Out()==null) || 
             (this.return_Out!=null &&
              java.util.Arrays.equals(this.return_Out, other.getReturn_Out())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDocumento_Out() != null) {
            _hashCode += getDocumento_Out().hashCode();
        }
        if (getOrder_Conditions_Out() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOrder_Conditions_Out());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOrder_Conditions_Out(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getReturn_Out() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getReturn_Out());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getReturn_Out(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DT_Simular_Crear_Proforma_Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", "DT_Simular_Crear_Proforma_Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documento_Out");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Documento_Out"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("order_Conditions_Out");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Order_Conditions_Out"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Response>Order_Conditions_Out>Item"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "Item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("return_Out");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Return_Out"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Response>Return_Out>Item"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "Item"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
