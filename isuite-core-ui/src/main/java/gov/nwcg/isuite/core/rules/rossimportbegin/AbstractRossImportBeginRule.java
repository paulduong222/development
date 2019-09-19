package gov.nwcg.isuite.core.rules.rossimportbegin;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.RossXmlFileVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractRossImportBeginRule extends AbstractRule {
	protected RossXmlFileVo rxfVo;
	protected Collection<Long> reimportReqIds;
	protected UserVo userVo;
	protected GlobalCacheVo gcVo;
	
	public AbstractRossImportBeginRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(RossImportProcessBeginRuleFactory.ObjectTypeEnum.ROSS_XML_FILE_VO.name()))
			rxfVo = (RossXmlFileVo)object;
		if(objectName.equals(RossImportProcessBeginRuleFactory.ObjectTypeEnum.REIMPORT_REQ_IDS.name()))
			reimportReqIds = (Collection<Long>)object;
		if(objectName.equals(RossImportProcessBeginRuleFactory.ObjectTypeEnum.USER_VO.name()))
			userVo = (UserVo)object;
	}
	
}
