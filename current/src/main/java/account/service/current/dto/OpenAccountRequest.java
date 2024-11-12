package account.service.current.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(
        name = "OpenAccountRequest",
        description = "Schema to hold information for opening an account"
)
public class OpenAccountRequest {

    @NotNull(message = "Customer ID must not be null")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(
            description = "Customer Id to identify customer",
            example = "1"
    )
    private Long customerId;

    @NotNull(message = "Initial credit must not be null")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero(message = "Initial credit must be a positive amount")
    @Schema(
            description = "Initial credit amount",
            example = "400.00"
    )
    private Double initialCredit;
}
