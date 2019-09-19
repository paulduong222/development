package gov.nwcg.isuite.core.rules.incidentgroup.saveqskinds;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentGroupSaveQSKindsRuleFactory {


	public enum ObjectTypeEnum {
		INCIDENT_GROUP
		,QS_KINDS_VOS
		,INCIDENT_GROUP_DAO
	}
	
	public enum RuleEnum {
		ENTITY_EXISTS(5)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, 
									ApplicationContext ctx,
									Collection<KindVo> vos,
									IncidentGroup entity,
									IncidentGroupDao dao) throws Exception {
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new EntityExistsRules(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(entity, ObjectTypeEnum.INCIDENT_GROUP.name());
			ruleInstance.setObject(vos, ObjectTypeEnum.QS_KINDS_VOS.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.INCIDENT_GROUP_DAO.name());
		}
		
		return ruleInstance;
	}
	
}
