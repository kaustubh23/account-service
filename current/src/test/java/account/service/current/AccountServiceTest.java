package account.service.current;

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
import account.service.current.service.impl.AccountServiceImpl;
import account.service.current.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void openAccount_whenInitialCreditIsPositive() {
        Long customerId = 1L;
        double initialCredit = 100.0;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        Account account = new Account();
        account.setAccountId(1L);
        account.setBalance(initialCredit);
        account.setCustomer(customer);

        Transaction transaction = new Transaction();
        transaction.setAmount(initialCredit);
        transaction.setTransactionType(TransactionType.CREDIT);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(transactionService.createTransaction(any(Account.class), eq(initialCredit), eq(TransactionType.CREDIT)))
                .thenReturn(transaction);

        AccountResponse response = accountService.openAccount(customerId, initialCredit);

        assertEquals(account.getAccountId(), response.getAccountId());
        assertEquals(1, response.getTransactions().size());
        verify(customerRepository).findById(customerId);
        verify(accountRepository).save(any(Account.class));
        verify(transactionService).createTransaction(any(Account.class), eq(initialCredit), eq(TransactionType.CREDIT));
    }

    @Test
    void getCustomerInfo_whenCustomerExists() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setName("Kaustubh");
        customer.setSurname("Sharma");

        Account account = new Account();
        account.setBalance(100.0);
        account.setTransactions(Collections.emptyList());

        customer.setAccounts(Collections.singletonList(account));

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(transactionMapper.listTransactionToTransactionDTO(anyList())).thenReturn(Collections.emptyList());

        CustomerInfoResponse response = accountService.getCustomerInfo(customerId);

        assertEquals("Kaustubh", response.getName());
        assertEquals("Sharma", response.getSurname());
        assertEquals(100.0, response.getBalance());
    }

    @Test
    void getCustomerInfo_whenCustomerDoesNotExist() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> accountService.getCustomerInfo(customerId));
    }
}