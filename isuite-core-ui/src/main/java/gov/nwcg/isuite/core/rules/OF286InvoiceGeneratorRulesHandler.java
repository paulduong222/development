package gov.nwcg.isuite.core.rules;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.rules.financial.OF286.OF286InvoiceGeneratorRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class OF286InvoiceGeneratorRulesHandler extends AbstractRuleHandler {
	
	public OF286InvoiceGeneratorRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(Collection<IncidentResourceVo> irs,
					  Collection<Date> dates,
		              TimeReportFilter filter,
		              DialogueVo dialogueVo) throws Exception {
		
		try{
			for(OF286InvoiceGeneratorRuleFactory.RuleEnum ruleEnum : OF286InvoiceGeneratorRuleFactory.RuleEnum.values()){
				IRule rule = OF286InvoiceGeneratorRuleFactory.getInstance(ruleEnum, context, irs, dates, filter);
				
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

	public void executeProcessedActions(Collection<IncidentResourceVo> irs,
									  Collection<Date> dates,
						              TimeReportFilter filter,
						              DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(OF286InvoiceGeneratorRuleFactory.RuleEnum ruleEnum : OF286InvoiceGeneratorRuleFactory.RuleEnum.values()){
				IRule rule = OF286InvoiceGeneratorRuleFactory.getInstance(ruleEnum, context, irs, dates, filter);
				
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
			
			for(OF286InvoiceGeneratorRuleFactory.RuleEnum ruleEnum : OF286InvoiceGeneratorRuleFactory.RuleEnum.values()){
				IRule rule = OF286InvoiceGeneratorRuleFactory.getInstance(ruleEnum, context, null, null, null);
			
				if(null != rule){
					rule.addAdditionalMessages(dialogueVo);
				}
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
}
