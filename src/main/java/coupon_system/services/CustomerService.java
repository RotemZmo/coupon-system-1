package coupon_system.services;

import coupon_system.entities.Coupon;
import coupon_system.repositories.CouponRepository;
import coupon_system.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CouponRepository couponRepository) {
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }

    public void purchaseCoupon(int couponId) {
    }

    public Collection<Coupon> getAllCustomerCoupons() {
        return null;
    }

    public Collection<Coupon> getAllAvailableCoupons() {
        return null;
    }
}
