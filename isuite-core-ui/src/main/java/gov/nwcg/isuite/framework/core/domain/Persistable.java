package gov.nwcg.isuite.framework.core.domain;

import java.util.Date;


/**
 * Represents a Persistent object
 * 
 * @author bsteiner
 */

public interface Persistable {

   /**
    * Accessor id
    * 
    * @see #setId(Long)
    * @return id
    */
   public Long getId();

   /**
    * Accessor id
    * 
    * @see #getId()
    * @param id id of object
    */
   public void setId(Long id);
   
   /**
    * Accessor createdBy
    * 
    * @see #setCreatedBy(String)
    * @return createdBy
    */
   public String getCreatedBy();
   
   /**
    * Accessor createdDate
    * 
    * @see #setCreatedDate(Date)
    * @return createdDate
    */
   public Date getCreatedDate();
   
   /**
    * Accessor lastModifiedBy
    * 
    * @see #setLastModifiedBy(String)
    * @return lastModifiedBy
    */
   public String getLastModifiedBy();
   
   /**
    * Accessor lastModifiedDate
    * 
    * @see #setLastModifiedDate(Date)
    * @return lastModifiedDate
    */
   public Date getLastModifiedDate();
   
   /**
    * Accessor createdBy
    * 
    * @see #getCreatedBy()
    * @param createdBy createdBy of object
    */
   public void setCreatedBy(String createdBy);
   
   /**
    * Accessor createdDate
    * 
    * @see #getCreatedDate()
    * @param createdDate createdDate of object
    */
   public void setCreatedDate(Date createdDate);
   
   /**
    * Accessor lastModifiedBy
    * 
    * @see #getLastModifiedBy()
    * @param lastModifiedBy lastModifiedBy of object
    */
   public void setLastModifiedBy(String lastModifiedBy);
   
   /**
    * Accessor lastModifiedDate
    * 
    * @see #getLastModifiedDate()
    * @param lastModifiedDate lastModifiedDate of object
    */
   public void setLastModifiedDate(Date lastModifiedDate);

   public void setCreatedById(Long userId);
   
   public Long getCreatedById();
   
   public void setLastModifiedById(Long userId);
   
   public Long getLastModifiedById();
   
}
