package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.timeassignadjust.deletebatch.TimeAssignAdjustDeleteBatchRuleFactory;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class TimeAssignAdjustDeleteBatchRulesHandler extends AbstractRuleHandler {

	public TimeAssignAdjustDeleteBatchRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param employmentType
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(TimeAssignAdjust entity, TimeAssignAdjustVo vo, Collection<Long> crewIds,DialogueVo dialogueVo) throws Exception {
		
		try{
			Long id=0L;
			if(LongUtility.hasValue(entity.getAssignmentId())){
				id=entity.getAssignmentId();
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

			for(TimeAssignAdjustDeleteBatchRuleFactory.RuleEnum ruleEnum : TimeAssignAdjustDeleteBatchRuleFactory.RuleEnum.values()){
				IRule rule = TimeAssignAdjustDeleteBatchRuleFactory.getInstance(ruleEnum
																		, context
																		, vo
																		, entity
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
	 * @param vo
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public int executeProcessedActions(TimeAssignAdjust entity, TimeAssignAdjustVo vo, Collection<Long> crewIds, DialogueVo dialogueVo) throws Exception {

		try{
			
			TimeAssignAdjustDao taaDao = (TimeAssignAdjustDao)context.getBean("timeAssignAdjustDao");

			for(TimeAssignAdjustDeleteBatchRuleFactory.RuleEnum ruleEnum : TimeAssignAdjustDeleteBatchRuleFactory.RuleEnum.values()){
				IRule rule = TimeAssignAdjustDeleteBatchRuleFactory.getInstance(ruleEnum
																		, context
																		, vo
																		, entity
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
