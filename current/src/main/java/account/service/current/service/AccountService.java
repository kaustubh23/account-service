package account.service.current.service;

import account.service.current.dto.AccountResponse;
import account.service.current.dto.CustomerInfoResponse;

public interface AccountService {

    AccountResponse openAccount(Long customerId, double initialCredit);
    CustomerInfoResponse getCustomerInfo(Long customerId);
}
