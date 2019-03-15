package coupon_system.services;

import coupon_system.entities.Coupon;
import coupon_system.entities.Customer;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.repositories.CouponRepository;
import coupon_system.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private int loggedCustomer;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CouponRepository couponRepository) {
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }

    public void purchaseCoupon(int couponId) throws CouponSystemException {
        AdminService.isCouponExists(couponId, couponRepository);
        Customer customer = customerRepository.findById(loggedCustomer);
        customer.purchaseCoupon(couponRepository.findById(couponId));
        customerRepository.save(customer);
    }

    public Collection<Coupon> getAllCustomerCoupons() {
        return null;
    }

    public Collection<Coupon> getAllAvailableCoupons() {
        return null;
    }

//    public Coupon getCustCoupon(int id) {
//        return couponRepository.findCustomerCoupon(id, loggedCustomer);
//    }

    public boolean login(String name, String password) throws CouponSystemException {
        Optional<Customer> canLogin = Optional.ofNullable(customerRepository.login(name, password));
        if (canLogin.isPresent()) {
            loggedCustomer = canLogin.get().getId();
            return true;
        } else {
            throw new LoginFailedException("Login failed, please try again.");
        }
    }

}
