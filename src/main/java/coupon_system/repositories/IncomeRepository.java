package coupon_system.repositories;

import coupon_system.entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("SELECT i FROM Income i")
    Optional<Collection<Income>> findAllIncomes();

    @Query("SELECT i FROM Income i WHERE i.company.id = ?1")
    Optional<Collection<Income>> findCompanyIncomes(long companyId);

    @Query("SELECT i FROM Income i WHERE i.customer.id = ?1")
    Optional<Collection<Income>> findCustomerIncomes(long companyId);
}
