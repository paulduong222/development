package gov.nwcg.isuite.core.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.TimePostAdjustmentData;
import gov.nwcg.isuite.core.service.TimePostAdjustmentService;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/time-posts/adjustments")
public class TimePostAdjustmentController extends BaseRestController {

	@Autowired
	private TimePostAdjustmentService service;
	
	@RequestMapping(value = "/assignments/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getTimeAdjustVos(@PathVariable("id") Long assignmentId) throws ServiceException {
		return resolveMessaging(service.getTimeAdjustVos(assignmentId, null));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIncidentResourceAndTimeAdjustVos(
			@RequestParam(value = "incidentResourceId", required = false) Long incidentResourceId,
			@RequestParam(value = "assignmentId", required = false) Long assignmentId) throws ServiceException {
		return resolveMessaging(service.getIncidentResourceAndTimeAdjustVos(incidentResourceId, assignmentId, null));
	}
	
	@RequestMapping(value = "/{timeAssignAdjustId}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getById(@PathVariable("timeAssignAdjustId") Long timeAssignAdjustId) throws ServiceException {
		return resolveMessaging(service.getById(timeAssignAdjustId, null));
	}
	
	/**
	 * Singular endpoint to support both the standard save and the batch-mode save.
	 * By default (no request parameter) it will do a standard <em>save</em>.
	 * 
	 * To perform a <em>batch save</em>, set requestParameter batch=true.
	 * 	example: POST /time-posts/adjustments?batch=true
	 * 
	 * @param batchMode
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo save(
			@RequestParam(value = "batch", defaultValue = "false", required = false) boolean batchMode,
			@RequestBody TimePostAdjustmentData data) throws ServiceException {
		
		if (batchMode) return resolveMessaging(service.saveBatch(data.getIncidentResourceVo(), data.getTimeAssignAdjustVo(), data.getCrewIds(), data.getDialogueVo()));
		else return resolveMessaging(service.save(data.getIncidentResourceVo(), data.getTimeAssignAdjustVo(), data.getDialogueVo()));
	}
	
	/**
	 * Singular endpoint to support deleting adjustments by id or to include deletion by crewIds.
	 * 
	 * Don't include any request parameters for a standard delete.
	 * 
	 * Use request parameter <em>crewId</em> to perform deletes against one or more crew ids:
	 * 	example: DELETE /time-posts/adjustments?crewId=1&crewId2...  
	 * 
	 * @param id
	 * @param crewIds
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo delete(
			@PathVariable("id") long id,
			@RequestParam(value="crewId", required=false) Set<Integer> crewIds) throws ServiceException {
		
		if (CollectionUtils.isEmpty(crewIds)) return resolveMessaging(service.deleteAdjustment(id, null));
		else return resolveMessaging(service.deleteAdjustmentBatch(id, crewIds, null));
	}
	
	
}
