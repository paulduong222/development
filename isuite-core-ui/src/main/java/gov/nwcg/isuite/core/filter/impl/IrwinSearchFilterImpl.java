package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.IrwinSearchFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import java.util.Date;

public class IrwinSearchFilterImpl extends FilterImpl implements IrwinSearchFilter {

	private Date startDate;
	private String incidentName;
	private String incidentState;
	private String unitId;
	private String number;
	private Long days;
	
	public IrwinSearchFilterImpl() {
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getIncidentName() {
		return incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getIncidentState() {
		return incidentState;
	}

	public void setIncidentState(String incidentState) {
		this.incidentState = incidentState;
	}
	
	public String getUnitId() {
		return unitId;
	}
	   
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getNumber() {
		return number;
	}
	   
	public void setNumber(String number) {
		this.number = number;
	}
	
	public Long getDays() {
		return days;
	}
	   
	public void setDays(Long days) {
		this.days = days;
	}

}

