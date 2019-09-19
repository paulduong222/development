package gov.nwcg.isuite.core.rules.rossimport.finalimport;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.EventType;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.RateClass;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
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
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.RossImportProcessDataErrorVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.RossXmlFileVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import org.springframework.context.ApplicationContext;

public class CreateRossIncident {

	/**
	 * @param ctx
	 * @param rxfVo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	public static Incident createIncident(ApplicationContext ctx, 
											RossXmlFileVo rxfVo,
											String unitCodeResolution,
											String agencyCodeResolution,
											String eventTypeResolution,
											UserVo userVo,
											DialogueVo dialogueVo) throws Exception {

		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		
		GlobalCacheVo gcVo = (GlobalCacheVo)ctx.getBean("globalCacheVo");
		
		IncidentDao incidentDao = (IncidentDao)ctx.getBean("incidentDao");
		
		Incident entity = new IncidentImpl();
		
		if(rxfVo.getIncidentName().length()>20)
			entity.setIncidentName(rxfVo.getIncidentName().substring(0, 19));
		else
			entity.setIncidentName(rxfVo.getIncidentName());
		
		entity.setCountrySubdivision(getCountrySubdivision(rxfVo,gcVo));
		
		entity.setHomeUnit(getHomeUnit(rxfVo,gcVo,unitCodeResolution));
		
		entity.setPdc(getPdc(entity.getHomeUnit().getId(),gcVo,ripVo.getDataConflictWizardVo().getRossImportProcessDataErrorVos()));

		if(null != dialogueVo.getResultObjectAlternate4()){
			AgencyVo agencyVo = (AgencyVo)dialogueVo.getResultObjectAlternate4();
			Agency ag = new AgencyImpl();
			ag.setId(agencyVo.getId());
			entity.setAgency(ag);
		}else
			entity.setAgency(getAgency(rxfVo,gcVo,agencyCodeResolution));
		
		entity.setNbr(StringUtility.getTokenValue(rxfVo.getIncidentNumber(),"-",3));
		
		entity.setEventType(getEventType(rxfVo,gcVo,eventTypeResolution));
		
		entity.setRossIncidentId(rxfVo.getRossIncidentId());
		
		entity.setRossXmlFileId(rxfVo.getId());
		
		entity.setNbrOfDaysPrior(Short.valueOf("0"));
		entity.setIapPersonNameOrder(Short.valueOf("0"));
		entity.setIapResourceToDisplayFrom(Short.valueOf("0"));
		entity.setIapTreeviewDisplay(Short.valueOf("0"));
		entity.setIncidentCostDefaultHours(Integer.valueOf(14));
		entity.setCostAutoRun(StringBooleanEnum.N);
		
		entity.setIncidentStartDate((null != rxfVo.getIncidentStartDate() ? rxfVo.getIncidentStartDate() : null));
		if(null != entity.getIncidentStartDate()){
			entity.setIncidentYear(DateUtil.getYearAsInteger(entity.getIncidentStartDate()));
		}
		
		entity.setIncludeFilled(StringBooleanEnum.N);
		
		RateClass rateClass = new RateClassImpl();
		rateClass.setId(1L);
		entity.setRateClass(rateClass);
		
		/*
		 * Determine how many incidents already have the same nbr + name
		 */
		Collection<Incident> matches = incidentDao.getByIncidentKey(entity.getNbr(), entity.getIncidentName());
		if(null != matches && matches.size() > 0){
			entity.setIncidentName(entity.getIncidentName() + " ("+(matches.size()+1)+")");
		}
		
		if(StringUtility.hasValue(entity.getIncidentName())){
			if(entity.getIncidentName().length()>20){
				throw new Exception("Incident Name exceeds maximum length of 20 characters.");
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
		
		incidentDao.save(entity);

		try{
			String mode=gcVo.getSystemVo().getParameter(SystemParameterTypeEnum.RUN_MODE.name());
			if(mode.equals("SITE")){
				incidentDao.executeInsertIncidentUser(entity.getId());
	
				// add to site group
				IncidentGroupDao incidentGroupDao = (IncidentGroupDao)ctx.getBean("incidentGroupDao");
				incidentGroupDao.executeInsertIncidentGroupNewIncident(entity.getId());
			}
		}catch(Exception e){
			//smother
		}
		
		return entity;
	}
	
	private static CountrySubdivision getCountrySubdivision(RossXmlFileVo rxfVo, GlobalCacheVo gcVo) throws Exception{
		String state = "";
		StringTokenizer st = new StringTokenizer(rxfVo.getIncidentNumber(),"-");
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
	
	private static Organization getHomeUnit(RossXmlFileVo rxfVo, GlobalCacheVo gcVo,String unitCodeResolution) throws Exception{

		String unitCode="";
		if(StringUtility.hasValue(unitCodeResolution)){
			unitCode=unitCodeResolution;
		}
		
		if(!StringUtility.hasValue(unitCode)){
			StringTokenizer st = new StringTokenizer(rxfVo.getIncidentNumber(),"-");
			int i=0;
			while(st.hasMoreTokens()){
				String val = (String)st.nextToken();
				if(i>0)val="-"+val;
				if(i<2)
					unitCode=unitCode+val;
				i++;
			}
		}
		for(OrganizationVo ovo : gcVo.getOrganizationVos()){
			if(ovo.getUnitCode().equalsIgnoreCase(unitCode)){
				return OrganizationVo.toEntity(null, ovo, false);
			}
		}
		
		return null;
	}

	private static Agency getAgency(RossXmlFileVo rxfVo, GlobalCacheVo gcVo,String agencyCodeResolution) throws Exception{
		String agency="";
		if(StringUtility.hasValue(agencyCodeResolution)){
			agency=agencyCodeResolution;
		}
		
		if(!StringUtility.hasValue(agency)){
			StringTokenizer st = new StringTokenizer(rxfVo.getIncidentNumber(),"-");
			int i=0;
			while(st.hasMoreTokens()){
				String val = (String)st.nextToken();
				if(i<1)
					agency=val;
				i++;
			}
		}
		for(AgencyVo avo : gcVo.getAgencyVos()){
			if(avo.getAgencyCd().equalsIgnoreCase(agency)){
				return AgencyVo.toEntity(null, avo, false);
			}
		}
		
		return null;
	}

	private static EventType getEventType(RossXmlFileVo rxfVo, GlobalCacheVo gcVo,String eventTypeResolution) throws Exception{

		// lookup up event type
		EventType eventType = new EventTypeImpl();
		eventType.setId(1L);
		
		if(StringUtility.hasValue(rxfVo.getIncidentEventType())){
			for(EventTypeVo et : gcVo.getEventTypeVos()){
				if(et.getType().equalsIgnoreCase(rxfVo.getIncidentEventType().trim())){
					eventType.setId(et.getId());
				}
			}
		}
		
		return eventType;
	}

	private static Organization getPdc(Long unitId,GlobalCacheVo gcVo, Collection<RossImportProcessDataErrorVo> vos) throws Exception {

		for(RossImportProcessDataErrorVo evo : vos){
			if(evo.getConflictCode().equals("UNKNOWN_INCIDENT_PDC")){
				OrganizationVo newValue = (OrganizationVo)evo.getNewValue();
				OrganizationVo org = 
					OrganizationVo.getById(newValue.getId(), gcVo.getOrganizationVos());

				if(null != org){
					Organization orgEntity = new OrganizationImpl();
					orgEntity.setId(org.getId());
					return orgEntity;
				}
			}
		}

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
