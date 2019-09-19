package gov.nwcg.isuite.core.filter;

import java.util.Date;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface IrwinSearchFilter extends Filter {
	
	public Date getStartDate();
	   
	public void setStartDate(Date startDate);
	
	public String getIncidentName();
	   
	public void setIncidentName(String incidentName);
	
	public String getIncidentState();
	   
	public void setIncidentState(String incidentState);
	
	public String getUnitId();
	   
	public void setUnitId(String unitId);
	
	public String getNumber();
	   
	public void setNumber(String number);
	
	public Long getDays();
	   
	public void setDays(Long days);

}


