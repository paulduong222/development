package gov.nwcg.isuite.core.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.DbBackupData;
import gov.nwcg.isuite.core.controllers.restdata.DbCopyData;
import gov.nwcg.isuite.core.controllers.restdata.DbRestoreData;
import gov.nwcg.isuite.core.service.DatabaseMgmtService;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

@Controller
@RequestMapping("/mgmt/dbs")
public class DatabaseMgmtController extends BaseRestController {
	
	
	@Autowired
	private DatabaseMgmtService svc;

	@RequestMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DialogueVo getGrid() throws ServiceException {
		return svc.getGrid(null);
	}
	
	@RequestMapping(method= {RequestMethod.POST,RequestMethod.PUT}, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DialogueVo saveDb(@RequestBody DbAvailVo vo) throws ServiceException, IOException {
		DialogueVo dvo = svc.saveDb(vo, null);
		return super.resolveMessaging(dvo);
		//return svc.saveDb(vo, null);
	}

	/**
	 * Copy the database given by the provided path id.
	 * 
	 * @param dbIdToCopy
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/{id}/copy", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DialogueVo copyDb(@PathVariable("id") Long dbIdToCopy, @RequestBody DbCopyData data) throws ServiceException {
		// Assert.notNull(data);
		// Assert.hasText(data.getSourceDatabaseName(), "Source database name required!");
		// Assert.hasText(data.getTargetDatabaseName(), "Target database name requried!");

		/*
		DbAvailVo source = new DbAvailVo();
		source.setId(dbIdToCopy);
		source.setDatabaseName(data.getSourceDatabaseName());
		
		DbAvailVo target = new DbAvailVo();
		target.setDatabaseName(data.getTargetDatabaseName());
		*/
		
		DialogueVo dvo = svc.copyDb(data.getSourceVo(), data.getTargetVo(), null);
		return super.resolveMessaging(dvo);
	}
	
	/**
	 * 
	 * @param dbIdToBackup
	 * @param data
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/{id}/backup", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DialogueVo backupDb(@PathVariable("id") Long dbIdToBackup, @RequestBody DbBackupData data) throws ServiceException {
		Assert.notNull(data);
		Assert.notNull(data.getVo());
		if (data.getVo().getId() == null) data.getVo().setId(dbIdToBackup);
		DialogueVo dvo = svc.backupDb(data.getVo(), data.getDestFileName(), data.getAltDestination(), null);
		return super.resolveMessaging(dvo);
	}

	/**
	 * @param filename
	 * @param targetDbName
	 * @param pwd
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/restore", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DialogueVo restoreDb(@RequestBody DbRestoreData data) throws ServiceException {
		DialogueVo dvo = svc.restoreDb(data.getFilename(), data.getTargetDbName(), data.getPwd(), ( null != data.getDialogueVo() ? data.getDialogueVo() : null ));
		return super.resolveMessaging(dvo);
	}

	/**
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DialogueVo removeDb(@PathVariable("id") long id, @RequestBody DbAvailVo vo) throws ServiceException {
//		DbAvailVo vo = new DbAvailVo();
//		vo.setId(id);
		
		DialogueVo dvo = svc.removeDb(vo, null);
		return super.resolveMessaging(dvo);
	}

	/**
	 * example endpoint url: <server>/isuite/service/mgmt/dbs/recover?filename=bobsFile.bak
	 * 
	 * @param filename
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value="/recover", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DialogueVo generateRecoverCode(@RequestParam(value="filename", required=true) String filename) throws ServiceException {
		DialogueVo dvo = svc.generateRecoverCode(filename, null);
		return super.resolveMessaging(dvo);
	}

	/**
	 * 
	 * @return the default backup directory.
	 * @throws ServiceException
	 */
	@RequestMapping(value="/backup-dir", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DialogueVo getDefaultBackupDir() throws ServiceException {
		return svc.getDefaultBackupDir(null);
	}
}
