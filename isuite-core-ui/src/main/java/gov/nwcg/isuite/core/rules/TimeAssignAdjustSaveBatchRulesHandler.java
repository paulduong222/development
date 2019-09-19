package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.timeassignadjust.savebatch.TimeAssignAdjustSaveBatchRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class TimeAssignAdjustSaveBatchRulesHandler extends AbstractRuleHandler {

	public TimeAssignAdjustSaveBatchRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param entity
	 * @param assignmentEntity
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(TimeAssignAdjust entity,IncidentResourceVo irvo, Assignment assignmentEntity,TimeAssignAdjustVo vo, Collection<Long> crewIds, DialogueVo dialogueVo) throws Exception {
		
		try{
			Long id=0L;
			if(null != assignmentEntity && LongUtility.hasValue(assignmentEntity.getId())){
				id=assignmentEntity.getId();
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, id, "ASSIGNMENT");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}

			TimeAssignAdjustDao taaDao = (TimeAssignAdjustDao)context.getBean("timeAssignAdjustDao");

			for(TimeAssignAdjustSaveBatchRuleFactory.RuleEnum ruleEnum : TimeAssignAdjustSaveBatchRuleFactory.RuleEnum.values()){
				IRule rule = TimeAssignAdjustSaveBatchRuleFactory.getInstance(ruleEnum
																		, context
																		,irvo
																		, vo
																		, entity
																		, assignmentEntity
																		, crewIds
																		, taaDao);
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
	 * @param entity
	 * @param assignmentEntity
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int executeProcessedActions(TimeAssignAdjust entity, IncidentResourceVo irvo, Assignment assignmentEntity,TimeAssignAdjustVo vo, Collection<Long> crewIds, DialogueVo dialogueVo) throws Exception {

		try{
			
			TimeAssignAdjustDao taaDao = (TimeAssignAdjustDao)context.getBean("timeAssignAdjustDao");

			for(TimeAssignAdjustSaveBatchRuleFactory.RuleEnum ruleEnum : TimeAssignAdjustSaveBatchRuleFactory.RuleEnum.values()){
				IRule rule = TimeAssignAdjustSaveBatchRuleFactory.getInstance(ruleEnum
																		, context
																		,irvo
																		, vo
																		, entity
																		, assignmentEntity
																		, crewIds
																		, taaDao);
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
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
	
}
