package gov.nwcg.isuite.core.rules.dbmgmt.copydb;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.domain.impl.DbAvailImpl;
import gov.nwcg.isuite.core.persistence.DbAvailDao;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import com.ibm.xml.crypto.util.Base64;

public class ValidateSourceDataRules extends AbstractDbMgmtCopyRule implements IRule {
	public static final String _RULE_NAME = DbMgmtCopyRuleFactory.RuleEnum.VALIDATE_SOURCE_DATA.name();

	public ValidateSourceDataRules(ApplicationContext ctx) {
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
		
		if(null == super.sourceVo || (null != super.sourceVo && !LongUtility.hasValue(super.sourceVo.getId()) )){
			dialogueVo.setCourseOfActionVo(
					super.buildErrorCoaVo("text.time"
										  ,"validationerror"
										  ,"error.800000"
										  , new String[]{"Source database is required."}));	
			return _FAIL;
		}

		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		
		// check source db password
		if(!StringUtility.hasValue(sourceVo.getPassword())){
			ErrorObject error2 = new ErrorObject("error.800000","Source Database Password is a required field.");
			errorObjects.add(error2);
		}
		
		try{
			if(StringUtility.hasValue(sourceVo.getPassword())){
				
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
				DbAvailDao dbAvailDao = (DbAvailDao)context.getBean("dbAvailDao");
				DbAvail entity = dbAvailDao.getById(sourceVo.getId(), DbAvailImpl.class);
				dbAvailDao.flushAndEvict(entity);
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
				if(StringUtility.hasValue(entity.getPasswordHash())){
					FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
					Base64 base64 = new Base64();
					byte[] bytes=base64.decode(entity.getPasswordHash());
					String dbpwd=new String(enc.decrypt(bytes));
					if(!dbpwd.equalsIgnoreCase(sourceVo.getPassword())){
						ErrorObject error2 = new ErrorObject("error.800000","Invalid Password for the source database.");
						errorObjects.add(error2);
					}
				}else{
					// error?
				}
			}
		}catch(Exception ee){
			ErrorObject error2 = new ErrorObject("error.800000",ee.getMessage());
			errorObjects.add(error2);
		}finally{
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
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
