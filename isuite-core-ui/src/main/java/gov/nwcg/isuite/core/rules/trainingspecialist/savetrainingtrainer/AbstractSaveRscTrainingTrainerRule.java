package gov.nwcg.isuite.core.rules.trainingspecialist.savetrainingtrainer;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.RscTrainingTrainerVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractSaveRscTrainingTrainerRule extends AbstractRule {
	
	RscTrainingTrainerVo rscTrainingTrainerVo;
	
	public AbstractSaveRscTrainingTrainerRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(TrainingTrainerSaveRuleFactory.ObjectTypeEnum.RSC_TRAINING_TRAINER_VO.name()))
			rscTrainingTrainerVo = (RscTrainingTrainerVo)object;
	}

}
