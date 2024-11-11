package account.service.current.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Response", description = "Schema to hold successful response information")
@Data
public class ResponseDto {

    @NotNull(message = "Status code must not be null")
    @Schema(description = "Status code in the response")
    private String statusCode;

    @NotNull(message = "Status message must not be null")
    @Schema(description = "Status message in the response")
    private String statusMsg;


    public ResponseDto(String statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }
}
