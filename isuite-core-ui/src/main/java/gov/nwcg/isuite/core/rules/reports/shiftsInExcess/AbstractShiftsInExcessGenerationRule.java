package gov.nwcg.isuite.core.rules.reports.shiftsInExcess;

import gov.nwcg.isuite.core.reports.filter.ShiftsInExcessOfStandardHoursReportFilter;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class AbstractShiftsInExcessGenerationRule extends AbstractRule {
	protected ShiftsInExcessOfStandardHoursReportFilter filter;
	
	public AbstractShiftsInExcessGenerationRule(ApplicationContext ctx){
		context=ctx;
	}

	public AbstractShiftsInExcessGenerationRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ShiftsInExcessReportGeneratorRuleFactory.ObjectTypeEnum.SHIFTS_REPORT_FILTER.name()))
			filter = (ShiftsInExcessOfStandardHoursReportFilter)object;
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
