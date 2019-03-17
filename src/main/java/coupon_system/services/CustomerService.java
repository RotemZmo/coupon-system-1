package coupon_system.services;

import coupon_system.entities.Coupon;
import coupon_system.entities.Customer;
import coupon_system.enums.ClientType;
import coupon_system.enums.CouponType;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.exceptions.couponExceptions.CouponExpiredException;
import coupon_system.exceptions.couponExceptions.CouponUnavaliableException;
import coupon_system.exceptions.customerExceptions.CustomerAlreadyHasCouponException;
import coupon_system.exceptions.customerExceptions.CustomerDoesntOwnCoupon;
import coupon_system.repositories.CouponRepository;
import coupon_system.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Component
public class CustomerService implements CouponClientService {

    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private int loggedCustomer;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           CouponRepository couponRepository) {
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
    }

    public void purchaseCoupon(int couponId) throws CouponSystemException {

        // Checking if coupon exists before purchasing it
        AdminService.isCouponExists(couponId, couponRepository);

        // Checking if customer already has a coupon
        this.isCustomerHasCoupon(couponId);

        Coupon coupon = couponRepository.findById(couponId);

        // Checking if coupon end date isn't expired
        if (coupon.getEndDate().after(new Date(System.currentTimeMillis()))) {

            // Checking amount of the coupon
            if (coupon.getAmount() > 0) {

                coupon.setAmount(coupon.getAmount() - 1);
                couponRepository.save(coupon);
                customerRepository.purchaseCoupon(loggedCustomer, couponId);
            } else {
                throw new CouponUnavaliableException("This coupon is not available");
            }
        } else {
            throw new CouponExpiredException("This coupon is expired.");
        }
    }

    public Collection<Coupon> getPurchasedCoupons() throws CustomerDoesntOwnCoupon {

        Collection<Coupon> coupons = couponRepository.findAllCustomerCoupons(loggedCustomer);

        // Checking if customer owns at least one coupon
        if (!coupons.isEmpty()) {
            return coupons;
        } else {
            throw new CustomerDoesntOwnCoupon("You have no coupons.");
        }
    }

    public Collection<Coupon> getPurchasedCouponsByType(CouponType couponType) throws CustomerDoesntOwnCoupon {

        Collection<Coupon> coupons = couponRepository.findAllCustomerCouponsByType(loggedCustomer, couponType);

        // Checking if customer owns at least one coupon by specified type
        if (!coupons.isEmpty()) {
            return coupons;
        } else {
            throw new CustomerDoesntOwnCoupon("You have no coupons by type: " + couponType + ".");
        }
    }

    public Collection<Coupon> getPurchasedCouponsByPrice(double price) throws CustomerDoesntOwnCoupon {

        Collection<Coupon> coupons = couponRepository.findAllCustomerCouponsByPrice(loggedCustomer, price);

        // Checking if customer owns at least one coupon by specified price
        if (!coupons.isEmpty()) {
            return coupons;
        } else {
            throw new CustomerDoesntOwnCoupon("You have no coupons by type: " + price + ".");
        }
    }

    public Collection<Coupon> getAllAvailableCoupons() throws CouponUnavaliableException {

        Collection<Coupon> coupons = couponRepository.findAllAvailableCoupons();

        if (!coupons.isEmpty()) {
            return coupons;
        } else {
            throw new CouponUnavaliableException("There are no available coupons.");
        }
    }

    @Override
    public CouponClientService login(String username, String password, ClientType clientType) {
        return null;
    }

    // Checking if customer already has a coupon
    private void isCustomerHasCoupon(int couponId) throws CustomerAlreadyHasCouponException {
        Optional<Coupon> isCustomerHasCoupon = Optional.ofNullable(couponRepository.findCustomerCoupon(loggedCustomer, couponId));
        if (isCustomerHasCoupon.isPresent()) {
            throw new CustomerAlreadyHasCouponException("You already have this coupon.");
        }
    }

    /**
     * FAKE LOGIN
     */
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
