/**
 * DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sgo.ws.sap.datamaestracliente;

public class DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem  implements java.io.Serializable {
    private java.lang.String codCliente;

    private java.lang.String orgVenta;

    private java.lang.String desOrgVenta;

    private java.lang.String canalDistrib;

    private java.lang.String desCanalDistrib;

    private java.lang.String sector;

    private java.lang.String desSector;

    public DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem() {
    }

    public DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem(
           java.lang.String codCliente,
           java.lang.String orgVenta,
           java.lang.String desOrgVenta,
           java.lang.String canalDistrib,
           java.lang.String desCanalDistrib,
           java.lang.String sector,
           java.lang.String desSector) {
           this.codCliente = codCliente;
           this.orgVenta = orgVenta;
           this.desOrgVenta = desOrgVenta;
           this.canalDistrib = canalDistrib;
           this.desCanalDistrib = desCanalDistrib;
           this.sector = sector;
           this.desSector = desSector;
    }


    /**
     * Gets the codCliente value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @return codCliente
     */
    public java.lang.String getCodCliente() {
        return codCliente;
    }


    /**
     * Sets the codCliente value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @param codCliente
     */
    public void setCodCliente(java.lang.String codCliente) {
        this.codCliente = codCliente;
    }


    /**
     * Gets the orgVenta value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @return orgVenta
     */
    public java.lang.String getOrgVenta() {
        return orgVenta;
    }


    /**
     * Sets the orgVenta value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @param orgVenta
     */
    public void setOrgVenta(java.lang.String orgVenta) {
        this.orgVenta = orgVenta;
    }


    /**
     * Gets the desOrgVenta value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @return desOrgVenta
     */
    public java.lang.String getDesOrgVenta() {
        return desOrgVenta;
    }


    /**
     * Sets the desOrgVenta value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @param desOrgVenta
     */
    public void setDesOrgVenta(java.lang.String desOrgVenta) {
        this.desOrgVenta = desOrgVenta;
    }


    /**
     * Gets the canalDistrib value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @return canalDistrib
     */
    public java.lang.String getCanalDistrib() {
        return canalDistrib;
    }


    /**
     * Sets the canalDistrib value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @param canalDistrib
     */
    public void setCanalDistrib(java.lang.String canalDistrib) {
        this.canalDistrib = canalDistrib;
    }


    /**
     * Gets the desCanalDistrib value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @return desCanalDistrib
     */
    public java.lang.String getDesCanalDistrib() {
        return desCanalDistrib;
    }


    /**
     * Sets the desCanalDistrib value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @param desCanalDistrib
     */
    public void setDesCanalDistrib(java.lang.String desCanalDistrib) {
        this.desCanalDistrib = desCanalDistrib;
    }


    /**
     * Gets the sector value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @return sector
     */
    public java.lang.String getSector() {
        return sector;
    }


    /**
     * Sets the sector value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @param sector
     */
    public void setSector(java.lang.String sector) {
        this.sector = sector;
    }


    /**
     * Gets the desSector value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @return desSector
     */
    public java.lang.String getDesSector() {
        return desSector;
    }


    /**
     * Sets the desSector value for this DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.
     * 
     * @param desSector
     */
    public void setDesSector(java.lang.String desSector) {
        this.desSector = desSector;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem)) return false;
        DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem other = (DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem) obj;
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
            ((this.orgVenta==null && other.getOrgVenta()==null) || 
             (this.orgVenta!=null &&
              this.orgVenta.equals(other.getOrgVenta()))) &&
            ((this.desOrgVenta==null && other.getDesOrgVenta()==null) || 
             (this.desOrgVenta!=null &&
              this.desOrgVenta.equals(other.getDesOrgVenta()))) &&
            ((this.canalDistrib==null && other.getCanalDistrib()==null) || 
             (this.canalDistrib!=null &&
              this.canalDistrib.equals(other.getCanalDistrib()))) &&
            ((this.desCanalDistrib==null && other.getDesCanalDistrib()==null) || 
             (this.desCanalDistrib!=null &&
              this.desCanalDistrib.equals(other.getDesCanalDistrib()))) &&
            ((this.sector==null && other.getSector()==null) || 
             (this.sector!=null &&
              this.sector.equals(other.getSector()))) &&
            ((this.desSector==null && other.getDesSector()==null) || 
             (this.desSector!=null &&
              this.desSector.equals(other.getDesSector())));
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
        if (getOrgVenta() != null) {
            _hashCode += getOrgVenta().hashCode();
        }
        if (getDesOrgVenta() != null) {
            _hashCode += getDesOrgVenta().hashCode();
        }
        if (getCanalDistrib() != null) {
            _hashCode += getCanalDistrib().hashCode();
        }
        if (getDesCanalDistrib() != null) {
            _hashCode += getDesCanalDistrib().hashCode();
        }
        if (getSector() != null) {
            _hashCode += getSector().hashCode();
        }
        if (getDesSector() != null) {
            _hashCode += getDesSector().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DT_Data_Maestra_Cliente_Proforma_ResponseDatosAreaVenta_OutItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Data_Maestra_Cliente_Proforma_Response>DatosAreaVenta_Out>Item"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CodCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgVenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OrgVenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desOrgVenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DesOrgVenta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canalDistrib");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CanalDistrib"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desCanalDistrib");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DesCanalDistrib"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sector");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Sector"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desSector");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DesSector"));
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
