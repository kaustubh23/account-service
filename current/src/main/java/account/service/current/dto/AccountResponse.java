package account.service.current.dto;


import account.service.current.entity.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class AccountResponse {

    private Long accountId;
    private List<Transaction> transactions;

}
