package coupon_system.services;

import coupon_system.entities.Coupon;
import coupon_system.entities.Customer;
import coupon_system.entities.Income;
import coupon_system.enums.CouponType;
import coupon_system.enums.IncomeType;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.exceptions.couponExceptions.CouponExpiredException;
import coupon_system.exceptions.couponExceptions.CouponUnavaliableException;
import coupon_system.exceptions.customerExceptions.CustomerAlreadyHasCouponException;
import coupon_system.exceptions.customerExceptions.CustomerDoesntOwnCoupon;
import coupon_system.repositories.CouponRepository;
import coupon_system.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class CustomerService extends CouponClientService {

    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private final IncomeService incomeService;
    private Customer loggedCustomer;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           CouponRepository couponRepository,
                           IncomeService incomeService) {
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
        this.incomeService = incomeService;
    }

    @Override
    public CouponClientService login(String username,
                                     String password) throws LoginFailedException {
        Optional<Customer> loginCheck = Optional.ofNullable(customerRepository.login(username, password));
        if (loginCheck.isPresent()) {
            loggedCustomer = loginCheck.get();
            return this;
        } else {
            throw new LoginFailedException("Authorization is failed, please try again.");
        }
    }

    public void purchaseCoupon(long couponId) throws CouponSystemException {

        // Checking if coupon exists before purchasing it
        AdminService.isCouponExists(couponId, couponRepository);

        // Checking if customer already has a coupon
        this.isCustomerHasCoupon(couponId);

        Coupon coupon = couponRepository.findById(couponId);

        // Checking if coupon end date isn't expired
        if (coupon.getEndDate().after(new Date(System.currentTimeMillis()))) {

            // Checking amount of the coupon
            if (coupon.getAmount() > 0) {

                Income income = new Income(loggedCustomer, new Date(System.currentTimeMillis()), IncomeType.CUSTOMER_PURCHASE, coupon.getPrice());
                incomeService.storeIncome(income);

                coupon.setAmount(coupon.getAmount() - 1);
                couponRepository.save(coupon);
                customerRepository.purchaseCoupon(loggedCustomer.getId(), couponId);
            } else {
                throw new CouponUnavaliableException("This coupon is not available");
            }
        } else {
            throw new CouponExpiredException("This coupon is expired.");
        }
    }

    public Collection<Coupon> getPurchasedCoupons() throws CustomerDoesntOwnCoupon {

        Collection<Coupon> coupons = couponRepository.findAllCustomerCoupons(loggedCustomer.getId());

        // Checking if customer owns at least one coupon
        if (!coupons.isEmpty()) {
            return coupons;
        } else {
            throw new CustomerDoesntOwnCoupon("You have no coupons.");
        }
    }

    public Collection<Coupon> getPurchasedCouponsByType(CouponType couponType) throws CustomerDoesntOwnCoupon {

        Collection<Coupon> coupons = couponRepository.findAllCustomerCouponsByType(loggedCustomer.getId(), couponType);

        // Checking if customer owns at least one coupon by specified type
        if (!coupons.isEmpty()) {
            return coupons;
        } else {
            throw new CustomerDoesntOwnCoupon("You have no coupons by type: " + couponType + ".");
        }
    }

    public Collection<Coupon> getPurchasedCouponsByPrice(double price) throws CustomerDoesntOwnCoupon {

        Collection<Coupon> coupons = couponRepository.findAllCustomerCouponsByPrice(loggedCustomer.getId(), price);

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

    // Checking if customer already has a coupon
    private void isCustomerHasCoupon(long couponId) throws CustomerAlreadyHasCouponException {
        Optional<Coupon> isCustomerHasCoupon = Optional.ofNullable(couponRepository.findCustomerCoupon(loggedCustomer.getId(), couponId));
        if (isCustomerHasCoupon.isPresent()) {
            throw new CustomerAlreadyHasCouponException("You already have this coupon.");
        }
    }
}
