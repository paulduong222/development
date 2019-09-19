package gov.nwcg.isuite.core.rules.incident.saveincident;

import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.core.domain.impl.IncidentShiftImpl;
import gov.nwcg.isuite.core.persistence.IncidentShiftDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CreateDefaultShiftRules extends AbstractIncidentSaveRule implements IRule{
	public static final String _RULE_NAME=SaveIncidentRuleFactory.RuleEnum.CREATE_DEFAULT_SHIFTS.name();

	private Boolean isNewIncident=false;
	
	public CreateDefaultShiftRules(ApplicationContext ctx)
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
			if(this.isNewIncident==true)
				dialogueVo.getProcessedCourseOfActionVos().add(super.buildAdditionalActionCoaVo(_RULE_NAME,true));
			else
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
		 * If saving a new incident, 
		 *  create default incident shifts (day and night)
		 */
		
		if (!LongUtility.hasValue(vo.getId())) 
			isNewIncident=true;
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa && coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
			if(null != incidentEntity){
				IncidentShiftDao incidentShiftDao = (IncidentShiftDao)context.getBean("incidentShiftDao");
				IncidentShift incidentShiftDay = new IncidentShiftImpl();
				incidentShiftDay.setShiftName("DAY");
				incidentShiftDay.setIncident(incidentEntity);
				incidentShiftDao.save(incidentShiftDay);
				incidentShiftDao.flushAndEvict(incidentShiftDay);
				
				IncidentShift incidentShiftNight = new IncidentShiftImpl();
				incidentShiftNight.setShiftName("NIGHT");
				incidentShiftNight.setIncident(incidentEntity);
				incidentShiftDao.save(incidentShiftNight);
				incidentShiftDao.flushAndEvict(incidentShiftNight);
			}
		}
	}

}
