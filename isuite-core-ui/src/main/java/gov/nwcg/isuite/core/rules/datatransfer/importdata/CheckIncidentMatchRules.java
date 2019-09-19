package gov.nwcg.isuite.core.rules.datatransfer.importdata;

import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentFilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckIncidentMatchRules extends AbstractImportDataRule implements IRule {
	public static final String _RULE_NAME = ImportDataRuleFactory.RuleEnum.CHECK_INCIDENT_MATCH.name();

	public CheckIncidentMatchRules(ApplicationContext ctx) {
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
		IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");

		IncidentFilter filter = new IncidentFilterImpl();
		Collection<IncidentVo> vos = new ArrayList<IncidentVo>();
		
		/*
		if(null != dataTransferXml.getIncident()){
			if(StringUtility.hasValue(super.dataTransferXml.getIncident().getRossIncidentId())){
				if(!dataTransferXml.getIncident().getRossIncidentId().equals("NAN")){
					filter.setRossIncId(TypeConverter.convertToLong(dataTransferXml.getIncident().getRossIncidentId()));
					vos = incidentDao.getPossibleRossMatches(filter);
				}
			}
			
		}
		*/

		/*
		if(!CollectionUtility.hasValue(vos)){
			// try and match with other criteria
			filter.setRossIncId(null);
			if(StringUtility.hasValue(dataTransferXml.getIncident().getNbr())){
				filter.setIncidentTagNumber(dataTransferXml.getIncident().getNbr());
			}

			if(DateUtil.hasValue(super.dataTransferXml.getIncident().getIncidentStartDate())){
				Date startDate=dataTransferXml.getIncident().getIncidentStartDate();
				Integer year=DateUtil.getYearAsInteger(startDate);
				filter.setIncidentYear(year);
			}
			
			 //* Query the incidents table and find best possible matches
			vos = incidentDao.getPossibleRossMatches(filter);
			
		}
		*/
		if(CollectionUtility.hasValue(vos)){
			IncidentVo incidentVo = vos.iterator().next();
			
			// store the matching incidentId
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			coaVo.setIsComplete(true);
			coaVo.setStoredObject(incidentVo.getId());
			dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
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
