package com.pzybrick.learnblockchain.supplychain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.Map;


public class SupplyChainWallet {
	public PrivateKey privateKey;
	public PublicKey publicKey;
	
	public SupplyChainWallet() throws Exception {
		KeyPair keyPair = BlockchainUtils.generateKeyPair();	
    	privateKey = keyPair.getPrivate();
    	publicKey = keyPair.getPublic();
		
	}

	
	
}