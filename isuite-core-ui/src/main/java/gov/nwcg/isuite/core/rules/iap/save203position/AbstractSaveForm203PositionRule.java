package gov.nwcg.isuite.core.rules.iap.save203position;

import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.vo.IapPersonnelVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveForm203PositionRule extends AbstractRule {
	protected IapPersonnelVo iapPersonnelVo;
	protected IapPersonnel entity;
	
	public AbstractSaveForm203PositionRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveForm203PositionRuleFactory.ObjectTypeEnum.IAP_PERSONNEL_VO.name()))
			iapPersonnelVo = (IapPersonnelVo)object;
		if(objectName.equals(SaveForm203PositionRuleFactory.ObjectTypeEnum.IAP_PERSONNEL_ENTITY.name()))
			entity = (IapPersonnel)object;
	}
}

