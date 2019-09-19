package gov.nwcg.isuite.framework.types;

/**
 * Available authorities.
 * 
 * @author bsteiner
 */
public enum IsuiteAuthorityNameEnum {
   
   ACCEPTED_AUTHORITY ("ROLE_ACCEPTED_AUTHORITY"),
   DECLINED_AUTHORITY ("ROLE_DECLINED_AUTHORITY"),
   LOGGED_IN_AUTHORITY ("ROLE_LOGGED_IN_AUTHORITY"),
   CHANGE_PASSWORD_AUTHORITY ("ROLE_CHANGE_PASSWORD_AUTHORITY"),
   SETUP_USER_AUTHORITY ("ROLE_SETUP_USER_AUTHORITY");
   
   private final String myName;
   
   public String getName() {
      return this.myName;
   }
   
   IsuiteAuthorityNameEnum(String authorityName) {
      this.myName = authorityName;
   }
}
