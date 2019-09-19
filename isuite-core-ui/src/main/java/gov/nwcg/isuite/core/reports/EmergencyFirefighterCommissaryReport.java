package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.EmergencyFirefighterCommissaryReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class EmergencyFirefighterCommissaryReport extends BaseReport implements IReport {

   private static final String REPORT_NAME="EmergencyFirefighterCommissaryReportV2a";
   private static final String SUB_REPORT_NAME="EmergencyFirefighterCommissaryReport_subreport12a";
   
   public EmergencyFirefighterCommissaryReport(Collection<EmergencyFirefighterCommissaryReportData> data) {
      super();
      super.setHeaderTitle("");
      //super.setHeaderSubTitle("");
      super.setReportData(data);
      super.addSubReportName(SUB_REPORT_NAME);
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.framework.report.BaseReport#getReportName()
    */
   public String getReportName() {
      return EmergencyFirefighterCommissaryReport.REPORT_NAME;
   }
}
