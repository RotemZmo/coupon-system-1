package coupon_system.services;

import coupon_system.entities.Company;
import coupon_system.entities.Coupon;
import coupon_system.entities.Income;
import coupon_system.enums.CouponType;
import coupon_system.enums.IncomeType;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.exceptions.companyExceptions.CompanyDoesntOwnCoupon;
import coupon_system.exceptions.couponExceptions.CouponExpiredException;
import coupon_system.exceptions.couponExceptions.CouponTitleDuplicateException;
import coupon_system.exceptions.couponExceptions.CouponUnavaliableException;
import coupon_system.repositories.CompanyRepository;
import coupon_system.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class CompanyService extends CouponClientService {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final IncomeService incomeService;
    private Company loggedCompany;

    @Autowired
    public CompanyService(CompanyRepository companyRepository,
                          CouponRepository couponRepository,
                          IncomeService incomeService) {
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
        this.incomeService = incomeService;
    }

    @Override
    public CouponClientService login(String username,
                                     String password) throws LoginFailedException {
        Optional<Company> loginCheck = Optional.ofNullable(companyRepository.login(username, password));
        if (loginCheck.isPresent()) {
            loggedCompany = loginCheck.get();
            return this;
        } else {
            throw new LoginFailedException("Authorization is failed, please try again.");
        }
    }

    public void createCoupon(Coupon coupon) throws CouponTitleDuplicateException {
        createCoupon(coupon, couponRepository, companyRepository, loggedCompany.getId());

        Income income = new Income(loggedCompany, new Date(System.currentTimeMillis()), IncomeType.COMPANY_NEW_COUPON, 100);
        incomeService.storeIncome(income);
    }

    public Coupon getCompanyCoupon(long couponId) throws CompanyDoesntOwnCoupon {
        this.isCompanyOwnsCoupon(couponId);
        return couponRepository.findCompanyCoupon(couponId, loggedCompany.getId());
    }

    public Collection<Coupon> getAllCompanyCoupons() throws CouponSystemException {

        Collection<Coupon> coupons = couponRepository.findAllCompanyCoupons(loggedCompany.getId());

        // Checking if company owns at least one coupon
        if (!coupons.isEmpty()) {
            return coupons;
        } else {
            throw new CompanyDoesntOwnCoupon("You have no coupons.");
        }
    }

    public Collection<Coupon> getAllCompanyCouponsByType(CouponType couponType) throws CompanyDoesntOwnCoupon {

        Collection<Coupon> coupons = couponRepository.findAllCompanyCouponsByType(loggedCompany.getId(), couponType);

        // Checking if company owns at least one coupon by specified type
        if (!coupons.isEmpty()) {
            return coupons;
        } else {
            throw new CompanyDoesntOwnCoupon("You have no coupons by type: " + couponType + ".");
        }
    }

    public Collection<Coupon> getAllCompanyCouponsByPrice(double price) throws CompanyDoesntOwnCoupon {

        Collection<Coupon> coupons = couponRepository.findAllCompanyCouponsByPrice(loggedCompany.getId(), price);

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

                    Income income = new Income(loggedCompany, new Date(System.currentTimeMillis()), IncomeType.COMPANY_UPDATE_COUPON, 10);
                    incomeService.storeIncome(income);
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

    public void deleteCoupon(long couponId) throws CompanyDoesntOwnCoupon {
        this.isCompanyOwnsCoupon(couponId);
        couponRepository.deleteById(couponId);
    }

    static void createCoupon(Coupon coupon, CouponRepository couponRepository, CompanyRepository companyRepository, long loggedCompany) throws CouponTitleDuplicateException {
        isCouponTitleDuplicate(coupon.getTitle(), couponRepository);
        coupon.setCompany(companyRepository.findById(loggedCompany));
        couponRepository.save(coupon);
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
    private void isCompanyOwnsCoupon(long couponId) throws CompanyDoesntOwnCoupon {
        Optional<Coupon> isCompanyOwnsCoupon = Optional
                .ofNullable(couponRepository.findCompanyCoupon(couponId, loggedCompany.getId()));
        if (!isCompanyOwnsCoupon.isPresent()) {
            throw new CompanyDoesntOwnCoupon("You don't own this coupon.");
        }
    }
}

