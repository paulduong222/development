package gov.nwcg.isuite.core.rules.training;

import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public abstract class AbstractTrainingDateSaveRule extends AbstractRule {
	protected Date trainingDate=null;
	
	public AbstractTrainingDateSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(TrainingDateSaveRuleFactory.ObjectTypeEnum.TRAINING_DATE.name()))
			trainingDate = (Date) object;
	}
}
