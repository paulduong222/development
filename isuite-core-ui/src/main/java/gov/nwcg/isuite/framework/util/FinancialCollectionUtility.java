package gov.nwcg.isuite.framework.util;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FinancialCollectionUtility {
	
  public static Map<Long, Map<Long, Map<Date, Map<Long, Collection<AssignmentTimePostVo>>>>> collectTimePostsByIncidentCodeDateAndRate(
      Collection<IncidentResourceVo> incidentResources) {
    
    Map<Long, Map<Long, Map<Date, Map<Long, Collection<AssignmentTimePostVo>>>>> incMap 
        = new HashMap<Long, Map<Long, Map<Date, Map<Long, Collection<AssignmentTimePostVo>>>>>();
    Map<Long, Map<Date, Map<Long, Collection<AssignmentTimePostVo>>>> codeMap;
    Map<Date, Map<Long, Collection<AssignmentTimePostVo>>> dateMap;
    Map<Long, Collection<AssignmentTimePostVo>> rateMap;
    Collection<AssignmentTimePostVo> atps;
    
    for (IncidentResourceVo ir : incidentResources) {
      for (AssignmentVo a : ir.getWorkPeriodVo().getAssignmentVos()) {
        for(AssignmentTimePostVo atp : a.getAssignmentTimeVo().getAssignmentTimePostVos()) {
          
          Long codeId = atp.getIncidentAccountCodeVo().getAccountCodeVo().getId();
          codeMap = incMap.get(ir.getIncidentVo().getId());
          if(codeMap==null) {
            codeMap = new HashMap<Long, Map<Date, Map<Long, Collection<AssignmentTimePostVo>>>>();
            incMap.put(ir.getIncidentVo().getId(), codeMap);
          }
          
          Date date = DateUtil.makeEndOfDay(atp.getPostStartDate());
          dateMap = codeMap.get(codeId);
          if(dateMap==null){
            dateMap = new HashMap<Date, Map<Long, Collection<AssignmentTimePostVo>>>();
            codeMap.put(codeId, dateMap);
          }

          Long rateId;
          if(atp.getPrimaryPosting() && atp.getGuaranteePosting()!=null && atp.getGuaranteePosting()) {
            // guarantee posting
            rateId = atp.getRefContractorRateVo().getId();
          } else if (atp.getPrimaryPosting() && atp.getSpecialPosting()) {
            // special posting
            rateId = atp.getRefContractorRateVo().getId();
          } else {
            // primary or both
            rateId = atp.getRefContractorRateVo().getId();
          }
          
          rateMap = dateMap.get(date);
          if(rateMap == null) {
            rateMap = new HashMap<Long, Collection<AssignmentTimePostVo>>();
            dateMap.put(date, rateMap);
          }
          
          atps = rateMap.get(rateId);
          if(atps == null){
            atps = new ArrayList<AssignmentTimePostVo>();
            rateMap.put(rateId, atps);
          }
          
          atps.add(atp);
        }
      }
    }
    return incMap;
  }
  
	public static Map<Long, Map<Long, Map<Date, Collection<AssignmentTimePostVo>>>> collectTimePostsByIncidentCodeAndDate(
      Collection<IncidentResourceVo> incidentResources, 
      Collection<Date> dates) {
    
	  Map<Long, Map<Long, Map<Date, Collection<AssignmentTimePostVo>>>> incMap 
        = new HashMap<Long, Map<Long, Map<Date, Collection<AssignmentTimePostVo>>>>();
	  Map<Long, Map<Date, Collection<AssignmentTimePostVo>>> codeMap;
    Map<Date, Collection<AssignmentTimePostVo>> dateMap;
    Collection<AssignmentTimePostVo> atps;
    
    
    
    for (IncidentResourceVo ir : incidentResources) {
      Set<Long> accountCodeIds = getAccountCodeIdSet(ir);
      codeMap = new HashMap<Long, Map<Date, Collection<AssignmentTimePostVo>>>();
      
      for(Long codeId :accountCodeIds) {
        dateMap = new HashMap<Date, Collection<AssignmentTimePostVo>>();
        
        for (Date dt : dates) {
          atps = new ArrayList<AssignmentTimePostVo>();  
          for (AssignmentVo a : ir.getWorkPeriodVo().getAssignmentVos()) {
            for(AssignmentTimePostVo atp : a.getAssignmentTimeVo().getAssignmentTimePostVos()) {
              if(DateUtil.makeEndOfDay(atp.getPostStartDate()).equals(dt) 
                  && atp.getIncidentAccountCodeVo().getAccountCodeVo().getId() == codeId) {
                atps.add(atp);
              }
            }
          }
          if(atps.size()>0)
            dateMap.put(dt, atps);
        }
        if(dateMap.size()>0)
          codeMap.put(codeId, dateMap);
      }
      if(codeMap.size()>0)
        incMap.put(ir.getIncidentVo().getId(), codeMap);
    }
    return incMap;
  }
	
	public static Map<Long, Map<Date, Collection<AssignmentTimePost>>> collectTimePostsByIncidentAndDate(
      Collection<IncidentResource> incidentResources, 
      Collection<Date> dates) {
    
    Map<Long, Map<Date, Collection<AssignmentTimePost>>> incMap 
        = new HashMap<Long, Map<Date, Collection<AssignmentTimePost>>>();
    Map<Date, Collection<AssignmentTimePost>> dtMap;
    Collection<AssignmentTimePost> datp;
    
    for (IncidentResource ir : incidentResources) {
      dtMap = new HashMap<Date, Collection<AssignmentTimePost>>();
      
      for (Date dt : dates) {
        datp = new ArrayList<AssignmentTimePost>();  
        for (Assignment a : ir.getWorkPeriod().getAssignments()) {
          for(AssignmentTimePost atp : a.getAssignmentTime().getAssignmentTimePosts()) {
            if(DateUtil.makeEndOfDay(atp.getPostStartDate()).equals(dt)) {
              datp.add(atp);
            }
          }
        }
        if(datp.size()>0)
          dtMap.put(dt, datp);
      }
      if(dtMap.size()>0)
        incMap.put(ir.getIncidentId(), dtMap);
    }
    return incMap;
  }
	
	private static Set<Long> getAccountCodeIdSet(IncidentResourceVo incidentResource) {
	  Set<Long> codes = new HashSet<Long>();
	  Collection<AssignmentVo> as = incidentResource.getWorkPeriodVo().getAssignmentVos();
	  for(AssignmentVo a : as) {
	    Collection<AssignmentTimePostVo> atps = a.getAssignmentTimeVo().getAssignmentTimePostVos();
	    for(AssignmentTimePostVo atp : atps) {
	      codes.add(atp.getIncidentAccountCodeVo().getAccountCodeVo().getId());
	    }
	  }
	  return codes;
	}
	
}
