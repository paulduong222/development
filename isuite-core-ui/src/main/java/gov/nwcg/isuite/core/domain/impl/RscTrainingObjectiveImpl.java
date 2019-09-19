package gov.nwcg.isuite.core.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.core.domain.RscTrainingObjective;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_RSC_TRAINING_OBJECTIVE", sequenceName="SEQ_RSC_TRAINING_OBJECTIVE")
@Table(name="isw_rsc_training_objective")
public class RscTrainingObjectiveImpl extends PersistableImpl implements RscTrainingObjective {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RSC_TRAINING_OBJECTIVE")
	private Long id;
	
	@ManyToOne(targetEntity=ResourceTrainingImpl.class, fetch=FetchType.LAZY)
    @JoinColumn(name = "RESOURCE_TRAINING_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private ResourceTraining resourceTraining;
    
    @Column(name="RESOURCE_TRAINING_ID", insertable = false, updatable = false, nullable = true)
	private Long resourceTrainingId;
    
    @Column(name="OBJECTIVE", length=255)
	private String objective;
    
    @Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;
	
	
	/**
	 * Default constructor
	 */
	public RscTrainingObjectiveImpl() {
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
	 * @param resourceTraining the resourceTraining to set
	 */
	public void setResourceTraining(ResourceTraining resourceTraining) {
		this.resourceTraining = resourceTraining;
	}

	/**
	 * @return the resourceTraining
	 */
	public ResourceTraining getResourceTraining() {
		return resourceTraining;
	}

	/**
	 * @param resourceTrainingId the resourceTrainingId to set
	 */
	public void setResourceTrainingId(Long resourceTrainingId) {
		this.resourceTrainingId = resourceTrainingId;
	}

	/**
	 * @return the resourceTrainingId
	 */
	public Long getResourceTrainingId() {
		return resourceTrainingId;
	}

	/**
	 * @param objective the objective to set
	 */
	public void setObjective(String objective) {
		this.objective = objective;
	}

	/**
	 * @return the objective
	 */
	public String getObjective() {
		return objective;
	}

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

}
