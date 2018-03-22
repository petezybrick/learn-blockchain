package com.pzybrick.learnblockchain.supplychain;

public class SupplierBlockVo {
	
	private String supplierBlockUuid;
	private String supplierBlockchainUuid;
	private String hash;
	private String previousHash;
	private SupplierBlockTransaction supplierBlockTransaction;
	private long blockTimestamp;
	private int blockSequence;
	

	public SupplierBlockVo() {
		this.blockTimestamp = System.currentTimeMillis();
	}
	
	
	public SupplierBlockVo updateHash() {
		this.hash = BlockchainUtils.applySha256( 
			this.previousHash +
			Long.toString(blockTimestamp) +
			this.supplierBlockTransaction + 
			Integer.toString(this.blockSequence)
			);
		return this;
	}
	
	public String calculateHash() {
		return BlockchainUtils.applySha256( 
			this.previousHash +
			Long.toString(blockTimestamp) +
			this.supplierBlockTransaction + 
			Integer.toString(this.blockSequence)
			);
	}

	public String getHash() {
		return hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public SupplierBlockTransaction getSupplierBlockTransaction() {
		return supplierBlockTransaction;
	}

	public long getBlockTimestamp() {
		return blockTimestamp;
	}

	public int getBlockSequence() {
		return blockSequence;
	}

	public SupplierBlockVo setHash(String hash) {
		this.hash = hash;
		return this;
	}

	public SupplierBlockVo setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
		return this;
	}

	public SupplierBlockVo setSupplierBlockTransaction(SupplierBlockTransaction supplierBlockTransaction) {
		this.supplierBlockTransaction = supplierBlockTransaction;
		return this;
	}

	public SupplierBlockVo setBlockTimestamp(long timeStamp) {
		this.blockTimestamp = timeStamp;
		return this;
	}

	public SupplierBlockVo setBlockSequence(int blockSequence) {
		this.blockSequence = blockSequence;
		return this;
	}

	@Override
	public String toString() {
		return "SupplierBlockVo [supplierBlockUuid=" + supplierBlockUuid + ", supplierBlockchainUuid=" + supplierBlockchainUuid + ", hash=" + hash
				+ ", previousHash=" + previousHash + ", supplierBlockTransaction=" + supplierBlockTransaction + ", blockTimestamp=" + blockTimestamp
				+ ", blockSequence=" + blockSequence + "]";
	}


	public String getSupplierBlockUuid() {
		return supplierBlockUuid;
	}


	public String getSupplierBlockchainUuid() {
		return supplierBlockchainUuid;
	}


	public SupplierBlockVo setSupplierBlockUuid(String supplierBlockUuid) {
		this.supplierBlockUuid = supplierBlockUuid;
		return this;
	}


	public SupplierBlockVo setSupplierBlockchainUuid(String supplierBlockchainUuid) {
		this.supplierBlockchainUuid = supplierBlockchainUuid;
		return this;
	}
	
}
