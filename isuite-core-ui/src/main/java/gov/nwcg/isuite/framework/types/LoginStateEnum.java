/**
 * 
 */
package gov.nwcg.isuite.framework.types;

/**
 * Represents the state of a user logging into the isuite system.
 * 
 * @author doug
 * 
 */
public enum LoginStateEnum {
   
   /*
    * user cannot login because their account has expired. (site
    * only)
    */
   ACCOUNT_EXPIRED,
   
   /*
    * user has successfully logged in but the account will expire soon (site
    * only)
    */
   ACCOUNT_MAY_EXPIRE,
   
   /*
    * successful
    */
   OK,

   /*
    * user has no account in isuite installation (site or enterprise)
    */
   NO_ACCOUNT_ISUITE,

   /*
    * user has no account in LDAP system (enterprise only)
    */
   NO_ACCOUNT_LDAP,

   /*
    * password does not match user name (site or enterprise)
    */
   INVALID_PASSWORD,

   /*
    * user has been disabled in the isuite installation (site or enterprise)
    */
   DISABLED_ISUITE,

   /*
    * user has been disabled in the LDAP system (enterprise only)
    */
   DISABLED_LDAP,

   /*
    * user has not been granted access to isuite by LDAP (enterprise only)
    */
   NO_ACCESS,

   /*
    * user is already logged into the system (site or enterprise)
    */
   CONCURRENT_LOGIN,

   /*
    * user's password has expired (site only)
    */
   PASSWORD_EXPIRED,

   /*
    * user has unsuccessfully loggin too many times (site only)
    */
   LOGIN_COUNT_EXCEEDED,

   /*
    * system has not yet been initialized and the first user must be the setup
    * user (site only)
    */
   MUST_BE_SETUP_USER,

   /*
    * user has successfully logged in but the password is will expire soon (site
    * only)
    */
   PASSWORD_MAY_EXPIRE,
   
   /*
    * User is a setup (creating site admin account) user
    */
   SETUP_USER;
}
