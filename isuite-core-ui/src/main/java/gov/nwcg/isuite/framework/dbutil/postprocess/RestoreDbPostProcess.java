package gov.nwcg.isuite.framework.dbutil.postprocess;

import gov.nwcg.isuite.framework.dbutil.postprocess.restoredb.SyncSysParamsPostProcess;

import org.springframework.context.ApplicationContext;

public class RestoreDbPostProcess {
	private ApplicationContext context=null;
	
	public enum PostProcessEnum {
		SYNC_SYS_PARAMS(10);
		
		private int ruleIdx=-1;
		
		PostProcessEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public RestoreDbPostProcess(ApplicationContext ctx){
		this.context=ctx;
	}
	
	public void executePostProcesses(String ds, int originalPatchLevel) throws Exception{
		try{
			
			for(RestoreDbPostProcess.PostProcessEnum postProcess : RestoreDbPostProcess.PostProcessEnum.values() ){
				switch(postProcess.getRuleIdx())
				{
					case 10:
						SyncSysParamsPostProcess postHandler = new SyncSysParamsPostProcess(this.context);
						postHandler.datasource=ds;
						postHandler.originalPatchLevel=originalPatchLevel;
						postHandler.execute();
						break;
				}
				
			}
		}catch(Exception e){
			throw e;
		}
	}
	
}
