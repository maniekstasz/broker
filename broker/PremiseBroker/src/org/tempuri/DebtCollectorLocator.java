/**
 * DebtCollectorLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class DebtCollectorLocator extends org.apache.axis.client.Service implements org.tempuri.DebtCollector {

    public DebtCollectorLocator() {
    }


    public DebtCollectorLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DebtCollectorLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IDebtCollector
    private java.lang.String BasicHttpBinding_IDebtCollector_address = "http://localhost:8113/DebtCollector/ep1";

    public java.lang.String getBasicHttpBinding_IDebtCollectorAddress() {
        return BasicHttpBinding_IDebtCollector_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IDebtCollectorWSDDServiceName = "BasicHttpBinding_IDebtCollector";

    public java.lang.String getBasicHttpBinding_IDebtCollectorWSDDServiceName() {
        return BasicHttpBinding_IDebtCollectorWSDDServiceName;
    }

    public void setBasicHttpBinding_IDebtCollectorWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IDebtCollectorWSDDServiceName = name;
    }

    public org.tempuri.IDebtCollector getBasicHttpBinding_IDebtCollector() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IDebtCollector_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IDebtCollector(endpoint);
    }

    public org.tempuri.IDebtCollector getBasicHttpBinding_IDebtCollector(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.BasicHttpBinding_IDebtCollectorStub _stub = new org.tempuri.BasicHttpBinding_IDebtCollectorStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IDebtCollectorWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IDebtCollectorEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IDebtCollector_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.IDebtCollector.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.BasicHttpBinding_IDebtCollectorStub _stub = new org.tempuri.BasicHttpBinding_IDebtCollectorStub(new java.net.URL(BasicHttpBinding_IDebtCollector_address), this);
                _stub.setPortName(getBasicHttpBinding_IDebtCollectorWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_IDebtCollector".equals(inputPortName)) {
            return getBasicHttpBinding_IDebtCollector();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "DebtCollector");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IDebtCollector"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IDebtCollector".equals(portName)) {
            setBasicHttpBinding_IDebtCollectorEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
