package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.TravelMethodVo;

import java.util.ArrayList;
import java.util.List;

public enum TravelMethodTypeEnum {
   AR("A/R","Air Travel then Rental"),
   AIR("AIR","Aircraft transportation"),
   AOV("AOV","Agency owned vehicle"),
   BUS("BUS","Bus"),
   OTH("OTH","Other"),
   PAS("PAS","Passenger"),
   POV("POV","Privately owned vehicle"),
   REN("REN","Rental vehicle");
   
   private String description="";
   private String displayName="";
   
   TravelMethodTypeEnum(String dispName,String desc){
	   this.displayName=dispName;
	   this.description=desc;
   }
   
   public String value() {
       return name();
   }
   
   /**
    * Returns the description of the assignment status.
    * 
    * @return
    * 	the description of the assignment status
    */
   public String getDescription(){
	   return this.description;
   }

   public String getDisplayName(){
	   return this.displayName;
   }
   
   public static List<TravelMethodVo> getTravelMethodVoList(){
	 	  List<TravelMethodVo> list = new ArrayList<TravelMethodVo>();

		  list.add(new TravelMethodVo(1L,TravelMethodTypeEnum.AR.name(),TravelMethodTypeEnum.AR.getDisplayName(),TravelMethodTypeEnum.AR.getDescription()));
		  list.add(new TravelMethodVo(2L,TravelMethodTypeEnum.AIR.name(),TravelMethodTypeEnum.AIR.getDisplayName(),TravelMethodTypeEnum.AIR.getDescription()));
		  list.add(new TravelMethodVo(3L,TravelMethodTypeEnum.AOV.name(),TravelMethodTypeEnum.AOV.getDisplayName(),TravelMethodTypeEnum.AOV.getDescription()));
		  list.add(new TravelMethodVo(4L,TravelMethodTypeEnum.BUS.name(),TravelMethodTypeEnum.BUS.getDisplayName(),TravelMethodTypeEnum.BUS.getDescription()));
		  list.add(new TravelMethodVo(5L,TravelMethodTypeEnum.OTH.name(),TravelMethodTypeEnum.OTH.getDisplayName(),TravelMethodTypeEnum.OTH.getDescription()));
		  list.add(new TravelMethodVo(6L,TravelMethodTypeEnum.PAS.name(),TravelMethodTypeEnum.PAS.getDisplayName(),TravelMethodTypeEnum.PAS.getDescription()));
		  list.add(new TravelMethodVo(7L,TravelMethodTypeEnum.POV.name(),TravelMethodTypeEnum.POV.getDisplayName(),TravelMethodTypeEnum.POV.getDescription()));
		  list.add(new TravelMethodVo(8L,TravelMethodTypeEnum.REN.name(),TravelMethodTypeEnum.REN.getDisplayName(),TravelMethodTypeEnum.REN.getDescription()));
		  		  
		  return list;
	   }
   
}
