package gov.nwcg.isuite.core.controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.IncidentSelectorRequestParams;
import gov.nwcg.isuite.core.filter.IncidentResourceFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentResourceFilterImpl;
import gov.nwcg.isuite.core.service.IncidentResource2Service;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.IncidentSelector2Vo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;

@Controller
@RequestMapping("/test")
public class TestController extends BaseRestController {

	@Autowired
	private IncidentResource2Service service;

	@RequestMapping(value = "/incidentresource/grid", method = RequestMethod.GET)
	public @ResponseBody DialogueVo getGrid(IncidentSelectorRequestParams params) throws ServiceException {
		IncidentResourceFilter filter = new IncidentResourceFilterImpl();
		
		filter.setIncidentId(params.getIncidentId());
		filter.setIncidentGroupId(params.getIncidentGroupId());

		DialogueVo dvo =  service.getGrid(filter, null, null);
		/*
		 * Reconstruct the collection of IncidentResourceVo's.
		 * in a different hierarchical manner
		 */
		if(CollectionUtility.hasValue(dvo.getRecordset())) {
			@SuppressWarnings("unchecked")
			Collection<IncidentResourceGridVo> newVos 
				= IncidentResourceGridVo.reGroup((Collection<IncidentResourceGridVo>)dvo.getRecordset(), new ArrayList<String>(), new ArrayList<Long>());
			dvo.setRecordset(newVos);
		}
		
		
		return super.resolveMessaging(dvo);
	}

}
