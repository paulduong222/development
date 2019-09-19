package gov.nwcg.isuite.core.datatransfer.importdata;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.DataTransfer;
import gov.nwcg.isuite.core.domain.EventType;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.RateClass;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.impl.EventTypeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.domain.impl.RateClassImpl;
import gov.nwcg.isuite.core.domain.impl.RestrictedIncidentUserImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.core.vo.EventTypeVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.xml.IncidentType;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import org.springframework.context.ApplicationContext;

public class IncidentImporter {
	private ApplicationContext context=null;
	private IncidentDao incidentDao=null;
	private IncidentVo incidentVo=null;
	private UserVo userVo = null;
	
	public IncidentImporter(ApplicationContext ctx){
		this.context=ctx;
		incidentDao = (IncidentDao)context.getBean("incidentDao");
		
		UserSessionVo userSessionVo = (UserSessionVo)context.getBean("userSessionVo");
		Long id = userSessionVo.getUserId();
		userVo = new UserVo();
		userVo.setId(id);
		userVo.setUserRoleVos(userSessionVo.getUserRoleVos());
		
	}

	/*
	public DialogueVo importData(DataTransfer dataTransfer, DialogueVo dialogueVo) throws Exception {
		Long existingIncidentId=0L;

		try{
			// determine if incident match was found
			CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName("CHECK_INCIDENT_MATCH");
			if(null != coaVo && null != coaVo.getStoredObject()){
				existingIncidentId=(Long)coaVo.getStoredObject();
			}
			
			if(!LongUtility.hasValue(existingIncidentId)){
				// create the incident
				this.incidentVo = this.createIncident(dataTransfer.getIncident(), dialogueVo);
			}else{
				// match the incident
			}
			
		}catch(Exception e){
			
		}
		
		return dialogueVo;
	}
	
	private IncidentVo createIncident(IncidentType incidentType, DialogueVo dialogueVo) throws Exception {
		IncidentVo vo = null;
		
		try{
			GlobalCacheVo gcVo = (GlobalCacheVo)context.getBean("globalCacheVo");
			String runMode=gcVo.getSystemVo().getParameter(SystemParameterTypeEnum.RUN_MODE.name());
			
			Incident entity = new IncidentImpl();
			entity.setIncidentName(incidentType.getIncidentName());
			entity.setCountrySubdivision(getCountrySubdivision(incidentType.getIncidentNumber(),gcVo));
			entity.setHomeUnit(getHomeUnit(incidentType.getIncidentNumber(),gcVo));
			entity.setPdc(getPdc(entity.getHomeUnit().getId(),gcVo));
			entity.setAgency(getAgency(incidentType.getAgency().getAgencyCode(),incidentType.getAgency().isStandard(),gcVo));
			entity.setNbr(incidentType.getNbr());
			entity.setEventType(getEventType(incidentType.getEventType().getEventTypeCode(),gcVo));

			if(StringUtility.hasValue(incidentType.getRossIncidentId())
					&& !incidentType.getRossIncidentId().equalsIgnoreCase("NAN")){
				Long val=Long.valueOf(incidentType.getRossIncidentId());
				if(LongUtility.hasValue(val))
					entity.setRossIncidentId(incidentType.getRossIncidentId());
			}
			entity.setNbrOfDaysPrior(Short.valueOf(incidentType.getNbrOfDaysPrior()));
			entity.setIapPersonNameOrder(Short.valueOf(incidentType.getIapPersonNameOrder()));
			entity.setIapResourceToDisplayFrom(Short.valueOf(incidentType.getIapResourceToDisplayFrom()));
			entity.setIapTreeviewDisplay(Short.valueOf(incidentType.getIapTreeviewDisplay()));
			entity.setIncidentCostDefaultHours(Integer.valueOf(incidentType.getIncidentCostDefaultHours()));
			entity.setCostAutoRun(StringBooleanEnum.valueOf(incidentType.getCostAutoRun()));

			if(StringUtility.hasValue(incidentType.getIncidentStartDate())){
				String startDate=incidentType.getIncidentStartDate();
				Date dteStart=DateUtil.toDate(startDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
				entity.setIncidentStartDate(dteStart);
			}
			if(null != entity.getIncidentStartDate()){
				entity.setIncidentYear(DateUtil.getYearAsInteger(entity.getIncidentStartDate()));
			}
			
			entity.setIncludeFilled(StringBooleanEnum.valueOf(incidentType.getIncludeFilled()));
			
			RateClass rateClass = new RateClassImpl();
			rateClass.setId(1L);
			entity.setRateClass(rateClass);
			
			// add default user to access list
			entity.setRestricted(true);
			
			RestrictedIncidentUser riu = new RestrictedIncidentUserImpl();
			riu.setUserType(RestrictedIncidentUserTypeEnum.USER);
			riu.setIncident(entity);
			riu.setUser(UserVo.toEntity(null,userVo,false));
			entity.setRestrictedIncidentUsers(new ArrayList<RestrictedIncidentUser>());
			entity.getRestrictedIncidentUsers().add(riu);
			
			incidentDao.save(entity);
			incidentDao.flushAndEvict(entity);
			entity=incidentDao.getById(entity.getId(),IncidentImpl.class);
			vo=IncidentVo.getInstance(entity, true);
			incidentDao.flushAndEvict(entity);
			
			// if runmode==site, add incident to site group
			if(runMode.equals("SITE")){
				incidentDao.executeInsertIncidentUser(entity.getId());

				// add to site group
				IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				incidentGroupDao.executeInsertIncidentGroupNewIncident(entity.getId());
			}
			
			// create default shifts day/night

			// create default iap positions
			
			// create site restricted users
			
		}catch(Exception e){
			throw e;
		}

		return vo;
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

	private static Agency getAgency(String agencyCode,Boolean isStandard, GlobalCacheVo gcVo) throws Exception{

		for(AgencyVo vo : gcVo.getAgencyVos()){
			if(vo.getAgencyCd().equalsIgnoreCase(agencyCode)
					&& vo.getStandard()==isStandard){
				return AgencyVo.toEntity(null, vo, false);
			}
		}
		
		return null;
	}
	*/
}
