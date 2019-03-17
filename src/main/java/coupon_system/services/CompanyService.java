package coupon_system.services;

import coupon_system.entities.Company;
import coupon_system.entities.Coupon;
import coupon_system.enums.CouponType;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.exceptions.companyExceptions.CompanyDoesntOwnCoupon;
import coupon_system.exceptions.couponExceptions.CouponExpiredException;
import coupon_system.exceptions.couponExceptions.CouponTitleDuplicateException;
import coupon_system.exceptions.couponExceptions.CouponUnavaliableException;
import coupon_system.repositories.CompanyRepository;
import coupon_system.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Component
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private int loggedCompany;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, CouponRepository couponRepository) {
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
    }

    public void createCoupon(Coupon coupon) throws CouponTitleDuplicateException {
        createCoupon(coupon, couponRepository, companyRepository, loggedCompany);
    }

    public Coupon getCompanyCoupon(int couponId) throws CompanyDoesntOwnCoupon {
        this.isCompanyOwnsCoupon(couponId);
        return couponRepository.findCompanyCoupon(couponId, loggedCompany);
    }

    public Collection<Coupon> getAllCompanyCoupons() throws CouponSystemException {

        Collection<Coupon> coupons = couponRepository.findAllCompanyCoupons(loggedCompany);

        // Checking if company owns at least one coupon
        if (!coupons.isEmpty()) {
            return coupons;
        } else {
            throw new CompanyDoesntOwnCoupon("You have no coupons.");
        }
    }

    public Collection<Coupon> getAllCompanyCouponsByType(CouponType couponType) throws CompanyDoesntOwnCoupon {

        Collection<Coupon> coupons = couponRepository.findAllCompanyCouponsByType(loggedCompany, couponType);

        // Checking if company owns at least one coupon by specified type
        if (!coupons.isEmpty()) {
            return coupons;
        } else {
            throw new CompanyDoesntOwnCoupon("You have no coupons by type: " + couponType + ".");
        }
    }

    public Collection<Coupon> getAllCompanyCouponsByPrice(double price) throws CompanyDoesntOwnCoupon {

        Collection<Coupon> coupons = couponRepository.findAllCompanyCouponsByPrice(loggedCompany, price);

        // Checking if company owns at least one coupon by specified price
        if (!coupons.isEmpty()) {
            return coupons;
        } else {
            throw new CompanyDoesntOwnCoupon("You have no coupons by type: " + price + ".");
        }
    }

    public void updateCoupon(Coupon coupon) throws CouponUnavaliableException, CompanyDoesntOwnCoupon, CouponExpiredException {

        this.isCompanyOwnsCoupon(coupon.getId());

        // Checking if updating end date is not expired
        if (coupon.getEndDate().after(new Date(System.currentTimeMillis()))) {

            if (coupon.getAmount() > 0) {

                if (coupon.getPrice() > 0) {

                    Coupon updCoupon = couponRepository.findById(coupon.getId());
                    updCoupon.setEndDate(coupon.getEndDate());
                    updCoupon.setPrice(coupon.getPrice());
                    updCoupon.setAmount(coupon.getAmount());

                    couponRepository.save(updCoupon);
                } else {
                    throw new CouponUnavaliableException("Not allowed to update price to less than 1.");
                }
            } else {
                throw new CouponUnavaliableException("Not allowed to update amount to less than 1.");
            }
        } else {
            throw new CouponExpiredException("Not allowed to update the coupon to expired date.");
        }
    }

    public void deleteCoupon(int id) throws CompanyDoesntOwnCoupon {
        this.isCompanyOwnsCoupon(id);
        couponRepository.deleteById(id);
    }

    static void createCoupon(Coupon coupon, CouponRepository couponRepository, CompanyRepository companyRepository, int loggedCompany) throws CouponTitleDuplicateException {
        isCouponTitleDuplicate(coupon.getTitle(), couponRepository);
        coupon.setCompany(companyRepository.findById(loggedCompany));
        couponRepository.save(coupon);
    }

    public boolean login(String name, String password) throws CouponSystemException {
        Optional<Company> canLogin = Optional.ofNullable(companyRepository.login(name, password));
        if (canLogin.isPresent()) {
            loggedCompany = canLogin.get().getId();
            return true;
        } else {
            throw new LoginFailedException("Login failed, please try again.");
        }
    }

    // Checking if title of the new coupon is not duplicate
    static void isCouponTitleDuplicate(String couponTitle, CouponRepository couponRepository) throws CouponTitleDuplicateException {
        Optional<Coupon> isCouponTitleDuplicate = Optional
                .ofNullable(couponRepository.findByTitle(couponTitle));
        if (isCouponTitleDuplicate.isPresent()) {
            throw new CouponTitleDuplicateException("Coupon title: " + couponTitle
                    + " already exists.");
        }
    }

    // Checking if company is owner of the coupon
    private void isCompanyOwnsCoupon(int couponId) throws CompanyDoesntOwnCoupon {
        Optional<Coupon> isCompanyOwnsCoupon = Optional
                .ofNullable(couponRepository.findCompanyCoupon(couponId, loggedCompany));
        if (!isCompanyOwnsCoupon.isPresent()) {
            throw new CompanyDoesntOwnCoupon("You don't own this coupon.");
        }
    }

}

