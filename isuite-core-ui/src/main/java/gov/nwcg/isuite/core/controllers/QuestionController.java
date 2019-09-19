package gov.nwcg.isuite.core.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.QuestionData;
import gov.nwcg.isuite.core.filter.impl.QuestionFilterImpl;
import gov.nwcg.isuite.core.service.QuestionService;
import gov.nwcg.isuite.core.vo.QuestionVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

@Controller
@RequestMapping("/questions")
public class QuestionController extends BaseRestController {

	@Autowired
	private QuestionService service;

	/**
	 * The options within the {@link QuestionFilterImpl} are available as query parameters.
	 * 
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Collection<QuestionVo> getByFilter(QuestionFilterImpl filter) throws ServiceException {
		return service.getByFilter(filter);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody QuestionVo getById(@PathVariable("id") Long id) throws ServiceException {
		return service.getById(id);
	}
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody void save(@RequestBody QuestionData data) throws ServiceException, ValidationException {
		if (data.getQuestionVo() != null) {
			service.save(data.getQuestionVo());
		} else if (!CollectionUtils.isEmpty(data.getQuestionVos())) {
			service.saveAll(data.getQuestionVos());
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void delete(@PathVariable("id") long id) throws ServiceException {
		QuestionVo vo = new QuestionVo();
		vo.setId(id);

		service.delete(vo);
	}
}
