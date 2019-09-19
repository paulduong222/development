package gov.nwcg.isuite.core.servlet;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

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
import org.apache.log4j.Level;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class RossFileUploadServlet extends HttpServlet {
 
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		//System.out.println("RossFileUploadServlet Start");
		LoggingInterceptor logger = this.getLogger(req.getSession(true).getServletContext());

		if(null != logger)logger.addLog("START RossFileUploadServlet", Level.TRACE);
		
		ServletOutputStream out = null;
		String uploadid="";
		
		res.setContentType("text");
		out = res.getOutputStream();
		
		try{
			/*
			 * Validate the client is our flex app
			 */
			//String sid = req.getParameter("sid");
			
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory(); 
			
			ServletFileUpload fileUpload = new ServletFileUpload(factory);

			String rossFilesFolder = this.getSystemParameterValue(
											SystemParameterTypeEnum.ROSS_XML_FOLDER
											,req.getSession(true).getServletContext());

			//rossFilesFolder = "c:/workspaces/isuite-core/Webroot/rossFiles/";

			if(!StringUtility.hasValue(rossFilesFolder))
				throw new Exception("Unable to determine location of ross xml folder.");
			
			String filename = "ROSS_" + Calendar.getInstance().getTimeInMillis()+".XML";
			File file = new File(rossFilesFolder+filename);
			
			// Process the uploaded items
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
			    	if(fieldName.equals("uploadid")){
					    //InputStream stream = fis.openStream();
					    //uploadid=Streams.asString(stream);
					    //stream.close();
						//((RossUploadVo)this.getBean("rossUploadVo", req.getSession(true).getServletContext())).addNewUploadData(uploadid, "");
			    	}
			    }
			}			
			
			//RossImportService rossImportService = (RossImportService)this.getBean("rossImportService", req.getSession(true).getServletContext());
			//int rslt=rossImportService.uploadFileComplete(filename,uploadid);
			out.print("STATUS:OK|FILENAME:"+filename);
			//System.out.println("servlet finished");
			
		}catch(Exception e){
			if(null != logger)logger.addLog("ERROR RossFileUploadServlet " + e.getMessage(), Level.TRACE);
			out.print("ERROR: "  + e.getMessage());
		}finally{
			
		}
		
		out.close();

		if(null != logger)logger.addLog("END RossFileUploadServlet ", Level.TRACE);
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
	
	
}
