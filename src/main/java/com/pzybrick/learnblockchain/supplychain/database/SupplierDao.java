package com.pzybrick.learnblockchain.supplychain.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pzybrick.learnblockchain.supplychain.SupplyBlockchainConfig;


public class SupplierDao {
	private static final Logger logger = LogManager.getLogger(SupplierDao.class);
	private static String sqlTruncate = "TRUNCATE supplier";
	private static String sqlDeleteByPk = "DELETE FROM supplier WHERE supplier_uuid=?";
	private static String sqlInsert = "INSERT INTO supplier (supplier_uuid,duns_number,supplier_name,supplier_category,supplier_sub_category,state_province,country,encoded_public_key,update_ts) VALUES (?,?,?,?,?,?,?,?,?)";
	private static String sqlFindByPk = "SELECT supplier_uuid,duns_number,supplier_name,supplier_category,supplier_sub_category,state_province,country,encoded_public_key,insert_ts,update_ts FROM supplier WHERE supplier_uuid=?";

	public static void truncate( ) throws Exception {
		try (Connection con = PooledDataSource.getInstance().getConnection();
				Statement stmt = con.createStatement() ){
			stmt.execute(sqlTruncate);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} 
	}
	
	public static void insertBatchList( List<SupplierVo> supplierVos ) throws Exception {
		//TODO: JDBC batch
		try (Connection con = PooledDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlInsert);){
			
			con.setAutoCommit(false);
			for( SupplierVo supplierVo : supplierVos ) {
				int offset = 1;
				pstmt.setString( offset++, supplierVo.getSupplierUuid() );
				pstmt.setString( offset++, supplierVo.getDunsNumber() );
				pstmt.setString( offset++, supplierVo.getSupplierName() );
				pstmt.setString( offset++, supplierVo.getSupplierCategory() );
				pstmt.setString( offset++, supplierVo.getSupplierSubCategory() );
				pstmt.setString( offset++, supplierVo.getStateProvince() );
				pstmt.setString( offset++, supplierVo.getCountry() );
				pstmt.setString( offset++, supplierVo.getEncodedPublicKey() );
				pstmt.setTimestamp( offset++, supplierVo.getUpdateTs() );
				pstmt.execute();
			}
			con.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static void insertBatchMode( Connection con, SupplierVo supplierVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, supplierVo.getSupplierUuid() );
			pstmt.setString( offset++, supplierVo.getDunsNumber() );
			pstmt.setString( offset++, supplierVo.getSupplierName() );
			pstmt.setString( offset++, supplierVo.getSupplierCategory() );
			pstmt.setString( offset++, supplierVo.getSupplierSubCategory() );
			pstmt.setString( offset++, supplierVo.getStateProvince() );
			pstmt.setString( offset++, supplierVo.getCountry() );
			pstmt.setString( offset++, supplierVo.getEncodedPublicKey() );
			pstmt.setTimestamp( offset++, supplierVo.getUpdateTs() );
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

	public static void insert( SupplyBlockchainConfig masterConfig, SupplierVo supplierVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(masterConfig).getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, supplierVo.getSupplierUuid() );
			pstmt.setString( offset++, supplierVo.getDunsNumber() );
			pstmt.setString( offset++, supplierVo.getSupplierName() );
			pstmt.setString( offset++, supplierVo.getSupplierCategory() );
			pstmt.setString( offset++, supplierVo.getSupplierSubCategory() );
			pstmt.setString( offset++, supplierVo.getStateProvince() );
			pstmt.setString( offset++, supplierVo.getCountry() );
			pstmt.setString( offset++, supplierVo.getEncodedPublicKey() );
			pstmt.setTimestamp( offset++, supplierVo.getUpdateTs() );
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

	public static void deleteByPk( SupplyBlockchainConfig masterConfig, SupplierVo supplierVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(masterConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierVo.getSupplierUuid() );
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

	public static void deleteBatchMode( Connection con, SupplierVo supplierVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierVo.getSupplierUuid() );
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

	public static SupplierVo findByPk( SupplyBlockchainConfig masterConfig, SupplierVo supplierVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(masterConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlFindByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierVo.getSupplierUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new SupplierVo(rs);
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
