DROP TABLE IF EXISTS supplier;
CREATE TABLE supplier (
	supplier_uuid CHAR(36) NOT NULL PRIMARY KEY,
	duns_number char(10) not null,
	supplier_name VARCHAR(255) not null,
	supplier_category VARCHAR(255) not null,
	supplier_sub_category VARCHAR(255) not null,
	state_province VARCHAR(255) not null,
	country VARCHAR(255) not null,
	encoded_public_key VARCHAR(1024) not null,
	insert_ts timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),
	update_ts TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS supply_chain_transaction;
CREATE TABLE supply_chain_transaction (
	supply_chain_transaction_uuid CHAR(36) NOT NULL PRIMARY KEY,
	transaction_id varchar(1024) not null,
	encoded_public_key_from VARCHAR(1024) not null,
	encoded_public_key_to VARCHAR(1024) not null,
	transaction_data varchar(16000) not null,
	signature varbinary(1024) not null,
	transaction_sequence integer not null,
	insert_ts timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),
	update_ts TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS supplier_block;
CREATE TABLE supplier_block (
	supplier_block_uuid CHAR(36) NOT NULL PRIMARY KEY,
	supply_chain_transaction_uuid CHAR(36),
	hash varchar(1024) not null,
	previous_hash varchar(1024) not null,
	block_timestamp timestamp(3) not null,
	block_sequence integer not null,
	insert_ts timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),
	update_ts TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


