package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.persistence.QuickStatsDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.QuickStatsQuery;
import gov.nwcg.isuite.core.vo.QuickStatsResourceTypeVo;
import gov.nwcg.isuite.core.vo.QuickStatsResourceVo;
import gov.nwcg.isuite.core.vo.QuickStatsTotalsVo;
import gov.nwcg.isuite.core.vo.QuickStatsVo;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.QuickStatsKindCodesEnum;
import gov.nwcg.isuite.framework.types.QuickStatsNamedParameterEnum;
import gov.nwcg.isuite.framework.types.QuickStatsSit209CodesEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.SQLQuery;

public class QuickStatsDaoHibernate extends TransactionSupportImpl implements QuickStatsDao {

   private QuickStatsVo vo = new QuickStatsVo();
   
	public QuickStatsDaoHibernate() {
	   super();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.QuickStatsDao#getQuickStats(java.lang.Long, java.lang.Long)
	 */
	public QuickStatsVo getQuickStats(Long incidentId, Long incidentGroupId) throws PersistenceException {
		this.vo = new QuickStatsVo();
		
		this.populateQuickStatsResourceVos(incidentId, incidentGroupId);
		this.populateQuickStatsResourceTypeVos(incidentId, incidentGroupId);
		this.calculateQuickStatsTotals(incidentId, incidentGroupId);

		this.vo.setIncidentId(incidentId);
		this.vo.setIncidentGroupId(incidentGroupId);
		
		return this.vo;
	}
	
	@SuppressWarnings({"unchecked", "serial"})
	private void populateQuickStatsResourceVos(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Collection<QuickStatsResourceVo> qsrVos = new ArrayList<QuickStatsResourceVo>();
		ArrayList<String> sit209CodesList = new ArrayList<String>();
		ArrayList<String> kindCodesList = new ArrayList<String>();

		for(QuickStatsSit209CodesEnum e : QuickStatsSit209CodesEnum.values()) {
			sit209CodesList.add(e.name());
		}

		for(QuickStatsKindCodesEnum e : QuickStatsKindCodesEnum.values()) {
			kindCodesList.add(e.name());
		}

		String sql = QuickStatsQuery.getQuickStatsQuery(incidentId, incidentGroupId);
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sql);
		sqlQuery.setParameterList(QuickStatsNamedParameterEnum.sit209codes.name(), sit209CodesList);
		sqlQuery.setParameterList(QuickStatsNamedParameterEnum.kindcodeslist.name(), kindCodesList);
		sqlQuery.setParameterList(QuickStatsNamedParameterEnum.checkedinorpending.name(), 
				new ArrayList<String>()
				{
					{
						add(AssignmentStatusTypeEnum.C.name());
						add(AssignmentStatusTypeEnum.P.name());
					}
				});
		CustomResultTransformer crt = new CustomResultTransformer(QuickStatsResourceVo.class);
		sqlQuery.setResultTransformer(crt);
		
		crt.addScalar("quantity", Integer.class.getName());
		
		qsrVos = sqlQuery.list();

		this.vo.addQuickStatResourceVos(qsrVos);
	}
	
	@SuppressWarnings("unchecked")
	private void populateQuickStatsResourceTypeVos(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Collection<QuickStatsResourceTypeVo> qsrTypeVos = new ArrayList<QuickStatsResourceTypeVo>();
		
		String sql = QuickStatsQuery.getQuickStatsTotalByType(incidentId, incidentGroupId);
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sql);

		CustomResultTransformer crt = new CustomResultTransformer(QuickStatsResourceTypeVo.class);
		sqlQuery.setResultTransformer(crt);
		
		crt.addScalar("count", Integer.class.getName());
		
		qsrTypeVos = sqlQuery.list();

		this.vo.addQuickStatsResourceTypeVos(qsrTypeVos);
	}

	@SuppressWarnings("serial")
	private void calculateQuickStatsTotals(Long incidentId, Long incidentGroupId) throws PersistenceException {
		QuickStatsTotalsVo qsTotalsVo = new QuickStatsTotalsVo();

		String sql = QuickStatsQuery.getQuickStatsTotalsQuery(incidentId, incidentGroupId);
		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sql);
		//sqlQuery.setLong(QuickStatsNamedParameterEnum.incidentid.name(), incidentId);
		sqlQuery.setParameterList(QuickStatsNamedParameterEnum.filled.name(), 
				new ArrayList<String>()
				{
					{
						add(AssignmentStatusTypeEnum.F.name());
					}
				});
		sqlQuery.setParameterList(QuickStatsNamedParameterEnum.checkedinorpending.name(), 
				new ArrayList<String>()
				{
					{
						add(AssignmentStatusTypeEnum.C.name());
						add(AssignmentStatusTypeEnum.P.name());
					}
				});
		sqlQuery.setParameterList(QuickStatsNamedParameterEnum.releasedresources.name(), 
				new ArrayList<String>()
				{
					{
						add(AssignmentStatusTypeEnum.D.name());
						add(AssignmentStatusTypeEnum.R.name());
					}
				});

		CustomResultTransformer crt = new CustomResultTransformer(QuickStatsTotalsVo.class);
		sqlQuery.setResultTransformer(crt);

		crt.addScalar("incidentId", Long.class.getName());
		
		crt.addScalar("filledResourceCount", Integer.class.getName());
		crt.addScalar("checkedInResourceCount", Integer.class.getName());
		crt.addScalar("releasedResourceCount", Integer.class.getName());
		crt.addScalar("totalOrderCount", Integer.class.getName());
		crt.addScalar("numberOfPersonnelCount", Integer.class.getName());

		List<QuickStatsTotalsVo> results = sqlQuery.list();
		
		if(CollectionUtility.hasValue(results)){
			for(QuickStatsTotalsVo v : results){
				/*
				this.vo.getQsTotalsVo().setFilledResourceCount(this.vo.getQsTotalsVo().getFilledResourceCount() + v.getFilledResourceCount());
				this.vo.getQsTotalsVo().setCheckedInResourceCount(this.vo.getQsTotalsVo().getCheckedInResourceCount() + v.getCheckedInResourceCount());
				this.vo.getQsTotalsVo().setReleasedResourceCount(this.vo.getQsTotalsVo().getReleasedResourceCount() + v.getReleasedResourceCount());
				this.vo.getQsTotalsVo().setTotalOrderCount(this.vo.getQsTotalsVo().getTotalOrderCount() + v.getTotalOrderCount());
				this.vo.getQsTotalsVo().setNumberOfPersonnelCount(this.vo.getQsTotalsVo().getNumberOfPersonnelCount() + v.getNumberOfPersonnelCount());
				*/
				this.vo.getQsTotalsVo().setFilledResourceCount(v.getFilledResourceCount());
				this.vo.getQsTotalsVo().setCheckedInResourceCount(v.getCheckedInResourceCount());
				this.vo.getQsTotalsVo().setReleasedResourceCount(v.getReleasedResourceCount());
				this.vo.getQsTotalsVo().setTotalOrderCount(v.getTotalOrderCount());
				this.vo.getQsTotalsVo().setNumberOfPersonnelCount(v.getNumberOfPersonnelCount());
			}
		}else{
			this.vo.getQsTotalsVo().setFilledResourceCount(new Integer(0));
			this.vo.getQsTotalsVo().setCheckedInResourceCount(new Integer(0));
			this.vo.getQsTotalsVo().setReleasedResourceCount(new Integer(0));
			this.vo.getQsTotalsVo().setTotalOrderCount(new Integer(0));
			this.vo.getQsTotalsVo().setNumberOfPersonnelCount(new Integer(0));
		}
		
		/*
		qsTotalsVo = (QuickStatsTotalsVo)sqlQuery.uniqueResult();

		if(qsTotalsVo != null) {
			this.vo.getQsTotalsVo().setFilledResourceCount(this.vo.getQsTotalsVo().getFilledResourceCount() + qsTotalsVo.getFilledResourceCount());
			this.vo.getQsTotalsVo().setCheckedInResourceCount(this.vo.getQsTotalsVo().getCheckedInResourceCount() + qsTotalsVo.getCheckedInResourceCount());
			this.vo.getQsTotalsVo().setReleasedResourceCount(this.vo.getQsTotalsVo().getReleasedResourceCount() + qsTotalsVo.getReleasedResourceCount());
			this.vo.getQsTotalsVo().setTotalOrderCount(this.vo.getQsTotalsVo().getTotalOrderCount() + qsTotalsVo.getTotalOrderCount());
			this.vo.getQsTotalsVo().setNumberOfPersonnelCount(this.vo.getQsTotalsVo().getNumberOfPersonnelCount() + qsTotalsVo.getNumberOfPersonnelCount());
		}
		*/
	}
	
}
