package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.ReportCostDao;
import gov.nwcg.isuite.core.reports.CostShareDetailReport;
import gov.nwcg.isuite.core.reports.CostShareShiftKindReport;
import gov.nwcg.isuite.core.reports.CostShareSummaryReport;
import gov.nwcg.isuite.core.reports.CostShareWorkSheetReport;
import gov.nwcg.isuite.core.reports.data.CostShareMainReportData;
import gov.nwcg.isuite.core.reports.data.CostShareSubSubReportData;
import gov.nwcg.isuite.core.reports.filter.CostShareReportFilter;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.ReportFilter;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;


public class CostShareReportGeneratorImpl extends ReportGenerator2Impl {
	
	private CostShareReportFilter filter;
	private ReportCostDao reportCostDao;
	private List<String> agencyGroupNames;
	private String reportName;
	
	@Override
	public <E extends ReportFilter> DialogueVo generateReport(E filterIn, DialogueVo dialogueVo) throws ServiceException,
				PersistenceException {
		try {
			if (dialogueVo == null) 
				dialogueVo = new DialogueVo();
			
			if(!(filterIn instanceof CostShareReportFilter)){
				throw new Exception("Invalid Filter Type. Expecting Filter to be of type GroupCategoryTotalReportFilter.");
			}
			
			filter = (CostShareReportFilter) filterIn;
	        this.reportName = filter.getReportType(); 		
	        
			//get incidents for incident group 
			Collection<Incident> incidents = new ArrayList<Incident>();
			IncidentGroup incidentGroup = null;
			if(filter.isIncidentGroup()) {
				incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId())));
				incidentGroup = getIncidentGroupById(filter.getIncidentGroupId());
			}
			else 
				incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
			
			reportCostDao = (ReportCostDao) super.context.getBean("reportCostDao");
			
			//get cost report data for all the selected incident/incident group
			Collection<CostShareSubSubReportData> costShareSubSubReportDataList = null;
			costShareSubSubReportDataList = reportCostDao.getCostShareReportData(filter);
			
			if(costShareSubSubReportDataList == null || costShareSubSubReportDataList.size() < 1){
				throw new ServiceException(super.buildNoDataMessage("Cost Share Report Data"));
			}
			
			// CostShareMainReportData ----> list of CostShareSubReportData, 
			// CostShareSubReportData  ----> list of CostShareSubSubReportData 
			List<CostShareMainReportData> mainReportDataList = new ArrayList<CostShareMainReportData>();
			
			for(Incident incident : incidents) { 
				//if incident group is selected, we get Agency List by each incident 
				//but only worksheet report we need get agency list by incidentGroup
				if(filter.isIncidentGroup)
					filter.setIncidentId(incident.getId());
				
				filter.setAgencyList(reportCostDao.getAgencyNameList(filter));
				filter.setCategoryList(reportCostDao.getCategoryList(filter));
				List<CostShareSubSubReportData> subReportDataList = new ArrayList<CostShareSubSubReportData>();
				
				//build cost share report data for each incident
				//List<CostShareSubReportData> subReportDataList = getCostShareSubReportData(costShareSubSubReportDataList, incident.getId());
				
				for(int i=0; i < filter.getCategoryList().size(); i++) {
					List<CostShareSubSubReportData> subReportDataListTemp = getCostShareSubReportData(costShareSubSubReportDataList, incident.getId(), filter.getCategoryList().get(i));
				
					if(subReportDataListTemp == null || subReportDataListTemp.isEmpty()) 
						continue;
					
					subReportDataList.addAll(subReportDataListTemp);
				}
				CostShareMainReportData  mainReportData = new CostShareMainReportData(); 

				mainReportData.setIncidentId(incident.getId());
				mainReportData.setIncidentName(incident.getIncidentName());
				mainReportData.setIncidentNumber(incident.getIncidentNumber());
				
				if(filter.isIncidentGroup()) {
					mainReportData.setIncidentGroupId(filter.getIncidentGroupId());
					mainReportData.setIncidentGroupName(incidentGroup == null ? "" : incidentGroup.getGroupName());
				}
				
				Double totalCost = 0.0;
				Double a_totalCost = 0.0;
				Double ce_totalCost = 0.0;
				Double so_totalCost = 0.0;
				//agencies cache for unique agencies
				HashMap<String, Double> cache = new HashMap<String, Double>();
				HashMap<String, Double> a_cache = new HashMap<String, Double>();
				HashMap<String, Double> ce_cache = new HashMap<String, Double>();
				HashMap<String, Double> so_cache = new HashMap<String, Double>();
				//last category for report grand total show and hide
				String lastCategory="";
				if(CollectionUtility.hasValue(subReportDataList)){
					lastCategory = subReportDataList.get(subReportDataList.size() - 1).getCategory();
				}
				
				//build cache and sub/grand total
				for(int i=0; i < subReportDataList.size(); i++) {
					CostShareSubSubReportData data = subReportDataList.get(i);
					
					if (data.getAgency_01() != null) {
					  cache.put(data.getAgency_01(), 0.0);
					  totalCost = totalCost + data.getCost_01();
					  if (data.getCategory().equals("Aircraft")) {
						  a_cache.put(data.getAgency_01(), 0.0);
						  a_totalCost = a_totalCost + data.getCost_01();
					  }
					  if (data.getCategory().equals("Crew & Equipment")) {
						  ce_cache.put(data.getAgency_01(), 0.0);
						  ce_totalCost = ce_totalCost + data.getCost_01();
					  }
					  if (data.getCategory().equals("Support & Overhead")) {
						  so_cache.put(data.getAgency_01(), 0.0);
						  so_totalCost = so_totalCost + data.getCost_01();
					  }
					}
					if (data.getAgency_02() != null) {
						  cache.put(data.getAgency_02(), 0.0);
						  totalCost = totalCost + data.getCost_02();
						  if (data.getCategory().equals("Aircraft")) {
							  a_cache.put(data.getAgency_02(), 0.0);
							  a_totalCost = a_totalCost + data.getCost_02();
						  }
						  if (data.getCategory().equals("Crew & Equipment")) {
							  ce_cache.put(data.getAgency_02(), 0.0);
							  ce_totalCost = ce_totalCost + data.getCost_02();
						  }
						  if (data.getCategory().equals("Support & Overhead")) {
							  so_cache.put(data.getAgency_02(), 0.0);
							  so_totalCost = so_totalCost + data.getCost_02();
						  }						  
					}
					if (data.getAgency_03() != null) {
						  cache.put(data.getAgency_03(), 0.0);
						  totalCost = totalCost + data.getCost_03();
						  if (data.getCategory().equals("Aircraft")) {
							  a_cache.put(data.getAgency_03(), 0.0);
							  a_totalCost = a_totalCost + data.getCost_03();
						  }
						  if (data.getCategory().equals("Crew & Equipment")) {
							  ce_cache.put(data.getAgency_03(), 0.0);
							  ce_totalCost = ce_totalCost + data.getCost_03();
						  }
						  if (data.getCategory().equals("Support & Overhead")) {
							  so_cache.put(data.getAgency_03(), 0.0);
							  so_totalCost = so_totalCost + data.getCost_03();
						  }						  					  
					}
					if (data.getAgency_04() != null) {
						  cache.put(data.getAgency_04(), 0.0);
						  totalCost = totalCost + data.getCost_04();
						  if (data.getCategory().equals("Aircraft")) {
							  a_cache.put(data.getAgency_04(), 0.0);
							  a_totalCost = a_totalCost + data.getCost_04();
						  }
						  if (data.getCategory().equals("Crew & Equipment")) {
							  ce_cache.put(data.getAgency_04(), 0.0);
							  ce_totalCost = ce_totalCost + data.getCost_04();
						  }
						  if (data.getCategory().equals("Support & Overhead")) {
							  so_cache.put(data.getAgency_04(), 0.0);
							  so_totalCost = so_totalCost + data.getCost_04();
						  }						  					  
					}
					if (data.getAgency_05() != null) {
						  cache.put(data.getAgency_05(), 0.0);
						  totalCost = totalCost + data.getCost_05();
						  if (data.getCategory().equals("Aircraft")) {
							  a_cache.put(data.getAgency_05(), 0.0);
							  a_totalCost = a_totalCost + data.getCost_05();
						  }
						  if (data.getCategory().equals("Crew & Equipment")) {
							  ce_cache.put(data.getAgency_05(), 0.0);
							  ce_totalCost = ce_totalCost + data.getCost_05();
						  }
						  if (data.getCategory().equals("Support & Overhead")) {
							  so_cache.put(data.getAgency_05(), 0.0);
							  so_totalCost = so_totalCost + data.getCost_05();
						  }						  					  
					}
				} 			
				
				//build total for each agency
				for(int i=0; i < subReportDataList.size(); i++) {
					CostShareSubSubReportData data = subReportDataList.get(i);
					
					if (data.getAgency_01() != null) {
					    Double old = cache.get(data.getAgency_01());
					    cache.put(data.getAgency_01(), old + data.getCost_01());
					    
					    if (data.getCategory().equals("Aircraft")) {
					    	Double a_old = a_cache.get(data.getAgency_01());
						    a_cache.put(data.getAgency_01(), a_old + data.getCost_01());
						}
						if (data.getCategory().equals("Crew & Equipment")) {
						   	Double ce_old = ce_cache.get(data.getAgency_01());
						    ce_cache.put(data.getAgency_01(), ce_old + data.getCost_01());
						}
						if (data.getCategory().equals("Support & Overhead")) {
						  	Double so_old = so_cache.get(data.getAgency_01());
						    so_cache.put(data.getAgency_01(), so_old + data.getCost_01());
						}		
						
					}
					if (data.getAgency_02() != null) {
						Double old = cache.get(data.getAgency_02());
						cache.put(data.getAgency_02(), old + data.getCost_02());
						
					    if (data.getCategory().equals("Aircraft")) {
					    	Double a_old = a_cache.get(data.getAgency_02());
						    a_cache.put(data.getAgency_02(), a_old + data.getCost_02());
						}
						if (data.getCategory().equals("Crew & Equipment")) {
						   	Double ce_old = ce_cache.get(data.getAgency_02());
						    ce_cache.put(data.getAgency_02(), ce_old + data.getCost_02());
						}
						if (data.getCategory().equals("Support & Overhead")) {
						  	Double so_old = so_cache.get(data.getAgency_02());
						    so_cache.put(data.getAgency_02(), so_old + data.getCost_02());
						}								
					}
					if (data.getAgency_03() != null) {
						Double old = cache.get(data.getAgency_03());
						cache.put(data.getAgency_03(), old + data.getCost_03());
						
					    if (data.getCategory().equals("Aircraft")) {
					    	Double a_old = a_cache.get(data.getAgency_03());
						    a_cache.put(data.getAgency_03(), a_old + data.getCost_03());
						}
						if (data.getCategory().equals("Crew & Equipment")) {
						   	Double ce_old = ce_cache.get(data.getAgency_03());
						    ce_cache.put(data.getAgency_03(), ce_old + data.getCost_03());
						}
						if (data.getCategory().equals("Support & Overhead")) {
						  	Double so_old = so_cache.get(data.getAgency_03());
						    so_cache.put(data.getAgency_03(), so_old + data.getCost_03());
						}								
					}
					if (data.getAgency_04() != null) {
						Double old = cache.get(data.getAgency_04());
						cache.put(data.getAgency_04(), old + data.getCost_04());
						
					    if (data.getCategory().equals("Aircraft")) {
					    	Double a_old = a_cache.get(data.getAgency_04());
						    a_cache.put(data.getAgency_04(), a_old + data.getCost_04());
						}
						if (data.getCategory().equals("Crew & Equipment")) {
						   	Double ce_old = ce_cache.get(data.getAgency_04());
						    ce_cache.put(data.getAgency_04(), ce_old + data.getCost_04());
						}
						if (data.getCategory().equals("Support & Overhead")) {
						  	Double so_old = so_cache.get(data.getAgency_04());
						    so_cache.put(data.getAgency_04(), so_old + data.getCost_04());
						}								
					}
					if (data.getAgency_05() != null) {
						Double old = cache.get(data.getAgency_05());
						cache.put(data.getAgency_05(), old + data.getCost_05());
					    if (data.getCategory().equals("Aircraft")) {
					    	Double a_old = a_cache.get(data.getAgency_05());
						    a_cache.put(data.getAgency_05(), a_old + data.getCost_05());
						}
						if (data.getCategory().equals("Crew & Equipment")) {
						   	Double ce_old = ce_cache.get(data.getAgency_05());
						    ce_cache.put(data.getAgency_05(), ce_old + data.getCost_05());
						}
						if (data.getCategory().equals("Support & Overhead")) {
						  	Double so_old = so_cache.get(data.getAgency_05());
						    so_cache.put(data.getAgency_05(), so_old + data.getCost_05());
						}								
					}
				} 				
				//final output
				for(int i=0; i < subReportDataList.size(); i++) {
					CostShareSubSubReportData data = subReportDataList.get(i);
					if (data.getAgency_01() != null) {
						data.setTotal_01(cache.get(data.getAgency_01()));
						if (data.getCategory().equals("Aircraft")) {
							data.setA_total_01(a_cache.get(data.getAgency_01()));
						}
						if (data.getCategory().equals("Crew & Equipment")) {
							data.setCe_total_01(ce_cache.get(data.getAgency_01()));
						}
						if (data.getCategory().equals("Support & Overhead")) {
							data.setSo_total_01(so_cache.get(data.getAgency_01()));
						}								
					}
					if (data.getAgency_02() != null) {
						data.setTotal_02(cache.get(data.getAgency_02()));
						if (data.getCategory().equals("Aircraft")) {
							data.setA_total_02(a_cache.get(data.getAgency_02()));
						}
						if (data.getCategory().equals("Crew & Equipment")) {
							data.setCe_total_02(ce_cache.get(data.getAgency_02()));
						}
						if (data.getCategory().equals("Support & Overhead")) {
							data.setSo_total_02(so_cache.get(data.getAgency_02()));
						}														
					}
					if (data.getAgency_03() != null) {
						data.setTotal_03(cache.get(data.getAgency_03()));
						if (data.getCategory().equals("Aircraft")) {
							data.setA_total_03(a_cache.get(data.getAgency_03()));
						}
						if (data.getCategory().equals("Crew & Equipment")) {
							data.setCe_total_03(ce_cache.get(data.getAgency_03()));
						}
						if (data.getCategory().equals("Support & Overhead")) {
							data.setSo_total_03(so_cache.get(data.getAgency_03()));
						}												
					}
					if (data.getAgency_04() != null) {
						data.setTotal_04(cache.get(data.getAgency_04()));
						if (data.getCategory().equals("Aircraft")) {
							data.setA_total_04(a_cache.get(data.getAgency_04()));
						}
						if (data.getCategory().equals("Crew & Equipment")) {
							data.setCe_total_04(ce_cache.get(data.getAgency_04()));
						}
						if (data.getCategory().equals("Support & Overhead")) {
							data.setSo_total_04(so_cache.get(data.getAgency_04()));
						}												
					}
					if (data.getAgency_05() != null) {
						data.setTotal_05(cache.get(data.getAgency_05()));
						if (data.getCategory().equals("Aircraft")) {
							data.setA_total_05(a_cache.get(data.getAgency_05()));
						}
						if (data.getCategory().equals("Crew & Equipment")) {
							data.setCe_total_05(ce_cache.get(data.getAgency_05()));
						}
						if (data.getCategory().equals("Support & Overhead")) {
							data.setSo_total_05(so_cache.get(data.getAgency_05()));
						}												
					}
					data.setIncidentName(incident.getIncidentName());
					data.setLastCategory(lastCategory);
					data.setTotal(totalCost);
					data.setA_total(a_totalCost);
					data.setCe_total(ce_totalCost);
					data.setSo_total(so_totalCost);
				} 				
				
				mainReportData.setCostShareSubReportDataList(subReportDataList);
				
//				System.out.print("Final Output"+"\n\r");
//								
//				System.out.print("LastCategory Incident	a	ce	so	Category      Date	Shift	dailyCost	Agency1	cost1	Per1	Agency2	cost2	Per2	Agency3	cost3	Per3	Agency4	cost4	Per4	Agency5	cost5	Per5"+"\n\r");
//				for(int i=0; i < subReportDataList.size(); i++) {
//					CostShareSubSubReportData data = subReportDataList.get(i);
//					
//					System.out.print("||"+data.getLastCategory()+" ||"+data.getIncidentName()+
//							         " ||"+data.getA_total()+" ||"+data.getCe_total()+" ||"+data.getSo_total()+
//							         " ||"+data.getCategory()+" ||"+data.getCostShareDate()+
//							         " ||"+data.getShift()+" ||"+data.getDailyCost()+
//							         " ||"+data.getA_total_01()+" ||"+data.getCe_total_01()+" ||"+data.getSo_total_01()+
//							         " ||"+data.getA_total_02()+" ||"+data.getCe_total_02()+" ||"+data.getSo_total_02()+
//							         " ||"+data.getA_total_03()+" ||"+data.getCe_total_03()+" ||"+data.getSo_total_03()+
//							         " ||"+data.getA_total_04()+" ||"+data.getCe_total_04()+" ||"+data.getSo_total_04()+
//							         " ||"+data.getA_total_05()+" ||"+data.getCe_total_05()+" ||"+data.getSo_total_05()+
//							         " ||"+data.getAgency_01()+" ||"+data.getCost_01()+" ||"+data.getPercentage_01()+
//							         " ||"+data.getAgency_02()+" ||"+data.getCost_02()+" ||"+data.getPercentage_02()+
//							         " ||"+data.getAgency_03()+" ||"+data.getCost_03()+" ||"+data.getPercentage_03()+
//							         " ||"+data.getAgency_04()+" ||"+data.getCost_04()+" ||"+data.getPercentage_04()+
//							         " ||"+data.getAgency_05()+" ||"+data.getCost_05()+" ||"+data.getPercentage_05()+"\n\r");
//				} 		
				
				
				
				mainReportDataList.add(mainReportData);
			}
			
			if(mainReportDataList.size() < 1){
				throw new ServiceException(super.buildNoDataMessage("Cost Share Report Data"));
			}
	
			// Instantiate the report controller object.
			IReport report = null;
			
			if(filter.isSummaryReport)
				report = new CostShareSummaryReport(mainReportDataList);
			else if(filter.isShiftKindReport)
				report = new CostShareShiftKindReport(mainReportDataList);
			else if(filter.isDetailReport)
				report = new CostShareDetailReport(mainReportDataList);
			else if(filter.isWorkSheetReport)
				report = new CostShareWorkSheetReport(mainReportDataList);
			 
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
			if(filter.isIncidentGroup()) 
				report.addCustomField("reportSubTitle2", incidentGroup == null ? "" : incidentGroup.getGroupName());
		
//			List<String> reportsList = new ArrayList<String>();
//			reportsList.add(createAndSaveReport(report));
//			
//			// add reportsList to dialogueVo
//			CourseOfActionVo coa = new CourseOfActionVo();
//			coa.setCoaName("COST_SHARE_REPORT");
//			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
//			coa.setIsDialogueEnding(true);
//			dialogueVo.setCourseOfActionVo(coa);
//			dialogueVo.setResultObject(reportsList);
			
			String pdfURL = null;
			pdfURL = createAndSaveReport(report);

					
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("REPORT COMPLETE");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coa.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setResultObject(pdfURL);
			

		} catch(PersistenceException e){
			super.handleException(e);
		} catch(ServiceException e){
			throw e;
		} catch(Exception e){
			super.handleException(e);
		}

		return dialogueVo;	
	}
	
	@SuppressWarnings("unchecked")
	private List<CostShareSubSubReportData> getCostShareSubReportData(Collection<CostShareSubSubReportData> costShareSubSubReportData, Long id, String category) throws Exception {
		List<CostShareSubSubReportData> reportDataList = new ArrayList<CostShareSubSubReportData>(costShareSubSubReportData);
		
		List<CostShareSubSubReportData> costShareSubReportDataList = new ArrayList<CostShareSubSubReportData>(); 
		
		try {
			//get data for the incident
			final Long incidentId = id;
			final String c = category;
			CollectionUtils.filter(reportDataList, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					CostShareSubSubReportData data = (CostShareSubSubReportData) object;
					// removed data if it is not the incident and the week 
					return incidentId.equals(data.getIncidentId()) && c.equalsIgnoreCase(data.getCategory());
				}
			});
			
			//rebuild reportDataList to make up missing agencies 
			reportDataList = rebuildReportDataList2(reportDataList);
			
			//build sub report data by agency group
			int counter = filter.getNumOfAgencyGroup();			
			
//			System.out.print("Agency Group:********** BEFORE ****************"+"\n\r");
//			System.out.print("Category:"+reportDataList.get(0).getCategory()+"\n\r");
//			
//			System.out.print("Date	Shift	dailyCost	Agency	cost	Per	"+"\n\r");
//			for(int i=0; i < reportDataList.size(); i++) {
//				CostShareSubSubReportData data = reportDataList.get(i);
//				
//				System.out.print("||"+data.getCostShareDate()+" ||"+data.getShift()+" ||"+data.getDailyCost()+
//						         " ||"+data.getAgency()+" ||"+data.getCost()+" ||"+data.getPercentage()+"\n\r");
//						        
//			} 
			
			for(int i=0; i < counter; i++) {
				agencyGroupNames = filter.getAgencyNames(i);
				//build sub sub report data
				costShareSubReportDataList.addAll(getCostShareSubSubReportDataDataByAgencyGroup(reportDataList));	                    
			}
			
			return costShareSubReportDataList;
		}
		catch (Exception e) {
			super.handleException(e);
		}
		
		return null; 
	}
	
	private List<CostShareSubSubReportData>  rebuildReportDataList(List<CostShareSubSubReportData> reportDataList){
		List<CostShareSubSubReportData> newReportDataList = new ArrayList<CostShareSubSubReportData>();
		
		CostShareSubSubReportData tempObj = null;
		boolean theFirst = true;
		List<CostShareSubSubReportData> group = new ArrayList<CostShareSubSubReportData>();
		
		for(int i=0; i<reportDataList.size(); i++) {
			CostShareSubSubReportData obj = reportDataList.get(i);
			
			System.out.println(DateUtil.toDateString(obj.getCostShareDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
			
			//get group first record and add to group
			if(theFirst) {
				tempObj = obj;
				theFirst = false;
				//group.add(obj);
				//continue;
			}
			
			//get the rest of the record and to group
			if(obj.equals(tempObj,reportName)) {
				group.add(obj);
				
				//Bill Tsai: this could be the last one too, take care of the last one 
				if (i == reportDataList.size() - 1) {
					tempObj = obj;
					addMissingAgencyToGroup(group);
					newReportDataList.addAll(group);
					group.clear();
					group.add(obj);
				}
			}
			else {// reach the last record of the agency group
				//theFirst = true;
				tempObj = obj;
				addMissingAgencyToGroup(group);
				newReportDataList.addAll(group);
				group.clear();
				group.add(obj);
			}		
		}
		
		return newReportDataList;
	}

	private List<CostShareSubSubReportData>  rebuildReportDataList2(List<CostShareSubSubReportData> reportDataList){
		List<CostShareSubSubReportData> newReportDataList = new ArrayList<CostShareSubSubReportData>();

		Collection<Date> dates = new ArrayList<Date>();
		for(CostShareSubSubReportData data : reportDataList){
			if(!dates.contains(data.getCostShareDate())){
				dates.add(data.getCostShareDate());
			}
		}
		
		for(Date dt : dates){
			for(CostShareSubSubReportData data : reportDataList){
				if(DateUtil.isSameDate(dt, data.getCostShareDate())){
					newReportDataList.add(data);					
				}
			}
			
			// do we have a record for all agencies on this date?
			List<CostShareSubSubReportData> tmpList = newReportDataList;			
			CostShareSubSubReportData tempObj=null;			
			for(String agency : filter.selectedAgencies){
				boolean hasAgency=false;
				for(CostShareSubSubReportData d : tmpList){
					if(DateUtil.isSameDate(dt, d.getCostShareDate())){
						if(tempObj==null) tempObj=d;
						
						if(d.getAgency().equalsIgnoreCase(agency)){
							hasAgency=true;
							break;
						}
					}
				}
				
				if(1==0 && hasAgency==false && tempObj != null){
					CostShareSubSubReportData obj = new CostShareSubSubReportData();
					obj.setAgency(agency);
					obj.setCategory(tempObj.getCategory());
					obj.setCost(0D);
					obj.setCostGroup(tempObj.getCostGroup());
					obj.setCostShareDate(tempObj.getCostShareDate());
					obj.setDailyCost(tempObj.getDailyCost());
					obj.setEndDate(tempObj.getEndDate());
					obj.setGroupingName(tempObj.getGroupingName());
					obj.setIncidentGroupId(tempObj.getIncidentGroupId());
					obj.setIncidentGroupName(tempObj.getIncidentGroupName());
					obj.setIncidentId(tempObj.getIncidentId());
					obj.setIncidentName(tempObj.getIncidentName());
					obj.setIncidentNumber(tempObj.getIncidentNumber());
					obj.setItemCode(tempObj.getItemCode());
					obj.setItemDescription(tempObj.getItemDescription());
					obj.setPercentage(BigDecimal.ZERO);
					obj.setQty(tempObj.getQty());
					obj.setResourceAgency(tempObj.getResourceAgency());
					obj.setResourceName(tempObj.getResourceName());
					obj.setShift(tempObj.getShift());
					obj.setStartDate(tempObj.getStartDate());
					
					newReportDataList.add(obj);
				}
			}
		}
		
		
		return newReportDataList;
	}
	
	private void addMissingAgencyToGroup(List<CostShareSubSubReportData> group){
		List<String> agencyNames = filter.selectedAgencies;
		List<CostShareSubSubReportData> newGroup = new ArrayList<CostShareSubSubReportData>();
		
		CostShareSubSubReportData tempObj = group.get(0);
		
		for(String s:agencyNames) {
			if(isAgencyInGroup(s,group))
				continue;
			
			CostShareSubSubReportData obj = new CostShareSubSubReportData();
			obj.setAgency(s);
			obj.setCategory(tempObj.getCategory());
			obj.setCost(0D);
			obj.setCostGroup(tempObj.getCostGroup());
			obj.setCostShareDate(tempObj.getCostShareDate());
			obj.setDailyCost(tempObj.getDailyCost());
			obj.setEndDate(tempObj.getEndDate());
			obj.setGroupingName(tempObj.getGroupingName());
			obj.setIncidentGroupId(tempObj.getIncidentGroupId());
			obj.setIncidentGroupName(tempObj.getIncidentGroupName());
			obj.setIncidentId(tempObj.getIncidentId());
			obj.setIncidentName(tempObj.getIncidentName());
			obj.setIncidentNumber(tempObj.getIncidentNumber());
			obj.setItemCode(tempObj.getItemCode());
			obj.setItemDescription(tempObj.getItemDescription());
			obj.setPercentage(BigDecimal.ZERO);
			obj.setQty(tempObj.getQty());
			obj.setResourceAgency(tempObj.getResourceAgency());
			obj.setResourceName(tempObj.getResourceName());
			obj.setShift(tempObj.getShift());
			obj.setStartDate(tempObj.getStartDate());
			
			newGroup.add(obj);
		}
		
		group.addAll(newGroup);
		
		Collections.sort(group, new Comparator<CostShareSubSubReportData>() {
			public int compare(CostShareSubSubReportData o1, CostShareSubSubReportData o2) {
				return o1.getAgency().compareTo(o2.getAgency());
			}
	    });
	}
	
	private List<CostShareSubSubReportData> getCostShareSubSubReportDataDataByAgencyGroup(List<CostShareSubSubReportData> reportDataList) throws Exception {
		List<CostShareSubSubReportData> list = new ArrayList<CostShareSubSubReportData>(); 
		
		try {	
			CostShareSubSubReportData tempObj = null;
			
			for(int i=0; i<reportDataList.size(); i++) {
				CostShareSubSubReportData obj = reportDataList.get(i);
				if(!isInAgencyNames(obj,agencyGroupNames))
					continue;
				
				if(tempObj!=null && obj.equals(tempObj,reportName)) {
					tempObj.setCostAndPercentage(obj.getCost(), 
												 obj.getPercentage().intValue(), 
												 getAgencyNameOrder(obj.getAgency()));
				}
				else {
					tempObj = obj;
					setAgencyNames(tempObj);
					tempObj.setCostAndPercentage(tempObj.getCost(), 
							 					 tempObj.getPercentage().intValue(), 
							 					 getAgencyNameOrder(tempObj.getAgency()));
					list.add(tempObj);
				}
			}	
			
//			System.out.print("Agency Group:************* AFTER ***************"+"\n\r");
//			System.out.print("Category:"+list.get(0).getCategory()+"\n\r");
//			
//			System.out.print("Date	Shift	dailyCost	Agency1	cost1	Per1	Agency2	cost2	Per2	Agency3	cost3	Per3	Agency4	cost4	Per4	Agency5	cost5	Per5"+"\n\r");
//			for(int i=0; i < list.size(); i++) {
//				CostShareSubSubReportData data = list.get(i);
//				
//				System.out.print("||"+data.getCostShareDate()+" ||"+data.getShift()+" ||"+data.getDailyCost()+
//						         " ||"+data.getAgency_01()+" ||"+data.getCost_01()+" ||"+data.getPercentage_01()+
//						         " ||"+data.getAgency_02()+" ||"+data.getCost_02()+" ||"+data.getPercentage_02()+
//						         " ||"+data.getAgency_03()+" ||"+data.getCost_03()+" ||"+data.getPercentage_03()+
//						         " ||"+data.getAgency_04()+" ||"+data.getCost_04()+" ||"+data.getPercentage_04()+
//						         " ||"+data.getAgency_05()+" ||"+data.getCost_05()+" ||"+data.getPercentage_05()+"\n\r");
//			} 
			

			return list;
		} catch (Exception e) {
			super.handleException(e);
		}
		
		return null;
	}
	
	private int getAgencyNameOrder(String agencyName){
		for(int i=0; i<agencyGroupNames.size(); i++)
			if(agencyName.equals(agencyGroupNames.get(i)))
				return i;
		
		return -1;
	}
	
	private void setAgencyNames(CostShareSubSubReportData tempObj) {
		for(int i=0; i<agencyGroupNames.size();i++)
			tempObj.setAgencyName(agencyGroupNames.get(i), i);
	}
	
	private boolean isInAgencyNames(CostShareSubSubReportData obj,List<String> agencyNames) {
		for(int i=0; i<agencyNames.size();i++) {
			if(obj.getAgency().equals((agencyNames.get(i))))
				return true;
		}
		return false;
	}
	
	private boolean isAgencyInGroup(String agencyName,List<CostShareSubSubReportData> group) {
		for(int i=0; i<group.size();i++) {
			if(agencyName.equals((group.get(i).getAgency())))
				return true;
		}
		return false;
	}
}


//if(filter.isSortByRequestName) {
//Collections.sort(resourceDateDataList, new Comparator<ResourceDateData>() {
//	public int compare(ResourceDateData o1, ResourceDateData o2) {
//		return o1.getFirstName().compareTo(o2.getFirstName());
//	}
//});
//}
//else {
//Collections.sort(resourceDateDataList, new Comparator<ResourceDateData>() {
//	public int compare(ResourceDateData o1, ResourceDateData o2) {
//		return o1.getRequestNumber().compareTo(o2.getRequestNumber());
//	}
//});
//}
//
////Collections.sort(resourceDateDataList, ResourceDateData.COMPARATOR__REQUEST_NUMBER_IGNORE_CASE_NULL_LAST);