package org.nwcg.www.webservices.security.getUsers;

public class GetUsersProxy implements org.nwcg.www.webservices.security.getUsers.GetUsers {
	private String _endpoint = null;
	private org.nwcg.www.webservices.security.getUsers.GetUsers getUsers = null;

	public GetUsersProxy() {
		_initGetUsersProxy();
	}

	public GetUsersProxy(String endpoint) {
		_endpoint = endpoint;
		_initGetUsersProxy();
	}

	private void _initGetUsersProxy() {
		try {
			getUsers = (new org.nwcg.www.webservices.security.getUsers.GetUsersServiceLocator()).getGetUsersSoap11();
			if (getUsers != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub)getUsers)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
				else
					_endpoint = (String)((javax.xml.rpc.Stub)getUsers)._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		}
		catch (javax.xml.rpc.ServiceException serviceException) {}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (getUsers != null)
			((javax.xml.rpc.Stub)getUsers)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public org.nwcg.www.webservices.security.getUsers.GetUsers getGetUsers() {
		if (getUsers == null)
			_initGetUsersProxy();
		return getUsers;
	}

	public org.nwcg.www.webservices.security.getUsers.GetUsersResponse getUsers(org.nwcg.www.webservices.security.getUsers.GetUsersRequest getUsersRequest) throws java.rmi.RemoteException{
		if (getUsers == null)
			_initGetUsersProxy();
		return getUsers.getUsers(getUsersRequest);
	}


}