package gov.nwcg.isuite.core.vo;

import java.util.Date;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
/**
 * 
 * @author toneil
 *
 */
public class ContractorAgreementGridVo extends AbstractVo {
	
	private String agreementNumber;
	private Date startDate;
	private Date endDate;
	private String pointOfHire;
	private String officeName;
	private AdminOfficeVo adminOfficeVo;
	private Long adminOfficeId;	
	private Long contractorId;
	private Date deletedDate;
	private Boolean deletable;
	private Boolean enabled;
	
	
	/**
	 * @param the agreementNumber
	 */
	public String getAgreementNumber() {
		return this.agreementNumber;
	}
	/**
	 * @param set the agreementNumber
	 */
	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}	
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param set startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param set endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the pointOfHire
	 */
	public String getPointOfHire() {
		return pointOfHire;
	}
	/**
	 * @param set point of hire
	 */
	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
	}
	/**
	 * @param the AdminOfficeId
	 */
	public Long getAdminOfficeId() {
		return this.adminOfficeId;
	}
	/**
	 * @param set the AdminOfficeId
	 */
	public void setAdminOfficeId(Long adminOfficeId) {
		this.adminOfficeId = adminOfficeId;
	}
	
	/**
	 * @param the contractorId
	 */
	public Long getContractorId() {
		return this.contractorId;
	}
	/**
	 * @param set the contactorId
	 */
	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}
	
	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}
	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
	

   /**
    * @return the deletable
    */
   public Boolean getDeletable() {
	  // TODO: Determine value of deletable
	  return deletable;
   }

   /**
    * @param deletable the deletable to set
    */
   public void setDeletable(Boolean deletable) {
      this.deletable = deletable;
   }
   
   /**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}
	
	/**
	 * @return the adminOfficeVo
	 */
	public AdminOfficeVo getAdminOfficeVo() {
		return adminOfficeVo;
	}
	
	/**
	 * @param adminOfficeVo
	 */
	public void setAdminOfficeVo(AdminOfficeVo adminOfficeVo) {
		this.adminOfficeVo = adminOfficeVo;
	}
	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}
}
