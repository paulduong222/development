package gov.nwcg.isuite.core.rules.costaccruals.runextract;

import gov.nwcg.isuite.core.persistence.CostAccrualExtractDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class AbstractRunExtractRule extends AbstractRule {
	protected String extractDateString;
	protected Date extractDate;
	protected CostAccrualExtractDao dao;
	protected Long incidentId;
	protected Long incidentGroupId;
	
	public AbstractRunExtractRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	public void setObject(Object object, String objectName) {
		if(objectName.equals(RunExtractRuleFactory.ObjectTypeEnum.EXTRACT_DATE_STRING.name())){
			extractDateString = (String)object;
			if(StringUtility.hasValue(extractDateString)){
				try{
					extractDate=DateUtil.toDate(extractDateString, DateUtil.MM_SLASH_DD_SLASH_YYYY);
				}catch(Exception e){}
			}
		}
		if(objectName.equals(RunExtractRuleFactory.ObjectTypeEnum.COST_ACCRUAL_DAO.name()))
			dao=(CostAccrualExtractDao)object;
		if(objectName.equals(RunExtractRuleFactory.ObjectTypeEnum.INCIDENT_ID.name()))
			incidentId=(Long)object;
		if(objectName.equals(RunExtractRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_ID.name()))
			incidentGroupId=(Long)object;
	}
}
