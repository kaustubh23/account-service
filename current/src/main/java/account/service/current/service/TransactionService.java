package account.service.current.service;

import account.service.current.entity.Account;
import account.service.current.entity.Transaction;
import account.service.current.entity.TransactionType;

public interface TransactionService {

    Transaction createTransaction(Account account, double amount, TransactionType type);
}
