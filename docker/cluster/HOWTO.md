Bring up MySQL the first time
	docker-compose up
Exec into the container
	docker exec -it blockchain-mysql /bin/bash
bring up mysql command line
	mysql -p
authorize root from any address
	grant all privileges on *.* to 'ROOT'@'%' identified by 'Password*8' with grant option;
	FLUSH PRIVILEGES;
create new user supplier/Password*8, authorize from any address
	create user 'supplier'@'localhost' identified by 'Password*8';
	grant all privileges on *.* to 'supplier'@'localhost' identified by 'Password*8' with grant option;
	create user 'supplier'@'%' identified by 'Password*8';
	grant all privileges on *.* to 'supplier'@'%' identified by 'Password*8' with grant option;
	FLUSH PRIVILEGES;
create new database, db_supplier
	create database db_supplier;
	use db_supplier;
	
Create this stored proc, will make it easier to drop fk's

DROP PROCEDURE IF EXISTS PROC_DROP_FOREIGN_KEY;
DELIMITER $$
CREATE PROCEDURE PROC_DROP_FOREIGN_KEY(IN tableName VARCHAR(64), IN constraintName VARCHAR(64))
BEGIN
    IF EXISTS(
        SELECT * FROM information_schema.table_constraints
        WHERE 
            table_schema    = DATABASE()     AND
            table_name      = tableName      AND
            constraint_name = constraintName AND
            constraint_type = 'FOREIGN KEY')
    THEN
        SET @query = CONCAT('ALTER TABLE ', tableName, ' DROP FOREIGN KEY ', constraintName, ';');
        PREPARE stmt FROM @query; 
        EXECUTE stmt; 
        DEALLOCATE PREPARE stmt; 
    END IF; 
END$$
DELIMITER ;

exit MySQL
	exit;
exit container
	exit