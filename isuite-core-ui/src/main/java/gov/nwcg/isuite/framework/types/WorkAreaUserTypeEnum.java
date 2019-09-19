package gov.nwcg.isuite.framework.types;


public enum WorkAreaUserTypeEnum {
   SYSTEM("Default System Access"),
   SHARED("Shared Access"),
   OWNER("Owner Access")
   ;
   
   private String description="";
   
   WorkAreaUserTypeEnum(String desc){
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
