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

import gov.nwcg.isuite.core.controllers.restdata.AdminOfficeData;
import gov.nwcg.isuite.core.filter.impl.AdminOfficeFilterImpl;
import gov.nwcg.isuite.core.service.AdminOfficeService2;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.IsuiteException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

@Controller
@RequestMapping("/adminoffice")
public class AdminOfficeController2 extends BaseRestController {


	@Autowired
	private AdminOfficeService2 service;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody DialogueVo save(@RequestBody AdminOfficeData data) throws ServiceException, ValidationException {
		DialogueVo dvo = service.save(data.getAdminOfficeVo(), data.getDialogueVo());
		return super.resolveMessaging(dvo);
	}

	/*
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo delete(@PathVariable("id") long id) throws ServiceException {
		AdminOfficeVo vo = new AdminOfficeVo();
		vo.setId(id);

		DialogueVo dvo = service.deleteAdminOffice(vo, null);
		return super.resolveMessaging(dvo);
	}
	*/
	
	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody DialogueVo delete(@RequestBody() AdminOfficeData data) throws ServiceException {
		DialogueVo dvo = service.deleteAdminOffice(data.getAdminOfficeVo(), data.getDialogueVo());
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid() throws ServiceException {
		DialogueVo dvo = service.getGrid(new AdminOfficeFilterImpl(), null);
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getById(@PathVariable("id") Long id) throws ServiceException {
		DialogueVo dvo = service.getById(id, null);
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/list-for-dropdowns", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getListForDropdowns() throws IsuiteException {
		return super.resolveMessaging(service.getDropdownList(null));
	}
}
