package com.pzybrick.learnblockchain.supplychain;

import java.io.File;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pzybrick.learnblockchain.supplychain.SimBlockchainSequenceItem.DescCatSubcatItem;
import com.pzybrick.learnblockchain.supplychain.database.PooledDataSource;
import com.pzybrick.learnblockchain.supplychain.database.SupplierBlockDao;
import com.pzybrick.learnblockchain.supplychain.database.SupplierBlockTransactionDao;
import com.pzybrick.learnblockchain.supplychain.database.SupplierBlockTransactionVo;
import com.pzybrick.learnblockchain.supplychain.database.SupplierBlockVo;
import com.pzybrick.learnblockchain.supplychain.database.SupplierBlockchainDao;
import com.pzybrick.learnblockchain.supplychain.database.SupplierBlockchainVo;
import com.pzybrick.learnblockchain.supplychain.database.SupplierDao;
import com.pzybrick.learnblockchain.supplychain.database.SupplierTransactionDao;
import com.pzybrick.learnblockchain.supplychain.database.SupplierTransactionVo;
import com.pzybrick.learnblockchain.supplychain.database.SupplierVo;

public class GenSimSuppliers {
	private static final Logger logger = LogManager.getLogger(GenSimSuppliers.class);
	private static final Random random = new Random();
	public static final int NUM_SIM_EACH_SUPPLIER = 30;
	public static final String[] triples = { "Brewers Rice Farms, LLC", "farm", "rice", "Chicken Meal Farms, LLC", "farm", "chicken", "Wheat Farms, LLC",
			"farm", "wheat", "Corn Gluten Farms, LLC", "farm", "corn", "Oat Groats Farms, LLC", "farm", "oat", "Fish Oil Farms, LLC", "farm", "fish",
			"Beet Pulp Farms, LLC", "farm", "beet", "Fertilizer Suppliers, LLC", "supplier", "fertilizer", "Seed Suppliers, LLC", "supplier", "seed",
			"Fish Fry Suppliers, LLC", "supplier", "fish_fry", "Fish Feed Suppliers, LLC", "supplier", "fish_feed",
			"Chicken Feed Suppliers, LLC", "supplier", "chicken_feed", "Chick Suppliers, LLC",
			"supplier", "chicks", "Brewers Rice Cooperative, LLC", "distributor", "rice", "Chicken Meal Cooperative, LLC", "distributor", "chicken",
			"Wheat Cooperative, LLC", "distributor", "wheat", "Corn Gluten Cooperative, LLC", "distributor", "corn", "Oat Groats Cooperative, LLC",
			"distributor", "oat", "Fish Oil Cooperative, LLC", "distributor", "fish_oil", "Beet Pulp Cooperative, LLC", "distributor", "beet", };
	public static final int NUM_SIM_SUPPLIERS = triples.length / 3;
	public static final int NUM_SIM_CHAINS_PER_SOURCE = 100;
	public static final int NUM_SIM_LOTS = 10;
	public static final int NUM_SIM_PROD_WEEKS = 8;
	// Demo/learning purposes only - use Keystore
	public static final String encodedPrivateKey = 
		"307B020100301306072A8648CE3D020106082A8648CE3D0301010461305F020101041815D67A1C8826B62A54AD050B65A9812470C04B5E25FDAA1DA00A06082A8648CE3D030101A13403320004F127F659E0B608FC1145E152DC54F1EA152824D21343AC0869077EB70837D9C70EEB9174D87D0AA89BF4C8AD5668402E";
	public static final String encodedPublicKey = 
		"3049301306072A8648CE3D020106082A8648CE3D03010103320004F127F659E0B608FC1145E152DC54F1EA152824D21343AC0869077EB70837D9C70EEB9174D87D0AA89BF4C8AD5668402E";
	public static final String encryptionAlgorithm = "ECDSA";

	private KeyFactory keyFactory;
	private PrivateKey privateKeyFrom;
	private PublicKey publicKeyFrom;
	private ZonedDateTime simDate;
	private int simdDateDaysOffset;
	private String pathSimBlockchainSequenceItemsJson;
	private SupplyBlockchainConfig config;

	public GenSimSuppliers(String pathSimBlockchainSequenceItemsJson) throws Exception {
		this.pathSimBlockchainSequenceItemsJson = pathSimBlockchainSequenceItemsJson;
		this.keyFactory = KeyFactory.getInstance(encryptionAlgorithm);
		privateKeyFrom = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(BlockchainUtils.toByteArray(encodedPrivateKey)));
		publicKeyFrom = keyFactory.generatePublic(new X509EncodedKeySpec(BlockchainUtils.toByteArray(encodedPublicKey)));
		simDate = ZonedDateTime.now().minusYears(1);
		simdDateDaysOffset = 0;
	}

	public static void main(String[] args) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			GenSimSuppliers genSimSuppliers = new GenSimSuppliers(args[0]);
			genSimSuppliers.process();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void process() throws Exception {
		try {
			config = SupplyBlockchainConfig.getInstance( System.getenv("ENV"), System.getenv("CONTACT_POINT"),
					System.getenv("KEYSPACE_NAME") );
			PooledDataSource.getInstance( config ); 
			// Clear out old simulated data
			deleteAllTables();

			List<SupplierVo> supplierVos = GenSimSuppliers.createSupplierVos();
			// farm,rice supplier,seed supplier,fert, coop,rice ABCPetFood
			Map<String, List<SupplierVo>> mapSupplierVos = new HashMap<String, List<SupplierVo>>();
			for (SupplierVo supplierVo : supplierVos) {
				String key = supplierVo.getSupplierCategory() + "|" + supplierVo.getSupplierSubCategory();
				List<SupplierVo> supplierVosByKey = mapSupplierVos.get(key);
				if (supplierVosByKey == null) {
					supplierVosByKey = new ArrayList<SupplierVo>();
					mapSupplierVos.put(key, supplierVosByKey);
				}
				supplierVosByKey.add(supplierVo);
			}
			// Create SupplyChainTransactions, where each block in the chain is
			// one of the random suppliers between 1 and NUM_SIM_EACH_SUPPLIER
			// and add each SupplyChainTransaction to a SupplierBlock to create
			// a block chain

			// TODO:
			// 1. break this into separate method, call for each chain of blocks
			// 2. write SupplierVO's to MySQL
			// x. add ABC Pet Food transaction block to end of chain - maybe
			// this has is the key in Cassandra?
			// 3. write the blockchains to Cassandra - need to figure out a way
			// to do this
			// x. Lot table - map each lot of dog food back to blockchain
			Map<String, List<SupplierBlockchainVo>> mapSimBlockchainsBySourceKey = new HashMap<String, List<SupplierBlockchainVo>>();
			List<SimBlockchainSequenceItem> simBlockchainSequenceItems = GenTransactions.objectMapper.readValue(new File(pathSimBlockchainSequenceItemsJson),
					new TypeReference<List<SimBlockchainSequenceItem>>() {
					});

			for (SimBlockchainSequenceItem simBlockchainSequenceItem : simBlockchainSequenceItems) {
				mapSimBlockchainsBySourceKey.put(simBlockchainSequenceItem.getSupplierType(),
						genSimBlockchains(mapSupplierVos, simBlockchainSequenceItem));
			}
			
			// a Lot of Canine Nutrition consists of Ingredients, each Ingredient comes from a chain of suppliers
			// 		For each ingredient type, i.e. Brewers Rice, there are multiple suppliers, aka multiple blockchains
			//		Pick a random supplier (blockchain) for each ingredient and add to the log
			List<String> supplierTypes = Arrays.asList("brewers_rice","chicken_meal","wheat","corn_gluten","oat_groats","beet_pulp","fish_oil");
			List<LotCanineNutrition> lotCanineNutritions = new ArrayList<LotCanineNutrition>();
			ZonedDateTime simProdWeek;
			simProdWeek = ZonedDateTime.of(2018, 1, 8, 0, 0, 0, 0, ZoneId.of("UTC"));
			final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
			for( int i=0 ; i<NUM_SIM_PROD_WEEKS ; i++) {
				String lotNumberRoot = simProdWeek.format(fmt) + "-";
				int lotNumberSeq = 1;
				for( int j=0 ; j<NUM_SIM_LOTS ; j++ ) {
					List<SupplierBlockchainVo> supplierBlockchains = new ArrayList<SupplierBlockchainVo>();
					LotCanineNutrition lotCanineNutrition = new LotCanineNutrition()
							.setLotNumber(lotNumberRoot +  lotNumberSeq)
							.setIngredientNames(supplierTypes)
							.setSupplierBlockchains(supplierBlockchains);
					lotCanineNutritions.add(lotCanineNutrition);
					for( String supplierType : supplierTypes ) {
						List<SupplierBlockchainVo> list = mapSimBlockchainsBySourceKey.get(supplierType);
						int offset = random.nextInt(list.size());
						//System.out.println(offset + " " + list.size());
						supplierBlockchains.add( list.get(offset));
					}
					//System.out.println("=========================");
					System.out.println(lotCanineNutrition);
					lotNumberSeq++;
				}
				simProdWeek = simProdWeek.plusWeeks(1);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
//>>> Stopped here
//>>> Next steps: SupplBlockchainVo, SupplierBlockVo, SupplierTransactionVo, 
					
	private List<SupplierBlockchainVo> genSimBlockchains(Map<String, List<SupplierVo>> mapSupplierVos, 
			SimBlockchainSequenceItem simBlockchainSequenceItem)
			throws Exception {
		List<SupplierBlockchainVo> supplierBlockChains = new ArrayList<SupplierBlockchainVo>();
		String supplierBlockChainUuid = BlockchainUtils.generateSortabledUuid();
		for (int i = 0; i < NUM_SIM_CHAINS_PER_SOURCE; i++) {
			int blockSequence = 0;
			String previousHash = "0";
			List<SupplierBlockVo> supplierBlocks = new ArrayList<SupplierBlockVo>();
			for (DescCatSubcatItem descCatSubcatItem : simBlockchainSequenceItem.getDescCatSubcatItems()) {
				String key = descCatSubcatItem.getCategory() + "|" + descCatSubcatItem.getSubCategory();
				List<SupplierVo> supplierVosByKey = mapSupplierVos.get(key);
				SupplierVo supplierVoRnd = supplierVosByKey.get(random.nextInt(NUM_SIM_EACH_SUPPLIER));
				// TODO: multiple lots per supplier, each with a different date
				String supplierTransactionUuid = BlockchainUtils.generateSortabledUuid();
				String supplierBlockTransactionUuid = BlockchainUtils.generateSortabledUuid();
				SupplierTransactionVo supplierTransactionVo = new SupplierTransactionVo()
						.setSupplierTransactionUuid(supplierTransactionUuid)
						.setSupplierBlockTransactionUuid(supplierBlockTransactionUuid)
						.setSupplierUuid(supplierVoRnd.getSupplierUuid())
						.setSupplierLotNumber("TODO").setItemNumber("TODO")
						.setDescription(descCatSubcatItem.getDesc()).setQty(100).setUnits("TODO")
						.setShippedDateIso8601( Timestamp.from( simDate.plusDays(simdDateDaysOffset++).toInstant()) )
						.setRcvdDateIso8601( Timestamp.from( simDate.plusDays(simdDateDaysOffset++).toInstant()) );
				PublicKey publicKeyTo = keyFactory.generatePublic(new X509EncodedKeySpec(BlockchainUtils.toByteArray(supplierVoRnd.getEncodedPublicKey())));
				SupplierBlockTransactionVo supplierBlockTransactionVo = new SupplierBlockTransactionVo()
						.setSupplierBlockTransactionUuid(supplierBlockTransactionUuid)
						.setEncodedPublicKeyFrom( BlockchainUtils.getEncodedStringFromKey( publicKeyFrom) )
						.setEncodedPublicKeyTo( BlockchainUtils.getEncodedStringFromKey( publicKeyTo) )
						.setSupplierTransactionVo(supplierTransactionVo).setTransactionSequence(blockSequence).updateTransactionId().generateSignature(privateKeyFrom);
				String supplierBlockUuid = BlockchainUtils.generateSortabledUuid();
				SupplierBlockVo supplierBlockVo = new SupplierBlockVo()
						.setPreviousHash(previousHash).setSupplierBlockTransactionVo(supplierBlockTransactionVo)
						.setBlockSequence(blockSequence).setSupplierBlockUuid(supplierBlockUuid).setSupplierBlockchainUuid(supplierBlockChainUuid).updateHash();
				supplierBlocks.add(supplierBlockVo);
				blockSequence++;
				previousHash = supplierBlockVo.getHash();
			}
			System.out.println("+++++++++++++++++++++++++++++++++++");
			for (SupplierBlockVo supplierBlock : supplierBlocks) {
				System.out.println(supplierBlock);
			}
			supplierBlockChains.add(new SupplierBlockchainVo().setSupplierBlockchainUuid(supplierBlockChainUuid)
					.setSupplierType(simBlockchainSequenceItem.getSupplierType()).setSupplierBlockVos(supplierBlocks));
		}
		return supplierBlockChains;
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
				SupplierVo supplierVo = new SupplierVo().setSupplierUuid(BlockchainUtils.generateSortabledUuid()).setDunsNumber(String.format("%09d", dunsNumber++))
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
		SupplierDao.insertBatchList( supplierVos );

		return supplierVos;
	}

	public static String randomStateProvince() {
		final String[] abbrevs = { "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA",
				"MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
				"VA", "WA", "WV", "WI", "WY" };
		return abbrevs[random.nextInt(50)];
	}
	
	public static void deleteAllTables() throws Exception {
		SupplierBlockchainDao.deleteAll();
		SupplierBlockDao.deleteAll();
		SupplierBlockTransactionDao.deleteAll();
		SupplierTransactionDao.deleteAll();
		SupplierDao.deleteAll();
	}
}
