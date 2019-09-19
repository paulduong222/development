package gov.nwcg.isuite.core.rules.incident.saveincident;

import gov.nwcg.isuite.core.persistence.PriorityProgramDao;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class SyncPriorityProgramsWithGroupRules extends AbstractIncidentSaveRule implements IRule{
	public static final String _RULE_NAME=SaveIncidentRuleFactory.RuleEnum.SYNC_PRIORITY_PROGRAMS_WITH_GROUP.name();

	public SyncPriorityProgramsWithGroupRules(ApplicationContext ctx)
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * If saving a new incident and in site mode 
		 *  sync priority programs with group
		 */
		if (!LongUtility.hasValue(vo.getId()) && super.getRunMode().equalsIgnoreCase("SITE")) 
			dialogueVo.getProcessedCourseOfActionVos().add(super.buildAdditionalActionCoaVo(_RULE_NAME,true));
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa && coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
			if(null != incidentEntity){
				PriorityProgramDao ppDao = (PriorityProgramDao)context.getBean("priorityProgramDao");
				Collection<PriorityProgramVo> vos =ppDao.getGrid(null, 1L);
				if(CollectionUtility.hasValue(vos)){
					for(PriorityProgramVo v : vos){
						ppDao.syncNewWithGroup(v.getCode(), 1L);
					}
				}
			}
		}
	}

}
