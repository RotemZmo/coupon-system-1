package coupon_system.repositories;

import coupon_system.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT с FROM Customer с")
    Optional<Collection<Customer>> findAllCustomers();

    @Query("SELECT DISTINCT c FROM Customer c WHERE UPPER(c.name) LIKE UPPER(?1)")
    Optional<Customer> findByName(String name);

    @Query("SELECT DISTINCT c FROM Customer c WHERE UPPER(c.name) LIKE UPPER(?1) AND  c.password = ?2")
    Optional<Customer> login(String name, String password);

}
