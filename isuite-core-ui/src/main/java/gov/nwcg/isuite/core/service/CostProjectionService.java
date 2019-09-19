package gov.nwcg.isuite.core.service;

import java.util.Collection;
import java.util.List;

import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.ProjectionItemVo;
import gov.nwcg.isuite.core.vo.ProjectionVo;
import gov.nwcg.isuite.core.vo.ProjectionWorksheetGridVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public interface CostProjectionService extends TransactionService {

	//public DialogueVo getIncidentsById(Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getProjectionGrid(Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getIncidentSummaryGrid(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getManuallyAddProjectionItemGrid(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getSupportingCostGrid(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getProjectionWorkSheetGrid(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo addProjection(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo updateProjection(ProjectionVo vo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo deleteProjection(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException;
	
	//public DialogueVo updateIncidentSummaryProjectionItem(ProjectionVo projectionVo, ProjectionItemVo projectionItemVo,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo addProjectionItem(ProjectionVo projectionVo, ProjectionItemVo projectionItemVo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo updateProjectionItem(ProjectionVo projectionVo, ProjectionItemVo projectionItemVo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo deleteProjectionItem(ProjectionVo projectionVo, ProjectionItemVo projectionItemVo, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getManuallyAddProjectionItemCode(ProjectionVo projectionVo,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo updatePorjectionQuantity(ProjectionVo projectionVo, ProjectionWorksheetGridVo projectionWorksheetGridVo,DialogueVo dialogueVo);
	public DialogueVo updateSupportingCostAverageCost(ProjectionVo projectionVo,DialogueVo dialogueVo);
	public DialogueVo getProjectionWorkSheetStartDate(ProjectionVo projectionVo, DialogueVo dialogueVo) throws ServiceException;
}
