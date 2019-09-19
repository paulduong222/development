package gov.nwcg.isuite.core.rules.iap.save203position;

import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.persistence.IapPersonnelDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class ValidateForm203PositionDataRules extends AbstractSaveForm203PositionRule implements IRule {
	public static final String _RULE_NAME=SaveForm203PositionRuleFactory.RuleEnum.VALIDATE_DATA.name();
	
	public ValidateForm203PositionDataRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, ruleName))
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
				.add(super.buildNoActionCoaVo(ruleName,true));
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;

		if(BooleanUtility.isFalse(super.iapPersonnelVo.getIsBlankLine())){
			// check for role
			if(!super.iapPersonnelVo.getSection().equals("AGENCY_SECTION")
					&& BooleanUtility.isFalse(super.iapPersonnelVo.getIsBranchName())  ){
				error=Validator.validateStringField2("Position", super.iapPersonnelVo.getRole(), 50, true);
				if(null != error)errorObjects.add(error);
			}
			if(super.iapPersonnelVo.getSection().equals("AGENCY_SECTION")
					&& BooleanUtility.isFalse(super.iapPersonnelVo.getIsBranchName())  ){
				error=Validator.validateStringField2("Agency", super.iapPersonnelVo.getRole(), 50, true);
				if(null != error)errorObjects.add(error);
			}

			// check for name
			/*
			if( ( !StringUtility.hasValue(super.iapPersonnelVo.getName()) 
					&& (null == super.iapPersonnelVo.getIncidentResourceVo() 
							|| !LongUtility.hasValue(super.iapPersonnelVo.getIncidentResourceVo().getId())))){
				ErrorObject error2 = new ErrorObject("error.800000","Resource Name is a required field.");
				errorObjects.add(error2);
			}
			*/
			
			// check for branch
			if(super.iapPersonnelVo.getSection().equals("BRANCH_SECTION") 
					&& BooleanUtility.isFalse(super.iapPersonnelVo.getIsBlankLine())
					&& BooleanUtility.isTrue(super.iapPersonnelVo.getIsBranchName())
					&& BooleanUtility.isFalse(super.iapPersonnelVo.getIsBlankBranch())){
				error=Validator.validateStringField2("Branch", super.iapPersonnelVo.getName(), 50, true);
				if(null != error)errorObjects.add(error);
				
				if(StringUtility.hasValue(super.iapPersonnelVo.getName())){
					// verify name is unique to form
					IapPersonnelDao ipDao = (IapPersonnelDao)context.getBean("iapPersonnelDao");
					IapPersonnel pentity=ipDao.getBranchByName(super.iapPersonnelVo.getIapForm203Id(), super.iapPersonnelVo.getName().toUpperCase(), super.iapPersonnelVo.getId());
					if(pentity != null && BooleanUtility.isTrue(pentity.getIsBranchName().getValue())){
						ErrorObject error2 = new ErrorObject("error.800000","Branch Name must be unique to the form.");
						errorObjects.add(error2);
					}
				}
			}
			
			// check for agency
			if(super.iapPersonnelVo.getSection().equals("AGENCY_SECTION")){
				//error=Validator.validateStringField2("Agency", super.iapPersonnelVo.getAgencyName(), 50, true);
				//if(null != error)errorObjects.add(error);
			}
		}
		
		if(CollectionUtility.hasValue(errorObjects)){
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("ValidationError");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
			coaVo.setIsDialogueEnding(true);

			coaVo.setErrorObjectVos(errorObjects);
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
		
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}


