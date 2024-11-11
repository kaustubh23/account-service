package account.service.current.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class CustomerInfoResponse {

    private String name;
    private String surname;
    private Double balance;
    private List<TransactionDTO> transactions;
}
