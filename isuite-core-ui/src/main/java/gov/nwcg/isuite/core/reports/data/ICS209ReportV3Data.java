package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.core.vo.ICS209ResourceData;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;



/**
 * Report data object for ICS209ReportV3.jrxml.
 */
public class ICS209ReportV3Data {
	
	public static final String NO_AGENCY_CODE = "No Agency";
	private Long incidentId;
	private String pagesubtitle;
	
	private String columnName1;
	private String columnName2;
	private String columnName3;
	private String columnName4;
	private String columnName5;
	private String columnName6;
	private String columnName7;
	private String columnName8;
	private String columnName9;
	private String columnName10;
	private String columnName11;
	private String columnName12;
	private String columnName13;
	private String columnName14;
	private String columnName15;
	private String columnName16;
	private String columnName17;
	private String columnName18;
	private String columnName19;
	private String columnName20;
	private String columnName21;
	private String columnName22;
	private String columnName23;
	private String columnName24;
	private String columnName25;
	private String columnName26;
	private String columnName27;
	private String columnName28;
	private String columnName29;
	private String columnName30;

	private List<ICS209SubReportV3Data> subReportData = new ArrayList<ICS209SubReportV3Data>();
	private JRBeanCollectionDataSource dataSourceReportData;
	
	public ICS209ReportV3Data(){
	}

	public static Collection<ICS209ReportV3Data> buildReportData(Collection<ICS209ResourceData> resourceData,Boolean byIncident, String groupName) {
		Collection<ICS209ReportV3Data> reportDataList = new ArrayList<ICS209ReportV3Data>();
		
		Collection<Long> incidentIds = new ArrayList<Long>();
		if(byIncident==true)
			incidentIds=getIncidentIds(resourceData);
		else{
			incidentIds.add(0L);
		}
		
		for(Long id : incidentIds){
			ICS209ReportV3Data reportData = new ICS209ReportV3Data();
			reportData.setIncidentId(id);
			if(byIncident==true){
				String incidentNumber=getIncidentNumber(id,resourceData);
				reportData.setPagesubtitle(incidentNumber);
			}else
				reportData.setPagesubtitle(groupName+" - Summary");
			
			//reportData.setPagesubtitle("testing");
			
			Collection<Long> processedIds = new ArrayList<Long>();

			// get list of agencies for this incidentId
			Collection<String> agencies = getAgencies(resourceData, id);
			Collection<String> categories = getCategories(resourceData, id);
			
			HashMap<Integer,Integer> runningColumnCountTotals = new HashMap<Integer,Integer>();
			HashMap<Integer,Integer> runningColumnPersonCountTotals = new HashMap<Integer,Integer>();
			
			// Initialize running column totals to 0
			int p=1;
			for(String s : categories){
				runningColumnCountTotals.put(p, 0);
				runningColumnPersonCountTotals.put(p, 0);
				//System.out.println("Category: "+s+" ColumnNumber: "+p);
				p++;
			}
			

			// set the column headers
			for(String agency : agencies){
				ICS209SubReportV3Data subRptData = new ICS209SubReportV3Data();
				subRptData.setAgencyCode(agency);
				
				int x=1;
				for(String category : categories){
					setColumnName(reportData,"ColumnName",x,category);
					x++;
				}
				reportData.getSubReportData().add(subRptData);
			}
			
			// categorize all single resources with no parent
			for(ICS209ResourceData rd : resourceData){
				System.out.println("Resource: "+rd.getResourceId()+ " " + rd.getAgencyCode());
				Boolean bInclude=false;
				Boolean bIsParentStrikeTeam=false;
				
				// always include top-level resources (non strike team) with status of C or P
				if(!LongUtility.hasValue(rd.getResourceParentId())){
					if(id.intValue()==0
							&& isStatusCorP(rd)==true
							&& BooleanUtility.isFalse(rd.getIsStrikeTeam())){
						bInclude=true;
					}else{
						if(id.compareTo(rd.getIncidentId())==0
								&& isStatusCorP(rd)==true
								&& BooleanUtility.isFalse(rd.getIsStrikeTeam())){
							bInclude=true;
						}
					}
				}else{
					// only include resources that are assigned to a toplevel parent strike team
					if(isParentStrikeTeamTopLevel(rd.getResourceParentId(),resourceData)==true){
						bIsParentStrikeTeam=true;
						if(id.intValue()==0
								&& isStatusCorP(rd)==true){
							bInclude=true;
						}else{
							if(id.compareTo(rd.getIncidentId())==0
									&& isStatusCorP(rd)==true){
								bInclude=true;
							}
						}
						
					}
				}
				
				if(bInclude==true){
					String agencyCode=rd.getAgencyCode();
					String category = rd.getSitCategory();
					
					if(category.contains("209") ){
						//System.out.println("");
					}

					for(ICS209SubReportV3Data subRptData : reportData.getSubReportData()){
						if(subRptData.getAgencyCode().equals(agencyCode)){
							int categoryColumn = getCategoryColumn(category,categories);
							int pcount=0;

							// 11/16/2015 - the rule below may change, see qc defect
							
							// if resource has personnel count , use value
							if(IntegerUtility.hasValue(rd.getPersonnelCount())
									&& rd.getPersonnelCount().intValue()>0)
								pcount=rd.getPersonnelCount().intValue();
							else{
								// otherwise pull default personnel count from kind table
								// 2018 - if resource is subordinate of ST or TF and a Non-Person
								if(BooleanUtility.isFalse(rd.getIsPerson())
										&& bIsParentStrikeTeam==true){
									if(IntegerUtility.hasValue(rd.getKindPeopleCount()))
										pcount=rd.getKindPeopleCount().intValue();
								}
							}
							
							Integer colValue=getColumnCount(subRptData,"ColumnCount",categoryColumn);
							Integer colPersValue=getColumnCount(subRptData,"ColumnPersonCount",categoryColumn);
							setColumnCount(subRptData,"ColumnCount",categoryColumn,colValue+1);
							setColumnCount(subRptData,"ColumnPersonCount",categoryColumn,colPersValue+pcount);
							//updateSubReportData(subRptData,categoryColumn,pcount);

							Integer columnCount=(Integer)runningColumnCountTotals.get(categoryColumn);
							columnCount=columnCount+1;
							runningColumnCountTotals.put(categoryColumn, columnCount);

							Integer columnPersonCount=(Integer)runningColumnPersonCountTotals.get(categoryColumn);
							columnPersonCount=columnPersonCount+pcount;
							runningColumnPersonCountTotals.put(categoryColumn, columnPersonCount);
							
						}
						
					}
					
				}
				
				
			}
		
			// categorize all strike team parent resources 
			// todo
			

			for(ICS209SubReportV3Data subRptData : reportData.getSubReportData()){
				// total all of the vertical columns
				if(subRptData.getAgencyCode().trim().equals("TOTAL")){
					for(String c : categories){
						int categoryColumn = getCategoryColumn(c,categories);
						Integer columnCount=(Integer)runningColumnCountTotals.get(categoryColumn);
						Integer columnPersonCount=(Integer)runningColumnPersonCountTotals.get(categoryColumn);
						setColumnCount(subRptData,"ColumnCount",categoryColumn,columnCount);
						setColumnCount(subRptData,"ColumnPersonCount",categoryColumn,columnPersonCount);
					}
				}
				
				
				// get the horizontal subtotal column values
				Integer runningCount=getRunningCount(subRptData,categories.size()-3,"ColumnCount");
				Integer runningPersonCount=getRunningCount(subRptData,categories.size()-3,"ColumnPersonCount");
				int subtotalCol=getCategoryColumn("SUB TOTAL",categories);
				setColumnCount(subRptData,"ColumnCount",subtotalCol,runningCount);
				setColumnCount(subRptData,"ColumnPersonCount",subtotalCol,runningPersonCount);
				
				// get the horizontal total column values
				Integer colValue=getColumnCount(subRptData,"ColumnCount",subtotalCol);
				Integer colPersonValue=getColumnCount(subRptData,"ColumnPersonCount",subtotalCol);
				
				int non209Col=getCategoryColumn("Non 209",categories);
				Integer colValue2=getColumnCount(subRptData,"ColumnCount",non209Col);
				Integer colPersonValue2=getColumnCount(subRptData,"ColumnPersonCount",non209Col);
				
				int totalCol=getCategoryColumn("GRAND TOTAL",categories);
				setColumnCount(subRptData,"ColumnCount",totalCol,(colValue+colValue2));
				setColumnCount(subRptData,"ColumnPersonCount",totalCol,(colPersonValue+colPersonValue2));
				
				
				// set unused columns to null
				int catSize=categories.size();
				int n=1;
				for(String s : categories){
					if(!StringUtility.hasValue(s)){
						setColumnCount(subRptData,"ColumnCount",n,-1);
						setColumnCount(subRptData,"ColumnPersonCount",n,-1);
					}
					n++;
				}
				/*
				for(int n=catSize+1;n<=30;n++){
					setColumnCount(subRptData,"ColumnCount",n,-1);
					setColumnCount(subRptData,"ColumnPersonCount",n,-1);
				}
				*/
			}
			
			reportDataList.add(reportData);
		}
		
		return reportDataList;
	}

	private static Integer getColumnCount(ICS209SubReportV3Data subRptData,String field, int colNumber) {
		try{
			Class cls = Class.forName(subRptData.getClass().getName());
			if(null != subRptData){
				Method method = cls.getMethod("get"+field+colNumber);
				if(null != method){
					Integer val=(Integer)method.invoke(subRptData);
					return val;
				}
			}
		}catch(Exception e){
			
		}
		
		return 0;
	}

	private static Integer getRunningCount(ICS209SubReportV3Data subRptData,int maxColumnNum,String field) {
		Integer total=0;
		
		try{
			
			Class cls = Class.forName(subRptData.getClass().getName());
			if(null != subRptData){
				for(int i=1;i<=maxColumnNum;i++){
					Method method = cls.getMethod("get"+field+i);
					if(null != method){
						Integer val=(Integer)method.invoke(subRptData);
						total=total+val;
					}
				}
			}
		}catch(Exception e){
			System.out.println("");
		}
		
		return total;
	}
	
	private static void setColumnCount(ICS209SubReportV3Data subRptData,String field, int colNumber, Integer val) {
		try{
			Class cls = Class.forName(subRptData.getClass().getName());
			if(null != subRptData){
				Method method = cls.getMethod("set"+field+colNumber,Integer.class);
				if(null != method){
					method.invoke(subRptData,val);
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

	private static void setColumnName(ICS209ReportV3Data rptData,String field, int colNumber, String val) {
		try{
			Class cls = Class.forName(rptData.getClass().getName());
			if(null != rptData){
				Method method = cls.getMethod("set"+field+colNumber,String.class);
				if(null != method){
					// Defect 5465 - Rename column to Dozer
					if(StringUtility.hasValue(val) && val.trim().equalsIgnoreCase("Dozer, Type 1")){
						method.invoke(rptData," Dozer");
					}else
						method.invoke(rptData,val);
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	private static Collection<Long> getIncidentIds(Collection<ICS209ResourceData> resourceData) {
		Collection<Long> incidentIds = new ArrayList<Long>();
		
		for(ICS209ResourceData d : resourceData){
			if(!incidentIds.contains(d.getIncidentId()))
				incidentIds.add(d.getIncidentId());
		}

		return incidentIds;
	}

	private static String getIncidentNumber(Long id,Collection<ICS209ResourceData> resourceData) {
		
		for(ICS209ResourceData d : resourceData){
			if(id.compareTo(d.getIncidentId())==0){
				return d.getIncidentNumber();
			}
		}

		return "NA";
	}

	private static Collection<String> getAgencies(Collection<ICS209ResourceData> resourceData, Long incidentId){
		Collection<String> agencies = new ArrayList<String>();

		boolean hasNoAgency=false;
		
		for(ICS209ResourceData rd : resourceData){
			
			if(incidentId.intValue()==0){
				// get single resource agencies
				if(!LongUtility.hasValue(rd.getResourceParentId())
						&& BooleanUtility.isFalse(rd.getIsStrikeTeam())
						&& isStatusCorP(rd)==true){
					if(!agencies.contains(rd.getAgencyCode())){
						if(rd.getAgencyCode().equals("No Agency")) 
							hasNoAgency=true;
						else
							agencies.add(rd.getAgencyCode());
					}
				}else{
					
					// get parent strike teams resource agencies
					if(LongUtility.hasValue(rd.getResourceParentId())
							&& BooleanUtility.isFalse(rd.getIsStrikeTeam())){
						if(isParentStrikeTeamTopLevel(rd.getResourceParentId(),resourceData)==true){
							if(isStatusCorP(rd)==true
									&& BooleanUtility.isFalse(rd.getIsStrikeTeam())){
								if(!agencies.contains(rd.getAgencyCode())){
									if(rd.getAgencyCode().equals("No Agency")) 
										hasNoAgency=true;
									else
										agencies.add(rd.getAgencyCode());
								}
							}
						}
					}
				}
	
				
			}else{
				// get single resource agencies
				if(incidentId.compareTo(rd.getIncidentId())==0
						&& !LongUtility.hasValue(rd.getResourceParentId())
						&& BooleanUtility.isFalse(rd.getIsStrikeTeam())
						&& isStatusCorP(rd)==true){
					if(!agencies.contains(rd.getAgencyCode())){
						if(rd.getAgencyCode().equals("No Agency")) 
							hasNoAgency=true;
						else
							agencies.add(rd.getAgencyCode());
					}
				}else if(incidentId.compareTo(rd.getIncidentId())==0){
					if(LongUtility.hasValue(rd.getResourceParentId())
							&& BooleanUtility.isFalse(rd.getIsStrikeTeam())){
						// get parent strike teams resource agencies
						if(isParentStrikeTeamTopLevel(rd.getResourceParentId(),resourceData)==true){
							if(isStatusCorP(rd)==true
									&& BooleanUtility.isFalse(rd.getIsStrikeTeam())){
								if(!agencies.contains(rd.getAgencyCode())){
									if(rd.getAgencyCode().equals("No Agency")) 
										hasNoAgency=true;
									else
										agencies.add(rd.getAgencyCode());
								}
							}
						}
					}					
				}
			}

		}
		
		Collections.sort((List)agencies, new Comparator<String>()
                {
                    public int compare(String s1, String s2)
                    {
                        return s1.compareTo(s2);
                    }        
                });
		
		// rebuild agencies, put NoAgency first if exists
		Collection<String> newAgencies = new ArrayList<String>();
		if(hasNoAgency==true)
			newAgencies.add("No Agency");
		
		for(String s : agencies){
			newAgencies.add(s);
		}
		
		newAgencies.add("TOTAL");
		
		return newAgencies;
	}
	
	private static Collection<String> getCategories(Collection<ICS209ResourceData> resourceData, Long incidentId){
		Collection<String> categories = new ArrayList<String>();

		Boolean hasNon209Category=false;
		Boolean hasOverheadCategory=false;
		
		for(ICS209ResourceData rd : resourceData){

			if(incidentId.intValue()==0){
				// get single resource agencies
				if(!LongUtility.hasValue(rd.getResourceParentId())
						&& isStatusCorP(rd)==true){

					if(!categories.contains(" "+rd.getSitCategory())){
						if(rd.getSitCategory().equalsIgnoreCase("Non 209"))
							hasNon209Category=true;
						else if(rd.getSitCategory().equalsIgnoreCase("OVERHEAD"))
							hasOverheadCategory=true;
						else
							categories.add(" "+rd.getSitCategory());
					}
				}else{
					// get parent strike teams resource categories
					if(isParentStrikeTeamTopLevel(rd.getResourceParentId(),resourceData)==true){
						if(isStatusCorP(rd)==true){
							if(!categories.contains(" "+rd.getSitCategory())){
								if(rd.getSitCategory().equalsIgnoreCase("Non 209"))
									hasNon209Category=true;
								else if(rd.getSitCategory().equalsIgnoreCase("OVERHEAD"))
									hasOverheadCategory=true;
								else
									categories.add(" "+rd.getSitCategory());
							}
						}
					}
					
				}
				
			}else{
				// get single resource agencies
				if(incidentId.compareTo(rd.getIncidentId())==0
						&& !LongUtility.hasValue(rd.getResourceParentId())
						&& isStatusCorP(rd)==true){

					if(!categories.contains(" "+rd.getSitCategory())){
						if(rd.getSitCategory().equalsIgnoreCase("Non 209"))
							hasNon209Category=true;
						else if(rd.getSitCategory().equalsIgnoreCase("OVERHEAD"))
							hasOverheadCategory=true;
						else
							categories.add(" "+rd.getSitCategory());
					}
				}else{
					// get parent strike teams resource categories
					if(incidentId.compareTo(rd.getIncidentId())==0){
						if(isParentStrikeTeamTopLevel(rd.getResourceParentId(),resourceData)==true){
							if(isStatusCorP(rd)==true){
								if(!categories.contains(" "+rd.getSitCategory())){
									if(rd.getSitCategory().equalsIgnoreCase("Non 209"))
										hasNon209Category=true;
									else if(rd.getSitCategory().equalsIgnoreCase("OVERHEAD"))
										hasOverheadCategory=true;
									else
										categories.add(" "+rd.getSitCategory());
								}
							}
						}
					}
					
				}
			}

		}


		Collections.sort((List)categories, new Comparator<String>()
                {
                    public int compare(String s1, String s2)
                    {
                        return s1.compareTo(s2);
                    }        
                });

		if(hasOverheadCategory==true)
			categories.add(" Overhead");
		
		if(CollectionUtility.hasValue(categories)){
			int size=categories.size();
			if(size<27){
				for(int x=(size+1);x<=27;x++){
					categories.add("");
				}
			}
		}
		categories.add(" SUB TOTAL");
		categories.add(" Non 209");
		categories.add(" GRAND TOTAL");

		return categories;
	}

	private static Boolean isStatusCorP(ICS209ResourceData r){
		if(StringUtility.hasValue(r.getAssignmentStatus())
				&& (r.getAssignmentStatus().equals("C") 
						|| r.getAssignmentStatus().equals("P"))){
			return true;
		}else
			return false;
	}

	public static int getCategoryColumn(String category, Collection<String> categories){
		int x=1;
		
		for(String s : categories){
			if(s.trim().equals(category.trim()))
				return x;
			x++;
		}
		
		return -1;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the pagesubtitle
	 */
	public String getPagesubtitle() {
		return pagesubtitle;
	}

	/**
	 * @param pagesubtitle the pagesubtitle to set
	 */
	public void setPagesubtitle(String pagesubtitle) {
		this.pagesubtitle = pagesubtitle;
	}

	/**
	 * @return the columnName1
	 */
	public String getColumnName1() {
		return columnName1;
	}

	/**
	 * @param columnName1 the columnName1 to set
	 */
	public void setColumnName1(String columnName1) {
		this.columnName1 = columnName1;
	}

	/**
	 * @return the columnName2
	 */
	public String getColumnName2() {
		return columnName2;
	}

	/**
	 * @param columnName2 the columnName2 to set
	 */
	public void setColumnName2(String columnName2) {
		this.columnName2 = columnName2;
	}

	/**
	 * @return the columnName3
	 */
	public String getColumnName3() {
		return columnName3;
	}

	/**
	 * @param columnName3 the columnName3 to set
	 */
	public void setColumnName3(String columnName3) {
		this.columnName3 = columnName3;
	}

	/**
	 * @return the columnName4
	 */
	public String getColumnName4() {
		return columnName4;
	}

	/**
	 * @param columnName4 the columnName4 to set
	 */
	public void setColumnName4(String columnName4) {
		this.columnName4 = columnName4;
	}

	/**
	 * @return the columnName5
	 */
	public String getColumnName5() {
		return columnName5;
	}

	/**
	 * @param columnName5 the columnName5 to set
	 */
	public void setColumnName5(String columnName5) {
		this.columnName5 = columnName5;
	}

	/**
	 * @return the columnName6
	 */
	public String getColumnName6() {
		return columnName6;
	}

	/**
	 * @param columnName6 the columnName6 to set
	 */
	public void setColumnName6(String columnName6) {
		this.columnName6 = columnName6;
	}

	/**
	 * @return the columnName7
	 */
	public String getColumnName7() {
		return columnName7;
	}

	/**
	 * @param columnName7 the columnName7 to set
	 */
	public void setColumnName7(String columnName7) {
		this.columnName7 = columnName7;
	}

	/**
	 * @return the columnName8
	 */
	public String getColumnName8() {
		return columnName8;
	}

	/**
	 * @param columnName8 the columnName8 to set
	 */
	public void setColumnName8(String columnName8) {
		this.columnName8 = columnName8;
	}

	/**
	 * @return the columnName9
	 */
	public String getColumnName9() {
		return columnName9;
	}

	/**
	 * @param columnName9 the columnName9 to set
	 */
	public void setColumnName9(String columnName9) {
		this.columnName9 = columnName9;
	}

	/**
	 * @return the columnName10
	 */
	public String getColumnName10() {
		return columnName10;
	}

	/**
	 * @param columnName10 the columnName10 to set
	 */
	public void setColumnName10(String columnName10) {
		this.columnName10 = columnName10;
	}

	/**
	 * @return the columnName11
	 */
	public String getColumnName11() {
		return columnName11;
	}

	/**
	 * @param columnName11 the columnName11 to set
	 */
	public void setColumnName11(String columnName11) {
		this.columnName11 = columnName11;
	}

	/**
	 * @return the columnName12
	 */
	public String getColumnName12() {
		return columnName12;
	}

	/**
	 * @param columnName12 the columnName12 to set
	 */
	public void setColumnName12(String columnName12) {
		this.columnName12 = columnName12;
	}

	/**
	 * @return the columnName13
	 */
	public String getColumnName13() {
		return columnName13;
	}

	/**
	 * @param columnName13 the columnName13 to set
	 */
	public void setColumnName13(String columnName13) {
		this.columnName13 = columnName13;
	}

	/**
	 * @return the columnName14
	 */
	public String getColumnName14() {
		return columnName14;
	}

	/**
	 * @param columnName14 the columnName14 to set
	 */
	public void setColumnName14(String columnName14) {
		this.columnName14 = columnName14;
	}

	/**
	 * @return the columnName15
	 */
	public String getColumnName15() {
		return columnName15;
	}

	/**
	 * @param columnName15 the columnName15 to set
	 */
	public void setColumnName15(String columnName15) {
		this.columnName15 = columnName15;
	}

	/**
	 * @return the columnName16
	 */
	public String getColumnName16() {
		return columnName16;
	}

	/**
	 * @param columnName16 the columnName16 to set
	 */
	public void setColumnName16(String columnName16) {
		this.columnName16 = columnName16;
	}

	/**
	 * @return the columnName17
	 */
	public String getColumnName17() {
		return columnName17;
	}

	/**
	 * @param columnName17 the columnName17 to set
	 */
	public void setColumnName17(String columnName17) {
		this.columnName17 = columnName17;
	}

	/**
	 * @return the columnName18
	 */
	public String getColumnName18() {
		return columnName18;
	}

	/**
	 * @param columnName18 the columnName18 to set
	 */
	public void setColumnName18(String columnName18) {
		this.columnName18 = columnName18;
	}

	/**
	 * @return the columnName19
	 */
	public String getColumnName19() {
		return columnName19;
	}

	/**
	 * @param columnName19 the columnName19 to set
	 */
	public void setColumnName19(String columnName19) {
		this.columnName19 = columnName19;
	}

	/**
	 * @return the columnName20
	 */
	public String getColumnName20() {
		return columnName20;
	}

	/**
	 * @param columnName20 the columnName20 to set
	 */
	public void setColumnName20(String columnName20) {
		this.columnName20 = columnName20;
	}

	/**
	 * @return the columnName21
	 */
	public String getColumnName21() {
		return columnName21;
	}

	/**
	 * @param columnName21 the columnName21 to set
	 */
	public void setColumnName21(String columnName21) {
		this.columnName21 = columnName21;
	}

	/**
	 * @return the columnName22
	 */
	public String getColumnName22() {
		return columnName22;
	}

	/**
	 * @param columnName22 the columnName22 to set
	 */
	public void setColumnName22(String columnName22) {
		this.columnName22 = columnName22;
	}

	/**
	 * @return the columnName23
	 */
	public String getColumnName23() {
		return columnName23;
	}

	/**
	 * @param columnName23 the columnName23 to set
	 */
	public void setColumnName23(String columnName23) {
		this.columnName23 = columnName23;
	}

	/**
	 * @return the columnName24
	 */
	public String getColumnName24() {
		return columnName24;
	}

	/**
	 * @param columnName24 the columnName24 to set
	 */
	public void setColumnName24(String columnName24) {
		this.columnName24 = columnName24;
	}

	/**
	 * @return the columnName25
	 */
	public String getColumnName25() {
		return columnName25;
	}

	/**
	 * @param columnName25 the columnName25 to set
	 */
	public void setColumnName25(String columnName25) {
		this.columnName25 = columnName25;
	}

	/**
	 * @return the columnName26
	 */
	public String getColumnName26() {
		return columnName26;
	}

	/**
	 * @param columnName26 the columnName26 to set
	 */
	public void setColumnName26(String columnName26) {
		this.columnName26 = columnName26;
	}

	/**
	 * @return the columnName27
	 */
	public String getColumnName27() {
		return columnName27;
	}

	/**
	 * @param columnName27 the columnName27 to set
	 */
	public void setColumnName27(String columnName27) {
		this.columnName27 = columnName27;
	}

	/**
	 * @return the columnName28
	 */
	public String getColumnName28() {
		return columnName28;
	}

	/**
	 * @param columnName28 the columnName28 to set
	 */
	public void setColumnName28(String columnName28) {
		this.columnName28 = columnName28;
	}

	/**
	 * @return the columnName29
	 */
	public String getColumnName29() {
		return columnName29;
	}

	/**
	 * @param columnName29 the columnName29 to set
	 */
	public void setColumnName29(String columnName29) {
		this.columnName29 = columnName29;
	}

	/**
	 * @return the columnName30
	 */
	public String getColumnName30() {
		return columnName30;
	}

	/**
	 * @param columnName30 the columnName30 to set
	 */
	public void setColumnName30(String columnName30) {
		this.columnName30 = columnName30;
	}

	/**
	 * @return the subReportData
	 */
	public List<ICS209SubReportV3Data> getSubReportData() {
		return subReportData;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportData(List<ICS209SubReportV3Data> subReportData) {
		this.subReportData = subReportData;
	}

	/**
	 * @return the dataSourceReportData
	 */
	public JRBeanCollectionDataSource getDataSourceReportData() {
		this.dataSourceReportData = new JRBeanCollectionDataSource(subReportData);
		return this.dataSourceReportData;
	}

	/**
	 * @param dataSourceReportData the dataSourceReportData to set
	 */
	public void setDataSourceReportData(
			JRBeanCollectionDataSource dataSourceReportData) {
		this.dataSourceReportData = dataSourceReportData;
	}
	
	public static Boolean isParentStrikeTeamTopLevel(Long parentId, Collection<ICS209ResourceData> resourceData){
		Boolean rtn=false;
		
		if(LongUtility.hasValue(parentId)){
			if(CollectionUtility.hasValue(resourceData)){
				for(ICS209ResourceData rd : resourceData){
					if(rd.getResourceId().compareTo(parentId)==0
							&& !LongUtility.hasValue(rd.getResourceParentId())){
						if(BooleanUtility.isTrue(rd.getIsStrikeTeam())){
							return true;
						}
						
						return false;
					}
				}
			}
			
		}
		return rtn;
	}
}
