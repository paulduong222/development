package gov.nwcg.isuite.core.controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.IncidentSelector2Data;
import gov.nwcg.isuite.core.controllers.restdata.IncidentSelectorRequestParams;
import gov.nwcg.isuite.core.service.IncidentSelectorService;
import gov.nwcg.isuite.core.vo.IncidentSelector2Vo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;

@Controller
@RequestMapping("/users")
public class IncidentSelectorController extends BaseRestController {

	@Autowired
	private IncidentSelectorService service;

	/**
	 * Supports HTTP query params for incidentId, incidentGroupId, incidentsOnly and filterExcluded if desired.
	 * 
	 * @param userId
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{userId}/incidents/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid(@PathVariable("userId") long userId,
			IncidentSelectorRequestParams params) throws ServiceException {
		
		// Set up any of the additional (optional) filters provided in request params.
		params.initializeService(service);
		DialogueVo dvo =  service.getGrid(userId, null);
		
		/*
		 * Reconstruct the collection of IncidentSelectorVo's.
		 * in a different hierarchical manner
		 */
		if(CollectionUtility.hasValue(dvo.getRecordset())) {
			@SuppressWarnings("unchecked")
			Collection<IncidentSelector2Vo> newVos 
				= IncidentSelector2Vo.reGroup((Collection<IncidentSelector2Vo>)dvo.getRecordset(), new ArrayList<String>());
			dvo.setRecordset(newVos);
		}
		
		return super.resolveMessaging(dvo);
	}

	/**
	 * Supports HTTP query params for incidentId and incidentGroupId if desired.
	 * 
	 * @param userId
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{userId}/incidents/custom-view", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getCustomUserIncDataView(@PathVariable("userId") long userId,
			IncidentSelectorRequestParams params) throws ServiceException {
		
		// Set up any of the additional (optional) filters provided in request params.
		params.initializeService(service);
		DialogueVo dvo =  service.getCustomUserIncDataView(userId, null);
		
		/*
		 * Reconstruct the collection of IncidentSelectorVo's.
		 * in a different hierarchical manner
		 */
		if(CollectionUtility.hasValue(dvo.getRecordset())) {
			@SuppressWarnings("unchecked")
			Collection<IncidentSelector2Vo> newVos 
				= IncidentSelector2Vo.reGroup((Collection<IncidentSelector2Vo>)dvo.getRecordset(), new ArrayList<String>());
			dvo.setRecordset(newVos);
		}
		
		return super.resolveMessaging(dvo);
	}
	
	@RequestMapping(value = "/{userId}/incidents/custom-view", method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo saveCustomView(@PathVariable("userId") long userId,
			@RequestBody IncidentSelector2Data data) throws ServiceException {
		return resolveMessaging(service.saveCustomView(userId, data.getIncidentSelector2Vos(), data.getDialogueVo()));
	}

}
