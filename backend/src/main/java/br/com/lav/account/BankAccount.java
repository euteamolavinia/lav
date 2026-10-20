
package br.com.lav.account;

import br.com.lav.customer.Customer;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Column(name = "account_type", nullable = false, length = 20)
    private String accountType;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected BankAccount() {
    }

    public BankAccount(
            Customer customer,
            String accountNumber,
            String accountType) {
        this.customer = customer;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.status = "ACTIVE";
        this.balance = BigDecimal.ZERO.setScale(2);
    }

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public UUID getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
