package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.framework.util.BooleanUtility;


public enum StringBooleanEnum {
   Y(true),
   N(false);
   
   private Boolean value=false;
   
   public Boolean getValue(){
	   return value;
   }
   
   StringBooleanEnum(Boolean val){
	   this.value=val;
   }
   
   public static StringBooleanEnum toEnumValue(Boolean val){
	   if(BooleanUtility.isTrue(val))
		   return StringBooleanEnum.Y;
	   else
		   return StringBooleanEnum.N;
   }
   
   public static Boolean toBooleanValue(StringBooleanEnum val){
	   if(null != val){
		   return val.getValue();
	   }else{
		   return false;
	   }
   }
}
