package gov.nwcg.isuite.core.rules.timeassignadjust.save;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractTimeAssignAdjustSaveRule extends AbstractRule {
	protected TimeAssignAdjustVo timeAssignAdjustVo=null;
	protected TimeAssignAdjustDao taaDao=null;
	protected TimeAssignAdjust timeAssignAdjustEntity=null;
	protected Assignment assignmentEntity = null;
	protected IncidentResourceVo irvo = null;
	
	public AbstractTimeAssignAdjustSaveRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(TimeAssignAdjustSaveRuleFactory.ObjectTypeEnum.INCIDENT_RESOURCE_VO.name()))
			irvo = (IncidentResourceVo)object;
		if(objectName.equals(TimeAssignAdjustSaveRuleFactory.ObjectTypeEnum.TIME_ASSIGN_ADJUST_VO.name()))
			timeAssignAdjustVo = (TimeAssignAdjustVo)object;
		if(objectName.equals(TimeAssignAdjustSaveRuleFactory.ObjectTypeEnum.TIME_ASSIGN_ADJUST_DAO.name()))
			taaDao = (TimeAssignAdjustDao)object;
		if(objectName.equals(TimeAssignAdjustSaveRuleFactory.ObjectTypeEnum.TIME_ASSIGN_ADJUST_ENTITY.name()))
			timeAssignAdjustEntity = (TimeAssignAdjust)object;
		if(objectName.equals(TimeAssignAdjustSaveRuleFactory.ObjectTypeEnum.ASSIGNMENT_ENTITY.name()))
			assignmentEntity = (Assignment)object;
	}
	
	
}
