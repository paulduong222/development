package gov.nwcg.isuite.core.rules.iap.deletetask;

import gov.nwcg.isuite.core.vo.IapAircraftTaskVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteTaskRule extends AbstractRule {
	protected IapAircraftTaskVo iapAircraftTaskVo;
	
	public AbstractDeleteTaskRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteTaskRuleFactory.ObjectTypeEnum.IAP_AIRCRAFT_TASK_VO.name()))
			iapAircraftTaskVo = (IapAircraftTaskVo)object;
	}
}

