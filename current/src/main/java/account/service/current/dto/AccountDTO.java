package account.service.current.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "AccountDTO", description = "Schema to hold Account information")
public class AccountDTO {

    @Schema(description = "Unique identifier for the account", example = "1")
    private Long accountId;

    @Schema(description = "Current balance of the account", example = "500.00")
    private double balance;
}
