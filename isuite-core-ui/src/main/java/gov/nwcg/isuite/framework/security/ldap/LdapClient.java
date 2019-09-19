package gov.nwcg.isuite.framework.security.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

@SuppressWarnings("unchecked")
public class LdapClient{
	private LdapProperties ldapProperties;
	
	public LdapClient(LdapProperties props){
		super();
		this.ldapProperties=props;
	}
	
	private Hashtable getEnvironment(String user,String pwd) {
        Hashtable env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY,ldapProperties.getLdapFactory());
        env.put(Context.PROVIDER_URL, ldapProperties.getLdapUrl());
        env.put(Context.SECURITY_AUTHENTICATION, ldapProperties.getLdapAuthType());
        env.put(Context.SECURITY_PRINCIPAL, "cn="+user+",ou=people,dc=sabioso,dc=com");
        env.put(Context.SECURITY_CREDENTIALS, pwd);

        return env;
	}

	public boolean checkCredentials(String user,String pwd) throws Exception {
	
        DirContext ctx = null;
        
        try {
        	
            ctx = new InitialDirContext(getEnvironment(user,pwd));

            // todo: validate isuiteUser object class
            
            
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e) {
                }
            }
        }

        if(null!=ctx)
        	return true;
        else
        	return false;
	}
	
	public boolean changePassword() throws Exception {
		return false;
	}

	public boolean isValidUser(String user) throws Exception {
		return false;
	}
	
	
}
