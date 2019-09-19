package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.AuthenticationData;
import gov.nwcg.isuite.core.service.AuthenticationService;
import gov.nwcg.isuite.core.service.SystemService;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.AuthenticationException;
import gov.nwcg.isuite.framework.exceptions.IsuiteException;

@Controller
@RequestMapping("/auth")
public class AuthenticationController extends BaseRestController {

	@Autowired
	private AuthenticationService service;

	@Autowired
	private SystemService systemService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public DialogueVo login(@RequestBody AuthenticationData authData) throws IsuiteException {
		// Fail fast
		if (StringUtils.isEmpty(authData.getUsername()) || StringUtils.isEmpty(authData.getPassword())) {
			throw new AuthenticationException();
		}

		DialogueVo vo = service.login2(authData.getUsername(), authData.getPassword(), null);
		vo.setResultObjectAlternate(systemService.getStaticDataWrapper().getServerDate());
		vo.setResultObjectAlternate2(systemService.getStaticDataWrapper().getServerVersion());
		return super.resolveMessaging(vo);
	}

	@RequestMapping(value = "/{userId}/logout", method = RequestMethod.POST)
	@ResponseBody
	public DialogueVo logout(@PathVariable("userId") Integer userId) throws IsuiteException {
		DialogueVo vo = service.endSession(null, userId, false, null);
		return super.resolveMessaging(vo);
	}
}
