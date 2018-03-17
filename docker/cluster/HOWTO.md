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
exit MySQL
	exit;
exit container
	exit
	
