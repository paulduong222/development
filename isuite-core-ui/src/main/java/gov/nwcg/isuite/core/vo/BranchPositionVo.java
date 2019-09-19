package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;

public class BranchPositionVo {
	private Boolean isNew;
	private String position;
	private Collection<KindVo> kindVos = new ArrayList<KindVo>();
	
	public static Collection<BranchPositionVo> getInstances(Collection<BranchSettingPositionVo> bspVos) {
		Collection<BranchPositionVo> vos = new ArrayList<BranchPositionVo>();
		
		for(BranchSettingPositionVo v : bspVos){
			BranchPositionVo vo = null;
			for(BranchPositionVo bpVo : vos){
				if(bpVo.getPosition().equals(v.getPosition())){
					vo=bpVo;
					vo.getKindVos().add(v.getKindVo());
					break;
				}
			}
			
			if(vo==null){
				vo = new BranchPositionVo();
				vo.setIsNew(false);
				vo.setPosition(v.getPosition());
				vo.getKindVos().add(v.getKindVo());
			}
			
			Collection<BranchPositionVo> newVos = new ArrayList<BranchPositionVo>();
			if(CollectionUtility.hasValue(vos)){
				Boolean bFound=false;
				for(BranchPositionVo bpVo : vos){
					if(bpVo.getPosition().equals(vo.getPosition())){
						newVos.add(vo);
						bFound=true;
					}else{
						newVos.add(bpVo);
					}
				}
				if(bFound==false)
					newVos.add(vo);
			}else{
				newVos.add(vo);
			}
			
			vos=newVos;
		}
		
		return vos;
	}
	
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return the kindVos
	 */
	public Collection<KindVo> getKindVos() {
		return kindVos;
	}
	/**
	 * @param kindVos the kindVos to set
	 */
	public void setKindVos(Collection<KindVo> kindVos) {
		this.kindVos = kindVos;
	}

	/**
	 * @return the isNew
	 */
	public Boolean getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew the isNew to set
	 */
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	
}
