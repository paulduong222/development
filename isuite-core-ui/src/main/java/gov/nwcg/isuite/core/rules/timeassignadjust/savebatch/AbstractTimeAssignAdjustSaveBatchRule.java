package gov.nwcg.isuite.core.rules.timeassignadjust.savebatch;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.rules.timeassignadjust.savebatch.TimeAssignAdjustSaveBatchRuleFactory;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class AbstractTimeAssignAdjustSaveBatchRule extends AbstractRule {
	protected TimeAssignAdjustVo timeAssignAdjustVo=null;
	protected TimeAssignAdjustDao taaDao=null;
	protected TimeAssignAdjust timeAssignAdjustEntity=null;
	protected Assignment assignmentEntity = null;
	protected IncidentResourceVo irvo = null;
	protected Collection<Long> crewIds=null;
	
	public AbstractTimeAssignAdjustSaveBatchRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(TimeAssignAdjustSaveBatchRuleFactory.ObjectTypeEnum.CREWIDS.name()))
			crewIds = (Collection<Long>)object;
		if(objectName.equals(TimeAssignAdjustSaveBatchRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_VO.name()))
			irvo = (IncidentResourceVo)object;
		if(objectName.equals(TimeAssignAdjustSaveBatchRuleFactory.ObjectTypeEnum.TIME_ASSIGN_ADJUST_VO.name()))
			timeAssignAdjustVo = (TimeAssignAdjustVo)object;
		if(objectName.equals(TimeAssignAdjustSaveBatchRuleFactory.ObjectTypeEnum.TIME_ASSIGN_ADJUST_DAO.name()))
			taaDao = (TimeAssignAdjustDao)object;
		if(objectName.equals(TimeAssignAdjustSaveBatchRuleFactory.ObjectTypeEnum.TIME_ASSIGN_ADJUST_ENTITY.name()))
			timeAssignAdjustEntity = (TimeAssignAdjust)object;
		if(objectName.equals(TimeAssignAdjustSaveBatchRuleFactory.ObjectTypeEnum.ASSIGNMENT_ENTITY.name()))
			assignmentEntity = (Assignment)object;
	}
	
	
}
