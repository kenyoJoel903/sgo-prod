/**
 * DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sgo.ws.sap.datamaestracliente;

public class DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem  implements java.io.Serializable {
    private java.lang.String sign;

    private java.lang.String option;

    private java.lang.String low;

    private java.lang.String high;

    public DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem() {
    }

    public DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem(
           java.lang.String sign,
           java.lang.String option,
           java.lang.String low,
           java.lang.String high) {
           this.sign = sign;
           this.option = option;
           this.low = low;
           this.high = high;
    }


    /**
     * Gets the sign value for this DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem.
     * 
     * @return sign
     */
    public java.lang.String getSign() {
        return sign;
    }


    /**
     * Sets the sign value for this DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem.
     * 
     * @param sign
     */
    public void setSign(java.lang.String sign) {
        this.sign = sign;
    }


    /**
     * Gets the option value for this DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem.
     * 
     * @return option
     */
    public java.lang.String getOption() {
        return option;
    }


    /**
     * Sets the option value for this DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem.
     * 
     * @param option
     */
    public void setOption(java.lang.String option) {
        this.option = option;
    }


    /**
     * Gets the low value for this DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem.
     * 
     * @return low
     */
    public java.lang.String getLow() {
        return low;
    }


    /**
     * Sets the low value for this DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem.
     * 
     * @param low
     */
    public void setLow(java.lang.String low) {
        this.low = low;
    }


    /**
     * Gets the high value for this DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem.
     * 
     * @return high
     */
    public java.lang.String getHigh() {
        return high;
    }


    /**
     * Sets the high value for this DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem.
     * 
     * @param high
     */
    public void setHigh(java.lang.String high) {
        this.high = high;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem)) return false;
        DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem other = (DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sign==null && other.getSign()==null) || 
             (this.sign!=null &&
              this.sign.equals(other.getSign()))) &&
            ((this.option==null && other.getOption()==null) || 
             (this.option!=null &&
              this.option.equals(other.getOption()))) &&
            ((this.low==null && other.getLow()==null) || 
             (this.low!=null &&
              this.low.equals(other.getLow()))) &&
            ((this.high==null && other.getHigh()==null) || 
             (this.high!=null &&
              this.high.equals(other.getHigh())));
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
        if (getSign() != null) {
            _hashCode += getSign().hashCode();
        }
        if (getOption() != null) {
            _hashCode += getOption().hashCode();
        }
        if (getLow() != null) {
            _hashCode += getLow().hashCode();
        }
        if (getHigh() != null) {
            _hashCode += getHigh().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DT_Data_Maestra_Cliente_Proforma_RequestCodCliente_InItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:petroperu.com.pe:pmerp:sd:proforma", ">>DT_Data_Maestra_Cliente_Proforma_Request>CodCliente_In>Item"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sign");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Sign"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("option");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Option"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("low");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Low"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("high");
        elemField.setXmlName(new javax.xml.namespace.QName("", "High"));
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
