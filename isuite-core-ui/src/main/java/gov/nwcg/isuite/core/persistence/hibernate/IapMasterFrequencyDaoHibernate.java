package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IapMasterFrequency;
import gov.nwcg.isuite.core.domain.impl.IapMasterFrequencyImpl;
import gov.nwcg.isuite.core.filter.IapMasterFrequencyFilter;
import gov.nwcg.isuite.core.persistence.IapMasterFrequencyDao;
import gov.nwcg.isuite.core.vo.IapMasterFrequencyVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class IapMasterFrequencyDaoHibernate extends TransactionSupportImpl implements IapMasterFrequencyDao {
	private final CrudDao<IapMasterFrequency> crudDao;
	private static final StringBuffer SQL_PART_NULL = new StringBuffer(" NULL "); // SPACE_NULL_SPACE
	private static final StringBuffer SQL_PART_SINGLE_QUOTE = new StringBuffer("'");
	
	public IapMasterFrequencyDaoHibernate(final CrudDao<IapMasterFrequency> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IapMasterFrequency persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IapMasterFrequency getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IapMasterFrequency persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IapMasterFrequency> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IapMasterFrequencyDao#getGrid()
	 */
	@SuppressWarnings("unchecked")
	public Collection<IapMasterFrequencyVo> getGrid(IapMasterFrequencyFilter filter) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(IapMasterFrequencyImpl.class);
		
		if(LongUtility.hasValue(filter.getIncidentId())) {
			crit.add(Restrictions.eq("incidentId", filter.getIncidentId()));
		}
		else {
			String sql = "this_.incident_group_id = " + filter.getIncidentGroupId() + 
				" or this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = "+filter.getIncidentGroupId()+")";
			crit.add(Restrictions.sqlRestriction(sql));
			//crit.add(Restrictions.eq("incidentGroupId", filter.getIncidentGroupId()));
		}
		
		crit.addOrder(Order.asc("positionNum"));
		
		Collection<IapMasterFrequency> entities = crit.list();
				
		try{
			return IapMasterFrequencyVo.getInstances(entities, true);
		}catch(Exception e){
	    	 throw new PersistenceException(e);
	    }
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IapMasterFrequencyDao#propagateChanges()
	 */
	public void propagateChanges(IapMasterFrequencyVo vo) throws PersistenceException {
				
		StringBuffer sql1 = new StringBuffer();
		SQLQuery query1;
		
		StringBuffer sql2 = new StringBuffer();
		SQLQuery query2;
		
		sql1.append("update isw_iap_frequency freq ")
		.append(" set channel = " + sqlPartChar(vo.getChannel()))
		.append(" , function = " + sqlPartChar(vo.getRfunction()))
		.append(" , frequency_rx = " + sqlPartChar(vo.getRxFreq()))
		.append(" , tone_rx = " + sqlPartChar(vo.getRxTone()))
		.append(" , frequency_tx = " + sqlPartChar(vo.getTxFreq()))
		.append(" , tone_tx = " + sqlPartChar(vo.getTxTone()))
		.append(" , assignment = " + sqlPartChar(vo.getAssignment()))
		.append(" , remarks = " + sqlPartChar(vo.getRemarks()))
		.append(" , zone_group = " + sqlPartChar(vo.getGroup()))
		.append(" , channel_name = " + sqlPartChar(vo.getChannelName()))
		.append(" , mode_type = " + sqlPartChar(vo.getMode()))
		.append(" where exists ")
		.append(" (select * from isw_iap_plan plan, isw_iap_form_205 form  ")
		.append(" where freq.master_freq_id = " + vo.getId())
		.append(" and form.iap_plan_id = plan.id ")
		.append(" and freq.iap_form_205_id = form.id ")
		.append(" and form.is_form_locked = 'N' ");
		
		sql2.append("update isw_iap_branch_comm_summary comm ")
		.append(" set function = " + sqlPartChar(vo.getRfunction()))
		.append(" , rx = " + sqlPartChar(vo.getRxFreq()))
		.append(" , tx = " + sqlPartChar(vo.getTxFreq()))
		.append(" , channel_1 = " + sqlPartChar(vo.getChannel()))
		.append(" , rx_tone = " + sqlPartChar(vo.getRxTone()))
		.append(" , tx_tone = " + sqlPartChar(vo.getTxTone()))
		.append(" , smode = " + sqlPartChar(vo.getMode()))	
		.append(" where exists ")
		.append(" (select * from isw_iap_plan plan, isw_iap_branch branch ")
		.append(" where comm.master_freq_id = " + vo.getId())
		.append(" and branch.iap_plan_id = plan.id ")
		.append(" and comm.iap_branch_id = branch.id ")
		.append(" and branch.is_form_204_locked = 'N' ");
						  		
		if(vo.getIncidentVo() != null) {
			sql1.append(" and plan.incident_id = " + vo.getIncidentVo().getId() + ")");
			
//			System.out.println(sql1.toString());
			  
			query1 = getHibernateSession().createSQLQuery(sql1.toString());
			query1.executeUpdate();
		
			sql2.append(" and plan.incident_id = " + vo.getIncidentVo().getId() + ")");
					  
//			System.out.println(sql2.toString());
			
			query2 = getHibernateSession().createSQLQuery(sql2.toString());
			query2.executeUpdate(); 
		}
		else if(vo.getIncidentGroupVo() != null) {
			sql1.append(" and plan.incident_group_id = " + vo.getIncidentGroupVo().getId() + ")");
			
			//System.out.println(sql1.toString());
			  
			query1 = getHibernateSession().createSQLQuery(sql1.toString());
			query1.executeUpdate();
					
			sql2.append(" and plan.incident_group_id = " + vo.getIncidentGroupVo().getId() + ")");
			
			//System.out.println(sql2.toString());
					  
			query2 = getHibernateSession().createSQLQuery(sql2.toString());
			query2.executeUpdate();
		}
	}
	
	public void propagateAllFrequencyChanges(Collection<IapMasterFrequencyVo> vos) throws PersistenceException {
		for (IapMasterFrequencyVo vo : vos) {
			propagateChanges(vo);
		}
	}
	
	private static StringBuffer sqlPartChar(String attribute) {
		if(StringUtility.hasValue(attribute)) {
			return new StringBuffer(SQL_PART_SINGLE_QUOTE + attribute + SQL_PART_SINGLE_QUOTE);
		} 
		return SQL_PART_NULL;
	}
	
	/**
	 * Transfers/Moves the MFL from an incident to an incident group. This is required when an incident is added to an incident group.
	 * 
	 *  For newly added incidents to an incident group: 
	     Transfer the MFL from the incident just added to Incident Group’s MFL (No duplicate checking needed).
	     Implementation: From the MFL record, isw_iap_master_frequency, set incident_id to null; update the value of incident_group.
	 * @param incidentId
	 * @param incidentGroupId
	 * @throws PersistenceException
	 */
	public void transferMFLFromIncidentToIncidentGroup(Long incidentId, Long incidentGroupId) throws PersistenceException {
		if(!LongUtility.hasValue(incidentId) || !LongUtility.hasValue(incidentGroupId)) {
			throw new PersistenceException("Incident Id and Incident Group Id must have values.");
		}
		
		String sql = "UPDATE isw_iap_master_frequency " 
					+ " SET incident_id = null, incident_group_id = " + incidentGroupId
					+ " WHERE incident_id = " + incidentId;
		
		//TODO: MANU - Remove test print below
		//System.out.println("\n\n***MFL FROM INCIDENT TO GROUP:\n\n" + sql.toString() + "\n\n***\n\n");
			  
		SQLQuery query1 = getHibernateSession().createSQLQuery(sql.toString());
		query1.executeUpdate();
	}

	/**
	 * Copies the MFL from an incident group to an incident. This is required when an incident is removed from an incident group.
	 * When removing an incident from an incident group:
	     Copy the entire MFL from the Incident Group for the incident.
	     Implementation: Copy and Insert same records as for incident group with incident_group_id set to null and incident_id set to correct value. 
	 * @param incidentId
	 * @param incidentGroupId
	 * @throws Exception 
	 */
	public void copyMFLFromIncidentGroupToIncident(Long incidentId, Long incidentGroupId) throws Exception {
		if(!LongUtility.hasValue(incidentId) || !LongUtility.hasValue(incidentGroupId)) {
			throw new PersistenceException("Incident Id and Incident Group Id must have values.");
		}
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO isw_iap_master_frequency(" +
				" id, incident_id," +
				" show, system, group_name, channel, rfunction," +
				" rx, tx, tone, assignment," +
				" remarks, created_by, created_date," +
				" last_modified_by, last_modified_date, created_by_id, last_modified_by_id," +
				" incident_group_id," +
				" channel_name_radio_talkgroup, rx_freq_n_w," +
				" rx_tone_nac, tx_freq_n_w, tx_tone_nac, mode_a_d_m) ");
		
		sql.append(" SELECT " + (isOracleDialect() ? "SEQ_IAP_MASTER_FREQUENCY.nextVal " : "nextVal('SEQ_IAP_MASTER_FREQUENCY') "));
		sql.append(", " + incidentId)
			.append(", mf.show, mf.system, mf.group_name, mf.channel, mf.rfunction")
			.append(", mf.rx, mf.tx, mf.tone, mf.assignment")
			.append(", mf.remarks, mf.created_by, mf.created_date")
			.append(", mf.last_modified_by, mf.last_modified_date, mf.created_by_id, mf.last_modified_by_id")
			.append(", NULL")
			.append(", mf.channel_name_radio_talkgroup, mf.rx_freq_n_w")
			.append(", mf.rx_tone_nac, mf.tx_freq_n_w, mf.tx_tone_nac, mf.mode_a_d_m")
			.append(" FROM isw_iap_master_frequency mf")
			.append(" WHERE mf.incident_group_id = " + incidentGroupId);
			
		//TODO: MANU - Remove test print below
		//System.out.println("\n\n\n\n***MFL FROM GROUP TO INCIDENT\n\n" + sql.toString() + "\n\n\n\n***\n\n");
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		query.executeUpdate();
		
		//TODO: MANU - Remove following commented code; It uses VOs instead of direct SQL (implemented above)
//		
//		// Retrieve the MFL for the incident group
//		IapMasterFrequencyFilter filter = new IapMasterFrequencyFilterImpl();
//		filter.setIncidentGroupId(incidentGroupId);
//		Collection<IapMasterFrequencyVo> incidentGroupMFList = this.getGrid(filter);
//		
//		// Update every master frequency Vo by removing its incident group and adding the incident
//		for (IapMasterFrequencyVo vo:incidentGroupMFList) {
//			// Set the new incident value
//			IncidentVo incidentVo = new IncidentVo();
//			incidentVo.setId(incidentId);
//			vo.setIncidentVo(incidentVo);
//			
//			// Remove the old incident group value
//			vo.setIncidentGroupVo(null);
//		}
//		
//		// Convert the udpate Vos to entity list
//		Collection<IapMasterFrequency> updatedMasterFrequencyEntityList = 
//			IapMasterFrequencyVo.toEntityList(incidentGroupMFList, true);
//		
//		// Save the new entity list
//		this.saveAll(updatedMasterFrequencyEntityList);
	}
}

