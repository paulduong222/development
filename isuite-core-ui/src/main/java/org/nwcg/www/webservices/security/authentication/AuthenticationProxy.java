package org.nwcg.www.webservices.security.authentication;

public class AuthenticationProxy implements org.nwcg.www.webservices.security.authentication.Authentication {
	private String _endpoint = null;
	private org.nwcg.www.webservices.security.authentication.Authentication authentication = null;

	public AuthenticationProxy() {
		_initAuthenticationProxy();
	}

	public AuthenticationProxy(String endpoint) {
		_endpoint = endpoint;
		_initAuthenticationProxy();
	}

	private void _initAuthenticationProxy() {
		try {
			authentication = (new org.nwcg.www.webservices.security.authentication.AuthenticationServiceLocator()).getAuthenticationSoap11();
			if (authentication != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub)authentication)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
				else
					_endpoint = (String)((javax.xml.rpc.Stub)authentication)._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		}
		catch (javax.xml.rpc.ServiceException serviceException) {}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (authentication != null)
			((javax.xml.rpc.Stub)authentication)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public org.nwcg.www.webservices.security.authentication.Authentication getAuthentication() {
		if (authentication == null)
			_initAuthenticationProxy();
		return authentication;
	}

	public org.nwcg.www.webservices.security.authentication.AuthenticationResponse authentication(org.nwcg.www.webservices.security.authentication.AuthenticationRequest authenticationRequest) throws java.rmi.RemoteException{
		if (authentication == null)
			_initAuthenticationProxy();
		return authentication.authentication(authenticationRequest);
	}


}