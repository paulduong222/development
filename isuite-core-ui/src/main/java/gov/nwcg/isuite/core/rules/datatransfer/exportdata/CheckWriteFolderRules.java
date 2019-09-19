package gov.nwcg.isuite.core.rules.datatransfer.exportdata;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckWriteFolderRules extends AbstractExportDataRule implements IRule {
	public static final String _RULE_NAME = ExportDataRuleFactory.RuleEnum.CHECK_WRITE_FOLDER.name();

	public CheckWriteFolderRules(ApplicationContext ctx) {
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
		
		String outputFolder=this.getOutputFolder();
		String milli=String.valueOf(Calendar.getInstance().getTimeInMillis());
		
		/*
		 * verify we have permission to create/delete folders
		 * in the reportsOutput folder
		 */
		try{
			FileUtil.makeDir(outputFolder+milli);
		}catch(Exception e){
			ErrorObject error2 = new ErrorObject("error.800000"
					,"The system cannot create dir files, check permissions on server. " + "["+outputFolder+milli+"]");
			errorObjects.add(error2);
		}
		
		try{
			FileUtil.deleteFile(outputFolder+milli);
		}catch(Exception e){
			ErrorObject error2 = new ErrorObject("error.800000"
					,"The system cannot delete dir files, check permissions on server. " + "["+outputFolder+milli+"]");
			errorObjects.add(error2);
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

	protected String getOutputFolder() throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		try{
			//SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.REPORT_OUTPUT_FOLDER.name());
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.DATA_TRANSFER_FOLDER.name());
			spDao.flushAndEvict(spEntity);
			
			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				return spEntity.getParameterValue();
			}else{
				return "";
			}

		}catch(Exception e){
			throw e;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
