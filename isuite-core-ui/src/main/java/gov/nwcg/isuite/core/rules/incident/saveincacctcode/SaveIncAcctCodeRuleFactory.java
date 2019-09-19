package gov.nwcg.isuite.core.rules.incident.saveincacctcode;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveIncAcctCodeRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_ENTITY
		,INCIDENT_ACCOUNT_CODE_VO
		,INCIDENT_ACCOUNT_CODE_ENTITY
		,INCIDENT_ACCOUNT_CODE_DAO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,VALIDATE_DATA(5)
		,CHECK_AGENCY_CODE_UNIQUE(10)
		,CHECK_ONLY_1_FED_AGENCY(12)
		,CHECK_CODE_FOR_FED_WILDFIRE(15)
		,CHECK_CODE_LENGTH_FOR_FS(20)
		,CHECK_NEW_DEFAULT(30)
		,CHECK_UNIQUE_CODE(35)
		,CHECK_SAME_AGENCY_CODE(40)
		,CHECK_ACCT_CODE_NOT_INVOICED(45)
		,CHECK_ACCT_CODE_NOT_POSTED(48)
		,CHECK_EDIT_STILL_DEFAULT(50)
		,CHECK_FIRST_CODE_IS_DEFAULT(55)
		,CHECK_ACCT_CODE_ALREADY_DEFINED_FOR_AGENCY(60)
		,CHECK_FIRST_CODE_SET_RES_DEFAULT(100)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx
									,IncidentAccountCodeVo vo
									,IncidentAccountCode entity
									,Incident incidentEntity
									,IncidentAccountCodeDao dao) throws Exception {

		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckAgencyCodeUniqueRules(ctx);
				break;
			case 12:
				ruleInstance = new CheckOnly1FedAgencyRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckCodeForFedWildfireRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckCodeLengthForFSRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckNewDefaultRules(ctx);
				break;
			case 35:
				ruleInstance = new CheckUniqueCodeRules(ctx);
				break;
			case 40:
				ruleInstance = new CheckSameCodeAgencyRules(ctx);
				break;
			case 45:
				ruleInstance = new CheckAcctCodeNotInvoicedRules(ctx);
				break;
			case 48:
				ruleInstance = new CheckAcctCodeNotPostedRules(ctx);
				break;
			case 50:
				ruleInstance = new CheckEditStillDefaultRules(ctx);
				break;
			case 55:
				ruleInstance = new CheckFirstCodeIsDefaultRules(ctx);
				break;
			case 60:
				ruleInstance = new CheckAccountCodeAlreadyDefinedForAgencyRules(ctx);
				break;
			case 100:
				ruleInstance = new CheckFirstCodeSetResDefaultRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.INCIDENT_ACCOUNT_CODE_VO.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.INCIDENT_ACCOUNT_CODE_DAO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.INCIDENT_ACCOUNT_CODE_ENTITY.name());
			ruleInstance.setObject(incidentEntity, ObjectTypeEnum.INCIDENT_ENTITY.name());
		}
		
		return ruleInstance;
	}
	

	
}
