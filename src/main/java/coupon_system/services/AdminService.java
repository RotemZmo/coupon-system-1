package coupon_system.services;

import coupon_system.entities.Company;
import coupon_system.entities.Coupon;
import coupon_system.entities.Customer;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.exceptions.companyExceptions.CompanyNameDuplicateException;
import coupon_system.exceptions.companyExceptions.CompanyNotExistsException;
import coupon_system.exceptions.couponExceptions.CouponExpiredException;
import coupon_system.exceptions.couponExceptions.CouponNotExistsException;
import coupon_system.exceptions.couponExceptions.CouponTitleDuplicateException;
import coupon_system.exceptions.customerExceptions.CustomerNameDuplicateException;
import coupon_system.exceptions.customerExceptions.CustomerNotExistsException;
import coupon_system.repositories.CompanyRepository;
import coupon_system.repositories.CouponRepository;
import coupon_system.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class AdminService extends CouponClientService {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AdminService(CompanyRepository companyRepository,
                        CouponRepository couponRepository,
                        CustomerRepository customerRepository) {
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public CouponClientService login(String username,
                                     String password) throws LoginFailedException {
        if (username.equalsIgnoreCase("admin") && password.equals("1234")) {
            return this;
        } else {
            throw new LoginFailedException("Authorization is failed, please try again.");
        }
    }

    public void createCompany(Company company) throws CompanyNameDuplicateException {
        this.isCompanyNameDuplicate(company.getName());
        companyRepository.save(company);
    }

    public Company getCompanyById(long companyId) throws CompanyNotExistsException {
        return companyRepository.findById(companyId).orElseThrow(CompanyNotExistsException::new);
    }

    public Collection<Company> getAllCompanies() throws CompanyNotExistsException {

        Collection<Company> companies = companyRepository.findAll();

        // Checking if at least one company exists before getting all of them
        if (!companies.isEmpty()) {
            return companies;
        } else {
            throw new CompanyNotExistsException("There are no companies.");
        }
    }

    public void updateCompany(Company company) throws CompanyNameDuplicateException, CompanyNotExistsException {
        this.isCompanyExists(company.getId());
        this.isCompanyNameDuplicate(company.getName());
        companyRepository.save(company);
    }

    public void deleteCompany(long companyId) {
        companyRepository.deleteById(companyId);
    }

    public void createCoupon(Coupon coupon) throws CompanyNotExistsException, CouponTitleDuplicateException {
        this.isCompanyExists(coupon.getCompanyId());
        CompanyService.createCoupon(coupon, couponRepository, companyRepository, coupon.getCompanyId());
    }

    public Coupon getCouponById(long couponId) throws CouponSystemException {
        return couponRepository.findById(couponId).orElseThrow(CouponNotExistsException::new);
    }

    public Collection<Coupon> getAllCoupons() throws CouponNotExistsException {

        Collection<Coupon> coupons = couponRepository.findAll();

        // Checking if there is at least one coupon in database
        if (!coupons.isEmpty()) {
            return coupons;
        } else {
            throw new CouponNotExistsException("There are no coupons.");
        }
    }

    public void updateCoupon(Coupon coupon) throws CouponSystemException {

        this.isCompanyExists(coupon.getCompanyId());
        isCouponExists(coupon.getId(), couponRepository);
        CompanyService.isCouponTitleDuplicate(coupon.getTitle(), couponRepository);

        // Checking if updating end date is not expired
        if (coupon.getEndDate().after(new Date(System.currentTimeMillis()))) {

            if (coupon.getAmount() > 0) {

                if (coupon.getPrice() > 0) {

                    couponRepository.save(coupon);
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

    public void deleteCoupon(long couponId) {
        couponRepository.deleteById(couponId);
    }

    public void createCustomer(Customer customer) throws CouponSystemException {
        this.isCustomerNameDuplicate(customer.getName());
        customerRepository.save(customer);
    }

    public Customer getCustomerById(long customerId) throws CustomerNotExistsException {
        return customerRepository.findById(customerId).orElseThrow(CustomerNotExistsException::new);
    }

    public Collection<Customer> getAllCustomers() throws CouponSystemException {

        Collection<Customer> customers = customerRepository.findAll();

        // Checking if at least one customer exists before getting all of them
        if (!customers.isEmpty()) {
            return customers;
        } else {
            throw new CustomerNotExistsException("There are no customers.");
        }
    }

    public void updateCustomer(Customer customer) throws CouponSystemException {
        this.isCustomerExists(customer.getId());
        this.isCustomerNameDuplicate(customer.getName());
        customerRepository.save(customer);
    }

    public void deleteCustomer(long customerId) {
        customerRepository.deleteById(customerId);
    }

    // Checking if company exists in database
    private void isCompanyExists(long companyId) throws CompanyNotExistsException {
        Optional<Company> isCompanyExists = companyRepository.findById(companyId);
        if (!isCompanyExists.isPresent()) {
            throw new CompanyNotExistsException("This company doesn't exist.");
        }
    }

    // Checking if coupon exists in database
    static void isCouponExists(long couponId, CouponRepository couponRepository) throws CouponNotExistsException {
        Optional<Coupon> isCouponExists = couponRepository.findById(couponId);
        if (!isCouponExists.isPresent()) {
            throw new CouponNotExistsException("This coupon doesn't exist.");
        }
    }

    // Checking if customer exists in database
    private void isCustomerExists(long customerId) throws CustomerNotExistsException {
        Optional<Customer> isCustomerExists = customerRepository.findById(customerId);
        if (!isCustomerExists.isPresent()) {
            throw new CustomerNotExistsException("This customer doesn't exist.");
        }
    }

    // Checking if name of the new company is not duplicate
    private void isCompanyNameDuplicate(String companyName) throws CompanyNameDuplicateException {
        Optional<Company> isCompanyNameDuplicate = Optional
                .ofNullable(companyRepository.findByName(companyName));
        if (isCompanyNameDuplicate.isPresent()) {
            throw new CompanyNameDuplicateException("Company name: " + companyName
                    + " already exists.");
        }
    }

    // Checking if name of the new customer is not duplicate
    private void isCustomerNameDuplicate(String customerName) throws CustomerNameDuplicateException {
        Optional<Customer> isCustomerNameDuplicate = Optional
                .ofNullable(customerRepository.findByName(customerName));
        if (isCustomerNameDuplicate.isPresent()) {
            throw new CustomerNameDuplicateException("Customer name: " + customerName
                    + " already exists.");
        }
    }

}
