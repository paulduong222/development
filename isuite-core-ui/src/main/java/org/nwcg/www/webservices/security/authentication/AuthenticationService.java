/**
 * AuthenticationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.nwcg.www.webservices.security.authentication;

public interface AuthenticationService extends javax.xml.rpc.Service {
    public java.lang.String getAuthenticationSoap11Address();

    public org.nwcg.www.webservices.security.authentication.Authentication getAuthenticationSoap11() throws javax.xml.rpc.ServiceException;

    public org.nwcg.www.webservices.security.authentication.Authentication getAuthenticationSoap11(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
