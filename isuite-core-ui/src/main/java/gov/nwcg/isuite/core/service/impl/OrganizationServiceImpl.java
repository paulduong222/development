package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.filter.AgencyFilter;
import gov.nwcg.isuite.core.filter.OrganizationFilter;
import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.service.OrganizationService;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.OrganizationPicklistVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public class OrganizationServiceImpl extends BaseService implements OrganizationService {
   private OrganizationDao organizationDao; 
   private AgencyDao agencyDao;
   
   public OrganizationServiceImpl() {
	   super();
   }

   public void initialization(){
	   organizationDao = (OrganizationDao)super.context.getBean("organizationDao");
	   agencyDao = (AgencyDao)super.context.getBean("agencyDao");
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.organization.OrganizationService#getAgencies(gov.nwcg.isuite.domain.organization.AgencyFilter)
    */
   public Collection<AgencyVo> getAgencies(AgencyFilter filter) throws ServiceException {
      //This method is duplicated.  See getAgenciesForPicklist below. -dbudge
      try {
         return agencyDao.getAgencies(filter);
      }
      catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.organization.OrganizationService#getOrganizations(gov.nwcg.isuite.domain.organization.OrganizationFilter)
    */
   public Collection<OrganizationVo> getOrganizations(OrganizationFilter filter) throws ServiceException {

      GlobalCacheVo globalCacheVo = (GlobalCacheVo)super.context.getBean("globalCacheVo");
      if(null == filter) {
         return globalCacheVo.getOrganizationVos();
      }
      
      try {
         Collection <Organization> orgList = this.organizationDao.getAll(filter);
         try {
            return OrganizationVo.getInstances(orgList, true);
         }
         catch ( Exception e ) {
            super.handleException(e);
         }
         //         Collection <OrganizationVo> orgVos = new ArrayList<OrganizationVo>(orgList.size());
         //         for (Organization org : orgList) {
         //               orgVos.add(new OrganizationVo(org));
         //         }
         //         return orgVos;
      }

      catch ( PersistenceException e ) {
         super.handleException(e);
         //         throw new ServiceException(e);
      }
      return null;
   }
   
   

   public Collection<OrganizationVo> getOrganizationsByWorkArea(Long workAreaId) throws ServiceException {

	   GlobalCacheVo globalCacheVo = (GlobalCacheVo)super.context.getBean("globalCacheVo");
	   if(null == workAreaId) {
		   return globalCacheVo.getOrganizationVos();
	   }

	   try {
		   Collection <Organization> orgList = this.organizationDao.getByWorkAreaId(workAreaId);
		   try {
			   return OrganizationVo.getInstances(orgList, true);
		   }
		   catch ( Exception e ) {
			   super.handleException(e);
		   }
	   }

	   catch ( PersistenceException e ) {
		   super.handleException(e);
	   }
	   return null;
   }

   
   
   

//   public Organization voToDo(OrganizationVo theVo) throws ServiceException {
//      // TODO Auto-generated method stub
//      return null;
//   }

   public void delete(Organization persistable) throws ServiceException {
      // TODO Auto-generated method stub

   }

   public Collection<Organization> getAll(Class clazz) throws ServiceException {
      // TODO Auto-generated method stub
      return null;
   }

   public Collection<Organization> getAll(Filter filter) throws ServiceException {
      // TODO Auto-generated method stub
      return null;
   }
   
   public OrganizationVo getById(Long id) throws ServiceException {
      Organization org = null;
      try {
         org = organizationDao.getById(id, OrganizationImpl.class);
      }
      catch ( PersistenceException e ) {
         super.handleException(e);
      }
      try {
         return OrganizationVo.getInstance(org, true);
      }
      catch ( Exception e ) {
         super.handleException(e);
      }
      return null;
   }

   public void save(Organization persistable) throws ServiceException {
      // TODO Auto-generated method stub

   }

   public Collection<OrganizationVo> dosToVos(Collection<Organization> theDos) throws ServiceException {
      // TODO Auto-generated method stub
      return null;
   }

   public Organization voToDo(OrganizationVo theVo, Organization theDo) throws ServiceException {
      // TODO Auto-generated method stub
      return null;
   }

   public Collection<Organization> vosToDos(Collection<OrganizationVo> theVos) throws ServiceException {
      // TODO Auto-generated method stub
      return null;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.service.organization.OrganizationService#getOrganizationPickList(java.lang.String)
    */
   public Collection<OrganizationPicklistVo> getOrganizationPickList(String unitCode, String name) throws ServiceException {
      try {
         return organizationDao.getOrganizationPickList(unitCode, name);
      }
      catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }
   }
   
   public Collection<AgencyVo> getAgenciesForPicklist(AgencyFilter agencyFilter) throws ServiceException {
      //Is anyone planning to use this method?  -dbudge
      //The getAgencies method above does the same thing.
      //This method is also duplicated in the AgencyServiceImpl.
      try {
         return agencyDao.getAgencies(agencyFilter);
      } catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }
   }
   
//   /*
//    * (non-Javadoc)
//    * @see gov.nwcg.isuite.core.service.OrganizationService#getDispatchCenters()
//    */
//   public Collection<OrganizationVo> getDispatchCenters() throws ServiceException {
//      try {
//        return organizationDao.getDispatchCenters();
//      }
//      catch ( PersistenceException e ) {
//         throw new ServiceException(e);
//      }
//   }
}
