package gov.nwcg.isuite.core.rules.incidentresourceother.save;

import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveIRORule extends AbstractRule {
	protected IncidentResourceOtherVo vo=null;
	protected IncidentResourceOther iroEntity=null;
	
	public AbstractSaveIRORule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveIRORuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_OTHER_VO.name()))
			vo = (IncidentResourceOtherVo)object;
		if(objectName.equals(SaveIRORuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_OTHER_ENTITY.name()))
			iroEntity = (IncidentResourceOther)object;
	}
	
	
}
