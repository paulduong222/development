package gov.nwcg.isuite.core.rules.rossimportend;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.EventType;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.RateClass;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.domain.impl.EventTypeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentShiftImpl;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.domain.impl.RateClassImpl;
import gov.nwcg.isuite.core.domain.impl.RestrictedIncidentUserImpl;
import gov.nwcg.isuite.core.irwin.IRWINUtil;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentShiftDao;
import gov.nwcg.isuite.core.persistence.PriorityProgramDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.core.vo.EventTypeVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import org.springframework.context.ApplicationContext;

public class CreateIncidentRules extends AbstractRossImportEndRule implements IRule{
	public static final String _RULE_NAME=RossImportProcessEndRuleFactory.RuleEnum.CREATE_INCIDENT.name();
	
	public CreateIncidentRules(ApplicationContext ctx)
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
		if(rossImportVo.getHasIncidentMatch()==true){
			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			Incident inc=incidentDao.getById(rossImportVo.getEisuiteIncidentId());
			Long incId=inc.getId();
			try{
				inc.setRossIncidentId(rossImportVo.getRossIncidentId());
				incidentDao.save(inc);
			}catch(Exception failsafe){
				
			}
			incidentDao.flushAndEvict(inc);
			dialogueVo.setResultObjectAlternate4(incId);
			
			return _OK;
		}
		
		Incident inc=buildIncident();
		
		dialogueVo.setResultObjectAlternate4(inc.getId());
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

	private Incident buildIncident() throws Exception {
		GlobalCacheVo gcVo = (GlobalCacheVo)context.getBean("globalCacheVo");
		
		IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
		
		Incident entity = new IncidentImpl();
		
		if(rossImportVo.getRossIncidentName().length()>50)
			entity.setIncidentName(rossImportVo.getRossIncidentName().substring(0, 49));
		else
			entity.setIncidentName(rossImportVo.getRossIncidentName());
		
		entity.setCountrySubdivision(getCountrySubdivision(rossImportVo.getRossIncidentNumber(),gcVo));
		
		entity.setHomeUnit(getHomeUnit(rossImportVo.getRossIncidentNumber(),gcVo));
		
		entity.setPdc(getPdc(entity.getHomeUnit().getId(),gcVo));

		AgencyVo agencyVo = super.rossImportVo.getIncidentAgencyVo();
		if(null != agencyVo){
			Agency ag = new AgencyImpl();
			ag.setId(agencyVo.getId());
			entity.setAgency(ag);
		}
		
		entity.setNbr(StringUtility.getTokenValue(rossImportVo.getRossIncidentNumber(),"-",3));
		
		entity.setEventType(getEventType(rossImportVo.getRossIncidentEventType(),gcVo));
		
		entity.setRossIncidentId(rossImportVo.getRossIncidentId());
		
		entity.setRossXmlFileId(rossImportVo.getRossXmlFileId());
		
		entity.setNbrOfDaysPrior(Short.valueOf("0"));
		entity.setIapPersonNameOrder(Short.valueOf("0"));
		entity.setIapResourceToDisplayFrom(Short.valueOf("0"));
		entity.setIapTreeviewDisplay(Short.valueOf("0"));
		entity.setIncidentCostDefaultHours(Integer.valueOf(14));
		entity.setCostAutoRun(StringBooleanEnum.N);
		
		entity.setIncidentStartDate((null != rossImportVo.getRossIncidentStartDate() ? rossImportVo.getRossIncidentStartDate() : null));
		if(null != entity.getIncidentStartDate()){
			entity.setIncidentYear(DateUtil.getYearAsInteger(entity.getIncidentStartDate()));
		}
		
		entity.setIncludeFilled(StringBooleanEnum.N);
		
		String rid=super.getSystemParamValue(SystemParameterTypeEnum.DEFAULT_RATE_CLASS_ID);
		Long rateClassId=1L;
		if(StringUtility.hasValue(rid)){
			try{
				rateClassId=TypeConverter.convertToLong(rid);
			}catch(Exception ee){}
		}
		RateClass rateClass = new RateClassImpl();
		rateClass.setId(rateClassId);
		entity.setRateClass(rateClass);
		
		//entity.setIncidentName(rossImportVo.getRossIncidentName());
		
		if(StringUtility.hasValue(entity.getIncidentName())){
			if(entity.getIncidentName().length()>50){
				throw new Exception("Incident Name exceeds maximum length of 50 characters.");
			}
		}
		
		// add default user to access list
		entity.setRestricted(true);
		
		RestrictedIncidentUser riu = new RestrictedIncidentUserImpl();
		riu.setUserType(RestrictedIncidentUserTypeEnum.USER);
		riu.setIncident(entity);
		riu.setUser(UserVo.toEntity(null,userVo,false));
		entity.setRestrictedIncidentUsers(new ArrayList<RestrictedIncidentUser>());
		entity.getRestrictedIncidentUsers().add(riu);
		entity.setIsSiteManaged(StringBooleanEnum.N);
		
		
		//start: find match in the IRWIN, pass in incidentVo, if found, populate the IRWIN data.
		SystemParameterDao spDao = (SystemParameterDao) super.context.getBean("systemParameterDao");
		IRWINUtil irwinUtil = new IRWINUtil(this.getRunMode(),spDao);
		IncidentVo incidentVo = new IncidentVo();
		//irwinUtil.findMatches(incidentVo, entity.getHomeUnit().getUnitCode(), entity.getIncidentNumber()); -- not working
		irwinUtil.findMatches(incidentVo, StringUtility.getTokenValue(rossImportVo.getRossIncidentNumber(),"-",1) + "-" + StringUtility.getTokenValue(rossImportVo.getRossIncidentNumber(),"-",2), StringUtility.getTokenValue(rossImportVo.getRossIncidentNumber(),"-",3));
		if (incidentVo.getIrwinIrwinId() != null) {
			if (incidentVo.getIrwinIrwinId().length() > 0) {
				entity.setIrwinIrwinId(incidentVo.getIrwinIrwinId());
				entity.setIrwinAbcdMisc(incidentVo.getIrwinAbcdMisc());
				entity.setIrwinComplexParentIrwinId(incidentVo.getIrwinComplexParentIrwinId());
				entity.setIrwinCreatedBySystem(incidentVo.getIrwinCreatedBySystem());
				entity.setIrwinCreatedOnDateTime(incidentVo.getIrwinCreatedOnDateTime());
				entity.setIrwinFireCode(incidentVo.getIrwinFireCode());
				entity.setIrwinFireDiscoveryDateTime(incidentVo.getIrwinFireDiscoveryDateTime());
				entity.setIrwinFsJobCode(incidentVo.getIrwinFsJobCode());
				entity.setIrwinFsOverrideCode(incidentVo.getIrwinFsOverrideCode());
				entity.setIrwinIncidentName(incidentVo.getIrwinIncidentName());
				entity.setIrwinIncidentTypeCategory(incidentVo.getIrwinIncidentTypeCategory());
				entity.setIrwinIncidentTypeKind(incidentVo.getIrwinIncidentTypeKind());
				entity.setIrwinIsActive(incidentVo.getIrwinIsActive());
				entity.setIrwinIsComplex(incidentVo.getIrwinIsComplex());
				entity.setIrwinLocalIncidentIdentifier(incidentVo.getIrwinLocalIncidentIdentifier());
				entity.setIrwinModifiedBySystem(incidentVo.getIrwinModifiedBySystem());
				entity.setIrwinModifiedOnDateTime(incidentVo.getIrwinModifiedOnDateTime());
				entity.setIrwinPooProtectingUnit(incidentVo.getIrwinPooProtectingUnit());
				entity.setIrwinUniqueFireIdentifier(incidentVo.getIrwinUniqueFireIdentifier());
				entity.setIrwinIsValid(incidentVo.getIrwinIsValid());
			}
		}

		//end: find match in the IRWIN 

		
		incidentDao.save(entity);
		incidentDao.flushAndEvict(entity);

		// create default shifts day/night
		IncidentShiftDao incidentShiftDao = (IncidentShiftDao)context.getBean("incidentShiftDao");
		IncidentShift incidentShiftDay = new IncidentShiftImpl();
		incidentShiftDay.setShiftName("DAY");
		incidentShiftDay.setIncident(entity);
		incidentShiftDao.save(incidentShiftDay);
		incidentShiftDao.flushAndEvict(incidentShiftDay);
		
		IncidentShift incidentShiftNight = new IncidentShiftImpl();
		incidentShiftNight.setShiftName("NIGHT");
		incidentShiftNight.setIncident(entity);
		incidentShiftDao.save(incidentShiftNight);
		incidentShiftDao.flushAndEvict(incidentShiftNight);
		
		// create default iap positions
		incidentDao.createDefaultIapSettings(entity.getId());
		
		try{
			String mode=gcVo.getSystemVo().getParameter(SystemParameterTypeEnum.RUN_MODE.name());
			if(mode.equals("SITE")){
				incidentDao.executeInsertIncidentUser(entity.getId());
	
				// add to site group
				IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				incidentGroupDao.executeInsertIncidentGroupNewIncident(entity.getId());
				
				// sync priority programs
				PriorityProgramDao ppDao = (PriorityProgramDao)context.getBean("priorityProgramDao");
				Collection<PriorityProgramVo> vos =ppDao.getGrid(null, 1L);
				if(CollectionUtility.hasValue(vos)){
					for(PriorityProgramVo v : vos){
						ppDao.syncNewWithGroup(v.getCode(), 1L);
					}
				}
			}
		}catch(Exception e){
			//smother
		}
		
		return entity;
	}

	private static CountrySubdivision getCountrySubdivision(String incidentNumber, GlobalCacheVo gcVo) throws Exception{
		String state = "UNK";
		StringTokenizer st = new StringTokenizer(incidentNumber,"-");
		int i=0;
		while(st.hasMoreTokens()){
			String val = (String)st.nextToken();
			if(i<1)
				state=val;
			i++;
		}
		for(CountryCodeSubdivisionVo ccsVo : gcVo.getCountryCodeSubdivisionVos()){
			if(ccsVo.getCountrySubAbbreviation().equalsIgnoreCase(state)){
				return CountryCodeSubdivisionVo.toEntity(ccsVo, false);
			}
		}
		
		return null;
	}
	
	private static Organization getHomeUnit(String incidentNumber, GlobalCacheVo gcVo) throws Exception{

		String unitCode="";
		
		if(!StringUtility.hasValue(unitCode)){
			StringTokenizer st = new StringTokenizer(incidentNumber,"-");
			int i=0;
			while(st.hasMoreTokens()){
				String val = (String)st.nextToken();
				if(i>0)val="-"+val;
				if(i<2)
					unitCode=unitCode+val;
				i++;
			}
		}
		
		if(!StringUtility.hasValue(unitCode))
			unitCode="UNK";

		for(OrganizationVo ovo : gcVo.getOrganizationVos()){
			if(ovo.getUnitCode().equalsIgnoreCase(unitCode)){
				return OrganizationVo.toEntity(null, ovo, false);
			}
		}
		
		return null;
	}

	private static EventType getEventType(String rossEventType, GlobalCacheVo gcVo) throws Exception{

		// lookup up event type
		EventType eventType = new EventTypeImpl();
		eventType.setId(1L);
		
		if(StringUtility.hasValue(rossEventType)){
			for(EventTypeVo et : gcVo.getEventTypeVos()){
				if(et.getType().equalsIgnoreCase(rossEventType.trim())){
					eventType.setId(et.getId());
				}
			}
		}
		
		return eventType;
	}

	private static Organization getPdc(Long unitId,GlobalCacheVo gcVo) throws Exception {

		/*
		 * Check if there is only one pdc for this org
		 */
		OrganizationVo org = 
			OrganizationVo.getById(unitId, gcVo.getOrganizationVos());

		if(CollectionUtility.hasValue(org.getDispatchCenters()) && org.getDispatchCenters().size() == 1){
			Organization orgEntity = new OrganizationImpl();
			OrganizationVo org2 = org.getDispatchCenters().iterator().next();
			orgEntity.setId(org2.getId());
			return orgEntity;
		}
		
		return null;
	}


}
