package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.DataAuditTracking;
import gov.nwcg.isuite.core.domain.impl.DataAuditTrackingImpl;
import gov.nwcg.isuite.core.filter.DataAuditTrackingFilter;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.persistence.DataAuditTrackingDao;
import gov.nwcg.isuite.core.vo.DataAuditTrackingGridVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Dao for data audit tracking.
 * @author jbrose
 * 
 */
public class DataAuditTrackingDaoHibernate extends TransactionSupportImpl implements DataAuditTrackingDao {
	private final CrudDao<DataAuditTracking> crudDao;

	protected CrudDao<DataAuditTracking> getCrudDao() {
		return crudDao;
	}

	public DataAuditTrackingDaoHibernate(final CrudDao<DataAuditTracking> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/**
	 * Retrieve a {@link Collection} of {@link UserGridVo} objects based on the
	 * provided filter.
	 * @param userFilter {@link UserFilter}
	 * @return A {@link Collection} of {@link UserGridVo} objects
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	public Collection<DataAuditTrackingGridVo> getGrid(DataAuditTrackingFilter dataAuditTrackingFilter) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(DataAuditTrackingImpl.class);

		if (null != dataAuditTrackingFilter){
			if(!DateUtil.hasValue(dataAuditTrackingFilter.getStartDate()) && !DateUtil.hasValue(dataAuditTrackingFilter.getEndDate())){
				try {
					crit.add(Restrictions.ge("changeDate", DateUtil.subtractDaysFromDate(DateUtil.addMilitaryTimeToDate(new Date(), "0001"),30)));
				} catch (Exception e) {
				}
			} else {
				//filter by begin date
				if(DateUtil.hasValue(dataAuditTrackingFilter.getStartDate())){
					crit.add(Restrictions.ge("changeDate", dataAuditTrackingFilter.getStartDate()));
				}

				//filter by end date
				if(DateUtil.hasValue(dataAuditTrackingFilter.getEndDate())){
					crit.add(Restrictions.le("changeDate", dataAuditTrackingFilter.getEndDate()));
				}
			}
			if(null!=dataAuditTrackingFilter.getAuditEventType() && dataAuditTrackingFilter.getAuditEventType().length > 0){
				Criterion auditConfig = Restrictions.in("dataAuditConfig.auditEventType", dataAuditTrackingFilter.getAuditEventType());
				crit.createAlias("dataAuditConfig", "dataAuditConfig", CriteriaSpecification.INNER_JOIN);
				crit.add(auditConfig);
				//crit.add(Restrictions.in("dataAuditConfig.auditEventType", dataAuditTrackingFilter.getAuditEventType()));
			}
		}

		//crit.addOrder(Order.desc("createdDate"));
		crit.setMaxResults(super.getMaxResultSize());
		crit.addOrder(Order.desc("id"));
		Collection<DataAuditTracking> entities = crit.list();

		Collection<DataAuditTrackingGridVo> vos = new ArrayList<DataAuditTrackingGridVo>();
		for(DataAuditTracking entity : entities){
			
			DataAuditTrackingGridVo vo = new DataAuditTrackingGridVo();
			vo.setId(entity.getId());
			if(StringUtility.hasValue(entity.getNewValue())){
				vo.setAuditEvent(entity.getDataAuditConfig().getAuditEventType() + " - " + entity.getNewValue());
			}else{
				vo.setAuditEvent(entity.getDataAuditConfig().getAuditEventType());
			}
			vo.setCreatedDate(entity.getCreatedDate());
			vo.setEventDate(entity.getCreatedDate());
			if(StringUtility.hasValue(entity.getNewValue()) && entity.getNewValue().equals("BACKUP COMPLETED")){
				vo.setBackupFilename(entity.getAuditField2());
				vo.setBackupFilepath(entity.getAuditField1());
				vo.setBackupType(entity.getAuditField3());
				vo.setLoginName(entity.getAuditField4());
			}else{
				vo.setLoginName(entity.getAuditField1());
				vo.setFirstName(entity.getAuditField3());
				vo.setLastName(entity.getAuditField2());
				vo.setHomeUnit(entity.getAuditField4());
				vo.setPrimaryDispatchCenter(entity.getAuditField5());
				vo.setModifiedBy(entity.getLastModifiedBy());
			}
			
			vos.add(vo);
		}

		return vos;

	}

	@Override
	public void delete(DataAuditTracking dataAuditTracking)
	throws PersistenceException {
		if(dataAuditTracking == null) {
			throw new PersistenceException("DataAuditTracking cannot be null.");
		}
		crudDao.delete(dataAuditTracking);
	}

	@Override
	public DataAuditTracking getById(Long id, Class<?> clazz)
	throws PersistenceException {
		return crudDao.getById(id, DataAuditTrackingImpl.class);
	}

	@Override
	public void save(DataAuditTracking dataAuditTracking) throws PersistenceException {
		getCrudDao().setSkipSetAuditInfo(super.getSkipSetAuditInfo());
		getCrudDao().save(dataAuditTracking);	
	}

	@Override
	public void saveAll(Collection<DataAuditTracking> dataAuditTracking)
	throws PersistenceException {
		getCrudDao().saveAll(dataAuditTracking);
	}
}
