package coupon_system.controllers;

import coupon_system.enums.ClientType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

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
