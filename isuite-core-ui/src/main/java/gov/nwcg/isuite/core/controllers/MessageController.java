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

import gov.nwcg.isuite.core.controllers.restdata.MessageBoardData;
import gov.nwcg.isuite.core.service.MessageService;
import gov.nwcg.isuite.core.vo.MessageBoardVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/messages")
public class MessageController extends BaseRestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private MessageService service;
	
	@RequestMapping(value="/grid", method=RequestMethod.GET)
	public @ResponseBody DialogueVo getPopUpGrid() throws ServiceException {
		DialogueVo dvo =  service.getPopUpGrid(null);
		return super.resolveMessaging(dvo);
	}
	
	@RequestMapping(method={RequestMethod.POST,RequestMethod.PUT})
	public @ResponseBody DialogueVo save(@RequestBody MessageBoardVo vo) throws ServiceException {
		DialogueVo dvo =  service.save(vo, null);
		return super.resolveMessaging(dvo);
	}
	
	@RequestMapping(value="/static", method=RequestMethod.GET)
	public @ResponseBody DialogueVo getStaticMessage() throws ServiceException {
		DialogueVo dvo =  service.getStaticMessage(null);
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value="/popups", method={RequestMethod.POST,RequestMethod.PUT})
	public @ResponseBody DialogueVo savePopUp(@RequestBody MessageBoardData data) throws ServiceException {
		DialogueVo dvo =  service.savePopUp(data.getMessageBoardVo(), data.getDialogueVo());
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value="/popups", method=RequestMethod.GET)
	public @ResponseBody DialogueVo getPopUpMessages() throws ServiceException {
		DialogueVo dvo =  service.getPopUpMessages(null);
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value="/popups/{id}", method=RequestMethod.DELETE)
	public @ResponseBody DialogueVo deletePopUpMessage(@PathVariable("id") Long id, @RequestBody MessageBoardData data) throws ServiceException {
		LOG.debug("Deleting popup message for id: " + id);
		DialogueVo dvo =  service.deletePopUpMessage(data.getMessageBoardVo(), data.getDialogueVo());
		return super.resolveMessaging(dvo);
	}
}
