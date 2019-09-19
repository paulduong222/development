package gov.nwcg.isuite.core.rules.rossimportbegin;

import gov.nwcg.isuite.core.persistence.RestrictedIncidentUserDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossImportVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckUserAccessRules extends AbstractRossImportBeginRule implements IRule {
	public static final String _RULE_NAME = RossImportProcessBeginRuleFactory.RuleEnum.CHECK_USER_ACCESS.name();

	public CheckUserAccessRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	/*
	 * (non-Javadoc)
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		/*
		 * CR 3607
		 */
		RossImportVo ripVo = (RossImportVo)dialogueVo.getResultObjectAlternate4();
		if(BooleanUtility.isTrue(ripVo.getHasIncidentMatch())){
			Long userId = super.userVo.getId();
			Long incidentId = ripVo.getEisuiteIncidentId();
			RestrictedIncidentUserDao riuDao = (RestrictedIncidentUserDao)context.getBean("restrictedIncidentUserDao");
			Collection<Long> userIds = riuDao.getRestrictedIncidentUsersAndOwnersUserIds(incidentId);
			Boolean hasAccess=false;
			if(CollectionUtility.hasValue(userIds)){
				for(Long id : userIds){
					if(userId.compareTo(id)==0){
						hasAccess=true;
						break;
					}
				}
			}
			
			if(hasAccess==false){
				String msg = "You do not have access to this Incident and therefore you may not import resources into this Incident.";
				dialogueVo.setCourseOfActionVo(
						super.buildErrorCoaVo("text.rossImport"
											  ,"validationerror"
											  ,"error.800000"
											  , new String[]{msg}));	
				return _FAIL;
			}
			
		}
		
		return _OK;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
