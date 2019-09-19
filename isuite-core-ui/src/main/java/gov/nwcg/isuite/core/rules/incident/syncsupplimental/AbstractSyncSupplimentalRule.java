package gov.nwcg.isuite.core.rules.incident.syncsupplimental;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSyncSupplimentalRule extends AbstractRule {
	protected Incident incidentEntity;
	
	public AbstractSyncSupplimentalRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SyncSupplimentalRuleFactory.ObjectTypeEnum.INCIDENT_ENTITY.name()))
			incidentEntity = (Incident)object;
	}
	
	
}
