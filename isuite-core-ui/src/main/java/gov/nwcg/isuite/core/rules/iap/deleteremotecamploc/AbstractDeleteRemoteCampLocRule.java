package gov.nwcg.isuite.core.rules.iap.deleteremotecamploc;

import gov.nwcg.isuite.core.vo.IapRemoteCampLocationsVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteRemoteCampLocRule extends AbstractRule {
	protected IapRemoteCampLocationsVo iapRemoteCampLocationsVo;
	
	public AbstractDeleteRemoteCampLocRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	} 
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteRemoteCampLocRuleFactory.ObjectTypeEnum.IAP_REMOTE_CAMP_LOCATIONS_VO.name()))
			iapRemoteCampLocationsVo = (IapRemoteCampLocationsVo)object;
	}
}

