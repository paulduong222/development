package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.filter.impl.TimePostQueryFilterImpl;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.rules.timepost.crews.DateOverlapRules;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.util.DateTimeValidator;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class AbstractCrewRule extends AbstractRule {
	protected AssignmentTimePostVo vo=null;
	protected Collection<AssignmentTime> entities=null;
	protected TimePostDao tpDao = null;
	protected TimePostQueryFilterImpl tpqFilter=null;
	protected String employmentType="";

	protected Collection<CourseOfActionVo> ruleCoaVos = new ArrayList<CourseOfActionVo>();
	
	public AbstractCrewRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(TimePostCrewRuleFactory.ObjectTypeEnum.ASSIGNMENT_TIME_POST_VO.name()))
			vo = (AssignmentTimePostVo)object;

		if(objectName.equals(TimePostCrewRuleFactory.ObjectTypeEnum.ASSIGNMENT_TIME_ENTITIES.name()))
			entities = (Collection<AssignmentTime>)object;
		
		if(objectName.equals(TimePostCrewRuleFactory.ObjectTypeEnum.TIME_POST_DAO.name()))
			tpDao = (TimePostDao)object;
	
		if(objectName.equals(TimePostCrewRuleFactory.ObjectTypeEnum.EMPLOYMENT_TYPE.name()))
			this.employmentType = (String)object;
	}

	protected Collection<ErrorObject> validateCommonData() throws Exception {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		
		ErrorObject error=null;

		// check accounting code
		error=Validator.validateVoField2("Accounting Code",vo.getIncidentAccountCodeVo(), true);
		if(null != error)errorObjects.add(error);
		
		// check post date
		error=Validator.validateDateField2("Post Date",vo.getPostStartDate(), true);
		if(null != error)errorObjects.add(error);

		// check item code
		error=Validator.validateVoField2("Item Code",vo.getKindVo(), true);
		if(null != error)errorObjects.add(error);
		

		if(AbstractVo.hasValue(vo.getSpecialPayVo())){
			
			
		}else{
			// start time
			if(StringUtility.hasValue(vo.getPostStartTime())){
				error = DateTimeValidator.validateTimeField("Start Time", vo.getPostStartTime());
				if(error!=null)errorObjects.add(error);
			}else{
				error=Validator.validateStringField2("Start Time", vo.getPostStartTime(), 4, true);
				if(error!=null)errorObjects.add(error);
			}
			
			// stop time
			if(StringUtility.hasValue(vo.getPostStopTime())){
				error = DateTimeValidator.validateTimeField("Stop Time", vo.getPostStopTime());
				if(error!=null)errorObjects.add(error);
			}else{
				error=Validator.validateStringField2("Stop Time", vo.getPostStopTime(), 4, true);
				if(error!=null)errorObjects.add(error);
			}
		}
		
		return errorObjects;
		
	}
	
	protected Boolean hasOverlap(DialogueVo dialogueVo) {
		for(CourseOfActionVo coaVo : dialogueVo.getProcessedCourseOfActionVos()){
			if(coaVo.getCoaName().equals(DateOverlapRules._RULE_NAME)){
				if(null != coaVo.getPromptVo()){
					if(coaVo.getPromptVo().getPromptResult()==PromptVo._YES){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
}
