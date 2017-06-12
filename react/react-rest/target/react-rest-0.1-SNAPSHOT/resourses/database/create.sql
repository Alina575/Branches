CREATE TABLE users (
	id	 INTEGER unique not null primary key,
	name    VARCHAR(20) unique not null,
	email VARCHAR(30) not null,
	phone VARCHAR(15) not null);