
package br.com.lav.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
public class TransactionHistoryController {

    private final AccountTransactionService service;

    public TransactionHistoryController(
            AccountTransactionService service) {
        this.service = service;
    }

    @GetMapping("/{id}/transactions")
    public Page<AccountTransactionResponse> getTransactions(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A página deve ser zero ou maior e o tamanho deve estar entre 1 e 100");
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return service.getTransactions(id, pageable);
    }
}
