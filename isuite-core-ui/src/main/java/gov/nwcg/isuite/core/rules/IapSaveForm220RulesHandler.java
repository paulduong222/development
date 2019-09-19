package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.rules.iap.saveform220.SaveForm220RuleFactory;
import gov.nwcg.isuite.core.vo.IapAircraftVo;
import gov.nwcg.isuite.core.vo.IapForm220Vo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class IapSaveForm220RulesHandler extends AbstractRuleHandler {

	public IapSaveForm220RulesHandler(ApplicationContext ctx) {
		super.context = ctx;
	}
	
	public int execute(IapForm220Vo vo, IapForm220 entity, DialogueVo dialogueVo) throws Exception {
		
		try {

			for(SaveForm220RuleFactory.RuleEnum ruleEnum : SaveForm220RuleFactory.RuleEnum.values()) {
				IRule rule = SaveForm220RuleFactory.getInstance(ruleEnum, context, vo, entity);
				
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
			coaVo.setMessageVo(new MessageVo("text.iap", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public int validateHelicopter(IapAircraftVo vo, DialogueVo dialogueVo) throws Exception {
		
		try {

			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));
			dialogueVo.setCourseOfActionVo(coaVo);
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(IapForm220Vo vo, IapForm220 entity, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}

