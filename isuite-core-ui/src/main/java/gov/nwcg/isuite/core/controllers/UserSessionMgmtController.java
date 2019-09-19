package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.emory.mathcs.backport.java.util.Collections;
import gov.nwcg.isuite.core.service.UserSessionManagementService2;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/sessions")
public class UserSessionMgmtController extends BaseRestController {

	@Autowired
	private UserSessionManagementService2 service;

	@RequestMapping(value = "/dbs/{dbName}/users/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getData(@PathVariable("dbName") String databaseName, @PathVariable("userId") Long userId)
			throws ServiceException {
		DialogueVo vo = service.getAllUserSessions(userId, databaseName, null);
		return super.resolveMessaging(vo);
	}

	@RequestMapping(value = "/dbs/{dbName}/users/{userId}/disconnect", method = RequestMethod.POST)
	@ResponseBody
	public DialogueVo disconnectUser(@PathVariable("dbName") String dbName, @PathVariable("userId") Integer userId) throws ServiceException {
		@SuppressWarnings("unchecked")
		DialogueVo vo = service.disconnectUserSessions(Collections.singletonList(userId), dbName, null);
		return super.resolveMessaging(vo);
	}

}
