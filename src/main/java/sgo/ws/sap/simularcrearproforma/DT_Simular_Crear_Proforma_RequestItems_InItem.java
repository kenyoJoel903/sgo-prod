/**
 * DT_Simular_Crear_Proforma_RequestItems_InItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sgo.ws.sap.simularcrearproforma;

public class DT_Simular_Crear_Proforma_RequestItems_InItem  implements java.io.Serializable {
    private java.lang.String posicion;

    private java.lang.String material;

    private java.lang.String centro;

    private java.math.BigDecimal cantidad;

    private java.lang.String unidadMedida;

    public DT_Simular_Crear_Proforma_RequestItems_InItem() {
    }

    public DT_Simular_Crear_Proforma_RequestItems_InItem(
           java.lang.String posicion,
           java.lang.String material,
           java.lang.String centro,
           java.math.BigDecimal cantidad,
           java.lang.String unidadMedida) {
           this.posicion = posicion;
           this.material = material;
           this.centro = centro;
           this.cantidad = cantidad;
           this.unidadMedida = unidadMedida;
    }


    /**
     * Gets the posicion value for this DT_Simular_Crear_Proforma_RequestItems_InItem.
     * 
     * @return posicion
     */
    public java.lang.String getPosicion() {
        return posicion;
    }


    /**
     * Sets the posicion value for this DT_Simular_Crear_Proforma_RequestItems_InItem.
     * 
     * @param posicion
     */
    public void setPosicion(java.lang.String posicion) {
        this.posicion = posicion;
    }


    /**
     * Gets the material value for this DT_Simular_Crear_Proforma_RequestItems_InItem.
     * 
     * @return material
     */
    public java.lang.String getMaterial() {
        return material;
    }


    /**
     * Sets the material value for this DT_Simular_Crear_Proforma_RequestItems_InItem.
     * 
     * @param material
     */
    public void setMaterial(java.lang.String material) {
        this.material = material;
    }


    /**
     * Gets the centro value for this DT_Simular_Crear_Proforma_RequestItems_InItem.
     * 
     * @return centro
     */
    public java.lang.String getCentro() {
        return centro;
    }


    /**
     * Sets the centro value for this DT_Simular_Crear_Proforma_RequestItems_InItem.
     * 
     * @param centro
     */
    public void setCentro(java.lang.String centro) {
        this.centro = centro;
    }


    /**
     * Gets the cantidad value for this DT_Simular_Crear_Proforma_RequestItems_InItem.
     * 
     * @return cantidad
     */
    public java.math.BigDecimal getCantidad() {
        return cantidad;
    }


    /**
     * Sets the cantidad value for this DT_Simular_Crear_Proforma_RequestItems_InItem.
     * 
     * @param cantidad
     */
    public void setCantidad(java.math.BigDecimal cantidad) {
        this.cantidad = cantidad;
    }


    /**
     * Gets the unidadMedida value for this DT_Simular_Crear_Proforma_RequestItems_InItem.
     * 
     * @return unidadMedida
     */
    public java.lang.String getUnidadMedida() {
        return unidadMedida;
    }


    /**
     * Sets the unidadMedida value for this DT_Simular_Crear_Proforma_RequestItems_InItem.
     * 
     * @param unidadMedida
     */
    public void setUnidadMedida(java.lang.String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DT_Simular_Crear_Proforma_RequestItems_InItem)) return false;
        DT_Simular_Crear_Proforma_RequestItems_InItem other = (DT_Simular_Crear_Proforma_RequestItems_InItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.posicion==null && other.getPosicion()==null) || 
             (this.posicion!=null &&
              this.posicion.equals(other.getPosicion()))) &&
            ((this.material==null && other.getMaterial()==null) || 
             (this.material!=null &&
              this.material.equals(other.getMaterial()))) &&
            ((this.centro==null && other.getCentro()==null) || 
             (this.centro!=null &&
              this.centro.equals(other.getCentro()))) &&
            ((this.cantidad==null && other.getCantidad()==null) || 
             (this.cantidad!=null &&
              this.cantidad.equals(other.getCantidad()))) &&
            ((this.unidadMedida==null && other.getUnidadMedida()==null) || 
             (this.unidadMedida!=null &&
              this.unidadMedida.equals(other.getUnidadMedida())));
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
        if (getPosicion() != null) {
            _hashCode += getPosicion().hashCode();
        }
        if (getMaterial() != null) {
            _hashCode += getMaterial().hashCode();
        }
        if (getCentro() != null) {
            _hashCode += getCentro().hashCode();
        }
        if (getCantidad() != null) {
            _hashCode += getCantidad().hashCode();
        }
        if (getUnidadMedida() != null) {
            _hashCode += getUnidadMedida().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DT_Simular_Crear_Proforma_RequestItems_InItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Simular_Crear_Proforma_Request>Items_In>Item"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("posicion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Posicion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("material");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Material"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("centro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Centro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cantidad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Cantidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unidadMedida");
        elemField.setXmlName(new javax.xml.namespace.QName("", "UnidadMedida"));
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
