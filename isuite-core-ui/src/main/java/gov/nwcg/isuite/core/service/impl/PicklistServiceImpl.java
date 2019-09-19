package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.filter.AgencyFilter;
import gov.nwcg.isuite.core.filter.CountryCodeFilter;
import gov.nwcg.isuite.core.filter.CountrySubdivisionFilter;
import gov.nwcg.isuite.core.filter.EventTypeFilter;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.KindFilter;
import gov.nwcg.isuite.core.filter.OrganizationFilter;
import gov.nwcg.isuite.core.filter.impl.CountrySubdivisionFilterImpl;
import gov.nwcg.isuite.core.service.PicklistService;
import gov.nwcg.isuite.core.vo.AccountCodePicklistVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.AssignmentStatusVo;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.core.vo.CountryCodeVo;
import gov.nwcg.isuite.core.vo.EventTypeVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.IncidentPicklistVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.RequestCategoryVo;
import gov.nwcg.isuite.core.vo.ResourceClassificationVo;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.core.vo.SpecialPayVo;
import gov.nwcg.isuite.core.vo.TravelMethodVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;

import java.util.Collection;
/**
 * @author ncollette
 */
public class PicklistServiceImpl extends BaseService implements PicklistService {
   private GlobalCacheVo cacheVo = null;
   
   public PicklistServiceImpl(){
	   
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.picklist.PicklistService#getAllCountryCodes()
    */
   public Collection<CountryCodeVo> getAllCountryCodes(CountryCodeFilter filter) throws ServiceException {
      try {
    	  cacheVo=super.getGlobalCacheVo();
    	  
    	  return CountryCodeVo.getVosByFilter(filter, cacheVo.getCountryCodeVos());
      }
      catch ( Exception e ) {
    	  super.handleException(e);
      }
      
      return null;
   }

   public Collection<KindVo> getKinds(KindFilter filter) throws ServiceException {
	   try {
		   cacheVo=super.getGlobalCacheVo();

		   return KindVo.getVosByFilter(filter, cacheVo.getKindVos());
	   }
	   catch ( Exception e ) {
		   super.handleException(e);
	   }

	   return null;
   }

   public Collection<JetPortVo> getJetPorts() throws ServiceException {
	   try {
		   cacheVo=super.getGlobalCacheVo();

		   return cacheVo.getJetPortVos();
	   }
	   catch ( Exception e ) {
		   super.handleException(e);
	   }
	   
	   return null;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.picklist.PicklistService#getCountryCodeSubdivisions()
    */   
   public Collection<CountryCodeSubdivisionVo> getCountryCodeSubdivisions() throws ServiceException {
	   try {
		   cacheVo=super.getGlobalCacheVo();

		   return cacheVo.getCountryCodeSubdivisionVos();
	   }
	   catch ( Exception e ) {
		   super.handleException(e);
	   }

	   return null;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.picklist.PicklistService#getSubdivisionsByCountryCodeId(java.lang.Long)
    */
   public Collection<CountryCodeSubdivisionVo> getSubdivisionsByCountryCodeId(Long id) throws ServiceException {
	   try {
		   cacheVo=super.getGlobalCacheVo();

		   CountrySubdivisionFilter filter = new CountrySubdivisionFilterImpl();
		   filter.setCountryCodeId(id);

    	   return CountryCodeSubdivisionVo.getVosByFilter(filter, cacheVo.getCountryCodeSubdivisionVos());
	   }
	   catch ( Exception e ) {
		   super.handleException(e);
	   }

	   return null;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.picklist.PicklistService#getAllEventTypes()
    */
   public Collection<EventTypeVo> getAllEventTypes(EventTypeFilter filter) throws ServiceException {
	   try {
		   cacheVo=super.getGlobalCacheVo();

    	   return EventTypeVo.getVosByFilter(filter, cacheVo.getEventTypeVos());
	   }
	   catch ( Exception e ) {
		   super.handleException(e);
	   }

	   return null;
   }

   public Collection<AgencyVo> getAgencies(AgencyFilter agencyFilter) throws ServiceException {
	   try {
		   cacheVo=super.getGlobalCacheVo();

    	   return AgencyVo.getVosByFilter(agencyFilter, cacheVo.getAgencyVos());
	   }
	   catch ( Exception e ) {
		   super.handleException(e);
	   }

	   return null;
   }

   /**
    * @param travelMethodFilter
    * @return
    * @throws ServiceException
    */
   public Collection<TravelMethodVo> getTravelMethods() throws ServiceException{
	   return TravelMethodTypeEnum.getTravelMethodVoList();
   }

   public Collection<ResourceClassificationVo> getResourceClassifications() throws ServiceException{
	   return ResourceClassificationEnum.getResourceClassificationVoList();
   }

   public Collection<AssignmentStatusVo> getAssignmentStatuses() throws ServiceException{
	   return AssignmentStatusTypeEnum.getAssignmentVoList(false);
   }
   
   public Collection<RequestCategoryVo> getRequestCategories() throws ServiceException{
	   try {
		   cacheVo=super.getGlobalCacheVo();

		   return cacheVo.getRequestCategoryVos();
	   }
	   catch ( Exception e ) {
		   super.handleException(e);
	   }

	   return null;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.PicklistService#getSit209Codes()
    */
   @Override
   public Collection<Sit209Vo> getSit209Codes() throws ServiceException {
	   try {
		   cacheVo=super.getGlobalCacheVo();

		   return cacheVo.getSit209CodeVos();
	   }
	   catch ( Exception e ) {
		   super.handleException(e);
	   }

	   return null;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.PicklistService#getAccrualOverrideCodes(java.lang.String)
    */
   @Override
   public Collection<AccountCodePicklistVo> getAccrualOverrideCodes(String accountCode) throws ServiceException {
	   return null;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.PicklistService#getIncidents(gov.nwcg.isuite.core.filter.IncidentFilter)
    */
   @Override
   public Collection<IncidentPicklistVo> getIncidents(IncidentFilter incidentFilter) throws ServiceException {
	   return null;
   }

   public Collection<OrganizationVo> getOrganizations(OrganizationFilter filter) throws ServiceException {
	   try {
		   cacheVo=super.getGlobalCacheVo();

    	   return OrganizationVo.getVosByFilter(filter, cacheVo.getOrganizationVos());
	   }
	   catch ( Exception e ) {
		   super.handleException(e);
	   }

	   return null;
   }
   
   public Collection<SpecialPayVo> getSpecialPays() throws ServiceException {
	   try {
		   cacheVo=super.getGlobalCacheVo();

		   return cacheVo.getSpecialPayVos();
	   }
	   catch ( Exception e ) {
		   super.handleException(e);
	   }

	   return null;
   }
}
