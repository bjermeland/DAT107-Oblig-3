DROP SCHEMA IF EXISTS oblig3 CASCADE;

CREATE SCHEMA oblig3;
SET search_path TO oblig3;

DROP TABLE IF EXISTS Ansatt;
DROP TABLE IF EXISTS Avdeling;
DROP TABLE IF EXISTS Prosjekt;
DROP TABLE IF EXISTS AnsattProsjekt;

CREATE TABLE Ansatt 
(	
	ansId SERIAL UNIQUE,
    brukernavn CHAR(4) NOT NULL UNIQUE,
	fornavn VARCHAR(255), 
	etternavn VARCHAR(255), 
	ansettelsesdato DATE, 
	stilling VARCHAR(255), 
	maanedslonn INT,
	
	CONSTRAINT AnsattPK PRIMARY KEY (ansId)
);

CREATE TABLE Avdeling (
	avdId SERIAL UNIQUE,
	navn VARCHAR(255),

	UNIQUE (AvdId),

	CONSTRAINT AvdelingPK PRIMARY KEY (avdId)
);

CREATE TABLE Prosjekt (
	proId SERIAL UNIQUE,
	navn VARCHAR(255),
	beskrivelse VARCHAR(255),
	ansatte INT,

	CONSTRAINT ProsjektPK PRIMARY KEY (proId)
);

CREATE TABLE AnsattProsjekt (
	ansId INT REFERENCES Ansatt(ansId),
	proId INT REFERENCES Prosjekt(proId),
	timer INT,
	rolle VARCHAR(40),

	CONSTRAINT AnsattProsjektPK PRIMARY KEY (ansId, proId)
);

ALTER TABLE Ansatt
	ADD FOREIGN KEY avdId INT REFERENCES Avdeling (avdId);

ALTER TABLE Avdeling 
	ADD FOREIGN KEY sjef INT REFERENCES Ansatt (ansId);

	
INSERT INTO 
   Ansatt(ansId, brukernavn, fornavn, etternavn, ansettelsesdato, stilling, maanedslonn)
VALUES
   (0, 'mb', 'Markus', 'Bjermeland', '2020-01-02', 'sjef', 100),
   (1, 'oo', 'Ola', 'Olasen', '2019-12-01', 'ikke-sjef', 1),
   (2, 'ee', 'Erik', 'Eriksen', '2020-04-01', 'oppvask', 2);
  
INSERT INTO 
   Avdeling(avdId, navn)
VALUES
   (0, 'Utviklere', 0),
   (1, 'Annet', 2);
   