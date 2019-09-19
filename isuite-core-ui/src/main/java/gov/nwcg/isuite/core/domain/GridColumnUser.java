package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface GridColumnUser extends Persistable {

	
   /**
    * Sets the grid column id.
    * 
    * @param gridColumnId 
    * 		the grid column id to set
    */
   public void setGridColumnId(Long gridColumnId);
   
   /**
    * Returns the grid column id.
    * 
    * @return 
    * 		the grid column id to return
    */
   public Long getGridColumnId();

   /**
    * Sets the gridColumn object.
    * 
    * @param gridColumn 
    * 			the grid column to set
    */
   public void setGridColumn(GridColumn gridColumn);
   
   /**
    * Returns the gridColumn.
    * 
    * @return 
    * 		the grid column to return
    */
   public GridColumn getGridColumn();
   
   /**
    * Sets the positional order for the column in the grid.
    * 
    * @param position 
    * 		the column position in the grid
    */
   public void setPosition(Integer position);
   
   /**
    * Returns the positional order of the column in the user's grid.
    * 
    * @return 
    *		the column position to return
    */
   public Integer getPosition();
 
   /**
    * Sets the user id.
    * 
    * @param userId 
    * 		the user id to set
    */
   public void setUserId(Long userId);
   
   /**
    * Returns the user id.
    * 
    * @return 
    * 		the user id to return
    */
   public Long getUserId();

   /**
    * Sets the user.
    * 
    * @param user 
    * 		the user to set
    */
   public void setUser(User user);
   
   /**
    * Returns the user.
    * 
    * @return 
    * 		the user to return
    */
   public User getUser();
   
	/**
	 * Returns the visible.
	 *
	 * @return 
	 *		the visible to return
	 */
	public Boolean isVisible();

	/**
	 * Sets the visible.
	 *
	 * @param visible 
	 *			the visible to set
	 */
	public void setVisible(Boolean visible);
   

}