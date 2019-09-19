package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.IapMasterFrequency;
import gov.nwcg.isuite.core.persistence.IapMasterFrequencyDao;
import gov.nwcg.isuite.core.rules.iap.savemasterfrequency.MasterFrequencySaveRuleFactory;
import gov.nwcg.isuite.core.vo.IapMasterFrequencyVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class IapMasterFrequencySaveRulesHandler extends AbstractRuleHandler {
	
	public IapMasterFrequencySaveRulesHandler(ApplicationContext ctx) {
		super.context = ctx;
	}
	
	/**
	 * @param iapMasterFrequencyVo
	 * @param entity
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(IapMasterFrequencyVo iapMasterFrequencyVo, IapMasterFrequency entity, DialogueVo dialogueVo) throws Exception {
	
		try {
			
			IapMasterFrequencyDao iapMasterFrequencyDao = (IapMasterFrequencyDao)context.getBean("iapMasterFrequencyDao");
			
			for(MasterFrequencySaveRuleFactory.RuleEnum ruleEnum : MasterFrequencySaveRuleFactory.RuleEnum.values()) {
				IRule rule = MasterFrequencySaveRuleFactory.getInstance(
						ruleEnum, context, iapMasterFrequencyVo, entity, iapMasterFrequencyDao);
			
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
			coaVo.setMessageVo(new MessageVo("text.masterFrequency", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	/**
	 * @param iapMasterFrequencyVo
	 * @param entity
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(IapMasterFrequencyVo iapMasterFrequencyVo, IapMasterFrequency entity, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			IapMasterFrequencyDao iapMasterFrequencyDao = (IapMasterFrequencyDao)context.getBean("iapMasterFrequencyDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(MasterFrequencySaveRuleFactory.RuleEnum ruleEnum : MasterFrequencySaveRuleFactory.RuleEnum.values()) {
				IRule rule = MasterFrequencySaveRuleFactory.getInstance(
						ruleEnum, context, iapMasterFrequencyVo, entity, iapMasterFrequencyDao);
				
				if(null != rule) {
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

}
