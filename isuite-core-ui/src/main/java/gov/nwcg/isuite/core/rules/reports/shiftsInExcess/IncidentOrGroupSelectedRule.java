package gov.nwcg.isuite.core.rules.reports.shiftsInExcess;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class IncidentOrGroupSelectedRule extends AbstractShiftsInExcessGenerationRule implements IRule {

	public IncidentOrGroupSelectedRule(ApplicationContext ctx, String rname)
	{
		super(ctx, rname);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
					
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _FAIL) {
			    return _FAIL;
			} 	
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(ruleName,true));
		
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		if ((filter.getIncidentId() != null && filter.getIncidentId() > 0) 
				|| (filter.getIncidentGroupId() != null && filter.getIncidentGroupId() >0)) {

			return _OK;
		} else {
			MessageVo messageVo = new MessageVo();
			messageVo.setMessageProperty("text.pleaseSelectAnIncidentOrIncidentGroup");
		    messageVo.setTitleProperty("text.timeReports");

		    CourseOfActionVo coa = new CourseOfActionVo();
		    coa.setCoaName(ruleName);
		    coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		    coa.setMessageVo(messageVo);
		    coa.setIsDialogueEnding(true);
		    
		    dialogueVo.setCourseOfActionVo(coa);
		}
		return _FAIL;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
