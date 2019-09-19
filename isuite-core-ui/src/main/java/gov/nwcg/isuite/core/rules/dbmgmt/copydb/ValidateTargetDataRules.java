package gov.nwcg.isuite.core.rules.dbmgmt.copydb;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class ValidateTargetDataRules extends AbstractDbMgmtCopyRule implements IRule {
	public static final String _RULE_NAME = DbMgmtCopyRuleFactory.RuleEnum.VALIDATE_TARGET_DATA.name();

	public ValidateTargetDataRules(ApplicationContext ctx) {
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		
		// check for name
		ErrorObject error=Validator.validateStringField2("New Database Name", targetVo.getDatabaseName(), 64, true);
		if(null != error)errorObjects.add(error);

		// make sure name starts with alpha character
		String dbname=targetVo.getDatabaseName();
		if(StringUtility.hasValue(dbname) && dbname.length() > 0){
			boolean val=dbname.substring(0,1).matches("[A-Z,a-z]");
			if(val==false){
				ErrorObject error2 = new ErrorObject("error.800000","New Database Name must begin with an Alpha Character.");
				errorObjects.add(error2);
			}
		}
		
		// check password
		error=Validator.validateStringField2("New Database Password", targetVo.getPassword(), 55, true);
		if(null != error)errorObjects.add(error);

		// check confirm password
		error=Validator.validateStringField2("New Database Verify Password", targetVo.getConfirmPassword(), 55, true);
		if(null != error)errorObjects.add(error);

		// check passwords match
		if(StringUtility.hasValue(targetVo.getPassword()) && StringUtility.hasValue(targetVo.getConfirmPassword())){
			if(!targetVo.getPassword().equals(targetVo.getConfirmPassword())){
				ErrorObject error2 = new ErrorObject("error.800000","New Database Password and Verify Password do not match.");
				errorObjects.add(error2);
			}
		}

		// check backup and interval
		if(BooleanUtility.isTrue(targetVo.getIsAutoBackup())){
			if(!IntegerUtility.hasValue(targetVo.getBackupInterval())){
				ErrorObject error2 = new ErrorObject("error.800000","Backup Interval is required when Auto Backup option is selected.");
				errorObjects.add(error2);
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

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
