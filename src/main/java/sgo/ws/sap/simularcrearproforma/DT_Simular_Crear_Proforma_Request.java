/**
 * DT_Simular_Crear_Proforma_Request.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sgo.ws.sap.simularcrearproforma;

public class DT_Simular_Crear_Proforma_Request  implements java.io.Serializable {
    private sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestHearder_In hearder_In;

    private sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestItems_InItem[] items_In;

    private sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestPartners_InItem[] partners_In;

    public DT_Simular_Crear_Proforma_Request() {
    }

    public DT_Simular_Crear_Proforma_Request(
           sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestHearder_In hearder_In,
           sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestItems_InItem[] items_In,
           sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestPartners_InItem[] partners_In) {
           this.hearder_In = hearder_In;
           this.items_In = items_In;
           this.partners_In = partners_In;
    }


    /**
     * Gets the hearder_In value for this DT_Simular_Crear_Proforma_Request.
     * 
     * @return hearder_In
     */
    public sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestHearder_In getHearder_In() {
        return hearder_In;
    }


    /**
     * Sets the hearder_In value for this DT_Simular_Crear_Proforma_Request.
     * 
     * @param hearder_In
     */
    public void setHearder_In(sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestHearder_In hearder_In) {
        this.hearder_In = hearder_In;
    }


    /**
     * Gets the items_In value for this DT_Simular_Crear_Proforma_Request.
     * 
     * @return items_In
     */
    public sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestItems_InItem[] getItems_In() {
        return items_In;
    }


    /**
     * Sets the items_In value for this DT_Simular_Crear_Proforma_Request.
     * 
     * @param items_In
     */
    public void setItems_In(sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestItems_InItem[] items_In) {
        this.items_In = items_In;
    }


    /**
     * Gets the partners_In value for this DT_Simular_Crear_Proforma_Request.
     * 
     * @return partners_In
     */
    public sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestPartners_InItem[] getPartners_In() {
        return partners_In;
    }


    /**
     * Sets the partners_In value for this DT_Simular_Crear_Proforma_Request.
     * 
     * @param partners_In
     */
    public void setPartners_In(sgo.ws.sap.simularcrearproforma.DT_Simular_Crear_Proforma_RequestPartners_InItem[] partners_In) {
        this.partners_In = partners_In;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DT_Simular_Crear_Proforma_Request)) return false;
        DT_Simular_Crear_Proforma_Request other = (DT_Simular_Crear_Proforma_Request) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.hearder_In==null && other.getHearder_In()==null) || 
             (this.hearder_In!=null &&
              this.hearder_In.equals(other.getHearder_In()))) &&
            ((this.items_In==null && other.getItems_In()==null) || 
             (this.items_In!=null &&
              java.util.Arrays.equals(this.items_In, other.getItems_In()))) &&
            ((this.partners_In==null && other.getPartners_In()==null) || 
             (this.partners_In!=null &&
              java.util.Arrays.equals(this.partners_In, other.getPartners_In())));
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
        if (getHearder_In() != null) {
            _hashCode += getHearder_In().hashCode();
        }
        if (getItems_In() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItems_In());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItems_In(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPartners_In() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPartners_In());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPartners_In(), i);
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
        new org.apache.axis.description.TypeDesc(DT_Simular_Crear_Proforma_Request.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", "DT_Simular_Crear_Proforma_Request"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hearder_In");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Hearder_In"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">DT_Simular_Crear_Proforma_Request>Hearder_In"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("items_In");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Items_In"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Request>Items_In>Item"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "Item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partners_In");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Partners_In"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Request>Partners_In>Item"));
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
