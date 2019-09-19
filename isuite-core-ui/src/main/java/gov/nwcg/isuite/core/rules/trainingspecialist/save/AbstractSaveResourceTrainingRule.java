package gov.nwcg.isuite.core.rules.trainingspecialist.save;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.ResourceTrainingVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractSaveResourceTrainingRule extends AbstractRule {
	
	ResourceTrainingVo resourceTrainingVo;
	
	public AbstractSaveResourceTrainingRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ResourceTrainingSaveRuleFactory.ObjectTypeEnum.RESOURCE_TRAINING_VO.name()))
			resourceTrainingVo = (ResourceTrainingVo)object;
	}

}
