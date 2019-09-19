package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.filter.CountrySubdivisionFilter;
import gov.nwcg.isuite.core.service.impl.CountrySubdivisionServiceImpl;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * This is the interface for the {@link CountrySubdivisionServiceImpl} class and allows a user
 * to add/edit/and otherwise modify {@link CountrySubdivision} records in the system
 * 
 * @author gyder
 *
 */
public interface CountrySubdivisionService {

   /**
    * Get the User Group grid populated based on the provided filter set.
    * 
    * @param filter {@link CountrySubdivisionFilter}
    * @return {@link Collection} of {@link CountrySubdivisionVo} objects.
    * @throws ServiceException
    */
   public Collection<CountryCodeSubdivisionVo> getGrid(CountrySubdivisionFilter filter) throws ServiceException;

   /**
    * Retrieve CountrySubdivisionVo data
    * 
    * @param id The id of the {@link CountrySubdivision} we are retrieving
    * @return The {@link CountrySubdivisionVo} for the id specified
    * @throws ServiceException
    */
   public CountryCodeSubdivisionVo getById(Long id) throws ServiceException;
   
   /**
    * Saves the CountrySubdivision information
    * 
    * @param vo The {@link CountrySubdivisionVo} we are saving
    * @throws ServiceException
    */
   public CountryCodeSubdivisionVo save(CountryCodeSubdivisionVo vo) throws ServiceException;

   /**
    * Deletes a {@link Collection} of {@link CountrySubdivision} records
    * 
    * @param agencyGroupIds The ids of the {@link CountrySubdivision} records
    * @throws ServiceException
    */
   public void delete(Collection<Long> agencyGroupIds) throws ServiceException;
   
}
