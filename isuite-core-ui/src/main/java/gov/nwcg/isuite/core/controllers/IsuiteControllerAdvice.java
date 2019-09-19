package gov.nwcg.isuite.core.controllers;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.AuthenticationException;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.IsuiteException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;

@ControllerAdvice
public class IsuiteControllerAdvice extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = { AuthenticationException.class })
	protected ResponseEntity<DialogueVo> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
		DialogueVo vo = new DialogueVo();
		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setErrorObjectVos(ex.getErrorObjects());
		vo.setCourseOfActionVo(coa);
		return new ResponseEntity<DialogueVo>(vo, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class , RuntimeException.class })
	protected ResponseEntity<DialogueVo> handleRuntimeException(RuntimeException ex, WebRequest request) {
		DialogueVo vo = new DialogueVo();
		// TODO: Determine what to provide here.
		ErrorObject err = new ErrorObject(ErrorEnum._90000_ERROR, ex.getMessage());
		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setErrorObjectVos(Collections.singletonList(err));
		vo.setCourseOfActionVo(coa);
		return new ResponseEntity<DialogueVo>(vo, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<DialogueVo> exception(Exception ie, WebRequest request) {
		DialogueVo vo = new DialogueVo();
		// TODO: Determine which error object would be desired here.
		ErrorObject err = new ErrorObject(ErrorEnum._90000_ERROR, ie.getMessage());
		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setErrorObjectVos(Collections.singletonList(err));
		coa.setCoaType(CourseOfActionTypeEnum.ERROR);
		vo.setCourseOfActionVo(coa);
		return new ResponseEntity<DialogueVo>(vo, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { IsuiteException.class })
	public ResponseEntity<DialogueVo> handleIsuiteException(IsuiteException ie, WebRequest request) {
		DialogueVo vo = new DialogueVo();
		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setErrorObjectVos(ie.getErrorObjects());
		coa.setCoaType(CourseOfActionTypeEnum.ERROR);
		vo.setCourseOfActionVo(coa);
		return new ResponseEntity<DialogueVo>(vo, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { ServiceException.class })
	public ResponseEntity<DialogueVo> handleServiceException(ServiceException ie, WebRequest request) {
		DialogueVo vo = new DialogueVo();
		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setErrorObjectVos(ie.getErrorObjects());
		vo.setCourseOfActionVo(coa);
		return new ResponseEntity<DialogueVo>(vo, HttpStatus.BAD_REQUEST);
	}
}
