package gov.nwcg.isuite.core.rules.costgroups.deleteallocationdate;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractCostGroupsDeleteAllocationDateRule extends AbstractRule {
	protected CostGroupDao cgDao=null;
	protected CostGroupAgencyDayShare costGroupAgencyDayShareEntity=null;
	
	public AbstractCostGroupsDeleteAllocationDateRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(CostGroupsDeleteAllocationDateRuleFactory.ObjectTypeEnum.COST_GROUP_DAO.name()))
			cgDao = (CostGroupDao)object; 
		if(objectName.equals(CostGroupsDeleteAllocationDateRuleFactory.ObjectTypeEnum.COST_GROUP_AGENCY_DAY_SHARE_ENTITY.name()))
			costGroupAgencyDayShareEntity = ( CostGroupAgencyDayShare)object;
	}
}
