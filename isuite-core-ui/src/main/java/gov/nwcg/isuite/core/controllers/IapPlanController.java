package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapAircraftData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapAircraftTaskData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapAreaLocationCapabilityData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapAttachmentData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapBranchCommSummaryData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapBranchPersonnelData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapBranchRscAssignData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapCopyData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapForm202Data;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapForm203Data;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapForm204Data;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapForm205Data;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapForm206Data;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapForm220Data;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapFrequencyData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapHospitalData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapMedicalAidData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapPersonnelData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapPlanData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapPlanPrintOrderData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapPrintJobData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapRemoteCampLocationData;
import gov.nwcg.isuite.core.service.IapCopyService;
import gov.nwcg.isuite.core.service.IapPlanService;
import gov.nwcg.isuite.core.service.IapReportService;
import gov.nwcg.isuite.core.vo.IapAttachmentVo;
import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

@Controller
@RequestMapping("/iaps")
public class IapPlanController extends BaseRestController {
	
	@Autowired
	private IapCopyService copyService;
	
	@Autowired
	private IapReportService reportService;
	
	@Autowired
	private IapPlanService service;
	
	@RequestMapping(value = "/plans/copy", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo copyPlan(@RequestBody IapCopyData data) throws ServiceException, ValidationException {
		return resolveMessaging(copyService.copyPlan(data.getIapCopyVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/copy", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo copyForm(@RequestBody IapCopyData data) throws ServiceException, ValidationException {
		return resolveMessaging(copyService.copyForm(data.getIapCopyVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/plans/job-data/print", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo printPlan(@RequestBody IapPrintJobData data) throws ServiceException, ValidationException {
		return resolveMessaging(reportService.printPlan(data.getIapPrintJobVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/plans/order-data/print", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo savePlanPrintOrder(@RequestBody IapPlanPrintOrderData data) throws ServiceException, ValidationException {
		return resolveMessaging(reportService.savePlanPrintOrder(data.getIapPlanPrintOrderVo(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIapPlanGrid(
			@RequestParam(value="incidentId", required=false) Long incidentId,
			@RequestParam(value="incidentGroupId", required=false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getIapPlanGrid(incidentId, incidentGroupId, null));
	}

	@RequestMapping(value = "/plans", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapPlan(@RequestBody IapPlanData data) throws ServiceException {
		return resolveMessaging(service.saveIapPlan(data.getIapPlanVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/plans/{id}", method = { RequestMethod.GET })
	public @ResponseBody DialogueVo getIapPlan(@PathVariable("id") Long iapPlanId) throws ServiceException {
		return resolveMessaging(service.getIapPlan(iapPlanId, null));
	}
	
	@RequestMapping(value = "/forms/{formType}/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getIapForm(
			@PathVariable("formType") String formType,
			@PathVariable("id") Long id) throws ServiceException {
		return resolveMessaging(service.getIapForm(id, formType, null));
	}
	
	@RequestMapping(value = "/plans/lock-unlock/{iapPlanId}/{action}", method = RequestMethod.PUT)
	public @ResponseBody DialogueVo lockUnlockIapPlan(
			@PathVariable("iapPlanId") Long iapPlanId,
			@PathVariable("action") String action,
			@RequestBody DialogueData data) throws ServiceException {
		return resolveMessaging(service.lockUnlockIapPlan(iapPlanId, action, data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/lock-unlock/{formType}/{formId}/{action}", method = RequestMethod.PUT)
	public @ResponseBody DialogueVo lockUnlockIapForm(
			@PathVariable("formId") Long formId,
			@PathVariable("formType") String formType,
			@PathVariable("action") String action,
			@RequestBody DialogueData data) throws ServiceException {
		return resolveMessaging(service.lockUnlockIapForm(formId, formType, action, data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/plans/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapPlan(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.deleteIapPlan(id, null));
	}
	
	@RequestMapping(value = "/forms/{formType}/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm(
			@PathVariable("formType") String formType,
			@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.deleteIapForm(id, formType, null));
	}
	
	@RequestMapping(value = "/forms/form-202", method = { RequestMethod.POST })
	public @ResponseBody DialogueVo addIapForm202(@RequestBody IapForm202Data data) throws ServiceException {
		return resolveMessaging(service.saveIapForm202(data.getIapForm202Vo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-202/{id}", method = { RequestMethod.PUT })
	public @ResponseBody DialogueVo updateIapForm202(
			@PathVariable("id") Long id,
			@RequestBody IapForm202Data data) throws ServiceException {
		return resolveMessaging(service.saveIapForm202(data.getIapForm202Vo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-203", method = { RequestMethod.POST })
	public @ResponseBody DialogueVo addIapForm203(@RequestBody IapForm203Data data) throws ServiceException {
		return resolveMessaging(service.saveIapForm203(data.getIapForm203Vo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-203/{id}", method = { RequestMethod.PUT })
	public @ResponseBody DialogueVo updateIapForm203(
			@PathVariable("id") Long id, 
			@RequestBody IapForm203Data data) throws ServiceException {
		return resolveMessaging(service.saveIapForm203(data.getIapForm203Vo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204", method = { RequestMethod.POST })
	public @ResponseBody DialogueVo addIapForm204(@RequestBody IapForm204Data data) throws ServiceException {
		return resolveMessaging(service.saveIapForm204(data.getIapForm204Vo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204/{id}", method = { RequestMethod.PUT })
	public @ResponseBody DialogueVo updateIapForm204(
			@PathVariable("id") Long id,
			@RequestBody IapForm204Data data) throws ServiceException {
		return resolveMessaging(service.saveIapForm204(data.getIapForm204Vo(), data.getDialogueVo()));
	}
	
	
	@RequestMapping(value = "/forms/form-205", method = { RequestMethod.POST })
	public @ResponseBody DialogueVo addIapForm205(@RequestBody IapForm205Data data) throws ServiceException {
		return resolveMessaging(service.saveIapForm205(data.getIapForm205Vo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-205/{id}", method = { RequestMethod.PUT })
	public @ResponseBody DialogueVo updateIapForm205(
			@PathVariable("id") Long id,
			@RequestBody IapForm205Data data) throws ServiceException {
		return resolveMessaging(service.saveIapForm205(data.getIapForm205Vo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-205/frequency", method = {RequestMethod.POST, RequestMethod.PUT})
	public @ResponseBody DialogueVo saveIapForm205Frequency(
			@RequestParam(value="iapForm205Id", required=true) Long iapForm205Id, 
			@RequestBody IapFrequencyData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm205Frequency(iapForm205Id, data.getIapFrequencyVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-205/freq/positions", method = {RequestMethod.POST, RequestMethod.PUT})
	public @ResponseBody DialogueVo saveIapForm205FrequencyPositions(
			@RequestBody IapFrequencyData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm205FrequencyPositions(data.getIapFrequencyVos(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/plans/{iapPlanId}/forms/form-205/{iapForm205Id}/frequencies/verify",
			method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo verifyFrequencies(
			@PathVariable("iapPlanId") long iapPlanId,
			@PathVariable("iapForm205Id") long iapForm205Id,
			@RequestBody IapFrequencyData data) throws ServiceException {
		return resolveMessaging(service.verifyFrequencies(iapPlanId, iapForm205Id, data.getIapFrequencyVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-205/frequencies", method = RequestMethod.POST)
	public @ResponseBody DialogueVo addIapForm205Frequencies(
			@RequestParam(value = "iapForm205Id", required=false) Long iapForm205Id,
			@RequestBody IapFrequencyData data) throws ServiceException {
		return resolveMessaging(service.addIapForm205Frequencies(iapForm205Id, data.getIapFrequencyVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-205/{id}/frequency", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm205Frequency(
			@PathVariable("id") long id,
			@RequestBody IapFrequencyData data) throws ServiceException {
		return resolveMessaging(service.deleteIapForm205Frequency(data.getIapFrequencyVo(), data.getDialogueVo()));
	}

	
	@RequestMapping(value = "/forms/form-206", method = { RequestMethod.POST })
	public @ResponseBody DialogueVo addIapForm206(@RequestBody IapForm206Data data) throws ServiceException {
		return resolveMessaging(service.saveIapForm206(data.getIapForm206Vo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-206/{id}", method = { RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm206(
			@PathVariable("id") Long id,
			@RequestBody IapForm206Data data) throws ServiceException {
		return resolveMessaging(service.saveIapForm206(data.getIapForm206Vo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-206/hospitals", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm206Hospital(
			@RequestParam(value = "id", required = false) Long id,
			@RequestBody IapHospitalData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm206Hospital(id, data.getIapHospitalVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-206/{id}/hospitals", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm206Hospital(
			@PathVariable("id") long id,
			@RequestBody IapHospitalData data) throws ServiceException {
		return resolveMessaging(service.deleteIapForm206Hospital(data.getIapHospitalVo(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/forms/form-206/ambulance", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm206Ambulance(
			@RequestParam(value = "id", required = false) Long id,
			@RequestBody IapMedicalAidData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm206Ambulance(id, data.getIapMedicalAidVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-206/air-ambulance", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm206AirAmbulance(
			@RequestParam(value = "id", required = false) Long id,
			@RequestBody IapMedicalAidData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm206AirAmbulance(id, data.getIapMedicalAidVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-206/{id}/ambulance", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm206Ambulance(
			@PathVariable("id") long id,
			@RequestBody IapMedicalAidData data) throws ServiceException {
		return resolveMessaging(service.deleteIapForm206Ambulance(data.getIapMedicalAidVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-206/{id}/air-ambulance", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm206AirAmbulance(
			@PathVariable("id") long id,
			@RequestBody IapMedicalAidData data) throws ServiceException {
		return resolveMessaging(service.deleteIapForm206AirAmbulance(data.getIapMedicalAidVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-220/helicopter", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm220Helicopter(
			@RequestParam(value = "id", required = false) Long id,
			@RequestBody IapAircraftData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm220Helicopter(id, data.getIapAircraftVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-220/{id}/helicopter", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm220Helicopter(
			@PathVariable("id") long id,
			@RequestBody IapAircraftData data) throws ServiceException {
		return resolveMessaging(service.deleteIapForm220Helicopter(data.getIapAircraftVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-220/task", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm220Task(
			@RequestParam(value = "id", required = false) Long id,
			@RequestBody IapAircraftTaskData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm220Task(id, data.getIapAircraftTaskVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-220/{id}/task", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm220Task(
			@PathVariable("id") long id,
			@RequestBody IapAircraftTaskData data) throws ServiceException {
		return resolveMessaging(service.deleteIapForm220Task(data.getIapAircraftTaskVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204/op-personnel", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm204OpPersonnel(
			@RequestParam(value = "id", required = false) Long id,
			@RequestBody IapBranchPersonnelData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm204OpPersonnel(id, data.getIapBranchPersonnelVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204/{id}/op-personnel", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm204OpPersonnel(
			@PathVariable("id") long id,
			@RequestBody IapBranchPersonnelData data) throws ServiceException {
		return resolveMessaging(service.deleteIapForm204OpPersonnel(data.getIapBranchPersonnelVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204/branch-comm", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm204BranchComm(
			@RequestParam(value = "id", required = false) Long id,
			@RequestBody IapBranchCommSummaryData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm204BranchComm(id, data.getIapBranchCommSummaryVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204/branch-comms", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo addIapForm204BranchComms(
			@RequestParam(value = "id", required = false) Long id,
			@RequestBody IapBranchCommSummaryData data) throws ServiceException {
		return resolveMessaging(service.addIapForm204BranchComms(id, data.getIapBranchCommSummaryVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204/{id}/branch-comm", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm204BranchComm(
			@PathVariable("id") long id,
			@RequestBody IapBranchCommSummaryData data) throws ServiceException {
		return resolveMessaging(service.deleteIapForm204BranchComm(data.getIapBranchCommSummaryVo(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/forms/form-204/branch-rsc-assign", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm204BranchRscAssign(
			@RequestParam(value = "id", required = false) Long id,
			@RequestBody IapBranchRscAssignData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm204BranchRscAssign(id, data.getIapBranchRscAssignVo(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/forms/form-204/rsc-assign", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo addIapForm204RscAssign(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "personNameOrder", required = false) String personNameOrder,
			@RequestBody IapBranchRscAssignData data) throws ServiceException {
		return resolveMessaging(service.addIapForm204RscAssign(id, personNameOrder, data.getIapBranchRscAssignVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204/{id}/branch-rsc-assign", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm204BranchRscAssign(
			@PathVariable("id") long id,
			@RequestBody IapBranchRscAssignData data) throws ServiceException {
		return resolveMessaging(service.deleteIapForm204BranchRscAssign(data.getIapBranchRscAssignVo(), data.getDialogueVo()));
	}

	/**
	 * Receives either a single IapForm203Position or multiple depending on 
	 * which object you populate in the data object.
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/forms/form-203/positions", method = { RequestMethod.POST })
	public @ResponseBody DialogueVo addIapForm203Positions(
			@RequestBody IapPersonnelData data) throws ServiceException {
		if (data.getIapPersonnelVo() != null)
			return resolveMessaging(service.saveIapForm203Position(null, data.getIapPersonnelVo(), data.getDialogueVo()));
		else return resolveMessaging(service.saveIapForm203Positions(data.getIapPersonnelVos(), data.getDialogueVo()));
	}
	
	
	@RequestMapping(value = "/forms/form-203/{id}/positions", method = { RequestMethod.PUT })
	public @ResponseBody DialogueVo updateIapForm203Position(
			@PathVariable("id") Long id,
			@RequestBody IapPersonnelData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm203Position(id, data.getIapPersonnelVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-203/{id}/positions", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm203Position(
			@PathVariable("id") long id,
			@RequestBody IapPersonnelData data) throws ServiceException {
		return resolveMessaging(service.deleteIapForm203Position(data.getIapPersonnelVo(), data.getDialogueVo()));
	}
	

	@RequestMapping(value = "/forms/form-206/area-location-cap", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm206AreaLocationCap(
			@RequestParam(value = "id", required = false) Long id,
			@RequestBody IapAreaLocationCapabilityData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm206AreaLocationCap(id, data.getIapAreaLocationCapabilityVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-206/{id}/area-location-cap", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm206AreaLocationCap(
			@PathVariable("id") long id,
			@RequestBody IapAreaLocationCapabilityData data) throws ServiceException {
		return resolveMessaging(service.deleteIapForm206AreaLocationCap(data.getIapAreaLocationCapabilityVo(), data.getDialogueVo()));
	}
	

	@RequestMapping(value = "/forms/form-206/remote-camp-location", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm206RemoteCampLocations(
			@RequestParam(value = "id", required = false) Long id,
			@RequestBody IapRemoteCampLocationData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm206RemoteCampLocations(id, data.getIapRemoteCampLocationsVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-206/{id}/remote-camp-location", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapForm206RemoteCampLocations(
			@PathVariable("id") long id,
			@RequestBody IapRemoteCampLocationData data) throws ServiceException {
		return resolveMessaging(service.deleteIapForm206RemoteCampLocations(data.getIapRemoteCampLocationsVo(), data.getDialogueVo()));
	}


	
	@RequestMapping(value = "/forms/form-220", method = { RequestMethod.POST })
	public @ResponseBody DialogueVo addIapForm220(@RequestBody IapForm220Data data) throws ServiceException {
		return resolveMessaging(service.saveIapForm220(data.getIapForm220Vo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-220/{id}", method = { RequestMethod.PUT })
	public @ResponseBody DialogueVo updateIapForm220(
			@PathVariable("id") Long id,
			@RequestBody IapForm220Data data) throws ServiceException {
		return resolveMessaging(service.saveIapForm220(data.getIapForm220Vo(), data.getDialogueVo()));
	}
	
	
	@RequestMapping(value = "/plans/{id}/attachments", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveAttachment(@RequestBody IapAttachmentData data) throws ServiceException {
		return resolveMessaging(service.saveAttachment(data.getIapAttachmentVo(), data.getPdfByteArray(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/plans/{id}/attachments", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo delete(@PathVariable("id") long id) throws ServiceException {
		IapAttachmentVo vo = new IapAttachmentVo();
		vo.setId(id);

		return resolveMessaging(service.deleteAttachment(vo, null));
	}
	
	@RequestMapping(value = "/forms/form-203/{id}/reset", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteAndResetToNoBranch(
			@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.deleteAndResetToNoBranch(id, null));
	}
	
	@RequestMapping(value = "/forms/form-203/reset", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo resetToBranch(
			@RequestParam(value = "id", required = false) Long id, 
			@RequestParam(value = "type", required = false) String type, 
			@RequestBody DialogueData data) throws ServiceException {
		return resolveMessaging(service.resetToBranch(id, type, data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204/{id}/autoFill", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo save(
			@PathVariable("id") long id, 
			@RequestBody IapBranchRscAssignData data) throws ServiceException {
		return resolveMessaging(service.autoFill(id, data.getIapBranchRscAssignVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204/{id}/branchIds/{iapBranchId}/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getRscAssignGrid(
			@PathVariable("id") long iapId,
			@PathVariable("iapBranchId") long iapBranchId) throws ServiceException {
		IapBranchRscAssignVo vo = new IapBranchRscAssignVo();
		vo.setIapBranchId(iapBranchId);
		return resolveMessaging(service.getRscAssignGrid(iapId, vo, null));
	}
	
	@RequestMapping(value = "/forms/form-204/{id}/position", method = { RequestMethod.POST })
	public @ResponseBody DialogueVo saveIapForm204Position(
			@PathVariable("id") long id,
			@RequestBody IapBranchRscAssignData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm204Position(id, data.getIapBranchRscAssignVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204/positions", method = { RequestMethod.POST })
	public @ResponseBody DialogueVo saveIapForm204Positions(
			@RequestBody IapBranchPersonnelData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm204Positions(data.getIapBranchPersonnelVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204/positions/resource-assignment", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm204ResourceAssignPositions(
			@RequestBody IapBranchRscAssignData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm204ResourceAssignPositions(data.getIapBranchRscAssignVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-204/positions/communication", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm204CommunicationPositions(
			@RequestBody IapBranchCommSummaryData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm204CommunicationPositions(data.getIapBranchCommSummaryVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-220/positions/aircraft", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm220AircraftPositions(
			@RequestBody IapAircraftData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm220AircraftPositions(data.getIapAircraftVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-220/positions/task", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm220TaskPositions(
			@RequestBody IapAircraftTaskData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm220TaskPositions(data.getIapAircraftTaskVos(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/forms/form-206/positions/air-ambulance", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm206AirAmbulancePositions(
			@RequestBody IapMedicalAidData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm206AirAmbulancePositions(data.getIapMedicalAidVos(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/forms/form-206/positions/ambulance", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm206AmbulancePositions(
			@RequestBody IapMedicalAidData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm206AmbulancePositions(data.getIapMedicalAidVos(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/forms/form-206/positions/hospital", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm206HospitalPositions(
			@RequestBody IapHospitalData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm206HospitalPositions(data.getIapHospitalVos(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/forms/form-206/positions/alc", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm206AlcPositions(
			@RequestBody IapAreaLocationCapabilityData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm206AlcPositions(data.getIapAreaLocationCapabilityVos(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/forms/form-206/positions/rcl", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapForm206RclPositions(
			@RequestBody IapRemoteCampLocationData data) throws ServiceException {
		return resolveMessaging(service.saveIapForm206RclPositions(data.getIapRemoteCampLocationsVos(), data.getDialogueVo()));
	}
}
