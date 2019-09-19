package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.persistence.CustomReportDao;
import gov.nwcg.isuite.core.rules.reports.customreports.save.CustomReportSaveRuleFactory;
import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CustomReportSaveRulesHandler extends AbstractRuleHandler {
	
	public CustomReportSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(CustomReportVo filter,
		               DialogueVo dialogueVo,
		               UserVo userVo) throws Exception {
		
		try{
			CustomReportDao customReportDao = (CustomReportDao)context.getBean("customReportDao");
			
			for(CustomReportSaveRuleFactory.RuleEnum ruleEnum : CustomReportSaveRuleFactory.RuleEnum.values()){
				IRule rule = CustomReportSaveRuleFactory.getInstance(ruleEnum, context, filter, userVo, customReportDao);
				
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
			coaVo.setMessageVo(new MessageVo("text.customReports", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(CustomReportVo filter,
										DialogueVo dialogueVo,
							               UserVo userVo) throws ServiceException {
		try{
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(CustomReportSaveRuleFactory.RuleEnum ruleEnum : CustomReportSaveRuleFactory.RuleEnum.values()){
				IRule rule = CustomReportSaveRuleFactory.getInstance(ruleEnum, context, filter, userVo, null);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	public void addAdditionalMessages(DialogueVo dialogueVo, UserVo userVo) throws Exception {
		try{
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			for(CustomReportSaveRuleFactory.RuleEnum ruleEnum : CustomReportSaveRuleFactory.RuleEnum.values()){
				IRule rule = CustomReportSaveRuleFactory.getInstance(ruleEnum, context, null, null, null);
			
				if(null != rule){
					rule.addAdditionalMessages(dialogueVo);
				}
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
}
