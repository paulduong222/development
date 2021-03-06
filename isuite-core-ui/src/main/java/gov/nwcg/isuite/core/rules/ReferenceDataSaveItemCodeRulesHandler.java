package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.referencedata.itemcodes.save.ReferenceDataSaveItemCodeRuleFactory;
import gov.nwcg.isuite.core.vo.KindVo;
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

public class ReferenceDataSaveItemCodeRulesHandler extends AbstractRuleHandler {
	
	public ReferenceDataSaveItemCodeRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	public int execute(KindVo vo, Kind entity, DialogueVo dialogueVo) throws Exception {
		
		try{
			Long id=0L;
			String type="NONE";
			if(null != vo && null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())){
				id=vo.getIncidentVo().getId();
				type="INCIDENT";
			} else if(null != vo && null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId())){
				id=vo.getIncidentGroupVo().getId();
				type="INCIDENTGROUP";
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, id, type);
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
			KindDao kindDao = (KindDao)context.getBean("kindDao");
			
			for(ReferenceDataSaveItemCodeRuleFactory.RuleEnum ruleEnum : ReferenceDataSaveItemCodeRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataSaveItemCodeRuleFactory.getInstance(ruleEnum, context, vo, entity, kindDao);
				
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

	public void executeProcessedActions(KindVo vo, Kind entity, DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;

			KindDao kindDao = (KindDao)context.getBean("kindDao");
			
			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(ReferenceDataSaveItemCodeRuleFactory.RuleEnum ruleEnum : ReferenceDataSaveItemCodeRuleFactory.RuleEnum.values()){
				IRule rule = ReferenceDataSaveItemCodeRuleFactory.getInstance(ruleEnum, context, vo, entity, kindDao);
				
				if(null != rule){
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	
}
