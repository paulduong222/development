package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

/**
 * @author dprice
 *
 */
public class AssignmentVo extends AbstractVo {
    private KindVo kindVo;
	private String requestNumber;
	private Date startDate;
	private Date endDate;
    private Boolean training;
    private AssignmentStatusVo assignmentStatusVo;
    private AssignmentStatusTypeEnum assignmentStatus;
	private Collection<WorkPeriodVo> workPeriodVos;
	private AssignmentTimeVo assignmentTimeVo = new AssignmentTimeVo();
	private Collection<TimeAssignAdjustVo> timeAssignAdjustVos;
	private AssignmentStatusVo origAssignmentStatusVo;
	private String reassignIncidentName;
	private String reassignIncidentNumber;
	
	public AssignmentVo(){
		
	}

	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static AssignmentVo getInstance(Assignment entity, Boolean cascadable ) throws Exception {
		AssignmentVo vo = new AssignmentVo();
		
		if(null==entity)
			throw new Exception("Unable to create assignment vo from null entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setAssignmentStatus(entity.getAssignmentStatus());

			if(null != entity.getAssignmentStatus()){
				Collection<AssignmentStatusVo> asvList = AssignmentStatusTypeEnum.getAssignmentVoList(false);
				for(AssignmentStatusVo asvo : asvList){
					if(asvo.getCode().equals(entity.getAssignmentStatus().name())){
						vo.setAssignmentStatusVo(asvo);
						vo.setOrigAssignmentStatusVo(asvo);
					}
				}
			} else {
				AssignmentStatusVo asvo = new AssignmentStatusVo();
				vo.setAssignmentStatusVo(asvo);
			}
			
			vo.setEndDate(entity.getEndDate());
			vo.setRequestNumber(entity.getRequestNumber());
			vo.setStartDate(entity.getStartDate());
			vo.setTraining(entity.isTraining());
			
			vo.setReassignIncidentName(entity.getReassignIncidentName());
			vo.setReassignIncidentNumber(entity.getReassignIncidentNumber());
			
			if(null != entity.getKind())
				vo.setKindVo(KindVo.getInstance(entity.getKind(), true));
			
			if(null != entity.getAssignmentTime() ){
				vo.setAssignmentTimeVo(AssignmentTimeVo.getInstance(entity.getAssignmentTime(), true));
			}
			
			if(null != entity.getTimeAssignAdjusts()) {

				if(null == vo.getTimeAssignAdjustVos()) {
					vo.setTimeAssignAdjustVos(new ArrayList<TimeAssignAdjustVo>());
				}				
				for(TimeAssignAdjust adjust : entity.getTimeAssignAdjusts()) {					
					TimeAssignAdjustVo adjustVo = TimeAssignAdjustVo.getInstance(adjust, cascadable);	
					if(adjustVo.getDeletedDate() == null){
						vo.getTimeAssignAdjustVos().add(adjustVo);	
					}
				}
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
	public static Collection<AssignmentVo> getInstances(Collection<Assignment> entities, Boolean cascadable) throws Exception {
		Collection<AssignmentVo> vos = new ArrayList<AssignmentVo>();
		
		for(Assignment entity : entities){
			vos.add(getInstance(entity,true));
		}
		
		return vos;
	}
	
	public static Assignment toEntity(Assignment entity, AssignmentVo vo, Boolean cascadable, Persistable... persistables) throws Exception {
		if(null == entity)
			entity = new AssignmentImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setRequestNumber(StringUtility.toUpper(vo.getRequestNumber()));
			entity.setReassignIncidentName(StringUtility.toUpper(vo.getReassignIncidentName()));
			entity.setReassignIncidentNumber(StringUtility.toUpper(vo.getReassignIncidentNumber()));
			
			if( null != vo.getAssignmentStatusVo() ) {
				entity.setAssignmentStatus(AssignmentStatusTypeEnum.valueOf(vo.getAssignmentStatusVo().getCode()));
			}

			/*
			if( (null != vo.getAssignmentStatusVo() )
					&&
				(null != vo.getAssignmentStatusVo().getId()) 
				&&
				(vo.getAssignmentStatusVo().getId() > 0) 
			){
				entity.setAssignmentStatus(AssignmentStatusTypeEnum.valueOf(vo.getAssignmentStatusVo().getCode()));
			}
			*/
			if( (null != vo.getKindVo().getId()) && (vo.getKindVo().getId()>0L) ){
				Kind kind = new KindImpl();
				kind.setId(vo.getKindVo().getId());
				entity.setKind(kind);
			}

			if(null != vo.getStartDate())
				entity.setStartDate(vo.getStartDate());
			
			if(null != vo.getEndDate())
				entity.setEndDate(vo.getEndDate());
			
			if(null != vo.getAssignmentTimeVo()){
				try{
					entity.setAssignmentTime(AssignmentTimeVo.toEntity(entity.getAssignmentTime(), vo.getAssignmentTimeVo(), true, entity));
				}catch(Exception e){
					throw e;
				}
			}
			
			if(null != vo.getTimeAssignAdjustVos()) {
				
				entity.setTimeAssignAdjusts(new ArrayList<TimeAssignAdjust>());
				
				for(TimeAssignAdjustVo adjustVo : vo.getTimeAssignAdjustVos()) {
					if(adjustVo.getDeletedDate() == null){
						entity.getTimeAssignAdjusts().add(TimeAssignAdjustVo.toEntity(null, adjustVo, cascadable, persistables));	
					}
				}
			}
			
			entity.setTraining(BooleanUtility.getValue(vo.getTraining()));
			
		}
		
		return entity;
	}
	
	/**
	 * Returns the kindVo.
	 *
	 * @return 
	 *		the kindVo to return
	 */
	public KindVo getKindVo() {
		return kindVo;
	}

	/**
	 * Sets the kindVo.
	 *
	 * @param kindVo 
	 *			the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}

	/**
	 * Returns the requestNumber.
	 *
	 * @return 
	 *		the requestNumber to return
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * Sets the requestNumber.
	 *
	 * @param requestNumber 
	 *			the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * Returns the startDate.
	 *
	 * @return 
	 *		the startDate to return
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the startDate.
	 *
	 * @param startDate 
	 *			the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the endDate.
	 *
	 * @return 
	 *		the endDate to return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the endDate.
	 *
	 * @param endDate 
	 *			the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Returns the training.
	 *
	 * @return 
	 *		the training to return
	 */
	public Boolean getTraining() {
		return training;
	}

	/**
	 * Sets the training.
	 *
	 * @param training 
	 *			the training to set
	 */
	public void setTraining(Boolean training) {
		this.training = training;
	}

	/**
	 * Returns the assignmentStatus.
	 *
	 * @return 
	 *		the assignmentStatus to return
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus() {
		return assignmentStatus;
	}

	/**
	 * Sets the assignmentStatus.
	 *
	 * @param assignmentStatus 
	 *			the assignmentStatus to set
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	/**
	 * Returns the workPeriodVos.
	 *
	 * @return 
	 *		the workPeriodVos to return
	 */
	public Collection<WorkPeriodVo> getWorkPeriodVos() {
		return workPeriodVos;
	}

	/**
	 * Sets the workPeriodVos.
	 *
	 * @param workPeriodVos 
	 *			the workPeriodVos to set
	 */
	public void setWorkPeriodVos(Collection<WorkPeriodVo> workPeriodVos) {
		this.workPeriodVos = workPeriodVos;
	}

	/**
	 * Returns the assignmentStatusVo.
	 *
	 * @return 
	 *		the assignmentStatusVo to return
	 */
	public AssignmentStatusVo getAssignmentStatusVo() {
		return assignmentStatusVo;
	}

	/**
	 * Sets the assignmentStatusVo.
	 *
	 * @param assignmentStatusVo 
	 *			the assignmentStatusVo to set
	 */
	public void setAssignmentStatusVo(AssignmentStatusVo assignmentStatusVo) {
		this.assignmentStatusVo = assignmentStatusVo;
	}

	/**
	 * @return the assignmentTimeVo
	 */
	public AssignmentTimeVo getAssignmentTimeVo() {
		return assignmentTimeVo;
	}

	/**
	 * @param assignmentTimeVo the assignmentTimeVo to set
	 */
	public void setAssignmentTimeVo(AssignmentTimeVo assignmentTimeVo) {
		this.assignmentTimeVo = assignmentTimeVo;
	}

	/**
	 * @return the timeAssignAdjustVos
	 */
	public Collection<TimeAssignAdjustVo> getTimeAssignAdjustVos() {
		return timeAssignAdjustVos;
	}

	/**
	 * @param timeAssignAdjustVos the timeAssignAdjustVos to set
	 */
	public void setTimeAssignAdjustVos(
			Collection<TimeAssignAdjustVo> timeAssignAdjustVos) {
		this.timeAssignAdjustVos = timeAssignAdjustVos;
	}

	public AssignmentStatusVo getOrigAssignmentStatusVo() {
		return origAssignmentStatusVo;
	}

	public void setOrigAssignmentStatusVo(AssignmentStatusVo origAssignmentStatusVo) {
		this.origAssignmentStatusVo = origAssignmentStatusVo;
	}

	public String getReassignIncidentName() {
		return reassignIncidentName;
	}

	public void setReassignIncidentName(String reassignIncidentName) {
		this.reassignIncidentName = reassignIncidentName;
	}

	public String getReassignIncidentNumber() {
		return reassignIncidentNumber;
	}

	public void setReassignIncidentNumber(String reassignIncidentNumber) {
		this.reassignIncidentNumber = reassignIncidentNumber;
	}
 }