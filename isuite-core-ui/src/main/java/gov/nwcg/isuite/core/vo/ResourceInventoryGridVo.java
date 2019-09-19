package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;


public class ResourceInventoryGridVo extends AbstractVo {
	
	private String resourceName;
	//private String firstName;
	//private String lastName;
	//private Boolean nonStandard;
	private String itemCode;
	private String itemName;
	private String agency;
	//private String incidentName;
	private String unitId;
	private String cellPhoneNumber;
	private String rossResourceName;
	private Date rossUpdatedDate;
	private String requestCategoryCode;
	private Boolean person;
	private Boolean isPerson;
	
	//private Collection<ResourceInventoryGridVo> children = new ArrayList<ResourceInventoryGridVo>();
	
	public ResourceInventoryGridVo() {
	}

	public static ResourceInventoryGridVo getInstance(Resource entity) throws Exception {
		ResourceInventoryGridVo vo = new ResourceInventoryGridVo();
		
		vo.setId(entity.getId());
		vo.setPerson(entity.isPerson());
		
		if(entity.isPerson()) {
			vo.setResourceName(entity.getLastName() + ", " + entity.getFirstName());
			vo.setRequestCategoryCode("O");
			vo.setRossResourceName(entity.getRossLastName() + ", " + entity.getRossFirstName());
			
		} else {
			vo.setResourceName(entity.getResourceName());
			
			for(ResourceKind rk : entity.getResourceKinds()) {
				vo.setItemCode(rk.getKind().getCode());
				vo.setItemName(rk.getKind().getDescription());
				vo.setRequestCategoryCode(rk.getKind().getRequestCategory().getCode());
			}
			
			vo.setRossResourceName(entity.getRossResourceName());
		}
		
		//vo.setNonStandard(Boolean.FALSE);
		vo.setCellPhoneNumber(entity.getPhone());
		
		if(null != entity.getAgency()) {
			vo.setAgency(entity.getAgency().getAgencyCode());
		}
		if(null != entity.getOrganization()) {
			vo.setUnitId(entity.getOrganization().getUnitCode());
		}
		
		vo.setRossUpdatedDate(entity.getLastRossUpdatedDate());
		
		return vo;
	}
	
	public static Collection<ResourceInventoryGridVo> getInstances(Collection<Resource> entities) throws Exception {	
		Collection<ResourceInventoryGridVo> vos = new ArrayList<ResourceInventoryGridVo>();
		
		for(Resource entity : entities) {
			vos.add(ResourceInventoryGridVo.getInstance(entity));
		}
		
		return vos;
	}
	
	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param firstName the firstName to set
	 */
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}

	/**
	 * @return the firstName
	 */
//	public String getFirstName() {
//		return firstName;
//	}

	/**
	 * @param lastName the lastName to set
	 */
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}

	/**
	 * @return the lastName
	 */
//	public String getLastName() {
//		return lastName;
//	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param agency the agency to set
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}

	/**
	 * @return the agency
	 */
	public String getAgency() {
		return agency;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
//	public void setIncidentName(String incidentName) {
//		this.incidentName = incidentName;
//	}

	/**
	 * @return the incidentName
	 */
//	public String getIncidentName() {
//		return incidentName;
//	}
	
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	/**
	 * @return the unitId
	 */
	public String getUnitId() {
		return unitId;
	}
	
	/**
	 * @param nonStandard the nonStandard to set
	 */
//	public void setNonStandard(Boolean nonStandard) {
//		this.nonStandard = nonStandard;
//	}

	/**
	 * @return the nonStandard
	 */
//	public Boolean getNonStandard() {
//		return nonStandard;
//	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param cellPhoneNumber the cellPhoneNumber to set
	 */
	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	/**
	 * @return the cellPhoneNumber
	 */
	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	/**
	 * @param rossResourceName the rossResourceName to set
	 */
	public void setRossResourceName(String rossResourceName) {
		this.rossResourceName = rossResourceName;
	}

	/**
	 * @return the rossResourceName
	 */
	public String getRossResourceName() {
		return rossResourceName;
	}

	/**
	 * @param rossUpdatedDate the rossUpdatedDate to set
	 */
	public void setRossUpdatedDate(Date rossUpdatedDate) {
		this.rossUpdatedDate = rossUpdatedDate;
	}

	/**
	 * @return the rossUpdatedDate
	 */
	public Date getRossUpdatedDate() {
		return rossUpdatedDate;
	}

	/**
	 * @param requestCategoryCode the requestCategoryCode to set
	 */
	public void setRequestCategoryCode(String requestCategoryCode) {
		this.requestCategoryCode = requestCategoryCode;
	}

	/**
	 * @return the requestCategoryCode
	 */
	public String getRequestCategoryCode() {
		return requestCategoryCode;
	}
	
	/**
	 * @return person
	 */
	public Boolean isPerson() {
		return person;
	}

	/**
	 * @return person
	 */
	public Boolean getPerson() {
		return isPerson();
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Boolean person) {
		this.person = person;
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
	 * @param vos
	 * @return
	 */
	public static Collection<Long> toResourceIds(Collection<ResourceInventoryGridVo> vos) {
		Collection<Long> ids = new ArrayList<Long>();
		
		for(ResourceInventoryGridVo vo : vos){
			ids.add(vo.getId());
		}
		
		return ids;
	}
	
//	/**
//	 * @param children the children to set
//	 */
//	public void setChildren(Collection<ResourceInventoryGridVo> children) {
//		this.children = children;
//	}
//
//	/**
//	 * @return the children
//	 */
//	public Collection<ResourceInventoryGridVo> getChildren() {
//		return children;
//	}
	
//	/**
//	 * 
//	 * @param vos
//	 * @return
//	 * @throws Exception
//	 */
//	public static Collection<ResourceInventoryGridVo> toHierarchyCollection(Collection<ResourceInventoryGridVo> vos) throws Exception {
//		Collection<ResourceInventoryGridVo> rtnVos = new ArrayList<ResourceInventoryGridVo>();
//		Boolean addedToParent=false;
//		
//		for(ResourceInventoryGridVo vo : vos) {
//			Long parentResourceId = vo.getParentResourceId();
//			
//			if(LongUtility.hasValue(parentResourceId)) {
//				addedToParent=addToParent(vos,parentResourceId,vo);
//			}
//			if ((null==parentResourceId) || (parentResourceId==0L) || (!addedToParent)){
//				rtnVos.add(vo);
//			}
//		}
//		
//		return rtnVos;
//	}

//	/**
//	 * 
//	 * @param vos
//	 * @param parentResourceId
//	 * @param voToAdd
//	 * @return
//	 * @throws Exception
//	 */
//	private static Boolean addToParent(Collection<ResourceInventoryGridVo> vos, Long parentResourceId, ResourceInventoryGridVo voToAdd) throws Exception {
//		
//		Boolean addedToParent = false;
//		
//		for(ResourceInventoryGridVo vo : vos) {
//			if(vo.getId().equals(parentResourceId)){
//				vo.getChildren().add(voToAdd);
//				addedToParent = true;
//				return addedToParent;
//			}
//			
//			if(vo.children.size() > 0) {
//				for(ResourceInventoryGridVo cvo : vo.children) {
//					if(cvo.getId().equals(parentResourceId)) {
//						cvo.getChildren().add(voToAdd);
//						addedToParent = true;
//						return addedToParent;
//					}
//				}
//			}
//		}
//		return addedToParent;
//	}

}
