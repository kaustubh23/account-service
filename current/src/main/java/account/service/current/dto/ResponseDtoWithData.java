package account.service.current.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
@Schema(name = "ResponseWithData", description = "Schema to hold response with data information")
public class ResponseDtoWithData<T> {

    @NotNull(message = "Status must not be null")
    @Schema(description = "Status code of the response")
    private String status;

    @NotNull(message = "Message must not be null")
    @Schema(description = "Message describing the status")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "Response data")
    private Optional<T> data;

    public ResponseDtoWithData(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = Optional.ofNullable(data);
    }

    public ResponseDtoWithData(String status, String message) {
        this.status = status;
        this.message = message;
        this.data = Optional.empty();
    }
}
