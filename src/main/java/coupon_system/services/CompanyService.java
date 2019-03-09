package coupon_system.services;

import coupon_system.entities.Coupon;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.exceptions.companyExceptions.CompanyDoesntOwnCoupon;
import coupon_system.exceptions.couponExceptions.CouponExpiredException;
import coupon_system.exceptions.couponExceptions.CouponTitleDuplicateException;
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

    public void createCoupon(Coupon coupon) throws CouponSystemException {
        createCoupon(coupon, couponRepository, companyRepository, loggedCompany);
    }

    public Collection<Coupon> getAllCompanyCoupons() throws CouponSystemException {

        Collection<Coupon> allCompanyCoupons = couponRepository.findAllByCompanyId(loggedCompany);

        // Checking if company owns at least one coupon
        if (!allCompanyCoupons.isEmpty()) {
            return allCompanyCoupons;
        } else {
            throw new CompanyDoesntOwnCoupon("You have no coupons.");
        }
    }

    public void updateCoupon(Coupon coupon) throws CouponSystemException {

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
                    throw new CouponExpiredException("Not allowed to update price to less than 1.");
                }
            } else {
                throw new CouponExpiredException("Not allowed to update amount to less than 1.");
            }
        } else {
            throw new CouponExpiredException("Not allowed to update the coupon to expired date.");
        }
    }

    public void deleteCoupon(int id) throws CouponSystemException {
        this.isCompanyOwnsCoupon(id);
        couponRepository.deleteById(id);
    }

    static void createCoupon(Coupon coupon, CouponRepository couponRepository, CompanyRepository companyRepository, int loggedCompany) throws CouponSystemException {

        // Checking if title of the new coupon is not duplicate
        isCouponTitleDuplicate(coupon.getTitle(), couponRepository);

        coupon.setCompany(companyRepository.findById(loggedCompany));
        couponRepository.save(coupon);
    }

    // Checking if title of the new coupon is not duplicate
    static void isCouponTitleDuplicate(String couponTitle, CouponRepository couponRepository) throws CouponSystemException {
        Optional<Coupon> isCouponTitleDuplicate = Optional
                .ofNullable(couponRepository.findByTitle(couponTitle));
        if (isCouponTitleDuplicate.isPresent()) {
            throw new CouponTitleDuplicateException("Coupon title: " + couponTitle
                    + " already exists.");
        }
    }

    // Checking if company is owner of the coupon
    private void isCompanyOwnsCoupon(int couponID) throws CouponSystemException {
        Optional<Coupon> isCompanyOwnsCoupon = Optional
                .ofNullable(couponRepository.findByIdAndCompanyId(couponID, loggedCompany));
        if (!isCompanyOwnsCoupon.isPresent()) {
            throw new CompanyDoesntOwnCoupon("You don't own this coupon.");
        }
    }

    @Deprecated
    public void setLoggedCompany(int loggedCompany) {
        this.loggedCompany = loggedCompany;
    }

}

