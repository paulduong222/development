package gov.nwcg.isuite.core.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.service.AccountCodeService;
import gov.nwcg.isuite.core.vo.AccountCodeVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/account-codes")
public class AccountCodeController extends BaseRestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(AccountCodeController.class);
	
	@Autowired
	private AccountCodeService service;

	@RequestMapping(method= {RequestMethod.POST, RequestMethod.PUT})
	public @ResponseBody AccountCodeVo save(@RequestBody AccountCodeVo vo) throws ServiceException {
		return service.save(vo);
	}

	@RequestMapping(value="/{accountCode}/agencies/{agencyId}", method=RequestMethod.GET)
	public @ResponseBody AccountCodeVo getByKey(@PathVariable("accountCode") String accountCode, 
			@PathVariable("agencyId") Long agencyId) throws ServiceException {
		LOG.debug(String.format("Looking up AccountCode by accountCode [%s] and agencyId [%d].", accountCode, agencyId));
		AgencyVo agencyVo = new AgencyVo();
		agencyVo.setId(agencyId);
		return service.getByKey(accountCode, agencyVo);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public @ResponseBody AccountCodeVo getById(@PathVariable("id") Long id) throws ServiceException {
		LOG.debug("Looking up AccountCode for id: " + id);
		return service.getById(id);
	}
}
