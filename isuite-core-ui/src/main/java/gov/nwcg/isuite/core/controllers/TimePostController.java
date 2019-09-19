package gov.nwcg.isuite.core.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.AssignmentTimePostData;
import gov.nwcg.isuite.core.controllers.restdata.TimePostContractorData;
import gov.nwcg.isuite.core.service.TimePostService;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/time-posts")
public class TimePostController extends BaseRestController {

	@Autowired
	private TimePostService service;
	
	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody Collection<AssignmentTimePostVo> getGrid(
			@RequestParam(value = "assignmentTimeId") Integer assignmentTimeId) throws ServiceException {
		return service.getGrid(assignmentTimeId);
	}

	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody AssignmentTimePostVo getById(@PathVariable("id") Long id) throws ServiceException {
		return service.getById(id);
	}
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveTimePost(
			@RequestParam("crewAssignmentTimesIds") List<Integer> crewAssignmentTimeIds, 
			@RequestBody AssignmentTimePostData data) throws ServiceException {
		return resolveMessaging(service.saveTimePost(data.getAssignmentTimePostVo(), crewAssignmentTimeIds, data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/contractors", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveTimePostContractor(
			@RequestBody TimePostContractorData data) throws ServiceException {
		return resolveMessaging(service.saveTimePostContractor(data.getPostType(), data.getPrimaryVo(), data.getSpecialVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody void deleteAssignmentTimePosts(@RequestParam(value = "id", required = true) List<Long> ids) throws ServiceException {
		Collection<AssignmentTimePostVo> vos = new ArrayList<AssignmentTimePostVo>();
		for (Long id : ids) {
			AssignmentTimePostVo vo = new AssignmentTimePostVo();
			vo.setId(id);
		}

		service.deleteAssignmentTimePosts(vos);
	}
	
	@RequestMapping(value = "/crews", method = RequestMethod.DELETE)
	public @ResponseBody void deleteAssignmentTimePostsCrew(
			@RequestParam(value = "incidentResourceId", required=false) List<Long> incidentResourceIds, 
			@RequestParam(value = "assignmentTimePostId", required=false) List<Long> assignmentTimePostIds) throws ServiceException {

		Collection<IncidentResourceVo> irVos = new ArrayList<IncidentResourceVo>();
		Collection<AssignmentTimePostVo> atpVos = new ArrayList<AssignmentTimePostVo>();
		
		if (CollectionUtils.isEmpty(incidentResourceIds)) {
			for (Long incidentResourceId : incidentResourceIds) {
				IncidentResourceVo irVo = new IncidentResourceVo();
				irVo.setId(incidentResourceId);
				irVos.add(irVo);
			}
		}
		
		if (CollectionUtils.isEmpty(assignmentTimePostIds)) {
			for (Long assignmentTimePostId : assignmentTimePostIds) {
				AssignmentTimePostVo atpVo = new AssignmentTimePostVo();
				atpVo.setId(assignmentTimePostId);
				atpVos.add(atpVo);
			}
		}

		service.deleteAssignmentTimePostsCrew(irVos, atpVos, null);
	}
	
	@RequestMapping(value = "/crews/grid", method = RequestMethod.GET)
	public @ResponseBody Collection<AssignmentTimePostVo> getGridCrew(
			@PathVariable("assignmentTimeId") Collection<Integer> assignmentTimeIds) throws ServiceException {
		return service.getGridCrew(assignmentTimeIds);
	}
}
