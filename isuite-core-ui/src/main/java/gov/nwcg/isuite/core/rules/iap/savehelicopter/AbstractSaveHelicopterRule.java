package gov.nwcg.isuite.core.rules.iap.savehelicopter;

import gov.nwcg.isuite.core.domain.IapAircraft;
import gov.nwcg.isuite.core.vo.IapAircraftVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveHelicopterRule extends AbstractRule {
	protected IapAircraftVo iapAircraftVo;
	protected IapAircraft entity;
	
	public AbstractSaveHelicopterRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveHelicopterRuleFactory.ObjectTypeEnum.IAP_AIRCRAFT_VO.name()))
			iapAircraftVo = (IapAircraftVo)object;
		if(objectName.equals(SaveHelicopterRuleFactory.ObjectTypeEnum.IAP_AIRCRAFT_ENTITY.name()))
			entity = (IapAircraft)object;
	}
}

