package com.pzybrick.learnblockchain.supplychain.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pzybrick.iote2e.common.config.MasterConfig;


public class SupplyChainTransactionDao {
	private static final Logger logger = LogManager.getLogger(SupplyChainTransactionDao.class);
	private static String sqlDeleteByPk = "DELETE FROM supply_chain_transaction WHERE supply_chain_transaction_uuid=?";
	private static String sqlInsert = "INSERT INTO supply_chain_transaction (supply_chain_transaction_uuid,transaction_id,encoded_public_key_from,encoded_public_key_to,transaction_data,signature,transaction_sequence,update_ts) VALUES (?,?,?,?,?,?,?,?)";
	private static String sqlFindByPk = "SELECT supply_chain_transaction_uuid,transaction_id,encoded_public_key_from,encoded_public_key_to,transaction_data,signature,transaction_sequence,insert_ts,update_ts FROM supply_chain_transaction WHERE supply_chain_transaction_uuid=?";

	public static void insertBatchMode( Connection con, SupplyChainTransactionVo supplyChainTransactionVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, supplyChainTransactionVo.getSupplyChainTransactionUuid() );
			pstmt.setString( offset++, supplyChainTransactionVo.getTransactionId() );
			pstmt.setString( offset++, supplyChainTransactionVo.getEncodedPublicKeyFrom() );
			pstmt.setString( offset++, supplyChainTransactionVo.getEncodedPublicKeyTo() );
			pstmt.setString( offset++, supplyChainTransactionVo.getTransactionData() );
			pstmt.setBytes( offset++, supplyChainTransactionVo.getSignature() );
			pstmt.setInt( offset++, supplyChainTransactionVo.getTransactionSequence() );
			pstmt.setTimestamp( offset++, supplyChainTransactionVo.getUpdateTs() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				if (pstmt != null)
				pstmt.close();
			} catch (Exception e) {
				logger.warn(e);
			}
		}
	}

	public static void insert( SupplyBlockchainConfig masterConfig, SupplyChainTransactionVo supplyChainTransactionVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(masterConfig).getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, supplyChainTransactionVo.getSupplyChainTransactionUuid() );
			pstmt.setString( offset++, supplyChainTransactionVo.getTransactionId() );
			pstmt.setString( offset++, supplyChainTransactionVo.getEncodedPublicKeyFrom() );
			pstmt.setString( offset++, supplyChainTransactionVo.getEncodedPublicKeyTo() );
			pstmt.setString( offset++, supplyChainTransactionVo.getTransactionData() );
			pstmt.setBytes( offset++, supplyChainTransactionVo.getSignature() );
			pstmt.setInt( offset++, supplyChainTransactionVo.getTransactionSequence() );
			pstmt.setTimestamp( offset++, supplyChainTransactionVo.getUpdateTs() );
			pstmt.execute();
			con.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if( con != null ) {
				try {
					con.rollback();
				} catch(Exception erb ) {
					logger.warn(e.getMessage(), e);
				}
			}
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				logger.warn(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception exCon) {
				logger.warn(exCon.getMessage());
			}
		}
	}

	public static void deleteByPk( SupplyBlockchainConfig masterConfig, SupplyChainTransactionVo supplyChainTransactionVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(masterConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplyChainTransactionVo.getSupplyChainTransactionUuid() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if( con != null ) {
				try {
					con.rollback();
				} catch(Exception erb ) {
					logger.warn(e.getMessage(), e);
				}
			}
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				logger.warn(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception exCon) {
				logger.warn(exCon.getMessage());
			}
		}
	}

	public static void deleteBatchMode( Connection con, SupplyChainTransactionVo supplyChainTransactionVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplyChainTransactionVo.getSupplyChainTransactionUuid() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				logger.warn(e);
			}
		}
	}

	public static SupplyChainTransactionVo findByPk( SupplyBlockchainConfig masterConfig, SupplyChainTransactionVo supplyChainTransactionVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(masterConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlFindByPk);
			int offset = 1;
			pstmt.setString( offset++, supplyChainTransactionVo.getSupplyChainTransactionUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new SupplyChainTransactionVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				logger.warn(e);
			}
		}
	}
}
