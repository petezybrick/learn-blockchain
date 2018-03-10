package com.pzybrick.learnblockchain.supplychain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierBlock {
	
	private String hash;
	private String previousHash;
	private SupplyChainTransaction supplyChainTransaction;
	private long timeStamp;
	private int sequence;
	

	public SupplierBlock(String previousHash, SupplyChainTransaction supplyChainTransaction, int sequence ) {
		this.previousHash = previousHash;
		this.supplyChainTransaction = supplyChainTransaction;
		this.timeStamp = System.currentTimeMillis();
		this.sequence = sequence;
		this.hash = calculateHash();
	}
	
	
	public String calculateHash() {
		return BlockchainUtils.applySha256( 
			this.previousHash +
			Long.toString(timeStamp) +
			this.supplyChainTransaction + 
			Integer.toString(this.sequence)
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

	public long getTimeStamp() {
		return timeStamp;
	}

	public int getSequence() {
		return sequence;
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

	public SupplierBlock setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
		return this;
	}

	public SupplierBlock setSequence(int sequence) {
		this.sequence = sequence;
		return this;
	}

	@Override
	public String toString() {
		return "SupplierBlock [hash=" + hash + ", previousHash=" + previousHash + ", supplyChainTransaction="
				+ supplyChainTransaction + ", timeStamp=" + timeStamp + ", sequence=" + sequence + "]";
	}
	
}
