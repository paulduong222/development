package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GlidePathReportResourceData {
	
	private static final String RESOURCE_NAME_EMPTY_SECTION = "NO_RESOURCE_FOUND";
	private static final String RESOURCE_NAME_TOTAL_ROW = "TOTAL";
	private static final String REQUEST_NUMBER_MARKER = "MARKER_ROW";
	
	private String subSectionName = "";
	private String requestNumber = "";
	private String resourceName = "";
	private String itemCode = "";
	private Date firstWorkingDate = null;
	private Integer lengthOfAssignment = null;
	private Date demobDate = null;
	private List<GlidePathReportResourceDayData> resourceDayData = new ArrayList<GlidePathReportResourceDayData>();
	
	public static GlidePathReportResourceData getSectionSheetEmptyPlaceholder(String subSectionName, int numberOfDays) {
		GlidePathReportResourceData sectionSheetEmptyPlaceholder = new GlidePathReportResourceData();
		sectionSheetEmptyPlaceholder.setSubSectionName(subSectionName);
		sectionSheetEmptyPlaceholder.setRequestNumber(REQUEST_NUMBER_MARKER);
		sectionSheetEmptyPlaceholder.setResourceName(RESOURCE_NAME_EMPTY_SECTION);
		sectionSheetEmptyPlaceholder.resourceDayData = GlidePathReportResourceDayData.getEmptyDaysList(numberOfDays);
		return sectionSheetEmptyPlaceholder;
	}
	
	// Returns a resource data record that will be used as the totals record.
	// This object should be the last addition to the ArrayList that denotes 
	// the resource data objects of a particular section/worksheet.
	public static GlidePathReportResourceData generateResourceDataTotalsRecord(String subSectionName, int numberOfDays){
		GlidePathReportResourceData totalsRecord = new GlidePathReportResourceData();
//		totalsRecord.setSubSectionName(subSectionName);
		totalsRecord.setRequestNumber(REQUEST_NUMBER_MARKER);
		totalsRecord.setResourceName(RESOURCE_NAME_TOTAL_ROW);
		totalsRecord.resourceDayData = GlidePathReportResourceDayData.generateTotalsRow(numberOfDays);
		return totalsRecord;
	}
	
	public void populateDayLabels(Date reportStartDate, Date reportEndDate, int numberOfDays) {
		GlidePathReportResourceDayData[] dayArray = new GlidePathReportResourceDayData[numberOfDays];
		for (int i=0; i<dayArray.length; i++) {
			dayArray[i] = GlidePathReportResourceDayData.getEmptyCell();
		}
		
		// Dont do anything if demob date is before the report start date OR the first working date is AFTER the report end date.
		// This case shouldn't arise because the SQL query used to generate the resource records should only get those resources
		// that correctly fall between the report start and end dates. 
		if(this.demobDate.getTime() >= reportStartDate.getTime() || this.firstWorkingDate.getTime() <= reportEndDate.getTime()) {
			
			// Both these values can be more than the length of the array. The represent where on the array the demob and firstworking date lie.
			int arrayIndexOfDemobDate = DateUtil.daysBetween(reportStartDate, this.demobDate);
			int arrayIndexOfFirstWorkingDate = DateUtil.daysBetween(reportStartDate, this.firstWorkingDate);

			int colorIndex = 0;
			
			for(int index = arrayIndexOfDemobDate; index>=0; index--){
				if(index < arrayIndexOfFirstWorkingDate) { // First working date is somewhere "in" the window. Don't need to color anymore. 
					break;
				}
				if(index < dayArray.length && index >= 0){
					dayArray[index] = GlidePathReportResourceDayData.getCellForColorIndex(colorIndex);
				}
				colorIndex++;
			}
		}
		
		// Convert array to arraylist
		this.resourceDayData = new ArrayList<GlidePathReportResourceDayData>(Arrays.asList(dayArray));
	}
	
	public String getSubSectionName() {
		return subSectionName;
	}

	public void setSubSectionName(String subSectionName) {
		this.subSectionName = subSectionName;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Date getFirstWorkingDate() {
		return firstWorkingDate;
	}

	public void setFirstWorkingDate(Date firstWorkingDate) {
		this.firstWorkingDate = firstWorkingDate;
	}

	public Integer getLengthOfAssignment() {
		return lengthOfAssignment;
	}

	public void setLengthOfAssignment(Integer lengthOfAssignment) {
		this.lengthOfAssignment = lengthOfAssignment;
	}

	public Date getDemobDate() {
		return demobDate;
	}

	public void setDemobDate(Date demobDate) {
		this.demobDate = demobDate;
	}
	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public List<GlidePathReportResourceDayData> getResourceDayData() {
		return resourceDayData;
	}

	public void setResourceDayData(List<GlidePathReportResourceDayData> resourceDayData) {
		this.resourceDayData = resourceDayData;
	}

	public JRBeanCollectionDataSource getResourceDayDataSource() {
		return new JRBeanCollectionDataSource(this.getResourceDayData());
	}
}
