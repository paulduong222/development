package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.RateClassRateData;
import gov.nwcg.isuite.core.service.impl.RateClassRateServiceImpl;
import gov.nwcg.isuite.core.vo.RateClassRateVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/rate-class-rates")
public class RateClassRateController extends BaseRestController {

	@Autowired
	@Qualifier("rateClassRateService")
	private RateClassRateServiceImpl service;
	
	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid() throws ServiceException {
		return resolveMessaging(service.getGrid(null));
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo save(@RequestBody RateClassRateData data) throws ServiceException {
		return resolveMessaging(service.save(data.getRateClassRateVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getById(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.getById(id, null));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteRateClassRate(@PathVariable("id") long id) throws ServiceException {
		RateClassRateVo vo = new RateClassRateVo();
		vo.setId(id);

		return resolveMessaging(service.deleteRateClassRate(vo, null));
	}
}
