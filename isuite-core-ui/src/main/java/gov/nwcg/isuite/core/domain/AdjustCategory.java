package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;

import java.util.Collection;

public interface AdjustCategory extends Persistable {

	   /**
	    * Sets the code.
	    * 
	    * @param code 
	    * 		the section code to set
	    */
	   public void setCode(String code);
	   
	   /**
	    * Returns the section code.
	    * 
	    * @return 
	    * 		the section code to return
	    */
	   public String getCode();

	   /**
	    * Sets the description.
	    * 
	    * @param description 
	    * 		the section code description to set
	    */
	   public void setDescription(String description);
	   
	   /**
	    * Returns the section code description.
	    * 
	    * @return 
	    * 		the section code description to return
	    */
	   public String getDescription();
	   
	   ////
	   
	   /**
	    * Sets the adjustment type.
	    * 
	    * @param description 
	    * 		the adjustment type description to set
	    */
	   public void setAdjustmentType(AdjustmentTypeEnum adjustmentType);
	   
	   /**
	    * Returns the adjustment type.
	    * 
	    * @return 
	    * 		the adjustment type to return
	    */
	   public AdjustmentTypeEnum getAdjustmentType();


		/**
		 * @return the timeAssignAdjust
		 */
		public Collection<TimeAssignAdjust> getTimeAssignAdjusts();

		/**
		 * @param timeAssignAdjust the timeAssignAdjust to set
		 */
		public void setTimeAssignAdjusts(Collection<TimeAssignAdjust> timeAssignAdjusts);

}
