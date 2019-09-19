package gov.nwcg.isuite.core.cost.data;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;

import java.math.BigDecimal;


public class DefaultCostRateData {
	public EmploymentTypeEnum employmentType=null;
	public String rateType="";
	public BigDecimal rateAmount=BigDecimal.valueOf(0.0);
	public Integer units=Integer.valueOf(1);
	
	public String estimateRateType="";
	public BigDecimal estimateRateAmount=BigDecimal.valueOf(0.0);
	public Integer estimateUnits=Integer.valueOf(0);
	
	public CostGroup costGroup;
	public IncidentShift incidentShift;
	
}
