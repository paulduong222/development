package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.VoValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

public class AssignmentTimePostVo extends AbstractVo implements Cloneable{

	private RateClassRateVo rateClassRateVo;
	private EmploymentTypeEnum employmentType;
	private IncidentAccountCodeVo incidentAccountCodeVo;
	private KindVo kindVo;
	private ContractorRateVo refContractorRateVo;
	private SpecialPayVo specialPayVo;
	private DateTransferVo postStartDateVo=new DateTransferVo();
	private String postStartTime;
	private DateTransferVo postStopDateVo = new DateTransferVo();
	private String postStopTime;
	private BigDecimal otherRate;
	private String rateType;
	private String unitOfMeasure;
	private BigDecimal rateAmount;
	private BigDecimal guaranteeAmount;
	private String description;
	private Long assignmentTimeId;
	private Boolean isHalfRate;
	private BigDecimal quantity;
	private Boolean training;
	private Boolean returnTravelStartOnly=false;
//	private AssignmentTimePostVo specialRateAssignmentTimePostVo;
	private Boolean primaryPosting;
	private Boolean specialPosting;
	private Boolean guaranteePosting;
	private BigDecimal invoicedAmount;
	private Collection<TimeInvoiceVo> timeInvoiceVos =new ArrayList<TimeInvoiceVo>();
	private Date lastInvoiceDate;
	
	private String contractorPostType;

	private String postStartDateString;
	private String postStopDateString;
	
	public AssignmentTimePostVo(){
		
	}

	static class StartDateComparator implements Comparator{

		public int compare(Object vo1, Object vo2){

//			String val1 = ( (IncidentResourceGridVo) vo1).getRequestNumber();
//			String val2 = ( (IncidentResourceGridVo) vo2).getRequestNumber();
			
			Date val1 = ( (AssignmentTimePostVo) vo1).getPostStartDate();
			Date val2 = ( (AssignmentTimePostVo) vo2).getPostStartDate();
			
			if(val1.before(val2))
				return 0;
			else
				return 1;
			//return val1.compareTo(val2);
		}
	}

	public AssignmentTimePostVo clone() throws CloneNotSupportedException {
		return (AssignmentTimePostVo)super.clone();
	}
	
	public static AssignmentTimePostVo getInstance(AssignmentTimePost entity, boolean cascadable) throws Exception {
		AssignmentTimePostVo vo = new AssignmentTimePostVo();
		
		if(null == entity)
			throw new Exception("Unable to create vo from null AssignmentTimePost entity");
		
		//System.out.println(entity.getId());
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setReturnTravelStartOnly(entity.getReturnTravelStartOnly());

			vo.setPostStartDate(entity.getPostStartDate());
			if(null != entity.getPostStartDate()){
				vo.setPostStartDateString(DateUtil.toDateString(entity.getPostStartDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
				vo.setPostStartTime(DateUtil.toMilitaryTime(entity.getPostStartDate()));
				if(StringUtility.hasValue(vo.getPostStartTime())){
					if(vo.getPostStartTime().equals("2359"))
						vo.setPostStartTime("2400");
				}
			}
			
				vo.setPostStopDate(entity.getPostStopDate());
				if(null != entity.getPostStopDate()){
					vo.setPostStopDateString(DateUtil.toDateString(entity.getPostStopDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
					vo.setPostStopTime(DateUtil.toMilitaryTime(entity.getPostStopDate()));	
					if(StringUtility.hasValue(vo.getPostStopTime())){
						if(vo.getPostStopTime().equals("2359") || vo.getPostStopTime().equals("0000"))
							vo.setPostStopTime("2400");
					}
						
					if(entity.getReturnTravelStartOnly() && vo.getPostStopTime().equals(vo.getPostStartTime())){				
						vo.setPostStopDate(null);
						vo.setPostStopTime(null);	
					}
				}
			
			vo.setAssignmentTimeId(entity.getAssignmentTimeId());
			vo.setEmploymentType(entity.getEmploymentType());
			vo.setOtherRate(entity.getOtherRate());
			vo.setUnitOfMeasure(entity.getUnitOfMeasure());
			vo.setRateAmount(entity.getRateAmount());
			vo.setGuaranteeAmount(entity.getGuaranteeAmount());
			vo.setDescription(entity.getDescription());
			vo.setIsHalfRate(entity.getIsHalfRate());
			vo.setQuantity(entity.getQuantity());
			vo.setTraining(entity.getTraining());
			vo.setPrimaryPosting(entity.getPrimaryPosting());
			vo.setSpecialPosting(entity.getSpecialPosting());
			vo.setGuaranteePosting(entity.getGuaranteePosting());
			vo.setInvoicedAmount(entity.getInvoicedAmount());
			vo.setContractorPostType(entity.getContractorPostType());
			
			if(null != entity.getRateClassRate())
				vo.setRateClassRateVo(RateClassRateVo.getInstance(entity.getRateClassRate(), true));

			if(null != entity.getIncidentAccountCode())
				vo.setIncidentAccountCodeVo(IncidentAccountCodeVo.getInstance(entity.getIncidentAccountCode(), true));
			
			if(null != entity.getKind())
				vo.setKindVo(KindVo.getInstance(entity.getKind(), true));
			
			if(null != entity.getRefContractorRate())
				vo.setRefContractorRateVo(ContractorRateVo.getInstance(entity.getRefContractorRate(), true));

			if(null != entity.getSpecialPay())
				vo.setSpecialPayVo(SpecialPayVo.getInstance(entity.getSpecialPay(), true));
			

			try{
				if(CollectionUtility.hasValue(entity.getTimeInvoices())) {
					vo.setTimeInvoiceVos(TimeInvoiceVo.getInstances(entity.getTimeInvoices(), cascadable));
					if(CollectionUtility.hasValue(vo.getTimeInvoiceVos())){
						for(TimeInvoiceVo tivo : vo.getTimeInvoiceVos()){
							if(BooleanUtility.isTrue(tivo.getIsInvoiceAdjust() )
									&& !DateUtil.hasValue(tivo.getDeletedDate())){
								vo.setLastInvoiceDate(tivo.getLastIncludeDate());
							}
						}
					}
				}
			}catch(Exception ee){}
			
		}
		
		return vo;
	}

	public static Collection<AssignmentTimePostVo> getInstances(Collection<AssignmentTimePost> entities,boolean cascadable) throws Exception {
		Collection<AssignmentTimePostVo> vos = new ArrayList<AssignmentTimePostVo>();
		
		for(AssignmentTimePost entity : entities){
			vos.add(AssignmentTimePostVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}

	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<AssignmentTimePostVo> getInstancesCrew(Collection<AssignmentTimePost> entities,boolean cascadable) throws Exception {
		Collection<AssignmentTimePostVo> vos = new ArrayList<AssignmentTimePostVo>();
		Collection<AssignmentTimePost> entities2 = entities;
		
		for(AssignmentTimePost entity : entities){
			
			/*
			 * Check for like items
			 */
			for(AssignmentTimePost entity2 : entities2){
				if(entity.getId().compareTo(entity2.getId()) != 0){
					if(DateUtil.isSameDate(entity.getPostStartDate(), entity2.getPostStartDate())){
						if(!containsId(vos,entity.getId())
								&&
							!containsCrewMatchInfo(vos, entity))
							vos.add(AssignmentTimePostVo.getInstance(entity, cascadable));
					}
				}
			}
			
		}
		
		return vos;
	}

	/**
	 * @param vos
	 * @param id
	 * @return
	 */
	public static Boolean containsId(Collection<AssignmentTimePostVo> vos, Long id) {
		
		for(AssignmentTimePostVo vo : vos){
			if(vo.getId().compareTo(id)==0)
				return true;
		}
		
		return false;
	}

	/**
	 * @param vos
	 * @param id
	 * @return
	 */
	public static Boolean containsCrewMatchInfo(Collection<AssignmentTimePostVo> vos, AssignmentTimePost entity) {
		
		for(AssignmentTimePostVo vo : vos){
			if(DateUtil.isSameDate(vo.getPostStartDate(), entity.getPostStartDate()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static AssignmentTimePost toEntity(AssignmentTimePost entity, AssignmentTimePostVo vo, boolean cascadable,Persistable...persistables) throws Exception {
		
		if(null == entity)
			entity = new AssignmentTimePostImpl();

		entity.setId(vo.getId());
		
		if(cascadable){
			
			AssignmentTime atEntity = (AssignmentTime)getPersistableObject(persistables,AssignmentTimeImpl.class);
			if(null != atEntity){
				entity.setAssignmentTime(atEntity);
			}else if(null != vo.getAssignmentTimeId() && vo.getAssignmentTimeId() > 0){
				AssignmentTime assignmentTime = new AssignmentTimeImpl();
				assignmentTime.setId(vo.getAssignmentTimeId());
				entity.setAssignmentTime(assignmentTime);
			}
			
			if(VoValidator.isValidAbstractVo(vo.getRateClassRateVo())){
				entity.setRateClassRate(vo.getRateClassRateVo().toEntity(null, vo.getRateClassRateVo(), false));
			}

			if(VoValidator.isValidAbstractVo(vo.getIncidentAccountCodeVo())){
				entity.setIncidentAccountCode(IncidentAccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo(),true));
			}

			if(VoValidator.isValidAbstractVo(vo.getKindVo())){
				entity.setKind(vo.getKindVo().toEntity(null,vo.getKindVo(), false));
			}
			
			if(VoValidator.isValidAbstractVo(vo.getRefContractorRateVo())){
				entity.setRefContractorRate(ContractorRateVo.toEntity(null, vo.getRefContractorRateVo(), false));
			}

			//if( (null != vo.getSpecialRateAssignmentTimePostVo()) && (LongUtility.hasValue(vo.getSpecialRateAssignmentTimePostVo().getId())) ){
			//	entity.setSpecialRateAssignmentTimePost(AssignmentTimePostVo.toEntity(null,vo.getSpecialRateAssignmentTimePostVo(), true, entity));
			//}
			
			
			AssignmentTimePost atpEntity= (AssignmentTimePost)getPersistableObject(persistables, AssignmentTimePostImpl.class);
			if(null!=atpEntity)
				entity.setAssignmentTimePost(atpEntity);
			
			
			if(VoValidator.isValidAbstractVo(vo.getSpecialPayVo())){
				entity.setSpecialPay(vo.getSpecialPayVo().toEntity(vo.getSpecialPayVo(), false));
			}
			
			// set the correct times
			if(StringUtility.hasValue(vo.getPostStartTime())){
				if(null != vo.getPostStartDate()){
					Date start = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
					entity.setPostStartDate(start);
				}
			}else{
				if(null != vo.getPostStartDate()){
					Date start = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), "0000");
					entity.setPostStartDate(start);
					//entity.setPostStartDate(vo.getPostStartDate());
				}
			}

			if(StringUtility.hasValue(vo.getPostStopTime())){
				if(null != vo.getPostStopDate()){
					Date stop = DateUtil.addMilitaryTimeToDate(vo.getPostStopDate(), vo.getPostStopTime());
					entity.setPostStopDate(stop);
				}else{
					if(StringUtility.hasValue(vo.getPostStartTime())){
						Date stop = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
						entity.setPostStopDate(stop);
					}
				}
			}else{
				if(null != vo.getPostStopDate()){
					Date stop = DateUtil.addMilitaryTimeToDate(vo.getPostStopDate(), "0000");
					if(StringUtility.hasValue(vo.getPostStartTime())){
						stop = DateUtil.addMilitaryTimeToDate(vo.getPostStopDate(), vo.getPostStartTime());
					}
					entity.setPostStopDate(stop);
					
					//entity.setPostStopDate(vo.getPostStopDate());
				}else{
					if(StringUtility.hasValue(vo.getPostStartTime())){
						Date stop = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
						entity.setPostStopDate(stop);
					}
				}
			}
			
			
			entity.setOtherRate(vo.getOtherRate());

			entity.setEmploymentType(vo.getEmploymentType());
			entity.setRateType(vo.getRateType());
			entity.setUnitOfMeasure(vo.getUnitOfMeasure());
			entity.setRateAmount(vo.getRateAmount());
			entity.setGuaranteeAmount(vo.getGuaranteeAmount());
			entity.setDescription(vo.getDescription());
			entity.setIsHalfRate(vo.getIsHalfRate());
			entity.setQuantity(vo.getQuantity());
			entity.setTraining(vo.getTraining());
			entity.setReturnTravelStartOnly(vo.getReturnTravelStartOnly());
			entity.setPrimaryPosting(vo.getPrimaryPosting());
			entity.setSpecialPosting(vo.getSpecialPosting());
			entity.setGuaranteePosting(vo.getGuaranteePosting());
			entity.setInvoicedAmount(vo.getInvoicedAmount());
			entity.setContractorPostType(vo.getContractorPostType());
			
			validateEntity(entity);
		}
		
		return entity;
	}
	
	/**
	 * Perform some validation on the AssignmentTimePost field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source AssignmentTimePost entity
	 * @throws ValidationException
	 */
	private static void validateEntity(AssignmentTimePost entity) throws ValidationException {
	}

	/**
	 * @return the rateClassRateVo
	 */
	public RateClassRateVo getRateClassRateVo() {
		return rateClassRateVo;
	}

	/**
	 * @param rateClassRateVo the rateClassRateVo to set
	 */
	public void setRateClassRateVo(RateClassRateVo rateClassRateVo) {
		this.rateClassRateVo = rateClassRateVo;
	}

	/**
	 * @return the incidentAccountCodeVo
	 */
	public IncidentAccountCodeVo getIncidentAccountCodeVo() {
		return incidentAccountCodeVo;
	}

	/**
	 * @param incidentAccountCodeVo the incidentAccountCodeVo to set
	 */
	public void setIncidentAccountCodeVo(IncidentAccountCodeVo incidentAccountCodeVo) {
		this.incidentAccountCodeVo = incidentAccountCodeVo;
	}

	/**
	 * @return the kindVo
	 */
	public KindVo getKindVo() {
		return kindVo;
	}

	/**
	 * @param kindVo the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}

	/**
	 * @return the refContractorRateVo
	 */
	public ContractorRateVo getRefContractorRateVo() {
		return refContractorRateVo;
	}

	/**
	 * @param refContractorRateVo the refContractorRateVo to set
	 */
	public void setRefContractorRateVo(ContractorRateVo refContractorRateVo) {
		this.refContractorRateVo = refContractorRateVo;
	}

	/**
	 * @return the specialPayVo
	 */
	public SpecialPayVo getSpecialPayVo() {
		return specialPayVo;
	}

	/**
	 * @param specialPayVo the specialPayVo to set
	 */
	public void setSpecialPayVo(SpecialPayVo specialPayVo) {
		this.specialPayVo = specialPayVo;
	}

	/**
	 * @return the postStartDate
	 */
	public Date getPostStartDate() {
		Date dt=null;
		if(DateTransferVo.hasDateString(this.getPostStartDateVo())){
			try{
				dt=DateTransferVo.getDate(this.getPostStartDateVo());
				if(StringUtility.hasValue(this.getPostStartDateVo().timeString)){
					String time=this.getPostStartDateVo().timeString;
					dt=DateUtil.addMilitaryTimeToDate(dt, time);
				}
			}catch(Exception ignore){}
		}
		
		return dt;
	}

	/**
	 * @param postStartDate the postStartDate to set
	 */
	public void setPostStartDate(Date postStartDate) {
		if(null != postStartDate){
			String dt=DateUtil.toDateString(postStartDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String time=DateUtil.toMilitaryTime(postStartDate);
			this.postStartDateVo.setDateString(dt);
			this.postStartDateVo.setTimeString(time);
		}
	}

	/**
	 * @return the postStopDate
	 */
	public Date getPostStopDate() {
		Date dt=null;
		if(DateTransferVo.hasDateString(this.getPostStopDateVo())){
			try{
				dt=DateTransferVo.getDate(this.getPostStopDateVo());
				if(StringUtility.hasValue(this.getPostStopDateVo().timeString)){
					String time=this.getPostStopDateVo().timeString;
					dt=DateUtil.addMilitaryTimeToDate(dt, time);
				}
			}catch(Exception ignore){}
		}
		
		return dt;
	}

	/**
	 * @param postStopDate the postStopDate to set
	 */
	public void setPostStopDate(Date postStopDate) {
		if(null != postStopDate){
			String dt=DateUtil.toDateString(postStopDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String time=DateUtil.toMilitaryTime(postStopDate);
			this.postStopDateVo.setDateString(dt);
			this.postStopDateVo.setTimeString(time);
		}
	}

	/**
	 * @return the otherRate
	 */
	public BigDecimal getOtherRate() {
		return otherRate;
	}

	/**
	 * @param otherRate the otherRate to set
	 */
	public void setOtherRate(BigDecimal otherRate) {
		this.otherRate = otherRate;
	}

	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}

	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**
	 * @return the unitOfMeasure
	 */
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	/**
	 * @param unitOfMeasure the unitOfMeasure to set
	 */
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	/**
	 * @return the rateAmmount
	 */
	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	/**
	 * @param rateAmount the rateAmmount to set
	 */
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}

	/**
	 * @return the guaranteeAmount
	 */
	public BigDecimal getGuaranteeAmount() {
		return guaranteeAmount;
	}

	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAssignmentTimeId() {
		return assignmentTimeId;
	}

	public void setAssignmentTimeId(Long assignmentTimeId) {
		this.assignmentTimeId = assignmentTimeId;
	}

	public String getPostStartTime() {
		return postStartTime;
	}

	public void setPostStartTime(String postStartTime) {
		this.postStartTime = postStartTime;
	}

	public String getPostStopTime() {
		return postStopTime;
	}

	public void setPostStopTime(String postStopTime) {
		this.postStopTime = postStopTime;
	}
	
	/**
	 * @return the isHalfRate
	 */
	public Boolean getIsHalfRate() {
		return isHalfRate;
	}


	/**
	 * @param isHalfRate the isHalfRate to set
	 */
	public void setIsHalfRate(Boolean isHalfRate) {
		this.isHalfRate = isHalfRate;
	}


	/**
	 * @return the hours
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}


	/**
	 * @param quantity the hours to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the training
	 */
	public Boolean getTraining() {
		return training;
	}

	/**
	 * @param training the training to set
	 */
	public void setTraining(Boolean training) {
		this.training = training;
	}

	/**
	 * @return the returnTravelStartOnly
	 */
	public Boolean getReturnTravelStartOnly() {
		return returnTravelStartOnly;
	}

	/**
	 * @param returnTravelStartOnly the returnTravelStartOnly to set
	 */
	public void setReturnTravelStartOnly(Boolean returnTravelStartOnly) {
		this.returnTravelStartOnly = returnTravelStartOnly;
	}

	/**
	 * @return the specialRateAssignmentTimePostVo
	 */
//	public AssignmentTimePostVo getSpecialRateAssignmentTimePostVo() {
//		return specialRateAssignmentTimePostVo;
//	}

	/**
	 * @param specialRateAssignmentTimePostVo the specialRateAssignmentTimePostVo to set
	 */
//	public void setSpecialRateAssignmentTimePostVo(
//			AssignmentTimePostVo specialRateAssignmentTimePostVo) {
//		this.specialRateAssignmentTimePostVo = specialRateAssignmentTimePostVo;
//	}

	/**
	 * @return the primaryPosting
	 */
	public Boolean getPrimaryPosting() {
		return primaryPosting;
	}

	/**
	 * @param primaryPosting the primaryPosting to set
	 */
	public void setPrimaryPosting(Boolean primaryPosting) {
		this.primaryPosting = primaryPosting;
	}

	/**
	 * @return the specialPosting
	 */
	public Boolean getSpecialPosting() {
		return specialPosting;
	}

	/**
	 * @param specialPosting the specialPosting to set
	 */
	public void setSpecialPosting(Boolean specialPosting) {
		this.specialPosting = specialPosting;
	}

	/**
	 * @return the guaranteePosting
	 */
	public Boolean getGuaranteePosting() {
		return guaranteePosting;
	}

	/**
	 * @param guaranteePosting the guaranteePosting to set
	 */
	public void setGuaranteePosting(Boolean guaranteePosting) {
		this.guaranteePosting = guaranteePosting;
	}

	/**
	 * @return the invoicedAmount
	 */
	public BigDecimal getInvoicedAmount() {
		return invoicedAmount;
	}

	/**
	 * @param invoicedAmount the invoicedAmount to set
	 */
	public void setInvoicedAmount(BigDecimal invoicedAmount) {
		this.invoicedAmount = invoicedAmount;
	}

	public void setEmploymentType(EmploymentTypeEnum employmentType) {
		this.employmentType = employmentType;
	}


	public EmploymentTypeEnum getEmploymentType() {
		return employmentType;
	}

	public String getContractorPostType() {
		return contractorPostType;
	}

	public void setContractorPostType(String contractorPostType) {
		this.contractorPostType = contractorPostType;
	}

	public Date getLastInvoiceDate() {
		return lastInvoiceDate;
	}

	public void setLastInvoiceDate(Date lastInvoiceDate) {
		this.lastInvoiceDate = lastInvoiceDate;
	}

	public Collection<TimeInvoiceVo> getTimeInvoiceVos() {
		return timeInvoiceVos;
	}

	public void setTimeInvoiceVos(Collection<TimeInvoiceVo> timeInvoiceVos) {
		this.timeInvoiceVos = timeInvoiceVos;
	}

	public String getPostStartDateString() {
		return postStartDateString;
	}

	public void setPostStartDateString(String postStartDateString) {
		this.postStartDateString = postStartDateString;
	}

	public String getPostStopDateString() {
		return postStopDateString;
	}

	public void setPostStopDateString(String postStopDateString) {
		this.postStopDateString = postStopDateString;
	}

	/**
	 * @return the postStartDateVo
	 */
	public DateTransferVo getPostStartDateVo() {
		return postStartDateVo;
	}

	/**
	 * @param postStartDateVo the postStartDateVo to set
	 */
	public void setPostStartDateVo(DateTransferVo postStartDateVo) {
		this.postStartDateVo = postStartDateVo;
	}

	/**
	 * @return the postStopDateVo
	 */
	public DateTransferVo getPostStopDateVo() {
		return postStopDateVo;
	}

	/**
	 * @param postStopDateVo the postStopDateVo to set
	 */
	public void setPostStopDateVo(DateTransferVo postStopDateVo) {
		this.postStopDateVo = postStopDateVo;
	}
}
