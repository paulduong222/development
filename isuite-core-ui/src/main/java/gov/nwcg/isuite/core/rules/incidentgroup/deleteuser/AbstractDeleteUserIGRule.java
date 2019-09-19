package gov.nwcg.isuite.core.rules.incidentgroup.deleteuser;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteUserIGRule extends AbstractRule {
	protected Collection<UserGridVo> userGridVos=new ArrayList<UserGridVo>();	
	protected Long incidentGroupId;
	protected IncidentGroupDao incidentGroupDao;
	
	public AbstractDeleteUserIGRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/**
	 * @param object
	 * @param objectName
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(IncidentGroupDeleteUserRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_ID.name()))
			incidentGroupId = (Long)object;
		if(objectName.equals(IncidentGroupDeleteUserRuleFactory.ObjectTypeEnum.USER_GRID_VOS.name()))
			userGridVos = (Collection<UserGridVo>)object;
		if(objectName.equals(IncidentGroupDeleteUserRuleFactory.ObjectTypeEnum.INCIDENT_GROUP_DAO.name()))
			incidentGroupDao = (IncidentGroupDao)object;
	}
	
}
