package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentSelector2Vo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface IncidentSelectorService {

	public DialogueVo getGrid(Long userId, DialogueVo dialogueVo) throws ServiceException ;

	public void setIncidentId(Long incidentId) ;
	
	public void setIncidentGroupId(Long incidentGroupId) ;	

	public void setIncidentsOnly(Boolean val);

	public DialogueVo getCustomUserIncDataView(Long userId, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo saveCustomView(Long userId, Collection<IncidentSelector2Vo> excludedVos, DialogueVo dialogueVo) throws ServiceException ;
	
	public Boolean getFilterExcluded() ;

	public void setFilterExcluded(Boolean filterExcluded) ;	
	
}
