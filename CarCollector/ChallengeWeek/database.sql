DROP DATABASE IF EXISTS ce29x_vd18143;
CREATE DATABASE ce29x_vd18143;
USE ce29x_vd18143;

CREATE TABLE manufacturer(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(30),
	PRIMARY KEY(id)
);
ALTER TABLE manufacturer AUTO_INCREMENT=1; 

CREATE TABLE owner(
	id INT NOT NULL AUTO_INCREMENT,
	firstName VARCHAR(30),
	lastName VARCHAR(30),
	postCode VARCHAR(30),
	PRIMARY KEY(id)
);
ALTER TABLE owner AUTO_INCREMENT=100; 

CREATE TABLE car(
	id INT NOT NULL AUTO_INCREMENT,
	manufacturerID INT NOT NULL ,
	ownerID INT NOT NULL ,
	model VARCHAR(30),
	yearBuilt INT,
	price INT,
	plateNumber VARCHAR(10) ,
	PRIMARY KEY(id),
	FOREIGN KEY(manufacturerID) REFERENCES manufacturer(id),
	FOREIGN KEY(ownerID) REFERENCES owner(id)
);
ALTER TABLE car AUTO_INCREMENT=1000; 


INSERT INTO manufacturer (name)
VALUES ('Ford');

INSERT INTO manufacturer (name)
VALUES ('Fiat');

INSERT INTO manufacturer (name)
VALUES ('Audi');


INSERT INTO owner(firstName,lastName,postCode) VALUES(
	"Tom",
	"Thomson",
	"CO3 PQR"
);

INSERT INTO owner(firstName,lastName,postCode)  VALUES(
	"Bob",
	"Bobson",
	"CO2 PYR"
);

INSERT INTO owner(firstName,lastName,postCode)  VALUES(
	"Mika",
	"Mikason",
	"CO9 PQH"
);

INSERT INTO car(manufacturerID,ownerID,model,yearBuilt,price,plateNumber) VALUES(
	1,
	101,
	"Contour",
	2000,
	16920.00,
	"AB 23 CVE"
);




INSERT INTO car (manufacturerID,ownerID,model,yearBuilt,price,plateNumber) VALUES(
	2,
	102,
	"Expedition",
	2000,
	29845.00,
	"ZB 33 YTY"
);














