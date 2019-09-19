package gov.nwcg.isuite.core.rules.rossimportbegin;

import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentFilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossImportVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckIncidentLockedRules extends AbstractRossImportBeginRule implements IRule{
	public static final String _RULE_NAME=RossImportProcessBeginRuleFactory.RuleEnum.CHECK_INCIDENT_LOCKED.name();
	
	public CheckIncidentLockedRules(ApplicationContext ctx)
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
		

		if(CollectionUtility.hasValue(super.reimportReqIds))
			rossImportVo.setIsReimport(true);
		else
			rossImportVo.setIsReimport(false);
		
		rossImportVo.setRossXmlFileId(rxfVo.getId());
		rossImportVo.setRossIncidentNumber(rxfVo.getIncidentNumber());
		rossImportVo.setRossIncidentName(rxfVo.getIncidentName());
		rossImportVo.setRossIncidentStartDate((null != rxfVo.getIncidentStartDate() ? rxfVo.getIncidentStartDate() : null));
		rossImportVo.setRossIncidentStartDateString((null != rxfVo.getIncidentStartDate() ? DateUtil.toDateString(rxfVo.getIncidentStartDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY) : null));
		rossImportVo.setRossIncidentId(rxfVo.getRossIncidentId());
		rossImportVo.setRossIncidentEventType(rxfVo.getIncidentEventType());
		rossImportVo.setHasIncidentMatch(false);
		
		// try and find an e-Isuite incident match
		IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
		IncidentFilter filter = new IncidentFilterImpl();
		
		/*
		 * do a look up by ross incident id
		 */
		filter.setRossIncId(TypeConverter.convertToLong(rxfVo.getRossIncidentId()));
		Collection<IncidentVo> vos = incidentDao.getPossibleRossMatches(filter);
		
		if(null == vos || vos.size() < 1){
			// do a look up by other info
			filter.setRossIncId(null);
			if(StringUtility.hasValue(rxfVo.getIncidentNumber())){
				filter.setIncidentTagNumber(rxfVo.getIncidentNumber());
			}

			if(DateUtil.hasValue(rxfVo.getIncidentStartDate())){
				Integer year=DateUtil.getYearAsInteger(rxfVo.getIncidentStartDate());
				filter.setIncidentYear(year);
			}
			
			/*
			 * Query the incidents table and find best possible matches
			 */
			vos = incidentDao.getPossibleRossMatches(filter);
			
		}
		
		if(CollectionUtility.hasValue(vos)){
			IncidentVo incidentVo = vos.iterator().next();
			if(BooleanUtility.isTrue(incidentVo.getIsSiteManaged())){
				if(super.getRunMode().equals("ENTERPRISE")){
					String msg = "This Incident is Site Managed therefore you may not import resources into this Incident.";
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.rossImport"
												  ,"validationerror"
												  ,"error.800000"
												  , new String[]{msg}));	
					return _FAIL;
				}
			}
		}

		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
