package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.List;

public class GlidePathReportResourceDayData {

	public static final String DAYCELL_TEXT_BLACK = "DMB";
	public static final String DAYCELL_TEXT_REDLWD = "LWD";
	public static final String DAYCELL_TEXT_RED = "___";
	public static final String DAYCELL_TEXT_YELLOW = "__";
	public static final String DAYCELL_TEXT_GREEN = "_";
	public static final String DAYCELL_TEXT_EMPTY = null;
		
	private static GlidePathReportResourceDayData BLACK;
	private static GlidePathReportResourceDayData REDLWD;
	private static GlidePathReportResourceDayData RED;
	private static GlidePathReportResourceDayData YELLOW;
	private static GlidePathReportResourceDayData GREEN;
	private static GlidePathReportResourceDayData EMPTY; // Required for padding so that a complete grid can be shown in excel.
	
	static {
		BLACK = generateCell(DAYCELL_TEXT_BLACK);
		REDLWD = generateCell(DAYCELL_TEXT_REDLWD);
		RED = generateCell(DAYCELL_TEXT_RED);
		YELLOW = generateCell(DAYCELL_TEXT_YELLOW);
		GREEN = generateCell(DAYCELL_TEXT_GREEN);
		EMPTY = generateCell(DAYCELL_TEXT_EMPTY);
	}
	
	/**
	 * Public static method to return appropriate cell from the end of the resource's row. Note that if the index
	 * is above a threshold, the value will always be green. The caller should only call this method based on the
	 * resource's starting day and the report's first day. 
	 * @param index
	 * @return a report cell of GlidePathReportResourceDayData type
	 */
	public static GlidePathReportResourceDayData getCellForColorIndex(int index){
		if(index==0) return BLACK;
		if(index==1) return REDLWD;
		if(index==2 || index==3) return RED;
		if(index>=4 && index<=7) return YELLOW;
		return GREEN;
	}
	
	private static GlidePathReportResourceDayData generateCell(String cellTextMarker) {
		GlidePathReportResourceDayData cell = new GlidePathReportResourceDayData();
		cell.text = cellTextMarker;
		return cell;
	}
	
	public static GlidePathReportResourceDayData generateCellForTotalsRow(int totalCellValue) {
		GlidePathReportResourceDayData cell = new GlidePathReportResourceDayData();
		cell.text = Integer.toString(totalCellValue);
		return cell;
	}
	
	public static GlidePathReportResourceDayData generateCellForTotalsRow() {
		return GlidePathReportResourceDayData.generateCellForTotalsRow(0);
	}
	
	public static List<GlidePathReportResourceDayData> generateTotalsRow(int numberOfDays) {
		List<GlidePathReportResourceDayData> totals = new ArrayList<GlidePathReportResourceDayData>();
		for(int i=0;i<numberOfDays;i++){
			totals.add(generateCellForTotalsRow());
		}
		return totals;
	}
	
	/**
	 * Static method that is called once for each resource in a SUB-section; The first parameter, totalsRecord, 
	 * is updated to reflect a running total for the caller.
	 */
	public static void addToTotals(GlidePathReportResourceData totalsRecord, GlidePathReportResourceData singleDayRecord, int numberOfDays){
		
		List<GlidePathReportResourceDayData> totals = totalsRecord.getResourceDayData();
		List<GlidePathReportResourceDayData> singleDayValues = singleDayRecord.getResourceDayData();
		
		if(totals==null || totals.size()!= numberOfDays){
			totals = generateTotalsRow(numberOfDays);
			totalsRecord.setResourceDayData(totals);
		}
		
		for(int i=0;i<singleDayValues.size();i++){
			String singleDayTextVal = (singleDayValues.get(i)).getText();
			if(DAYCELL_TEXT_REDLWD.equals(singleDayTextVal)
				|| DAYCELL_TEXT_RED.equals(singleDayTextVal)
				|| DAYCELL_TEXT_YELLOW.equals(singleDayTextVal)
				|| DAYCELL_TEXT_GREEN.equals(singleDayTextVal))
			{
				String totalDaysTextVal = (totals.get(i)).getText();
				int totalDaysIntVal = Integer.parseInt(totalDaysTextVal);
				totalDaysIntVal++;
				(totals.get(i)).setText(Integer.toString(totalDaysIntVal));
			}
		}
	}

	public static GlidePathReportResourceDayData getBlackCell() {
		return GlidePathReportResourceDayData.BLACK;
	}
	
	public static GlidePathReportResourceDayData getRedLwdCell() {
		return GlidePathReportResourceDayData.REDLWD;
	}
	
	public static GlidePathReportResourceDayData getRedCell() {
		return GlidePathReportResourceDayData.RED;
	}
	
	public static GlidePathReportResourceDayData getYellowCell() {
		return GlidePathReportResourceDayData.YELLOW;
	}
	
	public static GlidePathReportResourceDayData getGreenCell() {
		return GlidePathReportResourceDayData.GREEN;
	}
	
	public static GlidePathReportResourceDayData getEmptyCell() {
		return GlidePathReportResourceDayData.EMPTY;
	}
	
	public static List<GlidePathReportResourceDayData> getEmptyDaysList(int numberOfDays) {
		List<GlidePathReportResourceDayData> emptyDaysList = new ArrayList<GlidePathReportResourceDayData>();
		for(int i=0;i<numberOfDays;i++){
			emptyDaysList.add(EMPTY);
		}
		return emptyDaysList;
	}
	
	private String text;
	
	// Private constructor; No class should need to create any objects of this class.
	private GlidePathReportResourceDayData() {}

	public String getText() {
		return text;
	}	
	
	private void setText(String text) {
		this.text = text;
	}
	
}
