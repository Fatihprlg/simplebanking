CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE account
(
    account_number VARCHAR(255)     NOT NULL,
    owner          VARCHAR(255),
    balance        DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (account_number)
);

CREATE TABLE transaction
(
    id               BIGINT           NOT NULL,
    transaction_type VARCHAR(31),
    date             TIMESTAMP WITHOUT TIME ZONE,
    amount           DOUBLE PRECISION NOT NULL,
    account_id       VARCHAR(255),
    status           VARCHAR(255),
    body             VARCHAR(255),
    bill_type        INTEGER,
    CONSTRAINT pk_transaction PRIMARY KEY (id)
);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (account_number);