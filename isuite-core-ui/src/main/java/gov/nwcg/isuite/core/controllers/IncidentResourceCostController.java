package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.IncidentResourceDailyCostData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentResourceData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentResourceGridData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentResourceOtherData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentResourceOtherGridData;
import gov.nwcg.isuite.core.service.IncidentResourceDailyCostService;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/incident-resources/costs")
public class IncidentResourceCostController extends BaseRestController {

	@Autowired
	private IncidentResourceDailyCostService service;
	

	/**
	 * Handles running both individual and multiple manual resource daily costs.
	 * 
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/resources/daily", method = RequestMethod.POST)
	public @ResponseBody DialogueVo runManualResourceDailyCosts(
			@RequestBody IncidentResourceGridData data) throws ServiceException {
		if (data.getIncidentResourceGridVos() != null) {
			return resolveMessaging(service.runManualResourceDailyCosts(data.getIncidentResourceGridVo(), null));
		} else {
			return resolveMessaging(service.runManualResourcesDailyCosts(data.getIncidentResourceGridVo(), data.getIncidentResourceGridVos(), null));
		}
	}	

	/**
	 * Handles running both individual and multiple manual resource other daily costs.
	 * 
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/resources/other/daily", method = RequestMethod.POST)
	public @ResponseBody DialogueVo runManualResourceOtherDailyCosts(
			@RequestParam(value =  "incidentId", required = false) Long incidentId,
			@RequestParam(value =  "incidentGroupId", required = false) Long incidentGroupId,
			@RequestBody IncidentResourceOtherGridData data) throws ServiceException {
		if (data.getIncidentResourceOtherGridVos() != null) {
			return resolveMessaging(service.runManualResourceOtherDailyCosts(data.getIncidentResourceOtherGridVo(), 
					data.getDialogueVo()));
		} else {
			return resolveMessaging(service.runManualResourcesOtherDailyCosts(data.getIncidentResourceOtherGridVo(), 
					data.getIncidentResourceOtherGridVos(), incidentId, incidentGroupId, data.getDialogueVo()));
		}
	}
	
	@RequestMapping(value = "/incidents/{id}/daily", method = RequestMethod.POST)
	public @ResponseBody DialogueVo runManualIncidentDailyCosts(
			@PathVariable("id") long incidentId) throws ServiceException {
		return resolveMessaging(service.runManualIncidentDailyCosts(incidentId, null));
	}
	
	@RequestMapping(value = "/incident-groups/{id}/daily", method = RequestMethod.POST)
	public @ResponseBody DialogueVo runManualIncidentGroupDailyCosts(
			@PathVariable("id") long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.runManualIncidentGroupDailyCosts(incidentGroupId, null));
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteDailyCost(
			@RequestParam(value = "incidentResourceDailyCostId", required = false) Long incidentResourceDailyCostId,
			@RequestParam(value = "incidentResourceId", required = false) Long incidentResourceId) throws ServiceException {
		IncidentResourceDailyCostVo vo = new IncidentResourceDailyCostVo();
		vo.setId(incidentResourceDailyCostId);
		
		if (incidentResourceId != null) {
			IncidentResourceVo irVo = new IncidentResourceVo();
			irVo.setId(incidentResourceId);
			vo.setIncidentResourceVo(irVo);
		}
		
		return resolveMessaging(service.deleteDailyCost(vo, null));
	}	
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveDailyCost(@RequestBody IncidentResourceDailyCostData data) throws ServiceException {
		return resolveMessaging(service.saveDailyCost(data.getIncidentResourceDailyCostVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/{incidentResourceId}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getResourceDailyCosts(@PathVariable("incidentResourceId") Long incidentResourceId) throws ServiceException {
		return resolveMessaging(service.getResourceDailyCosts(incidentResourceId, null));
	}
	
	@RequestMapping(value = "/others/{incidentResourceOtherId}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getResourceOtherDailyCosts(@PathVariable("incidentResourceOtherId") Long incidentResourceOtherId ) 
			throws ServiceException {
		return resolveMessaging(service.getResourceOtherDailyCosts(incidentResourceOtherId, null));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIncidentTotalCost(
			@RequestParam(value =  "incidentId", required = false) Long incidentId,
			@RequestParam(value =  "incidentGroupId", required = false) Long incidentGroupId) 
			throws ServiceException {
		return resolveMessaging(service.getIncidentTotalCost(incidentId, incidentGroupId, null));
	}
	
	@RequestMapping(value = "/rates/resources/update", method = RequestMethod.POST)
	public @ResponseBody DialogueVo runUpdateRatesResource(@RequestBody IncidentResourceData data) 
			throws ServiceException {
		return resolveMessaging(service.runUpdateRatesResource(data.getIncidentResourceVo(), null));
	}
	
	@RequestMapping(value = "/rates/resources/other/update", method = RequestMethod.POST)
	public @ResponseBody DialogueVo runUpdateRatesResourceOther(@RequestBody IncidentResourceOtherData data) 
			throws ServiceException {
		return resolveMessaging(service.runUpdateRatesResourceOther(data.getIncidentResourceOtherVo(), null));
	}
	
	@RequestMapping(value = "/rates/update", method = RequestMethod.POST)
	public @ResponseBody DialogueVo runUpdateRates(
			@RequestParam(value =  "incidentId", required = false) Long incidentId,
			@RequestParam(value =  "incidentGroupId", required = false) Long incidentGroupId) 
			throws ServiceException {
		return resolveMessaging(service.runUpdateRates(incidentId, incidentGroupId, null));
	}
	
	@RequestMapping(value = "/next-sequence", method = RequestMethod.POST)
	public @ResponseBody DialogueVo runDailyCostsNextSequence() throws ServiceException {
		return resolveMessaging(service.runDailyCostsNextSequence(null));
	}
	
	
}
