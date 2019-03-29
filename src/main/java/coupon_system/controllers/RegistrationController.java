package coupon_system.controllers;

import coupon_system.entities.Company;
import coupon_system.entities.Customer;
import coupon_system.exceptions.CouponSystemException;
import coupon_system.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("registration")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class RegistrationController {

    private final RegistrationService registration;

    @Autowired
    public RegistrationController(RegistrationService registration) {
        this.registration = registration;
    }

    @RequestMapping(path = "companies", method = RequestMethod.POST)
    public ResponseEntity<?> registerCompany(@RequestBody Company company) {
        try {
            registration.registerCompany(company);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "customers", method = RequestMethod.POST)
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
        try {
            registration.registerCustomer(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
