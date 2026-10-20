
package br.com.lav.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AccountTransactionRepository
        extends JpaRepository<AccountTransaction, UUID> {

    List<AccountTransaction> findAllByAccount_IdOrderByCreatedAtDesc(
            UUID accountId);
}
