package gov.nwcg.isuite.core.reports.data;



/**
 * Report data object for ICS209Report.jrxml.
 */
public class ICS209ReportData {
	
	public static final String NO_AGENCY_CODE = "No Agency";
	
	private Long incidentId;
	private String pagesubtitle;
	
	private String agencyCode;
	
	private Integer crw1Count;
	private Integer crw1PersonCount;
	private Integer crw1StCount;
	private Integer crw1StPersonCount;
	private Integer crw2Count;
	private Integer crw2PersonCount;
	private Integer crw2StCount;
	private Integer crw2StPersonCount;
	private Integer hel1Count;
	private Integer hel1PersonCount;
	private Integer hel2Count;
	private Integer hel2PersonCount;
	private Integer hel3Count;
	private Integer hel3PersonCount;
	private Integer engsCount;
	private Integer engsPersonCount;
	private Integer engsStCount;
	private Integer engsStPersonCount;
	private Integer dozrCount;
	private Integer dozrPersonCount;
	private Integer dozrStCount;
	private Integer dozrStPersonCount;
	private Integer wtdrCount;
	private Integer wtdrPersonCount;
	private Integer ovhdCount;
	private Integer ovhdPersonCount;
	private Integer ccCount;
	private Integer ccPersonCount;
	
	private Integer non209Count;
	private Integer non209PersonCount;

	public ICS209ReportData(){
	}
	
	// Copy constructor:
	// This will typically be used to generate clones of this object that can be modified
	// and used as part of the summary page in ICS209 report
	//
	// NOTE: This method must be updated if fields are added or removed from this class
	//
	public ICS209ReportData(ICS209ReportData other){
		incidentId = other.getIncidentId();
		pagesubtitle = other.getPagesubtitle();
		agencyCode = other.getAgencyCode();
		crw1Count = other.getCrw1Count();
		crw1PersonCount = other.getCrw1PersonCount();
		crw1StCount = other.getCrw1StCount();
		crw1StPersonCount = other.getCrw1StPersonCount();
		crw2Count = other.getCrw2Count();
		crw2PersonCount = other.getCrw2PersonCount();
		crw2StCount = other.getCrw2StCount();
		crw2StPersonCount = other.getCrw2StPersonCount();
		hel1Count = other.getHel1Count();
		hel1PersonCount = other.getHel1PersonCount();
		hel2Count = other.getHel2Count();
		hel2PersonCount = other.getHel2PersonCount();
		hel3Count = other.getHel3Count();
		hel3PersonCount = other.getHel3PersonCount();
		engsCount = other.getEngsCount();
		engsPersonCount = other.getEngsPersonCount();
		engsStCount = other.getEngsStCount();
		engsStPersonCount = other.getEngsStPersonCount();
		dozrCount = other.getDozrCount();
		dozrPersonCount = other.getDozrPersonCount();
		dozrStCount = other.getDozrStCount();
		dozrStPersonCount = other.getDozrStPersonCount();
		wtdrCount = other.getWtdrCount();
		wtdrPersonCount = other.getWtdrPersonCount();
		ovhdCount = other.getOvhdCount();
		ovhdPersonCount = other.getOvhdPersonCount();
		ccCount = other.getCcCount();
		ccPersonCount = other.getCcPersonCount();
		non209Count = other.getNon209Count();
		non209PersonCount = other.getNon209PersonCount();
	}
	
	// Public static method to add the values from the second data object to the first data object.
	//
	// NOTE: This method must be updated if fields are added or removed from this object
	//
	public static void addToTotal(ICS209ReportData totalRecord, ICS209ReportData otherRecord){
//		addIntegerSafely(totalRecord.getCrw1Count(), otherRecord.getCrw1Count());
//		addIntegerSafely(totalRecord.getCrw1PersonCount(), otherRecord.getCrw1PersonCount());
//		addIntegerSafely(totalRecord.getCrw1StCount(), otherRecord.getCrw1StCount());
//		addIntegerSafely(totalRecord.getCrw2Count(), otherRecord.getCrw2Count());
//		addIntegerSafely(totalRecord.getCrw2PersonCount(), otherRecord.getCrw2PersonCount());
//		addIntegerSafely(totalRecord.getCrw1StPersonCount(), otherRecord.getCrw1StPersonCount());
//		addIntegerSafely(totalRecord.getCrw2StCount(), otherRecord.getCrw2StCount());
//		addIntegerSafely(totalRecord.getCrw2StPersonCount(), otherRecord.getCrw2StPersonCount());
//		addIntegerSafely(totalRecord.getHel1Count(), otherRecord.getHel1Count());
//		addIntegerSafely(totalRecord.getHel1PersonCount(), otherRecord.getHel1PersonCount());
//		addIntegerSafely(totalRecord.getHel2Count(), otherRecord.getHel2Count());
//		addIntegerSafely(totalRecord.getHel2PersonCount(), otherRecord.getHel2PersonCount());
//		addIntegerSafely(totalRecord.getHel3Count(), otherRecord.getHel3Count());
//		addIntegerSafely(totalRecord.getHel3PersonCount(), otherRecord.getHel3PersonCount());
//		addIntegerSafely(totalRecord.getEngsCount(), otherRecord.getEngsCount());
//		addIntegerSafely(totalRecord.getEngsPersonCount(), otherRecord.getEngsPersonCount());
//		addIntegerSafely(totalRecord.getEngsStCount(), otherRecord.getEngsStCount());
//		addIntegerSafely(totalRecord.getEngsStPersonCount(), otherRecord.getEngsStPersonCount());
//		addIntegerSafely(totalRecord.getDozrCount(), otherRecord.getDozrCount());
//		addIntegerSafely(totalRecord.getDozrPersonCount(), otherRecord.getDozrPersonCount());
//		addIntegerSafely(totalRecord.getDozrStCount(), otherRecord.getDozrStCount());
//		addIntegerSafely(totalRecord.getDozrStPersonCount(), otherRecord.getDozrStPersonCount());
//		addIntegerSafely(totalRecord.getWtdrCount(), otherRecord.getWtdrCount());
//		addIntegerSafely(totalRecord.getWtdrPersonCount(), otherRecord.getWtdrPersonCount());
//		addIntegerSafely(totalRecord.getOvhdCount(), otherRecord.getOvhdCount());
//		addIntegerSafely(totalRecord.getOvhdPersonCount(), otherRecord.getOvhdPersonCount());
//		addIntegerSafely(totalRecord.getCcCount(), otherRecord.getCcCount());
//		addIntegerSafely(totalRecord.getCcPersonCount(), otherRecord.getCcPersonCount());
//		addIntegerSafely(totalRecord.getNon209Count(), otherRecord.getNon209Count());
//		addIntegerSafely(totalRecord.getNon209PersonCount(), otherRecord.getNon209PersonCount());
		
		totalRecord.setCrw1Count(addIntegerSafely2(totalRecord.getCrw1Count(), otherRecord.getCrw1Count()));
		totalRecord.setCrw1PersonCount(addIntegerSafely2(totalRecord.getCrw1PersonCount(), otherRecord.getCrw1PersonCount()));
		totalRecord.setCrw1StCount(addIntegerSafely2(totalRecord.getCrw1StCount(), otherRecord.getCrw1StCount()));
		totalRecord.setCrw2Count(addIntegerSafely2(totalRecord.getCrw2Count(), otherRecord.getCrw2Count()));
		totalRecord.setCrw2PersonCount(addIntegerSafely2(totalRecord.getCrw2PersonCount(), otherRecord.getCrw2PersonCount()));
		totalRecord.setCrw1StPersonCount(addIntegerSafely2(totalRecord.getCrw1StPersonCount(), otherRecord.getCrw1StPersonCount()));
		totalRecord.setCrw2StCount(addIntegerSafely2(totalRecord.getCrw2StCount(), otherRecord.getCrw2StCount()));
		totalRecord.setCrw2StPersonCount(addIntegerSafely2(totalRecord.getCrw2StPersonCount(), otherRecord.getCrw2StPersonCount()));
		totalRecord.setHel1Count(addIntegerSafely2(totalRecord.getHel1Count(), otherRecord.getHel1Count()));
		totalRecord.setHel1PersonCount(addIntegerSafely2(totalRecord.getHel1PersonCount(), otherRecord.getHel1PersonCount()));
		totalRecord.setHel2Count(addIntegerSafely2(totalRecord.getHel2Count(), otherRecord.getHel2Count()));
		totalRecord.setHel2PersonCount(addIntegerSafely2(totalRecord.getHel2PersonCount(), otherRecord.getHel2PersonCount()));
		totalRecord.setHel3Count(addIntegerSafely2(totalRecord.getHel3Count(), otherRecord.getHel3Count()));
		totalRecord.setHel3PersonCount(addIntegerSafely2(totalRecord.getHel3PersonCount(), otherRecord.getHel3PersonCount()));
		totalRecord.setEngsCount(addIntegerSafely2(totalRecord.getEngsCount(), otherRecord.getEngsCount()));
		totalRecord.setEngsPersonCount(addIntegerSafely2(totalRecord.getEngsPersonCount(), otherRecord.getEngsPersonCount()));
		totalRecord.setEngsStCount(addIntegerSafely2(totalRecord.getEngsStCount(), otherRecord.getEngsStCount()));
		totalRecord.setEngsStPersonCount(addIntegerSafely2(totalRecord.getEngsStPersonCount(), otherRecord.getEngsStPersonCount()));
		totalRecord.setDozrCount(addIntegerSafely2(totalRecord.getDozrCount(), otherRecord.getDozrCount()));
		totalRecord.setDozrPersonCount(addIntegerSafely2(totalRecord.getDozrPersonCount(), otherRecord.getDozrPersonCount()));
		totalRecord.setDozrStCount(addIntegerSafely2(totalRecord.getDozrStCount(), otherRecord.getDozrStCount()));
		totalRecord.setDozrStPersonCount(addIntegerSafely2(totalRecord.getDozrStPersonCount(), otherRecord.getDozrStPersonCount()));
		totalRecord.setWtdrCount(addIntegerSafely2(totalRecord.getWtdrCount(), otherRecord.getWtdrCount()));
		totalRecord.setWtdrPersonCount(addIntegerSafely2(totalRecord.getWtdrPersonCount(), otherRecord.getWtdrPersonCount()));
		totalRecord.setOvhdCount(addIntegerSafely2(totalRecord.getOvhdCount(), otherRecord.getOvhdCount()));
		totalRecord.setOvhdPersonCount(addIntegerSafely2(totalRecord.getOvhdPersonCount(), otherRecord.getOvhdPersonCount()));
		totalRecord.setCcCount(addIntegerSafely2(totalRecord.getCcCount(), otherRecord.getCcCount()));
		totalRecord.setCcPersonCount(addIntegerSafely2(totalRecord.getCcPersonCount(), otherRecord.getCcPersonCount()));
		totalRecord.setNon209Count(addIntegerSafely2(totalRecord.getNon209Count(), otherRecord.getNon209Count()));
		totalRecord.setNon209PersonCount(addIntegerSafely2(totalRecord.getNon209PersonCount(), otherRecord.getNon209PersonCount()));
	}
	
	/**
	 * Adds the second object value to the first.
	 * Note: If the second object is null or 0 value, the first is untouched, i.e., remains null if the 
	 * first object was null. 
	 * @param fromTotal
	 * @param fromOther
	 */
	private static void addIntegerSafely(Integer fromTotal, Integer fromOther){
		if(null == fromOther || fromOther.intValue()==0) return; // Nothing to add
		
		if(null == fromTotal) {
			fromTotal = new Integer(fromOther);
		} else {
			fromTotal += fromOther;
		}
	}
	
	/**
	 * Adds the second object value to the first.
	 * @param fromTotal
	 * @param fromOther
	 */
	private static Integer addIntegerSafely2(Integer fromTotal, Integer fromOther){
		
		if(null == fromOther) {
			fromOther = 0;
		}
		
		if(null == fromTotal) {
			fromTotal = 0;
		}
			
		fromTotal += fromOther;	
		
		return fromTotal;
	}

	/**
	 * Returns the agencyCode.
	 *
	 * @return 
	 *		the agencyCode to return
	 */
	public String getAgencyCode() {
		return agencyCode;
	}

	/**
	 * Sets the agencyCode.
	 *
	 * @param agencyCode 
	 *			the agencyCode to set
	 */
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	/**
	 * Returns the crw1Count.
	 *
	 * @return 
	 *		the crw1Count to return
	 */
	public Integer getCrw1Count() {
		return (null != crw1Count ? crw1Count : 0);
	}

	/**
	 * Sets the crw1Count.
	 *
	 * @param crw1Count 
	 *			the crw1Count to set
	 */
	public void setCrw1Count(Integer crw1Count) {
		this.crw1Count = crw1Count;
	}

	/**
	 * Returns the crw1PersonCount.
	 *
	 * @return 
	 *		the crw1PersonCount to return
	 */
	public Integer getCrw1PersonCount() {
		return (null != crw1PersonCount ? crw1PersonCount : 0);
	}

	/**
	 * Sets the crw1PersonCount.
	 *
	 * @param crw1PersonCount 
	 *			the crw1PersonCount to set
	 */
	public void setCrw1PersonCount(Integer crw1PersonCount) {
		this.crw1PersonCount = crw1PersonCount;
	}

	/**
	 * Returns the crw1StCount.
	 *
	 * @return 
	 *		the crw1StCount to return
	 */
	public Integer getCrw1StCount() {
		return (null != crw1StCount ? crw1StCount : 0);
	}

	/**
	 * Sets the crw1StCount.
	 *
	 * @param crw1StCount 
	 *			the crw1StCount to set
	 */
	public void setCrw1StCount(Integer crw1StCount) {
		this.crw1StCount = crw1StCount;
	}

	/**
	 * Returns the crw1StPersonCount.
	 *
	 * @return 
	 *		the crw1StPersonCount to return
	 */
	public Integer getCrw1StPersonCount() {
		return (null != crw1StPersonCount ? crw1StPersonCount : 0);
	}

	/**
	 * Sets the crw1StPersonCount.
	 *
	 * @param crw1StPersonCount 
	 *			the crw1StPersonCount to set
	 */
	public void setCrw1StPersonCount(Integer crw1StPersonCount) {
		this.crw1StPersonCount = crw1StPersonCount;
	}

	/**
	 * Returns the crw2Count.
	 *
	 * @return 
	 *		the crw2Count to return
	 */
	public Integer getCrw2Count() {
		return (null != crw2Count ? crw2Count : 0);
	}

	/**
	 * Sets the crw2Count.
	 *
	 * @param crw2Count 
	 *			the crw2Count to set
	 */
	public void setCrw2Count(Integer crw2Count) {
		this.crw2Count = crw2Count;
	}

	/**
	 * Returns the crw2PersonCount.
	 *
	 * @return 
	 *		the crw2PersonCount to return
	 */
	public Integer getCrw2PersonCount() {
		return (null != crw2PersonCount ? crw2PersonCount : 0);
	}

	/**
	 * Sets the crw2PersonCount.
	 *
	 * @param crw2PersonCount 
	 *			the crw2PersonCount to set
	 */
	public void setCrw2PersonCount(Integer crw2PersonCount) {
		this.crw2PersonCount = crw2PersonCount;
	}

	/**
	 * Returns the crw2StCount.
	 *
	 * @return 
	 *		the crw2StCount to return
	 */
	public Integer getCrw2StCount() {
		return (null != crw2StCount ? crw2StCount : 0);
	}

	/**
	 * Sets the crw2StCount.
	 *
	 * @param crw2StCount 
	 *			the crw2StCount to set
	 */
	public void setCrw2StCount(Integer crw2StCount) {
		this.crw2StCount = crw2StCount;
	}

	/**
	 * Returns the crw2StPersonCount.
	 *
	 * @return 
	 *		the crw2StPersonCount to return
	 */
	public Integer getCrw2StPersonCount() {
		return (null != crw2StPersonCount ? crw2StPersonCount : 0);
	}

	/**
	 * Sets the crw2StPersonCount.
	 *
	 * @param crw2StPersonCount 
	 *			the crw2StPersonCount to set
	 */
	public void setCrw2StPersonCount(Integer crw2StPersonCount) {
		this.crw2StPersonCount = crw2StPersonCount;
	}

	/**
	 * Returns the hel1Count.
	 *
	 * @return 
	 *		the hel1Count to return
	 */
	public Integer getHel1Count() {
		return (null != hel1Count ? hel1Count : 0);
	}

	/**
	 * Sets the hel1Count.
	 *
	 * @param hel1Count 
	 *			the hel1Count to set
	 */
	public void setHel1Count(Integer hel1Count) {
		this.hel1Count = hel1Count;
	}

	/**
	 * Returns the hel1PersonCount.
	 *
	 * @return 
	 *		the hel1PersonCount to return
	 */
	public Integer getHel1PersonCount() {
		return (null != hel1PersonCount ? hel1PersonCount : 0);
	}

	/**
	 * Sets the hel1PersonCount.
	 *
	 * @param hel1PersonCount 
	 *			the hel1PersonCount to set
	 */
	public void setHel1PersonCount(Integer hel1PersonCount) {
		this.hel1PersonCount = hel1PersonCount;
	}

	/**
	 * Returns the hel2Count.
	 *
	 * @return 
	 *		the hel2Count to return
	 */
	public Integer getHel2Count() {
		return (null != hel2Count ? hel2Count : 0);
	}

	/**
	 * Sets the hel2Count.
	 *
	 * @param hel2Count 
	 *			the hel2Count to set
	 */
	public void setHel2Count(Integer hel2Count) {
		this.hel2Count = hel2Count;
	}

	/**
	 * Returns the hel2PersonCount.
	 *
	 * @return 
	 *		the hel2PersonCount to return
	 */
	public Integer getHel2PersonCount() {
		return (null != hel2PersonCount ? hel2PersonCount : 0);
	}

	/**
	 * Sets the hel2PersonCount.
	 *
	 * @param hel2PersonCount 
	 *			the hel2PersonCount to set
	 */
	public void setHel2PersonCount(Integer hel2PersonCount) {
		this.hel2PersonCount = hel2PersonCount;
	}

	/**
	 * Returns the hel3Count.
	 *
	 * @return 
	 *		the hel3Count to return
	 */
	public Integer getHel3Count() {
		return (null != hel3Count ? hel3Count : 0);
	}

	/**
	 * Sets the hel3Count.
	 *
	 * @param hel3Count 
	 *			the hel3Count to set
	 */
	public void setHel3Count(Integer hel3Count) {
		this.hel3Count = hel3Count;
	}

	/**
	 * Returns the hel3PersonCount.
	 *
	 * @return 
	 *		the hel3PersonCount to return
	 */
	public Integer getHel3PersonCount() {
		return (null != hel3PersonCount ? hel3PersonCount : 0);
	}

	/**
	 * Sets the hel3PersonCount.
	 *
	 * @param hel3PersonCount 
	 *			the hel3PersonCount to set
	 */
	public void setHel3PersonCount(Integer hel3PersonCount) {
		this.hel3PersonCount = hel3PersonCount;
	}

	/**
	 * Returns the engsCount.
	 *
	 * @return 
	 *		the engsCount to return
	 */
	public Integer getEngsCount() {
		return (null != engsCount ? engsCount : 0);
	}

	/**
	 * Sets the engsCount.
	 *
	 * @param engsCount 
	 *			the engsCount to set
	 */
	public void setEngsCount(Integer engsCount) {
		this.engsCount = engsCount;
	}

	/**
	 * Returns the engsPersonCount.
	 *
	 * @return 
	 *		the engsPersonCount to return
	 */
	public Integer getEngsPersonCount() {
		return (null != engsPersonCount ? engsPersonCount : 0);
	}

	/**
	 * Sets the engsPersonCount.
	 *
	 * @param engsPersonCount 
	 *			the engsPersonCount to set
	 */
	public void setEngsPersonCount(Integer engsPersonCount) {
		this.engsPersonCount = engsPersonCount;
	}

	/**
	 * Returns the engsStCount.
	 *
	 * @return 
	 *		the engsStCount to return
	 */
	public Integer getEngsStCount() {
		return (null != engsStCount ? engsStCount : 0);
	}

	/**
	 * Sets the engsStCount.
	 *
	 * @param engsStCount 
	 *			the engsStCount to set
	 */
	public void setEngsStCount(Integer engsStCount) {
		this.engsStCount = engsStCount;
	}

	/**
	 * Returns the engsStPersonCount.
	 *
	 * @return 
	 *		the engsStPersonCount to return
	 */
	public Integer getEngsStPersonCount() {
		return (null != engsStPersonCount ? engsStPersonCount : 0);
	}

	/**
	 * Sets the engsStPersonCount.
	 *
	 * @param engsStPersonCount 
	 *			the engsStPersonCount to set
	 */
	public void setEngsStPersonCount(Integer engsStPersonCount) {
		this.engsStPersonCount = engsStPersonCount;
	}

	/**
	 * Returns the dozrCount.
	 *
	 * @return 
	 *		the dozrCount to return
	 */
	public Integer getDozrCount() {
		return (null != dozrCount ? dozrCount : 0);
	}

	/**
	 * Sets the dozrCount.
	 *
	 * @param dozrCount 
	 *			the dozrCount to set
	 */
	public void setDozrCount(Integer dozrCount) {
		this.dozrCount = dozrCount;
	}

	/**
	 * Returns the dozrPersonCount.
	 *
	 * @return 
	 *		the dozrPersonCount to return
	 */
	public Integer getDozrPersonCount() {
		return (null != dozrPersonCount ? dozrPersonCount : 0);
	}

	/**
	 * Sets the dozrPersonCount.
	 *
	 * @param dozrPersonCount 
	 *			the dozrPersonCount to set
	 */
	public void setDozrPersonCount(Integer dozrPersonCount) {
		this.dozrPersonCount = dozrPersonCount;
	}

	/**
	 * Returns the dozrStCount.
	 *
	 * @return 
	 *		the dozrStCount to return
	 */
	public Integer getDozrStCount() {
		return (null != dozrStCount ? dozrStCount : 0);
	}

	/**
	 * Sets the dozrStCount.
	 *
	 * @param dozrStCount 
	 *			the dozrStCount to set
	 */
	public void setDozrStCount(Integer dozrStCount) {
		this.dozrStCount = dozrStCount;
	}

	/**
	 * Returns the dozrStPersonCount.
	 *
	 * @return 
	 *		the dozrStPersonCount to return
	 */
	public Integer getDozrStPersonCount() {
		return (null != dozrStPersonCount ? dozrStPersonCount : 0);
	}

	/**
	 * Sets the dozrStPersonCount.
	 *
	 * @param dozrStPersonCount 
	 *			the dozrStPersonCount to set
	 */
	public void setDozrStPersonCount(Integer dozrStPersonCount) {
		this.dozrStPersonCount = dozrStPersonCount;
	}

	/**
	 * Returns the wtdrCount.
	 *
	 * @return 
	 *		the wtdrCount to return
	 */
	public Integer getWtdrCount() {
		return (null != wtdrCount ? wtdrCount : 0);
	}

	/**
	 * Sets the wtdrCount.
	 *
	 * @param wtdrCount 
	 *			the wtdrCount to set
	 */
	public void setWtdrCount(Integer wtdrCount) {
		this.wtdrCount = wtdrCount;
	}

	/**
	 * Returns the wtdrPersonCount.
	 *
	 * @return 
	 *		the wtdrPersonCount to return
	 */
	public Integer getWtdrPersonCount() {
		return (null != wtdrPersonCount ? wtdrPersonCount : 0);
	}

	/**
	 * Sets the wtdrPersonCount.
	 *
	 * @param wtdrPersonCount 
	 *			the wtdrPersonCount to set
	 */
	public void setWtdrPersonCount(Integer wtdrPersonCount) {
		this.wtdrPersonCount = wtdrPersonCount;
	}

	/**
	 * Returns the ovhdCount.
	 *
	 * @return 
	 *		the ovhdCount to return
	 */
	public Integer getOvhdCount() {
		return (null != ovhdCount ? ovhdCount : 0);
	}

	/**
	 * Sets the ovhdCount.
	 *
	 * @param ovhdCount 
	 *			the ovhdCount to set
	 */
	public void setOvhdCount(Integer ovhdCount) {
		this.ovhdCount = ovhdCount;
	}

	/**
	 * Returns the ovhdPersonCount.
	 *
	 * @return 
	 *		the ovhdPersonCount to return
	 */
	public Integer getOvhdPersonCount() {
		return (null != ovhdPersonCount ? ovhdPersonCount : 0);
	}

	/**
	 * Sets the ovhdPersonCount.
	 *
	 * @param ovhdPersonCount 
	 *			the ovhdPersonCount to set
	 */
	public void setOvhdPersonCount(Integer ovhdPersonCount) {
		this.ovhdPersonCount = ovhdPersonCount;
	}

	/**
	 * Returns the ccCount.
	 *
	 * @return 
	 *		the ccCount to return
	 */
	public Integer getCcCount() {
		return (null != ccCount ? ccCount : 0);
	}

	/**
	 * Sets the ccCount.
	 *
	 * @param ccCount 
	 *			the ccCount to set
	 */
	public void setCcCount(Integer ccCount) {
		this.ccCount = ccCount;
	}

	/**
	 * Returns the ccPersonCount.
	 *
	 * @return 
	 *		the ccPersonCount to return
	 */
	public Integer getCcPersonCount() {
		return (null != ccPersonCount ? ccPersonCount : 0);
	}

	/**
	 * Sets the ccPersonCount.
	 *
	 * @param ccPersonCount 
	 *			the ccPersonCount to set
	 */
	public void setCcPersonCount(Integer ccPersonCount) {
		this.ccPersonCount = ccPersonCount;
	}

	/**
	 * Returns the non209Count.
	 *
	 * @return 
	 *		the non209Count to return
	 */
	public Integer getNon209Count() {
		return (null != non209Count ? non209Count : 0);
	}

	/**
	 * Sets the non209Count.
	 *
	 * @param non209Count 
	 *			the non209Count to set
	 */
	public void setNon209Count(Integer non209Count) {
		this.non209Count = non209Count;
	}

	/**
	 * Returns the non209PersonCount.
	 *
	 * @return 
	 *		the non209PersonCount to return
	 */
	public Integer getNon209PersonCount() {
		return (null != non209PersonCount ? non209PersonCount : 0);
	}

	/**
	 * Sets the non209PersonCount.
	 *
	 * @param non209PersonCount 
	 *			the non209PersonCount to set
	 */
	public void setNon209PersonCount(Integer non209PersonCount) {
		this.non209PersonCount = non209PersonCount;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public String getPagesubtitle() {
		return pagesubtitle;
	}

	public void setPagesubtitle(String pagesubtitle) {
		this.pagesubtitle = pagesubtitle;
	}

	
}
