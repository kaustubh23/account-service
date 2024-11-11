package account.service.current.repository;

import account.service.current.entity.Account;
import account.service.current.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Find all accounts for a given customer
    List<Account> findByCustomer(Customer customer);

    // Alternatively, find accounts by customer ID
    List<Account> findByCustomer_CustomerId(Long customerId);
}
