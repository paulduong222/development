package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentPrefs;
import gov.nwcg.isuite.core.domain.IncidentQSKind;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.IncidentPrefsSectionNameEnum;
import gov.nwcg.isuite.framework.types.PersonsNameOrderEnum;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.types.ResourcesToDisplayFromEnum;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;
import gov.nwcg.isuite.framework.types.ShortUtil;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.TreeviewDisplayEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 * Values representing an Incident.
 */
public class IncidentVo extends AbstractVo implements PersistableVo {
	private String incidentName;
	private String incidentNumber;
	private String incidentDescription;
	private CountryCodeSubdivisionVo countryCodeSubdivisionVo;
	private OrganizationVo homeUnitVo;
	private OrganizationVo pdcVo = new OrganizationVo();
	private AgencyVo agencyVo;
	private String location;
	private String latitude;
	private String longitude;
//	private Date incidentStartDate;
//	private Date incidentEndDate;
	private DateTransferVo incStartDateTransferVo=new DateTransferVo();;
	private DateTransferVo incEndDateTransferVo=new DateTransferVo();;
	private Integer incidentYear;
	private EventTypeVo eventTypeVo;
	private Boolean restricted;
	private Collection<IncidentAccountCodeVo> incidentAccountCodeVos;
	private Collection<IncidentShiftVo> incidentShiftVos = new ArrayList<IncidentShiftVo>();
	private Collection<RestrictedIncidentUserVo> restrictedIncidentUserVos;
	private Collection<RestrictedIncidentUserVo> restrictedOwners;
	private Collection<RestrictedIncidentUserVo> restrictedUsers;
	private IncidentPrefsOtherFieldsVo incidentPrefsOtherFieldsVo = new IncidentPrefsOtherFieldsVo();
	private RateClassVo rateClassVo;
	private Collection<IncidentQuestionVo> airTravelQuestions = new ArrayList<IncidentQuestionVo>();
	private Collection<IncidentQuestionVo> checkInQuestions = new ArrayList<IncidentQuestionVo>();
	private Collection<IncidentQSKindVo> incidentQSKindVos = new ArrayList<IncidentQSKindVo>();
	private String rossIncidentId;
	private String rossIncId;
	private Long rossXmlFileId;
	private Boolean isSiteManaged=false;
	private IncidentCostDefaultsVo incidentCostDefaultsVo = new IncidentCostDefaultsVo();
	private Collection<IncidentPrefsVo> incidentPrefsVos = new ArrayList<IncidentPrefsVo>();
	
	private Collection<IncidentPrefsVo> logistictsPrefsVos = new ArrayList<IncidentPrefsVo>();
	private Collection<IncidentPrefsVo> planningPrefsVos = new ArrayList<IncidentPrefsVo>();
	private Collection<IncidentPrefsVo> financePrefsVos = new ArrayList<IncidentPrefsVo>();
	private Collection<IncidentPrefsVo> otherPrefsVos = new ArrayList<IncidentPrefsVo>();
	private Collection<IncidentPrefsVo> iap204PrefsVos = new ArrayList<IncidentPrefsVo>();
	
	private Collection<CostGroupVo> costGroupVos = new ArrayList<CostGroupVo>();

	private Date userDefaultCheckinDate;
    private String userDefaultCheckinType;
	
	private Long incidentGroupId;
	
	private String iapPersonNameOrder;
	private Boolean includeFilled;
	private String iapResourceToDisplayFrom;
	private String iapTreeviewDisplay;
	private Date byDate;
	private Short nbrOfDaysPrior;
	private String nbrOfDaysPriorString;

	private String irwinIrwinId;
	private String irwinIncidentName;
	private String irwinFireDiscoveryDateTime;
	private String irwinPooResponsibleUnit; 
	private String irwinLocalIncidentIdentifier; 
	private String irwinIncidentTypeKind;
	private String irwinIncidentTypeCategory; 
	private String irwinFireCode; 
	private String irwinFsJobCode;
	private String irwinFsOverrideCode;
	private String irwinIsActive;
	private String irwinRecordSource; 
	private String irwinCreatedBySystem; 
	private String irwinCreatedOnDateTime; 
	private String irwinModifiedBySystem; 
	private String irwinModifiedOnDateTime; 
	private String irwinPooProtectingUnit;
	private String irwinIsComplex;
	private String irwinComplexParentIrwinId;
	private String irwinUniqueFireIdentifier;
	private String irwinAbcdMisc;
	private String irwinStatus;
	private String irwinIsValid;
			
	// iap lists
	private Collection<IapMasterFrequencyVo> iapMasterFrequencyVos = new ArrayList<IapMasterFrequencyVo>();
	private Collection<IapPositionItemCodeVo> iapPositionItemCodeVos = new ArrayList<IapPositionItemCodeVo>();
	private Collection<BranchSettingVo> branchSettingVos = new ArrayList<BranchSettingVo>();
	
	private Collection<AgencyVo> agencyVos = new ArrayList<AgencyVo>();
	private Collection<OrganizationVo> organizationVos = new ArrayList<OrganizationVo>();
	private Collection<JetPortVo> jetPortVos = new ArrayList<JetPortVo>();
	private Collection<KindVo> kindVos = new ArrayList<KindVo>();
	
	private Integer incidentCostDefaultHours;
	private Boolean costAutoRun = false;  
	
	public IncidentVo() {
	}

	/**
	 * Returns a IncidentVo instance from a Incident entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of IncidentVo
	 * @throws Exception
	 */
	public static IncidentVo getInstance(Incident entity,boolean cascadable) throws Exception {
		IncidentVo vo = new IncidentVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentVo from null Incident entity.");

		vo.setId(entity.getId());
 
		// 10/27/2015 adding check on incidentname for propagate incident issue
		if(cascadable && StringUtility.hasValue(entity.getIncidentName())){
		//if(cascadable){
			vo.setRossIncidentId(entity.getRossIncidentId());
			if(StringUtility.hasValue(entity.getRossIncidentId()))
				vo.setRossIncId(entity.getRossIncidentId());
			else
				vo.setRossIncId("");
			vo.setIncidentName(entity.getIncidentName());
			vo.setIncidentDescription(entity.getIncidentDescription());
			vo.setIncidentNumber(entity.getNbr());
			vo.setLocation(entity.getLocation());
			vo.setLatitude(entity.getLatitude());
			vo.setLongitude(entity.getLongitude());
			// todo pass start date as string
			//vo.setIncidentStartDate(entity.getIncidentStartDate());
			if(DateUtil.hasValue(entity.getIncidentStartDate()))
				DateTransferVo.populateDate(vo.getIncStartDateTransferVo(), entity.getIncidentStartDate());
			
			vo.setRestricted(entity.getRestricted());
			vo.setIncidentYear(entity.getIncidentYear());
			// todo: pass end date as string
			//vo.setIncidentEndDate(entity.getIncidentEndDate());
			if(DateUtil.hasValue(entity.getIncidentEndDate()))
				DateTransferVo.populateDate(vo.getIncEndDateTransferVo(), entity.getIncidentEndDate());
			vo.setRossXmlFileId(entity.getRossXmlFileId());
			vo.setIsSiteManaged(entity.getIsSiteManaged().getValue());
			vo.setIrwinIrwinId(entity.getIrwinIrwinId());
			vo.setIrwinIncidentName(entity.getIrwinIncidentName());			
			vo.setIrwinFireDiscoveryDateTime(entity.getIrwinFireDiscoveryDateTime());
			vo.setIrwinPooResponsibleUnit(entity.getIrwinPooResponsibleUnit()); 
			vo.setIrwinLocalIncidentIdentifier(entity.getIrwinLocalIncidentIdentifier()); 
			vo.setIrwinIncidentTypeKind(entity.getIrwinIncidentTypeKind());
			vo.setIrwinIncidentTypeCategory(entity.getIrwinIncidentTypeCategory()); 
			vo.setIrwinFireCode(entity.getIrwinFireCode()); 
			vo.setIrwinFsJobCode(entity.getIrwinFsJobCode());
			vo.setIrwinFsOverrideCode(entity.getIrwinFsOverrideCode());
			vo.setIrwinIsActive(entity.getIrwinIsActive());
			vo.setIrwinRecordSource(entity.getIrwinRecordSource()); 
			vo.setIrwinCreatedBySystem(entity.getIrwinCreatedBySystem()); 
			vo.setIrwinCreatedOnDateTime(entity.getIrwinCreatedOnDateTime()); 
			vo.setIrwinModifiedBySystem(entity.getIrwinModifiedBySystem()); 
			vo.setIrwinModifiedOnDateTime(entity.getIrwinModifiedOnDateTime()); 
			vo.setIrwinPooProtectingUnit(entity.getIrwinPooProtectingUnit()); 
			vo.setIrwinComplexParentIrwinId(entity.getIrwinComplexParentIrwinId());
			vo.setIrwinAbcdMisc(entity.getIrwinAbcdMisc());
			vo.setIrwinIsComplex(entity.getIrwinIsComplex());
			vo.setIrwinUniqueFireIdentifier(entity.getIrwinUniqueFireIdentifier());
			vo.setIrwinStatus(entity.getIrwinStatus());
			vo.setIrwinIsValid(entity.getIrwinIsValid());
			
			if(null != entity.getAgencyId()){
				vo.setAgencyVo(AgencyVo.getInstance(entity.getAgency(), true));
			}

			if(null != entity.getCountrySubdivision()){
				vo.setCountryCodeSubdivisionVo(CountryCodeSubdivisionVo.getInstance(entity.getCountrySubdivision(),true));
			}

			if(null != entity.getEventType()){
				vo.setEventTypeVo(EventTypeVo.getInstance(entity.getEventType(),true));
			}

			if(null != entity.getHomeUnit()){
				vo.setHomeUnitVo(OrganizationVo.getInstance(entity.getHomeUnit(), true));
			}

			if(null != entity.getPdc()) {
				vo.setPdcVo(OrganizationVo.getInstance(entity.getPdc(), true));
			}
			
			if(null != entity.getRestrictedIncidentUsers()){
				vo.setRestrictedIncidentUserVos(RestrictedIncidentUserVo.getInstances(entity.getRestrictedIncidentUsers(), true));
				vo.setRestrictedOwners(new ArrayList<RestrictedIncidentUserVo>());
				vo.setRestrictedUsers(new ArrayList<RestrictedIncidentUserVo>());
				for(RestrictedIncidentUserVo riuVo : vo.getRestrictedIncidentUserVos()){
					if(riuVo.getUserType().equals(RestrictedIncidentUserTypeEnum.OWNER)){
						vo.getRestrictedOwners().add(riuVo);
					}else{
						vo.getRestrictedUsers().add(riuVo);
					}
				}
			}
			
			if(null != entity.getIncidentAccountCodes()){
				vo.setIncidentAccountCodeVos(IncidentAccountCodeVo.getInstances(entity.getIncidentAccountCodes(),true));
			}
			
			if(null != entity.getIncidentPrefsOtherFields()){
				vo.setIncidentPrefsOtherFieldsVo(IncidentPrefsOtherFieldsVo.getInstance(entity.getIncidentPrefsOtherFields(), true));
			}
			
			if(null != entity.getIncidentPrefs()) {
				for(IncidentPrefs ip : entity.getIncidentPrefs()) {
					if(ip.getSectionName().equals(IncidentPrefsSectionNameEnum.LOGISTICS)) {
						vo.getLogistictsPrefsVos().add(IncidentPrefsVo.getInstance(ip, true));    
					}
					if(ip.getSectionName().equals(IncidentPrefsSectionNameEnum.PLANNING)) {
						vo.getPlanningPrefsVos().add(IncidentPrefsVo.getInstance(ip, true));
					}
					if(ip.getSectionName().equals(IncidentPrefsSectionNameEnum.FINANCE)) {
						vo.getFinancePrefsVos().add(IncidentPrefsVo.getInstance(ip, true));
					}
					if(ip.getSectionName().equals(IncidentPrefsSectionNameEnum.OTHER_LABEL)) {
						vo.getOtherPrefsVos().add(IncidentPrefsVo.getInstance(ip, true));
					}
					if(ip.getSectionName().name().startsWith("IAP") ){
						if(ip.getFieldLabel().equalsIgnoreCase("LAST DAY TO WORK")){
							// skip last day to work field, no longer applicable
							// need to hardcode skipping until we remove from incident trigger
						}else{
							vo.getIap204PrefsVos().add(IncidentPrefsVo.getInstance(ip,true));
						}
					}
				}
			}
			
			if(CollectionUtility.hasValue(entity.getCostGroups())){
				vo.setCostGroupVos(CostGroupVo.getInstances(entity.getCostGroups(), true));
			}
			
			if(null != entity.getRateClass()){
				vo.setRateClassVo(RateClassVo.getInstance(entity.getRateClass(), true));
			}
			
			if(null != entity.getIncidentShifts()) {
				vo.setIncidentShiftVos(IncidentShiftVo.getInstances(entity.getIncidentShifts(), true));
			}
			
			if(null != entity.getIncidentQSKinds()){
				Collection<IncidentQSKindVo> qsKindVos = new ArrayList<IncidentQSKindVo>();
				
				for(IncidentQSKind qsKind : entity.getIncidentQSKinds()){
					IncidentQSKindVo qsKindVo = new IncidentQSKindVo();
					
					qsKindVo.setId(qsKind.getId());
					
					qsKindVo.setIncidentId(qsKind.getIncidentId());
					
					KindVo kindVo = new KindVo();
					Kind kind = qsKind.getKind();
					kindVo.setId(kind.getId());
					kindVo.setCode(kind.getCode());
					kindVo.setDescription(kind.getDescription());
					kindVo.setStandard(kind.isStandard());
					kindVo.setActive(StringBooleanEnum.toBooleanValue(kind.isActive()));
					
					qsKindVo.setKindVo(kindVo);
					
					qsKindVos.add(qsKindVo);
				}
				
				vo.setIncidentQSKindVos(qsKindVos);
			}
			
			if(null != entity.getIncidentQuestions()){
				for(IncidentQuestion iq : entity.getIncidentQuestions()){
					if(iq.getQuestion().getQuestionType()==QuestionTypeEnum.AIRTRAVEL)
						vo.getAirTravelQuestions().add(IncidentQuestionVo.getInstance(iq, true));
					if(iq.getQuestion().getQuestionType()==QuestionTypeEnum.PREPLANNING)
						vo.getCheckInQuestions().add(IncidentQuestionVo.getInstance(iq, true));
				}
			}
		
			if(null != entity.getIncidentCostDefaults()){
				vo.setIncidentCostDefaultsVo(IncidentCostDefaultsVo.getInstance(entity.getIncidentCostDefaults(), true));
			}
			
			if(CollectionUtility.hasValue(entity.getIncidentGroups())){
				IncidentGroup igEntity = entity.getIncidentGroups().iterator().next();
				vo.setIncidentGroupId(igEntity.getId());
			}
			
			if(ShortUtil.hasValue(entity.getIapPersonNameOrder()))
				vo.setIapPersonNameOrder(PersonsNameOrderEnum.toStringValue(entity.getIapPersonNameOrder()));
			else // For a newly created incident without a default PersonNameOrder value set
				vo.setIapPersonNameOrder(PersonsNameOrderEnum.toStringValue(Short.valueOf("0")));	
			
			if(null != entity.getIncludeFilled())
				vo.setIncludeFilled(entity.getIncludeFilled().getValue());
			else
				vo.setIncludeFilled(false);
			
			vo.setIapResourceToDisplayFrom(ResourcesToDisplayFromEnum.toStringValue(entity.getIapResourceToDisplayFrom()));
			vo.setIapTreeviewDisplay(TreeviewDisplayEnum.toStringValue(entity.getIapTreeviewDisplay()));
			vo.setByDate(entity.getByDate());
			vo.setNbrOfDaysPrior(entity.getNbrOfDaysPrior());
			if(ShortUtil.hasValue(vo.getNbrOfDaysPrior()))
				vo.setNbrOfDaysPriorString(String.valueOf(vo.getNbrOfDaysPrior()));
			else
				vo.setNbrOfDaysPriorString("0");
			
			vo.setIncidentCostDefaultHours(entity.getIncidentCostDefaultHours());
			if(null != entity.getCostAutoRun())
				vo.setCostAutoRun(entity.getCostAutoRun().getValue());
			else
				vo.setCostAutoRun(false);
			
//			if(null != entity.getIapMasterFrequencies()) {
//				vo.setIapMasterFrequencyVos(IapMasterFrequencyVo.getInstances(entity.getIapMasterFrequencies(), true));
//			}

			if(CollectionUtility.hasValue(entity.getIapPositionItemCodes())){
				vo.setIapPositionItemCodeVos(IapPositionItemCodeVo.getInstances(entity.getIapPositionItemCodes(), true));
			}

			if(CollectionUtility.hasValue(entity.getBranchSettings())){
				vo.setBranchSettingVos(BranchSettingVo.getInstances(entity.getBranchSettings(), true));
			}
			
			if(null != entity.getJetPorts()) {
				Collection<JetPortVo> jetPortVos = new ArrayList<JetPortVo>();
				
				for(JetPort jp : entity.getJetPorts()) {
					if (StringBooleanEnum.toBooleanValue(jp.isActive())) {
						JetPortVo jpvo = new JetPortVo();
						jpvo.setId(jp.getId());
						jpvo.setCode(jp.getCode());
						jpvo.setDescription(jp.getDescription());
						jpvo.setStandard(jp.isStandard());
						jpvo.setActive(StringBooleanEnum.toBooleanValue(jp.isActive()));
						jetPortVos.add(jpvo);
					}
				}
				vo.setJetPortVos(jetPortVos);
			}
			
			if(null != entity.getOrganizations()) {
				Collection<OrganizationVo> organizationVos = new ArrayList<OrganizationVo>();
				
				for(Organization o : entity.getOrganizations()) {
					if (StringBooleanEnum.toBooleanValue(o.isActive())) {
						OrganizationVo ov = new OrganizationVo();
						ov.setId(o.getId());
						ov.setUnitCode(o.getUnitCode());
						ov.setName(o.getName());
						ov.setStandard(o.isStandard());
						ov.setActive(StringBooleanEnum.toBooleanValue(o.isActive()));
						organizationVos.add(ov);
					}
				}
				vo.setOrganizationVos(organizationVos);
			}
			
			if(null != entity.getKinds()) {
				Collection<KindVo> kindVos = new ArrayList<KindVo>();
				
				for(Kind k : entity.getKinds()) {
					if (StringBooleanEnum.toBooleanValue(k.isActive())) {
						KindVo kvo = new KindVo();
						kvo.setId(k.getId());
						kvo.setCode(k.getCode());
						kvo.setDescription(k.getDescription());
						kvo.setStandard(k.isStandard());
						kvo.setActive(StringBooleanEnum.toBooleanValue(k.isActive()));
						kindVos.add(kvo);
					}
				}
				vo.setKindVos(kindVos);
			}
			
			if(null != entity.getAgencies()) {
				Collection<AgencyVo> agencyVos = new ArrayList<AgencyVo>();
				
				for(Agency a : entity.getAgencies()) {
					if (StringBooleanEnum.toBooleanValue(a.isActive())) {
						AgencyVo avo = new AgencyVo();
						avo.setId(a.getId());
						avo.setAgencyCd(a.getAgencyCode());
						avo.setAgencyNm(a.getAgencyName());
						avo.setStandard(a.isStandard());
						avo.setActive(StringBooleanEnum.toBooleanValue(a.isActive()));
						agencyVos.add(avo);
					}
				}
				vo.setAgencyVos(agencyVos);
			}
		}

		return vo;
	}

	public static Collection<IncidentVo> getInstances(Collection<Incident> entities, boolean cascadable) throws Exception {
		Collection<IncidentVo> vos = new ArrayList<IncidentVo>();

		for(Incident entity : entities){
			vos.add(IncidentVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	/**
	 * Returns a Incident entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of Incident entity
	 * @throws Exception
	 */
	public static Incident toEntity(Incident entity, IncidentVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity)
			entity=new IncidentImpl();

		entity.setId(vo.getId());

		if(cascadable){
			if(StringUtility.hasValue(vo.getRossIncidentId())){
				int idx=vo.getRossIncidentId().indexOf(".");
				if(idx>0){
					String str=vo.getRossIncidentId().substring(0, idx);
					entity.setRossIncidentId(str);
				}else
					entity.setRossIncidentId(vo.getRossIncidentId());
			}
			entity.setIncidentDescription((null != vo.getIncidentDescription() ? vo.getIncidentDescription().toUpperCase() : ""));
			entity.setIncidentName(vo.getIncidentName().toUpperCase());
			
			if(DateTransferVo.hasDateString(vo.getIncStartDateTransferVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getIncStartDateTransferVo());
				entity.setIncidentStartDate(dt);
			}
			if(DateTransferVo.hasDateString(vo.getIncEndDateTransferVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getIncEndDateTransferVo());
				entity.setIncidentEndDate(dt);
			} else {
				entity.setIncidentEndDate(null);
			}

			entity.setNbr(vo.getIncidentNumber().toUpperCase());
			entity.setLatitude((null != vo.getLatitude() ? vo.getLatitude().toUpperCase() : ""));
			entity.setLongitude((null != vo.getLongitude() ? vo.getLongitude().toUpperCase() : ""));
			entity.setLocation((null != vo.getLocation() ? vo.getLocation().toUpperCase() : ""));
			entity.setRestricted(true); // all incs are now restricted
			entity.setRossXmlFileId(vo.getRossXmlFileId());
			entity.setIncidentCostDefaultHours(vo.getIncidentCostDefaultHours());
			entity.setCostAutoRun(vo.getCostAutoRun()== true ? StringBooleanEnum.Y : StringBooleanEnum.N);
			entity.setIsSiteManaged(StringBooleanEnum.toEnumValue(vo.getIsSiteManaged()));

			entity.setIrwinIrwinId(vo.getIrwinIrwinId());
			entity.setIrwinIncidentName(vo.getIrwinIncidentName());	
			entity.setIrwinFireDiscoveryDateTime(vo.getIrwinFireDiscoveryDateTime());
			entity.setIrwinPooResponsibleUnit(vo.getIrwinPooResponsibleUnit()); 
			entity.setIrwinLocalIncidentIdentifier(vo.getIrwinLocalIncidentIdentifier()); 
			entity.setIrwinIncidentTypeKind(vo.getIrwinIncidentTypeKind());
			entity.setIrwinIncidentTypeCategory(vo.getIrwinIncidentTypeCategory()); 
			entity.setIrwinFireCode(vo.getIrwinFireCode()); 
			entity.setIrwinFsJobCode(vo.getIrwinFsJobCode());
			entity.setIrwinFsOverrideCode(vo.getIrwinFsOverrideCode());
			entity.setIrwinIsActive(vo.getIrwinIsActive());
			entity.setIrwinRecordSource(vo.getIrwinRecordSource()); 
			entity.setIrwinCreatedBySystem(vo.getIrwinCreatedBySystem()); 
			entity.setIrwinCreatedOnDateTime(vo.getIrwinCreatedOnDateTime()); 
			entity.setIrwinModifiedBySystem(vo.getIrwinModifiedBySystem()); 
			entity.setIrwinModifiedOnDateTime(vo.getIrwinModifiedOnDateTime()); 
			entity.setIrwinPooProtectingUnit(vo.getIrwinPooProtectingUnit()); 
			entity.setIrwinComplexParentIrwinId(vo.getIrwinComplexParentIrwinId());
			entity.setIrwinAbcdMisc(vo.getIrwinAbcdMisc());
			entity.setIrwinIsComplex(vo.getIrwinIsComplex());
			entity.setIrwinUniqueFireIdentifier(vo.getIrwinUniqueFireIdentifier());
			//entity.setIrwinStatus(vo.getIrwinStatus() != null ? vo.getIrwinStatus() : "SYNC");
			entity.setIrwinStatus("SYNC");
			entity.setIrwinIsValid(vo.getIrwinIsValid());

			Date startDate=null;
			if(DateTransferVo.hasDateString(vo.getIncStartDateTransferVo())){
				startDate=DateTransferVo.getTransferDate(vo.getIncStartDateTransferVo());
			}
			if(null != startDate){
		      entity.setIncidentYear(DateUtil.getYearAsInteger(startDate));
			}
			
			if(null != vo.getAgencyVo()){
				entity.setAgency(AgencyVo.toEntity(null, vo.getAgencyVo(), false));
			}

			if(null != vo.getHomeUnitVo()){
				entity.setHomeUnit(OrganizationVo.toEntity(null,vo.getHomeUnitVo(), false));
			}

			if(null != vo.getPdcVo() && LongUtility.hasValue(vo.getPdcVo().getId())) {
				entity.setPdc(OrganizationVo.toEntity(null, vo.getPdcVo(), false));
			}
			
			if(null != vo.getCountryCodeSubdivisionVo() && LongUtility.hasValue(vo.getCountryCodeSubdivisionVo().getId())){
				entity.setCountrySubdivision(CountryCodeSubdivisionVo.toEntity(vo.getCountryCodeSubdivisionVo(),false));
			}

			if(null != vo.getEventTypeVo()){
				entity.setEventType(EventTypeVo.toEntity(vo.getEventTypeVo(),false));
			}

			if(null != vo.getIncidentAccountCodeVos()){
				entity.setIncidentAccountCodes(IncidentAccountCodeVo.toEntityList(vo.getIncidentAccountCodeVos(), true,entity));
			}
			
			if(null != vo.getRestrictedIncidentUserVos()){
				if(null==entity.getRestrictedIncidentUsers())
					entity.setRestrictedIncidentUsers(new ArrayList<RestrictedIncidentUser>());
				
				Collection<RestrictedIncidentUser> removeList = 
					RestrictedIncidentUserVo.toEntityRemoveList(vo.getRestrictedIncidentUserVos(),entity.getRestrictedIncidentUsers());
				
				Collection<RestrictedIncidentUser> addList = 
					RestrictedIncidentUserVo.toEntityAddList(vo.getRestrictedIncidentUserVos(),entity.getRestrictedIncidentUsers(),entity);
				
				if(CollectionUtility.hasValue(removeList))
					entity.getRestrictedIncidentUsers().removeAll(removeList);
				
				if(CollectionUtility.hasValue(addList))
					entity.getRestrictedIncidentUsers().addAll(addList);

				//entity.setRestrictedIncidentUsers(RestrictedIncidentUserVo.toEntityList(entity.getRestrictedIncidentUsers(), vo.getRestrictedIncidentUserVos(), true,entity));
			}

			if(null != vo.getRateClassVo()) {
				entity.setRateClass(RateClassVo.toEntity(vo.getRateClassVo(), true, entity));
			}
			
			if(null != vo.getIncidentShiftVos()) {
				entity.setIncidentShifts(IncidentShiftVo.toEntityList(vo.getIncidentShiftVos(), true));
			}

			if(null != vo.getIncidentCostDefaultsVo()) {
				entity.setIncidentCostDefaults(IncidentCostDefaultsVo.toEntity(entity.getIncidentCostDefaults(),vo.getIncidentCostDefaultsVo(), true,entity));
			}
			
			
			
			
			if(CollectionUtility.hasValue(vo.getIncidentQSKindVos())) {
				Collection<IncidentQSKind> removeList = 
					IncidentQSKindVo.toEntityRemoveList(vo.getIncidentQSKindVos(),entity.getIncidentQSKinds());
				
				Collection<IncidentQSKind> addList = 
					IncidentQSKindVo.toEntityAddList(vo.getIncidentQSKindVos(),entity.getIncidentQSKinds(),entity);
				
				if(CollectionUtility.hasValue(removeList))
					entity.getIncidentQSKinds().removeAll(removeList);
				
				if(CollectionUtility.hasValue(addList))
					entity.getIncidentQSKinds().addAll(addList);
			}
			
			if(ShortUtil.hasValue(vo.getIapPersonNameOrder()))
				entity.setIapPersonNameOrder(PersonsNameOrderEnum.toShortValue(vo.getIapPersonNameOrder()));
			else
				entity.setIapPersonNameOrder(Short.valueOf("0"));

			entity.setIncludeFilled(StringBooleanEnum.toEnumValue(vo.getIncludeFilled()));
			
			if(ShortUtil.hasValue(vo.getIapResourceToDisplayFrom()))
				entity.setIapResourceToDisplayFrom(ResourcesToDisplayFromEnum.toShortValue(vo.getIapResourceToDisplayFrom()));
			else
			    entity.setIapResourceToDisplayFrom(Short.valueOf("0"));
			
			if(ShortUtil.hasValue(vo.getIapTreeviewDisplay()))
				entity.setIapTreeviewDisplay(TreeviewDisplayEnum.toShortValue(vo.getIapTreeviewDisplay()));
			else
				entity.setIapTreeviewDisplay(Short.valueOf("0"));
			
			entity.setByDate(vo.getByDate());
			
			if(StringUtility.hasValue(vo.getNbrOfDaysPriorString()))
				entity.setNbrOfDaysPrior(Short.valueOf(vo.getNbrOfDaysPriorString()));
			else
				entity.setNbrOfDaysPrior(Short.valueOf("0"));
			//entity.setNbrOfDaysPrior(vo.getNbrOfDaysPrior());

			if(null != vo.getIncidentPrefsOtherFieldsVo()){
				entity.setIncidentPrefsOtherFields(IncidentPrefsOtherFieldsVo.toEntity(entity.getIncidentPrefsOtherFields()
																, vo.getIncidentPrefsOtherFieldsVo()
																, true
																, entity));
			}
			
//			if(null != vo.getIapMasterFrequencyVos()) {
//				entity.setIapMasterFrequencies(IapMasterFrequencyVo.toEntityList(vo.getIapMasterFrequencyVos(), true));
//			}
			
			/*
			 * Validate the entity
			 */
			 validateEntity(entity);

		}

		return entity;
	}

	public static Collection<Incident> toEntityList(Collection<IncidentVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<Incident> entities = new ArrayList<Incident>();
		
		for(IncidentVo vo : vos){
			entities.add(IncidentVo.toEntity(null, vo, cascadable,persistables));
		}
		
		return entities;
	}

	public static Collection<Incident> toEntityRemoveList(Collection<IncidentVo> newList, Collection<Incident> incidents){
		Collection<Incident> removeList = new ArrayList<Incident>();
		
		if(!CollectionUtility.hasValue(incidents))
			return removeList;
		
		for(Incident i : incidents){
			Boolean found=false;
			
			for(IncidentVo newVo : newList){
				if(i.getId().compareTo(newVo.getId())==0){
					found=true;
					break;
				};
			}
			if(!found){
				removeList.add(i);
			}
		}
		
		return removeList;
	}
	
	public static Collection<Incident> toEntityAddList(Collection<IncidentVo> vos
		, Collection<Incident> incidents
		, IncidentGroup entity) throws Exception {
			
			Collection<Incident> addList = new ArrayList<Incident>();
			
			if(!CollectionUtility.hasValue(incidents)){
				/*
				* Add all
				*/
				for(IncidentVo vo : vos){
					Incident i = new IncidentImpl();
					i.setId(vo.getId());
					//i.setIncidentGroup(entity);
					addList.add(i);
				}
				return addList;
			}
			
			for(IncidentVo iVo : vos){
				Boolean found=false;
				
				for(Incident i : incidents){
					if(i.getId().compareTo(iVo.getId())==0){
						found=true;
						break;
					};
				}
				
				if(!found){
					addList.add(IncidentVo.toEntity(null, iVo, false, entity));
				}
			}
			
			return addList;
		}
	
	
	/**
	 * Perform some validation on the incident entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source incident entity
	 * @throws ValidationException
	 */
	private static void validateEntity(Incident entity) throws ValidationException {
    	Validator.validateStringField("incidentName", entity.getIncidentName(), 50, true);
    	Validator.validateStringField("incidentDescription", entity.getIncidentDescription(), 1024, false);
    	Validator.validateStringField("incidentNumber", entity.getNbr(), 10, true);
    	Validator.validateEntityField("agency", entity.getAgency(), true);
    	Validator.validateEntityField("homeUnit", entity.getHomeUnit(), true);
    	Validator.validateEntityField("eventType", entity.getEventType(), true);
    	Validator.validateDateField("incidentStartDate", entity.getIncidentStartDate(), true);
	}

	public static boolean hasRestrictedIncidentUser(Long riuId,Collection<RestrictedIncidentUserVo> vos) throws Exception {
		if(null != vos){
			for(RestrictedIncidentUserVo vo : vos){
				if(vo.getId().compareTo(riuId)==0)
					return true;
			}
		}
		return false;
	}
	
	public static boolean hasIncidentAccountCode(Long iacId,Collection<IncidentAccountCodeVo> vos) throws Exception {
		if(null != vos){
			for(IncidentAccountCodeVo vo : vos){
				if(vo.getId().compareTo(iacId)==0)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the incidentName.
	 *
	 * @return 
	 *		the incidentName to return
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * Sets the incidentName.
	 *
	 * @param incidentName 
	 *			the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * Returns the incidentNumber.
	 *
	 * @return 
	 *		the incidentNumber to return
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}

	/**
	 * Sets the incidentNumber.
	 *
	 * @param incidentNumber 
	 *			the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	/**
	 * Returns the incidentDescription.
	 *
	 * @return 
	 *		the incidentDescription to return
	 */
	public String getIncidentDescription() {
		return incidentDescription;
	}

	/**
	 * Sets the incidentDescription.
	 *
	 * @param incidentDescription 
	 *			the incidentDescription to set
	 */
	public void setIncidentDescription(String incidentDescription) {
		this.incidentDescription = incidentDescription;
	}

	/**
	 * Returns the countryCodeSubdivisionVo.
	 *
	 * @return 
	 *		the countryCodeSubdivisionVo to return
	 */
	public CountryCodeSubdivisionVo getCountryCodeSubdivisionVo() {
		return countryCodeSubdivisionVo;
	}

	/**
	 * Sets the countryCodeSubdivisionVo.
	 *
	 * @param countryCodeSubdivisionVo 
	 *			the countryCodeSubdivisionVo to set
	 */
	public void setCountryCodeSubdivisionVo(
			CountryCodeSubdivisionVo countryCodeSubdivisionVo) {
		this.countryCodeSubdivisionVo = countryCodeSubdivisionVo;
	}

	/**
	 * Returns the homeUnitVo.
	 *
	 * @return 
	 *		the homeUnitVo to return
	 */
	public OrganizationVo getHomeUnitVo() {
		return homeUnitVo;
	}

	/**
	 * Sets the homeUnitVo.
	 *
	 * @param homeUnitVo 
	 *			the homeUnitVo to set
	 */
	public void setHomeUnitVo(OrganizationVo homeUnitVo) {
		this.homeUnitVo = homeUnitVo;
	}

	/**
	 * @return the pdcVo
	 */
	public OrganizationVo getPdcVo() {
		return pdcVo;
	}

	/**
	 * @param pdcVo the pdcVo to set
	 */
	public void setPdcVo(OrganizationVo pdcVo) {
		this.pdcVo = pdcVo;
	}

	/**
	 * Returns the agencyVo.
	 *
	 * @return 
	 *		the agencyVo to return
	 */
	public AgencyVo getAgencyVo() {
		return agencyVo;
	}

	/**
	 * Sets the agencyVo.
	 *
	 * @param agencyVo 
	 *			the agencyVo to set
	 */
	public void setAgencyVo(AgencyVo agencyVo) {
		this.agencyVo = agencyVo;
	}

	/**
	 * Returns the location.
	 *
	 * @return 
	 *		the location to return
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location 
	 *			the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Returns the latitude.
	 *
	 * @return 
	 *		the latitude to return
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude 
	 *			the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * Returns the longitude.
	 *
	 * @return 
	 *		the longitude to return
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude 
	 *			the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	/**
	 * Returns the incidentYear.
	 *
	 * @return 
	 *		the incidentYear to return
	 */
	public Integer getIncidentYear() {
		return incidentYear;
	}

	/**
	 * Sets the incidentYear.
	 *
	 * @param incidentYear 
	 *			the incidentYear to set
	 */
	public void setIncidentYear(Integer incidentYear) {
		this.incidentYear = incidentYear;
	}

	/**
	 * Returns the eventTypeVo.
	 *
	 * @return 
	 *		the eventTypeVo to return
	 */
	public EventTypeVo getEventTypeVo() {
		return eventTypeVo;
	}

	/**
	 * Sets the eventTypeVo.
	 *
	 * @param eventTypeVo 
	 *			the eventTypeVo to set
	 */
	public void setEventTypeVo(EventTypeVo eventTypeVo) {
		this.eventTypeVo = eventTypeVo;
	}

	/**
	 * Returns the restricted.
	 *
	 * @return 
	 *		the restricted to return
	 */
	public Boolean getRestricted() {
		return restricted;
	}

	/**
	 * Sets the restricted.
	 *
	 * @param restricted 
	 *			the restricted to set
	 */
	public void setRestricted(Boolean restricted) {
		this.restricted = restricted;
	}

	/**
	 * Returns the restrictedIncidentUserVos.
	 *
	 * @return 
	 *		the restrictedIncidentUserVos to return
	 */
	public Collection<RestrictedIncidentUserVo> getRestrictedIncidentUserVos() {
		return restrictedIncidentUserVos;
	}

	/**
	 * Sets the restrictedIncidentUserVos.
	 *
	 * @param restrictedIncidentUserVos 
	 *			the restrictedIncidentUserVos to set
	 */
	public void setRestrictedIncidentUserVos(
			Collection<RestrictedIncidentUserVo> restrictedIncidentUserVos) {
		this.restrictedIncidentUserVos = restrictedIncidentUserVos;
	}

	/**
	 * Returns the restrictedOwners.
	 *
	 * @return 
	 *		the restrictedOwners to return
	 */
	public Collection<RestrictedIncidentUserVo> getRestrictedOwners() {
		return restrictedOwners;
	}

	/**
	 * Sets the restrictedOwners.
	 *
	 * @param restrictedOwners 
	 *			the restrictedOwners to set
	 */
	public void setRestrictedOwners(
			Collection<RestrictedIncidentUserVo> restrictedOwners) {
		this.restrictedOwners = restrictedOwners;
	}

	/**
	 * Returns the restrictedUsers.
	 *
	 * @return 
	 *		the restrictedUsers to return
	 */
	public Collection<RestrictedIncidentUserVo> getRestrictedUsers() {
		return restrictedUsers;
	}

	/**
	 * Sets the restrictedUsers.
	 *
	 * @param restrictedUsers 
	 *			the restrictedUsers to set
	 */
	public void setRestrictedUsers(
			Collection<RestrictedIncidentUserVo> restrictedUsers) {
		this.restrictedUsers = restrictedUsers;
	}

	/**
	 * Returns the incidentAccountCodeVos.
	 *
	 * @return 
	 *		the incidentAccountCodeVos to return
	 */
	public Collection<IncidentAccountCodeVo> getIncidentAccountCodeVos() {
		return incidentAccountCodeVos;
	}

	/**
	 * Sets the incidentAccountCodeVos.
	 *
	 * @param incidentAccountCodeVos 
	 *			the incidentAccountCodeVos to set
	 */
	public void setIncidentAccountCodeVos(
			Collection<IncidentAccountCodeVo> incidentAccountCodeVos) {
		this.incidentAccountCodeVos = incidentAccountCodeVos;
	}


	/**
	 * @return the incidentPrefsOtherFieldsVo
	 */
	public IncidentPrefsOtherFieldsVo getIncidentPrefsOtherFieldsVo() {
		return incidentPrefsOtherFieldsVo;
	}

	/**
	 * @param incidentPrefsOtherFieldsVo the incidentPrefsOtherFieldsVo to set
	 */
	public void setIncidentPrefsOtherFieldsVo(
			IncidentPrefsOtherFieldsVo incidentPrefsOtherFieldsVo) {
		this.incidentPrefsOtherFieldsVo = incidentPrefsOtherFieldsVo;
	}

	public RateClassVo getRateClassVo() {
		return rateClassVo;
	}

	public void setRateClassVo(RateClassVo rateClassVo) {
		this.rateClassVo = rateClassVo;
	}

	public Collection<IncidentQuestionVo> getAirTravelQuestions() {
		return airTravelQuestions;
	}

	public void setAirTravelQuestions(Collection<IncidentQuestionVo> airTravelQuestions) {
		this.airTravelQuestions = airTravelQuestions;
	}

	public Collection<IncidentQuestionVo> getCheckInQuestions() {
		return checkInQuestions;
	}

	public void setCheckInQuestions(Collection<IncidentQuestionVo> checkInQuestions) {
		this.checkInQuestions = checkInQuestions;
	}

	/**
	 * @return the rossIncidentId
	 */
	public String getRossIncidentId() {
		return rossIncidentId;
	}

	/**
	 * @param rossIncidentId the rossIncidentId to set
	 */
	public void setRossIncidentId(String rossIncidentId) {
		this.rossIncidentId = rossIncidentId;
	}

	/**
	 * @return the rossIncId
	 */
	public String getRossIncId() {
		return rossIncId;
	}

	/**
	 * @param rossIncId the rossIncId to set
	 */
	public void setRossIncId(String rossIncId) {
		this.rossIncId = rossIncId;
	}

	/**
	 * @return the rossXmlFileId
	 */
	public Long getRossXmlFileId() {
		return rossXmlFileId;
	}

	/**
	 * @param rossXmlFileId the rossXmlFileId to set
	 */
	public void setRossXmlFileId(Long rossXmlFileId) {
		this.rossXmlFileId = rossXmlFileId;
	}

	/**
	 * @param incidentShiftVos the incidentShiftVos to set
	 */
	public void setIncidentShiftVos(Collection<IncidentShiftVo> incidentShiftVos) {
		this.incidentShiftVos = incidentShiftVos;
	}

	/**
	 * @return the incidentShiftVos
	 */
	public Collection<IncidentShiftVo> getIncidentShiftVos() {
		return incidentShiftVos;
	}

	/**
	 * @return the incidentQSKindVos
	 */
	public Collection<IncidentQSKindVo> getIncidentQSKindVos() {
		return incidentQSKindVos;
	}

	/**
	 * @param incidentQSKindVos the incidentQSKindVos to set
	 */
	public void setIncidentQSKindVos(Collection<IncidentQSKindVo> incidentQSKindVos) {
		this.incidentQSKindVos = incidentQSKindVos;
	}

	/**
	 * @return the incidentPrefsVo
	 */
	public Collection<IncidentPrefsVo> getIncidentPrefsVo() {
		return incidentPrefsVos;
	}

	/**
	 * @param incidentPrefsVo the incidentPrefsVo to set
	 */
	public void setIncidentPrefsVo(Collection<IncidentPrefsVo> incidentPrefsVo) {
		this.incidentPrefsVos = incidentPrefsVo;
	}

	/**
	 * @return the incidentCostDefaultsVo
	 */
	public IncidentCostDefaultsVo getIncidentCostDefaultsVo() {
		return incidentCostDefaultsVo;
	}

	/**
	 * @param incidentCostDefaultsVo the incidentCostDefaultsVo to set
	 */
	public void setIncidentCostDefaultsVo(
			IncidentCostDefaultsVo incidentCostDefaultsVo) {
		this.incidentCostDefaultsVo = incidentCostDefaultsVo;
	}

	/**
	 * @return the logistictsPrefsVos
	 */
	public Collection<IncidentPrefsVo> getLogistictsPrefsVos() {
		return logistictsPrefsVos;
	}

	/**
	 * @param logistictsPrefsVos the logistictsPrefsVos to set
	 */
	public void setLogistictsPrefsVos(Collection<IncidentPrefsVo> logistictsPrefsVos) {
		this.logistictsPrefsVos = logistictsPrefsVos;
	}

	/**
	 * @return the planningPrefsVos
	 */
	public Collection<IncidentPrefsVo> getPlanningPrefsVos() {
		return planningPrefsVos;
	}

	/**
	 * @param planningPrefsVos the planningPrefsVos to set
	 */
	public void setPlanningPrefsVos(Collection<IncidentPrefsVo> planningPrefsVos) {
		this.planningPrefsVos = planningPrefsVos;
	}

	/**
	 * @return the financePrefsVos
	 */
	public Collection<IncidentPrefsVo> getFinancePrefsVos() {
		return financePrefsVos;
	}

	/**
	 * @param financePrefsVos the financePrefsVos to set
	 */
	public void setFinancePrefsVos(Collection<IncidentPrefsVo> financePrefsVos) {
		this.financePrefsVos = financePrefsVos;
	}

	/**
	 * @return the otherPrefsVos
	 */
	public Collection<IncidentPrefsVo> getOtherPrefsVos() {
		return otherPrefsVos;
	}

	/**
	 * @param otherPrefsVos the otherPrefsVos to set
	 */
	public void setOtherPrefsVos(Collection<IncidentPrefsVo> otherPrefsVos) {
		this.otherPrefsVos = otherPrefsVos;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	public Date getUserDefaultCheckinDate() {
		return userDefaultCheckinDate;
	}

	public void setUserDefaultCheckinDate(Date userDefaultCheckinDate) {
		this.userDefaultCheckinDate = userDefaultCheckinDate;
	}

	public String getUserDefaultCheckinType() {
		return userDefaultCheckinType;
	}

	public void setUserDefaultCheckinType(String userDefaultCheckinType) {
		this.userDefaultCheckinType = userDefaultCheckinType;
	}

	/**
	 * @param iapPersonNameOrder the iapPersonNameOrder to set
	 */
	public void setIapPersonNameOrder(String iapPersonNameOrder) {
		this.iapPersonNameOrder = iapPersonNameOrder;
	}

	/**
	 * @return the iapPersonNameOrder
	 */
	public String getIapPersonNameOrder() {
		return iapPersonNameOrder;
	}

	/**
	 * @param includeFilled the includeFilled to set
	 */
	public void setIncludeFilled(Boolean includeFilled) {
		this.includeFilled = includeFilled;
	}

	/**
	 * @return the includeFilled
	 */
	public Boolean getIncludeFilled() {
		return includeFilled;
	}

	/**
	 * @param iapResourceToDisplayFrom the iapResourceToDisplayFrom to set
	 */
	public void setIapResourceToDisplayFrom(String iapResourceToDisplayFrom) {
		this.iapResourceToDisplayFrom = iapResourceToDisplayFrom;
	}

	/**
	 * @return the iapResourceToDisplayFrom
	 */
	public String getIapResourceToDisplayFrom() {
		return iapResourceToDisplayFrom;
	}

	/**
	 * @param iapTreeviewDisplay the iapTreeviewDisplay to set
	 */
	public void setIapTreeviewDisplay(String iapTreeviewDisplay) {
		this.iapTreeviewDisplay = iapTreeviewDisplay;
	}

	/**
	 * @return the iapTreeviewDisplay
	 */
	public String getIapTreeviewDisplay() {
		return iapTreeviewDisplay;
	}

	/**
	 * @param byDate the byDate to set
	 */
	public void setByDate(Date byDate) {
		this.byDate = byDate;
	}

	/**
	 * @return the byDate
	 */
	public Date getByDate() {
		return byDate;
	}

	/**
	 * @param nbrOfDaysPrior the nbrOfDaysPrior to set
	 */
	public void setNbrOfDaysPrior(Short nbrOfDaysPrior) {
		this.nbrOfDaysPrior = nbrOfDaysPrior;
	}

	/**
	 * @return the nbrOfDaysPrior
	 */
	public Short getNbrOfDaysPrior() {
		return nbrOfDaysPrior;
	}

	/**
	 * @param iapMasterFrequencyVos
	 */
	public void setIapMasterFrequencyVos(Collection<IapMasterFrequencyVo> iapMasterFrequencyVos) {
		this.iapMasterFrequencyVos = iapMasterFrequencyVos;
	}

	/**
	 * @return the iapMasterFrequencyVos
	 */
	public Collection<IapMasterFrequencyVo> getIapMasterFrequencyVos() {
		return iapMasterFrequencyVos;
	}

	/**
	 * @return the costGroupVos
	 */
	public Collection<CostGroupVo> getCostGroupVos() {
		return costGroupVos;
	}

	/**
	 * @param costGroupVos the costGroupVos to set
	 */
	public void setCostGroupVos(Collection<CostGroupVo> costGroupVos) {
		this.costGroupVos = costGroupVos;
	}
	
	/**
	    * @return the incident cost run automatically
	*/
	public Integer getIncidentCostDefaultHours() {
		return this.incidentCostDefaultHours;
	}
	   
	/**
	    * @param to set the incident cost default hours
	*/
	public void setIncidentCostDefaultHours(Integer incidentCostDefaultHours) {
		this.incidentCostDefaultHours = incidentCostDefaultHours;
	}
	
	/**
	    * @return whether the incident run cost manually or automatically 
	*/
	public boolean getCostAutoRun() {
		return this.costAutoRun;
	}
	   
	/**
	    * @param to set the incident CostAutoRun 
	*/
	public void setCostAutoRun(boolean costAutoRun) {
		this.costAutoRun = costAutoRun;
	}
	
	/**
	 * @return the iap204PrefsVos
	 */
	public Collection<IncidentPrefsVo> getIap204PrefsVos() {
		return iap204PrefsVos;
	}

	/**
	 * @param iap204PrefsVos the iap204PrefsVos to set
	 */
	public void setIap204PrefsVos(Collection<IncidentPrefsVo> iap204PrefsVos) {
		this.iap204PrefsVos = iap204PrefsVos;
	}

	/**
	 * @param agencyVos the agencyVos to set
	 */
	public void setAgencyVos(Collection<AgencyVo> agencyVos) {
		this.agencyVos = agencyVos;
	}

	/**
	 * @return the agencyVos
	 */
	public Collection<AgencyVo> getAgencyVos() {
		return agencyVos;
	}

	/**
	 * @param organizationVos the organizationVos to set
	 */
	public void setOrganizationVos(Collection<OrganizationVo> organizationVos) {
		this.organizationVos = organizationVos;
	}

	/**
	 * @return the organizationVos
	 */
	public Collection<OrganizationVo> getOrganizationVos() {
		return organizationVos;
	}

	/**
	 * @param jetPortVos the jetPortVos to set
	 */
	public void setJetPortVos(Collection<JetPortVo> jetPortVos) {
		this.jetPortVos = jetPortVos;
	}

	/**
	 * @return the jetPortVos
	 */
	public Collection<JetPortVo> getJetPortVos() {
		return jetPortVos;
	}

	/**
	 * @param kindVos the kindVos to set
	 */
	public void setKindVos(Collection<KindVo> kindVos) {
		this.kindVos = kindVos;
	}

	/**
	 * @return the kindVos
	 */
	public Collection<KindVo> getKindVos() {
		return kindVos;
	}

	/**
	 * @return the iapPositionItemCodeVos
	 */
	public Collection<IapPositionItemCodeVo> getIapPositionItemCodeVos() {
		return iapPositionItemCodeVos;
	}

	/**
	 * @param iapPositionItemCodeVos the iapPositionItemCodeVos to set
	 */
	public void setIapPositionItemCodeVos(
			Collection<IapPositionItemCodeVo> iapPositionItemCodeVos) {
		this.iapPositionItemCodeVos = iapPositionItemCodeVos;
	}

	/**
	 * @return the branchSettingVos
	 */
	public Collection<BranchSettingVo> getBranchSettingVos() {
		return branchSettingVos;
	}

	/**
	 * @param branchSettingVos the branchSettingVos to set
	 */
	public void setBranchSettingVos(Collection<BranchSettingVo> branchSettingVos) {
		this.branchSettingVos = branchSettingVos;
	}

	/**
	 * @return the isSiteManaged
	 */
	public Boolean getIsSiteManaged() {
		return isSiteManaged;
	}

	/**
	 * @param isSiteManaged the isSiteManaged to set
	 */
	public void setIsSiteManaged(Boolean isSiteManaged) {
		this.isSiteManaged = isSiteManaged;
	}

	/**
	 * @return the nbrOfDaysPriorString
	 */
	public String getNbrOfDaysPriorString() {
		return nbrOfDaysPriorString;
	}

	/**
	 * @param nbrOfDaysPriorString the nbrOfDaysPriorString to set
	 */
	public void setNbrOfDaysPriorString(String nbrOfDaysPriorString) {
		this.nbrOfDaysPriorString = nbrOfDaysPriorString;
	}

	/**
	 * @return the irwinIrwinId
	 */
	public String getIrwinIrwinId() {
		return irwinIrwinId;
	}

	/**
	 * @param irwinIrwinId the irwinIrwinId to set
	 */
	public void setIrwinIrwinId(String irwinIrwinId) {
		this.irwinIrwinId = irwinIrwinId;
	}

	/**
	 * @return the irwinIncidentName
	 */
	public String getIrwinIncidentName() {
		return irwinIncidentName;
	}
	
	/**
	 * @param irwinIncidentName the irwinIncidentName to set
	 */
	public void setIrwinIncidentName(String irwinIncidentName) {
		this.irwinIncidentName = irwinIncidentName;
	}

	public String getIrwinFireDiscoveryDateTime() {
		return irwinFireDiscoveryDateTime;
	}

	public void setIrwinFireDiscoveryDateTime(String irwinFireDiscoveryDateTime) {
		this.irwinFireDiscoveryDateTime = irwinFireDiscoveryDateTime;
	}

	public String getIrwinPooResponsibleUnit() {
		return irwinPooResponsibleUnit;
	}

	public void setIrwinPooResponsibleUnit(String irwinPooResponsibleUnit) {
		this.irwinPooResponsibleUnit = irwinPooResponsibleUnit;
	}

	public String getIrwinLocalIncidentIdentifier() {
		return irwinLocalIncidentIdentifier;
	}

	public void setIrwinLocalIncidentIdentifier(String irwinLocalIncidentIdentifier) {
		this.irwinLocalIncidentIdentifier = irwinLocalIncidentIdentifier;
	}

	public String getIrwinIncidentTypeKind() {
		return irwinIncidentTypeKind;
	}

	public void setIrwinIncidentTypeKind(String irwinIncidentTypeKind) {
		this.irwinIncidentTypeKind = irwinIncidentTypeKind;
	}

	public String getIrwinIncidentTypeCategory() {
		return irwinIncidentTypeCategory;
	}

	public void setIrwinIncidentTypeCategory(String irwinIncidentTypeCategory) {
		this.irwinIncidentTypeCategory = irwinIncidentTypeCategory;
	}

	public String getIrwinFireCode() {
		return irwinFireCode;
	}

	public void setIrwinFireCode(String irwinFireCode) {
		this.irwinFireCode = irwinFireCode;
	}

	public String getIrwinFsJobCode() {
		return irwinFsJobCode;
	}

	public void setIrwinFsJobCode(String irwinFsJobCode) {
		this.irwinFsJobCode = irwinFsJobCode;
	}

	public String getIrwinFsOverrideCode() {
		return irwinFsOverrideCode;
	}

	public void setIrwinFsOverrideCode(String irwinFsOverrideCode) {
		this.irwinFsOverrideCode = irwinFsOverrideCode;
	}

	public String getIrwinIsActive() {
		return irwinIsActive;
	}

	public void setIrwinIsActive(String irwinIsActive) {
		this.irwinIsActive = irwinIsActive;
	}

	public String getIrwinRecordSource() {
		return irwinRecordSource;
	}

	public void setIrwinRecordSource(String irwinRecordSource) {
		this.irwinRecordSource = irwinRecordSource;
	}

	public String getIrwinCreatedBySystem() {
		return irwinCreatedBySystem;
	}

	public void setIrwinCreatedBySystem(String irwinCreatedBySystem) {
		this.irwinCreatedBySystem = irwinCreatedBySystem;
	}

	public String getIrwinCreatedOnDateTime() {
		return irwinCreatedOnDateTime;
	}

	public void setIrwinCreatedOnDateTime(String irwinCreatedOnDateTime) {
		this.irwinCreatedOnDateTime = irwinCreatedOnDateTime;
	}

	public String getIrwinModifiedBySystem() {
		return irwinModifiedBySystem;
	}

	public void setIrwinModifiedBySystem(String irwinModifiedBySystem) {
		this.irwinModifiedBySystem = irwinModifiedBySystem;
	}

	public String getIrwinModifiedOnDateTime() {
		return irwinModifiedOnDateTime;
	}

	public void setIrwinModifiedOnDateTime(String irwinModifiedOnDateTime) {
		this.irwinModifiedOnDateTime = irwinModifiedOnDateTime;
	}
	
	public String getIrwinPooProtectingUnit() {
		return irwinPooProtectingUnit;
	}

	public void setIrwinPooProtectingUnit(String irwinPooProtectingUnit) {
		this.irwinPooProtectingUnit = irwinPooProtectingUnit;
	}
	
	public String getIrwinIsComplex() {
		return irwinIsComplex;
	}

	public void setIrwinIsComplex(String irwinIsComplex) {
		this.irwinIsComplex = irwinIsComplex;
	}
	
	public String getIrwinComplexParentIrwinId() {
		return irwinComplexParentIrwinId;
	}

	public void setIrwinComplexParentIrwinId(String irwinComplexParentIrwinId) {
		this.irwinComplexParentIrwinId = irwinComplexParentIrwinId;
	}
	
	public String getIrwinUniqueFireIdentifier() {
		return irwinUniqueFireIdentifier;
	}

	public void setIrwinUniqueFireIdentifier(String irwinUniqueFireIdentifier) {
		this.irwinUniqueFireIdentifier = irwinUniqueFireIdentifier;
	}
	
	public String getIrwinAbcdMisc() {
		return irwinAbcdMisc;
	}

	public void setIrwinAbcdMisc(String irwinAbcdMisc) {
		this.irwinAbcdMisc = irwinAbcdMisc;
	}
	
	public String getIrwinStatus() {
		return irwinStatus;
	}

	public void setIrwinStatus(String irwinStatus) {
		this.irwinStatus = irwinStatus;
	}
	
	public String getIrwinIsValid() {
		return irwinIsValid;
	}

	public void setIrwinIsValid(String irwinIsValid) {
		this.irwinIsValid = irwinIsValid;
	}

	public DateTransferVo getIncStartDateTransferVo() {
		return incStartDateTransferVo;
	}

	public void setIncStartDateTransferVo(DateTransferVo incStartDateTransferVo) {
		this.incStartDateTransferVo = incStartDateTransferVo;
	}

	public DateTransferVo getIncEndDateTransferVo() {
		return incEndDateTransferVo;
	}

	public void setIncEndDateTransferVo(DateTransferVo incEndDateTransferVo) {
		this.incEndDateTransferVo = incEndDateTransferVo;
	}

}
