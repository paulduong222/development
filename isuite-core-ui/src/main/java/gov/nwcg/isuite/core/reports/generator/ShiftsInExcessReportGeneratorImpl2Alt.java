package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.filter.impl.TimeReport2FilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.reports.ShiftsInExcessOfStandardHoursReport;
import gov.nwcg.isuite.core.reports.data.ShiftsInExcessOfStandardHoursReportData;
import gov.nwcg.isuite.core.reports.data.ShiftsInExcessOfStandardHoursSubReportData;
import gov.nwcg.isuite.core.reports.filter.ShiftsInExcessOfStandardHoursReportFilter;
import gov.nwcg.isuite.core.rules.ShiftsInExcessReportGeneratorRulesHandler;
import gov.nwcg.isuite.core.vo.TimeReportData2Vo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class ShiftsInExcessReportGeneratorImpl2Alt extends ReportGeneratorImpl {
	private Collection<Long> atpIdsIncluded = new ArrayList<Long>();
	private BigDecimal totalHoursWorked = new BigDecimal(0.0);
	private String shiftEndDate="";
	private HashMap<Long, Collection<TimeReportData2Vo>> resourceVoMap = new HashMap<Long, Collection<TimeReportData2Vo>>();
	
	@Override
	public <E extends TimeReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException {

		ShiftsInExcessOfStandardHoursReportFilter thisReportFilter = (ShiftsInExcessOfStandardHoursReportFilter) filter;
		ReportTimeDao reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		IncidentGroupDao igDao=(IncidentGroupDao)context.getBean("incidentGroupDao");
		IncidentDao incDao=(IncidentDao)context.getBean("incidentDao");

		try{
			ShiftsInExcessReportGeneratorRulesHandler rulesHandler = new ShiftsInExcessReportGeneratorRulesHandler(context);
			if (rulesHandler.execute(thisReportFilter, dialogueVo) == ShiftsInExcessReportGeneratorRulesHandler._OK) {

				// main report collection
				Collection<ShiftsInExcessOfStandardHoursReportData> reportDataList = new ArrayList<ShiftsInExcessOfStandardHoursReportData>();

				// prepare filter
				TimeReport2FilterImpl trFilter = new TimeReport2FilterImpl();
				trFilter.setReportIsShiftsInExcess(true);
				trFilter.setIncidentId(thisReportFilter.getIncidentId());
				trFilter.setIncidentGroupId(thisReportFilter.getIncidentGroupId());
				trFilter.setStartDate(filter.getFirstDateToIncludeOnReport());
				if(DateUtil.hasValue(filter.getLastDateToIncludeOnReport()))
					trFilter.setStopDate(filter.getLastDateToIncludeOnReport());
				else
					trFilter.setStopDate(new Date());

				trFilter.setIncidentResourceId(thisReportFilter.getIncidentResourceId());
				trFilter.setResourceId(thisReportFilter.getResourceId());
				trFilter.setExcludeDemob(thisReportFilter.isExcludeDemob());

				if(StringUtility.hasValue(thisReportFilter.getSortBy())){
					if(thisReportFilter.getSortBy().equalsIgnoreCase("REQUEST_NUMBER"))
						trFilter.setSortByRequestNumber(true);
					else if(thisReportFilter.getSortBy().equalsIgnoreCase("LAST_NAME"))
						trFilter.setSortByLastName(true);
					else if(thisReportFilter.getSortBy().equalsIgnoreCase("TOTAL"))
						trFilter.setSortByTotal(true);
				}

				// load incidentIds
				Collection<Long> incidentIds = new ArrayList<Long>();
				if(LongUtility.hasValue(thisReportFilter.getIncidentGroupId())){
					incidentIds=igDao.getIncidentIdsInGroup(thisReportFilter.getIncidentGroupId());
				}else{
					incidentIds.add(thisReportFilter.getIncidentId());
				}

				Boolean hasData=false;

				// for each incident load report data
				for(Long incidentId : incidentIds){

					trFilter.setIncidentId(incidentId);

					// init hashmap for this incident
					resourceVoMap = new HashMap<Long, Collection<TimeReportData2Vo>>();
					
					Collection<TimeReportData2Vo> vos = reportTimeDao.getTimeReportData(trFilter);

					// get list of unique resourceIds
					Collection<Long> resourceIds = this.getUniqueResourceIds(vos);

					// build hashmap
					buildHashMap(resourceIds,vos);

					// reset atpIds already examined
					this.atpIdsIncluded = new ArrayList<Long>();

					ShiftsInExcessOfStandardHoursReportData reportData = new ShiftsInExcessOfStandardHoursReportData();

					Collection<ShiftsInExcessOfStandardHoursSubReportData> subReportData = new ArrayList<ShiftsInExcessOfStandardHoursSubReportData>();

					Incident incidentEntity=incDao.getById(incidentId);
					if(null != incidentEntity){
						String incTag=incidentEntity.getHomeUnit().getUnitCode()+"-"+incidentEntity.getNbr();
						reportData.setIncidentTag(incTag);
						reportData.setIncidentName(incidentEntity.getIncidentName());
						incDao.flushAndEvict(incidentEntity.getHomeUnit());
						incDao.flushAndEvict(incidentEntity);
					}

					reportData.setIncidentId(incidentId);
					reportData.setFirstDateToInclude(trFilter.getStartDate());
					reportData.setLastDateToInclude(trFilter.getStopDate());
					reportData.setReportPrintedDate(new Date());
					reportData.setStandardHours(thisReportFilter.getStandardHours());


					for(Long resourceId : resourceIds){
						// get all timeposting records for this resourceId
						Collection<TimeReportData2Vo> resourceDataVos = new ArrayList<TimeReportData2Vo>();

						if(this.resourceVoMap.containsKey(resourceId)){
							resourceDataVos = (Collection<TimeReportData2Vo>)resourceVoMap.get(resourceId);
						}
						
						// get all shifts over the hours parameter
						for(TimeReportData2Vo vo : resourceDataVos){
							// reset totalhoursworked
							totalHoursWorked = new BigDecimal(0.0);

							// only go to next posting not already used in totals
							if(!atpIdsIncluded.contains(vo.getAtpId())){
								// calculate total hours for the shift
								this.totalShiftHours(vo.getAtpId(), resourceDataVos);

								if(totalHoursWorked.doubleValue() > thisReportFilter.getStandardHours()){
									ShiftsInExcessOfStandardHoursSubReportData data = new ShiftsInExcessOfStandardHoursSubReportData();

									data.setRequestNumber(vo.getRequestNumber());
									data.setResourceName(vo.getLastName() + ", " + vo.getFirstName());
									data.setShiftStartDate(vo.getAtpStartDate());
									if(StringUtility.hasValue(this.shiftEndDate)){
										Date dt=DateUtil.toDate(this.shiftEndDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
										data.setShiftEndDate(dt);
									}else{
										data.setShiftEndDate(vo.getAtpStartDate());
									}
									data.setTotalShiftHours(totalHoursWorked.doubleValue());

									BigDecimal difference = totalHoursWorked.subtract(new BigDecimal(thisReportFilter.getStandardHours()));
									data.setAmountExcess(difference.doubleValue());

									subReportData.add(data);
									//System.out.println("found one " + vo.getRequestNumber() + " " + vo.getAtpStartDate() + " " + totalHoursWorked.doubleValue());
								}
							}
						}
					}

					if(CollectionUtility.hasValue(subReportData)){
						hasData=true;
					}

					reportData.setSubReportData(subReportData);
					reportDataList.add(reportData);
				}
				
				if(CollectionUtility.hasValue(reportDataList) && hasData==true){
					// Create and send back the report.
					//List<String> reportsList = new ArrayList<String>();
					String pdfURL = null;

					try {

						IReport report = new ShiftsInExcessOfStandardHoursReport("", reportDataList);
						report.addCustomField("SUBREPORT_DIR", getSourcePath());

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
					return noDataMessage("Shifts in Excess of Standard Hours", dialogueVo);
				}
			}

		}catch(Exception e){
			super.handleException(e);
		}

		return dialogueVo;	
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
					}
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
}
