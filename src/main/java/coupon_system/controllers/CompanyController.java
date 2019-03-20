package coupon_system.controllers;

import coupon_system.entities.Coupon;
import coupon_system.entities.Income;
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

    @RequestMapping(path = "coupons", method = RequestMethod.POST)
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
        try {
            companyService.createCoupon(coupon);
            return new ResponseEntity<>(coupon, HttpStatus.CREATED);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons/{couponId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCompanyCoupon(@PathVariable int couponId) {
        try {
            Coupon coupon = companyService.getCompanyCoupon(couponId);
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCompanyCoupons() {
        try {
            Collection<Coupon> coupons = companyService.getAllCompanyCoupons();
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons-by-type/{couponType}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCompanyCouponsByType(@PathVariable CouponType couponType) {
        try {
            Collection<Coupon> coupons = companyService.getAllCompanyCouponsByType(couponType);
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CompanyDoesntOwnCoupon e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons-by-price/{price}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCompanyCouponsByPrice(@PathVariable double price) {
        try {
            Collection<Coupon> coupons = companyService.getAllCompanyCouponsByPrice(price);
            return new ResponseEntity<>(coupons, HttpStatus.OK);
        } catch (CompanyDoesntOwnCoupon e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon) {
        try {
            companyService.updateCoupon(coupon);
            return new ResponseEntity<>(coupon, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "coupons/{couponId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCoupon(@PathVariable int couponId) {
        try {
            companyService.deleteCoupon(couponId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "incomes", method = RequestMethod.GET)
    public ResponseEntity<?> getCompanyIncomes() {
        try {
            Collection<Income> incomes = companyService.getCompanyIncomes();
            return new ResponseEntity<>(incomes, HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
