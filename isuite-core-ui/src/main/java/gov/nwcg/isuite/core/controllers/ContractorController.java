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

import gov.nwcg.isuite.core.controllers.restdata.ContractorAgreementData;
import gov.nwcg.isuite.core.controllers.restdata.ContractorData;
import gov.nwcg.isuite.core.filter.ContractorFilter;
import gov.nwcg.isuite.core.filter.impl.ContractorFilterImpl;
import gov.nwcg.isuite.core.service.ContractorService2;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.LongUtility;

@Controller
@RequestMapping("/contractors")
public class ContractorController extends BaseRestController {

	@Autowired
	private ContractorService2 service;

	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIapPlanGrid(
			@RequestParam(value="incidentId", required=false) Long incidentId,
			@RequestParam(value="incidentGroupId", required=false) Long incidentGroupId) throws ServiceException {
		ContractorFilter filter = new ContractorFilterImpl();
		filter.setIncidentGroupId(incidentGroupId);
		Collection<Long> incidentIds = new ArrayList<Long>();
		if(LongUtility.hasValue(incidentId))
			incidentIds.add(incidentId);
		filter.setIncidentIds(incidentIds);
		return resolveMessaging(service.getGrid(filter, null));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getById(@PathVariable("id") Long id) throws ServiceException {
		DialogueVo dvo = service.getById(id, null);
		return super.resolveMessaging(dvo);
	}
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo save(@RequestBody ContractorData data) throws ServiceException, ValidationException {
		DialogueVo dvo = service.save(data.getContractorVo(), data.getDialogueVo());
		return super.resolveMessaging(dvo);
	}	

	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo delete(@RequestBody() ContractorData data) throws ServiceException {
		DialogueVo dvo = service.deleteContractor(data.getContractorVo(), data.getDialogueVo());
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/{id}/agreement", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveAgreement(
			@PathVariable("id") Long contractorId, 
			@RequestBody ContractorAgreementData data) throws ServiceException, ValidationException {
		DialogueVo dvo = service.saveAgreement(data.getContractorAgreementVo(), data.getDialogueVo());
		return super.resolveMessaging(dvo);
	}	

	@RequestMapping(value = "/{id}/agreement", method = { RequestMethod.DELETE })
	public @ResponseBody DialogueVo deleteAgreement(
			@PathVariable("id") Long contractorId, 
			@RequestBody ContractorAgreementData data) throws ServiceException, ValidationException {
		DialogueVo dvo = service.deleteAgreement(data.getContractorAgreementVo().getId(), data.getDialogueVo());
		return super.resolveMessaging(dvo);
	}	
	
}
