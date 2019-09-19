package gov.nwcg.isuite.core.rules.dbmgmt.savedb;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import com.ibm.xml.crypto.util.Base64;

public class ValidateDataRules extends AbstractDbMgmtSaveRule implements IRule {
	public static final String _RULE_NAME = DbMgmtSaveRuleFactory.RuleEnum.VALIDATE_DATA.name();

	public ValidateDataRules(ApplicationContext ctx) {
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
		/*
		 * 3. The system must enforce the following rules when naming a new database:
				3.1. The name cannot include any spaces.
				3.2. The user cannot use any special characters except an underscore (_).
				3.3. The user can use any alphabetic and numeric characters (a-z and 0-9).
				3.4. The name cannot be longer than 64 characters.
				
			 DEV: Above validation handled on flex ui  ie. restrict="[a-z,A-Z,_]"
		 */
		
		/*
		 * When the user sets the system to automatically backup the database, 
		 * the system must automatically set the backup destination for the backup file. 
		 * The user cannot change this destination.
		 */
		
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		
		// check for name
		ErrorObject error=Validator.validateStringField2("Database Name", vo.getDatabaseName(), 64, true);
		if(null != error)errorObjects.add(error);

		// make sure name starts with alpha character
		String dbname=vo.getDatabaseName();
		if(StringUtility.hasValue(dbname)){
			boolean val=dbname.substring(0,1).matches("[A-Z,a-z]");
			if(val==false){
				ErrorObject error2 = new ErrorObject("error.800000","Database Name must begin with an Alpha Character.");
				errorObjects.add(error2);
			}
		}
		
		if(!LongUtility.hasValue(vo.getId())){
			// check password
			error=Validator.validateStringField2("Database Password", vo.getPassword(), 55, true);
			if(null != error)errorObjects.add(error);

			// check confirm password
			error=Validator.validateStringField2("Verify Password", vo.getConfirmPassword(), 55, true);
			if(null != error)errorObjects.add(error);

			// check passwords match
			if(StringUtility.hasValue(vo.getPassword()) && StringUtility.hasValue(vo.getConfirmPassword())){
				if(!vo.getPassword().equals(vo.getConfirmPassword())){
					ErrorObject error2 = new ErrorObject("error.800000","Password and Verify Password do not match.");
					errorObjects.add(error2);
				}
			}
		}else{
			// check if user is changing password
			if(BooleanUtility.isTrue(vo.getIsChangePassword())){
				// is the current password valid?
				error=Validator.validateStringField2("Current Password", vo.getCurrentPassword(), 55, true);
				if(null != error)errorObjects.add(error);
				
				if(StringUtility.hasValue(vo.getCurrentPassword())){
					FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
					byte[] bytes = enc.encrypt(vo.getCurrentPassword().getBytes());
					Base64 base64 = new Base64();
					String pwdhash=base64.encode(bytes);
					
					if(!pwdhash.equals(entity.getPasswordHash())){
						// current password does not match
						ErrorObject error2 = new ErrorObject("error.800000","Invalid Current Password.");
						errorObjects.add(error2);
					}
				}
				
				// check new password
				error=Validator.validateStringField2("New Password", vo.getChangePassword() , 55, true);
				if(null != error)errorObjects.add(error);
				
				// check verify password
				error=Validator.validateStringField2("Verify Password", vo.getConfirmChangePassword() , 55, true);
				if(null != error)errorObjects.add(error);

				if(StringUtility.hasValue(vo.getChangePassword()) && StringUtility.hasValue(vo.getConfirmChangePassword())){
					if(!vo.getChangePassword().equals(vo.getConfirmChangePassword())){
						// new pwd does not match
						ErrorObject error2 = new ErrorObject("error.800000","New Password and Verify Password do not match.");
						errorObjects.add(error2);
					}
				}
			}
		}

		// check backup and interval
		if(BooleanUtility.isTrue(vo.getIsAutoBackup())){
			if(!IntegerUtility.hasValue(vo.getBackupInterval())){
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
