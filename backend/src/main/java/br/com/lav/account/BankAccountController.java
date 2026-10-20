
package br.com.lav.account;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
public class BankAccountController {

    private final BankAccountService service;

    public BankAccountController(BankAccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BankAccountResponse> open(
            @Valid @RequestBody OpenAccountRequest request) {

        BankAccountResponse response = service.open(request);

        return ResponseEntity
                .created(URI.create(
                        "/api/v1/accounts/" + response.accountNumber()))
                .body(response);
    }

    @GetMapping("/{accountNumber}")
    public BankAccountResponse findByAccountNumber(
            @PathVariable String accountNumber) {
        return service.findByAccountNumber(accountNumber);
    }

    @GetMapping("/id/{id}")
    public BankAccountResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<BankAccountResponse> findByCustomerId(
            @PathVariable UUID customerId) {
        return service.findByCustomerId(customerId);
    }
}
