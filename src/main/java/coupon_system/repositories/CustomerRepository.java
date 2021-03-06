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

    @Query("SELECT DISTINCT c FROM Customer c WHERE UPPER(c.name) LIKE UPPER(:name)")
    Optional<Customer> findByName(String name);

    @Query("SELECT DISTINCT c FROM Customer c WHERE UPPER(c.email) LIKE UPPER(:email)")
    Optional<Customer> findByEmail(String email);

    @Query("SELECT DISTINCT c FROM Customer c WHERE UPPER(c.name) LIKE UPPER(:name) AND  c.password = :password")
    Optional<Customer> findByNameAndPassword(String name, String password);

}
