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

import gov.nwcg.isuite.core.controllers.restdata.GroupCategoryData;
import gov.nwcg.isuite.core.service.GroupCategoryService;
import gov.nwcg.isuite.core.vo.GroupCategoryVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

@Controller
@RequestMapping("/group-categories")
public class GroupCategoryController extends BaseRestController {

	private static final Logger LOG = LoggerFactory.getLogger(GroupCategoryController.class);

	@Autowired
	private GroupCategoryService service;


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
	public @ResponseBody DialogueVo save(@RequestBody GroupCategoryData data)
			throws ServiceException, ValidationException {
		DialogueVo dvo =  service.save(data.getGroupCategoryVo(), data.getDialogueVo());
		return super.resolveMessaging(dvo);
	}

	/**
	 * usage: DELETE ~/isuite/service/group-categories/{id}
	 *  
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo delete(@PathVariable("id") long id)
			throws ServiceException {
		LOG.debug("Deleting GroupCategory id: " + id);
		GroupCategoryVo vo = new GroupCategoryVo();
		vo.setId(id);

		DialogueVo dvo =  service.deleteGroupCategory(vo, null);
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid() throws ServiceException {
		DialogueVo dvo =  service.getGrid(null);
		return super.resolveMessaging(dvo);
	}

}
