package com.pzybrick.learnblockchain.supplychain;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenSimSuppliers {
	private static final Logger logger = LogManager.getLogger(GenSimSuppliers.class);
	private static final Random random = new Random();
	public static final int NUM_SIM_EACH_SUPPLIER = 30;
	public static final String[] triples = { "Brewers Rice Farms, LLC", "farm", "rice", "Chicken Meal Farms, LLC", "farm", "chicken", "Wheat Farms, LLC",
			"farm", "wheat", "Corn Gluten Farms, LLC", "farm", "corn", "Oat Groats Farms, LLC", "farm", "oat", "Fish Oil Farms, LLC", "farm", "fish",
			"Beet Pulp Farms, LLC", "farm", "beet", "Fertilizer Suppliers, LLC", "supplier", "fertilizer", "Seed Suppliers, LLC", "supplier", "seed",
			"Fish Fry Suppliers, LLC", "supplier", "fish_fry", "Fish Feed Suppliers, LLC", "supplier", "fish_feeNUM_SIM_EACH_SUPPLIERd", "Chick Suppliers, LLC",
			"supplier", "chicks", "Brewers Rice Cooperative, LLC", "distributor", "rice", "Chicken Meal Cooperative, LLC", "distributor", "chicken",
			"Wheat Cooperative, LLC", "distributor", "wheat", "Corn Gluten Cooperative, LLC", "distributor", "corn", "Oat Groats Cooperative, LLC",
			"distributor", "oat", "Fish Oil Cooperative, LLC", "distributor", "fish", "Beet Pulp Cooperative, LLC", "distributor", "beet", };
	public static final int NUM_SIM_SUPPLIERS = triples.length / 3;
	public static final int NUM_SIM_CHAINS_PER_SOURCE = 10;

	public static void main(String[] args) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			List<SupplierVo> supplierVos = GenSimSuppliers.createSupplierVos();
			// farm,rice supplier,seed supplier,fert coop,rice ABCPetFood
			Map<String, List<SupplierVo>> map = new HashMap<String, List<SupplierVo>>();
			for (SupplierVo supplierVo : supplierVos) {
				String key = supplierVo.getSupplierCategory() + "|" + supplierVo.getSupplierSubCategory();
				List<SupplierVo> supplierVosByKey = map.get(key);
				if (supplierVosByKey == null) {
					supplierVosByKey = new ArrayList<SupplierVo>();
					map.put(key, supplierVosByKey);
				}
				supplierVosByKey.add(supplierVo);
			}
			// Create SupplyChainTransactions, where each block in the chain is
			// one of the random suppliers between 1 and NUM_SIM_EACH_SUPPLIER
			// and add each SupplyChainTransaction to a SupplierBlock to create
			// a block chain
			KeyFactory kf = KeyFactory.getInstance(GenTransactions.encryptionAlgorithm); 
			PrivateKey privateKeyFrom = kf.generatePrivate(new PKCS8EncodedKeySpec( BlockchainUtils.toByteArray(GenTransactions.encodedPrivateKey) ));
			PublicKey publicKeyFrom = kf.generatePublic(new X509EncodedKeySpec( BlockchainUtils.toByteArray(GenTransactions.encodedPublicKey) ));
			ZonedDateTime simDate = ZonedDateTime.now().minusYears(1);
			int simdDateDaysOffset = 0;
			String[] brewersRiceSimStepKeys = { "farm|rice", "supplier|seed", "supplier|fertilizer", "distributor|rice" };
			String[] descs = { "Brewers Rice", "Grade A Brewers Rice Seeds", "10-20-10 Fertilizer", "Grade A Brewers Rice" };
			// TODO:
			// 1. break this into separate method, call for each chain of blocks
			// 2. write SupplierVO's to MySQL
			// x. add ABC Pet Food transaction block to end of chain - maybe this has is the key in Cassandra?
			// 3. write the blockchains to Cassandra - need to figure out a way to do this
			for (int i = 0; i < NUM_SIM_CHAINS_PER_SOURCE; i++) {
				int sequence = 0;
				String previousHash = "0";
				List<SupplierBlock> supplierBlockchain = new ArrayList<SupplierBlock>();
				for (int j = 0; j < brewersRiceSimStepKeys.length; j++) {
					String key = brewersRiceSimStepKeys[j];
					List<SupplierVo> supplierVosByKey = map.get(key);
					SupplierVo supplierVoRnd = supplierVosByKey.get(random.nextInt(NUM_SIM_EACH_SUPPLIER));
					SupplierTransaction supplierTransaction = new SupplierTransaction().setDunsNumber(supplierVoRnd.getDunsNumber())
							.setSupplierName(supplierVoRnd.getSupplierName()).setSupplierCategory(supplierVoRnd.getSupplierCategory())
							.setSupplierSubCategory(supplierVoRnd.getSupplierSubCategory()).setLotNumber("TODO").setItemNumber("TODO").setDescription(descs[j])
							.setQty(100).setUnits("TODO").setShippedDateIso8601(simDate.plusDays(simdDateDaysOffset++).format(DateTimeFormatter.ISO_INSTANT))
							.setRcvdDateIso8601(simDate.plusDays(simdDateDaysOffset++).format(DateTimeFormatter.ISO_INSTANT));
					String transactionData = GenTransactions.objectMapper.writeValueAsString(supplierTransaction);
					PublicKey publicKeyTo = kf.generatePublic(new X509EncodedKeySpec( BlockchainUtils.toByteArray(supplierVoRnd.getEncodedPublicKey()) )); 
					SupplyChainTransaction supplyChainTransaction = new SupplyChainTransaction( publicKeyFrom, publicKeyTo, transactionData, privateKeyFrom );
					SupplierBlock supplierBlock = new SupplierBlock( previousHash, supplyChainTransaction, sequence );
					supplierBlockchain.add( supplierBlock );
					sequence++;
					previousHash = supplierBlock.getHash();
				}
				System.out.println("+++++++++++++++++++++++++++++++++++");
				for( SupplierBlock supplierBlock : supplierBlockchain ) {
					System.out.println(supplierBlock);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static List<SupplierVo> createSupplierVos() throws Exception {
		char prefChar = 'A';
		int dunsNumber = 1;
		List<SupplierVo> supplierVos = new ArrayList<SupplierVo>();
		for (int i = 0; i < NUM_SIM_EACH_SUPPLIER; i++) {
			for (int j = 0; j < triples.length; j += 3) {
				KeyPair keyPair = BlockchainUtils.generateKeyPair();
				String encodedPublicKey = BlockchainUtils.toHexString(keyPair.getPublic().getEncoded());
				String supplierName = prefChar + String.valueOf(i + 1) + " " + triples[j];
				SupplierVo supplierVo = new SupplierVo().setSupplierUuid(UUID.randomUUID().toString()).setDunsNumber(String.format("%09d", dunsNumber++))
						.setSupplierName(supplierName).setSupplierCategory(triples[j + 1]).setSupplierSubCategory(triples[j + 2])
						.setStateProvince(randomStateProvince()).setCountry("US").setEncodedPublicKey(encodedPublicKey);
				System.out.println(supplierVo);
				supplierVos.add(supplierVo);
			}
			if (prefChar != 'Z')
				prefChar++;
			else
				prefChar = 'A';
		}
		return supplierVos;
	}

	public static String randomStateProvince() {
		final String[] abbrevs = { "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA",
				"MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
				"VA", "WA", "WV", "WI", "WY" };
		return abbrevs[random.nextInt(50)];
	}
}
