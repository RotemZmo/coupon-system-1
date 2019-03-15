package coupon_system.controllers;

import coupon_system.entities.Company;
import coupon_system.entities.Coupon;
import coupon_system.entities.Customer;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(path = "company", method = RequestMethod.POST)
    public ResponseEntity<?> createCompany(@RequestBody Company company) {
        try {
            adminService.createCompany(company);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "companyById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCompanyById(@PathVariable("id") int companyId) {
        try {
            Company company = adminService.getCompanyById(companyId);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "company", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCompanies() {
        try {
            Collection<Company> companies = adminService.getAllCompanies();
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "company", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCompany(@RequestBody Company company) {
        try {
            adminService.updateCompany(company);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "company/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCompany(@PathVariable int id) {
        adminService.deleteCompany(id);
        return new ResponseEntity<>("Company successfully deleted.", HttpStatus.OK);
    }

    @RequestMapping(path = "coupon", method = RequestMethod.POST)
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
        try {
            adminService.createCoupon(coupon);
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "couponById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCouponById(@PathVariable("id") int couponId) {
        try {
            Coupon coupon = adminService.getCouponById(couponId);
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupon", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCoupons() {
        try {
            Collection<Coupon> coupons = adminService.getAllCoupons();
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupon", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon) {
        try {
            adminService.updateCoupon(coupon);
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupon/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCoupon(@PathVariable int id) {
        adminService.deleteCoupon(id);
        return new ResponseEntity<>("Coupon successfully deleted.", HttpStatus.OK);
    }

    @RequestMapping(path = "customer", method = RequestMethod.POST)
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            adminService.createCustomer(customer);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "customerById/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerById(@PathVariable("id") int customerId) {
        try {
            Customer customer = adminService.getCustomerById(customerId);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "customer", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCustomers() {
        try {
            Collection<Customer> customers = adminService.getAllCustomers();
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "customer", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        try {
            adminService.updateCustomer(customer);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "customer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
        adminService.deleteCustomer(id);
        return new ResponseEntity<>("Customer successfully deleted.", HttpStatus.OK);
    }
}
