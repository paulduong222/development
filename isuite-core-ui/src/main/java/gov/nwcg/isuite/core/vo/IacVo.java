package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IacVo {
	private Date activityDate;
	private Long incidentAccountCodeId;

	public static Collection<Long> getIdsByDate(Date dt,Collection<IacVo> vos) {
		Collection<Long> list = new ArrayList<Long>();
		
		for(IacVo v : vos){
			if(DateUtil.isSameDate(dt, v.getActivityDate()))
				list.add(v.getIncidentAccountCodeId());
		}
		
		return list;
	}
	
	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		return activityDate;
	}
	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}
	/**
	 * @return the incidentAccountCodeId
	 */
	public Long getIncidentAccountCodeId() {
		return incidentAccountCodeId;
	}
	/**
	 * @param incidentAccountCodeId the incidentAccountCodeId to set
	 */
	public void setIncidentAccountCodeId(Long incidentAccountCodeId) {
		this.incidentAccountCodeId = incidentAccountCodeId;
	}

	
}
