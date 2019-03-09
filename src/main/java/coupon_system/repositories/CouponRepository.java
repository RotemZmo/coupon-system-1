package coupon_system.repositories;

import coupon_system.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    Coupon findById(int id);

    @Query("SELECT DISTINCT c FROM Coupon c WHERE UPPER(c.title) LIKE UPPER(?1)")
    Coupon findByTitle(String title);

    @Query("SELECT c FROM Coupon c WHERE c.id = ?1 AND company_id = ?2")
    Coupon findByIdAndCompanyId(int couponId, int companyId);

    @Query("SELECT c FROM Coupon c WHERE company_id = ?1")
    Collection<Coupon> findAllByCompanyId(int companyId);

    @Query("DELETE FROM Coupon c WHERE c.endDate < CURRENT_DATE ")
    void deleteExpiredCoupons();

}
