package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

/**
 * @author bsteiner
 *
 */
public interface ResourceFilter extends Filter {
	
	public String getResourceName();
	
	public void setResourceName(String val);
	
	public String getFirstName();
	
	public void setFirstName(String val);
	
	public String getLastName();
	
	public void setLastName(String val);

	public Boolean getEnabled();
	
	public void setEnabled(Boolean val);

	public Boolean getPerson();
	
	public void setPerson(Boolean val);

	public Boolean getContracted();
	
	public void setContracted(Boolean val);

	public Boolean getPermanent();
	
	public void setPermanent(Boolean val);

	public Boolean getActive();
	
	public void setActive(Boolean val);
	
	public Boolean getLeader();
	
	public void setLeader(Boolean val);

	public String getNameOnPictureId();
	
	public void setNameOnPictureId(String val);

	public String getContactName();
	
	public void setContactName(String val);

	public String getPhone();
	
	public void setPhone(String val);
	
	public String getEmail();
	
	public void setEmail(String val);

	public String getOther1();
	
	public void setOther1(String val);

	public String getOther2();
	
	public void setOther2(String val);

	public String getOther3();
	
	public void setOther3(String val);
	
	/**
	 * 
	 * @param crypticFilterCodeForReleaseDate
	 */
	public void setCrypticFilterCodeForReleaseDate(String crypticFilterCodeForReleaseDate);
	
	/**
	 * 
	 * @return
	 */
	public String getCrypticFilterCodeForReleaseDate();
	
}
