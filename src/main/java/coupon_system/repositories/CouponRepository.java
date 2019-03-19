package coupon_system.repositories;

import coupon_system.entities.Coupon;
import coupon_system.enums.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Coupon findById(long id);

    @Query("SELECT DISTINCT c FROM Coupon c WHERE UPPER(c.title) LIKE UPPER(?1)")
    Coupon findByTitle(String title);

    @Query("SELECT c FROM Coupon c WHERE c.amount > 0")
    Collection<Coupon> findAllAvailableCoupons();

    @Modifying
    @Transactional
    @Query("DELETE FROM Coupon c WHERE c.endDate < CURRENT_DATE ")
    void deleteExpiredCoupons();

    /**
     * Queries for coupons of the company
     */
    @Query("SELECT c FROM Coupon c WHERE c.id = ?1 AND company_id = ?2")
    Coupon findCompanyCoupon(long couponId, long companyId);

    @Query("SELECT c FROM Coupon c WHERE company_id = ?1")
    Collection<Coupon> findAllCompanyCoupons(long companyId);

    @Query("SELECT c FROM Coupon c WHERE company_id = ?1 AND c.couponType = ?2")
    Collection<Coupon> findAllCompanyCouponsByType(long companyId, CouponType couponType);

    @Query("SELECT c FROM Coupon c WHERE company_id = ?1 AND c.price <= ?2")
    Collection<Coupon> findAllCompanyCouponsByPrice(long companyId, double price);

    /**
     * Queries for coupons of the customer
     */
    @Query("SELECT coupon FROM Customer c JOIN c.coupons coupon WHERE c.id = ?1 AND coupon.id = ?2")
    Coupon findCustomerCoupon(long customerId, long couponId);

    @Query("SELECT coupon FROM Customer c JOIN c.coupons coupon WHERE c.id = ?1")
    Collection<Coupon> findAllCustomerCoupons(long customerId);

    @Query("SELECT coupon FROM Customer c JOIN c.coupons coupon WHERE c.id = ?1 AND coupon.couponType = ?2")
    Collection<Coupon> findAllCustomerCouponsByType(long customerId, CouponType couponType);

    @Query("SELECT coupon FROM Customer c JOIN c.coupons coupon WHERE c.id = ?1 AND coupon.price <= ?2")
    Collection<Coupon> findAllCustomerCouponsByPrice(long customerId, double price);

}
