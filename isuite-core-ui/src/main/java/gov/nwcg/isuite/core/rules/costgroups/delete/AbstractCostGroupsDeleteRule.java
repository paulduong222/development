package gov.nwcg.isuite.core.rules.costgroups.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.vo.CostGroupVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractCostGroupsDeleteRule extends AbstractRule {
	protected CostGroupVo costGroupVo=null;
	protected CostGroupDao cgDao=null;
	protected CostGroup costGroupEntity=null;
	
	public AbstractCostGroupsDeleteRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(CostGroupsDeleteRuleFactory.ObjectTypeEnum.COST_GROUP_VO.name()))
			costGroupVo = (CostGroupVo)object;
		if(objectName.equals(CostGroupsDeleteRuleFactory.ObjectTypeEnum.COST_GROUP_DAO.name()))
			cgDao = (CostGroupDao)object; 
		if(objectName.equals(CostGroupsDeleteRuleFactory.ObjectTypeEnum.COST_GROUP_ENTITY.name()))
			costGroupEntity = (CostGroup)object;
	}
}
