package gov.nwcg.isuite.core.rules.referencedata.subcategorygroups.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.SubGroupCategory;
import gov.nwcg.isuite.core.persistence.SubGroupCategoryDao;
import gov.nwcg.isuite.core.vo.SubGroupCategoryVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractDeleteSubGroupCategoryRule extends AbstractRule {
	
	protected SubGroupCategoryVo subGroupCategoryVo;
	protected SubGroupCategory subGroupCategoryEntity;
	protected SubGroupCategoryDao subGroupCategoryDao;
	
	public AbstractDeleteSubGroupCategoryRule(ApplicationContext ctx, String rname) {
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ReferenceDataDeleteSubGroupCategoryRuleFactory.ObjectTypeEnum.SUB_GROUP_CATEOGRY_VO.name()))
			subGroupCategoryVo = (SubGroupCategoryVo)object;
		if(objectName.equals(ReferenceDataDeleteSubGroupCategoryRuleFactory.ObjectTypeEnum.SUB_GROUP_CATEGORY_ENTITY.name()))
			subGroupCategoryEntity = (SubGroupCategory)object;
		if(objectName.equals(ReferenceDataDeleteSubGroupCategoryRuleFactory.ObjectTypeEnum.SUB_GROUP_CATEGORY_DAO.name()))
			subGroupCategoryDao = (SubGroupCategoryDao)object;
	}

}
