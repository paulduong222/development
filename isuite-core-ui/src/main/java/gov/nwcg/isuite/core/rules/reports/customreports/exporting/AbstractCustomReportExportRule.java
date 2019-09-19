package gov.nwcg.isuite.core.rules.reports.customreports.exporting;

import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class AbstractCustomReportExportRule extends AbstractRule {
	protected CustomReportVo reportVo;
	protected UserVo userVo;
	
	public AbstractCustomReportExportRule(ApplicationContext ctx){
		context=ctx;
	}

	public AbstractCustomReportExportRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(CustomReportExportRuleFactory.ObjectTypeEnum.CUSTOM_REPORT_FILTER.name())) {
			reportVo = (CustomReportVo)object;
		} 
		if(objectName.equals(CustomReportExportRuleFactory.ObjectTypeEnum.USER_VO.name())){
			userVo = (UserVo)object;
		}
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
