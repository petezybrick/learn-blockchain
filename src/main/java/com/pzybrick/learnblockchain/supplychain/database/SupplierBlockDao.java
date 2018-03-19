package com.pzybrick.learnblockchain.supplychain.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pzybrick.iote2e.common.config.MasterConfig;


public class SupplierBlockDao {
	private static final Logger logger = LogManager.getLogger(SupplierBlockDao.class);
	private static String sqlDeleteByPk = "DELETE FROM supplier_block WHERE supplier_block_uuid=?";
	private static String sqlInsert = "INSERT INTO supplier_block (supplier_block_uuid,supply_chain_transaction_uuid,hash,previous_hash,block_timestamp,block_sequence,update_ts) VALUES (?,?,?,?,?,?,?)";
	private static String sqlFindByPk = "SELECT supplier_block_uuid,supply_chain_transaction_uuid,hash,previous_hash,block_timestamp,block_sequence,insert_ts,update_ts FROM supplier_block WHERE supplier_block_uuid=?";

	public static void insertBatchMode( Connection con, SupplierBlockVo supplierBlockVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockVo.getSupplierBlockUuid() );
			pstmt.setString( offset++, supplierBlockVo.getSupplyChainTransactionUuid() );
			pstmt.setString( offset++, supplierBlockVo.getHash() );
			pstmt.setString( offset++, supplierBlockVo.getPreviousHash() );
			pstmt.setTimestamp( offset++, supplierBlockVo.getBlockTimestamp() );
			pstmt.setInt( offset++, supplierBlockVo.getBlockSequence() );
			pstmt.setTimestamp( offset++, supplierBlockVo.getUpdateTs() );
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

	public static void insert( SupplyBlockchainConfig masterConfig, SupplierBlockVo supplierBlockVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(masterConfig).getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockVo.getSupplierBlockUuid() );
			pstmt.setString( offset++, supplierBlockVo.getSupplyChainTransactionUuid() );
			pstmt.setString( offset++, supplierBlockVo.getHash() );
			pstmt.setString( offset++, supplierBlockVo.getPreviousHash() );
			pstmt.setTimestamp( offset++, supplierBlockVo.getBlockTimestamp() );
			pstmt.setInt( offset++, supplierBlockVo.getBlockSequence() );
			pstmt.setTimestamp( offset++, supplierBlockVo.getUpdateTs() );
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

	public static void deleteByPk( SupplyBlockchainConfig masterConfig, SupplierBlockVo supplierBlockVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(masterConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockVo.getSupplierBlockUuid() );
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

	public static void deleteBatchMode( Connection con, SupplierBlockVo supplierBlockVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockVo.getSupplierBlockUuid() );
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

	public static SupplierBlockVo findByPk( SupplyBlockchainConfig masterConfig, SupplierBlockVo supplierBlockVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(masterConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlFindByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockVo.getSupplierBlockUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new SupplierBlockVo(rs);
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
