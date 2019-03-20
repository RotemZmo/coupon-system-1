package coupon_system.controllers;

import coupon_system.entities.Coupon;
import coupon_system.enums.CouponType;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.exceptions.couponExceptions.CouponUnavaliableException;
import coupon_system.exceptions.customerExceptions.CustomerDoesntOwnCoupon;
import coupon_system.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(path = "coupons/{couponId}", method = RequestMethod.GET)
    public ResponseEntity<?> purchaseCoupon(@PathVariable int couponId) {
        try {
            customerService.purchaseCoupon(couponId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCustomerCoupons() {
        try {
            Collection<Coupon> coupons = customerService.getPurchasedCoupons();
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CustomerDoesntOwnCoupon e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons-by-type/{couponType}", method = RequestMethod.GET)
    public ResponseEntity<?> getPurchasedCouponsByType(@PathVariable CouponType couponType) {
        try {
            Collection<Coupon> coupons = customerService.getPurchasedCouponsByType(couponType);
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CustomerDoesntOwnCoupon e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons-by-price/{price}", method = RequestMethod.GET)
    public ResponseEntity<?> getPurchasedCouponsByPrice(@PathVariable double price) {
        try {
            Collection<Coupon> coupons = customerService.getPurchasedCouponsByPrice(price);
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
