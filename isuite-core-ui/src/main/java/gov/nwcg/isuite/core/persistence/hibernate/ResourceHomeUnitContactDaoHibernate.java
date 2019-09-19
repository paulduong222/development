package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.ResourceHomeUnitContact;
import gov.nwcg.isuite.core.domain.impl.ResourceHomeUnitContactImpl;
import gov.nwcg.isuite.core.vo.AddressVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResourceHomeUnitContactVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

public class ResourceHomeUnitContactDaoHibernate extends TransactionSupportImpl
		implements ResourceHomeUnitContactDao {

	private final CrudDao<ResourceHomeUnitContact> crudDao;
	
	public ResourceHomeUnitContactDaoHibernate(final CrudDao<ResourceHomeUnitContact> crudDao) {
		 
		if ( crudDao == null ) {
	         throw new IllegalArgumentException("crudDao cannot be null");
	      }
	      this.crudDao = crudDao;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ResourceHomeUnitContactVo> getGrid() throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ResourceHomeUnitContactImpl.class);
		
		Collection<ResourceHomeUnitContact> entities = crit.list();
		
		try{
			return ResourceHomeUnitContactVo.getInstances(entities, true);
		}catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<ResourceHomeUnitContactVo> getGrid2(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ResourceHomeUnitContactImpl.class);

		if(LongUtility.hasValue(incidentId)){
			crit.add(Expression.sql("this_.incident_resource_id in ("+
										"select id from isw_incident_resource " +
										"where incident_id = " + incidentId + ")")); 
		}else{
			crit.add(Expression.sql("this_.incident_resource_id in ("+
									"select id from isw_incident_resource " +
									"where incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = "+incidentGroupId+") )")); 
		}
		
		Collection<ResourceHomeUnitContact> entities = crit.list();
		Collection<ResourceHomeUnitContactVo> vos = new ArrayList<ResourceHomeUnitContactVo>();
		
		try{
			if(CollectionUtility.hasValue(entities)){
				for(ResourceHomeUnitContact entity : entities){
					ResourceHomeUnitContactVo v = new ResourceHomeUnitContactVo();
					v.setId(entity.getId());
					v.setContactName(entity.getContactName());
					if(null != entity.getUnit()) {
						OrganizationVo orgVo = new OrganizationVo();
						orgVo.setId(entity.getUnit().getId());
						orgVo.setName(entity.getUnit().getName());
						v.setUnitVo(orgVo);
					}
					if(null != entity.getAddress()){
						v.setAddressVo(AddressVo.getInstance(entity.getAddress(), true));
					}
					v.setPhone(entity.getPhone());
					v.setEmail(entity.getEmail());
					vos.add(v);
				}
			}
			
			return vos;
			//return ResourceHomeUnitContactVo.getInstances(entities, true);
		}catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void delete(ResourceHomeUnitContact persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	@Override
	public ResourceHomeUnitContact getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, ResourceHomeUnitContactImpl.class);
	}

	@Override
	public void save(ResourceHomeUnitContact persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	@Override
	public void saveAll(Collection<ResourceHomeUnitContact> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	public ResourceHomeUnitContact getResourceHomeUnitContact(Long incidentResourceId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ResourceHomeUnitContactImpl.class);
		
		crit.add(Restrictions.eq("incidentResourceId", incidentResourceId));
		
		ResourceHomeUnitContact entity = (ResourceHomeUnitContact)crit.uniqueResult();
		
		return entity;
		
	}

}
