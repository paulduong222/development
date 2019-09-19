package gov.nwcg.isuite.core.rules.rossimportend;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.RossIncDataBlacklist;
import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.impl.RossIncDataBlacklistImpl;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.RossIncDataBlacklistDao;
import gov.nwcg.isuite.core.persistence.RossXmlFileDao;
import gov.nwcg.isuite.core.persistence.RossXmlFileDataDao;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossResourceVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import org.springframework.context.ApplicationContext;

public class UpdateExcludeResourcesRules extends AbstractRossImportEndRule implements IRule{
	public static final String _RULE_NAME=RossImportProcessEndRuleFactory.RuleEnum.UPDATE_EXCLUDE_RESOURCES.name();
	
	public UpdateExcludeResourcesRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
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
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		if(super.rossImportVo.getIsReimport()==true){
			RossIncDataBlacklistDao blacklistDao=(RossIncDataBlacklistDao)context.getBean("rossIncDataBlacklistDao");
			
			for(RossResourceVo rossResourceVo : rossImportVo.getRossResourceVos()){
				blacklistDao.deleteByRossIncIdResReqId(rossImportVo.getRossIncidentId(), rossResourceVo.getRossResReqId());
			}
			
			return _OK;
		}
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}


}
