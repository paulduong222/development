package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.dailycost.delete.DeleteDailyCostRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
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

public class DailyCostDeleteRulesHandler extends AbstractRuleHandler {
	
	public DailyCostDeleteRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(IncidentResourceDailyCostVo vo, IncidentResourceDailyCostDao dao, DialogueVo dialogueVo) throws Exception {
		
		try{
			Long irid=0L;
			if(null != vo.getIncidentResourceVo() && LongUtility.hasValue(vo.getIncidentResourceVo().getId())){
				irid=vo.getIncidentResourceVo().getId();
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, irid, "INCIDENTRESOURCE");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}

			for(DeleteDailyCostRuleFactory.RuleEnum ruleEnum : DeleteDailyCostRuleFactory.RuleEnum.values()){
				IRule rule = DeleteDailyCostRuleFactory.getInstance(ruleEnum, context, vo, dao);
				
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
			coaVo.setMessageVo(new MessageVo("text.dailyCost", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

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
	public void executeProcessedActions(IncidentResourceDailyCostVo vo, IncidentResourceDailyCostDao dao, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			for(DeleteDailyCostRuleFactory.RuleEnum ruleEnum : DeleteDailyCostRuleFactory.RuleEnum.values()){
				IRule rule = DeleteDailyCostRuleFactory.getInstance(ruleEnum, context, vo, dao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
}
