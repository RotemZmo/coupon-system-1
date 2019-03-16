package coupon_system.repositories;

import coupon_system.entities.Coupon;
import coupon_system.enums.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    Coupon findById(int id);

    @Query("SELECT DISTINCT c FROM Coupon c WHERE UPPER(c.title) LIKE UPPER(?1)")
    Coupon findByTitle(String title);

    @Query("SELECT c FROM Coupon c WHERE c.amount > 0")
    Collection<Coupon> findAllAvailableCoupons();

    @Query("DELETE FROM Coupon c WHERE c.endDate < CURRENT_DATE")
    void deleteExpiredCoupons();

    /**
     * Queries for coupons of the company
     */
    @Query("SELECT c FROM Coupon c WHERE c.id = ?1 AND company_id = ?2")
    Coupon findCompanyCoupon(int couponId, int companyId);

    @Query("SELECT c FROM Coupon c WHERE company_id = ?1")
    Collection<Coupon> findAllCompanyCoupons(int companyId);

    @Query("SELECT c FROM Coupon c WHERE company_id = ?1 AND c.couponType = ?2")
    Collection<Coupon> findAllCompanyCouponsByType(int companyId, CouponType couponType);

    @Query("SELECT c FROM Coupon c WHERE company_id = ?1 AND c.price <= ?2")
    Collection<Coupon> findAllCompanyCouponsByPrice(int companyId, double price);

    /**
     * Queries for coupons of the customer
     */
    @Query("SELECT coupon FROM Customer c JOIN c.coupons coupon WHERE c.id = ?1 AND coupon.id = ?2")
    Coupon findCustomerCoupon(int customerId, int couponId);

    @Query("SELECT coupon FROM Customer c JOIN c.coupons coupon WHERE c.id = ?1")
    Collection<Coupon> findAllCustomerCoupons(int customerId);

    @Query("SELECT coupon FROM Customer c JOIN c.coupons coupon WHERE c.id = ?1 AND coupon.couponType = ?2")
    Collection<Coupon> findAllCustomerCouponsByType(int customerId, CouponType couponType);

    @Query("SELECT coupon FROM Customer c JOIN c.coupons coupon WHERE c.id = ?1 AND coupon.price <= ?2")
    Collection<Coupon> findAllCustomerCouponsByPrice(int customerId, double price);

}
