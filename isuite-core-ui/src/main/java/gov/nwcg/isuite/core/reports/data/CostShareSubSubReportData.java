package gov.nwcg.isuite.core.reports.data;

import java.util.Comparator;
import org.apache.commons.lang.StringUtils;


public class CostShareSubSubReportData extends CostShareReportData  {
	private static final String SUMMARY ="summary";
	private static final String WORKSHEET ="worksheet";
	private static final String SHIFTKIND ="shiftkind";
	private static final String DETAIL ="detail";
	
	private String agency_01;
	private String agency_02;
	private String agency_03;  
	private String agency_04;
	private String agency_05;
	private Double cost_01 = 0.0;
	private Double cost_02 = 0.0;
	private Double cost_03 = 0.0;
	private Double cost_04 = 0.0;
	private Double cost_05 = 0.0;
//	private int percentage_01 = 0;
//	private int percentage_02 = 0;
//	private int percentage_03 = 0;
//	private int percentage_04 = 0;
//	private int percentage_05 = 0;
	private Double percentage_01 = 0.0;
	private Double percentage_02 = 0.0;
	private Double percentage_03 = 0.0;
	private Double percentage_04 = 0.0;
	private Double percentage_05 = 0.0;
	
    private String incidentName;
    private String lastCategory;    
    private Double total = 0.0;
    private Double total_01 = 0.0;
	private Double total_02 = 0.0;
	private Double total_03 = 0.0;
	private Double total_04 = 0.0;
	private Double total_05 = 0.0;

	//Aircraft
	private Double a_total = 0.0;
    private Double a_total_01 = 0.0;
	private Double a_total_02 = 0.0;
	private Double a_total_03 = 0.0;
	private Double a_total_04 = 0.0;
	private Double a_total_05 = 0.0;
	//Crew & Equipment
	private Double ce_total = 0.0;
    private Double ce_total_01 = 0.0;
	private Double ce_total_02 = 0.0;
	private Double ce_total_03 = 0.0;
	private Double ce_total_04 = 0.0;
	private Double ce_total_05 = 0.0;
	//Support & Overhead
	private Double so_total = 0.0;
    private Double so_total_01 = 0.0;
	private Double so_total_02 = 0.0;
	private Double so_total_03 = 0.0;
	private Double so_total_04 = 0.0;
	private Double so_total_05 = 0.0;
		
	public CostShareSubSubReportData(){
		
	}
	
	public Double getA_total() {
		return a_total;
	}

	public void setA_total(Double a_total) {
		this.a_total = a_total;
	}

	public Double getA_total_01() {
		return a_total_01;
	}

	public void setA_total_01(Double a_total_01) {
		this.a_total_01 = a_total_01;
	}

	public Double getA_total_02() {
		return a_total_02;
	}

	public void setA_total_02(Double a_total_02) {
		this.a_total_02 = a_total_02;
	}

	public Double getA_total_03() {
		return a_total_03;
	}

	public void setA_total_03(Double a_total_03) {
		this.a_total_03 = a_total_03;
	}

	public Double getA_total_04() {
		return a_total_04;
	}

	public void setA_total_04(Double a_total_04) {
		this.a_total_04 = a_total_04;
	}

	public Double getA_total_05() {
		return a_total_05;
	}

	public void setA_total_05(Double a_total_05) {
		this.a_total_05 = a_total_05;
	}

	public Double getCe_total() {
		return ce_total;
	}

	public void setCe_total(Double ce_total) {
		this.ce_total = ce_total;
	}

	public Double getCe_total_01() {
		return ce_total_01;
	}

	public void setCe_total_01(Double ce_total_01) {
		this.ce_total_01 = ce_total_01;
	}

	public Double getCe_total_02() {
		return ce_total_02;
	}

	public void setCe_total_02(Double ce_total_02) {
		this.ce_total_02 = ce_total_02;
	}

	public Double getCe_total_03() {
		return ce_total_03;
	}

	public void setCe_total_03(Double ce_total_03) {
		this.ce_total_03 = ce_total_03;
	}

	public Double getCe_total_04() {
		return ce_total_04;
	}

	public void setCe_total_04(Double ce_total_04) {
		this.ce_total_04 = ce_total_04;
	}

	public Double getCe_total_05() {
		return ce_total_05;
	}

	public void setCe_total_05(Double ce_total_05) {
		this.ce_total_05 = ce_total_05;
	}

	public Double getSo_total() {
		return so_total;
	}

	public void setSo_total(Double so_total) {
		this.so_total = so_total;
	}

	public Double getSo_total_01() {
		return so_total_01;
	}

	public void setSo_total_01(Double so_total_01) {
		this.so_total_01 = so_total_01;
	}

	public Double getSo_total_02() {
		return so_total_02;
	}

	public void setSo_total_02(Double so_total_02) {
		this.so_total_02 = so_total_02;
	}

	public Double getSo_total_03() {
		return so_total_03;
	}

	public void setSo_total_03(Double so_total_03) {
		this.so_total_03 = so_total_03;
	}

	public Double getSo_total_04() {
		return so_total_04;
	}

	public void setSo_total_04(Double so_total_04) {
		this.so_total_04 = so_total_04;
	}

	public Double getSo_total_05() {
		return so_total_05;
	}

	public void setSo_total_05(Double so_total_05) {
		this.so_total_05 = so_total_05;
	}

    public String getLastCategory() {
		return lastCategory;
	}

	public void setLastCategory(String lastCategory) {
		this.lastCategory = lastCategory;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTotal_01() {
		return total_01;
	}

	public void setTotal_01(Double total_01) {
		this.total_01 = total_01;
	}

	public Double getTotal_02() {
		return total_02;
	}

	public void setTotal_02(Double total_02) {
		this.total_02 = total_02;
	}

	public Double getTotal_03() {
		return total_03;
	}

	public void setTotal_03(Double total_03) {
		this.total_03 = total_03;
	}

	public Double getTotal_04() {
		return total_04;
	}

	public void setTotal_04(Double total_04) {
		this.total_04 = total_04;
	}

	public Double getTotal_05() {
		return total_05;
	}

	public void setTotal_05(Double total_05) {
		this.total_05 = total_05;
	}
	
	public String getIncidentName() {
		return incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getAgency_01() {
		return agency_01;
	}

	public void setAgency_01(String agency_01) {
		this.agency_01 = agency_01;
	}

	public String getAgency_02() {
		return agency_02;
	}

	public void setAgency_02(String agency_02) {
		this.agency_02 = agency_02;
	}


	public String getAgency_03() {
		return agency_03;
	}


	public void setAgency_03(String agency_03) {
		this.agency_03 = agency_03;
	}


	public String getAgency_04() {
		return agency_04;
	}


	public void setAgency_04(String agency_04) {
		this.agency_04 = agency_04;
	}


	public String getAgency_05() {
		return agency_05;
	}


	public void setAgency_05(String agency_05) {
		this.agency_05 = agency_05;
	}


	public Double getCost_01() {
		return cost_01;
	}


	public void setCost_01(Double cost_01) {
		this.cost_01 = cost_01;
	}


	public Double getCost_02() {
		return cost_02;
	}


	public void setCost_02(Double cost_02) {
		this.cost_02 = cost_02;
	}


	public Double getCost_03() {
		return cost_03;
	}


	public void setCost_03(Double cost_03) {
		this.cost_03 = cost_03;
	}


	public Double getCost_04() {
		return cost_04;
	}


	public void setCost_04(Double cost_04) {
		this.cost_04 = cost_04;
	}


	public Double getCost_05() {
		return cost_05;
	}


	public void setCost_05(Double cost_05) {
		this.cost_05 = cost_05;
	}


//	public int getPercentage_01() {
//		return percentage_01;
//	}
//
//
//	public void setPercentage_01(int percentage_01) {
//		this.percentage_01 = percentage_01;
//	}
//
//
//	public int getPercentage_02() {
//		return percentage_02;
//	}
//
//
//	public void setPercentage_02(int percentage_02) {
//		this.percentage_02 = percentage_02;
//	}
//
//
//	public int getPercentage_03() {
//		return percentage_03;
//	}
//
//
//	public void setPercentage_03(int percentage_03) {
//		this.percentage_03 = percentage_03;
//	}
//
//
//	public int getPercentage_04() {
//		return percentage_04;
//	}
//
//
//	public void setPercentage_04(int percentage_04) {
//		this.percentage_04 = percentage_04;
//	}
//
//
//	public int getPercentage_05() {
//		return percentage_05;
//	}
//
//
//	public void setPercentage_05(int percentage_05) {
//		this.percentage_05 = percentage_05;
//	}

	
	public static Comparator<CostShareReportData> getCOMPARATOR__REQUEST_NUMBER_IGNORE_CASE_NULL_LAST() {
		return COMPARATOR__REQUEST_NUMBER_IGNORE_CASE_NULL_LAST;
	}

//	public Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
	
	public Double getPercentage_01() {
		return percentage_01;
	}

	public void setPercentage_01(Double percentage_01) {
		this.percentage_01 = percentage_01;
	}

	public Double getPercentage_02() {
		return percentage_02;
	}

	public void setPercentage_02(Double percentage_02) {
		this.percentage_02 = percentage_02;
	}

	public Double getPercentage_03() {
		return percentage_03;
	}

	public void setPercentage_03(Double percentage_03) {
		this.percentage_03 = percentage_03;
	}

	public Double getPercentage_04() {
		return percentage_04;
	}

	public void setPercentage_04(Double percentage_04) {
		this.percentage_04 = percentage_04;
	}

	public Double getPercentage_05() {
		return percentage_05;
	}

	public void setPercentage_05(Double percentage_05) {
		this.percentage_05 = percentage_05;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(CostShareSubSubReportData obj, String reportName) {
		
		//summary,shift kind,detail,worksheet 
		if(this.getCostShareDate()!=null && !this.getCostShareDate().equals(obj.getCostShareDate()))
			return false;
	 
		if(this.getDailyCost()!=null && !this.getDailyCost().equals(obj.getDailyCost()))
			return false;
	
		//summary,shift kind,detail
		//not worksheet
		if(!WORKSHEET.equals(reportName) && this.getCategory()!=null && !this.getCategory().equals(obj.getCategory()))
			return false;
		
		//summary,shift kind,detail
		//not worksheet
		if(!WORKSHEET.equals(reportName) && this.getShift()!=null && !this.getShift().equals(obj.getShift()))
			return false;
		
		// shift kind, detail, worksheet
		//not summary
		if(!SUMMARY.equals(reportName) && this.getItemCode()!=null && !this.getItemCode().equals(obj.getItemCode()))
			return false;
		
		//detail, worksheet
		if((DETAIL.equals(reportName) || WORKSHEET.equals(reportName)) && this.getCostGroup()!=null && !this.getCostGroup().equals(obj.getCostGroup()))
			return false;
		
		//detail, worksheet
		if((DETAIL.equals(reportName) || WORKSHEET.equals(reportName)) && this.getResourceName()!=null && !this.getResourceName().equals(obj.getResourceName()))
			return false;
		
		//detail, worksheet
		if((DETAIL.equals(reportName) || WORKSHEET.equals(reportName)) && this.getResourceAgency()!=null && !this.getResourceAgency().equals(obj.getResourceAgency()))
			return false;
		
		//workshet
		if(!SUMMARY.equals(reportName) && this.getQty() != obj.getQty())
			return false;
		
		return true;
	}   
	
	
	
	/**
     */
    public static final Comparator<CostShareReportData> COMPARATOR__REQUEST_NUMBER_IGNORE_CASE_NULL_LAST = new Comparator<CostShareReportData>() {
        public int compare(CostShareReportData o1, CostShareReportData o2) {
            String name1 = StringUtils.lowerCase(o1.getCategory());
            String name2 = StringUtils.lowerCase(o2.getCategory());
            return compareStr(name1, name2);
        }
    };
    
    private static int compareStr(String o1, String o2) {
    	if (o1 == null && o2 == null) {
    		return 0;
    	}

    	if (o1 == null && o2 != null) {
    		return 1;
    	}

    	if (o1 != null && o2 == null) {
    		return -1;
    	}
    	return o1.compareTo(o2);
    }
    
	public void setCostAndPercentage() {
		this.cost_01 = getCost();
		//this.percentage_01 = getPercentage().intValue();
		this.percentage_01 = getPercentage().doubleValue();
	}
    
	public void setCostAndPercentage(Double cost, int percentage, int order) {
		
		if(order == 0) {
			//this.agency_01 = agency;
			this.cost_01 = this.cost_01 + cost;
			this.percentage_01 = this.percentage_01 + percentage;
		}
		else if(order == 1) {
			//this.agency_02 = agency;
			this.cost_02 = this.cost_02 + cost;
			this.percentage_02 = this.percentage_02 + percentage;
		}
		else if(order == 2) {
			//this.agency_03 = agency;
			this.cost_03 = this.cost_03 + cost;
			this.percentage_03 = this.percentage_03 + percentage;
		}
		else if(order == 3) {
			//this.agency_04 = agency;
			this.cost_04 = this.cost_04 + cost;
			this.percentage_04 = this.percentage_04 + percentage;
		}
		else if(order == 4) {
			//this.agency_05 = agency;
			this.cost_05 = this.cost_05 + cost;
			this.percentage_05 = this.percentage_05 + percentage;
		}
	}
	
	public void setAgencyName(String agency, int order) {
		
		if(order == 0) {
			this.agency_01 = agency;
		}
		else if(order == 1) {
			this.agency_02 = agency;
		}
		else if(order == 2) {
			this.agency_03 = agency;
		}
		else if(order == 3) {
			this.agency_04 = agency;
		}
		else if(order == 4) {
			this.agency_05 = agency;
		}
	}
}
