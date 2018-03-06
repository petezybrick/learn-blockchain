package com.pzybrick.learnblockchain.supplychain;

import java.security.PrivateKey;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.crypto.util.PrivateKeyFactory;

public class GenTransactions {

	public static List<Block> blockchain = new ArrayList<Block>();

	public static void main(String[] args) {	
		//Setup Bouncey castle as a Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		//Create the new wallets
		// TODO: store local public/private key, have table of supply chain public keys
		SupplyChainWallet wallet = new SupplyChainWallet();
		System.out.println(wallet.privateKey + " " + wallet.publicKey);
		
		//Test public and private keys
		System.out.println("Private and public keys:");
//		PrivateKey privateKey =  PrivateKeyFactory.createKey(arg0);
//		System.out.println(BlockchainUtils.getStringFromKey(walletA.privateKey));
//		System.out.println(BlockchainUtils.getStringFromKey(walletA.publicKey));
//		//Create a test transaction from WalletA to walletB 
//		SupplyChainTransaction transaction = new SupplyChainTransaction(walletA.publicKey, walletB.publicKey, 5, null);
//		transaction.generateSignature(walletA.privateKey);
		//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified");
//		System.out.println(transaction.verifiySignature());
		
	}

	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}
}
