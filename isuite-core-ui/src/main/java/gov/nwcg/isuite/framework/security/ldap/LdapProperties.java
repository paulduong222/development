/**
 * 
 */
package gov.nwcg.isuite.framework.security.ldap;

import java.util.Properties;

/**
 * @author dprice
 *
 */
public class LdapProperties extends Properties {
	private static final long serialVersionUID = -6444258542214856L;
	private final String LDAP_FACTORY="ldap.factory";
	private final String LDAP_URL="ldap.url";
	private final String LDAP_AUTHTYPE="ldap.authenticationtype";
	
	/**
	 * 
	 */
	public LdapProperties() {
	}

	/**
	 * @param defaults
	 */
	public LdapProperties(Properties defaults) {
		super(defaults);
	}

	public void setLdapUrl(String data) {
		super.setProperty(LDAP_URL, data);
	}

	public String getLdapUrl() {
		return super.getProperty(LDAP_URL);
	}

	public void setLdapFactory(String data) {
		super.setProperty(LDAP_FACTORY, data);
	}

	public String getLdapFactory() {
		return super.getProperty(LDAP_FACTORY);
	}

	public void setLdapAuthType(String data) {
		super.setProperty(LDAP_AUTHTYPE, data);
	}

	public String getLdapAuthType() {
		return super.getProperty(LDAP_AUTHTYPE);
	}
	
	
	
	
}
