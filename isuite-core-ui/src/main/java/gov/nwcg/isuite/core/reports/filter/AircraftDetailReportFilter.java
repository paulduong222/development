package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.framework.filter.ReportFilter;

import java.util.Date;
import java.util.List;

public class AircraftDetailReportFilter extends ReportFilter{
	
	private List<String> aircraftTypes;
	
	public boolean isReportOnly = false;
	public boolean isGraphOnly = false;
	public boolean isReportAndGraph = true;
	public boolean isDateRangeSelected = false;
	public boolean isIncludeAllAircraftType = true;
	public boolean isSelectiveAircraftTypes;
	public boolean isIncidentGroup = false;
	private DateTransferVo startDateVo=new DateTransferVo();
	private DateTransferVo endDateVo=new DateTransferVo();

	public AircraftDetailReportFilter() {
	}
	
	public DateTransferVo getStartDateVo() {
		return startDateVo;
	}

	public void setStartDateVo(DateTransferVo startDateVo) {
		this.startDateVo = startDateVo;
	}

	public DateTransferVo getEndDateVo() {
		return endDateVo;
	}

	public void setEndDateVo(DateTransferVo endDateVo) {
		this.endDateVo = endDateVo;
	}
	
	public List<String> getAircraftTypes() {
		return aircraftTypes;
	}

	public void setAircraftTypes(List<String> aircraftTypes) {
		this.aircraftTypes = (List<String>)aircraftTypes;
	}

	public String getFormatedAircraftTypes() {
		String types = "";
		
		for(String s :this.aircraftTypes) 
			types += "'" + s + "',";
		types = types.substring(0, types.length()-1);
		return types;
	}
	
	public String getReportType() {
		if(isReportOnly)  
			return "reportsOnly";
		else if(isReportAndGraph)
			return "reportsAndGraph";
		else if(isGraphOnly)
			return "graphOnly";
		else
			return "";
	}
	
	public boolean isIncidentGroup() {
		return this.isIncidentGroup;
	}

	public void setIncidentGroup(boolean isIncidentGroup) {
		this.isIncidentGroup = isIncidentGroup;
	}

	public boolean isIncludeAllAircraftType() {
		return this.isIncludeAllAircraftType;
	}
	
	public void setIncludeAllAircraftType(boolean isIncludeAllAircraftType) {
		this.isIncludeAllAircraftType = isIncludeAllAircraftType;
	}
	
	public boolean isSelectiveAircraftTypes() {
		return this.isSelectiveAircraftTypes;
	}
	
	public void setisSelectiveAircraftTypes(boolean isSelectiveAircraftTypes) {
		this.isSelectiveAircraftTypes = isSelectiveAircraftTypes;
	}
}
