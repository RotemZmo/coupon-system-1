package coupon_system.repositories;

import coupon_system.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findById(int id);

    @Query("SELECT DISTINCT c FROM Customer c WHERE UPPER(c.name) LIKE UPPER(?1)")
    Customer findByName(String name);

}
