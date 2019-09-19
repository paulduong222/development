package gov.nwcg.isuite.framework.financial.calculator;

import gov.nwcg.isuite.core.financial.calculator.DailyTimePostTotal;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public interface DailyTimeCalculator {

  /**
   *  Calculate the total daily earned amount for a set of time postings,
   *  based on incident, accounting code, date, and defined rate.
   *  
   *  Return a nested map that has the DailyTimePostTotal for each rate, 
   *  for each date, for each account code, for each incident. 
   * 
   * @param incidentResources
   * @param dates
   * @return Map<IncidentId, Map<AcctCodeId, Map<Date, Collection<DailyTimePostTotal>>>>
   */
  Map<Long, Map<Long, Map<Date, Collection<DailyTimePostTotal>>>> calculateDailyTotals(Collection<IncidentResourceVo> incidentResources, Collection<Date> dates);
  
  /**
   * Accept the calculated daily total nested map and apply the applicable guarantee as applicable;
   * 
   * Set the total daily invoice amount to the last time post for the date, for the accounting code
   * 
   * @param incMap
   * @param incidentResources
   * @param dates
   */
  void applyGuarantees(Map<Long, Map<Long, Map<Date, Collection<DailyTimePostTotal>>>> incMap, Collection<IncidentResourceVo> incidentResources, Collection<Date> dates);

  /**
   * Calculate the earned amount for individual time postings and apply it to the invoicedAmount field.
   * 
   * @param incidentResources
   * @param dates
   */
  void calculateTimePostInvoicedAmount(IncidentResourceVo irVo);
  
}
