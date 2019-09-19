package gov.nwcg.isuite.core.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import gov.nwcg.isuite.core.controllers.restdata.PasswordChangeData;
import gov.nwcg.isuite.core.filter.UserFilter;
import gov.nwcg.isuite.core.filter.impl.UserFilterImpl;
import gov.nwcg.isuite.core.service.UserService2;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/users")
public class UsersController extends BaseRestController {

	@Autowired
	private UserService2 service;

	/*
	 * @RequestMapping(value = "", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public DialogueVo getGrid() { UserFilter filter = new
	 * UserFilterImpl(); DialogueVo dvo = null;
	 * 
	 * try { dvo = service.getGrid(filter, null); return dvo; } catch(Exception e){
	 * return super.handlerException(e); } }
	 */
	/**
	 * Returns list of UserGridVos in DialogueVo.recordset
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DialogueVo findAll() throws ServiceException {
		UserFilter filter = new UserFilterImpl();
		DialogueVo dvo = service.getGrid(filter, null);
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/{id}", 
			method = RequestMethod.GET, 
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DialogueVo getById(@PathVariable("id") Long id) throws ServiceException {
		DialogueVo vo = this.service.getById(id, null);
		return super.resolveMessaging(vo);
	}

	@RequestMapping(value = "/{id}/password", 
			method = RequestMethod.PUT, 
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DialogueVo changePassword(@PathVariable("id") Long id, @RequestBody PasswordChangeData data) throws ServiceException {
		// Fail fast
//		validate(data);

		DialogueVo vo = this.service.changePassword(id, data.getCurPwd(), data.getNewPwd(), data.getConfPwd(), null);
		return super.resolveMessaging(vo);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DialogueVo delete(@PathVariable("id") long id) throws ServiceException {
		DialogueVo dvo = this.service.deleteUser(id, null);
		dvo = super.resolveMessaging(dvo);
		return dvo;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public String update(@PathVariable("id") Long id, @RequestBody UserVo vo) {
		return "update record";
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String create(@RequestBody UserVo vo) {
		return "create record return userVo as json";
	}

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	public byte[] exportItems(@RequestBody String userGridVos) throws ServiceException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Collection<UserGridVo> vos = objectMapper.readValue(userGridVos, new TypeReference<Collection<UserGridVo>>(){});
		byte[] dvo = this.service.exportUserAccounts(vos, null);
		return dvo;
	}

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	//Will use a different method than RequestParams
	public DialogueVo importUsers(@RequestParam Map<String,String> requestParams, @RequestBody int[] xmlByteArray) throws ServiceException{		
		byte[] xmlBytes = new byte[xmlByteArray.length];
		for(int i= 0; i < xmlBytes.length; i++){
			xmlBytes[i] = (byte) xmlByteArray[i];
		}
		String defaultPassword = requestParams.get("defaultPassword");
		String confirmDefaultPassword = requestParams.get("confirmDefaultPassword");
		DialogueVo dvo = this.service.importUserAccounts(xmlBytes, defaultPassword, confirmDefaultPassword, null);
		return super.resolveMessaging(dvo);
	}
/*
	@RequestMapping(value = "/resolveImport", method = RequestMethod.POST)
	@ResponseBody
	public DialogueVo resolveImport(@RequestBody String userGridVos) throws ServiceException, IOException {
//		System.out.println(userGridVos);
		DialogueVo dvo = null;
		try{	
			ObjectMapper objectMapper = new ObjectMapper();
			List<Map<String, Object>> mapList = objectMapper.readValue(userGridVos, new TypeReference<List<Map<String, Object>>>(){});
			Long[] uifIds = new Long[mapList.size()];
			StringBuilder voString = new StringBuilder();
			int index = 0;
			for(Map<String, Object> map: mapList){
				for(Map.Entry<String, Object> entry: map.entrySet()){
					if(entry.getKey().equals("id")){
						uifIds[index] = Long.parseLong(entry.getValue().toString());
						index++;
					}						
					String entryString = new String(entry.getKey().toString() + "=" + 
							(entry.getValue() != null ? entry.getValue().toString() : "null") + 
							",");
					voString.append(entryString);
				};
				voString.deleteCharAt(voString.length()-1);
				voString.append("|");
			}
			voString.deleteCharAt(voString.length()-1);
			Collection<UserVo> voCollection = UserVo.buildList(voString.toString());
//			System.out.println("VOS2:\n"+voCollection.toString());
//			System.out.println(Arrays.toString(uifIds));
			dvo = this.service.resolveImportFailure(uifIds, voCollection, null);
		}catch (Exception e){
			e.printStackTrace();
		}
		return super.resolveMessaging(dvo);
//		return dvo;
	}
	*/
	
	@RequestMapping(value = "/resolveImport", method = RequestMethod.POST)
	@ResponseBody
	public DialogueVo resolveImport(@RequestBody String jsonVos) throws ServiceException, IOException {
		DialogueVo dvo = null;		
		ObjectMapper objectMapper = new ObjectMapper();
		TypeFactory typeFactory = objectMapper.getTypeFactory();
		try{
			Collection<UserVo> vos = objectMapper.readValue(jsonVos, typeFactory.constructCollectionType(Collection.class, UserVo.class));
			dvo = this.service.resolveImportFailure(vos, null);
		}catch (Exception e){
			e.printStackTrace();
		}
		return super.resolveMessaging(dvo);
	}

	@RequestMapping(value = "/{userId}/password/status", method = RequestMethod.GET)
	@ResponseBody
	public String checkPassword(@PathVariable("userId") Long userId) {
		// TODO: Leaving this try block since it's not returning a DialogueVo.  
		//       Seems it probably should to conform and to leverage IsuiteControllerAdvice.
		try {
			String result = this.service.checkPasswordStatus(userId);
			return result;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/{userId}/robupdate", method = RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DialogueVo updateRob(@PathVariable("userId") Long userId, @RequestParam("robType") String robType) throws ServiceException {
		DialogueVo dvo = this.service.updateROBAgreementDate(userId, robType);
		super.resolveMessaging(dvo);
		return dvo;
	}
	
	@RequestMapping(value = "/{userId}/save", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DialogueVo save(@PathVariable("userId") Long userId, @RequestBody UserVo vo) throws ServiceException {
		DialogueVo dvo = this.service.save(vo, vo.getDbname(), null);
		dvo = super.resolveMessaging(dvo);
		return dvo;
	}
	
	@RequestMapping(value = "/createNewAccountManager", method =  RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DialogueVo createNewAccountManager(@RequestBody UserVo vo) throws ServiceException {
		DialogueVo dvo = this.service.createNewAccountManager(vo, null);
		dvo = super.resolveMessaging(dvo);
		return dvo;
	}
	
}
