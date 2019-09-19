package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;

public class IncidentSelector2Vo extends AbstractVo {
	// hierarchalgroupfield is the field ag-grid will use for the grid tree
	private Collection<String> hierachalGroupField = new ArrayList<String>();
	private Long id;
	private Long incidentId;
	private Long incidentGroupId;
	private Long parentGroupId;
	private String name;
	private String nameUnmodified;
	private String type;
	private String incidentNumber;
	private String unitCode;
	private String countryCode;
	private String eventType;
	private String startDate;
	private String agency;
	private String defaultAccountingCode;
	private String defaultAccountingCodeAgency;
	private Boolean isSiteManaged=false;

	// helper accessor fields for convenience in resource area
	private String other1Label;
	private String other2Label;
	private String other3Label;
	
	private Collection<IncidentSelector2Vo> children =new ArrayList<IncidentSelector2Vo>();

	public IncidentSelector2Vo() {

	}

	public static Collection<IncidentSelector2Vo> reGroup(Collection<IncidentSelector2Vo> vos, Collection<String> parentControlNames ) {
		Collection<IncidentSelector2Vo> newVos = new ArrayList<IncidentSelector2Vo>();
		for(IncidentSelector2Vo vo : vos ) {
//			System.out.println(vo.getName());
			Collection<String> controlNamesThis = new ArrayList<String>();
			vo.nameUnmodified = vo.getName();
			controlNamesThis.addAll(parentControlNames);
			if ( getNameCount(vos, vo.getName()) > 1) {
				controlNamesThis.add(vo.getName() + " (" + vo.getIncidentId() + ")");
			} else {
				controlNamesThis.add(vo.getName());
			}
			vo.setHierachalGroupField(controlNamesThis);
			newVos.add(vo);
			if(CollectionUtility.hasValue(vo.getChildren())){
				Collection<IncidentSelector2Vo> childVos = new ArrayList<IncidentSelector2Vo>();
				for(IncidentSelector2Vo vo2 : vo.getChildren()) {
					vo2.setParentGroupId(vo.getId());
					childVos.add(vo2);
				}
				newVos.addAll(IncidentSelector2Vo.reGroup(childVos, controlNamesThis));
			}
		}
		
		return newVos;
	}
	
	private static int getNameCount(Collection<IncidentSelector2Vo> vos, String name) {
		int cnt=0;
		for(IncidentSelector2Vo v : vos) {
			if(v.getName().equalsIgnoreCase(name)){
				cnt++;
			}
		}
		return cnt;
	}
	public static IncidentSelector2Vo getInstance(Incident entity) {
		IncidentSelector2Vo vo = new IncidentSelector2Vo();
		
		vo.setId(entity.getId());
		vo.setName(entity.getIncidentName());
		vo.setType("INCIDENT");
		vo.setStartDate(DateUtil.toDateString(entity.getIncidentStartDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
		vo.setIsSiteManaged(entity.getIsSiteManaged().getValue());
		
		if(null != entity.getHomeUnit())
			vo.setIncidentNumber("US-"+entity.getHomeUnit().getUnitCode()+"-"+entity.getNbr());

		if(null != entity.getEventType())
			vo.setEventType(entity.getEventType().getEventType());
		
		if(null != entity.getAgencyId()){
			vo.setAgency(entity.getAgency().getAgencyCode());
		}
		
		for(IncidentAccountCode iac : entity.getIncidentAccountCodes()){
			if(BooleanUtility.isTrue(iac.getDefaultFlag())){
				vo.setDefaultAccountingCode(iac.getAccountCode().getAccountCode());
				if(null != iac.getAccountCode().getAgency())
					vo.setDefaultAccountingCodeAgency(iac.getAccountCode().getAgency().getAgencyCode());
				break;
			}
		}

		if ( null != entity.getIncidentPrefsOtherFields()) {
			vo.setOther1Label(entity.getIncidentPrefsOtherFields().getOther1Label());
			vo.setOther2Label(entity.getIncidentPrefsOtherFields().getOther2Label());
			vo.setOther3Label(entity.getIncidentPrefsOtherFields().getOther3Label());
		}
		return vo;
	}
	
	public static Collection<Long> toIncidentIds(Collection<IncidentSelector2Vo> vos) {
		Collection<Long> ids = new ArrayList<Long>();
		
		for(IncidentSelector2Vo vo : vos){
			if(vo.getType().equals("INCIDENT"))
				ids.add(vo.getIncidentId());
		}
		
		return ids;
	}

	public static Collection<Long> toIncidentGroupIds(Collection<IncidentSelector2Vo> vos) {
		Collection<Long> ids = new ArrayList<Long>();
		
		for(IncidentSelector2Vo vo : vos){
			if(vo.getType().equals("INCIDENTGROUP"))
				ids.add(vo.getIncidentGroupId());
		}
		
		return ids;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIncidentNumber() {
		return incidentNumber;
	}

	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getDefaultAccountingCode() {
		return defaultAccountingCode;
	}

	public void setDefaultAccountingCode(String defaultAccountingCode) {
		this.defaultAccountingCode = defaultAccountingCode;
	}

	public String getDefaultAccountingCodeAgency() {
		return defaultAccountingCodeAgency;
	}

	public void setDefaultAccountingCodeAgency(String defaultAccountingCodeAgency) {
		this.defaultAccountingCodeAgency = defaultAccountingCodeAgency;
	}

	public Collection<IncidentSelector2Vo> getChildren() {
		return children;
	}

	public void setChildren(Collection<IncidentSelector2Vo> children) {
		this.children = children;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the isSiteManaged
	 */
	public Boolean getIsSiteManaged() {
		return isSiteManaged;
	}

	/**
	 * @param isSiteManaged the isSiteManaged to set
	 */
	public void setIsSiteManaged(Boolean isSiteManaged) {
		this.isSiteManaged = isSiteManaged;
	}

	public Collection<String> getHierachalGroupField() {
		return hierachalGroupField;
	}

	public void setHierachalGroupField(Collection<String> hierachalGroupField) {
		this.hierachalGroupField = hierachalGroupField;
	}

	public Long getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(Long parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	public String getNameUnmodified() {
		return nameUnmodified;
	}

	public void setNameUnmodified(String nameUnmodified) {
		this.nameUnmodified = nameUnmodified;
	}

	public String getOther1Label() {
		return other1Label;
	}

	public void setOther1Label(String other1Label) {
		this.other1Label = other1Label;
	}

	public String getOther2Label() {
		return other2Label;
	}

	public void setOther2Label(String other2Label) {
		this.other2Label = other2Label;
	}

	public String getOther3Label() {
		return other3Label;
	}

	public void setOther3Label(String other3Label) {
		this.other3Label = other3Label;
	}


}
