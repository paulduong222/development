package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.IncidentRestrictedStatusVo;

import java.util.ArrayList;
import java.util.List;

public enum IncidentRestrictedStatusTypeEnum {
   RESTRICTED_NO_ACCESS("RESTRICTED NO ACCESS"),
   RESTRICTED_ACCESS("RESTRICTED ACCESS"),
   UNRESTRICTED("UNRESTRICTED")
   ;
   
   private String description="";
   
   IncidentRestrictedStatusTypeEnum(String desc){
	   this.description=desc;
   }
   
   /**
    * Returns the description of the incident restricted status.
    * 
    * @return
    * 	the description of the status
    */
   public String getDescription(){
	   return this.description;
   }

   public static List<IncidentRestrictedStatusVo> getIncidentRestrictedStatusVoList(){
 	  List<IncidentRestrictedStatusVo> list = new ArrayList<IncidentRestrictedStatusVo>();

	  list.add(new IncidentRestrictedStatusVo(IncidentRestrictedStatusTypeEnum.RESTRICTED_NO_ACCESS.name(),IncidentRestrictedStatusTypeEnum.RESTRICTED_NO_ACCESS.getDescription()));
	  list.add(new IncidentRestrictedStatusVo(IncidentRestrictedStatusTypeEnum.RESTRICTED_ACCESS.name(),IncidentRestrictedStatusTypeEnum.RESTRICTED_ACCESS.getDescription()));
	  list.add(new IncidentRestrictedStatusVo(IncidentRestrictedStatusTypeEnum.UNRESTRICTED.name(),IncidentRestrictedStatusTypeEnum.UNRESTRICTED.getDescription()));
	  
	  return list;
   }
}
