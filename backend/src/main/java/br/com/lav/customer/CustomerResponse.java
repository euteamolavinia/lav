
package br.com.lav.customer;

import java.time.Instant;
import java.util.UUID;

public record CustomerResponse(
    UUID id,
    String fullName,
    String email,
    String status,
    Instant createdAt
) {
    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
            customer.getId(),
            customer.getFullName(),
            customer.getEmail(),
            customer.getStatus(),
            customer.getCreatedAt()
        );
    }
}
