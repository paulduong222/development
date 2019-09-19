package gov.nwcg.isuite.core.rules.workarea.save;

import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.persistence.WorkAreaDao;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveWorkAreaRule extends AbstractRule {
	protected WorkAreaVo workAreaVo;
	protected WorkArea entity;
	protected WorkAreaDao workAreaDao;
	
	public AbstractSaveWorkAreaRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(WorkAreaSaveRuleFactory.ObjectTypeEnum.WORK_AREA_VO.name()))
			workAreaVo = (WorkAreaVo)object;
		if(objectName.equals(WorkAreaSaveRuleFactory.ObjectTypeEnum.WORK_AREA_ENTITY.name()))
			entity = (WorkArea)object;
		if(objectName.equals(WorkAreaSaveRuleFactory.ObjectTypeEnum.WORK_AREA_DAO.name()))
			workAreaDao = (WorkAreaDao)object;
	}

	
	
}
