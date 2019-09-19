package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

public class Tnsp3SubReportData {
	private String agency;
	private String commandCount;
	private String operationsCount;
	private String plansCount;
	private String logisticsCount;
	private String financeCount;
	private String externalCount;
	private String totalCount;
	
	public static Tnsp3SubReportData createTotalRow(Collection<Tnsp3SubReportData> list){
		Tnsp3SubReportData totalRow=new Tnsp3SubReportData();
		totalRow.setAgency("Total");
		int cCount=0;
		int oCount=0;
		int pCount=0;
		int lCount=0;
		int fCount=0;
		int eCount=0;
		int tCount=0;
		for(Tnsp3SubReportData d : list){
			cCount=cCount+Integer.parseInt(d.getCommandCount());
			oCount=oCount+Integer.parseInt(d.getOperationsCount());
			pCount=pCount+Integer.parseInt(d.getPlansCount());
			lCount=lCount+Integer.parseInt(d.getLogisticsCount());
			fCount=fCount+Integer.parseInt(d.getFinanceCount());
			eCount=eCount+Integer.parseInt(d.getExternalCount());
			tCount=tCount+Integer.parseInt(d.getTotalCount());
		}
		totalRow.setCommandCount(String.valueOf(cCount));
		totalRow.setOperationsCount(String.valueOf(oCount));
		totalRow.setPlansCount(String.valueOf(pCount));
		totalRow.setLogisticsCount(String.valueOf(lCount));
		totalRow.setCommandCount(String.valueOf(cCount));
		totalRow.setFinanceCount(String.valueOf(fCount));
		totalRow.setExternalCount(String.valueOf(eCount));
		totalRow.setTotalCount(String.valueOf(tCount));
		return totalRow;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getCommandCount() {
		return commandCount;
	}
	public void setCommandCount(String commandCount) {
		this.commandCount = commandCount;
	}
	public String getOperationsCount() {
		return operationsCount;
	}
	public void setOperationsCount(String operationsCount) {
		this.operationsCount = operationsCount;
	}
	public String getPlansCount() {
		return plansCount;
	}
	public void setPlansCount(String plansCount) {
		this.plansCount = plansCount;
	}
	public String getLogisticsCount() {
		return logisticsCount;
	}
	public void setLogisticsCount(String logisticsCount) {
		this.logisticsCount = logisticsCount;
	}
	public String getFinanceCount() {
		return financeCount;
	}
	public void setFinanceCount(String financeCount) {
		this.financeCount = financeCount;
	}
	public String getExternalCount() {
		return externalCount;
	}
	public void setExternalCount(String externalCount) {
		this.externalCount = externalCount;
	}
	public String getTotalCount() {
		int t=Integer.parseInt((StringUtility.hasValue(logisticsCount) ? logisticsCount : "0"))+
				Integer.parseInt((StringUtility.hasValue(plansCount) ? plansCount : "0"))+
				Integer.parseInt((StringUtility.hasValue(externalCount) ? externalCount : "0"))+
				Integer.parseInt((StringUtility.hasValue(commandCount) ? commandCount : "0"))+
				Integer.parseInt((StringUtility.hasValue(operationsCount) ? operationsCount : "0"))+
				Integer.parseInt((StringUtility.hasValue(financeCount) ? financeCount : "0"));
		return String.valueOf(t);
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
}
