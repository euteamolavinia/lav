CREATE TABLE customers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(254) UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT customers_status_check
        CHECK (status IN ('ACTIVE', 'INACTIVE'))
);

CREATE TABLE bank_accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id UUID NOT NULL,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type VARCHAR(20) NOT NULL DEFAULT 'CHECKING',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    balance NUMERIC(19, 2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT bank_accounts_customer_fk
        FOREIGN KEY (customer_id)
        REFERENCES customers(id),

    CONSTRAINT bank_accounts_type_check
        CHECK (account_type IN ('CHECKING', 'SAVINGS')),

    CONSTRAINT bank_accounts_status_check
        CHECK (status IN ('ACTIVE', 'BLOCKED', 'CLOSED')),

    CONSTRAINT bank_accounts_balance_check
        CHECK (balance >= 0)
);

CREATE INDEX idx_bank_accounts_customer_id
    ON bank_accounts(customer_id);