package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.filter.impl.DataAuditTrackingFilterImpl;
import gov.nwcg.isuite.core.service.DataAuditTrackingService;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.IsuiteException;

@Controller
@RequestMapping("/dataaudit")
public class DataAuditTrackingController extends BaseRestController {

	@Autowired
	private DataAuditTrackingService service;

	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseBody
	public DialogueVo getData(@RequestBody DataAuditTrackingFilterImpl filter) throws IsuiteException {
		if(null != filter) {
			if(DateTransferVo.hasDateString(filter.getStartDateVo())) {
				filter.setStartDate(DateTransferVo.getDate(filter.getStartDateVo()));
			}
			if(DateTransferVo.hasDateString(filter.getEndDateVo())) {
				filter.setEndDate(DateTransferVo.getDate(filter.getEndDateVo()));
			}
		}
		return service.getGrid(null, filter);
	}
}
