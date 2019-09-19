package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.CostRateFilterData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentCostRateData;
import gov.nwcg.isuite.core.service.IncidentCostRateService;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/incident-cost-rates")
public class IncidentCostRateController extends BaseRestController {

	@Autowired
	private IncidentCostRateService service;
	
	/**
	 * Has no body since it is a GET.  Use query params provided in the CostRateFilterData.
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getDefaultRatesGrid(CostRateFilterData data) throws ServiceException {
		return resolveMessaging(service.getDefaultRatesGrid(data.getCostRateFilter(),data.getDialogueVo()));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIncidentCostRate(CostRateFilterData data) throws ServiceException {
		return resolveMessaging(service.getIncidentCostRate(data.getCostRateFilter(),data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/kinds", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentCostRateKind(@RequestBody IncidentCostRateData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentCostRateKind(data.getIncidentCostRateKindVo(), data.getCategory(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/ovhds", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentCostRateOvhd(@RequestBody IncidentCostRateData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentCostRateOvhd(data.getIncidentCostRateOvhdVo(), data.getCategory(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/states", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentCostRateState(@RequestBody IncidentCostRateData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentCostRateState(data.getIncidentCostRateStateVo(), data.getCategory(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/kind-states", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIncidentCostRateStateKind(@RequestBody IncidentCostRateData data) throws ServiceException {
		return resolveMessaging(service.saveIncidentCostRateStateKind(data.getIncidentCostRateStateKindVo(), data.getCategory(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/ovhds", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIncidentCostRateOvhd(CostRateFilterData filter) throws ServiceException {
		return resolveMessaging(service.getIncidentCostRateOvhd(filter.getCostRateFilter(), filter.getDialogueVo()));
	}
	
	@RequestMapping(value = "/states", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIncidentCostRateState(CostRateFilterData filter) throws ServiceException {
		return resolveMessaging(service.getIncidentCostRateState(filter.getCostRateFilter(), filter.getDialogueVo()));
	}
	
	@RequestMapping(value = "/state-kinds", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIncidentCostRateStateKinds(CostRateFilterData filter) throws ServiceException {
		return resolveMessaging(service.getIncidentCostRateStateKinds(filter.getCostRateFilter(), filter.getDialogueVo()));
	}
}
