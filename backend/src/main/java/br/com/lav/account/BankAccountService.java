
package br.com.lav.account;

import br.com.lav.customer.Customer;
import br.com.lav.customer.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

@Service
public class BankAccountService {

    private final BankAccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final SecureRandom random = new SecureRandom();

    public BankAccountService(
            BankAccountRepository accountRepository,
            CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public BankAccountResponse open(OpenAccountRequest request) {
        Customer customer = customerRepository
                .findById(request.customerId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));

        String accountType = request.accountType() == null
                ? "CHECKING"
                : request.accountType();

        for (int attempt = 0; attempt < 10; attempt++) {
            String accountNumber = generateAccountNumber();

            if (!accountRepository.existsByAccountNumber(accountNumber)) {
                BankAccount account = new BankAccount(
                        customer,
                        accountNumber,
                        accountType);

                try {
                    return BankAccountResponse.from(
                            accountRepository.saveAndFlush(account));
                } catch (org.springframework.dao.DataIntegrityViolationException exception) {
                    if (attempt == 9) {
                        throw new ResponseStatusException(
                                HttpStatus.SERVICE_UNAVAILABLE,
                                "Não foi possível gerar um número de conta disponível");
                    }
                }
            }
        }

        throw new ResponseStatusException(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Não foi possível gerar um número de conta disponível");
    }

    @Transactional(readOnly = true)
    public BankAccountResponse findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(BankAccountResponse::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Conta bancária não encontrada"));
    }

    @Transactional(readOnly = true)
    public BankAccountResponse findById(UUID id) {
        return accountRepository.findById(id)
                .map(BankAccountResponse::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Conta bancária não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<BankAccountResponse> findByCustomerId(UUID customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Cliente não encontrado");
        }

        return accountRepository.findAllByCustomer_Id(customerId)
                .stream()
                .map(BankAccountResponse::from)
                .toList();
    }

    private String generateAccountNumber() {
        return Long.toString(
                random.nextLong(1_000_000_000L, 10_000_000_000L));
    }
}
