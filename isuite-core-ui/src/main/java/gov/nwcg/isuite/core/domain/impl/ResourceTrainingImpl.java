package gov.nwcg.isuite.core.domain.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.Complexity;
import gov.nwcg.isuite.core.domain.FuelType;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.PriorityProgram;
import gov.nwcg.isuite.core.domain.Recommendation;
import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.core.domain.RscTrainingObjective;
import gov.nwcg.isuite.core.domain.RscTrainingTrainer;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

@Entity
@SequenceGenerator(name="SEQ_RESOURCE_TRAINING", sequenceName="SEQ_RESOURCE_TRAINING")
@Table(name="isw_resource_training")
public class ResourceTrainingImpl extends PersistableImpl implements ResourceTraining {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RESOURCE_TRAINING")
	private Long id;
	
	@ManyToMany(targetEntity=FuelTypeImpl.class, fetch = FetchType.LAZY)
	@JoinTable(name = "ISW_RSC_TRAINING_FUEL_TYPE", 
			joinColumns = 
			{@JoinColumn(name = "RESOURCE_TRAINING_ID",  nullable = false, updatable = false) },
			inverseJoinColumns = 
			{ @JoinColumn(name = "FUEL_TYPE_ID", nullable = false, updatable = false) }
	)
	private Collection<FuelType> fuelTypes = new ArrayList<FuelType>();
	
    @ManyToOne(targetEntity=KindImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="KIND_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private Kind kind;
	
    @Column(name="KIND_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long kindId;
    
    @ManyToOne(targetEntity=PriorityProgramImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="PRIORITY_PROGRAM_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private PriorityProgram priorityProgram;
	
    @Column(name="PRIORITY_PROGRAM_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long priorityProgramId;
    
    @ManyToOne(targetEntity=ComplexityImpl.class, fetch=FetchType.LAZY)
    @JoinColumn(name = "COMPLEXITY_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private Complexity complexity;
  
    @Column(name="COMPLEXITY_ID", insertable = false, updatable = false, nullable = true)
	private Long complexityId;
    
    @ManyToOne(targetEntity=IncidentResourceImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_RESOURCE_ID", nullable = true)
	private IncidentResource incidentResource;
	
	@Column(name = "INCIDENT_RESOURCE_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long incidentResourceId;
	
	@Column(name = "IS_INITIAL_ASSIGNMENT",nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum initialAssignment;
	
	@Column(name = "START_DATE", length = 29)
	private Date startDate;
	
	@Column(name = "END_DATE", length = 29)
	private Date endDate;

	@Column(name = "IS_FS_PRIORITY_TRAINEE",nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum fsPriorityTrainee;
	
	@Column(name = "IS_VALID_RED_CARD",nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum validRedCard;
	
	@Column(name = "OBJECTIVE_ISSUER", length = 20)
	private String objectiveIssuer;
	
	@Column(name = "PTB_PERCENTAGE", nullable = true)
	private Integer ptbPercentage;
	
	@ManyToOne(targetEntity=RecommendationImpl.class, fetch=FetchType.LAZY)
    @JoinColumn(name = "RECOMMENDATION_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private Recommendation recommendation;
  
    @Column(name="RECOMMENDATION_ID", insertable = false, updatable = false, nullable = true)
	private Long recommendationId;
	
	@Column(name = "NUMBER_OF_ACRES", nullable = true)
	private Long numberOfAcres;
    
    @Column(name="TNSP_COMMENTS", length=450)
	private String tnspComments;
    
    @Column(name = "INCIDENT_TASK_BOOK",nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum incidentTaskBook;
	
	@OneToMany(targetEntity=RscTrainingTrainerImpl.class, fetch = FetchType.LAZY, mappedBy = "resourceTraining")
	private Collection<RscTrainingTrainer> rscTrainingTrainers = new ArrayList<RscTrainingTrainer>();
	
	@OneToMany(targetEntity=RscTrainingObjectiveImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resourceTraining")
	private Collection<RscTrainingObjective> rscTrainingObjectives = new ArrayList<RscTrainingObjective>();
	
	/**
	 * Default constructor
	 */
	public ResourceTrainingImpl() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the kind
	 */
	public Kind getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * @param priorityProgram the priorityProgram to set
	 */
	public void setPriorityProgram(PriorityProgram priorityProgram) {
		this.priorityProgram = priorityProgram;
	}

	/**
	 * @return the priorityProgram
	 */
	public PriorityProgram getPriorityProgram() {
		return priorityProgram;
	}

	/**
	 * @param priorityProgramId the priorityProgramId to set
	 */
	public void setPriorityProgramId(Long priorityProgramId) {
		this.priorityProgramId = priorityProgramId;
	}

	/**
	 * @return the priorityProgramId
	 */
	public Long getPriorityProgramId() {
		return priorityProgramId;
	}
	
	/**
	 * @param incidentResource the incidentResource to set
	 */
	public void setIncidentResource(IncidentResource incidentResource) {
		this.incidentResource = incidentResource;
	}

	/**
	 * @return the incidentResource
	 */
	public IncidentResource getIncidentResource() {
		return incidentResource;
	}

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}
	
	/**
	 * @param the initialAssignment to set
	 */
	public void setInitialAssignment(StringBooleanEnum initialAssignment) {
		this.initialAssignment = initialAssignment;
	}
	
	/**
	 * @return the initialAssignment
	 */
	public StringBooleanEnum isInitialAssignment() {
		return initialAssignment;
	}
	
	/**
	 * @param the fsPriorityTrainee to set
	 */
	public void setFsPriorityTrainee(StringBooleanEnum fsPriorityTrainee) {
		this.fsPriorityTrainee = fsPriorityTrainee;
	}
	
	/**
	 * @return the fsPriorityTrainee
	 */
	public StringBooleanEnum isFsPriorityTrainee() {
		return fsPriorityTrainee;
	}
	
	/**
	 * @param the validRedCard to set
	 */
	public void setValidRedCard(StringBooleanEnum validRedCard) {
		this.validRedCard = validRedCard;
	}
	
	/**
	 * @return the validRedCard
	 */
	public StringBooleanEnum isValidRedCard() {
		return validRedCard;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param objectiveIssuer the objectiveIssuer to set
	 */
	public void setObjectiveIssuer(String objectiveIssuer) {
		this.objectiveIssuer = objectiveIssuer;
	}

	/**
	 * @return the objectiveIssuer
	 */
	public String getObjectiveIssuer() {
		return objectiveIssuer;
	}
	
	/**
	 * @param ptbPercentage the ptbPercentage to set
	 */
	public void setPtbPercentage(Integer ptbPercentage) {
		this.ptbPercentage = ptbPercentage;
	}

	/**
	 * @return the ptbPercentage
	 */
	public Integer getPtbPercentage() {
		return ptbPercentage;
	}

	/**
	 * @param numberOfAcres the numberOfAcres to set
	 */
	public void setNumberOfAcres(Long numberOfAcres) {
		this.numberOfAcres = numberOfAcres;
	}

	/**
	 * @return the numberOfAcres
	 */
	public Long getNumberOfAcres() {
		return numberOfAcres;
	}

	/**
	 * @param tnspComments the tnspComments to set
	 */
	public void setTnspComments(String tnspComments) {
		this.tnspComments = tnspComments;
	}

	/**
	 * @return the tnspComments
	 */
	public String getTnspComments() {
		return tnspComments;
	}

	/**
	 * @param rscTrainingTrainers the rscTrainingTrainers to set
	 */
	public void setRscTrainingTrainers(Collection<RscTrainingTrainer> rscTrainingTrainers) {
		this.rscTrainingTrainers = rscTrainingTrainers;
	}

	/**
	 * @return the rscTrainingTrainers
	 */
	public Collection<RscTrainingTrainer> getRscTrainingTrainers() {
		return rscTrainingTrainers;
	}

	/**
	 * @param rscTrainingObjectives the rscTrainingObjectives to set
	 */
	public void setRscTrainingObjectives(Collection<RscTrainingObjective> rscTrainingObjectives) {
		this.rscTrainingObjectives = rscTrainingObjectives;
	}

	/**
	 * @return the rscTrainingObjectives
	 */
	public Collection<RscTrainingObjective> getRscTrainingObjectives() {
		return rscTrainingObjectives;
	}

	/**
	 * @param incidentTaskBook the incidentTaskBook to set
	 */
	public void setIncidentTaskBook(StringBooleanEnum incidentTaskBook) {
		this.incidentTaskBook = incidentTaskBook;
	}

	/**
	 * @return the incidentTaskBook
	 */
	public StringBooleanEnum isIncidentTaskBook() {
		return incidentTaskBook;
	}
	
	/**
	 * @param complexity the complexity to set
	 */
	public void setComplexity(Complexity complexity) {
		this.complexity = complexity;
	}

	/**
	 * @return the complexity
	 */
	public Complexity getComplexity() {
		return complexity;
	}

	/**
	 * @param complexityId the complexityId to set
	 */
	public void setComplexityId(Long complexityId) {
		this.complexityId = complexityId;
	}

	/**
	 * @return the complexityId
	 */
	public Long getComplexityId() {
		return complexityId;
	}

	/**
	 * @param fuelTypes the fuelTypes to set
	 */
	public void setFuelTypes(Collection<FuelType> fuelTypes) {
		this.fuelTypes = fuelTypes;
	}

	/**
	 * @return the fuelTypes
	 */
	public Collection<FuelType> getFuelTypes() {
		return fuelTypes;
	}

	/**
	 * @param recommendation the recommendation to set
	 */
	public void setRecommendation(Recommendation recommendation) {
		this.recommendation = recommendation;
	}

	/**
	 * @return the recommendation
	 */
	public Recommendation getRecommendation() {
		return recommendation;
	}

	/**
	 * @param recommendationId the recommendationId to set
	 */
	public void setRecommendationId(Long recommendationId) {
		this.recommendationId = recommendationId;
	}

	/**
	 * @return the recommendationId
	 */
	public Long getRecommendationId() {
		return recommendationId;
	}

}
