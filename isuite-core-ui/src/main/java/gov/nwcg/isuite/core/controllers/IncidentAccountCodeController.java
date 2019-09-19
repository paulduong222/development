package gov.nwcg.isuite.core.controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.IncidentAccountCodeData;
import gov.nwcg.isuite.core.filter.impl.IncidentAccountCodeFilterImpl;
import gov.nwcg.isuite.core.service.IncidentAccountCodeService;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;


// currently not in use , refer to incidentcontroller
@Controller
@RequestMapping("/incident-account-codes")
public class IncidentAccountCodeController extends BaseRestController {

	@Autowired
	private IncidentAccountCodeService service;
	
	/* --------- Implementation Comments ------------ */
	// Skipping getAll() -- Deprecated.
	// Skipping voToDo() -- Doesn't seem an appropriate method to expose and is commented out.
	// TODO: Do we need to implement getAccountFireCodeById?  Seems it can be handled by getIacVoById.  Skipped for now.
	// add/updateIncidentAccountCode not implemented.  Using save
	// TODO: shouldWarnUserAboutExistingRelationships only ever returns false.  Implement?
	// getIncidentsInvolvedInPotentialMassUpdate just returns null.  ignoring.
	

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable("id") long id) throws ServiceException {

		service.delete(id);
	}
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public @ResponseBody void save(
			@RequestBody IncidentAccountCodeData data) throws ServiceException, ValidationException {
		service.save(data.getIncidentAccountCodeVo());
	}
	
	
	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentAccountCodeVo> getGrid(
			IncidentAccountCodeFilterImpl queryParams) throws ServiceException {
		return service.getGrid(queryParams);
	}

	
	@RequestMapping(value = "/lookup-by", method = RequestMethod.GET)
	public @ResponseBody Collection<IncidentAccountCodeVo> getExistingAccountCodeIncidentAssociations(
			@RequestParam(value = "accountCodeId", required = false) Long accountCodeId,
			@RequestParam(value = "accountFireCode", required = false) String accountFireCode) throws ServiceException {
		if (accountCodeId != null) return service.getExistingAccountCodeIncidentAssociations(accountCodeId);
		if (StringUtils.hasText(accountFireCode)) return service.getExistingAccountCodeIncidentAssociations(accountFireCode);
		
		return new ArrayList<IncidentAccountCodeVo>();
	}
	
	@RequestMapping(value = "/event-type-id/{incidentId}", method = RequestMethod.GET)
	public @ResponseBody Long getById(@PathVariable("incidentId") Long incidentId) throws ServiceException {
		return service.getEventTypeIdByIncidentId(incidentId);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody IncidentAccountCodeVo getIacVoById(@PathVariable("id") Long id) throws ServiceException {
		return service.getIacVoById(id);
	}
}
