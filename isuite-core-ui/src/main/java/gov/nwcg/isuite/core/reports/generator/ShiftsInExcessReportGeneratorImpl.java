package gov.nwcg.isuite.core.reports.generator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.reports.ShiftsInExcessOfStandardHoursReport;
import gov.nwcg.isuite.core.reports.data.ShiftsInExcessOfStandardHoursReportData;
import gov.nwcg.isuite.core.reports.data.ShiftsInExcessOfStandardHoursSubReportData;
import gov.nwcg.isuite.core.reports.filter.ShiftsInExcessOfStandardHoursReportFilter;
import gov.nwcg.isuite.core.rules.ShiftsInExcessReportGeneratorRulesHandler;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.types.TimeReportsSortByEnum;
import gov.nwcg.isuite.framework.util.CalendarUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class ShiftsInExcessReportGeneratorImpl extends ReportGeneratorImpl {

    @Override
	public <E extends TimeReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException,
			PersistenceException {
		try {
			// apply rules
			ShiftsInExcessReportGeneratorRulesHandler rulesHandler = new ShiftsInExcessReportGeneratorRulesHandler(context);
			if (rulesHandler.execute((ShiftsInExcessOfStandardHoursReportFilter) filter, dialogueVo) == ShiftsInExcessReportGeneratorRulesHandler._OK) {
				
				ReportTimeDao reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");
				Collection<ShiftsInExcessOfStandardHoursReportData> reportData;
				//TODO: Manu - Temporary commit of old Shifts In Excess Report code
				reportData = null;//reportTimeDao.getShiftsInExcessOfStandardHoursReportData((ShiftsInExcessOfStandardHoursReportFilter) filter);
				
				if(reportData == null || reportData.size() == 0) {
					return noDataMessage("Shifts in Excess of Standard Hours", dialogueVo);
				}
				
				reportData = timePostsOverStandard(reportData, ((ShiftsInExcessOfStandardHoursReportFilter) filter));
				
				if(reportData == null || reportData.size() == 0) {
					return noDataMessage("Shifts in Excess of Standard Hours", dialogueVo);
				}
				
				//List<String> reportsList = new ArrayList<String>();
				String pdfURL = null;
				try {
					IReport report = new ShiftsInExcessOfStandardHoursReport("", reportData);
					report.addCustomField("SUBREPORT_DIR", getSourcePath());

					//reportsList.add(createAndSaveReport(report));
					pdfURL = createAndSaveReport(report);
				} catch (Exception e) {
					super.handleException(e);
				}

				// add reportsList to dialogueVo
				//CourseOfActionVo coa = new CourseOfActionVo();
				//coa.setCoaName("GENERATE_SHIFTS_IN_EXCESS_OF_STANDARD_HOURS_REPORT");
				//coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				//coa.setIsDialogueEnding(true);
				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("REPORT COMPLETE");
				coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coa.setIsDialogueEnding(Boolean.TRUE);	  
				dialogueVo.setCourseOfActionVo(coa);
				dialogueVo.setResultObject(pdfURL);
			}
		} catch (Exception e) {
			super.handleException(e);
		}

		return dialogueVo;	
	}

    private Collection<ShiftsInExcessOfStandardHoursReportData> timePostsOverStandard(
			Collection<ShiftsInExcessOfStandardHoursReportData> reportData, 
			ShiftsInExcessOfStandardHoursReportFilter filter) {
    	
    	double standardHours = filter.getStandardHours();
		Collection<ShiftsInExcessOfStandardHoursReportData> remove = new ArrayList<ShiftsInExcessOfStandardHoursReportData>();

		for (ShiftsInExcessOfStandardHoursReportData rd : reportData) {
			ArrayList<ShiftsInExcessOfStandardHoursSubReportData> data = 
				(ArrayList<ShiftsInExcessOfStandardHoursSubReportData>) rd.getSubReportData();
			List<ShiftsInExcessOfStandardHoursSubReportData> overHours = new ArrayList<ShiftsInExcessOfStandardHoursSubReportData>();
			           
			for (int i = 0; i < data.size(); i++) {
				ShiftsInExcessOfStandardHoursSubReportData dt = data.get(i);
				double totalTime = getTotalShiftHours(dt);

				boolean cont = true;
				ShiftsInExcessOfStandardHoursSubReportData dt1 = dt;
				while (cont && (i + 1) < data.size()) {
					ShiftsInExcessOfStandardHoursSubReportData dt2 = data.get(i + 1);
					if (isContinuationofShift(dt1, dt2)) {
						totalTime += getTotalShiftHours(dt2);
						i++;
						dt1 = dt2;
					} else {
						cont = false;
					}
				}

				if (totalTime > standardHours) {
					dt.setTotalShiftHours(totalTime);
					dt.setAmountExcess(totalTime - standardHours);
					overHours.add(dt);
				}
			}
			
			// DO NOT DELETE THIS COMMENT:
			// Sort overHours by total hours if user requested so. 
			// Note that other sorting options, namely, sorting by request number and resource name
			// were done via the SQL Query when the data was retrieved from the database. Sorting by 
			// total hours was not done because totalHours is a calcuated value in Java and not 
			// retrieved from the database.
			if(filter.getSortBy().equals(TimeReportsSortByEnum.TOTAL.name())) {
				//Collections.sort(overHours, ShiftsInExcessOfStandardHoursSubReportData.TotalShiftHoursComparator);
			}
			
			if (overHours.size() > 0) {
				rd.setSubReportData(overHours);
			} else {
				remove.add(rd);
			}
		}
		for (ShiftsInExcessOfStandardHoursReportData rd : remove) {
			reportData.remove(rd);
		}
		return reportData;
	}

	private double getTotalShiftHours(ShiftsInExcessOfStandardHoursSubReportData dt) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(dt.getShiftStartDate());
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(dt.getShiftEndDate());

		double totalTime = CalendarUtil.getTimeBetweenInQuarterHours(cal1, cal2);

		return totalTime;
	}

	private boolean isContinuationofShift(ShiftsInExcessOfStandardHoursSubReportData dt1,
										  ShiftsInExcessOfStandardHoursSubReportData dt2) {
		
		if(StringUtility.hasValue(dt1.getRequestNumber())
				&& StringUtility.hasValue(dt2.getRequestNumber())){

			if(dt1.getRequestNumber().equals(dt2.getRequestNumber()) ) {
				if(dt1.getShiftEndDate().before(dt2.getShiftStartDate())) {
					double maxBreak = 1.0;
					try {
						maxBreak = Double.valueOf(this.getGlobalCacheVo().getSystemVo()
								.getParameter(SystemParameterTypeEnum.MAX_SHIFT_CONTINUATION_BREAK.name()));
					} catch (Exception e) {
						// use default of 2.0;
					}
					double totalTime = 0.0;
		
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(dt1.getShiftEndDate());
					Calendar cal2 = Calendar.getInstance();
					cal2.setTime(dt2.getShiftStartDate());
			
					totalTime = CalendarUtil.getTimeBetweenInQuarterHours(cal1, cal2);
			
					if (totalTime < maxBreak)
						return true;
				}
			}
			
		}
		return false;
	}
}
