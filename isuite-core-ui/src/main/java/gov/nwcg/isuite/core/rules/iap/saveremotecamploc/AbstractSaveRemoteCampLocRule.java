package gov.nwcg.isuite.core.rules.iap.saveremotecamploc;

import gov.nwcg.isuite.core.domain.IapRemoteCampLocations;
import gov.nwcg.isuite.core.vo.IapRemoteCampLocationsVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveRemoteCampLocRule extends AbstractRule {
	protected IapRemoteCampLocationsVo iapRemoteCampLocationsVo;
	protected IapRemoteCampLocations entity;
	
	public AbstractSaveRemoteCampLocRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveRemoteCampLocRuleFactory.ObjectTypeEnum.IAP_REMOTE_CAMP_LOCATIONS_VO.name()))
			iapRemoteCampLocationsVo = (IapRemoteCampLocationsVo)object;
		if(objectName.equals(SaveRemoteCampLocRuleFactory.ObjectTypeEnum.IAP_REMOTE_CAMP_LOCATIONS_ENTITY.name()))
			entity = (IapRemoteCampLocations)object;
	}
}

