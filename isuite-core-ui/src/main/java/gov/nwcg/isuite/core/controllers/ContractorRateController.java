package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.ContractorRateData;
import gov.nwcg.isuite.core.service.ContractorRateService;
import gov.nwcg.isuite.core.vo.AssignmentTimeVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.ContractorPaymentInfoVo;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.core.vo.WorkPeriodVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

@Controller
@RequestMapping("/contractor-rates")
public class ContractorRateController extends BaseRestController {

	@Autowired
	private ContractorRateService service;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo save(@RequestBody ContractorRateData data)
			throws ServiceException, ValidationException {
		return resolveMessaging(service.save(data.getContractorRateVo(), data.getWorkPeriodVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteContractorRate(@PathVariable("id") long id,
			@RequestParam(value = "assignmentTimeId", required = false) Long assignmentTimeId,
			@RequestParam(value = "contractorPaymentInfoId", required = false) Long contractorPaymentInfoId) throws ServiceException {
		ContractorRateVo vo = new ContractorRateVo();
		vo.setId(id);
		WorkPeriodVo wpVo = new WorkPeriodVo();
		AssignmentVo aVo;
		AssignmentTimeVo atVo;
		if (assignmentTimeId != null || contractorPaymentInfoId != null) {
			aVo = new AssignmentVo();
			atVo = new AssignmentTimeVo();
			atVo.setId(assignmentTimeId);
			if (contractorPaymentInfoId != null) {
				ContractorPaymentInfoVo cpiVo = new ContractorPaymentInfoVo();
				cpiVo.setId(contractorPaymentInfoId);
				atVo.setContractorPaymentInfoVo(cpiVo);
			}
			aVo.setAssignmentTimeVo(atVo);
			wpVo.setCurrentAssignmentVo(aVo);
		}

		return resolveMessaging(service.deleteContractorRate(vo, wpVo, null));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getById(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.getById(id, null));
	}
	
	@RequestMapping(value = "/grid", method = RequestMethod.POST)
	public @ResponseBody DialogueVo getGrid(
			@RequestBody ContractorRateData data) throws ServiceException {
		DialogueVo vo = service.getGrid(data.getContractorRateFilter(), null);
		return resolveMessaging(vo);
	}

}
