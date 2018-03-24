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
//		int blockSequence = 0;
//		String previousHash = "0";
//		for( KeyPair supplierKeyPair : supplierKeyPairs ) {
//			SupplierBlockTransaction supplyChainTransaction = new SupplierBlockTransaction(publicKeyMaster, supplierKeyPair.getPublic(), "test order " + blockSequence, privateKeyMaster, blockSequence);
//			SupplierBlockVo supplierBlock = new SupplierBlockVo()
//					.setPreviousHash(previousHash).setSupplierBlockTransaction(supplyChainTransaction)
//					.setBlockSequence(blockSequence).setSupplierBlockUuid("TODO").setSupplierBlockchainUuid("TODO").updateHash();
//			supplierBlockVos.add( supplierBlock );
//			blockSequence++;
//			previousHash = supplierBlock.getHash(); 
//		}
		
		for( SupplierBlockVo supplierBlock : supplierBlockVos ) {
			logger.info(supplierBlock.toString());
		}
		logger.info("isChainValid " + isChainValid() );
	}
	
	public void initKeysMaster() throws Exception {
		KeyFactory kf = KeyFactory.getInstance(GenSimSuppliers.encryptionAlgorithm); // or "EC" or whatever
		this.privateKeyMaster = kf.generatePrivate(new PKCS8EncodedKeySpec( BlockchainUtils.toByteArray(GenSimSuppliers.encodedPrivateKey) ));
		this.publicKeyMaster = kf.generatePublic(new X509EncodedKeySpec( BlockchainUtils.toByteArray(GenSimSuppliers.encodedPublicKey) ));
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
