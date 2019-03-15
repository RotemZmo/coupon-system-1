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
//    @Query("SELECT c FROM Coupon c WHERE c.id IN (SELECT coupons.id FROM customer_coupons WHERE coupons_id = ?1 AND customer_id = ?2)")
//    Coupon findCustomerCoupon(int couponId, int customerId);
    @Query("SELECT c FROM Coupon c WHERE company_id = ?1")
    Collection<Coupon> findAllCustomerCoupons(int customerId);

    @Query("SELECT c FROM Coupon c WHERE company_id = ?1 AND c.couponType = ?2")
    Collection<Coupon> findAllCustomerCouponsByType(int customerId, CouponType couponType);

    @Query("SELECT c FROM Coupon c WHERE company_id = ?1 AND c.price <= ?2")
    Collection<Coupon> findAllCustomerCouponsByPrice(int customerId, double price);

}
