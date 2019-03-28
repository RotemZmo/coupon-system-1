package coupon_system.controllers;

import coupon_system.entities.Coupon;
import coupon_system.entities.Customer;
import coupon_system.entities.Token;
import coupon_system.enums.ClientType;
import coupon_system.enums.CouponType;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.exceptions.couponExceptions.CouponUnavaliableException;
import coupon_system.exceptions.customerExceptions.CustomerDoesntOwnCoupon;
import coupon_system.repositories.CustomerRepository;
import coupon_system.repositories.TokenRepository;
import coupon_system.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping("customer")
@Scope("session")
@CrossOrigin(value = "http://localhost:4200",
        allowCredentials = "true",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
        allowedHeaders = {"Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Access-Control-Allow-Origin", "Authorization"},
        exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"})
public class CustomerController {

    private final CustomerService customerService;
    private Customer customer;

    @Autowired
    public CustomerController(CustomerService customerService,
                              CustomerRepository customerRepository,
                              TokenRepository tokenRepository,
                              HttpServletRequest request) {
        this.customerService = customerService;

        Token token = null;
        Cookie[] cookie = request.getCookies();
        for (Cookie c : cookie) {
            if (c.getName().equals("auth")) {
                token = tokenRepository.findByClientTypeAndToken(ClientType.CUSTOMER, c.getValue());
            }
        }
        if (token != null) {
            this.customer = customerRepository.findById(token.getUserId()).get();
        }
    }

    @RequestMapping(path = "coupons/{couponId}", method = RequestMethod.GET)
    public ResponseEntity<?> purchaseCoupon(@PathVariable int couponId) {
        try {
            customerService.purchaseCoupon(customer, couponId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCustomerCoupons() {
        try {
            Collection<Coupon> coupons = customerService.getPurchasedCoupons(customer);
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CustomerDoesntOwnCoupon e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons-by-type/{couponType}", method = RequestMethod.GET)
    public ResponseEntity<?> getPurchasedCouponsByType(@PathVariable CouponType couponType) {
        try {
            Collection<Coupon> coupons = customerService.getPurchasedCouponsByType(customer, couponType);
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CustomerDoesntOwnCoupon e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons-by-price/{price}", method = RequestMethod.GET)
    public ResponseEntity<?> getPurchasedCouponsByPrice(@PathVariable double price) {
        try {
            Collection<Coupon> coupons = customerService.getPurchasedCouponsByPrice(customer, price);
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CustomerDoesntOwnCoupon e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons-available", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAvailableCoupons() {
        try {
            Collection<Coupon> coupons = customerService.getAllAvailableCoupons();
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CouponUnavaliableException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
