package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.filter.impl.TimeReport2FilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.reports.WorkRestRatioReport;
import gov.nwcg.isuite.core.reports.data.WorkRestRatioReportData;
import gov.nwcg.isuite.core.reports.data.WorkRestRatioSubReportData;
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
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class WorkRestRatioReportGeneratorImpl2Alt extends ReportGeneratorImpl {
	private Collection<Long> atpIdsIncluded = new ArrayList<Long>();
	private BigDecimal totalHoursWorked = new BigDecimal(0.0);
	private BigDecimal totalRest = new BigDecimal(0.0);
	private String shiftEndDate="";
	private Boolean shiftHasCompleted=false;
	private HashMap<Long, Collection<TimeReportData2Vo>> resourceVoMap = new HashMap<Long, Collection<TimeReportData2Vo>>();

	// Private static Strings that are expected by the JasperReport. 
	// Changing these string values will require a change in the JasperReport also.
	private static final String GROUPBYCLAUSE_NONE = "";
	private static final String GROUPBYCLAUSE_SECTION = "section";
	private static final String GROUPBYCLAUSE_DATE = "date";
	
	@Override
	public <E extends TimeReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException {
    	ReportTimeDao reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		IncidentGroupDao igDao=(IncidentGroupDao)context.getBean("incidentGroupDao");
		IncidentDao incDao=(IncidentDao)context.getBean("incidentDao");
		
		try {

        	if(!(filter instanceof WorkRestRatioReportFilter)){
    			throw new Exception("Invalid Filter Type. Expecting Filter to be of type WorkRestRatioReportFilter.");
    		}
        	
        	WorkRestRatioReportFilter wrrReportFilter = (WorkRestRatioReportFilter)filter;

        	// init main report data 
        	Collection<WorkRestRatioReportData> reportDataList = new ArrayList<WorkRestRatioReportData>();
        	
			// prepare filter
			TimeReport2FilterImpl trFilter = new TimeReport2FilterImpl();
			trFilter.setReportIsWorkRatio(true);
			trFilter.setIncidentId(wrrReportFilter.getIncidentId());
			trFilter.setIncidentGroupId(wrrReportFilter.getIncidentGroupId());
			trFilter.setStartDate(filter.getStartDate());
			if(DateUtil.hasValue(filter.getEndDate()))
				trFilter.setStopDate(filter.getEndDate());
			else
				trFilter.setStopDate(new Date());

			trFilter.setIncidentResourceId(wrrReportFilter.getIncidentResourceId());
			trFilter.setResourceId(wrrReportFilter.getResourceId());
			trFilter.setExcludeDemob(wrrReportFilter.isExcludeDemob());

			trFilter.setSectionIncludeAll(true);
			
			if(BooleanUtility.isTrue(wrrReportFilter.isGroupByNone())){
				trFilter.setSortByRequestNumber(true);
			}else if(BooleanUtility.isTrue(wrrReportFilter.isGroupBySection())){
				if(BooleanUtility.isTrue(wrrReportFilter.isSectionTypeAll())){
					trFilter.setSectionIncludeAll(true);
				}else{
					trFilter.setSectionIncludeAll(false);
					String sectionCriteria=this.getSectionCriteria(wrrReportFilter);
					trFilter.setSectionFilter(sectionCriteria);
				}
				
				if(BooleanUtility.isTrue(wrrReportFilter.isSectionSortByShiftStartDate())){
					trFilter.setSortByShiftStartDate(true);
				}else if(BooleanUtility.isTrue(wrrReportFilter.isSectionSortByRequestNumber())){
					trFilter.setSortByRequestNumber(true);
				}else if(BooleanUtility.isTrue(wrrReportFilter.isSectionSortByName())){
					trFilter.setSortByLastName(true);
				}
				
			}else if(BooleanUtility.isTrue(wrrReportFilter.isGroupByDate())){
				if(BooleanUtility.isTrue(wrrReportFilter.isSectionSortByRequestNumber())){
					trFilter.setSortByRequestNumber(true);
				}else if(BooleanUtility.isTrue(wrrReportFilter.isSectionSortByName())){
					trFilter.setSortByLastName(true);
				}
			}else{
				trFilter.setSortByRequestNumber(true);
			}
			
        	
			// load incidentIds
			Collection<Long> incidentIds = new ArrayList<Long>();
			if(LongUtility.hasValue(wrrReportFilter.getIncidentGroupId())){
				incidentIds=igDao.getIncidentIdsInGroup(wrrReportFilter.getIncidentGroupId());
			}else{
				incidentIds.add(wrrReportFilter.getIncidentId());
			}

			Boolean hasData=false;

			// for each incident load report data
			for(Long incidentId : incidentIds){

				// init hashmap for this incident
				resourceVoMap = new HashMap<Long, Collection<TimeReportData2Vo>>();
				
				trFilter.setIncidentId(incidentId);

				Collection<TimeReportData2Vo> vos = reportTimeDao.getTimeReportData(trFilter);

				// get list of unique resourceIds
				Collection<Long> resourceIds = this.getUniqueResourceIds(vos);

				// build hashmap
				buildHashMap(resourceIds,vos);
				
				// reset atpIds already examined
				this.atpIdsIncluded = new ArrayList<Long>();

				WorkRestRatioReportData reportData = new WorkRestRatioReportData();

				Collection<WorkRestRatioSubReportData> subReportDataList = new ArrayList<WorkRestRatioSubReportData>();

				reportData.setIncidentId(incidentId);
				
				Incident incidentEntity=incDao.getById(incidentId);
				if(null != incidentEntity){
					String incTag=incidentEntity.getHomeUnit().getUnitCode()+"-"+incidentEntity.getNbr();
					reportData.setIncidentTag(incTag);
					reportData.setIncidentName(incidentEntity.getIncidentName());
					incDao.flushAndEvict(incidentEntity.getHomeUnit());
					incDao.flushAndEvict(incidentEntity);
				}

				for(Long resourceId : resourceIds){
					// get all timeposting records for this resourceId
					Collection<TimeReportData2Vo> resourceDataVos = new ArrayList<TimeReportData2Vo>();

					if(this.resourceVoMap.containsKey(resourceId)){
						resourceDataVos = (Collection<TimeReportData2Vo>)resourceVoMap.get(resourceId);
					}

					if(resourceId.compareTo(12164L)==0){
						//System.out.println("");
					}

					totalHoursWorked = new BigDecimal(0.0);
					totalRest = new BigDecimal(0.0);
					shiftHasCompleted=false;
					
					// get all shifts over the hours parameter
					for(TimeReportData2Vo vo : resourceDataVos){
						// reset totalhoursworked, totalRest for this new shift
						shiftHasCompleted=false;
						
						// only go to next posting not already used in previous totals
						if(!atpIdsIncluded.contains(vo.getAtpId())){
							Date shiftStart=vo.getAtpStartDate();
							
							// calculate total hours for the shift
							this.totalShiftHours(vo.getAtpId(), resourceDataVos);

							if(shiftHasCompleted==true){
								BigDecimal totalRest_times_2 = totalRest.multiply(new BigDecimal(2.0));
								if(totalRest_times_2.doubleValue() < totalHoursWorked.doubleValue()){
									BigDecimal diff = totalHoursWorked.subtract(totalRest_times_2).divide(new BigDecimal(2.));

									WorkRestRatioSubReportData subReportData = new WorkRestRatioSubReportData();
									subReportData.setRequestNumber(vo.getRequestNumber()); 
									subReportData.setResourceName(vo.getLastName() + ", " + vo.getFirstName());
									subReportData.setItemCode(vo.getItemCode());
									subReportData.setStatus(vo.getAssignmentStatus());
									subReportData.setSection(vo.getSectionName());
									subReportData.setHoursOfWork(totalHoursWorked!=null?totalHoursWorked.doubleValue():0);
									subReportData.setHoursOfRest(totalRest!=null?totalRest.doubleValue():0);
									subReportData.setHoursOfMitigation(diff!=null?diff.doubleValue():0);
									subReportData.setShiftStartDate(DateUtil.getDateZeroTimeStamp(shiftStart));

									subReportDataList.add(subReportData);
								}
								
								totalHoursWorked = new BigDecimal(0.0);
								totalRest = new BigDecimal(0.0);
							}
						}
					}
				}

				if(CollectionUtility.hasValue(subReportDataList)){
					hasData=true;
				}

				if(BooleanUtility.isTrue(wrrReportFilter.isGroupByDate())
						&& BooleanUtility.isTrue(wrrReportFilter.isDateTypeDescending())){
					Collections.reverse((ArrayList<WorkRestRatioSubReportData>)subReportDataList);
				}
				reportData.setSubReportData(subReportDataList);
				reportDataList.add(reportData);
			}
			
			if(CollectionUtility.hasValue(reportDataList) && hasData==true){
				// Create and send back the report.
				//List<String> reportsList = new ArrayList<String>();
				String pdfURL = null;

				try {
					String groupByClause = getGroupByClause((WorkRestRatioReportFilter)filter);

					IReport report = new WorkRestRatioReport("", reportDataList);
					report.addCustomField("SUBREPORT_DIR", getSourcePath());
					report.addCustomField("startDate", filter.getStartDate());
					report.addCustomField("endDate", filter.getEndDate());
					report.addCustomField("groupByClause", groupByClause);

					//reportsList.add(createAndSaveReport(report));
					pdfURL = createAndSaveReport(report);
				} catch (Exception e) {
					super.handleException(e);
				}			

				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("REPORT COMPLETE");
				coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coa.setIsDialogueEnding(Boolean.TRUE);	  
				dialogueVo.setCourseOfActionVo(coa);
				dialogueVo.setResultObject(pdfURL);
			}else{
				return noDataMessage("Work/Rest Ratio Report.", dialogueVo);
			}
		} catch (Exception e) {
			super.handleException(e);
		} finally {
		}
		return dialogueVo;	
	}
    
    
    /**
     * Private method to return the appropriate group-by clause to the Jasper Report
     * @param filter
     * @return
     */
    private String getGroupByClause(WorkRestRatioReportFilter filter) {
		if(filter==null) return GROUPBYCLAUSE_NONE;
		
		if(filter.isGroupBySection())
			return GROUPBYCLAUSE_SECTION;
		else if(filter.isGroupByDate())
			return GROUPBYCLAUSE_DATE;
		else return GROUPBYCLAUSE_NONE;
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


	private void totalShiftHours(Long atpId, Collection<TimeReportData2Vo> vos){
		for(TimeReportData2Vo v : vos){
			if(atpId.compareTo(v.getAtpId())==0){
				totalHoursWorked=totalHoursWorked.add(v.getAtpQuantity());

				if(DecimalUtil.hasValue(v.getRestBeforeNextStart())){
					totalRest=totalRest.add(v.getRestBeforeNextStart());
				}
				
				this.shiftEndDate = DateUtil.toDateString(v.getAtpStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);

				// put in collection, so we do not read this record again
				this.atpIdsIncluded.add(v.getAtpId());

				if(DecimalUtil.hasValue(v.getRestBeforeNextStart())){
					if(v.getRestBeforeNextStart().doubleValue() <= 2.0){
						//if(v.getAtpQuantity().doubleValue() <= 2.0){
						if(LongUtility.hasValue(v.getAtpNextId()) 
								&& !this.atpIdsIncluded.contains(v.getAtpNextId())){
							this.totalShiftHours(v.getAtpNextId(), vos);
						}
					}else
						this.shiftHasCompleted=true;
				}else{
					if(LongUtility.hasValue(v.getAtpNextId()) 
							&& !this.atpIdsIncluded.contains(v.getAtpNextId())){
						this.totalShiftHours(v.getAtpNextId(), vos);
					}
				}
			}
		}

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
	
	private String getSectionCriteria(WorkRestRatioReportFilter f) {
		String criteria = "";
		Collection<String> sectionList = new ArrayList<String>();
		
		if(BooleanUtility.isTrue(f.isSectionTypeCommand()))
			sectionList.add("C");
		if(BooleanUtility.isTrue(f.isSectionTypeExternal()))
			sectionList.add("E");
		if(BooleanUtility.isTrue(f.isSectionTypeFinance()))
			sectionList.add("F");
		if(BooleanUtility.isTrue(f.isSectionTypeLogistics()))
			sectionList.add("L");
		if(BooleanUtility.isTrue(f.isSectionTypeOperations()))
			sectionList.add("O");
		if(BooleanUtility.isTrue(f.isSectionTypePlanning()))
			sectionList.add("P");

		for(String s : sectionList){
			if(StringUtility.hasValue(criteria))
				criteria=criteria+",'"+s+"'";
			else
				criteria="'"+s+"'";
		}
		
		return criteria;
	}
}
