package gov.nwcg.isuite.framework.types;


public enum DataTransferTypeEnum {
   STRING(0),
   INTEGER(1),
   BIGDECIMAL(2),
   LONG(3),
   DATE(4),
   SHORT(5),
   BOOLEAN(6),
   TIMESTAMP(7);

   public int type;
   
   DataTransferTypeEnum(int val){
	   this.type=val;
   }

   public static DataTransferTypeEnum getByName(String name){
	   for(DataTransferTypeEnum t : DataTransferTypeEnum.values()){
		   if(t.name().equalsIgnoreCase(name))
			   return t;
	   }
	   
	   return null;
   }
}
