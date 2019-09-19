package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.List;

public class GlidePathReportSummaryData {
	
	private String sectionName;
	private List<Integer> dayValues = new ArrayList<Integer>(); 

	// Should really never be needed because Jasper's printWhenExpression should be 
	// such that only the required number of day values are printed.
	public static final Integer NA = 0; 
	
	public GlidePathReportSummaryData(String sectionName){
		this.sectionName = sectionName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public List<Integer> getDayValues() {
		return dayValues;
	}

	public void setDayValues(List<Integer> dayValues) {
		this.dayValues = dayValues;
	}

	public Integer getDay1() { return (dayValues.size()>0)? dayValues.get(0):NA; }
	public Integer getDay2() { return (dayValues.size()>1)? dayValues.get(1):NA; }
	public Integer getDay3() { return (dayValues.size()>2)? dayValues.get(2):NA; }
	public Integer getDay4() { return (dayValues.size()>3)? dayValues.get(3):NA; }
	public Integer getDay5() { return (dayValues.size()>4)? dayValues.get(4):NA; }
	public Integer getDay6() { return (dayValues.size()>5)? dayValues.get(5):NA; }
	public Integer getDay7() { return (dayValues.size()>6)? dayValues.get(6):NA; }
	public Integer getDay8() { return (dayValues.size()>7)? dayValues.get(7):NA; }
	public Integer getDay9() { return (dayValues.size()>8)? dayValues.get(8):NA; }
	public Integer getDay10() { return (dayValues.size()>9)? dayValues.get(9):NA; }
	public Integer getDay11() { return (dayValues.size()>10)? dayValues.get(10):NA; }
	public Integer getDay12() { return (dayValues.size()>11)? dayValues.get(11):NA; }
	public Integer getDay13() { return (dayValues.size()>12)? dayValues.get(12):NA; }
	public Integer getDay14() { return (dayValues.size()>13)? dayValues.get(13):NA; }
	public Integer getDay15() { return (dayValues.size()>14)? dayValues.get(14):NA; }
	public Integer getDay16() { return (dayValues.size()>15)? dayValues.get(15):NA; }
	public Integer getDay17() { return (dayValues.size()>16)? dayValues.get(16):NA; }
	public Integer getDay18() { return (dayValues.size()>17)? dayValues.get(17):NA; }
	public Integer getDay19() { return (dayValues.size()>18)? dayValues.get(18):NA; }
	public Integer getDay20() { return (dayValues.size()>19)? dayValues.get(19):NA; }
	public Integer getDay21() { return (dayValues.size()>20)? dayValues.get(20):NA; }
	public Integer getDay22() { return (dayValues.size()>21)? dayValues.get(21):NA; }
	public Integer getDay23() { return (dayValues.size()>22)? dayValues.get(22):NA; }
	public Integer getDay24() { return (dayValues.size()>23)? dayValues.get(23):NA; }
	public Integer getDay25() { return (dayValues.size()>24)? dayValues.get(24):NA; }
	public Integer getDay26() { return (dayValues.size()>25)? dayValues.get(25):NA; }
	public Integer getDay27() { return (dayValues.size()>26)? dayValues.get(26):NA; }
	public Integer getDay28() { return (dayValues.size()>27)? dayValues.get(27):NA; }
	public Integer getDay29() { return (dayValues.size()>28)? dayValues.get(28):NA; }
	public Integer getDay30() { return (dayValues.size()>29)? dayValues.get(29):NA; }
}
