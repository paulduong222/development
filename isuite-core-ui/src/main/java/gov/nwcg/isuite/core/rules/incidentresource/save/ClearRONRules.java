package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class ClearRONRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="CLEAR_RON";

	public ClearRONRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try {

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
			
		} catch(Exception e){
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
		 * Defect 3545 - Per Scenario Testing Sept 2013
		 * When Rest overnight is unchecked, clear out all rest overnight locations.
		 */

		/*
		 * Check if user is saving an existing record
		 */
		if(LongUtility.hasValue(vo.getId())){

			if(BooleanUtility.isFalse(vo.getWorkPeriodVo().getDmRestOvernight())){
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
				dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
			}
			
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		/*
		 * Determine if we need to remove any time postings?
		 */
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		try{
			if(null != coa && coa.getCoaType() == CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
				IncidentResourceDao irdao = (IncidentResourceDao)context.getBean("incidentResourceDao");
				IncidentResource irent=irdao.getById(irEntity.getId(), IncidentResourceImpl.class);
				irent.getWorkPeriod().getWpOvernightStayInfos().clear();
				
				irdao.save(irent);
				irdao.flushAndEvict(irent.getWorkPeriod());
				irdao.flushAndEvict(irent);
			}
		}catch(Exception e){
			throw e;
		}
		
	}

}
