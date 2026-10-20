package br.com.lav.account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record BankAccountResponse(
    UUID id,
    UUID customerId,
    String accountNumber,
    String accountType,
    String status,
    BigDecimal balance,
    Instant createdAt
) {
    public static BankAccountResponse from(BankAccount account) {
        return new BankAccountResponse(
            account.getId(),
            account.getCustomer().getId(),
            account.getAccountNumber(),
            account.getAccountType(),
            account.getStatus(),
            account.getBalance(),
            account.getCreatedAt()
        );
    }
}