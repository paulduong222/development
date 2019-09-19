package gov.nwcg.isuite.core.rules.incident.deleteincacctcode;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteIncAcctCodeRule extends AbstractRule {
	protected IncidentAccountCode entity;
	protected IncidentAccountCodeDao dao;
	protected Incident incidentEntity;
	
	public AbstractDeleteIncAcctCodeRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(DeleteIncAcctCodeRuleFactory.ObjectTypeEnum.INCIDENT_ACCOUNT_CODE_ENTITY.name()))
			entity = (IncidentAccountCode)object;
		if(objectName.equals(DeleteIncAcctCodeRuleFactory.ObjectTypeEnum.INCIDENT_ENTITY.name()))
			incidentEntity = (Incident)object;
		if(objectName.equals(DeleteIncAcctCodeRuleFactory.ObjectTypeEnum.INCIDENT_ACCOUNT_CODE_DAO.name()))
			dao = (IncidentAccountCodeDao)object;
	}
	
	
}
