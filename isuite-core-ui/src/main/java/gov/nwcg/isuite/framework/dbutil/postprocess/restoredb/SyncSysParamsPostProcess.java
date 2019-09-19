package gov.nwcg.isuite.framework.dbutil.postprocess.restoredb;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.SystemParameterImpl;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.vo.SystemParameterVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class SyncSysParamsPostProcess {
	private ApplicationContext context = null;
	private SystemParameterDao dao = null;
	public String datasource="";
	
	// need to know if this post process action should only be applied based on originalPatchLevel
	public int originalPatchLevel=0;
	
	public SyncSysParamsPostProcess(ApplicationContext ctx){
		this.context=ctx;
	}
	
	/*
	 * For each system parameter from isuite_site_master
	 * sync/create it in the target datasource database
	 */
	public void execute() throws Exception {
		Collection<String> sqls = new ArrayList<String>();

		// evaluate originalPatchLevel, in case new schema is added after a certain
		// patch that requires some default data be set, we can evalute patch level
		// to determine if any special post processing setup needs to happen
		
		// for this postProcessor (syncsysparams), ignore patchlevel and always apply
		//if(originalPatchLevel <= ?)
		//	return;
		
		try{
			dao = (SystemParameterDao)context.getBean("systemParameterDao");
			
			// all sys params from both site master and target database
			Collection<SystemParameterVo> siteMasterVos = this.getSystemParameterVos(true);
			Collection<SystemParameterVo> datasourceVos = this.getSystemParameterVos(false);

			boolean hasSiteAdminUserRecord=false;
			
			// sync the params by using site master params as source
			for(SystemParameterVo masterVo : siteMasterVos){
				//System.out.println("Master Name:"+masterVo.getParameterName());
				//System.out.println("Master Value:"+masterVo.getParameterValue());
				
				//if(!masterVo.getParameterName().equals("SITE_ADMIN_USER")){
					SystemParameterVo targetVo=null;
					
					for(SystemParameterVo vo : datasourceVos){
						if(vo.getParameterName().equalsIgnoreCase(masterVo.getParameterName())){
							targetVo=vo;
							break;
						}
					}
					
					if(masterVo.getParameterName().equals("SITE_ADMIN_USER")
							&& targetVo != null){
						hasSiteAdminUserRecord=true;
					}
					
					String sql="";
					if(null == targetVo){
						sql="INSERT INTO ISW_SYSTEM_PARAMETER (ID, PARAMETER_NAME, PARAMETER_VALUE) ";
						sql=sql+"VALUES (";
						if(dao.isOracleDialect()==true){
							sql=sql+"SEQ_SYSTEM_PARAMETER.nextVal,";
						}else{
							sql=sql+"nextVal('SEQ_SYSTEM_PARAMETER'),";
						}
						sql=sql+"'"+masterVo.getParameterName()+"',";
						sql=sql+"'"+masterVo.getParameterValue()+"'";
						sql=sql+") ";
						sqls.add(sql);
					}else{
						sql="UPDATE ISW_SYSTEM_PARAMETER SET ";
						sql=sql+"PARAMETER_VALUE='"+masterVo.getParameterValue()+"' ";
						sql=sql+"WHERE ID = " + targetVo.getId();
						sqls.add(sql);
						//System.out.println("Target Name:"+targetVo.getParameterName());
						//System.out.println("Target Value:"+targetVo.getParameterValue());
					}
				}
			//}

			if(hasSiteAdminUserRecord==false){
				for(SystemParameterVo vo : datasourceVos){
					if(vo.getParameterName().equalsIgnoreCase("SITE_ADMIN_USER")){
						hasSiteAdminUserRecord=true;
						break;
					}
				}
			}			
			
			if(hasSiteAdminUserRecord==false){
				String sql="INSERT INTO ISW_SYSTEM_PARAMETER (ID, PARAMETER_NAME, PARAMETER_VALUE) ";
				sql=sql+"VALUES (";
				if(dao.isOracleDialect()==true){
					sql=sql+"SEQ_SYSTEM_PARAMETER.nextVal,";
				}else{
					sql=sql+"nextVal('SEQ_SYSTEM_PARAMETER'),";
				}
				sql=sql+"'SITE_ADMIN_USER',";
				sql=sql+"'FROMBACKUP'";
				sql=sql+") ";
				sqls.add(sql);
			}
			
			if(CollectionUtility.hasValue(sqls)){
				dao.persistSqls(sqls);
			}
			
		}catch(Exception e){
			throw e;
		}
	}
	
	private Collection<SystemParameterVo> getSystemParameterVos(Boolean useMaster) throws Exception {
		Collection<SystemParameterVo> vos = new ArrayList<SystemParameterVo>();

		try{
			if(BooleanUtility.isTrue(useMaster))
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");

			// clear out any cached entities
			Collection<SystemParameter> entities = dao.getGrid();
			for(SystemParameter sp : entities){
				dao.flushAndEvict(sp);
			}
			
			vos = dao.getParametersForSync();
				
			if(BooleanUtility.isTrue(useMaster))
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
		}catch(Exception e){
			throw e;
		}finally{
			if(BooleanUtility.isTrue(useMaster))
				((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
		}
		return vos;
	}
}
