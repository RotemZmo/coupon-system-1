package coupon_system.controllers;

import coupon_system.entities.*;
import coupon_system.enums.ClientType;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.repositories.TokenRepository;
import coupon_system.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping(value = "admin")
@Scope("session")
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
        allowedHeaders = {"Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Access-Control-Allow-Origin", "Authorization"},
        exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"})
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService,
                           TokenRepository tokenRepository,
                           HttpServletRequest request) throws LoginFailedException {
        this.adminService = adminService;

        Cookie[] cookie = request.getCookies();
        for (Cookie c : cookie) {
            if (c.getName().equals("auth")) {
                Token token = tokenRepository.findByClientTypeAndToken(ClientType.ADMIN, c.getValue());
                if (token == null) {
                    throw new LoginFailedException("Authorization is failed, please try again.");
                }
            }
        }
    }

    @RequestMapping(path = "companies", method = RequestMethod.POST)
    public ResponseEntity<?> createCompany(@RequestBody Company company) {
        try {
            adminService.createCompany(company);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "companies-by-id/{companyId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCompanyById(@PathVariable int companyId) {
        try {
            Company company = adminService.getCompanyById(companyId);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "companies", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCompanies() {
        try {
            Collection<Company> companies = adminService.getAllCompanies();
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "companies", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCompany(@RequestBody Company company) {
        try {
            adminService.updateCompany(company);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "companies/{companyId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCompany(@PathVariable int companyId) {
        adminService.deleteCompany(companyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "coupons", method = RequestMethod.POST)
    public ResponseEntity<?> createCoupon(@RequestBody long companyId, Coupon coupon) {
        try {
            adminService.createCoupon(companyId, coupon);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons-by-id/{couponId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCouponById(@PathVariable int couponId) {
        try {
            Coupon coupon = adminService.getCouponById(couponId);
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCoupons() {
        try {
            Collection<Coupon> coupons = adminService.getAllCoupons();
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCoupon(@RequestBody long companyId, Coupon coupon) {
        try {
            adminService.updateCoupon(companyId, coupon);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons/{couponId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCoupon(@PathVariable int couponId) {
        adminService.deleteCoupon(couponId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "customers", method = RequestMethod.POST)
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            adminService.createCustomer(customer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "customers-by-id/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerById(@PathVariable int customerId) {
        try {
            Customer customer = adminService.getCustomerById(customerId);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "customers", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCustomers() {
        try {
            Collection<Customer> customers = adminService.getAllCustomers();
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "customers", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        try {
            adminService.updateCustomer(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "customers/{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCustomer(@PathVariable int customerId) {
        adminService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "incomes", method = RequestMethod.GET)
    public ResponseEntity<?> getAllIncomes() {
        try {
            Collection<Income> incomes = adminService.getAllIncomes();
            return new ResponseEntity<>(incomes, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "company-incomes/{companyId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCompanyIncomes(@PathVariable long companyId) {
        try {
            Collection<Income> incomes = adminService.getCompanyIncomes(companyId);
            return new ResponseEntity<>(incomes, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "customer-incomes/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerIncomes(@PathVariable long customerId) {
        try {
            Collection<Income> incomes = adminService.getCustomerIncomes(customerId);
            return new ResponseEntity<>(incomes, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
