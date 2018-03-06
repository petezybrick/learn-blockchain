package com.pzybrick.learnblockchain.supplychain;

import java.security.*;
import java.util.ArrayList;

public class SupplyChainTransaction {

	public String transactionId; // this is also the hash of the transaction.
	public PublicKey sender; // senders address/public key.
	public PublicKey recipient; // Recipients address/public key.
	public String transactionData;
	public byte[] signature; // this is to prevent anybody else from spending
								// funds in our wallet.
	private static int sequence = 0; // a rough count of how many transactions
										// have been generated.

	// Constructor:
	public SupplyChainTransaction(PublicKey from, PublicKey to, String transactionData ) {
		this.sender = from;
		this.recipient = to;
		this.transactionData = transactionData;
	}

	// This Calculates the transaction hash (which will be used as its Id)
	private String calculateHash() {
		sequence++; // increase the sequence to avoid 2 identical transactions
					// having the same hash
		return BlockchainUtils.applySha256(BlockchainUtils.getStringFromKey(sender)
				+ BlockchainUtils.getStringFromKey(recipient) + transactionData + sequence);
	}

	// Signs all the data we dont wish to be tampered with.
	public void generateSignature(PrivateKey privateKey) {
		String data = BlockchainUtils.getStringFromKey(sender) + BlockchainUtils.getStringFromKey(recipient)
				+ transactionData;
		signature = BlockchainUtils.applyECDSASig(privateKey, data);
	}

	// Verifies the data we signed hasnt been tampered with
	public boolean verifiySignature() {
		String data = BlockchainUtils.getStringFromKey(sender) + BlockchainUtils.getStringFromKey(recipient)
				+ transactionData;
		return BlockchainUtils.verifyECDSASig(sender, data, signature);
	}

	// Returns true if new transaction could be created.
	public boolean processTransaction() {

		if (verifiySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
		transactionId = calculateHash();
		return true;
	}

}