package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;


public class ResourceAssignmentVo extends AbstractVo {
   private Boolean mainDirtyFlag = false;
   private Boolean assignmentDirtyFlag = false;
   private Boolean workPeriodDirtyFlag = false;
   private Boolean incidentResourceDirtyFlag = false;
   private Boolean resourceDirtyFlag = false;
   private Boolean timeDirtyFlag = false; // Not implemented
   private Boolean costDirtyFlag = false; // Not implemented
   
   private IncidentResourceCommonVo resourceCommonVo;
   private ResourceCheckinDemobVo resourceCheckinDemobVo;
   private ResourceTimeVo resourceTimeVo; // Not implemented
   private ResourceCostVo resourceCostVo; // Not implemented

   /**
    * @return the mainDirtyFlag
    */
   public Boolean isMainDirtyFlag() {
      return mainDirtyFlag;
   }

   /**
    * Convenience method for flex reflection.
    * 
    * @return
    */
   public Boolean getMainDirtyFlag(){
      return isMainDirtyFlag();
   }
   
   /**
    * @param mainDirtyFlag the mainDirtyFlag to set
    */
   public void setMainDirtyFlag(Boolean mainDirtyFlag) {
      this.mainDirtyFlag = mainDirtyFlag;
   }

   /**
    * @return the assignmentDirtyFlag
    */
   public Boolean isAssignmentDirtyFlag() {
      return assignmentDirtyFlag;
   }

   /**
    * Convenience method for flex reflection.
    * 
    * @return
    */
   public Boolean getAssignmentDirtyFlag() {
      return isAssignmentDirtyFlag();
   }
   
   /**
    * @param assignmentDirtyFlag the assignmentDirtyFlag to set
    */
   public void setAssignmentDirtyFlag(Boolean assignmentDirtyFlag) {
      this.assignmentDirtyFlag = assignmentDirtyFlag;
   }
   
   /**
    * @return the workPeriodDirtyFlag
    */
   public Boolean isWorkPeriodDirtyFlag() {
      return workPeriodDirtyFlag;
   }
   
   /**
    * Convenience method for flex reflection.
    * 
    * @return
    */
   public Boolean getWorkPeriodDirtyFlag() {
      return isWorkPeriodDirtyFlag();
   }
   
   /**
    * @param workPeriodDirtyFlag the workPeriodDirtyFlag to set
    */
   public void setWorkPeriodDirtyFlag(Boolean workPeriodDirtyFlag) {
      this.workPeriodDirtyFlag = workPeriodDirtyFlag;
   }

   /**
    * @return the incidentResourceDirtyFlag
    */
   public Boolean isIncidentResourceDirtyFlag() {
      return incidentResourceDirtyFlag;
   }

   /**
    * Convenience method for flex reflection.
    * 
    * @return
    */
   public Boolean getIncidentResourceDirtyFlag() {
      return isIncidentResourceDirtyFlag();
   }
   
   /**
    * @param incidentResourceDirtyFlag the incidentResourceDirtyFlag to set
    */
   public void setIncidentResourceDirtyFlag(Boolean incidentResourceDirtyFlag) {
      this.incidentResourceDirtyFlag = incidentResourceDirtyFlag;
   }

   /**
    * @return the resourceDirtyFlag
    */
   public Boolean isResourceDirtyFlag() {
      return resourceDirtyFlag;
   }

   /**
    * Convenience method for flex reflection.
    * 
    * @return
    */
   public Boolean getResourceDirtyFlag() {
      return isResourceDirtyFlag();
   }
   
   /**
    * @param resourceDirtyFlag the resourceDirtyFlag to set
    */
   public void setResourceDirtyFlag(Boolean resourceDirtyFlag) {
      this.resourceDirtyFlag = resourceDirtyFlag;
   }

   /**
    * @return the timeDirtyFlag
    */
   public Boolean isTimeDirtyFlag() {
      return timeDirtyFlag;
   }
   
   /**
    * @return the timeDirtyFlag
    */
   public Boolean getTimeDirtyFlag() {
      return isTimeDirtyFlag();
   }
   
   /**
    * @param timeDirtyFlag the timeDirtyFlag to set
    */
   public void setTimeDirtyFlag(Boolean timeDirtyFlag) {
      this.timeDirtyFlag = timeDirtyFlag;
   }

   /** 
    * @return the costDirtyFlag
    */
   public Boolean isCostDirtyFlag() {
      return costDirtyFlag;
   }
   
   /** 
    * @return the costDirtyFlag
    */
   public Boolean getCostDirtyFlag() {
      return isCostDirtyFlag();
   }
   
   /**
    * @param costDirtyFlag the costDirtyFlag to set
    */
   public void setCostDirtyFlag(Boolean costDirtyFlag) {
      this.costDirtyFlag = costDirtyFlag;
   }
   
   /**
    * @return the resourceCommonVo
    */
   public IncidentResourceCommonVo getResourceCommonVo() {
       if (resourceCommonVo == null) {
          setResourceCommonVo(new IncidentResourceCommonVo());
       }
       return resourceCommonVo;
    }
   
   /**
    * @param resourceCommonVo the resourceCommonVo to set
    */
   public void setResourceCommonVo(IncidentResourceCommonVo resourceCommonVo) {
      this.resourceCommonVo = resourceCommonVo;
   }

   /**
    * @return the resourceCheckinDemobVo
    */
    public ResourceCheckinDemobVo getResourceCheckinDemobVo() {
      if (resourceCheckinDemobVo == null) {
         setResourceCheckinDemobVo(new ResourceCheckinDemobVo());
      }
      return resourceCheckinDemobVo;
   }
   
   /**
    * @param resourceCheckinDemobVo the resourceCheckinDemobVo to set
    */
   public void setResourceCheckinDemobVo(ResourceCheckinDemobVo resourceCheckinDemobVo) {
      this.resourceCheckinDemobVo = resourceCheckinDemobVo;
   }
   
   /**
    * @return the resourceTimeVo
    */
   public ResourceTimeVo getResourceTimeVo() {
      if (resourceTimeVo == null) {
         setResourceTimeVo(new ResourceTimeVo());
      }
      return resourceTimeVo;
   }
   
   /**
    * @param resourceTimeVo the resourceTimeVo to set
    */
   public void setResourceTimeVo(ResourceTimeVo resourceTimeVo) {
      this.resourceTimeVo = resourceTimeVo;
   }
   
   /**
    * @return the resourceCostVo
    */
   public ResourceCostVo getResourceCostVo() {
      if (resourceCostVo == null) {
         setResourceCostVo(new ResourceCostVo());
      }
      return resourceCostVo;
   }
   
   /**
    * @param resourceCostVo the resourceCostVo to set
    */
   public void setResourceCostVo(ResourceCostVo resourceCostVo) {
      this.resourceCostVo = resourceCostVo;
   }
   
}
