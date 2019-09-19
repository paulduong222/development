package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.AssignmentStatusVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum AssignmentStatusTypeEnum {
   NA("Not Available"),
   //S("Staging"),
   F("Filled"),
   C("Checked-In"),
   P("Pending Demob"),
   D("Demob"),
   R("Reassigned");
   //L("Loaned");
   
   private String description="";
   
   AssignmentStatusTypeEnum(String desc){
	   this.description=desc;
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

   public static Collection<AssignmentStatusVo> getAssignmentVoList(boolean includeNA){
// 	  List<AssignmentStatusVo> list = new ArrayList<AssignmentStatusVo>();
	   Collection<AssignmentStatusVo> list = new ArrayList<AssignmentStatusVo>();
 	  
 	  /*
 	  if(includeNA)
 		  list.add(new AssignmentStatusVo(AssignmentStatusTypeEnum.NA.name(),AssignmentStatusTypeEnum.NA.getDescription()));
 		*/
 	  
	  list.add(new AssignmentStatusVo(1L,AssignmentStatusTypeEnum.F.name(),AssignmentStatusTypeEnum.F.getDescription()));
	  //list.add(new AssignmentStatusVo(2L,AssignmentStatusTypeEnum.S.name(),AssignmentStatusTypeEnum.S.getDescription()));
	  list.add(new AssignmentStatusVo(2L,AssignmentStatusTypeEnum.C.name(),AssignmentStatusTypeEnum.C.getDescription()));
	  list.add(new AssignmentStatusVo(3L,AssignmentStatusTypeEnum.P.name(),AssignmentStatusTypeEnum.P.getDescription()));
	  list.add(new AssignmentStatusVo(4L,AssignmentStatusTypeEnum.D.name(),AssignmentStatusTypeEnum.D.getDescription()));
	  list.add(new AssignmentStatusVo(5L,AssignmentStatusTypeEnum.R.name(),AssignmentStatusTypeEnum.R.getDescription()));
	  //list.add(new AssignmentStatusVo(7L,AssignmentStatusTypeEnum.L.name(),AssignmentStatusTypeEnum.L.getDescription()));
	  
	  return list;
   }
   
   public static AssignmentStatusVo getAssignmentVoByCode(String code){
	   AssignmentStatusVo vo = null;
	   
	   for(AssignmentStatusVo v : getAssignmentVoList(false)){
		   if(v.getCode().equals(code))
			   vo=v;
	   }
	   
	   return vo;
   }
   
   public static AssignmentStatusTypeEnum getByCode(String code){
	   
	   for(AssignmentStatusTypeEnum v : AssignmentStatusTypeEnum.values()){
		   if(v.name().equalsIgnoreCase(code))
			   return v;
	   }
	   
	   return null;
   }
   
}
