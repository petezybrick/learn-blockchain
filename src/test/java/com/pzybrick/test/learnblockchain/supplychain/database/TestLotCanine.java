package com.pzybrick.test.learnblockchain.supplychain.database;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pzybrick.learnblockchain.supplychain.database.LotCanineDao;
import com.pzybrick.learnblockchain.supplychain.database.LotCanineVo;
import com.pzybrick.learnblockchain.supplychain.database.LotIngredientItem;
import com.pzybrick.learnblockchain.supplychain.database.LotSupplierBlockItem;
import com.pzybrick.learnblockchain.supplychain.database.LotTreeItem;

public class TestLotCanine extends TestBase {
	public static final String existsLotNumber = "20180108-1";
	public static final String notExistsLogNumber = "abc123";

	@BeforeClass
	public static void beforeClass() throws Exception {
		TestBase.beforeClass();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testFound() throws Exception {
		LotCanineVo lotCanineVo = LotCanineDao.findByLotNumber(existsLotNumber);
		Assert.assertNotNull(lotCanineVo);
		Assert.assertEquals(existsLotNumber, lotCanineVo.getManufacturerLotNumber());

	}

	@Test
	public void testNotFound() throws Exception {
		LotCanineVo lotCanineVo = LotCanineDao.findByLotNumber(notExistsLogNumber);
		Assert.assertNull(lotCanineVo);
	}
	

	@Test
	public void testFoundBuildLotTree() throws Exception {
		LotTreeItem lotTreeItem = LotCanineDao.findLotTree(existsLotNumber);
		dumpLotTreeItemToConsole( lotTreeItem );
		Assert.assertNotNull(lotTreeItem);
	}
	
	public static void dumpLotTreeItemToConsole( LotTreeItem lotTreeItem ) throws Exception {
		System.out.println( "Manufacturer Lot Number: " + lotTreeItem.getManufacturerLotNumber() + 
				", Manufacturer Filled Date: " + lotTreeItem.getManufacturerLotFilledDate());
		for( LotIngredientItem lotIngredientItem :  lotTreeItem.getLotIngredientItems()) {
			System.out.println("\t" + lotIngredientItem.getIngredientName());
			for(LotSupplierBlockItem lotSupplierBlockItem : lotIngredientItem.getLotSupplierBlockItems() ) {
				System.out.println("\t\t" + lotSupplierBlockItem.getSupplierName() );
				System.out.println("\t\t\tOrigin Country: " + lotSupplierBlockItem.getCountry() + ", State/Province: " + lotSupplierBlockItem.getStateProvince());
				System.out.println("\t\t\tDUNS Number: " + lotSupplierBlockItem.getDunsNumber());
				System.out.println("\t\t\tBlockChain PrevHash: " + lotSupplierBlockItem.getPreviousHash() + ", Hash: " + lotSupplierBlockItem.getHash());
				System.out.println("\t\t\tCategory: " + lotSupplierBlockItem.getSupplierCategory() + ", SubCategory: " + lotSupplierBlockItem.getSupplierSubCategory());
				System.out.println("\t\t\tSupplier Lot Number: " + lotSupplierBlockItem.getSupplierLotNumber() );
			}
		}
	}
	
	
}
