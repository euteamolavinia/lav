
package br.com.lav.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountTransactionRepository
        extends JpaRepository<AccountTransaction, UUID> {

    Page<AccountTransaction> findAllByAccount_Id(
            UUID accountId,
            Pageable pageable
    );
}
