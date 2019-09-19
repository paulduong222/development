package gov.nwcg.isuite.core.servlet.dtimport;

import gov.nwcg.isuite.core.datatransfer.DataImporter;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.rules.LoginRulesHandler;
import gov.nwcg.isuite.core.vo.IncidentGroupGridVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Level;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.xml.crypto.util.Base64;

public class DataTransferImportServlet extends HttpServlet {
	private String importException="";
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		LoggingInterceptor logger = this.getLogger(req.getSession(true).getServletContext());

		if(null != logger)logger.addLog("START DataTransferImportServlet", Level.TRACE);
		String dtFolder = "";
		
		try{
			dtFolder = this.getSystemParameterValue(
									SystemParameterTypeEnum.DATA_TRANSFER_FOLDER
									,req.getSession(true).getServletContext());
		
			if(!StringUtility.hasValue(dtFolder))
				throw new Exception("Unable to determine location of data transfer folder.");
			
		}catch(Exception e){
			throw new ServletException(e);
		}
		
		String filename = "dtSvltImport_" + Calendar.getInstance().getTimeInMillis()+".isw";
		File file = new File(dtFolder+filename);
		
		ServletOutputStream out = null;
		
		res.setContentType("text");
		out = res.getOutputStream();
		
		try{
			FileItemFactory factory = new DiskFileItemFactory(); 
			ServletFileUpload fileUpload = new ServletFileUpload(factory);

			String cred1="";
			String cred2="";
			String mode="";
			String incidentTis="";
			String groupname="";
			String incidentgroupTi="";
			String revisionlevel="";
			Long dsUserId=0L;
			
			FileItemIterator iter = fileUpload.getItemIterator(req);
			while (iter.hasNext()) {
				
			    FileItemStream fis =iter.next();
			    
			    if(!fis.isFormField()){
				    InputStream stream = fis.openStream();
				    OutputStream os = new FileOutputStream(file,true);
				    byte buf[] = new byte[512000];
				    int len;
				    while((len=stream.read(buf))>0){
					    ByteArrayOutputStream baos = new ByteArrayOutputStream();
				    	baos.write(buf,0,len);
						baos.writeTo(os);
						baos.close();
				    }
				    os.close();
				    stream.close();
			    	
			    }else{
			    	String fieldName=fis.getFieldName();
				    InputStream stream = fis.openStream();
				    String val=Streams.asString(stream);
				    stream.close();
				    
			    	if(fieldName.equals("cred")){
			    		StringTokenizer st = new StringTokenizer(val,"|");
			    		int i=0;
			    		while(st.hasMoreTokens()){
			    			String token = (String)st.nextToken();
			    			if(i==0)
			    				cred1=token;
			    			else
			    				cred2=token;
			    			i++;
			    		}
			    	}else if(fieldName.equals("incidentsti")){
			    		incidentTis=val;
			    	}else if(fieldName.equals("incidentgroupti")){
			    		incidentgroupTi=val;
			    	}else if(fieldName.equals("incidentgroupname")){
			    		groupname=val;
			    	}else if(fieldName.equals("revisionlevel")){
			    		revisionlevel=val;
			    	}else if(fieldName.equals("dsuserid")){
			    		dsUserId=TypeConverter.convertToLong(val);
			    	}else if(fieldName.equals("mode")){
			    		mode=val;
			    	}
			    	
			    	//System.out.println(fieldName);
			    	//System.out.println(val);
			    }
			}

			if(mode.equals("precheck")){
				FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
				Base64 base64 = new Base64();
				
				byte[] cred1Bytes=base64.decode(cred1);
				String strCred1=new String(enc.decrypt(cred1Bytes));
				
				byte[] cred2Bytes=base64.decode(cred2);
				String strCred2=new String(enc.decrypt(cred2Bytes));
				
				String loginName = strCred1;
				String password = strCred2;
				
				// Authenticate user first
				String authstatus=this.authenticateUser(loginName, password, req.getSession(true).getServletContext());
				if(!authstatus.equals("SUCCESS")){
					out.print(authstatus);
					out.close();
					return;
				}
					
				// validate revision level is same
				String rvstatus=this.validateRevisionLevel(revisionlevel, req.getSession(true).getServletContext());
				if(!rvstatus.equals("SUCCESS")){
					out.print(rvstatus);
					out.close();
					return;
				}
					
				// check if we can match group name
				if(StringUtility.hasValue(incidentgroupTi) && !StringUtility.hasValue(groupname)){
					String igstatus=this.validateGroupTi(incidentgroupTi, req.getSession(true).getServletContext());

					if(igstatus.equals("PROMPTNAME") || !igstatus.equals("SUCCESS")){
						out.print(igstatus);
						out.close();
						return;
					}
						
				}
					
				// check if group name is unique
				if(StringUtility.hasValue(incidentgroupTi) && StringUtility.hasValue(groupname)){
					String igstatus=this.validateUniqueGroupName(groupname, req.getSession(true).getServletContext());
						
					if(!igstatus.equals("SUCCESS")){
						out.print(igstatus);
						out.close();
						return;
					}
					
				}
				
				// all is ok, return success
				out.print("SUCCESS");
				out.close();
				return;
			}else{
				FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
				Base64 base64 = new Base64();
				
				byte[] cred1Bytes=base64.decode(cred1);
				String strCred1=new String(enc.decrypt(cred1Bytes));
				
				Long userId=this.getUserId(strCred1, req.getSession(true).getServletContext());
				
				// try and import transition file
				DialogueVo dvo=this.processFile(dtFolder+filename
												, userId
												, dsUserId
												, groupname
												, logger
												,req.getSession(true).getServletContext());
				
				//inspect dvo
				CourseOfActionVo coaVo = dvo.getCourseOfActionVo();
				if(coaVo.getCoaName().equals("IMPORT_DATA_FROM_FILE")){
					out.print("SUCCESS");
					out.flush();
					out.close();
					return;
				}else if(coaVo.getCoaName().equals("Exception")){
					out.print("ERROR:"+this.importException);
					out.flush();
					out.close();
					return;
				}

			}
				
		}catch(Exception e){
			out.print("ERROR: "  + e.getMessage());
		}finally{
			
		}
		
		if(null != logger)logger.addLog("END DataTransferImportServlet", Level.TRACE);
		
		out.close();

	}

	private LoggingInterceptor getLogger(ServletContext ctx) {
		try{
			return (LoggingInterceptor)this.getBean("loggingInterceptor", ctx);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * @param spType
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	private String getSystemParameterValue(SystemParameterTypeEnum spType, ServletContext ctx) throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)this.getBean("systemParameterDao",ctx);
		
		SystemParameter spEntity = spDao.getByParameterName(spType.name());
		
		if(null != spEntity)
			return spEntity.getParameterValue();
		else
			return null;
	}
	
	/**
	 * Convenience method to allow the httpservlet to get access to the 
	 * spring context beans.
	 * 
	 * @param beanName
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	private Object getBean(String beanName, ServletContext ctx) throws Exception {
		WebApplicationContext webCtx = WebApplicationContextUtils.getWebApplicationContext(ctx);
		if(null != webCtx)
			return webCtx.getBean(beanName);
		else
			return null;
	}

	private String getIsuiteProperty(ServletContext ctx, String name, String... args){
		Object argsArray[] = null;
		if(null!=args)
			argsArray=(Object[])args;
		
		WebApplicationContext webCtx = WebApplicationContextUtils.getWebApplicationContext(ctx);
		if(null != webCtx){
			ReloadableResourceBundleMessageSource isuiteProperties=(ReloadableResourceBundleMessageSource)webCtx.getBean("messageSource");
		
			return isuiteProperties.getMessage(name, argsArray,null);
		}else{
			return "";
		}
	}
	
	private String authenticateUser(String loginName, String password
								, ServletContext ctx) throws Exception {

		String status="";
		
		try{
			// Authenticate user
			WebApplicationContext webCtx = WebApplicationContextUtils.getWebApplicationContext(ctx);
			LoginRulesHandler ruleHandler = new LoginRulesHandler(webCtx);
			DialogueVo dialogueVo = new DialogueVo();
			if(ruleHandler.execute(loginName, password,true, dialogueVo) == AbstractRule._OK){
				// set userId
				String sql="SELECT id from isw_user where login_name='"+loginName+"'";
				
				
				status="SUCCESS";
			}else{
				String error = "";
				CourseOfActionVo coaVo = dialogueVo.getCourseOfActionVo();
				if(coaVo.getCoaType()==CourseOfActionTypeEnum.SHOWMESSAGE){
					MessageVo messageVo = coaVo.getMessageVo();
					String msg=this.getIsuiteProperty(ctx
														, messageVo.getMessageProperty()
														, messageVo.getParameters());
										
					
					status="AUTHFAIL:"+msg;
				}
				if(coaVo.getCoaType()==CourseOfActionTypeEnum.ERROR){
					MessageVo messageVo = coaVo.getMessageVo();
					String[] errParams=messageVo.getParameters();
					error = errParams[0];
					status="AUTHFAIL:"+error;
				}
				
			}
			
		}catch(Exception e){
			status="ERROR: authenticateUser:"+e.getMessage();
		}

		return status;
	}

	private String validateRevisionLevel(String level, ServletContext ctx) throws Exception {
		String status="";
		
		try{
			WebApplicationContext webCtx = WebApplicationContextUtils.getWebApplicationContext(ctx);
			
			String dblevel="";
			DataTransferDao dao = (DataTransferDao)webCtx.getBean("dataTransferDao");
			
			String sql="SELECT revisionlevel from revision";
			ArrayList rslt=(ArrayList)dao.executeQuery(sql);
			if(null != rslt){
				dblevel=TypeConverter.convertToString(rslt.get(0));
			}
			
			if(dblevel.equals(level)){
				status="SUCCESS";
			}else{
				status="ERROR: The Transition File Revision Level does not match the Revision Level in the database.";
			}
		}catch(Exception e){
			status="ERROR: validateRevisionLevel:"+e.getMessage();
		}
		
		return status;
	}
	
	private String validateGroupTi(String incomingTi, ServletContext ctx) throws Exception {
		String status="";
		
		try{
			WebApplicationContext webCtx = WebApplicationContextUtils.getWebApplicationContext(ctx);
			
			String dblevel="";
			IncidentGroupDao dao = (IncidentGroupDao)webCtx.getBean("incidentGroupDao");
			
			Collection<IncidentGroupGridVo> gridVos=dao.getGroupsForDataTransfer();
			Long matchingGroupId=0L;

			// try and find a matching ig
			for(IncidentGroupGridVo vo : gridVos){
				if(StringUtility.hasValue(vo.getTransferableIdentity())){
					if(vo.getTransferableIdentity().equals(incomingTi)){
						matchingGroupId=vo.getId();
						break;
					}
				}
			}
			
			if(!LongUtility.hasValue(matchingGroupId)){
				// need to prompt user for group name
				status="PROMPTNAME";
			}else{
				status="SUCCESS";
			}
			
		}catch(Exception e){
			status="ERROR: validateGroupTi:"+e.getMessage();
		}
		
		return status;
	}
	
	private String validateUniqueGroupName(String incomingName, ServletContext ctx) throws Exception {
		String status="";
		
		try{
			WebApplicationContext webCtx = WebApplicationContextUtils.getWebApplicationContext(ctx);
			
			IncidentGroupDao dao = (IncidentGroupDao)webCtx.getBean("incidentGroupDao");
			
			Collection<IncidentGroupGridVo> gridVos=dao.getGroupsForDataTransfer();

			// try and find a matching ig
			for(IncidentGroupGridVo vo : gridVos){
				if(vo.getGroupName().equalsIgnoreCase(incomingName)){
					status="VALIDATIONERROR: "+"An Incident Group already exists in Enterprise with the same name.  "+
							" Please enter a unique Incident Group Name.";
					return status;
				}
			}
			
			status="SUCCESS";
		}catch(Exception e){
			status="ERROR: validateRevisionLevel:"+e.getMessage();
		}
		
		return status;
	}

	private Long getUserId(String loginName, ServletContext ctx) throws Exception {
		Long id=0L;
		
		try{
			WebApplicationContext webCtx = WebApplicationContextUtils.getWebApplicationContext(ctx);
			
			DataTransferDao dao = (DataTransferDao)webCtx.getBean("dataTransferDao");
			
			String sql="SELECT id from isw_user where login_name='"+loginName+"' and deleted_date is null";
			ArrayList rslt=(ArrayList)dao.executeQuery(sql);
			if(null != rslt){
				id=TypeConverter.convertToLong(rslt.get(0));
			}
			
		}catch(Exception e){
			throw e;
		}
		
		return id;
	}
	
	private DialogueVo processFile(String filename
									, Long userId
									, Long dsUserId
									, String groupName
									,LoggingInterceptor logger
									,ServletContext ctx) throws Exception {
		DialogueVo dialogueVo = new DialogueVo();
		
		try{
			byte[] xmlByteArray = FileUtil.getFileContentsBytes(filename);
			
			WebApplicationContext webCtx = WebApplicationContextUtils.getWebApplicationContext(ctx);
			
			// init data importer
			DataImporter importer = new DataImporter(webCtx,"ENTERPRISE",logger);
			importer.xmlByteArray=xmlByteArray;
			importer.filePassword="eisuite";
			importer.fromWebServlet=true;
			importer.fromWebServletUserId=userId;
			importer.dataStewardUserId=dsUserId;
			
			importer.importData(dialogueVo);

			if(importer.hasException==true){
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("Exception");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
				coaVo.setIsDialogueEnding(true);
				
				importException=importer.importException;
				
				ErrorObject errorObject = new ErrorObject(ErrorEnum._90000_ERROR,new String[]{importer.importException});
				coaVo.getErrorObjectVos().add(errorObject);
				dialogueVo.setCourseOfActionVo(coaVo);
			}else{
				if(StringUtility.hasValue(importer.incidentGroupTi)){
					IncidentGroupDao igdao = (IncidentGroupDao)this.getBean("incidentGroupDao",ctx);

					if(StringUtility.hasValue(groupName)){
						// update group name
						igdao.updateGroupName(importer.incidentGroupTi, groupName);
					}
					
					// unlock group and all incidents
					igdao.updateSiteManaged(importer.incidentGroupTi, false);
					igdao.updateGroupIncidentsSiteManaged(importer.incidentGroupTi, false);
				}else{
					// unlock incident
					if(StringUtility.hasValue(importer.incidentTi)){
						IncidentDao idao = (IncidentDao)this.getBean("incidentDao",ctx);
						idao.updateSiteManaged(importer.incidentTi, false);
					}
				}
			}

		}catch(Exception e){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("ServletException");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
			coaVo.setIsDialogueEnding(true);
			importException=e.getMessage();

			ErrorObject errorObject = new ErrorObject(ErrorEnum._90000_ERROR,new String[]{e.getMessage()});
			coaVo.getErrorObjectVos().add(errorObject);
			dialogueVo.setCourseOfActionVo(coaVo);
		}
		
		return dialogueVo;
	}
}
