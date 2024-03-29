-- +migrate Up

CREATE TABLE category (
    cID    INTEGER PRIMARY KEY,
    cName  VARCHAR(20) UNIQUE NOT NULL
) CHARACTER SET 'latin1' COLLATE 'latin1_general_cs';

CREATE TABLE manufacturer (
    mID             INTEGER PRIMARY KEY,
    mName           VARCHAR(20) NOT NULL,
    mAddress        VARCHAR(50) NOT NULL,
    mPhoneNumber    INTEGER     NOT NULL
) CHARACTER SET 'latin1' COLLATE 'latin1_general_cs';

CREATE TABLE part (
    pID                 INTEGER PRIMARY KEY,
    pName               VARCHAR(20) NOT NULL,
    pPrice              INTEGER     NOT NULL,
    mID                 INTEGER     NOT NULL,
    cID                 INTEGER     NOT NULL,
    pWarrantyPeriod     INTEGER     NOT NULL,
    pAvailableQuantity  INTEGER     NOT NULL,
    
    FOREIGN KEY (mID)
        REFERENCES manufacturer (mID) ON DELETE CASCADE,
    FOREIGN KEY (cID)
        REFERENCES category (cID) ON DELETE CASCADE
) CHARACTER SET 'latin1' COLLATE 'latin1_general_cs';

CREATE TABLE salesperson (
    sID             INTEGER PRIMARY KEY,
    sName           VARCHAR(20) NOT NULL,
    sAddress        VARCHAR(50) NOT NULL,
    sPhoneNumber    INTEGER     NOT NULL,
    sExperience     INTEGER     NOT NULL
) CHARACTER SET 'latin1' COLLATE 'latin1_general_cs';

CREATE TABLE transaction (
    tID    INTEGER PRIMARY KEY,
    pID    INTEGER  NOT NULL,
    sID    INTEGER  NOT NULL,
    tDate  DATE NOT NULL,

    FOREIGN KEY(pID)
        REFERENCES part(pID) ON DELETE CASCADE,
    FOREIGN KEY(sID)
        REFERENCES salesperson(sID) ON DELETE CASCADE
) CHARACTER SET 'latin1' COLLATE 'latin1_general_cs';

-- +migrate Down
DROP TABLE transaction;
DROP TABLE salesperson;
DROP TABLE part;
DROP TABLE manufacturer;
DROP TABLE category;
