package gov.nwcg.isuite.core.rules.user.importusers;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.xml.XmlSchemaTypeEnum;

import org.springframework.context.ApplicationContext;

public class SetXMLSchemaAndXSDPathRules extends AbstractImportUsersRule implements IRule {

	public static final String _RULE_NAME=ImportUserAccountsRuleFactory.RuleEnum.SET_XML_SCHEMA_AND_XSD_PATH_RULE.name();

	public SetXMLSchemaAndXSDPathRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;

			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
			.add(super.buildNoActionCoaVo(_RULE_NAME,true));

		}catch(Exception e){
			throw new ServiceException(e);
		}

		return _OK;
	}

	/**
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		xmlHandler.setXmlSchemaType(XmlSchemaTypeEnum.USER_TRANSFER);
		
		if(super.getSystemParamValue(SystemParameterTypeEnum.VALIDATE_XML).equals("TRUE")){
			String xsdBasePath=super.getSystemParamValue(SystemParameterTypeEnum.XSD_BASE_PATH);
			xmlHandler.setXsdBasePath(xsdBasePath);
		} else {
			return _FAIL;
			//TODO Construct COA vo.
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

}
