package coupon_system.controllers;

import coupon_system.entities.Token;
import coupon_system.enums.ClientType;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.repositories.TokenRepository;
import coupon_system.services.LoginService;
import coupon_system.utilities.DateGenerator;
import coupon_system.utilities.SecureTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("login")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LoginController {

    private final LoginService loginService;
    private final TokenRepository tokenRepository;

    @Autowired
    public LoginController(LoginService loginService, TokenRepository tokenRepository) {
        this.loginService = loginService;
        this.tokenRepository = tokenRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
        try {
            long userId = loginService.login(user.getName(), user.getPassword());
            System.out.println(userId);
            Token token = new Token(userId, user.getClientType(), DateGenerator.getDateAfterMonths(2), SecureTokenGenerator.nextToken());
            tokenRepository.save(token);
            Cookie cookie = new Cookie("auth", token.getToken());
            cookie.setMaxAge(/* ~2 months */ 5000000);
            response.addCookie(cookie);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (LoginFailedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
