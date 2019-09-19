package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.core.domain.AccountCode;
import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.framework.core.filter.Filter;

/**
 *  Filter for Account Code Manage page (<b>accountCodes.jspx</b>)
 */
public interface IncidentAccountCodeFilter extends Filter {
   
	/**
	 * Get the {@link AccountCode}
	 * @return A {@link String} representation of an {@link AccountCode}
	 */
	public String getAccountCode();
	
	/**
	 * Set the {@link AccountCode}
	 * @param accountCode - A {@link String} representation of an {@link AccountCode}
	 */
	public void setAccountCode(String accountCode);

	/**
	 * Get the {@link Agency} Code
	 * @return A {@link String} representation of an {@link Agency} Code
	 */
	public String getAgencyCode();

	/**
	 * Set the {@link Agency} Code
	 * @param agencyCode - A {@link String} representation of an {@link Agency} Code
	 */
	public void setAgencyCode(String agencyCode);

	/**
	 * Set the Region/Unit Code
	 * @param regionUnitCode - A {@link String} representation of the Region/Unit Code
	 */
	public void setRegionUnitCode(String regionUnitCode);

	/**
	 * Get the Region/Unit Code
	 * @return A {@link String} representation of a Region/Unit Code
	 */
	public String getRegionUnitCode();

	/**
	 * Set the {@link Agency} ID
	 * @param agencyId - agencyId as a {@link Long}
	 */
	public void setAgencyId(Long agencyId);

	/**
	 * Get the {@link Agency} ID
	 * @return The {@link Agency} ID as a {@link Long}
	 */
	public Long getAgencyId();
	
	/**
	 * Get the default flag
	 * @return The default flag
	 */
	public Boolean getDefaultFlag();
	
	/**
	 * Set the default flag
	 * @param defaultFlag
	 */
	public void setDefaultFlag(Boolean defaultFlag);
	
	/**
	 * Get the {@link Incident} name
	 * @return The {@link Incident} name
	 */
	public String getIncidentName();
	
	/**
	 * Set the {@link Incident} name
	 * @param incidentName
	 */
	public void setIncidentName(String incidentName);

	/**
	 * Returns the deletable.
	 *
	 * @return 
	 *		the deletable to return
	 */
	public Boolean getDeletable();
	
	/**
	 * Sets the deletable.
	 *
	 * @param deletable 
	 *			the deletable to set
	 */
	public void setDeletable(Boolean deletable);
}
