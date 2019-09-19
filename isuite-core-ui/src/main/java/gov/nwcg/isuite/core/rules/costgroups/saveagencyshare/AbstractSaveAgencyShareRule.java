package gov.nwcg.isuite.core.rules.costgroups.saveagencyshare;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDayShareVo;
import gov.nwcg.isuite.core.vo.CostGroupVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveAgencyShareRule extends AbstractRule {
	protected CostGroupVo costGroupVo=null;
	protected CostGroupDao cgDao=null;
	protected CostGroupAgencyDayShare entity=null;
	protected CostGroupAgencyDayShareVo vo=null;
	
	public AbstractSaveAgencyShareRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveAgencyShareRuleFactory.ObjectTypeEnum.COST_GROUP_DAO.name()))
			cgDao = (CostGroupDao)object; 
		if(objectName.equals(SaveAgencyShareRuleFactory.ObjectTypeEnum.COST_GROUP_DAY_SHARE_ENTITY.name()))
			entity = (CostGroupAgencyDayShare)object;
		if(objectName.equals(SaveAgencyShareRuleFactory.ObjectTypeEnum.COST_GROUP_DAY_SHARE_VO.name()))
			vo = (CostGroupAgencyDayShareVo)object;
	}
}
