package account.service.current.service.impl;

import account.service.current.dto.AccountResponse;
import account.service.current.dto.CustomerInfoResponse;
import account.service.current.entity.Account;
import account.service.current.entity.Customer;
import account.service.current.entity.Transaction;
import account.service.current.entity.TransactionType;
import account.service.current.exception.ResourceNotFoundException;
import account.service.current.mapper.TransactionMapper;
import account.service.current.repository.AccountRepository;
import account.service.current.repository.CustomerRepository;
import account.service.current.service.AccountService;
import account.service.current.service.TransactionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    /**
     * Opens a new account for a customer with the given initial credit.
     *
     * @param customerId The customer ID to whom the account will be opened
     * @param initialCredit The initial amount to deposit into the account
     * @return AccountResponse with account details
     */
    @Override
    public AccountResponse openAccount(Long customerId, double initialCredit) {
        logger.info("Attempting to open a new account for customer with ID: {}", customerId);

        // Validate initial credit
        if (initialCredit < 0) {
            logger.error("Initial credit cannot be negative: {}", initialCredit);
            throw new IllegalArgumentException("Initial credit must be zero or a positive value.");
        }

        // Fetch the customer from the repository
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    logger.error("Customer with ID {} not found", customerId);
                    return new ResourceNotFoundException("Customer not found");
                });

        // Create and save a new account for the customer
        Account account = new Account();
        account.setBalance(initialCredit);
        account.setCustomer(customer);
        account = accountRepository.save(account);

        logger.info("Account created successfully for customer with ID: {}", customerId);

        // Create an initial transaction if initialCredit > 0
        List<Transaction> transactions = new ArrayList<>();
        if (initialCredit > 0) {
            Transaction transaction = transactionService.createTransaction(account, initialCredit, TransactionType.CREDIT);
            transactions.add(transaction);
        }

        // Populate and return the response DTO
        AccountResponse response = new AccountResponse();
        response.setAccountId(account.getAccountId());
        response.setTransactions(transactions);

        logger.info("Account response prepared for customer with ID: {}", customerId);
        return response;
    }

    /**
     * Retrieves customer information, including total balance and transaction history.
     *
     * @param customerId The customer ID for which information is requested
     * @return CustomerInfoResponse containing customer details and transactions
     */
    @Override
    public CustomerInfoResponse getCustomerInfo(Long customerId) {
        logger.info("Fetching customer info for customer with ID: {}", customerId);

        // Fetch the customer from the repository
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    logger.error("Customer with ID {} not found", customerId);
                    return new ResourceNotFoundException("Customer not found");
                });

        double totalBalance = customer.getAccounts().stream()
                .mapToDouble(Account::getBalance)
                .sum();
        logger.info("Total balance calculated for customer with ID {}: {}", customerId, totalBalance);

        List<Transaction> allTransactions = customer.getAccounts().stream()
                .flatMap(account -> account.getTransactions().stream())
                .collect(Collectors.toList());

        CustomerInfoResponse response = new CustomerInfoResponse();
        response.setName(customer.getName());
        response.setSurname(customer.getSurname());
        response.setBalance(totalBalance);
        response.setTransactions(transactionMapper.listTransactionToTransactionDTO(allTransactions));

        logger.debug("Customer info response prepared for customer with ID: {}", customerId);
        return response;
    }
}
