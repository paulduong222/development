package gov.nwcg.isuite.core.rules.dailycost.delete;

import gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteDailyCostRule extends AbstractRule {
	protected IncidentResourceDailyCostVo irdcVo=null;
	protected IncidentResourceDailyCostDao irdcDao=null;
	
	public AbstractDeleteDailyCostRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteDailyCostRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_DAILY_COST_VO.name()))
			irdcVo = (IncidentResourceDailyCostVo)object;
		if(objectName.equals(DeleteDailyCostRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_DAILY_COST_DAO.name()))
			irdcDao = (IncidentResourceDailyCostDao)object;
	}
	
	
}
