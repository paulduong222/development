package gov.nwcg.isuite.core.rules.datatransfer.getfile;

import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.CustomPromptVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.MultipartUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.context.ApplicationContext;

import com.ibm.xml.crypto.util.Base64;

public class GetFileListRules extends AbstractGetEnterpriseFileRule implements IRule {
	public static final String _RULE_NAME = GetEnterpriseFileRuleFactory.RuleEnum.GET_FILE_LIST.name();

	public GetFileListRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
				
				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
				
			}else{
				
				/*
				 * Run rule check
				 */
				if(runRuleCheck(dialogueVo)==_FAIL)
					return _FAIL;
				

				/*
				 * Rule check passed, mark as completed
				 */
				dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildNoActionCoaVo(_RULE_NAME,true));
				
			}
				
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	private int checkPromptResult(DialogueVo dialogueVo) {
		
		// continue
		dialogueVo.getCourseOfActionVo().setIsComplete(true);
		dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		
		// Connect with Enterprise and get transition file list
		String requestURL=super.getSystemParamValue(SystemParameterTypeEnum.ENT_DATA_TRANSFER_EXPORT_URL);

		if(StringUtility.hasValue(requestURL)){
			String charset = "UTF-8";
			FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
			Base64 base64 = new Base64();
			
			byte[] bytes1 = enc.encrypt(super.dataTransferVo.getEnterpriseLoginName().getBytes());
			String strCred1=base64.encode(bytes1);
			
			byte[] bytes2 = enc.encrypt(super.dataTransferVo.getEnterprisePassword().getBytes());
			String strCred2=base64.encode(bytes2);
			
			String cred=strCred1+"|"+strCred2;
			
			MultipartUtility multipart = new MultipartUtility(requestURL, charset);

			multipart.addHeaderField("User-Agent", "e-ISuite");
			multipart.addFormField("cred", cred);
			multipart.addFormField("requesttype", "GET_FILELIST");
			multipart.addFormField("fillerfield", "");

			List<String> response = multipart.finish();

			//System.out.println("SERVER REPLIED:");
			String result="";
			
			for (String line : response) {
				result=result+line;
				//System.out.println(line);
			}

			if(StringUtility.hasValue(result)){
				if(result.startsWith("AUTHFAIL") || result.startsWith("ERROR")){
					ErrorObject error2 = 
						new ErrorObject("error.800000",result);
					errorObjects.add(error2);
				}else if(result.startsWith("NO_FILES") ){
					ErrorObject error2 = 
						new ErrorObject("error.800000","There are no transition files available from e-Isuite Enterprise.");
					errorObjects.add(error2);
				}else{
					// parse results
					Collection<DataTransferVo> vos = this.parseList(result);

					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
					coaVo.setCustomPromptVo(new CustomPromptVo("SELECT_FILE","text.dataTransfer" ,"action.none",vos));
					
					dialogueVo.setCourseOfActionVo(coaVo);
					
					return _FAIL;
				}
			}else{
				// no reply from server
				ErrorObject error2 = 
					new ErrorObject("error.800000"
										,"No response from e-ISuite Enterprise.");
				errorObjects.add(error2);
			}
		}else{
			// system parameter not defined
			ErrorObject error2 = 
				new ErrorObject("error.800000"
									,"e-ISuite Enterprise system parameter not defined.");
			errorObjects.add(error2);
		}
		
		if(CollectionUtility.hasValue(errorObjects)){
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("ValidationError");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
			coaVo.setIsDialogueEnding(true);

			coaVo.setErrorObjectVos(errorObjects);
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

	private Collection<DataTransferVo> parseList(String result) throws Exception{
		Collection<DataTransferVo> list = new ArrayList<DataTransferVo>();
		
		if(result.indexOf("|")>0){
			StringTokenizer st = new StringTokenizer(result,"|");
			while(st.hasMoreTokens()){
				String token = st.nextToken();
				
				DataTransferVo vo = this.parseVoData(token);
				list.add(vo);
			}
			
		}else{
			DataTransferVo vo = this.parseVoData(result);
			list.add(vo);
		}
		
		return list;
	}
	
	private DataTransferVo parseVoData(String result) throws Exception {
		DataTransferVo dtvo = new DataTransferVo();
		
		StringTokenizer st2 = new StringTokenizer(result,",");
		while(st2.hasMoreTokens()){
			String valpair=st2.nextToken();
			StringTokenizer st3 = new StringTokenizer(valpair,"=");
			int i=0;
			String name="";
			String value="";
			while(st3.hasMoreTokens()){
				String val=st3.nextToken();
				if(i==0)
					name=val;
				else
					value=val;
				i++;
			}
			
			if(name.equals("ID")){
				dtvo.setEnterpriseFileId(value);
			}else if(name.equals("FILENAME")){
				dtvo.setFilename(value);
			}else if(name.equals("INCIDENTID")){
				if(StringUtility.hasValue(value) && !value.equals("0") && !value.equalsIgnoreCase("null")){
					Long id=TypeConverter.convertToLong(value);
					dtvo.setIncidentId(id);
				}
			}else if(name.equals("INCIDENTGROUPID")){
				if(StringUtility.hasValue(value) && !value.equals("0") && !value.equalsIgnoreCase("null")){
					Long id=TypeConverter.convertToLong(value);
					dtvo.setIncidentGroupId(id);
				}
			}else if(name.equals("CREATEDDATE")){
				if(StringUtility.hasValue(value)){
					dtvo.setFileCreatedDate(value);
				}else
					dtvo.setFileCreatedDate("");
			}else if(name.equals("STOREDFILEPATH")){
				dtvo.setStoredFilepath(value);
			}
			
		}
		
		return dtvo;
	}
}
