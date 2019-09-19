package gov.nwcg.isuite.core.vo;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.impl.IapPlanImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class IapPlanVo extends AbstractVo {
	private Long incidentId;
	private Long incidentGroupId;
	private String incidentName;
	private String operationalPeriod;
	private Date fromDate;
	private String fromDateTime;
	private String fromDateString;
	private Date toDate;
	private String toDateTime;
	private String toDateString;
	private Boolean isPlanLocked;

	private Collection<IapForm202Vo> iapForm202Vos;
	private Collection<IapForm203Vo> iapForm203Vos;
	private Collection<IapForm204Vo> iapForm204Vos;
	private Collection<IapForm205Vo> iapForm205Vos;
	private Collection<IapForm206Vo> iapForm206Vos;
	private Collection<IapForm220Vo> iapForm220Vos;

	
	public IapPlanVo(){
		
	}

	/**
	 * Convenience method to convert the date/time string fields to the datetime field.
	 * Used to avoid timezone adjustments between flex and java.
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public static void fixDateTimes(IapPlanVo vo) throws Exception{
		if(StringUtility.hasValue(vo.getFromDateString())){
			vo.setFromDate(DateUtil.toDate(vo.getFromDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
			
			if(StringUtility.hasValue(vo.getFromDateTime())){
				Date newDate=DateUtil.addMilitaryTimeToDate(vo.getFromDate(), vo.getFromDateTime());
				vo.setFromDate(newDate);
			}
		}
		
		if(StringUtility.hasValue(vo.getToDateString())){
			vo.setToDate(DateUtil.toDate(vo.getToDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
			
			if(StringUtility.hasValue(vo.getToDateTime())){
				Date newDate=DateUtil.addMilitaryTimeToDate(vo.getToDate(), vo.getToDateTime());
				vo.setToDate(newDate);
			}
		}
	}
	
	public static IapPlanVo getInstance(IapPlan entity, Boolean cascadable) throws Exception {
		IapPlanVo vo = new IapPlanVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapPlanVo from null IapPlan entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIncidentGroupId(entity.getIncidentGroupId());
			vo.setIncidentId(entity.getIncidentId());
			vo.setIncidentName(entity.getIncidentName());
			vo.setOperationalPeriod(entity.getOperationPeriod());
			vo.setFromDate(entity.getFromDate());
			if(DateUtil.hasValue(entity.getFromDate())){
				vo.setFromDateString(DateUtil.toDateString(entity.getFromDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
				vo.setFromDateTime(DateUtil.toMilitaryTime(entity.getFromDate()));
			}
			vo.setToDate(entity.getToDate());
			if(DateUtil.hasValue(entity.getToDate())){
				vo.setToDateString(DateUtil.toDateString(entity.getToDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
				vo.setToDateTime(DateUtil.toMilitaryTime(entity.getToDate()));
			}
			
			vo.setIsPlanLocked(StringBooleanEnum.toBooleanValue(entity.getIsPlanLocked()));
			
			if(CollectionUtility.hasValue(entity.getIapForm202s())){
				
			}
			
			if(CollectionUtility.hasValue(entity.getIapForm203s())){
				
			}

			if(CollectionUtility.hasValue(entity.getIapBranchs())){
				
			}

			if(CollectionUtility.hasValue(entity.getIapForm205s())){
				
			}

			if(CollectionUtility.hasValue(entity.getIapForm206s())){
				
			}
			
			if(CollectionUtility.hasValue(entity.getIapForm220s())){
				
			}
			
		}
		
		return vo;
	
	}
	
	public static IapPlan toEntity(IapPlan entity, IapPlanVo vo, Boolean cascadable ) throws Exception {
		if(null == entity) entity = new IapPlanImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setFromDate(vo.getFromDate());
			entity.setToDate(vo.getToDate());
			entity.setIncidentName(vo.getIncidentName().toUpperCase());
			entity.setOperationPeriod(StringUtility.hasValue(vo.getOperationalPeriod()) ? vo.getOperationalPeriod().toUpperCase() : "");
			if(LongUtility.hasValue(vo.getIncidentId()))
				entity.setIncidentId(vo.getIncidentId());
			if(LongUtility.hasValue(vo.getIncidentGroupId()))
				entity.setIncidentGroupId(vo.getIncidentGroupId());
			entity.setIsPlanLocked(StringBooleanEnum.toEnumValue(vo.getIsPlanLocked()));
		}
		
		return entity;
	}
	
	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the operationalPeriod
	 */
	public String getOperationalPeriod() {
		return operationalPeriod;
	}

	/**
	 * @param operationalPeriod the operationalPeriod to set
	 */
	public void setOperationalPeriod(String operationalPeriod) {
		this.operationalPeriod = operationalPeriod;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the fromDateTime
	 */
	public String getFromDateTime() {
		return fromDateTime;
	}

	/**
	 * @param fromDateTime the fromDateTime to set
	 */
	public void setFromDateTime(String fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	/**
	 * @return the fromDateString
	 */
	public String getFromDateString() {
		return fromDateString;
	}

	/**
	 * @param fromDateString the fromDateString to set
	 */
	public void setFromDateString(String fromDateString) {
		this.fromDateString = fromDateString;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the toDateTime
	 */
	public String getToDateTime() {
		return toDateTime;
	}

	/**
	 * @param toDateTime the toDateTime to set
	 */
	public void setToDateTime(String toDateTime) {
		this.toDateTime = toDateTime;
	}

	/**
	 * @return the toDateString
	 */
	public String getToDateString() {
		return toDateString;
	}

	/**
	 * @param toDateString the toDateString to set
	 */
	public void setToDateString(String toDateString) {
		this.toDateString = toDateString;
	}

	/**
	 * @return the isPlanLocked
	 */
	public Boolean getIsPlanLocked() {
		return isPlanLocked;
	}

	/**
	 * @param isPlanLocked the isPlanLocked to set
	 */
	public void setIsPlanLocked(Boolean isPlanLocked) {
		this.isPlanLocked = isPlanLocked;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the iapForm203Vos
	 */
	@JsonIgnore
	public Collection<IapForm203Vo> getIapForm203Vos() {
		return iapForm203Vos;
	}

	/**
	 * @param iapForm203Vos the iapForm203Vos to set
	 */
	@JsonIgnore
	public void setIapForm203Vos(Collection<IapForm203Vo> iapForm203Vos) {
		this.iapForm203Vos = iapForm203Vos;
	}

	/**
	 * @return the iapForm205Vos
	 */
	@JsonIgnore
	public Collection<IapForm205Vo> getIapForm205Vos() {
		return iapForm205Vos;
	}

	/**
	 * @param iapForm205Vos the iapForm205Vos to set
	 */
	@JsonIgnore
	public void setIapForm205Vos(Collection<IapForm205Vo> iapForm205Vos) {
		this.iapForm205Vos = iapForm205Vos;
	}

	/**
	 * @return the iapForm206Vos
	 */
	@JsonIgnore
	public Collection<IapForm206Vo> getIapForm206Vos() {
		return iapForm206Vos;
	}

	/**
	 * @param iapForm206Vos the iapForm206Vos to set
	 */
	@JsonIgnore
	public void setIapForm206Vos(Collection<IapForm206Vo> iapForm206Vos) {
		this.iapForm206Vos = iapForm206Vos;
	}

	/**
	 * @return the iapForm220Vos
	 */
	@JsonIgnore
	public Collection<IapForm220Vo> getIapForm220Vos() {
		return iapForm220Vos;
	}

	/**
	 * @param iapForm220Vos the iapForm220Vos to set
	 */
	@JsonIgnore
	public void setIapForm220Vos(Collection<IapForm220Vo> iapForm220Vos) {
		this.iapForm220Vos = iapForm220Vos;
	}

	/**
	 * @return the iapForm202Vos
	 */
	@JsonIgnore
	public Collection<IapForm202Vo> getIapForm202Vos() {
		return iapForm202Vos;
	}

	/**
	 * @param iapForm202Vos the iapForm202Vos to set
	 */
	@JsonIgnore
	public void setIapForm202Vos(Collection<IapForm202Vo> iapForm202Vos) {
		this.iapForm202Vos = iapForm202Vos;
	}

	/**
	 * @return the iapForm204Vos
	 */
	@JsonIgnore
	public Collection<IapForm204Vo> getIapForm204Vos() {
		return iapForm204Vos;
	}

	/**
	 * @param iapForm204Vos the iapForm204Vos to set
	 */
	@JsonIgnore
	public void setIapForm204Vos(Collection<IapForm204Vo> iapForm204Vos) {
		this.iapForm204Vos = iapForm204Vos;
	}
	
	
}
