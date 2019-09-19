package gov.nwcg.isuite.core.domain;

import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface TransferControl extends Persistable {


	public Date getStartTime();


	public void setStartTime(Date startTime);

	public Date getStopTime();

	public void setStopTime(Date stopTime);



	public String getIncidentTI() ;

	public void setIncidentTI(String incidentTI);


	public String getIncidentGroupTI();


	public void setIncidentGroupTI(String incidentGroupTI);


	public String getStatus();


	public void setStatus(String status);


	public String getLastError();


	public void setLastError(String lastError);


	public StringBooleanEnum getIsIncidentGroup();


	public void setIsIncidentGroup(StringBooleanEnum isIncidentGroup);

}
