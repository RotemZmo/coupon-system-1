package coupon_system;

import coupon_system.entities.Company;
import coupon_system.entities.Coupon;
import coupon_system.entities.Customer;
import coupon_system.enums.ClientType;
import coupon_system.enums.CouponType;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.main_app.CouponSystem;
import coupon_system.services.AdminService;
import coupon_system.services.CompanyService;
import coupon_system.services.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

@SpringBootApplication
public class CouponSystemApplication {

    public static void main(String[] args) throws CouponSystemException {
//        SpringApplication.run(CouponSystemApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(CouponSystemApplication.class, args);
        CouponSystem couponSystem = context.getBean("couponSystem", CouponSystem.class);
        AdminService adminService = (AdminService) couponSystem.login("admin", "1234", ClientType.ADMIN);
        CompanyService companyService = (CompanyService) couponSystem.login("comp", "comp", ClientType.COMPANY);
        CustomerService customerService = (CustomerService) couponSystem.login("cust", "cust", ClientType.CUSTOMER);
        /**
         *
         * */

    }

    /**
     * COMPANY
     */
    static Company company = new Company("comp1111", "comp1", "comp1@gmail.com");
    static Company companyUpd = new Company(15, "comp666", "666", "comp666@gmail.com");

    /**
     * CUSTOMER
     */
    static Customer customer = new Customer("cust", "cust", "cust@gmail.com");
    static Customer customerUpd = new Customer(21, "Cust666", "666", "cust666@gmail.com");

    /**
     * COUPON
     */
    static Coupon coupon = new Coupon("Coupon41", new Date(0), new Date(0), 10, CouponType.CAMPING, "coup", 10, "coup");
    static Coupon couponUpd = new Coupon(21, "Coupon1sss", new Date(0), new Date(System.currentTimeMillis() + 100000), 100, CouponType.CAMPING, "coup", 100, "coup");

}
