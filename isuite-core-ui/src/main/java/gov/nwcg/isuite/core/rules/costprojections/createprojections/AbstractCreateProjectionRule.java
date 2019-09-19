package gov.nwcg.isuite.core.rules.costprojections.createprojections;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.CostProjectionDao;
import gov.nwcg.isuite.core.vo.ProjectionVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class AbstractCreateProjectionRule extends AbstractRule {
//	protected String startDateString;
//	protected String projectionName;
//	protected Date startDate;
//	protected Integer days;
//	protected Long incidentId;
//	protected Long incidentGroupId;
	protected CostProjectionDao dao;
	protected ProjectionVo projectionVo;
	protected Incident incident;
	protected IncidentGroup incidentGroup;
	protected String today;

	
	
	public AbstractCreateProjectionRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname; 
	}
	
	public void setObject(Object object, String objectName) {
//		if(objectName.equals(RunCreateProjectionRuleFactory.ObjectTypeEnum.PROJECTION_START_DATE_STRING.name())){
//			startDateString = (String)object;
//			if(StringUtility.hasValue(startDateString)){
//				try{
//					startDate = DateUtil.toDate(startDateString, DateUtil.MM_SLASH_DD_SLASH_YYYY);
//				}catch(Exception e){}
//			}
//		}
		
		if(objectName.equals(RunCreateProjectionRuleFactory.ObjectTypeEnum.COST_PROJECTION_DAO.name()))
			dao=(CostProjectionDao)object;
		
		if(objectName.equals(RunCreateProjectionRuleFactory.ObjectTypeEnum.PROJECTION_VO.name()))
			projectionVo = (ProjectionVo)object;
		
		if(objectName.equals(RunCreateProjectionRuleFactory.ObjectTypeEnum.INCIDENT.name()))
			incident = (Incident)object;
		
		if(objectName.equals(RunCreateProjectionRuleFactory.ObjectTypeEnum.INCIDENTGROUP.name()))
			incidentGroup = (IncidentGroup)object;
		
		if(objectName.equals(RunCreateProjectionRuleFactory.ObjectTypeEnum.TODAY.name()))
			today = (String)object;
		
//		if(objectName.equals(RunCreateProjectionRuleFactory.ObjectTypeEnum.INCIDENT_ID.name()))
//			incidentId=(Long)object;
//		
//		if(objectName.equals(RunCreateProjectionRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_ID.name()))
//			incidentGroupId=(Long)object;
//		
//		if(objectName.equals(RunCreateProjectionRuleFactory.ObjectTypeEnum.PRJECTION_NAME_STRING.name()))
//			projectionName =(String)object;
//		
//		if(objectName.equals(RunCreateProjectionRuleFactory.ObjectTypeEnum.PROJECTION_DAYS_RANGE_STRING.name())) 
//			days = (Integer) object;
	}
}
