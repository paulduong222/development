/**
 * AuthenticationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.nwcg.www.webservices.security.authentication;

import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Calendar;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;

import org.apache.axis.message.SOAPHeaderElement;

import com.ibm.xml.crypto.util.Base64;

public class AuthenticationServiceLocator extends org.apache.axis.client.Service implements org.nwcg.www.webservices.security.authentication.AuthenticationService {
	public String userName;
	public String pwd;
	
    public AuthenticationServiceLocator() {
    }


    public AuthenticationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AuthenticationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AuthenticationSoap11
    //private java.lang.String AuthenticationSoap11_address = "https://nap-ft.nwcg.gov:443/rossTR/authenticationService";
    private java.lang.String AuthenticationSoap11_address = "https://nap.nwcg.gov:443/rossTR/authenticationService";
	// For NAP Field Test inside NITC
	 //private java.lang.String AuthenticationSoap11_address = "https://magik.nwcg.gov:443/rossTR/authenticationService";

    public java.lang.String getAuthenticationSoap11Address() {
        return AuthenticationSoap11_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AuthenticationSoap11WSDDServiceName = "AuthenticationSoap11";

    public java.lang.String getAuthenticationSoap11WSDDServiceName() {
        return AuthenticationSoap11WSDDServiceName;
    }

    public void setAuthenticationSoap11WSDDServiceName(java.lang.String name) {
        AuthenticationSoap11WSDDServiceName = name;
    }

    public org.nwcg.www.webservices.security.authentication.Authentication getAuthenticationSoap11() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AuthenticationSoap11_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAuthenticationSoap11(endpoint);
    }

    public org.nwcg.www.webservices.security.authentication.Authentication getAuthenticationSoap11(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.nwcg.www.webservices.security.authentication.AuthenticationSoap11Stub _stub = new org.nwcg.www.webservices.security.authentication.AuthenticationSoap11Stub(portAddress, this);
            _stub.setPortName(getAuthenticationSoap11WSDDServiceName());
            /*dan - begin */
            try{
            	this.addWsSecurityHeader(_stub );
                //System.out.println(_stub.getHeaders().length);
                //for(SOAPHeaderElement elem : _stub.getHeaders()){
                //	System.out.println(elem.getAsString());
                //}
            }catch(Exception ee){
            	System.out.println(ee.getMessage());
            }
            /*dan - end */
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAuthenticationSoap11EndpointAddress(java.lang.String address) {
        AuthenticationSoap11_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.nwcg.www.webservices.security.authentication.Authentication.class.isAssignableFrom(serviceEndpointInterface)) {
                org.nwcg.www.webservices.security.authentication.AuthenticationSoap11Stub _stub = new org.nwcg.www.webservices.security.authentication.AuthenticationSoap11Stub(new java.net.URL(AuthenticationSoap11_address), this);
                _stub.setPortName(getAuthenticationSoap11WSDDServiceName());
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
        if ("AuthenticationSoap11".equals(inputPortName)) {
            return getAuthenticationSoap11();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

	private void addWsSecurityHeader(org.apache.axis.client.Stub binding) throws SOAPException {

		// Create the top-level WS-Security SOAP header XML name.
		QName headerName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security", "wsse");
		SOAPHeaderElement securityElement = new SOAPHeaderElement(headerName);
		SOAPElement userTokenElement = securityElement.addChildElement ("UsernameToken", "wsse","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		SOAPElement childElement = userTokenElement.addChildElement("Username", "wsse","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		childElement.addTextNode(userName);
		childElement = userTokenElement.addChildElement("Password", "wsse","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		childElement.addTextNode(pwd);
		//QName qname = new QName("TYPE","","http://www.nwcg.org/webservices/security/getUsers");
		SOAPFactory soapFactory = SOAPFactory.newInstance();
		//childElement.addAttribute(soapFactory.createName("Type", "", ""), "WSSE_PASSWORD_TEXT");
		childElement = userTokenElement.addChildElement("Nonce", "wsse","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		String nonce="UtahJazz!234Ball";
		Base64 base64 = new Base64();
		nonce=base64.encode(nonce.getBytes());
		childElement.addTextNode(nonce);
		childElement = userTokenElement.addChildElement("Created", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		childElement.addTextNode(DateUtil.toDateStringDash(Calendar.getInstance().getTime())+" MST");

		//  no intermediate actors are involved.
		securityElement.setActor(null);
		// not important, "wsse" is standard
		securityElement.setPrefix("wsse");
		securityElement.setMustUnderstand(false);

		// Finally, attach the header to the binding.
		binding.setHeader(securityElement);
	}	
    
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/authentication", "AuthenticationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/authentication", "AuthenticationSoap11"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AuthenticationSoap11".equals(portName)) {
            setAuthenticationSoap11EndpointAddress(address);
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
