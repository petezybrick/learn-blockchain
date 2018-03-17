package com.pzybrick.learnblockchain.supplychain;

public class SupplierBlock {
	
	private String hash;
	private String previousHash;
	private SupplyChainTransaction supplyChainTransaction;
	private long blockTimestamp;
	private int blockSequence;
	

	public SupplierBlock(String previousHash, SupplyChainTransaction supplyChainTransaction, int blockSequence ) {
		this.previousHash = previousHash;
		this.supplyChainTransaction = supplyChainTransaction;
		this.blockTimestamp = System.currentTimeMillis();
		this.blockSequence = blockSequence;
		this.hash = calculateHash();
	}
	
	
	public String calculateHash() {
		return BlockchainUtils.applySha256( 
			this.previousHash +
			Long.toString(blockTimestamp) +
			this.supplyChainTransaction + 
			Integer.toString(this.blockSequence)
			);
	}

	public String getHash() {
		return hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public SupplyChainTransaction getSupplyChainTransaction() {
		return supplyChainTransaction;
	}

	public long getBlockTimestamp() {
		return blockTimestamp;
	}

	public int getBlockSequence() {
		return blockSequence;
	}

	public SupplierBlock setHash(String hash) {
		this.hash = hash;
		return this;
	}

	public SupplierBlock setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
		return this;
	}

	public SupplierBlock setSupplyChainTransaction(SupplyChainTransaction supplyChainTransaction) {
		this.supplyChainTransaction = supplyChainTransaction;
		return this;
	}

	public SupplierBlock setBlockTimestamp(long timeStamp) {
		this.blockTimestamp = timeStamp;
		return this;
	}

	public SupplierBlock setBlockSequence(int blockSequence) {
		this.blockSequence = blockSequence;
		return this;
	}

	@Override
	public String toString() {
		return "SupplierBlock [hash=" + hash + ", previousHash=" + previousHash + ", supplyChainTransaction="
				+ supplyChainTransaction + ", timeStamp=" + blockTimestamp + ", sequence=" + blockSequence + "]";
	}
	
}
