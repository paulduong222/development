package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.core.domain.impl.ResourceTrainingImpl;
import gov.nwcg.isuite.core.persistence.ResourceTrainingDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.TnspReportQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.TrainingSpecialistQuery;
import gov.nwcg.isuite.core.reports.data.Tnsp225ReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp2ReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp2SubReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp2SubReportTnspData;
import gov.nwcg.isuite.core.reports.data.Tnsp3ReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp3SubReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp3SubReportPPData;
import gov.nwcg.isuite.core.reports.data.Tnsp3SubReportTnspData;
import gov.nwcg.isuite.core.reports.data.Tnsp4ReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp5ReportData;
import gov.nwcg.isuite.core.reports.data.TnspEvalRecordReportData;
import gov.nwcg.isuite.core.reports.data.TnspHUAvery5160ReportData;
import gov.nwcg.isuite.core.reports.data.TnspIncidentTraineeReportData;
import gov.nwcg.isuite.core.reports.filter.IncidentTrainingSummaryReportFilter;
import gov.nwcg.isuite.core.reports.filter.TrainingAssignmentsListReportFilter;
import gov.nwcg.isuite.core.vo.FuelTypeVo;
import gov.nwcg.isuite.core.vo.ResourceTrainingVo;
import gov.nwcg.isuite.core.vo.TrainingSpecialistVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

public class ResourceTrainingDaoHibernate extends TransactionSupportImpl
		implements ResourceTrainingDao {
	
	private final CrudDao<ResourceTraining> crudDao;
	
	public ResourceTrainingDaoHibernate(final CrudDao<ResourceTraining> crudDao) {
		if (crudDao == null) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(ResourceTraining persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public ResourceTraining getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(ResourceTraining persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<ResourceTraining> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ResourceTrainingVo> getResourceTrainings(Long incidentResourceId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ResourceTrainingImpl.class);
		crit.createAlias("this.kind", "k");
		crit.add(Expression.eq("incidentResourceId", incidentResourceId));
		crit.addOrder(Order.asc("k.code"));
		Collection<ResourceTraining> entities = crit.list();
		
		try {
			return ResourceTrainingVo.getInstances(entities, true);
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<FuelTypeVo> getSelectedFuelTypes(Long resourceTrainingId) throws PersistenceException {
		if(!LongUtility.hasValue(resourceTrainingId)) {
			throw new IllegalArgumentException("resourceTrainingId cannot be null.");
		}
		
		String sql = TrainingSpecialistQuery.getSelectedFuelTypes(resourceTrainingId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(FuelTypeVo.class);
		
		crt.addScalar("id", Long.class.getName());
		
		query.setResultTransformer(crt);
		
		query.setMaxResults(getMaxResultSize());
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<FuelTypeVo> getAvailableFuelTypes(Long resourceTrainingId) throws PersistenceException {
		if(!LongUtility.hasValue(resourceTrainingId)) {
			throw new IllegalArgumentException("resourceTrainingId cannot be null.");
		}
		
		String sql = TrainingSpecialistQuery.getAvailableFuelTypes(resourceTrainingId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(FuelTypeVo.class);
		
		crt.addScalar("id", Long.class.getName());
		
		query.setResultTransformer(crt);
		
		query.setMaxResults(getMaxResultSize());
		
		return query.list();
	}
	
	public String getTraineeTotal(Long incidentId, Long incidentGroupId) throws PersistenceException {
		
		String sql = TrainingSpecialistQuery.getTraineeTotal(incidentId, incidentGroupId, super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object result = query.uniqueResult();
		
		if(null != result){
			try{
				return String.valueOf(result);
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}else
			return "0";
		
	}
	
	public String getPriorityTrainees(Long incidentId, Long incidentGroupId) throws PersistenceException {
		
		String sql = TrainingSpecialistQuery.getPriorityTrainees(incidentId, incidentGroupId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object result = query.uniqueResult();
		
		if(null != result){
			try{
				return String.valueOf(result);
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}else
			return "0";
	}
	
	public Collection<TnspIncidentTraineeReportData> getTraineeDataFormReportData(Long rtId, Long trainerId, Long tnspId) throws PersistenceException {
		Collection<TnspIncidentTraineeReportData> list = new ArrayList<TnspIncidentTraineeReportData>();
		String sql=TnspReportQuery.getTrainingDataQuery(rtId, trainerId, tnspId,super.isOracleDialect());
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(TnspIncidentTraineeReportData.class);
		crt.addScalar("traineeIA", String.class.getName());
		crt.addScalar("traineeName", String.class.getName());
		crt.addScalar("requestNumber", String.class.getName());
		crt.addScalar("traineeItemCode", String.class.getName());
		crt.addScalar("traineeItemCodeDesc", String.class.getName());
		crt.addScalar("traineeSection", String.class.getName());
		crt.addScalar("traineeIA", String.class.getName());
		crt.addScalar("traineeAgency", String.class.getName());
		crt.addScalar("traineeUnitCode", String.class.getName());
		crt.addScalar("traineeUnitDesc", String.class.getName());
		crt.addScalar("traineeAssnStart", String.class.getName());
		crt.addScalar("traineeAssnEnd", String.class.getName());
		crt.addScalar("traineeRedCard", String.class.getName());
		crt.addScalar("traineeCurrentTaskBook", String.class.getName());
		crt.addScalar("traineeIncidentTaskBook", String.class.getName());
		crt.addScalar("traineePriority", String.class.getName());
		crt.addScalar("traineePriorityProgram", String.class.getName());
		crt.addScalar("traineeHUName", String.class.getName());
		crt.addScalar("traineeHUCode", String.class.getName());
		crt.addScalar("traineeHUDesc", String.class.getName());
		crt.addScalar("traineeHUAddress", String.class.getName());
		crt.addScalar("traineeHUCity", String.class.getName());
		crt.addScalar("traineeHUState", String.class.getName());
		crt.addScalar("traineeHUZip", String.class.getName());
		crt.addScalar("traineeHUPhone", String.class.getName());
		crt.addScalar("traineeHUEmail", String.class.getName());
		crt.addScalar("traineeGoal1", String.class.getName());
		crt.addScalar("traineeGoal2", String.class.getName());
		crt.addScalar("traineeGoal3", String.class.getName());
		crt.addScalar("trainerName", String.class.getName());
		crt.addScalar("trainerRequestNumber", String.class.getName());
		crt.addScalar("trainerUnit", String.class.getName());
		crt.addScalar("trainerUnitDesc", String.class.getName());
		crt.addScalar("trainerItemCode", String.class.getName());
		crt.addScalar("trainerItemDesc", String.class.getName());
		crt.addScalar("trainerAddress", String.class.getName());
		crt.addScalar("trainerCity", String.class.getName());
		crt.addScalar("trainerState", String.class.getName());
		crt.addScalar("trainerZip", String.class.getName());
		crt.addScalar("trainerPhone", String.class.getName());
		crt.addScalar("trainerEmail", String.class.getName());
		crt.addScalar("trainerRecommend", String.class.getName());
		crt.addScalar("trainerPtbProgress", String.class.getName());
		crt.addScalar("incidentName", String.class.getName());
		crt.addScalar("incidentNumber", String.class.getName());
		crt.addScalar("incidentType", String.class.getName());
		crt.addScalar("incidentComplexity", String.class.getName());
		crt.addScalar("incidentAcres", String.class.getName());
		crt.addScalar("incidentFuelType", String.class.getName());
		crt.addScalar("tnspFullName", String.class.getName());
		crt.addScalar("tnspUnit", String.class.getName());
		crt.addScalar("tnspUnitDescription", String.class.getName());
		crt.addScalar("tnspAgency", String.class.getName());
		crt.addScalar("tnspPhone", String.class.getName());
		crt.addScalar("tnspEmail", String.class.getName());
		
		q.setResultTransformer(crt); 

		list = q.list();
		
		return list;
	}
	
	public Collection<TnspEvalRecordReportData> getTnspEvalRecordReportData(Long rtId, Long rttId) throws PersistenceException {
		Collection<TnspEvalRecordReportData> list = new ArrayList<TnspEvalRecordReportData>();
		String sql=TnspReportQuery.getTrainingEvaluationDataQuery(rtId, rttId, super.isOracleDialect());
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(TnspEvalRecordReportData.class);
		crt.addScalar("traineeName", String.class.getName());
		crt.addScalar("requestNumber", String.class.getName());
		crt.addScalar("traineeItemCode", String.class.getName());
		crt.addScalar("traineeItemCodeDesc", String.class.getName());
		crt.addScalar("traineeAgency", String.class.getName());
		crt.addScalar("traineeUnitCode", String.class.getName());
		crt.addScalar("traineeUnitDesc", String.class.getName());
		crt.addScalar("traineeHUName", String.class.getName());
		crt.addScalar("traineeHUCode", String.class.getName());
		crt.addScalar("traineeHUDesc", String.class.getName());
		crt.addScalar("traineeHUAddress", String.class.getName());
		crt.addScalar("traineeHUCity", String.class.getName());
		crt.addScalar("traineeHUState", String.class.getName());
		crt.addScalar("traineeHUZip", String.class.getName());
		crt.addScalar("traineeHUPhone", String.class.getName());
		crt.addScalar("traineeHUEmail", String.class.getName());
		crt.addScalar("trainerName", String.class.getName());
		crt.addScalar("trainerRequestNumber", String.class.getName());
		crt.addScalar("trainerUnit", String.class.getName());
		crt.addScalar("trainerUnitDesc", String.class.getName());
		crt.addScalar("trainerItemCode", String.class.getName());
		crt.addScalar("trainerItemDesc", String.class.getName());
		crt.addScalar("trainerAddress", String.class.getName());
		crt.addScalar("trainerCity", String.class.getName());
		crt.addScalar("trainerState", String.class.getName());
		crt.addScalar("trainerZip", String.class.getName());
		crt.addScalar("trainerPhone", String.class.getName());
		crt.addScalar("trainerEmail", String.class.getName());
		crt.addScalar("trainerRecommend", String.class.getName());
		crt.addScalar("incidentName", String.class.getName());
		crt.addScalar("incidentNumber", String.class.getName());
		crt.addScalar("incidentType", String.class.getName());
		crt.addScalar("incidentComplexity", String.class.getName());
		crt.addScalar("incidentAcres", String.class.getName());
		crt.addScalar("incidentFuelType", String.class.getName());
		crt.addScalar("trainerComments", String.class.getName());
		crt.addScalar("traineeAssnStart", String.class.getName());
		crt.addScalar("traineeAssnEnd", String.class.getName());

		q.setResultTransformer(crt); 

		list = q.list();
		
		return list;
		
	}

	public Collection<Tnsp5ReportData> getTnspHomeUnitLetterReportData(Long rtId, Long tnspId) throws PersistenceException {
		Collection<Tnsp5ReportData> list = new ArrayList<Tnsp5ReportData>();
		Collection<Tnsp5ReportData> listtmp = new ArrayList<Tnsp5ReportData>();
		String sql=TnspReportQuery.getTrainingHomeUnitDataQuery(rtId, tnspId,super.isOracleDialect());
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(Tnsp5ReportData.class);
		crt.addScalar("traineeName", String.class.getName());
		crt.addScalar("requestNumber", String.class.getName());
		crt.addScalar("traineeItemCode", String.class.getName());
		crt.addScalar("traineeItemCodeDesc", String.class.getName());
		crt.addScalar("traineeAgency", String.class.getName());
		crt.addScalar("traineeUnitCode", String.class.getName());
		crt.addScalar("traineeUnitDesc", String.class.getName());
		crt.addScalar("traineeHUName", String.class.getName());
		crt.addScalar("traineeHUCode", String.class.getName());
		crt.addScalar("traineeHUDesc", String.class.getName());
		crt.addScalar("traineeHUAddress", String.class.getName());
		crt.addScalar("traineeHUCity", String.class.getName());
		crt.addScalar("traineeHUState", String.class.getName());
		crt.addScalar("traineeHUZip", String.class.getName());
		crt.addScalar("traineeHUPhone", String.class.getName());
		crt.addScalar("traineeHUEmail", String.class.getName());
		crt.addScalar("trainerRecommend", String.class.getName());
		crt.addScalar("incidentName", String.class.getName());
		crt.addScalar("incidentNumber", String.class.getName());
		crt.addScalar("incidentType", String.class.getName());
		crt.addScalar("incidentComplexity", String.class.getName());
		crt.addScalar("incidentAcres", String.class.getName());
		crt.addScalar("incidentFuelType", String.class.getName());
		crt.addScalar("trainerComments", String.class.getName());
		/*
		crt.addScalar(" tnspFullName", String.class.getName());
		crt.addScalar(" tnspUnit", String.class.getName());
		crt.addScalar(" tnspAgency", String.class.getName());
		crt.addScalar(" tnspPhone", String.class.getName());
		crt.addScalar(" tnspEmail", String.class.getName());
		*/

		q.setResultTransformer(crt); 

		listtmp = q.list();

		if(CollectionUtility.hasValue(listtmp)){
			Collection<TrainingSpecialistVo> list2 = new ArrayList<TrainingSpecialistVo>();
			String sql2=TnspReportQuery.getTrainingSpecialist3Query(tnspId);
			SQLQuery q2 = getHibernateSession().createSQLQuery(sql2);
			CustomResultTransformer crt2 = new CustomResultTransformer(TrainingSpecialistVo.class);
			crt2.addScalar("id", Long.class.getName());
			crt2.addScalar("tnspName", String.class.getName());
			crt2.addScalar("tnspUnit", String.class.getName());
			crt2.addScalar("tnspAgency", String.class.getName());
			crt2.addScalar("tnspPhone", String.class.getName());
			crt2.addScalar("tnspEmail", String.class.getName());
			q2.setResultTransformer(crt2); 
			list2=q2.list();
			if(CollectionUtility.hasValue(list2)){
				TrainingSpecialistVo tnspVo = list2.iterator().next();
				for(Tnsp5ReportData d : listtmp){
					d.setTnspAgency(tnspVo.getTnspAgency());
					d.setTnspEmail(tnspVo.getTnspEmail());
					d.setTnspFullName(tnspVo.getTnspName());
					d.setTnspPhone(tnspVo.getTnspPhone());
					d.setTnspUnit(tnspVo.getTnspUnit());
					list.add(d);
				}
			}else{
				return listtmp;
			}
		}
		
		return list;
	}

	public Collection<Tnsp4ReportData> getExitInterviewReportData(Long rtId,Long trainerId, Long tnspId) throws PersistenceException {
		Collection<Tnsp4ReportData> list = new ArrayList<Tnsp4ReportData>();
		String sql=TnspReportQuery.getExitInterviewDataQuery(rtId,trainerId,tnspId, super.isOracleDialect());
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(Tnsp4ReportData.class);
		crt.addScalar("traineeName", String.class.getName());
		crt.addScalar("requestNumber", String.class.getName());
		crt.addScalar("traineeItemCode", String.class.getName());
		crt.addScalar("traineeItemCodeDesc", String.class.getName());
		crt.addScalar("trainerName", String.class.getName());
		crt.addScalar("trainerRequestNumber", String.class.getName());
		crt.addScalar("trainerUnit", String.class.getName());
		crt.addScalar("trainerUnitDesc", String.class.getName());
		crt.addScalar("trainerItemCode", String.class.getName());
		crt.addScalar("trainerItemDesc", String.class.getName());
		crt.addScalar("incidentName", String.class.getName());
		crt.addScalar("incidentNumber", String.class.getName());
		crt.addScalar("tnspFullName", String.class.getName());

		q.setResultTransformer(crt); 

		list = q.list();
		
		return list;
	}
	
	public Collection<Tnsp225ReportData> getPerformanceEvalReportData(Long rtId, Long rttId) throws PersistenceException {
		Collection<Tnsp225ReportData> list = new ArrayList<Tnsp225ReportData>();
		String sql=TnspReportQuery.getPerformanceEvalDataQuery(rtId, rttId,super.isOracleDialect());
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(Tnsp225ReportData.class);
		crt.addScalar("traineeName", String.class.getName());
		crt.addScalar("requestNumber", String.class.getName());
		crt.addScalar("traineeItemCode", String.class.getName());
		crt.addScalar("traineeItemCodeDesc", String.class.getName());
		crt.addScalar("traineeSection", String.class.getName());
		crt.addScalar("traineeIA", String.class.getName());
		crt.addScalar("traineeAgency", String.class.getName());
		crt.addScalar("traineeUnitCode", String.class.getName());
		crt.addScalar("traineeUnitDesc", String.class.getName());
		crt.addScalar("traineeAssnStart", String.class.getName());
		crt.addScalar("traineeAssnEnd", String.class.getName());
		crt.addScalar("traineeRedCard", String.class.getName());
		crt.addScalar("traineeCurrentTaskBook", String.class.getName());
		crt.addScalar("traineeIncidentTaskBook", String.class.getName());
		crt.addScalar("traineePriority", String.class.getName());
		crt.addScalar("traineePriorityProgram", String.class.getName());
		crt.addScalar("traineeHUName", String.class.getName());
		crt.addScalar("traineeHUCode", String.class.getName());
		crt.addScalar("traineeHUDesc", String.class.getName());
		crt.addScalar("traineeHUAddress", String.class.getName());
		crt.addScalar("traineeHUCity", String.class.getName());
		crt.addScalar("traineeHUState", String.class.getName());
		crt.addScalar("traineeHUZip", String.class.getName());
		crt.addScalar("traineeHUPhone", String.class.getName());
		crt.addScalar("traineeHUEmail", String.class.getName());
		crt.addScalar("traineeGoal1", String.class.getName());
		crt.addScalar("traineeGoal2", String.class.getName());
		crt.addScalar("traineeGoal3", String.class.getName());
		crt.addScalar("trainerName", String.class.getName());
		crt.addScalar("trainerRequestNumber", String.class.getName());
		crt.addScalar("trainerUnit", String.class.getName());
		crt.addScalar("trainerUnitDesc", String.class.getName());
		crt.addScalar("trainerItemCode", String.class.getName());
		crt.addScalar("trainerItemDesc", String.class.getName());
		crt.addScalar("trainerAddress", String.class.getName());
		crt.addScalar("trainerCity", String.class.getName());
		crt.addScalar("trainerState", String.class.getName());
		crt.addScalar("trainerZip", String.class.getName());
		crt.addScalar("trainerPhone", String.class.getName());
		crt.addScalar("trainerEmail", String.class.getName());
		crt.addScalar("trainerRecommend", String.class.getName());
		crt.addScalar("trainerPtbProgress", String.class.getName());
		crt.addScalar("incidentName", String.class.getName());
		crt.addScalar("incidentNumber", String.class.getName());
		crt.addScalar("incidentType", String.class.getName());
		crt.addScalar("incidentComplexity", String.class.getName());
		crt.addScalar("incidentAcres", String.class.getName());
		crt.addScalar("incidentFuelType", String.class.getName());
		crt.addScalar("tnspFullName", String.class.getName());
		crt.addScalar("tnspUnit", String.class.getName());
		crt.addScalar("tnspUnitDescription", String.class.getName());
		crt.addScalar("tnspAgency", String.class.getName());
		crt.addScalar("tnspPhone", String.class.getName());
		crt.addScalar("tnspEmail", String.class.getName());

		q.setResultTransformer(crt); 

		list = q.list();
		
		return list;
	}

	public Collection<Tnsp3ReportData> getTrainingSummaryReportData(IncidentTrainingSummaryReportFilter filter) throws PersistenceException {
		Collection<Tnsp3ReportData> list = new ArrayList<Tnsp3ReportData>();

		Tnsp3ReportData reportData = new Tnsp3ReportData();

		// get list of active tnsp's 
		String sql=TnspReportQuery.getTrainingSpecialistQuery(filter.getIncidentId(),filter.getIncidentGroupId());
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		CustomResultTransformer crt = new CustomResultTransformer(Tnsp3SubReportTnspData.class);
		crt.addScalar("tnspName",String.class.getName());
		crt.addScalar("tnspName",String.class.getName());
		crt.addScalar("tnspAgency",String.class.getName());
		crt.addScalar("tnspEmail",String.class.getName());
		crt.addScalar("tnspUnit",String.class.getName());
		q.setResultTransformer(crt); 
		Collection<Tnsp3SubReportTnspData> tnspList = new ArrayList<Tnsp3SubReportTnspData>();
		tnspList=q.list();
		if(CollectionUtility.hasValue(tnspList)){
			reportData.setSubReportTnspData(tnspList);
		}
	
		// get list of agency counts
		Collection<Tnsp3SubReportData> list3 = new ArrayList<Tnsp3SubReportData>();
		String sql2=TnspReportQuery.getTrainingSummaryQuery(filter);
		SQLQuery q2 = getHibernateSession().createSQLQuery(sql2);
		crt = new CustomResultTransformer(Tnsp3SubReportData.class);
		crt.addScalar("agency", String.class.getName());
		crt.addScalar("commandCount", String.class.getName());
		crt.addScalar("operationsCount", String.class.getName());
		crt.addScalar("plansCount", String.class.getName());
		crt.addScalar("logisticsCount", String.class.getName());
		crt.addScalar("financeCount", String.class.getName());
		crt.addScalar("externalCount", String.class.getName());
		
		q2.setResultTransformer(crt); 

		list3 = q2.list();
		if(CollectionUtility.hasValue(list3))
			reportData.setSubReportData(list3);

		// get list of trainee counts
		Collection<Tnsp3ReportData> list4 = new ArrayList<Tnsp3ReportData>();
		String sql3=TnspReportQuery.getTrainingSummaryQuery3(filter, super.isOracleDialect());
		SQLQuery q3 = getHibernateSession().createSQLQuery(sql3);
		crt = new CustomResultTransformer(Tnsp3ReportData.class);
		crt.addScalar("code1Count", String.class.getName());
		crt.addScalar("code2Count", String.class.getName());
		crt.addScalar("code3Count", String.class.getName());
		crt.addScalar("code4Count", String.class.getName());
		crt.addScalar("code5Count", String.class.getName());
		//crt.addScalar("trainingCount", String.class.getName());
		//crt.addScalar("incTrainingCount", String.class.getName());
		
		q3.setResultTransformer(crt); 

		list4 = q3.list();
		if(CollectionUtility.hasValue(list4)){
			for(Tnsp3ReportData d2 : list4){
				reportData.setCode1Count(d2.getCode1Count());
				reportData.setCode2Count(d2.getCode2Count());
				reportData.setCode3Count(d2.getCode3Count());
				reportData.setCode4Count(d2.getCode4Count());
				reportData.setCode5Count(d2.getCode5Count());
				break;
			}
		}

		reportData.setTrainingCount("0");
		reportData.setIncTrainingCount("0");
		
		String sql3Pt2=TnspReportQuery.getTrainingSummaryQuery3Pt2(filter, super.isOracleDialect());
		SQLQuery q3Pt2 = getHibernateSession().createSQLQuery(sql3Pt2);
		Object val1=q3Pt2.uniqueResult();
		if(null != val1)
			reportData.setTrainingCount(String.valueOf(val1));
		
		String sql3Pt3=TnspReportQuery.getTrainingSummaryQuery3Pt3(filter, super.isOracleDialect());
		SQLQuery q3Pt3 = getHibernateSession().createSQLQuery(sql3Pt3);
		Object val2=q3Pt3.uniqueResult();
		if(null != val2)
			reportData.setIncTrainingCount(String.valueOf(val2));
		
		
		// get list or priority program counts
		Collection<Tnsp3SubReportPPData> list5 = new ArrayList<Tnsp3SubReportPPData>();
		String sql4=TnspReportQuery.getTrainingSummaryQuery4(filter, isOracleDialect());
		SQLQuery q4 = getHibernateSession().createSQLQuery(sql4);
		crt = new CustomResultTransformer(Tnsp3SubReportPPData.class);
		crt.addScalar("resourceCount", String.class.getName());
		crt.addScalar("programName", String.class.getName());
		q4.setResultTransformer(crt); 

		list5 = q4.list();
		if(CollectionUtility.hasValue(list5)){
			reportData.setSubReportPPData(list5);
		}

		
		if(null != reportData)
			list.add(reportData);
		
		return list;
	}

	public Collection<TrainingSpecialistVo> getTrainingSpecialistList(Long incidentId, Long groupId, Long rtId) throws PersistenceException {
		Collection<TrainingSpecialistVo> list = new ArrayList<TrainingSpecialistVo>();
		String sql=TnspReportQuery.getTrainingSpecialist2Query(incidentId, groupId,rtId);
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		CustomResultTransformer crt = new CustomResultTransformer(TrainingSpecialistVo.class);
		crt.addScalar("id", Long.class.getName());
		crt.addScalar("tnspName", String.class.getName());
		crt.addScalar("tnspUnit", String.class.getName());
		crt.addScalar("tnspAgency", String.class.getName());
		crt.addScalar("tnspPhone", String.class.getName());
		crt.addScalar("tnspEmail", String.class.getName());

		q.setResultTransformer(crt); 

		list=q.list();
		
		return list;
	}

	public String getEarliestStartDate(Long incidentId, Long groupId) throws PersistenceException {
		String dte="";
		if(LongUtility.hasValue(incidentId)){
			String s="select to_char(incident_start_date,'MM/DD/YYYY') from isw_incident where id = " + incidentId+" ";
			SQLQuery q=getHibernateSession().createSQLQuery(s);
			Object rslt=q.uniqueResult();
			if(null != rslt){
				try{
					dte=TypeConverter.convertToString(rslt);
				}catch(Exception e){}
			}
		}
		
		if(LongUtility.hasValue(groupId)){
			String s="select to_char(min(incident_start_date),'MM/DD/YYYY') "+
					 "from isw_incident where id in (" + 
					 "  select incident_id from isw_incident_group_incident where incident_group_id = " + groupId+") ";
			SQLQuery q=getHibernateSession().createSQLQuery(s);
			Object rslt=q.uniqueResult();
			if(null != rslt){
				try{
					dte=TypeConverter.convertToString(rslt);
				}catch(Exception e){}
			}
		}
		
		return dte;
	}
	
	public Collection<Tnsp2ReportData> getTrainingAssnListReportData(TrainingAssignmentsListReportFilter filter) throws PersistenceException {
		Collection<Tnsp2ReportData> list = new ArrayList<Tnsp2ReportData>();
		Tnsp2ReportData reportData = new Tnsp2ReportData();
		
		// get list of active tnsp's 
		String sql=TnspReportQuery.getTrainingSpecialistQuery(filter.getIncidentId(),filter.getIncidentGroupId());
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		CustomResultTransformer crt = new CustomResultTransformer(Tnsp2SubReportTnspData.class);
		crt.addScalar("tnspName",String.class.getName());
		crt.addScalar("tnspName",String.class.getName());
		crt.addScalar("tnspAgency",String.class.getName());
		crt.addScalar("tnspEmail",String.class.getName());
		crt.addScalar("tnspUnit",String.class.getName());
		q.setResultTransformer(crt); 
		Collection<Tnsp2SubReportTnspData> tnspList = new ArrayList<Tnsp2SubReportTnspData>();
		tnspList=q.list();
		if(CollectionUtility.hasValue(tnspList)){
			reportData.setSubReportTnspData(tnspList);
		}

		sql=TnspReportQuery.getTrainingAssignmentList(filter, super.isOracleDialect());
		q = getHibernateSession().createSQLQuery(sql);
		crt = new CustomResultTransformer(Tnsp2SubReportData.class);
		crt.addScalar("traineeName",String.class.getName());
		crt.addScalar("traineeRequestNumber",String.class.getName());
		crt.addScalar("traineeItemCode",String.class.getName());
		crt.addScalar("section",String.class.getName());
		crt.addScalar("traineeAgencyCode",String.class.getName());
		crt.addScalar("traineeUnitCode",String.class.getName());
		crt.addScalar("traineeStart",String.class.getName());
		crt.addScalar("traineeEnd",String.class.getName());
		crt.addScalar("recommend",String.class.getName());
		crt.addScalar("ptb",String.class.getName());
		crt.addScalar("trainerName",String.class.getName());
		q.setResultTransformer(crt); 
		Collection<Tnsp2SubReportData> subReportList = new ArrayList<Tnsp2SubReportData>();
		subReportList=q.list();
		if(CollectionUtility.hasValue(subReportList)){
			reportData.setSubReportData(subReportList);
		}

		list.add(reportData);
		
		return list;
	}

	public Collection<TnspHUAvery5160ReportData> getHomeUnitContactLabelData(Collection<Long> huIds) throws PersistenceException {
		Collection<TnspHUAvery5160ReportData> list = new ArrayList<TnspHUAvery5160ReportData>();
		String sql=TnspReportQuery.getHomeUnitContactLabelQuery(huIds);
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(TnspHUAvery5160ReportData.class);
		crt.addScalar("labelName",String.class.getName());
		crt.addScalar("address1",String.class.getName());
		crt.addScalar("city",String.class.getName());
		crt.addScalar("state",String.class.getName());
		crt.addScalar("zip",String.class.getName());
		crt.addScalar("unitDescription",String.class.getName());
		q.setResultTransformer(crt); 

		list = q.list();
		
		return list;
	}
	
}
