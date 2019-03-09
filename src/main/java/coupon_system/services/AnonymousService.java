package coupon_system.services;

import coupon_system.entities.Company;
import coupon_system.entities.Customer;
import coupon_system.exceptions.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Service for registration
@Component
public class AnonymousService {

    private final AdminService adminService;

    @Autowired
    public AnonymousService(AdminService adminService) {
        this.adminService = adminService;
    }

    public void registerCompany(Company company) throws CouponSystemException {
        adminService.createCompany(company);
    }

    public void registerCustomer(Customer customer) throws CouponSystemException {
        adminService.createCustomer(customer);
    }
}
