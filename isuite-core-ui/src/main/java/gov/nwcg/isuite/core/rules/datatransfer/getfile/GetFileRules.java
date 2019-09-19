package gov.nwcg.isuite.core.rules.datatransfer.getfile;

import gov.nwcg.isuite.core.datatransfer.DataImporter;
import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.MultipartUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import com.ibm.xml.crypto.util.Base64;

public class GetFileRules extends AbstractGetEnterpriseFileRule implements IRule {
	public static final String _RULE_NAME = GetEnterpriseFileRuleFactory.RuleEnum.GET_FILE.name();

	public GetFileRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

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
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * When importing, we want to check if the transition file revision level
		 * matches the revision level in the db.
		 */
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		// get selected file from getfilelist coa
		CourseOfActionVo coaPromptVo=dialogueVo.getCourseOfActionByName(GetFileListRules._RULE_NAME);
		if(null != coaPromptVo){
			DataTransferVo vo = (DataTransferVo)coaPromptVo.getStoredObject();
			
			// request file from server
			String requestURL=super.getSystemParamValue(SystemParameterTypeEnum.ENT_DATA_TRANSFER_EXPORT_URL);
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
			multipart.addFormField("requesttype", "GET_FILE");
			multipart.addFormField("fileid", String.valueOf(vo.getEnterpriseFileId()));
			multipart.addFormField("filepwd", vo.getFilePassword());
			multipart.addFormField("filepath", vo.getStoredFilepath());
			multipart.addFormField("fillerfield", "");
			
			byte[] filebytes=multipart.getAttachment();
			String message=multipart.attachmentMessage;
			
			if(message.equals("")){
				// write file to disk
				//FileUtil.writeFile2(filebytes, "c:/workspace/isuite-core/Webroot/datatransferfiles/downloadedfile.isw");
				
				// import file
				DataImporter importer = new DataImporter(super.context,super.getRunMode(),super.logger);
				importer.xmlByteArray=filebytes;
				importer.filePassword=vo.getFilePassword();
				importer.fromWebServlet=true;
				
				importer.importData(dialogueVo);
				
				if(importer.ruleHandlerFailure==false){
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setIsDialogueEnding(Boolean.TRUE);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.dataTransfer", "info.generic" , new String[]{"The transition file was imported."}, MessageTypeEnum.INFO));
					dialogueVo.setCourseOfActionVo(coaVo);
				}else{
					// do nothing dialogueVo should already have the coa
				}
				
			}else{
				message=message.replace("[", "");
				message=message.replace("]", "");
				ErrorObject error2 = 
					new ErrorObject("error.800000",message);
				errorObjects.add(error2);
			}
			
		}else{
			ErrorObject error2 = 
				new ErrorObject("error.800000","Internal error, prompt vo not available.");
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

}
