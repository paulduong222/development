package gov.nwcg.isuite.core.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.IsuiteException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ErrorEnum;

/**
 * Controller for testing Controller Advice configurations.  
 * 
 * @see IsuiteControllerAdvice 
 * 
 * @author ben
 */
@Controller
@RequestMapping("/exceptions")
public class ExceptionController {


	@RequestMapping(value = "/exception", method = RequestMethod.GET)
	ResponseEntity<DialogueVo> exception() throws Exception {
		throw new Exception("Generic Exception thrown.");	
	}
	
	@RequestMapping(value = "/illegalArgument", method = RequestMethod.GET)
	void illegalArgumentException() {
		Assert.notNull(null, "Threw an IllegalArgumentException!");
	}
	
	@RequestMapping(value = "/isuite", method = RequestMethod.GET)
	void isuiteException() throws Throwable {
		throw new IsuiteException("ISuite Exception thrown!");
	}

	@RequestMapping(value = "/runtime", method = RequestMethod.GET)
	ResponseEntity<DialogueVo> runtimeException() {
		throw new RuntimeException("RuntimeException thrown WITH wrapped ResponseEntity expected.");	
	}

	@RequestMapping(value = "/service", method = RequestMethod.GET)
	DialogueVo serviceException() throws ServiceException {
		throw new ServiceException(new ErrorObject(ErrorEnum._0003_INVALID_PASSWORD, "Invalid Username or Password provided."));	
	}
	
	
}
