package gov.nwcg.isuite.core.vo.rossimport2;

import gov.nwcg.isuite.core.vo.RossXmlFileDataVo;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class RossResourceVo extends AbstractVo{
	private Long rossXmlFileDataId;
	private Long rossResId;
	private Long rossResReqId;

	private String sortRequestNumber;
	private String requestNumber;
	private String resourceName;
	private String firstName;
	private String lastName;
	private String middleName="";
	private String assignmentName;
	private String unitCode;
	private String agencyCode;
	private String itemCode;
	private String itemName;
	private String jetPort;
	private Date mobDate;
	private String vendorName;
	private String vendorOwnedFlag;
	
	private String requestCatalogName;
	private String requestCategoryName;

	private Boolean isPerson;
	
	private Boolean excluded=false;

	// eisuite resource match info
	private Boolean matched=false;
	private Boolean userUnmatched=false;
	private String matchType="";
	private Long eisuiteResourceId;
	private Long eisuiteIncidentResourceId;
	private String eisuiteSortRequestNumber;
	private String eisuiteRequestNumber;
	private String eisuiteUnitCode;
	private String eisuiteAgencyCode;
	private String eisuiteItemCode;
	private String eisuiteItemName;
	
	private String dataConflict;

	private Long parentRossResReqId;
	private Collection<RossResourceVo> children = new ArrayList<RossResourceVo>();
	
	public static Collection<RossResourceVo> getInstances(Collection<RossXmlFileDataVo> rxfdVos) throws Exception {
		Collection<RossResourceVo> vos = new ArrayList<RossResourceVo>();

		for(RossXmlFileDataVo rxfdVo : rxfdVos){
			RossResourceVo vo = new RossResourceVo();
			vo.setRossXmlFileDataId(rxfdVo.getId());
			vo.setRossResId(rxfdVo.getResId());
			vo.setRossResReqId(TypeConverter.convertToLong(rxfdVo.getReqId()));
			vo.setResourceName(rxfdVo.getResName());
			vo.setRequestNumber(rxfdVo.getReqNumber());
			vo.setRequestCatalogName(rxfdVo.getReqCatalogName());
			vo.setRequestCategoryName(rxfdVo.getReqCategoryName());
			vo.setDataConflict("");
			vo.setExcluded(false);
			
			if(vo.getRequestCatalogName().equals("OVERHEAD") && (vo.getRequestCatalogName().equals("POSITIONS") ) ){
				vo.setIsPerson(true);
				vo.setFirstName(rxfdVo.getFirstName());
				vo.setLastName(rxfdVo.getLastName());
				
				if(!StringUtility.hasValue(vo.getLastName())){
					// try and get name from resourceName
					if(vo.getResourceName().contains(",")){
						String fname = StringUtility.getTokenValue(vo.getResourceName(), ",", 2);
						String lname=StringUtility.getTokenValue(vo.getResourceName(), ",", 1);
						vo.setLastName(StringUtility.leftTrim(lname,35).trim());
						vo.setFirstName(StringUtility.leftTrim(fname,35).trim());
					}				
				}
			}else
				vo.setIsPerson(false);
			
			vo.setAgencyCode("");
			vo.setItemCode("");
			vo.setUnitCode("");
			
			
			vos.add(vo);
		}
		
		return vos;
	}
	
	/**
	 * @return the rossXmlFileDataId
	 */
	public Long getRossXmlFileDataId() {
		return rossXmlFileDataId;
	}
	/**
	 * @param rossXmlFileDataId the rossXmlFileDataId to set
	 */
	public void setRossXmlFileDataId(Long rossXmlFileDataId) {
		this.rossXmlFileDataId = rossXmlFileDataId;
	}
	/**
	 * @return the rossResId
	 */
	public Long getRossResId() {
		return rossResId;
	}
	/**
	 * @param rossResId the rossResId to set
	 */
	public void setRossResId(Long rossResId) {
		this.rossResId = rossResId;
	}
	/**
	 * @return the rossResReqId
	 */
	public Long getRossResReqId() {
		return rossResReqId;
	}
	/**
	 * @param rossResReqId the rossResReqId to set
	 */
	public void setRossResReqId(Long rossResReqId) {
		this.rossResReqId = rossResReqId;
	}
	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}
	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}
	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}
	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	/**
	 * @return the agencyCode
	 */
	public String getAgencyCode() {
		return agencyCode;
	}
	/**
	 * @param agencyCode the agencyCode to set
	 */
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}
	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	/**
	 * @return the requestCatalogName
	 */
	public String getRequestCatalogName() {
		return requestCatalogName;
	}
	/**
	 * @param requestCatalogName the requestCatalogName to set
	 */
	public void setRequestCatalogName(String requestCatalogName) {
		this.requestCatalogName = requestCatalogName;
	}
	/**
	 * @return the requestCategoryName
	 */
	public String getRequestCategoryName() {
		return requestCategoryName;
	}
	/**
	 * @param requestCategoryName the requestCategoryName to set
	 */
	public void setRequestCategoryName(String requestCategoryName) {
		this.requestCategoryName = requestCategoryName;
	}
	/**
	 * @return the isPerson
	 */
	public Boolean getIsPerson() {
		return isPerson;
	}
	/**
	 * @param isPerson the isPerson to set
	 */
	public void setIsPerson(Boolean isPerson) {
		this.isPerson = isPerson;
	}
	/**
	 * @return the dataConflict
	 */
	public String getDataConflict() {
		return dataConflict;
	}
	/**
	 * @param dataConflict the dataConflict to set
	 */
	public void setDataConflict(String dataConflict) {
		this.dataConflict = dataConflict;
	}
	/**
	 * @return the excluded
	 */
	public Boolean getExcluded() {
		return excluded;
	}
	/**
	 * @param excluded the excluded to set
	 */
	public void setExcluded(Boolean excluded) {
		this.excluded = excluded;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the sortRequestNumber
	 */
	public String getSortRequestNumber() {
		return sortRequestNumber;
	}

	/**
	 * @param sortRequestNumber the sortRequestNumber to set
	 */
	public void setSortRequestNumber(String sortRequestNumber) {
		this.sortRequestNumber = sortRequestNumber;
	}

	/**
	 * @return the assignmentName
	 */
	public String getAssignmentName() {
		return assignmentName;
	}

	/**
	 * @param assignmentName the assignmentName to set
	 */
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	/**
	 * @return the matched
	 */
	public Boolean getMatched() {
		return matched;
	}

	/**
	 * @param matched the matched to set
	 */
	public void setMatched(Boolean matched) {
		this.matched = matched;
	}

	/**
	 * @return the matchType
	 */
	public String getMatchType() {
		return matchType;
	}

	/**
	 * @param matchType the matchType to set
	 */
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	/**
	 * @return the eisuiteResourceId
	 */
	public Long getEisuiteResourceId() {
		return eisuiteResourceId;
	}

	/**
	 * @param eisuiteResourceId the eisuiteResourceId to set
	 */
	public void setEisuiteResourceId(Long eisuiteResourceId) {
		this.eisuiteResourceId = eisuiteResourceId;
	}

	/**
	 * @return the eisuiteIncidentResourceId
	 */
	public Long getEisuiteIncidentResourceId() {
		return eisuiteIncidentResourceId;
	}

	/**
	 * @param eisuiteIncidentResourceId the eisuiteIncidentResourceId to set
	 */
	public void setEisuiteIncidentResourceId(Long eisuiteIncidentResourceId) {
		this.eisuiteIncidentResourceId = eisuiteIncidentResourceId;
	}

	/**
	 * @return the eisuiteSortRequestNumber
	 */
	public String getEisuiteSortRequestNumber() {
		return eisuiteSortRequestNumber;
	}

	/**
	 * @param eisuiteSortRequestNumber the eisuiteSortRequestNumber to set
	 */
	public void setEisuiteSortRequestNumber(String eisuiteSortRequestNumber) {
		this.eisuiteSortRequestNumber = eisuiteSortRequestNumber;
	}

	/**
	 * @return the eisuiteRequestNumber
	 */
	public String getEisuiteRequestNumber() {
		return eisuiteRequestNumber;
	}

	/**
	 * @param eisuiteRequestNumber the eisuiteRequestNumber to set
	 */
	public void setEisuiteRequestNumber(String eisuiteRequestNumber) {
		this.eisuiteRequestNumber = eisuiteRequestNumber;
	}

	/**
	 * @return the eisuiteUnitCode
	 */
	public String getEisuiteUnitCode() {
		return eisuiteUnitCode;
	}

	/**
	 * @param eisuiteUnitCode the eisuiteUnitCode to set
	 */
	public void setEisuiteUnitCode(String eisuiteUnitCode) {
		this.eisuiteUnitCode = eisuiteUnitCode;
	}

	/**
	 * @return the eisuiteAgencyCode
	 */
	public String getEisuiteAgencyCode() {
		return eisuiteAgencyCode;
	}

	/**
	 * @param eisuiteAgencyCode the eisuiteAgencyCode to set
	 */
	public void setEisuiteAgencyCode(String eisuiteAgencyCode) {
		this.eisuiteAgencyCode = eisuiteAgencyCode;
	}

	/**
	 * @return the eisuiteItemCode
	 */
	public String getEisuiteItemCode() {
		return eisuiteItemCode;
	}

	/**
	 * @param eisuiteItemCode the eisuiteItemCode to set
	 */
	public void setEisuiteItemCode(String eisuiteItemCode) {
		this.eisuiteItemCode = eisuiteItemCode;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the jetPort
	 */
	public String getJetPort() {
		return jetPort;
	}

	/**
	 * @param jetPort the jetPort to set
	 */
	public void setJetPort(String jetPort) {
		this.jetPort = jetPort;
	}

	/**
	 * @return the mobDate
	 */
	public Date getMobDate() {
		return mobDate;
	}

	/**
	 * @param mobDate the mobDate to set
	 */
	public void setMobDate(Date mobDate) {
		this.mobDate = mobDate;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @return the vendorOwnedFlag
	 */
	public String getVendorOwnedFlag() {
		return vendorOwnedFlag;
	}

	/**
	 * @param vendorOwnedFlag the vendorOwnedFlag to set
	 */
	public void setVendorOwnedFlag(String vendorOwnedFlag) {
		this.vendorOwnedFlag = vendorOwnedFlag;
	}

	/**
	 * @return the parentRossResReqId
	 */
	public Long getParentRossResReqId() {
		return parentRossResReqId;
	}

	/**
	 * @param parentRossResReqId the parentRossResReqId to set
	 */
	public void setParentRossResReqId(Long parentRossResReqId) {
		this.parentRossResReqId = parentRossResReqId;
	}

	/**
	 * @return the children
	 */
	public Collection<RossResourceVo> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Collection<RossResourceVo> children) {
		this.children = children;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the userUnmatched
	 */
	public Boolean getUserUnmatched() {
		return userUnmatched;
	}

	/**
	 * @param userUnmatched the userUnmatched to set
	 */
	public void setUserUnmatched(Boolean userUnmatched) {
		this.userUnmatched = userUnmatched;
	}

	/**
	 * @return the eisuiteItemName
	 */
	public String getEisuiteItemName() {
		return eisuiteItemName;
	}

	/**
	 * @param eisuiteItemName the eisuiteItemName to set
	 */
	public void setEisuiteItemName(String eisuiteItemName) {
		this.eisuiteItemName = eisuiteItemName;
	}

	
}
