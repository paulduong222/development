package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.ContractorFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;


public class ContractorFilterImpl extends FilterImpl implements ContractorFilter {
	
	private static final long serialVersionUID = -1764469133883804076L;
	private Boolean groupByContractor=false;
	private Boolean groupByAgreement=false;
	
	private Long id;
	
	private String name;
	private String duns;
	private String phone;
	private String addressLine1;
	private String city;
	private String state;
	private String postalCode;
	
	private String agreement;
	private String pointOfHire;
	private Date beginDate;
	private Date endDate;
	
	private Boolean deletable;
	private String deletableString;
	private Collection<Long> incidentIds;
	private Long incidentGroupId;
	
	public ContractorFilterImpl(){
		
	}

	/**
	 * Returns the groupByContractor.
	 *
	 * @return 
	 *		the groupByContractor to return
	 */
	public Boolean getGroupByContractor() {
		return groupByContractor;
	}

	/**
	 * Sets the groupByContractor.
	 *
	 * @param groupByContractor 
	 *			the groupByContractor to set
	 */
	public void setGroupByContractor(Boolean groupByContractor) {
		this.groupByContractor = groupByContractor;
	}

	/**
	 * Returns the groupByAgreement.
	 *
	 * @return 
	 *		the groupByAgreement to return
	 */
	public Boolean getGroupByAgreement() {
		return groupByAgreement;
	}

	/**
	 * Sets the groupByAgreement.
	 *
	 * @param groupByAgreement 
	 *			the groupByAgreement to set
	 */
	public void setGroupByAgreement(Boolean groupByAgreement) {
		this.groupByAgreement = groupByAgreement;
	}

	/**
	 * Returns the name.
	 *
	 * @return 
	 *		the name to return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name 
	 *			the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the duns.
	 *
	 * @return 
	 *		the duns to return
	 */
	public String getDuns() {
		return duns;
	}

	/**
	 * Sets the duns.
	 *
	 * @param duns 
	 *			the duns to set
	 */
	public void setDuns(String duns) {
		this.duns = duns;
	}

	/**
	 * Returns the phone.
	 *
	 * @return 
	 *		the phone to return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone 
	 *			the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Returns the addressLine1.
	 *
	 * @return 
	 *		the addressLine1 to return
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * Sets the addressLine1.
	 *
	 * @param addressLine1 
	 *			the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * Returns the city.
	 *
	 * @return 
	 *		the city to return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city 
	 *			the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Returns the state.
	 *
	 * @return 
	 *		the state to return
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state 
	 *			the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Returns the postalCode.
	 *
	 * @return 
	 *		the postalCode to return
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Sets the postalCode.
	 *
	 * @param postalCode 
	 *			the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * Returns the agreement.
	 *
	 * @return 
	 *		the agreement to return
	 */
	public String getAgreement() {
		return agreement;
	}

	/**
	 * Sets the agreement.
	 *
	 * @param agreement 
	 *			the agreement to set
	 */
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	/**
	 * Returns the pointOfHire.
	 *
	 * @return 
	 *		the pointOfHire to return
	 */
	public String getPointOfHire() {
		return pointOfHire;
	}

	/**
	 * Sets the pointOfHire.
	 *
	 * @param pointOfHire 
	 *			the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
	}

	/**
	 * Returns the beginDate.
	 *
	 * @return 
	 *		the beginDate to return
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * Sets the beginDate.
	 *
	 * @param beginDate 
	 *			the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * Returns the endDate.
	 *
	 * @return 
	 *		the endDate to return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the endDate.
	 *
	 * @param endDate 
	 *			the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.ContractorFilter#setDeletable(java.lang.Boolean)
	 */
	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.ContractorFilter#getDeletable()
	 */
	public Boolean getDeletable() {
		return deletable;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.ContractorFilter#getDeletableString()
	 */
	public String getDeletableString() {
		return deletableString;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.ContractorFilter#setDeletableString(java.lang.String)
	 */
	public void setDeletableString(String deletableString) {
		 this.deletableString = deletableString;
		 this.setDeletable(super.determineDeletableValue(this.deletableString));
	 }
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.ContractorFilter#setIncidentIds(java.util.Collection)
	 */
	public void setIncidentIds(Collection<Long> incidentIds) {
		this.incidentIds = incidentIds;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.ContractorFilter#getIncidentIds()
	 */
	public Collection<Long> getIncidentIds() {
		return incidentIds;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.ContractorFilter#getIdsAsLongs()
	 */
	public Collection<Long> getIncidentIdsAsLongs() throws Exception {
		 Collection<Long> ids = new ArrayList<Long>();
		   
//		 for(Object o : this.incidentIds){
//			 ids.add(TypeConverter.convertToLong(o));
//		 }
		 
		// Use safe string conversion to prevent errors on Linux OS
		   Iterator iter = this.incidentIds.iterator();
			
			while(iter.hasNext()){
				String s = String.valueOf(iter.next());
				Long l = TypeConverter.convertToLong(s);
				ids.add(l);
			}
		   
		 return ids;
	}

	/**
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static Collection<FilterCriteria> getFilterCriteria(ContractorFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		// TYPE_ILIKE
		criteria.add( null != filter.getName() && !filter.getName().isEmpty()  ? new FilterCriteria("this.name",filter.getName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getDuns() && !filter.getDuns().isEmpty()  ? new FilterCriteria("this.duns",filter.getDuns(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getPhone() && !filter.getPhone().isEmpty()  ? new FilterCriteria("this.phone",filter.getPhone(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getAddressLine1() && !filter.getAddressLine1().isEmpty()  ? new FilterCriteria("addr.addressLine1",filter.getAddressLine1(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getCity() && !filter.getCity().isEmpty()  ? new FilterCriteria("addr.city",filter.getCity(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getState() && !filter.getState().isEmpty()  ? new FilterCriteria("csd.abbreviation",filter.getState(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getPostalCode() && !filter.getPostalCode().isEmpty()  ? new FilterCriteria("addr.postalCode",filter.getPostalCode(),FilterCriteria.TYPE_ILIKE) : null);

		criteria.add( null != filter.getAgreement() && !filter.getAgreement().isEmpty()  ? new FilterCriteria("agreement.agreement",filter.getAgreement(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getPointOfHire() && !filter.getPointOfHire().isEmpty()  ? new FilterCriteria("agreement.pointOfHire",filter.getPointOfHire(),FilterCriteria.TYPE_ILIKE) : null);

		// TYPE_EQUAL
		if(LongUtility.hasValue(filter.getId())){
			criteria.add(new FilterCriteria("this.id", filter.getId(), FilterCriteria.TYPE_EQUAL));
		}
		
		if (null==filter.getIncidentIds() || filter.getIncidentIds().size() < 1) {
			//criteria.add(new FilterCriteria("wa.id", filter.getWorkAreaId(), FilterCriteria.TYPE_EQUAL));
		}
		
		// TYPE_ISNULL

		// TYPE_ISNOTNULL
		
		// TYPE_IN
		if (null != filter.getIncidentIds() && filter.getIncidentIds().size() > 0) {
			criteria.add(new FilterCriteria("i.id", filter.getIncidentIdsAsLongs(), FilterCriteria.TYPE_IN_STRING));
		} 
		
		return criteria;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
