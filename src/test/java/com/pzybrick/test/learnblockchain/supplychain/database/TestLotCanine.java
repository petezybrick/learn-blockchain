package com.pzybrick.test.learnblockchain.supplychain.database;

import java.sql.ResultSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pzybrick.learnblockchain.supplychain.database.LotCanineDao;
import com.pzybrick.learnblockchain.supplychain.database.LotCanineVo;
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
		System.out.println(lotTreeItem);
		Assert.assertNotNull(lotTreeItem);
	}
	
	
}
