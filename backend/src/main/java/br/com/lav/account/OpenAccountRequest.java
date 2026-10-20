package br.com.lav.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

public record OpenAccountRequest(
    @NotNull(message = "o ID do cliente é obrigatório")
    UUID customerId,

    @Pattern(
        regexp = "CHECKING|SAVINGS",
        message = "o tipo deve ser CHECKING ou SAVINGS"
    )
    String accountType
) {}