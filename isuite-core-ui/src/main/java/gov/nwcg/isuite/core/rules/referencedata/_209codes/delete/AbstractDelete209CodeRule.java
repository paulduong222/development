package gov.nwcg.isuite.core.rules.referencedata._209codes.delete;

import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.persistence.Sit209Dao;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDelete209CodeRule extends AbstractRule {
	
	Sit209Vo sit209Vo;
	Sit209 sit209Entity;
	Sit209Dao sit209Dao;
	
	public AbstractDelete209CodeRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ReferenceDataDelete209CodeRuleFactory.ObjectTypeEnum._209_CODE_VO.name()))
			sit209Vo = (Sit209Vo)object;
		if(objectName.equals(ReferenceDataDelete209CodeRuleFactory.ObjectTypeEnum._209_CODE_ENTITY.name()))
			sit209Entity = (Sit209)object;
		if(objectName.equals(ReferenceDataDelete209CodeRuleFactory.ObjectTypeEnum._209_CODE_DAO.name()))
			sit209Dao = (Sit209Dao)object;
	}
	
	
}
