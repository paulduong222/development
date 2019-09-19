package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.CostRateFilterData;
import gov.nwcg.isuite.core.controllers.restdata.SysCostRatesData;
import gov.nwcg.isuite.core.service.SysCostRateService;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/sys-cost-rates")
public class SysCostRateServiceController extends BaseRestController {

	@Autowired
	private SysCostRateService service;
	
	/* This may have problems when passing in details to the CostRateFilter.
	 * In particular with the subFilter.  That may pose difficulty and may need
	 * to provide a specific Data class to handle each situation. 
	 */
	@RequestMapping(value = "/default-rates", method = RequestMethod.POST)
	public @ResponseBody DialogueVo getDefaultRatesGrid(@RequestBody CostRateFilterData data) throws Exception {
		return resolveMessaging(service.getDefaultRatesGrid(data.getCostRateFilter(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public @ResponseBody DialogueVo getSysCostRate(@RequestBody CostRateFilterData data) throws ServiceException {
		return resolveMessaging(service.getSysCostRate(data.getCostRateFilter(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/kinds", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveSysCostRateKind(@RequestBody SysCostRatesData data) throws ServiceException {
		return resolveMessaging(service.saveSysCostRateKind(data.getSysCostRateKindVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/ovhds", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveSysCostRateOvhd(@RequestBody SysCostRatesData data) throws ServiceException {
		return resolveMessaging(service.saveSysCostRateOvhd(data.getSysCostRateOvhdVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/states", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveSysCostRateState(@RequestBody SysCostRatesData data) throws ServiceException {
		return resolveMessaging(service.saveSysCostRateState(data.getSysCostRateStateVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/states/kinds", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveSysCostRateStateKind(@RequestBody SysCostRatesData data) throws ServiceException {
		return resolveMessaging(service.saveSysCostRateStateKind(data.getSysCostRateStateKindVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/ovhds/find", method = RequestMethod.POST)
	public @ResponseBody DialogueVo getSysCostRateOvhd(@RequestBody CostRateFilterData data) throws ServiceException {
		return resolveMessaging(service.getSysCostRateOvhd(data.getCostRateFilter(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/states/find", method = RequestMethod.POST)
	public @ResponseBody DialogueVo getSysCostRateState(@RequestBody CostRateFilterData data) throws ServiceException {
		return resolveMessaging(service.getSysCostRateState(data.getCostRateFilter(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/states/kinds/find", method = RequestMethod.POST)
	public @ResponseBody DialogueVo getSysCostRateStateKinds(@RequestBody CostRateFilterData data) throws ServiceException {
		return resolveMessaging(service.getSysCostRateStateKinds(data.getCostRateFilter(), data.getDialogueVo()));
	}
}
