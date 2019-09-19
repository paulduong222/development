package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.UserImportFailureFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Filter for a userImportFailure.
 * @author mpoll
 *
 */
public class UserImportFailureFilterImpl extends FilterImpl implements UserImportFailureFilter {
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String loginName;
	private String unitCode;
	private String failureReason;

	public UserImportFailureFilterImpl() {
	}

	/**
	 * Returns the firstName.
	 *
	 * @return 
	 *		the firstName to return
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * Sets the firstName.
	 *
	 * @param firstName 
	 *			the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * Returns the lastName.
	 *
	 * @return 
	 *		the lastName to return
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * Sets the lastName.
	 *
	 * @param lastName 
	 *			the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * Returns the loginName.
	 *
	 * @return 
	 *		the loginName to return
	 */
	public String getLoginName() {
		return loginName;
	}


	/**
	 * Sets the loginName.
	 *
	 * @param loginName 
	 *			the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
	 * Returns the failureReason.
	 *
	 * @return 
	 *		the failureReason to return
	 */
	public String getFailureReason() {
		return failureReason;
	}


	/**
	 * Sets the failureReason.
	 *
	 * @param failureReason 
	 *			the failureReason to set
	 */
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}


	public static Collection<FilterCriteria> getFilterCriteria(UserImportFailureFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		// TYPE_EQUALS

		// TYPE_NOT_EQUAL

		// TYPE_IN_STRING

		// TYPE_ILIKE
		criteria.add( null != filter.getFirstName() && !filter.getFirstName().isEmpty() ? new FilterCriteria("this.firstName",filter.getFirstName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getLastName() && !filter.getLastName().isEmpty() ? new FilterCriteria("this.lastName",filter.getLastName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getLoginName() && !filter.getLoginName().isEmpty() ? new FilterCriteria("this.loginName",filter.getLoginName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getUnitCode() && !filter.getUnitCode().isEmpty() ? new FilterCriteria("this.homeUnitCode",filter.getUnitCode(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getFailureReason() && !filter.getFailureReason().isEmpty() ? new FilterCriteria("this.failureReason",filter.getFailureReason(),FilterCriteria.TYPE_ILIKE) : null);

		// TYPE_ISNULL

		return criteria;
	}

}
