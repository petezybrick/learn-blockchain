/**
 *    Copyright 2016, 2017 Peter Zybrick and others.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * 
 * @author  Pete Zybrick
 * @version 1.0.0, 2017-09
 * 
 */
package com.pzybrick.learnblockchain.supplychain;

import java.io.Serializable;

import javax.annotation.Generated;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.pzybrick.learnblockchain.supplychain.database.CassandraBaseDao;
import com.pzybrick.learnblockchain.supplychain.database.ConfigDao;


/**
 * The Class SupplyBlockchainConfig.
 */
@Generated("org.jsonschema2pojo")
public class SupplyBlockchainConfig implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2980557216488140670L;
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(SupplyBlockchainConfig.class);
	
	/** The master config json key. */
	private String supplyBlockchainConfigJsonKey;
	
	/** The contact point. */
	private String contactPoint;
	
	/** The keyspace name. */
	private String keyspaceName;
	
	/** The master config. */
	private static SupplyBlockchainConfig supplyBlockchainConfig;
	
	/** The rule svc class name. */
	@Expose
	private String ruleSvcClassName;
	
	
	/** The jdbc driver class name. */
	@Expose
	private String jdbcDriverClassName;
	
	/** The jdbc insert block size. */
	@Expose
	private Integer jdbcInsertBlockSize;
	
	/** The jdbc login. */
	@Expose
	private String jdbcLogin;
	
	/** The jdbc password. */
	@Expose
	private String jdbcPassword;
	
	/** The jdbc url. */
	@Expose
	private String jdbcUrl;	


	/**
	 * Instantiates a new master config.
	 */
	private SupplyBlockchainConfig() {
		
	}

	
	/**
	 * Gets the single instance of SupplyBlockchainConfig.
	 *
	 * @return single instance of SupplyBlockchainConfig
	 * @throws Exception the exception
	 */
	public static SupplyBlockchainConfig getInstance( ) throws Exception {
		if( SupplyBlockchainConfig.supplyBlockchainConfig == null ) throw new Exception("SupplyBlockchainConfig was never initialized");
			return SupplyBlockchainConfig.supplyBlockchainConfig;
	}

		
	/**
	 * Gets the single instance of SupplyBlockchainConfig.
	 *
	 * @param supplyBlockchainConfigJsonKey the master config json key
	 * @param contactPoint the contact point
	 * @param keyspaceName the keyspace name
	 * @return single instance of SupplyBlockchainConfig
	 * @throws Exception the exception
	 */
	public static SupplyBlockchainConfig getInstance( String supplyBlockchainConfigJsonKey, String contactPoint, String keyspaceName ) throws Exception {
		if( SupplyBlockchainConfig.supplyBlockchainConfig != null ) return SupplyBlockchainConfig.supplyBlockchainConfig;
		SupplyBlockchainConfig supplyBlockchainConfigNew = null;
		final int RETRY_MINUTES = 10;
		long maxWait = System.currentTimeMillis() + (RETRY_MINUTES * 60 * 1000);
		Exception exception = null;
		logger.info("Instantiating SupplyBlockchainConfig");
		if( keyspaceName == null ) keyspaceName = CassandraBaseDao.DEFAULT_KEYSPACE_NAME;
		
		while( true ) {
			try {
				ConfigDao.connect(contactPoint, keyspaceName);
				String rawJson = ConfigDao.findConfigJson(supplyBlockchainConfigJsonKey);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				supplyBlockchainConfigNew = gson.fromJson(rawJson, SupplyBlockchainConfig.class);
				supplyBlockchainConfigNew.setContactPoint(contactPoint);
				supplyBlockchainConfigNew.setKeyspaceName(keyspaceName);
				SupplyBlockchainConfig.supplyBlockchainConfig = supplyBlockchainConfigNew;
				return supplyBlockchainConfigNew;
			} catch(Exception e ) {
				exception = e;
				ConfigDao.disconnect();
			}
			if( System.currentTimeMillis() > maxWait ) break;
			logger.debug("retrying Cassandra connection");
			try { Thread.sleep(5000); } catch(Exception e) {}
		}
		throw exception;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public static Logger getLogger() {
		return logger;
	}


	public String getSupplyBlockchainConfigJsonKey() {
		return supplyBlockchainConfigJsonKey;
	}


	public String getContactPoint() {
		return contactPoint;
	}


	public String getKeyspaceName() {
		return keyspaceName;
	}


	public String getRuleSvcClassName() {
		return ruleSvcClassName;
	}


	public String getJdbcDriverClassName() {
		return jdbcDriverClassName;
	}


	public Integer getJdbcInsertBlockSize() {
		return jdbcInsertBlockSize;
	}


	public String getJdbcLogin() {
		return jdbcLogin;
	}


	public String getJdbcPassword() {
		return jdbcPassword;
	}


	public String getJdbcUrl() {
		return jdbcUrl;
	}


	public SupplyBlockchainConfig setSupplyBlockchainConfigJsonKey(String supplyBlockchainConfigJsonKey) {
		this.supplyBlockchainConfigJsonKey = supplyBlockchainConfigJsonKey;
		return this;
	}


	public SupplyBlockchainConfig setContactPoint(String contactPoint) {
		this.contactPoint = contactPoint;
		return this;
	}


	public SupplyBlockchainConfig setKeyspaceName(String keyspaceName) {
		this.keyspaceName = keyspaceName;
		return this;
	}


	public SupplyBlockchainConfig setRuleSvcClassName(String ruleSvcClassName) {
		this.ruleSvcClassName = ruleSvcClassName;
		return this;
	}


	public SupplyBlockchainConfig setJdbcDriverClassName(String jdbcDriverClassName) {
		this.jdbcDriverClassName = jdbcDriverClassName;
		return this;
	}


	public SupplyBlockchainConfig setJdbcInsertBlockSize(Integer jdbcInsertBlockSize) {
		this.jdbcInsertBlockSize = jdbcInsertBlockSize;
		return this;
	}


	public SupplyBlockchainConfig setJdbcLogin(String jdbcLogin) {
		this.jdbcLogin = jdbcLogin;
		return this;
	}


	public SupplyBlockchainConfig setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
		return this;
	}


	public SupplyBlockchainConfig setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
		return this;
	}


	@Override
	public String toString() {
		return "SupplyBlockchainConfig [supplyBlockchainConfigJsonKey=" + supplyBlockchainConfigJsonKey + ", contactPoint=" + contactPoint + ", keyspaceName="
				+ keyspaceName + ", ruleSvcClassName=" + ruleSvcClassName + ", jdbcDriverClassName=" + jdbcDriverClassName + ", jdbcInsertBlockSize="
				+ jdbcInsertBlockSize + ", jdbcLogin=" + jdbcLogin + ", jdbcPassword=" + jdbcPassword + ", jdbcUrl=" + jdbcUrl + "]";
	}
	

}
