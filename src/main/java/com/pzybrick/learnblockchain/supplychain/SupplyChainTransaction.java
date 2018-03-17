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
	private int transactionSequence = 0;

	// Constructor:
	public SupplyChainTransaction(PublicKey publicKeyFrom, PublicKey publicKeyTo, String transactionData, PrivateKey privateKeyFrom,
			int transactionSequence) {
		this.publicKeyFrom = publicKeyFrom;
		this.publicKeyTo = publicKeyTo;
		this.transactionData = transactionData;
		this.transactionSequence = transactionSequence;
		transactionId = calculateHash();
		this.generateSignature( privateKeyFrom );
	}

	// This Calculates the transaction hash (which will be used as its Id)
	private String calculateHash() {
		return BlockchainUtils.applySha256(BlockchainUtils.getStringFromKey(publicKeyFrom)
				+ BlockchainUtils.getStringFromKey(publicKeyTo) + transactionData + transactionSequence);
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
		return "SupplyChainTransaction [transactionId=" + transactionId + ", transactionData=" + transactionData + ", sequence=" + transactionSequence + "]";
	}

}