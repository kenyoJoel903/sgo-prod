/**
 * DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sgo.ws.sap.datamaestracliente;

public class DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem  implements java.io.Serializable {
    private java.lang.String codCliente;

    private java.lang.String nroRuc;

    private java.lang.String nombre;

    private java.lang.String ramo;

    public DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem() {
    }

    public DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem(
           java.lang.String codCliente,
           java.lang.String nroRuc,
           java.lang.String nombre,
           java.lang.String ramo) {
           this.codCliente = codCliente;
           this.nroRuc = nroRuc;
           this.nombre = nombre;
           this.ramo = ramo;
    }


    /**
     * Gets the codCliente value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem.
     * 
     * @return codCliente
     */
    public java.lang.String getCodCliente() {
        return codCliente;
    }


    /**
     * Sets the codCliente value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem.
     * 
     * @param codCliente
     */
    public void setCodCliente(java.lang.String codCliente) {
        this.codCliente = codCliente;
    }


    /**
     * Gets the nroRuc value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem.
     * 
     * @return nroRuc
     */
    public java.lang.String getNroRuc() {
        return nroRuc;
    }


    /**
     * Sets the nroRuc value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem.
     * 
     * @param nroRuc
     */
    public void setNroRuc(java.lang.String nroRuc) {
        this.nroRuc = nroRuc;
    }


    /**
     * Gets the nombre value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem.
     * 
     * @return nombre
     */
    public java.lang.String getNombre() {
        return nombre;
    }


    /**
     * Sets the nombre value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem.
     * 
     * @param nombre
     */
    public void setNombre(java.lang.String nombre) {
        this.nombre = nombre;
    }


    /**
     * Gets the ramo value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem.
     * 
     * @return ramo
     */
    public java.lang.String getRamo() {
        return ramo;
    }


    /**
     * Sets the ramo value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem.
     * 
     * @param ramo
     */
    public void setRamo(java.lang.String ramo) {
        this.ramo = ramo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem)) return false;
        DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem other = (DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codCliente==null && other.getCodCliente()==null) || 
             (this.codCliente!=null &&
              this.codCliente.equals(other.getCodCliente()))) &&
            ((this.nroRuc==null && other.getNroRuc()==null) || 
             (this.nroRuc!=null &&
              this.nroRuc.equals(other.getNroRuc()))) &&
            ((this.nombre==null && other.getNombre()==null) || 
             (this.nombre!=null &&
              this.nombre.equals(other.getNombre()))) &&
            ((this.ramo==null && other.getRamo()==null) || 
             (this.ramo!=null &&
              this.ramo.equals(other.getRamo())));
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
        if (getCodCliente() != null) {
            _hashCode += getCodCliente().hashCode();
        }
        if (getNroRuc() != null) {
            _hashCode += getNroRuc().hashCode();
        }
        if (getNombre() != null) {
            _hashCode += getNombre().hashCode();
        }
        if (getRamo() != null) {
            _hashCode += getRamo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DT_Data_Maestra_Cliente_Proforma_ResponseDatosClientes_OutItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Data_Maestra_Cliente_Proforma_Response>DatosClientes_Out>Item"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CodCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nroRuc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NroRuc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ramo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Ramo"));
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
