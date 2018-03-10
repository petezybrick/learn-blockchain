package com.pzybrick.learnblockchain.supplychain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenSimSuppliers {
	private static final Logger logger = LogManager.getLogger(GenSimSuppliers.class);
	private Random random = new Random();

	public static void main(String[] args) {
		try {
			GenSimSuppliers genSimSuppliers = new GenSimSuppliers();
			genSimSuppliers.process();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	
	public void process() throws Exception {
		final int numIterations = 30;
		final String[] triples = { "Brewers Rice Farms, LLC", "farm", "rice", "Chicken Meal Farms, LLC", "farm",
				"chicken", "Wheat Farms, LLC", "farm", "wheat", "Corn Gluten Farms, LLC", "farm", "corn",
				"Oat Groats Farms, LLC", "farm", "oat", "Fish Oil Farms, LLC", "farm", "fish",
				"Beet Pulp Farms, LLC", "farm", "beet", };

		// TODO: add seed, fertilizer, fish feed and chick suppliers
		char prefChar = 'A';
		int dunsNumber = 1;
		List<SupplierVo> supplierVos = new ArrayList<SupplierVo>();
		for (int i=0; i < numIterations; i++) {
			for( int j=0 ; j<triples.length ; j+=3 ) {
				String supplierName = prefChar + String.valueOf(i + 1) + " " + triples[j];
				SupplierVo supplierVo = new SupplierVo().setSupplierUuid(UUID.randomUUID().toString())
						.setDunsNumber(String.format("%09d", dunsNumber++)).setSupplierName(supplierName)
						.setSupplierCategory(triples[j + 1]).setSupplierSubCategory(triples[j + 2])
						.setStateProvince(randomStateProvince()).setCountry("US");
				System.out.println(supplierVo);
				supplierVos.add(supplierVo);
			}
			if( prefChar != 'Z' ) prefChar++;
			else prefChar = 'A';
		}
	}
	
	
	private String randomStateProvince() {
		final String[] abbrevs = {"AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};
		return abbrevs[ random.nextInt(49) ];
	}
}
