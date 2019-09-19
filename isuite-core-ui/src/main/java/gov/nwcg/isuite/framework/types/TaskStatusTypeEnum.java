package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.TaskStatusTypeVo;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;


public enum TaskStatusTypeEnum {
   PROCESSING(1L,"PROCESSING"),
   ERROR(2L,"ERROR"),
   TIMEOUT(3L,"TIMEOUT"), 
   COMPLETED(4L,"COMPLETED")
   ;
   
   private Long id;
   private String description="";
   
   TaskStatusTypeEnum(Long id,String desc){
	   this.id=id;
	   this.description=desc;
   }
   
   public static TaskStatusTypeVo getVo(TaskStatusTypeEnum val){
	   if(null==val)
		   return null;
	   
	   for(TaskStatusTypeVo vo : getTaskStatusTypeVos()){
		   if(vo.getStatus().equals(val.description)){
			   return vo;
		   }
	   }
	   return null;
   }
   
   public static TaskStatusTypeEnum toEnumValue(TaskStatusTypeVo vo){
	   if(null==vo || !StringUtility.hasValue(vo.getStatus()))
		   return null;
	   else if(vo.getStatus().equals(TaskStatusTypeEnum.PROCESSING.description))
		   return TaskStatusTypeEnum.PROCESSING;
	   else if(vo.getStatus().equals(TaskStatusTypeEnum.ERROR.description))
		   return TaskStatusTypeEnum.ERROR;
	   else if(vo.getStatus().equals(TaskStatusTypeEnum.TIMEOUT.description))
		   return TaskStatusTypeEnum.TIMEOUT;
	   else if(vo.getStatus().equals(TaskStatusTypeEnum.COMPLETED.description))
		   return TaskStatusTypeEnum.COMPLETED;
	   else
		   return null;
   }
   
   public static Collection<TaskStatusTypeVo> getTaskStatusTypeVos(){
	   Collection<TaskStatusTypeVo> list = new ArrayList<TaskStatusTypeVo>();

	   list.add(TaskStatusTypeVo.getInstance(TaskStatusTypeEnum.PROCESSING));
	   list.add(TaskStatusTypeVo.getInstance(TaskStatusTypeEnum.ERROR));
	   list.add(TaskStatusTypeVo.getInstance(TaskStatusTypeEnum.TIMEOUT));
	   list.add(TaskStatusTypeVo.getInstance(TaskStatusTypeEnum.COMPLETED));
	   
	   return list;
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

   public Long getId(){
	   return this.id;
   }
}
