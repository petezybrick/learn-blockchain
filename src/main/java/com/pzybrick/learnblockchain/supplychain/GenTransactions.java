package com.pzybrick.learnblockchain.supplychain;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

public class GenTransactions {
	public static List<Block> blockchain = new ArrayList<Block>();
	// Demo/learning purposes only - use Keystore
	private static final String encPrivateKey = 
		"307B020100301306072A8648CE3D020106082A8648CE3D0301010461305F020101041815D67A1C8826B62A54AD050B65A9812470C04B5E25FDAA1DA00A06082A8648CE3D030101A13403320004F127F659E0B608FC1145E152DC54F1EA152824D21343AC0869077EB70837D9C70EEB9174D87D0AA89BF4C8AD5668402E";
	private static final String encPublicKey = 
		"3049301306072A8648CE3D020106082A8648CE3D03010103320004F127F659E0B608FC1145E152DC54F1EA152824D21343AC0869077EB70837D9C70EEB9174D87D0AA89BF4C8AD5668402E";
	private static final String encAlgorithm = "ECDSA";
	private PrivateKey myPrivateKey;	
	private PublicKey myPublicKey;

	public static void main(String[] args) {
		try {
			//Setup Bouncey castle as a Security Provider
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			GenTransactions genTransactions = new GenTransactions();
			genTransactions.process();

		} catch( Exception e ) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public void process() throws Exception  {
		initMyKeys();
		// TODO: supplier database with their public keys
		KeyPair supplierKeyPair = BlockchainUtils.generateKeyPair();
		SupplyChainTransaction transaction = new SupplyChainTransaction(myPublicKey, supplierKeyPair.getPublic(), "test order");
		transaction.generateSignature(myPrivateKey);
				//Verify the signature works and verify it from the public key
		System.out.println("Is signature verified");
		System.out.println(transaction.verifiySignature());

	}
	
	public void initMyKeys() throws Exception {
		KeyFactory kf = KeyFactory.getInstance(encAlgorithm); // or "EC" or whatever
		this.myPrivateKey = kf.generatePrivate(new PKCS8EncodedKeySpec( BlockchainUtils.toByteArray(encPrivateKey) ));
		this.myPublicKey = kf.generatePublic(new X509EncodedKeySpec( BlockchainUtils.toByteArray(encPublicKey) ));
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
