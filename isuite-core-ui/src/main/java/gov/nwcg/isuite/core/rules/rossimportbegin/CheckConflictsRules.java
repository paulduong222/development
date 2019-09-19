package gov.nwcg.isuite.core.rules.rossimportbegin;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossConflictVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossImportVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossResourceVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class CheckConflictsRules extends AbstractRossImportBeginRule implements IRule{
	public static final String _RULE_NAME=RossImportProcessBeginRuleFactory.RuleEnum.CHECK_CONFLICTS.name();
	
	public CheckConflictsRules(ApplicationContext ctx)
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
		RossImportVo rossImportVo = (RossImportVo)dialogueVo.getResultObjectAlternate4();

		checkResourceConflicts(rossImportVo);
			
		if(CollectionUtility.hasValue(rossImportVo.getRossConflictVos()))
			rossImportVo.setHasConflicts(true);
		else
			rossImportVo.setHasConflicts(false);
			
		
		// update roa4 with updated rossimportvo
		dialogueVo.setResultObjectAlternate4(rossImportVo);
		
		return _OK;
	}

	private void checkResourceConflicts(RossImportVo rossImportVo) {
		
		int cnt=1;
		
		for(RossResourceVo vo : rossImportVo.getRossResourceVos()){

			// Check person conflicts
			if(BooleanUtility.isTrue(vo.getIsPerson())){
				/*
				 * Verify we have firstName and lastName
				 * If there is no lastName, determine if we can parse resourceName
				 */
				if(!StringUtility.hasValue(vo.getLastName())){
					// unable to determine first and last name
					RossConflictVo conflictVo = new RossConflictVo();
					conflictVo.setRossResId(vo.getRossResId());
					conflictVo.setRossResReqId(vo.getRossResReqId());
					conflictVo.setRequestNumber(vo.getRequestNumber());
					conflictVo.setSortRequestNumber(vo.getSortRequestNumber());
					conflictVo.setIsResolved(false);
					conflictVo.setId(Long.valueOf(cnt));
					cnt++;
					conflictVo.setOrigFieldValue(vo.getResourceName());
					conflictVo.setConflictType("CANNOT_RESOLVE_FIRST_LAST_NAMES");
					conflictVo.setConflictDesc("Cannot resolve First and Last Names");
					rossImportVo.getRossConflictVos().add(conflictVo);
					
				}else{
					// check for lastname and firstname lengths
					if(StringUtility.hasValue(vo.getFirstName())){
						if(vo.getFirstName().length()>35){
							RossConflictVo conflictVo = new RossConflictVo();
							conflictVo.setRossResId(vo.getRossResId());
							conflictVo.setRossResReqId(vo.getRossResReqId());
							conflictVo.setRequestNumber(vo.getRequestNumber());
							conflictVo.setSortRequestNumber(vo.getSortRequestNumber());
							conflictVo.setIsResolved(false);
							conflictVo.setId(Long.valueOf(cnt));
							cnt++;
							conflictVo.setOrigFieldValue(vo.getFirstName());
							conflictVo.setConflictType("RESOURCE_FIRST_NAME_TOO_LONG");
							conflictVo.setConflictDesc("First Name exceeds 35 characters");
							rossImportVo.getRossConflictVos().add(conflictVo);
						}
					}
					
					if(StringUtility.hasValue(vo.getLastName())){
						if(vo.getLastName().length()>35){
							RossConflictVo conflictVo = new RossConflictVo();
							conflictVo.setRossResId(vo.getRossResId());
							conflictVo.setRossResReqId(vo.getRossResReqId());
							conflictVo.setRequestNumber(vo.getRequestNumber());
							conflictVo.setSortRequestNumber(vo.getSortRequestNumber());
							conflictVo.setIsResolved(false);
							conflictVo.setId(Long.valueOf(cnt));
							cnt++;
							conflictVo.setOrigFieldValue(vo.getLastName());
							conflictVo.setConflictType("RESOURCE_LAST_NAME_TOO_LONG");
							conflictVo.setConflictDesc("Last Name exceeds 35 characters");
							rossImportVo.getRossConflictVos().add(conflictVo);
						}
					}
				}
			}

			// Check non-person conflicts
			if(BooleanUtility.isFalse(vo.getIsPerson())){
				if(StringUtility.hasValue(vo.getResourceName())){
					if(vo.getResourceName().length()>55){
						RossConflictVo conflictVo = new RossConflictVo();
						conflictVo.setRossResId(vo.getRossResId());
						conflictVo.setRossResReqId(vo.getRossResReqId());
						conflictVo.setRequestNumber(vo.getRequestNumber());
						conflictVo.setSortRequestNumber(vo.getSortRequestNumber());
						conflictVo.setIsResolved(false);
						conflictVo.setId(Long.valueOf(cnt));
						cnt++;
						conflictVo.setOrigFieldValue(vo.getResourceName());
						conflictVo.setConflictType("RESOURCE_NAME_TOO_LONG");
						conflictVo.setConflictDesc("Resource Name exceeds 55 characters");
						rossImportVo.getRossConflictVos().add(conflictVo);
					}
				}
			}
		}
		
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
