package gov.nwcg.isuite.framework.types;


public enum QuestionTypeEnum {
   AIRTRAVEL("Air Travel Questions"),
   PREPLANNING("Pre-Planning Questions"),
   BOTH("Both")
   ;
   
   private String description="";
   
   QuestionTypeEnum(String desc){
	   this.description=desc;
   }
   
   /**
    * Returns the description of the question type.
    * 
    * @return
    * 	the description of the question type
    */
   public String getDescription(){
	   return this.description;
   }
   
}
