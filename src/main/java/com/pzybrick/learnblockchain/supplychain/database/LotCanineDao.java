package com.pzybrick.learnblockchain.supplychain.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.sql.PreparedStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pzybrick.learnblockchain.supplychain.SupplyBlockchainConfig;


public class LotCanineDao {
	private static final Logger logger = LogManager.getLogger(LotCanineDao.class);
	private static String sqlDeleteAll = "DELETE FROM lot_canine";
	private static String sqlDeleteByPk = "DELETE FROM lot_canine WHERE lot_canine_uuid=?";
	private static String sqlInsert = "INSERT INTO lot_canine (lot_canine_uuid,manufacturer_lot_number,lot_filled_date,update_ts) VALUES (?,?,?,?)";
	private static String sqlFindByPk = "SELECT lot_canine_uuid,manufacturer_lot_number,lot_filled_date,insert_ts,update_ts FROM lot_canine WHERE lot_canine_uuid=?";

	
	public static void deleteAll( ) throws Exception {
		try (Connection con = PooledDataSource.getInstance().getConnection();
				Statement stmt = con.createStatement();){
			con.setAutoCommit(true);
			stmt.execute( sqlDeleteAll );
		}
	}
	
	public static void insertBatchList( Connection con, List<LotCanineVo> lotCanineVos ) throws Exception {
		final int BATCH_SIZE = 1000;
		try (PreparedStatement pstmt = con.prepareStatement(sqlInsert);){
			int cnt = 0;
			for( LotCanineVo lotCanineVo : lotCanineVos ) {
				int offset = 1;
				pstmt.setString( offset++, lotCanineVo.getLotCanineUuid() );
				pstmt.setString( offset++, lotCanineVo.getManufacturerLotNumber() );
				pstmt.setTimestamp( offset++, lotCanineVo.getLotFilledDate() );
				pstmt.setTimestamp( offset++, lotCanineVo.getUpdateTs() );
				pstmt.addBatch();
				if( cnt % BATCH_SIZE == 0 ) {
					pstmt.executeBatch();
				}
				if( lotCanineVo.getMapLotCanineSupplierBlockchainVos() != null ) {
					MapLotCanineSupplierBlockchainDao.insertBatchList( con, lotCanineVo.getMapLotCanineSupplierBlockchainVos() );
				}
			}
			pstmt.executeBatch();
		}
	}

	public static void insertBatchMode( Connection con, LotCanineVo lotCanineVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, lotCanineVo.getLotCanineUuid() );
			pstmt.setString( offset++, lotCanineVo.getManufacturerLotNumber() );
			pstmt.setTimestamp( offset++, lotCanineVo.getLotFilledDate() );
			pstmt.setTimestamp( offset++, lotCanineVo.getUpdateTs() );
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

	public static void insert( SupplyBlockchainConfig supplyBlockchainConfig, LotCanineVo lotCanineVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, lotCanineVo.getLotCanineUuid() );
			pstmt.setString( offset++, lotCanineVo.getManufacturerLotNumber() );
			pstmt.setTimestamp( offset++, lotCanineVo.getLotFilledDate() );
			pstmt.setTimestamp( offset++, lotCanineVo.getUpdateTs() );
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

	public static void deleteByPk( SupplyBlockchainConfig supplyBlockchainConfig, LotCanineVo lotCanineVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, lotCanineVo.getLotCanineUuid() );
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

	public static void deleteBatchMode( Connection con, LotCanineVo lotCanineVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, lotCanineVo.getLotCanineUuid() );
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

	public static LotCanineVo findByPk( SupplyBlockchainConfig supplyBlockchainConfig, LotCanineVo lotCanineVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlFindByPk);
			int offset = 1;
			pstmt.setString( offset++, lotCanineVo.getLotCanineUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new LotCanineVo(rs);
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
