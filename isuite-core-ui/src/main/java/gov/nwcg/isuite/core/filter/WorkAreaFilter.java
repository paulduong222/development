package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;


/**
 * Filter for Work Area queries.
 * 
 * @author bsteiner, kvelasquez
 */
public interface WorkAreaFilter extends Filter {
   
   /**
    * @return the name
    */
   public String getName();
   
   /**
    * @param name
    */
   public void setName(String name);
   
   /**
    * @return the description
    */
   public String getDescription();
   
   /**
    * @param desc
    */
   public void setDescription(String desc);
   
   /**
    * @return standard
    */
   public Boolean getStandard();
   
   /**
    * @param standard
    */
   public void setStandard(Boolean standard);
   
   /**
    * 
    * @return standardAsString
    */
   public String getStandardAsString();
   
   /**
    * 
    * @param standardAsString
    */
   public void setStandardAsString(String standardAsString);
   
   /**
    * @return sharedOut
    */
   public Boolean getSharedOut();
   
   /**
    * @param sharedOut
    */
   public void setSharedOut(Boolean sharedOut);
   
   /**
    * 
    * @return sharedOutAsString
    */
   public String getSharedOutAsString();
   
   /**
    * 
    * @param sharedOutAsString
    */
   public void setSharedOutAsString(String sharedOutAsString);
   
   /**
    * @return createdBy
    */
   public String getCreatedBy();
   
   /**
    * @param createdBy
    */
   public void setCreatedBy(String createdBy);
 
   /**
    * Returns the deletable.
    *
    * @return 
    *		the deletable to return
    */
   public Boolean getDeletable();

   /**
    * Sets the deletable.
    *
    * @param deletable 
    *			the deletable to set
    */
   public void setDeletable(Boolean deletable);
   
   /**
    * 
    * @return
    */
   public String getCode();
   
   /**
    * 
    * @param code
    */
   public void setCode(String code);
}