package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.ResourceFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author bsteiner
 */
public class ResourceFilterImpl extends FilterImpl implements ResourceFilter {
	private static final long serialVersionUID = 1L;
	
	private String resourceName;
	private String firstName;
	private String lastName;
    private Boolean person;
    private Boolean contracted;
    private Boolean leader;
    private String nameOnPictureId;
    private String contactName;
    private String phone;
    private String email;
    private String other1;
    private String other2;
    private String other3;
    private Boolean enabled;
    private Boolean permanent;
    private String crypticFilterCodeForReleaseDate;
    
    /*
     * By default always filter for active (non-deleted) resources.
     * If deleted resources are needed, implementations should override
     * the default setting and set it False.
     */
    private Boolean active = Boolean.TRUE; 
    
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
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static Collection<FilterCriteria> getFilterCriteria(ResourceFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		// TYPE_EQUALS
		criteria.add( null != filter.getPermanent() && filter.getPermanent() ? new FilterCriteria("this.permanent",filter.getPermanent(),FilterCriteria.TYPE_EQUAL) : null);
		criteria.add( null != filter.getEnabled() && filter.getEnabled() ? new FilterCriteria("this.enabled",filter.getEnabled(),FilterCriteria.TYPE_EQUAL) : null);
		criteria.add( null != filter.getLeader() && filter.getLeader() ? new FilterCriteria("this.leader",filter.getLeader(),FilterCriteria.TYPE_EQUAL) : null);
		criteria.add( null != filter.getPerson() && filter.getPerson() ? new FilterCriteria("this.person",filter.getPerson(),FilterCriteria.TYPE_EQUAL) : null);
		criteria.add( null != filter.getContracted() && filter.getContracted() ? new FilterCriteria("this.contracted",filter.getContracted(),FilterCriteria.TYPE_EQUAL) : null);
		
		// TYPE_ILIKE
		criteria.add( null != filter.getLastName() && !filter.getLastName().isEmpty()  ? new FilterCriteria("this.lastName",filter.getLastName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getFirstName() && !filter.getFirstName().isEmpty()  ? new FilterCriteria("this.firstName",filter.getFirstName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getResourceName() && !filter.getResourceName().isEmpty()  ? new FilterCriteria("this.resourceName",filter.getResourceName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getContactName() && !filter.getContactName().isEmpty()  ? new FilterCriteria("this.contactName",filter.getContactName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getEmail() && !filter.getEmail().isEmpty()  ? new FilterCriteria("this.email",filter.getEmail(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getOther1() && !filter.getOther1().isEmpty()  ? new FilterCriteria("this.other1",filter.getOther1(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getOther2() && !filter.getOther2().isEmpty()  ? new FilterCriteria("this.other2",filter.getOther2(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getOther3() && !filter.getOther3().isEmpty()  ? new FilterCriteria("this.other3",filter.getOther3(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getNameOnPictureId() && !filter.getNameOnPictureId().isEmpty()  ? new FilterCriteria("this.nameOnPictureId",filter.getNameOnPictureId(),FilterCriteria.TYPE_ILIKE) : null);

		// TYPE_ISNULL
		criteria.add( null != filter.getActive() && filter.getActive() ? new FilterCriteria("this.deletedDate",null,FilterCriteria.TYPE_ISNULL) : null);

		// TYPE_ISNOTNULL
		criteria.add( null != filter.getActive() && !filter.getActive() ? new FilterCriteria("this.deletedDate",null,FilterCriteria.TYPE_ISNOTNULL) : null);
		
		return criteria;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.ResourceFilter#getCrypticFilterCodeForReleaseDate()
	 */
	@Override
	public String getCrypticFilterCodeForReleaseDate() {
		return this.crypticFilterCodeForReleaseDate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.ResourceFilter#setCrypticFilterCodeForReleaseDate(java.lang.String)
	 */
	@Override
	public void setCrypticFilterCodeForReleaseDate(String crypticFilterCodeForReleaseDate) {
		this.crypticFilterCodeForReleaseDate = crypticFilterCodeForReleaseDate;
	}
	
}