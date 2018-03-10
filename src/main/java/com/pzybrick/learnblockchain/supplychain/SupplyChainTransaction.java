package com.pzybrick.learnblockchain.supplychain;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

public class SupplyChainTransaction {

	public String transactionId;
	public PublicKey publicKeyFrom;
	public PublicKey publicKeyTo;
	public String transactionData;
	public byte[] signature;
	private int sequence = 0;

	// Constructor:
	public SupplyChainTransaction(PublicKey publicKeyFrom, PublicKey publicKeyTo, String transactionData, PrivateKey privateKeyFrom ) {
		this.publicKeyFrom = publicKeyFrom;
		this.publicKeyTo = publicKeyTo;
		this.transactionData = transactionData;
		transactionId = calculateHash();
		this.generateSignature( privateKeyFrom );
	}

	// This Calculates the transaction hash (which will be used as its Id)
	private String calculateHash() {
		sequence++; // increase the sequence to avoid 2 identical transactions
					// having the same hash
		return BlockchainUtils.applySha256(BlockchainUtils.getStringFromKey(publicKeyFrom)
				+ BlockchainUtils.getStringFromKey(publicKeyTo) + transactionData + sequence);
	}

	// Signs all the data we dont wish to be tampered with.
	public void generateSignature(PrivateKey privateKey) {
		String data = BlockchainUtils.getStringFromKey(publicKeyFrom) + BlockchainUtils.getStringFromKey(publicKeyTo)
				+ transactionData;
		signature = BlockchainUtils.applyECDSASig(privateKey, data);
	}

	// Verifies the data we signed hasnt been tampered with
	public boolean verifySignature() {
		String data = BlockchainUtils.getStringFromKey(publicKeyFrom) + BlockchainUtils.getStringFromKey(publicKeyTo)
				+ transactionData;
		return BlockchainUtils.verifyECDSASig(publicKeyFrom, data, signature);
	}

	// Returns true if new transaction could be created.
	public boolean processTransaction() {

		if (verifySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
		transactionId = calculateHash();
		return true;
	}

	@Override
	public String toString() {
		return "SupplyChainTransaction [transactionId=" + transactionId + ", transactionData=" + transactionData + ", sequence=" + sequence + "]";
	}

}