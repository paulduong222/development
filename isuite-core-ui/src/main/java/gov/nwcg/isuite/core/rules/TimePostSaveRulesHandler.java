package gov.nwcg.isuite.core.rules;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.rules.timepost.contractor.TimePostContractorRuleFactory;
import gov.nwcg.isuite.core.rules.timepost.personnel.TimePostPersonnelRuleFactory;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class TimePostSaveRulesHandler extends AbstractRuleHandler {

	public TimePostSaveRulesHandler(ApplicationContext ctx) {
		super.context=ctx;
	}

	/**
	 * @param employmentType
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public int execute(AssignmentTime assignmentTimeEntity, AssignmentTimePostVo vo, DialogueVo dialogueVo, String empType) throws Exception {
		
		try{
			Long id=0L;
			if(LongUtility.hasValue(assignmentTimeEntity.getAssignmentId())){
				id=assignmentTimeEntity.getAssignmentId();
			}
			
			for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
				IRule rule = SiteManagedRuleFactory.getInstance(re, context, id, "ASSIGNMENT");
				
				if(null != rule){
					if(_OK != rule.executeRules(dialogueVo)){
						return _FAIL;
					}
				}
			}

			TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");
			Long incidentResourceId = tpDao.getIncidentResourceId(vo.getAssignmentTimeId());
			
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResource irEntity = irDao.getById(incidentResourceId, IncidentResourceImpl.class);

			if(assignmentTimeEntity.getEmploymentType()==EmploymentTypeEnum.CONTRACTOR){
				/*
				for(TimePostContractorRuleFactory.RuleEnum ruleEnum : TimePostContractorRuleFactory.RuleEnum.values()){
					IRule rule = TimePostContractorRuleFactory.getInstance(ruleEnum
																			, context
																			, vo
																			, assignmentTimeEntity
																			, irEntity
																			, tpDao);
					
					if(null != rule){
						System.out.println(rule.getRuleName());
						if(_OK != rule.executeRules(dialogueVo)){
							return _FAIL;
						}
					}
				
				}
				*/
			}else {

				for(TimePostPersonnelRuleFactory.RuleEnum ruleEnum : TimePostPersonnelRuleFactory.RuleEnum.values()){
					IRule rule = TimePostPersonnelRuleFactory.getInstance(ruleEnum, context, vo, assignmentTimeEntity,tpDao,empType);
					if(null != rule){
						if(_OK != rule.executeRules(dialogueVo)){
							return _FAIL;
						}
						
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

	public int executeContractor(AssignmentTime assignmentTimeEntity, AssignmentTimePostVo primaryVo, AssignmentTimePostVo specialVo, DialogueVo dialogueVo, String empType,String postType) throws Exception {
		
		try{

			TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");
			Long incidentResourceId = tpDao.getIncidentResourceId(assignmentTimeEntity.getId());
			
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResource irEntity = irDao.getById(incidentResourceId, IncidentResourceImpl.class);

			for(TimePostContractorRuleFactory.RuleEnum ruleEnum : TimePostContractorRuleFactory.RuleEnum.values()){
				IRule rule = TimePostContractorRuleFactory.getInstance(ruleEnum
																		, context
																		, primaryVo
																		, specialVo
																		, assignmentTimeEntity
																		, irEntity
																		, tpDao
																		, postType);
					
				if(null != rule){
					System.out.println(rule.getRuleName());
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

	public int executeProcessedActionsContractor(AssignmentTime assignmentTimeEntity, AssignmentTimePostVo primaryVo, AssignmentTimePostVo specialVo, DialogueVo dialogueVo, String empType,String postType) throws Exception {
		
		try{

			TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");
			Long incidentResourceId = tpDao.getIncidentResourceId(assignmentTimeEntity.getId());
			
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResource irEntity = irDao.getById(incidentResourceId, IncidentResourceImpl.class);

			for(TimePostContractorRuleFactory.RuleEnum ruleEnum : TimePostContractorRuleFactory.RuleEnum.values()){
				IRule rule = TimePostContractorRuleFactory.getInstance(ruleEnum
																		, context
																		, primaryVo
																		, specialVo
																		, assignmentTimeEntity
																		, irEntity
																		, tpDao
																		, postType);
					
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

	/**
	 * @param vo
	 * @param dialogueVo
	 * @throws ServiceException
	 */
	public void executeProcessedActions(AssignmentTime assignmentTimeEntity,AssignmentTimePostVo vo, DialogueVo dialogueVo,String empType) throws ServiceException {
		try{
			
			TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");
			Long incidentResourceId = tpDao.getIncidentResourceId(vo.getAssignmentTimeId());
			
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResource irEntity = irDao.getById(incidentResourceId, IncidentResourceImpl.class);
			
			if(assignmentTimeEntity.getEmploymentType()==EmploymentTypeEnum.CONTRACTOR){

				/*
				for(TimePostContractorRuleFactory.RuleEnum ruleEnum : TimePostContractorRuleFactory.RuleEnum.values()){
					IRule rule = TimePostContractorRuleFactory.getInstance(ruleEnum
							, context
							, vo
							, assignmentTimeEntity
							, irEntity
							, tpDao);
					
					if(null != rule){
						rule.executePostProcessActions(dialogueVo);
					}
				}
				*/
			}else {
				
				for(TimePostPersonnelRuleFactory.RuleEnum ruleEnum : TimePostPersonnelRuleFactory.RuleEnum.values()){
					IRule rule = TimePostPersonnelRuleFactory.getInstance(ruleEnum, context, vo, assignmentTimeEntity,tpDao,empType);
					
					if(null != rule){
						rule.executePostProcessActions(dialogueVo);
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
		}
		
	}
	
}
