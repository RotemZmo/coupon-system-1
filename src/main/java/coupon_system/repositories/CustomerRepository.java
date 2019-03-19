package coupon_system.repositories;

import coupon_system.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT DISTINCT c FROM Customer c WHERE UPPER(c.name) LIKE UPPER(?1)")
    Customer findByName(String name);

    @Query("SELECT DISTINCT c FROM Customer c WHERE UPPER(c.name) LIKE UPPER(?1) AND  c.password = ?2")
    Customer login(String name, String password);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO customer_coupon (customer_id, coupon_id) VALUES (?1, ?2)", nativeQuery = true)
    void purchaseCoupon(long customerId, long couponId);

}
