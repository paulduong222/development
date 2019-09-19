package gov.nwcg.isuite.core.servlet.dtusers;

import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.rules.LoginRulesHandler;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.xml.crypto.util.Base64;

public class DataTransferGetDsUsersServlet extends HttpServlet {
	private String importException="";

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


		ServletOutputStream out = null;

		try{
			res.setContentType("text");
			out = res.getOutputStream();

			FileItemFactory factory = new DiskFileItemFactory(); 
			ServletFileUpload fileUpload = new ServletFileUpload(factory);

			String cred1="";
			String cred2="";

			FileItemIterator iter = fileUpload.getItemIterator(req);
			while (iter.hasNext()) {

				FileItemStream fis =iter.next();

				if(!fis.isFormField()){
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
					}

				}
			}

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

			UserDao userDao = (UserDao)this.getBean("userDao", req.getSession(true).getServletContext());
			if(null != userDao){
				Collection<UserVo> vos = userDao.getDataStewardUsers();
				if(!CollectionUtility.hasValue(vos)){
					out.print("NO_USERS");
					out.close();
					return;
				}else{
					int i=0;
					for(UserVo vo : vos){
						String user="USERID="+vo.getId()+","+
									"LOGINNAME="+vo.getLoginName()+","+
									"FIRSTNAME="+vo.getFirstName()+","+
									"LASTNAME="+vo.getLastName();
						if(i==0)
							out.print(user);
						else
							out.print("|"+user);
						i++;
					}
				}
			}else{
				out.print("ERROR: Unable to get userDao");
			}

		}catch(Exception e){
			throw new ServletException(e);
		}

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

	private String authenticateUser(String loginName
										, String password
										, ServletContext ctx) throws Exception {

		String status="";

		try{
			// Authenticate user
			WebApplicationContext webCtx = WebApplicationContextUtils.getWebApplicationContext(ctx);
			LoginRulesHandler ruleHandler = new LoginRulesHandler(webCtx);
			DialogueVo dialogueVo = new DialogueVo();
			if(ruleHandler.execute(loginName, password,true, dialogueVo) == AbstractRule._OK){
				
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
	
}
