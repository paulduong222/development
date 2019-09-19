package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class CheckUnknownItemCodeRules extends AbstractIncidentResourceSaveRule implements IRule{
	public static final String _RULE_NAME=IncidentResourceSaveRuleFactory.RuleEnum.CHECK_UNKNOWN_ITEM_CODE.name();

	public CheckUnknownItemCodeRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
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
					.add(super.buildNoActionCoaVo(_RULE_NAME,true));
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) {
	
		/*
			8.	[CR122] When a Resource has an Unknown Item Code and the user edits that 
			Resource, the system must not allow the user to save that Resource until 
			the user selects a valid Item Code. 
			An error message will display to the user indicating that they must select an 
			appropriate Item Code before saving. 
		 */
		String itemCode=vo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getCode();
		
		if(StringUtility.hasValue(itemCode)){
			if(itemCode.equals("UNK")){
				String errorMsg="You must select an appropriate Item Code, Unknown Item Code is invalid.";
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incidentResources"
												, "info.generic"
												, new String[]{errorMsg}
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
								
				dialogueVo.setCourseOfActionVo(coaVo);
				return _FAIL;
			}
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
