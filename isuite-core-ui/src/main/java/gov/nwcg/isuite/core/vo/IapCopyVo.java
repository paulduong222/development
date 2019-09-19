package gov.nwcg.isuite.core.vo;

import java.util.Collection;

public class IapCopyVo {
	
	private IapPlanVo iapPlanSourceVo;
	private IapPlanVo iapPlanDestinationVo;
	private Collection<Integer> iapForm202Ids;
	private Collection<Integer> iapForm203Ids;
	private Collection<IapForm204Vo> iapForm204Vos;
	private Collection<Integer> iapForm205Ids;
	private Collection<Integer> iapForm206Ids;
	private Collection<Integer> iapForm220Ids;
	private Collection<IapAttachmentVo> iapAttachmentVos;
	
	public IapCopyVo(){
	}


	/**
	 * @param iapPlanSourceVo the iapPlanSourceVo to set
	 */
	public void setIapPlanSourceVo(IapPlanVo iapPlanSourceVo) {
		this.iapPlanSourceVo = iapPlanSourceVo;
	}

	/**
	 * @return the iapPlanSourceVo
	 */
	public IapPlanVo getIapPlanSourceVo() {
		return iapPlanSourceVo;
	}

	/**
	 * @param iapPlanDestinationVo the iapPlanDestinationVo to set
	 */
	public void setIapPlanDestinationVo(IapPlanVo iapPlanDestinationVo) {
		this.iapPlanDestinationVo = iapPlanDestinationVo;
	}

	/**
	 * @return the iapPlanDestinationVo
	 */
	public IapPlanVo getIapPlanDestinationVo() {
		return iapPlanDestinationVo;
	}

	/**
	 * @return the iapForm202Ids
	 */
	public Collection<Integer> getIapForm202Ids() {
		return iapForm202Ids;
	}

	/**
	 * @param iapForm202Ids the iapForm202Ids to set
	 */
	public void setIapForm202Ids(Collection<Integer> iapForm202Ids) {
		this.iapForm202Ids = iapForm202Ids;
	}

	/**
	 * @return the iapForm203Ids
	 */
	public Collection<Integer> getIapForm203Ids() {
		return iapForm203Ids;
	}

	/**
	 * @param iapForm203Ids the iapForm203Ids to set
	 */
	public void setIapForm203Ids(Collection<Integer> iapForm203Ids) {
		this.iapForm203Ids = iapForm203Ids;
	}

	/**
	 * @return the iapForm205Ids
	 */
	public Collection<Integer> getIapForm205Ids() {
		return iapForm205Ids;
	}

	/**
	 * @param iapForm205Ids the iapForm205Ids to set
	 */
	public void setIapForm205Ids(Collection<Integer> iapForm205Ids) {
		this.iapForm205Ids = iapForm205Ids;
	}

	/**
	 * @return the iapForm206Ids
	 */
	public Collection<Integer> getIapForm206Ids() {
		return iapForm206Ids;
	}

	/**
	 * @param iapForm206Ids the iapForm206Ids to set
	 */
	public void setIapForm206Ids(Collection<Integer> iapForm206Ids) {
		this.iapForm206Ids = iapForm206Ids;
	}

	/**
	 * @return the iapForm220Ids
	 */
	public Collection<Integer> getIapForm220Ids() {
		return iapForm220Ids;
	}

	/**
	 * @param iapForm220Ids the iapForm220Ids to set
	 */
	public void setIapForm220Ids(Collection<Integer> iapForm220Ids) {
		this.iapForm220Ids = iapForm220Ids;
	}

	/**
	 * @param iapForm204Vos the iapForm204Vos to set
	 */
	public void setIapForm204Vos(Collection<IapForm204Vo> iapForm204Vos) {
		this.iapForm204Vos = iapForm204Vos;
	}

	/**
	 * @return the iapForm204Vos
	 */
	public Collection<IapForm204Vo> getIapForm204Vos() {
		return iapForm204Vos;
	}

	/**
	 * @return the iapAttachmentVos
	 */
	public Collection<IapAttachmentVo> getIapAttachmentVos() {
		return iapAttachmentVos;
	}

	/**
	 * @param iapAttachmentVos the iapAttachmentVos to set
	 */
	public void setIapAttachmentVos(Collection<IapAttachmentVo> iapAttachmentVos) {
		this.iapAttachmentVos = iapAttachmentVos;
	}
}
