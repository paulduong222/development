package gov.nwcg.isuite.core.financial.calculator;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.financial.calculator.DailyTimeCalculator;

public class OtherDailyTimeCalculator implements DailyTimeCalculator {

  @Override
  public void applyGuarantees(
      Map<Long, Map<Long, Map<Date, Collection<DailyTimePostTotal>>>> incMap,
      Collection<IncidentResourceVo> incidentResource, Collection<Date> dates) {
    // TODO Auto-generated method stub
  }

  @Override
  public Map<Long, Map<Long, Map<Date, Collection<DailyTimePostTotal>>>> calculateDailyTotals(
      Collection<IncidentResourceVo> incidentResource, Collection<Date> dates) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void calculateTimePostInvoicedAmount(IncidentResourceVo irVo) {
    // TODO Auto-generated method stub
    
  }

}
