package coupon_system.repositories;

import coupon_system.entities.Coupon;
import coupon_system.enums.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("SELECT DISTINCT c FROM Coupon c WHERE UPPER(c.title) LIKE UPPER(?1)")
    Optional<Coupon> findByTitle(String title);

    @Query("SELECT с FROM Coupon с")
    Optional<Collection<Coupon>> findAllCoupons();

    @Query("SELECT c FROM Coupon c WHERE c.amount > 0")
    Optional<Collection<Coupon>> findAllAvailableCoupons();

    @Modifying
    @Transactional
    @Query("DELETE FROM Coupon c WHERE c.endDate < CURRENT_DATE ")
    void deleteExpiredCoupons();

    /**
     * Queries for coupons of the company
     */
    @Query("SELECT c FROM Coupon c WHERE c.company.id = ?1 AND c.id = ?2")
    Optional<Coupon> findCompanyCoupon(long companyId, long couponId);

    @Query("SELECT c FROM Coupon c WHERE c.company.id = ?1")
    Optional<Collection<Coupon>> findAllCompanyCoupons(long companyId);

    @Query("SELECT c FROM Coupon c WHERE c.company.id = ?1 AND c.couponType = ?2")
    Optional<Collection<Coupon>> findAllCompanyCouponsByType(long companyId, CouponType couponType);

    @Query("SELECT c FROM Coupon c WHERE c.company.id = ?1 AND c.price <= ?2")
    Optional<Collection<Coupon>> findAllCompanyCouponsByPrice(long companyId, double price);

    /**
     * Queries for coupons of the customer
     */
    @Query("SELECT coupon FROM Customer c JOIN c.coupons coupon WHERE c.id = ?1 AND coupon.id = ?2")
    Optional<Coupon> findCustomerCoupon(long customerId, long couponId);

    @Query("SELECT coupon FROM Customer c JOIN c.coupons coupon WHERE c.id = ?1")
    Optional<Collection<Coupon>> findAllCustomerCoupons(long customerId);

    @Query("SELECT coupon FROM Customer c JOIN c.coupons coupon WHERE c.id = ?1 AND coupon.couponType = ?2")
    Optional<Collection<Coupon>> findAllCustomerCouponsByType(long customerId, CouponType couponType);

    @Query("SELECT coupon FROM Customer c JOIN c.coupons coupon WHERE c.id = ?1 AND coupon.price <= ?2")
    Optional<Collection<Coupon>> findAllCustomerCouponsByPrice(long customerId, double price);

}
