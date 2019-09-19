package gov.nwcg.isuite.core.rules.referencedata.itemcodes.save;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveItemCodeRule extends AbstractRule {
	
	KindVo kindVo;
	Kind kindEntity;
	KindDao kindDao;
	
	public AbstractSaveItemCodeRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ReferenceDataSaveItemCodeRuleFactory.ObjectTypeEnum.KIND_VO.name()))
			kindVo = (KindVo)object;
		if(objectName.equals(ReferenceDataSaveItemCodeRuleFactory.ObjectTypeEnum.KIND_ENTITY.name()))
			kindEntity = (Kind)object;
		if(objectName.equals(ReferenceDataSaveItemCodeRuleFactory.ObjectTypeEnum.KIND_DAO.name()))
			kindDao = (KindDao)object;
	}
	
	
}
