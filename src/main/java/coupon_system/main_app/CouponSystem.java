package coupon_system.main_app;

import coupon_system.enums.ClientType;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.services.AdminService;
import coupon_system.services.CompanyService;
import coupon_system.services.CouponClientService;
import coupon_system.services.CustomerService;
import coupon_system.tasks.DailyCouponExpirationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CouponSystem {

    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;

    @Autowired
    public CouponSystem(AdminService adminService,
                        CompanyService companyService,
                        CustomerService customerService,
                        DailyCouponExpirationTask task) {
        this.adminService = adminService;
        this.companyService = companyService;
        this.customerService = customerService;
        task.start();
    }

    public CouponClientService login(String username,
                                     String password,
                                     ClientType clientType) throws LoginFailedException {
        switch (clientType) {
            case ADMIN:
                return adminService.login(username, password);
            case COMPANY:
                return companyService.login(username, password);
            case CUSTOMER:
                return customerService.login(username, password);
            default:
                throw new LoginFailedException("Authorization is failed, please try again.");
        }
    }
}
