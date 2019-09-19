package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.FinancialExport;
import gov.nwcg.isuite.core.persistence.FinancialExportDao;
import gov.nwcg.isuite.core.rules.financialexport.export.FinancialExportExportRuleFactory;
import gov.nwcg.isuite.core.vo.FinancialExportVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class FinancialExportExportRulesHandler extends AbstractRuleHandler {
	
	public FinancialExportExportRulesHandler(ApplicationContext ctx){
		super.context=ctx;
	}
	
	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(FinancialExport entity, FinancialExportVo vo, DialogueVo dialogueVo) throws Exception {
		try {
			FinancialExportDao feDao = (FinancialExportDao)context.getBean("financialExportDao");
			
			for(FinancialExportExportRuleFactory.RuleEnum ruleEnum : FinancialExportExportRuleFactory.RuleEnum.values()) {
				IRule rule = FinancialExportExportRuleFactory.getInstance(ruleEnum, context, vo, entity, feDao);
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
		}catch(Exception e){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.financialExport", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public int executeProcessedActions(FinancialExport entity, FinancialExportVo vo, DialogueVo dialogueVo) throws ServiceException {
		try{
			FinancialExportDao feDao = (FinancialExportDao)context.getBean("financialExportDao");
			
			for(FinancialExportExportRuleFactory.RuleEnum ruleEnum : FinancialExportExportRuleFactory.RuleEnum.values()) {
				IRule rule = FinancialExportExportRuleFactory.getInstance(ruleEnum, context, vo, entity, feDao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.financialExport", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}


}
