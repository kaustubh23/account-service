package account.service.current.controller;

import account.service.current.constant.AccountConstants;
import account.service.current.dto.*;
import account.service.current.exception.ResourceNotFoundException;
import account.service.current.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Endpoint to create a new customer and account.
     *
     * @param request The request body containing customerId and initial credit
     * @return Response with account creation status and account ID
     */
    @Operation(
            summary = "Create Account REST API",
            description = "This API creates a new account for a customer along with an initial credit."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Account created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request due to invalid input"

            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"

            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> openAccount(@Valid @RequestBody OpenAccountRequest request, BindingResult result) {
        try {

            if (result.hasErrors()) {
                List<String> errorMessages = result.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList());
                return new ResponseEntity<>(new ResponseDto("Validation Failed", String.join(", ", errorMessages)), HttpStatus.BAD_REQUEST);
            }
                // Attempt to create account and get response
            AccountResponse account = accountService.openAccount(request.getCustomerId(), request.getInitialCredit());
            // Return success response
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(AccountConstants.STATUS_201, String.format(AccountConstants.MESSAGE_201, account.getAccountId())));
        } catch (IllegalArgumentException e) {
            // Handle invalid request data
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseDto(AccountConstants.STATUS_400, "Invalid input: " + e.getMessage()));
        }     catch (ResourceNotFoundException e) {
                // Handle invalid request data
                return ResponseEntity
                        .badRequest()
                        .body(new ResponseDto(AccountConstants.STATUS_404, "Not Found: " + e.getMessage()));
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.STATUS_500, "Error while creating account: " + e.getMessage()));
        }
    }

    /**
     * Endpoint to retrieve customer info along with all associated accounts and transactions.
     *
     * @param customerId The customer ID for which to fetch the info
     * @return Response with customer information and associated transactions
     */
    @Operation(
            summary = "Get Customer Information",
            description = "This API fetches customer details including their balance and transactions."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer info retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = CustomerInfoResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request due to invalid input",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDtoWithData.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDtoWithData.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDtoWithData.class)
                    )
            )
    })
    @GetMapping("/customer/{customerId}/info")
    public ResponseEntity<ResponseDtoWithData<CustomerInfoResponse>> getCustomerInfo(@PathVariable Long customerId) {
        try {
            // Attempt to retrieve customer info
            CustomerInfoResponse response = accountService.getCustomerInfo(customerId);
            // Return success response
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDtoWithData<>(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200, response));
        } catch (IllegalArgumentException e) {
            // Handle invalid customer ID or data
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseDtoWithData<>(AccountConstants.STATUS_400, "Invalid input: " + e.getMessage() ));
        } catch (ResourceNotFoundException e) {
            // Handle customer not found
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDtoWithData<>(AccountConstants.STATUS_404, e.getMessage()));
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDtoWithData<>(AccountConstants.STATUS_500, "Error retrieving customer info: " + e.getMessage()));
        }
    }
}
