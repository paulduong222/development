package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.service.SystemService;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.Dvo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

@Controller
@RequestMapping("/system")
public class SystemController extends BaseRestController {

	@Autowired
	private SystemService service;
	
	@RequestMapping(value = "/runmode", method = RequestMethod.GET)
	@ResponseBody
	public String getRunMode() {
		try {
			String s = service.getRunMode();
			return s;
		} catch(Exception e){
			return e.getMessage();
		}
	}
	
	@RequestMapping(value = "/dbs", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getDatabaseList() throws ServiceException {
		DialogueVo vo = service.getSiteDatabaseList(null);
		vo.setServiceCallEventType("test");
		return vo;
	}
	
	@RequestMapping(value = "/dbs2", method = RequestMethod.GET)
	@ResponseBody
	public Dvo getDatabaseList2() {
		try {
			Dvo dvo = new Dvo();
			DialogueVo vo = service.getSiteDatabaseList(null);
			//dvo.resultObject = vo.getResultObject();
			dvo.resultObject = "testing";
			return dvo;
		} catch(Exception e){
			return null;
			//return super.handlerException(e);
		}
	}
	
	@RequestMapping(value = "/dbs/site/connect", method = RequestMethod.POST)
	@ResponseBody
	public DialogueVo connectToSiteDatabase(@RequestBody DbAvailVo vo) throws ServiceException {
		DialogueVo dvo = service.connectToSiteDatabase(vo,null);
		return dvo;
	}
	
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	@ResponseBody
	public DialogueVo getSystemRoles() throws ServiceException {
		DialogueVo dvo = new DialogueVo();
		dvo.setCourseOfActionVo(super.getCourseOfActionVo(CourseOfActionTypeEnum.HANDLE_RECORDSET, "GET_SYSTEM_ROLES"));
		dvo.setRecordset(super.getGlobalCacheVo().getSystemRoleVos());
		return dvo;
	}
	
	@RequestMapping(value = "/lastRecoverCode/{dbName}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DialogueVo getLastRecoverCode(@PathVariable("dbName") String dbName) throws ServiceException {
		DialogueVo dvo = service.getLastRecoverCode(dbName, null);
		return super.resolveMessaging(dvo);
	}
	
	@RequestMapping(value = "/authenticateRecoverCode/{dbName},{authCode}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DialogueVo authenticateRecoverCode(@PathVariable("dbName") String dbName, @PathVariable("authCode") String authCode) throws ServiceException {
		DialogueVo dvo = service.authenticateRecoverCode(dbName, authCode, null);
		return super.resolveMessaging(dvo);
	}
}

class DbAvail2 {
	public String databaseName;
	public String datasource;
}