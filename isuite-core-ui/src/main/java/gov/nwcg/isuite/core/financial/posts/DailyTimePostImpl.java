package gov.nwcg.isuite.core.financial.posts;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.domain.impl.TimeAssignAdjustImpl;
import gov.nwcg.isuite.core.persistence.AssignmentTimeDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimeInvoiceDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeAdustDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimePostDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.financial.posts.DailyTimePost;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DailyTimePostImpl implements ApplicationContextAware, DailyTimePost {
  protected ApplicationContext context;
  
	/*
	 * (non-Javadoc) @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
  	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.context = ctx;
	}
  
  	@Override
	public Collection<IncidentResourceVo> getTimePosts(Long incidentResourceId, Date startDate)
			throws ServiceException {

		IncidentResourceDao ird = (IncidentResourceDao) context.getBean("incidentResourceDao");
		Collection<IncidentResourceVo> incidentResources = null;

		try {
			incidentResources = ird.getNonInvoicedIncidentResourcesById(incidentResourceId, startDate);
		} catch (PersistenceException e) {
			throw new ServiceException(e);
		}

		return incidentResources;
	}
  
	public void markInvoiced(Date lastIncludeDate,IncidentResourceVo irVo, TimeInvoice timeInvoice, Boolean invoice) throws PersistenceException {

		TimeInvoiceDao tid = (TimeInvoiceDao) context.getBean("timeInvoiceDao");

		if (invoice) {
				for (AssignmentVo a : irVo.getWorkPeriodVo().getAssignmentVos()) {
					for (AssignmentTimePostVo atp : a.getAssignmentTimeVo().getAssignmentTimePostVos()) {
						try{
							String tpDate=DateUtil.toDateString(atp.getPostStartDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
							Date dtePost=DateUtil.toDate(tpDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
							
							String fiString=DateUtil.toDateString(timeInvoice.getFirstIncludeDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
							Date fiDate=DateUtil.toDate(fiString, DateUtil.MM_SLASH_DD_SLASH_YYYY);
							
							// only mark ones that were used
							if((dtePost.compareTo(fiDate) >= 0)  && (!dtePost.after(lastIncludeDate))) {
								if (timeInvoice.getAssignmentTimePosts() == null) {
									timeInvoice.setAssignmentTimePosts(new ArrayList<AssignmentTimePost>());
								}
								AssignmentTimePost atp2 = new AssignmentTimePostImpl();
								atp2.setId(atp.getId());
								timeInvoice.getAssignmentTimePosts().add(atp2);
							}
						}catch(Exception e){}
					}
					
					for (TimeAssignAdjustVo adj : a.getTimeAssignAdjustVos()) {
						try{
							String adjDate="";
							if(DateTransferVo.hasDateString(adj.getActivityDateVo())){
								adjDate=adj.getActivityDateVo().getDateString();
							}
							//String adjDate=DateUtil.toDateString(adj.getActivityDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
							Date dteAdj=DateUtil.toDate(adjDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);

							// only mark ones that were used
							if(!dteAdj.after(lastIncludeDate)) {
								if (timeInvoice.getTimeAssignmentAdjusts() == null) {
									timeInvoice.setTimeAssignmentAdjusts(new ArrayList<TimeAssignAdjust>());
								}
								TimeAssignAdjust adj2 = new TimeAssignAdjustImpl();
								adj2.setId(adj.getId());
								timeInvoice.getTimeAssignmentAdjusts().add(adj2);
							}
						}catch(Exception e){}
					}
				}
			tid.save(timeInvoice);
		}
	}
  
	public void markInvoiced2(Date lastIncludeDate
								,IncidentResourceTimeDataVo irTimeDataVo
								,Collection<IncidentResourceTimePostDataVo> irTimePostDataVos
								,Collection<IncidentResourceTimeAdustDataVo> timeAdjustDataVos
								, TimeInvoice timeInvoice
								, Boolean invoice) throws PersistenceException {

		TimeInvoiceDao tid = (TimeInvoiceDao) context.getBean("timeInvoiceDao");
		TimePostDao tpDao2 = (TimePostDao)context.getBean("timePostDao");

		if (invoice) {
			for(IncidentResourceTimePostDataVo v : irTimePostDataVos){
				try{
					String tpDate=DateUtil.toDateString(v.getPostStartDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
					Date dtePost=DateUtil.toDate(tpDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
					
					String fiString=DateUtil.toDateString(timeInvoice.getFirstIncludeDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
					Date fiDate=DateUtil.toDate(fiString, DateUtil.MM_SLASH_DD_SLASH_YYYY);
					
					// only mark ones that were used
					if((dtePost.compareTo(fiDate) >= 0)  && (!dtePost.after(lastIncludeDate))) {
						if (timeInvoice.getAssignmentTimePosts() == null) {
							timeInvoice.setAssignmentTimePosts(new ArrayList<AssignmentTimePost>());
						}
						AssignmentTimePost atp2 = new AssignmentTimePostImpl();
						atp2.setId(v.getAssignTimePostId());
						atp2.setInvoicedAmount(v.getInvoicedAmount());
						timeInvoice.getAssignmentTimePosts().add(atp2);
					}
				}catch(Exception e){}
			}
			
			for(IncidentResourceTimeAdustDataVo a : timeAdjustDataVos){
				try{
					Date dteAdj=a.getActivityDate();
					
					// only mark ones that were used
					if(!dteAdj.after(lastIncludeDate)) {
						if (timeInvoice.getTimeAssignmentAdjusts() == null) {
							timeInvoice.setTimeAssignmentAdjusts(new ArrayList<TimeAssignAdjust>());
						}
						TimeAssignAdjust adj2 = new TimeAssignAdjustImpl();
						adj2.setId(a.getTimeAssignAdjustId());
						timeInvoice.getTimeAssignmentAdjusts().add(adj2);
					}
				}catch(Exception e){}
			}
					
			for(AssignmentTimePost vo2 : timeInvoice.getAssignmentTimePosts()){
				tpDao2.saveInvoicedAmount(vo2);
			}
			tid.save(timeInvoice);
		}
	}

	public void saveInvoicedAmounts(IncidentResourceVo irVo, TimeReportFilter filter) throws ServiceException, PersistenceException {
		AssignmentTimeDao atd = (AssignmentTimeDao) context.getBean("assignmentTimeDao");
		
		AssignmentTime at;
		Collection<AssignmentVo> avs;
		
		avs = irVo.getWorkPeriodVo().getAssignmentVos();
		for(AssignmentVo av : avs) {
			at = atd.getById(av.getAssignmentTimeVo().getId(), AssignmentTimeImpl.class);
			for(AssignmentTimePostVo atpv : av.getAssignmentTimeVo().getAssignmentTimePostVos()) {
				for(AssignmentTimePost atp : at.getAssignmentTimePosts()) {
					if(atpv.getId() == atp.getId()) {
						//if((atp.getReturnTravelStartOnly() != null && !atp.getReturnTravelStartOnly())  
						//		|| (atp.getReturnTravelStartOnly() == null && atp.getSpecialRateAssignmentTimePost() != null && !atp.getSpecialRateAssignmentTimePost().getReturnTravelStartOnly()))
							atp.setInvoicedAmount(atpv.getInvoicedAmount());
					}
				}
			}
		}
	}
	
	public void saveInvoicedAmounts2(IncidentResourceTimeDataVo irTimeDataVo, TimeReportFilter filter) throws ServiceException, PersistenceException {
		AssignmentTimeDao atd = (AssignmentTimeDao) context.getBean("assignmentTimeDao");
		
		AssignmentTime at;
		Collection<AssignmentVo> avs;
		/*
		avs = irVo.getWorkPeriodVo().getAssignmentVos();
		for(AssignmentVo av : avs) {
			at = atd.getById(av.getAssignmentTimeVo().getId(), AssignmentTimeImpl.class);
			for(AssignmentTimePostVo atpv : av.getAssignmentTimeVo().getAssignmentTimePostVos()) {
				for(AssignmentTimePost atp : at.getAssignmentTimePosts()) {
					if(atpv.getId() == atp.getId()) {
						//if((atp.getReturnTravelStartOnly() != null && !atp.getReturnTravelStartOnly())  
						//		|| (atp.getReturnTravelStartOnly() == null && atp.getSpecialRateAssignmentTimePost() != null && !atp.getSpecialRateAssignmentTimePost().getReturnTravelStartOnly()))
							atp.setInvoicedAmount(atpv.getInvoicedAmount());
					}
				}
			}
		}
		*/
	}
}
