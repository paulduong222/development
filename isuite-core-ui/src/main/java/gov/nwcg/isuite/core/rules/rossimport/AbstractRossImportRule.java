package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.vo.RossXmlFileVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractRossImportRule extends AbstractRule {
	protected RossXmlFileVo rxfVo;
	protected String action="";
	protected UserVo userVo;
	
	public AbstractRossImportRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(RossImportProcessRuleFactory.ObjectTypeEnum.ROSS_XML_FILE_VO.name()))
			rxfVo = (RossXmlFileVo)object;
		if(objectName.equals(RossImportProcessRuleFactory.ObjectTypeEnum.ACTION.name()))
			action = (String)object;
		if(objectName.equals(RossImportProcessRuleFactory.ObjectTypeEnum.USER_VO.name()))
			userVo = (UserVo)object;
	}
	
	public boolean validateProceedThisWizard(DialogueVo dialogueVo) throws Exception{
		return true;
	}
	
}
