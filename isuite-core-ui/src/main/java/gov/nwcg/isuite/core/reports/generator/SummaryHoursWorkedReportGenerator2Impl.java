package gov.nwcg.isuite.core.reports.generator;


import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.filter.impl.TimeReport2FilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.reports.SummaryHoursWorkedReport;
import gov.nwcg.isuite.core.reports.data.GroupDateData;
import gov.nwcg.isuite.core.reports.data.ResourceDateData;
import gov.nwcg.isuite.core.reports.data.SummaryOfHoursWorkedReportData;
import gov.nwcg.isuite.core.reports.filter.SummaryHoursWorkedReportFilter;
import gov.nwcg.isuite.core.reports.filter.WorkRestRatioReportFilter;
import gov.nwcg.isuite.core.vo.TimeReportData2Vo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class SummaryHoursWorkedReportGenerator2Impl extends ReportGeneratorImpl {
	
	private SummaryHoursWorkedReportFilter filter;
	private ReportTimeDao reportTimeDao;
	private List<ResourceDateData> scratchReportTimeData;
	private Collection<Long> atpIdsIncluded = new ArrayList<Long>();
	private HashMap<Long, Collection<TimeReportData2Vo>> resourceVoMap = new HashMap<Long, Collection<TimeReportData2Vo>>();
	
	@Override
	public <E extends TimeReportFilter> DialogueVo generateReport(E filterIn, DialogueVo dialogueVo) throws ServiceException, PersistenceException {
		try {	
			if (dialogueVo == null) dialogueVo = new DialogueVo();
			IncidentGroupDao igDao=(IncidentGroupDao)context.getBean("incidentGroupDao");
			IncidentDao incDao=(IncidentDao)context.getBean("incidentDao");
			
			if(!(filterIn instanceof SummaryHoursWorkedReportFilter)){
				throw new Exception("Invalid Filter Type. Expecting Filter to be of type SummaryHoursWorkedReportFilter.");
			}

			SummaryHoursWorkedReportFilter shwFilter = (SummaryHoursWorkedReportFilter) filterIn;
			
			// prepare filter
			TimeReport2FilterImpl trFilter = new TimeReport2FilterImpl();
			trFilter.setReportIsSummaryHours(true);
			trFilter.setIncidentId(shwFilter.getIncidentId());
			trFilter.setIncidentGroupId(shwFilter.getIncidentGroupId());
			trFilter.setStartDate(shwFilter.getStartDate());
			if(DateUtil.hasValue(shwFilter.getEndDate()))
				trFilter.setStopDate(shwFilter.getEndDate());
			else
				trFilter.setStopDate(new Date());
			trFilter.setIncidentResourceId(shwFilter.getIncidentResourceId());
			trFilter.setResourceId(shwFilter.getResourceId());
			trFilter.setExcludeDemob(shwFilter.isExcludeDemob());
			trFilter.setSectionIncludeAll(true);

			if(BooleanUtility.isTrue(shwFilter.isNonGroupBy())){
				trFilter.setSortByRequestNumber(true);
			}else if(BooleanUtility.isTrue(shwFilter.isSection())){
				//if(BooleanUtility.isTrue(wrrReportFilter.isSectionTypeAll())){
				if(shwFilter.getSections().size()==6){
					trFilter.setSectionIncludeAll(true);
				}else{
					trFilter.setSectionIncludeAll(false);
					String sectionCriteria=this.getSectionCriteria(shwFilter);
					trFilter.setSectionFilter(sectionCriteria);
				}

				if(BooleanUtility.isTrue(shwFilter.isSortByShifStartDate())){
					trFilter.setSortByShiftStartDate(true);
				}else if(BooleanUtility.isTrue(shwFilter.isSortByRequestNum())){
					trFilter.setSortByRequestNumber(true);
				}else if(BooleanUtility.isTrue(shwFilter.isSortByResourceName())){
					trFilter.setSortByLastName(true);
				}
				
			}else{
				trFilter.setSortByRequestNumber(true);
			}
			
			reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");
			reportTimeDao.setLoggingInterceptor(super.logger);
			
			// load incidentIds
			Collection<Long> incidentIds = new ArrayList<Long>();
			if(LongUtility.hasValue(shwFilter.getIncidentGroupId())){
				incidentIds=igDao.getIncidentIdsInGroup(shwFilter.getIncidentGroupId());
			}else{
				incidentIds.add(shwFilter.getIncidentId());
			}

			Boolean hasData=false;
			
			// the list of master report data source
			List<SummaryOfHoursWorkedReportData> shwReportDataList = new ArrayList<SummaryOfHoursWorkedReportData>();
			
			// for each incident load report data
			for(Long incidentId : incidentIds){

				// init hashmap for this incident
				resourceVoMap = new HashMap<Long, Collection<TimeReportData2Vo>>();
				
				trFilter.setIncidentId(incidentId);

				Collection<TimeReportData2Vo> vos = reportTimeDao.getTimeReportData(trFilter);

				if(CollectionUtility.hasValue(vos)){
					// get list of unique resourceIds
					Collection<Long> resourceIds = this.getUniqueResourceIds(vos);

					// build hashmap
					buildHashMap(resourceIds,vos);
					
					// reset atpIds already examined
					this.atpIdsIncluded = new ArrayList<Long>();

					SummaryOfHoursWorkedReportData shwReportData = new SummaryOfHoursWorkedReportData();
					
					shwReportData.setIncidentId(incidentId);
					
					Incident incidentEntity=incDao.getById(incidentId);
					if(null != incidentEntity){
						String incTag=incidentEntity.getHomeUnit().getUnitCode()+"-"+incidentEntity.getNbr();
						shwReportData.setIncidentNumber(incTag);
						shwReportData.setIncidentName(incidentEntity.getIncidentName());
						incDao.flushAndEvict(incidentEntity.getHomeUnit());
						incDao.flushAndEvict(incidentEntity);
					}
                	shwReportData.setStartDate(shwFilter.getStartDateString());
                	shwReportData.setEndDate(shwFilter.getEndDateString());

    				//sub report data source
    				List<GroupDateData> groupDateDataList = new ArrayList<GroupDateData>();
    				
    				//build sub report data by week
    				int counter = shwFilter.getNumOfWeeks();
    				for(int i=0; i < counter; i++) {
    					GroupDateData groupDateData = new GroupDateData();
    					
    					//set sub report column header  
    					groupDateData.setDays(shwFilter.getDaysByWeek(i),shwFilter.getEndDate());
    					
    					//build sub sub report data by incident and week
    					Collection<ResourceDateData> resourceDateDataList =new ArrayList<ResourceDateData>();
    					for(Long resourceId : resourceIds){
    						// get all timeposting records for this resourceId
    						Collection<TimeReportData2Vo> resourceDataVos = new ArrayList<TimeReportData2Vo>();

    						TimeReportData2Vo trdVoFirst=null;
    						if(this.resourceVoMap.containsKey(resourceId)){
    							resourceDataVos = (Collection<TimeReportData2Vo>)resourceVoMap.get(resourceId);
    							trdVoFirst=resourceDataVos.iterator().next();
    						}

    						ResourceDateData rdd = new ResourceDateData();
    						rdd.setIncidentId(incidentId);
    						rdd.setIncidentName(shwReportData.getIncidentName());
    						rdd.setIncidentNumber(shwReportData.getIncidentNumber());
    						rdd.setResourceid(resourceId);
    						rdd.setRequestNumber(trdVoFirst.getRequestNumber());
    						rdd.setSortedRequestNumber(trdVoFirst.getSortedRequestNumber());
    						rdd.setFirstName(trdVoFirst.getFirstName());
    						rdd.setLastName(trdVoFirst.getLastName());
    						rdd.setItemCode(trdVoFirst.getItemCode());
    						rdd.setStatus(trdVoFirst.getAssignmentStatus());
    						rdd.setSectionName(trdVoFirst.getSectionName());
    						rdd.setWeek(i);
    						rdd.setDate1(groupDateData.getDate1());
    						rdd.setDate2(groupDateData.getDate2());
    						rdd.setDate3(groupDateData.getDate3());
    						rdd.setDate4(groupDateData.getDate4());
    						rdd.setDate5(groupDateData.getDate5());
    						rdd.setDate6(groupDateData.getDate6());
    						rdd.setDate7(groupDateData.getDate7());
    						Date minDate=DateUtil.subtractDaysFromDate(getFirstDate(groupDateData), 1);
    						Date maxDate=DateUtil.addDaysToDate(getLastDate(groupDateData), 1);
    						
    						int groupDateInt=0;
    						
        					for(TimeReportData2Vo trdVo : resourceDataVos){
        						
        						if(trdVo.getAtpStartDate().after(minDate) && trdVo.getAtpStartDate().before(maxDate)){
        							groupDateInt=getGroupDateInt(groupDateData,trdVo.getAtpStartDate());
        							double hours=trdVo.getAtpQuantity().doubleValue();        		
        							switch(groupDateInt)
        							{
        								case 1:
        									rdd.setHoursWorkedDate1(hours+rdd.getHoursWorkedDate1());
        									break;
        								case 2:
        									rdd.setHoursWorkedDate2(hours+rdd.getHoursWorkedDate2());
        									break;
        								case 3:
        									rdd.setHoursWorkedDate3(hours+rdd.getHoursWorkedDate3());
        									break;
        								case 4:
        									rdd.setHoursWorkedDate4(hours+rdd.getHoursWorkedDate4());
        									break;
        								case 5:
        									rdd.setHoursWorkedDate5(hours+rdd.getHoursWorkedDate5());
        									break;
        								case 6:
        									rdd.setHoursWorkedDate6(hours+rdd.getHoursWorkedDate6());
        									break;
        								case 7:
        									rdd.setHoursWorkedDate7(hours+rdd.getHoursWorkedDate7());
        									break;
        							}
        						}
        					}
    						
        					rdd.setTotal(getWeekTotal(rdd));
    						rdd = resetUnusedHoursWorked(rdd);
        					resourceDateDataList.add(rdd);
    					}
    					
    					//getResourceDateDataByIncidentIdAndWeek(incidentId,i+1,vos);	                    
    				    groupDateData.setResourceDateDataList(resourceDateDataList);   
    				    groupDateDataList.add(groupDateData);
    				}
    				
    				shwReportData.setGroupDateDataList(groupDateDataList);  
    				shwReportDataList.add(shwReportData);
				}
				
			}
			
			if(!CollectionUtility.hasValue(shwReportDataList)){
				return noDataMessage("Summary Of Hours Worked Report.", dialogueVo);
			}
				
			IReport report = new SummaryHoursWorkedReport(shwReportDataList);
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
		
			//List<String> reportsList = new ArrayList<String>();
			//reportsList.add(createAndSaveReport(report));
			String pdfURL = null;
			pdfURL = createAndSaveReport(report);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("REPORT COMPLETE");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coa.setIsDialogueEnding(Boolean.TRUE);	      
			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setResultObject(pdfURL);				
			
		} catch (Exception e) {
			super.handleException(e);
	    } finally {
		}

		return dialogueVo;	
	}

	private ResourceDateData resetUnusedHoursWorked(ResourceDateData rdd){
		if(!StringUtility.hasValue(rdd.getDate1()))
			rdd.setHoursWorkedDate1(null);
		if(!StringUtility.hasValue(rdd.getDate2()))
			rdd.setHoursWorkedDate2(null);
		if(!StringUtility.hasValue(rdd.getDate3()))
			rdd.setHoursWorkedDate3(null);
		if(!StringUtility.hasValue(rdd.getDate4()))
			rdd.setHoursWorkedDate4(null);
		if(!StringUtility.hasValue(rdd.getDate5()))
			rdd.setHoursWorkedDate5(null);
		if(!StringUtility.hasValue(rdd.getDate6()))
			rdd.setHoursWorkedDate6(null);
		if(!StringUtility.hasValue(rdd.getDate7()))
			rdd.setHoursWorkedDate7(null);
		return rdd;
	}

	private double getWeekTotal(ResourceDateData rdd) throws Exception {
		double total = 0.0;
		total=total+rdd.getHoursWorkedDate1();
		total=total+rdd.getHoursWorkedDate2();
		total=total+rdd.getHoursWorkedDate3();
		total=total+rdd.getHoursWorkedDate4();
		total=total+rdd.getHoursWorkedDate5();
		total=total+rdd.getHoursWorkedDate6();
		total=total+rdd.getHoursWorkedDate7();
		return total;
	}
	
	private int getGroupDateInt(GroupDateData gdd, Date atpDate) throws Exception {
		if(StringUtility.hasValue(gdd.getDate1()) 
				&& DateUtil.isSameDate(atpDate, DateUtil.toDate(gdd.getDate1(), DateUtil.MM_SLASH_DD_SLASH_YYYY)))
			return 1;
		if(StringUtility.hasValue(gdd.getDate2()) 
				&& DateUtil.isSameDate(atpDate, DateUtil.toDate(gdd.getDate2(), DateUtil.MM_SLASH_DD_SLASH_YYYY)))
			return 2;
		if(StringUtility.hasValue(gdd.getDate3()) 
				&& DateUtil.isSameDate(atpDate, DateUtil.toDate(gdd.getDate3(), DateUtil.MM_SLASH_DD_SLASH_YYYY)))
			return 3;
		if(StringUtility.hasValue(gdd.getDate4()) 
				&& DateUtil.isSameDate(atpDate, DateUtil.toDate(gdd.getDate4(), DateUtil.MM_SLASH_DD_SLASH_YYYY)))
			return 4;
		if(StringUtility.hasValue(gdd.getDate5()) 
				&& DateUtil.isSameDate(atpDate, DateUtil.toDate(gdd.getDate5(), DateUtil.MM_SLASH_DD_SLASH_YYYY)))
			return 5;
		if(StringUtility.hasValue(gdd.getDate6()) 
				&& DateUtil.isSameDate(atpDate, DateUtil.toDate(gdd.getDate6(), DateUtil.MM_SLASH_DD_SLASH_YYYY)))
			return 6;
		if(StringUtility.hasValue(gdd.getDate7()) 
				&& DateUtil.isSameDate(atpDate, DateUtil.toDate(gdd.getDate7(), DateUtil.MM_SLASH_DD_SLASH_YYYY)))
			return 7;
		return 0;
	}
	
	private Collection<Long> getUniqueResourceIds(Collection<TimeReportData2Vo> allVos) {
		Collection<Long> resourceIds = new ArrayList<Long>();

		if(CollectionUtility.hasValue(allVos)){
			for(TimeReportData2Vo vo : allVos){
				if(!resourceIds.contains(vo.getResourceId())){
					resourceIds.add(vo.getResourceId());
				}
			}
		}

		return resourceIds;
	}

	private void buildHashMap(Collection<Long> resourceIds,Collection<TimeReportData2Vo> vos) {
		for(Long resourceId : resourceIds){
			
			Collection<TimeReportData2Vo> resourceVos = new ArrayList<TimeReportData2Vo>();
			
			for(TimeReportData2Vo vo : vos){
				
				if(resourceId.compareTo(vo.getResourceId())==0){
					resourceVos.add(vo);
				}
			}
			
			this.resourceVoMap.put(resourceId, resourceVos);
		}
	}
	
	private Date getFirstDate(GroupDateData gdd) throws Exception{
		return DateUtil.toDate(gdd.getDate1(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
	}
	
	private Date getLastDate(GroupDateData gdd) throws Exception{
		if(StringUtility.hasValue(gdd.getDate7()))
			return DateUtil.toDate(gdd.getDate7(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
		if(StringUtility.hasValue(gdd.getDate6()))
			return DateUtil.toDate(gdd.getDate6(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
		if(StringUtility.hasValue(gdd.getDate5()))
			return DateUtil.toDate(gdd.getDate5(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
		if(StringUtility.hasValue(gdd.getDate4()))
			return DateUtil.toDate(gdd.getDate4(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
		if(StringUtility.hasValue(gdd.getDate3()))
			return DateUtil.toDate(gdd.getDate3(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
		if(StringUtility.hasValue(gdd.getDate2()))
			return DateUtil.toDate(gdd.getDate2(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
			
		return DateUtil.toDate(gdd.getDate1(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
	}
	
	private String getSectionCriteria(SummaryHoursWorkedReportFilter f) {
		String criteria = "";
		Collection<String> sectionList = new ArrayList<String>();

		for(String sct : f.getSections()){
			if(StringUtility.hasValue(criteria))
				criteria=criteria+",'"+sct.toUpperCase()+"'";
			else
				criteria="'"+sct.toUpperCase()+"'";
		}
		
		return criteria;
	}
	
}