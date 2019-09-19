package gov.nwcg.isuite.core.controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

import gov.nwcg.isuite.core.filter.KindGroupFilter;
import gov.nwcg.isuite.core.filter.impl.KindGroupFilterImpl;
import gov.nwcg.isuite.core.service.KindGroupService;
import gov.nwcg.isuite.core.vo.KindGroupVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

@Controller
@RequestMapping("/kind-groups")
public class KindGroupController extends BaseRestController {

	private static final Logger LOG = LoggerFactory.getLogger(KindGroupController.class);

	@Autowired private KindGroupService service;

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
	public @ResponseBody KindGroupVo save(@RequestBody KindGroupVo data) throws ServiceException, ValidationException {
		return service.save(data);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody KindGroupVo getById(@PathVariable("id") Long id) throws ServiceException {
		LOG.debug("Looking up KindGroup for id: " + id);
		return service.getById(id);
	}
	
	/**
	 * Delete multiple KindGroups by their ids.
	 * 
	 * usage: DELETE ~/isuite/service/kinds/groups?id=1&id=2&id=3 
	 * 
	 * @param ids
	 * @throws ServiceException
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody void deleteIds(@RequestParam("id") List<Long> ids) throws ServiceException {
		LOG.debug("Deleting KindGroup ids: " + ids.toString());

		service.delete(ids);
	}

	/**
	 * Delete a specific KindGroup by its id.
	 * 
	 * usage: DELETE ~/isuite/service/kinds/groups/{id} 
	 * 
	 * @param ids
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable("id") Long id) throws ServiceException {
		deleteIds(Collections.singletonList(id));
	}
	
	/**
	 * Find the KindGroups by provided parameters.
	 * 
	 * usage: GET ~/isuite/service/kinds/groups/grid?code=someCode&standard=true
	 * 
	 * @param code
	 * @param description
	 * @param standard
	 * @param deletable
	 * @return
	 * @throws ServiceException
	 */
	// @formatter:off
	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody Collection<KindGroupVo> getGrid(
			@RequestParam("code") String code, 
			@RequestParam("description") String description,
			@RequestParam("standard") Boolean standard,
			@RequestParam("deletable") Boolean deletable
			) throws ServiceException {
		
		KindGroupFilter filter = new KindGroupFilterImpl();
		if (StringUtils.hasText(code)) filter.setCode(code.trim());
		if (StringUtils.hasText(description)) filter.setDescription(description.trim());
		if (standard != null) filter.setStandard(standard);
		if (deletable != null) filter.setDeletable(deletable);
		
		return service.getGrid(filter);
	}
	// @formatter:on

	
}
