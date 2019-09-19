package gov.nwcg.isuite.core.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.AgencyGroupData;
import gov.nwcg.isuite.core.service.AgencyGroupService;
import gov.nwcg.isuite.core.service.AgencyGroupService2;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

@Controller
@RequestMapping("/agency-groups")
public class AgencyGroupController extends BaseRestController {

	private static final Logger LOG = LoggerFactory.getLogger(AgencyGroupController.class);

	@Autowired
	private AgencyGroupService service;
	@Autowired
	private AgencyGroupService2 service2;

	/* -------------------- AgencyGroupService2 ---------------------- */

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
	public @ResponseBody DialogueVo save(@RequestBody AgencyGroupData data)
			throws ServiceException, ValidationException {
		return service2.save(data.getAgencyGroupVo(), data.getDialogueVo());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo delete(@PathVariable("id") Long id) throws ServiceException {
		LOG.debug("Deleting AgencyGroup id: " + id);
		AgencyGroupVo vo = new AgencyGroupVo();
		vo.setId(id);

		DialogueVo dvo = service2.deleteAgencyGroup(vo, null);
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid() throws ServiceException {
		DialogueVo dvo = service2.getGrid(null);
		return super.resolveMessaging(dvo);
	}

	/* -------------------- AgencyGroupService ---------------------- */

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody AgencyGroupVo getById(@PathVariable("id") Long id) throws ServiceException {
		LOG.debug("Looking up AgencyGroup for id: " + id);
		return service.getById(id);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable("id") List<Long> ids) throws ServiceException {
		LOG.debug("Deleting AgencyGroup ids: " + ids.toString());
		service.delete(ids);
	}
}
