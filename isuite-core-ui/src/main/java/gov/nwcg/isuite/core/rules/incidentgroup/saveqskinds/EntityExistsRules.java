package gov.nwcg.isuite.core.rules.incidentgroup.saveqskinds;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class EntityExistsRules extends AbstractIncidentGroupSaveQSKindsRule implements IRule{
	
	public EntityExistsRules(ApplicationContext ctx, String rname)
	{
		super(ctx,rname);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
			
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(ruleName,true));
			
			

			
			
			
			
			
//			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
//				// add to processed
//				dialogueVo.getCourseOfActionVo().setIsComplete(true);
//				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
//						
//			}else{
//				
//			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception 
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * Incident Group entity must be persisted before saving QS Kinds
		 */

		if (null == entity) {
			MessageVo msgVo = new MessageVo();
			msgVo.setTitleProperty("text.incidentGroup");
			msgVo.setMessageType(MessageTypeEnum.CRITICAL);
			msgVo.setMessageProperty("Can not save QS Kinds to an Incident Group that is not persisted");
			
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(msgVo);
					
			dialogueVo.setCourseOfActionVo(coaVo);
			return _FAIL;
		}
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
