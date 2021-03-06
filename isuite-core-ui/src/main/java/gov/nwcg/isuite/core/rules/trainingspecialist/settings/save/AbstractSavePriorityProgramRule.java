package gov.nwcg.isuite.core.rules.trainingspecialist.settings.save;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.persistence.PriorityProgramDao;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractSavePriorityProgramRule extends AbstractRule {
	
	PriorityProgramVo priorityProgramVo;
	PriorityProgramDao priorityProgramDao;
	
	public AbstractSavePriorityProgramRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(PriorityProgramSaveRuleFactory.ObjectTypeEnum.PRIORITY_PROGRAM_VO.name()))
			priorityProgramVo = (PriorityProgramVo)object;
		if(objectName.equals(PriorityProgramSaveRuleFactory.ObjectTypeEnum.PRIORITY_PROGRAM_DAO.name()))
			priorityProgramDao = (PriorityProgramDao)object;
	}

}
