package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.core.domain.ScratchReportTime;
import gov.nwcg.isuite.core.domain.impl.ReportImpl;
import gov.nwcg.isuite.core.domain.impl.ScratchReportTimeImpl;
import gov.nwcg.isuite.core.filter.impl.TimeReport2FilterImpl;
import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.SummaryHoursWorkedReportQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.TimePostQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.TimePreviousInvoiceQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.TimeReportQuery;
import gov.nwcg.isuite.core.reports.data.CrewRosterReportData;
import gov.nwcg.isuite.core.reports.data.MissingDaysOfPostingsSubReportData;
import gov.nwcg.isuite.core.reports.data.PersonnelTimeReportAgencyFaxResourceData;
import gov.nwcg.isuite.core.reports.data.PersonnelTimeReportData;
import gov.nwcg.isuite.core.reports.data.PersonnelTimeSubReportData;
import gov.nwcg.isuite.core.reports.data.ResourceDateData;
import gov.nwcg.isuite.core.reports.data.ShiftsInExcessOfStandardHoursReportData;
import gov.nwcg.isuite.core.reports.data.VendorResourceSummaryReportData;
import gov.nwcg.isuite.core.reports.filter.MissingDaysOfPostingsReportFilter;
import gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter;
import gov.nwcg.isuite.core.reports.filter.ShiftsInExcessOfStandardHoursReportFilter;
import gov.nwcg.isuite.core.reports.filter.SummaryHoursWorkedReportFilter;
import gov.nwcg.isuite.core.reports.filter.VendorResourceSummaryReportFilter;
import gov.nwcg.isuite.core.reports.filter.WorkRestRatioReportFilter;
import gov.nwcg.isuite.core.vo.PreviousInvoiceDataVo;
import gov.nwcg.isuite.core.vo.ReportAgencySelect;
import gov.nwcg.isuite.core.vo.ReportSelectVo;
import gov.nwcg.isuite.core.vo.TimeReportData2Vo;
import gov.nwcg.isuite.framework.core.persistence.hibernate.OrderBySql;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.types.TimeReportsSortByEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Level;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class ReportTimeDaoHibernate extends TransactionSupportImpl implements ReportTimeDao {
 
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  
  private DataSource dataSource; 
  
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getCrewNamesForSelectedIncident(java.lang.Long)
	 */
	public Collection<ReportSelectVo> getCrewNamesForSelectedIncident_old(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Collection<ReportSelectVo> vos = new ArrayList<ReportSelectVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct")
		    .append(" r.resource_name as label, ")
		    .append("ir.resource_id as resourceId, ")
			.append("ir.id as incidentResourceId ")
		  .append("from isw_resource r, ")
		    .append("isw_incident_resource ir, ")
		    .append("isw_resource_kind rk, ")
		    .append("iswl_kind k, ")
		    .append("iswl_request_category rc ")
		  .append("where r.id = ir.resource_id ")
		    .append("and r.id = rk.resource_id ")
		    .append("and rk.kind_id = k.id ")
		    .append("and k.request_category_id = rc.id ")
		    .append("and r.id in (")
		    .append("select distinct r.parent_resource_id from isw_resource r )");
		if (incidentId > 0) {
			sb.append(" and ir.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" and ir.incident_id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentGroupId + ")");
		}
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());

		CustomResultTransformer crt = new CustomResultTransformer(ReportSelectVo.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		sqlQuery.setResultTransformer(crt);
    
		try {
			vos = sqlQuery.list();
			if(vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}
  
	public Collection<ReportSelectVo> getCrewNamesForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Collection<ReportSelectVo> vos = new ArrayList<ReportSelectVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("select ")
		    .append(" case " )
		    .append("  when r.is_person = " + (super.isOracleDialect() ? 0 : false) + " then r.resource_name ")
		    .append("  when r.is_person = " + (super.isOracleDialect() ? 1 : true) + " then r.last_name || ', ' || r.first_name ")
		    .append(" end ")
		    .append(" as label, ")
		    .append("ir.resource_id as resourceId, ")
			.append("ir.id as incidentResourceId ")
		  .append("from isw_resource r, ")
		    .append("isw_incident_resource ir ")
		    .append("where r.id = ir.resource_id ")
		    .append("and r.id in (")
		    .append("select distinct r.parent_resource_id from isw_resource r )");
		if (incidentId > 0) {
			sb.append(" and ir.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" and ir.incident_id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentGroupId + ")");
		}
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());

		CustomResultTransformer crt = new CustomResultTransformer(ReportSelectVo.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		sqlQuery.setResultTransformer(crt);
    
		try {
			vos = sqlQuery.list();
			if(vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ReportSelectVo> getCrewRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId)
			throws PersistenceException {
		Collection<ReportSelectVo> vos = new ArrayList<ReportSelectVo>();
		StringBuffer sb = new StringBuffer();
		
		sb.append("select a.request_number as label, ")
		    .append("ir.resource_id as resourceId, ")
			.append("ir.id as incidentResourceId, ")
			.append("r.is_person as isPerson, " )
			.append("r.is_contracted as isContractor ")
		  .append("from isw_assignment a, ")
		    .append("isw_work_period_assignment wpa, ")
		    .append("isw_work_period wp, ")
		    .append("isw_incident_resource ir, ")
		    .append("isw_resource r ")
		    .append("where ");
		
		if (incidentId > 0) {
			sb.append(" ir.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" ir.incident_id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentGroupId + ")");
		}
		    
		sb.append(" and r.id = ir.resource_id ")
			.append(" and r.id in (")
		    	.append("select distinct r2.parent_resource_id from isw_resource r2 ) ")
		    .append(" and wp.incident_resource_id = ir.id ")
		 	.append(" and wpa.work_period_id = wp.id ")
		 	.append(" and a.id = wpa.assignment_id ")
		    .append(" and a.request_number != ' ' ")
		    .append(" order by sortrequestnumber(a.request_number)");
		
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());

		CustomResultTransformer crt = new CustomResultTransformer(ReportSelectVo.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		crt.addScalar("isPerson",Boolean.class.getName());
		crt.addScalar("isContractor",Boolean.class.getName());

		sqlQuery.setResultTransformer(crt);

		try {
			vos = sqlQuery.list();
			if (vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}
	
	

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getPersonResourceNamesForSelectedIncident(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ReportSelectVo> getPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId)
			throws PersistenceException {
		Collection<ReportSelectVo> vos = new ArrayList<ReportSelectVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("select r.last_name||', '||r.first_name as label, ")
		    .append("ir.resource_id as resourceId, ")
			.append("ir.id as incidentResourceId, ")
			.append("r.is_person as isPerson, " )
			.append("r.is_contracted as isContractor ")
		  .append("from isw_resource r, ")
		    .append("isw_incident_resource ir ")
		    .append("where r.id = ir.resource_id ")
		    //.append("and r.first_name != ' ' ")
		    //.append("and r.last_name != ' ' ")
		    ;
		if(super.isOracleDialect()) {
			sb.append(" and r.is_person = 1 ");
		} else {
			sb.append(" and r.is_person = true ");
		}
		if(incidentId > 0) {
			sb.append(" and ir.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" and ir.incident_id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentGroupId + ")");
		}
		
		sb.append(" order by r.last_name, r.first_name, ir.resource_id");
		
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());

		CustomResultTransformer crt = new CustomResultTransformer(ReportSelectVo.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		crt.addScalar("isPerson",Boolean.class.getName());
		crt.addScalar("isContractor",Boolean.class.getName());
		sqlQuery.setResultTransformer(crt);
    
		try {
			vos = sqlQuery.list();
			if (vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}
  
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getContractedPersonResourceNamesForSelectedIncident(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ReportSelectVo> getContractedPersonResourceNamesForSelectedIncident(Long incidentId,
			Long incidentGroupId) throws PersistenceException {
		Collection<ReportSelectVo> vos = new ArrayList<ReportSelectVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("select r.last_name||', '||r.first_name as label, ")
		    .append("ir.resource_id as resourceId, ")
			.append("ir.id as incidentResourceId, ")
			.append("wp.dm_release_date as releaseDate ")
		  .append("from isw_resource r, ")
		  	.append("isw_incident_resource ir, ")
		  	.append("isw_work_period wp ")
		  .append("where r.id = ir.resource_id ")
		  	.append("and ir.id = wp.incident_resource_id ")
		  	//.append("and r.first_name != ' ' ")
		  	//.append("and r.last_name != ' ' ")
		  	;
		if(super.isOracleDialect()) {
			sb.append(" and r.is_contracted = 1 ");
			sb.append(" and r.is_person = 1 ");
		} else {
			sb.append(" and r.is_contracted = true ");
			sb.append(" and r.is_person = true ");
		}
		if(incidentId > 0) {
			sb.append(" and ir.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" and ir.incident_id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentGroupId + ")");
		}
		sb.append(" order by r.last_name, r.first_name, ir.resource_id");
		
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());

		CustomResultTransformer crt = new CustomResultTransformer(ReportSelectVo.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		sqlQuery.setResultTransformer(crt);
    
		try {
			vos = sqlQuery.list();
			if (vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}
  
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getNonContractedPersonResourceNamesForSelectedIncident(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ReportSelectVo> getNonContractedPersonResourceNamesForSelectedIncident(Long incidentId,
			Long incidentGroupId) throws PersistenceException {
		Collection<ReportSelectVo> vos = new ArrayList<ReportSelectVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("select r.last_name||', '||r.first_name as label, ")
		    .append("ir.resource_id as resourceId, ")
			.append("ir.id as incidentResourceId ")
		  .append("from isw_resource r, ")
		    .append("isw_incident_resource ir ")
		  .append("where r.id = ir.resource_id ")
		    //.append("and r.first_name != ' ' ")
		    //.append("and r.last_name != ' ' ")
		    ;
		if(super.isOracleDialect()) {
			sb.append(" and r.is_contracted = 0 ");
			sb.append(" and r.is_person = 1 ");
		} else {
			sb.append(" and r.is_contracted = false ");
			sb.append(" and r.is_person = true ");
		}
		if(incidentId > 0) {
			sb.append(" and ir.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" and ir.incident_id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentGroupId + ")");
		}
		sb.append(" order by r.last_name, r.first_name, ir.resource_id");
		
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());

		CustomResultTransformer crt = new CustomResultTransformer(ReportSelectVo.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		sqlQuery.setResultTransformer(crt);
    
		try {
			vos = sqlQuery.list();
			if(vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}
  
  /*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getRequestNumbersForSelectedIncident(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ReportSelectVo> getRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId)
			throws PersistenceException {
		Collection<ReportSelectVo> vos = new ArrayList<ReportSelectVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("select a.request_number as label, ")
		  	.append("ir.resource_id as resourceId, ")
			.append("ir.id as incidentResourceId ")
		  .append("from isw_assignment a, ")
		  	.append("isw_work_period_assignment wpa, ")
		  	.append("isw_work_period wp, ")
		  	.append("isw_incident_resource ir ")
		  .append("where a.id = wpa.assignment_id ")
		    .append("and wpa.work_period_id = wp.id ")
		  	.append("and wp.incident_resource_id = ir.id ")
		  	.append("and a.request_number != ' ' ");
		if (incidentId > 0) {
			sb.append(" and ir.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" and ir.incident_id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentGroupId + ")");
		}
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());
		// sqlQuery.setParameter("incidentid", incidentId);
        
		CustomResultTransformer crt = new CustomResultTransformer(ReportSelectVo.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		sqlQuery.setResultTransformer(crt);
    
		try {
			vos = sqlQuery.list();
			if (vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getNonContractedRequestNumbersForSelectedIncident(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ReportSelectVo> getNonContractedRequestNumbersForSelectedIncident(Long incidentId,
			Long incidentGroupId) throws PersistenceException {
		Collection<ReportSelectVo> vos = new ArrayList<ReportSelectVo>();
		StringBuffer sb = new StringBuffer();
		
		sb.append("select a.request_number as label, ")
		    .append("ir.resource_id as resourceId, ")
			.append("ir.id as incidentResourceId, ")
			.append("r.is_person as isPerson, " )
			.append("r.is_contracted as isContractor ")
		  .append("from isw_assignment a, ")
		    .append("isw_work_period_assignment wpa, ")
		     .append("isw_work_period wp, ")
		   .append("isw_incident_resource ir, ")
		    .append("isw_resource r ")
		  .append("where a.id = wpa.assignment_id ")
		    .append("and wpa.work_period_id = wp.id ")
		    .append("and wp.incident_resource_id = ir.id ")
		    .append("and r.id = ir.resource_id ")
		    .append("and a.request_number != ' ' ");
		if (super.isOracleDialect()) {
			sb.append(" and r.is_contracted = 0 ");
		} else {
			sb.append(" and r.is_contracted = false ");
		}
		if (incidentId > 0) {
			sb.append(" and ir.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" and ir.incident_id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentGroupId + ")");
		}
		//sb.append(" order by a.request_number, ir.resource_id");
		sb.append(" order by sortrequestnumber(a.request_number), ir.resource_id");  //Bill Tsai 01/10/2014 fix: use stored function: sortrequestnumber
		
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());
		// sqlQuery.setParameter("incidentid", incidentId);

		CustomResultTransformer crt = new CustomResultTransformer(ReportSelectVo.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		crt.addScalar("isPerson",Boolean.class.getName());
		crt.addScalar("isContractor",Boolean.class.getName());
		sqlQuery.setResultTransformer(crt);

		try {
			vos = sqlQuery.list();
			if (vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getContractedRequestNumbersForSelectedIncident(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ReportSelectVo> getContractedRequestNumbersForSelectedIncident(Long incidentId,
			Long incidentGroupId) throws PersistenceException {
		Collection<ReportSelectVo> vos = new ArrayList<ReportSelectVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("select a.request_number as label, ")
		    .append("ir.resource_id as resourceId, ")
		    .append("ir.id as incidentResourceId, ")
			.append("wp.dm_release_date as releaseDate ")
		  .append("from isw_assignment a, ")
		    .append("isw_work_period_assignment wpa, ")
		    .append("isw_work_period wp, ")
		    .append("isw_incident_resource ir, ")
		    .append("isw_resource r ")
		  .append("where a.id = wpa.assignment_id ")
		    .append("and wpa.work_period_id = wp.id ")
		    .append("and wp.incident_resource_id = ir.id ")
		    .append("and r.id = ir.resource_id ")
		    .append("and a.request_number != ' ' ");
		if (super.isOracleDialect()) {
			sb.append(" and r.is_contracted = 1 ");
		} else {
			sb.append(" and r.is_contracted = true ");
		}
		if (incidentId > 0) {
			sb.append(" and ir.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" and ir.incident_id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentGroupId + ")");
		}
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());
		// sqlQuery.setParameter("incidentid", incidentId);

		CustomResultTransformer crt = new CustomResultTransformer(ReportSelectVo.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		crt.addScalar("releaseDate", Date.class.getName());
		sqlQuery.setResultTransformer(crt);

		try {
			vos = sqlQuery.list();
			if (vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}

	public Collection<ReportSelectVo> getContractedResourcesForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Collection<ReportSelectVo> vos = new ArrayList<ReportSelectVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("select ir.resource_id as resourceId, ")
		    .append("ir.id as incidentResourceId, ")
			.append("wp.dm_release_date as releaseDate, ")
			.append("( ")
			.append("   case ");
			if(super.isOracleDialect()){
				sb.append("   when r.is_person = 1 then r.first_name||' '||r.last_name ");
				sb.append("   when r.is_person = 0 then r.resource_name ");
			}else{
				sb.append("   when r.is_person = true then r.first_name||' '||r.last_name ");
				sb.append("   when r.is_person = false then r.resource_name ");
			}
			sb.append(" end ) as label ")
		  .append("from isw_assignment a, ")
		    .append("isw_work_period_assignment wpa, ")
		    .append("isw_work_period wp, ")
		    .append("isw_incident_resource ir, ")
		    .append("isw_resource r ")
		  .append("where a.id = wpa.assignment_id ")
		    .append("and wpa.work_period_id = wp.id ")
		    .append("and wp.incident_resource_id = ir.id ")
		    .append("and r.id = ir.resource_id ")
		    .append("and a.request_number != ' ' ");
		if (super.isOracleDialect()) {
			sb.append(" and r.is_contracted = 1 ");
		} else {
			sb.append(" and r.is_contracted = true ");
		}
		if (incidentId > 0) {
			sb.append(" and ir.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" and ir.incident_id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentGroupId + ")");
		}
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());
		// sqlQuery.setParameter("incidentid", incidentId);

		CustomResultTransformer crt = new CustomResultTransformer(ReportSelectVo.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		crt.addScalar("releaseDate", Date.class.getName());
		sqlQuery.setResultTransformer(crt);

		try {
			vos = sqlQuery.list();
			if (vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}

	@SuppressWarnings("unchecked")
	public Collection<ReportSelectVo> getPersonRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId)
			throws PersistenceException {
		Collection<ReportSelectVo> vos = new ArrayList<ReportSelectVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("select a.request_number as label, ")
		    .append("ir.resource_id as resourceId, ")
			.append("ir.id as incidentResourceId, ")
			.append("r.is_person as isPerson, " )
			.append("r.is_contracted as isContractor ")
		  .append("from isw_assignment a, ")
		    .append("isw_work_period_assignment wpa, ")
		    .append("isw_work_period wp, ")
		    .append("isw_incident_resource ir, ")
		    .append("isw_resource r ")
		  .append("where a.id = wpa.assignment_id ")
		    .append("and wpa.work_period_id = wp.id ")
		    .append("and wp.incident_resource_id = ir.id ")
		    .append("and r.id = ir.resource_id ")
		    .append("and a.request_number != ' ' ");
		if (super.isOracleDialect()) {
			sb.append(" and r.is_person = 1 ");
		} else {
			sb.append(" and r.is_person = true ");
		}
		if (incidentId > 0) {
			sb.append(" and ir.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" and ir.incident_id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentGroupId + ")");
		}
		
		sb.append(" order by sortrequestnumber(a.request_number)");
		
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());
		// sqlQuery.setParameter("incidentid", incidentId);

		CustomResultTransformer crt = new CustomResultTransformer(ReportSelectVo.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		crt.addScalar("isPerson",Boolean.class.getName());
		crt.addScalar("isContractor",Boolean.class.getName());

		sqlQuery.setResultTransformer(crt);

		try {
			vos = sqlQuery.list();
			if (vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getAgencyResourcesForSelectedIncident(java.lang.Long, java.lang.Long)
	 */
	public Collection<ReportAgencySelect> getAgencyResourcesForSelectedIncident(Long incidentId, Long incidentGroupId)
			throws PersistenceException {
		Collection<ReportAgencySelect> vos = new ArrayList<ReportAgencySelect>();

		StringBuffer sb = new StringBuffer();
		sb.append("select distinct")
		    .append(" ir.resource_id as resourceId")
		    .append(", r.first_name||' '||r.last_name as resourceName")
		    .append(", at.fax as faxNumber")
		    .append(", ag.agency_code as agencyCode")
		  .append(" from")
		    .append(" isw_resource r")
		      .append(" join iswl_agency ag on r.agency_id = ag.id")
		    .append(", isw_incident_resource ir")
		    .append(", isw_assignment a")
		    .append(", isw_assignment_time at")
		      .append(" join isw_assign_time_post atp on at.id = atp.assignment_time_id")
		    .append(", isw_work_period_assignment wpa")
		    .append(", isw_work_period wp")
		  .append(" where");
		if (incidentId > 0) {
			sb.append(" ir.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" ir.incident_id in (")
			.append(" select igi.incident_id from isw_incident_group_incident igi")
			.append(" where igi.incident_group_id = " + incidentGroupId + ")");
		}
		sb.append(" and r.id = ir.resource_id")
		  .append(" and ir.id = wp.incident_resource_id")
		  .append(" and wp.id = wpa.work_period_id")
		  .append(" and wpa.assignment_id = a.id")
		  .append(" and a.id = at.assignment_id")
		  .append(" and at.hiring_fax is not null")
		  .append(" and a.assignment_status != 'D'")
		  .append(" and at.employment_type in ('FED', 'OTHER')");

		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());

		CustomResultTransformer crt = new CustomResultTransformer(ReportAgencySelect.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		sqlQuery.setResultTransformer(crt);

		try {
			vos = sqlQuery.list();
			if (vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}

	public Collection<ReportSelectVo> getAvailableForReleaseReprintListForIncident(Long incidentId, Long incidentGroupId)
			throws PersistenceException {
		Collection<ReportSelectVo> vos = new ArrayList<ReportSelectVo>();

		StringBuffer sb = new StringBuffer();
		sb.append("select id, date_generated as dateGenerated")
		  .append(" from")
		  .append(" isw_report r")
		  .append(" where")
		  .append(" r.original_report_id is null")
		  .append(" and report_name = 'AVAILABLEFORRELEASEREPORT'");
		if (incidentId > 0) {
			sb.append(" and r.incident_id = " + incidentId);
		} else if (incidentGroupId > 0) {
			sb.append(" and (r.incident_group_id = " + incidentGroupId)
			  .append(" or r.incident_id in (")
			  .append(" select igi.incident_id from isw_incident_group_incident igi")
			  .append(" where igi.incident_group_id = " + incidentGroupId + ") )");
		}

		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sb.toString());

		CustomResultTransformer crt = new CustomResultTransformer(ReportImpl.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("dateGenerated", Date.class.getName());
		sqlQuery.setResultTransformer(crt);

		try {
			List<Report> result = sqlQuery.list();
			DateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
			if (result.size() == 0) {
				return vos;
			} else {
				for (int i = 0; i < result.size(); i++) {
					ReportSelectVo vo = new ReportSelectVo();
					vo.setResourceId(result.get(i).getId());
					vo.setLabel(formatter.format(result.get(i).getDateGenerated()));

					vos.add(vo);
				}
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getShiftsInExcessOfStandardHoursReportData
	 * (gov.nwcg.isuite.core.reports.filter.ShiftsInExcessOfStandardHoursReportFilter)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Collection<ShiftsInExcessOfStandardHoursReportData> getShiftsInExcessOfStandardHoursReportData2(
			ShiftsInExcessOfStandardHoursReportFilter filter)
			throws PersistenceException {
			
		//get incident(s) name/number for specific incident or all incidents in the group
		Collection<ShiftsInExcessOfStandardHoursReportData> allIncidentLevelData = new ArrayList<ShiftsInExcessOfStandardHoursReportData>();
		
		StringBuffer sbsql = new StringBuffer();
		sbsql.append(" SELECT DISTINCT i.id AS incidentId, ")
			.append(" i.incident_name AS incidentName, ")
			.append(" country.cc_abbreviation || '-' || org.unit_code || '-' || i.nbr AS incidentTag ")
			.append(" FROM isw_incident i ")
			.append(" LEFT OUTER JOIN iswl_country_subdivision country_sub ON country_sub.id = i.country_subdivision_id ")
			.append(" LEFT OUTER JOIN iswl_country country ON country.id = country_sub.country_id ")
			.append(" LEFT OUTER JOIN isw_organization org ON org.id = i.unit_id ")
			.append(" WHERE ");
		if(filter.getIncidentId() != null && filter.getIncidentId() > 0) {
			sbsql.append(" i.id = " + filter.getIncidentId());
		} else if (filter.getIncidentGroupId() != null && filter.getIncidentGroupId() > 0) {
			sbsql.append(" i.id IN (")
			  .append(" SELECT igi.incident_id FROM isw_incident_group_incident igi")
			  .append(" WHERE incident_group_id = " + filter.getIncidentGroupId() + ") ");
		}
		sbsql.append(" ORDER BY incidentId");
		
		SQLQuery allIncidentsSQLQuery = getHibernateSession().createSQLQuery(sbsql.toString());
		CustomResultTransformer crt = new CustomResultTransformer(ShiftsInExcessOfStandardHoursReportData.class);
		crt.addScalar("incidentId", Long.class.getName());
		allIncidentsSQLQuery.setResultTransformer(crt);
		
		try {
			allIncidentLevelData = allIncidentsSQLQuery.list();
			if(allIncidentLevelData == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		
		for(ShiftsInExcessOfStandardHoursReportData topLevelReportData : allIncidentLevelData) {
			topLevelReportData.setFirstDateToInclude(filter.getFirstDateToIncludeOnReport());
			topLevelReportData.setLastDateToInclude(filter.getLastDateToIncludeOnReport());
			topLevelReportData.setStandardHours(filter.getStandardHours());
			topLevelReportData.setReportPrintedDate(new Date());
		}
		
		return allIncidentLevelData;
	}
	
	public Long insertShiftsInExcessReportDataToScratchTable(ShiftsInExcessOfStandardHoursReportFilter filter) throws PersistenceException {
		Long transactionId = null;
		this.dataSource = (DataSource)context.getBean("dataSource");
		
		String excludeDemob = filter.isExcludeDemob()?"Y":"N";
		
		try {
			if(super.isOracleDialect()) {
				SqlParameterSource in = new MapSqlParameterSource()
					.addValue("p_incident_id", filter.getIncidentId(), Types.NUMERIC)
	            	.addValue("p_incident_group_id", filter.getIncidentGroupId(), Types.NUMERIC)
	            	.addValue("p_range_start_date", filter.getFirstDateToIncludeOnReport(), Types.TIMESTAMP)
	    			.addValue("p_range_end_date", filter.getLastDateToIncludeOnReport(), Types.TIMESTAMP)
					.addValue("p_resource_id", filter.getResourceId(), Types.NUMERIC)
					.addValue("p_exclude_demob", excludeDemob, Types.VARCHAR);
				
				
				SimpleJdbcCall genRepSummaryProcedure = new SimpleJdbcCall(dataSource);
				genRepSummaryProcedure.withCatalogName("report_time_pkg")
					.withProcedureName("rpt_time_genrep_shifts_excess");
				
				Map<String, Object> out = genRepSummaryProcedure.execute(in);
				transactionId = ((java.math.BigDecimal)(out.get("P_TRANSACTION_ID"))).longValue();
			} else { 
				SqlParameterSource in = new MapSqlParameterSource()
					.addValue("p_incident_id", filter.getIncidentId(), Types.BIGINT)
	            	.addValue("p_incident_group_id", filter.getIncidentGroupId(), Types.BIGINT)
	    			.addValue("p_range_start_date", filter.getFirstDateToIncludeOnReport(), Types.TIMESTAMP)
	    			.addValue("p_range_end_date", filter.getLastDateToIncludeOnReport(), Types.TIMESTAMP)
					.addValue("p_resource_id", filter.getResourceId(), Types.BIGINT)
					.addValue("p_exclude_demob", excludeDemob, Types.VARCHAR);
			
				SimpleJdbcCall genRepSummaryProcedure = new SimpleJdbcCall(dataSource);
				genRepSummaryProcedure.withFunctionName("rpt_time_genrep_shifts_excess");
						 
				Map<String, Object> out = genRepSummaryProcedure.execute(in);
				transactionId = (Long)(out.get("returnvalue"));
			}
			
			return transactionId;
			
		} catch (Exception e) {  
			throw new PersistenceException(e);	        
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ScratchReportTime> getShiftsInExcessOfStandardHoursReportDetails2(
			Long incidentId,
			Long transactionId,
			ShiftsInExcessOfStandardHoursReportFilter filter) 
		throws PersistenceException {
		
		String sortRequestNumberAscendingSQL="sortrequestnumber(this_.request_number)";
		BigDecimal standardHours = new BigDecimal(filter.getStandardHours());
		
		if(!LongUtility.hasValue(transactionId)) {
			throw new IllegalArgumentException("Transaction Id cannot be null.");
		}
		
		// Criteria specifically for WorkRestRatio Report
		Criteria crit = getHibernateSession().createCriteria(ScratchReportTimeImpl.class)
			.add(Restrictions.eq("transactionId", transactionId))
			.add(Restrictions.eq("incidentId", incidentId))
			.add(Restrictions.gt("hoursOfWork", standardHours));
		
		if(filter.getSortBy().equals(TimeReportsSortByEnum.REQUEST_NUMBER.name())) {
			crit.addOrder(OrderBySql.sql(sortRequestNumberAscendingSQL))
				.addOrder(Order.asc("shiftStartDate"));
		} else if(filter.getSortBy().equals(TimeReportsSortByEnum.LAST_NAME.name())) {
			crit.addOrder(Order.asc("resourceLastName"))
				.addOrder(Order.asc("resourceFirstName"))
				.addOrder(Order.asc("shiftStartDate"))
				.addOrder(Order.asc("hoursOfWork"));
		} else {
			crit.addOrder(Order.asc("hoursOfWork"))
				.addOrder(Order.asc("shiftStartDate"))
				.addOrder(Order.asc("resourceLastName"))
				.addOrder(Order.asc("resourceFirstName"));
		}
		
		Collection<ScratchReportTime> entities = crit.list();
		return entities;
	}
	
	/**
	 * Retrieves data that will be used (After later transforming) as the datasource for 
	 * the Personnel Time Report's agency/resource/fax checkbox tree component.
	 * @param incidentOrGroupId
	 * @param isIncidentGroup
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<PersonnelTimeReportAgencyFaxResourceData> getAgencyTreeDataForPersonnelTimeRep(
			Long incidentOrGroupId, boolean isIncidentGroup) throws PersistenceException {
		
		Collection<PersonnelTimeReportAgencyFaxResourceData> retrievedList = 
					new ArrayList<PersonnelTimeReportAgencyFaxResourceData>();
		
		String sqlQueryString = TimePostQuery.getAgencyTreeQueryForPersonnelTimeRep(
				incidentOrGroupId, isIncidentGroup, super.isOracleDialect());
		
		SQLQuery sqlData = getHibernateSession().createSQLQuery(sqlQueryString);
		CustomResultTransformer crt = new CustomResultTransformer(PersonnelTimeReportAgencyFaxResourceData.class);
		crt.addScalar("agencyId", Long.class.getName());
		crt.addScalar("resourceId", Long.class.getName());
		sqlData.setResultTransformer(crt);

		try {
			retrievedList = sqlData.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		
		return retrievedList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getPersonnelTimeReportData(gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<PersonnelTimeReportData> getPersonnelTimeReportData(PersonnelTimeReportFilter filter,
			Long incidentId) throws PersistenceException {

		Collection<PersonnelTimeReportData> rData = new ArrayList<PersonnelTimeReportData>();
		Collection<PersonnelTimeReportData> remove = new ArrayList<PersonnelTimeReportData>();
		
		/*
		Time is posted for the Resource during the defined date range.
		A fax number is defined for the Resource in the e-ISuite System.
		The Employment Type is FED or OTHER
		The Resource has an Agency defined.
		The Resource does not have a D (Demobed) Status.
		*/
		StringBuffer sbsql = new StringBuffer();
		sbsql.append("SELECT DISTINCT")
		  .append(" i.id AS incidentId, ")
		  .append(" i.incident_name AS incidentName, ")
		  .append(" country.cc_abbreviation || '-' || org.unit_code || '-' || i.nbr AS incidentNumber, ")
		  .append(" at.fax AS faxNumber ")
		  .append(" FROM ")
		  .append(" isw_incident i ")
		  .append(" LEFT OUTER JOIN iswl_country_subdivision country_sub ON country_sub.id = i.country_subdivision_id ")
		  .append(" LEFT OUTER JOIN iswl_country country ON country.id = country_sub.country_id ")
		  .append(" LEFT OUTER JOIN isw_organization org ON org.id = i.unit_id ")
		  .append(", isw_incident_resource ir")
		  .append(", isw_assignment a")
		  .append(", isw_assignment_time at")
		  .append(", isw_work_period_assignment wpa")
		  .append(", isw_work_period wp")
		  .append(" WHERE ");
		if(filter.getIncidentId() > 0) {
			sbsql.append(" i.id = " + filter.getIncidentId());
		} else if (filter.getIncidentGroupId() > 0) {
			sbsql.append(" i.id IN (")
			  .append(" SELECT igi.incident_id FROM isw_incident_group_incident igi")
			  .append(" WHERE igi.incident_group_id = " + filter.getIncidentGroupId() + ")");
		}
		sbsql.append(" AND ir.id = wp.incident_resource_id")
		  .append(" AND i.id = ir.incident_id")
		  .append(" AND wp.id = wpa.work_period_id")
		  .append(" AND wpa.assignment_id = a.id")
		  .append(" AND a.id = at.assignment_id")
		  .append(" AND a.assignment_status != 'D'")
		  .append(" AND ir.resource_id in (" + filter.getResourceIdsString() + ")")
		  .append(" AND at.fax is not null")
		  .append(" ORDER BY incidentId");

		SQLQuery sqlrData = getHibernateSession().createSQLQuery(sbsql.toString());
		CustomResultTransformer crt = new CustomResultTransformer(PersonnelTimeReportData.class);
		crt.addScalar("incidentId", Long.class.getName());
		sqlrData.setResultTransformer(crt);

		try {
			rData = sqlrData.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		for(PersonnelTimeReportData rd : rData) {
			rd.setPostStartDate(filter.getStartDate());
			rd.setPostStopDate(filter.getEndDate());
			rd.setTimeUnitLeaderName(filter.getTimeUnitLeaderName()!=null?filter.getTimeUnitLeaderName().toUpperCase():null);
			rd.setTimeUnitLeaderPhoneNumber(filter.getTimeUnitLeaderPhoneNumber());

			Collection<PersonnelTimeSubReportData> subData = getPersonnelTimeReportDetails(filter, rd);
			if(subData != null && subData.size() > 0){
				// Modify subdata to show group rows:
				Collection<PersonnelTimeSubReportData> groupedSubData = groupPersonnelTimeSubReportData(subData);
				rd.setSubReportData(groupedSubData);
			}
			else
				remove.add(rd);
		}

		if(remove.size() > 0) {
			for(PersonnelTimeReportData rd : remove) {
				rData.remove(rd);
			}
		}
		
		return rData;
	}
	
	/**
	 * Private method to allow grouping of the report content under resource name and post start
	 * date. This allows creating the required report content without creating multiple levels of
	 * sub-reports. 
	 * @param inList
	 * @return A modified list of data that contains group header rows along with the original data
	 */
	private Collection<PersonnelTimeSubReportData> groupPersonnelTimeSubReportData(Collection<PersonnelTimeSubReportData> inList) {
		// Sort items in ascending order of ResourceName, PostStartDate, AccountingCode
		Collections.sort((List<PersonnelTimeSubReportData>) inList);
		
		// Add group header objects and set labels inside group parts to null 
		Collection<PersonnelTimeSubReportData> outList = new ArrayList<PersonnelTimeSubReportData>();
		
		// Every time resource name changes, add a resource group header, and set resource name in existing 
		// object to null
		String currentLevel1Value = "";
		Date currentLevel2Value = null;
		String previousResourceName = ""; 
		
		for (PersonnelTimeSubReportData inData: inList) {
			if(!currentLevel1Value.equals(inData.getResourceName())){
				currentLevel1Value = inData.getResourceName();
				PersonnelTimeSubReportData groupHeader = new PersonnelTimeSubReportData();
				groupHeader.setResourceName(currentLevel1Value);
				outList.add(groupHeader);
			}
			
			if(inData.getPostStartDate()!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");      
				Date dateWithoutTime = null;
				try {
					dateWithoutTime = sdf.parse(sdf.format(inData.getPostStartDate()));
				} catch (ParseException e) {
					//e.printStackTrace();
				}
			
				// If the resource is the same, and the date has not changed, don't add
				// the start date header. However, if the date has changed, OR, the
				// resource has changed, add the date header.
				if(dateWithoutTime!=null 
						&& !(dateWithoutTime.equals(currentLevel2Value) &&  previousResourceName.equals(inData.getResourceName()))
					){
					currentLevel2Value = dateWithoutTime;
					PersonnelTimeSubReportData groupHeader = new PersonnelTimeSubReportData();
					groupHeader.setPostStartDate(currentLevel2Value);
					outList.add(groupHeader);
					
					// Save the current resource name, if it exists, so that it can be checked
					// in the next loop
					if(StringUtility.hasValue(inData.getResourceName())){
						previousResourceName = inData.getResourceName();
					}
				}
			}
			inData.setResourceName(null);
			inData.setPostStartDate(null);
			outList.add(inData);
			
			
		}
		return outList;
	}

	private Collection<PersonnelTimeSubReportData> getPersonnelTimeReportDetails(PersonnelTimeReportFilter filter,
			PersonnelTimeReportData data) throws PersistenceException {

		Collection<PersonnelTimeSubReportData> subReportData = new ArrayList<PersonnelTimeSubReportData>();

		StringBuffer sbsql = new StringBuffer();
		sbsql.append("select distinct ")
				.append(" case ")
				.append("when r.is_person = " + (super.isOracleDialect() ? 1 : true) + " then r.last_name || ', ' || r.first_name ")
				.append("else ")
				.append("r.resource_name ")
				.append("end as resourceName ")
				.append(", ac.account_code as accountingCode")
				.append(", atp.post_start_date as postStartDate")
				.append(", atp.post_start_date as postStartTime")
				.append(", atp.post_stop_date as postStopDate")
				.append(", atp.quantity as totalHours") //TODO: Check UOM field to determine proper display of hours,etc. -dbudge
				.append(", sp.code as premiumCode")
				.append(" from")
				.append(" isw_resource r")
				.append(", isw_incident i")
				.append(", isw_incident_resource ir")
				.append(", isw_assignment a")
				.append(", isw_assignment_time at")
				.append(", isw_assign_time_post atp")
				.append(" left join iswl_special_pay sp on atp.special_pay_id = sp.id")
				.append(", isw_work_period wp")
				.append(", isw_work_period_assignment wpa")
				.append(", isw_incident_account_code iac")
				.append(", isw_account_code ac")
				.append(" where")
				.append(" atp.incident_account_code_id = iac.id")
				.append(" and iac.account_code_id = ac.id ")
				.append(" and iac.incident_id = i.id ")
				.append(" and i.id = " + data.getIncidentId())
				.append(" and i.id = ir.incident_id ")
				.append(" and r.id = ir.resource_id ")
				.append(" and ir.id = wp.incident_resource_id ")
				.append(" and wp.id = wpa.work_period_id ")
				.append(" and wpa.assignment_id = a.id ")
				.append(" and a.id = at.assignment_id ")
				.append(" and a.assignment_status != 'D'")
				.append(" and at.id = atp.assignment_time_id ")
				.append(" and r.id in (" + filter.getResourceIdsString() + ")")
				.append(" and at.fax = '" + data.getFaxNumber() + "'")
				.append(" and atp.post_start_date between ")
				.append(" to_date('" + sdf.format(filter.getStartDate())
								+ " 00:00:00','yyyy-MM-dd hh24:mi:ss') ")
				.append(" and to_date('" + sdf.format(filter.getEndDate())
								+ " 23:59:59','yyyy-MM-dd hh24:mi:ss') ")
				.append(" order by resourceName, postStartDate, accountingCode");

		SQLQuery sqlSubReport = getHibernateSession().createSQLQuery(sbsql.toString());
		CustomResultTransformer crt = new CustomResultTransformer(PersonnelTimeSubReportData.class);
		crt.addScalar("totalHours", Double.class.getName());
		sqlSubReport.setResultTransformer(crt);

		try {
			subReportData = sqlSubReport.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return subReportData;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getPreviousTimeInvoicesNames(java.lang.String)
	 */
	public Collection<String> getPreviousTimeInvoicesNames(String invoiceIdSub) throws PersistenceException {
		if(null == invoiceIdSub) {
			throw new PersistenceException("Invoice Number is required for ReportTimeDao.getOF288PreviousInvoiceNames");
		}

		String sql = TimePreviousInvoiceQuery.getPreviousInvoiceQuery(invoiceIdSub+"%",super.isOracleDialect());
		/*
		StringBuffer sql = new StringBuffer();
		//sql.append("select distinct ti.invoice_number ")
		sql.append("select distinct to_char(ti.first_include_date, 'MM/DD/YYYY')||' - '||")
		  .append("to_char(ti.last_include_date, 'MM/DD/YYYY')||' for'||")
		  .append("to_char(((select sum(atp.rate_amount * atp.quantity) ")
		  .append("from isw_assign_time_post atp inner join isw_assign_time_post_invoice atpi on ")
		  .append("atpi.assign_time_post_id = atp.id ")
		  .append("where atpi.time_invoice_id = ti.id)+(")
		  .append("coalesce((select sum(taa1.adjustment_amount) from isw_time_assign_adjust taa1 ")
		  .append("inner join isw_time_assign_adj_invoice taai1 on taai1.time_post_adjust_id = taa1.id ")
		  .append("where taai1.time_invoice_id = ti.id and taa1.adjustment_type = 'ADDITION'),0)-")
		  .append("(coalesce((select sum(taa2.adjustment_amount) from isw_time_assign_adjust taa2 ")
		  .append("inner join isw_time_assign_adj_invoice taai2 on taai2.time_post_adjust_id = taa2.id ")
		  .append("where taai2.time_invoice_id = ti.id and taa2.adjustment_type = 'DEDUCTION'),0)))),'999,999.99') ")
		  .append("as data from isw_time_invoice ti ")
		  
		  
		  .append("where invoice_number like '" + invoiceIdSub + "%' ")
		  .append("and deleted_date is null ");
		if(super.isOracleDialect()) {
			sql.append("and is_draft = 0 ");
			sql.append("and is_duplicate = 0 ");
		} else {
			sql.append("and is_draft = false ");
			sql.append("and is_duplicate = false ");
		}
		sql.append("order by data ");
		*/
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		CustomResultTransformer crt = new CustomResultTransformer(PreviousInvoiceDataVo.class);
		crt.addScalar("totalPostings", Double.class.getName());
		crt.addScalar("totalAdditions", Double.class.getName());
		crt.addScalar("totalDeductions", Double.class.getName());
		query.setResultTransformer(crt);
		
		Collection<PreviousInvoiceDataVo> data = query.list();
		Collection<String> sdata = new ArrayList<String>();
		if(null != data && CollectionUtility.hasValue(data)){
			for(PreviousInvoiceDataVo p : data){
				//sdata.add(p.getVal1()+PreviousInvoiceDataVo.getInvoiceTotal(p));
				sdata.add(p.getVal1());
			}
		}
		
		return sdata;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getTimeInvoiceNameCount(java.lang.String)
	 */
	public String getTimeInvoiceNameCount(String invoiceId) throws PersistenceException {
		if(null == invoiceId) {
			throw new PersistenceException("Invoice Number is required for ReportTimeDao.getOF288InvoiceNameCount");
		}

		StringBuffer sql = new StringBuffer();
		sql.append("select (count(ti.invoice_number)+1) ")
		  .append("from isw_time_invoice ti ")
		  .append("where invoice_number = '" + invoiceId + "' ");
		if(super.isOracleDialect()) {
			sql.append("and is_draft = 0 ");
		} else {
			sql.append("and is_draft = false ");
		}

		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		String count = "";
		if(super.isOracleDialect()) {
			BigDecimal data = (BigDecimal) query.uniqueResult();
			count = Integer.toString(data.intValue());
		} else {
			BigInteger data = (BigInteger) query.uniqueResult();
			count = Integer.toString(data.intValue());
		}

		return count;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getTimeInvoiceLikeNameCount(java.lang.String)
	 */
	public Integer getTimeInvoiceLikeNameCount(String invoiceId) throws PersistenceException {
		if(null == invoiceId) {
			throw new PersistenceException("Invoice Number is required for ReportTimeDao.getOF288InvoiceNameCount");
		}

		StringBuffer sql = new StringBuffer();
		sql.append("select (count(ti.invoice_number)) ")
		  .append("from isw_time_invoice ti ")
		  .append("where invoice_number like '" + invoiceId + "%' ");
		if(super.isOracleDialect()) {
			sql.append("and is_draft = 0 ");
		} else {
			sql.append("and is_draft = false ");
		}

		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		Integer count = 0;
		if(super.isOracleDialect()) {
			BigDecimal data = (BigDecimal) query.uniqueResult();
			count = data.intValue();
		} else {
			BigInteger data = (BigInteger) query.uniqueResult();
			count = data.intValue();
		}

		return count;
	}

	private BigDecimal checkForTimePostings(TimeReportFilter filter) throws PersistenceException {
		BigDecimal resourceId;
		if(filter.getResourceId() == null)
			resourceId = this.getResourceId(filter);
		else
			resourceId = new BigDecimal(filter.getResourceId());

		StringBuffer sbsql = new StringBuffer();
		sbsql.append("select ")
		  .append("distinct assignment_time_id ")
		  .append("from ")
		  .append("isw_resource r, ")
		  .append("isw_incident_resource ir, ")
		  .append("isw_work_period wp, ")
		  .append("isw_work_period_assignment wpa, ")
		  .append("isw_assignment a, ")
		  .append("isw_assignment_time at, ")
		  .append("isw_assign_time_post atp ")
		  .append("where ")
		  .append("r.id = " + resourceId + " ")
		  .append("and r.id = ir.resource_id ")
		  .append("and ir.id = wp.incident_resource_id ")
		  .append("and wp.id = wpa.work_period_id ")
		  .append("and wpa.assignment_id = a.id ")
		  .append("and a.id = at.assignment_id ")
		  .append("and at.id = atp.assignment_time_id ");

		BigDecimal assignmentTimeId;
		try {
			SQLQuery sqlQuery1 = getHibernateSession().createSQLQuery(sbsql.toString());
			assignmentTimeId = (BigDecimal) sqlQuery1.uniqueResult();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		if(assignmentTimeId == null) {
			//TODO: Return message to the user stating that Time reports can
			// only be generated if the selected Resource has a Time posting. -dbudge
			throw new PersistenceException("Fail!");
		}

		return resourceId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ReportTimeDao#getResourceId(gov.nwcg.isuite.framework.filter.TimeReportFilter)
	 */
	public BigDecimal getResourceId(TimeReportFilter filter) throws PersistenceException {
		String[] names = null;
		if(filter.getPersonName() != null) {
			names = filter.getPersonName().split(" ");
		}

		StringBuffer sbsql = new StringBuffer();

		sbsql.append("select ")
		  .append("distinct r.id ")
		  .append("from ")
		  .append("isw_assignment a, ")
		  .append("isw_work_period_assignment wpa, ")
		  .append("isw_work_period wp, ")
		  .append("isw_incident_resource ir, ")
		  .append("isw_resource r ")
		  .append("where ");
		if(filter.getRequestNumber() != null) {
			sbsql.append("a.request_number = '" + filter.getRequestNumber() + "' ");
		} else if(filter.getPersonName() != null) {
			sbsql.append("r.first_name = '" + names[0] + "' ")
			.append(" and r.last_name = '" + names[1] + "' ");
		}
		sbsql.append("and a.id = wpa.assignment_id ")
		  .append("and wpa.work_period_id = wp.id ")
		  .append("and wp.incident_resource_id = ir.id ")
		  .append("and ir.resource_id = r.id ");

		BigDecimal resourceId;
		try{
			SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sbsql.toString());
			resourceId = (BigDecimal)sqlQuery.uniqueResult();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return resourceId;
	}

	/**
	 * Method to generate the Vendor Resource Summary Report
	 * UC R-6.07
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<VendorResourceSummaryReportData> getVendorResourceSummaryReportData(
			VendorResourceSummaryReportFilter filter) throws PersistenceException {

		Collection<VendorResourceSummaryReportData> vos = new ArrayList<VendorResourceSummaryReportData>();
		
		// Create the SQL Query String
		String sqlQueryString = TimePostQuery.getVendorResourceSummaryReport(filter, super.isOracleDialect());
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sqlQueryString);

		CustomResultTransformer crt = new CustomResultTransformer(VendorResourceSummaryReportData.class);
		crt.addScalar("hireDate", Date.class.getName());
		crt.addScalar("releaseDate", Date.class.getName());
		sqlQuery.setResultTransformer(crt);
    
		try {
			vos = sqlQuery.list();
			if(vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}
	
	/**
	 * Method to generate the Missing Days Of Postings Report
	 * UC R-6.05
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<MissingDaysOfPostingsSubReportData> getDaysOfPostingsReportData(
			MissingDaysOfPostingsReportFilter filter, Long incidentId) throws PersistenceException {

		Collection<MissingDaysOfPostingsSubReportData> vos = new ArrayList<MissingDaysOfPostingsSubReportData>();
		
		// Create the SQL Query String
		String sqlQueryString = TimePostQuery.getDaysOfPostingsReport(filter, incidentId, super.isOracleDialect());
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sqlQueryString);

		CustomResultTransformer crt = new CustomResultTransformer(MissingDaysOfPostingsSubReportData.class);
		crt.addScalar("postingDate", Date.class.getName());
		sqlQuery.setResultTransformer(crt);
    
		try {
			vos = sqlQuery.list();
			
			if(vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
				
		return vos;
	}
	
	/**
	 * Method to generate the Missing Days Of Postings Report
	 * UC R-6.05
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<MissingDaysOfPostingsSubReportData> getUniqueDaysOfPostingsReportData(
			MissingDaysOfPostingsReportFilter filter, Long incidentId) throws PersistenceException {

		Collection<MissingDaysOfPostingsSubReportData> vos = new ArrayList<MissingDaysOfPostingsSubReportData>();
		
		// Create the SQL Query String
		String sqlQueryString = TimePostQuery.getUniqueDaysOfPostingsReport(filter, incidentId, super.isOracleDialect());
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sqlQueryString);

		CustomResultTransformer crt = new CustomResultTransformer(MissingDaysOfPostingsSubReportData.class);
		crt.addScalar("postingDate", Date.class.getName());
		sqlQuery.setResultTransformer(crt);
    
		try {
			vos = sqlQuery.list();
						
			if(vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
				
		return vos;
	}	

	/**
	 * Method to generate the Summary Of Hours Worked Report
	 * UC R-6.07
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceDateData> getSummaryHoursWorkedReportData(SummaryHoursWorkedReportFilter filter) 
	                 throws PersistenceException {
		
		if(null != logger)logger.addLog("ReportTimeDaoHibernate: getSummaryHoursWorkedReportData() Begin", Level.INFO);
		
		List<ResourceDateData> sub2ReportData = new ArrayList<ResourceDateData>();
		SQLQuery query = getHibernateSession().createSQLQuery(
				SummaryHoursWorkedReportQuery.getSummaryHoursWorkedDateDataQuery(filter, super.isOracleDialect()));
		
		try {
			CustomResultTransformer crt = new CustomResultTransformer(ResourceDateData.class);
			crt.addScalar("incidentid", Long.class.getName());
			crt.addScalar("resourceid", Long.class.getName());
			crt.addScalar("hoursWorkedDate1", Double.class.getName());
			crt.addScalar("hoursWorkedDate2", Double.class.getName());
			crt.addScalar("hoursWorkedDate3", Double.class.getName());
			crt.addScalar("hoursWorkedDate4", Double.class.getName());
			crt.addScalar("hoursWorkedDate5", Double.class.getName());
			crt.addScalar("hoursWorkedDate6", Double.class.getName());
			crt.addScalar("hoursWorkedDate7", Double.class.getName());
			crt.addScalar("total", Double.class.getName());
			crt.addScalar("week", Integer.class.getName());
			
			query.setResultTransformer(crt);
			if(null != logger)logger.addLog("ReportTimeDaoHibernate: Before Query ", Level.INFO);
			sub2ReportData = query.list();
			if(null != logger)logger.addLog("ReportTimeDaoHibernate: After Query ", Level.INFO);
			if(null != logger)logger.addLog("ReportTimeDaoHibernate: sub2ReportData Size: " + (null != sub2ReportData ? sub2ReportData.size() : "null"), Level.INFO);
			
		} catch (Exception e) {
			if(null != logger)logger.addLog("ReportTimeDaoHibernate: Exception: " + e.getMessage(), Level.INFO);
			throw new PersistenceException(e);
		}

		if(null != logger)logger.addLog("ReportTimeDaoHibernate: getSummaryHoursWorkedReportData() End", Level.INFO);
		return sub2ReportData;
	}
	
	/**
	 * Method to generate the Summary Of Hours Worked Report data in isw_scratch_report_time table
	 * UC R-6.07
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insertSummaryHoursWorkedReportDataToScratchTable(SummaryHoursWorkedReportFilter filter) 
	                 throws PersistenceException {
		
		if(null != logger)logger.addLog("ReportTimeDaoHibernate: insertSummaryHoursWorkedReportDataToScratchTable() Begin", Level.INFO);

		Long transactionId = null;
		this.dataSource = (DataSource)context.getBean("dataSource");
		
		String excludeDemob = filter.isExcludeDemob()?"Y":"N";
		
		try {
			if(super.isOracleDialect()) {
				SqlParameterSource in = new MapSqlParameterSource()
					.addValue("p_incident_id", filter.getIncidentId(), Types.NUMERIC)
	            	.addValue("p_incident_group_id", filter.getIncidentGroupId(), Types.NUMERIC)
	            	.addValue("p_range_start_date", filter.getStartDate(), Types.TIMESTAMP)
	    			.addValue("p_range_end_date", filter.getEndDate(), Types.TIMESTAMP)
					.addValue("p_resource_id", filter.getResourceId(), Types.NUMERIC)
					.addValue("p_section_list", filter.getSectionsString(), Types.VARCHAR)
					//.addValue("p_exclude_demob", filter.isExcludeDemob(), Types.VARCHAR);
					.addValue("p_exclude_demob", excludeDemob, Types.VARCHAR);
				
				if(null != logger)logger.addLog("ReportTimeDaoHibernate: before call report_time_pkg.rpt_time_genrep_summary_hrs #1", Level.INFO);
				
				SimpleJdbcCall genRepSummaryProcedure = new SimpleJdbcCall(dataSource);
				genRepSummaryProcedure.withCatalogName("report_time_pkg")
					.withProcedureName("rpt_time_genrep_summary_hrs");
				
				Map<String, Object> out = genRepSummaryProcedure.execute(in);
				if(null != logger)logger.addLog("ReportTimeDaoHibernate: after call call report_time_pkg.rpt_time_genrep_summary_hrs #1", Level.INFO);
				transactionId = ((java.math.BigDecimal)(out.get("P_TRANSACTION_ID"))).longValue();
				if(null != logger)logger.addLog("ReportTimeDaoHibernate: transactionId: "+ (transactionId != null ? String.valueOf(transactionId) : "null"), Level.INFO);
				filter.setTransactionId(transactionId);
			} else { 
				SqlParameterSource in = new MapSqlParameterSource()
					.addValue("p_incident_id", filter.getIncidentId(), Types.BIGINT)
	            	.addValue("p_incident_group_id", filter.getIncidentGroupId(), Types.BIGINT)
	    			.addValue("p_range_start_date", filter.getStartDate(), Types.TIMESTAMP)
	    			.addValue("p_range_end_date", filter.getEndDate(), Types.TIMESTAMP)
					.addValue("p_resource_id", filter.getResourceId(), Types.BIGINT)
					.addValue("p_section_list", filter.getSectionsString(), Types.VARCHAR)
					.addValue("p_exclude_demob", excludeDemob, Types.VARCHAR);
			
				SimpleJdbcCall genRepSummaryProcedure = new SimpleJdbcCall(dataSource);
				genRepSummaryProcedure.withFunctionName("rpt_time_genrep_summary_hrs");
						 
				if(null != logger)logger.addLog("ReportTimeDaoHibernate: before call report_time_pkg.rpt_time_genrep_summary_hrs #2", Level.INFO);
				Map<String, Object> out = genRepSummaryProcedure.execute(in);
				if(null != logger)logger.addLog("ReportTimeDaoHibernate: after call report_time_pkg.rpt_time_genrep_summary_hrs #2", Level.INFO);
				transactionId = (Long)(out.get("returnvalue"));
				if(null != logger)logger.addLog("ReportTimeDaoHibernate: transactionId: "+ (transactionId != null ? String.valueOf(transactionId) : "null"), Level.INFO);
				filter.setTransactionId(transactionId);
			}
		} catch (Exception e) {  
			if(null != logger)logger.addLog("ReportTimeDaoHibernate: Exception: "+e.getMessage(), Level.INFO);
			throw new PersistenceException(e);	        
		}
		if(null != logger)logger.addLog("ReportTimeDaoHibernate: insertSummaryHoursWorkedReportDataToScratchTable() End", Level.INFO);
	}
	
	/**
	 * Method to generate the Work/Rest Ratio Report.
	 * This method inserts data in the scratch time report table, which is later retrieved by a different method, using the
	 * transaction Id.
	 * UC R-6.03
	 */
	//@SuppressWarnings("unchecked")
	@Override
	public Long insertWorkRestRatioReportDataToScratchTable(WorkRestRatioReportFilter filter) throws PersistenceException {
		Long transactionId = null;
		this.dataSource = (DataSource)context.getBean("dataSource");
		
		String sectionString = filter.getFilteredSectionString();// Options: Some comma separated combination of F,L,O,P,C,E or null
		String excludeDemob = filter.isExcludeDemob()?"Y":"N";
		
		try {
			if(super.isOracleDialect()) {
				SqlParameterSource in = new MapSqlParameterSource()
					.addValue("p_incident_id", filter.getIncidentId(), Types.NUMERIC)
	            	.addValue("p_incident_group_id", filter.getIncidentGroupId(), Types.NUMERIC)
	            	.addValue("p_range_start_date", filter.getStartDate(), Types.TIMESTAMP)
	    			.addValue("p_range_end_date", filter.getEndDate(), Types.TIMESTAMP)
					.addValue("p_resource_id", filter.getResourceId(), Types.NUMERIC)
					.addValue("p_section_list", sectionString!=null?sectionString.trim():null, Types.VARCHAR)
					.addValue("p_exclude_demob", excludeDemob, Types.VARCHAR);
				
				SimpleJdbcCall genRepSummaryProcedure = new SimpleJdbcCall(dataSource);
				genRepSummaryProcedure.withCatalogName("report_time_pkg")
					.withProcedureName("rpt_time_genrep_wr_ratio");
				
				Map<String, Object> out = genRepSummaryProcedure.execute(in);
				transactionId = ((java.math.BigDecimal)(out.get("P_TRANSACTION_ID"))).longValue();
			} else { 
				SqlParameterSource in = new MapSqlParameterSource()
					.addValue("p_incident_id", filter.getIncidentId(), Types.BIGINT)
	            	.addValue("p_incident_group_id", filter.getIncidentGroupId(), Types.BIGINT)
	    			.addValue("p_range_start_date", filter.getStartDate(), Types.TIMESTAMP)
	    			.addValue("p_range_end_date", filter.getEndDate(), Types.TIMESTAMP)
					.addValue("p_resource_id", filter.getResourceId(), Types.BIGINT)
					.addValue("p_section_list", sectionString, Types.VARCHAR)
					.addValue("p_exclude_demob", excludeDemob, Types.VARCHAR);
			
				SimpleJdbcCall genRepSummaryProcedure = new SimpleJdbcCall(dataSource);
				genRepSummaryProcedure.withFunctionName("rpt_time_genrep_wr_ratio");
						 
				Map<String, Object> out = genRepSummaryProcedure.execute(in);
				transactionId = (Long)(out.get("returnvalue"));
			}
			
			return transactionId;
			
		} catch (Exception e) {  
			throw new PersistenceException(e);	        
		}
	}
	
	/**
    * Method used to retrieve Report Time scratch records using a transaction Id
    * UC R-6.03
    */
	@Override
	@SuppressWarnings("unchecked")
	public Collection<ScratchReportTime> getWorkRestRatioReportData(Long transactionId, WorkRestRatioReportFilter filter) 
		throws PersistenceException {

		String sortRequestNumberAscendingSQL="sortrequestnumber(this_.request_number)";

		if(!LongUtility.hasValue(transactionId)) {
			throw new IllegalArgumentException("Transaction Id cannot be null.");
		}
		
		// Criteria specifically for WorkRestRatio Report
		Criteria crit = getHibernateSession().createCriteria(ScratchReportTimeImpl.class)
			.add(Restrictions.eq("transactionId", transactionId))
			.add(Restrictions.gt("mitigationNeeded", new BigDecimal(0)))
			.add(Restrictions.gt("hoursOfRest", new BigDecimal(-1)));
		
		// Apply sorting based on user selected criteria
		if(filter.isAllResources()) { // User generated report for all resources
			// Apply the grouping and sorting clauses
			if(filter.isGroupBySection()) {
				crit.addOrder(Order.asc("sectionCode"));
				if(filter.isSectionSortByShiftStartDate()) {
					crit.addOrder(Order.asc("shiftStartDate"))
						.addOrder(OrderBySql.sql(sortRequestNumberAscendingSQL));
				} else if(filter.isSectionSortByRequestNumber()) {
					crit.addOrder(OrderBySql.sql(sortRequestNumberAscendingSQL))
						.addOrder(Order.asc("shiftStartDate"));
				} else if(filter.isSectionSortByName()) {
					crit.addOrder(Order.asc("resourceLastName"))
						.addOrder(Order.asc("resourceFirstName"))
						.addOrder(Order.asc("shiftStartDate"))
						.addOrder(OrderBySql.sql(sortRequestNumberAscendingSQL));
						
				} 
			} else if(filter.isGroupByDate()) {
				if(filter.isDateTypeAscending()) {
					crit.addOrder(Order.asc("shiftStartDate"));
				} else {
					crit.addOrder(Order.desc("shiftStartDate"));
				}
				if(filter.isDateSortByRequestNumber()) {
					crit.addOrder(OrderBySql.sql(sortRequestNumberAscendingSQL));
				} else if(filter.isDateSortByName()) {
					crit.addOrder(Order.asc("resourceLastName"))
						.addOrder(Order.asc("resourceFirstName"))
						.addOrder(OrderBySql.sql(sortRequestNumberAscendingSQL));
				}
			} else {
				crit.addOrder(OrderBySql.sql(sortRequestNumberAscendingSQL))
					.addOrder(Order.asc("shiftStartDate"));
			}
		} else { // User generated report for a specific resource
			crit.addOrder(Order.asc("shiftStartDate"))
				.addOrder(OrderBySql.sql(sortRequestNumberAscendingSQL));
		}
		
		Collection<ScratchReportTime> entities = crit.list();
		
		return entities;
	}
	
	/**
	 * Method used to remove Report Time related scratch records.
	 * @param transactionId
	 * @return boolean indicating that the scratch records were deleted successfully.
	 * @throws PersistenceException
	 */
	@Override
	public boolean deleteReportTimeScratchData(Long transactionId) throws PersistenceException {
		SQLQuery query = getHibernateSession().createSQLQuery("delete from ISW_SCRATCH_REPORT_TIME where transaction_id = :id ");
		query.setLong("id", transactionId);
		query.executeUpdate();
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Collection<CrewRosterReportData> getCrewRosterReportData(TimeReportFilter filter) throws PersistenceException {
		
		Collection<CrewRosterReportData> vos = new ArrayList<CrewRosterReportData>();
		
		// Create the SQL Query String
		String sqlQueryString = TimePostQuery.getCrewRosterReportQuery(filter, super.isOracleDialect());
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sqlQueryString);

		CustomResultTransformer crt = new CustomResultTransformer(CrewRosterReportData.class);
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("checkInDate", Date.class.getName());
		crt.addScalar("firstWorkDay", Date.class.getName());
		crt.addScalar("lastWorkDay", Date.class.getName());
		crt.addScalar("actualReleaseDate", Date.class.getName());
		crt.addScalar("parent", Boolean.class.getName());
		crt.addScalar("lengthOfAssignment", Long.class.getName());
		
		sqlQuery.setResultTransformer(crt);
		
		try {
			vos = sqlQuery.list();
			if(vos == null) {
				return null;
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
		return vos;
	}
	
	public Collection<TimeReportData2Vo> getTimeReportData(TimeReport2FilterImpl filter) throws PersistenceException {
		String sql = TimeReportQuery.getTimeReportDataQuery(filter, super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		CustomResultTransformer crt = new CustomResultTransformer(TimeReportData2Vo.class);
		crt.addScalar("atpId", Long.class.getName());
		crt.addScalar("atpAssignmentTimeId", Long.class.getName());
		crt.addScalar("atpStartDate", Date.class.getName());
		crt.addScalar("atpStopDate", Date.class.getName());
		crt.addScalar("atpQuantity", BigDecimal.class.getName());
		crt.addScalar("atpNextId", Long.class.getName());
		crt.addScalar("atpNextStartDate", Date.class.getName());
		crt.addScalar("atpPreviousId", Long.class.getName());
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("restBeforeNextStart", BigDecimal.class.getName());
		
		query.setResultTransformer(crt);
		
		return query.list();
	}
}
