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
import coupon_system.repositories.IncomeRepository;
import coupon_system.utilities.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class CompanyService extends CouponClientService implements Validations {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final IncomeRepository incomeRepository;
    private Company loggedCompany;

    @Autowired
    public CompanyService(CompanyRepository companyRepository,
                          CouponRepository couponRepository,
                          IncomeRepository incomeRepository) {
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
        this.incomeRepository = incomeRepository;
    }

    @Override
    public CouponClientService login(String username,
                                     String password) throws LoginFailedException {
        loggedCompany = companyRepository.login(username, password)
                .orElseThrow(() -> new LoginFailedException("Authorization is failed, please try again."));
        return this;
    }

    public void createCoupon(Coupon coupon) throws CouponTitleDuplicateException {
        this.isCouponTitleDuplicate(coupon.getTitle(), couponRepository);
        createCoupon(coupon, couponRepository, companyRepository, loggedCompany.getId());

        incomeRepository.save(new Income(loggedCompany,
                new Date(System.currentTimeMillis()),
                IncomeType.COMPANY_CREATED_COUPON,
                100));
    }

    public Coupon getCompanyCoupon(long couponId) throws CompanyDoesntOwnCoupon {
        return couponRepository.findCompanyCoupon(loggedCompany.getId(), couponId)
                .orElseThrow(() -> new CompanyDoesntOwnCoupon("You don't own this coupon."));
    }

    public Collection<Coupon> getAllCompanyCoupons() throws CouponSystemException {
        return couponRepository.findAllCompanyCoupons(loggedCompany.getId())
                .orElseThrow(() -> new CompanyDoesntOwnCoupon("You have no coupons."));
    }

    public Collection<Coupon> getAllCompanyCouponsByType(CouponType couponType) throws CompanyDoesntOwnCoupon {
        return couponRepository.findAllCompanyCouponsByType(loggedCompany.getId(), couponType)
                .orElseThrow(() -> new CompanyDoesntOwnCoupon("You have no coupons by type: " + couponType + "."));
    }

    public Collection<Coupon> getAllCompanyCouponsByPrice(double price) throws CompanyDoesntOwnCoupon {
        return couponRepository.findAllCompanyCouponsByPrice(loggedCompany.getId(), price)
                .orElseThrow(() -> new CompanyDoesntOwnCoupon("You have no coupons by type: " + price + "."));
    }

    public void updateCoupon(Coupon coupon) throws CouponUnavaliableException, CompanyDoesntOwnCoupon, CouponExpiredException, CouponTitleDuplicateException {

        this.isCompanyOwnsCoupon(loggedCompany.getId(), coupon.getId(), couponRepository);
        this.isCouponTitleDuplicate(coupon.getTitle(), couponRepository);

        // Checking if updating end date is not expired
        if (coupon.getEndDate().after(new Date(System.currentTimeMillis()))) {

            if (coupon.getAmount() > 0) {

                if (coupon.getPrice() > 0) {

                    Coupon updCoupon = couponRepository.findById(coupon.getId()).get();
                    updCoupon.setEndDate(coupon.getEndDate());
                    updCoupon.setPrice(coupon.getPrice());
                    updCoupon.setAmount(coupon.getAmount());

                    couponRepository.save(updCoupon);

                    incomeRepository.save(new Income(loggedCompany,
                            new Date(System.currentTimeMillis()),
                            IncomeType.COMPANY_UPDATED_COUPON,
                            10));
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
        this.isCompanyOwnsCoupon(loggedCompany.getId(), couponId, couponRepository);
        couponRepository.deleteById(couponId);

        incomeRepository.save(new Income(
                new Date(System.currentTimeMillis()),
                IncomeType.COMPANY_DELETED_COUPON,
                1));
    }

    static void createCoupon(Coupon coupon, CouponRepository couponRepository, CompanyRepository companyRepository, long loggedCompany) throws CouponTitleDuplicateException {
        coupon.setCompany(companyRepository.findById(loggedCompany).get());
        couponRepository.save(coupon);
    }

    public Collection<Income> getCompanyIncomes() throws CouponSystemException {
        return incomeRepository.findCompanyIncomes(loggedCompany.getId())
                .orElseThrow(() -> new CouponSystemException("There are no incomes of the companies."));
    }
}

