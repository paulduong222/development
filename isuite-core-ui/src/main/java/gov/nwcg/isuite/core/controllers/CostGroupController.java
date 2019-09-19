package gov.nwcg.isuite.core.controllers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.CostGroupAgencyDayShareData;
import gov.nwcg.isuite.core.controllers.restdata.CostGroupAgencyDaySharePercentageData;
import gov.nwcg.isuite.core.controllers.restdata.CostGroupData;
import gov.nwcg.isuite.core.controllers.restdata.CostGroupResourcesData;
import gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter;
import gov.nwcg.isuite.core.filter.CostGroupFilter;
import gov.nwcg.isuite.core.filter.impl.CostGroupAgencyDayShareFilterImpl;
import gov.nwcg.isuite.core.filter.impl.CostGroupFilterImpl;
import gov.nwcg.isuite.core.service.CostGroupService;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

@Controller
@RequestMapping("/cost-groups")
public class CostGroupController extends BaseRestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CostGroupController.class);

	@Autowired
	private CostGroupService service;
	
	// @formatter:off
	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid(
			@RequestParam(value="incidentId", required=false) Long incidentId,
			@RequestParam(value="incidentGroupId", required=false) Long incidentGroupId,
			@RequestParam(value="incidentName", required=false) String incidentName,
			@RequestParam(value="costGroupName", required=false) String costGroupName,
			@RequestParam(value="startDate", required=false) Date startDate,
			@RequestParam(value="crypticDateFilterCodeForStartDate", required=false) String crypticDateFilterCodeForStartDate,
			@RequestParam(value="description", required=false) String description,
			@RequestParam(value="shift", required=false) String shift
			) throws ServiceException {
		
		CostGroupFilter filter = buildCostGroupFilter(incidentId, incidentGroupId, incidentName, costGroupName,
				startDate, crypticDateFilterCodeForStartDate, description, shift);
		
		return resolveMessaging(service.getGrid(filter, null));
	} 
	// @formatter:on
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getById(@PathVariable("id") Long id) throws ServiceException {
		LOG.debug("Looking up  for id: " + id);
		return resolveMessaging(service.getById(id, null));
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
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo save(@RequestBody CostGroupData data) throws ServiceException {
		return resolveMessaging(service.save(data.getDialogueVo(), data.getCostGroupVo()));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo delete(@PathVariable("id") long id) throws ServiceException {
		LOG.debug("Deleting id: " + id);

		return resolveMessaging(service.deleteCostGroup(id, null));
	}
	
	// @formatter:off
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody DialogueVo getCostGroups(
			@RequestParam(value="incidentId", required=false) Long incidentId,
			@RequestParam(value="incidentGroupId", required=false) Long incidentGroupId,
			@RequestParam(value="incidentName", required=false) String incidentName,
			@RequestParam(value="costGroupName", required=false) String costGroupName,
			@RequestParam(value="startDate", required=false) Date startDate,
			@RequestParam(value="crypticDateFilterCodeForStartDate", required=false) String crypticDateFilterCodeForStartDate,
			@RequestParam(value="description", required=false) String description,
			@RequestParam(value="shift", required=false) String shift
			) throws ServiceException {
		
		CostGroupFilter filter = buildCostGroupFilter(incidentId, incidentGroupId, incidentName, costGroupName,
				startDate, crypticDateFilterCodeForStartDate, description, shift);
		
		return resolveMessaging(service.getCostGroups(null, filter));
	} 
	// @formatter:on


	@RequestMapping(value = "/resources/{id}/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getCostGroupResourceGrids(@PathVariable("id") Long id) throws ServiceException {
		LOG.debug("Looking up CostGroupResources for id: " + id);
		return resolveMessaging(service.getCostGroupResourceGrids(null, id));
	}
	
	@RequestMapping(value="/resources/{id}", method = RequestMethod.PUT )
	public @ResponseBody DialogueVo save(@PathVariable("id") Long id,
			@RequestBody CostGroupResourcesData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.saveCostGroupResources(id, data.getResources(), data.getOtherResources(), data.getDialogueVo()));
	}
	
	/* AGENCY DAY SHARES */
	
	/**
	 * The "upsert" REST endpoint to create or modify an Agency Group.
	 * 
	 * Suggested usage is to use a POST when creating and a PUT when updating.
	 * 
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/agency-day-shares", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveAgencyDayShare(@RequestBody CostGroupAgencyDayShareData data) throws ServiceException, ValidationException {
		return resolveMessaging(service.saveAgencyDayShare(data.getCostGroupAgencyDayShareVo(), data.getDialogueVo()));
	}
	
	@RequestMapping(value = "/agency-day-shares/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getAgencyDayShareById(@PathVariable("id") Long id) throws ServiceException {
		return resolveMessaging(service.getAgencyDayShareById(id, null));
	}
	
	@RequestMapping(value = "/agency-day-shares/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteCostGroupAgencyDayShare(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.deleteCostGroupAgencyDayShare(null, id));
	}
	
	@RequestMapping(value = "/agency-day-shares/{costGroupId}/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getAgencyDayShareGrid(
			@PathVariable("costGroupId") Long costGroupId) throws ServiceException {
		return resolveMessaging(service.getAgencyDayShareGrid(null, costGroupId));
	}
	
	/* AGENCY DAY SHARE PERCENTAGES */
	
	@RequestMapping(value = "/agency-day-shares/percentages/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getAgencyDaySharePercentageById(@PathVariable("id") Long id) throws ServiceException {
		return resolveMessaging(service.getAgencyDaySharePercentageById(id, null));
	}
	
	@RequestMapping(value = "/agency-day-shares/percentages/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo deleteCostGroupAgencyDaySharePct(@PathVariable("id") long id) throws ServiceException {
		return resolveMessaging(service.deleteCostGroupAgencyDaySharePct(null, id));
	}
	
	@RequestMapping(value = "/agency-day-shares/percentages/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getAgencyDaySharePercentageGrid(
			@RequestParam(value = "costGroupId", required = false) Long costGroupId,
			@RequestParam(value = "agency", required = false) String agency,
			@RequestParam(value = "agencyShareDate", required = false) Date agencyShareDate,
			@RequestParam(value = "crypticDateFilterCodeForAgencyShareDate", required = false) String crypticDateFilterCodeForAgencyShareDate,
			@RequestParam(value = "percentage", required = false) String percentage
			) throws ServiceException {
		
		CostGroupAgencyDayShareFilter filter = buildCostGroupAgencyDayShareFilter(costGroupId, agency, agencyShareDate,
				crypticDateFilterCodeForAgencyShareDate, percentage);
		
		return resolveMessaging(service.getAgencyDaySharePercentageGrid(null, filter));
	}

	/**
	 * The "upsert" REST endpoint
	 * 
	 * Suggested usage is to use a POST when creating and a PUT when updating.
	 * 
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/agency-day-shares/percentages", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveAgencyDaySharePercentage(@RequestBody CostGroupAgencyDaySharePercentageData data) throws ServiceException {
		return resolveMessaging(service.saveAgencyDaySharePercentage(data.getCostGroupAgencyDaySharePercentageVo(), data.getDialogueVo()));
	}
	

	// @formatter:off
	private CostGroupAgencyDayShareFilter buildCostGroupAgencyDayShareFilter(Long costGroupId, String agency, Date agencyShareDate,
			String crypticDateFilterCodeForAgencyShareDate, String percentage) {
		CostGroupAgencyDayShareFilter filter = new CostGroupAgencyDayShareFilterImpl();
		if (costGroupId != null) filter.setCostGroupId(costGroupId);
		if (StringUtils.hasText(agency)) filter.setAgency(agency);
		if (agencyShareDate != null) filter.setAgencyShareDate(agencyShareDate);
		if (StringUtils.hasText(crypticDateFilterCodeForAgencyShareDate)) filter.setCrypticDateFilterCodeForAgencyShareDate(crypticDateFilterCodeForAgencyShareDate);
		if (StringUtils.hasText(percentage)) filter.setPercentage(percentage);
		
		return filter;
	}
	
	private CostGroupFilter buildCostGroupFilter(Long incidentId, Long incidentGroupId, String incidentName,
			String costGroupName, Date startDate, String crypticDateFilterCodeForStartDate, String description,
			String shift) {
		CostGroupFilter filter = new CostGroupFilterImpl();
		if (incidentId != null) filter.setIncidentId(incidentId);
		if (incidentGroupId != null) filter.setIncidentGroupId(incidentGroupId);
		if (StringUtils.hasText(incidentName)) filter.setIncidentName(incidentName);
		if (StringUtils.hasText(costGroupName)) filter.setCostGroupName(costGroupName);
		if (startDate != null) filter.setStartDate(startDate);
		if (StringUtils.hasText(crypticDateFilterCodeForStartDate)) filter.setCrypticDateFilterCodeForStartDate(crypticDateFilterCodeForStartDate);
		if (StringUtils.hasText(description)) filter.setDescription(description);
		if (StringUtils.hasText(shift)) filter.setShift(shift);
		return filter;
	}
	// @formatter:on

}
