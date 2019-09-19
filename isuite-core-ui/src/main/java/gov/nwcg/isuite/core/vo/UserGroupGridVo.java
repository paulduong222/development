package gov.nwcg.isuite.core.vo;


import gov.nwcg.isuite.framework.core.vo.AbstractVo;


/**
 * @author bsteiner
 *
 */
public class UserGroupGridVo extends AbstractVo {
	private String groupName;
	private Boolean deletable;
	private Long groupOwnerId;

	public UserGroupGridVo(){
		super();
	}
	
	/**
	 * Returns the groupName.
	 *
	 * @return 
	 *		the groupName to return
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * Sets the groupName.
	 *
	 * @param groupName 
	 *			the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

   /**
    * @return the groupOwnerId
    */
   public Long getGroupOwnerId() {
      return groupOwnerId;
   }

   /**
    * @param groupOwnerId the groupOwnerId to set
    */
   public void setGroupOwnerId(Long groupOwnerId) {
      this.groupOwnerId = groupOwnerId;
   }

   /**
    * @return the deletable
    */
   public Boolean getDeletable() {
      return deletable;
   }

   /**
    * @param deletable the deletable to set
    */
   public void setDeletable(Boolean deletable) {
      this.deletable = deletable;
   }


}
