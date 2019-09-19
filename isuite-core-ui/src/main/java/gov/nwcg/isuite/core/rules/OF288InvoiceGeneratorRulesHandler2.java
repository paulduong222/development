package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.rules.financial.OF288V2.OF288InvoiceGeneratorRuleFactory2;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeAdustDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimePostDataVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class OF288InvoiceGeneratorRulesHandler2 extends AbstractRuleHandler {
	
	public OF288InvoiceGeneratorRulesHandler2(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(Collection<IncidentResourceTimeDataVo> irTimeDataVos,
					  Collection<Date> dates,
		              TimeReportFilter filter,
		  			  Collection<IncidentResourceTimePostDataVo> timePostDataVos,
		  			  Collection<IncidentResourceTimeAdustDataVo> timeAdjustDataVos,
		              DialogueVo dialogueVo) throws Exception {
		
		try{
			for(OF288InvoiceGeneratorRuleFactory2.RuleEnum ruleEnum : OF288InvoiceGeneratorRuleFactory2.RuleEnum.values()){
				IRule rule = OF288InvoiceGeneratorRuleFactory2.getInstance(ruleEnum, context, irTimeDataVos, dates, filter,
						timePostDataVos, timeAdjustDataVos);
				
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

	public void executeProcessedActions(Collection<IncidentResourceTimeDataVo> irTimeDataVos,
									  Collection<Date> dates,
						              TimeReportFilter filter,
						  			  Collection<IncidentResourceTimePostDataVo> timePostDataVos,
						  			  Collection<IncidentResourceTimeAdustDataVo> timeAdjustDataVos,
						              DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(OF288InvoiceGeneratorRuleFactory2.RuleEnum ruleEnum : OF288InvoiceGeneratorRuleFactory2.RuleEnum.values()){
				IRule rule = OF288InvoiceGeneratorRuleFactory2.getInstance(ruleEnum, context, irTimeDataVos, dates, filter,
						timePostDataVos, timeAdjustDataVos);
				
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
			
			for(OF288InvoiceGeneratorRuleFactory2.RuleEnum ruleEnum : OF288InvoiceGeneratorRuleFactory2.RuleEnum.values()){
				IRule rule = OF288InvoiceGeneratorRuleFactory2.getInstance(ruleEnum, context, null, null, null,null,null);
			
				if(null != rule){
					rule.addAdditionalMessages(dialogueVo);
				}
			}
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
}
