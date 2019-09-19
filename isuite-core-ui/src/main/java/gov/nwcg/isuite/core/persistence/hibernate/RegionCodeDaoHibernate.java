package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.RegionCode;
import gov.nwcg.isuite.core.domain.impl.RegionCodeImpl;
import gov.nwcg.isuite.core.persistence.RegionCodeDao;
import gov.nwcg.isuite.core.vo.RegionCodeVo;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

public class RegionCodeDaoHibernate extends TransactionSupportImpl implements RegionCodeDao {

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   public void delete(RegionCode persistable) throws PersistenceException {
      // TODO Auto-generated method stub

   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   public RegionCode getById(Long id, Class clazz) throws PersistenceException {
      // TODO Auto-generated method stub
      return null;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   public void save(RegionCode persistable) throws PersistenceException {
      // TODO Auto-generated method stub

   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<RegionCode> persistables) throws PersistenceException {
      // TODO Auto-generated method stub

   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.TransferableDao#getByUniqueIdentity(java.lang.String, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   public RegionCode getByUniqueIdentity(String uniqueIdentity, Class clazz) throws PersistenceException,
            DuplicateItemException {
      // TODO Auto-generated method stub
      return null;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.RegionCodeDao#getRegionCodes()
    */
   @SuppressWarnings("unchecked")
   public Collection<RegionCodeVo> getRegionCodes() throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(RegionCodeImpl.class);
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("id"), "id")
               .add(Projections.property("code"), "code")
               .add(Projections.property("description"), "description")
               );
      crit.setResultTransformer(Transformers.aliasToBean(RegionCodeVo.class));
      crit.addOrder(Order.asc("code"));
      return crit.list();
   }

}
