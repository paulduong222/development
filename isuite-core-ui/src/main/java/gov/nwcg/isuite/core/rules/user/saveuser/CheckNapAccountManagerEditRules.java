package gov.nwcg.isuite.core.rules.user.saveuser;

import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckNapAccountManagerEditRules extends AbstractSaveUserRule implements IRule{
	public static final String _RULE_NAME=SaveUserRuleFactory.RuleEnum.CHECK_NAP_ACCOUNT_MANAGER_RULE.name();

	public CheckNapAccountManagerEditRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
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
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) {
		Collection<ErrorObject> errorObjectVos = new ArrayList<ErrorObject>();

		/*
		 * If the user record being saved is being enabled or disabled,
		 * and the user record is a data manager, global rates mgr, or geo rates mgr,
		 * and the logged in user is only a account manager,
		 * then the logged in user cannot enable or disable the user record.
		 */
		String runMode="";
		try{
			runMode=super.getRunMode();
		}catch(Exception ee){}
		
		if(runMode.equals("ENTERPRISE")){
		
			if(super.userEntity != null){
				if(super.userEntity.isEnabled() != super.userVo.getEnabled()){
					
					if(sessionUserIsAccountManagerOnly()==true){
						int cnt=0;
						for(SystemRole sr : userEntity.getSystemRoles()){
							if(sr.getRoleName().equalsIgnoreCase("ROLE_DATA_MANAGER"))
								cnt+=1;
							if(sr.getRoleName().equalsIgnoreCase("ROLE_GLOBAL_REF_DATA_MANAGER"))
								cnt+=1;
							if(sr.getRoleName().equalsIgnoreCase("ROLE_GEOGRAPHIC_DATA_MANAGER"))
								cnt+=1;
						}
						
						if(cnt>0){
							ErrorObject error2 = new ErrorObject("error.800000","User with only the Account Manager Role cannot enable/disable Data, Global Reference, or Geographic Data Managers.");
							errorObjectVos.add(error2);
						}
					}
				}
			}
			
			if(errorObjectVos != null && errorObjectVos.size() > 0) {
	
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaName(_RULE_NAME);
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ERROR);
				courseOfActionVo.setErrorObjectVos(errorObjectVos);
				courseOfActionVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(courseOfActionVo);
				
				return _FAIL;
			}
		}
		
		return _OK;
	}

	private boolean sessionUserIsAccountManagerOnly() {
		boolean rtn=false;
		boolean hasAMRole=false;
		boolean hasDMRole=false;
		
		UserSessionVo sessionUserVo=super.getUserSessionVo();
		for(SystemRoleVo v : sessionUserVo.getUserRoleVos()){
			if(v.getRoleName().equalsIgnoreCase("ROLE_ACCOUNT_MANAGER")){
				hasAMRole=true;
			}
			if(v.getRoleName().equalsIgnoreCase("ROLE_DATA_MANAGER")){
				hasDMRole=true;
			}
		}
		
		if(hasAMRole==true && hasDMRole==false)
			rtn = true;
		
		return rtn;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
