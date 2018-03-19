package com.pzybrick.learnblockchain.supplychain.database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SupplyChainTransactionVo {
	private static final Logger logger = LogManager.getLogger(SupplyChainTransactionVo.class);
	private String supplyChainTransactionUuid;
	private String transactionId;
	private String encodedPublicKeyFrom;
	private String encodedPublicKeyTo;
	private String transactionData;
	private byte[] signature;
	private int transactionSequence;
	private Timestamp insertTs;
	private Timestamp updateTs;


	public SupplyChainTransactionVo() {
	}


	public SupplyChainTransactionVo(ResultSet rs) throws SQLException {
		this.supplyChainTransactionUuid = rs.getString("supply_chain_transaction_uuid");
		this.transactionId = rs.getString("transaction_id");
		this.encodedPublicKeyFrom = rs.getString("encoded_public_key_from");
		this.encodedPublicKeyTo = rs.getString("encoded_public_key_to");
		this.transactionData = rs.getString("transaction_data");
		this.signature = rs.getBytes("signature");
		this.transactionSequence = rs.getInt("transaction_sequence");
		this.insertTs = rs.getTimestamp("insert_ts");
		this.updateTs = rs.getTimestamp("update_ts");
	}


	public String getSupplyChainTransactionUuid() {
		return this.supplyChainTransactionUuid;
	}
	public String getTransactionId() {
		return this.transactionId;
	}
	public String getEncodedPublicKeyFrom() {
		return this.encodedPublicKeyFrom;
	}
	public String getEncodedPublicKeyTo() {
		return this.encodedPublicKeyTo;
	}
	public String getTransactionData() {
		return this.transactionData;
	}
	public byte[] getSignature() {
		return this.signature;
	}
	public int getTransactionSequence() {
		return this.transactionSequence;
	}
	public Timestamp getInsertTs() {
		return this.insertTs;
	}
	public Timestamp getUpdateTs() {
		return this.updateTs;
	}


	public SupplyChainTransactionVo setSupplyChainTransactionUuid( String supplyChainTransactionUuid ) {
		this.supplyChainTransactionUuid = supplyChainTransactionUuid;
		return this;
	}
	public SupplyChainTransactionVo setTransactionId( String transactionId ) {
		this.transactionId = transactionId;
		return this;
	}
	public SupplyChainTransactionVo setEncodedPublicKeyFrom( String encodedPublicKeyFrom ) {
		this.encodedPublicKeyFrom = encodedPublicKeyFrom;
		return this;
	}
	public SupplyChainTransactionVo setEncodedPublicKeyTo( String encodedPublicKeyTo ) {
		this.encodedPublicKeyTo = encodedPublicKeyTo;
		return this;
	}
	public SupplyChainTransactionVo setTransactionData( String transactionData ) {
		this.transactionData = transactionData;
		return this;
	}
	public SupplyChainTransactionVo setSignature( byte[] signature ) {
		this.signature = signature;
		return this;
	}
	public SupplyChainTransactionVo setTransactionSequence( int transactionSequence ) {
		this.transactionSequence = transactionSequence;
		return this;
	}
	public SupplyChainTransactionVo setInsertTs( Timestamp insertTs ) {
		this.insertTs = insertTs;
		return this;
	}
	public SupplyChainTransactionVo setUpdateTs( Timestamp updateTs ) {
		this.updateTs = updateTs;
		return this;
	}
}

// SupplyChainTransactionVo supplyChainTransactionVo = new SupplyChainTransactionVo()
//	 .setSupplyChainTransactionUuid("xxx")
//	 .setTransactionId("xxx")
//	 .setEncodedPublicKeyFrom("xxx")
//	 .setEncodedPublicKeyTo("xxx")
//	 .setTransactionData("xxx")
//	 .setSignature("xxx")
//	 .setTransactionSequence("xxx")
//	 .setInsertTs("xxx")
//	 .setUpdateTs("xxx")
//	 ;
