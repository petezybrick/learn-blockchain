package com.pzybrick.learnblockchain.supplychain;

import java.util.List;

public class SupplierBlockchainVo {
	private String supplierBlockChainUuid;
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

	public SupplierBlockchainVo setSupplierBlockChainUuid(String supplierBlockChainUuid) {
		this.supplierBlockChainUuid = supplierBlockChainUuid;
		return this;
	}
	

}
