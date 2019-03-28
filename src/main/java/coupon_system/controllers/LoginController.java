package coupon_system.controllers;

import coupon_system.entities.Token;
import coupon_system.enums.ClientType;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.main_app.CouponSystem;
import coupon_system.repositories.TokenRepository;
import coupon_system.utilities.SecureTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("login")
@Scope("session")
@CrossOrigin(value = "http://localhost:4200",
        allowCredentials = "true",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
        allowedHeaders = {"Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Access-Control-Allow-Origin", "Authorization"},
        exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"})
public class LoginController {

    private final CouponSystem couponSystem;
    private final TokenRepository tokenRepository;

    @Autowired
    public LoginController(CouponSystem couponSystem, TokenRepository tokenRepository) {
        this.couponSystem = couponSystem;
        this.tokenRepository = tokenRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        switch (user.getClientType()) {
            case ADMIN:
                try {
                    if (couponSystem.login(user.getName(), user.getPassword(), user.getClientType()) == 1) {
                        Token token = new Token(ClientType.ADMIN, getDateAfterMonths(2), SecureTokenGenerator.nextToken());
                        tokenRepository.save(token);
                        Cookie cookie = new Cookie("auth", token.getToken());
                        response.addCookie(cookie);
                        return new ResponseEntity<>(HttpStatus.ACCEPTED);
                    }
                } catch (LoginFailedException e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            case COMPANY:
                try {
                    long companyId = couponSystem.login(user.getName(), user.getPassword(), user.getClientType());
                    Token token = new Token(companyId, ClientType.COMPANY, getDateAfterMonths(2), SecureTokenGenerator.nextToken());
                    tokenRepository.save(token);
                    Cookie cookie = new Cookie("auth", token.getToken());
                    response.addCookie(cookie);
                    return new ResponseEntity<>(HttpStatus.ACCEPTED);
                } catch (LoginFailedException e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            case CUSTOMER:
                try {
                    long customerId = couponSystem.login(user.getName(), user.getPassword(), user.getClientType());
                    Token token = new Token(customerId, ClientType.CUSTOMER, getDateAfterMonths(2), SecureTokenGenerator.nextToken());
                    tokenRepository.save(token);
                    Cookie cookie = new Cookie("auth", token.getToken());
                    response.addCookie(cookie);
                    return new ResponseEntity<>(HttpStatus.ACCEPTED);
                } catch (LoginFailedException e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private Date getDateAfterMonths(int months) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
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


