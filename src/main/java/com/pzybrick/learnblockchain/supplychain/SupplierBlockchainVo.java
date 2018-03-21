package com.pzybrick.learnblockchain.supplychain;

import java.util.List;

public class SupplierBlockchainVo {
	private String supplierBlockChainUuid;
	private String supplierUuid;
	private List<SupplierBlockVo> supplierBlockVos;

	public List<SupplierBlockVo> getSupplierBlockVos() {
		return supplierBlockVos;
	}

	public SupplierBlockchainVo setSupplierBlockVos(List<SupplierBlockVo> supplierBlocks) {
		this.supplierBlockVos = supplierBlocks;
		return this;
	}

	@Override
	public String toString() {
		return "SupplierBlockchain [supplierBlocks=" + supplierBlockVos + "]";
	}

	public String getSupplierBlockChainUuid() {
		return supplierBlockChainUuid;
	}

	public String getSupplierUuid() {
		return supplierUuid;
	}

	public SupplierBlockchainVo setSupplierBlockChainUuid(String supplierBlockChainUuid) {
		this.supplierBlockChainUuid = supplierBlockChainUuid;
		return this;
	}

	public SupplierBlockchainVo setSupplierUuid(String supplierUuid) {
		this.supplierUuid = supplierUuid;
		return this;
	}
	
	

}
