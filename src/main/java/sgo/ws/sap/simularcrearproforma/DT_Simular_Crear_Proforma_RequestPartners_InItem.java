/**
 * DT_Simular_Crear_Proforma_RequestPartners_InItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sgo.ws.sap.simularcrearproforma;

public class DT_Simular_Crear_Proforma_RequestPartners_InItem  implements java.io.Serializable {
    private java.lang.String funInterlocutor;

    private java.lang.String codInterlocutor;

    private java.lang.String posicion;

    public DT_Simular_Crear_Proforma_RequestPartners_InItem() {
    }

    public DT_Simular_Crear_Proforma_RequestPartners_InItem(
           java.lang.String funInterlocutor,
           java.lang.String codInterlocutor,
           java.lang.String posicion) {
           this.funInterlocutor = funInterlocutor;
           this.codInterlocutor = codInterlocutor;
           this.posicion = posicion;
    }


    /**
     * Gets the funInterlocutor value for this DT_Simular_Crear_Proforma_RequestPartners_InItem.
     * 
     * @return funInterlocutor
     */
    public java.lang.String getFunInterlocutor() {
        return funInterlocutor;
    }


    /**
     * Sets the funInterlocutor value for this DT_Simular_Crear_Proforma_RequestPartners_InItem.
     * 
     * @param funInterlocutor
     */
    public void setFunInterlocutor(java.lang.String funInterlocutor) {
        this.funInterlocutor = funInterlocutor;
    }


    /**
     * Gets the codInterlocutor value for this DT_Simular_Crear_Proforma_RequestPartners_InItem.
     * 
     * @return codInterlocutor
     */
    public java.lang.String getCodInterlocutor() {
        return codInterlocutor;
    }


    /**
     * Sets the codInterlocutor value for this DT_Simular_Crear_Proforma_RequestPartners_InItem.
     * 
     * @param codInterlocutor
     */
    public void setCodInterlocutor(java.lang.String codInterlocutor) {
        this.codInterlocutor = codInterlocutor;
    }


    /**
     * Gets the posicion value for this DT_Simular_Crear_Proforma_RequestPartners_InItem.
     * 
     * @return posicion
     */
    public java.lang.String getPosicion() {
        return posicion;
    }


    /**
     * Sets the posicion value for this DT_Simular_Crear_Proforma_RequestPartners_InItem.
     * 
     * @param posicion
     */
    public void setPosicion(java.lang.String posicion) {
        this.posicion = posicion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DT_Simular_Crear_Proforma_RequestPartners_InItem)) return false;
        DT_Simular_Crear_Proforma_RequestPartners_InItem other = (DT_Simular_Crear_Proforma_RequestPartners_InItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.funInterlocutor==null && other.getFunInterlocutor()==null) || 
             (this.funInterlocutor!=null &&
              this.funInterlocutor.equals(other.getFunInterlocutor()))) &&
            ((this.codInterlocutor==null && other.getCodInterlocutor()==null) || 
             (this.codInterlocutor!=null &&
              this.codInterlocutor.equals(other.getCodInterlocutor()))) &&
            ((this.posicion==null && other.getPosicion()==null) || 
             (this.posicion!=null &&
              this.posicion.equals(other.getPosicion())));
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
        if (getFunInterlocutor() != null) {
            _hashCode += getFunInterlocutor().hashCode();
        }
        if (getCodInterlocutor() != null) {
            _hashCode += getCodInterlocutor().hashCode();
        }
        if (getPosicion() != null) {
            _hashCode += getPosicion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DT_Simular_Crear_Proforma_RequestPartners_InItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Request>Partners_In>Item"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("funInterlocutor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FunInterlocutor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codInterlocutor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CodInterlocutor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("posicion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Posicion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
