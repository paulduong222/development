package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface AirTravel extends Persistable {

   /**
    * Sets the jet port id.
    * 
    * @param jetPortId
    * 		the jet port id to set
    */
   public void setJetPortId(Long jetPortId);
   
   /**
    * Returns the jet port id.
    * 
    * @return 
    * 		the jet port id to return
    */
   public Long getJetPortId();

   /**
    * Sets the jet port.
    * 
    * @param jetPort
    * 		the jet port to set
    */
   public void setJetPort(JetPort jetPort);
   
   /**
    * Returns the jet port.
    * 
    * @return 
    * 		the jet port to return
    */
   public JetPort getJetPort();
   
   /**
    * Sets the airline.
    * 
    * @param val
    * 		the airline to set
    */
   public void setAirline(String val);
   
   /**
    * Returns the airline.
    * 
    * @return 
    * 		the airline to return
    */
   public String getAirline();
   
   /**
    * Returns whether or not dispatch is notified.
    * 
    * @return 
    * 		the dispatch is notified
    */
   public Boolean isDispatchNotified();

   /**
    * Sets whether or not the dispatch is notified.
    * 
    * @param val
    * 			the dispatch is notified value
    */
   public void setDispatchNotified(Boolean val);

   /**
    * Returns whether or not itinerary is received.
    * 
    * @return 
    * 		the itinerary is received value
    */
   public Boolean isItineraryReceived();
   
   /**
    * Sets whether or not the itinerary is received.
    * 
    * @param val
    * 			the itinerary is received value
    */
   public void setItineraryReceived(Boolean val);
 
   /**
    * Sets the air travel remarks.
    * 
    * @param val
    * 		the air travel remarks to set
    */
   public void setRemarks(String val);
   
   /**
    * Returns the air travel remarks.
    * 
    * @return 
    * 		the air travel remarks to return
    */
   public String getRemarks();

   /**
    * Sets the air travel flight number.
    * 
    * @param val
    * 		the flight number to set
    */
   public void setFlightNumber(String val);
   
   /**
    * Returns the air travel flight number.
    * 
    * @return 
    * 		the flight number to return
    */
   public String getFlightNumber();

   /**
    * Sets the flight time.
    * 
    * @param val
    * 		the flight time to set
    */
   public void setFlightTime(Integer val);
   
   /**
    * Returns the flight time.
    * 
    * @return 
    * 		the flight time to return
    */
   public Integer getFlightTime();

   /**
    * Sets the hours to airport.
    * 
    * @param val
    * 		the hours to airport to set
    */
   public void setHoursToAirport(Integer val);
   
   /**
    * Returns the hours to airport.
    * 
    * @return 
    * 		the hours to airport to return
    */
   public Integer getHoursToAirport();

   /**
    * Sets the minutes to airport.
    * 
    * @param val
    * 		the minutes to airport to set
    */
   public void setMinutesToAirport(Integer val);
   
   /**
    * Returns the minutes to airport.
    * 
    * @return 
    * 		the minutes to airport to return
    */
   public Integer getMinutesToAirport();

   /**
    * Sets the leave time.
    * 
    * @param val
    * 		the leave time to set
    */
   public void setLeaveTime(Integer val);
   
   /**
    * Returns the leave time.
    * 
    * @return 
    * 		the leave time to return
    */
   public Integer getLeaveTime();
 
   /**
    * Returns the workPeriod.
    *
    * @return 
    *		the workPeriod to return
    */
   public WorkPeriod getWorkPeriod();


   /**
    * Sets the workPeriod.
    *
    * @param workPeriod 
    *			the workPeriod to set
    */
   public void setWorkPeriod(WorkPeriod workPeriod);
}