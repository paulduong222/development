package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.domain.IapPersonnelRes;
import gov.nwcg.isuite.core.domain.impl.IapForm203Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm220Impl;
import gov.nwcg.isuite.core.domain.impl.IapPersonnelImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IapPersonnelVo extends AbstractVo implements PersistableVo {
	private Long iapForm203Id;
	private Long iapForm220Id;
	private String agencyName;
	private String role;
	private String roleType;
	private String name;
	private String phone;
	private String form;
	private String section;
	private Boolean isBlankLine=false;
	private Boolean isBlankBranch=false;
	private String divisionGroupName;
	private Integer positionNum;
	private Boolean isTrainee=false;
	private IncidentResourceVo incidentResourceVo;
	private BranchSettingVo branchSettingVo;
	private Boolean isBranchName=false;
	private Long iapBranchPersonnelParentId;
	private Collection<IapPersonnelVo> branchPersonnelVos = new ArrayList<IapPersonnelVo>();
	private IapPersonnelResVo iapPersonnelResVo1=new IapPersonnelResVo();
	private IapPersonnelResVo iapPersonnelResVo2=new IapPersonnelResVo();
	
	
	/**
	 * Constructor
	 */
	public IapPersonnelVo() {
	}

	public static IapPersonnel buildDefaultBlankBranch(Long form203Id){
		IapPersonnel branch = new IapPersonnelImpl();
		branch.setIsBranchName(StringBooleanEnum.Y);
		branch.setIsBlankBranch(StringBooleanEnum.Y);
		branch.setIsBlankLine(StringBooleanEnum.N);
		branch.setIsTrainee(StringBooleanEnum.N);
		branch.setForm("203");
		branch.setPositionNum(Integer.valueOf(0));
		branch.setSection("BRANCH_SECTION");
		IapForm203 form203 = new IapForm203Impl();
		form203.setId(form203Id);
		branch.setIapForm203(form203);
		
		return branch;
	}
	
	public static IapPersonnel buildDefaultBlankDivGroup(Long form203Id,Long branchId, int posnum){
		IapPersonnel position = new IapPersonnelImpl();
		position.setIsBranchName(StringBooleanEnum.N);
		position.setIsBlankBranch(StringBooleanEnum.N);
		position.setIsBlankLine(StringBooleanEnum.N);
		position.setIsTrainee(StringBooleanEnum.N);
		position.setForm("203");
		position.setPositionNum(Integer.valueOf(posnum));
		position.setSection("BRANCH_SECTION");
		position.setRole("DIVISION/GROUP");
		IapForm203 form203 = new IapForm203Impl();
		form203.setId(form203Id);
		IapPersonnel iapBranchPersonnelParent = new IapPersonnelImpl();
		iapBranchPersonnelParent.setId(branchId);
		position.setIapBranchPersonnelParent(iapBranchPersonnelParent);
		
		position.setIapForm203(form203);
		
		return position;
	}

	public static Collection<IapPersonnelVo> buildDefaultVos(IapForm220Vo form220Vo) throws Exception {
		Collection<IapPersonnelVo> personnelVos = new ArrayList<IapPersonnelVo>();
		personnelVos.add(IapPersonnelVo.buildEmptyVo("Air Operations Branch Director", form220Vo));
		personnelVos.add(IapPersonnelVo.buildEmptyVo("Air Support Group Supervisor", form220Vo));
		personnelVos.add(IapPersonnelVo.buildEmptyVo("Air Tactical Group Supervisor", form220Vo));
		personnelVos.add(IapPersonnelVo.buildEmptyVo("Helicopter Coordinator", form220Vo));
		personnelVos.add(IapPersonnelVo.buildEmptyVo("Helibase Manager", form220Vo));
		personnelVos.add(IapPersonnelVo.buildEmptyVo("", form220Vo));
		personnelVos.add(IapPersonnelVo.buildEmptyVo("", form220Vo));
		personnelVos.add(IapPersonnelVo.buildEmptyVo("", form220Vo));
		personnelVos.add(IapPersonnelVo.buildEmptyVo("", form220Vo));
		personnelVos.add(IapPersonnelVo.buildEmptyVo("", form220Vo));
		personnelVos.add(IapPersonnelVo.buildEmptyVo("", form220Vo));
		
		return personnelVos;
	}

	public static IapPersonnelVo buildEmptyVo(String role, IapForm220Vo form220Vo) throws Exception {
		IapPersonnelVo vo = new IapPersonnelVo();
		vo.setRole(role);
		
		if(form220Vo != null){
			vo.setIapForm220Id(form220Vo.getId());
		}
		
		return vo;
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapPersonnelVo getInstance(IapPersonnel entity, boolean cascadable) throws Exception {
		IapPersonnelVo vo = new IapPersonnelVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapPersonnelVo from null IapPersonnel entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setAgencyName(entity.getAgencyName());
			vo.setRole(entity.getRole());
			//vo.setRoleType(entity.getRoleType());
			vo.setIsTrainee(entity.getIsTrainee().getValue());
			vo.setIsBranchName(entity.getIsBranchName().getValue());
			vo.setIsBlankBranch(entity.getIsBlankBranch().getValue());
			vo.setDivisionGroupName(entity.getDivisionGroupName());
			vo.setName(entity.getName());
			vo.setPhone(entity.getPhone());
			vo.setForm(entity.getForm());
			vo.setSection(entity.getSection());
			vo.setIapForm203Id(entity.getIapForm203Id());
			vo.setIapForm220Id(entity.getIapForm220Id());
			if(IntegerUtility.hasValue(entity.getPositionNum()))
				vo.setPositionNum(entity.getPositionNum());
			else
				vo.setPositionNum(new Integer(0));

			vo.setIsBlankLine(entity.getIsBlankLine().getValue());

			if(null != entity.getBranchSetting()){
				vo.setBranchSettingVo(BranchSettingVo.getInstance(entity.getBranchSetting(), true));
			}
			
			if(LongUtility.hasValue(entity.getIapForm203Id())){
				if(BooleanUtility.isTrue(vo.getIsBlankLine())){
					vo.setRole("[Blank Line]");
				}
			}
			
			vo.setIapBranchPersonnelParentId(entity.getIapBranchPersonnelParentId());
			if(CollectionUtility.hasValue(entity.getBranchPersonnel())){
				vo.setBranchPersonnelVos(getInstances(entity.getBranchPersonnel(),true,entity.getForm()));
			}
			
			if(CollectionUtility.hasValue(entity.getIapPersonnelResources())){
				for(IapPersonnelRes ipr : entity.getIapPersonnelResources()){
					if(IntegerUtility.hasValue(ipr.getPositionNum())){
						if(ipr.getPositionNum().intValue()==1){
							vo.setIapPersonnelResVo1(IapPersonnelResVo.getInstance(ipr, true));
						}
						if(ipr.getPositionNum().intValue()==2){
							vo.setIapPersonnelResVo2(IapPersonnelResVo.getInstance(ipr, true));
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
	public static Collection<IapPersonnelVo> getInstances(Collection<IapPersonnel> entities, boolean cascadable,String formType) throws Exception {
		Collection<IapPersonnelVo> vos = new ArrayList<IapPersonnelVo>();
		
		for(IapPersonnel entity : entities) {
			if(formType.equals("203") && LongUtility.hasValue(entity.getIapForm203Id())){
				vos.add(IapPersonnelVo.getInstance(entity, cascadable));
			}
			if(formType.equals("220") && LongUtility.hasValue(entity.getIapForm220Id())){
				vos.add(IapPersonnelVo.getInstance(entity, cascadable));
			}
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
	public static IapPersonnel toEntity(IapPersonnel entity, IapPersonnelVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapPersonnelImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setAgencyName(StringUtility.toUpper(vo.getAgencyName()));
			entity.setRole(StringUtility.toUpper(vo.getRole()));
			//entity.setRoleType(StringUtility.toUpper(vo.getRoleType()));
			entity.setName(StringUtility.toUpper(vo.getName()));
			entity.setPhone(vo.getPhone());
			entity.setForm(StringUtility.toUpper(vo.getForm()));
			entity.setSection(StringUtility.toUpper(vo.getSection()));
			entity.setDivisionGroupName(StringUtility.toUpper(vo.getDivisionGroupName()));
			
			if(null != vo.getBranchSettingVo() && LongUtility.hasValue(vo.getBranchSettingVo().getId())){
				entity.setBranchSetting(BranchSettingVo.toEntity(null, vo.getBranchSettingVo(), false));
			}
			
			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));

			entity.setIsBlankLine(StringBooleanEnum.toEnumValue(vo.getIsBlankLine()));
			entity.setIsBlankBranch(StringBooleanEnum.toEnumValue(vo.getIsBlankBranch()));
			entity.setIsTrainee(StringBooleanEnum.toEnumValue(vo.getIsTrainee()));
			entity.setIsBranchName(StringBooleanEnum.toEnumValue(vo.getIsBranchName()));
			
			IapForm203 form203=(IapForm203)AbstractVo.getPersistableObject(persistables, IapForm203Impl.class);
			if(null != form203){
				entity.setIapForm203(form203);
			}
			
			IapForm220 form220=(IapForm220)AbstractVo.getPersistableObject(persistables, IapForm220Impl.class);
			if(null != form220){
				entity.setIapForm220(form220);
			}

			IapPersonnel parent=(IapPersonnel)AbstractVo.getPersistableObject(persistables, IapPersonnelImpl.class);
			if(null != parent){
				entity.setIapBranchPersonnelParent(parent);
			}

			if(null != vo.getIapPersonnelResVo1() && null != form203){
				IapPersonnelRes iapPersonnelRes1=null;
				IapPersonnelRes iapPersonnelRes2=null;
				for(IapPersonnelRes r : entity.getIapPersonnelResources()){
					if(IntegerUtility.hasValue(r.getPositionNum()) && r.getPositionNum().intValue()==1)
						iapPersonnelRes1=r;
					if(IntegerUtility.hasValue(r.getPositionNum()) && r.getPositionNum().intValue()==2)
						iapPersonnelRes2=r;
				}
				vo.getIapPersonnelResVo1().setPositionNum(new Integer(1));
				vo.getIapPersonnelResVo2().setPositionNum(new Integer(2));
				entity.getIapPersonnelResources().clear();
				entity.getIapPersonnelResources().add(IapPersonnelResVo.toEntity(iapPersonnelRes1,vo.getIapPersonnelResVo1(), true, entity) );
				entity.getIapPersonnelResources().add(IapPersonnelResVo.toEntity(iapPersonnelRes2,vo.getIapPersonnelResVo2(), true, entity) );
				
				entity.setBranchPersonnel(IapPersonnelVo.toEntityList(vo.getBranchPersonnelVos(), true, entity.getIapForm203()));
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
	public static Collection<IapPersonnel> toEntityList(Collection<IapPersonnelVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapPersonnel> entities = new ArrayList<IapPersonnel>();
		
		for(IapPersonnelVo vo : vos) {
			entities.add(IapPersonnelVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(String form) {
		this.form = form;
	}

	/**
	 * @return the form
	 */
	public String getForm() {
		return form;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @return the iapForm203Id
	 */
	public Long getIapForm203Id() {
		return iapForm203Id;
	}

	/**
	 * @param iapForm203Id the iapForm203Id to set
	 */
	public void setIapForm203Id(Long iapForm203Id) {
		this.iapForm203Id = iapForm203Id;
	}

	/**
	 * @return the iapForm220Id
	 */
	public Long getIapForm220Id() {
		return iapForm220Id;
	}

	/**
	 * @param iapForm220Id the iapForm220Id to set
	 */
	public void setIapForm220Id(Long iapForm220Id) {
		this.iapForm220Id = iapForm220Id;
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
	 * @return the incidentResourceVo
	 */
	public IncidentResourceVo getIncidentResourceVo() {
		return incidentResourceVo;
	}

	/**
	 * @param incidentResourceVo the incidentResourceVo to set
	 */
	public void setIncidentResourceVo(IncidentResourceVo incidentResourceVo) {
		this.incidentResourceVo = incidentResourceVo;
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
	 * @return the branchSettingVo
	 */
	public BranchSettingVo getBranchSettingVo() {
		return branchSettingVo;
	}

	/**
	 * @param branchSettingVo the branchSettingVo to set
	 */
	public void setBranchSettingVo(BranchSettingVo branchSettingVo) {
		this.branchSettingVo = branchSettingVo;
	}

	/**
	 * @return the agencyName
	 */
	public String getAgencyName() {
		return agencyName;
	}

	/**
	 * @param agencyName the agencyName to set
	 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
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
	 * @return the isBranchName
	 */
	public Boolean getIsBranchName() {
		return isBranchName;
	}

	/**
	 * @param isBranchName the isBranchName to set
	 */
	public void setIsBranchName(Boolean isBranchName) {
		this.isBranchName = isBranchName;
	}

	/**
	 * @return the iapBranchPersonnelParentId
	 */
	public Long getIapBranchPersonnelParentId() {
		return iapBranchPersonnelParentId;
	}

	/**
	 * @param iapBranchPersonnelParentId the iapBranchPersonnelParentId to set
	 */
	public void setIapBranchPersonnelParentId(Long iapBranchPersonnelParentId) {
		this.iapBranchPersonnelParentId = iapBranchPersonnelParentId;
	}

	/**
	 * @return the branchPersonnelVos
	 */
	public Collection<IapPersonnelVo> getBranchPersonnelVos() {
		return branchPersonnelVos;
	}

	/**
	 * @param branchPersonnelVos the branchPersonnelVos to set
	 */
	public void setBranchPersonnelVos(Collection<IapPersonnelVo> branchPersonnelVos) {
		this.branchPersonnelVos = branchPersonnelVos;
	}

	/**
	 * @return the isBlankBranch
	 */
	public Boolean getIsBlankBranch() {
		return isBlankBranch;
	}

	/**
	 * @param isBlankBranch the isBlankBranch to set
	 */
	public void setIsBlankBranch(Boolean isBlankBranch) {
		this.isBlankBranch = isBlankBranch;
	}

	/**
	 * @return the divisionGroupName
	 */
	public String getDivisionGroupName() {
		return divisionGroupName;
	}

	/**
	 * @param divisionGroupName the divisionGroupName to set
	 */
	public void setDivisionGroupName(String divisionGroupName) {
		this.divisionGroupName = divisionGroupName;
	}

	/**
	 * @return the iapPersonnelResVo1
	 */
	public IapPersonnelResVo getIapPersonnelResVo1() {
		return iapPersonnelResVo1;
	}

	/**
	 * @param iapPersonnelResVo1 the iapPersonnelResVo1 to set
	 */
	public void setIapPersonnelResVo1(IapPersonnelResVo iapPersonnelResVo1) {
		this.iapPersonnelResVo1 = iapPersonnelResVo1;
	}

	/**
	 * @return the iapPersonnelResVo2
	 */
	public IapPersonnelResVo getIapPersonnelResVo2() {
		return iapPersonnelResVo2;
	}

	/**
	 * @param iapPersonnelResVo2 the iapPersonnelResVo2 to set
	 */
	public void setIapPersonnelResVo2(IapPersonnelResVo iapPersonnelResVo2) {
		this.iapPersonnelResVo2 = iapPersonnelResVo2;
	}

}
