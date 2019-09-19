package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import gov.nwcg.isuite.core.domain.Font;
import gov.nwcg.isuite.core.domain.impl.FontImpl;
import gov.nwcg.isuite.core.persistence.FontDao;
import gov.nwcg.isuite.core.vo.FontVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class FontDaoHibernate extends TransactionSupportImpl implements FontDao {
	private final CrudDao<Font> crudDao;
	
	public FontDaoHibernate(final CrudDao<Font> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(Font persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public Font getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(Font persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<Font> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.FontDao#getPicklist()
	 */
	@SuppressWarnings("unchecked")
	public Collection<FontVo> getPicklist() throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(FontImpl.class);
		
		crit.addOrder(Order.asc("this.fontName"));
		
		Collection<Font> entities = crit.list();
		
		try {
			return FontVo.getInstances(entities, true);
		}catch(Exception e) {
			throw new PersistenceException(e);
		}
	}

}

