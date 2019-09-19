package gov.nwcg.isuite.core.rules.adminoffice.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class ValidateDataRules extends AbstractAdminOfficeSaveRule implements IRule{
	public static final String _RULE_NAME=AdminOfficeSaveRuleFactory.RuleEnum.VALIDATE_DATA.name();

	public ValidateDataRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
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
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) {
		
		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;

		// check for name
		error=Validator.validateStringField2("Office Name", vo.getOfficeName(), 55, true);
		if(null != error)errorObjects.add(error);
		
		
		if(null != vo.getAddressVo()){
			// check addr1
			error=Validator.validateStringField2("Address Line 1", vo.getAddressVo().getAddressLine1(), 35, true);
			if(null != error)errorObjects.add(error);
			
			// check addr2
			error=Validator.validateStringField2("Address Line 2", vo.getAddressVo().getAddressLine2(), 35, false);
			if(null != error)errorObjects.add(error);
			
			// check city
			error=Validator.validateStringField2("City", vo.getAddressVo().getCity(), 30, true);
			if(null != error)errorObjects.add(error);
			
			// check state
			error=Validator.validateVoField2("State", vo.getAddressVo().getCountrySubdivisionVo(), true);
			if(null != error)errorObjects.add(error);
			
			// check zip
			error=Validator.validateStringField2("Zip", vo.getAddressVo().getPostalCode(), 10, true);
			if(null != error)errorObjects.add(error);
			
		}else{
			error=new ErrorObject("info.generic","Address information is required.");
			if(null != error)errorObjects.add(error);
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
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
