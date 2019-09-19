package gov.nwcg.isuite.core.rules.referencedata._209codes.save;

import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.persistence.Sit209Dao;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSave209CodeRule extends AbstractRule {
	
	protected Sit209Vo vo;
	protected Sit209 sit209Entity;
	protected Sit209Dao sit209Dao;
	
	public AbstractSave209CodeRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ReferenceDataSave209CodeRuleFactory.ObjectTypeEnum.SIT209_VO.name()))
			vo = (Sit209Vo)object;
		if(objectName.equals(ReferenceDataSave209CodeRuleFactory.ObjectTypeEnum.SIT209_ENTITY.name()))
			sit209Entity = (Sit209)object;
		if(objectName.equals(ReferenceDataSave209CodeRuleFactory.ObjectTypeEnum.SIT209_DAO.name()))
			sit209Dao = (Sit209Dao)object;
	}
	
}
