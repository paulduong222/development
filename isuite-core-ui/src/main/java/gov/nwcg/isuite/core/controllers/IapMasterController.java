package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.BranchPositionData;
import gov.nwcg.isuite.core.controllers.restdata.BranchSettingData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentGroupData;
import gov.nwcg.isuite.core.controllers.restdata.IncidentShiftData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapMasterFrequencyData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapMasterFrequencyFilterData;
import gov.nwcg.isuite.core.controllers.restdata.iap.IapPositionData;
import gov.nwcg.isuite.core.filter.impl.IapMasterFrequencyFilterImpl;
import gov.nwcg.isuite.core.service.IapService;
import gov.nwcg.isuite.core.vo.BranchPositionVo;
import gov.nwcg.isuite.core.vo.BranchSettingVo;
import gov.nwcg.isuite.core.vo.IapMasterFrequencyVo;
import gov.nwcg.isuite.core.vo.IapPositionVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.IncidentShiftVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

@Controller
@RequestMapping("/iaps")
public class IapMasterController extends BaseRestController {

	@Autowired
	private IapService service;

	@RequestMapping(value = "/master-frequencies", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapMasterFrequency(@RequestBody IapMasterFrequencyData data)
			throws ServiceException, ValidationException {
		return resolveMessaging(service.saveIapMasterFrequency(data.getDialogueVo(), data.getIapMasterFrequencyVo()));
	}

	@RequestMapping(value = "/master-frequencies/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteIapMasterFrequency(@PathVariable("id") long id,
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		IapMasterFrequencyVo vo = new IapMasterFrequencyVo();
		vo.setId(id);
		if (null != incidentId) {
			IncidentVo incidentVo = new IncidentVo();
			incidentVo.setId(incidentId);
			vo.setIncidentVo(incidentVo);
		}
		if (null != incidentGroupId) {
			IncidentGroupVo incidentGroupVo = new IncidentGroupVo();
			incidentGroupVo.setId(incidentGroupId);
			vo.setIncidentGroupVo(incidentGroupVo);
		}

		return resolveMessaging(service.deleteIapMasterFrequency(null, vo));
	}

	@RequestMapping(value = "/master-frequencies/propagate", method = { RequestMethod.PUT })
	public @ResponseBody DialogueVo propagateChanges(@RequestBody IapMasterFrequencyData data)
			throws ServiceException, ValidationException {
		return resolveMessaging(service.propagateChanges(data.getDialogueVo(), data.getIapMasterFrequencyVos()));
	}

	@RequestMapping(value = "/master-frequencies/positions/reorder", method = { RequestMethod.PUT })
	public @ResponseBody DialogueVo reorderMasterFrequencyPositions(@RequestBody IapMasterFrequencyData data)
			throws ServiceException, ValidationException {
		return resolveMessaging(
				service.reorderMasterFrequencyPositions(data.getDialogueVo(), data.getIapMasterFrequencyVos()));
	}

	@RequestMapping(value = "/master-frequencies/export", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo exportMasterFrequency(@RequestBody IapMasterFrequencyFilterData data)
			throws ServiceException, ValidationException {
		return resolveMessaging(
				service.exportMasterFrequency(data.getDialogueVo(), data.getIapMasterFrequencyFilter()));
	}

	// TODO: Figure out the best way to handle this xmlByteArray
	@RequestMapping(value = "/master-frequencies/import", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo importMasterFrequency(@RequestBody IapMasterFrequencyFilterData data)
			throws ServiceException, ValidationException {
		return resolveMessaging(service.importMasterFrequency(data.getDialogueVo(), data.getIapMasterFrequencyFilter(),
				data.getXmlByteArray()));
	}

	/**
	 * Filter options can be provided as Query params.
	 * 
	 * Example url:  http://<server>/isuite/service/iaps/master-frequencies/grid?incidentId=10001&incidentGroupId=10001&rxTone=226.40hz
	 * 
	 * @param parameters
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/master-frequencies/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getMasterFrequencyGrid(IapMasterFrequencyFilterImpl parameters) throws ServiceException {
		return resolveMessaging(service.getMasterFrequencyGrid(null, parameters));
	}

	@RequestMapping(value = "/operational-periods", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveOperationalPeriod(@RequestBody IncidentShiftData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.saveOperationalPeriod(data.getDialogueVo(), data.getIncidentShiftVo()));
	}
	
	@RequestMapping(value = "/operational-periods/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteOperationalPeriod(@PathVariable("id") long id) throws ServiceException {
		IncidentShiftVo vo = new IncidentShiftVo();
		vo.setId(id);
		return resolveMessaging(service.deleteOperationalPeriod(null, vo));
	}
	
	@RequestMapping(value = "/incident/settings", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapSettings(@RequestBody IncidentData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.saveIapSettings(data.getDialogueVo(), data.getIncidentVo()));
	}
	
	@RequestMapping(value = "/incident-group/settings", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveIapSettingsIG(@RequestBody IncidentGroupData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.saveIapSettingsIG(data.getDialogueVo(), data.getIncidentGroupVo()));
	}
	
	@RequestMapping(value = "/incidents/{id}/positions", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getPositions(@PathVariable("id") long incidentId) throws ServiceException {
		return resolveMessaging(service.getPositions(null, incidentId));
	}
	
	/**
	 * The "upsert" REST endpoint to create or modify an Agency Group.
	 * 
	 * Suggested usage is to use a POST when creating and a PUT when updating.
	 * 
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/incidents/positions", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo savePosition(@RequestBody IapPositionData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.savePosition(data.getDialogueVo(), data.getIapPositionVo()));
	}
	
	@RequestMapping(value = "/incidents/positions", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deletePosition(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId,
			@RequestParam(value = "position", required = false) String position
			) throws ServiceException {
		IapPositionVo vo = new IapPositionVo();
		if (id != null) vo.setId(id);
		if (StringUtils.hasText(position)) vo.setPosition(position);
		if (incidentId != null) {
			IncidentVo incidentVo = new IncidentVo();
			incidentVo.setId(incidentId);
			vo.setIncidentVo(incidentVo);
		}if (incidentGroupId != null) {
			IncidentGroupVo incidentGroupVo = new IncidentGroupVo();
			incidentGroupVo.setId(incidentGroupId);
			vo.setIncidentGroupVo(incidentGroupVo);
		}

		return resolveMessaging(service.deletePosition(null, vo));
	}
	
	@RequestMapping(value = "/branches/settings", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getBranchSettings(
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId) throws ServiceException {
		return resolveMessaging(service.getBranchSettings(incidentId, incidentGroupId, null));
	}
	
	@RequestMapping(value = "/branches/settings", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveBranchSetting(@RequestBody BranchSettingData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.saveBranchSetting(data.getBranchSettingVo(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/branches/settings", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteBranchSetting(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "incidentId", required = false) Long incidentId,
			@RequestParam(value = "incidentGroupId", required = false) Long incidentGroupId
			) throws ServiceException {
		BranchSettingVo vo = new BranchSettingVo();
		vo.setId(id);
		vo.setIncidentId(incidentId);
		vo.setIncidentGroupId(incidentGroupId);

		return resolveMessaging(service.deleteBranchSetting(vo, null));
	}
	
	@RequestMapping(value = "/branches/positions", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveBranchPosition(
			@RequestParam(value = "branchSettingId", required = false) Long branchSettingId,
			@RequestBody BranchPositionData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.saveBranchPosition(branchSettingId, data.getBranchPositionVo(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/branches/positions", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteBranchPosition(
			@RequestParam(value = "branchPositionId", required = false) Long branchPositionId,
			@RequestParam(value = "position", required = false) String position
			) throws ServiceException {
		BranchPositionVo vo = new BranchPositionVo();
		vo.setPosition(position);

		return resolveMessaging(service.deleteBranchPosition(branchPositionId, vo, null));
	}
}
