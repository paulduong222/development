package gov.nwcg.isuite.core.rules;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.rules.incidentgroup.saveqskinds.IncidentGroupSaveQSKindsRuleFactory;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class IncidentGroupSaveQSKindsRulesHandler extends AbstractRuleHandler {
	
	public IncidentGroupSaveQSKindsRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(IncidentGroup entity, Collection<KindVo> vos, DialogueVo dialogueVo)throws Exception {
		
		try{
			IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			
			for(IncidentGroupSaveQSKindsRuleFactory.RuleEnum ruleEnum : IncidentGroupSaveQSKindsRuleFactory.RuleEnum.values()){
				IRule rule = IncidentGroupSaveQSKindsRuleFactory.getInstance(ruleEnum, context, vos, entity, incidentGroupDao);
				
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
			coaVo.setMessageVo(new MessageVo("text.incident", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(DialogueVo dialogueVo) throws Exception {
		try{

		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
