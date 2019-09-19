package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.types.ResourceStatusTypeEnum;

import java.util.Collection;

/**
 * 
 * @author bsteiner
 */
public class ResourceGridVo extends AbstractVo {
	private String resourceName;
	private String firstName;
	private String lastName;
    private ResourceVo parentResourceVo;
    private ResourceVo permanentResourceVo;
    private Long parentResourceId;
    private Long permanentResourceId;
	private Collection<Long> childResourceIds;
    private OrganizationVo organizationVo;
    private Long organizationId;
    private ResourceClassificationEnum resourceClassification;
    private ResourceStatusTypeEnum resourceStatus;
    private AgencyVo agencyVo;
    private Long agencyId;
    private Boolean person;
    private Boolean contracted;
    private Boolean leader;
    private Integer leaderType;
    private String nameOnPictureId;
    private String contactName;
    private String phone;
    private String email;
    private String other1;
    private String other2;
    private String other3;
    private Boolean enabled;
    private Boolean permanent;
    private Boolean deletable=true;

    private String unitCode;
    
	public ResourceGridVo() {}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public ResourceVo getParentResourceVo() {
		return parentResourceVo;
	}

	public void setParentResourceVo(ResourceVo parentResourceVo) {
		this.parentResourceVo = parentResourceVo;
	}

	public ResourceVo getPermanentResourceVo() {
		return permanentResourceVo;
	}

	public void setPermanentResourceVo(ResourceVo permanentResourceVo) {
		this.permanentResourceVo = permanentResourceVo;
	}

	public Long getParentResourceId() {
		return parentResourceId;
	}

	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}

	public Long getPermanentResourceId() {
		return permanentResourceId;
	}

	public void setPermanentResourceId(Long permanentResourceId) {
		this.permanentResourceId = permanentResourceId;
	}

	public Collection<Long> getChildResourceIds() {
		return childResourceIds;
	}

	public void setChildResourceIds(Collection<Long> childResourceIds) {
		this.childResourceIds = childResourceIds;
	}

	public OrganizationVo getOrganizationVo() {
		return organizationVo;
	}

	public void setOrganizationVo(OrganizationVo organizationVo) {
		this.organizationVo = organizationVo;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public ResourceClassificationEnum getResourceClassification() {
		return resourceClassification;
	}

	public void setResourceClassification(
			ResourceClassificationEnum resourceClassification) {
		this.resourceClassification = resourceClassification;
	}

	public ResourceStatusTypeEnum getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(ResourceStatusTypeEnum resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public AgencyVo getAgencyVo() {
		return agencyVo;
	}

	public void setAgencyVo(AgencyVo agencyVo) {
		this.agencyVo = agencyVo;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public Boolean getPerson() {
		return person;
	}

	public void setPerson(Boolean person) {
		this.person = person;
	}

	public Boolean getContracted() {
		return contracted;
	}

	public void setContracted(Boolean contracted) {
		this.contracted = contracted;
	}

	public Boolean getLeader() {
		return leader;
	}

	public void setLeader(Boolean leader) {
		this.leader = leader;
	}
	
	public Integer getLeaderType() {
		return leaderType;
	}

	public void setLeader(Integer leaderType) {
		this.leaderType = leaderType;
	}

	public String getNameOnPictureId() {
		return nameOnPictureId;
	}

	public void setNameOnPictureId(String nameOnPictureId) {
		this.nameOnPictureId = nameOnPictureId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOther1() {
		return other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
	}

	public String getOther2() {
		return other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}

	public String getOther3() {
		return other3;
	}

	public void setOther3(String other3) {
		this.other3 = other3;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getPermanent() {
		return permanent;
	}

	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Boolean getDeletable() {		  
		return deletable;
	}

	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}
}