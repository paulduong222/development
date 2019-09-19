package gov.nwcg.isuite.core.rules.costgroups.deleteallocationpercent;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractCostGroupsDeleteAllocationPctRule extends AbstractRule {
	protected CostGroupDao cgDao=null;
	protected CostGroupAgencyDaySharePercentage costGroupAgencyDaySharePctEntity=null;
	
	public AbstractCostGroupsDeleteAllocationPctRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(CostGroupsDeleteAllocationPctRuleFactory.ObjectTypeEnum.COST_GROUP_DAO.name()))
			cgDao = (CostGroupDao)object; 
		if(objectName.equals(CostGroupsDeleteAllocationPctRuleFactory.ObjectTypeEnum.COST_GROUP_AGENCY_DAY_SHARE_PCT_ENTITY.name()))
			costGroupAgencyDaySharePctEntity = ( CostGroupAgencyDaySharePercentage)object;
	}
}
