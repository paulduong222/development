package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.vo.AssignmentTimeVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.WorkPeriodVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class ContractorEmpTypeChange3Rules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="CONTRACTOR_EMP_TYPE_CHANGE3";

	public ContractorEmpTypeChange3Rules(ApplicationContext ctx){
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
		 * If the user attempts to change the Contractor/Cooperator designation 
		 * for a Resource assigned to an Incident and that Resource 
		 * has Original Invoices that were exported for that Incident, 
	 	 * the system must not change the Contractor/Cooperator designation. 
	 	 * (Applies to Use Cases 6.0006 and 6.0008.)
		 *           
		 */
		
		/*
		 * Check if user is saving an existing record
		 */
		if(LongUtility.hasValue(vo.getId())){
			
			if(isContractorEmpTypeChanged()){
				
				if(this.hasExportedInvoices(irEntity.getId())){
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setIsDialogueEnding(Boolean.TRUE);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.incidentResources", "action.9910a" , null, MessageTypeEnum.INFO));

					dialogueVo.setCourseOfActionVo(coaVo);
					
					return _FAIL;
				}
			}
		}
		
		return _OK;
	}
	
	/**
	 * Returns whether or not the resource's emp type is getting changed from:
	 *    contractor to non-contractor
	 *        or
	 *    non-contractor to contractor
	 * @return
	 */
	private Boolean isContractorEmpTypeChanged(){
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
					/*
					 * Are they different?
					 */
					if(atEntity.getEmploymentType() != atVo.getEmploymentType()){
					
						/*
						 * Is one of them a contractor
						 */
						if( (atEntity.getEmploymentType() == EmploymentTypeEnum.CONTRACTOR)
								||
							(atVo.getEmploymentType() == EmploymentTypeEnum.CONTRACTOR)){
							return true;
						}
					}
					
				}
			}	
		}
	
		return false;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
