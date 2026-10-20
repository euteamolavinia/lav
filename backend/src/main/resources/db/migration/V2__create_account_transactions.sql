
CREATE TABLE account_transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    account_id UUID NOT NULL,
    operation_id UUID NOT NULL,

    transaction_type VARCHAR(20) NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    balance_after NUMERIC(19, 2) NOT NULL,

    description VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT account_transactions_account_fk
        FOREIGN KEY (account_id)
        REFERENCES bank_accounts(id),

    CONSTRAINT account_transactions_type_check
        CHECK (
            transaction_type IN (
                'DEPOSIT',
                'WITHDRAWAL',
                'TRANSFER_IN',
                'TRANSFER_OUT'
            )
        ),

    CONSTRAINT account_transactions_amount_check
        CHECK (amount > 0),

    CONSTRAINT account_transactions_balance_check
        CHECK (balance_after >= 0)
);

CREATE INDEX idx_account_transactions_account_id
    ON account_transactions(account_id);

CREATE INDEX idx_account_transactions_operation_id
    ON account_transactions(operation_id);

CREATE INDEX idx_account_transactions_created_at
    ON account_transactions(created_at);
