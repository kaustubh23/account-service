package account.service.current.service.impl;

import account.service.current.entity.Account;
import account.service.current.entity.Transaction;
import account.service.current.entity.TransactionType;
import account.service.current.repository.TransactionRepository;
import account.service.current.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl  implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class); // Logger initialization

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Creates a new transaction and updates the account balance accordingly.
     *
     * @param account The account to which the transaction is associated
     * @param amount The transaction amount
     * @param type The type of the transaction (credit or debit)
     * @return The created transaction object
     */
    @Override
    public Transaction createTransaction(Account account, double amount, TransactionType type) {
        logger.info("Creating transaction for account ID: {}. Amount: {}, Type: {}", account.getAccountId(), amount, type);

        // Create a new transaction object and set its properties
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setTimestamp(LocalDateTime.now()); // Set the current timestamp

        // Log the transaction creation
        logger.debug("Transaction details: Account ID: {}, Amount: {}, Type: {}, Timestamp: {}",
                account.getAccountId(), amount, type, transaction.getTimestamp());

        // Save the transaction in the repository
        transaction = transactionRepository.save(transaction);
        logger.info("Transaction saved with ID: {}", transaction.getTransactionId());

        // Update the account balance based on the transaction type
        double updatedBalance = account.getBalance() + (type == TransactionType.CREDIT ? amount : -amount);
        account.setBalance(updatedBalance);

        // Log the updated account balance
        logger.info("Account ID: {} balance updated to: {}", account.getAccountId(), updatedBalance);

        return transaction;
    }
}
