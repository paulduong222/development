package gov.nwcg.isuite.core.rules.referencedata.categorygroups.save;

import gov.nwcg.isuite.core.domain.GroupCategory;
import gov.nwcg.isuite.core.persistence.GroupCategoryDao;
import gov.nwcg.isuite.core.vo.GroupCategoryVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveCategoryGroupRule extends AbstractRule {
	
	protected GroupCategoryVo groupCategoryVo;
	protected GroupCategory groupCategoryEntity;
	protected GroupCategoryDao groupCategoryDao;
	
	public AbstractSaveCategoryGroupRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ReferenceDataSaveCategoryGroupRuleFactory.ObjectTypeEnum.GROUP_CATEGORY_VO.name()))
			groupCategoryVo = (GroupCategoryVo)object;
		if(objectName.equals(ReferenceDataSaveCategoryGroupRuleFactory.ObjectTypeEnum.GROUP_CATEGORY_ENTITY.name()))
			groupCategoryEntity = (GroupCategory)object;
		if(objectName.equals(ReferenceDataSaveCategoryGroupRuleFactory.ObjectTypeEnum.GROUP_CATEGORY_DAO.name()))
			groupCategoryDao = (GroupCategoryDao)object;
	}
	
	
}
