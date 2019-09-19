package gov.nwcg.isuite.core.rules.financial.OF286;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class AbstractInvoiceGenerationRule extends AbstractRule {
	protected Collection<IncidentResourceVo> irs;
	protected Collection<Date> dates;
	protected TimeReportFilter filter;
	protected String reportName;
	
	public AbstractInvoiceGenerationRule(ApplicationContext ctx){
		context=ctx;
	}

	public AbstractInvoiceGenerationRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void setObject(Object object, String objectName) {
		if(objectName.equals(OF286InvoiceGeneratorRuleFactory.ObjectTypeEnum.NEW_INCIDENT_RESOURCE_VO.name()))
			irs = (Collection<IncidentResourceVo>)object;
		if(objectName.equals(OF286InvoiceGeneratorRuleFactory.ObjectTypeEnum.DATES.name()))
			dates = (Collection<Date>)object;
		if(objectName.equals(OF286InvoiceGeneratorRuleFactory.ObjectTypeEnum.TIME_REPORT_FILTER.name()))
			filter = (TimeReportFilter)object;
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

	protected String getResourceName(Long id, Collection<IncidentResourceVo> irs) {
		String resourceName = "";
		for (IncidentResourceVo ir : irs) {
			if (ir.getResourceVo().getId() == id) {
				if (ir.getResourceVo().getResourceName() != null) {
					resourceName = ir.getResourceVo().getResourceName();
					break;
				} else {
					resourceName = ir.getResourceVo().getFirstName() + " " + ir.getResourceVo().getLastName();
					break;
				}
			}
		}
		return resourceName;
	}

	protected String getRequestNumber(Long id, Collection<IncidentResourceVo> irs) {
		String requestNumber = "";
		for (IncidentResourceVo ir : irs) {
			if (ir.getResourceVo().getId() == id) {
				for (AssignmentVo a : ir.getWorkPeriodVo().getAssignmentVos()) {
					requestNumber = a.getRequestNumber();
					if (requestNumber != null)
						break;
				}
			}
		}
		return requestNumber;
	}

}
