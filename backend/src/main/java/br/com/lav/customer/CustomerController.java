
package br.com.lav.customer;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(
        @Valid @RequestBody CustomerRequest request
    ) {
        CustomerResponse response = service.create(request);

        return ResponseEntity
            .created(URI.create("/api/v1/customers/" + response.id()))
            .body(response);
    }

    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    
    @GetMapping
    public java.util.List<CustomerResponse> findAll() {
    return service.findAll();
    }

}
