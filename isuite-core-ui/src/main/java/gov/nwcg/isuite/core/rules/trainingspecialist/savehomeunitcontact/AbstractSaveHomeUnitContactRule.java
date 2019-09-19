package gov.nwcg.isuite.core.rules.trainingspecialist.savehomeunitcontact;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.ResourceHomeUnitContactVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractSaveHomeUnitContactRule extends AbstractRule {
	
	ResourceHomeUnitContactVo homeUnitContactVo;
	
	public AbstractSaveHomeUnitContactRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(HomeUnitContactSaveRuleFactory.ObjectTypeEnum.HOME_UNIT_CONTACT_VO.name()))
			homeUnitContactVo = (ResourceHomeUnitContactVo)object;
	}

}
