package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.CostAccrualExtractData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentAccountCodeData;
import gov.nwcg.isuite.core.service.CostAccrualService;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/cost-accruals")
public class CostAccrualController extends BaseRestController {

	@Autowired
	private CostAccrualService service;
	
	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getGrid(incidentId, incidentGroupId, null));
	}

	@RequestMapping(value = "/extract", method = RequestMethod.POST)
	public @ResponseBody DialogueVo runExtract(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId,
			@RequestBody CostAccrualExtractData data) throws ServiceException {
		return resolveMessaging(service.runExtract(data.getExtractDate(), incidentId, incidentGroupId, data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/finalize", method = RequestMethod.POST)
	public @ResponseBody DialogueVo runFinalize(@RequestBody CostAccrualExtractData data) throws ServiceException {
		return resolveMessaging(service.runFinalize(data.getCostAccrualExtractVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/accrual-code", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveAccrualCode(@RequestBody IncidentAccountCodeData data) throws ServiceException {
		return resolveMessaging(service.saveAccrualCode(data.getIncidentAccountCodeVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/reports/summary/{extractId}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo generateAccrualSummaryReport(@PathVariable("extractId") long id) throws ServiceException {
		return resolveMessaging(service.generateAccrualSummaryReport(id, null));
	}
	
	@RequestMapping(value = "/reports/detail/{extractId}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo generateAccrualDetailReport(@PathVariable("extractId") long id) throws ServiceException {
		return resolveMessaging(service.generateAccrualDetailReport(id, null));
	}
	
	@RequestMapping(value = "/reports/all-detail/{extractId}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo generateAccrualAllDetailReport(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.generateAccrualAllDetailReport(incidentId, incidentGroupId, null));
	}
}
