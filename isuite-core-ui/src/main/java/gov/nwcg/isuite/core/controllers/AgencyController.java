package gov.nwcg.isuite.core.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.AgencyData;
import gov.nwcg.isuite.core.service.AgencyService2;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

@Controller
@RequestMapping("/agencies")
public class AgencyController extends BaseRestController {

	private static final Logger LOG = LoggerFactory.getLogger(AgencyController.class);

	@Autowired
	private AgencyService2 service;

	/**
	 * The "upsert" REST endpoint to create or modify an Agency.
	 * 
	 * Suggested usage is to use a POST when creating and a PUT when updating.
	 * 
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo save(@RequestBody AgencyData data) throws ServiceException, ValidationException {
		DialogueVo dvo = service.save(data.getAgencyVo(), data.getDialogueVo());
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo delete(@PathVariable("id") long id)
			throws ServiceException {
		LOG.debug("Deleting Agency id: " + id);
		AgencyVo vo = new AgencyVo();
		vo.setId(id);

		DialogueVo dvo = service.deleteAgency(vo, null);
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid() throws ServiceException {
		DialogueVo dvo = service.getGrid(null);
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGridIncidentOrGroup(@RequestParam("incidentId") Long incidentId,
			@RequestParam("incidentGroupId") Long incidentGroupId,  @RequestParam("incidentOnly") Boolean incidentOnly) throws ServiceException {
		DialogueVo dvo = service.getGridIncidentorGroup(incidentId, incidentGroupId, incidentOnly, null);
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getById(@PathVariable("id") Long id) throws ServiceException {
		LOG.debug("Looking up Agency for id: " + id);
		DialogueVo dvo = service.getById(id, null);
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/standard", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getStandardAgencies() throws ServiceException {
		DialogueVo dvo = service.getStandardAgencies(null);
		return super.resolveMessaging(dvo);
	}
}
