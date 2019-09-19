package gov.nwcg.isuite.core.rules.datatransferv3.importdata;

import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckRevisionLevelRules extends AbstractImportDataRule implements IRule {
	public static final String _RULE_NAME = ImportDataRuleFactory.RuleEnum.CHECK_REVISION_LEVEL.name();

	public CheckRevisionLevelRules(ApplicationContext ctx) {
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
		 * When importing, we want to check if the transition file revision level
		 * matches the revision level in the db.
		 */
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		String level="";
		DataTransferDao dao = (DataTransferDao)context.getBean("dataTransferDao");
		
		String sql="SELECT revisionlevel from revision";
		ArrayList rslt=(ArrayList)dao.executeQuery(sql);
		if(null != rslt){
			level=TypeConverter.convertToString(rslt.get(0));
		}

		if(StringUtility.hasValue(dataTransferXml.getSourceRevisionLevel())){
			int dbLevel=Integer.parseInt(level);
			int xmlLevel=Integer.parseInt(dataTransferXml.getSourceRevisionLevel());
			
			//if(!level.equals(dataTransferXml.getSourceRevisionLevel())){
			if(xmlLevel > dbLevel){
				//ErrorObject error2 = 
				//	new ErrorObject("error.800000"
				//						,"The Transition File Revision Level does not match the Revision Level in the database.");
				ErrorObject error2 = 
					new ErrorObject("error.800000"
										,"The Transition File Revision Level is not compatible with the database.");
				errorObjects.add(error2);
				
			}
		}else{
			ErrorObject error2 = 
				new ErrorObject("error.800000"
									,"The Transition File does not contain a Source Revision Level.");
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

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
