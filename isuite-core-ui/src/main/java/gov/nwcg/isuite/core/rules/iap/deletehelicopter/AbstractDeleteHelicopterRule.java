package gov.nwcg.isuite.core.rules.iap.deletehelicopter;

import gov.nwcg.isuite.core.vo.IapAircraftVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteHelicopterRule extends AbstractRule {
	protected IapAircraftVo iapAircraftVo;
	
	public AbstractDeleteHelicopterRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteHelicopterRuleFactory.ObjectTypeEnum.IAP_AIRCRAFT_VO.name()))
			iapAircraftVo = (IapAircraftVo)object;
	}
}

