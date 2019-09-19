package gov.nwcg.isuite.core.rules.financial.OF288V2;

import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeAdustDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimePostDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class AbstractInvoiceGenerationRule extends AbstractRule {
	protected Collection<IncidentResourceTimeDataVo> irTimeDataVos;
	protected Collection<Date> dates;
	protected TimeReportFilter filter;
	protected String reportName;
	protected Collection<IncidentResourceTimePostDataVo> timePostDataVos;
	protected Collection<IncidentResourceTimeAdustDataVo> timeAdjustDataVos;
	  
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
		if(objectName.equals(OF288InvoiceGeneratorRuleFactory2.ObjectTypeEnum.NEW_INCIDENT_RESOURCE_TIME_DATA_VO.name()))
			irTimeDataVos = (Collection<IncidentResourceTimeDataVo>)object;
		if(objectName.equals(OF288InvoiceGeneratorRuleFactory2.ObjectTypeEnum.DATES.name()))
			dates = (Collection<Date>)object;
		if(objectName.equals(OF288InvoiceGeneratorRuleFactory2.ObjectTypeEnum.TIME_REPORT_FILTER.name()))
			filter = (TimeReportFilter)object;
		if(objectName.equals(OF288InvoiceGeneratorRuleFactory2.ObjectTypeEnum.TIME_POST_DATA_VOS.name()))
			timePostDataVos = (Collection<IncidentResourceTimePostDataVo>)object;
		if(objectName.equals(OF288InvoiceGeneratorRuleFactory2.ObjectTypeEnum.TIME_ADJUST_DATA_VOS.name()))
			timeAdjustDataVos = (Collection<IncidentResourceTimeAdustDataVo>)object;
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
