package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.vo.AccrualCodeVo;
import gov.nwcg.isuite.core.vo.AdjustCategoryVo;
import gov.nwcg.isuite.core.vo.AssignmentStatusVo;
import gov.nwcg.isuite.core.vo.ComplexityVo;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.core.vo.EmploymentTypeVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.IncidentRestrictedStatusVo;
import gov.nwcg.isuite.core.vo.RateAreaVo;
import gov.nwcg.isuite.core.vo.RateClassRateVo;
import gov.nwcg.isuite.core.vo.RateClassVo;
import gov.nwcg.isuite.core.vo.RateTypeVo;
import gov.nwcg.isuite.core.vo.RecommendationVo;
import gov.nwcg.isuite.core.vo.RegionCodeVo;
import gov.nwcg.isuite.core.vo.RequestCategoryVo;
import gov.nwcg.isuite.core.vo.ResourceClassificationVo;
import gov.nwcg.isuite.core.vo.SectionVo;
import gov.nwcg.isuite.core.vo.SpecialPayVo;
import gov.nwcg.isuite.core.vo.SystemParameterVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.SystemTypeVo;
import gov.nwcg.isuite.core.vo.TrainingSystemVo;
import gov.nwcg.isuite.core.vo.UnitOfMeasureVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.wrapper.StaticDataWrapperVo;
import gov.nwcg.isuite.core.vo.wrapper.StaticDataWrapperVo2;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;


public interface SystemService {

	public String getRunMode() throws Exception;

	public DialogueVo getSiteDatabaseList(DialogueVo dialogueVo);

	public DialogueVo connectToSiteDatabase(DbAvailVo vo, DialogueVo dialogueVo) throws ServiceException ;
	
    public StaticDataWrapperVo getStaticDataWrapper() throws ServiceException ;
    	
	public String getSessionTimeout() throws Exception;

	public Collection<SystemRoleVo> getSystemRoles() throws Exception;

	public Collection<SystemRoleVo> getPrivilegedSystemRoles() throws Exception;
	
	public Collection<SystemRoleVo> getNonPrivilegedSystemRoles() throws Exception;

	public Collection<RequestCategoryVo> getRequestCategories() throws Exception;
	
	public Collection<ComplexityVo> getComplexityVos() throws Exception;
	
	public Collection<RecommendationVo> getRecommendationVos() throws Exception;
	
	public String getAdminUserStatus() throws Exception;

	public Collection<AssignmentStatusVo> getAssignmentStatuses() throws Exception;
	
	public Collection<EmploymentTypeVo> getEmploymentTypes() throws Exception;

	public Collection<IncidentRestrictedStatusVo> getIncidentRestrictedStatuses() throws Exception;

	public Collection<ResourceClassificationVo> getResourceClassifications() throws Exception;
	
	/**
	 * 
	 * @return {@link Collection} of {@link RegionCodeVo} objects.
	 * @throws Exception
	 */
	public Collection<RegionCodeVo> getRegionCodes() throws Exception;

   public Collection<SpecialPayVo> getSpecialPays() throws Exception;  
   
   public Collection<AdjustCategoryVo> getAdjustCategories() throws Exception;
   
   public Collection<RateTypeVo> getRateTypes() throws Exception;
   
   public Collection<UnitOfMeasureVo> getUnitsOfMeasure() throws Exception;

   public Collection<RateAreaVo> getRateAreaVos() throws Exception;
   
   public Collection<RateClassVo> getRateClasses() throws Exception;

   public Collection<SystemParameterVo> getSystemParams() throws Exception;

   public Collection<RateClassRateVo> getRateClassRates() throws Exception;

   public Collection<AccrualCodeVo> getAccrualCodes() throws Exception;

   public Collection<Integer> getRateYears() throws Exception;

   public GlobalCacheVo getGlobalCache() throws ServiceException;
   
   public DialogueVo getNonPdcOrgs(DialogueVo dialogueVo) throws ServiceException;
   
   public Collection<SystemTypeVo> getSystemTypes() throws Exception;
   
   public Collection<SectionVo> getSections() throws Exception;

   public DialogueVo getIncidentRefData(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;   

   public DialogueVo getLastRecoverCode(String dbName, DialogueVo dialogueVo) throws ServiceException;

   public DialogueVo authenticateRecoverCode(String dbName,String authCode, DialogueVo dialogueVo) throws ServiceException;

   public DialogueVo saveSystemTraining(TrainingSystemVo vo, DialogueVo dialogueVo) throws ServiceException;

   public DialogueVo getTrainingMode(DialogueVo dialogueVo) throws Exception;

   public DialogueVo saveTrainingSystemDate(TrainingSystemVo vo,DialogueVo dialogueVo) throws Exception;
   
	public StaticDataWrapperVo2 getStaticDataWrapper2() throws ServiceException;
}
