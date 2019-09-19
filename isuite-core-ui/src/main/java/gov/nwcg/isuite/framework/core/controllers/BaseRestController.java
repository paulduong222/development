package gov.nwcg.isuite.framework.core.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.web.context.ServletContextAware;

import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

public class BaseRestController implements ApplicationContextAware, ServletContextAware {
	protected ApplicationContext context;
	protected ServletContext servletContext;

	@Autowired
	protected MessageSource messageSource;
	
	protected DialogueVo handlerException(Exception e){
		//TODO
		return new DialogueVo();
	}

	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	protected DialogueVo resolveMessaging(DialogueVo vo) {
		DialogueVo newVo = new DialogueVo();
		newVo = vo;
		if(null != vo.getCourseOfActionVo() && vo.getCourseOfActionVo().getCoaType() == CourseOfActionTypeEnum.SHOWMESSAGE){
			if(null != vo.getCourseOfActionVo().getMessageVo()){
				newVo.getCourseOfActionVo().getMessageVo()
					.setMessageProperty(this.messageSource.getMessage(
							vo.getCourseOfActionVo().getMessageVo().getMessageProperty(),
							vo.getCourseOfActionVo().getMessageVo().getParameters(),
							Locale.US));
				newVo.getCourseOfActionVo().getMessageVo()
					.setTitleProperty(this.messageSource.getMessage(vo.getCourseOfActionVo().getMessageVo().getTitleProperty(), null,Locale.US));

				if(CollectionUtility.hasValue(vo.getCourseOfActionVo().getMessageVo().getAdditionalMessageVos())){
					Collection<MessageVo> newAdditionalVos = new ArrayList<MessageVo>();
					for(MessageVo mvo : vo.getCourseOfActionVo().getMessageVo().getAdditionalMessageVos()) {
						MessageVo newMvo = new MessageVo();
						newMvo.setMessageProperty(this.messageSource.getMessage(
								mvo.getMessageProperty(),
								mvo.getParameters(),
								Locale.US)
						);
						newMvo.setTitleProperty(
							this.messageSource.getMessage(
								mvo.getTitleProperty(), null,Locale.US)
						);
						newAdditionalVos.add(newMvo);
					}
					vo.getCourseOfActionVo().getMessageVo().setAdditionalMessageVos(newAdditionalVos);
				}
			}
		}
		if(null != vo.getCourseOfActionVo() && vo.getCourseOfActionVo().getCoaType() == CourseOfActionTypeEnum.ERROR){
			if(CollectionUtility.hasValue(vo.getCourseOfActionVo().getErrorObjectVos())){
				Collection<ErrorObject> errs = new ArrayList<ErrorObject>();
				for(ErrorObject v : vo.getCourseOfActionVo().getErrorObjectVos()) {
					ErrorObject err = new ErrorObject();
					err.setErrorProperty(this.messageSource.getMessage(v.getErrorProperty(),v.getParameters(),Locale.US));
					//Add parameters to ErrorObject
					err.setParameters(v.getParameters());
					errs.add(err);
				}
				newVo.getCourseOfActionVo().setErrorObjectVos(errs);
			}
		}
		if(null != vo.getCourseOfActionVo() && vo.getCourseOfActionVo().getCoaType() == CourseOfActionTypeEnum.PROMPT){
			if(null != vo.getCourseOfActionVo().getPromptVo()){
				newVo.getCourseOfActionVo().getPromptVo()
					.setMessageProperty(this.messageSource.getMessage(
							vo.getCourseOfActionVo().getPromptVo().getMessageProperty(),
							vo.getCourseOfActionVo().getPromptVo().getParameters(),
							Locale.US));
				newVo.getCourseOfActionVo().getPromptVo()
					.setTitleProperty(this.messageSource.getMessage(vo.getCourseOfActionVo().getPromptVo().getTitleProperty(), null,Locale.US));
			}
		}
		return newVo;
	}
	
	protected CourseOfActionVo getCourseOfActionVo(CourseOfActionTypeEnum coaType, String coaName) {
		CourseOfActionVo vo = new CourseOfActionVo();
		vo.setCoaName(coaName);
		vo.setCoaType(coaType);
		return vo;
	}
	protected GlobalCacheVo getGlobalCacheVo() throws ServiceException {
		return (GlobalCacheVo)context.getBean("globalCacheVo");
	}
}
