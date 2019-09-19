package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupPrefs;
import gov.nwcg.isuite.core.domain.IncidentGroupQSKind;
import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.core.domain.IncidentGroupUser;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.IncidentPrefsSectionNameEnum;
import gov.nwcg.isuite.framework.types.PersonsNameOrderEnum;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.types.ResourcesToDisplayFromEnum;
import gov.nwcg.isuite.framework.types.ShortUtil;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.TreeviewDisplayEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * A Persistable VO representative of an Incident Group.
 * 
 * @author bsteiner
 */
public class IncidentGroupVo extends AbstractVo implements PersistableVo {

	private String groupName;
	private Long primaryIncidentId;
	
	private Collection<IncidentVo> incidentVos;
	private Collection<IncidentVo> incidentRemoveVos =new ArrayList<IncidentVo>();
	private Collection<IncidentGroupUserVo> incidentGroupUsers;
	private Collection<IncidentGroupQuestionVo> airTravelQuestions = new ArrayList<IncidentGroupQuestionVo>();
	private Collection<IncidentGroupQuestionVo> checkInQuestions = new ArrayList<IncidentGroupQuestionVo>();
	private Collection<IncidentGroupQSKindVo> incidentGroupQSKindVos;

	private Collection<IncidentGroupPrefsVo> checkOutLogisticsVos = new ArrayList<IncidentGroupPrefsVo>();
	private Collection<IncidentGroupPrefsVo> checkOutPlanningVos = new ArrayList<IncidentGroupPrefsVo>();
	private Collection<IncidentGroupPrefsVo> checkOutFinanceVos = new ArrayList<IncidentGroupPrefsVo>();
	private Collection<IncidentGroupPrefsVo> checkOutOtherVos = new ArrayList<IncidentGroupPrefsVo>();
		
	private Date createDate;  //TODO: why is this here? Why not use the auditable createDate?

	private IncidentPrefsOtherFieldsVo incidentPrefsOtherFieldsVo = new IncidentPrefsOtherFieldsVo();
	
	// read-only
	private Collection<IncidentAccountCodeVo> incidentAccountCodeVos = new ArrayList<IncidentAccountCodeVo>();
	
	private Integer incidentGroupCostDefaultHours;
	private Boolean costAutoRun;
	
    private String iapPersonNameOrder;
	private Boolean includeFilled;
	private String iapResourceToDisplayFrom;
	private String iapTreeviewDisplay;
	private Date byDate;
	private Short nbrOfDaysPrior;
	private String nbrOfDaysPriorString;

	// iap lists
	private Collection<IapMasterFrequencyVo> iapMasterFrequencyVos = new ArrayList<IapMasterFrequencyVo>();
	private Collection<IapPositionItemCodeVo> iapPositionItemCodeVos = new ArrayList<IapPositionItemCodeVo>();
	private Collection<BranchSettingVo> branchSettingVos = new ArrayList<BranchSettingVo>();
	private Collection<IncidentGroupPrefsVo> iap204PrefsVos = new ArrayList<IncidentGroupPrefsVo>();

	private Collection<AgencyVo> agencyVos = new ArrayList<AgencyVo>();
	private Collection<OrganizationVo> organizationVos = new ArrayList<OrganizationVo>();
	private Collection<JetPortVo> jetPortVos = new ArrayList<JetPortVo>();
	private Collection<KindVo> kindVos = new ArrayList<KindVo>();

	private Boolean isSiteManaged=false;
	
	public IncidentGroupVo() {}

	public IncidentGroupVo(IncidentGroup entity) {
		super(entity);
	}

	/**
	 * Returns a IncidentGroupVo from a incidentGroup entity.
	 * 
	 * @param entity
	 * 			the source IncidentGroup entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of IncidentGroup vo
	 * @throws Exception
	 */
	public static IncidentGroupVo getInstance(IncidentGroup entity,boolean cascadable) throws Exception{
		IncidentGroupVo vo = new IncidentGroupVo();

		if(null == entity)
			throw new Exception("Unable to create IncidentGroupVo from null IncidentGroup entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			vo.setGroupName(entity.getGroupName());
			
			vo.setIsSiteManaged(StringBooleanEnum.toBooleanValue(entity.getIsSiteManaged()));
			
			vo.setIncidentGroupUsers(IncidentGroupUserVo.getInstances(entity.getIncidentGroupUsers(), cascadable));
			if(LongUtility.hasValue(entity.getPrimaryIncidentId()))
				vo.setPrimaryIncidentId(entity.getPrimaryIncidentId());
			else
				vo.setPrimaryIncidentId(-1L);
			
			if(null != entity.getIncidents()){
				vo.setIncidentVos(new ArrayList<IncidentVo>());
				for(Incident incidentEntity : entity.getIncidents()){
					vo.getIncidentVos().add(IncidentVo.getInstance(incidentEntity, true));
					if(null != incidentEntity.getIncidentAccountCodes()){
						Collection<IncidentAccountCodeVo> iacVos = IncidentAccountCodeVo.getInstances(incidentEntity.getIncidentAccountCodes(), true);
						vo.getIncidentAccountCodeVos().addAll(iacVos);
					}
				}
			}

			if(null != entity.getIncidentGroupQuestions()){
				for(IncidentGroupQuestion iq : entity.getIncidentGroupQuestions()){
					if(iq.getQuestion().getQuestionType()==QuestionTypeEnum.AIRTRAVEL)
						vo.getAirTravelQuestions().add(IncidentGroupQuestionVo.getInstance(iq, true));
					if(iq.getQuestion().getQuestionType()==QuestionTypeEnum.PREPLANNING)
						vo.getCheckInQuestions().add(IncidentGroupQuestionVo.getInstance(iq, true));
				}
			}

			if(null != entity.getIncidentGroupPrefs()){
				for(IncidentGroupPrefs iq : entity.getIncidentGroupPrefs()){
					if(iq.getSectionName()==IncidentPrefsSectionNameEnum.FINANCE)
						vo.getCheckOutFinanceVos().add(IncidentGroupPrefsVo.getInstance(iq, true));
					if(iq.getSectionName()==IncidentPrefsSectionNameEnum.PLANNING)
						vo.getCheckOutPlanningVos().add(IncidentGroupPrefsVo.getInstance(iq, true));
					if(iq.getSectionName()==IncidentPrefsSectionNameEnum.LOGISTICS)
						vo.getCheckOutLogisticsVos().add(IncidentGroupPrefsVo.getInstance(iq, true));
					if(iq.getSectionName()==IncidentPrefsSectionNameEnum.OTHER_LABEL)
						vo.getCheckOutOtherVos().add(IncidentGroupPrefsVo.getInstance(iq, true));
					if(iq.getSectionName().name().startsWith("IAP") ){
						if(iq.getFieldLabel().equalsIgnoreCase("LAST DAY TO WORK")){
							// skip last day to work field, no longer applicable
							// need to hardcode skipping until we remove from incident group trigger
						}else{
							vo.getIap204PrefsVos().add(IncidentGroupPrefsVo.getInstance(iq,true));
						}
					}
				}
			}

			if(null != entity.getIncidentGroupQSKinds()){
				vo.setIncidentGroupQSKindVos(IncidentGroupQSKindVo.getInstances(entity.getIncidentGroupQSKinds(),true));
			}
			
			vo.setIncidentGroupCostDefaultHours(entity.getIncidentGroupCostDefaultHours());

			if(null != entity.getCostAutoRun())
				vo.setCostAutoRun(entity.getCostAutoRun().getValue());
			else
				vo.setCostAutoRun(false);
			
			if(ShortUtil.hasValue(entity.getIapPersonNameOrder()))
				vo.setIapPersonNameOrder(PersonsNameOrderEnum.toStringValue(entity.getIapPersonNameOrder()));
			else // For a newly created incident group without a default PersonNameOrder value set
				vo.setIapPersonNameOrder(PersonsNameOrderEnum.toStringValue(Short.valueOf("0")));	
			
			vo.setIncludeFilled(StringBooleanEnum.toBooleanValue(entity.getIncludeFilled()));
			vo.setIapResourceToDisplayFrom(ResourcesToDisplayFromEnum.toStringValue(entity.getIapResourceToDisplayFrom()));

			if(ShortUtil.hasValue(entity.getIapTreeviewDisplay()))
				vo.setIapTreeviewDisplay(TreeviewDisplayEnum.toStringValue(entity.getIapTreeviewDisplay()));
			else
				vo.setIapTreeviewDisplay(TreeviewDisplayEnum.toStringValue(Short.valueOf("0")));
			vo.setByDate(entity.getByDate());
			vo.setNbrOfDaysPrior(entity.getNbrOfDaysPrior());
			if(ShortUtil.hasValue(vo.getNbrOfDaysPrior()))
				vo.setNbrOfDaysPriorString(String.valueOf(vo.getNbrOfDaysPrior()));
			else
				vo.setNbrOfDaysPriorString("0");

//			if(null != entity.getIapMasterFrequencies()) {
//				vo.setIapMasterFrequencyVos(IapMasterFrequencyVo.getInstances(entity.getIapMasterFrequencies(), true));
//			}

			if(CollectionUtility.hasValue(entity.getIapPositionItemCodes())){
				vo.setIapPositionItemCodeVos(IapPositionItemCodeVo.getInstances(entity.getIapPositionItemCodes(), true));
			}
		
			if(CollectionUtility.hasValue(entity.getBranchSettings())){
				vo.setBranchSettingVos(BranchSettingVo.getInstances(entity.getBranchSettings(), true));
			}

			if(null != entity.getIncidentPrefsOtherFields()){
				vo.setIncidentPrefsOtherFieldsVo(IncidentPrefsOtherFieldsVo.getInstance(entity.getIncidentPrefsOtherFields(), true));
			}
			
			vo.setCreatedDate(entity.getCreatedDate());
		}

		return vo;
	}

	/**
	 * Returns a collection of IncidentGroupVos from a collection of IncidentGroup entities.
	 * 
	 * @param entities
	 * 			the source collection of IncidentGroup entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of IncidentGroup vos
	 * @throws Exception
	 */
	public static Collection<IncidentGroupVo> getInstances(Collection<IncidentGroup> entities, boolean cascadable) throws Exception {
		Collection<IncidentGroupVo> vos = new ArrayList<IncidentGroupVo>();

		for(IncidentGroup entity : entities){
			vos.add(IncidentGroupVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a IncidentGroup entity from a IncidentGroup vo.
	 * 
	 * @param vo
	 * 			the source IncidentGroup vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of IncidentGroup entity
	 * @throws Exception
	 */
	public static IncidentGroup toEntity(IncidentGroup entity,IncidentGroupVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null==entity)
			entity = new IncidentGroupImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setGroupName(vo.getGroupName());
			entity.setIncidentGroupCostDefaultHours(vo.getIncidentGroupCostDefaultHours());
			entity.setCostAutoRun(vo.getCostAutoRun()== true ? StringBooleanEnum.Y : StringBooleanEnum.N);

			entity.setIsSiteManaged(StringBooleanEnum.toEnumValue(vo.getIsSiteManaged()));

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

			if(CollectionUtility.hasValue(vo.getIncidentVos())){
				Collection<Incident> removeList = 
					IncidentVo.toEntityRemoveList(vo.getIncidentVos(),entity.getIncidents());
				
				Collection<Incident> addList = 
					IncidentVo.toEntityAddList(vo.getIncidentVos(),entity.getIncidents(),entity);
				
				if(CollectionUtility.hasValue(removeList))
					entity.getIncidents().removeAll(removeList);
				
				if(CollectionUtility.hasValue(addList)){
					entity.getIncidents().addAll(addList);
				}
				 entity.setPrimaryIncidentId(vo.getPrimaryIncidentId());
			}else
				entity.setPrimaryIncidentId(null);
			
			if(CollectionUtility.hasValue(vo.getIncidentGroupUsers())){
				Collection<IncidentGroupUser> removeList = 
					IncidentGroupUserVo.toEntityRemoveList(vo.getIncidentGroupUsers(),entity.getIncidentGroupUsers());
				
				Collection<IncidentGroupUser> addList = 
					IncidentGroupUserVo.toEntityAddList(vo.getIncidentGroupUsers(),entity.getIncidentGroupUsers(),entity);
				
				if(CollectionUtility.hasValue(removeList))
					entity.getIncidentGroupUsers().removeAll(removeList);
				
				if(CollectionUtility.hasValue(addList))
					entity.getIncidentGroupUsers().addAll(addList);
			}
				
			if(CollectionUtility.hasValue(vo.getIncidentGroupQSKindVos())) {
				Collection<IncidentGroupQSKind> removeList = 
					IncidentGroupQSKindVo.toEntityRemoveList(vo.getIncidentGroupQSKindVos(),entity.getIncidentGroupQSKinds());
				
				Collection<IncidentGroupQSKind> addList = 
					IncidentGroupQSKindVo.toEntityAddList(vo.getIncidentGroupQSKindVos(),entity.getIncidentGroupQSKinds(),entity);
				
				if(CollectionUtility.hasValue(removeList))
					entity.getIncidentGroupQSKinds().removeAll(removeList);
				
				if(CollectionUtility.hasValue(addList))
					entity.getIncidentGroupQSKinds().addAll(addList);
				
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
			

			if(null != vo.getIncidentPrefsOtherFieldsVo()){
				entity.setIncidentPrefsOtherFields(IncidentPrefsOtherFieldsVo.toEntity(entity.getIncidentPrefsOtherFields()
																, vo.getIncidentPrefsOtherFieldsVo()
																, true
																, entity));
			}
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);

		}

		return entity;
	}

	/**
	 * Perform some validation on the kind field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source entity
	 * @throws ValidationException
	 */
	private static void validateEntity(IncidentGroup entity) throws ValidationException {
		//Validator.validateStringField("groupName", entity.getGroupName(), 50, true);
		//Validator.validateLongField("workAreaId", entity.getWorkArea().getId(), true);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getGroupName() {
		return this.groupName;
	}

	/**
	 * 
	 * @param name
	 */
	public void setGroupName(String name) {
		this.groupName = name;  
	}

	/**
	 * 
	 * @return
	 */
	public Collection<IncidentVo> getIncidentVos() {
		return this.incidentVos;
	}

	public void setIncidentVos(Collection<IncidentVo> incidentVos) {
		this.incidentVos = incidentVos;
	}

	/**
	 * 
	 * @param incidents
	 */
	public void setIncidents(Collection<IncidentVo> incidentVos) {
		this.incidentVos = incidentVos;
	}


	/**
	 * 
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the incidentGroupUsers
	 */
	public Collection<IncidentGroupUserVo> getIncidentGroupUsers() {
		return incidentGroupUsers;
	}

	/**
	 * @param incidentGroupUsers the incidentGroupUsers to set
	 */
	public void setIncidentGroupUsers(Collection<IncidentGroupUserVo> incidentGroupUsers) {
		this.incidentGroupUsers = incidentGroupUsers;
	}

	/**
	 * @return the incidentAccountCodeVos
	 */
	public Collection<IncidentAccountCodeVo> getIncidentAccountCodeVos() {
		return incidentAccountCodeVos;
	}

	/**
	 * @param incidentAccountCodeVos the incidentAccountCodeVos to set
	 */
	public void setIncidentAccountCodeVos(
			Collection<IncidentAccountCodeVo> incidentAccountCodeVos) {
		this.incidentAccountCodeVos = incidentAccountCodeVos;
	}

	/**
	 * @return the incidentGroupQSKindVos
	 */
	public Collection<IncidentGroupQSKindVo> getIncidentGroupQSKindVos() {
		return incidentGroupQSKindVos;
	}

	/**
	 * @param incidentGroupQSKindVos the incidentGroupQSKindVos to set
	 */
	public void setIncidentGroupQSKindVos(
			Collection<IncidentGroupQSKindVo> incidentGroupQSKindVos) {
		this.incidentGroupQSKindVos = incidentGroupQSKindVos;
	}

	/**
	 * @return the airTravelQuestions
	 */
	public Collection<IncidentGroupQuestionVo> getAirTravelQuestions() {
		return airTravelQuestions;
	}

	/**
	 * @param airTravelQuestions the airTravelQuestions to set
	 */
	public void setAirTravelQuestions(
			Collection<IncidentGroupQuestionVo> airTravelQuestions) {
		this.airTravelQuestions = airTravelQuestions;
	}

	/**
	 * @return the checkInQuestions
	 */
	public Collection<IncidentGroupQuestionVo> getCheckInQuestions() {
		return checkInQuestions;
	}

	/**
	 * @param checkInQuestions the checkInQuestions to set
	 */
	public void setCheckInQuestions(
			Collection<IncidentGroupQuestionVo> checkInQuestions) {
		this.checkInQuestions = checkInQuestions;
	}

	
	/**
	 * @return getCheckOutLogisticsVos
	 */
	public Collection<IncidentGroupPrefsVo> getCheckOutLogisticsVos() {
		return checkOutLogisticsVos;
	}
	
	/**
	 * @param checkOutLogisticsVos
	 */

	public void setCheckOutLogisticsVos(
			Collection<IncidentGroupPrefsVo> checkOutLogisticsVos) {
		this.checkOutLogisticsVos = checkOutLogisticsVos;
	}
	
	/**
	 * @return getCheckOutPlanningVos
	 */

	public Collection<IncidentGroupPrefsVo> getCheckOutPlanningVos() {
		return checkOutPlanningVos;
	}
	
	/**
	 * @param checkOutPlanningVos
	 */

	public void setCheckOutPlanningVos(
			Collection<IncidentGroupPrefsVo> checkOutPlanningVos) {
		this.checkOutPlanningVos = checkOutPlanningVos;
	}
	
	/**
	 * @return getCheckOutPlanningVos
	 */

	public Collection<IncidentGroupPrefsVo> getCheckOutFinanceVos() {
		return checkOutFinanceVos;
	}
	
	/**
	 * @param checkOutFinanceVos
	 */

	public void setCheckOutFinanceVos(
			Collection<IncidentGroupPrefsVo> checkOutFinanceVos) {
		this.checkOutFinanceVos = checkOutFinanceVos;
	}
	
	/**
	 * @return getCheckOutOtherVos
	 */

	public Collection<IncidentGroupPrefsVo> getCheckOutOtherVos() {
		return checkOutOtherVos;
	}
	
	/**
	 * @param checkOutOtherVos
	 */

	public void setCheckOutOtherVos(
			Collection<IncidentGroupPrefsVo> checkOutOtherVos) {
		this.checkOutOtherVos = checkOutOtherVos;
	}

	public Collection<IncidentVo> getIncidentRemoveVos() {
		return incidentRemoveVos;
	}

	public void setIncidentRemoveVos(Collection<IncidentVo> incidentRemoveVos) {
		this.incidentRemoveVos = incidentRemoveVos;
	}
	
	/**
	    * @return the incident cost default hours
	*/
	public Integer getIncidentGroupCostDefaultHours() {
		return this.incidentGroupCostDefaultHours;
	}
	   
	/**
	    * @param to set the incident cost default hours
	*/
	public void setIncidentGroupCostDefaultHours(Integer incidentGroupCostDefaultHours) {
		this.incidentGroupCostDefaultHours = incidentGroupCostDefaultHours;
	}

	/**
	 * @return the costAutoRun
	 */
	public Boolean getCostAutoRun() {
		return this.costAutoRun;
	}

	/**
	 * @param costAutoRun the costAutoRun to set
	 */
	public void setCostAutoRun(Boolean costAutoRun) {
		this.costAutoRun = costAutoRun;
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
	 * @return the iapMasterFrequencyVos
	 */
	public Collection<IapMasterFrequencyVo> getIapMasterFrequencyVos() {
		return iapMasterFrequencyVos;
	}

	/**
	 * @param iapMasterFrequencyVos the iapMasterFrequencyVos to set
	 */
	public void setIapMasterFrequencyVos(
			Collection<IapMasterFrequencyVo> iapMasterFrequencyVos) {
		this.iapMasterFrequencyVos = iapMasterFrequencyVos;
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
	 * @return the primaryIncidentId
	 */
	public Long getPrimaryIncidentId() {
		return primaryIncidentId;
	}

	/**
	 * @param primaryIncidentId the primaryIncidentId to set
	 */
	public void setPrimaryIncidentId(Long primaryIncidentId) {
		this.primaryIncidentId = primaryIncidentId;
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

	/**
	 * @return the iap204PrefsVos
	 */
	public Collection<IncidentGroupPrefsVo> getIap204PrefsVos() {
		return iap204PrefsVos;
	}

	/**
	 * @param iap204PrefsVos the iap204PrefsVos to set
	 */
	public void setIap204PrefsVos(Collection<IncidentGroupPrefsVo> iap204PrefsVos) {
		this.iap204PrefsVos = iap204PrefsVos;
	}

	/**
	 * @return the agencyVos
	 */
	public Collection<AgencyVo> getAgencyVos() {
		return agencyVos;
	}

	/**
	 * @param agencyVos the agencyVos to set
	 */
	public void setAgencyVos(Collection<AgencyVo> agencyVos) {
		this.agencyVos = agencyVos;
	}

	/**
	 * @return the organizationVos
	 */
	public Collection<OrganizationVo> getOrganizationVos() {
		return organizationVos;
	}

	/**
	 * @param organizationVos the organizationVos to set
	 */
	public void setOrganizationVos(Collection<OrganizationVo> organizationVos) {
		this.organizationVos = organizationVos;
	}

	/**
	 * @return the jetPortVos
	 */
	public Collection<JetPortVo> getJetPortVos() {
		return jetPortVos;
	}

	/**
	 * @param jetPortVos the jetPortVos to set
	 */
	public void setJetPortVos(Collection<JetPortVo> jetPortVos) {
		this.jetPortVos = jetPortVos;
	}

	/**
	 * @return the kindVos
	 */
	public Collection<KindVo> getKindVos() {
		return kindVos;
	}

	/**
	 * @param kindVos the kindVos to set
	 */
	public void setKindVos(Collection<KindVo> kindVos) {
		this.kindVos = kindVos;
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
	 * @return the iapTreeviewDisplay
	 */
	public String getIapTreeviewDisplay() {
		return iapTreeviewDisplay;
	}

	/**
	 * @param iapTreeviewDisplay the iapTreeviewDisplay to set
	 */
	public void setIapTreeviewDisplay(String iapTreeviewDisplay) {
		this.iapTreeviewDisplay = iapTreeviewDisplay;
	}

	/**
	 * @return the byDate
	 */
	public Date getByDate() {
		return byDate;
	}

	/**
	 * @param byDate the byDate to set
	 */
	public void setByDate(Date byDate) {
		this.byDate = byDate;
	}

	/**
	 * @return the nbrOfDaysPrior
	 */
	public Short getNbrOfDaysPrior() {
		return nbrOfDaysPrior;
	}

	/**
	 * @param nbrOfDaysPrior the nbrOfDaysPrior to set
	 */
	public void setNbrOfDaysPrior(Short nbrOfDaysPrior) {
		this.nbrOfDaysPrior = nbrOfDaysPrior;
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

}
