package coupon_system.repositories;

import coupon_system.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findById(long id);

    @Query("SELECT DISTINCT c FROM Company c WHERE UPPER(c.name) LIKE UPPER(?1)")
    Company findByName(String name);

    @Query("SELECT DISTINCT c FROM Company c WHERE UPPER(c.name) LIKE UPPER(?1) AND c.password = ?2")
    Company login(String name, String password);

}
