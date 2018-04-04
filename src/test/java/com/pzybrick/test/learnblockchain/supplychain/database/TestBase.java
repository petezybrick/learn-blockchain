package com.pzybrick.test.learnblockchain.supplychain.database;

import java.security.Security;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.pzybrick.learnblockchain.supplychain.SupplyBlockchainConfig;
import com.pzybrick.learnblockchain.supplychain.database.CassandraBaseDao;
import com.pzybrick.learnblockchain.supplychain.database.PooledDataSource;

public class TestBase {

	@BeforeClass
	public static void beforeClass() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		SupplyBlockchainConfig supplyBlockchainConfig = 
				SupplyBlockchainConfig.getInstance( System.getenv("ENV"), System.getenv("CONTACT_POINT"),System.getenv("KEYSPACE_NAME") );
		PooledDataSource.getInstance(supplyBlockchainConfig);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		CassandraBaseDao.disconnect();
	}


}
