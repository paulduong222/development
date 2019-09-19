package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;



/**
 * VO for populating the grid data for Work Area.
 * 
 * @author bsteiner
 */
public class WorkAreaGridVo extends AbstractVo {
	private String waName;
	private String waDesc;
	private String createdByWhom;
	private Boolean standard;
	private Long standardOrgId;
	private Boolean sharedOut;
	private Boolean deletable;
	private Long userId;

	/**
	 * Constructor
	 */
	public WorkAreaGridVo() {

	}

	/**
	 * @return sharedOut
	 */
	public Boolean getSharedOut() {
		return sharedOut;
	}

	/**
	 * @param sharedOut the sharedOut to set
	 */
	public void setSharedOut(Boolean sharedOut) {
		this.sharedOut = sharedOut;
	}

	/**
	 * Determine if this is a standard work area.
	 * 
	 * @return true if the standard organization id is not null
	 */
	public Boolean getStandard() {
		return standardOrgId != null;
	}

	/**
	 * @return the standardOrgId
	 */
	public Long getStandardOrgId() {
		return standardOrgId;
	}

	/**
	 * @param standardOrgId the standardOrgId to set
	 */
	public void setStandardOrgId(Long standardOrgId) {
		this.standardOrgId = standardOrgId;
		this.standard = (standardOrgId != null) ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Returns the waName.
	 *
	 * @return 
	 *		the waName to return
	 */
	public String getWaName() {
		return waName;
	}

	/**
	 * Sets the waName.
	 *
	 * @param waName 
	 *			the waName to set
	 */
	public void setWaName(String waName) {
		this.waName = waName;
	}

	/**
	 * Returns the waDesc.
	 *
	 * @return 
	 *		the waDesc to return
	 */
	public String getWaDesc() {
		return waDesc;
	}

	/**
	 * Sets the waDesc.
	 *
	 * @param waDesc 
	 *			the waDesc to set
	 */
	public void setWaDesc(String waDesc) {
		this.waDesc = waDesc;
	}

	/**
	 * Returns the createdByWhom.
	 *
	 * @return 
	 *		the createdByWhom to return
	 */
	public String getCreatedByWhom() {
		return createdByWhom;
	}

	/**
	 * Sets the createdByWhom.
	 *
	 * @param createdByWhom 
	 *			the createdByWhom to set
	 */
	public void setCreatedByWhom(String createdByWhom) {
		this.createdByWhom = createdByWhom;
	}

	/**
	 * Sets the standard.
	 *
	 * @param standard 
	 *			the standard to set
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
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
