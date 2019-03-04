/**
 * DT_Simular_Crear_Proforma_RequestHearder_In.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sgo.ws.sap.simularcrearproforma;

public class DT_Simular_Crear_Proforma_RequestHearder_In  implements java.io.Serializable {
    private java.lang.String docVenta;

    private java.lang.String orgVenta;

    private java.lang.String canal;

    private java.lang.String sector;

    private java.util.Date fecValida_De;

    private java.util.Date fecValida_A;

    private java.lang.String moneda;

    private java.lang.String proceso;

    public DT_Simular_Crear_Proforma_RequestHearder_In() {
    }

    public DT_Simular_Crear_Proforma_RequestHearder_In(
           java.lang.String docVenta,
           java.lang.String orgVenta,
           java.lang.String canal,
           java.lang.String sector,
           java.util.Date fecValida_De,
           java.util.Date fecValida_A,
           java.lang.String moneda,
           java.lang.String proceso) {
           this.docVenta = docVenta;
           this.orgVenta = orgVenta;
           this.canal = canal;
           this.sector = sector;
           this.fecValida_De = fecValida_De;
           this.fecValida_A = fecValida_A;
           this.moneda = moneda;
           this.proceso = proceso;
    }


    /**
     * Gets the docVenta value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @return docVenta
     */
    public java.lang.String getDocVenta() {
        return docVenta;
    }


    /**
     * Sets the docVenta value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @param docVenta
     */
    public void setDocVenta(java.lang.String docVenta) {
        this.docVenta = docVenta;
    }


    /**
     * Gets the orgVenta value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @return orgVenta
     */
    public java.lang.String getOrgVenta() {
        return orgVenta;
    }


    /**
     * Sets the orgVenta value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @param orgVenta
     */
    public void setOrgVenta(java.lang.String orgVenta) {
        this.orgVenta = orgVenta;
    }


    /**
     * Gets the canal value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @return canal
     */
    public java.lang.String getCanal() {
        return canal;
    }


    /**
     * Sets the canal value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @param canal
     */
    public void setCanal(java.lang.String canal) {
        this.canal = canal;
    }


    /**
     * Gets the sector value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @return sector
     */
    public java.lang.String getSector() {
        return sector;
    }


    /**
     * Sets the sector value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @param sector
     */
    public void setSector(java.lang.String sector) {
        this.sector = sector;
    }


    /**
     * Gets the fecValida_De value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @return fecValida_De
     */
    public java.util.Date getFecValida_De() {
        return fecValida_De;
    }


    /**
     * Sets the fecValida_De value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @param fecValida_De
     */
    public void setFecValida_De(java.util.Date fecValida_De) {
        this.fecValida_De = fecValida_De;
    }


    /**
     * Gets the fecValida_A value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @return fecValida_A
     */
    public java.util.Date getFecValida_A() {
        return fecValida_A;
    }


    /**
     * Sets the fecValida_A value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @param fecValida_A
     */
    public void setFecValida_A(java.util.Date fecValida_A) {
        this.fecValida_A = fecValida_A;
    }


    /**
     * Gets the moneda value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @return moneda
     */
    public java.lang.String getMoneda() {
        return moneda;
    }


    /**
     * Sets the moneda value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @param moneda
     */
    public void setMoneda(java.lang.String moneda) {
        this.moneda = moneda;
    }


    /**
     * Gets the proceso value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @return proceso
     */
    public java.lang.String getProceso() {
        return proceso;
    }


    /**
     * Sets the proceso value for this DT_Simular_Crear_Proforma_RequestHearder_In.
     * 
     * @param proceso
     */
    public void setProceso(java.lang.String proceso) {
        this.proceso = proceso;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DT_Simular_Crear_Proforma_RequestHearder_In)) return false;
        DT_Simular_Crear_Proforma_RequestHearder_In other = (DT_Simular_Crear_Proforma_RequestHearder_In) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.docVenta==null && other.getDocVenta()==null) || 
             (this.docVenta!=null &&
              this.docVenta.equals(other.getDocVenta()))) &&
            ((this.orgVenta==null && other.getOrgVenta()==null) || 
             (this.orgVenta!=null &&
              this.orgVenta.equals(other.getOrgVenta()))) &&
            ((this.canal==null && other.getCanal()==null) || 
             (this.canal!=null &&
              this.canal.equals(other.getCanal()))) &&
            ((this.sector==null && other.getSector()==null) || 
             (this.sector!=null &&
              this.sector.equals(other.getSector()))) &&
            ((this.fecValida_De==null && other.getFecValida_De()==null) || 
             (this.fecValida_De!=null &&
              this.fecValida_De.equals(other.getFecValida_De()))) &&
            ((this.fecValida_A==null && other.getFecValida_A()==null) || 
             (this.fecValida_A!=null &&
              this.fecValida_A.equals(other.getFecValida_A()))) &&
            ((this.moneda==null && other.getMoneda()==null) || 
             (this.moneda!=null &&
              this.moneda.equals(other.getMoneda()))) &&
            ((this.proceso==null && other.getProceso()==null) || 
             (this.proceso!=null &&
              this.proceso.equals(other.getProceso())));
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
        if (getDocVenta() != null) {
            _hashCode += getDocVenta().hashCode();
        }
        if (getOrgVenta() != null) {
            _hashCode += getOrgVenta().hashCode();
        }
        if (getCanal() != null) {
            _hashCode += getCanal().hashCode();
        }
        if (getSector() != null) {
            _hashCode += getSector().hashCode();
        }
        if (getFecValida_De() != null) {
            _hashCode += getFecValida_De().hashCode();
        }
        if (getFecValida_A() != null) {
            _hashCode += getFecValida_A().hashCode();
        }
        if (getMoneda() != null) {
            _hashCode += getMoneda().hashCode();
        }
        if (getProceso() != null) {
            _hashCode += getProceso().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DT_Simular_Crear_Proforma_RequestHearder_In.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">DT_Simular_Crear_Proforma_Request>Hearder_In"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docVenta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DocVenta"));
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
        elemField.setFieldName("canal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Canal"));
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
        elemField.setFieldName("fecValida_De");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FecValida_De"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecValida_A");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FecValida_A"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("moneda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Moneda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("proceso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Proceso"));
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
