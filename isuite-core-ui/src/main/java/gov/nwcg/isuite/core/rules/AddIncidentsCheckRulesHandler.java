package gov.nwcg.isuite.core.rules;

import java.util.Collection;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.rules.incidentgroup.addincidentscheck.AddIncidentsCheckRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class AddIncidentsCheckRulesHandler extends AbstractRuleHandler {
	
	public AddIncidentsCheckRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(IncidentGroupVo vo, Collection<IncidentGridVo> incGridVos, DialogueVo dialogueVo) throws Exception {
		
		try{

			IncidentGroupDao dao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			for(AddIncidentsCheckRuleFactory.RuleEnum ruleEnum : AddIncidentsCheckRuleFactory.RuleEnum.values()){
				IRule rule = AddIncidentsCheckRuleFactory.getInstance(ruleEnum, context, vo, incGridVos,dao);
				
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

	/**
	 * @param vo
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(IncidentGroupVo vo, Collection<IncidentGridVo> incGridVos, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			IncidentGroupDao dao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			for(AddIncidentsCheckRuleFactory.RuleEnum ruleEnum : AddIncidentsCheckRuleFactory.RuleEnum.values()){
				IRule rule = AddIncidentsCheckRuleFactory.getInstance(ruleEnum, context, vo, incGridVos,dao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
