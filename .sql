CREATE TABLE currency(
    currency VARCHAR(6) PRIMARY KEY,
    currency_name_cn VARCHAR(6) NOT NULL,
    exchange_rate FLOAT NOT NULL,
    updated_date DATE NOT NULL

    )