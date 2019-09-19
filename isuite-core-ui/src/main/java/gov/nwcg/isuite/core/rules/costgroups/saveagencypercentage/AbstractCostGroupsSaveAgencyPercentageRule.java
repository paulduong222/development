package gov.nwcg.isuite.core.rules.costgroups.saveagencypercentage;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDaySharePercentageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractCostGroupsSaveAgencyPercentageRule extends AbstractRule {
	protected CostGroupAgencyDayShare dayShareEntity=null;
	protected CostGroupAgencyDaySharePercentage daySharePctEntity=null;
	protected CostGroupAgencyDaySharePercentageVo daySharePctVo=null;
	
	public AbstractCostGroupsSaveAgencyPercentageRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(CostGroupsSaveAgencyPercentageRuleFactory.ObjectTypeEnum.COST_GROUP_AGENCY_DAY_SHARE_PERCENTAGE_VO.name()))
			this.daySharePctVo=(CostGroupAgencyDaySharePercentageVo)object;
		if(objectName.equals(CostGroupsSaveAgencyPercentageRuleFactory.ObjectTypeEnum.COST_GROUP_AGENCY_DAY_SHARE_ENTITY.name()))
			this.dayShareEntity=(CostGroupAgencyDayShare)object;
		if(objectName.equals(CostGroupsSaveAgencyPercentageRuleFactory.ObjectTypeEnum.COST_GROUP_AGENCY_DAY_SHARE_PCT_ENTITY.name()))
			this.daySharePctEntity=(CostGroupAgencyDaySharePercentage)object;
	}

}
