package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import org.springframework.context.ApplicationContext;

public class AbstractIncidentResourceSaveRule extends AbstractRule {
	protected IncidentResourceVo vo=null;
	protected Long workAreaId=null;
	protected IncidentResourceDao irDao = null;
	protected ResourceData origResourceData = null;
	protected IncidentResource irEntity=null;
	
	protected TimePostDao tpDao=null;
	
	public AbstractIncidentResourceSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentResourceSaveRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_VO.name()))
			vo = (IncidentResourceVo)object;
		if(objectName.equals(IncidentResourceSaveRuleFactory.ObjectTypeEnum.WORK_AREA_ID.name()))
			workAreaId = (Long)object;
		if(objectName.equals(IncidentResourceSaveRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_DAO.name()))
			irDao = (IncidentResourceDao)object;
		if(objectName.equals(IncidentResourceSaveRuleFactory.ObjectTypeEnum.RESOURCE_DATA.name()))
			origResourceData = (ResourceData)object;
		if(objectName.equals(IncidentResourceSaveRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_ENTITY.name()))
			irEntity = (IncidentResource)object;
	}

	/**
	 * Returns whether or not the resource has any non-invoiced time postings.
	 *  
	 * @param id
	 * 		the incident resource id
	 * @return
	 */
	protected Boolean hasNonInvoicedTimePostings(Long id) throws Exception {
		
		TimePostDao timePostDao = (TimePostDao)context.getBean("timePostDao");
		
		int cnt = timePostDao.getResourceNonInvoicedTimePostCount(id);
		
		return (cnt > 0 ? true : false);
	}
	
	/**
	 * Returns whether or not the resource has non-invoiced time postings.
	 *  
	 * @param id
	 * 		the incident resource id
	 * @return
	 */
	protected Boolean hasInvoicedTimePostings(Long id) throws Exception {
		
		TimePostDao timePostDao = (TimePostDao)context.getBean("timePostDao");
		
		int cnt = timePostDao.getResourceInvoicedTimePostCount(id);
		
		return (cnt > 0 ? true : false);
	}
	
	/**
	 * Returns whether or not the resource has exported time postings.
	 *  
	 * @param id
	 * 		the incident resource id
	 * @return
	 */
	protected Boolean hasExportedInvoices(Long id) throws Exception {

		return false;
	}
	
	protected Boolean isParentStrikeTeam(IncidentResource parentIR){
		Boolean strikeTeam=false;
		
		/*
		 * Establish if isParentStrikeForce is true or false
		 */
		if(null != parentIR){
			for(Assignment a : parentIR.getWorkPeriod().getAssignments()){
				if(a.getEndDate()==null){
					if(BooleanUtility.isTrue(a.getKind().getStrikeTeam())){
						strikeTeam=true;
					}
					break;
				}
			}
		}
		
		return strikeTeam;
	}

	
}
