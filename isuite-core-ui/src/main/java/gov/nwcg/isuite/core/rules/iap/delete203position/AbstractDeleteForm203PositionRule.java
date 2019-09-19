package gov.nwcg.isuite.core.rules.iap.delete203position;

import gov.nwcg.isuite.core.vo.IapPersonnelVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteForm203PositionRule extends AbstractRule {
	protected IapPersonnelVo iapPersonnelVo;
	
	public AbstractDeleteForm203PositionRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteForm203PositionRuleFactory.ObjectTypeEnum.IAP_PERSONNEL_VO.name()))
			iapPersonnelVo = (IapPersonnelVo)object;
	}
}

