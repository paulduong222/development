package gov.nwcg.isuite.core.cost.utilities;

import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;

import java.util.Calendar;
import java.util.Date;

public class StopDateResolver {

	
	/**
	 * This method will try to resolve the daily cost stop date to use 
	 * for the daily cost calculations.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Date resolveIRStopDate(IncidentResourceVo irVo) throws Exception {
		Date stopDate=null;
		Date sysDate=Calendar.getInstance().getTime();
		
		
		try{
			/*
			 * e-ISuite Manage Costs - Daily Cost Use Case.pdf
			 * Page: 12
			 * 
			 * 		The system must stop generating costs based on either 
			 *  	the Actual Release Date or Estimated Date of Arrival, 
			 *  	depending on which date is later.
			 *  
			 *  	(Updated 05/30/2012) 
			 *  	If the Estimated Date of Arrival is null, 
			 *  	the system must stop generating costs based on the Actual Release Date.
			 *  
			 *  	(Updated 05/30/2012) If the user entered an Estimated Date of Arrival 
			 *  	that is later than the Actual Release Date, 
			 *  	the system must generate Cost records for each of the dates between the 
			 *  	Actual Release Date and the Estimated Date of Arrival. 
			 *  	Costs would also be generated on the defined Estimated Date of Arrival. 
			 *  	Costs would stop being generated on the day after 
			 *  	the Estimated Date of Arrival.
			 */
			Date releaseDate=DateTransferVo.getDate(irVo.getWorkPeriodVo().getDmReleaseDateVo());
			Date arrivalDate=DateTransferVo.getDate(irVo.getWorkPeriodVo().getDmTentativeArrivalDateVo());
			
			if(DateUtil.hasValue(releaseDate) 
					&& DateUtil.hasValue(arrivalDate)){
				DateUtil.addTimeToDate(arrivalDate, "2359");
				if(arrivalDate.after(releaseDate))
					stopDate=arrivalDate;
				else
					stopDate=releaseDate;
			}else if(DateUtil.hasValue(releaseDate)){
				stopDate=releaseDate;
			}else if(DateUtil.hasValue(arrivalDate)){
				stopDate=arrivalDate;
			}else
				stopDate=sysDate; //Calendar.getInstance().getTime();

			if(DateUtil.isSameDate(stopDate, sysDate)){
				
			}else{
				Date tmpDate = DateUtil.addMilitaryTimeToDate(stopDate, "0001");
				if(tmpDate.after(sysDate))
				{
					stopDate=sysDate;
				}
			}
			
		}catch(Exception e){
			throw e;
		}
		
		return stopDate;
	}

	public static Date resolveIRStopDate(CostResourceDataVo vo, String dbName) throws Exception {
		Date stopDate=null;
		//Date sysDate=Calendar.getInstance().getTime();
		Date sysDate=EISuiteCalendar.getCalendarDate(dbName); 
		Date incidentEndDate=vo.getEndDate();
		
		try{
			/*
			 * e-ISuite Manage Costs - Daily Cost Use Case.pdf
			 * Page: 12
			 * 
			 * 		The system must stop generating costs based on either 
			 *  	the Actual Release Date or Estimated Date of Arrival, 
			 *  	depending on which date is later.
			 *  
			 *  	(Updated 05/30/2012) 
			 *  	If the Estimated Date of Arrival is null, 
			 *  	the system must stop generating costs based on the Actual Release Date.
			 *  
			 *  	(Updated 05/30/2012) If the user entered an Estimated Date of Arrival 
			 *  	that is later than the Actual Release Date, 
			 *  	the system must generate Cost records for each of the dates between the 
			 *  	Actual Release Date and the Estimated Date of Arrival. 
			 *  	Costs would also be generated on the defined Estimated Date of Arrival. 
			 *  	Costs would stop being generated on the day after 
			 *  	the Estimated Date of Arrival.
			 */
			Date releaseDate=vo.getReleaseDate();
			Date arrivalDate=vo.getArrivalDate();
			
			if(DateUtil.hasValue(releaseDate) 
					&& DateUtil.hasValue(arrivalDate)){
				DateUtil.addTimeToDate(arrivalDate, "2359");
				if(arrivalDate.after(releaseDate))
					stopDate=arrivalDate;
				else
					stopDate=releaseDate;
			}else if(DateUtil.hasValue(releaseDate)){
				stopDate=releaseDate;
			}else if(DateUtil.hasValue(arrivalDate)){
				stopDate=arrivalDate;
			}else
				stopDate=sysDate; //Calendar.getInstance().getTime();

			if(DateUtil.isSameDate(stopDate, sysDate)){
				
			}else{
				Date tmpDate = DateUtil.addMilitaryTimeToDate(stopDate, "0001");
				if(tmpDate.after(sysDate))
				{
					stopDate=sysDate;
				}
			}
			if(DateUtil.hasValue(incidentEndDate)&&DateUtil.hasValue(stopDate)){
				if(stopDate.after(incidentEndDate)){
					stopDate=incidentEndDate;
				}
			}
			
		}catch(Exception e){
			throw e;
		}
		
		return stopDate;
	}

	public static Date resolveIROStopDate(IncidentResourceOtherVo iroVo) throws Exception {
		Date sysDate=Calendar.getInstance().getTime();
		Date stopDate=sysDate;
		
		try{
			/*
			 * 5.	If no Cost records have been generated for an Other Cost 
			 * 		and the user selects the Run Costs button, 
			 * 		the system must generate Cost records for that Other Cost from the 
			 * 		Assign Date up to the current date
			 */
			Date releaseDate=iroVo.getResourceOtherVo().getActualReleaseDate();
			
			if(DateUtil.hasValue(releaseDate)) {
				return releaseDate;
			}else
				stopDate=sysDate;
			
			if(DateUtil.isSameDate(stopDate, sysDate)){
				
			}else{
				Date tmpDate = DateUtil.addMilitaryTimeToDate(stopDate, "0001");
				if(tmpDate.after(sysDate))
				{
					stopDate=sysDate;
				}
			}
			return stopDate;
			
		}catch(Exception e){
			
		}
		
		return stopDate;
	}


}
