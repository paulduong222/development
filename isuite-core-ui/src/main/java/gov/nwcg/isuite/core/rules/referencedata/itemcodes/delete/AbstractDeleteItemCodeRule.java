package gov.nwcg.isuite.core.rules.referencedata.itemcodes.delete;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractDeleteItemCodeRule extends AbstractRule {
	
	KindVo kindVo;
	Kind kindEntity;
	KindDao kindDao;
	
	public AbstractDeleteItemCodeRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ReferenceDataDeleteItemCodeRuleFactory.ObjectTypeEnum.KIND_VO.name()))
			kindVo = (KindVo)object;
		if(objectName.equals(ReferenceDataDeleteItemCodeRuleFactory.ObjectTypeEnum.KIND_ENTITY.name()))
			kindEntity = (Kind)object;
		if(objectName.equals(ReferenceDataDeleteItemCodeRuleFactory.ObjectTypeEnum.KIND_DAO.name()))
			kindDao = (KindDao)object;
	}
	
	
}
