package gov.nwcg.isuite.core.rules.user.resolvefailedimports;

import gov.nwcg.isuite.core.domain.UserImportFailure;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.persistence.UserImportFailureDao;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ResolveFailedImportRulesFactory {

	public enum ObjectTypeEnum {
		VALID_USER_VO,
		UIF_DAO,
		USER_DAO,
		UIF_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_LOGIN_NAME_RULE(5),
		VALIDATE_RESOLVE_FAILED_IMPORTS_FORM_DATA(10),
		CHECK_PRIV_USER_RULE(15)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	public static IRule getInstance(
			RuleEnum rule, ApplicationContext ctx, UserVo validUserVo,UserImportFailureDao uifDao, 
			UserDao userDao, UserImportFailure uifEntity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 5:
				ruleInstance = new CheckLoginNameRules(ctx, rule.name());
				break;
			case 10:
				ruleInstance = new ValidateResolveFailedImportsFormDataRules(ctx, rule.name());
				break;
			case 15:
				ruleInstance = new CheckPrivUserRules(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(validUserVo, ObjectTypeEnum.VALID_USER_VO.name());
			ruleInstance.setObject(uifDao, ObjectTypeEnum.UIF_DAO.name());
			ruleInstance.setObject(userDao, ObjectTypeEnum.USER_DAO.name());
			ruleInstance.setObject(uifEntity, ObjectTypeEnum.UIF_ENTITY.name());
		}
		
		return ruleInstance;
	}
	
}
