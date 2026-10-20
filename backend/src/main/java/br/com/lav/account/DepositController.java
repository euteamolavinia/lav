
package br.com.lav.account;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
public class DepositController {

    private final AccountTransactionService service;

    public DepositController(AccountTransactionService service) {
        this.service = service;
    }

    @PostMapping("/{id}/deposits")
    public ResponseEntity<AccountTransactionResponse> deposit(
            @PathVariable UUID id,
            @Valid @RequestBody DepositRequest request) {

        return ResponseEntity.ok(
                service.deposit(id, request));
    }
}
