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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class GenTransactions {
	private static final Logger logger = LogManager.getLogger(GenTransactions.class);
	// Demo/learning purposes only - use Keystore
	public static final String encodedPrivateKey = 
		"307B020100301306072A8648CE3D020106082A8648CE3D0301010461305F020101041815D67A1C8826B62A54AD050B65A9812470C04B5E25FDAA1DA00A06082A8648CE3D030101A13403320004F127F659E0B608FC1145E152DC54F1EA152824D21343AC0869077EB70837D9C70EEB9174D87D0AA89BF4C8AD5668402E";
	public static final String encodedPublicKey = 
		"3049301306072A8648CE3D020106082A8648CE3D03010103320004F127F659E0B608FC1145E152DC54F1EA152824D21343AC0869077EB70837D9C70EEB9174D87D0AA89BF4C8AD5668402E";
	public static final String encryptionAlgorithm = "ECDSA";
	private List<SupplierBlockVo> supplierBlockVos = new ArrayList<SupplierBlockVo>();
	private PrivateKey privateKeyMaster;	
	private PublicKey publicKeyMaster;
	public static final ObjectMapper objectMapper;
	
	static {
		objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
	}
 	
	public static void main(String[] args) {
		try {
			//Setup Bouncey castle as a Security Provider
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			GenTransactions genTransactions = new GenTransactions();
			genTransactions.process();

		} catch( Exception e ) {
			logger.error(e);
		}
	}
	
	public void process() throws Exception  {
		final int NUM_TEST_SUPPLIERS = 10;
		initKeysMaster();
		// TODO: Get all of the SupplierBlockchainVo's
		//simSupplierBlock
		List<KeyPair> supplierKeyPairs = new ArrayList<KeyPair>();
		for( int i=0 ; i<NUM_TEST_SUPPLIERS ; i++ ) {
			supplierKeyPairs.add( BlockchainUtils.generateKeyPair());
		}
		int blockSequence = 0;
		String previousHash = "0";
		for( KeyPair supplierKeyPair : supplierKeyPairs ) {
			SupplierBlockTransaction supplyChainTransaction = new SupplierBlockTransaction(publicKeyMaster, supplierKeyPair.getPublic(), "test order " + blockSequence, privateKeyMaster, blockSequence);
			SupplierBlockVo supplierBlock = new SupplierBlockVo()
					.setPreviousHash(previousHash).setSupplyChainTransaction(supplyChainTransaction)
					.setBlockSequence(blockSequence).setSupplierBlockUuid("TODO").setSupplierBlockChainUuid("TODO").updateHash();
			supplierBlockVos.add( supplierBlock );
			blockSequence++;
			previousHash = supplierBlock.getHash(); 
		}
		
		for( SupplierBlockVo supplierBlock : supplierBlockVos ) {
			logger.info(supplierBlock.toString());
		}
		logger.info("isChainValid " + isChainValid() );
	}
	
	public void initKeysMaster() throws Exception {
		KeyFactory kf = KeyFactory.getInstance(encryptionAlgorithm); // or "EC" or whatever
		this.privateKeyMaster = kf.generatePrivate(new PKCS8EncodedKeySpec( BlockchainUtils.toByteArray(encodedPrivateKey) ));
		this.publicKeyMaster = kf.generatePublic(new X509EncodedKeySpec( BlockchainUtils.toByteArray(encodedPublicKey) ));
	}

	public Boolean isChainValid() {
		SupplierBlockVo currentBlock; 
		SupplierBlockVo previousBlock; 
		
		//loop through blockchain to check hashes:
		for(int i=1; i < supplierBlockVos.size(); i++) {
			currentBlock = supplierBlockVos.get(i);
			previousBlock = supplierBlockVos.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
				logger.info("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.getHash().equals(currentBlock.getPreviousHash()) ) {
				logger.info("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}
}
