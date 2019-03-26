package coupon_system.repositories;

import coupon_system.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT с FROM Company с")
    Optional<Collection<Company>> findAllCompanies();

    @Query("SELECT DISTINCT c FROM Company c WHERE UPPER(c.name) LIKE UPPER(:name)")
    Optional<Company> findByName(String name);

    @Query("SELECT DISTINCT c FROM Company c WHERE UPPER(c.name) LIKE UPPER(:name) AND c.password = :password")
    Optional<Company> login(String name, String password);

}
