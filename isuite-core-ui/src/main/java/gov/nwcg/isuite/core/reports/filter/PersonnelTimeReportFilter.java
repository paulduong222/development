package gov.nwcg.isuite.core.reports.filter;

import java.util.Collection;

import gov.nwcg.isuite.framework.filter.TimeReportFilter;

public class PersonnelTimeReportFilter extends TimeReportFilter {

	private Collection<String> resourceIds;
	private String timeUnitLeaderName;
	private String timeUnitLeaderPhoneNumber;
	private Boolean callsAreLongDistance;
	private Boolean dialOutsideLine;
	private String dialOutsideLineNumber;

	public PersonnelTimeReportFilter() {

	}

	public Collection<String> getResourceIds() {
    return resourceIds;
  }

  public void setResourceIds(Collection<String> resourceIds) {
    this.resourceIds = resourceIds;
  }

  public String getResourceIdsString() {
    String str = "";
    for(String id : resourceIds) {
      if(str.length()>0)
        str += ", ";
      
      str += id;
    }
    
    return str;
  }
  /**
	 * @return the timeUnitLeaderName
	 */
	public String getTimeUnitLeaderName() {
		return timeUnitLeaderName;
	}

	/**
	 * @param timeUnitLeaderName the timeUnitLeaderName to set
	 */
	public void setTimeUnitLeaderName(String timeUnitLeaderName) {
		this.timeUnitLeaderName = timeUnitLeaderName;
	}

	/**
	 * @return the callsAreLongDistance
	 */
	public Boolean getCallsAreLongDistance() {
		return callsAreLongDistance;
	}

	/**
	 * @param callsAreLongDistance the callsAreLongDistance to set
	 */
	public void setCallsAreLongDistance(Boolean callsAreLongDistance) {
		this.callsAreLongDistance = callsAreLongDistance;
	}

	/**
	 * @return the dialOutsideLine
	 */
	public Boolean getDialOutsideLine() {
		return dialOutsideLine;
	}

	/**
	 * @param dialOutsideLine the dialOutsideLine to set
	 */
	public void setDialOutsideLine(Boolean dialOutsideLine) {
		this.dialOutsideLine = dialOutsideLine;
	}

	/**
	 * @return the dialOutsideLineNumber
	 */
	public String getDialOutsideLineNumber() {
		return dialOutsideLineNumber;
	}

	/**
	 * @param dialOutsideLineNumber the dialOutsideLineNumber to set
	 */
	public void setDialOutsideLineNumber(String dialOutsideLineNumber) {
		this.dialOutsideLineNumber = dialOutsideLineNumber;
	}

	/**
	 * @return the timeUnitLeaderPhoneNumber
	 */
	public String getTimeUnitLeaderPhoneNumber() {
		return timeUnitLeaderPhoneNumber;
	}

	/**
	 * @param timeUnitLeaderPhoneNumber the timeUnitLeaderPhoneNumber to set
	 */
	public void setTimeUnitLeaderPhoneNumber(String timeUnitLeaderPhoneNumber) {
		this.timeUnitLeaderPhoneNumber = timeUnitLeaderPhoneNumber;
	}
}
