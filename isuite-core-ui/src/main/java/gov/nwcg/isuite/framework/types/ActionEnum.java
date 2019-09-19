package gov.nwcg.isuite.framework.types;


/**
 * These enums will be used as keys to the messages in the properties
 * file.  These particular messages will only be used in dialog boxes.
 * 
 * @author ncollette
  */
public enum ActionEnum {
   _0017_CHANGE_EXPIRING_PASSWORD("0017"),
   _0033_CONTINUE_WITH_DELETION("0033"),
   _0041_USER_EXISTS_UPDATE_INFO("0041"),
   _0078_DELETE_SHARED_WORK_AREA("0078"),
   _0129_REPLACE_DEFAULT_ACCT_CODE("0129");
   
   private final String actionNumber;
   
   public String getInfoName() {
      return this.actionNumber;
   }
   
   ActionEnum(String actionNumber) {
      this.actionNumber = actionNumber;
   }
   
   /**
    * This method will return the number representing
    * this action dialog message in the use cases if it exists
    * in the use cases, and if it doesn't then it returns
    * the name of the enum.
    */
   @Override
   public String toString() {
      return ("action." + (this.actionNumber.equals("") 
               ? this.name().toLowerCase() 
               : this.actionNumber));
   }
}
