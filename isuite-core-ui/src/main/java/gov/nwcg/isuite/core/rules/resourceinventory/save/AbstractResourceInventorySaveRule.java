package gov.nwcg.isuite.core.rules.resourceinventory.save;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractResourceInventorySaveRule extends AbstractRule {
	protected ResourceVo resourceVo;
	
	public AbstractResourceInventorySaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ResourceInventorySaveRuleFactory.ObjectTypeEnum.RESOURCE_VO.name()))
			resourceVo = (ResourceVo)object;
	}

}
