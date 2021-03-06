package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.AssignmentTimeVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.WorkPeriodVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class PersonTypeChange1Rules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="PERSON_TYPE_CHANGE1";

	public PersonTypeChange1Rules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try {

			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;
			

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;

				
			}else{
				/*
				 * Run rule check
				 */
				if(runRuleCheck(dialogueVo)==_FAIL)
					return _FAIL;
				
				/*
				 * Rule check passed, mark as completed
				 */
				dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildNoActionCoaVo(_RULE_NAME,true));
				
			}
			
		} catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * B.R. 6.0002
		 * 
		 * If the user changes the Person designation (e.g., from Person to Non-Person) 
		 * for a Resource assigned to an Incident and that Resource has Time postings 
		 * for that Incident that were not included on an Original Invoice, 
		 * the system must ask the user if they want to remove the existing Time postings. 
		 * If the user indicates that they do, 
		 * the system must remove all Time postings for that Incident and change 
		 * the Person designation for that Resource. (Applies to Use Cases 6.0006 and 6.0008.)
		 */

		/*
		 * Check if user is saving an existing record
		 */
		if(LongUtility.hasValue(vo.getId())){
			
			if(isPersonTypeChanged() && isContractor()==false){
				
				if(this.hasNonInvoicedTimePostings(irEntity.getId())){
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
					coaVo.setPromptVo(new PromptVo("text.incidentResources"
										,"action.9909"
										,null
										,PromptVo._YES | PromptVo._NO));
					
					dialogueVo.setCourseOfActionVo(coaVo);
				
					return _FAIL;
				}
			}
		}
		
		return _OK;
	}
	
	private Boolean isContractor(){
		WorkPeriod wpEntity = irEntity.getWorkPeriod();
		WorkPeriodVo wpVo = vo.getWorkPeriodVo();
		
		if(null != wpEntity && null != wpVo){
			Assignment assignEntity = null;
			AssignmentVo assignVo = null;
			
			// get current assignments
			for(Assignment a : wpEntity.getAssignments()){
				if(null==a.getEndDate()){
					assignEntity=a;
					break;
				}
			}
			
			for(AssignmentVo avo : wpVo.getAssignmentVos()){
				if(null==avo.getEndDate()){
					assignVo=avo;
					break;
				}
			}
			
			if(null != assignEntity && null != assignVo){
				AssignmentTime atEntity = assignEntity.getAssignmentTime();
				AssignmentTimeVo atVo = assignVo.getAssignmentTimeVo();
				
				if(null != atEntity && null != atVo){
					if(null != atEntity.getEmploymentType() 
							&& atEntity.getEmploymentType()==EmploymentTypeEnum.CONTRACTOR)
						return true;
				}
			}			
		}
	
		return false;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {

		dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
		
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// continue;
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		}else{
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentResources", "text.abortProcess" , new String[]{"save"}, MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		return _OK;
	}
	
	/**
	 * Returns whether or not the resource's person type is getting changed from:
	 *    person to non-person
	 *        or
	 *    non-person to person
	 *    
	 * @return
	 */
	private Boolean isPersonTypeChanged(){
		
		if(null == irEntity)
			return false;
		
		/*
		 * Are the person types different?
		 */
		if(irEntity.getResource().isPerson() != vo.getResourceVo().getPerson()){
			
			return true;
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		/*
		 * Determine if we need to remove any time postings?
		 */
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		try{
			if(null != coa && coa.getCoaType() == CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
				IncidentResourceDao irdao = (IncidentResourceDao)context.getBean("incidentResourceDao");
				IncidentResource irent=irdao.getById(irEntity.getId(), IncidentResourceImpl.class);
				IncidentResourceVo irvo = IncidentResourceVo.getInstance(irent, true);
				irdao.flushAndEvict(irent);
	
				TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");
				TimeAssignAdjustDao taaDao = (TimeAssignAdjustDao)context.getBean("timeAssignAdjustDao");
				
				// remove all time_invoice records
				tpDao.deletePersonInvoiceRecords(irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getId());
				
				// remove employment designation, person info, if not contractor
				irdao.clearNonContractorEmploymentType(irEntity.getId());
				
				// delete the time postings
				tpDao.deleteResourceNonInvoicedTimePosts(irEntity.getId());

				// delete the adjustments
				taaDao.deleteResourceAdjustments(irEntity.getId());
				
				for(Assignment a : irent.getWorkPeriod().getAssignments()){
					irdao.flushAndEvict(a.getAssignmentTime());
					irdao.flushAndEvict(a);
				}
				irdao.flushAndEvict(irent.getWorkPeriod());
				irdao.flushAndEvict(irent);
			}
		}catch(Exception e){
			throw e;
		}
		
	}

}
