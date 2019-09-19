package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.service.QuickStatsService2;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/quick-stats")
public class QuickStatsController extends BaseRestController {

	@Autowired
	private QuickStatsService2 service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody DialogueVo getQuickStats(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getQuickStats(incidentId, incidentGroupId, null));
	}
}
