package gov.nwcg.isuite.core.reports.data;

import java.util.Date;

import gov.nwcg.isuite.framework.report.data.BaseReportData;
import gov.nwcg.isuite.framework.util.StringUtility;

/**
 * Report data object for GroundSupportReport.jrxml.
 */
public class GroundSupportReportData extends BaseReportData {
	private String leaveTime;
	private String airport="";
	private String airline="";
	private String flightTime;
	private Date tentativeReleaseDate = null;
	
	public GroundSupportReportData() {
		super();
		super.omitCountryPrefix=true;
	}

	/**
	 * Sets the leaveTime
	 * 
	 * @param leaveTime
	 * 		the leaveTime to set
	 */
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public void setLeaveTimeInt(Integer val){
		if(null != val)
			this.leaveTime = String.valueOf(val);
	}
	
	/**
	 * Returns the leaveTime
	 * 
	 * @return
	 * 		the leaveTime to return
	 */
	public String getLeaveTime() {
		if (null != this.leaveTime) {
			if(this.leaveTime.length() < 4) {
				this.setLeaveTime(StringUtility.leftPad(this.leaveTime, "0", 4));
			}
			
		}else
			leaveTime="";
		
		// sme's wrote defect about 0000 not showing
		// going to uncomment out, in case we need to not show it later
		//if(!leaveTime.equals("0000"))
			return leaveTime;
		//else
		//	return "";
	}

	/**
	 * Sets the airport
	 * 
	 * @param airport
	 * 		the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}

	/**
	 * Returns the airport
	 * 
	 * @return
	 * 		the airport time to return
	 */
	public String getAirport() {
		return airport;
	}

	/**
	 * Sets the airline
	 * 
	 * @param airline
	 * 		the airline to set
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * Returns the airline
	 * 
	 * @return
	 * 		the airline to return
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * Sets the flightTime
	 * 
	 * @param flightTime
	 * 		the flightTime to set
	 */
	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	/**
	 * Returns the flightTime
	 * 
	 * @return
	 * 		the flightTime to return
	 */
	public String getFlightTime() {
		if (null != this.flightTime) {
			if(this.flightTime.length() < 4) {
				this.setFlightTime(StringUtility.leftPad(this.flightTime, "0", 4));
			}
			
			if(!flightTime.equals("0000"))
				return flightTime;
			else
				return "";
		} else
			return "";
		
	}
	
	public void setFlightTimeInt(Integer flightTimeInt) {
		if (null != flightTimeInt) {
			this.flightTime = String.valueOf(flightTimeInt);
		}
	}
	
	/**
	 * @return the tentativeReleaseDate
	 */
	public Date getTentativeReleaseDate() {
		return tentativeReleaseDate;
	}

	/**
	 * @param tentativeReleaseDate the tentativeReleaseDate to set
	 */
	public void setTentativeReleaseDate(Date tentativeReleaseDate) {
		this.tentativeReleaseDate = tentativeReleaseDate;
	}
}
