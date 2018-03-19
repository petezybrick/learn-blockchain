package com.pzybrick.learnblockchain.supplychain.database;

public class InitLoadConfig {

	public static void main(String[] args) {
		try {
			ConfigVo configVo = createConfigVo();
			InitLoadConfig.loadSingle( new ConfigVo("first_config", "test json 1") );
			
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
		return null;
	}

}
