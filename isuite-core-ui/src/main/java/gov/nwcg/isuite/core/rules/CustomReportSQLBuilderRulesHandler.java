package gov.nwcg.isuite.core.rules;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.rules.reports.customreports.sqlbuilder.CustomReportSQLBuilderRuleFactory;
import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class CustomReportSQLBuilderRulesHandler extends AbstractRuleHandler {
	
	public CustomReportSQLBuilderRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}
	
	public int execute(CustomReportVo reportVo, DialogueVo dialogueVo) throws Exception {
		
		try{
			
			for(CustomReportSQLBuilderRuleFactory.RuleEnum ruleEnum : CustomReportSQLBuilderRuleFactory.RuleEnum.values()){
				IRule rule = CustomReportSQLBuilderRuleFactory.getInstance(ruleEnum, context, reportVo);
				
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
	
	public int executeProcessedActions(CustomReportVo reportVo, DialogueVo dialogueVo) throws Exception {
		try{
			
			for(CustomReportSQLBuilderRuleFactory.RuleEnum ruleEnum : CustomReportSQLBuilderRuleFactory.RuleEnum.values()){
				IRule rule = CustomReportSQLBuilderRuleFactory.getInstance(ruleEnum, context, reportVo);
				
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
	
}
