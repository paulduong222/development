package gov.nwcg.isuite.core.rules.timepost.contractor;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.ContractorPaymentInfoVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class HasPrimaryRateRule extends AbstractContractorRule implements IRule {

	public static final String _RULE_NAME=TimePostContractorRuleFactory.RuleEnum.HAS_PRIMARY_RATE.name();
	
	public HasPrimaryRateRule(ApplicationContext ctx) {
		super(ctx,_RULE_NAME);
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		// TODO Auto-generated method stub
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
		 *  B.R. 6.0007 – Post Contractor/Cooperator Resource Time
		 *  
		 *  1.	The system must allow the user to post time for a Contractor/Cooperator Resource that 
		 *  	has at least one primary rate defined.
		 */
		
		Boolean hasPrimaryRate = false;
		
		try {
			hasPrimaryRate = 
				ContractorPaymentInfoVo.hasPrimaryRate(
						super.assignmentTimeEntity.getContractorPaymentInfo());
		} catch (Exception ex) {
			hasPrimaryRate = false;
		}
		
		if(hasPrimaryRate) {
			//the contractor has a primary rate
			
			return _OK;
		} else {
			
			//the contractor has no primary rate
			
			String errorMsg="You cannot post time to a contractor / cooperator that has no primary rate." ;	
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.time"
											, "error.800000"
											, new String[]{errorMsg}
											, MessageTypeEnum.CRITICAL));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
						
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;	
		}
	}
}
