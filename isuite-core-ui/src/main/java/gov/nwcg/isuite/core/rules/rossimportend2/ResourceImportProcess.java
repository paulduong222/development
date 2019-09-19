package gov.nwcg.isuite.core.rules.rossimportend2;

import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;

public class ResourceImportProcess {
	public static HashMap<Long,Collection<CostResourceDataVo>> processes = new HashMap<Long,Collection<CostResourceDataVo>>();
	public static HashMap<Long,Hashtable> cdrHt = new HashMap<Long,Hashtable>();
	
	public static Long addProcess(Collection<CostResourceDataVo> costDataResourceVos) {
		Long processId = new Long(processes.size() + 1);
		Hashtable ht = CollectionUtility.splitCollection(costDataResourceVos, 50);
		
		processes.put(processId, costDataResourceVos);
		cdrHt.put(processId,ht);
		return processId;
	}

	public static int getProcessSequenceSize(Long processId) {
		Hashtable ht = (Hashtable)cdrHt.get(processId);
		if(null != ht){
			return ht.size();
		}
		return 0;
	}
	
	public static Boolean hasNext(Long processId, int sequence) {
		Hashtable ht = (Hashtable)cdrHt.get(processId);
		if(null != ht && ht.containsKey(sequence)){
			return true;
		}else{
			return false;
		}
	}

	public static Collection<CostResourceDataVo> getCompleteList(Long processId) {
		if(processes.containsKey(processId)){
			Collection<CostResourceDataVo> list = processes.get(processId);
			return list;
		}else
			return null;
	}
	
	public static Collection<CostResourceDataVo> getSequenceGroup(Long processId, int sequence) {
		Hashtable ht = (Hashtable)cdrHt.get(processId);
		if(null != ht && ht.containsKey(sequence)){
			Collection<CostResourceDataVo> vos = (Collection<CostResourceDataVo>)ht.get(sequence);
			return vos;
		}else{
			return null;
		}
	}

	public static void removeProcess(Long processId) {
		if(cdrHt.containsKey(processId)){
			cdrHt.remove(processId);
		}
		if(processes.containsKey(processId)){
			processes.remove(processId);
		}
	}
	
	
}
