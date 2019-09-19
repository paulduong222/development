package gov.nwcg.isuite.core.financial.calculator;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.financial.calculator.DailyTimeCalculator;

public class NonContractorDailyTimeCalculator implements DailyTimeCalculator  {
  
  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.framework.financial.calculator.DailyTimeCalculator#calculateTimePostInvoicedAmount(java.util.Collection)
   */
  public void calculateTimePostInvoicedAmount(IncidentResourceVo irVo) {

		// evaluate for each incident
			for (AssignmentVo a : irVo.getWorkPeriodVo().getAssignmentVos()) {
				for (AssignmentTimePostVo atp : a.getAssignmentTimeVo().getAssignmentTimePostVos()) {
					BigDecimal qty = atp.getQuantity() != null ? atp.getQuantity() : new BigDecimal(0.0);
					BigDecimal rt = atp.getRateAmount() != null ? atp.getRateAmount() : new BigDecimal(0.0);
					BigDecimal invoiced = qty.multiply(rt);
					atp.setInvoicedAmount(invoiced);
				}
			}
	}

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.framework.financial.calculator.DailyTimeCalculator#applyGuarantees(java.util.Map, java.util.Collection, java.util.Collection)
   */
  @Override
  public void applyGuarantees(
      Map<Long, Map<Long, Map<Date, Collection<DailyTimePostTotal>>>> incMap,
      Collection<IncidentResourceVo> incidentResource, Collection<Date> dates) {
    // not applicable to non-contracted resources
  }

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.framework.financial.calculator.DailyTimeCalculator#calculateDailyTotals(java.util.Collection, java.util.Collection)
   */
  @Override
  public Map<Long, Map<Long, Map<Date, Collection<DailyTimePostTotal>>>> calculateDailyTotals(
      Collection<IncidentResourceVo> incidentResource, Collection<Date> dates) {
    // not applicable to non-contracted resources
    return null;
  }
  
}
