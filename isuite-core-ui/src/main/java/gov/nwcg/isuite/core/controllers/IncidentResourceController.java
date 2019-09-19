package gov.nwcg.isuite.core.controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.IncidentResourceData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentResourceFilterData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentResourceGridData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentSelectorRequestParams;
import gov.nwcg.isuite.core.controllers.restdata.ResourceInventoryGridData;
import gov.nwcg.isuite.core.filter.IncidentResourceFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentResourceFilterImpl;
import gov.nwcg.isuite.core.service.IncidentResource2Service;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.CollectionUtility;

@Controller
@RequestMapping("/incident-resources")
public class IncidentResourceController extends BaseRestController {

	@Autowired
	private IncidentResource2Service service;
	
	// TODO: Setup filter.
	/*
	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid(
			IncidentResourceFilterData filter) throws ServiceException {
		return resolveMessaging(service.getGrid(filter.getIncidentResourceFilter(), filter.getSortFields(), null));
	}
	*/
	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid(IncidentSelectorRequestParams params) throws ServiceException {
		IncidentResourceFilter filter = new IncidentResourceFilterImpl();
		
		filter.setIncidentId(params.getIncidentId());
		filter.setIncidentGroupId(params.getIncidentGroupId());

		DialogueVo dvo =  service.getGrid(filter, null, null);
		/*
		 * Reconstruct the collection of IncidentResourceVo's.
		 * in a different hierarchical manner
		
		if(CollectionUtility.hasValue(dvo.getRecordset())) {
			@SuppressWarnings("unchecked")
			Collection<IncidentResourceGridVo> newVos 
				= IncidentResourceGridVo.reGroup((Collection<IncidentResourceGridVo>)dvo.getRecordset(), new ArrayList<String>(), new ArrayList<Long>());
			dvo.setRecordset(newVos);
		}
		 */
		
		
		return super.resolveMessaging(dvo);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getById(@PathVariable("id") Long id) throws ServiceException {
		return resolveMessaging(service.getById(id, null));
	}
	
	@RequestMapping(value = "/{incidentResourceId}/resources/{resourceId}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteResource(
			@PathVariable("incidentResourceId") Long incidentResourceId,
			@PathVariable("resourceId") Long resourceId) throws ServiceException {
		IncidentResourceGridVo vo = new IncidentResourceGridVo();
		vo.setIncidentResourceId(incidentResourceId);
		vo.setResourceId(resourceId);

		return resolveMessaging(service.deleteResource(vo, null));
	}
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveResource(
			@RequestBody IncidentResourceData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.saveResource(data.getIncidentResourceVo(), data.getPropagateToChildren(), data.getDialogueVo()));
	}
	
	// addPermanentResource has no implementation.  Ignoring.
	
	@RequestMapping(value = "/unassigned/{dispatchCenter}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getUnassignedInventoryResources(@PathVariable("dispatchCenter") String dispatchCenter) throws ServiceException {
		return resolveMessaging(service.getUnassignedInventoryResources(dispatchCenter, null));
	}
	
	@RequestMapping(value = "/prep-inventory", method = RequestMethod.POST)
	public @ResponseBody DialogueVo getPrepInventoryResources(@RequestBody ResourceInventoryGridData data) throws ServiceException {
		return resolveMessaging(service.getPrepInventoryResources(data.getResourceInventoryGridVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/{parentResourceId}/rosters", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveAndRoster(
			@PathVariable("parentResourceId") long parentResourceId, @RequestBody IncidentResourceData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.saveAndRoster(parentResourceId, data.getIncidentResourceVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/rosters/{rosterParentId}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo loadParentAndSupplimentals(
			@PathVariable("rosterParentId") Long id) throws ServiceException {
		return resolveMessaging(service.loadParentAndSupplimentals(id, null));
	}
	
	@RequestMapping(value = "/rosters/unroster", method = RequestMethod.POST)
	public @ResponseBody DialogueVo unroster(@RequestBody IncidentResourceGridData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.unroster(data.getIncidentResourceGridVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/rosters/{parentResourceId}/existing", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo rosterExisting(
			@PathVariable("parentResourceId") long parentResourceId, 
			@RequestBody IncidentResourceGridData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.rosterExisting(parentResourceId, data.getIncidentResourceGridVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/strike-teams", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getStrikeTeams(
			@RequestParam(value =  "incidentId", required = false) Long incidentId,
			@RequestParam(value =  "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getStrikeTeams(incidentId, incidentGroupId, null));
	}
	
	@RequestMapping(value = "/{incidentResourceId}/crews/propagate/employment-types/{employmentType}", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo propagateCrewEmploymentType(
			@PathVariable("incidentResourceId") long incidentResourceId, 
			@PathVariable("employmentType") String employmentType) throws ServiceException, ValidationException {
		return resolveMessaging(service.propagateCrewEmploymentType(employmentType, incidentResourceId, null));
	}
	
	@RequestMapping(value = "/{incidentResourceId}/crews/propagate/address", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo propagateCrewAddress(
			@PathVariable("incidentResourceId") long incidentResourceId) throws ServiceException, ValidationException {
		return resolveMessaging(service.propagateCrewAddress(incidentResourceId, null));
	}
	
	@RequestMapping(value = "/{incidentResourceId}/crews/clear/address", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo clearAllCrewAddress(
			@PathVariable("incidentResourceId") long incidentResourceId) throws ServiceException, ValidationException {
		return resolveMessaging(service.clearAllCrewAddress(incidentResourceId, null));
	}
	
	@RequestMapping(value = "/crews/members", method = RequestMethod.POST)
	public @ResponseBody DialogueVo getAllCrewMembers(
			@RequestBody IncidentResourceGridData data) throws ServiceException {
		return resolveMessaging(service.getAllCrewMembers(data.getIncidentResourceGridVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/{incidentResourceId}/max-posting-date", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getMaxPostingDate(@PathVariable("incidentResourceId") Long id,
			@RequestParam(value = "isCrew", required = false, defaultValue = "false") Boolean isCrew) throws ServiceException {
		return resolveMessaging(service.getMaxPostingDate(id, isCrew, null));
	}
	
	@RequestMapping(value = "/incident-groups/{incidentGroupId}/account-codes", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIncidentGroupAccountCodes(@PathVariable("incidentGroupId") Long incidentGroupId) 
			throws ServiceException {
		return resolveMessaging(service.getIncidentGroupAccountCodes(null, incidentGroupId));
	}
	
	@RequestMapping(value = "/iap", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIapResources(
			@RequestParam(value =  "incidentId", required = false) Long incidentId,
			@RequestParam(value =  "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getIapResources(incidentId, incidentGroupId, null));
	}
	
	@RequestMapping(value = "/{incidentResourceId}/crew-hire-info/clear", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo clearAllCrewHireInfo(
			@PathVariable("incidentResourceId") long incidentResourceId) throws ServiceException, ValidationException {
		return resolveMessaging(service.clearAllCrewHireInfo(incidentResourceId, null));
	}
	
	@RequestMapping(value = "/{incidentResourceId}/crew-hire-info/propagate", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo propagateCrewHireInfo(
			@PathVariable("incidentResourceId") long incidentResourceId) throws ServiceException, ValidationException {
		return resolveMessaging(service.propagateCrewHireInfo(incidentResourceId, null));
	}
}
