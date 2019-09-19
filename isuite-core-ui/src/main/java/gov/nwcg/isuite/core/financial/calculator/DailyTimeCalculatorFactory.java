package gov.nwcg.isuite.core.financial.calculator;

import gov.nwcg.isuite.framework.financial.calculator.DailyTimeCalculator;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;

public class DailyTimeCalculatorFactory {

  public DailyTimeCalculator getCalculatorInstance(EmploymentTypeEnum employeeType) {
    DailyTimeCalculator dtc = null;
    if(employeeType != null) {
      if(employeeType == EmploymentTypeEnum.AD) {
        dtc = new AdDailyTimeCalculator();
      } else if(employeeType == EmploymentTypeEnum.FED) {
        dtc = new FedDailyTimeCalculator();
      } else if(employeeType == EmploymentTypeEnum.OTHER) {
        dtc = new OtherDailyTimeCalculator();
      } else if(employeeType == EmploymentTypeEnum.CONTRACTOR) {
        dtc = new ContractorDailyTimeCalculator();
      }
    } else {
      dtc = new NonContractorDailyTimeCalculator();
    }
    
    return dtc;
  }
}
