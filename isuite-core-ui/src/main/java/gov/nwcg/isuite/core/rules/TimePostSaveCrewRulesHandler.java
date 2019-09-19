package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.timepost.crews.TimePostCrewRuleFactory;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class TimePostSaveCrewRulesHandler extends AbstractRuleHandler {

	public TimePostSaveCrewRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param employmentType
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(Collection<AssignmentTime> entities, AssignmentTimePostVo vo, DialogueVo dialogueVo) throws Exception {
		
		try{
			Long atId=vo.getAssignmentTimeId();
			dialogueVo.setResultObjectAlternate4(atId);
			
			/*
			if(CollectionUtility.hasValue(entities)){
				AssignmentTime at = entities.iterator().next();
				atId=at.getId();
			}
		*/
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, atId, "ASSIGNMENTTIME");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
			TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");
			
			for(TimePostCrewRuleFactory.RuleEnum ruleEnum : TimePostCrewRuleFactory.RuleEnum.values()){
				IRule rule = TimePostCrewRuleFactory.getInstance(ruleEnum
																	, context
																	, vo
																	, entities
																	, tpDao);
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
			coaVo.setMessageVo(new MessageVo("text.time", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

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
	public void executeProcessedActions(Collection<AssignmentTime> entities,AssignmentTimePostVo vo, DialogueVo dialogueVo) throws ServiceException {

		try{
			
			TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");
			
			for(TimePostCrewRuleFactory.RuleEnum ruleEnum : TimePostCrewRuleFactory.RuleEnum.values()){
				IRule rule = TimePostCrewRuleFactory.getInstance(ruleEnum
																	, context
																	, vo
																	, entities
																	, tpDao);
					
					if(null != rule){
						rule.executePostProcessActions(dialogueVo);
					}
			}
				
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
	}
	
}
