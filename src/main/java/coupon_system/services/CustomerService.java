package coupon_system.services;

import coupon_system.entities.Coupon;
import coupon_system.entities.Customer;
import coupon_system.entities.Income;
import coupon_system.enums.CouponType;
import coupon_system.enums.IncomeType;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.exceptions.couponExceptions.CouponExpiredException;
import coupon_system.exceptions.couponExceptions.CouponNotExistsException;
import coupon_system.exceptions.couponExceptions.CouponUnavaliableException;
import coupon_system.exceptions.customerExceptions.CustomerDoesntOwnCoupon;
import coupon_system.repositories.CouponRepository;
import coupon_system.repositories.CustomerRepository;
import coupon_system.repositories.IncomeRepository;
import coupon_system.utilities.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
@Scope("prototype")
public class CustomerService extends CouponClientService implements Validations {

    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;
    private final IncomeRepository incomeRepository;
    private Customer loggedCustomer;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           CouponRepository couponRepository,
                           IncomeRepository incomeRepository) {
        this.customerRepository = customerRepository;
        this.couponRepository = couponRepository;
        this.incomeRepository = incomeRepository;
    }

    @Override
    public CouponClientService login(String username,
                                     String password) throws LoginFailedException {
        loggedCustomer = customerRepository.login(username, password)
                .orElseThrow(() -> new LoginFailedException("Authorization is failed, please try again."));
        return this;
    }

    public void purchaseCoupon(long couponId) throws CouponSystemException {

        // Checking if customer already has a coupon
        this.isCustomerHasCoupon(loggedCustomer.getId(), couponId, couponRepository);

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotExistsException("This coupon doesn't exist."));

        // Checking if coupon end date isn't expired
        if (coupon.getEndDate().after(new Date(System.currentTimeMillis()))) {

            // Checking amount of the coupon
            if (coupon.getAmount() > 0) {

                coupon.setAmount(coupon.getAmount() - 1);
                couponRepository.save(coupon);
                customerRepository.purchaseCoupon(loggedCustomer.getId(), couponId);

                incomeRepository.save(new Income(loggedCustomer,
                        new Date(System.currentTimeMillis()),
                        IncomeType.CUSTOMER_PURCHASED_COUPON,
                        coupon.getPrice()));
            } else {
                throw new CouponUnavaliableException("This coupon is not available");
            }
        } else {
            throw new CouponExpiredException("This coupon is expired.");
        }
    }

    public Collection<Coupon> getPurchasedCoupons() throws CustomerDoesntOwnCoupon {
        return couponRepository.findAllCustomerCoupons(loggedCustomer.getId())
                .orElseThrow(() -> new CustomerDoesntOwnCoupon("You have no coupons."));
    }

    public Collection<Coupon> getPurchasedCouponsByType(CouponType couponType) throws CustomerDoesntOwnCoupon {
        return couponRepository.findAllCustomerCouponsByType(loggedCustomer.getId(), couponType)
                .orElseThrow(() -> new CustomerDoesntOwnCoupon("You have no coupons by type: " + couponType + "."));
    }

    public Collection<Coupon> getPurchasedCouponsByPrice(double price) throws CustomerDoesntOwnCoupon {
        return couponRepository.findAllCustomerCouponsByPrice(loggedCustomer.getId(), price)
                .orElseThrow(() -> new CustomerDoesntOwnCoupon("You have no coupons by type: " + price + "."));
    }

    public Collection<Coupon> getAllAvailableCoupons() throws CouponUnavaliableException {
        return couponRepository.findAllAvailableCoupons()
                .orElseThrow(() -> new CouponUnavaliableException("There are no available coupons."));
    }
}
