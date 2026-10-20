ALTER TABLE account_transactions
ADD COLUMN balance_before NUMERIC(19, 2);

UPDATE account_transactions t
SET balance_before = CASE
    WHEN t.transaction_type IN ('DEPOSIT', 'TRANSFER_IN')
        THEN t.balance_after - t.amount
    WHEN t.transaction_type IN ('WITHDRAWAL', 'TRANSFER_OUT')
        THEN t.balance_after + t.amount
END;

ALTER TABLE account_transactions
ALTER COLUMN balance_before SET NOT NULL;

ALTER TABLE account_transactions
ADD CONSTRAINT account_transactions_balance_before_check
CHECK (balance_before >= 0);