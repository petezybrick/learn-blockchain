package com.pzybrick.learnblockchain.supplychain.database;

import java.util.List;

import com.pzybrick.learnblockchain.supplychain.SupplierBlockVo;

public class SupplierBlockchainVo {
	private String supplierBlockChainUuid;
	private String supplierType;
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

	public String getSupplierType() {
		return supplierType;
	}

	public SupplierBlockchainVo setSupplierType(String supplierType) {
		this.supplierType = supplierType;
		return this;
	}


	

}
