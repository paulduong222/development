package gov.nwcg.isuite.core.financial.calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.financial.calculator.DailyTimeCalculator;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.FinancialCollectionUtility;

public class ContractorDailyTimeCalculator implements DailyTimeCalculator {

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.framework.financial.calculator.DailyTimeCalculator#applyGuarantees(java.util.Map, java.util.Collection, java.util.Collection)
   */
  @Override
  public void applyGuarantees(
      Map<Long, Map<Long, Map<Date, Collection<DailyTimePostTotal>>>> incMap,
      Collection<IncidentResourceVo> incidentResources, 
      Collection<Date> dates) {
    
    applyGuaranteesByIncidentCodeDateAndRate(incMap, incidentResources, dates);
  }
  
  private void applyGuaranteesByIncidentCodeDateAndRate(
      Map<Long, Map<Long, Map<Date, Collection<DailyTimePostTotal>>>> incMap,
      Collection<IncidentResourceVo> incidentResources, 
      Collection<Date> dates) {

    Map<Long, Map<Date, Collection<DailyTimePostTotal>>> codeMap;
    Map<Date, Collection<DailyTimePostTotal>> dateMap;
    Collection<DailyTimePostTotal> dateColl;
    
    for(Long incidentId : incMap.keySet()) {
      codeMap = incMap.get(incidentId);
      
      for(Long codeId : codeMap.keySet()) {
        dateMap = codeMap.get(codeId);
        
        for(Date dt : dateMap.keySet()) {
          dateColl = dateMap.get(dt);
          BigDecimal dayTotal = new BigDecimal(0.0);
          
          for(DailyTimePostTotal dtpt : dateColl) {
              dayTotal = dayTotal.add(dtpt.getTotalPrimaryAmount());
              dayTotal = dayTotal.add(dtpt.getTotalSpecialAmount()); 

              if(dtpt.getGuaranteeAmount() != null){
            	  if(dtpt.getGuaranteeAmount().doubleValue() < dayTotal.doubleValue()){
            		  dayTotal= dtpt.getGuaranteeAmount();	
            	  }
              }
          }
          AssignmentTimePostVo last = getLastIncidentPostForAccountCodeOnDate(incidentId, codeId, dt, dates, incidentResources);
          last.setInvoicedAmount(dayTotal);
        }
      }
    }
  }
  
  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.framework.financial.calculator.DailyTimeCalculator#calculateDailyTotals(java.util.Collection, java.util.Collection)
   */
  @Override
  public Map<Long, Map<Long, Map<Date, Collection<DailyTimePostTotal>>>> calculateDailyTotals(
      Collection<IncidentResourceVo> incidentResources, 
      Collection<Date> dates) {
    
    return calculateDailyTotalsByIncidentCodeDateAndRate(incidentResources);
  }
  
  private Map<Long, Map<Long, Map<Date, Collection<DailyTimePostTotal>>>> calculateDailyTotalsByIncidentCodeDateAndRate(
      Collection<IncidentResourceVo> incidentResources) {

    Map<Long, Map<Long, Map<Date, Map<Long, Collection<AssignmentTimePostVo>>>>> incMap = 
      FinancialCollectionUtility.collectTimePostsByIncidentCodeDateAndRate(incidentResources);
    
    Map<Long, Map<Date, Map<Long, Collection<AssignmentTimePostVo>>>> cdMap; // grouped by accounting code
    Map<Date, Map<Long, Collection<AssignmentTimePostVo>>> dtMap; // grouped by date
    Map<Long, Collection<AssignmentTimePostVo>> rtMap; // grouped by rate 

    Map<Long, Map<Long, Map<Date, Collection<DailyTimePostTotal>>>> incidentsMap 
      = new HashMap<Long, Map<Long, Map<Date, Collection<DailyTimePostTotal>>>>();
    Map<Long, Map<Date, Collection<DailyTimePostTotal>>> codeMap;
    Map<Date, Collection<DailyTimePostTotal>> dateMap;
    Collection<DailyTimePostTotal> dateColl;
    
    // evaluate for each incident 
    for(Long incidentId : incMap.keySet()) {
      cdMap = incMap.get(incidentId);
      codeMap = new HashMap<Long, Map<Date, Collection<DailyTimePostTotal>>>();
      
      for(Long codeId : cdMap.keySet()) {
        dtMap = cdMap.get(codeId);
        dateMap = new HashMap<Date,Collection<DailyTimePostTotal>>();
             
        // evaluate for each date
        for(Date dt : dtMap.keySet()) {
          rtMap = dtMap.get(dt);
          
          dateColl = new ArrayList<DailyTimePostTotal>();
          
          // sum the primary and special totals for each rate definition set
          for(Long rt : rtMap.keySet()) {
            Collection<AssignmentTimePostVo> col = rtMap.get(rt);
            DailyTimePostTotal dtpt = new DailyTimePostTotal();
            double ptotal=0.0;
            double stotal=0.0;
            int size = col.size();
            int count = 1;

            //System.out.println(col.size());
            
            BigDecimal grate = new BigDecimal(0.0);
            
            for(AssignmentTimePostVo atp : col) {
              ptotal += DailyTimePostTotal.getTotalPrimaryAmount(atp).doubleValue(); 
              //stotal += DailyTimePostTotal.getTotalSpecialAmount(atp).doubleValue();
              atp.setInvoicedAmount(new BigDecimal(0.0));
              
				if(null != atp.getRefContractorRateVo() && DecimalUtil.hasValue(atp.getRefContractorRateVo().getGuaranteeAmount())){
					if(grate.doubleValue() < atp.getRefContractorRateVo().getGuaranteeAmount().doubleValue()){
						grate=atp.getRefContractorRateVo().getGuaranteeAmount();
					}
				}else{
					if(DecimalUtil.hasValue(atp.getGuaranteeAmount())){
						if(grate.doubleValue() < atp.getGuaranteeAmount().doubleValue()){
							grate=atp.getGuaranteeAmount();
						}
						
					}
				}

              if(count == size) {
                dtpt.setRateClassId(rt);
                //dtpt.setGuaranteeAmount(atp.getGuaranteeAmount());
				
				if(ptotal < grate.doubleValue()){
					dtpt.setTotalPrimaryAmount(grate);
				}else
	                dtpt.setTotalPrimaryAmount(new BigDecimal(ptotal));
				
                dtpt.setTotalSpecialAmount(new BigDecimal(stotal));
                dateColl.add(dtpt);
              }
              count++;
            }
          }
          if(dateColl.size()>0) {
            dateMap.put(dt, dateColl);
          }
        }
        if(dateMap.size()>0) 
          codeMap.put(codeId, dateMap);
      }
      if(codeMap.size()>0)
        incidentsMap.put(incidentId, codeMap);
    }
    
    return incidentsMap;
  }
   
  private AssignmentTimePostVo getLastIncidentPostForAccountCodeOnDate( 
      Long incidentId, 
      Long accountCodeId,
      Date dt,
      Collection<Date> dates,
      Collection<IncidentResourceVo> incidentResources) {
    
    AssignmentTimePostVo atp = new AssignmentTimePostVo();
    Collection<AssignmentTimePostVo> atps;
    
    Map<Long, Map<Long, Map<Date, Collection<AssignmentTimePostVo>>>> incMap = 
      FinancialCollectionUtility.collectTimePostsByIncidentCodeAndDate(incidentResources, dates);
    
    atps = incMap.get(incidentId).get(accountCodeId).get(dt);
    for(AssignmentTimePostVo a : atps) {
      atp = a; 
    }
    return atp;
  }

  
  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.framework.financial.calculator.DailyTimeCalculator#calculateTimePostInvoicedAmount(java.util.Collection, java.util.Collection)
   */
  @Override
  public void calculateTimePostInvoicedAmount(IncidentResourceVo irVo) {
    // not applicable to contracted resources
  }
}
