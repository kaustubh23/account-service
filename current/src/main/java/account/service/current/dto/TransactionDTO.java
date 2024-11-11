package account.service.current.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "TransactionDTO", description = "Schema to hold Transaction details")
public class TransactionDTO {

    @Schema(description = "Unique identifier for the transaction", example = "101")
    private Long transactionId;

    @Schema(description = "Transaction amount", example = "200.00")
    private double amount;

    @Schema(description = "Timestamp of the transaction", example = "2024-11-11T10:15:30")
    private LocalDateTime timestamp;

    @Schema(description = "Type of transaction (CREDIT/DEBIT)", example = "CREDIT")
    private String transactionType;

    @Schema(description = "Account associated with the transaction")
    private AccountDTO account;
}
