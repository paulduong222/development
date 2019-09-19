package gov.nwcg.isuite.core.rules.reports.personnelTime;

import gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class AbstractPersonnelTimeGenerationRule extends AbstractRule {
	protected PersonnelTimeReportFilter filter;
	
	public AbstractPersonnelTimeGenerationRule(ApplicationContext ctx){
		context=ctx;
	}

	public AbstractPersonnelTimeGenerationRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void setObject(Object object, String objectName) {
		if(objectName.equals(PersonnelTimeReportGeneratorRuleFactory.ObjectTypeEnum.PERSONNEL_TIME_REPORT_FILTER.name()))
			filter = (PersonnelTimeReportFilter)object;
	}
	
	@Override
	public void addAdditionalMessages(DialogueVo dialogueVo) throws Exception {

		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName);

		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			if(null != dialogueVo.getCourseOfActionVo().getMessageVo()){
				dialogueVo.getCourseOfActionVo().getMessageVo().getAdditionalMessageVos().add(coaVo.getMessageVo());
			}
		}
	}

}
