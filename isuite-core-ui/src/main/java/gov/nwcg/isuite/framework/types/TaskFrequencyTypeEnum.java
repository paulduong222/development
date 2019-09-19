package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.TaskFrequencyTypeVo;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;


public enum TaskFrequencyTypeEnum {
   ONETIME(1L,"ONETIME"),
   DAILY(2L,"DAILY"),
   HOURLY(3L,"HOURLY"),
   WEEKLY(4L,"WEEKLY"),
   MONTHLY(5L,"MONTHLY")
   ;
   
   private Long id;
   private String description="";
   
   TaskFrequencyTypeEnum(Long id,String desc){
	   this.id=id;
	   this.description=desc;
   }
   
   public static Collection<TaskFrequencyTypeVo> getVos(){
	   Collection<TaskFrequencyTypeVo> vos = new ArrayList<TaskFrequencyTypeVo>();
	   
	   vos.add(TaskFrequencyTypeVo.getInstance(TaskFrequencyTypeEnum.ONETIME));
	   vos.add(TaskFrequencyTypeVo.getInstance(TaskFrequencyTypeEnum.DAILY));
	   vos.add(TaskFrequencyTypeVo.getInstance(TaskFrequencyTypeEnum.HOURLY));
	   vos.add(TaskFrequencyTypeVo.getInstance(TaskFrequencyTypeEnum.WEEKLY));
	   vos.add(TaskFrequencyTypeVo.getInstance(TaskFrequencyTypeEnum.MONTHLY));
	   
	   return vos;
   }
   
   public static TaskFrequencyTypeVo getVo(TaskFrequencyTypeEnum val){
	   if(null==val)
		   return null;
	   
	   for(TaskFrequencyTypeVo vo : getTaskFrequencyTypeVos()){
		   if(vo.getFrequency().equals(val.description)){
			   return vo;
		   }
	   }
	   return null;
   }
   
   public static Collection<TaskFrequencyTypeVo> getTaskFrequencyTypeVos(){
	   Collection<TaskFrequencyTypeVo> list = new ArrayList<TaskFrequencyTypeVo>();

	   list.add(TaskFrequencyTypeVo.getInstance(TaskFrequencyTypeEnum.ONETIME));
	   list.add(TaskFrequencyTypeVo.getInstance(TaskFrequencyTypeEnum.DAILY));
	   list.add(TaskFrequencyTypeVo.getInstance(TaskFrequencyTypeEnum.HOURLY));
	   list.add(TaskFrequencyTypeVo.getInstance(TaskFrequencyTypeEnum.WEEKLY));
	   list.add(TaskFrequencyTypeVo.getInstance(TaskFrequencyTypeEnum.MONTHLY));
	   
	   return list;
   }

   public static TaskFrequencyTypeEnum toEnumType(TaskFrequencyTypeVo vo){
	   if(null==vo || !StringUtility.hasValue(vo.getFrequency()))
		   return null;
	   else if(vo.getFrequency().equals(TaskFrequencyTypeEnum.ONETIME.description))
		   return TaskFrequencyTypeEnum.ONETIME;
	   else if(vo.getFrequency().equals(TaskFrequencyTypeEnum.DAILY.description))
		   return TaskFrequencyTypeEnum.DAILY;
	   else if(vo.getFrequency().equals(TaskFrequencyTypeEnum.HOURLY.description))
		   return TaskFrequencyTypeEnum.HOURLY;
	   else if(vo.getFrequency().equals(TaskFrequencyTypeEnum.WEEKLY.description))
		   return TaskFrequencyTypeEnum.WEEKLY;
	   else if(vo.getFrequency().equals(TaskFrequencyTypeEnum.MONTHLY.description))
		   return TaskFrequencyTypeEnum.MONTHLY;
	   else
		   return null;
   }
   
   /**
    * Returns the description of the task queue type.
    * 
    * @return
    * 	the description of the task queue type
    */
   public String getDescription(){
	   return this.description;
   }

   public Long getId(){
	   return this.id;
   }
}
