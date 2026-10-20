
package br.com.lav.account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AccountTransactionResponse(
        UUID id,
        UUID accountId,
        UUID operationId,
        String transactionType,
        BigDecimal amount,
        BigDecimal balanceBefore,
        BigDecimal balanceAfter,
        String description,
        Instant createdAt
) {
    public static AccountTransactionResponse from(
            AccountTransaction transaction) {

        return new AccountTransactionResponse(
                transaction.getId(),
                transaction.getAccount().getId(),
                transaction.getOperationId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getBalanceBefore(),
                transaction.getBalanceAfter(),
                transaction.getDescription(),
                transaction.getCreatedAt()
        );
    }
}
