package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.BranchSettingPosition;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

import java.util.ArrayList;
import java.util.Collection;

public class BranchSettingPositionVo extends AbstractVo implements PersistableVo {
	private Long branchSettingId;
	private KindVo kindVo;
	private String position;
	
	public BranchSettingPositionVo(){
		
	}

	public static BranchSettingPositionVo getInstance(BranchSettingPosition entity, Boolean cascadable) throws Exception {
		BranchSettingPositionVo vo = new BranchSettingPositionVo();
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setBranchSettingId(entity.getBranchSettingId());
			vo.setPosition(entity.getPosition());
			
			if(null != entity.getKind()){
				vo.setKindVo(KindVo.getInstance(entity.getKind(), true));
			}
		}
		
		return vo;
	}
	
	public static Collection<BranchSettingPositionVo> getInstances(Collection<BranchSettingPosition> entities, Boolean cascadable) throws Exception {
		Collection<BranchSettingPositionVo> vos = new ArrayList<BranchSettingPositionVo>();
		
		for(BranchSettingPosition entity : entities){
			vos.add(getInstance(entity,cascadable));
		}
		
		return vos;
	}

	/**
	 * @return the branchSettingId
	 */
	public Long getBranchSettingId() {
		return branchSettingId;
	}

	/**
	 * @param branchSettingId the branchSettingId to set
	 */
	public void setBranchSettingId(Long branchSettingId) {
		this.branchSettingId = branchSettingId;
	}

	/**
	 * @return the kindVo
	 */
	public KindVo getKindVo() {
		return kindVo;
	}

	/**
	 * @param kindVo the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
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
	
	
}
