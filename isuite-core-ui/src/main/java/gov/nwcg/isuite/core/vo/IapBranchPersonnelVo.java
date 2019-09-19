package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapBranchPersonnel;
import gov.nwcg.isuite.core.domain.IapBranchPersonnelRes;
import gov.nwcg.isuite.core.domain.impl.IapBranchImpl;
import gov.nwcg.isuite.core.domain.impl.IapBranchPersonnelImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IapBranchPersonnelVo extends AbstractVo implements PersistableVo {
	private IncidentResourceVo incidentResourceVo;
	private Long iapBranchId;
	private String role;
	private String resourceName;
	private String phone1;
	private String phone2;
	private String roleType;
	private Boolean isTrainee;
	private Integer positionNum;
	private IapBranchPersonnelResVo iapBranchPersonnelResVo1=new IapBranchPersonnelResVo();
	private IapBranchPersonnelResVo iapBranchPersonnelResVo2=new IapBranchPersonnelResVo();
	private Boolean isBlankLine=false;
	
	/**
	 * Constructor
	 */
	public IapBranchPersonnelVo() {
	}
	
	public static IapBranchPersonnelVo buildEmptyVo(String role, IapForm204Vo form204Vo) throws Exception {
		IapBranchPersonnelVo vo = new IapBranchPersonnelVo();
		vo.setRole(role);
		
		if(form204Vo != null){
			vo.setIapBranchId(form204Vo.getId());
		}
		
		return vo;
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapBranchPersonnelVo getInstance(IapBranchPersonnel entity, boolean cascadable) throws Exception {
		IapBranchPersonnelVo vo = new IapBranchPersonnelVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapBranchPersonnelVo from null IapBranchPersonnel entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			// Set blank line property first as it is used ahead
			vo.setIsBlankLine(entity.getIsBlankLine().getValue());
			
			vo.setRole(entity.getRole());
			vo.setResourceName(entity.getResourceName());
			vo.setPhone1(entity.getPhone1());
			vo.setPhone2(entity.getPhone2());
			//vo.setRoleType(entity.getRoleType());
			vo.setIsTrainee(entity.getIsTrainee().getValue());
			vo.setIapBranchId(entity.getIapBranchId());
			
			if(IntegerUtility.hasValue(entity.getPositionNum()))
				vo.setPositionNum(entity.getPositionNum());
			else
				vo.setPositionNum(new Integer(0));

			if(CollectionUtility.hasValue(entity.getIapBranchPersonnelResources())){
				for(IapBranchPersonnelRes ipr : entity.getIapBranchPersonnelResources()){
					if(IntegerUtility.hasValue(ipr.getPositionNum())){
						if(ipr.getPositionNum().intValue()==1){
							vo.setIapBranchPersonnelResVo1(IapBranchPersonnelResVo.getInstance(ipr, true));
						}
						if(ipr.getPositionNum().intValue()==2){
							vo.setIapBranchPersonnelResVo2(IapBranchPersonnelResVo.getInstance(ipr, true));
						}
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
	public static Collection<IapBranchPersonnelVo> getInstances(Collection<IapBranchPersonnel> entities, boolean cascadable) throws Exception {
		Collection<IapBranchPersonnelVo> vos = new ArrayList<IapBranchPersonnelVo>();
		
		for(IapBranchPersonnel entity : entities) {
			vos.add(IapBranchPersonnelVo.getInstance(entity, cascadable));
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
	public static IapBranchPersonnel toEntity(IapBranchPersonnel entity, IapBranchPersonnelVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapBranchPersonnelImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			// Set blank line property first as it is used ahead
			entity.setIsBlankLine(StringBooleanEnum.toEnumValue(vo.getIsBlankLine()));
			
			entity.setRole(StringUtility.toUpper(vo.getRole()));
			//entity.setRoleType(vo.getRoleType());
			entity.setPhone1(vo.getPhone1());
			entity.setPhone2(vo.getPhone2());
			entity.setIsTrainee(StringBooleanEnum.toEnumValue(vo.getIsTrainee()));
			
			entity.setResourceName(StringUtility.toUpper(vo.getResourceName()));

			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));

			IapBranch branchEntity = (IapBranch)(AbstractVo.getPersistableObject(persistables, IapBranchImpl.class));
			entity.setIapBranch(branchEntity);
//			if(null != branchEntity){
//				IapBranch branchEntity2 = new IapBranchImpl();
//				branchEntity2.setId(branchEntity.getId());
//				entity.setIapBranch(branchEntity2);
//			}
			
			if(null != vo.getIapBranchPersonnelResVo1() && null != branchEntity){
				IapBranchPersonnelRes iapBranchPersonnelRes1=null;
				IapBranchPersonnelRes iapBranchPersonnelRes2=null;
				for(IapBranchPersonnelRes r : entity.getIapBranchPersonnelResources()){
					if(IntegerUtility.hasValue(r.getPositionNum()) && r.getPositionNum().intValue()==1)
						iapBranchPersonnelRes1=r;
					if(IntegerUtility.hasValue(r.getPositionNum()) && r.getPositionNum().intValue()==2)
						iapBranchPersonnelRes2=r;
				}
				vo.getIapBranchPersonnelResVo1().setPositionNum(new Integer(1));
				vo.getIapBranchPersonnelResVo2().setPositionNum(new Integer(2));
				entity.getIapBranchPersonnelResources().clear();
				entity.getIapBranchPersonnelResources().add(IapBranchPersonnelResVo.toEntity(iapBranchPersonnelRes1,vo.getIapBranchPersonnelResVo1(), true, entity) );
				entity.getIapBranchPersonnelResources().add(IapBranchPersonnelResVo.toEntity(iapBranchPersonnelRes2,vo.getIapBranchPersonnelResVo2(), true, entity) );
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
	public static Collection<IapBranchPersonnel> toEntityList(Collection<IapBranchPersonnelVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapBranchPersonnel> entities = new ArrayList<IapBranchPersonnel>();
		
		for(IapBranchPersonnelVo vo : vos) {
			entities.add(IapBranchPersonnelVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}


	/**
	 * @param incidentResourceVo the incidentResourceVo to set
	 */
	public void setIncidentResourceVo(IncidentResourceVo incidentResourceVo) {
		this.incidentResourceVo = incidentResourceVo;
	}

	/**
	 * @return the incidentResourceVo
	 */
	public IncidentResourceVo getIncidentResourceVo() {
		return incidentResourceVo;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the iapBranchId
	 */
	public Long getIapBranchId() {
		return iapBranchId;
	}

	/**
	 * @param iapBranchId the iapBranchId to set
	 */
	public void setIapBranchId(Long iapBranchId) {
		this.iapBranchId = iapBranchId;
	}

	/**
	 * @return the phone1
	 */
	public String getPhone1() {
		return phone1;
	}

	/**
	 * @param phone1 the phone1 to set
	 */
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 * @param phone2 the phone2 to set
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the isTrainee
	 */
	public Boolean getIsTrainee() {
		return isTrainee;
	}

	/**
	 * @param isTrainee the isTrainee to set
	 */
	public void setIsTrainee(Boolean isTrainee) {
		this.isTrainee = isTrainee;
	}

	/**
	 * @return the iapBranchPersonnelResVo1
	 */
	public IapBranchPersonnelResVo getIapBranchPersonnelResVo1() {
		return iapBranchPersonnelResVo1;
	}

	/**
	 * @param iapBranchPersonnelResVo1 the iapBranchPersonnelResVo1 to set
	 */
	public void setIapBranchPersonnelResVo1(
			IapBranchPersonnelResVo iapBranchPersonnelResVo1) {
		this.iapBranchPersonnelResVo1 = iapBranchPersonnelResVo1;
	}

	/**
	 * @return the iapBranchPersonnelResVo2
	 */
	public IapBranchPersonnelResVo getIapBranchPersonnelResVo2() {
		return iapBranchPersonnelResVo2;
	}

	/**
	 * @param iapBranchPersonnelResVo2 the iapBranchPersonnelResVo2 to set
	 */
	public void setIapBranchPersonnelResVo2(
			IapBranchPersonnelResVo iapBranchPersonnelResVo2) {
		this.iapBranchPersonnelResVo2 = iapBranchPersonnelResVo2;
	}

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

	/**
	 * @return the isBlankLine
	 */
	public Boolean getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(Boolean isBlankLine) {
		this.isBlankLine = isBlankLine;
	}

}
