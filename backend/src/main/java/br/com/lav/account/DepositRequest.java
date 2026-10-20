
package br.com.lav.account;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record DepositRequest(

        @NotNull(message = "O valor do depósito é obrigatório")
        @DecimalMin(
                value = "0.01",
                message = "O depósito deve ser maior que zero")
        @Digits(
                integer = 17,
                fraction = 2,
                message = "O depósito pode ter no máximo duas casas decimais")
        BigDecimal amount,

        @Size(
                max = 255,
                message = "A descrição deve ter até 255 caracteres")
        String description

) {
}
