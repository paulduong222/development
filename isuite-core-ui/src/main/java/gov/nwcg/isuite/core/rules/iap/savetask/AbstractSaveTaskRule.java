package gov.nwcg.isuite.core.rules.iap.savetask;

import gov.nwcg.isuite.core.domain.IapAircraftTask;
import gov.nwcg.isuite.core.vo.IapAircraftTaskVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveTaskRule extends AbstractRule {
	protected IapAircraftTaskVo iapAircraftTaskVo;
	protected IapAircraftTask entity;
	
	public AbstractSaveTaskRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveTaskRuleFactory.ObjectTypeEnum.IAP_AIRCRAFT_TASK_VO.name()))
			iapAircraftTaskVo = (IapAircraftTaskVo)object;
		if(objectName.equals(SaveTaskRuleFactory.ObjectTypeEnum.IAP_AIRCRAFT_TASK_ENTITY.name()))
			entity = (IapAircraftTask)object;
	}
}

