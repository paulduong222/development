package gov.nwcg.isuite.core.reports.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.util.Collection;

public class CostProjectionCategoryDetailSubReportData  extends  CostProjectionSubReportData{
	
	private String day_01= "";
	private String day_02= "";
	private String day_03= "";
	private String day_04= "";
	private String day_05= "";
	private String day_06= "";
	private String day_07= "";
	private String day_08= "Total";
	private Double costAmount_01=0.00;
	private Double costAmount_02=0.00;
	private Double costAmount_03=0.00;
	private Double costAmount_04=0.00;
	private Double costAmount_05=0.00;
	private Double costAmount_06=0.00;
	private Double costAmount_07=0.00;
	private Double costAmount_08=0.00;
	private Integer quantity_01;
	private Integer quantity_02;
	private Integer quantity_03;
	private Integer quantity_04;
	private Integer quantity_05;
	private Integer quantity_06;
	private Integer quantity_07;
	private Integer quantity_08=0;
	private Double total;
	private int week;
	public SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	public String getDay_01() {
		return day_01;
	}
	public void setDay_01(String day_01) {
		this.day_01 = day_01;
	}
	public String getDay_02() {
		return day_02;
	}
	public void setDay_02(String day_02) {
		this.day_02 = day_02;
	}
	public String getDay_03() {
		return day_03;
	}
	public void setDay_03(String day_03) {
		this.day_03 = day_03;
	}
	public String getDay_04() {
		return day_04;
	}
	public void setDay_04(String day_04) {
		this.day_04 = day_04;
	}
	public String getDay_05() {
		return day_05;
	}
	public void setDay_05(String day_05) {
		this.day_05 = day_05;
	}
	public String getDay_06() {
		return day_06;
	}
	public void setDay_06(String day_06) {
		this.day_06 = day_06;
	}
	public String getDay_07() {
		return day_07;
	}
	public void setDay_07(String day_07) {
		this.day_07 = day_07;
	}
	public String getDay_08() {
		return day_08;
	}
	public Double getCostAmount_01() {
		return costAmount_01;
	}
	public void setCostAmount_01(Double costAmount_01) {
		this.costAmount_01 = costAmount_01;
	}
	public Double getCostAmount_02() {
		return costAmount_02;
	}
	public void setCostAmount_02(Double costAmount_02) {
		this.costAmount_02 = costAmount_02;
	}
	public Double getCostAmount_03() {
		return costAmount_03;
	}
	public void setCostAmount_03(Double costAmount_03) {
		this.costAmount_03 = costAmount_03;
	}
	public Double getCostAmount_04() {
		return costAmount_04;
	}
	public void setCostAmount_04(Double costAmount_04) {
		this.costAmount_04 = costAmount_04;
	}
	public Double getCostAmount_05() {
		return costAmount_05;
	}
	public void setCostAmount_05(Double costAmount_05) {
		this.costAmount_05 = costAmount_05;
	}
	public Double getCostAmount_06() {
		return costAmount_06;
	}
	public void setCostAmount_06(Double costAmount_06) {
		this.costAmount_06 = costAmount_06;
	}
	public Double getCostAmount_07() {
		return costAmount_07;
	}
	public void setCostAmount_07(Double costAmount_07) {
		this.costAmount_07 = costAmount_07;
	}
	public Double getCostAmount_08() {
		if(this.costAmount_01 != null)
			this.costAmount_08 = this.costAmount_08 + this.costAmount_01; 
		if(this.costAmount_02 != null)
			this.costAmount_08 = this.costAmount_08 + this.costAmount_02; 
		if(this.costAmount_03 != null)
			this.costAmount_08 = this.costAmount_08 + this.costAmount_03; 
		if(this.costAmount_04 != null)
			this.costAmount_08 = this.costAmount_08 + this.costAmount_04; 
		if(this.costAmount_05 != null)
			this.costAmount_08 = this.costAmount_08 + this.costAmount_05; 
		if(this.costAmount_06 != null)
			this.costAmount_08 = this.costAmount_08 + this.costAmount_06; 
		if(this.costAmount_07 != null)
			this.costAmount_08 = this.costAmount_08 + this.costAmount_07; 
		return costAmount_08;
	}
	public Integer getQuantity_01() {
		return quantity_01;
	}
	public void setQuantity_01(Integer quantity_01) {
		this.quantity_01 = quantity_01;
	}
	public Integer getQuantity_02() {
		return quantity_02;
	}
	public void setQuantity_02(Integer quantity_02) {
		this.quantity_02 = quantity_02;
	}
	public Integer getQuantity_03() {
		return quantity_03;
	}
	public void setQuantity_03(Integer quantity_03) {
		this.quantity_03 = quantity_03;
	}
	public Integer getQuantity_04() {
		return quantity_04;
	}
	public void setQuantity_04(Integer quantity_04) {
		this.quantity_04 = quantity_04;
	}
	public Integer getQuantity_05() {
		return quantity_05;
	}
	public void setQuantity_05(Integer quantity_05) {
		this.quantity_05 = quantity_05;
	}
	public Integer getQuantity_06() {
		return quantity_06;
	}
	public void setQuantity_06(Integer quantity_06) {
		this.quantity_06 = quantity_06;
	}
	public Integer getQuantity_07() {
		return quantity_07;
	}
	public Integer getQuantity_08() {
		if(this.quantity_01 != null)
			this.quantity_08 = this.quantity_08 + this.quantity_01; 
		if(this.quantity_02 != null)
			this.quantity_08 = this.quantity_08 + this.quantity_02; 
		if(this.quantity_03 != null)
			this.quantity_08 = this.quantity_08 + this.quantity_03; 
		if(this.quantity_04 != null)
			this.quantity_08 = this.quantity_08 + this.quantity_04; 
		if(this.quantity_05 != null)
			this.quantity_08 = this.quantity_08 + this.quantity_05; 
		if(this.quantity_06 != null)
			this.quantity_08 = this.quantity_08 + this.quantity_06; 
		if(this.quantity_07 != null)
			this.quantity_08 = this.quantity_08 + this.quantity_07; 
		return this.quantity_08;
	}
	public void setQuantity_07(Integer quantity_07) {
		this.quantity_07 = quantity_07;
	}
	
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public void setDays(List<String> days, Date endDate) {
		int counter = 7;
		String d = dateFormat.format(endDate);
		
		//reset counter if the days is less than 7 days 
		for(int i=0; i < 7; i++) {
			if(d.equals(days.get(i))) 
				counter = i+1;
		}	
		
		for(int i=0; i < counter; i++) {
			if(i == 0)
				day_01 = days.get(i);
			else if (i == 1)
				day_02 = days.get(i);
			else if (i == 2)
				day_03 = days.get(i);
			else if (i == 3)
				day_04 = days.get(i);
			else if (i == 4)
				day_05 = days.get(i);
			else if (i == 5)
				day_06 = days.get(i);
			else if (i == 6)
				day_07 = days.get(i);		
		}
	}
}
