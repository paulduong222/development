package gov.nwcg.isuite.framework.core.domain;

import java.util.Date;

/**
 * Adds the created and last updated fields to a domain object.
 * 
 * @author bsteiner
 */
public interface Auditable {

   /**
    * Retrieves the date the object was created.
    * 
    * @return
    */
   public Date getCreatedDate();
   
   /**
    * Retrieves the name of the person/interface who created the object.
    * 
    * @return
    */
   public String getCreatedBy();
   
   /**
    * Retrieves the date the object was last modified.
    * 
    * @return
    */
   public Date getLastModifiedDate();
   
   /**
    * Retrieves the name of the person/interface who last modified the object.
    * @return
    */
   public String getLastModifiedBy();
   
   /**
    * Sets the date the object was created.
    * 
    * @param date creation date
    * @return
    */
   public void setCreatedDate(Date createdDate);
   
   /**
    * Sets the name of the person/interface who created the object.
    * 
    * @param createdBy user/interface who created the object.
    * @return
    */
   public void setCreatedBy(String createdBy);
   
   /**
    * Retrieves the date the object was last modified.
    * 
    * @param lastModifiedDate date object was last modified.
    */
   public void setLastModifiedDate(Date lastModifiedDate);
   
   /**
    * Retrieves the name of the person/interface who last modified the object.
    * 
    * @param createdBy user/interface who last modified the object.
    */
   public void setLastModifiedBy(String lastModifiedBy);

	/**
	 * @return the createdById
	 */
	public Long getCreatedById();

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) ;

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById();

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById);

}
