package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.EventType;
import gov.nwcg.isuite.core.filter.AgencyFilter;
import gov.nwcg.isuite.core.filter.CountryCodeFilter;
import gov.nwcg.isuite.core.filter.EventTypeFilter;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.KindFilter;
import gov.nwcg.isuite.core.filter.OrganizationFilter;
import gov.nwcg.isuite.core.vo.AccountCodePicklistVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.AssignmentStatusVo;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.core.vo.CountryCodeVo;
import gov.nwcg.isuite.core.vo.EventTypeVo;
import gov.nwcg.isuite.core.vo.IncidentPicklistVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.RequestCategoryVo;
import gov.nwcg.isuite.core.vo.ResourceClassificationVo;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.core.vo.SpecialPayVo;
import gov.nwcg.isuite.core.vo.TravelMethodVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * This serves up static or nearly static pick type lists from the database.  
 * 
 * @author nateCollette
 */
public interface PicklistService extends TransactionService {
      
   /**
    * Retrieve a collection of country codes.
    * @return
    * @throws ServiceException
    */
   public Collection<CountryCodeVo> getAllCountryCodes(CountryCodeFilter filter) throws ServiceException;

   /**
    * Retrieve a collection of all country code subdivisions
    * 
    * @return A Collection of CountryCodeSubdivisions.
    * @throws ServiceException
    */
   public Collection<CountryCodeSubdivisionVo> getCountryCodeSubdivisions() throws ServiceException;   
   
   /**
    * Retrieve a collection of subdivisions by country code id
    * 
    * @param id country code ID
    * @return A Collection of CountryCodeSubdivisions.
    * @throws ServiceException
    */
   public Collection<CountryCodeSubdivisionVo> getSubdivisionsByCountryCodeId(Long countryCodeId) throws ServiceException;

   /**
    * Retrieve a {@link Collection} of {@link EventType} objects.
    * 
    * @return
    * @throws ServiceException
    */
   public Collection<EventTypeVo> getAllEventTypes(EventTypeFilter filter) throws ServiceException;
   
   /**
    * 
    * @return
    * @throws ServiceException
    */
   public Collection<AccountCodePicklistVo> getAccrualOverrideCodes(String accountCode) throws ServiceException;

   /**
    * 
    * @param agencyFilter
    * @return
    * @throws ServiceException
    */
   public Collection<AgencyVo> getAgencies(AgencyFilter agencyFilter) throws ServiceException;
   
   /**
    * 
    * @return {@link IncidentPicklistVo} Collection
    * @throws ServiceException
    */
   public Collection<IncidentPicklistVo> getIncidents(IncidentFilter incidentFilter) throws ServiceException;

   /**
    * @return
    * @throws ServiceException
    */
   public Collection<KindVo> getKinds(KindFilter filter) throws ServiceException;

   /**
    * @return
    * @throws ServiceException
    */
   public Collection<JetPortVo> getJetPorts() throws ServiceException ;

	/**
    * @return
    * @throws ServiceException
    */
   public Collection<AssignmentStatusVo> getAssignmentStatuses() throws ServiceException;

   /**
    * @return
    * @throws ServiceException
    */
   public Collection<TravelMethodVo> getTravelMethods() throws ServiceException;

   /**
    * @return
    * @throws ServiceException
    */
   public Collection<ResourceClassificationVo> getResourceClassifications() throws ServiceException;
   
   /**
    * @return {@link RequestCategoryVo} {@link Collection}
    * @throws ServiceException
    */
   public Collection<RequestCategoryVo> getRequestCategories() throws ServiceException;
   
   /**
    * @return {@link Sit209Vo} {@link Collection}
    * @throws ServiceException
    */
   public Collection<Sit209Vo> getSit209Codes() throws ServiceException;
   
   public Collection<OrganizationVo> getOrganizations(OrganizationFilter filter) throws ServiceException;

   public Collection<SpecialPayVo> getSpecialPays() throws ServiceException;

}
