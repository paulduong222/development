package gov.nwcg.isuite.core.rules.syscostrate.ovhd;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.SysCostRateOvhdVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractSysCostRateOvhdSaveRule extends AbstractRule {
	protected SysCostRateOvhdVo vo;
	protected String costRateCategory;
	
	public AbstractSysCostRateOvhdSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/**
	 * @param object
	 * @param objectName
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SysCostRateOvhdSaveRuleFactory.ObjectTypeEnum.SYSCOST_RATE_OVHD_VO.name()))
			vo = (SysCostRateOvhdVo)object;
		if(objectName.equals(SysCostRateOvhdSaveRuleFactory.ObjectTypeEnum.COST_RATE_CATEGORY.name()))
			costRateCategory = (String)object;
	}
	
}
