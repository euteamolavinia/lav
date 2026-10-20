
package br.com.lav.account;

import br.com.lav.customer.Customer;
import br.com.lav.customer.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.security.SecureRandom;

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
        Customer customer = customerRepository.findById(request.customerId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cliente não encontrado"
            ));

        String accountType = request.accountType() == null
            ? "CHECKING"
            : request.accountType();

        String accountNumber = generateAccountNumber();

        BankAccount account = new BankAccount(
            customer,
            accountNumber,
            accountType
        );

        return BankAccountResponse.from(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public BankAccountResponse findById(UUID id) {
        BankAccount account = accountRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Conta bancária não encontrada"
            ));

        return BankAccountResponse.from(account);
    }

    @Transactional(readOnly = true)
    public List<BankAccountResponse> findByCustomerId(UUID customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cliente não encontrado"
            );
        }

        return accountRepository.findAllByCustomer_Id(customerId)
            .stream()
            .map(BankAccountResponse::from)
            .toList();
    }

    private String generateAccountNumber() {
        for (int attempt = 0; attempt < 10; attempt++) {
            String number = String.format(
                "%010d",
                random.nextLong(1_000_000_000L, 10_000_000_000L)
            );

            if (!accountRepository.existsByAccountNumber(number)) {
                return number;
            }
        }

        throw new ResponseStatusException(
            HttpStatus.SERVICE_UNAVAILABLE,
            "Não foi possível gerar um número de conta. Tente novamente."
        );
    }
}
