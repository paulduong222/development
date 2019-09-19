package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceTrainingImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class ResourceTrainingVo extends AbstractVo implements PersistableVo {
	private KindVo kindVo;
	private IncidentResourceVo incidentResourceVo;
	private Boolean initialAssignment;
	private DateTransferVo startDateTransferVo = new DateTransferVo();
	private DateTransferVo endDateTransferVo = new DateTransferVo();
	private Boolean fsPriorityTrainee;
	private Boolean validRedCard;
	private String objectiveIssuer;
	private Collection<RscTrainingTrainerVo> rscTrainingTrainerVos = new ArrayList<RscTrainingTrainerVo>();
	private Collection<RscTrainingObjectiveVo> rscTrainingObjectiveVos = new ArrayList<RscTrainingObjectiveVo>();
	private Integer ptbPercentage;
	private Long numberOfAcres;
	private String tnspComments;
	private Boolean incidentTaskBook;
	private ComplexityVo complexityVo;
	private PriorityProgramVo priorityProgramVo;
	private RecommendationVo recommendationVo;
	private Collection<FuelTypeVo> fuelTypeVos = new ArrayList<FuelTypeVo>();
	
	/**
	 * Default constructor
	 */
	public ResourceTrainingVo(){
		super();
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static ResourceTrainingVo getInstance(ResourceTraining entity, boolean cascadable) throws Exception {
		ResourceTrainingVo vo = new ResourceTrainingVo();
		
		if(null != entity) {
			vo.setId(entity.getId());
			
			if(cascadable){
				if(null != entity.getKind()){
					vo.setKindVo(KindVo.getInstance(entity.getKind(), true));
				}
				if(null != entity.getIncidentResource()){
					vo.setIncidentResourceVo(IncidentResourceVo.getInstance(entity.getIncidentResource(), true));
				}
				vo.setInitialAssignment(StringBooleanEnum.toBooleanValue(entity.isInitialAssignment()));
				if(DateUtil.hasValue(entity.getStartDate()))
					DateTransferVo.populateDate(vo.getStartDateTransferVo(), entity.getStartDate());
				if(DateUtil.hasValue(entity.getEndDate()))
					DateTransferVo.populateDate(vo.getEndDateTransferVo(), entity.getEndDate());
				vo.setFsPriorityTrainee(StringBooleanEnum.toBooleanValue(entity.isFsPriorityTrainee()));
				vo.setValidRedCard(StringBooleanEnum.toBooleanValue(entity.isValidRedCard()));
				vo.setObjectiveIssuer(entity.getObjectiveIssuer());
				vo.setPtbPercentage(entity.getPtbPercentage());
				vo.setNumberOfAcres(entity.getNumberOfAcres());
				vo.setTnspComments(entity.getTnspComments());
				vo.setIncidentTaskBook(StringBooleanEnum.toBooleanValue(entity.isIncidentTaskBook()));
				
				if(null != entity.getPriorityProgram())
					vo.setPriorityProgramVo(PriorityProgramVo.getInstance(entity.getPriorityProgram(), true));
				
				if(null != entity.getComplexity())
					vo.setComplexityVo(ComplexityVo.getInstance(entity.getComplexity(), true));
				
				if(null != entity.getRecommendation())
					vo.setRecommendationVo(RecommendationVo.getInstance(entity.getRecommendation(), true));
				
				if(null != entity.getRscTrainingTrainers())
					vo.setRscTrainingTrainerVos(RscTrainingTrainerVo.getInstances(entity.getRscTrainingTrainers(), true));
				
				if(null != entity.getRscTrainingObjectives())
					vo.setRscTrainingObjectiveVos(RscTrainingObjectiveVo.getInstances(entity.getRscTrainingObjectives(), true));
				
				if(null != entity.getFuelTypes())
					vo.setFuelTypeVos(FuelTypeVo.getInstances(entity.getFuelTypes(), true));
				
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
	public static Collection<ResourceTrainingVo> getInstances(Collection<ResourceTraining> entities, boolean cascadable) throws Exception {
		Collection<ResourceTrainingVo> vos = new ArrayList<ResourceTrainingVo>();
		
		for(ResourceTraining entity : entities){
			vos.add(ResourceTrainingVo.getInstance(entity, cascadable));
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
	public static ResourceTraining toEntity(ResourceTraining entity, ResourceTrainingVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new ResourceTrainingImpl();
		
		if(vo.getId()==0){
			entity.setId(null);
		}else{
			entity.setId(vo.getId());
		}
		
		if(cascadable){
			if(null != vo.getKindVo())
				entity.setKind(KindVo.toEntity(null, vo.getKindVo(), false));
			
			IncidentResource incidentResourceEntity=(IncidentResource)getPersistableObject(persistables,IncidentResourceImpl.class);
			if(null != incidentResourceEntity)
				entity.setIncidentResource(incidentResourceEntity);
			
			entity.setInitialAssignment(StringBooleanEnum.toEnumValue(vo.getInitialAssignment()));
			
			if(DateTransferVo.hasDateString(vo.getStartDateTransferVo()))
				entity.setStartDate(DateTransferVo.getTransferDate(vo.getStartDateTransferVo()));
			
			if(DateTransferVo.hasDateString(vo.getEndDateTransferVo()))
				entity.setEndDate(DateTransferVo.getTransferDate(vo.getEndDateTransferVo()));
			else
				entity.setEndDate(null);
			
			entity.setFsPriorityTrainee(StringBooleanEnum.toEnumValue(vo.getFsPriorityTrainee()));
			entity.setValidRedCard(StringBooleanEnum.toEnumValue(vo.getValidRedCard()));
			entity.setObjectiveIssuer(vo.getObjectiveIssuer());
			entity.setPtbPercentage(vo.getPtbPercentage());
			entity.setNumberOfAcres(vo.getNumberOfAcres());
			entity.setTnspComments(vo.getTnspComments());
			entity.setIncidentTaskBook(StringBooleanEnum.toEnumValue(vo.getIncidentTaskBook()));

			if(BooleanUtility.isTrue(vo.getFsPriorityTrainee())){
				if(null != vo.getPriorityProgramVo())
					entity.setPriorityProgram(PriorityProgramVo.toEntity(null, vo.getPriorityProgramVo(), false, persistables));
				else
					entity.setPriorityProgram(null);
			}else{
				entity.setPriorityProgram(null);
			}
			
			if(null != vo.getComplexityVo())
				entity.setComplexity(ComplexityVo.toEntity(null, vo.getComplexityVo(), false, persistables));
			
			if(null != vo.getRecommendationVo())
				entity.setRecommendation(RecommendationVo.toEntity(null, vo.getRecommendationVo(), false, persistables));
			else
				entity.setRecommendation(null);
			
			if(null != vo.getRscTrainingTrainerVos())
				entity.setRscTrainingTrainers(RscTrainingTrainerVo.toEntityList(vo.getRscTrainingTrainerVos(), true));
			
			if(null != vo.getRscTrainingObjectiveVos())
				entity.setRscTrainingObjectives(RscTrainingObjectiveVo.toEntityList(vo.getRscTrainingObjectiveVos(), true, entity));
			
			if(null != vo.getFuelTypeVos())
				entity.setFuelTypes(FuelTypeVo.toEntityList(vo.getFuelTypeVos(), true));
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
	public static Collection<ResourceTraining> toEntityList(Collection<ResourceTrainingVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<ResourceTraining> entities = new ArrayList<ResourceTraining>();
		
		for(ResourceTrainingVo vo : vos){
			entities.add(ResourceTrainingVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}

	/**
	 * @param initialAssignment the initialAssignment to set
	 */
	public void setInitialAssignment(Boolean initialAssignment) {
		this.initialAssignment = initialAssignment;
	}

	/**
	 * @return the initialAssignment
	 */
	public Boolean getInitialAssignment() {
		return initialAssignment;
	}

	/**
	 * @param startDateTransferVo the startDateTransferVo to set
	 */
	public void setStartDateTransferVo(DateTransferVo startDateTransferVo) {
		this.startDateTransferVo = startDateTransferVo;
	}

	/**
	 * @return the startDateTransferVo
	 */
	public DateTransferVo getStartDateTransferVo() {
		return startDateTransferVo;
	}

	/**
	 * @param endDateTransferVo the endDateTransferVo to set
	 */
	public void setEndDateTransferVo(DateTransferVo endDateTransferVo) {
		this.endDateTransferVo = endDateTransferVo;
	}

	/**
	 * @return the endDateTransferVo
	 */
	public DateTransferVo getEndDateTransferVo() {
		return endDateTransferVo;
	}

	/**
	 * @param fsPriorityTrainee the fsPriorityTrainee to set
	 */
	public void setFsPriorityTrainee(Boolean fsPriorityTrainee) {
		this.fsPriorityTrainee = fsPriorityTrainee;
	}

	/**
	 * @return the fsPriorityTrainee
	 */
	public Boolean getFsPriorityTrainee() {
		return fsPriorityTrainee;
	}

	/**
	 * @param validRedCard the validRedCard to set
	 */
	public void setValidRedCard(Boolean validRedCard) {
		this.validRedCard = validRedCard;
	}

	/**
	 * @return the validRedCard
	 */
	public Boolean getValidRedCard() {
		return validRedCard;
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
	 * @param kindVo the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}

	/**
	 * @return the kindVo
	 */
	public KindVo getKindVo() {
		return kindVo;
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
	 * @param rscTrainingTrainerVos the rscTrainingTrainerVos to set
	 */
	public void setRscTrainingTrainerVos(Collection<RscTrainingTrainerVo> rscTrainingTrainerVos) {
		this.rscTrainingTrainerVos = rscTrainingTrainerVos;
	}

	/**
	 * @return the rscTrainingTrainerVos
	 */
	public Collection<RscTrainingTrainerVo> getRscTrainingTrainerVos() {
		return rscTrainingTrainerVos;
	}

	/**
	 * @param rscTrainingObjectiveVos the rscTrainingObjectiveVos to set
	 */
	public void setRscTrainingObjectiveVos(Collection<RscTrainingObjectiveVo> rscTrainingObjectiveVos) {
		this.rscTrainingObjectiveVos = rscTrainingObjectiveVos;
	}

	/**
	 * @return the rscTrainingObjectiveVos
	 */
	public Collection<RscTrainingObjectiveVo> getRscTrainingObjectiveVos() {
		return rscTrainingObjectiveVos;
	}

	/**
	 * @param incidentTaskBook the incidentTaskBook to set
	 */
	public void setIncidentTaskBook(Boolean incidentTaskBook) {
		this.incidentTaskBook = incidentTaskBook;
	}

	/**
	 * @return the incidentTaskBook
	 */
	public Boolean getIncidentTaskBook() {
		return incidentTaskBook;
	}
	
	/**
	 * @param complexityVo the complexityVo to set
	 */
	public void setComplexityVo(ComplexityVo complexityVo) {
		this.complexityVo = complexityVo;
	}

	/**
	 * @return the complexityVo
	 */
	public ComplexityVo getComplexityVo() {
		return complexityVo;
	}

	/**
	 * @param priorityProgramVo the priorityProgramVo to set
	 */
	public void setPriorityProgramVo(PriorityProgramVo priorityProgramVo) {
		this.priorityProgramVo = priorityProgramVo;
	}

	/**
	 * @return the priorityProgramVo
	 */
	public PriorityProgramVo getPriorityProgramVo() {
		return priorityProgramVo;
	}

	/**
	 * @param fuelTypeVos the fuelTypeVos to set
	 */
	public void setFuelTypeVos(Collection<FuelTypeVo> fuelTypeVos) {
		this.fuelTypeVos = fuelTypeVos;
	}

	/**
	 * @return the fuelTypeVos
	 */
	public Collection<FuelTypeVo> getFuelTypeVos() {
		return fuelTypeVos;
	}

	/**
	 * @param recommendationVo the recommendationVo to set
	 */
	public void setRecommendationVo(RecommendationVo recommendationVo) {
		this.recommendationVo = recommendationVo;
	}

	/**
	 * @return the recommendationVo
	 */
	public RecommendationVo getRecommendationVo() {
		return recommendationVo;
	}
}
