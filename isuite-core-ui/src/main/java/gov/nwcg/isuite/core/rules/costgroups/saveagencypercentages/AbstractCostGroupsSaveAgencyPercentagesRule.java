package gov.nwcg.isuite.core.rules.costgroups.saveagencypercentages;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.CostGroupAgencyDaySharePercentageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractCostGroupsSaveAgencyPercentagesRule extends AbstractRule {
	protected Collection<CostGroupAgencyDaySharePercentageVo> costGroupAgencyDaySharePercentageVos=null;
	
	public AbstractCostGroupsSaveAgencyPercentagesRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(CostGroupsSaveAgencyPercentagesRuleFactory.ObjectTypeEnum.COST_GROUP_AGENCY_PERCENTAGE_VOS))
			costGroupAgencyDaySharePercentageVos = (Collection<CostGroupAgencyDaySharePercentageVo>)object;
	}

}
