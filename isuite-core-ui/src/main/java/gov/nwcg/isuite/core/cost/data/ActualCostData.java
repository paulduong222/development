package gov.nwcg.isuite.core.cost.data;

import gov.nwcg.isuite.core.domain.IncidentAccountCode;

public class ActualCostData {
	public IncidentAccountCode iac;
	public double totalAmount=0.0;
	public double units=0.0;
	public double rateAmount=0.0; 
	public double adjustmentAmount=0.0;
	public String rateType="HOURLY";
	
}
