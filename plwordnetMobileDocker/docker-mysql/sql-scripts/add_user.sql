CREATE DATABASE IF NOT EXISTS wordnet;
USE wordnet;
CREATE USER 'wordnet_android'@'%' IDENTIFIED BY 'a54b3fkjxdtasdf3';
USE wordnet;
GRANT SELECT ON wordnet.* TO 'wordnet_android'@'%';
CREATE TABLE wordnet.db_change_records (change_id INT AUTO_INCREMENT,
date DATE,
table_name VARCHAR(255),
change_type VARCHAR(10),
record_id INT,
record_id2 INT,
PRIMARY KEY(change_id));