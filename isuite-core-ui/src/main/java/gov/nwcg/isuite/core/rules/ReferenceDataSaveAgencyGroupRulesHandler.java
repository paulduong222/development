package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.persistence.AgencyGroupDao;
import gov.nwcg.isuite.core.rules.referencedata.agencygroups.save.ReferenceDataSaveAgencyGroupRuleFactory;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class ReferenceDataSaveAgencyGroupRulesHandler extends AbstractRuleHandler {
	
	public ReferenceDataSaveAgencyGroupRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(AgencyGroupVo agencyGroupVo, AgencyGroup agencyGroupEntity,DialogueVo dialogueVo) throws Exception {
		
		try{
			
			AgencyGroupDao agencyGroupDao = (AgencyGroupDao)context.getBean("agencyGroupDao");
			
			for(ReferenceDataSaveAgencyGroupRuleFactory.RuleEnum ruleEnum : ReferenceDataSaveAgencyGroupRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataSaveAgencyGroupRuleFactory.getInstance(
						ruleEnum, context, agencyGroupVo, agencyGroupEntity, agencyGroupDao);
				
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
			coaVo.setMessageVo(new MessageVo("text.agencyGroup", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(
			 AgencyGroupVo agencyGroupVo, AgencyGroup agencyGroupEntity,DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			AgencyGroupDao agencyGroupDao = (AgencyGroupDao)context.getBean("agencyGroupDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ReferenceDataSaveAgencyGroupRuleFactory.RuleEnum ruleEnum : ReferenceDataSaveAgencyGroupRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataSaveAgencyGroupRuleFactory.getInstance(
						ruleEnum, context, agencyGroupVo, agencyGroupEntity, agencyGroupDao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
