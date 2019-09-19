package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;

public class CommonDataResourceSaveRule extends
		AbstractIncidentResourceSaveRule implements IRule {

	public static final String _RULE_NAME="COMMON_DATA_VALIDATION";
	
	public CommonDataResourceSaveRule(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
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
			
		} catch(Exception e) {
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

		try {
			
			ArrayList<ErrorObject> errors = new ArrayList<ErrorObject>();
			
			
			// check incident is assigned
			if(!AbstractVo.hasValue(vo.getIncidentVo())){
				errors.add(new ErrorObject("info.generic",new String[]{"You must select an Incident for the Resource"}));
			}

			// check accounting code if from time area
			/*
			if(vo.getSourcePage().equals("time")){
				if(!AbstractVo.hasValue(vo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo())){
					errors.add(new ErrorObject("info.generic",new String[]{"You must select an Accounting Code."}));
				}
			}*/
			
			//required fields
			if(super.vo.getResourceVo().isPerson()) {// the resource is a person
				
				//first name
				if(!StringUtility.hasValue(vo.getResourceVo().getFirstName())) {
					errors.add(new ErrorObject("text.requiredFirstName"));
				}
				
				//last name
				if(!StringUtility.hasValue(vo.getResourceVo().getLastName())) {
					errors.add(new ErrorObject("text.requiredLastName"));					
				}
				
			} else { //the resource is equipment
				
				//resource name
				if(!StringUtility.hasValue(vo.getResourceVo().getResourceName())) {
					errors.add(new ErrorObject("text.requiredResourceName"));					
				}
			}
			
			//if the resource is a leader, it must have a leader type
			if(vo.getResourceVo().isLeader()
					&& vo.getResourceVo().getLeaderType() != 1
					&& vo.getResourceVo().getLeaderType() != 2) {
				
				errors.add(new ErrorObject("text.requiredLeaderType"));
			}
			
			//status
			if(null == vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo()
					|| !StringUtility.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode())) {
				errors.add(new ErrorObject("text.requiredFieldAssignmentStatus"));
			}
			
			//unit code
			if(!AbstractVo.hasValue(vo.getResourceVo().getOrganizationVo())){
				errors.add(new ErrorObject("text.requiredUnitCode"));
			}
			
			//item code
			if(!AbstractVo.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo())){
			//if(null == vo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo()
			//		|| null == vo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getCode()) {
				errors.add(new ErrorObject("text.requiredItemCode"));
			}
			
			//primary dispatch center
			//3257 if(null == vo.getResourceVo().getPrimaryDispatchCenterVo()) {
			//3257	errors.add(new ErrorObject("text.requiredPrimaryDispatchCenter"));
			//3257 }
			if(errors.size() > 0) {
				
				CourseOfActionVo coa = buildValidationErrorCoaVo(errors);
				dialogueVo.setCourseOfActionVo(coa);
				return _FAIL;
			} else {
				return _OK;
			}
			
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
