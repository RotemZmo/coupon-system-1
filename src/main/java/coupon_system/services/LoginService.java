package coupon_system.services;

import coupon_system.enums.ClientType;
import coupon_system.exceptions.LoginFailedException;
import coupon_system.repositories.CompanyRepository;
import coupon_system.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginService {

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public LoginService(CompanyRepository companyRepository,
                        CustomerRepository customerRepository) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
    }

    public CouponClientService login(User user) throws LoginFailedException {

        switch (user.getClientType()) {
            case ADMIN:

            case COMPANY:

            case CUSTOMER:

            default:
                throw new LoginFailedException("Login failed, please try again.");
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
