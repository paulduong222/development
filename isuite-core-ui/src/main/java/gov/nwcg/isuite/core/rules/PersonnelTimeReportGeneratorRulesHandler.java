package gov.nwcg.isuite.core.rules;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.PersonnelTimeReportData;
import gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter;
import gov.nwcg.isuite.core.rules.reports.personnelTime.PersonnelTimeReportGeneratorRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class PersonnelTimeReportGeneratorRulesHandler extends AbstractRuleHandler {
	
	public PersonnelTimeReportGeneratorRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(PersonnelTimeReportFilter filter, DialogueVo dialogueVo) throws Exception {
		
		try{
			for(PersonnelTimeReportGeneratorRuleFactory.RuleEnum ruleEnum : PersonnelTimeReportGeneratorRuleFactory.RuleEnum.values()){
				IRule rule = PersonnelTimeReportGeneratorRuleFactory.getInstance(ruleEnum, context, filter);
				
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
			coaVo.setMessageVo(new MessageVo("text.incidentGroup", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(PersonnelTimeReportFilter filter,
										DialogueVo dialogueVo) throws ServiceException {
		try{
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(PersonnelTimeReportGeneratorRuleFactory.RuleEnum ruleEnum : PersonnelTimeReportGeneratorRuleFactory.RuleEnum.values()){
				IRule rule = PersonnelTimeReportGeneratorRuleFactory.getInstance(ruleEnum, context, filter);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	public void addAdditionalMessages(DialogueVo dialogueVo) throws Exception {
		try{
			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			for(PersonnelTimeReportGeneratorRuleFactory.RuleEnum ruleEnum : PersonnelTimeReportGeneratorRuleFactory.RuleEnum.values()){
				IRule rule = PersonnelTimeReportGeneratorRuleFactory.getInstance(ruleEnum, context, null);
			
				if(null != rule){
					rule.addAdditionalMessages(dialogueVo);
				}
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
}
