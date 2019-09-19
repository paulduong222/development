package gov.nwcg.isuite.framework.cost.generator;

import gov.nwcg.isuite.core.cost.data.DefaultCostRateData;
import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentCostRateKind;
import gov.nwcg.isuite.core.domain.IncidentCostRateStateKind;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.persistence.IncidentCostRateKindDao;
import gov.nwcg.isuite.core.persistence.IncidentCostRateStateKindDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.AssignmentTimeVo;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.framework.types.AgencyTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.DecimalUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class AbstractGenerator {
	public enum CostTypeEnum {
		RESOURCE
		,RESOURCE_OTHER
		,RESOURCE_AIRCRAFT
	}
	
	protected IncidentResourceDailyCostDao irdcDao=null;
	protected TimeAssignAdjustDao taaDao=null;
	protected TimePostDao tpDao=null;

	protected Collection<IncidentResourceDailyCost> irdcEntities = null;
	protected Collection<AssignmentTimePost> tpEntities = new ArrayList<AssignmentTimePost>();
	protected Collection<TimeAssignAdjust> taaEntities = new ArrayList<TimeAssignAdjust>();
	
	protected ApplicationContext context;
	protected IncidentResource irEntity;
	protected IncidentResourceOther iroEntity;
	protected CostResourceDataVo costResourceDataVo;
	
	protected IncidentResourceVo irVo;
	protected IncidentResourceOtherVo iroVo;
	
	public Date dailyCostStartDate;
	public Date dailyCostEndDate;

	// resource cost start/end dates
	public Date resourceCostStartDate;
	public Date resourceCostEndDate;
	
	public Boolean isParent=false;
	public Boolean generateParentEstimates=false;
	
	protected DefaultCostRateData defaultCostRateData=new DefaultCostRateData();
	
	/**
	 * Sets the application context instance.
	 * 
	 * @param ctx
	 * 		ApplicationContext
	 */
	public void setContext(ApplicationContext ctx){
		this.context=ctx;
		
		irdcDao=(IncidentResourceDailyCostDao)context.getBean("incidentResourceDailyCostDao");
		taaDao=(TimeAssignAdjustDao)context.getBean("timeAssignAdjustDao");
		tpDao=(TimePostDao)context.getBean("timePostDao");
	}

	/**
	 * Sets the Incident Resource entity.
	 * 
	 * @param irEntity
	 * 		IncidentResource
	 */
	public void setIncidentResource(IncidentResource irEntity) {
		this.irEntity=irEntity;
	}

	/**
	 * Sets the Incident Resource Vo.
	 * 
	 * @param vo
	 * 		IncidentResourceVo
	 */
	public void setIncidentResourceVo(IncidentResourceVo vo) {
		this.irVo=vo;
	}
	
	/**
	 * @param roEntity
	 */
	public void setIncidentResourceOther(IncidentResourceOther iroEntity){
		this.iroEntity=iroEntity;
	}

	public void setIncidentResourceOtherVo(IncidentResourceOtherVo vo) {
		this.iroVo=vo;
	}

	/**
	 * Builds a list of dates based on start/end dates.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	protected Collection<Date> buildDates(Date startDate, Date endDate) throws Exception {
		Boolean bContinue=true;
		
		Collection<Date> dates = new ArrayList<Date>();
		
		while(bContinue==true){
			// add the date
			dates.add(startDate);
			
			// if startDate == endDate, exit loop
			if(DateUtil.isSameDate(startDate, endDate)){
				bContinue=false;
			}else{
				// add 1 day
				startDate=DateUtil.addDaysToDate(startDate, 1);
			}
			
		}
		
		return dates;
	}

	/**
	 * @param d
	 * @return
	 * @throws Exception
	 */
	protected Collection<Long> getChildUniqueAcctCodeIdsByDate(Date d) throws Exception{

		// get iac's for child timepostings
		//Collection<Long> childIACs 
		//	= tpDao.getChildUniqueAcctCodeIdsByDate(irEntity.getId(), irVo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo().getId() , d);
		
		// get iac's for child time adjustments
		
		// get iac's for child daily costs records
		//Collection<Long> childIACs 
		//	= this.irdcDao.getChildUniqueAcctCodeIdsByDate(irEntity.getId(), irVo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo().getId() , d);
		Collection<Long> childIACs 
			= this.irdcDao.getChildUniqueAcctCodeIdsByDate(this.costResourceDataVo.getIncidentResourceId(), null , d);
		
		return childIACs;
	}
	
	protected void loadCollections(CostTypeEnum costType) throws Exception {

		// get any existing cost records for the resource
		if(costType!=CostTypeEnum.RESOURCE_OTHER){
			//irdcEntities = irdcDao.getByIncidentResourceId(irVo.getId());
		
			// get any existing time post records for the resource
			tpEntities = tpDao.getByIncidentResourceId(irVo.getId());
			
			// get any existing adjustment records for the resource
			taaEntities = taaDao.getByIncidentResourceId(irVo.getId());
		}else{
			irdcEntities = irdcDao.getByIncidentResourceOtherId(iroVo.getId());
		}
		
	}

	protected void loadCollections2(CostTypeEnum costType) throws Exception {

		// get any existing cost records for the resource
		if(costType!=CostTypeEnum.RESOURCE_OTHER){
			//irdcEntities = irdcDao.getByIncidentResourceId(irVo.getId());
		
			// get any existing time post records for the resource
			tpEntities = tpDao.getByIncidentResourceId(costResourceDataVo.getIncidentResourceId());
			
			// get any existing adjustment records for the resource
			taaEntities = taaDao.getByIncidentResourceId(costResourceDataVo.getIncidentResourceId());
		}else{
			irdcEntities = irdcDao.getByIncidentResourceOtherId(iroVo.getId());
		}
		
	}

	private int getDefaultHours(Long incidentId) throws Exception{
		IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
		Incident inc = incidentDao.getById(incidentId, IncidentImpl.class);
		if(null != inc && null != inc.getIncidentCostDefaultHours()){
			return inc.getIncidentCostDefaultHours().intValue();
		}
		
		return 0;
	}
	
	/**
	 * This methods loads the rates used in Actuals
	 * @throws Exception
	 */
	protected void loadDefaultRate() throws Exception {
		EmploymentTypeEnum empType = 
			irVo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType();

		KindVo kindVo = 
			irVo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo();

		AssignmentTimeVo assignmentTimeVo =
			irVo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo();

		// lookup default hours value
		int defaultHours=this.getDefaultHours(irVo.getIncidentVo().getId());
		
		// set defaults, override later if necessary
		this.defaultCostRateData.rateAmount=BigDecimal.valueOf(0.0);
		this.defaultCostRateData.units=defaultHours;
		this.defaultCostRateData.rateType="HOURLY";
		
		if(null==empType)
			return;
		else
			this.defaultCostRateData.employmentType=empType;
			
		/*
		 * For FED Resources, the system must use the FED rate defined for the 
		 * Resource’s Item Code in the Cost Rate table to determine the Unit Cost.
		 */
		if(empType==EmploymentTypeEnum.FED){
			IncidentCostRateKindDao icrkDao = (IncidentCostRateKindDao)context.getBean("incidentCostRateKindDao");
			IncidentCostRateKind icrkEntity = 
				icrkDao.getByKind("FED", irVo.getIncidentVo().getId(), kindVo.getId());
			
			if(null!=icrkEntity){
				this.defaultCostRateData.rateAmount=icrkEntity.getRateAmount();
				this.defaultCostRateData.rateType=icrkEntity.getRateType();
			}
		}
		
		/* 
		 * For an AD Resource, the system must use the actual rate that was 
		 * entered for the Time Posting as the Unit Cost.
		 */
		if(empType==EmploymentTypeEnum.AD){
			if(null != assignmentTimeVo 
					&& null != assignmentTimeVo.getAdPaymentInfoVo()
					&& null != assignmentTimeVo.getAdPaymentInfoVo().getRateClassRateVo()){

				this.defaultCostRateData.rateAmount=
					assignmentTimeVo.getAdPaymentInfoVo().getRateClassRateVo().getRate();
			}
		}
		
		/* 
		 * For an OTHER Resource, the system must use the actual rate that was entered 
		 * for the Time Posting, unless no rate was defined. 
		 * If the rate is null, then the system must use the default rate from the 
		 * Cost Rates table. ( how we do determine rate lookup from cost table?)
		 */ 
		if(empType==EmploymentTypeEnum.OTHER){
			if(DecimalUtil.hasValue(assignmentTimeVo.getOtherDefaultRate())){
				this.defaultCostRateData.rateAmount=assignmentTimeVo.getOtherDefaultRate();
			}else{
				// if other rate is zero, lookup rate from cost rates table
				IncidentCostRateKindDao icrkDao = (IncidentCostRateKindDao)context.getBean("incidentCostRateKindDao");
				IncidentCostRateKind icrkEntity = 
					icrkDao.getByKind("FED", irVo.getIncidentVo().getId(), kindVo.getId());
				
				if(null!=icrkEntity){
					this.defaultCostRateData.rateAmount=icrkEntity.getRateAmount();
					this.defaultCostRateData.rateType=icrkEntity.getRateType();
				}
			}
		}
		
		/* When a Resource is a Contractor/Cooperator, 
		 * the system must use the Contractor rate defined in the Cost Rate table 
		 * for the Item Code assigned to the Resource to estimate costs.
		 * 
		 */
		if(empType==EmploymentTypeEnum.CONTRACTOR){
			// use time posting data
		}
		
		/*
		 * When a Resource is a State Resource, 
		 * the system must use the Custom State Rate defined for the Resource’s Item Code. 
		 * If there is no Custom State Rate defined for an Item Code, 
		 * then the system must use the State Default Rate for the Item Code 
		 * in the Cost Rates table. (dev: how do we determine if resource is a state resource?)
		 */

		
		if(this.defaultCostRateData.rateType.equals("EACH"))
			this.defaultCostRateData.units=1;
		if(this.defaultCostRateData.rateType.equals("DAILY"))
			this.defaultCostRateData.units=1;
	}

	protected void loadEstimateDefaultRate() throws Exception {
		EmploymentTypeEnum empType = 
			irVo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType();

		KindVo kindVo = 
			irVo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo();

		AgencyVo agencyVo =
			irVo.getResourceVo().getAgencyVo();
		
		AssignmentTimeVo assignmentTimeVo =
			irVo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo();

		int defaultHours=this.getDefaultHours(irVo.getIncidentVo().getId());
		
		// set defaults, override later if necessary
		this.defaultCostRateData.estimateRateAmount=BigDecimal.valueOf(0.0);
		this.defaultCostRateData.estimateUnits=defaultHours;
		this.defaultCostRateData.estimateRateType="HOURLY";
		
		//if(null==empType)
		//	return;
		//else
			this.defaultCostRateData.employmentType=empType;

		IncidentCostRateKindDao icrkDao = (IncidentCostRateKindDao)context.getBean("incidentCostRateKindDao");
		IncidentCostRateStateKindDao icrskDao = (IncidentCostRateStateKindDao)context.getBean("incidentCostRateStateKindDao");
		
		/* 
		 * Is the Invoice Setup (OF-286) flag set for the Resource? 
		 * If yes, use the Contractor Rates in the Cost Rates table.
		 */
		if(empType != null && empType==EmploymentTypeEnum.CONTRACTOR){
			IncidentCostRateKind icrkEntity = 
				icrkDao.getByKind("CONTRACTOR", irVo.getIncidentVo().getId(), kindVo.getId());
			if(null!=icrkEntity){
				this.defaultCostRateData.estimateRateAmount=icrkEntity.getRateAmount();
				this.defaultCostRateData.estimateRateType=icrkEntity.getRateType();
				if(icrkEntity.getRateType().equals("DAILY") || icrkEntity.getRateType().equals("EACH"))
					this.defaultCostRateData.estimateUnits=1;
				else
					this.defaultCostRateData.estimateUnits=defaultHours;
			}else{
				this.defaultCostRateData.estimateRateType="DAILY";
				this.defaultCostRateData.estimateUnits=1;
			}
		}else {
			// does resource have an agency?
			if(null != agencyVo){
				AgencyDao agencyDao = (AgencyDao)context.getBean("agencyDao");
				Agency agency = agencyDao.getById(agencyVo.getId(), AgencyImpl.class);
				
				/*
				 * Is the Agency for the Resource a federal agency? 
				 * If yes, use the Federal Rates in the Cost Rates table.
				 */
				if(null != agency && agency.getAgencyType()==AgencyTypeEnum.FEDERAL){
					IncidentCostRateKind icrkEntity = 
						icrkDao.getByKind("FED", irVo.getIncidentVo().getId(), kindVo.getId());
					
					if(null!=icrkEntity){
						this.defaultCostRateData.estimateRateAmount=icrkEntity.getRateAmount();
						this.defaultCostRateData.estimateRateType=icrkEntity.getRateType();
					}
					
				}else if(null != agency && agency.getAgencyType()==AgencyTypeEnum.STATE){
					 /*
					  * Is the Agency for the Resource a state? 
					  * If yes, are there Custom Rates for that state in the Cost Rates table? 
					  * If yes, use the custom rates. If no, use the Default State Rates.	
					  */
					IncidentCostRateStateKind icrskEntity = 
						icrskDao.getByKind("STATE_COOP_CUSTOM", irVo.getIncidentVo().getId(), kindVo.getId(), agency.getId());

					// if custom entity is not null and rate is > 0, then use it
					if(null != icrskEntity && DecimalUtil.hasValue(icrskEntity.getRateAmount()) ){
						this.defaultCostRateData.estimateRateAmount=icrskEntity.getRateAmount();
						this.defaultCostRateData.estimateRateType=icrskEntity.getRateType();
					}else{
						IncidentCostRateKind icrkEntity = 
							icrkDao.getByKind("STATE_COOP", irVo.getIncidentVo().getId(), kindVo.getId());

						if(null != icrkEntity){
							this.defaultCostRateData.estimateRateAmount=icrkEntity.getRateAmount();
							this.defaultCostRateData.estimateRateType=icrkEntity.getRateType();
						}
					}
				}
				
			}
			
		}
		
	}

	protected void loadDefaultRateResourceOther() throws Exception {
		// set defaults, override later if necessary
		this.defaultCostRateData.rateAmount=BigDecimal.valueOf(0.0);
		this.defaultCostRateData.units=0;
		this.defaultCostRateData.rateType="HOURLY";
		
	}

	protected void loadEstimateDefaultRateResourceOther() throws Exception {
		KindVo kindVo = null;
		AgencyVo agencyVo = null;
		
		if(null != iroVo && null != iroVo.getResourceOtherVo()){
			if(null!=iroVo.getResourceOtherVo().getKindVo()){
				kindVo=iroVo.getResourceOtherVo().getKindVo();
			}
			
			if(null!=iroVo.getResourceOtherVo().getAgencyVo()){
				agencyVo=iroVo.getResourceOtherVo().getAgencyVo();
			}
		}
		
		int defaultHours=this.getDefaultHours(iroVo.getIncidentVo().getId());

		// set Defaults
		this.defaultCostRateData.estimateUnits=defaultHours;
		this.defaultCostRateData.estimateRateAmount=BigDecimal.valueOf(0.0);
		this.defaultCostRateData.estimateRateType="HOURLY";
		
		if(null != kindVo && !kindVo.getCode().equals("MISC")){
			IncidentCostRateKindDao icrkDao = (IncidentCostRateKindDao)context.getBean("incidentCostRateKindDao");

			// does resource have an agency?
			if(null != agencyVo){
				AgencyDao agencyDao = (AgencyDao)context.getBean("agencyDao");
				Agency agency = agencyDao.getById(agencyVo.getId(), AgencyImpl.class);
				
				/*
				 * Is the Agency for the Resource a federal agency? 
				 * If yes, use the Federal Rates in the Cost Rates table.
				 */
				if(null != agency && (agency.getAgencyType()==AgencyTypeEnum.FEDERAL || agency.getAgencyType()==AgencyTypeEnum.OTHER)){
					IncidentCostRateKind icrkEntity = 
						icrkDao.getByKind("FED", iroVo.getIncidentVo().getId(), kindVo.getId());
					
					if(null!=icrkEntity){
						this.defaultCostRateData.estimateRateAmount=icrkEntity.getRateAmount();
						this.defaultCostRateData.estimateRateType=icrkEntity.getRateType();
					}
					
				}else if(null != agency && agency.getAgencyType()==AgencyTypeEnum.STATE){
					 /*
					  * Is the Agency for the Resource a state? 
					  * If yes, are there Custom Rates for that state in the Cost Rates table? 
					  * If yes, use the custom rates. If no, use the Default State Rates.	
					  */
					IncidentCostRateStateKindDao icrskDao = (IncidentCostRateStateKindDao)context.getBean("incidentCostRateStateKindDao");
					IncidentCostRateStateKind icrskEntity = 
						icrskDao.getByKind("STATE_COOP_CUSTOM", iroVo.getIncidentVo().getId(), kindVo.getId(), agency.getId());

					// if custom entity is not null and rate is > 0, then use it
					if(null != icrskEntity && DecimalUtil.hasValue(icrskEntity.getRateAmount()) ){
						this.defaultCostRateData.estimateRateAmount=icrskEntity.getRateAmount();
						this.defaultCostRateData.estimateRateType=icrskEntity.getRateType();
					}else{
						IncidentCostRateKind icrkEntity = 
							icrkDao.getByKind("STATE_COOP_CUSTOM", iroVo.getIncidentVo().getId(), kindVo.getId());

						if(null != icrkEntity){
							this.defaultCostRateData.estimateRateAmount=icrkEntity.getRateAmount();
							this.defaultCostRateData.estimateRateType=icrkEntity.getRateType();
						}
					}
					
				}
				
			}
			
		}else if(null != kindVo && kindVo.getCode().equals("MISC")){
			// default to empty
			this.defaultCostRateData.estimateUnits=0;
		}
		
	}
	
	protected void loadDefaultCostGroupShift() throws Exception {
		if(null != this.irEntity.getCostData()){
			if(null != irEntity.getCostData().getDefaultCostGroup()){
				this.defaultCostRateData.costGroup=irEntity.getCostData().getDefaultCostGroup();
			}
			
			if(null != irEntity.getCostData().getDefaultIncidentShift()){
				this.defaultCostRateData.incidentShift=irEntity.getCostData().getDefaultIncidentShift();
			}
		}
	}
	
	protected void loadDefaultCostGroupShiftResourceOther() throws Exception {
		if(null != this.iroEntity.getCostData()){
			if(null != iroEntity.getCostData().getDefaultCostGroup()){
				this.defaultCostRateData.costGroup=iroEntity.getCostData().getDefaultCostGroup();
			}
			
			if(null != iroEntity.getCostData().getDefaultIncidentShift()){
				this.defaultCostRateData.incidentShift=iroEntity.getCostData().getDefaultIncidentShift();
			}
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.cost.generator.ICostGenerator#rollup(gov.nwcg.isuite.core.domain.IncidentResource)
	 */
	public void rollup(IncidentResource incidentResource) throws Exception {

	}

	/**
	 * @param dt
	 */
	public void setRunEndDate(Date dt){
		this.dailyCostEndDate=dt;
	}
	
	public void setIsParent(Boolean val){
		this.isParent=val;
	}

	public void setGenerateParentEstimates(Boolean val){
		this.generateParentEstimates=val;
	}
	
	protected Boolean isAccrualCodeChanged(IncidentResourceDailyCost dcEntity, IncidentResourceDailyCostVo vo){
		if(null != dcEntity.getAccrualCode() && null == vo.getAccrualCodeVo())
			return true;
		
		if(null == dcEntity.getAccrualCode() && null != vo.getAccrualCodeVo())
			return true;

		if(null != dcEntity.getAccrualCode() && null!=vo.getAccrualCodeVo()){
			Long entityId=dcEntity.getAccrualCode().getId();
			Long voId=vo.getAccrualCodeVo().getId();
			
			if(entityId.compareTo(voId)!=0)
				return true;
		}
		
		return false;
	}
	
	protected Boolean isCostGroupChanged(IncidentResourceDailyCost dcEntity, IncidentResourceDailyCostVo vo){
		if(null != dcEntity.getCostGroup() && null == vo.getCostGroupVo())
			return true;
		
		if(null == dcEntity.getCostGroup() && null != vo.getCostGroupVo())
			return true;

		if(null != dcEntity.getCostGroup() && null!=vo.getCostGroupVo()){
			Long entityId=dcEntity.getCostGroup().getId();
			Long voId=vo.getCostGroupVo().getId();
			
			if(entityId.compareTo(voId)!=0)
				return true;
		}
		
		return false;
	}

	protected Boolean isAccountCodeChanged(IncidentResourceDailyCost dcEntity, IncidentResourceDailyCostVo vo){
		if(null != dcEntity.getIncidentAccountCode() && null == vo.getIncidentAccountCodeVo())
			return true;
		
		if(null == dcEntity.getIncidentAccountCode() && null != vo.getIncidentAccountCodeVo())
			return true;

		if(null != dcEntity.getIncidentAccountCode() && null!=vo.getIncidentAccountCodeVo()){
			Long entityId=dcEntity.getIncidentAccountCode().getId();
			Long voId=vo.getIncidentAccountCodeVo().getId();
			
			if(entityId.compareTo(voId)!=0)
				return true;
		}
		
		return false;
	}

	/**
	 * @param costResourceDataVo the costResourceDataVo to set
	 */
	public void setCostResourceDataVo(CostResourceDataVo costResourceDataVo) {
		this.costResourceDataVo = costResourceDataVo;
	}
	
	
}
