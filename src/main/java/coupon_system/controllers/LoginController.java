package coupon_system.controllers;

import coupon_system.enums.ClientType;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.main_app.CouponSystem;
import coupon_system.services.AdminService;
import coupon_system.services.CompanyService;
import coupon_system.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("login")
public class LoginController {

    private CouponSystem couponSystem;

    @Autowired
    public LoginController(CouponSystem couponSystem) {
        this.couponSystem = couponSystem;
    }

    static final String ADMIN_SERVICE = "adminService";
    static final String COMPANY_SERVICE = "companyService";
    static final String CUSTOMER_SERVICE = "customerService";

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody User user, HttpServletRequest request) {

        request.getSession().invalidate();

        switch (user.getClientType()) {
            case ADMIN:
                try {
                    AdminService adminService = (AdminService) couponSystem.login(user.getName(), user.getPassword(), user.getClientType());
                    HttpSession adminSession = request.getSession();
                    adminSession.setAttribute(ADMIN_SERVICE, adminService);
                    return new ResponseEntity<>(HttpStatus.ACCEPTED);
                } catch (LoginFailedException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            case COMPANY:
                try {
                    CompanyService companyService = (CompanyService) couponSystem.login(user.getName(), user.getPassword(), user.getClientType());
                    HttpSession companySession = request.getSession();
                    companySession.setAttribute(COMPANY_SERVICE, companyService);
                    return new ResponseEntity<>(HttpStatus.ACCEPTED);
                } catch (LoginFailedException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            case CUSTOMER:
                try {
                    CustomerService customerService = (CustomerService) couponSystem.login(user.getName(), user.getPassword(), user.getClientType());
                    HttpSession customerSession = request.getSession();
                    customerSession.setAttribute(CUSTOMER_SERVICE, customerService);
                    return new ResponseEntity<>(HttpStatus.ACCEPTED);
                } catch (LoginFailedException e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

class User {
    private String name;
    private String password;
    private ClientType clientType;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ClientType getClientType() {
        return clientType;
    }
}
