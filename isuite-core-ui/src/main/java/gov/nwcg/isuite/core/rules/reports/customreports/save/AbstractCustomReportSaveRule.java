package gov.nwcg.isuite.core.rules.reports.customreports.save;

import gov.nwcg.isuite.core.persistence.CustomReportDao;
import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class AbstractCustomReportSaveRule extends AbstractRule {
	protected CustomReportVo customReportVo;
	protected UserVo userVo;
	CustomReportDao customReportDao;
	
	public AbstractCustomReportSaveRule(ApplicationContext ctx){
		context=ctx;
	}

	public AbstractCustomReportSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(CustomReportSaveRuleFactory.ObjectTypeEnum.CUSTOM_REPORT_FILTER.name())) {
			customReportVo = (CustomReportVo)object;
		} 
		if(objectName.equals(CustomReportSaveRuleFactory.ObjectTypeEnum.USER_VO.name())){
			userVo = (UserVo)object;
		}
		if(objectName.equals(CustomReportSaveRuleFactory.ObjectTypeEnum.CUSTOM_REPORT_DAO.name())){
			customReportDao = (CustomReportDao)object;
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
