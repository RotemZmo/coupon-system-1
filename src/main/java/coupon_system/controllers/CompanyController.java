package coupon_system.controllers;

import coupon_system.entities.Coupon;
import coupon_system.enums.CouponType;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.exceptions.companyExceptions.CompanyDoesntOwnCoupon;
import coupon_system.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping(path = "coupon", method = RequestMethod.POST)
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
        try {
            companyService.createCoupon(coupon);
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupon-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCompanyCoupon(@PathVariable("id") int couponId) {
        try {
            Coupon coupon = companyService.getCompanyCoupon(couponId);
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupon", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCompanyCoupons() {
        try {
            Collection<Coupon> coupons = companyService.getAllCompanyCoupons();
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupon-by-type/{type}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCompanyCouponsByType(@PathVariable("type") CouponType couponType) {
        try {
            Collection<Coupon> coupons = companyService.getAllCompanyCouponsByType(couponType);
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CompanyDoesntOwnCoupon e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupon-by-price/{price}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCompanyCouponsByPrice(@PathVariable("price") double price) {
        try {
            Collection<Coupon> coupons = companyService.getAllCompanyCouponsByPrice(price);
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CompanyDoesntOwnCoupon e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupon", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon) {
        try {
            companyService.updateCoupon(coupon);
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupon/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCoupon(@PathVariable("id") int id) {
        try {
            companyService.deleteCoupon(id);
            return new ResponseEntity<>("Coupon successfully deleted.", HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
