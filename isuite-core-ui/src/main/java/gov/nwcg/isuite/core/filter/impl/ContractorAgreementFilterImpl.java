package gov.nwcg.isuite.core.filter.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import gov.nwcg.isuite.core.filter.ContractorAgreementFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;

/**
 * @author toneil
 *
 */
public class ContractorAgreementFilterImpl extends FilterImpl implements
	ContractorAgreementFilter {

	/**
	 * system generated 
	 */
	private static final long serialVersionUID = 3812179421721262315L;
	
	
	public Long adminOfficeId;
	public String agreementNumber;
	public Date startDate;
	public Date endDate;
	public String pointOfHire;
	public Long contractorId;
	public Date deletedDate;
	private Boolean deletable;
	private String deletableString;
	private String adminOffice;
	
	private String crypticDateFilterCodeStartDate;
	private String crypticDateFilterCodeEndDate;


	/**
	 * @return the adminOfficeId
	 */
	public Long getAdminOfficeId() {
		return adminOfficeId;
	}
	
	/**
	 * @param set adminOfficeId
	 */
	public void setAdminOfficeId(Long adminOfficeId) {
		this.adminOfficeId = adminOfficeId;
	}
	
	/**
	 * @return the agreementNumber
	 */
	public String getAgreementNumber() {
		return agreementNumber;
	}

	/**
	 * @param set agreementNumber
	 */
	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
		
	}
	
	/**
	 * @return the contractorId
	 */
	public Long getContractorId() {
		return contractorId;
	}

	/**
	 * @param set contractorId
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
	 * @param set deletedDate 
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
	
	/**
	 * @return the deletable
	 */
	public Boolean getDeletable() {
		return deletable;
	}

	/**
	 * @param set deletable
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
	 * @param set deletableString
	 */
	public void setDeletableString(String deletableString) {
		this.deletableString = deletableString;
		this.setDeletable(super.determineDeletableValue(this.deletableString));
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
	
	
	public Boolean getGroupByContractorAgreement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGroupByContractorAgreement(Boolean groupByContractorAgreement) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return the pointOfHire
	 */
	public String getPointOfHire() {
		return pointOfHire;
	}

	/**
	 * @param set pointOfHire
	 */
	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
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
	 * @return the adminOffice
	 */
	public String getAdminOffice() {
		return adminOffice;
	}
	/**
	 * @param adminOffice the adminOffice to set
	 */
	public void setAdminOffice(String adminOffice) {
		this.adminOffice = adminOffice;
	}
	
	/**
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static Collection<FilterCriteria> getFilterCriteria(ContractorAgreementFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();		
		return criteria;
	}

	/**
	 * @return the crypticDateFilterCodeStartDate
	 */
	public String getCrypticDateFilterCodeStartDate() {
		return crypticDateFilterCodeStartDate;
	}

	/**
	 * @param crypticDateFilterCodeStartDate the crypticDateFilterCodeStartDate to set
	 */
	public void setCrypticDateFilterCodeStartDate(
			String crypticDateFilterCodeStartDate) {
		this.crypticDateFilterCodeStartDate = crypticDateFilterCodeStartDate;
	}

	/**
	 * @return the crypticDateFilterCodeEndDate
	 */
	public String getCrypticDateFilterCodeEndDate() {
		return crypticDateFilterCodeEndDate;
	}

	/**
	 * @param crypticDateFilterCodeEndDate the crypticDateFilterCodeEndDate to set
	 */
	public void setCrypticDateFilterCodeEndDate(String crypticDateFilterCodeEndDate) {
		this.crypticDateFilterCodeEndDate = crypticDateFilterCodeEndDate;
	}
	
}
