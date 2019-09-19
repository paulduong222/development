package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.IncidentResourceOtherData;
import gov.nwcg.isuite.core.filter.impl.OtherResourceCostFilterImpl;
import gov.nwcg.isuite.core.service.OtherResourceCostService;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/costs/resources/other")
public class OtherResourceCostController extends BaseRestController {

	@Autowired
	private OtherResourceCostService service;

	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid(OtherResourceCostFilterImpl queryParams)
			throws ServiceException {
		return resolveMessaging(service.getGrid(queryParams, null));
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveOtherResource(@RequestBody IncidentResourceOtherData data)
			throws ServiceException {
		return resolveMessaging(service.saveOtherResource(data.getIncidentResourceOtherVo(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getOtherResourceById(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.getOtherResourceById(id, null));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteOtherResource(@PathVariable("id") long id) throws ServiceException {

		return resolveMessaging(service.deleteOtherResource(id, null));
	}
}
