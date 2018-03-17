package com.pzybrick.learnblockchain.supplychain;

import java.io.File;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pzybrick.learnblockchain.supplychain.SimBlockchainSequenceItem.DescCatSubcatItem;

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
	private KeyFactory keyFactory;
	private PrivateKey privateKeyFrom;
	private PublicKey publicKeyFrom;
	private ZonedDateTime simDate;
	private int simdDateDaysOffset;
	private String pathSimBlockchainSequenceItemsJson;

	public GenSimSuppliers(String pathSimBlockchainSequenceItemsJson) throws Exception {
		this.pathSimBlockchainSequenceItemsJson = pathSimBlockchainSequenceItemsJson;
		this.keyFactory = KeyFactory.getInstance(GenTransactions.encryptionAlgorithm);
		privateKeyFrom = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(BlockchainUtils.toByteArray(GenTransactions.encodedPrivateKey)));
		publicKeyFrom = keyFactory.generatePublic(new X509EncodedKeySpec(BlockchainUtils.toByteArray(GenTransactions.encodedPublicKey)));
		simDate = ZonedDateTime.now().minusYears(1);
		simdDateDaysOffset = 0;
	}

	public static void main(String[] args) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			GenSimSuppliers genSimSuppliers = new GenSimSuppliers(args[0]);
			genSimSuppliers.process();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void process() throws Exception {
		try {
			List<SupplierVo> supplierVos = GenSimSuppliers.createSupplierVos();
			// farm,rice supplier,seed supplier,fert coop,rice ABCPetFood
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
			Map<String, List<SupplierBlockchain>> mapSimBlockchainsBySourceKey = new HashMap<String, List<SupplierBlockchain>>();
			List<SimBlockchainSequenceItem> simBlockchainSequenceItems = GenTransactions.objectMapper.readValue(new File(pathSimBlockchainSequenceItemsJson),
					new TypeReference<List<SimBlockchainSequenceItem>>() {
					});

			for (SimBlockchainSequenceItem simBlockchainSequenceItem : simBlockchainSequenceItems) {
				mapSimBlockchainsBySourceKey.put(simBlockchainSequenceItem.getSupplierType(),
						genSimBlockchains(mapSupplierVos, simBlockchainSequenceItem.getDescCatSubcatItems()));
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
					List<SupplierBlockchain> supplierBlockchains = new ArrayList<SupplierBlockchain>();
					LotCanineNutrition lotCanineNutrition = new LotCanineNutrition()
							.setLotNumber(lotNumberRoot +  lotNumberSeq)
							.setIngredientNames(supplierTypes)
							.setSupplierBlockchains(supplierBlockchains);
					lotCanineNutritions.add(lotCanineNutrition);
					for( String supplierType : supplierTypes ) {
						List<SupplierBlockchain> list = mapSimBlockchainsBySourceKey.get(supplierType);
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

	private List<SupplierBlockchain> genSimBlockchains(Map<String, List<SupplierVo>> mapSupplierVos, List<DescCatSubcatItem> descCatSubcatItems)
			throws Exception {
		List<SupplierBlockchain> supplierBlockChains = new ArrayList<SupplierBlockchain>();
		for (int i = 0; i < NUM_SIM_CHAINS_PER_SOURCE; i++) {
			int sequence = 0;
			String previousHash = "0";
			List<SupplierBlock> supplierBlocks = new ArrayList<SupplierBlock>();
			for (DescCatSubcatItem descCatSubcatItem : descCatSubcatItems) {
				String key = descCatSubcatItem.getCategory() + "|" + descCatSubcatItem.getSubCategory();
				List<SupplierVo> supplierVosByKey = mapSupplierVos.get(key);
				SupplierVo supplierVoRnd = supplierVosByKey.get(random.nextInt(NUM_SIM_EACH_SUPPLIER));
				SupplierTransaction supplierTransaction = new SupplierTransaction().setDunsNumber(supplierVoRnd.getDunsNumber())
						.setSupplierName(supplierVoRnd.getSupplierName()).setSupplierCategory(supplierVoRnd.getSupplierCategory())
						.setSupplierSubCategory(supplierVoRnd.getSupplierSubCategory()).setLotNumber("TODO").setItemNumber("TODO")
						.setDescription(descCatSubcatItem.getDesc()).setQty(100).setUnits("TODO")
						.setShippedDateIso8601(simDate.plusDays(simdDateDaysOffset++).format(DateTimeFormatter.ISO_INSTANT))
						.setRcvdDateIso8601(simDate.plusDays(simdDateDaysOffset++).format(DateTimeFormatter.ISO_INSTANT));
				String transactionData = GenTransactions.objectMapper.writeValueAsString(supplierTransaction);
				PublicKey publicKeyTo = keyFactory.generatePublic(new X509EncodedKeySpec(BlockchainUtils.toByteArray(supplierVoRnd.getEncodedPublicKey())));
				SupplyChainTransaction supplyChainTransaction = new SupplyChainTransaction(publicKeyFrom, publicKeyTo, transactionData, privateKeyFrom);
				SupplierBlock supplierBlock = new SupplierBlock(previousHash, supplyChainTransaction, sequence);
				supplierBlocks.add(supplierBlock);
				sequence++;
				previousHash = supplierBlock.getHash();
			}
			System.out.println("+++++++++++++++++++++++++++++++++++");
			for (SupplierBlock supplierBlock : supplierBlocks) {
				System.out.println(supplierBlock);
			}
			supplierBlockChains.add(new SupplierBlockchain().setSupplierBlocks(supplierBlocks));
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
