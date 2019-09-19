package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.impl.IapForm203Impl;
import gov.nwcg.isuite.core.domain.impl.IapPlanImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IapForm203Vo extends AbstractVo {
	private Long iapPlanId;
	private String preparedBy;
	private String preparedByPosition;
	private DateTransferVo preparedDateVo = new DateTransferVo();
	private String preparedTime;
	private Boolean isFormLocked=false;
	private Boolean isNoBranch=true;
	private Collection<IapPersonnelVo> iapPersonnelVos = new ArrayList<IapPersonnelVo>();
	private Collection<IapPersonnelVo> iapCommanderVos = new ArrayList<IapPersonnelVo>();
	private Collection<IapPersonnelVo> iapLogisticsVos = new ArrayList<IapPersonnelVo>();
	private Collection<IapPersonnelVo> iapPlanningVos = new ArrayList<IapPersonnelVo>();
	private Collection<IapPersonnelVo> iapAirOpsVos = new ArrayList<IapPersonnelVo>();
	private Collection<IapPersonnelVo> iapFinanceVos = new ArrayList<IapPersonnelVo>();
	private Collection<IapPersonnelVo> iapOperationsVos = new ArrayList<IapPersonnelVo>();
	private Collection<IapPersonnelVo> iapBranchVos = new ArrayList<IapPersonnelVo>();
	private Collection<IapPersonnelVo> iapAgencyVos = new ArrayList<IapPersonnelVo>();
	
	public IapForm203Vo(){
		
	}
	
	public static IapForm203Vo getInstance(IapForm203 entity, Boolean cascadable) throws Exception {
		IapForm203Vo vo = new IapForm203Vo();
		
		if(null==entity)
			throw new Exception("Unable to create IapForm203Vo from null IapForm203 entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setIapPlanId(entity.getIapPlanId());
			vo.setIsFormLocked(StringBooleanEnum.toBooleanValue(entity.getIsFormLocked()));
			vo.setIsNoBranch(StringBooleanEnum.toBooleanValue(entity.getIsNoBranch()));
			vo.setPreparedBy(entity.getPreparedBy());
			vo.setPreparedByPosition(entity.getPreparedByPosition());
			if(DateUtil.hasValue(entity.getPreparedDate())){
				DateTransferVo.populateDate(vo.getPreparedDateVo(), entity.getPreparedDate());
				vo.setPreparedTime(vo.getPreparedDateVo().getTimeString());
			}
			
			if(CollectionUtility.hasValue(entity.getIapPersonnels())){
				vo.setIapPersonnelVos(IapPersonnelVo.getInstances(entity.getIapPersonnels(), true,"203"));
				for(IapPersonnelVo v : vo.getIapPersonnelVos()){
					if(v.getSection().equals("INCIDENT_COMMANDER")){
						vo.getIapCommanderVos().add(v);
					}
					if(v.getSection().equals("PLANNING_SECTION")){
						vo.getIapPlanningVos().add(v);
					}
					if(v.getSection().equals("FINANCE_SECTION")){
						vo.getIapFinanceVos().add(v);
					}
					if(v.getSection().equals("LOGISTICS_SECTION")){
						vo.getIapLogisticsVos().add(v);
					}
					if(v.getSection().equals("OPERATIONS_SECTION")){
						vo.getIapOperationsVos().add(v);
					}
					if(v.getSection().equals("AIR_OPERATIONS")){
						vo.getIapAirOpsVos().add(v);
					}
					if(v.getSection().equals("BRANCH_SECTION") && BooleanUtility.isTrue(v.getIsBranchName())){
						vo.getIapBranchVos().add(v);
					}
					if(v.getSection().equals("AGENCY_SECTION")){
						vo.getIapAgencyVos().add(v);
					}
				}
				
			}
			
		}
		
		return vo;
	}
	
	public static IapForm203 toEntity(IapForm203 entity, IapForm203Vo vo, Boolean cascadable,Persistable...persistables  ) throws Exception {
		if(null == entity) entity = new IapForm203Impl();
		
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
			entity.setIsNoBranch(StringBooleanEnum.toEnumValue(vo.getIsNoBranch()));
			entity.setPreparedBy(StringUtility.toUpper(vo.getPreparedBy()));
			entity.setPreparedByPosition(StringUtility.toUpper(vo.getPreparedByPosition()));
			if(DateTransferVo.hasDateString(vo.getPreparedDateVo())){
				Date dt=DateTransferVo.getDate(vo.getPreparedDateVo());
				if(StringUtility.hasValue(vo.getPreparedTime())){
					dt=DateUtil.addMilitaryTimeToDate(dt, vo.getPreparedTime());
					entity.setPreparedDate(dt);
				}
			}else{
				entity.setPreparedDate(null);
			}
			
			if(CollectionUtility.hasValue(vo.getIapPersonnelVos())){
				entity.setIapPersonnels(IapPersonnelVo.toEntityList(vo.getIapPersonnelVos(), Boolean.TRUE, entity));
			}
		}
		
		return entity;
	}
	
	public static Collection<IapForm203> toEntityList(Collection<IapForm203Vo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapForm203> entities = new ArrayList<IapForm203>();

		for(IapForm203Vo vo : vos){
			entities.add(IapForm203Vo.toEntity(null, vo,cascadable, persistables));
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
	 * @return the iapCommanderVos
	 */
	public Collection<IapPersonnelVo> getIapCommanderVos() {
		return iapCommanderVos;
	}

	/**
	 * @param iapCommanderVos the iapCommanderVos to set
	 */
	public void setIapCommanderVos(Collection<IapPersonnelVo> iapCommanderVos) {
		this.iapCommanderVos = iapCommanderVos;
	}

	/**
	 * @return the iapLogisticsVos
	 */
	public Collection<IapPersonnelVo> getIapLogisticsVos() {
		return iapLogisticsVos;
	}

	/**
	 * @param iapLogisticsVos the iapLogisticsVos to set
	 */
	public void setIapLogisticsVos(Collection<IapPersonnelVo> iapLogisticsVos) {
		this.iapLogisticsVos = iapLogisticsVos;
	}

	/**
	 * @return the iapPlanningVos
	 */
	public Collection<IapPersonnelVo> getIapPlanningVos() {
		return iapPlanningVos;
	}

	/**
	 * @param iapPlanningVos the iapPlanningVos to set
	 */
	public void setIapPlanningVos(Collection<IapPersonnelVo> iapPlanningVos) {
		this.iapPlanningVos = iapPlanningVos;
	}

	/**
	 * @return the iapFinanceVos
	 */
	public Collection<IapPersonnelVo> getIapFinanceVos() {
		return iapFinanceVos;
	}

	/**
	 * @param iapFinanceVos the iapFinanceVos to set
	 */
	public void setIapFinanceVos(Collection<IapPersonnelVo> iapFinanceVos) {
		this.iapFinanceVos = iapFinanceVos;
	}

	/**
	 * @return the iapOperationsVos
	 */
	public Collection<IapPersonnelVo> getIapOperationsVos() {
		return iapOperationsVos;
	}

	/**
	 * @param iapOperationsVos the iapOperationsVos to set
	 */
	public void setIapOperationsVos(Collection<IapPersonnelVo> iapOperationsVos) {
		this.iapOperationsVos = iapOperationsVos;
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
	 * @return the iapBranchVos
	 */
	public Collection<IapPersonnelVo> getIapBranchVos() {
		return iapBranchVos;
	}

	/**
	 * @param iapBranchVos the iapBranchVos to set
	 */
	public void setIapBranchVos(Collection<IapPersonnelVo> iapBranchVos) {
		this.iapBranchVos = iapBranchVos;
	}

	/**
	 * @return the iapAgencyVos
	 */
	public Collection<IapPersonnelVo> getIapAgencyVos() {
		return iapAgencyVos;
	}

	/**
	 * @param iapAgencyVos the iapAgencyVos to set
	 */
	public void setIapAgencyVos(Collection<IapPersonnelVo> iapAgencyVos) {
		this.iapAgencyVos = iapAgencyVos;
	}

	/**
	 * @return the isNoBranch
	 */
	public Boolean getIsNoBranch() {
		return isNoBranch;
	}

	/**
	 * @param isNoBranch the isNoBranch to set
	 */
	public void setIsNoBranch(Boolean isNoBranch) {
		this.isNoBranch = isNoBranch;
	}

	/**
	 * @return the iapAirOpsVos
	 */
	public Collection<IapPersonnelVo> getIapAirOpsVos() {
		return iapAirOpsVos;
	}

	/**
	 * @param iapAirOpsVos the iapAirOpsVos to set
	 */
	public void setIapAirOpsVos(Collection<IapPersonnelVo> iapAirOpsVos) {
		this.iapAirOpsVos = iapAirOpsVos;
	}
	
}
