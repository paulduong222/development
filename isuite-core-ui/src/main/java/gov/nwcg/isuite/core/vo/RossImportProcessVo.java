package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.RossImportProcess;
import gov.nwcg.isuite.core.domain.impl.RossImportProcessImpl;
import gov.nwcg.isuite.core.vo.rossimport.DataConflictWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.ExcludeResourceWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.MatchByRequestNumberAndNameWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.MatchByRequestNumberWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.MatchByResourceAndItemNameWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.MatchIncidentsWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.OverheadResourceGroupWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.ReviewRosteredResourcesWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.UnmatchedResourcesNoReqNumWizardVo;
import gov.nwcg.isuite.core.vo.rossimport.UnmatchedResourcesWizardVo;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Collection;

public class RossImportProcessVo extends AbstractVo {
	private RossXmlFileVo rossXmlFileVo;
	private String completedStage;

	// list of ross resources
	private Collection<RossImportProcessResourceVo> rossImportProcessResourceVos = new ArrayList<RossImportProcessResourceVo>();

	// list of eisuite resources
	private Collection<RossImportProcessEISuiteResourceVo> rossImportProcessEisuiteVos = new ArrayList<RossImportProcessEISuiteResourceVo>();
	
	// match incident screen data
	private MatchIncidentsWizardVo matchIncidentsWizardVo = new MatchIncidentsWizardVo();

	// resolve data conflicts screen data
	private DataConflictWizardVo dataConflictWizardVo = new DataConflictWizardVo();
	
	// view resources / exclude screen data
	private ExcludeResourceWizardVo excludeResourceWizardVo = new ExcludeResourceWizardVo();
	
	// match by request number and resource name screen data
	private MatchByRequestNumberAndNameWizardVo matchByRequestNumberAndNameWizardVo = new MatchByRequestNumberAndNameWizardVo();

	// match by resource and item name screen data
	private MatchByResourceAndItemNameWizardVo matchByResourceAndItemNameWizardVo = new MatchByResourceAndItemNameWizardVo();

	// match by request number screen data
	private MatchByRequestNumberWizardVo matchByRequestNumberWizardVo = new MatchByRequestNumberWizardVo();
	
	// unmatched resources no req num screen data
	private UnmatchedResourcesNoReqNumWizardVo unmatchedResourcesNoReqNumWizardVo = new UnmatchedResourcesNoReqNumWizardVo();
	
	// unmatched resources screen data
	private UnmatchedResourcesWizardVo unmatchedResourcesWizardVo = new UnmatchedResourcesWizardVo();
	
	// review rostered resources screen data
	private ReviewRosteredResourcesWizardVo reviewRosteredResourcesWizardVo = new ReviewRosteredResourcesWizardVo();
	
	// overhead resource group screen data
	private OverheadResourceGroupWizardVo overheadResourceGroupWizardVo = new OverheadResourceGroupWizardVo();
	
	public RossImportProcessVo(){
		
	}

	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static RossImportProcessVo getInstance(RossImportProcess entity, Boolean cascadable) throws Exception {
		RossImportProcessVo vo = new RossImportProcessVo();

		if(null == entity)
			throw new Exception("Unable to create RossImportProcessVo from null RossImportProcess entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setCompletedStage(entity.getCompletedStage());
			
			if(null != entity.getRossXmlFileId())
				vo.setRossXmlFileVo(RossXmlFileVo.getInstance(entity.getRossXmlFile(),false));
			
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<RossImportProcessVo> getInstances(Collection<RossImportProcess> entities, Boolean cascadable) throws Exception {
		Collection<RossImportProcessVo> vos = new ArrayList<RossImportProcessVo>();
		
		for(RossImportProcess entity : entities){
			vos.add(RossImportProcessVo.getInstance(entity,cascadable));
		}
		
		return vos;
	}

	/**
	 * @param vo
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static RossImportProcess toEntity(RossImportProcess entity,RossImportProcessVo vo, Boolean cascadable) throws Exception {
		if(null == entity)
			entity=new RossImportProcessImpl();

		entity.setId(vo.getId());

		if(cascadable){
			
			entity.setCompletedStage(vo.getCompletedStage());
		
			/*
			 * Validate the entity
			 */
			 validateEntity(entity);
		}

		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<RossImportProcess> toEntities(Collection<RossImportProcessVo> vos, Boolean cascadable) throws Exception {
		Collection<RossImportProcess> entities = new ArrayList<RossImportProcess>();
		
		for(RossImportProcessVo vo : vos){
			entities.add(RossImportProcessVo.toEntity(null,vo,cascadable));
		}
		
		return entities;
	}

	/**
	 * @param entity
	 * @throws ValidationException
	 */
	private static void validateEntity(RossImportProcess entity) throws ValidationException {
		
	}

	/**
	 * @return the rossXmlFileVo
	 */
	public RossXmlFileVo getRossXmlFileVo() {
		return rossXmlFileVo;
	}

	/**
	 * @param rossXmlFileVo the rossXmlFileVo to set
	 */
	public void setRossXmlFileVo(RossXmlFileVo rossXmlFileVo) {
		this.rossXmlFileVo = rossXmlFileVo;
	}

	/**
	 * @return the completedStage
	 */
	public String getCompletedStage() {
		return completedStage;
	}

	/**
	 * @param completedStage the completedStage to set
	 */
	public void setCompletedStage(String completedStage) {
		this.completedStage = completedStage;
	}

	/**
	 * @return the matchIncidentsWizardVo
	 */
	public MatchIncidentsWizardVo getMatchIncidentsWizardVo() {
		return matchIncidentsWizardVo;
	}

	/**
	 * @param matchIncidentsWizardVo the matchIncidentsWizardVo to set
	 */
	public void setMatchIncidentsWizardVo(
			MatchIncidentsWizardVo matchIncidentsWizardVo) {
		this.matchIncidentsWizardVo = matchIncidentsWizardVo;
	}

	/**
	 * @return the dataConflictWizardVo
	 */
	public DataConflictWizardVo getDataConflictWizardVo() {
		return dataConflictWizardVo;
	}

	/**
	 * @param dataConflictWizardVo the dataConflictWizardVo to set
	 */
	public void setDataConflictWizardVo(DataConflictWizardVo dataConflictWizardVo) {
		this.dataConflictWizardVo = dataConflictWizardVo;
	}

	/**
	 * @return the excludeResourceWizardVo
	 */
	public ExcludeResourceWizardVo getExcludeResourceWizardVo() {
		return excludeResourceWizardVo;
	}

	/**
	 * @param excludeResourceWizardVo the excludeResourceWizardVo to set
	 */
	public void setExcludeResourceWizardVo(
			ExcludeResourceWizardVo excludeResourceWizardVo) {
		this.excludeResourceWizardVo = excludeResourceWizardVo;
	}

	/**
	 * @return the matchByRequestNumberAndNameWizardVo
	 */
	public MatchByRequestNumberAndNameWizardVo getMatchByRequestNumberAndNameWizardVo() {
		return matchByRequestNumberAndNameWizardVo;
	}

	/**
	 * @param matchByRequestNumberAndNameWizardVo the matchByRequestNumberAndNameWizardVo to set
	 */
	public void setMatchByRequestNumberAndNameWizardVo(
			MatchByRequestNumberAndNameWizardVo matchByRequestNumberAndNameWizardVo) {
		this.matchByRequestNumberAndNameWizardVo = matchByRequestNumberAndNameWizardVo;
	}

	/**
	 * @return the matchByRequestNumberWizardVo
	 */
	public MatchByRequestNumberWizardVo getMatchByRequestNumberWizardVo() {
		return matchByRequestNumberWizardVo;
	}

	/**
	 * @param matchByRequestNumberWizardVo the matchByRequestNumberWizardVo to set
	 */
	public void setMatchByRequestNumberWizardVo(
			MatchByRequestNumberWizardVo matchByRequestNumberWizardVo) {
		this.matchByRequestNumberWizardVo = matchByRequestNumberWizardVo;
	}

	/**
	 * @return the unmatchedResourcesWizardVo
	 */
	public UnmatchedResourcesWizardVo getUnmatchedResourcesWizardVo() {
		return unmatchedResourcesWizardVo;
	}

	/**
	 * @param unmatchedResourcesWizardVo the unmatchedResourcesWizardVo to set
	 */
	public void setUnmatchedResourcesWizardVo(
			UnmatchedResourcesWizardVo unmatchedResourcesWizardVo) {
		this.unmatchedResourcesWizardVo = unmatchedResourcesWizardVo;
	}

	/**
	 * @return the reviewRosteredResourcesWizardVo
	 */
	public ReviewRosteredResourcesWizardVo getReviewRosteredResourcesWizardVo() {
		return reviewRosteredResourcesWizardVo;
	}

	/**
	 * @param reviewRosteredResourcesWizardVo the reviewRosteredResourcesWizardVo to set
	 */
	public void setReviewRosteredResourcesWizardVo(
			ReviewRosteredResourcesWizardVo reviewRosteredResourcesWizardVo) {
		this.reviewRosteredResourcesWizardVo = reviewRosteredResourcesWizardVo;
	}

	/**
	 * @return the overheadResourceGroupWizardVo
	 */
	public OverheadResourceGroupWizardVo getOverheadResourceGroupWizardVo() {
		return overheadResourceGroupWizardVo;
	}

	/**
	 * @param overheadResourceGroupWizardVo the overheadResourceGroupWizardVo to set
	 */
	public void setOverheadResourceGroupWizardVo(
			OverheadResourceGroupWizardVo overheadResourceGroupWizardVo) {
		this.overheadResourceGroupWizardVo = overheadResourceGroupWizardVo;
	}

	/**
	 * @return the rossImportProcessResourceVos
	 */
	public Collection<RossImportProcessResourceVo> getRossImportProcessResourceVos() {
		return rossImportProcessResourceVos;
	}

	/**
	 * @param rossImportProcessResourceVos the rossImportProcessResourceVos to set
	 */
	public void setRossImportProcessResourceVos(
			Collection<RossImportProcessResourceVo> rossImportProcessResourceVos) {
		this.rossImportProcessResourceVos = rossImportProcessResourceVos;
	}

	/**
	 * @return the matchByResourceAndItemNameWizardVo
	 */
	public MatchByResourceAndItemNameWizardVo getMatchByResourceAndItemNameWizardVo() {
		return matchByResourceAndItemNameWizardVo;
	}

	/**
	 * @param matchByResourceAndItemNameWizardVo the matchByResourceAndItemNameWizardVo to set
	 */
	public void setMatchByResourceAndItemNameWizardVo(
			MatchByResourceAndItemNameWizardVo matchByResourceAndItemNameWizardVo) {
		this.matchByResourceAndItemNameWizardVo = matchByResourceAndItemNameWizardVo;
	}

	/**
	 * @return the rossImportProcessEisuiteVos
	 */
	public Collection<RossImportProcessEISuiteResourceVo> getRossImportProcessEisuiteVos() {
		return rossImportProcessEisuiteVos;
	}

	/**
	 * @param rossImportProcessEisuiteVos the rossImportProcessEisuiteVos to set
	 */
	public void setRossImportProcessEisuiteVos(
			Collection<RossImportProcessEISuiteResourceVo> rossImportProcessEisuiteVos) {
		this.rossImportProcessEisuiteVos = rossImportProcessEisuiteVos;
	}

	public UnmatchedResourcesNoReqNumWizardVo getUnmatchedResourcesNoReqNumWizardVo() {
		return unmatchedResourcesNoReqNumWizardVo;
	}

	public void setUnmatchedResourcesNoReqNumWizardVo(
			UnmatchedResourcesNoReqNumWizardVo unmatchedResourcesNoReqNumWizardVo) {
		this.unmatchedResourcesNoReqNumWizardVo = unmatchedResourcesNoReqNumWizardVo;
	}


}
