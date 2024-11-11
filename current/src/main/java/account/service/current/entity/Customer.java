package account.service.current.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private Long customerId;
    private String name;
    private String surname;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Account> accounts = new ArrayList<>();


    public Customer(Long customerId, String name, String surname) {
        this.customerId = customerId;
        this.name = name;
        this.surname = surname;
        this.accounts = new ArrayList<>();
    }


}
