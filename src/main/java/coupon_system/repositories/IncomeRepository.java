package coupon_system.repositories;

import coupon_system.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("SELECT i FROM Income i WHERE i.company.id = ?1")
    Collection<Income> findCompanyIncomes(long companyId);

    @Query("SELECT i FROM Income i WHERE i.customer.id = ?1")
    Collection<Income> findCustomerIncomes(long companyId);
}
