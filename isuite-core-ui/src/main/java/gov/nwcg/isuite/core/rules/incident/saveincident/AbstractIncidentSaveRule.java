package gov.nwcg.isuite.core.rules.incident.saveincident;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractIncidentSaveRule extends AbstractRule {
	protected IncidentVo vo;
	protected Incident incidentEntity;
	protected IncidentDao incidentDao;
	protected WorkAreaVo workAreaVo;
	protected OrganizationVo originalPdcVo;
	protected OrganizationVo newPdcVo;
	
	public AbstractIncidentSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveIncidentRuleFactory.ObjectTypeEnum.INCIDENT_VO.name()))
			vo = (IncidentVo)object;
		if(objectName.equals(SaveIncidentRuleFactory.ObjectTypeEnum.INCIDENT_ENTITY.name()))
			incidentEntity = (Incident)object;
		if(objectName.equals(SaveIncidentRuleFactory.ObjectTypeEnum.INCIDENT_DAO.name()))
			incidentDao = (IncidentDao)object;
		if(objectName.equals(SaveIncidentRuleFactory.ObjectTypeEnum.WORK_AREA_VO.name()))			
			workAreaVo = (WorkAreaVo)object;
		if(objectName.equals(SaveIncidentRuleFactory.ObjectTypeEnum.ORIGINAL_PDC_VO.name()))			
			originalPdcVo = (OrganizationVo)object;
		if(objectName.equals(SaveIncidentRuleFactory.ObjectTypeEnum.NEW_PDC_VO.name()))			
			newPdcVo = (OrganizationVo)object;
	}
	
	
}
