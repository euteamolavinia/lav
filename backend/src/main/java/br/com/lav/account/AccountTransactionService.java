package br.com.lav.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountTransactionService {

    private final BankAccountRepository accountRepository;
    private final AccountTransactionRepository transactionRepository;

    public AccountTransactionService(
            BankAccountRepository accountRepository,
            AccountTransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public AccountTransactionResponse deposit(
            String accountNumber,
            DepositRequest request) {

        BankAccount account = accountRepository
                .findByAccountNumberForUpdate(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Conta bancária não encontrada"));

        if (!"ACTIVE".equals(account.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A conta não está ativa para receber depósitos");
        }

        BigDecimal amount = request.amount();

        if (amount == null
                || amount.signum() <= 0
                || amount.scale() > 2
                || amount.precision() - amount.scale() > 17) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O valor deve ser positivo, ter no máximo duas casas decimais e respeitar o limite permitido");
        }

        BigDecimal balanceBefore = account.getBalance();

        account.deposit(amount);

        BigDecimal balanceAfter = account.getBalance();

        AccountTransaction transaction = new AccountTransaction(
                account,
                UUID.randomUUID(),
                "DEPOSIT",
                amount,
                balanceBefore,
                balanceAfter,
                request.description()
        );

        AccountTransaction saved =
                transactionRepository.save(transaction);

        return AccountTransactionResponse.from(saved);
    }

    @Transactional
    public AccountTransactionResponse withdraw(
            String accountNumber,
            DepositRequest request) {

        BankAccount account = accountRepository
                .findByAccountNumberForUpdate(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Conta bancária não encontrada"));

        if (!"ACTIVE".equals(account.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A conta não está ativa para realizar saques");
        }

        BigDecimal amount = request.amount();

        if (amount == null
                || amount.signum() <= 0
                || amount.scale() > 2
                || amount.precision() - amount.scale() > 17) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O valor deve ser positivo, ter no máximo duas casas decimais e respeitar o limite permitido");
        }

        BigDecimal balanceBefore = account.getBalance();

        if (amount.compareTo(balanceBefore) > 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Saldo insuficiente para realizar o saque");
        }

        account.withdraw(amount);

        BigDecimal balanceAfter = account.getBalance();

        AccountTransaction transaction = new AccountTransaction(
                account,
                UUID.randomUUID(),
                "WITHDRAWAL",
                amount,
                balanceBefore,
                balanceAfter,
                request.description()
        );

        AccountTransaction saved =
                transactionRepository.save(transaction);

        return AccountTransactionResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public Page<AccountTransactionResponse> getTransactions(
            String accountNumber,
            Pageable pageable) {

        if (!accountRepository.existsByAccountNumber(accountNumber)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Conta bancária não encontrada");
        }

        return transactionRepository
                .findAllByAccount_Id(
                        accountRepository
                                .findByAccountNumber(accountNumber)
                                .orElseThrow()
                                .getId(),
                        pageable)
                .map(AccountTransactionResponse::from);
    }
}