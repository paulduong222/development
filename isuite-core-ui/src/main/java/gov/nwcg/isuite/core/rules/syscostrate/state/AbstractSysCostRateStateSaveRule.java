package gov.nwcg.isuite.core.rules.syscostrate.state;

import gov.nwcg.isuite.core.vo.SysCostRateStateVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSysCostRateStateSaveRule extends AbstractRule {
	protected SysCostRateStateVo vo;
	
	public AbstractSysCostRateStateSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/**
	 * @param object
	 * @param objectName
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SysCostRateStateSaveRuleFactory.ObjectTypeEnum.SYSCOST_RATE_STATE_VO.name()))
			vo = (SysCostRateStateVo)object;
	}
	
}
