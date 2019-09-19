package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.WorkAreaFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

/**
 * Filter for Work Area Queries.
 * 
 * @author bsteiner
 */
public class WorkAreaFilterImpl extends FilterImpl implements WorkAreaFilter {
   
   private static final long serialVersionUID = -331372559822311418L;
   private Boolean sharedOut;
   private String sharedOutAsString;
   private Boolean standard;
   private String standardAsString;
   private String name;
   private String description;
   private String createdBy;
   private String code;
   
   private Boolean deletable=false;
   private String deletableString;
   
   public WorkAreaFilterImpl() {
      this.reset();
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaFilter#getName()
    */
   public String getName() {
      return name;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaFilter#setName(java.lang.String)
    */
   public void setName(String name) {
      this.name = name;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaFilter#getDescription()
    */
   public String getDescription() {
      return description;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaFilter#setDescription(java.lang.String)
    */
   public void setDescription(String description) {
      this.description = description;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaFilter#getStandard()
    */
   public Boolean getStandard(){
      return standard;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaFilter#setStandard(java.lang.Boolean)
    */
   public void setStandard(Boolean standard) {
      this.standard = standard;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.WorkAreaFilter#getStandardAsString()
    */
   public String getStandardAsString() {
	   return this.standardAsString;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.WorkAreaFilter#setStandardAsString(java.lang.String)
    */
   public void setStandardAsString(String standardAsString) {
	   this.standardAsString = standardAsString;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaFilter#getSharedOut()
    */
   public Boolean getSharedOut() {
      return sharedOut;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaFilter#setSharedOut(java.lang.Boolean)
    */
   public void setSharedOut(Boolean sharedOut) {
      this.sharedOut = sharedOut;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.WorkAreaFilter#getSharedOutAsString()
    */
   public String getSharedOutAsString() {
	   return this.sharedOutAsString;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.WorkAreaFilter#setSharedOutAsString(java.lang.String)
    */
   public void setSharedOutAsString(String sharedOutAsString) {
	   this.sharedOutAsString = sharedOutAsString;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.impl.FilterImpl#reset()
    */
   public final void reset() {
      this.sharedOut = null;
      this.standard = null;
      this.name = null;
      this.description = null;
      this.createdBy = null;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaFilter#getCreatedBy()
    */
   public String getCreatedBy() {
      return createdBy;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaFilter#setCreatedBy(java.lang.String)
    */
   public void setCreatedBy(String createdBy) {
      this.createdBy = createdBy;
   }

   /**
    * Returns the deletable.
    *
    * @return 
    *		the deletable to return
    */
   public Boolean getDeletable() {
	   return deletable;
   }

   /**
    * Sets the deletable.
    *
    * @param deletable 
    *			the deletable to set
    */
   public void setDeletable(Boolean deletable) {
	   this.deletable = deletable;
   }

   /**
    * @return the deletableString
    */
   public String getDeletableString() {
      return deletableString;
   }

   /**
    * @param deletableString the deletableString to set
    */
   public void setDeletableString(String deletableString) {
      this.deletableString = deletableString;
      this.setDeletable(super.determineDeletableValue(this.deletableString));
   }

   /**
    * @return the code
    */
   public String getCode() {
	   return code;
   }

   /**
    * @param code the code to set
    */
   public void setCode(String code) {
	   this.code = code;
   }
 
}