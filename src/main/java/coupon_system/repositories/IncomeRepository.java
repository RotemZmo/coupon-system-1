package coupon_system.repositories;

import coupon_system.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {

    @Query("SELECT i FROM Income i WHERE company_id = ?1")
    Collection<Income> findCompanyIncomes(int companyId);

    @Query("SELECT i FROM Income i WHERE customer_id = ?1")
    Collection<Income> findCustomerIncomes(int companyId);
}
