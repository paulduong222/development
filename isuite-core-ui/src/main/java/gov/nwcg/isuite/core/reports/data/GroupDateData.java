package gov.nwcg.isuite.core.reports.data;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GroupDateData {
	private Long incidentId;
	private String incidentName;
	private String incidentNumber;  //unitCode	
	private Date startDate;
	private Date endDate;
	private String date1 = "";
	private String date2 = "";
	private String date3 = "";
	private String date4 = "";
	private String date5 = "";
	private String date6 = "";
	private String date7 = "";
	private String sectionName;
	public SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	private JRBeanCollectionDataSource jsResourceDateData;
	private Collection<ResourceDateData> resourceDateDataList;
	
	public GroupDateData(){
		
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public String getIncidentName() {
		return incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getIncidentNumber() {
		return incidentNumber;
	}

	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return the date1
	 */
	public String getDate1() {
		return date1;
	}

	/**
	 * @param date1 the date1 to set
	 */
	public void setDate1(String date1) {
		this.date1 = date1;
	}

	/**
	 * @return the date2
	 */
	public String getDate2() {
		return date2;
	}

	/**
	 * @param date2 the date2 to set
	 */
	public void setDate2(String date2) {
		this.date2 = date2;
	}

	/**
	 * @return the date3
	 */
	public String getDate3() {
		return date3;
	}

	/**
	 * @param date3 the date3 to set
	 */
	public void setDate3(String date3) {
		this.date3 = date3;
	}
	
	public String getDate4() {
		return date4;
	}

	public void setDate4(String date4) {
		this.date4 = date4;
	}

	public String getDate5() {
		return date5;
	}

	public void setDate5(String date5) {
		this.date5 = date5;
	}

	public String getDate6() {
		return date6;
	}

	public void setDate6(String date6) {
		this.date6 = date6;
	}

	public String getDate7() {
		return date7;
	}

	public void setDate7(String date7) {
		this.date7 = date7;
	}

	public JRBeanCollectionDataSource getJsResourceDateData() {
		return new JRBeanCollectionDataSource(this.resourceDateDataList);
	}

	public void setJsResourceDateData(JRBeanCollectionDataSource jsResourceDateData) {
		this.jsResourceDateData = jsResourceDateData;
	}

	/**
	 * @return the resourceDateData2
	 */
	public Collection<ResourceDateData> getResourceDateDataList() {
		return resourceDateDataList;
	}

	/**
	 * @param resourceDateData2 the resourceDateData2 to set
	 */
	public void setResourceDateDataList(Collection<ResourceDateData> resourceDateDataList) {
		this.resourceDateDataList = resourceDateDataList;
	}

	/**
	 * @return the sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	public void setDays(List<String> days, Date endDate) {
		int counter = 7;
		String d = dateFormat.format(endDate);
		
		//reset counter if the days is less than 7 days 
		for(int i=0; i < 7; i++) {
			if(d.equals(days.get(i))) 
				counter = i+1;
		}	
		
		for(int i=0; i < counter; i++) {
			if(i == 0)
				date1 = days.get(i);
			else if (i == 1)
				date2 = days.get(i);
			else if (i == 2)
				date3 = days.get(i);
			else if (i == 3)
				date4 = days.get(i);
			else if (i == 4)
				date5 = days.get(i);
			else if (i == 5)
				date6 = days.get(i);
			else if (i == 6)
				date7 = days.get(i);		
		}
	}
}
