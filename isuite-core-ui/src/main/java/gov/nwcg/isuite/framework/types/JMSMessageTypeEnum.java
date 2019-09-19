package gov.nwcg.isuite.framework.types;


public enum JMSMessageTypeEnum {
   SESSION_KILL("You are being logged out.")
   ,TEST("Test Enum to provide sample")
   //,ADDANOTHER("Add another enum like this")
   ;
   
   private String description="";
   
   JMSMessageTypeEnum(String desc){
	   this.description=desc;
   }
   
   /**
    * Returns the description of the task status type.
    * 
    * @return
    * 	the description of the task status type
    */
   public String getDescription(){
	   return this.description;
   }

}
