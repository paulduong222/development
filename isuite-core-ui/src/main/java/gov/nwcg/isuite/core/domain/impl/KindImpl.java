package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.BranchSettingPosition;
import gov.nwcg.isuite.core.domain.DailyForm;
import gov.nwcg.isuite.core.domain.Department;
import gov.nwcg.isuite.core.domain.GroupCategory;
import gov.nwcg.isuite.core.domain.IapBranchPosItemCode;
import gov.nwcg.isuite.core.domain.IapPositionItemCode;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentCostRateKind;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.KindAltDesc;
import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.core.domain.RequestCategory;
import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.core.domain.ResourceOther;
import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.domain.SubGroupCategory;
import gov.nwcg.isuite.core.domain.SysCostRateKind;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_KIND", sequenceName="SEQ_KIND")
@Table(name = "iswl_kind")
public class KindImpl extends PersistableImpl implements Kind {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_KIND")
   private Long id = 0L;
   
   @Column(name = "CODE", length = 10)
   private String code;
   
   @Column(name = "DESCRIPTION", length = 75)
   private String description;
   
   @Column(name = "IS_STANDARD")
   private Boolean standard;
   
   @ManyToOne(targetEntity=RequestCategoryImpl.class, fetch = FetchType.LAZY)
   @JoinColumn(name = "REQUEST_CATEGORY_ID", insertable=true,updatable=true,nullable = false)
   private RequestCategory requestCategory;

   @Column(name = "REQUEST_CATEGORY_ID",insertable=false,updatable=false)
   private Long requestCategoryId;
   
   @OneToMany(targetEntity=gov.nwcg.isuite.core.domain.impl.ResourceKindImpl.class,fetch = FetchType.LAZY, mappedBy = "kind")
   private Collection<ResourceKind> resourceKinds;
   
   @ManyToOne(targetEntity=DepartmentImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="DEPARTMENT_ID", insertable=true, updatable=true, unique=false, nullable=false)
   private Department department;
   
   @Column(name="DEPARTMENT_ID",insertable=false,updatable=false)
   private Long departmentId;
   
   @ManyToOne(targetEntity=Sit209Impl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="SIT_209_ID", insertable=true, updatable=true, unique=false, nullable=true)
   private Sit209 sit209;
   
   @Column(name="SIT_209_ID",insertable=false,updatable=false)
   private Long sit209Id;
   
   @Column(name="IS_DIRECT")
   private Boolean direct;
   
   @Column(name="IS_INDIRECT")
   private Boolean inDirect;

   @ManyToOne(targetEntity=DailyFormImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="DAILY_FORM_ID", insertable=true, updatable=true, nullable=false)
   private DailyForm dailyForm;
   
   @Column(name="DAILY_FORM_ID",insertable=false,updatable=false)
   private Long dailyFormId;
   
   @Column(name = "UNITS", precision = 4, scale = 0)
   private Integer units;
   
   @Column(name = "PEOPLE", precision = 4, scale = 0)
   private Integer people;
   
   @ManyToOne(targetEntity = SubGroupCategoryImpl.class, fetch = FetchType.LAZY)
   @JoinColumn(name = "SUB_GROUP_CATEGORY_ID", insertable=true, updatable=true, unique=false, nullable=false)
   private SubGroupCategory subGroupCategory;
   
   @Column(name = "SUB_GROUP_CATEGORY_ID",insertable=false,updatable=false)
   private Long subGroupCategoryId;
   
   @ManyToOne(targetEntity = GroupCategoryImpl.class, fetch = FetchType.LAZY)
   @JoinColumn(name = "GROUP_CATEGORY_ID", insertable=true, updatable=true, unique=false, nullable=false)
   private GroupCategory groupCategory;
   
   @Column(name = "GROUP_CATEGORY_ID",insertable=false,updatable=false)
   private Long groupCategoryId;
   
   @Column(name="IS_LINE_OVERHEAD")
   private Boolean lineOverhead;
   
   @Column(name="IS_SUBORDINATE")
   private Boolean subordinate;
   
   @Column(name="IS_STRIKE_TEAM")
   private Boolean strikeTeam;
   
   @OneToMany(targetEntity=SysCostRateKindImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kind")
   private Collection<SysCostRateKind> sysCostRateKinds = new ArrayList<SysCostRateKind>();

   @OneToMany(targetEntity=IncidentCostRateKindImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kind")
   private Collection<IncidentCostRateKind> incidentCostRateKinds = new ArrayList<IncidentCostRateKind>();

   @OneToMany(targetEntity=ResourceOtherImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kind")
   private Collection<ResourceOther> resourceOthers = new ArrayList<ResourceOther>();
   
   @OneToMany(targetEntity=AssignmentImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kind")
   private Collection<Assignment> assignments = new ArrayList<Assignment>();
   
   @Column(name="IS_AIRCRAFT",nullable=false)
   @Enumerated(EnumType.STRING)
   private StringBooleanEnum aircraft;
   
   @OneToMany(targetEntity=IapPositionItemCodeImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kind")
   private Collection<IapPositionItemCode> iapPositionItemCodes = new ArrayList<IapPositionItemCode>();
   
   @OneToMany(targetEntity=IapBranchPosItemCodeImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kind")
   private Collection<IapBranchPosItemCode> iapBranchPosItemCodes = new ArrayList<IapBranchPosItemCode>();
   
   @OneToMany(targetEntity=BranchSettingPositionImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kind")
   private Collection<BranchSettingPosition> branchSettingPositions = new ArrayList<BranchSettingPosition>();

   @ManyToOne(targetEntity=IncidentImpl.class, fetch = FetchType.LAZY)
   @JoinColumn(name = "INCIDENT_ID")
   private Incident incident;
   
   @Column(name = "INCIDENT_ID", insertable = false, updatable = false, unique=false)
   private Long incidentId;
   
   @ManyToOne(targetEntity=IncidentGroupImpl.class, fetch = FetchType.LAZY)
   @JoinColumn(name = "INCIDENT_GROUP_ID")
   private IncidentGroup incidentGroup;
   
   @Column(name = "INCIDENT_GROUP_ID", insertable = false, updatable = false, unique=false)
   private Long incidentGroupId;
   
   @Column(name = "IS_ACTIVE",nullable=false)
   @Enumerated(EnumType.STRING)
   private StringBooleanEnum active;
   
   @OneToMany(targetEntity=ProjectionItemImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kind")
   private Collection<ProjectionItem> projectionItems;
   
   @Column(name = "STANDARD_COST", precision = 22)
   private BigDecimal standardCost;
   
   @OneToMany(targetEntity=KindAltDescImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kind")
   private Collection<KindAltDesc> kindAltDescs = new ArrayList<KindAltDesc>();
   
   @OneToMany(targetEntity=ResourceTrainingImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kind")
   private Collection<ResourceTraining> resourceTrainings = new ArrayList<ResourceTraining>();
   
   @OneToMany(targetEntity=RscTrainingTrainerImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "kind")
   private Collection<ResourceTraining> rscTrainingTrainers = new ArrayList<ResourceTraining>();
   
   @Column(name = "OBSOLETE")
   @Enumerated(EnumType.STRING)
   private StringBooleanEnum obsolete;
   
   public KindImpl() {
		super();
   }
   
   public KindImpl(String code, String description, Boolean isStandard) {

      super();
      
      if(code == null) {
         throw new IllegalArgumentException("the code cannot be null");
      }
      
      this.code = code;
      
      if(description == null) {
         throw new IllegalArgumentException("the description cannot be null");
      }
      
      this.description = description;
      
      if(isStandard == null) {
         throw new IllegalArgumentException("iStandard cannot be null");
      }
      
      this.standard = isStandard;
   }
   
   /**
    * @param code the kind code
    */
   public void setCode(String code) {
      this.code = code;
   }
   
   /**
    * @return the kind code
    */
   public String getCode() {
      return this.code;
   }

   /**
    * @param description the kind code description
    */
   public void setDescription(String description) {
      this.description = description;
   }
   
   /**
    * @return the kind code description
    */
   public String getDescription() {
      return this.description;
   }
   
   /**
    * @return the standard
    */
   public Boolean isStandard() {
      return this.standard;
   }

   /**
    * @param isStandard the isStandard to set
    */
   public void setStandard(Boolean isStandard) {
      this.standard = isStandard;
   }    
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Kind#getResourceKinds()
	 */
	public Collection<ResourceKind> getResourceKinds() {
		if(null==resourceKinds)
			resourceKinds=new ArrayList<ResourceKind>();
		
		return resourceKinds;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Kind#setResourceKinds(java.util.Collection)
	 */
	public void setResourceKinds(Collection<ResourceKind> resourceKinds) {
		this.resourceKinds = resourceKinds;
	}

   /* 
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#getId()
    */
   public Long getId() {
      return this.id;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
    */
   public void setId(Long id) {
      this.id = id;
   } 

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Kind#setRequestCategoryId(java.lang.Long)
    */
   public void setRequestCategoryId(Long id){
	   this.requestCategoryId=id;
	   
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Kind#getRequestCategoryId()
    */
   public Long getRequestCategoryId(){
	   return this.requestCategoryId;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Kind#getRequestCategory()
    */
   public RequestCategory getRequestCategory(){
	   return this.requestCategory;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Kind#setRequestCategory(gov.nwcg.isuite.core.domain.RequestCategory)
    */
   public void setRequestCategory(RequestCategory rc){
	   this.requestCategory=rc;
   }
   
   /**
    * @return the sit209Id
    */
   public Long getSit209Id() {
      return sit209Id;
   }

   /**
    * @param sit209Id the sit209Id to set
    */
   public void setSit209Id(Long sit209Id) {
      this.sit209Id = sit209Id;
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj) {
      if ( obj == null ) return false;
      if ( this == obj ) return true;
      if ( getClass() != obj.getClass() ) return false;
      KindImpl o = (KindImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,code,description,standard,requestCategoryId},
      			new Object[]{o.id,o.code,o.description,o.standard,o.requestCategoryId})
  	    .appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,code,description,standard,requestCategoryId})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("code", code)
	       .append("description", description)
	       .append("standard",standard)
	       .append("requestCategoryId",requestCategoryId)
	       .appendSuper(super.toString())
	       .toString();
   }

	/**
	 * @return the sysCostRateKinds
	 */
	public Collection<SysCostRateKind> getSysCostRateKinds() {
		return sysCostRateKinds;
	}
	
	/**
	 * @param sysCostRateKinds the sysCostRateKinds to set
	 */
	public void setSysCostRateKinds(Collection<SysCostRateKind> sysCostRateKinds) {
		this.sysCostRateKinds = sysCostRateKinds;
	}

	/**
	 * @return the resourceOthers
	 */
	public Collection<ResourceOther> getResourceOthers() {
		return resourceOthers;
	}

	/**
	 * @param resourceOthers the resourceOthers to set
	 */
	public void setResourceOthers(Collection<ResourceOther> resourceOthers) {
		this.resourceOthers = resourceOthers;
	}

	/**
	 * @return the incidentCostRateKinds
	 */
	public Collection<IncidentCostRateKind> getIncidentCostRateKinds() {
		return incidentCostRateKinds;
	}

	/**
	 * @param incidentCostRateKinds the incidentCostRateKinds to set
	 */
	public void setIncidentCostRateKinds(
			Collection<IncidentCostRateKind> incidentCostRateKinds) {
		this.incidentCostRateKinds = incidentCostRateKinds;
	}

	/**
	 * @param sit209 the sit209 to set
	 */
	public void setSit209(Sit209 sit209) {
		this.sit209 = sit209;
	}

	/**
	 * @return the sit209
	 */
	public Sit209 getSit209() {
		return sit209;
	}

	/**
	 * @param direct the direct to set
	 */
	public void setDirect(Boolean direct) {
		this.direct = direct;
	}

	/**
	 * @return the direct
	 */
	public Boolean getDirect() {
		return direct;
	}

	/**
	 * @param dailyForm the dailyForm to set
	 */
	public void setDailyForm(DailyForm dailyForm) {
		this.dailyForm = dailyForm;
	}

	/**
	 * @return the dailyForm
	 */
	public DailyForm getDailyForm() {
		return dailyForm;
	}

	/**
	 * @param dailyFormId the dailyFormId to set
	 */
	public void setDailyFormId(Long dailyFormId) {
		this.dailyFormId = dailyFormId;
	}

	/**
	 * @return the dailyFormId
	 */
	public Long getDailyFormId() {
		return dailyFormId;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(Integer units) {
		this.units = units;
	}

	/**
	 * @return the units
	 */
	public Integer getUnits() {
		return units;
	}

	/**
	 * @param people the people to set
	 */
	public void setPeople(Integer people) {
		this.people = people;
	}

	/**
	 * @return the people
	 */
	public Integer getPeople() {
		return people;
	}

	/**
	 * @param subGroupCategory the subGroupCategory to set
	 */
	public void setSubGroupCategory(SubGroupCategory subGroupCategory) {
		this.subGroupCategory = subGroupCategory;
	}

	/**
	 * @return the subGroupCategory
	 */
	public SubGroupCategory getSubGroupCategory() {
		return subGroupCategory;
	}

	/**
	 * @param subGroupCategoryId the subGroupCategoryId to set
	 */
	public void setSubGroupCategoryId(Long subGroupCategoryId) {
		this.subGroupCategoryId = subGroupCategoryId;
	}

	/**
	 * @return the subGroupCategoryId
	 */
	public Long getSubGroupCategoryId() {
		return subGroupCategoryId;
	}

	/**
	 * @param groupCategory the groupCategory to set
	 */
	public void setGroupCategory(GroupCategory groupCategory) {
		this.groupCategory = groupCategory;
	}

	/**
	 * @return the groupCategory
	 */
	public GroupCategory getGroupCategory() {
		return groupCategory;
	}

	/**
	 * @param groupCategoryId the groupCategoryId to set
	 */
	public void setGroupCategoryId(Long groupCategoryId) {
		this.groupCategoryId = groupCategoryId;
	}

	/**
	 * @return the groupCategoryId
	 */
	public Long getGroupCategoryId() {
		return groupCategoryId;
	}

	/**
	 * @param lineOverhead the lineOverhead to set
	 */
	public void setLineOverhead(Boolean lineOverhead) {
		this.lineOverhead = lineOverhead;
	}

	/**
	 * @return the lineOverhead
	 */
	public Boolean getLineOverhead() {
		return lineOverhead;
	}

	/**
	 * @param subordinate the subordinate to set
	 */
	public void setSubordinate(Boolean subordinate) {
		this.subordinate = subordinate;
	}

	/**
	 * @return the subordinate
	 */
	public Boolean getSubordinate() {
		return subordinate;
	}

	/**
	 * @param strikeTeam the strikeTeam to set
	 */
	public void setStrikeTeam(Boolean strikeTeam) {
		this.strikeTeam = strikeTeam;
	}

	/**
	 * @return the strikeTeam
	 */
	public Boolean getStrikeTeam() {
		return strikeTeam;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the departmentId
	 */
	public Long getDepartmentId() {
		return departmentId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Kind#setAssignments(java.util.Collection)
	 */
	public void setAssignments(Collection<Assignment> assignments) {
		this.assignments = assignments;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Kind#getAssignments()
	 */
	public Collection<Assignment> getAssignments() {
		return assignments;
	}

	public StringBooleanEnum getAircraft() {
		return aircraft;
	}

	public void setAircraft(StringBooleanEnum aircraft) {
		this.aircraft = aircraft;
	}

	/**
	 * @param iapPositionItemCodes the iapPositionItemCodes to set
	 */
	public void setIapPositionItemCodes(Collection<IapPositionItemCode> iapPositionItemCodes) {
		this.iapPositionItemCodes = iapPositionItemCodes;
	}

	/**
	 * @return the iapPositionItemCodes
	 */
	public Collection<IapPositionItemCode> getIapPositionItemCodes() {
		return iapPositionItemCodes;
	}

	/**
	 * @param iapBranchPosItemCodes the iapBranchPosItemCodes to set
	 */
	public void setIapBranchPosItemCodes(Collection<IapBranchPosItemCode> iapBranchPosItemCodes) {
		this.iapBranchPosItemCodes = iapBranchPosItemCodes;
	}

	/**
	 * @return the iapBranchPosItemCodes
	 */
	public Collection<IapBranchPosItemCode> getIapBranchPosItemCodes() {
		return iapBranchPosItemCodes;
	}   
	
	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	/**
	 * @return the incident
	 */
	public Incident getIncident() {
		return incident;
	}
	
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	
	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) {
		this.incidentGroup = incidentGroup;
	}
	
	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup() {
		return incidentGroup;
	}
	
	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	
	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}
	
	/**
	 * @param active the active to set
	 */
	public void setActive(StringBooleanEnum active) {
		this.active = active;
	}
	
	/**
	 * @return the active
	 */
	public StringBooleanEnum isActive() {
		return active;
	}

	/**
	 * @return the projectionItems
	 */
	public Collection<ProjectionItem> getProjectionItems() {
		return projectionItems;
	}

	/**
	 * @param projectionItems the projectionItems to set
	 */
	public void setProjectionItems(Collection<ProjectionItem> projectionItems) {
		this.projectionItems = projectionItems;
	}

	/**
	 * @param standardCost the standardCost to set
	 */
	public void setStandardCost(BigDecimal standardCost) {
		this.standardCost = standardCost;
	}

	/**
	 * @return the standardCost
	 */
	public BigDecimal getStandardCost() {
		return standardCost;
	}

	/**
	 * @return the branchSettingPositions
	 */
	public Collection<BranchSettingPosition> getBranchSettingPositions() {
		return branchSettingPositions;
	}

	/**
	 * @param branchSettingPositions the branchSettingPositions to set
	 */
	public void setBranchSettingPositions(
			Collection<BranchSettingPosition> branchSettingPositions) {
		this.branchSettingPositions = branchSettingPositions;
	}
	
	/**
	 * @return the inDirect
	 */
	public Boolean getInDirect() {
		return inDirect;
	}

	/**
	 * @param inDirect the inDirect to set
	 */
	public void setInDirect(Boolean inDirect) {
		this.inDirect = inDirect;
	}

	/**
	 * @param kindAltDescs the kindAltDescs to set
	 */
	public void setKindAltDescs(Collection<KindAltDesc> kindAltDescs) {
		this.kindAltDescs = kindAltDescs;
	}

	/**
	 * @return the kindAltDescs
	 */
	public Collection<KindAltDesc> getKindAltDescs() {
		return kindAltDescs;
	}
	
	/**
	 * @param resourceTrainings the resourceTrainings to set
	 */
	public void setResourceTrainings(Collection<ResourceTraining> resourceTrainings) {
		this.resourceTrainings = resourceTrainings;
	}

	/**
	 * @return the resourceTrainings
	 */
	public Collection<ResourceTraining> getResourceTrainings() {
		return resourceTrainings;
	}

	/**
	 * @param rscTrainingTrainers the rscTrainingTrainers to set
	 */
	public void setRscTrainingTrainers(Collection<ResourceTraining> rscTrainingTrainers) {
		this.rscTrainingTrainers = rscTrainingTrainers;
	}

	/**
	 * @return the rscTrainingTrainers
	 */
	public Collection<ResourceTraining> getRscTrainingTrainers() {
		return rscTrainingTrainers;
	}
	
	/**
	 * @param obsolete
	 */
	public void setObsolete(StringBooleanEnum obsolete) {
		this.obsolete = obsolete;
	}

	/**
	 * @return obsolete
	 */
	public StringBooleanEnum getObsolete() {
		return obsolete;
	}
   
}
