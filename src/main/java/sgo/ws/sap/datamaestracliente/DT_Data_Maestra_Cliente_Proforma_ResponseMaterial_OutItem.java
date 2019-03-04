/**
 * DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sgo.ws.sap.datamaestracliente;

public class DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem  implements java.io.Serializable {
    private java.lang.String codMaterial;

    private java.lang.String desMaterial;

    private java.util.Date fecValidez_Fin;

    private java.util.Date fecValidez_Ini;

    public DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem() {
    }

    public DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem(
           java.lang.String codMaterial,
           java.lang.String desMaterial,
           java.util.Date fecValidez_Fin,
           java.util.Date fecValidez_Ini) {
           this.codMaterial = codMaterial;
           this.desMaterial = desMaterial;
           this.fecValidez_Fin = fecValidez_Fin;
           this.fecValidez_Ini = fecValidez_Ini;
    }


    /**
     * Gets the codMaterial value for this DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem.
     * 
     * @return codMaterial
     */
    public java.lang.String getCodMaterial() {
        return codMaterial;
    }


    /**
     * Sets the codMaterial value for this DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem.
     * 
     * @param codMaterial
     */
    public void setCodMaterial(java.lang.String codMaterial) {
        this.codMaterial = codMaterial;
    }


    /**
     * Gets the desMaterial value for this DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem.
     * 
     * @return desMaterial
     */
    public java.lang.String getDesMaterial() {
        return desMaterial;
    }


    /**
     * Sets the desMaterial value for this DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem.
     * 
     * @param desMaterial
     */
    public void setDesMaterial(java.lang.String desMaterial) {
        this.desMaterial = desMaterial;
    }


    /**
     * Gets the fecValidez_Fin value for this DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem.
     * 
     * @return fecValidez_Fin
     */
    public java.util.Date getFecValidez_Fin() {
        return fecValidez_Fin;
    }


    /**
     * Sets the fecValidez_Fin value for this DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem.
     * 
     * @param fecValidez_Fin
     */
    public void setFecValidez_Fin(java.util.Date fecValidez_Fin) {
        this.fecValidez_Fin = fecValidez_Fin;
    }


    /**
     * Gets the fecValidez_Ini value for this DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem.
     * 
     * @return fecValidez_Ini
     */
    public java.util.Date getFecValidez_Ini() {
        return fecValidez_Ini;
    }


    /**
     * Sets the fecValidez_Ini value for this DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem.
     * 
     * @param fecValidez_Ini
     */
    public void setFecValidez_Ini(java.util.Date fecValidez_Ini) {
        this.fecValidez_Ini = fecValidez_Ini;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem)) return false;
        DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem other = (DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codMaterial==null && other.getCodMaterial()==null) || 
             (this.codMaterial!=null &&
              this.codMaterial.equals(other.getCodMaterial()))) &&
            ((this.desMaterial==null && other.getDesMaterial()==null) || 
             (this.desMaterial!=null &&
              this.desMaterial.equals(other.getDesMaterial()))) &&
            ((this.fecValidez_Fin==null && other.getFecValidez_Fin()==null) || 
             (this.fecValidez_Fin!=null &&
              this.fecValidez_Fin.equals(other.getFecValidez_Fin()))) &&
            ((this.fecValidez_Ini==null && other.getFecValidez_Ini()==null) || 
             (this.fecValidez_Ini!=null &&
              this.fecValidez_Ini.equals(other.getFecValidez_Ini())));
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
        if (getCodMaterial() != null) {
            _hashCode += getCodMaterial().hashCode();
        }
        if (getDesMaterial() != null) {
            _hashCode += getDesMaterial().hashCode();
        }
        if (getFecValidez_Fin() != null) {
            _hashCode += getFecValidez_Fin().hashCode();
        }
        if (getFecValidez_Ini() != null) {
            _hashCode += getFecValidez_Ini().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DT_Data_Maestra_Cliente_Proforma_ResponseMaterial_OutItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Data_Maestra_Cliente_Proforma_Response>Material_Out>Item"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codMaterial");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CodMaterial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desMaterial");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DesMaterial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecValidez_Fin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FecValidez_Fin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecValidez_Ini");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FecValidez_Ini"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
