package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.rules.incidentresource.save.IncidentResourceSaveRuleFactory;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
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

public class IncidentResourceSaveRulesHandler extends AbstractRuleHandler {
	
	public IncidentResourceSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

//	MJG, 03/23/2012: work areas removed
//	public int execute(IncidentResourceVo vo, Long workAreaId, IncidentResource irEntity, DialogueVo dialogueVo) throws Exception {
	public int execute(IncidentResourceVo vo, IncidentResource irEntity, DialogueVo dialogueVo) throws Exception {
		
		try{

			Long id=0L;
			String type="";
			if(null != vo && LongUtility.hasValue(vo.getId())){
				id=vo.getId();
				type="INCIDENTRESOURCE";
			}else if(null != vo && null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())){
				id=vo.getIncidentVo().getId();
				type="INCIDENT";
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, id, type);
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}
			
			
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			
			for(IncidentResourceSaveRuleFactory.RuleEnum ruleEnum : IncidentResourceSaveRuleFactory.RuleEnum.values()){
//				IRule rule = IncidentResourceSaveRuleFactory.getInstance(ruleEnum, context, vo, workAreaId, irDao, irEntity);
				IRule rule = IncidentResourceSaveRuleFactory.getInstance(ruleEnum, context, vo, irDao, irEntity);
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
					
					//System.out.println(rule.getRuleName());
					if(DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
						//System.out.println("");
					}
				}
				
			}
			
		}catch(Exception e){
			// handle exception
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentResources", "error.900000" , new String[]{e.getMessage()}, MessageTypeEnum.CRITICAL ));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	public void executeProcessedActions(IncidentResourceVo vo, ResourceData origResourceData, IncidentResource irEntity, DialogueVo dialogueVo) throws ServiceException {
		
		try{

			if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
				return;
			
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");

			/*
			 * Execute any needed follow up actions based on coa type and/or prompt values.
			 */
			for(IncidentResourceSaveRuleFactory.RuleEnum ruleEnum : IncidentResourceSaveRuleFactory.RuleEnum.values()){
//				IRule rule = IncidentResourceSaveRuleFactory.getInstance(ruleEnum, context, vo, null, irDao, irEntity);
				IRule rule = IncidentResourceSaveRuleFactory.getInstance(ruleEnum, context, vo, irDao, irEntity);
				
				if(null != rule){
					rule.setObject(origResourceData, IncidentResourceSaveRuleFactory.ObjectTypeEnum.RESOURCE_DATA.name());
					rule.executePostProcessActions(dialogueVo);
				}
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
}
