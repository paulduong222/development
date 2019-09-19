package gov.nwcg.isuite.framework.cost;

import gov.nwcg.isuite.core.persistence.CostAccrualExtractDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.IsuiteException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class BaseCostAccrualGenerator {
	protected ApplicationContext context=null;
	protected CostAccrualExtractDao costAccrualExtractDao;

	protected Collection<Long> getTopLevelResourceIds(Long incidentId) throws Exception{
		/*
		 * Get Top Level Resources
		 */
		IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
		
		return irDao.getTopLevelResourceIds(incidentId);
	}
	
	protected void dialogueException(DialogueVo dialogueVo, Exception e){
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("ServiceException");
		coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
		coaVo.setIsDialogueEnding(true);
		
		if(e instanceof IsuiteException){
			IsuiteException ie = (IsuiteException)e;
			coaVo.setErrorObjectVos(ie.getErrorObjects());
		}else{
			ErrorObject errorObject = new ErrorObject(ErrorEnum._90000_ERROR,new String[]{e.getMessage()});
			coaVo.getErrorObjectVos().add(errorObject);
		}
		dialogueVo.setCourseOfActionVo(coaVo);
	}

}
