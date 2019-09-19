package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.impl.IapBranchImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IapBranchVo extends AbstractVo implements PersistableVo {
	private Long iapPlanId;
	private String branchName;
	private String divisionName;
	private String groupName;
	private String controlOperations;
	private String specialInstructions;
	private String stagingArea;
	private String workAssignment;
	private String preparedBy;
	private String approvedBy;
	private DateTransferVo preparedDateVo;
	private String preparedByPosition;
	private Collection<IapBranchCommSummaryVo> iapBranchCommSummaryVos = new ArrayList<IapBranchCommSummaryVo>();
	private Collection<IapBranchPersonnelVo> iapBranchPersonnelVos = new ArrayList<IapBranchPersonnelVo>();
	private Collection<IapBranchRscAssignVo> iapBranchRscAssignVos = new ArrayList<IapBranchRscAssignVo>();
	
	public IapBranchVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapBranchVo getInstance(IapBranch entity, boolean cascadable) throws Exception {
		IapBranchVo vo = new IapBranchVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapBranchVo from null IapBranch entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIapPlanId(entity.getIapPlanId());
			vo.setBranchName(entity.getBranchName());
			vo.setDivisionName(entity.getDivisionName());
			vo.setGroupName(entity.getGroupName());
			vo.setSpecialInstructions(entity.getSpecialInstructions());
			vo.setStagingArea(entity.getStagingArea());
			vo.setWorkAssignment(entity.getWorkAssignment());
			vo.setPreparedBy(entity.getPreparedBy());
			vo.setPreparedByPosition(entity.getPreparedByPosition());
			if(DateUtil.hasValue(entity.getPreparedDate())){
				DateTransferVo.populateDate(vo.getPreparedDateVo(), entity.getPreparedDate());
			}
			vo.setApprovedBy(entity.getApprovedBy());
			
			if(null != entity.getIapBranchCommSummaries()) {
				vo.setIapBranchCommSummaryVos(IapBranchCommSummaryVo.getInstances(entity.getIapBranchCommSummaries(), true));
			}
			
			if(null != entity.getIapBranchPersonnels()) {
				vo.setIapBranchPersonnelVos(IapBranchPersonnelVo.getInstances(entity.getIapBranchPersonnels(), true));
			}
			
			if(null != entity.getIapBranchRscAssigns()) {
				vo.setIapBranchRscAssignVos(IapBranchRscAssignVo.getInstances(entity.getIapBranchRscAssigns(), true));
			}
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapBranchVo> getInstances(Collection<IapBranch> entities, boolean cascadable) throws Exception {
		Collection<IapBranchVo> vos = new ArrayList<IapBranchVo>();
		
		for(IapBranch entity : entities) {
			vos.add(IapBranchVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	/**
	 * @param entity
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static IapBranch toEntity(IapBranch entity, IapBranchVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapBranchImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setBranchName(StringUtility.toUpper(vo.getBranchName()));
			entity.setDivisionName(StringUtility.toUpper(vo.getDivisionName()));
			entity.setGroupName(StringUtility.toUpper(vo.getGroupName()));
			entity.setSpecialInstructions(StringUtility.toUpper(vo.getSpecialInstructions()));
			entity.setStagingArea(StringUtility.toUpper(vo.getStagingArea()));
			entity.setWorkAssignment(StringUtility.toUpper(vo.getWorkAssignment()));
			entity.setPreparedBy(StringUtility.toUpper(vo.getPreparedBy()));
			entity.setApprovedBy(StringUtility.toUpper(vo.getApprovedBy()));
			
			if(null != vo.getIapBranchCommSummaryVos()) {
				entity.setIapBranchCommSummaries(IapBranchCommSummaryVo.toEntityList(vo.getIapBranchCommSummaryVos(), true));
			}
			
			if(null != vo.getIapBranchPersonnelVos()) {
				entity.setIapBranchPersonnels(IapBranchPersonnelVo.toEntityList(vo.getIapBranchPersonnelVos(), true));
			}
			
			if(null != vo.getIapBranchRscAssignVos()) {
				entity.setIapBranchRscAssigns(IapBranchRscAssignVo.toEntityList(vo.getIapBranchRscAssignVos(), true));
			}
		}
		
		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapBranch> toEntityList(Collection<IapBranchVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapBranch> entities = new ArrayList<IapBranch>();
		
		for(IapBranchVo vo : vos) {
			entities.add(IapBranchVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param divisionName the divisionName to set
	 */
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	/**
	 * @return the divisionName
	 */
	public String getDivisionName() {
		return divisionName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param controlOperations the controlOperations to set
	 */
	public void setControlOperations(String controlOperations) {
		this.controlOperations = controlOperations;
	}

	/**
	 * @return the controlOperations
	 */
	public String getControlOperations() {
		return controlOperations;
	}

	/**
	 * @param specialInstructions the specialInstructions to set
	 */
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	/**
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions() {
		return specialInstructions;
	}

	/**
	 * @param stagingArea the stagingArea to set
	 */
	public void setStagingArea(String stagingArea) {
		this.stagingArea = stagingArea;
	}

	/**
	 * @return the stagingArea
	 */
	public String getStagingArea() {
		return stagingArea;
	}

	/**
	 * @param workAssignment the workAssignment to set
	 */
	public void setWorkAssignment(String workAssignment) {
		this.workAssignment = workAssignment;
	}

	/**
	 * @return the workAssignment
	 */
	public String getWorkAssignment() {
		return workAssignment;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param iapBranchCommSummaryVos the iapBranchCommSummaryVos to set
	 */
	public void setIapBranchCommSummaryVos(Collection<IapBranchCommSummaryVo> iapBranchCommSummaryVos) {
		this.iapBranchCommSummaryVos = iapBranchCommSummaryVos;
	}

	/**
	 * @return the iapBranchCommSummaryVos
	 */
	public Collection<IapBranchCommSummaryVo> getIapBranchCommSummaryVos() {
		return iapBranchCommSummaryVos;
	}

	/**
	 * @param iapBranchPersonnelVos the iapBranchPersonnelVos to set
	 */
	public void setIapBranchPersonnelVos(Collection<IapBranchPersonnelVo> iapBranchPersonnelVos) {
		this.iapBranchPersonnelVos = iapBranchPersonnelVos;
	}

	/**
	 * @return the iapBranchPersonnelVos
	 */
	public Collection<IapBranchPersonnelVo> getIapBranchPersonnelVos() {
		return iapBranchPersonnelVos;
	}

	/**
	 * @param iapBranchRscAssignVos the iapBranchRscAssignVos to set
	 */
	public void setIapBranchRscAssignVos(Collection<IapBranchRscAssignVo> iapBranchRscAssignVos) {
		this.iapBranchRscAssignVos = iapBranchRscAssignVos;
	}

	/**
	 * @return the iapBranchRscAssignVos
	 */
	public Collection<IapBranchRscAssignVo> getIapBranchRscAssignVos() {
		return iapBranchRscAssignVos;
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
}
