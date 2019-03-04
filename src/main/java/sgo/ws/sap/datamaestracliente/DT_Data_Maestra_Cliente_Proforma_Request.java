/**
 * DT_Data_Maestra_Cliente_Proforma_Request.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sgo.ws.sap.datamaestracliente;

public class DT_Data_Maestra_Cliente_Proforma_Request  implements java.io.Serializable {
    private sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem[] codCliente_In;

    public DT_Data_Maestra_Cliente_Proforma_Request() {
    }

    public DT_Data_Maestra_Cliente_Proforma_Request(
           sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem[] codCliente_In) {
           this.codCliente_In = codCliente_In;
    }


    /**
     * Gets the codCliente_In value for this DT_Data_Maestra_Cliente_Proforma_Request.
     * 
     * @return codCliente_In
     */
    public sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem[] getCodCliente_In() {
        return codCliente_In;
    }


    /**
     * Sets the codCliente_In value for this DT_Data_Maestra_Cliente_Proforma_Request.
     * 
     * @param codCliente_In
     */
    public void setCodCliente_In(sgo.ws.sap.datamaestracliente.DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem[] codCliente_In) {
        this.codCliente_In = codCliente_In;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DT_Data_Maestra_Cliente_Proforma_Request)) return false;
        DT_Data_Maestra_Cliente_Proforma_Request other = (DT_Data_Maestra_Cliente_Proforma_Request) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codCliente_In==null && other.getCodCliente_In()==null) || 
             (this.codCliente_In!=null &&
              java.util.Arrays.equals(this.codCliente_In, other.getCodCliente_In())));
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
        if (getCodCliente_In() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCodCliente_In());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCodCliente_In(), i);
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
        new org.apache.axis.description.TypeDesc(DT_Data_Maestra_Cliente_Proforma_Request.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", "DT_Data_Maestra_Cliente_Proforma_Request"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codCliente_In");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CodCliente_In"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Data_Maestra_Cliente_Proforma_Request>CodCliente_In>Item"));
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
