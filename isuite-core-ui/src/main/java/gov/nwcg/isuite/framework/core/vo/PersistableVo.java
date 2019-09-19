package gov.nwcg.isuite.framework.core.vo;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Date;

/**
 * 
 * @author ncollette
 *
 */
public interface PersistableVo  {

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
		 * Returns the user login name.
		 * 
		 * @return 
		 * 		the userLoginName
		 */
		public String getUserLoginName();

		/**
		 * Sets the user login name.
		 * 
		 * @param userLoginName 
		 * 				the userLoginName to set
		 */
		public void setUserLoginName(String userLoginName); 
		
		/**
		 * Sets the created by user.
		 * 
		 * @param val
		 * 			the created by user to set
		 */
		public void setCreatedBy(String val);
			   
		/**
		 * Returns the created by user.
		 * 
		 * @return
		 * 		the created by user to return
		 */
		public String getCreatedBy();
			   
		/**
		 * Sets the created date.
		 * 
		 * @param dt
		 * 		the created date to set
		 */
		public void setCreatedDate(Date dt);

		/**
		 * Returns the created date.
		 * 
		 * @return
		 * 		the created date to return
		 */
		public Date getCreatedDate();
	   
	   /**
	    * Returns an entity object populated with the values from the vo object.
	    * 
	    * If the param entity is null, a new instance will be created and populated,
	    * otherwise the entity passed will be populated with the vo values.
	    * 
	    * @param entity
	    * 			the entity to populate if it is available
	    * @param cascadable
	    * 			flag to indicate whether or not the entity instance
	    * 			that is being returned should be treated as cascadable 
	    * 			object.  If the flag is true, then the entire entity
	    * 			will be built and returned, otherwise only the id is set
	    * @return
	    * 		the populated entity to return
	    * @throws Exception
	    */
		@Deprecated
		public Persistable toEntity(Persistable entity) throws Exception;

		
}
