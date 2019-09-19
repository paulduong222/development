package gov.nwcg.isuite.core.reports.data;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class ResourceDateData  implements Cloneable {
	private Long incidentId;
	private String incidentName;
	private String incidentNumber;  //unitCode	
	private Long resourceid;
	private String requestNumber;
	private String sortedRequestNumber;
	private String firstName;
	private String lastName;
	private String itemCode;
	private String status;
	private String sectionName;
	private Double hoursWorkedDate1=0.0;
	private Double hoursWorkedDate2=0.0;
	private Double hoursWorkedDate3=0.0;
	private Double hoursWorkedDate4=0.0;
	private Double hoursWorkedDate5=0.0;
	private Double hoursWorkedDate6=0.0;
	private Double hoursWorkedDate7=0.0;
	private Double total=0.0;
	private Integer week;
	private String date1 = "";
	private String date2 = "";
	private String date3 = "";
	private String date4 = "";
	private String date5 = "";
	private String date6 = "";
	private String date7 = "";
	
	public ResourceDateData(){
		
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

	public Long getResourceid() {
		return resourceid;
	}

	public void setResourceid(Long resourceid) {
		this.resourceid = resourceid;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	/**
	 * @return the hoursWorkedDate1
	 */
	public Double getHoursWorkedDate1() {
		return hoursWorkedDate1;
	}

	/**
	 * @param hoursWorkedDate1 the hoursWorkedDate1 to set
	 */
	public void setHoursWorkedDate1(Double hoursWorkedDate1) {
		this.hoursWorkedDate1 = hoursWorkedDate1;
	}

	/**
	 * @return the hoursWorkedDate2
	 */
	public Double getHoursWorkedDate2() {
		return hoursWorkedDate2;
	}

	/**
	 * @param hoursWorkedDate2 the hoursWorkedDate2 to set
	 */
	public void setHoursWorkedDate2(Double hoursWorkedDate2) {
		this.hoursWorkedDate2 = hoursWorkedDate2;
	}

	/**
	 * @return the hoursWorkedDate3
	 */
	public Double getHoursWorkedDate3() {
		return hoursWorkedDate3;
	}

	/**
	 * @param hoursWorkedDate3 the hoursWorkedDate3 to set
	 */
	public void setHoursWorkedDate3(Double hoursWorkedDate3) {
		this.hoursWorkedDate3 = hoursWorkedDate3;
	}

	/**
	 * @return the hoursWorkedDate4
	 */
	public Double getHoursWorkedDate4() {
		return hoursWorkedDate4;
	}

	/**
	 * @param hoursWorkedDate4 the hoursWorkedDate4 to set
	 */
	public void setHoursWorkedDate4(Double hoursWorkedDate4) {
		this.hoursWorkedDate4 = hoursWorkedDate4;
	}

	/**
	 * @return the hoursWorkedDate5
	 */
	public Double getHoursWorkedDate5() {
		return hoursWorkedDate5;
	}

	/**
	 * @param hoursWorkedDate5 the hoursWorkedDate5 to set
	 */
	public void setHoursWorkedDate5(Double hoursWorkedDate5) {
		this.hoursWorkedDate5 = hoursWorkedDate5;
	}

	/**
	 * @return the hoursWorkedDate6
	 */
	public Double getHoursWorkedDate6() {
		return hoursWorkedDate6;
	}

	/**
	 * @param hoursWorkedDate6 the hoursWorkedDate6 to set
	 */
	public void setHoursWorkedDate6(Double hoursWorkedDate6) {
		this.hoursWorkedDate6 = hoursWorkedDate6;
	}

	/**
	 * @return the hoursWorkedDate7
	 */
	public Double getHoursWorkedDate7() {
		return hoursWorkedDate7;
	}

	/**
	 * @param hoursWorkedDate7 the hoursWorkedDate7 to set
	 */
	public void setHoursWorkedDate7(Double hoursWorkedDate7) {
		this.hoursWorkedDate7 = hoursWorkedDate7;
	}
	
	/**
	 * @return total of hoursWorkedDates
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * @param hoursWorkedDate7 the hoursWorkedDate7 to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
	/**
     * Comparator that sorts Matrix objects by matrixName (case-insensitive) in
     * ascending order. Those with <code>null</code> matrixName will be at the
     * end of the sorted list.
     */
    public static final Comparator<ResourceDateData> COMPARATOR__REQUEST_NUMBER_IGNORE_CASE_NULL_LAST = new Comparator<ResourceDateData>() {
        public int compare(ResourceDateData o1, ResourceDateData o2) {
            String name1 = StringUtils.lowerCase(o1.getRequestNumber());
            String name2 = StringUtils.lowerCase(o2.getRequestNumber());
            return compareStr(name1, name2);
        }
    };
    
    private static int compareStr(String o1, String o2) {
    	if (o1 == null && o2 == null) {
    		return 0;
    	}

    	if (o1 == null && o2 != null) {
    		return 1;
    	}

    	if (o1 != null && o2 == null) {
    		return -1;
    	}
    	return o1.compareTo(o2);
    }

	public void setSortedRequestNumber(String sortedRequestNumber) {
		this.sortedRequestNumber = sortedRequestNumber;
	}

	public String getSortedRequestNumber() {
		return sortedRequestNumber;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public String getDate3() {
		return date3;
	}

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
}
