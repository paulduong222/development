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

import gov.nwcg.isuite.core.controllers.restdata.JetportData;
import gov.nwcg.isuite.core.service.JetportService2;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

@Controller
@RequestMapping("/jetports")
public class JetportController extends BaseRestController {

	private static final Logger LOG = LoggerFactory.getLogger(JetportController.class);
	
	@Autowired
	private JetportService2 service;
	
	/**
	 * The "upsert" REST endpoint to create or modify an Agency Group.
	 * 
	 * Suggested usage is to use a POST when creating and a PUT when updating.
	 * 
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo save(@RequestBody JetportData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.save(data.getJetportVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo delete(@PathVariable("id") long id) throws ServiceException {
		LOG.debug("Deleting jetport id: " + id);
		JetPortVo vo = new JetPortVo();
		vo.setId(id);

		return resolveMessaging(service.deleteJetport(vo, null));
	}
	
	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId,
			@RequestParam(value = "incidentOnly", required = false) Boolean incidentOnly) throws ServiceException {
		return incidentId == null && incidentGroupId == null 
				? resolveMessaging(service.getGrid(null))
				: resolveMessaging(service.getGridIncidentorGroup(incidentId, incidentGroupId, incidentOnly, null));
	}

}
