package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.IapOperationalPeriodFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class IapOperationalPeriodFilterImpl extends FilterImpl implements IapOperationalPeriodFilter {

	private static final long serialVersionUID = 4670369347847396264L;
	
	public IapOperationalPeriodFilterImpl() {
	}

	private Long incidentId = 0L;
	private String name;
	private String startTime;
	private String endTime;
	private String font;
	
	
	/**
	 * @return endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @return font
	 */
	public String getFont() {
		return font;
	}

	/**
	 * @return incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @param font
	 */
	public void setFont(String font) {
		this.font = font;
	}

	/**
	 * @param incidentId
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}
