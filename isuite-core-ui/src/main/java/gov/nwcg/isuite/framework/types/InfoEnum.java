package gov.nwcg.isuite.framework.types;


public enum InfoEnum {
   _0010_SETUP_USER_PASSWORD_CHANGED("0010"),
   _0011_CREATE_FIRST_ADMIN_ACCOUNT("0011"),
   _0013_PASSWORD_RESET("0013"),
   _0014_FIRST_TIME_LOGGING_IN_PASSWORD_MUST_CHANGE("0014"),
   _0015_PASSWORD_EXPIRED("0015"),
   _0022_DISABLED_USER("0022"),
   _0026_ENABLED_USER("0026"),
   _0028_DELETED_USER("0028"),
   _0030_DATA_SAVED("0030"),
   _0038_USER_ACCOUNTS_EXPORTED("0038"),
   _0042_USER_ACCOUNTS_IMPORTED("0042"),
   _0130_NO_CHANGE_TO_DEFAULT_ACCT_CODE("0130"),
   _0164_ACCOUNT_MAY_EXPIRE("0164"),
   _CREATING_FIRST_ADMIN(""),
   _PASSWORD_RESET(""),
   _PASSWORD_EXPIRED(""); 
   
   private final String infoNumber;
   
   public String getInfoName() {
      return this.infoNumber;
   }
   
   InfoEnum(String infoNumber) {
      this.infoNumber = infoNumber;
   }
   
   /**
    * This method will return the number representing
    * this informational message in the usecases if it exists
    * in the use cases, and if it doesn't then it returns
    * the name of the enum.
    */
   @Override
   public String toString() {
      return ("info." + (this.infoNumber.equals("") 
               ? this.name().toLowerCase() 
               : this.infoNumber));
   }
}
