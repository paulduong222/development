package gov.nwcg.isuite.core.rules.workarea.save;

import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.persistence.WorkAreaDao;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class WorkAreaSaveRuleFactory {

	public enum ObjectTypeEnum {
		WORK_AREA_VO
		,WORK_AREA_ENTITY
		,WORK_AREA_DAO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_UNIQUE_NAME(5)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, WorkAreaVo vo, WorkArea entity, WorkAreaDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckUniqueNameRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.WORK_AREA_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.WORK_AREA_ENTITY.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.WORK_AREA_DAO.name());
		}
		
		return ruleInstance;
	}
	

	
}
