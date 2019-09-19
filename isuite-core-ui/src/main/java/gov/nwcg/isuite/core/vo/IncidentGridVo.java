package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.IncidentRestrictedStatusTypeEnum;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This is a VO to be used for the incident grid.  This is a READ-ONLY vo.
 * 
 * @author bsteiner
 *
 */
public class IncidentGridVo extends AbstractVo {
	private String incidentName;
	private String eventTypeName;
	private String countryCode;
	private String countrySubdivisionCode;
	private String unitCode;
	private String incidentJurisdictionCode;
	private String incidentNumber;
	private Date incidentStartDt;
	private boolean selected = false;
	private Boolean restricted;
	private RestrictedIncidentUserTypeEnum riuType;
	private IncidentRestrictedStatusTypeEnum incidentRestrictedStatusType;
	private String defaultAccountingCode;
	private Boolean sharedUser=false;
	private Boolean deletable;
	private Collection<String> restrictedAccessPermissions = new ArrayList<String>();
	private String rossIncidentId;
	private IncidentGroupVo incidentGroupVo;
	private Boolean isPrimaryIncident;

//	//IncidentGroup ID is needed for the WorkAreaIncidentSelector.incidentSelector drop down.
//	private Long incidentGroupId;
//	private Boolean isGroup;

	private Long rossXmlFileId;
	
	public IncidentGridVo() {
	}

	public static IncidentGridVo getInstance(Incident entity,Long userId, Boolean privilegedUser) {
		IncidentGridVo vo = new IncidentGridVo();

		vo.setId(entity.getId());
		vo.setIncidentName(entity.getIncidentName());
		vo.setIncidentNumber(entity.getNbr());
		vo.setIncidentStartDt(entity.getIncidentStartDate());
		vo.setRossIncidentId((null != entity.getRossIncidentId() ? entity.getRossIncidentId() : ""));
		
		vo.setRestricted(entity.getRestricted());

		if(vo.getRestricted()){
			if(privilegedUser) {
				vo.setIncidentRestrictedStatusType(IncidentRestrictedStatusTypeEnum.RESTRICTED_ACCESS);
			} else {
				vo.setIncidentRestrictedStatusType(IncidentRestrictedStatusTypeEnum.RESTRICTED_NO_ACCESS);
				if(null != entity.getRestrictedIncidentUsers()){
					for(RestrictedIncidentUser riuEntity : entity.getRestrictedIncidentUsers()){
						if(null != riuEntity.getUserId() && null == riuEntity.getAccessEndDate()){
							if(userId.compareTo(riuEntity.getUserId())==0){
								vo.setIncidentRestrictedStatusType(IncidentRestrictedStatusTypeEnum.RESTRICTED_ACCESS);
								//vo.setRiuType(riuEntity.getUserType());

								if(riuEntity.getUserType()==RestrictedIncidentUserTypeEnum.USER){
									vo.setSharedUser(true);
								}
								break;
							}
						}
					}
				}
			}
		}else{
			vo.setIncidentRestrictedStatusType(IncidentRestrictedStatusTypeEnum.UNRESTRICTED);
		}

		if(null != entity.getIncidentAccountCodes()){
			for(IncidentAccountCode iacEntity : entity.getIncidentAccountCodes()){
				if(iacEntity.getDefaultFlag()){
					vo.setDefaultAccountingCode(iacEntity.getAccountCode().getAccountCode());
					break;
				}
			}
		}
		if(null != entity.getCountrySubdivisionId())
			vo.setCountrySubdivisionCode(entity.getCountrySubdivision().getAbbreviation());

		if(null != entity.getAgencyId())
			vo.setIncidentJurisdictionCode(entity.getAgency().getAgencyCode());

		if(null != entity.getHomeUnitId())
			vo.setUnitCode(entity.getHomeUnit().getUnitCode());

		if(null != entity.getEventTypeId())
			vo.setEventTypeName(entity.getEventType().getEventType());

		
		vo.setDeletable(true);  //TODO:  Determine deletable criteria. -dbudge

		return vo;
	}

	public static Collection<IncidentGridVo> getInstances(Collection<Incident> entities, Long userId, Boolean privilegedUser){
		Collection<IncidentGridVo> vos = new ArrayList<IncidentGridVo>();

		for(Incident entity : entities){
			vos.add(getInstance(entity,userId, privilegedUser));
		}

		return vos;
	}

	/**
	 * Returns the incidentName.
	 *
	 * @return 
	 *		the incidentName to return
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * Sets the incidentName.
	 *
	 * @param incidentNam e
	 *			the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * Returns the eventTypeName.
	 *
	 * @return 
	 *		the eventTypeName to return
	 */
	public String getEventTypeName() {
		return eventTypeName;
	}

	/**
	 * Sets the eventTypeName.
	 *
	 * @param eventTypeName 
	 *			the eventTypeName to set
	 */
	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}

	/**
	 * Returns the countryCode.
	 *
	 * @return 
	 *		the countryCode to return
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * Sets the countryCode.
	 *
	 * @param countryCode 
	 *			the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * Returns the countrySubdvisionCode.
	 *
	 * @return 
	 *		the countrySubdvisionCode to return
	 */
	public String getCountrySubdivisionCode() {
		return countrySubdivisionCode;
	}

	/**
	 * Sets the countrySubdvisionCode.
	 *
	 * @param countrySubdvisionCode 
	 *			the countrySubdvisionCode to set
	 */
	public void setCountrySubdivisionCode(String countrySubdivisionCode) {
		this.countrySubdivisionCode = countrySubdivisionCode;
	}

	/**
	 * Returns the unitCode.
	 *
	 * @return 
	 *		the unitCode to return
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * Sets the unitCode.
	 *
	 * @param unitCode 
	 *			the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * Returns the incidentJurisdictionCode.
	 *
	 * @return 
	 *		the incidentJurisdictionCode to return
	 */
	public String getIncidentJurisdictionCode() {
		return incidentJurisdictionCode;
	}

	/**
	 * Sets the incidentJurisdictionCode.
	 *
	 * @param incidentJurisdictionCode 
	 *			the incidentJurisdictionCode to set
	 */
	public void setIncidentJurisdictionCode(String incidentJurisdictionCode) {
		this.incidentJurisdictionCode = incidentJurisdictionCode;
	}

	/**
	 * Returns the incidentNumber.
	 *
	 * @return 
	 *		the incidentNumber to return
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}

	/**
	 * Sets the incidentNumber.
	 *
	 * @param incidentNumber 
	 *			the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	/**
	 * Returns the incidentStartDt.
	 *
	 * @return 
	 *		the incidentStartDt to return
	 */
	public Date getIncidentStartDt() {
		return incidentStartDt;
	}

	/**
	 * Sets the incidentStartDt.
	 *
	 * @param incidentStartDt 
	 *			the incidentStartDt to set
	 */
	public void setIncidentStartDt(Date incidentStartDt) {
		this.incidentStartDt = incidentStartDt;
	}

	/**
	 * Returns the selected.
	 *
	 * @return 
	 *		the selected to return
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Sets the selected.
	 *
	 * @param selected 
	 *			the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Returns the restricted.
	 *
	 * @return 
	 *		the restricted to return
	 */
	public Boolean getRestricted() {
		return (null != restricted ? restricted : false);
	}

	/**
	 * Sets the restricted.
	 *
	 * @param restricted 
	 *			the restricted to set
	 */
	public void setRestricted(Boolean restricted) {
		this.restricted = restricted;
	}

	/**
	 * Sets the userType
	 * 
	 * @param userType  the userType to set
	 */
	public void setRiuType(RestrictedIncidentUserTypeEnum riuType) {
		this.riuType = riuType;
	}

	/**
	 * Returns the userType
	 * 
	 * @return  the userType to return
	 */
	public RestrictedIncidentUserTypeEnum getRiuType() {
		return riuType;
	}

	/**
	 * Returns the defaultAccountingCode.
	 *
	 * @return 
	 *		the defaultAccountingCode to return
	 */
	public String getDefaultAccountingCode() {
		return defaultAccountingCode;
	}

	/**
	 * Sets the defaultAccountingCode.
	 *
	 * @param defaultAccountingCode 
	 *			the defaultAccountingCode to set
	 */
	public void setDefaultAccountingCode(String defaultAccountingCode) {
		this.defaultAccountingCode = defaultAccountingCode;
	}

	public IncidentRestrictedStatusTypeEnum getIncidentRestrictedStatusType() {
		return incidentRestrictedStatusType;
	}

	public void setIncidentRestrictedStatusType(
			IncidentRestrictedStatusTypeEnum incidentRestrictedStatusType) {
		this.incidentRestrictedStatusType = incidentRestrictedStatusType;
	}

//	/**
//	 * @return the incidentGroupId
//	 */
//	public Long getIncidentGroupId() {
//		return incidentGroupId;
//	}
//
//	/**
//	 * @param incidentGroupId the incidentGroupId to set
//	 */
//	public void setIncidentGroupId(Long incidentGroupId) {
//		this.incidentGroupId = incidentGroupId;
//	}

	/**
	 * Returns the sharedUser.
	 *
	 * @return 
	 *		the sharedUser to return
	 */
	public Boolean getSharedUser() {
		return sharedUser;
	}

	/**
	 * Sets the sharedUser.
	 *
	 * @param sharedUser 
	 *			the sharedUser to set
	 */
	public void setSharedUser(Boolean sharedUser) {
		this.sharedUser = sharedUser;
	}

	/**
	 * Returns the restrictedAccessPermissions.
	 *
	 * @return 
	 *		the restrictedAccessPermissions to return
	 */
	public Collection<String> getRestrictedAccessPermissions() {
		return restrictedAccessPermissions;
	}

	/**
	 * Sets the restrictedAccessPermissions.
	 *
	 * @param restrictedAccessPermissions 
	 *			the restrictedAccessPermissions to set
	 */
	public void setRestrictedAccessPermissions(
			Collection<String> restrictedAccessPermissions) {
		this.restrictedAccessPermissions = restrictedAccessPermissions;
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

//   /**
//    * @return the isGroup
//    */
//   public Boolean getIsGroup() {
//      return isGroup;
//   }
//
//   /**
//    * @param isGroup the isGroup to set
//    */
//   public void setIsGroup(Boolean isGroup) {
//      this.isGroup = isGroup;
//   }

	/**
	 * @return the rossIncidentId
	 */
	public String getRossIncidentId() {
		return rossIncidentId;
	}

	/**
	 * @param rossIncidentId the rossIncidentId to set
	 */
	public void setRossIncidentId(String rossIncidentId) {
		this.rossIncidentId = rossIncidentId;
	}

	/**
	 * @return the rossXmlFileId
	 */
	public Long getRossXmlFileId() {
		return rossXmlFileId;
	}

	/**
	 * @param rossXmlFileId the rossXmlFileId to set
	 */
	public void setRossXmlFileId(Long rossXmlFileId) {
		this.rossXmlFileId = rossXmlFileId;
	}

	/**
	 * @return the incidentGroupVo
	 */
	public IncidentGroupVo getIncidentGroupVo() {
		return incidentGroupVo;
	}

	/**
	 * @param incidentGroupVo the incidentGroupVo to set
	 */
	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
		this.incidentGroupVo = incidentGroupVo;
	}

	/**
	 * @return the isPrimaryIncident
	 */
	public Boolean getIsPrimaryIncident() {
		return isPrimaryIncident;
	}

	/**
	 * @param isPrimaryIncident the isPrimaryIncident to set
	 */
	public void setIsPrimaryIncident(Boolean isPrimaryIncident) {
		this.isPrimaryIncident = isPrimaryIncident;
	}


}
