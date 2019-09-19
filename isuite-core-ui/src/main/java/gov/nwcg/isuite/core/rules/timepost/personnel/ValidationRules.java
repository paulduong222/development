package gov.nwcg.isuite.core.rules.timepost.personnel;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;

import org.springframework.context.ApplicationContext;

public class ValidationRules extends AbstractPersonnelRule implements IRule {
	public static final String _RULE_NAME="TIME_VALIDATION";
	
	public ValidationRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{

			if(_OK != validateEmploymentType(dialogueVo)){
				return _FAIL;
			}

			if(_OK != validateNotNonContractorNonPerson(dialogueVo)){
				return _FAIL;
			}
			
		}catch(Exception e){
			throw e;
		}

		return _OK;
	}

	/**
	 * When posting Time for an Incident, the system must prevent the user 
	 * from posting Time or Adjustments to a Resource that does not have an active status. 
	 * (Applies to Use Cases 6.0011, 6.0012, 6.0013 and 6.0026.)
	 * 
	 *    Resource status must be 'C' or 'P'.
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int validateActiveStatus(DialogueVo dialogueVo) throws Exception {
		// method moved to its own rule class.
		
		Boolean passed=false;
		if(null != super.assignmentTimeEntity.getAssignment()){
			if(null != super.assignmentTimeEntity.getAssignment().getAssignmentStatus()){
				String status = super.assignmentTimeEntity.getAssignment().getAssignmentStatus().name();
				
				if(status.equals("C") || status.equals("P")){
					passed=true;
				}
			}
		}
		
		//if(!incidentResourceEntity.getResource().isEnabled()){
		//	passed=false;
		//}
		
		if(!passed){
			dialogueVo.setCourseOfActionVo(
					super.buildErrorCoaVo("text.time"
										  ,"validationerror"
										  ,"error.900016"
										  ,null));	
			return _FAIL;
		}
		
		return _OK;
	}
	
	/**
	 * The system must prevent the user from adding Time data to a 
	 * Non-Contracted, Non-Person Resource (e.g., equipment).	  
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int validateNotNonContractorNonPerson(DialogueVo dialogueVo) throws Exception {
		if(null != assignmentTimeEntity.getEmploymentType()
			&& assignmentTimeEntity.getEmploymentType() != EmploymentTypeEnum.CONTRACTOR){
			
			if(!incidentResourceEntity.getResource().isPerson()){
				dialogueVo.setCourseOfActionVo(
						super.buildErrorCoaVo("text.time"
											  ,"validationerror"
											  ,"error.900015"
											  ,null));	
				return _FAIL;
			}
		}
		
		return _OK;
	}

	/**
	 *   If the user attempts to post time for a person Resource that does not have 
	 *   an Employment Type defined, the system must not post time and must display 
	 *   a message. 
	 *   [Message 0171]
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int validateEmploymentType(DialogueVo dialogueVo) throws Exception {
		
		if(null == assignmentTimeEntity.getEmploymentType()){
			dialogueVo.setCourseOfActionVo(
					super.buildErrorCoaVo("text.time"
										  ,"validationerror"
										  ,"error.0171"
										  ,null));	
			return _FAIL;
		}
		
		return _OK;
	}
	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
