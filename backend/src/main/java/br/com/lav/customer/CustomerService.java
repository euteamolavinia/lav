
package br.com.lav.customer;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CustomerResponse create(CustomerRequest request) {
        String email = request.email().trim();

        if (repository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Este e-mail já está cadastrado"
            );
        }

        Customer customer = new Customer(
            request.fullName().trim(),
            email
        );

        return CustomerResponse.from(repository.save(customer));
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(UUID id) {
        Customer customer = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cliente não encontrado"
            ));

        return CustomerResponse.from(customer);
    }

    
        @Transactional(readOnly = true)
    public java.util.List<CustomerResponse> findAll() {
    return repository.findAll()
        .stream()
        .map(CustomerResponse::from)
        .toList();
    }

}
