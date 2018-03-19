package com.pzybrick.learnblockchain.supplychain.database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SupplierBlockVo {
	private static final Logger logger = LogManager.getLogger(SupplierBlockVo.class);
	private String supplierBlockUuid;
	private String supplyChainTransactionUuid;
	private String hash;
	private String previousHash;
	private Timestamp blockTimestamp;
	private int blockSequence;
	private Timestamp insertTs;
	private Timestamp updateTs;


	public SupplierBlockVo() {
	}


	public SupplierBlockVo(ResultSet rs) throws SQLException {
		this.supplierBlockUuid = rs.getString("supplier_block_uuid");
		this.supplyChainTransactionUuid = rs.getString("supply_chain_transaction_uuid");
		this.hash = rs.getString("hash");
		this.previousHash = rs.getString("previous_hash");
		this.blockTimestamp = rs.getTimestamp("block_timestamp");
		this.blockSequence = rs.getInt("block_sequence");
		this.insertTs = rs.getTimestamp("insert_ts");
		this.updateTs = rs.getTimestamp("update_ts");
	}


	public String getSupplierBlockUuid() {
		return this.supplierBlockUuid;
	}
	public String getSupplyChainTransactionUuid() {
		return this.supplyChainTransactionUuid;
	}
	public String getHash() {
		return this.hash;
	}
	public String getPreviousHash() {
		return this.previousHash;
	}
	public Timestamp getBlockTimestamp() {
		return this.blockTimestamp;
	}
	public int getBlockSequence() {
		return this.blockSequence;
	}
	public Timestamp getInsertTs() {
		return this.insertTs;
	}
	public Timestamp getUpdateTs() {
		return this.updateTs;
	}


	public SupplierBlockVo setSupplierBlockUuid( String supplierBlockUuid ) {
		this.supplierBlockUuid = supplierBlockUuid;
		return this;
	}
	public SupplierBlockVo setSupplyChainTransactionUuid( String supplyChainTransactionUuid ) {
		this.supplyChainTransactionUuid = supplyChainTransactionUuid;
		return this;
	}
	public SupplierBlockVo setHash( String hash ) {
		this.hash = hash;
		return this;
	}
	public SupplierBlockVo setPreviousHash( String previousHash ) {
		this.previousHash = previousHash;
		return this;
	}
	public SupplierBlockVo setBlockTimestamp( Timestamp blockTimestamp ) {
		this.blockTimestamp = blockTimestamp;
		return this;
	}
	public SupplierBlockVo setBlockSequence( int blockSequence ) {
		this.blockSequence = blockSequence;
		return this;
	}
	public SupplierBlockVo setInsertTs( Timestamp insertTs ) {
		this.insertTs = insertTs;
		return this;
	}
	public SupplierBlockVo setUpdateTs( Timestamp updateTs ) {
		this.updateTs = updateTs;
		return this;
	}
}

// SupplierBlockVo supplierBlockVo = new SupplierBlockVo()
//	 .setSupplierBlockUuid("xxx")
//	 .setSupplyChainTransactionUuid("xxx")
//	 .setHash("xxx")
//	 .setPreviousHash("xxx")
//	 .setBlockTimestamp("xxx")
//	 .setBlockSequence("xxx")
//	 .setInsertTs("xxx")
//	 .setUpdateTs("xxx")
//	 ;
