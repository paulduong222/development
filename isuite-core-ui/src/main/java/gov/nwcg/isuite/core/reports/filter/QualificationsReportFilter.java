package gov.nwcg.isuite.core.reports.filter;

import gov.nwcg.isuite.core.vo.KindVo;

import java.util.ArrayList;
import java.util.Collection;

public class QualificationsReportFilter {
	private Boolean traineesOnly;
	private Boolean excludeTrainees;
	private Long incidentId;
	private Long incidentGroupId;
	
	private Collection<KindVo> selectedKinds = new ArrayList<KindVo>();
	
	public QualificationsReportFilter(){
		
	}

	public static String getKinds(QualificationsReportFilter filter){
		String s = "";
		
		for(KindVo vo : filter.getSelectedKinds()){
			s=s+(s.length()<1 ? "'"+vo.getCode()+"'" :  ",'"+vo.getCode()+"'");
		}
		
		return s;
	}
	
	public Boolean getTraineesOnly() {
		if(null==traineesOnly)
			return false;
		else
			return traineesOnly;
	}

	public void setTraineesOnly(Boolean traineesOnly) {
		this.traineesOnly = traineesOnly;
	}

	public Boolean getExcludeTrainees() {
		if(null==excludeTrainees)
			return false;
		else
			return excludeTrainees;
	}

	public void setExcludeTrainees(Boolean excludeTrainees) {
		this.excludeTrainees = excludeTrainees;
	}

	public Collection<KindVo> getSelectedKinds() {
		return selectedKinds;
	}

	public void setSelectedKinds(Collection<KindVo> selectedKinds) {
		this.selectedKinds = selectedKinds;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
}
