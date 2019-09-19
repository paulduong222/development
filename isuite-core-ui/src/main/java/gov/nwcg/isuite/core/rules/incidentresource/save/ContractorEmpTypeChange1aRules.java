package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.AssignmentTimeVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.WorkPeriodVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class ContractorEmpTypeChange1aRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="CONTRACTOR_EMP_TYPE_CHANGE1a";
	private Boolean clearContractorInfo=false;
	
	public ContractorEmpTypeChange1aRules(ApplicationContext ctx){
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
			if(this.clearContractorInfo==true){
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
				coaVo.setIsComplete(true);
				dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
			}else{
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
		 * Defect 2932 - Clear contractor/rate info
		 * May be a missed requirement. 
		 * Unchecking the checkbox, should remove the rate data and any postings 
		 * for the resource if it meets the criteria to be changed.
		 *           
		 */
		
		/*
		 * Check if user is saving an existing record
		 */
		if(LongUtility.hasValue(vo.getId())){
			
			if(isContractorNowNonContractor()){
				clearContractorInfo=true;
			}
		}
		
		return _OK;
	}
	
	/**
	 * Returns whether or not the resource's emp type is getting changed from:
	 *    contractor to non-contractor
	 *    
	 * @return
	 */
	private Boolean isContractorNowNonContractor(){
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
						if( (atEntity.getEmploymentType() == EmploymentTypeEnum.CONTRACTOR)){
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
		/*
		 * Determine if we need to remove any time postings?
		 */
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa && coa.getCoaType() == CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
			IncidentResourceDao irdao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResource irent=irdao.getById(irEntity.getId(), IncidentResourceImpl.class);
			IncidentResourceVo irvo = IncidentResourceVo.getInstance(irent, true);
			irdao.flushAndEvict(irent);
			
			// remove contractor/rate info
			if(null != irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()){
				Long assignmentTimeId=irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getId();
				irdao.clearContractorInfo(assignmentTimeId);
			}
		}
		
	}

}
