package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.reports.filter.GlidePathReportFilter;
import gov.nwcg.isuite.core.service.GlidePathReportService;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/glide-path-reports")
public class GlidePathReportController extends BaseRestController {

	@Autowired
	private GlidePathReportService service;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateGlidePathReport(@RequestBody GlidePathReportFilter data)
			throws ServiceException, PersistenceException {
		return resolveMessaging(service.generateGlidePathReport(data, null));
	}
}
