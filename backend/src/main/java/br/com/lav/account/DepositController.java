package br.com.lav.account;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class DepositController {

    private final AccountTransactionService service;

    public DepositController(AccountTransactionService service) {
        this.service = service;
    }

    @PostMapping("/{accountNumber}/deposits")
    public ResponseEntity<AccountTransactionResponse> deposit(
            @PathVariable String accountNumber,
            @Valid @RequestBody DepositRequest request) {

        return ResponseEntity.ok(
                service.deposit(accountNumber, request));
    }

    @PostMapping("/{accountNumber}/withdrawals")
    public ResponseEntity<AccountTransactionResponse> withdraw(
            @PathVariable String accountNumber,
            @Valid @RequestBody DepositRequest request) {

        return ResponseEntity.ok(
                service.withdraw(accountNumber, request));
    }
}