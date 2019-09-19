package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface SystemParameter extends Persistable {

	public void setParameterName(String val);
	
	public String getParameterName();
	
	public void setParameterValue(String val);
	
	public String getParameterValue();
	

}