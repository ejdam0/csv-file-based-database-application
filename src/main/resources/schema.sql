DROP TABLE IF EXISTS users;

CREATE TABLE users (
	id int AUTO_INCREMENT PRIMARY KEY,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	birth_date varchar(10) NOT NULL,
	phone_no varchar(9) UNIQUE
);