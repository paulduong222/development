package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class RossImportProcessResourceVo extends AbstractVo {
	private Long rossXmlFileDataId;
	private Long rossResId;
	private String requestNumber;
	private String resourceName;
	private String assignmentName;
	private String itemCode;
	private Long itemId;
	private String unitCode;
	private Long unitId;
	private String agencyCode;
	private Long agencyId;
	private Date mobEtd;
	
	private Boolean excludeResource=false;
	private Boolean excludeFromRoster=false;
	private String rossGroupAssignment="";

	private Long rossResReqId;
	private String lastName;
	private String firstName;
	private String middleName;
	private String requestCatalogName;
	private String requestCategoryName;
	
	private Long eisuiteResourceId;
	
	// only used as part of synchronization
	private RossXmlFileDataVo rossXmlFileDataVo;
	private Collection<RossXmlFileDataVo> qualifications = new ArrayList<RossXmlFileDataVo>();
	
	public RossImportProcessResourceVo(){
		
	}

	public static HashMap toHashMapRxfdId(Collection<RossImportProcessResourceVo> vos){
		HashMap<Long,RossImportProcessResourceVo> map = new HashMap<Long,RossImportProcessResourceVo>();
	
		for(RossImportProcessResourceVo vo : vos){
			map.put(vo.getRossXmlFileDataId(), vo);
		}
		
		return map;
	}
	
	public static HashMap atoHashMapRossResId(Collection<RossImportProcessResourceVo> vos){
		HashMap<Long,RossImportProcessResourceVo> map = new HashMap<Long,RossImportProcessResourceVo>();
	
		for(RossImportProcessResourceVo vo : vos){
			map.put(vo.getRossResId(), vo);
		}
		
		return map;
	}

	public static HashMap toHashMapRossResReqId(Collection<RossImportProcessResourceVo> vos){
		HashMap<Long,RossImportProcessResourceVo> map = new HashMap<Long,RossImportProcessResourceVo>();
	
		for(RossImportProcessResourceVo vo : vos){
			//System.out.println(vo.getRossResReqId());
			map.put(vo.getRossResReqId(), vo);
		}
		
		return map;
	}
	
	public static HashMap toHashMapEisuiteId(Collection<RossImportProcessResourceVo> vos){
		HashMap<Long,RossImportProcessResourceVo> map = new HashMap<Long,RossImportProcessResourceVo>();
	
		for(RossImportProcessResourceVo vo : vos){
			if(LongUtility.hasValue(vo.getEisuiteResourceId())){
				map.put(vo.getEisuiteResourceId() , vo);
			}
		}
		
		return map;
	}
	
	public static Collection<Long> toRossResourceIds(Collection<RossImportProcessResourceVo> vos){
		Collection<Long> ids = new ArrayList<Long>();
		
		for(RossImportProcessResourceVo vo : vos){
			ids.add(vo.getRossResId());
		}
		return ids;
	}

	public static Collection<Long> toRxfdExcludedIds(Collection<RossImportProcessResourceVo> vos){
		Collection<Long> ids = new ArrayList<Long>();
		
		for(RossImportProcessResourceVo vo : vos){
			if(vo.getExcludeResource())
				ids.add(vo.getRossXmlFileDataId());
		}
		
		return ids;
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
		if(!StringUtility.hasValue(resourceName))
			return assignmentName;
		else
			return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the excludeResource
	 */
	public Boolean getExcludeResource() {
		return excludeResource;
	}

	/**
	 * @param excludeResource the excludeResource to set
	 */
	public void setExcludeResource(Boolean excludeResource) {
		this.excludeResource = excludeResource;
	}

	/**
	 * @return the eIsuiteResourceId
	 */
	public Long getEisuiteResourceId() {
		return eisuiteResourceId;
	}

	/**
	 * @param isuiteResourceId the eIsuiteResourceId to set
	 */
	public void setEisuiteResourceId(Long isuiteResourceId) {
		eisuiteResourceId = isuiteResourceId;
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
	 * @return the itemId
	 */
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
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
	 * @return the unitId
	 */
	public Long getUnitId() {
		return unitId;
	}

	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
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
	 * @return the agencyId
	 */
	public Long getAgencyId() {
		return agencyId;
	}

	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	/**
	 * @return the qualifications
	 */
	public Collection<RossXmlFileDataVo> getQualifications() {
		return qualifications;
	}

	/**
	 * @param qualifications the qualifications to set
	 */
	public void setQualifications(
			Collection<RossXmlFileDataVo> qualifications) {
		this.qualifications = qualifications;
	}

	/**
	 * @return the rossXmlFileDataVo
	 */
	public RossXmlFileDataVo getRossXmlFileDataVo() {
		return rossXmlFileDataVo;
	}

	/**
	 * @param rossXmlFileDataVo the rossXmlFileDataVo to set
	 */
	public void setRossXmlFileDataVo(RossXmlFileDataVo rossXmlFileDataVo) {
		this.rossXmlFileDataVo = rossXmlFileDataVo;
	}

	/**
	 * @return the excludeFromRoster
	 */
	public Boolean getExcludeFromRoster() {
		return excludeFromRoster;
	}

	/**
	 * @param excludeFromRoster the excludeFromRoster to set
	 */
	public void setExcludeFromRoster(Boolean excludeFromRoster) {
		this.excludeFromRoster = excludeFromRoster;
	}

	public Date getMobEtd() {
		return mobEtd;
	}

	public void setMobEtd(Date mobEtd) {
		this.mobEtd = mobEtd;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getRequestCatalogName() {
		return requestCatalogName;
	}

	public void setRequestCatalogName(String requestCatalogName) {
		this.requestCatalogName = requestCatalogName;
	}

	public String getRequestCategoryName() {
		return requestCategoryName;
	}

	public void setRequestCategoryName(String requestCategoryName) {
		this.requestCategoryName = requestCategoryName;
	}

	public Long getRossResReqId() {
		return rossResReqId;
	}

	public void setRossResReqId(Long rossResReqId) {
		this.rossResReqId = rossResReqId;
	}

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public String getRossGroupAssignment() {
		return rossGroupAssignment;
	}

	public void setRossGroupAssignment(String rossGroupAssignment) {
		this.rossGroupAssignment = rossGroupAssignment;
	}
	
	
}
