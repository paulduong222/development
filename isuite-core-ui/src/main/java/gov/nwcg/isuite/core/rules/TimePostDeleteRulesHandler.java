package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.timepost.delete.TimePostDeleteRuleFactory;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class TimePostDeleteRulesHandler extends AbstractRuleHandler {

	public TimePostDeleteRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(AssignmentTimePost entity, DialogueVo dialogueVo) throws Exception {
		
		try{
			Long id=0L;
			if(LongUtility.hasValue(entity.getAssignmentTimeId())){
				id=entity.getAssignmentTimeId();
			}else if(null != entity.getAssignmentTime() && LongUtility.hasValue(entity.getAssignmentTime().getId())){
				id=entity.getAssignmentTime().getId();
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, id, "ASSIGNMENTTIME");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}

			TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");

			for(TimePostDeleteRuleFactory.RuleEnum ruleEnum : TimePostDeleteRuleFactory.RuleEnum.values()){
				IRule rule = TimePostDeleteRuleFactory.getInstance(ruleEnum
																	, context
																	, entity
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
	public int executeProcessedActions(AssignmentTimePost entity, DialogueVo dialogueVo) throws Exception {

		try{
			
			TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");

			for(TimePostDeleteRuleFactory.RuleEnum ruleEnum : TimePostDeleteRuleFactory.RuleEnum.values()){
				IRule rule = TimePostDeleteRuleFactory.getInstance(ruleEnum
																	, context
																	, entity
																	, tpDao);
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
