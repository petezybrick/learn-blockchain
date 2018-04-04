package com.pzybrick.learnblockchain.supplychain.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pzybrick.learnblockchain.supplychain.SupplyBlockchainConfig;


public class LotCanineDao {
	private static final Logger logger = LogManager.getLogger(LotCanineDao.class);
	private static String sqlDeleteAll = "DELETE FROM lot_canine";
	private static String sqlDeleteByPk = "DELETE FROM lot_canine WHERE lot_canine_uuid=?";
	private static String sqlInsert = "INSERT INTO lot_canine (lot_canine_uuid,manufacturer_lot_number,lot_filled_date,update_ts) VALUES (?,?,?,?)";
	private static String sqlFindByPk = "SELECT lot_canine_uuid,manufacturer_lot_number,lot_filled_date,insert_ts,update_ts FROM lot_canine WHERE lot_canine_uuid=?";
	private static String sqlFindByLotNumber = "SELECT lot_canine_uuid,manufacturer_lot_number,lot_filled_date,insert_ts,update_ts FROM lot_canine WHERE manufacturer_lot_number=?";
	private static String sqlFindLotTree = 
		"select " + 
			"lc.manufacturer_lot_number as manufacturer_lot_number, " + 
			"lc.lot_filled_date as manufacturer_lot_filled_date, " + 
			"mlcsb.ingredient_sequence as ingredient_sequence, " + 
			"mlcsb.ingredient_name as ingredient_name, " + 
			"sb.block_sequence as block_sequence, " + 
			"sb.hash as hash, " + 
			"sb.previous_hash as previous_hash, " + 
			"st.supplier_lot_number as supplier_lot_number, " + 
			"s.supplier_category as supplier_category, " + 
			"s.supplier_sub_category as supplier_sub_category, " + 
			"s.supplier_name as supplier_name, " + 
			"s.duns_number as duns_number " + 
			"from lot_canine lc " + 
			"inner join map_lot_canine_supplier_blockchain mlcsb on mlcsb.lot_canine_uuid=lc.lot_canine_uuid " + 
			"inner join supplier_blockchain sbc on sbc.supplier_blockchain_uuid=mlcsb.supplier_blockchain_uuid " + 
			"inner join supplier_block sb on sb.supplier_blockchain_uuid=sbc.supplier_blockchain_uuid " + 
			"inner join supplier_block_transaction sbt on sbt.supplier_block_uuid=sb.supplier_block_uuid " + 
			"inner join supplier_transaction st on st.supplier_block_transaction_uuid=sbt.supplier_block_transaction_uuid " + 
			"inner join supplier s on s.supplier_uuid=st.supplier_uuid " + 
			"where lc.manufacturer_lot_number=? " + 
			"order by mlcsb.ingredient_sequence, sb.block_sequence";
	
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
		try ( Connection con = PooledDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlFindByPk);
			){
			con.setAutoCommit(true);
			int offset = 1;
			pstmt.setString( offset++, lotCanineVo.getLotCanineUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new LotCanineVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
		}
	}
	
	
	public static LotCanineVo findByLotNumber( String manufacturerLotNumber ) throws Exception {
		try ( Connection con = PooledDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlFindByLotNumber);
			){
			con.setAutoCommit(true);
			int offset = 1;
			pstmt.setString( offset++, manufacturerLotNumber );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new LotCanineVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
		}
	}
	
	
	public static LotTreeItem findLotTree( String manufacturerLotNumber ) throws Exception {
		try ( Connection con = PooledDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlFindLotTree);
			){
			con.setAutoCommit(true);
			int offset = 1;
			pstmt.setString( offset++, manufacturerLotNumber );
			ResultSet rs = pstmt.executeQuery();
			return buildLotTree( rs );
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
		}
	}
	
	public static LotTreeItem buildLotTree( ResultSet rs ) throws Exception {
		LotTreeItem lotTreeItem = null;
		List<LotIngredientItem> lotIngredientItems = null;
		while( rs.next() ) {
			if( lotTreeItem == null ) {
				lotIngredientItems = new ArrayList<LotIngredientItem>();
				lotTreeItem = new LotTreeItem().setManufacturerLotNumber(rs.getString("manufacturer_lot_number"))
						.setManufacturerLotFilledDate(rs.getTimestamp("manufacturer_lot_filled_date"))
						.setLotIngredientItems(lotIngredientItems);
			}
		}
		
		return lotTreeItem;
	}
}
