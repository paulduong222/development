package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.FinancialExportData;
import gov.nwcg.isuite.core.service.FinancialExportService;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/exports/financials")
public class FinancialExportController extends BaseRestController {

	@Autowired
	private FinancialExportService service;
	
	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getGrid(null, incidentId, incidentGroupId));
	}
	
	@RequestMapping(value = "export", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo export(@RequestBody FinancialExportData data) throws ServiceException {
		return resolveMessaging(service.export(data.getDialogueVo(), data.getFinancialExportVo()));
	}
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo save(@RequestBody FinancialExportData data) throws ServiceException {
		return resolveMessaging(service.save(data.getDialogueVo(), data.getFinancialExportVo()));
	}

}
