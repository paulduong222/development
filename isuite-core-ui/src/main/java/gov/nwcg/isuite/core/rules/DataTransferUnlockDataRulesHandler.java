package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.datatransfer.unlock.UnlockDataRuleFactory;
import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class DataTransferUnlockDataRulesHandler extends AbstractRuleHandler {
	
	public DataTransferUnlockDataRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(DataTransferVo dataTransferVo, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			for(UnlockDataRuleFactory.RuleEnum ruleEnum : UnlockDataRuleFactory.RuleEnum.values()){
				IRule rule = UnlockDataRuleFactory.getInstance(ruleEnum, context, dataTransferVo);
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.dataTransfer", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(DataTransferVo dataTransferVo,DialogueVo dialogueVo) throws ServiceException {
		
	}
	
}
