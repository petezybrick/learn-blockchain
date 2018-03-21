package com.pzybrick.learnblockchain.supplychain.database;

import com.pzybrick.learnblockchain.supplychain.BlockchainUtils;
import com.pzybrick.learnblockchain.supplychain.SupplyBlockchainConfig;

public class InitLoadConfig {

	public static void main(String[] args) {
		try {
			ConfigVo configVo = createConfigVo();
			InitLoadConfig.loadSingle( configVo );
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}
	
	public static void loadSingle(ConfigVo configVo) throws Exception {
		try {
			String keyspaceName = "supply_blockchain";
			CassandraBaseDao.connect("127.0.0.1", keyspaceName);
			ConfigDao.dropKeyspace(keyspaceName);
			ConfigDao.createKeyspace(keyspaceName, "SimpleStrategy", 3);			
			ConfigDao.useKeyspace(keyspaceName);
			ConfigDao.dropTable();
			ConfigDao.createTable();
			ConfigDao.insertConfig(configVo);
		} catch( Exception e ) {
			throw e;
		} finally {
			ConfigDao.disconnect();
		}
	}

	public static ConfigVo createConfigVo() throws Exception {
		SupplyBlockchainConfig supplyBlockchainConfig = new SupplyBlockchainConfig()
				.setContactPoint("127.0.0.1")
				.setKeyspaceName("supply_blockchain")
				.setJdbcDriverClassName("com.mysql.jdbc.Driver")
				.setJdbcInsertBlockSize(1000)
				.setJdbcLogin("supplier")
				.setJdbcPassword("Password*8")
				.setJdbcUrl("jdbc:mysql://localhost:3307/db_supplier")
				.setSupplyBlockchainConfigJsonKey("dev");
        String rawJson = BlockchainUtils.objectMapper.writeValueAsString(supplyBlockchainConfig);

		ConfigVo configVo = new ConfigVo("dev", rawJson );
		return configVo;
	}

}
