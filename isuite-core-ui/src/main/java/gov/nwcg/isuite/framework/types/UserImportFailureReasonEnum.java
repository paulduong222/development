package gov.nwcg.isuite.framework.types;

/**
 * Enumeration of the expected reasons for failure of an imported user.
 * 
 * @author bsteiner
 */
public enum UserImportFailureReasonEnum {
   USERNAME_ALREADY_EXISTS, //These values should match the corresponding isuite.properties file.
   MISSING_USERNAME,
   MISSING_FIRST_NAME,
   MISSING_LAST_NAME,
   MISSING_HOME_UNIT_CODE,
   MISSING_PDC_UNIT_CODE,
   NO_ASSOCIATED_ROLES,
   ADMINISTRATOR_WITH_OTHER_ROLES,
   INVALID_ROLE_LIST,
   UNKNOWN_REASON;

   @Override
   public String toString() {
      return this.name().toLowerCase();
   }
}
