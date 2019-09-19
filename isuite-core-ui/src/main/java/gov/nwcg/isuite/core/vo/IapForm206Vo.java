package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapMedicalAid;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.impl.IapForm206Impl;
import gov.nwcg.isuite.core.domain.impl.IapPlanImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IapForm206Vo extends AbstractVo {
	private Long iapPlanId;
	private Collection<IapMedicalAidVo> iapAmbulanceVos = new ArrayList<IapMedicalAidVo>();
	private Collection<IapMedicalAidVo> iapAirAmbulanceVos = new ArrayList<IapMedicalAidVo>();
	private Collection<IapHospitalVo> iapHospitalVos = new ArrayList<IapHospitalVo>();
	private Collection<IapAreaLocationCapabilityVo> iapAreaLocationCapabilityVos = new ArrayList<IapAreaLocationCapabilityVo>();
	private Collection<IapRemoteCampLocationsVo> iapRemoteCampLocationsVos = new ArrayList<IapRemoteCampLocationsVo>();
	private String preparedBy;
	private DateTransferVo preparedDateVo = new DateTransferVo();
	private String preparedTime;
	private String reviewedBy;
	private DateTransferVo reviewedDateVo = new DateTransferVo();;
	private String reviewedTime;
	private Boolean isFormLocked=false;
	
	public IapForm206Vo(){
		
	}

	public static IapForm206Vo getInstance(IapForm206 entity, Boolean cascadable) throws Exception {
		IapForm206Vo vo = new IapForm206Vo();
		
		if(null==entity)
			throw new Exception("Unable to create IapForm206Vo from null IapForm206 entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setIsFormLocked(StringBooleanEnum.toBooleanValue(entity.getIsFormLocked()));
			vo.setPreparedBy(entity.getPreparedBy());
			vo.setReviewedBy(entity.getReviewedBy());
			vo.setIapPlanId(entity.getIapPlanId());

			if(DateUtil.hasValue(entity.getPreparedDate())){
				DateTransferVo.populateDate(vo.getPreparedDateVo(), entity.getPreparedDate());
				vo.setPreparedTime(vo.getPreparedDateVo().getTimeString());
			}
			
			if(DateUtil.hasValue(entity.getReviewedDate())){
				DateTransferVo.populateDate(vo.getReviewedDateVo(), entity.getReviewedDate());
				vo.setReviewedTime(vo.getReviewedDateVo().getTimeString());
			}
			
			if(CollectionUtility.hasValue(entity.getIapMedicalAids())){
				for(IapMedicalAid ma : entity.getIapMedicalAids()){
					if(ma.getType().equals("AMBULANCE")){
						vo.getIapAmbulanceVos().add(IapMedicalAidVo.getInstance(ma, true));
					}
					if(ma.getType().equals("AIRAMBULANCE")){
						vo.getIapAirAmbulanceVos().add(IapMedicalAidVo.getInstance(ma, true));
					}
				}
			}
			
			if(CollectionUtility.hasValue(entity.getIapHospitals())){
				vo.setIapHospitalVos(IapHospitalVo.getInstances(entity.getIapHospitals(), true));
			}
			
			if(CollectionUtility.hasValue(entity.getIapAreaLocationCapabilities())){
				vo.setIapAreaLocationCapabilityVos(IapAreaLocationCapabilityVo.getInstances(entity.getIapAreaLocationCapabilities(), true));
			}
			
			if(CollectionUtility.hasValue(entity.getIapRemoteCampLocations())){
				vo.setIapRemoteCampLocationsVos(IapRemoteCampLocationsVo.getInstances(entity.getIapRemoteCampLocations(), true));
			}
		}
		
		return vo;
	}
	
	public static IapForm206 toEntity(IapForm206 entity, IapForm206Vo vo, Boolean cascadable,Persistable...persistables  ) throws Exception {
		if(null == entity) entity = new IapForm206Impl();
		
		entity.setId(vo.getId());
		
		IapPlan iapPlan =(IapPlan)AbstractVo.getPersistableObject(persistables, IapPlanImpl.class);
		if(null != iapPlan)
			entity.setIapPlan(iapPlan);
		else if(LongUtility.hasValue(vo.getIapPlanId())){
			iapPlan = new IapPlanImpl();
			iapPlan.setId(vo.getIapPlanId());
			entity.setIapPlan(iapPlan);
		}
		
		if(cascadable){
			entity.setIsFormLocked(StringBooleanEnum.toEnumValue(vo.getIsFormLocked()));
			entity.setPreparedBy(StringUtility.toUpper(vo.getPreparedBy()));
			entity.setReviewedBy(StringUtility.toUpper(vo.getReviewedBy()));

			entity.setIsAviationUtilized(StringBooleanEnum.N);
			
			if(DateTransferVo.hasDateString(vo.getPreparedDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getPreparedDateVo());
				if(StringUtility.hasValue(vo.getPreparedTime())){
					dt=DateUtil.addMilitaryTimeToDate(dt, vo.getPreparedTime());
				}
				entity.setPreparedDate(dt);
			}else
				entity.setPreparedDate(null);
			
			if(DateTransferVo.hasDateString(vo.getReviewedDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getReviewedDateVo());
				if(StringUtility.hasValue(vo.getReviewedTime())){
					dt=DateUtil.addMilitaryTimeToDate(dt, vo.getReviewedTime());
				}
				entity.setReviewedDate(dt);
			}else
				entity.setReviewedDate(null);
			
			// Common list for ambulance and air-ambulances
			Collection<IapMedicalAid> medicalAidEntities = new ArrayList<IapMedicalAid>();
			if(CollectionUtility.hasValue(vo.getIapAmbulanceVos())){
				medicalAidEntities = IapMedicalAidVo.toEntityList(vo.getIapAmbulanceVos(), Boolean.TRUE, entity); // returns non-null list
			}
			if(CollectionUtility.hasValue(vo.getIapAirAmbulanceVos())){
				medicalAidEntities.addAll(IapMedicalAidVo.toEntityList(vo.getIapAirAmbulanceVos(), Boolean.TRUE, entity));
			}
			// Set combined list of ambulance and air-ambulance.
			entity.setIapMedicalAids(medicalAidEntities);
			
			if(CollectionUtility.hasValue(vo.getIapHospitalVos())){
				entity.setIapHospitals(IapHospitalVo.toEntityList(vo.getIapHospitalVos(), Boolean.TRUE, entity));
			}
			
			if(CollectionUtility.hasValue(vo.getIapAreaLocationCapabilityVos())){
				entity.setIapAreaLocationCapabilities(IapAreaLocationCapabilityVo.toEntityList(vo.getIapAreaLocationCapabilityVos(), Boolean.TRUE, entity));
			}
			
			if(CollectionUtility.hasValue(vo.getIapRemoteCampLocationsVos())){
				entity.setIapRemoteCampLocations(IapRemoteCampLocationsVo.toEntityList(vo.getIapRemoteCampLocationsVos(), Boolean.TRUE, entity));
			}
		}
		
		return entity;
	}
	
	public static Collection<IapForm206> toEntityList(Collection<IapForm206Vo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapForm206> entities = new ArrayList<IapForm206>();

		for(IapForm206Vo vo : vos){
			entities.add(IapForm206Vo.toEntity(null, vo,cascadable, persistables));
		}

		return entities;
	}

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId() {
		return iapPlanId;
	}

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId) {
		this.iapPlanId = iapPlanId;
	}

	/**
	 * @return the iapHospitalVos
	 */
	public Collection<IapHospitalVo> getIapHospitalVos() {
		return iapHospitalVos;
	}

	/**
	 * @param iapHospitalVos the iapHospitalVos to set
	 */
	public void setIapHospitalVos(Collection<IapHospitalVo> iapHospitalVos) {
		this.iapHospitalVos = iapHospitalVos;
	}

	/**
	 * @return the iapAmbulanceVos
	 */
	public Collection<IapMedicalAidVo> getIapAmbulanceVos() {
		return iapAmbulanceVos;
	}

	/**
	 * @param iapAmbulanceVos the iapAmbulanceVos to set
	 */
	public void setIapAmbulanceVos(Collection<IapMedicalAidVo> iapAmbulanceVos) {
		this.iapAmbulanceVos = iapAmbulanceVos;
	}

	/**
	 * @return the iapAirAmbulanceVos
	 */
	public Collection<IapMedicalAidVo> getIapAirAmbulanceVos() {
		return iapAirAmbulanceVos;
	}

	/**
	 * @param iapAirAmbulanceVos the iapAirAmbulanceVos to set
	 */
	public void setIapAirAmbulanceVos(Collection<IapMedicalAidVo> iapAirAmbulanceVos) {
		this.iapAirAmbulanceVos = iapAirAmbulanceVos;
	}

	/**
	 * @return the iapAreaLocationCapabilityVos
	 */
	public Collection<IapAreaLocationCapabilityVo> getIapAreaLocationCapabilityVos() {
		return iapAreaLocationCapabilityVos;
	}

	/**
	 * @param iapAreaLocationCapabilityVos the iapAreaLocationCapabilityVos to set
	 */
	public void setIapAreaLocationCapabilityVos(
			Collection<IapAreaLocationCapabilityVo> iapAreaLocationCapabilityVos) {
		this.iapAreaLocationCapabilityVos = iapAreaLocationCapabilityVos;
	}

	/**
	 * @return the iapRemoteCampLocationsVos
	 */
	public Collection<IapRemoteCampLocationsVo> getIapRemoteCampLocationsVos() {
		return iapRemoteCampLocationsVos;
	}

	/**
	 * @param iapRemoteCampLocationsVos the iapRemoteCampLocationsVos to set
	 */
	public void setIapRemoteCampLocationsVos(
			Collection<IapRemoteCampLocationsVo> iapRemoteCampLocationsVos) {
		this.iapRemoteCampLocationsVos = iapRemoteCampLocationsVos;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedDateVo
	 */
	public DateTransferVo getPreparedDateVo() {
		return preparedDateVo;
	}

	/**
	 * @param preparedDateVo the preparedDateVo to set
	 */
	public void setPreparedDateVo(DateTransferVo preparedDateVo) {
		this.preparedDateVo = preparedDateVo;
	}

	/**
	 * @return the reviewedBy
	 */
	public String getReviewedBy() {
		return reviewedBy;
	}

	/**
	 * @param reviewedBy the reviewedBy to set
	 */
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	/**
	 * @return the reviewedDateVo
	 */
	public DateTransferVo getReviewedDateVo() {
		return reviewedDateVo;
	}

	/**
	 * @param reviewedDateVo the reviewedDateVo to set
	 */
	public void setReviewedDateVo(DateTransferVo reviewedDateVo) {
		this.reviewedDateVo = reviewedDateVo;
	}

	/**
	 * @return the preparedTime
	 */
	public String getPreparedTime() {
		return preparedTime;
	}

	/**
	 * @param preparedTime the preparedTime to set
	 */
	public void setPreparedTime(String preparedTime) {
		this.preparedTime = preparedTime;
	}

	/**
	 * @return the reviewedTime
	 */
	public String getReviewedTime() {
		return reviewedTime;
	}

	/**
	 * @param reviewedTime the reviewedTime to set
	 */
	public void setReviewedTime(String reviewedTime) {
		this.reviewedTime = reviewedTime;
	}

	/**
	 * @return the isFormLocked
	 */
	public Boolean getIsFormLocked() {
		return isFormLocked;
	}

	/**
	 * @param isFormLocked the isFormLocked to set
	 */
	public void setIsFormLocked(Boolean isFormLocked) {
		this.isFormLocked = isFormLocked;
	}
	
}
