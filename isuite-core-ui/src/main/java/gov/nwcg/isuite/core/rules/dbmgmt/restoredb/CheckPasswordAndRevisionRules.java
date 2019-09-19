package gov.nwcg.isuite.core.rules.dbmgmt.restoredb;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.SystemDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.cryptfile.ISWFileEncryption;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import org.springframework.context.ApplicationContext;

import com.ibm.xml.crypto.util.Base64;

public class CheckPasswordAndRevisionRules extends AbstractDbMgmtRestoreRule implements IRule {
	public static final String _RULE_NAME = DbMgmtRestoreRuleFactory.RuleEnum.CHECK_PWD_AND_REVISION.name();

	public CheckPasswordAndRevisionRules(ApplicationContext ctx) {
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
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		ISWFileEncryption encFexp = new ISWFileEncryption();

		try{
			SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.ROSS_XML_FOLDER.name());
			String path=spEntity.getParameterValue();
			
			// unpack the backup file
			encFexp.tmpDir = FileUtil.iswCreateTmpPath();
			//System.out.println(encFexp.tmpDir);
			FileUtil.makeDir(encFexp.tmpDir+File.separator+"work");
			try{
				encFexp.unArchive(encFexp.tmpDir, path+super.backupFilename);
			}catch(Exception eee){
				//System.out.println(eee.getMessage());
			}
			encFexp.loadKey(encFexp.tmpDir, ISWFileEncryption.ISWPUBKEY);
			encFexp.decrypt(encFexp.tmpDir, "db.tar", encFexp.tmpDir+File.separator+"work"+File.separator);
			encFexp.decrypt(encFexp.tmpDir, "db.metadata", encFexp.tmpDir+File.separator+"work"+File.separator);
			
			// verify the pwd for the restore db
			StringBuffer metadata=FileUtil.getFileContents(encFexp.tmpDir+File.separator+"work"+File.separator+"db.metadata");
			StringTokenizer st = new StringTokenizer(metadata.toString(),"|");
			int i=0;
			String backupFileDbPasswordHash="";
			String backupFileRevision="";
			while(st.hasMoreTokens()){
				String token = (String)st.nextToken();
				switch(i){
					case 0:
						break;
					case 1:
						StringTokenizer st1 = new StringTokenizer(token,":");
						int x=0;
						while(st1.hasMoreTokens()){
							String strX=(String)st1.nextToken();
							if(x==1)
								backupFileRevision=strX;
							x++;
						}
						break;
					case 2:
						StringTokenizer st2 = new StringTokenizer(token,":");
						int y=0;
						while(st2.hasMoreTokens()){
							String strY=(String)st2.nextToken();
							if(y==1)
								backupFileDbPasswordHash=strY;
							y++;
						}
						break;
				}
				i++;
				
			}
			
			FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
			Base64 base64 = new Base64();
			byte[] bytes=base64.decode(backupFileDbPasswordHash);
			String dbpwd=new String(enc.decrypt(bytes));
			if(!dbpwd.equals(super.pwd)){
				// invalid password to restore the file
				ErrorObject error2 = new ErrorObject("error.800000","The password you entered is not correct. Please enter the correct password to restore this file.");
				errorObjects.add(error2);
			}
			
			// check revision level
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
			SystemDao dao = (SystemDao)context.getBean("systemDao");
			String systemRevisionLevel=dao.getRevisionLevel();
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
			int sysLevel=Integer.parseInt(systemRevisionLevel);
			int fileLevel=Integer.parseInt(backupFileRevision);
			if(fileLevel < sysLevel){
				// patch not up to date in restore file
				ErrorObject error2 = new ErrorObject("error.800000","The revision level for the restore database is not compatible with this release.");
				//errorObjects.add(error2);
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}finally {
			try{
				if (null != encFexp.tmpDir && encFexp.tmpDir.exists()) 
					FileUtil.rDelete(encFexp.tmpDir);
			} catch (Exception fe) {
				// smother
			}
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

	public static void main(String[] args) throws Exception {
		FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
		Base64 base64 = new Base64();
		
		byte[] pwdbytes=enc.encrypt("Betatester1!".getBytes());
		String pwd=base64.encode(pwdbytes);
		
		System.out.println(new String(pwd));
		
		byte[] bytes=base64.decode("uGCtKtrJ3tk=");
		String dbpwd=new String(enc.decrypt(bytes));
		System.out.println(dbpwd);
	}
}
