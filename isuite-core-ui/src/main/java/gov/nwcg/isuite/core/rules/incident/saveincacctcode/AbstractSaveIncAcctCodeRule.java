package gov.nwcg.isuite.core.rules.incident.saveincacctcode;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveIncAcctCodeRule extends AbstractRule {
	protected Incident incidentEntity;
	protected IncidentAccountCodeVo vo;
	protected IncidentAccountCode entity;
	protected IncidentAccountCodeDao dao;
	
	public AbstractSaveIncAcctCodeRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveIncAcctCodeRuleFactory.ObjectTypeEnum.INCIDENT_ACCOUNT_CODE_ENTITY.name()))
			entity = (IncidentAccountCode)object;
		if(objectName.equals(SaveIncAcctCodeRuleFactory.ObjectTypeEnum.INCIDENT_ENTITY.name()))
			incidentEntity = (Incident)object;
		if(objectName.equals(SaveIncAcctCodeRuleFactory.ObjectTypeEnum.INCIDENT_ACCOUNT_CODE_VO.name()))
			vo = (IncidentAccountCodeVo)object;
		if(objectName.equals(SaveIncAcctCodeRuleFactory.ObjectTypeEnum.INCIDENT_ACCOUNT_CODE_DAO.name()))
			dao = (IncidentAccountCodeDao)object;
	}
	
	
}
