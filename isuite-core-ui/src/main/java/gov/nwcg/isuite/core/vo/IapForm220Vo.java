package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapAircraft;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.impl.IapForm220Impl;
import gov.nwcg.isuite.core.domain.impl.IapPlanImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IapForm220Vo extends AbstractVo {
	private Long iapPlanId;
	private String sunrise;
	private String sunset;
	private String readyAlertAircraft;
	private String medivac;
	private String newIncident;
	private String altitude;
	private String centralPoint;
	private String remarks;
	private String preparedBy;
	private String preparedByPosition;
	private DateTransferVo preparedByDateVo = new DateTransferVo();
	private Boolean isFormLocked=false;

	private Collection<IapAircraftVo> iapAircraftVos = new ArrayList<IapAircraftVo>();
	
	private Collection<IapAircraftVo> iapFixedWingVos = new ArrayList<IapAircraftVo>();
	private Collection<IapAircraftVo> iapHelicopterVos = new ArrayList<IapAircraftVo>();
	
	private Collection<IapAircraftFrequencyVo> iapAircraftFrequencyVos = new ArrayList<IapAircraftFrequencyVo>();
	private Collection<IapAircraftTaskVo> iapAircraftTaskVos = new ArrayList<IapAircraftTaskVo>();
	private Collection<IapPersonnelVo> iapPersonnelVos = new ArrayList<IapPersonnelVo>();
	
	// placeholders for 8 Personnel
	private IapPersonnelVo iapPersonnel1;
	private IapPersonnelVo iapPersonnel2;
	private IapPersonnelVo iapPersonnel3;
	private IapPersonnelVo iapPersonnel4;
	private IapPersonnelVo iapPersonnel5;
	private IapPersonnelVo iapPersonnel6;
	private IapPersonnelVo iapPersonnel7;
	private IapPersonnelVo iapPersonnel8;
	private IapPersonnelVo iapPersonnel9;
	private IapPersonnelVo iapPersonnel10;
	private IapPersonnelVo iapPersonnel11;

	// placeholders for 9 Aircraft Frequencies
	private IapAircraftFrequencyVo iapAircraftFrequency1;
	private IapAircraftFrequencyVo iapAircraftFrequency2;
	private IapAircraftFrequencyVo iapAircraftFrequency3;
	private IapAircraftFrequencyVo iapAircraftFrequency4;
	private IapAircraftFrequencyVo iapAircraftFrequency5;
	private IapAircraftFrequencyVo iapAircraftFrequency6;
	private IapAircraftFrequencyVo iapAircraftFrequency7;
	private IapAircraftFrequencyVo iapAircraftFrequency8;
	private IapAircraftFrequencyVo iapAircraftFrequency9;
	private IapAircraftFrequencyVo iapAircraftFrequency10;
	private IapAircraftFrequencyVo iapAircraftFrequency11;
	private IapAircraftFrequencyVo iapAircraftFrequency12;

	// placeholders for 8 Fixed Wing
	private IapAircraftVo iapFixedWing1;
	private IapAircraftVo iapFixedWing2;
	private IapAircraftVo iapFixedWing3;
	private IapAircraftVo iapFixedWing4;
	private IapAircraftVo iapFixedWing5;
	private IapAircraftVo iapFixedWing6;
	private IapAircraftVo iapFixedWing7;
	private IapAircraftVo iapFixedWing8;
	private IapAircraftVo iapFixedWing9;
	private IapAircraftVo iapFixedWing10;
	private IapAircraftVo iapFixedWing11;
	private IapAircraftVo iapFixedWing12;
	
	public IapForm220Vo(){
		
	}
	
	public static IapForm220Vo getInstance(IapForm220 entity, Boolean cascadable) throws Exception {
		IapForm220Vo vo = new IapForm220Vo();
		
		if(null==entity)
			throw new Exception("Unable to create IapForm220Vo from null IapForm220 entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setAltitude(entity.getAltitude());
			vo.setCentralPoint(entity.getCentralPoint());
			vo.setIapPlanId(entity.getIapPlanId());
			vo.setMedivac(entity.getMedivac());
			vo.setNewIncident(entity.getNewIncident());
			vo.setPreparedBy(entity.getPreparedBy());
			vo.setPreparedByPosition(entity.getPreparedByPosition());
			vo.setReadyAlertAircraft(entity.getReadyAlertAircraft());
			vo.setRemarks(entity.getRemarks());
			vo.setSunrise(entity.getSunrise());
			vo.setSunset(entity.getSunset());
			vo.setIsFormLocked(StringBooleanEnum.toBooleanValue(entity.getIsFormLocked()));

			if(DateUtil.hasValue(entity.getPreparedDate())) {
				DateTransferVo.populateDate(vo.getPreparedByDateVo(), entity.getPreparedDate());
			} else {
				vo.setPreparedByDateVo(new DateTransferVo());
			}
		
			if(CollectionUtility.hasValue(entity.getIapAircrafts()))
				vo.setIapAircraftVos(IapAircraftVo.getInstances(entity.getIapAircrafts(), true));
			
			if(CollectionUtility.hasValue(entity.getIapAircraftFrequencies()))
				vo.setIapAircraftFrequencyVos(IapAircraftFrequencyVo.getInstances(entity.getIapAircraftFrequencies(), true));
			
			if(CollectionUtility.hasValue(entity.getIapAircraftTasks()))
				vo.setIapAircraftTaskVos(IapAircraftTaskVo.getInstances(entity.getIapAircraftTasks(), true));
			
			if(CollectionUtility.hasValue(entity.getIapPersonnels())){
				vo.setIapPersonnelVos(IapPersonnelVo.getInstances(entity.getIapPersonnels(), true,"220"));
				
				int idx=1;
				for(IapPersonnelVo ipvo : vo.getIapPersonnelVos()){
					switch(idx){
						case 1:
							vo.setIapPersonnel1(ipvo);
							break;
						case 2:
							vo.setIapPersonnel2(ipvo);
							break;
						case 3:
							vo.setIapPersonnel3(ipvo);
							break;
						case 4:
							vo.setIapPersonnel4(ipvo);
							break;
						case 5:
							vo.setIapPersonnel5(ipvo);
							break;
						case 6:
							vo.setIapPersonnel6(ipvo);
							break;
						case 7:
							vo.setIapPersonnel7(ipvo);
							break;
						case 8:
							vo.setIapPersonnel8(ipvo);
							break;
						case 9:
							vo.setIapPersonnel9(ipvo);
							break;
						case 10:
							vo.setIapPersonnel10(ipvo);
							break;
						case 11:
							vo.setIapPersonnel11(ipvo);
							break;
					}
					idx++;
				}
			}

			if(CollectionUtility.hasValue(entity.getIapAircrafts())){
				vo.setIapAircraftVos(IapAircraftVo.getInstances(entity.getIapAircrafts(), true));
				int idx=1;
				for(IapAircraftVo avo : vo.getIapAircraftVos()){
					if(StringUtility.hasValue(avo.getWingType()) && avo.getWingType().equals("FIXED")){
						int posNum=1;
						if(IntegerUtility.hasValue(avo.getPositionNum()))
							posNum=avo.getPositionNum().intValue();
						
						switch(posNum){
							case 1:
								vo.setIapFixedWing1(avo);
								break;
							case 2:
								vo.setIapFixedWing2(avo);
								break;
							case 3:
								vo.setIapFixedWing3(avo);
								break;
							case 4:
								vo.setIapFixedWing4(avo);
								break;
							case 5:
								vo.setIapFixedWing5(avo);
								break;
							case 6:
								vo.setIapFixedWing6(avo);
								break;
							case 7:
								vo.setIapFixedWing7(avo);
								break;
							case 8:
								vo.setIapFixedWing8(avo);
								break;
							case 9:
								vo.setIapFixedWing9(avo);
								break;
							case 10:
								vo.setIapFixedWing10(avo);
								break;
							case 11:
								vo.setIapFixedWing11(avo);
								break;
							case 12:
								vo.setIapFixedWing12(avo);
								break;
						}
						if(idx<13)
							vo.getIapFixedWingVos().add(avo);
						else
							break;
						idx++;
					}
					
					if(StringUtility.hasValue(avo.getWingType()) && avo.getWingType().equals("HELI")){
						vo.getIapHelicopterVos().add(avo);
					}
					
				}
			}
			
			if(CollectionUtility.hasValue(entity.getIapAircraftFrequencies())){
				vo.setIapAircraftFrequencyVos(IapAircraftFrequencyVo.getInstances(entity.getIapAircraftFrequencies(), true));
				int idx=1;
				for(IapAircraftFrequencyVo iafvo : vo.getIapAircraftFrequencyVos()){
					switch(idx){
						case 1:
							vo.setIapAircraftFrequency1(iafvo);
							break;
						case 2:
							vo.setIapAircraftFrequency2(iafvo);
							break;
						case 3:
							vo.setIapAircraftFrequency3(iafvo);
							break;
						case 4:
							vo.setIapAircraftFrequency4(iafvo);
							break;
						case 5:
							vo.setIapAircraftFrequency5(iafvo);
							break;
						case 6:
							vo.setIapAircraftFrequency6(iafvo);
							break;
						case 7:
							vo.setIapAircraftFrequency7(iafvo);
							break;
						case 8:
							vo.setIapAircraftFrequency8(iafvo);
							break;
						case 9:
							vo.setIapAircraftFrequency9(iafvo);
							break;
						case 10:
							vo.setIapAircraftFrequency10(iafvo);
							break;
						case 11:
							vo.setIapAircraftFrequency11(iafvo);
							break;
						case 12:
							vo.setIapAircraftFrequency12(iafvo);
							break;
					}
					idx++;
				}
			}

			
		}
		
		return vo;
	}
	
	public static IapForm220 toEntity(IapForm220 entity, IapForm220Vo vo, Boolean cascadable,Persistable...persistables  ) throws Exception {
		if(null == entity) entity = new IapForm220Impl();
		
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
			entity.setSunrise(vo.getSunrise());
			entity.setSunset(vo.getSunset());
			entity.setAltitude(StringUtility.toUpper(vo.getAltitude()));
			entity.setCentralPoint(StringUtility.toUpper(vo.getCentralPoint()));
			entity.setRemarks(vo.getRemarks());
			entity.setNewIncident(StringUtility.toUpper(vo.getNewIncident()));
			entity.setMedivac(StringUtility.toUpper(vo.getMedivac()));
			entity.setReadyAlertAircraft(StringUtility.toUpper(vo.getReadyAlertAircraft()));
			entity.setPreparedBy(StringUtility.toUpper(vo.getPreparedBy()));
			entity.setPreparedByPosition(StringUtility.toUpper(vo.getPreparedByPosition()));

			if(DateTransferVo.hasDateString(vo.getPreparedByDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getPreparedByDateVo());
				entity.setPreparedDate(dt);
			} else {
				entity.setPreparedDate(null);
			}
			
			entity.setIsFormLocked(StringBooleanEnum.toEnumValue(vo.getIsFormLocked()));

			if(CollectionUtility.hasValue(vo.getIapPersonnelVos())){
				entity.setIapPersonnels(IapPersonnelVo.toEntityList(vo.getIapPersonnelVos(), true, entity));
			}
			
			Collection<IapAircraft> iapAircrafts = new ArrayList<IapAircraft>();
			
			if(CollectionUtility.hasValue(vo.getIapFixedWingVos())){
				Collection<IapAircraft> tmpAircrafts = IapAircraftVo.toEntityList(vo.getIapFixedWingVos(), true, entity);
				if(CollectionUtility.hasValue(tmpAircrafts))
					iapAircrafts.addAll(tmpAircrafts);
				//entity.setIapAircrafts(IapAircraftVo.toEntityList(vo.getIapFixedWingVos(), true, entity));
			}
			
			if(CollectionUtility.hasValue(vo.getIapHelicopterVos())){
				//if(entity.getIapAircrafts()==null) {
				//	entity.setIapAircrafts(new ArrayList<IapAircraft>());
				//}
				Collection<IapAircraft> tmpAircrafts = IapAircraftVo.toEntityList(vo.getIapHelicopterVos(), true, entity);
				if(CollectionUtility.hasValue(tmpAircrafts))
					iapAircrafts.addAll(tmpAircrafts);
				//entity.getIapAircrafts().addAll(IapAircraftVo.toEntityList(vo.getIapHelicopterVos(), true, entity));
			}
			
			if(CollectionUtility.hasValue(iapAircrafts)){
				entity.setIapAircrafts(iapAircrafts);
			}else{
				entity.setIapAircrafts(new ArrayList<IapAircraft>());
			}
			/*
			if(CollectionUtility.hasValue(vo.getIapAircraftVos())){
				if(entity.getIapAircrafts()==null) {
					entity.setIapAircrafts(new ArrayList<IapAircraft>());
				}
				entity.setIapAircrafts(IapAircraftVo.toEntityList(vo.getIapAircraftVos(), true, entity));
			}
			*/
			
			if(CollectionUtility.hasValue(vo.getIapAircraftFrequencyVos())){
				entity.setIapAircraftFrequencies(IapAircraftFrequencyVo.toEntityList(vo.getIapAircraftFrequencyVos(), true, entity));
			}
			
			if(CollectionUtility.hasValue(vo.getIapAircraftTaskVos())){
				entity.setIapAircraftTasks(IapAircraftTaskVo.toEntityList(vo.getIapAircraftTaskVos(), true, entity));
			}
		}
		
		return entity;
	}
	
	public static Collection<IapForm220> toEntityList(Collection<IapForm220Vo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapForm220> entities = new ArrayList<IapForm220>();

		for(IapForm220Vo vo : vos){
			entities.add(IapForm220Vo.toEntity(null, vo,cascadable, persistables));
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
	 * @return the sunrise
	 */
	public String getSunrise() {
		return sunrise;
	}

	/**
	 * @param sunrise the sunrise to set
	 */
	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	/**
	 * @return the sunset
	 */
	public String getSunset() {
		return sunset;
	}

	/**
	 * @param sunset the sunset to set
	 */
	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	/**
	 * @return the readyAlertAircraft
	 */
	public String getReadyAlertAircraft() {
		return readyAlertAircraft;
	}

	/**
	 * @param readyAlertAircraft the readyAlertAircraft to set
	 */
	public void setReadyAlertAircraft(String readyAlertAircraft) {
		this.readyAlertAircraft = readyAlertAircraft;
	}

	/**
	 * @return the medivac
	 */
	public String getMedivac() {
		return medivac;
	}

	/**
	 * @param medivac the medivac to set
	 */
	public void setMedivac(String medivac) {
		this.medivac = medivac;
	}

	/**
	 * @return the newIncident
	 */
	public String getNewIncident() {
		return newIncident;
	}

	/**
	 * @param newIncident the newIncident to set
	 */
	public void setNewIncident(String newIncident) {
		this.newIncident = newIncident;
	}

	/**
	 * @return the altitude
	 */
	public String getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	/**
	 * @return the centralPoint
	 */
	public String getCentralPoint() {
		return centralPoint;
	}

	/**
	 * @param centralPoint the centralPoint to set
	 */
	public void setCentralPoint(String centralPoint) {
		this.centralPoint = centralPoint;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition() {
		return preparedByPosition;
	}

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition) {
		this.preparedByPosition = preparedByPosition;
	}

	/**
	 * @return the preparedByDateVo
	 */
	public DateTransferVo getPreparedByDateVo() {
		return preparedByDateVo;
	}

	/**
	 * @param preparedByDateVo the preparedByDateVo to set
	 */
	public void setPreparedByDateVo(DateTransferVo preparedByDateVo) {
		this.preparedByDateVo = preparedByDateVo;
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

	/**
	 * @return the iapAircraftVos
	 */
	public Collection<IapAircraftVo> getIapAircraftVos() {
		return iapAircraftVos;
	}

	/**
	 * @param iapAircraftVos the iapAircraftVos to set
	 */
	public void setIapAircraftVos(Collection<IapAircraftVo> iapAircraftVos) {
		this.iapAircraftVos = iapAircraftVos;
	}

	/**
	 * @return the iapAircraftFrequencyVos
	 */
	public Collection<IapAircraftFrequencyVo> getIapAircraftFrequencyVos() {
		return iapAircraftFrequencyVos;
	}

	/**
	 * @param iapAircraftFrequencyVos the iapAircraftFrequencyVos to set
	 */
	public void setIapAircraftFrequencyVos(
			Collection<IapAircraftFrequencyVo> iapAircraftFrequencyVos) {
		this.iapAircraftFrequencyVos = iapAircraftFrequencyVos;
	}

	/**
	 * @return the iapAircraftTaskVos
	 */
	public Collection<IapAircraftTaskVo> getIapAircraftTaskVos() {
		return iapAircraftTaskVos;
	}

	/**
	 * @param iapAircraftTaskVos the iapAircraftTaskVos to set
	 */
	public void setIapAircraftTaskVos(
			Collection<IapAircraftTaskVo> iapAircraftTaskVos) {
		this.iapAircraftTaskVos = iapAircraftTaskVos;
	}

	/**
	 * @return the iapPersonnelVos
	 */
	public Collection<IapPersonnelVo> getIapPersonnelVos() {
		return iapPersonnelVos;
	}

	/**
	 * @param iapPersonnelVos the iapPersonnelVos to set
	 */
	public void setIapPersonnelVos(Collection<IapPersonnelVo> iapPersonnelVos) {
		this.iapPersonnelVos = iapPersonnelVos;
	}

	/**
	 * @return the iapPersonnel1
	 */
	public IapPersonnelVo getIapPersonnel1() {
		return iapPersonnel1;
	}

	/**
	 * @param iapPersonnel1 the iapPersonnel1 to set
	 */
	public void setIapPersonnel1(IapPersonnelVo iapPersonnel1) {
		this.iapPersonnel1 = iapPersonnel1;
	}

	/**
	 * @return the iapPersonnel2
	 */
	public IapPersonnelVo getIapPersonnel2() {
		return iapPersonnel2;
	}

	/**
	 * @param iapPersonnel2 the iapPersonnel2 to set
	 */
	public void setIapPersonnel2(IapPersonnelVo iapPersonnel2) {
		this.iapPersonnel2 = iapPersonnel2;
	}

	/**
	 * @return the iapPersonnel3
	 */
	public IapPersonnelVo getIapPersonnel3() {
		return iapPersonnel3;
	}

	/**
	 * @param iapPersonnel3 the iapPersonnel3 to set
	 */
	public void setIapPersonnel3(IapPersonnelVo iapPersonnel3) {
		this.iapPersonnel3 = iapPersonnel3;
	}

	/**
	 * @return the iapPersonnel4
	 */
	public IapPersonnelVo getIapPersonnel4() {
		return iapPersonnel4;
	}

	/**
	 * @param iapPersonnel4 the iapPersonnel4 to set
	 */
	public void setIapPersonnel4(IapPersonnelVo iapPersonnel4) {
		this.iapPersonnel4 = iapPersonnel4;
	}

	/**
	 * @return the iapPersonnel5
	 */
	public IapPersonnelVo getIapPersonnel5() {
		return iapPersonnel5;
	}

	/**
	 * @param iapPersonnel5 the iapPersonnel5 to set
	 */
	public void setIapPersonnel5(IapPersonnelVo iapPersonnel5) {
		this.iapPersonnel5 = iapPersonnel5;
	}

	/**
	 * @return the iapPersonnel6
	 */
	public IapPersonnelVo getIapPersonnel6() {
		return iapPersonnel6;
	}

	/**
	 * @param iapPersonnel6 the iapPersonnel6 to set
	 */
	public void setIapPersonnel6(IapPersonnelVo iapPersonnel6) {
		this.iapPersonnel6 = iapPersonnel6;
	}

	/**
	 * @return the iapPersonnel7
	 */
	public IapPersonnelVo getIapPersonnel7() {
		return iapPersonnel7;
	}

	/**
	 * @param iapPersonnel7 the iapPersonnel7 to set
	 */
	public void setIapPersonnel7(IapPersonnelVo iapPersonnel7) {
		this.iapPersonnel7 = iapPersonnel7;
	}

	/**
	 * @return the iapPersonnel8
	 */
	public IapPersonnelVo getIapPersonnel8() {
		return iapPersonnel8;
	}

	/**
	 * @param iapPersonnel8 the iapPersonnel8 to set
	 */
	public void setIapPersonnel8(IapPersonnelVo iapPersonnel8) {
		this.iapPersonnel8 = iapPersonnel8;
	}

	/**
	 * @return the iapAircraftFrequency1
	 */
	public IapAircraftFrequencyVo getIapAircraftFrequency1() {
		return iapAircraftFrequency1;
	}

	/**
	 * @param iapAircraftFrequency1 the iapAircraftFrequency1 to set
	 */
	public void setIapAircraftFrequency1(
			IapAircraftFrequencyVo iapAircraftFrequency1) {
		this.iapAircraftFrequency1 = iapAircraftFrequency1;
	}

	/**
	 * @return the iapAircraftFrequency2
	 */
	public IapAircraftFrequencyVo getIapAircraftFrequency2() {
		return iapAircraftFrequency2;
	}

	/**
	 * @param iapAircraftFrequency2 the iapAircraftFrequency2 to set
	 */
	public void setIapAircraftFrequency2(
			IapAircraftFrequencyVo iapAircraftFrequency2) {
		this.iapAircraftFrequency2 = iapAircraftFrequency2;
	}

	/**
	 * @return the iapAircraftFrequency3
	 */
	public IapAircraftFrequencyVo getIapAircraftFrequency3() {
		return iapAircraftFrequency3;
	}

	/**
	 * @param iapAircraftFrequency3 the iapAircraftFrequency3 to set
	 */
	public void setIapAircraftFrequency3(
			IapAircraftFrequencyVo iapAircraftFrequency3) {
		this.iapAircraftFrequency3 = iapAircraftFrequency3;
	}

	/**
	 * @return the iapAircraftFrequency4
	 */
	public IapAircraftFrequencyVo getIapAircraftFrequency4() {
		return iapAircraftFrequency4;
	}

	/**
	 * @param iapAircraftFrequency4 the iapAircraftFrequency4 to set
	 */
	public void setIapAircraftFrequency4(
			IapAircraftFrequencyVo iapAircraftFrequency4) {
		this.iapAircraftFrequency4 = iapAircraftFrequency4;
	}

	/**
	 * @return the iapAircraftFrequency5
	 */
	public IapAircraftFrequencyVo getIapAircraftFrequency5() {
		return iapAircraftFrequency5;
	}

	/**
	 * @param iapAircraftFrequency5 the iapAircraftFrequency5 to set
	 */
	public void setIapAircraftFrequency5(
			IapAircraftFrequencyVo iapAircraftFrequency5) {
		this.iapAircraftFrequency5 = iapAircraftFrequency5;
	}

	/**
	 * @return the iapAircraftFrequency6
	 */
	public IapAircraftFrequencyVo getIapAircraftFrequency6() {
		return iapAircraftFrequency6;
	}

	/**
	 * @param iapAircraftFrequency6 the iapAircraftFrequency6 to set
	 */
	public void setIapAircraftFrequency6(
			IapAircraftFrequencyVo iapAircraftFrequency6) {
		this.iapAircraftFrequency6 = iapAircraftFrequency6;
	}

	/**
	 * @return the iapAircraftFrequency7
	 */
	public IapAircraftFrequencyVo getIapAircraftFrequency7() {
		return iapAircraftFrequency7;
	}

	/**
	 * @param iapAircraftFrequency7 the iapAircraftFrequency7 to set
	 */
	public void setIapAircraftFrequency7(
			IapAircraftFrequencyVo iapAircraftFrequency7) {
		this.iapAircraftFrequency7 = iapAircraftFrequency7;
	}

	/**
	 * @return the iapAircraftFrequency8
	 */
	public IapAircraftFrequencyVo getIapAircraftFrequency8() {
		return iapAircraftFrequency8;
	}

	/**
	 * @param iapAircraftFrequency8 the iapAircraftFrequency8 to set
	 */
	public void setIapAircraftFrequency8(
			IapAircraftFrequencyVo iapAircraftFrequency8) {
		this.iapAircraftFrequency8 = iapAircraftFrequency8;
	}

	/**
	 * @return the iapAircraftFrequency9
	 */
	public IapAircraftFrequencyVo getIapAircraftFrequency9() {
		return iapAircraftFrequency9;
	}

	/**
	 * @param iapAircraftFrequency9 the iapAircraftFrequency9 to set
	 */
	public void setIapAircraftFrequency9(
			IapAircraftFrequencyVo iapAircraftFrequency9) {
		this.iapAircraftFrequency9 = iapAircraftFrequency9;
	}

	/**
	 * @return the iapFixedWing1
	 */
	public IapAircraftVo getIapFixedWing1() {
		return iapFixedWing1;
	}

	/**
	 * @param iapFixedWing1 the iapFixedWing1 to set
	 */
	public void setIapFixedWing1(IapAircraftVo iapFixedWing1) {
		this.iapFixedWing1 = iapFixedWing1;
	}

	/**
	 * @return the iapFixedWing2
	 */
	public IapAircraftVo getIapFixedWing2() {
		return iapFixedWing2;
	}

	/**
	 * @param iapFixedWing2 the iapFixedWing2 to set
	 */
	public void setIapFixedWing2(IapAircraftVo iapFixedWing2) {
		this.iapFixedWing2 = iapFixedWing2;
	}

	/**
	 * @return the iapFixedWing3
	 */
	public IapAircraftVo getIapFixedWing3() {
		return iapFixedWing3;
	}

	/**
	 * @param iapFixedWing3 the iapFixedWing3 to set
	 */
	public void setIapFixedWing3(IapAircraftVo iapFixedWing3) {
		this.iapFixedWing3 = iapFixedWing3;
	}

	/**
	 * @return the iapFixedWing4
	 */
	public IapAircraftVo getIapFixedWing4() {
		return iapFixedWing4;
	}

	/**
	 * @param iapFixedWing4 the iapFixedWing4 to set
	 */
	public void setIapFixedWing4(IapAircraftVo iapFixedWing4) {
		this.iapFixedWing4 = iapFixedWing4;
	}

	/**
	 * @return the iapFixedWing5
	 */
	public IapAircraftVo getIapFixedWing5() {
		return iapFixedWing5;
	}

	/**
	 * @param iapFixedWing5 the iapFixedWing5 to set
	 */
	public void setIapFixedWing5(IapAircraftVo iapFixedWing5) {
		this.iapFixedWing5 = iapFixedWing5;
	}

	/**
	 * @return the iapFixedWing6
	 */
	public IapAircraftVo getIapFixedWing6() {
		return iapFixedWing6;
	}

	/**
	 * @param iapFixedWing6 the iapFixedWing6 to set
	 */
	public void setIapFixedWing6(IapAircraftVo iapFixedWing6) {
		this.iapFixedWing6 = iapFixedWing6;
	}

	/**
	 * @return the iapFixedWing7
	 */
	public IapAircraftVo getIapFixedWing7() {
		return iapFixedWing7;
	}

	/**
	 * @param iapFixedWing7 the iapFixedWing7 to set
	 */
	public void setIapFixedWing7(IapAircraftVo iapFixedWing7) {
		this.iapFixedWing7 = iapFixedWing7;
	}

	/**
	 * @return the iapFixedWing8
	 */
	public IapAircraftVo getIapFixedWing8() {
		return iapFixedWing8;
	}

	/**
	 * @param iapFixedWing8 the iapFixedWing8 to set
	 */
	public void setIapFixedWing8(IapAircraftVo iapFixedWing8) {
		this.iapFixedWing8 = iapFixedWing8;
	}

	/**
	 * @return the iapFixedWingVos
	 */
	public Collection<IapAircraftVo> getIapFixedWingVos() {
		return iapFixedWingVos;
	}

	/**
	 * @param iapFixedWingVos the iapFixedWingVos to set
	 */
	public void setIapFixedWingVos(Collection<IapAircraftVo> iapFixedWingVos) {
		this.iapFixedWingVos = iapFixedWingVos;
	}

	/**
	 * @return the iapHelicopterVos
	 */
	public Collection<IapAircraftVo> getIapHelicopterVos() {
		return iapHelicopterVos;
	}

	/**
	 * @param iapHelicopterVos the iapHelicopterVos to set
	 */
	public void setIapHelicopterVos(Collection<IapAircraftVo> iapHelicopterVos) {
		this.iapHelicopterVos = iapHelicopterVos;
	}

	/**
	 * @return the iapPersonnel9
	 */
	public IapPersonnelVo getIapPersonnel9() {
		return iapPersonnel9;
	}

	/**
	 * @param iapPersonnel9 the iapPersonnel9 to set
	 */
	public void setIapPersonnel9(IapPersonnelVo iapPersonnel9) {
		this.iapPersonnel9 = iapPersonnel9;
	}

	/**
	 * @return the iapPersonnel10
	 */
	public IapPersonnelVo getIapPersonnel10() {
		return iapPersonnel10;
	}

	/**
	 * @param iapPersonnel10 the iapPersonnel10 to set
	 */
	public void setIapPersonnel10(IapPersonnelVo iapPersonnel10) {
		this.iapPersonnel10 = iapPersonnel10;
	}

	/**
	 * @return the iapPersonnel11
	 */
	public IapPersonnelVo getIapPersonnel11() {
		return iapPersonnel11;
	}

	/**
	 * @param iapPersonnel11 the iapPersonnel11 to set
	 */
	public void setIapPersonnel11(IapPersonnelVo iapPersonnel11) {
		this.iapPersonnel11 = iapPersonnel11;
	}

	/**
	 * @return the iapAircraftFrequency10
	 */
	public IapAircraftFrequencyVo getIapAircraftFrequency10() {
		return iapAircraftFrequency10;
	}

	/**
	 * @param iapAircraftFrequency10 the iapAircraftFrequency10 to set
	 */
	public void setIapAircraftFrequency10(
			IapAircraftFrequencyVo iapAircraftFrequency10) {
		this.iapAircraftFrequency10 = iapAircraftFrequency10;
	}

	/**
	 * @return the iapAircraftFrequency11
	 */
	public IapAircraftFrequencyVo getIapAircraftFrequency11() {
		return iapAircraftFrequency11;
	}

	/**
	 * @param iapAircraftFrequency11 the iapAircraftFrequency11 to set
	 */
	public void setIapAircraftFrequency11(
			IapAircraftFrequencyVo iapAircraftFrequency11) {
		this.iapAircraftFrequency11 = iapAircraftFrequency11;
	}

	/**
	 * @return the iapAircraftFrequency12
	 */
	public IapAircraftFrequencyVo getIapAircraftFrequency12() {
		return iapAircraftFrequency12;
	}

	/**
	 * @param iapAircraftFrequency12 the iapAircraftFrequency12 to set
	 */
	public void setIapAircraftFrequency12(
			IapAircraftFrequencyVo iapAircraftFrequency12) {
		this.iapAircraftFrequency12 = iapAircraftFrequency12;
	}

	/**
	 * @return the iapFixedWing9
	 */
	public IapAircraftVo getIapFixedWing9() {
		return iapFixedWing9;
	}

	/**
	 * @param iapFixedWing9 the iapFixedWing9 to set
	 */
	public void setIapFixedWing9(IapAircraftVo iapFixedWing9) {
		this.iapFixedWing9 = iapFixedWing9;
	}

	/**
	 * @return the iapFixedWing10
	 */
	public IapAircraftVo getIapFixedWing10() {
		return iapFixedWing10;
	}

	/**
	 * @param iapFixedWing10 the iapFixedWing10 to set
	 */
	public void setIapFixedWing10(IapAircraftVo iapFixedWing10) {
		this.iapFixedWing10 = iapFixedWing10;
	}

	/**
	 * @return the iapFixedWing11
	 */
	public IapAircraftVo getIapFixedWing11() {
		return iapFixedWing11;
	}

	/**
	 * @param iapFixedWing11 the iapFixedWing11 to set
	 */
	public void setIapFixedWing11(IapAircraftVo iapFixedWing11) {
		this.iapFixedWing11 = iapFixedWing11;
	}

	/**
	 * @return the iapFixedWing12
	 */
	public IapAircraftVo getIapFixedWing12() {
		return iapFixedWing12;
	}

	/**
	 * @param iapFixedWing12 the iapFixedWing12 to set
	 */
	public void setIapFixedWing12(IapAircraftVo iapFixedWing12) {
		this.iapFixedWing12 = iapFixedWing12;
	}

	
}
