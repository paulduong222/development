package gov.nwcg.isuite.core.datatransfer;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.persistence.IapAttachmentDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.TimeInvoiceDao;
import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.framework.dbutil.pg.BackupDatabaseUtil;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class DataExporterV3 extends BaseDataTransfer {
	public StringBuffer xmlString;
	public byte[] xmlStringEncrypted;
	public String incidentName;
	private LoggingInterceptor logger=null;
	public Collection<String> logEntries = new ArrayList<String>();
	public String incidentGroupTi="";
	private Boolean doLogging=false;

	private String pgBaseDir="";
	private String nwcgFolder="";
	private String nwcgBackupFolder="";
	private String nwcgPatchFolder="";
	
	public DataExporterV3(ApplicationContext ctx, String runmode,LoggingInterceptor loger){
		super.context=ctx;
		super.runMode=runmode;
		this.logger=loger;
	}

	public String exportData(DataTransferVo vo, DbAvailVo dbVo, String groupTi,String incidentTi) throws Exception {
		String dataTransferFile="";
		String xmlFile="";
		String ts=String.valueOf(Calendar.getInstance().getTimeInMillis());
		super.dataTransFilesFldr=this.getDataTransferFilesFolder();
		String tmpDir=super.dataTransFilesFldr;
		String dtFile="dt_"+ts+".isw";

		BackupDatabaseUtil util = new BackupDatabaseUtil(context);
		// load nwcg folder paths from system parameter table
		this.pgBaseDir=getSystemParamValue(SystemParameterTypeEnum.PG_BASE_DIR);
		this.nwcgFolder=getSystemParamValue(SystemParameterTypeEnum.NWCG_FOLDER);
		this.nwcgBackupFolder=tmpDir+"tmpexp"+ts;
		
		util.pgBaseDir=this.pgBaseDir;
		util.nwcgFolder=this.nwcgFolder;
		util.nwcgBackupFolder=this.nwcgBackupFolder;
		util.nwcgPatchFolder=this.nwcgPatchFolder;

		try{
			// address sub group category record C4
			DataTransferDao dtDao = (DataTransferDao)context.getBean("dataTransferDao");
			String sql2="update iswl_sub_group_category set transferable_identity='ab5a4b5f-42bf-3e36-b546-328e395eafad' where code='C4'";
			dtDao.executeUpdate(sql2);
			
			TimeInvoiceDao tiDao = (TimeInvoiceDao)context.getBean("timeInvoiceDao");
			Collection<String> invoiceFilenames = tiDao.getAllInvoiceFileNames();
			util.pdfFilenames=invoiceFilenames;

			IapAttachmentDao iapAttachDao = (IapAttachmentDao)context.getBean("iapAttachmentDao");
			Collection<String> iapAttachments=iapAttachDao.getIapAttachmentFilenames();
			if(CollectionUtility.hasValue(iapAttachments))
				util.pdfFilenames.addAll(iapAttachments);
			
			String reportOutputFolder = getSystemParamValue(SystemParameterTypeEnum.REPORT_OUTPUT_FOLDER);
			util.reportOutputFolder = reportOutputFolder;
			
			String rl=getRevisionLevel();
			String dtXml="<DataTransfer>"+
				"<Password>"+vo.getFilePassword()+"</Password>"+
				"<Source>"+this.runMode+"</Source>"+
				"<ExportDate>"+""+"</ExportDate>"+
				"<SourceRevisionLevel>"+rl+"</SourceRevisionLevel>"+
				"<IncidentGroupTI>"+groupTi+"</IncidentGroupTI>"+
				"<IncidentTI>"+incidentTi+"</IncidentTI>"+
				"</DataTransfer>";

			util.backupDatabaseForTransfer(dbVo, tmpDir,dtFile, rl,dtXml);
			
			dataTransferFile = tmpDir+dtFile;			
		}catch(Exception e){
			throw e;
			//System.out.println(e.getMessage());
		}
		
		return dataTransferFile;
	}

	public void addLog(String s) {
		
	}

	public void setDoLogging(String s){
		if(s.equals("1"))
			doLogging=true;
	}
	
	protected String getRevisionLevel() throws Exception {
		String level="";
		DataTransferDao dao = (DataTransferDao)context.getBean("dataTransferDao");
		
		String sql="SELECT revisionlevel from revision";
		ArrayList rslt=(ArrayList)dao.executeQuery(sql);
		if(null != rslt){
			level=TypeConverter.convertToString(rslt.get(0));
		}
		
		return level;
	}

	protected String getDataTransferFilesFolder() throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.DATA_TRANSFER_FOLDER.name());

			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				String dirName=spEntity.getParameterValue();
				
				if(!FileUtil.isDir(dirName)){
					FileUtil.makeDir(dirName);
				}

				// the spentity.parameter_value  should end with file.separator
				// ex:  c:\\temp\\datatransferfiles\\"
				//      /temp/datatransferfiles/"
				String path=spEntity.getParameterValue();
				
				spDao.flushAndEvict(spEntity);
				
				return path;
			}else{
				return "";
			}

		}catch(Exception e){
			throw e;
		}
	}

	protected String getOutputFolder() throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.DATA_TRANSFER_FOLDER.name());
			
			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				String dirName=spEntity.getParameterValue();
				
				if(!FileUtil.isDir(dirName)){
					FileUtil.makeDir(dirName);
				}
				
				return dirName;
			}else{
				return "";
			}

		}catch(Exception e){
			throw e;
		}
	}
	
	protected String getOutputFile(String fileName) throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.DATA_TRANSFER_FOLDER.name());
			
			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				String dirName=spEntity.getParameterValue();
				
				if(!FileUtil.isDir(dirName)){
					FileUtil.makeDir(dirName);
				}
				
				return spEntity.getParameterValue() + fileName;
			}else{
				return fileName;
			}

		}catch(Exception e){
			throw e;
		}
	}
	
	protected String getSystemParamValue(SystemParameterTypeEnum paramName) throws Exception {
		
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
		
		SystemParameter entity = spDao.getByParameterName(paramName.name());
		
		return entity.getParameterValue();
	}
	
	public static void main(String[] args){
		String s="<IswIncident><Id>10000</Id><ti>bc88e455-ef72-33fe-90b9-16796492d44c</ti><A>LAKE</A><B></B><C>007894</C><D></D><E></E><F></F><G></G><H>06/17/2015 00:00:00</H><I>null</I><J>2015</J><K>true</K><L>200815</L><M>1</M><N>Y</N><O>0</O><P>0</P><Q>null</Q><R>0</R><S>14</S><T>N</T><U>8</U><D1>81</D1></IswIncident>";
		System.out.println(s.length());
		
	}
	
	
}
